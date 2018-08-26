name := """quick-blog"""
organization := "de.ruffy"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.7.19" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "de.ruffy.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "de.ruffy.binders._"
