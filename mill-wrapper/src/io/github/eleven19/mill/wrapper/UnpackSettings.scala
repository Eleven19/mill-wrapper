package io.github.eleven19.mill.wrapper

import java.net.URI
import java.nio.file.Path

object UnpackSettings:
  final val ALWAYS_UNPACK_ENV: String = "MILL_WRAPPER_ALWAYS_UNPACK"

final case class UnpackSettings(zipPath: Path, zipBase: String, alwaysUnpack: Boolean)
