package com.eddsteel.ruhe

import Types._

final case class Interval(min: Double, max: Double) {
  require(min <= max)

  def apply(d: UnitD): Double = {
    val value = (max - min) * d + min
    assert(min <= value && value <= max)
    value
  }
}
