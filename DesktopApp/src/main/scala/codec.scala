import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.socketio.scaladsl.SocketIOEventCodec._

case class Flag(message: String)
object Flag {
  implicit val format: Format[Flag] = Json.format
}

val decoder = decodeByName {
  case "start response" => decodeJson[String]
}