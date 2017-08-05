package com.eddsteel.ruhe

import util.Random
import collection.immutable.Seq

final case class Seed(seed: Long) {

  private val rand = new Random(seed)

  def germinate: Seq[Tree] = {
    (1.to(10)).map { i =>
      Tree(rand.nextDouble())
    }
  }

}
