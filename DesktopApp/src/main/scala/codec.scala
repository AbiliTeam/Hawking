import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.socketio.scaladsl.SocketIOEventCodec._

sealed trait HawkingEvent

case class Flag(message: String) extends HawkingEvent
object Flag {
  implicit val format: Format[Flag] = Json.format
}

val decoder = decodeByName {
  case "flag" => decodeJson[String]
}
