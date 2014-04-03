package afterparty

case class IssueComment(
  action: String, issue: Issue, comment: Comment
)

case class Issue(
  url: String,
  number: Int,
  state: String,
  title: String,
  body: String,
  user: User,
  labels: List[Label],
  assignee: User,
  comments: Int,
  created_at: String,
  updated_at: String,
  closed_by: User
)

case class Label(url: String, name: String, color: String)

case class Comment(
  id: Int,
  url: String,
  body: String,
  user: User,
  created_at: String,
  updated_at: String
)

