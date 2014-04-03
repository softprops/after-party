package afterparty

object PullRequestComment(comment: PRComment)

case class PRComment(
  url: String,
  id: Int,
  user: User,
  body: String,
  created_at: String,
  updated_at: String,
  pull_request_url: String,
  html_url: String
)
