import sbt._
import sbt.Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object ScalaJS {
  def settings: Seq[Setting[_]] =
    Seq(scalaJSUseMainModuleInitializer := true)
}
