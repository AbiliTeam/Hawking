package hawking.desktop

import play.socketio.scaladsl.SocketIO
import akka.stream.{ClosedShape, Materializer}
import akka.stream.scaladsl._
import akka.NotUsed

class MyEngine(socketIO: SocketIO)(implicit mat: Materializer) {

  val mergedSources = Source.combine[HawkingEvent, NotUsed](flags, orientations, motions)(Merge(_))

  val controller = {
    socketIO.builder
      .defaultNamespace(Codec.decoder, Codec.encoder)
  }
}