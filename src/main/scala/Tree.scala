package com.eddsteel.ruhe

import cats.Show
import Types._

final case class Tree(
  initialPosition: UnitD
)

object Tree {
  implicit val treeShow: Show[Tree] = Show.fromToString[Tree]
}
