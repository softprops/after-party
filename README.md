# after party

define: [webhook](https://help.github.com/articles/post-receive-hooks). a place for your commits to go after the party. where can they go.

* a ci server
* irc notifier
* ...

## install

This library is currently only published for scala 2.11. Reach out with with a github issue if you would like a version published
for older versions of scala.

Add the following to your build definition

```scala
libraryDependencies += "me.lessis" %% "after-party" % "0.1.0"
```

## usage

create a server. start a party

```scala
import afterparty._
Server(port).start(AfterParty.empty.onPush { push =>
  println(
    s"${push.pusher.name} pushed ${push.commits.size} commits to ${push.ref}")
})
```

if writing shell scripts is your thing you can alternatively pipe these events into one of those


```
import afterparty._
Server(port).start(AfterParty.empty.onPush { push =>
  Shell.pipe(push).cmd("/bin/push")()
})
```



Doug Tangren (softprops) 2014-2016
