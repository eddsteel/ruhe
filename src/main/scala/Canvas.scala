package com.eddsteel.ruhe

import org.scalajs.dom
import org.scalajs.dom.html.{Canvas => HCanvas}

import Types._

object CanvasOps {

  type DrawOp = HCanvas => Unit

  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  def draw(f: (dom.CanvasRenderingContext2D, HCanvas) => Unit): DrawOp = {
    hCanvas =>
      val ctx =
        hCanvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
      f(ctx, hCanvas)
      ctx.stroke()
  }

  def drawVerticalLine(xPos: UnitD): DrawOp = draw { (ctx, canvas) =>
    ctx.beginPath()
    ctx.moveTo(xPos * canvas.width, 0)
    ctx.lineTo(xPos * canvas.width, canvas.height.toDouble)
  }
}
