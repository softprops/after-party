package afterparty

import dispatch._, dispatch.Defaults._
import hipshot._

object Hipchat {
  private[this] lazy val props = {
    val file = getClass.getResourceAsStream("/afterparty.properties")
    val props = new java.util.Properties
    props.load(file)
    file.close()
    props
  }
 
  private[this] def prop(name: String) = Option(props.getProperty(name))

  def notify(msg: String) =
    for {
      token <- prop("afterparty.hipchat.token")
      room <- prop("afterparty.hipchat.room")
    } yield {
      hipshot.Client(token).rooms.notify(
        room,
        msg
      )(as.String).apply()
    }
}
