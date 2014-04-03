package afterparty

import org.json4s.{ DefaultFormats, JValue }

sealed trait Event[T] {
  def name: String
  def json: JValue
  def payload: T
}

object Event {
  implicit val formats = DefaultFormats

  abstract class Named[T](val name: String)(implicit ev: Manifest[T]) extends Event[T] {
    lazy val payload = json.extract[T]
  }
  
  case class Pinged(json: JValue) extends Named[Ping]("ping")

  /** https://developer.github.com/v3/activity/events/types/#pushevent */
  case class Pushed(json: JValue) extends Named[Push]("push")

  /* https://developer.github.com/v3/activity/events/types/#pullrequestevent */
  case class PullRequested(json: JValue) extends Named[PullRequest]("pull_request")

  /** https://developer.github.com/v3/activity/events/types/#pullrequestreviewcommentevent */
  case class PullRequestCommented(json: JValue) extends Named[PullRequestComment]("pull_request_review_comment")

  /** https://developer.github.com/v3/activity/events/types/#issuecommentevent */
  case class IssueCommented(json: JValue) extends Named[IssueComment]("issue_comment")

  private[this] val events: Map[String, JValue => Event[_]] =
    Map("issue_comment" -> { IssueCommented(_) },
        "push" -> { Pushed(_) },
        "ping" -> { Pinged(_) },
        "pull_request" -> { PullRequested(_)},
        "pull_request_review_comment" -> { PullRequestCommented(_) })

  def of(hook: String, json: JValue) = events.get(hook).map(_(json))
}
