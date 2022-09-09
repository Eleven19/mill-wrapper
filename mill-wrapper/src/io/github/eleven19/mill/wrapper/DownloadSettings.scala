package io.github.eleven19.mill.wrapper

import java.net.URI
import java.nio.file.Path

object DownloadSettings:
  final val ALWAYS_DOWNLOAD_ENV: String = "MILL_WRAPPER_ALWAYS_DOWNLOAD"

final case class DownloadSettings(
    distribution: URI,
    distributionBase: String,
    distributionPath: Path,
    alwaysDownload: Path
)
