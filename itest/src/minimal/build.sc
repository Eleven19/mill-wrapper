import mill._, scalalib._
import $exec.plugins
import mill.eval.Evaluator
import $ivy.`org.scalameta::munit:1.0.0-M6`
import munit.Assertions._
import io.eleven19.mill.wrapper.Wrapper

object minimal extends ScalaModule {
  def scalaVersion = "2.13.8"
}

def verify(ev: Evaluator) = T.command {
  val res = Wrapper.wrapper(ev)()
  println(res)
  assertEquals(1, 1)
}
