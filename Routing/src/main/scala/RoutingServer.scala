import akka.actor.ActorSystem
import akka.http.scaladsl.server.RequestContextImpl
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.io.StdIn
import scala.util.{Failure, Success}

object RoutingServer extends App {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val route =
    post {
      path("addip") { entity(as[String]) { ipAddr =>
        println(ipAddr)
        val fToken: Future[Token] = Future(Token("1234"))
        onComplete(fToken) {
          case Success(token) => complete(token.value)
          case Failure(_)     => failWith(new RuntimeException("Failed to create token"))
        }
      }}
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 7000)

  println("Server online at http://localhost:7000/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}