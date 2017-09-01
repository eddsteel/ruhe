package com.eddsteel.ruhe

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
  saturation: Interval
)

object Config {
  val default: Config =
    Config(
      forestSize = 100,
      walkSpeed = 10.0,
      walkVariance = 1.5,
      walkPace = 0.08,
      width = Interval(0.02, 0.04),
      distance = Interval(0.1, 1.0),
      hue = Interval(30.0, 35.0),
      saturation = Interval(0.5, 0.7)
    )
}
