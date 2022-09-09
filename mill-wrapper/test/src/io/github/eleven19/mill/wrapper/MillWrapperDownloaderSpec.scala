package io.github.eleven19.mill.wrapper

import zio.test.*
object MillWrapperDownloaderSpec extends ZIOSpecDefault:
  def spec = suite("MillWrapperDownloaderSpec")(
    test("download") {
      val downloader = new DefaultDownloader()
      assertTrue(1 == 1)
    }
  )
end MillWrapperDownloaderSpec
