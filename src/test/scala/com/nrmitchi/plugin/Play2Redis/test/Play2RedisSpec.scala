package com.nrmitchi.plugin.test

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import com.nrmitchi.plugin.Resque
import net.greghaines.jesque.Job
import java.util.Date
import play.api.libs.json.{JsObject, JsString, Json}
import collection.JavaConverters._

class Play2RedisSpec extends Specification {

  def testConfiguration = {
    inMemoryDatabase()
  }

  "Play2Resque" should {

    "be able to queue a job with array params" in {

      running(FakeApplication(additionalConfiguration = testConfiguration)) {

        Resque.withClient { client =>
          client.enqueue("hello", new Job("Worker", "params","moreparams", new Date()))
        }

        Resque.push("hello","Worker", Array("params","moreparams", new Date().toString))

        Resque.push("hello","Worker", "params","moreparams", new Date().toString)

        Resque.push("hello", "Worker", 12321312.toString, Map("test" -> "value" ).asJava)

      }
    }

  }

}
