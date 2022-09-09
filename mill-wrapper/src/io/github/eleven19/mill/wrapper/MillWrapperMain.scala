package io.github.eleven19.mill.wrapper

import zio._
import java.nio.file.{Paths, Path}

class MillWrapperMain

object MillWrapperMain extends ZIOAppDefault:
  final val DEFAULT_MILL_USER_HOME = Paths.get(sys.props("user.home")).resolve(".mill")
  final val MILL_USER_HOME_PROPERTY_KEY = "mill.user.home"
  final val MILL_USER_HOME_ENV_KEY: String = "MILL_USER_HOME"
  final val MILLW_VERBOSE: String = "MILLW_VERBOSE"
  final val MILLW_USERNAME: String = "MILLW_USERNAME"
  final val MILLW_PASSWORD: String = "MILLW_PASSWORD"
  final val MILLW_REPOURL: String = "MILLW_REPOURL"
  def run = millUserHome

  private[wrapper] def millUserHome: Task[Path] =
    System
      .property(MILL_USER_HOME_PROPERTY_KEY)
      .flatMap {
        case None => System.env(MILL_USER_HOME_ENV_KEY)
        case v    => ZIO.succeed(v)
      }
      .map(home => home.fold(DEFAULT_MILL_USER_HOME)(Paths.get(_)))
