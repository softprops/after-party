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
  number: Int,
  state: String,
  title: String,
  body: String,
  created_at: String,
  closed_at: String,
  merged_at: String,
  user: User,
  merged_by: User,
  merged: Boolean,
  mergeable: Boolean,
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

