
version := "1.0-SNAPSHOT"


lazy val commonSettings = Seq(

  scalaVersion := "2.12.6",
    name := """quick-blog""",
    organization := "de.ruffy"
)

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    commonSettings,
    Defaults.itSettings,
  ).enablePlugins(PlayScala)

val testDependencies = Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2",
  "org.mockito" % "mockito-core" % "2.7.19",
   "com.whisk" %% "docker-testkit-scalatest" % "0.9.5",
   "com.whisk" %% "docker-testkit-impl-spotify" % "0.9.5"
)


libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1102-jdbc4"
libraryDependencies ++= testDependencies.map(d => d % "test,it")



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "de.ruffy.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "de.ruffy.binders._"
