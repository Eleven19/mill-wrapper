package io.eleven19.mill.wrapper

import java.net.URI
import java.nio.file.Path

trait Downloader:
  def download(address: URI, destination: Path): Unit

object Downloader
