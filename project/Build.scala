import sbt._
import sbt.Keys._
import org.scalajs.sbtplugin._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object Build extends sbt.Build {
  val buildOrganisation = "widok-drag-and-drop"
  val buildVersion = "0.1-SNAPSHOT"
  val buildScalaVersion = "2.11.6"
  val buildScalaOptions = Seq(
    "-unchecked", "-deprecation",
    "-encoding", "utf8",
    "-Xelide-below", annotation.elidable.ALL.toString
  )

  lazy val main = Project(id = "widok-drag-and-drop", base = file("."))
    .enablePlugins(ScalaJSPlugin)
    .settings(
      logLevel := Level.Debug,
      libraryDependencies ++= Seq(
        "io.github.widok" %%% "widok" % "0.2.1"
      ),
      organization := buildOrganisation,
      version := buildVersion,
      scalaVersion := buildScalaVersion,
      scalacOptions := buildScalaOptions,
      persistLauncher := true
    )
}
