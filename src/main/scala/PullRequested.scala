package afterparty

case class PullRequest(
  action: String,
  number: Int,
  pull_request: PullReq)

// todo: fill out other props
case class PullReq(
  id: Int,
  url: String,
  number: Int,
  state: String,
  title: String,
  body: String,
  created_at: String,
  closed_at: String,
  merged_at: String,
  repo: Repo,
  user: User,
  merged_by: User,
  merged: Boolean,
  mergeable: Boolean)

