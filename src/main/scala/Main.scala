package com.eddsteel.ruhe

import Types._

import cats._
import scala.scalajs.js.timers
import org.scalajs.dom
import org.scalajs.dom.document

import collection.immutable.Seq

object Main {
  private val forest = Seed(System.currentTimeMillis).germinate

  def main(args: Array[String]): Unit = {
    val canvas =
      document.getElementById("ruhe-canvas").asInstanceOf[dom.html.Canvas]

    val fs: (Any => Any) = _ => {
      canvas.width = dom.window.innerWidth.toInt
      canvas.height = dom.window.innerHeight.toInt
    }
    fs(())

    dom.window.addEventListener("resize", fs, false)

    var tick = 0l

    val _ = timers.setInterval(16) {
      drawScene(canvas, forest, tick)
      tick = tick + 1
    }

    ()
  }

  def drawScene(canvas: dom.html.Canvas, forest: Seq[Tree], tick: Long): Unit = {
    // vary t based on tick to simulate walking pace
    val pace: Double = 0.0005 * Math.sin(0.1 * tick.toDouble) + 0.02
    val scene = Monoid.combineAll(forest.map { tree =>
      CanvasOps.draw(tree.at(tick + pace))
    })(DrawOp.monoid)

    CanvasOps.drawFrame(canvas, scene)
  }
}
