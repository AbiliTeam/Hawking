package hawking.desktop

import play.socketio.scaladsl.SocketIO
import akka.stream.Materializer
import akka.stream.scaladsl._
import akka.NotUsed

class MyEngine(socketIO: SocketIO)(implicit mat: Materializer) {

  val chatFlow: Flow[String, String, NotUsed] = {
    val (sink, source) = MergeHub.source[String].toMat(BroadcastHub.sink)(Keep.both).run()
    Flow.fromSinkAndSourceCoupled(sink, source)
  }

  val controller = {
    socketIO.builder
      .defaultNamespace(Codec.decoder, Codec.encoder)
  }
}