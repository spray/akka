/**
 * Copyright (C) 2015 Typesafe Inc. <http://www.typesafe.com>
 */
package akka.stream.impl

import akka.actor.Actor

/**
 * INTERNAL API
 */
private[akka] abstract class ExposedPublisherReceive(activeReceive: Actor.Receive, unhandled: Any ⇒ Unit) extends Actor.Receive {
  private var stash = List.empty[Any]

  def isDefinedAt(o: Any): Boolean = true

  def apply(o: Any): Unit = o match {
    case ep: ExposedPublisher ⇒
      receiveExposedPublisher(ep)
      if (stash.nonEmpty) {
        // we don't use sender() so this is allright
        stash.reverse.foreach { msg ⇒
          activeReceive.applyOrElse(msg, unhandled)
        }
      }
    case other ⇒
      stash ::= other
  }

  def receiveExposedPublisher(ep: ExposedPublisher): Unit
}
