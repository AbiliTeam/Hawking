package hawking.gesture

import akka.actor.ActorSystem

import scala.concurrent.Future

object GestureEngine {
  implicit val system = ActorSystem("hawking-system")
  implicit val executionContext = system.dispatcher

  def run(event: Event): Future[Unit] = Future {
    val totMotion = event.motionEvents.reduce{ (acc, cur) =>
      Motion(
        Acceleration(
          acc.acceleration.x + cur.acceleration.x,
          acc.acceleration.y + cur.acceleration.y,
          acc.acceleration.z + cur.acceleration.z
        ), RotationRate(
          acc.rotationRate.alpha + cur.rotationRate.alpha,
          acc.rotationRate.beta + cur.rotationRate.beta,
          acc.rotationRate.gamma + cur.rotationRate.gamma
        ))}
    val motionsLength = event.motionEvents.length
    val avgMotion = Motion(
      Acceleration(
        totMotion.acceleration.x / motionsLength,
        totMotion.acceleration.y / motionsLength,
        totMotion.acceleration.z / motionsLength
      ),
      RotationRate(
        totMotion.rotationRate.alpha / motionsLength,
        totMotion.rotationRate.beta / motionsLength,
        totMotion.rotationRate.gamma / motionsLength,
      ))
  }
}