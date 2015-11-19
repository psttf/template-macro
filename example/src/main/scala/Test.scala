import annotations.template
import reflect.runtime.universe.TypeTag

case class SW(s: String)
case class IW(c: Int)
case class DW(d: Double)
case class PD(sw: SW, iw: IW, bw: DW)

@template((i: Int, s: String, d: Double) => PD(SW(s), IW(i), DW(d)))
object Test extends App {
  def typeToString[T](o: T)(implicit t: TypeTag[T]) = t.tpe.toString

  lazy val appl   = apply(2, "sss", 2d)
  lazy val unappl = unapply(PD(SW("s"), IW(1100), DW(28d)))

  println(s"apply: ${appl}: ${typeToString(appl)}")
  println(s"unapply: ${unappl}: ${typeToString(unappl)}")
}
