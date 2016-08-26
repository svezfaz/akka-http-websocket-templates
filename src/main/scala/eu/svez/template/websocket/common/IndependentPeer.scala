package eu.svez.template.websocket.common

import akka.http.scaladsl.model.ws.Message
import akka.stream.scaladsl.{Flow, Sink, Source}

trait IndependentPeer extends WebSocketPeer {

  val messageSource: Source[Message, _]
  val messageSink: Sink[Message, _]

  override lazy val handlerFlow = Flow.fromSinkAndSource(messageSink, messageSource)

}