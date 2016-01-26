organization := "me.lessis"

name := "after-party"

version := "0.1.0-SNAPSHOT"

crossScalaVersions := Seq("2.11.7")

scalaVersion := crossScalaVersions.value.head

libraryDependencies ++= Seq(
  "net.databinder" %% "unfiltered-netty-server" % "0.7.1",
  "org.json4s" %% "json4s-native" % "3.3.0",
   "net.databinder.dispatch" %% "dispatch-core" % "0.11.2")

buildInfoKeys := Seq(version)

buildInfoPackage := "afterparty"
