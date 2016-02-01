package afterparty

import dispatch._, Defaults._
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods.{compact, render}
import unfiltered.netty
import unfiltered.request.Path
import unfiltered.response.{Ok, Pass}

object Server {
  def register(
    token: String,
    owner: String,
    repo: String,
    name: String,
    hookurl: String,
    apihost: String = "api.github.com"
  ) = {

    val response = Http(
      url(apihost).POST / "api" / "v3" / "repos" / owner / repo / "hooks"
        <:< Map(
          "authorization" -> s"bearer $token",
          "Content-Type" -> "application/json",
          "User-Agent" -> s"Afterparty/${BuildInfo.version}"
        )
          << compact(
            render(
              ("name" -> name) ~
                ("active" -> true) ~
                ("events" ->
                  List(
                    "pull_request", "push", "issue_comment", "pull_request_review_comment", "deployment_status", "deployment", "issues", "member"
                  )) ~
                    ("config" ->
                      ("url" -> hookurl) ~
                      ("content_type" -> "form"))
            )
          )
    ).apply()
    (response.getStatusCode, response.getResponseBody)
  }

  def hooks(
    token: String,
    owner: String,
    repo: String,
    apihost: String = "api.github.com"
  ) = {
    val response = Http((url(apihost) <:< Map(
      "authorization" -> s"bearer $token",
      "content-type" -> "application/json",
      "User-Agent" -> s"Afterparty/${BuildInfo.version}"
    )) / "api" / "v3" / "repos" / owner / repo / "hooks").apply()
    (response.getStatusCode, response.getResponseBody)
  }

  def main(args: Array[String]) {
    Server(4567).start(
      AfterParty.empty
        .onPing { ping =>
          println(s"got ping ${ping.zen}")
        }
        .onPush { push =>
          println(s"got pushed $push")
        }
        .onPullRequest { pr =>
          println(s"got a pull ${pr.action} request from user ${pr.pull_request.user.name} entitled '${pr.pull_request.title}'\n${pr.pull_request.body}")
        }
    )
  }
}

case class Server(
    port: Int,
    path: String = "/"
) {

  // i've it org.jboss.netty.handler.codec.frame.TooLongFrameException: HTTP content length exceeded 1048576 bytes. with the default chunk size
  // so we're generously increasing it below
  def chunkSize: Int =
    sys.env.get("AFTER_PARTY_SERVER_CHUNK_SIZE").map(_.toInt)
      .getOrElse(1048576 * 4)

  def start(after: AfterParty) =
    netty.Http(port).chunked(chunkSize).handler(netty.async.Planify {
      case req @ Path(path) =>
        println(s"afterparty hit with $path")
        after.intent.lift(req).getOrElse {
          req.respond(Pass)
        }
    }).run()
}
