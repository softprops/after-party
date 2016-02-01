import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform

organization := "me.lessis"

name := "after-party"

version := "0.1.0"

crossScalaVersions := Seq("2.11.7")

scalaVersion := crossScalaVersions.value.head

libraryDependencies ++= Seq(
  "net.databinder" %% "unfiltered-netty-server" % "0.7.1",
  "org.json4s" %% "json4s-native" % "3.3.0",
   "net.databinder.dispatch" %% "dispatch-core" % "0.11.2"
)

buildInfoKeys := Seq(version)

buildInfoPackage := "afterparty"

licenses := Seq(
  "MIT" ->
  url(s"https://github.com/softprops/${name.value}/blob/${version.value}/LICENSE")
)

homepage := Some(
  url(s"https://github.com/softprops/${name.value}/#readme")
)

pomExtra := (
  <scm>
    <url>git@github.com:softprops/{name.value}.git</url>
    <connection>scm:git:git@github.com:softprops/{name.value}.git</connection>
  </scm>
  <developers>
    <developer>
      <id>softprops</id>
      <name>Doug Tangren</name>
      <url>https://github.com/softprops</url>
    </developer>
  </developers>
)

bintrayPackageLabels := Seq("github", "webhooks")

SbtScalariform.scalariformSettings ++ Seq(
  SbtScalariform.ScalariformKeys.preferences := SbtScalariform.ScalariformKeys.preferences.value
    .setPreference(AlignArguments, false)
    .setPreference(AlignParameters, false)
    .setPreference(AlignSingleLineCaseStatements, false)
    .setPreference(CompactControlReadability, false)
    .setPreference(CompactStringConcatenation, false)
    .setPreference(DoubleIndentClassDeclaration, true)
    .setPreference(FormatXml, true)
    .setPreference(IndentLocalDefs, true)
    .setPreference(IndentPackageBlocks, true)
    .setPreference(IndentSpaces, 2)
    .setPreference(IndentWithTabs, false)
    .setPreference(MultilineScaladocCommentsStartOnFirstLine, false)
    .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, false)
    .setPreference(PreserveDanglingCloseParenthesis, false)
    .setPreference(PreserveSpaceBeforeArguments, false)
    .setPreference(RewriteArrowSymbols, false)
    .setPreference(SpacesAroundMultiImports, false)
    .setPreference(SpaceBeforeColon, false)
    .setPreference(SpaceInsideBrackets, false)
    .setPreference(SpaceInsideParentheses, false)
    .setPreference(SpacesWithinPatternBinders, true))
