package annotations

import org.specs2.mutable.Specification

case class Twice(first: Int, second: Int)

class TemplateSpec extends Specification {

  "Template" should {
    "reuse pattern variables" in {
      @template((i: Int) => Twice(i, i)) object SameTwice
      SameTwice apply 1 mustEqual Twice(1, 1)
      SameTwice unapply Twice(1, 1) mustEqual Some(1)
      SameTwice unapply Twice(1, 2) mustEqual None
    }
  }

}
