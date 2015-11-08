## Template

The motivation is to avoid `case class` extention necessity. As the result it is possible to use macro, to generate `apply` and `unapply` function for an `object`. 

## Usage

```scala
import annotations.template

case class SW(s: String)
case class IW(c: Int)
case class BW(d: Double)
case class PD(sw: SW, iw: IW, bw: BW)

@template((i: Int, s: String, d: Double) => PD(SW(s), IW(i), BW(d)))
object Test extends App {
  println(apply(2, "sss", 2d))
  println(unapply(PD(SW("s"), IW(1100), BW(28d))))
}
```

## Idea

* The idea belongs to [Pavel Shapkin](https://github.com/psttf)

## License

TOFILL...