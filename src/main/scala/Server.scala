package afterparty

import unfiltered.netty
import unfiltered.request.Path
import unfiltered.response.{ Ok, Pass }

object Server {
  def register(
    token: String, owner: String, repo: String,
    name: String, hookurl: String, apihost: String = "api.github.com") = {
    import dispatch._, Defaults._
    import org.json4s.JsonDSL._
    import org.json4s.native.JsonMethods.{ compact, render }
    Http(:/(apihost).POST / "api" / "v3" / "repos" / owner / repo / "hooks"
         <:< Map("authorization" -> s"bearer $token")
         << compact(render((("name" -> name) ~
                            ("active" -> true) ~
                            ("events" -> List("pull_request", "push", "issue_comment", "pull_request_review_comment", "deployment_status", "deployment", "issues", "member")) ~
                            ("config" ->
                             ("url" -> hookurl) ~
                             ("content_type" -> "form"))))) > as.String).apply()
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
        })
  }
}

case class Server(port: Int, path: String = "/") {
  def start(after: AfterParty) =
    netty.Http(port).chunked().handler(netty.async.Planify {
      case req @ Path(path) => after.intent.lift(req).getOrElse {
        req.respond(Pass)
      }
    }).run()
}
