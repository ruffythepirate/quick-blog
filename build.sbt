
version := "1.0-SNAPSHOT"


lazy val commonSettings = Seq(

  scalaVersion := "2.12.6",
    name := """quick-blog""",
    organization := "de.ruffy"
)

lazy val root = (project in file("."))
  .configs(ITest)
  .settings( inConfig(ITest)(Defaults.testSettings) : _*)
  .settings(
    commonSettings,
  ).enablePlugins(PlayScala)


lazy val ITest = config("it") extend(Test)

javaSource in ITest := baseDirectory.value / "/it"
resourceDirectory in ITest := baseDirectory.value / "/it/resources"
scalaSource in ITest := baseDirectory.value / "/it"

val testDependencies = Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2",
  "org.mockito" % "mockito-core" % "2.7.19",
   "com.whisk" %% "docker-testkit-scalatest" % "0.9.5",
   "com.whisk" %% "docker-testkit-impl-spotify" % "0.9.5"
)


libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.4"
libraryDependencies ++= testDependencies.map(d => d % "test,it")



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "de.ruffy.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "de.ruffy.binders._"
