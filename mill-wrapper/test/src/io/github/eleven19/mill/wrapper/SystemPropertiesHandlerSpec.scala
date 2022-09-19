package io.github.eleven19.mill.wrapper

import java.nio.file.{Paths, Path}
import zio.test.*
import io.github.eleven19.mill.wrapper.SystemPropertiesHandler

object SystemPropertiesHandlerSpec extends ZIOSpecDefault {
  def spec = suite("SystemPropertiesHandlerSpec")(
    test("No property file"){
      SystemPropertiesHandler.getSystemProperties(Paths.get(""))
      assertTrue(1 ==1 )
    }
  )
}
