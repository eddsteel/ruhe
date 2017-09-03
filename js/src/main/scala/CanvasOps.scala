package com.eddsteel.ruhe

import org.scalajs.dom
import org.scalajs.dom.html.Canvas

import Types._

object CanvasOps {

  // lazy hack: rather than drawing partial trees, we make the canvas
  // wider than the screen, so whole trees don't visibly blip into full existence
  private val stretch: Double = 1.1

  def drawVerticalLine(xPos: UnitD): DrawOp = (canvas, ctx) => {
    ctx.beginPath()
    ctx.moveTo(xPos * canvas.width, 0)
    ctx.lineTo(xPos * canvas.width, canvas.height.toDouble)
    ctx.stroke()
  }

  def fillAll(color: HSL): DrawOp =
    withFillStyle(color)((canvas, ctx) => {
      ctx.fillRect(0, 0, canvas.width.toDouble, canvas.height.toDouble)
    })

  def fillAllGradient(color1: HSL, color2: HSL): DrawOp =
    (canvas, ctx) => {
      val gradient = ctx.createLinearGradient(0, 0, 0, canvas.height.toDouble)
      gradient.addColorStop(0, color1.toString)
      gradient.addColorStop(1, color2.toString)
      val previous = ctx.fillStyle
      ctx.fillStyle = gradient
      ctx.fillRect(0, 0, canvas.width.toDouble, canvas.height.toDouble)
      ctx.fillStyle = previous
    }

  def drawVerticalRect(xPos: UnitD, width: UnitD, color: HSL): DrawOp =
    withFillStyle(color)((canvas, ctx) => {
      ctx.fillRect(
        Math.max(0, xPos - width) * canvas.width * stretch,
        0,
        width * canvas.width * stretch,
        canvas.height.toDouble)
    })

  def drawText(text: String): DrawOp = (canvas, ctx) => {
    ctx.fillText(text, canvas.width * 0.8, canvas.height * 0.9)
  }

  def draw[T: Drawable](drawable: T): DrawOp =
    implicitly[Drawable[T]].draw(drawable)

  def withFillStyle(hsl: HSL)(f: DrawOp): DrawOp = (canvas, ctx) => {
    val previous = ctx.fillStyle
    ctx.fillStyle = hsl.toString
    f(canvas, ctx)
    ctx.fillStyle = previous
  }

  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  def drawFrame(canvas: Canvas, op: DrawOp): Unit = {
    val ctx =
      canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    ctx.clearRect(0, 0, canvas.width.toDouble, canvas.height.toDouble)
    op(canvas, ctx)
  }
}
