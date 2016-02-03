package afterparty

case class User(login: Option[String], name: Option[String], email: Option[String], username: Option[String]) {
  val identifier = login.orElse(name).getOrElse("nobody")
}

case class Commit(
  id: String,
  message: String,
  timestamp: String,
  author: User,
  committer: User,
  url: String,
  distinct: Boolean,
  added: List[String],
  removed: List[String],
  modified: List[String]
)

/** https://developer.github.com/v3/activity/events/types/#pushevent */
case class Push(
  ref: String,
  after: String,
  before: String,
  created: Boolean,
  deleted: Boolean,
  forced: Boolean,
  compare: String,
  commits: List[Commit],
  head_commit: Commit,
  repository: Repo,
  pusher: User
)

case class Repo(
  id: Int,
  name: String,
  full_name: String,
  url: String,
  description: String,
  watchers: Int,
  forks: Int,
  fork: Boolean,
  size: Int,
  owner: User,
  open_issues: Int,
  has_issues: Boolean,
  has_downloads: Boolean,
  has_wiki: Boolean,
  language: String,
  created_at: String,
  pushed_at: String,
  default_branch: String
)
