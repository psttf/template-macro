## Template macro
[![Build Status](https://secure.travis-ci.org/pomadchin/template-macro.png)](https://travis-ci.org/pomadchin/template-macro/)

The motivation is to avoid `case class` inheritance necessity. As the result it is possible to use macro, to generate `apply` and `unapply` function for an `object`. 

## Usage example

```scala
import annotations.template

case class SW(s: String)
case class IW(c: Int)
case class DW(d: Double)
case class PD(sw: SW, iw: IW, bw: DW)

@template((i: Int, s: String, d: Double) => PD(SW(s), IW(i), DW(d)))
object Test {
  println(apply(2, "sss", 2d))
  println(unapply(PD(SW("s"), IW(1100), DW(28d))))
}
```

## Install

```scala
scalaVersion := "2.11.7"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "DaunnC bintray repo" at "http://dl.bintray.com/daunnc/maven/"
)

addCompilerPlugin(
 "org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full
)

libraryDependencies += "com.dc" %% "template-macros" % "0.0.1"
```

## License

* The idea belongs to [Pavel Shapkin](https://github.com/psttf)
* Licensed under the Apache License, Version 2.0: [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)
