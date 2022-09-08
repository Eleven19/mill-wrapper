package com.damianreeves.mill.wrapper
import mill._
import mill.define.ExternalModule
import mill.eval.Evaluator
import mill.main.EvaluatorScopt

object Wrapper extends ExternalModule {
  implicit def millScoptEvaluatorReads[T]: EvaluatorScopt[T] =
    new mill.main.EvaluatorScopt[T]()

  lazy val millDiscover = mill.define.Discover[this.type]

  def wrapper(ev: Evaluator) = T.command {
    val log = T.log
    log.info("Running wrapper")
    T.ctx.workspace
  }
}
