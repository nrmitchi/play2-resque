# play2-resque

Thin resque(jesque) client wrapper for play2.1+ framework.

## Install

Add dependency to your `project/Build.scala`

```scala
"com.nrmitchi.plugin" % "play2-resque" % "0.0.1"
```

Starting project, it will be like this

```scala
val appDependencies = Seq(
  "com.nrmitchi.plugin" % "play2-resque" % "0.0.1",
  jdbc,
  anorm
)
```
 With Play 2.2, exclude scala-stm
```scala
val appDependencies = Seq(
  "com.nrmitchi.plugin" % "play2-resque" % "0.0.1"
    exclude("org.scala-stm", "scala-stm_2.10.0"),
  jdbc,
  anorm
)
```
## Usage

You can use like this.

```scala
import com.nrmitchi.plugin.Resque

object Application extends Controller {
  def index = Action {
    Resque.withClient { client =>
        client.enqueue("hello", new Job("Worker", Array("params","moreparams")))
    }
  }
}
```

## Configuration

You can change following redis server configuration in `conf/application.conf`.

```text
resque.host=localhost
resque.port=6879
resque.namespace="play2-resque"
resque.database=0
resque.password=
```

## LICENSE
This software is licensed under the Apache 2 license, quoted below.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at


    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
