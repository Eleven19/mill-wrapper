package io.github.eleven19.mill.wrapper

import java.net.URI
import java.nio.file.Path
import java.net.Authenticator
import java.net.PasswordAuthentication

class DefaultDownloader extends Downloader:
  def download(address: URI, destination: Path): Unit = ???

  private def configureProxyAuthentication(): Unit =
    sys.props.get("http.proxy").foreach { proxy =>
      Authenticator.setDefault(new Authenticator {
        override def getPasswordAuthentication(): PasswordAuthentication =
          new PasswordAuthentication(
            sys.props("http.proxyUser"),
            sys.props("http.proxyPassword").toCharArray
          )
      })
    }
