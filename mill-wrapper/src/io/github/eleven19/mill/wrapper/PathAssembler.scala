package io.github.eleven19.mill.wrapper

import io.github.eleven19.mill.wrapper.PathAssembler.LocalDistribution

import java.nio.file.Path

final case class PathAssembler(millUserHome: Path):
  def getDistribution(configuration: WrapperConfiguration): LocalDistribution = ???

object PathAssembler:
  final case class LocalDistribution(distDir: Path, distZip: Path)
