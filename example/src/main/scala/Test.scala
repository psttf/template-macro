import annotations.template
import reflect.runtime.universe.TypeTag

case class SW(s: String)
case class IW(c: Int)
case class BW(d: Double)
case class PD(sw: SW, iw: IW, bw: BW)

@template((i: Int, s: String, d: Double) => PD(SW(s), IW(i), BW(d)))
object Test extends App {
  def typeToString[T](o: T)(implicit t: TypeTag[T]) = t.tpe.toString

  val appl   = apply(2, "sss", 2d)
  val unappl = unapply(PD(SW("s"), IW(1100), BW(28d)))

  println(s"apply: ${appl}: ${typeToString(appl)}")
  println(s"unapply: ${unappl}: ${typeToString(unappl)}")
}
