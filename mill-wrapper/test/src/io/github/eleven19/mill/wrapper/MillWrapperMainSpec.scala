package io.github.eleven19.mill.wrapper

import java.nio.file.Paths
import zio._
import zio.test._

object MillWrapperMainSpec extends ZIOSpecDefault:
  def spec = suite("MillWrapperMainSpec")(millUserHomeSuite)

  def millUserHomeSuite = suiteAll("Getting millUserHome"){
    val millUserHomeProperyKey = MillWrapperMain.MILL_USER_HOME_PROPERTY_KEY
    val millUserHomePropertyValue = Paths.get("testUser","tools","mill").toString
    test(s"When $millUserHomeProperyKey is set to a valid path"){
      for {
        _ <- TestSystem.putProperty(millUserHomeProperyKey, millUserHomePropertyValue)
        millUserHomeProp <- System.property(millUserHomeProperyKey)
        _ <- Console.printLine(s"$millUserHomeProperyKey=$millUserHomeProp")
        result = MillWrapperMain.millUserHome
      } yield assertTrue(result == Paths.get(millUserHomePropertyValue))
    }
  }
