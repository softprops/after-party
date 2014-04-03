# after party

define: [webhook](https://help.github.com/articles/post-receive-hooks). a place for your commits to go after the party. where can they go.

* a ci server
* irc notifier
* ...


# usage

create a server. start a party

```scala
import afterparty._
Server(port).start(AfterParty.empty.onPush { push =>
  println(
    s"${push.pusher.name} pushed ${commits.size} commits to ${push.ref}")
})
```

Doug Tangren (softprops) 2014
