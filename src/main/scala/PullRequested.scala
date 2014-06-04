package afterparty

/** https://developer.github.com/v3/activity/events/types/#pullrequestevent */
case class PullRequest(
  action: String,
  number: Int,
  pull_request: PullReq,
  repository: Repo,
  sender: User
)