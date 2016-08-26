package eu.svez.template.websocket.examples

import akka.http.scaladsl.model.ws.Message
import akka.stream.scaladsl.Sink
import eu.svez.template.websocket.common.ListeningPeer
import eu.svez.template.websocket.ops._

trait LoggingPeer extends ListeningPeer {

  override val messageSink: Sink[Message, _] = flow.messageToString() to sink.log

}
