package afterparty

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
