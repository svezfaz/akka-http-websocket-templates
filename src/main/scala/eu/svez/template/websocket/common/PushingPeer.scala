package eu.svez.template.websocket.common

import akka.http.scaladsl.model.ws.Message
import akka.stream.scaladsl.Sink

trait PushingPeer extends IndependentPeer {

  override val messageSink: Sink[Message, _] = Sink.ignore

}


