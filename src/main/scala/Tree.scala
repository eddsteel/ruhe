package com.eddsteel.ruhe

import cats.Show
import Types._

final case class Tree(
    initialPosition: UnitD,
    distance: UnitD
) {
  def at(time: Double): TreeInstant = {
    val tpos: Double = (time.toDouble / 1000.0) * distance
    TreeInstant(position = UnitD.modulo(initialPosition + tpos))
  }
}

object Tree {
  implicit val treeShow: Show[Tree] = Show.fromToString[Tree]
}

final case class TreeInstant(
    position: UnitD
)

object TreeInstant {
  implicit val drawTreeInstant: Drawable[TreeInstant] =
    Drawable(ti => CanvasOps.drawVerticalLine(1 - ti.position))
}
