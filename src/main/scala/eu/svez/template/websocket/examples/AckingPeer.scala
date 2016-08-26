package eu.svez.template.websocket.examples

import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl.Flow
import eu.svez.template.websocket.common.WebSocketPeer
import eu.svez.template.websocket.ops._

trait AckingPeer extends WebSocketPeer {

  private def ack(message: String): Message = TextMessage(s"ACK for: $message")

  override val handlerFlow: Flow[Message, Message, _] = flow.messageToString() map ack
}
