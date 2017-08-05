name := "ruhe"
scalaVersion := "2.12.2"
organization := "com.eddsteel"
version := "slice1"

libraryDependencies += "org.typelevel" %%% "cats"        % "0.9.0"
libraryDependencies += "org.scala-js"  %%% "scalajs-dom" % "0.9.1"

enablePlugins(ScalaJSPlugin)

Lint.settings
Flags.settings
ScalaJS.settings
