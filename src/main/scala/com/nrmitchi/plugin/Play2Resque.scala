package com.nrmitchi.plugin

import play.Plugin
import play.api.Application
import play.Logger

class Play2Resque(app: Application) extends Plugin {

  private lazy val host      = app.configuration.getString("resque.host").getOrElse("localhost")
  private lazy val port      = app.configuration.getInt("resque.port").getOrElse(6379)
  private lazy val database  = try {
     app.configuration.getInt("resque.database").getOrElse(0)
  } catch {
    case (e) => 0
  }
  private lazy val namespace = app.configuration.getString("resque.namespace").getOrElse("resque")
  private lazy val password  = app.configuration.getString("resque.password")

  // Whether or not to print debug log statements
  private lazy val debug     = app.configuration.getBoolean("resque.logging").getOrElse(false)

  override def onStart() {
    Resque(host = host, port = port, database = database, namespace = namespace, password = password, debug = debug)
  }

  override def onStop() {
    Resque.close()
  }
}
