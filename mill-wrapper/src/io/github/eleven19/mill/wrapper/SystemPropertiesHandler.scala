package io.github.eleven19.mill.wrapper

import java.nio.file.{Files, Path}
import java.util.Properties
import java.io.IOException
import scala.util.matching.Regex
import scala.jdk.CollectionConverters.*

object SystemPropertiesHandler {

  final val SYSPROP_PATTERN = "systemProp\\.(.*)".r

  def getSystemProperties(propertiesFile: Path): Map[String, String] =
    if (!Files.isRegularFile(propertiesFile)) Map.empty

    val properties = new Properties()
    try
      properties.load(Files.newInputStream(propertiesFile))
    catch
      case io: IOException => throw new RuntimeException("Error when loading properties file=" + propertiesFile, io);

    properties.entrySet.asScala.foldLeft(Map.empty[String, String]) { (map, entry) =>
      SYSPROP_PATTERN.findFirstIn(entry.getKey.toString) match
      case Some(value)
      if (!value.isEmpty)
      => map + (value -> entry.getKey.toString)
      case _ => map
    }
}
