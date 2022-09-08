package com.damianreeves.mill.wrapper

object Logger:
  val verbose: Boolean =
    (for {
      verboseStr <- sys.env.get("MILL_VERBOSE")
      value <- verboseStr.toBooleanOption
    } yield value).getOrElse(false)
