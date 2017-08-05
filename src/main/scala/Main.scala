package com.eddsteel.ruhe

import cats._, cats.implicits._
import org.scalajs.dom
import org.scalajs.dom.document

import collection.immutable.Seq

object Main {

  def appendPar(targetNode: dom.Node, text: String): dom.Node = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    val _ = parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }

  def ul[A: Show](targetNode: dom.Node, as: Seq[A]): dom.Node = {
    val ulNode = document.createElement("ul")
    as.foreach { a =>
      val li = document.createElement("li")
      val _ = li.appendChild(document.createTextNode(a.show))
      ulNode.appendChild(li)
    }

    targetNode.appendChild(ulNode)
  }

  private val forest = Seed(System.currentTimeMillis).germinate

  @SuppressWarnings(
    Array("org.wartremover.warts.AsInstanceOf",
          "org.wartremover.warts.Println"))
  def main(args: Array[String]): Unit = {
    val _ = appendPar(document.body, "😸")
    val canvas =
      document.getElementById("ruhe-canvas").asInstanceOf[dom.html.Canvas]

    forest.foreach { tree =>
      CanvasOps.drawVerticalLine(tree.initialPosition)(canvas)
    }

  }
}
