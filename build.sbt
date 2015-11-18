name := "drifter"

scalaVersion := "2.11.7"

version := "0.1"
organization := "xyz.jmullin.drifter"
version := "0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.badlogicgames.gdx" % "gdx" % "latest.integration" % "provided",
  "com.badlogicgames.gdx" % "gdx-box2d" % "latest.integration" % "provided"
)
