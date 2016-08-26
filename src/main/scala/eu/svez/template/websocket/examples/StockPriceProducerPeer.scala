package eu.svez.template.websocket.examples

import akka.http.scaladsl.model.ws.TextMessage
import akka.stream.scaladsl.Source
import eu.svez.template.websocket.common.PushingPeer

import scala.concurrent.duration._
import scala.util.Random

trait StockPriceProducerPeer extends PushingPeer {

  override val messageSource: Source[TextMessage, _] =
    Source(Stream.continually(() => Random.nextDouble()))
      .zip(Source.tick(1 second, 1 second, (): Unit)).map{
      case (price, _) => TextMessage(price().toString)
    }

}
