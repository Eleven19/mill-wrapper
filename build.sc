import $ivy.`com.goyeau::mill-scalafix::0.2.10`
import $ivy.`de.tototec::de.tobiasroeser.mill.vcs.version::0.2.0`
import $ivy.`io.chris-kipp::mill-ci-release::0.1.1`

import mill._
import mill.scalalib._
import mill.scalalib.scalafmt._
import mill.scalalib.publish._
import mill.scalalib.api.ZincWorkerUtil
import mill.scalalib.api.Util.scalaNativeBinaryVersion

import com.goyeau.mill.scalafix.ScalafixModule
import de.tobiasroeser.mill.vcs.version.VcsVersion
import io.kipp.mill.ci.release.CiReleaseModule

val millVersion = "0.10.0"
val scala213 = "2.13.8"
val pluginName = "mill-wrapper"

def millBinaryVersion(millVersion: String) = scalaNativeBinaryVersion(
  millVersion
)

object plugin
    extends ScalaModule
    with CiReleaseModule
    with ScalafixModule
    with ScalafmtModule {

  override def scalaVersion = scala213

  override def artifactName =
    s"${pluginName}_mill${millBinaryVersion(millVersion)}"

  override def pomSettings = PomSettings(
    description =
      "A Mill plugin to provide a wrapper/launcher similar to maven-wrapper or gradle-wrapper.",
    organization = "com.damianreeves",
    url = "https://github.com/DamianReeves/mill-wrapper",
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
