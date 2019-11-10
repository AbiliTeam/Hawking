import play.api.libs.json._
import play.socketio.scaladsl.SocketIOEventCodec._

sealed trait HawkingEvent

case class Flag(message: String) extends HawkingEvent

object Flag {
  implicit val format: Format[Flag] = Json.format
}

case class Orientation(z: Int, x: Int, y: Int) extends HawkingEvent

object Orientation {
  implicit val format: Format[Orientation] = Json.format
}

case class Motion(acceleration: Acceleration, rotation: Rotation, interval: Interval)

object Motion {
  implicit val format: Format[Motion] = Json.format
}

case class Acceleration(x: Int, y: Int, z: Int)

object Acceleration {
  implicit val format: Format[Acceleration] = Json.format
}

case class Rotation(alpha: Int, beta: Int, gamma: Int)

object Rotation {
  implicit val format: Format[Rotation] = Json.format
}

case class Interval(ms: Int)

object Interval {
  implicit val format: Format[Orientation] = Json.format
}

val decoder = decodeByName {
  case "flag" => decodeJson[Flag]
  case "orientation event" => decodeJson[Orientation]
  case "motion event" => decodeJson[Motion]
}
