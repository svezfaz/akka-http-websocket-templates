package eu.svez.template.websocket

import eu.svez.template.websocket.endpoint.WebSocketServer
import eu.svez.template.websocket.examples.{ChatPeer, LoggingPeer, StockPriceProducerPeer}

object ChatServer extends ChatPeer with WebSocketServer with LocalHostAndPort

object ChatClient extends ChatPeer with WebSocketClient with LocalHostAndPort

object StockPriceServer extends StockPriceProducerPeer with WebSocketServer with LocalHostAndPort

object LoggingClient extends LoggingPeer with WebSocketClient with LocalHostAndPort

trait LocalHostAndPort {
  self: WebSocketEndpoint =>

  override val host: String = "localhost"
  override val port: Int = 8080
}
