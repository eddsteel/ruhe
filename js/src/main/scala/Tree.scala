package com.eddsteel.ruhe

import cats.Show
import Types._

final case class Tree(
  initialPosition: UnitD,
  distance: UnitD,
  width: UnitD,
  tip: UnitD,
  base: HSL,
  config: Config
) {
  val speed: UnitD = 1 - distance
  val scaledSpeed: Double = speed / 1000.0
  val perceivedWidth: UnitD =
    width * speed

  // scale color based on distance
  val perceivedColor: HSL = {
    val l = config.lightness(speed)
    val s = base.saturation * speed
    base.copy(lightness = l, saturation = s)
  }

  def at(time: Double): TreeInstant = {
    val tpos: Double = time.toDouble * scaledSpeed
    TreeInstant(
      position = UnitD.modulo(initialPosition + tpos),
      width = perceivedWidth,
      tip = config.tip(tip),
      color = perceivedColor
    )
  }
}

object Tree {
  implicit val treeShow: Show[Tree] = Show.fromToString[Tree]
}

final case class TreeInstant(
  position: UnitD,
  width: UnitD,
  tip: UnitD,
  color: HSL
)

object TreeInstant {
  implicit val drawTreeInstant: Drawable[TreeInstant] =
    Drawable { ti =>
      Canvas.drawRotatedVerticalRect(1 - ti.position, ti.width, ti.color, ti.tip)
    }
}
