package afterparty

case class Ref(
  label: String,
  ref: String,
  sha: String,
  user: User,
  repo: Repo
)