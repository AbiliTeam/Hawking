package hawking.routing

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.io.StdIn
import scala.util.{Failure, Success}

object RoutingServer extends App {
  implicit val system = ActorSystem("hawking-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val route =
    concat (
      post {
        path("addip") {
          entity(as[String]) { ipAddr =>
            val fToken: Future[Token] = PairingBroker.makePair(ipAddr)
            onComplete(fToken) {
              case Success(token) => complete(s"${token.value}")
              case Failure(e) => failWith(e)
            }
          }
        }
      },
      get {
        pathPrefix("getip" / IntNumber) { token =>
          onComplete(PairingBroker.getIp(Token(token))) {
            case Success(ipaddr) => complete(ipaddr)
            case Failure(_: NoSuchElementException)
              => complete(422, s"No IP address matches token: $token")
            case Failure(e) => failWith(e)
          }
        }
      }
    )

  val bindingFuture = Http().bindAndHandle(route, "localhost", 7000)

  println("Server online at http://localhost:7000/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}