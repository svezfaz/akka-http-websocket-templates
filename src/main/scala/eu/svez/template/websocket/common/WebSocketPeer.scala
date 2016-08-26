package eu.svez.template.websocket.common

import akka.http.scaladsl.model.ws.Message
import akka.stream.Materializer
import akka.stream.scaladsl.Flow

import scala.concurrent.ExecutionContext

trait WebSocketPeer {

  implicit val executionContext: ExecutionContext
  implicit val materializer: Materializer

  val handlerFlow: Flow[Message, Message, _]

}
