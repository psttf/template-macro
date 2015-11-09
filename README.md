## Template macro

The motivation is to avoid `case class` inheritance necessity. As the result it is possible to use macro, to generate `apply` and `unapply` function for an `object`. 

## Usage example

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

## Install

```scala
scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.dc" %% "template-macros" % "0.0.1"
)

resolvers ++= Seq(
  "DaunnC bintray repo" at "http://dl.bintray.com/daunnc/maven/"
)
```

## License

* The idea belongs to [Pavel Shapkin](https://github.com/psttf)
* Licensed under the Apache License, Version 2.0: [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)
