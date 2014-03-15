package com.nrmitchi.plugin


import net.greghaines.jesque._
import net.greghaines.jesque.client.ClientImpl
import play.Logger

object Resque {
  private var _host: String        = _
  private var _port: Int           = _
  private var _database: Int       = _
  private var _namespace: String   = _
  private var _password: Option[String]    = _
  private var _debug: Boolean      = _

  private lazy val config = new ConfigBuilder()
                                  .withHost(_host)
                                  .withPort(_port)
                                  .withDatabase(_database)
                                  .withPassword(_password match {
                                    case Some(x) => if (x.length <= 0) null else x
                                    case None => null
                                  })
                                  .withNamespace( if (_namespace.length <= 0) "play2resque" else _namespace )

//  val config = new ConfigBuilder().build()

  private lazy val jesque = new ClientImpl(config.build())

  def apply(host: String, port: Int, database: Int, namespace: String, password: Option[String], debug: Boolean) = {
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

  // Shortcut for publishing an event
  def push(queue: String, className: String, payload: java.lang.Object) = {
    jesque.enqueue(queue, new Job(className, payload))
  }
}
