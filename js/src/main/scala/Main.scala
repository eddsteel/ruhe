package com.eddsteel.ruhe

import Types._

import cats._
import scala.scalajs.js.timers
import org.scalajs.dom
import org.scalajs.dom.document

import collection.immutable.Seq

object Main {

  val config = Config.default

  def main(args: Array[String]): Unit = {
    val canvas =
      document.getElementById("ruhe-canvas").asInstanceOf[dom.html.Canvas]
    val forest = Seed(System.currentTimeMillis).germinate(config)
    resizeAndAddHook(canvas, dom.window)
    animate(canvas, forest)
    ()
  }

  private def resizeAndAddHook(canvas: dom.html.Canvas, window: dom.raw.Window): Unit = {
    val fs: (Any => Any) = _ => {
      canvas.width = window.innerWidth.toInt
      canvas.height = window.innerHeight.toInt
    }
    fs(())

    window.addEventListener("resize", fs, false)
  }

  private def animate(canvas: dom.html.Canvas, forest: Seq[Tree]) = {
    var tick = 0.0d

    val _ = timers.setInterval(100.0d / 6.0d) {
      drawScene(canvas, forest, tick)
      tick = tick + 1
    }

  }

  // vary t based on tick to simulate walking pace
  def pace(tick: Double): Double =
    config.walkVariance * Math.sin(config.walkPace * tick) + config.walkSpeed

  def flicker(tick: Double): Double =
    0.05 * Math.sin(config.walkPace * 0.1 * tick)

  def drawScene(canvas: dom.html.Canvas, forest: Seq[Tree], tick: Double): Unit = {
    val f = flicker(tick)
    val p = pace(tick)
    val t = tick + p
    val (skyc, skyp) = config.bg1
    val sky = (skyc.copy(lightness = skyc.lightness + f), skyp)

    val scene =
      DrawOp.monoid.combine(
        Canvas.fillAllGradient(config.bg0, sky, config.bg2, config.bg3),
        Monoid.combineAll(forest.map { tree =>
          Canvas.draw(tree.at(t))
        })(DrawOp.monoid)
      )

    Canvas.drawFrame(canvas, scene)
  }
}
