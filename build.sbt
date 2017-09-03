// scalafmt: {align.tokens = ["%", "%%", "%%%"]}

import org.scalajs.sbtplugin.cross.CrossProject

scalaVersion in ThisBuild := "2.12.3"
organization in ThisBuild := "com.eddsteel"
version in ThisBuild := "slice3"

// thanks https://gitlab.com/bullbytes/scala-js-example/blob/master/build.sbt
def jsOp =
  if (sys.props.get("production").exists(value => value.equalsIgnoreCase("true"))) fullOptJS
  else fastOptJS

val ruhe: CrossProject = crossProject
  .in(file("."))
  .settings(
    libraryDependencies += "org.typelevel" %%% "cats" % "0.9.0",
    Lint.settings,
    Flags.settings
  )

val js: Project = ruhe.js
  .settings(
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1",
    ScalaJS.settings
  )
  .enablePlugins(ScalaJSPlugin)

val jvm: Project = ruhe.jvm
  .settings(
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl"          % "0.17.0",
      "org.http4s" %% "http4s-blaze-server" % "0.17.0"
    ),
    libraryDependencies += "co.fs2" %% "fs2-core" % "0.9.7",
    libraryDependencies += "org.log4s" %% "log4s" % "1.3.4",
    libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.5",
    (resources in Compile) += (packageMinifiedJSDependencies in (js, Compile)).value,
    (resources in Compile) += (jsOp in (js, Compile)).value.data,
    fork in run := true,
    maintainer in Docker := "Edd Steel <edward.steel@gmail.com>",
    packageName in Docker := "eddsteel/ruhe",
    packageSummary in Docker := "An animation",
    packageDescription := "An animation",
    dockerExposedPorts := List(8081),
    dockerBaseImage := "frolvlad/alpine-scala",
    dockerUpdateLatest := true
  )
  .enablePlugins(JavaAppPackaging)

val root: Project = project.in(file(".")).aggregate(js, jvm)
