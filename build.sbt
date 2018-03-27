scalaVersion := "2.12.4"
name := "chisel-scalacheck"
organization := ""
version := "0.1"

// Provide a managed dependency on X if -DXVersion="" is supplied on the command line.
// The following are the default development versions, not the "release" versions.
val defaultVersions = Map(
  "chisel3" -> "3.0.+",
  "chisel-iotesters" -> "1.1.+"
)

libraryDependencies ++= (Seq("chisel3", "chisel-iotesters").map { dep: String =>
  "edu.berkeley.cs" %% dep % sys.props.getOrElse(dep + "Version",
                                                 defaultVersions(dep))
})

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
