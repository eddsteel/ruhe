package com.eddsteel.ruhe

import cats._
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.{CanvasRenderingContext2D => Context}

object Types {

  /** Double between 0 and 1 */
  type UnitD = Double

  object UnitD {
    def modulo(d: Double): UnitD =
      d - d.toInt
  }

  type DrawOp = (Canvas, Context) => Unit

  object DrawOp {
    implicit val monoid: Monoid[DrawOp] = new Monoid[DrawOp] {
      def empty: DrawOp = (_, _) => ()
      def combine(a: DrawOp, b: DrawOp): DrawOp = (ca, co) => {
        a(ca, co)
        b(ca, co)
      }
    }
  }

  final case class HSL(hue: Int, saturation: UnitD, lightness: UnitD) {
    override def toString: String = {
      def pc(d: UnitD): String = s"${(d * 100).toInt}%"
      s"hsl($hue, ${pc(saturation)}, ${pc(lightness)})"
    }
  }

  object HSL {
    val Black: HSL = HSL(0, 0.0, 0.0)
  }
}
