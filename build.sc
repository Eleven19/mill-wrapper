import $ivy.`com.goyeau::mill-scalafix::0.2.10`
import $ivy.`de.tototec::de.tobiasroeser.mill.vcs.version::0.2.0`
import $ivy.`io.chris-kipp::mill-ci-release::0.1.1`
import $ivy.`de.tototec::de.tobiasroeser.mill.integrationtest::0.6.1`

import mill._
import mill.scalalib._
import mill.scalalib.scalafmt._
import mill.scalalib.publish._
import mill.scalalib.api.ZincWorkerUtil
import mill.scalalib.api.Util.scalaNativeBinaryVersion

import com.goyeau.mill.scalafix.ScalafixModule
import de.tobiasroeser.mill.vcs.version.VcsVersion
import de.tobiasroeser.mill.integrationtest._
import io.kipp.mill.ci.release.CiReleaseModule

val millVersion = "0.10.0"
val scala213 = "2.13.8"
val wrapperScalaVersion = "3.2.0"
val pluginName = "mill-wrapper"

def millBinaryVersion(millVersion: String) = scalaNativeBinaryVersion(
  millVersion
)

object `mill-wrapper` extends ScalaModule with ReleaseModule {
  def scalaVersion = wrapperScalaVersion
  override def pomDescription: String =
    "Mill Wrapper Jar downloads, installs, and launches target mill distribution as part of mill wrapper scripts run."

  object distribution extends JavaModule with ReleaseModule {
    override def pomDescription: String =
      "Mill Wrapper Jar downloads, installs, and launches target mill distribution as part of mill wrapper scripts run."

    object itest extends CommonTestModule {}
  }

  object test extends CommonTestModule {}
}

object plugin
    extends ScalaModule
    with ReleaseModule
    with ScalafixModule
    with ScalafmtModule {

  override def scalaVersion = scala213

  override def artifactName =
    s"${pluginName}_mill${millBinaryVersion(millVersion)}"

  override def sonatypeUri = "https://s01.oss.sonatype.org/service/local"
  override def sonatypeSnapshotUri =
    "https://s01.oss.sonatype.org/content/repositories/snapshots"

  override def compileIvyDeps = super.compileIvyDeps() ++ Agg(
    ivy"com.lihaoyi::mill-scalalib:${millVersion}"
  )

  // override def ivyDeps = super.ivyDeps() ++ Agg(
  //   ivy"de.tototec::de.tobiasroeser.mill.vcs.version_mill0.10::0.2.0"
  // )
  override def scalacOptions = Seq("-Ywarn-unused", "-deprecation")

  override def scalafixScalaBinaryVersion =
    ZincWorkerUtil.scalaBinaryVersion(scala213)

  override def scalafixIvyDeps = Agg(
    ivy"com.github.liancheng::organize-imports:0.6.0"
  )
}

object itest extends MillIntegrationTestModule {

  override def millTestVersion = millVersion

  override def pluginsUnderTest = Seq(plugin)

  def testBase = millSourcePath / "src"

  override def testInvocations: T[Seq[(PathRef, Seq[TestInvocation.Targets])]] =
    T {
      Seq(
        PathRef(testBase / "minimal") -> Seq(
          TestInvocation.Targets(Seq("verify"), noServer = true)
        )
      )
    }
}

trait ReleaseModule extends CiReleaseModule {
  def pomDescription =
    "A Mill plugin to provide a wrapper/launcher similar to maven-wrapper or gradle-wrapper."

  override def pomSettings = PomSettings(
    description = pomDescription,
    organization = "io.eleven19",
    url = "https://github.com/Eleven19/mill-wrapper",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl
      .github(owner = "DamianReeves", repo = "mill-wrapper"),
    developers = Seq(
      Developer(
        "DamianReeves",
        "Damian Reeves",
        "https://github.com/DamianReeves"
      )
    )
  )
}

trait CommonTestModule extends ScalaModule with TestModule {
  def scalaVersion: T[String] = wrapperScalaVersion
  def ivyDeps = super.ivyDeps() ++ Agg(
    ivy"dev.zio::zio-test::2.0.1",
    ivy"dev.zio::zio-test-sbt::2.0.1"
  )
  def testFramework = "zio.test.sbt.ZTestFramework"
}
