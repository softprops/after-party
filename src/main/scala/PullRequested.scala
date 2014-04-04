package afterparty

case class PullRequest(
  action: String,
  number: Int,
  pull_request: PullReq,
  repository: Repo,
  sender: User
)

// todo: fill out other props
case class PullReq(
  id: Int,
  url: String,
  html_url: String,
  number: Int,
  state: String,
  title: String,
  body: String,
  created_at: String,
  closed_at: Option[String],
  merged_at: Option[String],
  user: User,
  merged_by: Option[User],
  merged: Boolean,
  mergeable: Option[Boolean],
  head: Ref,
  base: Ref
)


case class Ref(
  label: String,
  ref: String,
  sha: String,
  user: User,
  repo: Repo
)

