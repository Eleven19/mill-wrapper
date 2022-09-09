package io.github.eleven19.mill.wrapper

import zio.*

import java.net.{URI, URISyntaxException}
import java.nio.file.{Path, Paths}

class MillWrapperMain

object MillWrapperMain extends ZIOAppDefault:
  final val DEFAULT_MILL_USER_HOME = Paths.get(sys.props("user.home")).resolve(".mill")
  final val MILL_USER_HOME_PROPERTY_KEY = "mill.user.home"
  final val MILL_USER_HOME_ENV_KEY: String = "MILL_USER_HOME"
  final val MILLW_VERBOSE: String = "MILLW_VERBOSE"
  final val MILLW_USERNAME: String = "MILLW_USERNAME"
  final val MILLW_PASSWORD: String = "MILLW_PASSWORD"
  final val MILLW_REPOURL: String = "MILLW_REPOURL"

  val addSimpleLogger: ZLayer[Any, Nothing, Unit] =
    Runtime.addLogger((_, _, level, message: () => Any, _, _, _, _) => println(s"[${level.label}] ${message()}"))

  override val bootstrap: ZLayer[Any, Nothing, Any] =
    Runtime.removeDefaultLoggers ++ addSimpleLogger
  def run =
    for {
      wrapperJarPath <- wrapperJar
      rootDirPath = rootDir(wrapperJarPath)
      _ <- ZIO.logInfo(s"Mill Wrapper $wrapperVersion")
    } yield ()

  lazy val wrapperJar: Task[Path] =
    val getLocation: Task[URI] =
      ZIO.attempt(MillWrapperMain.getClass.getProtectionDomain.getCodeSource.getLocation.toURI).refineOrDie {
        case e: URISyntaxException => new RuntimeException(e)
      }

    getLocation.flatMap { location =>
      if (!"file".equals(location.getScheme))
        ZIO.fail(new RuntimeException(s"Cannot determine classpath for wrapper Jar from codebase '$location'."))
      ZIO.succeed(Paths.get(location))
    }
  def wrapperVersion: String = MillWrapperBuildInfo.version

  private[wrapper] def rootDir(wrapperJar: Path) =
    wrapperJar.getParent.getParent.getParent

  private[wrapper] lazy val millUserHome: Task[Path] =
    System
      .property(MILL_USER_HOME_PROPERTY_KEY)
      .flatMap {
        case None => System.env(MILL_USER_HOME_ENV_KEY)
        case v    => ZIO.succeed(v)
      }
      .map(home => home.fold(DEFAULT_MILL_USER_HOME)(Paths.get(_)))

  private[wrapper] lazy val millUserHome: Task[Path] =
    System
      .property(MILL_USER_HOME_PROPERTY_KEY)
      .flatMap {
        case None => System.env(MILL_USER_HOME_ENV_KEY)
        case v    => ZIO.succeed(v)
      }
      .map(home => home.fold(DEFAULT_MILL_USER_HOME)(Paths.get(_)))
