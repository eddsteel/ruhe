package com.eddsteel.ruhe

import cats.Show
import Types._

final case class Tree(
  initialPosition: UnitD,
  distance: UnitD,
  width: UnitD,
  base: HSL,
  config: Config
) {
  val speed: UnitD = 1 - distance
  val perceivedWidth: UnitD =
    width * speed

  // scale color based on distance
  val perceivedColor: HSL = {
    val l = Interval(0.05, 0.15)(speed)
    val s = 0.5 * (1 - (Interval(0.2, 0.8)(distance) * base.saturation))
    base.copy(lightness = l, saturation = s)
  }

  def at(time: Double): TreeInstant = {
    val tpos: Double = (time.toDouble / 1000.0) * speed
    TreeInstant(
      position = UnitD.modulo(initialPosition + tpos),
      width = perceivedWidth,
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
  color: HSL
)

object TreeInstant {
  implicit val drawTreeInstant: Drawable[TreeInstant] =
    Drawable(ti => CanvasOps.drawVerticalRect(1 - ti.position, ti.width, ti.color))
}
