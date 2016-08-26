package eu.svez.template.websocket.endpoint

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import eu.svez.template.websocket.common.WebSocketPeer

trait WebSocketServer extends App with WebSocketPeer with WebSocketEndpoint {

  implicit val system = ActorSystem("websocket-server")
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  private val route = get {
    path("connect") {
      extractUpgradeToWebSocket{ wsUpgrade =>
        complete(wsUpgrade.handleMessages(handlerFlow))
      }
    }
  }

  val host = "0.0.0.0"

  delayedInit {
    Http().bindAndHandle(route, host, port).map { _ =>
      println(s"Websocket server started on $host:$port")
    }
  }
}


