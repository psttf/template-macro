val paradiseVersion = "2.1.0-M5"

val buildSettings = Defaults.defaultSettings ++ Seq(
  organization := "com.dc",
  version := "0.0.2",
  scalacOptions ++= Seq(),
  scalaVersion := "2.11.7",
  crossScalaVersions := Seq("2.10.2", "2.10.3", "2.10.4", "2.10.5", "2.10.6", "2.11.0", "2.11.1", "2.11.2", "2.11.3", "2.11.4", "2.11.5", "2.11.6", "2.11.7"),
  resolvers += Resolver.sonatypeRepo("snapshots"),
  resolvers += Resolver.sonatypeRepo("releases"),
  addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)
)

val publishSettings = BintrayPlugin.bintrayPublishSettings ++ Seq(
  description := "Scala macro to generate apply and unapply functions for an object.",
  homepage := Some(url("https://github.com/pomadchin/template-macro")),
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  publishMavenStyle := true,
  bintrayRepository := "maven",
  bintrayOrganization := None
)

lazy val root = Project(
  "root",
  file("."),
  settings = buildSettings ++ Seq(
    run <<= run in Compile in example
  )
) aggregate(macros, example)

lazy val macros = Project(
  "macros",
  file("macros"),
  settings = buildSettings ++ publishSettings ++ Seq(
    name := "template-macros",
    scalacOptions in Test += "-Yrangepos",
    libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _),
    libraryDependencies += "org.specs2" %% "specs2-core" % "3.6.5" % "test",
    libraryDependencies ++= (
      if (scalaVersion.value.startsWith("2.10")) List("org.scalamacros" %% "quasiquotes" % paradiseVersion)
      else Nil
    )
  )
)

lazy val example = Project(
  "example",
  file("example"),
  settings = buildSettings
) dependsOn(macros)
