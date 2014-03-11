package com.nrmitchi.plugin

import com.redis._

import net.greghaines.jesque._
import net.greghaines.jesque.client.ClientImpl

import play.Logger
import redis.clients.jedis.Client

object Resque {
  private var _host: String        = _
  private var _port: Int           = _
  private var _database: Int       = _
  private var _namespace: Option[String]   = _
  private var _password: Option[String]    = _
  private var _debug: Boolean      = _

  lazy val config = {
    val conf = (new ConfigBuilder())
      .withHost(_host)
      .withPort(_port)
      .withDatabase(_database)

    _namespace match {
      case Some(x) => conf.withNamespace(x)
      case None => conf
    }
    _password match {
      case Some(x) => conf.withPassword(x)
      case None => conf
    }
  }

//  val config = new ConfigBuilder().build()

  private lazy val jesque = new ClientImpl(config.build())

  def apply(host: String, port: Int, database: Int, namespace: Option[String], password: Option[String], debug: Boolean) = {
    _host = host
    _port = port
    _namespace = namespace
    _database = database
    _password = password

    jesque
  }

  private def log(x: Any) {
    if (_debug) Logger.debug("Resque:: "+x.toString)
  }
  def close() {
    log("Closing Resque connections...")
    jesque.end
    log("Resque connections closed...")
  }

  def withClient[T](body: ClientImpl => T) = {
    /*
     * I feel like I should be passing in a client, insead of jesque itself
     *
     * eg.
     *
     * jesque.withClient {
     *   body
     * }
     */
    body(jesque)

  }
}
