package hawking.routing

import scala.util.Random
import scala.collection.mutable.Map
import scala.concurrent.Future

object PairingBroker {
  import RoutingServer.executionContext
  private val ip2tok = Map[String, Token]()
  private val tok2ip = Map[Token, String]()
  private var generator: List[Int] = Random.shuffle((1000 to 9999).toList)

  def makePair(ipAddr: String): Future[Token] = Future {
    ip2tok.getOrElse(ipAddr, {
      val token = Token(generator.head)
      generator = generator.tail
      ip2tok += (ipAddr -> token)
      tok2ip += (token -> ipAddr)
      token
    })
  }

  def getIp(token: Token): Future[String] = Future {
    tok2ip.get(token).get
  }
}

case class Token(value: Int)
