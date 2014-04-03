package afterparty

import unfiltered.netty
import unfiltered.request.Path

object Server {
  def main(args: Array[String]) {
    Server(4567).run(AfterParty.empty.onPush {
      push => println(s"got pushed $push")
    })
  }
}

case class Server(port: Int, path: String = "/") {
  def run(after: AfterParty) =
    netty.Http(port).handler(netty.async.Planify {
      case req @ Path(path) => after.intent(req)
    }).run()
}
