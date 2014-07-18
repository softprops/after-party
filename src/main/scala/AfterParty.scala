package afterparty

import unfiltered.netty
import unfiltered.request.{ Params, StringHeader, & }
import unfiltered.response.Ok
import org.jboss.netty.channel.ChannelHandlerContext
import org.json4s.native.JsonMethods.parse
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.control.NonFatal
import java.util.Date

object XGithubDelivery extends StringHeader("X-GitHub-Delivery")
object XGithubEvent extends StringHeader("X-Github-Event")
object XGithubSignature extends StringHeader("X-GitHub-Signature")
object Payload extends Params.Extract("payload", Params.first)

object AfterParty {
  type Handler[_] = PartialFunction[Event[_], Unit]
  val Unhandled: AfterParty.Handler[_] = {
    case event => println(s"unhandled ${event.name} event")
  }
  val empty = AfterParty()
}

case class AfterParty private[afterparty](
  handlers: List[AfterParty.Handler[_]] = Nil)
  extends netty.async.Plan {

  def handle(f: AfterParty.Handler[_]) = copy(handlers = f :: handlers)

  object shell {
    def onPullRequest(program: String, args: String*) = handle({
      case e: Event.PullRequested => Shell.pipe(e)(program)(args: _*)
    })

    def onPush(program: String, args: String*) = handle({
      case e: Event.Pushed => Shell.pipe(e)(program)(args:_*)
    })

    def onPing(program: String, args: String*) = handle({
      case e: Event.Pinged => Shell.pipe(e)(program)(args: _*)
    })
  }

  def onPullRequest(f: PullRequest => Unit) = handle({
    case e: Event.PullRequested => f(e.payload)
  })

  def onPulRequestComment(f: PullRequestComment => Unit) = handle({
    case e: Event.PullRequestCommented => f(e.payload)
  })

  def onIssueCommented(f: IssueComment => Unit) = handle({
    case e: Event.IssueCommented => f(e.payload)
  })

  def onPush(f: Push => Unit) = handle({
    case e: Event.Pushed => f(e.payload)
  })

  def onPing(f: Ping => Unit) = handle({
    case e: Event.Pinged => f(e.payload)
  })

  private def prefix = s"[after party ${new Date}]"

  def intent = {
    case req @ XGithubEvent(event) & Params(Payload(payload)) =>
      println(s"$prefix hit with $event")
      val hands = for {
        ev <- Event.of(event, parse(payload))
      } handlers.filter(_.isDefinedAt(ev)) match {
        case Nil   =>
	  Future(AfterParty.Unhandled(ev))
        case hands => hands.foreach { hand =>
          Future(hand(ev)).onFailure({ case NonFatal(e) =>
            println(s"$prefix $event handler for event $event failed ${e.getMessage} with payload $payload")
	    println(s"$prefix error ==")
            e.printStackTrace
          })
        }
      }
      req.respond(Ok)
    case req =>
      val now = new Date()
      val prefix = s"[after party $now]"
      println(s"$prefix request didn't have x github event or payload param")
      req.respond(Ok)
  }

  def onException(ctx: ChannelHandlerContext, t: Throwable) = {
    t.printStackTrace
    ctx.getChannel.close()
  }
}
