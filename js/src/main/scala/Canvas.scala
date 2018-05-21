package com.eddsteel.ruhe

import org.scalajs.dom
import org.scalajs.dom.html.Canvas

import Types._

object Canvas {

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

  def fillAllGradient(
    color0: (HSL, UnitD),
    color1: (HSL, UnitD),
    color2: (HSL, UnitD),
    color3: (HSL, UnitD)): DrawOp =
    (canvas, ctx) => {
      val gradient = ctx.createLinearGradient(0, 0, 0, canvas.height.toDouble)
      gradient.addColorStop(color0._2, color0._1.toString)
      gradient.addColorStop(color1._2, color1._1.toString)
      gradient.addColorStop(color2._2, color2._1.toString)
      gradient.addColorStop(color3._2, color3._1.toString)
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

  def drawRotatedVerticalRect(xPos: UnitD, width: UnitD, color: HSL, degrees: UnitD): DrawOp =
    withFillStyle(color)((canvas, ctx) => {
      ctx.rotate(degrees)
      ctx.fillRect(
        Math.max(0, xPos - width) * canvas.width * stretch,
        -100,
        width * canvas.width * stretch,
        canvas.height.toDouble + 200)
      ctx.rotate(-degrees)
    })

  def drawText(text: String, x: Double, y: Double): DrawOp = (canvas, ctx) => {
    ctx.fillText(text, (1.0 - x) * canvas.width, y * canvas.height + x * 500)
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
