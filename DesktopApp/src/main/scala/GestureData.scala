package hawking.gesture

final case class Event(timeInterval: Int,
                       startOrientation: Orientation,
                       endOrientation: Orientation,
                       motionEvents: List[Motion])
final case class Orientation(alpha: Int, beta: Int, gamma: Int)
final case class Motion(acceleration: Acceleration, rotationRate: RotationRate)
final case class Acceleration(x: Int, y: Int, z: Int)
final case class RotationRate(alpha: Int, beta: Int, gamma: Int)