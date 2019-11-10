package hawking.gesture

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.unmarshalling._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn
import scala.util.{Failure, Success}
import spray.json._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import java.net._
import java.util.concurrent.TimeUnit

import akka.http.scaladsl.unmarshalling.Unmarshal

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val rotationRateFormat = jsonFormat3(RotationRate)
  implicit val accelerationFormat = jsonFormat3(Acceleration)
  implicit val motionFormat = jsonFormat2(Motion)
  implicit val orientationFormat = jsonFormat3(Orientation)
  implicit val eventFormat = jsonFormat4(Event)
}

object RoutingServer extends App with JsonSupport {
  implicit val system = ActorSystem("hawking-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val routingServerURI = "http://[IP]:7000"

  val localhost: InetAddress = InetAddress.getLocalHost
  val localIpAddress: String = localhost.getHostAddress

  lazy val fToken: Future[HttpResponse] =
    Http().singleRequest(HttpRequest(
      method = HttpMethods.POST,
      uri = s"$routingServerURI/addip",
      entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, localIpAddress)))

  lazy val token: Int =
    Await.result(fToken.flatMap(x => Unmarshal(x.entity).to[String]),
      Duration(10, TimeUnit.SECONDS)).toInt

  val route = cors() {
    post {
      pathPrefix("gesturedata" / IntNumber) {
        _ match {
          case this.token => entity(as[Event]) { event =>
            onComplete(GestureEngine.run(event)) {
              case Success(_) => complete("Gesture Served")
              case Failure(e) => failWith(e)
            }
          }
          case _ => failWith(new RuntimeException("Token mismatch"))
        }
      }
    }
  }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 5000)

  println("Server online at http://localhost:5000/\nPress RETURN to stop...")
  println(s"Your token is $token")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}