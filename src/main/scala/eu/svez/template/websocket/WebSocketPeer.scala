package eu.svez.template.websocket

import akka.http.scaladsl.model.ws.Message
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.ExecutionContext

trait WebSocketPeer {

  implicit val executionContext: ExecutionContext
  implicit val materializer: Materializer

  val handlerFlow: Flow[Message, Message, _]

}

trait IndependentPeer extends WebSocketPeer {

  val messageSource: Source[Message, _]
  val messageSink: Sink[Message, _]

  override lazy val handlerFlow = Flow.fromSinkAndSource(messageSink, messageSource)

}

trait PushingPeer extends IndependentPeer {

  override val messageSink: Sink[Message, _] = Sink.ignore

}

trait ListeningPeer extends IndependentPeer {

  override val messageSource: Source[Message, _] = Source.maybe

}