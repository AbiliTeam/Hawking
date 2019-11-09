import akka.stream.Materializer
import akka.stream.scaladsl._
import play.engineio.EngineIOController
import play.socketio.scaladsl.SocketIO

class GestureEngine(socketIO: SocketIO)(implicit mat: Materializer) {

  import play.socketio.scaladsl.SocketIOEventCodec._

  // codec to encode/codec chat message events to/from strings
  val decoder = decodeByName {
    case "chat message" => decodeJson[String]
  }
  val encoder = encodeByType[String] {
    case _: String => "chat message" -> encodeJson[String]
  }

  // Merge/broadcast hub that each client will connect to
  private val chatFlow = {
    val (sink, source) = MergeHub.source[String]
      .toMat(BroadcastHub.sink)(Keep.both).run
    Flow.fromSinkAndSourceCoupled(sink, source)
  }

  val controller: EngineIOController = socketIO.builder
    .addNamespace("/chat", decoder, encoder, chatFlow)
    .createController()
}