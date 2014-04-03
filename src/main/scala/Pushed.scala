package afterparty

case class User(name: String, email: Option[String], username: Option[String])

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
  modified: List[String])

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
  pusher: User)

case class Repo(
  id: Int,
  name: String,
  url: String,
  description: String,
  watchers: Int,
  stargazers: Int,
  forks: Int,
  fork: Boolean,
  size: Int,
  owner: User,
  `private`: Boolean,
  open_issues: Int,
  has_issues: Boolean,
  has_downloads: Boolean,
  has_wiki: Boolean,
  language: String,
  created_at: Int,
  pushed_at: Int,
  master_branch: String)
