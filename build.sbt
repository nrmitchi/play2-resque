name := "play2-resque"

version := "0.0.3"

organization := "com.nrmitchi.plugin"

scalaVersion := "2.10.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= {
  val playVersion = "2.1.4"
  Seq(
    "play" %% "play" % playVersion,
    "play" %% "play-test" % playVersion % "test",
    "net.greghaines" % "jesque" % "2.0.0"
  )
}


publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

credentials += Credentials(Path.userHome / ".sbt" / "sonatype.creds")

crossPaths := false

publishArtifact in Test := false

pomIncludeRepository := { repo => false }

pomExtra := (
  <url>https://github.com/nrmitchi/play2-resque</url>
  <licenses>
    <license>
      <name>Apache 2.0 License</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:nrmitchi/play2-resque.git</url>
    <connection>git@github.com:nrmitchi/play2-resque.git</connection>
  </scm>
  <developers>
    <developer>
      <id>nrmitchi</id>
      <name>Nick Mitchinson</name>
      <url>http://www.nrmitchi.com</url>
    </developer>
  </developers>)
