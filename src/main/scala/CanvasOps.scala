package com.eddsteel.ruhe

import org.scalajs.dom
import org.scalajs.dom.html.Canvas

import Types._

object CanvasOps {

  def drawVerticalLine(xPos: UnitD): DrawOp = (canvas, ctx) => {
    ctx.beginPath()
    ctx.moveTo(xPos * canvas.width, 0)
    ctx.lineTo(xPos * canvas.width, canvas.height.toDouble)
    ctx.stroke()
  }

  def draw[T: Drawable](drawable: T): DrawOp =
    implicitly[Drawable[T]].draw(drawable)

  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  def drawFrame(canvas: Canvas, op: DrawOp): Unit = {
    val ctx =
      canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    ctx.clearRect(0, 0, canvas.width.toDouble, canvas.height.toDouble)
    op(canvas, ctx)
  }
}
