import sbt._
import sbt.Keys._

object ProjectBuild extends Build {

  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "diablo3-ircbot",
      organization := "cc.clv",
      version := "0.1-SNAPSHOT",
      scalacOptions ++= Seq("-deprecation"),
      scalaVersion := "2.9.1",
      resolvers += "twitter-repo" at "http://maven.twttr.com",
      libraryDependencies ++= Seq(
        // test
        "org.specs2" %% "specs2" % "1.9" % "test",
            
        // log
        "org.clapper" %% "grizzled-slf4j" % "0.6.8",
        "ch.qos.logback" % "logback-classic" % "1.0.1",
        
        // config
        "com.twitter" %% "util-eval" % "3.0.0",
        
        // irc
        "pircbot" % "pircbot" % "1.5.0"
      )
      // add other settings here
    )
  )
}
