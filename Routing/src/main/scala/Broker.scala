package hawking

import RoutingServer.system

import scala.util.Random
import scala.collection.mutable.Map
import scala.concurrent.Future

object PairingBroker {
  implicit val executionContext = system.dispatcher
  private val ip2tok = Map[String, Token]()
  private val tok2ip = Map[Token, String]()

  def makePair(ipAddr: String): Future[Token] = Future {
    ip2tok.getOrElse(ipAddr, {
      val token = Token(Random.between(1000, 10000))
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
