package eu.svez.template.websocket

import akka.NotUsed
import akka.http.scaladsl.model.ws.{BinaryMessage, Message, TextMessage}
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Sink}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

object ops {

  object flow {

    def messageToString(parallelism: Int = 4, streamedMessageTimeout: FiniteDuration = 5 seconds)
                       (implicit mat: Materializer, ac: ExecutionContext): Flow[Message, String, NotUsed] =
      Flow[Message].mapAsync(parallelism) {
        case msg: BinaryMessage =>
          msg.dataStream.runWith(Sink.ignore)
          Future.successful(None)
        case TextMessage.Strict(msg) =>
          Future.successful(Some(msg))
        case TextMessage.Streamed(src) =>
          src.completionTimeout(streamedMessageTimeout).runFold("")(_ + _).map { msg =>
            Some(msg)
          }
      }.collect {
        case Some(msg) => msg
      }

    val stringToMessage: Flow[String, Message, NotUsed] = Flow[String].map(TextMessage.apply)

  }

  object sink {

    val log: Sink[String, NotUsed] = Flow[String]
      .map(message => println(s"Received text message: [$message]"))
      .to(Sink.ignore)

  }

}
