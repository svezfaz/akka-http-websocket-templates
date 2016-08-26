package eu.svez.template.websocket.endpoint

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws._
import akka.stream.scaladsl.GraphDSL.Builder
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph}
import akka.stream.{ActorMaterializer, ClosedShape}
import eu.svez.template.websocket.common.WebSocketPeer

import scala.concurrent.Future

trait WebSocketClient extends App with WebSocketPeer with WebSocketEndpoint {

  implicit val system = ActorSystem("websocket-client")
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  private lazy val clientFlow: Flow[Message, Message, Future[WebSocketUpgradeResponse]] =
    Http().webSocketClientFlow(WebSocketRequest(s"ws://$host:$port/connect"))

  private lazy val webSocketUpgrade = RunnableGraph.fromGraph[(Future[WebSocketUpgradeResponse])](
    GraphDSL.create[ClosedShape, Future[WebSocketUpgradeResponse]] (clientFlow)
      { implicit builder: Builder[Future[WebSocketUpgradeResponse]] => wsFlow =>
      import GraphDSL.Implicits._

      val clientLoop = builder.add(handlerFlow)
      wsFlow.out ~> clientLoop.in
      wsFlow.in <~ clientLoop.out

      ClosedShape
    }).run()

  delayedInit {
    println(s"Attempting to connect to $host:$port")
    webSocketUpgrade.map {
      upgrade => upgrade.response.status match {
        case StatusCodes.SwitchingProtocols => println(s"Client connected to $host:$port")
        case _ => throw new RuntimeException(s"Connection failed: ${upgrade.response.status}")
      }
    }
  }
}


