package afterparty

case class Author(name: String, email: String, username: Option[String])

case class Commit(
  id: String,
  message: String,
  timestamp: String,
  author: Author,
  committer: Author,
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
  pusher: Author)

case class Repo(id: Int, name: String, url: String, description: String,
              homepage: String, watchers: Int, stargazers: Int, forks: Int,
              fork: Boolean, size: Int, owner: Author, `private`: Boolean,
              open_issues: Int, has_issues: Boolean, has_downloads: Boolean,
              has_wiki: Boolean, language: String, created_at: Int,
              pushed_at: Int,
              master_branch: String)
