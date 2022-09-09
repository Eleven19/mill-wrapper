package io.github.eleven19.mill.wrapper

import zio._
import java.nio.file.{Path, Paths}

trait Installer:
  def createDist(wrapperConfiguration: WrapperConfiguration): ZIO[Any, Exception, Path]
  def unzip(zip: Path, dest: Path): ZIO[Any, Exception, Unit]

object Installer:
  final val DEFAULT_DISTRIBUTION_PATH = Paths.get("wrapper", "dists")
