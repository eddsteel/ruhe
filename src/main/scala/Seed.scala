package com.eddsteel.ruhe

import util.Random
import collection.immutable.Seq

final case class Seed(seed: Long) {

  private val rand = new Random(seed)

  private def initialPos: Double = rand.nextDouble()

  private def speed: Double = rand.nextDouble() * 0.6 + 0.4

  def germinate: Seq[Tree] = {
    (1.to(20)).map { i =>
      Tree(initialPos, speed)
    }
  }
}
