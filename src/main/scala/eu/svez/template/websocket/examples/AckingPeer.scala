package eu.svez.template.websocket.examples

import akka.NotUsed
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl.Flow
import eu.svez.template.websocket.ReplyingPeer
import eu.svez.template.websocket.ops._

trait AckingPeer extends ReplyingPeer {
  private def ack(message: String): Message = TextMessage(s"ACK for: $message")

  override val handlerFlow: Flow[Message, Message, NotUsed] = flow.messageToString() map ack
}
