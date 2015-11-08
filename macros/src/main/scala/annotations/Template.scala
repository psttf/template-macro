package annotations

import utils.MacroApplication
import scala.reflect.macros.whitebox.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation
import scala.collection.mutable.ListBuffer

final class template[F, T](t: F => T) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro templateMacro.apply
}

class templateMacro(val c: Context) extends MacroApplication {

  import c.universe._
  import c.Expr

  // TODO: absract over function global vals usage
  def apply(annottees: Expr[Any]*): Expr[Any] = {
    val tree: Tree = MacroApp(c.macroApplication).termArgs.head.head
    val expr = c.Expr[Any](c.typecheck(tree))
    val (fargs, fbody) = anonymousFunction(tree)
    val fargsMap = fargs.map { case v => v.name.toString -> v.name }.toMap

    // TODO: avoid mutable collection usage
    val mutableBuffer = ListBuffer[Tree]()

    def recursiveMatch(subtree: List[Tree]): List[Tree] = {
      subtree.map {
        case Apply(fun, args) => Apply(fun, recursiveMatch(args))
        case args => {
          fargsMap.get(args.toString()).fold(args){ fa =>
            mutableBuffer += q"${fa}"
            pq"${fa} @ ${Ident(termNames.WILDCARD)}" }
        }
      }
    }

    val result = recursiveMatch(List(fbody)).head

    c.Expr[Any] {
      annottees.map(_.tree).toList match {
        case q"object $name extends ..$parents { ..$body }" :: Nil =>
          q"""
          object $name extends ..$parents {
            def apply = ${tree}
            def unapply(a: ${expr.actualType.typeArgs.last}) = a match {
              case ${result} => Some((..${mutableBuffer}))
              case _ => None
            }
            ..$body
          }
        """
      }
    }
  }

  def anonymousFunction(t: Tree): (List[c.universe.ValDef], c.universe.Tree) = t match {
    case Function(params, body) => (params, body)
  }
}
