import sbt._
import sbt.Keys._
import wartremover._

object Lint { // avoid namie conflicts
  def settings: Seq[Setting[_]] =
    Seq(
      wartremoverErrors in (Compile, compile) ++= Warts.all,
      // Javascript nonsense
      wartremoverExcluded += baseDirectory.value / "src" / "main" / "scala" / "Main.scala"
    )
}
