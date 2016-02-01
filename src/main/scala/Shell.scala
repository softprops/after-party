package afterparty

import scala.collection.JavaConverters._

import java.io.{BufferedWriter, OutputStreamWriter}
import org.json4s.native.JsonMethods.{compact, render}

/**
 * a shell program interface. shell programs should expect to take at least one argument,
 *  the event name, and an arbitrary list of handler defined args. The payload of the event
 *  will be passed in as stdinput. assume execution as cat push.json | /bin/program push
 */
object Shell {
  def pipe(event: Event[_])(program: String)(args: String*): Int = {
    val proc = new ProcessBuilder((program :: event.name :: args.toList).asJava).start()
    val stdin = proc.getOutputStream()
    val writer = new BufferedWriter(new OutputStreamWriter(stdin))
    writer.write(compact(render(event.json)))
    writer.newLine()
    writer.flush()
    writer.close()
    proc.waitFor()
  }
}
