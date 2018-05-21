package com.eddsteel.ruhe

import Types._

final case class Config(
  forestSize: Int,
  // sin shift
  walkSpeed: Double,
  // sin amplitude
  walkVariance: Double,
  // sin freq
  walkPace: Double,
  // range of tree width
  width: Interval,
  // range of tree distance
  distance: Interval,
  // range of hue (randomly selected) degrees
  hue: Interval,
  // range of tree color saturation
  saturation: Interval,
  // range of tree color lightness
  lightness: Interval,
  // background color0
  bg0: (HSL, UnitD),
  // background color1
  bg1: (HSL, UnitD),
  // background color2
  bg2: (HSL, UnitD),
  // background color3
  bg3: (HSL, UnitD),
  // degree of tipping
  tip: Interval
)

object Config {
  val default: Config =
    Config(
      forestSize = 100,
      walkSpeed = 12.0,
      walkVariance = 1.5,
      walkPace = 0.08,
      width = Interval(0.02, 0.04),
      distance = Interval(0.1, 1.0),
      hue = Interval(30.0, 40.0),
      saturation = Interval(0.45, 0.5),
      lightness = Interval(0.4, 0.45),
      bg0 = (HSL(194, 0, 1), 0.0d),
      bg1 = (HSL(194, .6, .8), 0.55d),
      bg2 = (HSL(120, .1, .5), 0.7d),
      bg3 = (HSL(120, .1, .52), 1.0d),
      tip = Interval(-0.02, 0.02)
    )
}
