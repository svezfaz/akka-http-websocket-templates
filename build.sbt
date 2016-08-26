
name := "akka-http-websocket-templates"

organization := "eu.svez"

version := "1.0"

scalaVersion := "2.11.8"

val akkaHttp = "com.typesafe.akka" %% "akka-http-experimental" % "2.4.9"

lazy val root = (project in file("."))
  .settings(libraryDependencies += akkaHttp)
