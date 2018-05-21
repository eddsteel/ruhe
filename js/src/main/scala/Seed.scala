package com.eddsteel.ruhe

import Types._
import util.Random
import collection.immutable.Seq

final case class Seed(seed: Long) {
  private val rand = new Random(seed)

  private def unitD = rand.nextDouble()

  private def color(config: Config): HSL =
    HSL(config.hue(unitD).toInt, config.saturation(unitD), 0.2)

  val far: Interval = Interval(0.7, 0.9)
  val medium: Interval = Interval(0.5, 0.8)

  def germinate(config: Config): Seq[Tree] =
    1.to(config.forestSize / 5)
      .flatMap { i =>
        Seq(
          Tree(
            unitD,
            far(config.distance(unitD)),
            config.width(unitD),
            unitD,
            color(config),
            config),
          Tree(
            unitD,
            far(config.distance(unitD)),
            config.width(unitD),
            unitD,
            color(config),
            config),
          Tree(
            unitD,
            medium(config.distance(unitD)),
            config.width(unitD),
            unitD,
            color(config),
            config),
          Tree(
            unitD,
            medium(config.distance(unitD)),
            config.width(unitD),
            unitD,
            color(config),
            config),
          Tree(unitD, config.distance(unitD), config.width(unitD), unitD, color(config), config)
        )
      }
      .sortBy(_.speed)
}
