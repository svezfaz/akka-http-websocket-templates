package eu.svez.template.websocket.examples

import akka.NotUsed
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl.{Sink, Source}
import eu.svez.template.websocket.IndependentPeer
import eu.svez.template.websocket.ops.{flow, sink}

import scala.util.Random
import scala.concurrent.duration._

trait ChatPeer extends IndependentPeer {
  def random = Random.nextInt(5)

  override val messageSource: Source[TextMessage, NotUsed] =
    Source(Stream.continually(() => Random.alphanumeric take 20 mkString))
      .zip(Source.tick(initialDelay = random seconds, interval = random seconds, tick = (): Unit)).map{
      case (price, _) => TextMessage(price().toString)
    }

  override val messageSink: Sink[Message, NotUsed] = flow.messageToString() to sink.log
}
