package io.github.eleven19.mill.wrapper

import java.nio.file.Paths

class MillWrapperMain

object MillWrapperMain:
  final val DEFAULT_MILL_USER_HOME = Paths.get(sys.props("user.home")).resolve(".mill")
  final val MILL_USER_HOME_PROPERTY_KEY = "mill.user.home"
  final val MILL_USER_HOME_ENV_KEY: String = "MILL_USER_HOME"
  final val MILLW_VERBOSE: String = "MILLW_VERBOSE"
  final val MILLW_USERNAME: String = "MILLW_USERNAME"
  final val MILLW_PASSWORD: String = "MILLW_PASSWORD"
  final val MILLW_REPOURL: String = "MILLW_REPOURL"
  def main(args: Array[String]): Unit = ()
