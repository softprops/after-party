package afterparty

import unfiltered.netty
import unfiltered.request.{ Path, Params }
import unfiltered.response.Pass

object Server {
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
    netty.Http(port).handler(netty.async.Planify {
      case req @ Path(path) => after.intent.lift(req).getOrElse {
        println(s"passing on path $path with params ${Params.unapply(req)}")
        req.respond(Pass)
      }
    }).run()
}
