package com.eddsteel.ruhe

import org.http4s._
import org.http4s.dsl._
import org.http4s.server.blaze._
import org.http4s.util.StreamApp
import org.log4s
import fs2.{Stream, Task}

object Server extends StreamApp {

  private val logger = log4s.getLogger

  def static(file: String, request: Request): Task[Response] =
    StaticFile.fromResource("/" + file, Some(request)).value.flatMap { maybeResponse =>
      maybeResponse.map(Task.now).getOrElse(NotFound())
    }

  val service: HttpService = HttpService {
    case request @ GET -> Root =>
      logger.info("GET /")
      static("index.html", request)

    case request @ GET -> Root / path if List(".js", ".map", ".html").exists(path.endsWith) =>
      logger.info(s"GET /$path")
      static(path, request)
  }

  def stream(args: List[String]): Stream[Task, Nothing] =
    BlazeBuilder.bindHttp(8081, "0.0.0.0").mountService(service, "/").serve
}
