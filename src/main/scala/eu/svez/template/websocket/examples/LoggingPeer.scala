package eu.svez.template.websocket.examples

import akka.NotUsed
import akka.http.scaladsl.model.ws.Message
import akka.stream.scaladsl.Sink
import eu.svez.template.websocket.ListeningPeer
import eu.svez.template.websocket.ops._

trait LoggingPeer extends ListeningPeer {
  override val messageSink: Sink[Message, NotUsed] = flow.messageToString() to sink.log
}
