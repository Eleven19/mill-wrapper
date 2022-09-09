package io.github.eleven19.mill.wrapper

object Logger:
  private final val verbose: Boolean =
    (for {
      verboseStr <- sys.env.get("MILL_VERBOSE")
      value <- verboseStr.toBooleanOption
    } yield value).getOrElse(false)

  def info(msg: String): Unit =
    if verbose then println(s"[INFO] $msg")

  def warn(msg: String): Unit =
    println(s"[WARNING] $msg")
