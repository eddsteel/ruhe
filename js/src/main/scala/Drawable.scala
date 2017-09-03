package com.eddsteel.ruhe

import Types._

trait Drawable[T] {
  def draw(t: T): DrawOp
}

object Drawable {
  def apply[T](f: T => DrawOp): Drawable[T] =
    new Drawable[T] {
      def draw(t: T): DrawOp = f(t)
    }
}
