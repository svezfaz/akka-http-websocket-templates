package eu.svez.template.websocket

import akka.NotUsed
import akka.http.scaladsl.model.ws.Message
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.ExecutionContext

trait WebSocketPeer {
  implicit val executionContext: ExecutionContext
  implicit val materializer: Materializer

  val handlerFlow: Flow[Message, Message, NotUsed]
}

trait PushingPeer extends IndependentPeer {
  import ops._

  override val messageSink: Sink[Message, _] = sink.ignoreMessages()
}

trait ListeningPeer extends IndependentPeer {
  override val messageSource: Source[Message, _] = Source.maybe
}

trait ReplyingPeer extends WebSocketPeer

trait IndependentPeer extends WebSocketPeer {
  val messageSource: Source[Message, _]
  val messageSink: Sink[Message, _]

  override lazy val handlerFlow = Flow.fromSinkAndSource(messageSink, messageSource)
}

