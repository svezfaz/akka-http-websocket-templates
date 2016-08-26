package eu.svez.template.websocket.common

import akka.http.scaladsl.model.ws.Message
import akka.stream.scaladsl.Source

trait ListeningPeer extends IndependentPeer {

  override val messageSource: Source[Message, _] = Source.maybe

}


