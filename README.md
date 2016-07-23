<h1> PCF - Spring Boot Portable Cloud Ready HTTP Session </h1>

Storing HTTPSession state for failed instances in cloud native applications requires session data to be stored 
in some data repository which application instances read/write from. In this example below we use Spring Session
to automatically store session state in a Redis instance. 

Spring Session provides a very nice solution for all of these problems. It’s a wrapper around the standard 
Servlet HTTP Session abstraction. It’s easy to plug in to any application, whether they’re Spring-based or not. 
It acts as a sort of proxy in front of the HTTP session that forwards requests to a strategy implementation

<h2>Run locally</h2> 

- Clone code as shown below.

```
$ git clone https://github.com/papicella/SpringBootHTTPSession.git
```

- Change to cloned directory

```
cd SpringBootHTTPSession
```

- Package as shown below

```
$ mvn package
```

- Start a Redis server using localhost and redis default port 6379

```
pasapicella@pas-macbook:~/bin$ redis-server
58892:C 23 Jul 22:11:23.528 # Warning: no config file specified, using the default config. In order to specify a config file use redis-server /path/to/redis.conf
58892:M 23 Jul 22:11:23.530 * Increased maximum number of open files to 10032 (it was originally set to 4864).
                _._
           _.-``__ ''-._
      _.-``    `.  `_.  ''-._           Redis 3.0.7 (00000000/0) 64 bit
  .-`` .-```.  ```\/    _.,_ ''-._
 (    '      ,       .-`  | `,    )     Running in standalone mode
 |`-._`-...-` __...-.``-._|'` _.-'|     Port: 6379
 |    `-._   `._    /     _.-'    |     PID: 58892
  `-._    `-._  `-./  _.-'    _.-'
 |`-._`-._    `-.__.-'    _.-'_.-'|
 |    `-._`-._        _.-'_.-'    |           http://redis.io
  `-._    `-._`-.__.-'_.-'    _.-'
 |`-._`-._    `-.__.-'    _.-'_.-'|
 |    `-._`-._        _.-'_.-'    |
  `-._    `-._`-.__.-'_.-'    _.-'
      `-._    `-.__.-'    _.-'
          `-._        _.-'
              `-.__.-'

58892:M 23 Jul 22:11:23.531 # Server started, Redis version 3.0.7
58892:M 23 Jul 22:11:23.531 * The server is now ready to accept connections on port 6379

```

- Run as shown below

```
$ mvn spring-boot:run


  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v1.2.5.RELEASE)

2016-07-23 22:20:45.939  INFO 59378 --- [lication.main()] .SpringBootHttpSessionProjectApplication : Starting SpringBootHttpSessionProjectApplication on pas-macbook with PID 59378 (/Users/pasapicella/pivotal/DemoProjects/spring-starter/jazzhub/SpringBootHTTPSession/target/classes started by pasapicella in /Users/pasapicella/pivotal/DemoProjects/spring-starter/jazzhub/SpringBootHTTPSession)

.... 

2016-07-23 22:20:48.227  INFO 59378 --- [lication.main()] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2016-07-23 22:20:48.232  INFO 59378 --- [lication.main()] o.s.c.support.DefaultLifecycleProcessor  : Starting beans in phase 2147483647
2016-07-23 22:20:48.281  INFO 59378 --- [lication.main()] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2016-07-23 22:20:48.283  INFO 59378 --- [lication.main()] .SpringBootHttpSessionProjectApplication : Started SpringBootHttpSessionProjectApplication in 2.551 seconds (JVM running for 4.344)

```

- Access as follows

```
http://localhost:8080/
```

<h2> Deploy to PCF </h2>

- Edit the manifest.yml below to enter a unique name and host and ensure you use the correct service name

```
applications:
 - name: apples-sbsessions
   memory: 512M
   instances: 2
   path: ./target/SpringBootHTTPSession-0.0.1-SNAPSHOT.jar
   host: apples-sbsessions-${random-word}
   services:
    - redis-session
```

- Create a service on PCF for storing the HTTPSession data

```
$ cf create-service rediscloud 30mb redis-session
```

- Push as shown below

```
cf push
Using manifest file /Users/pasapicella/pivotal/DemoProjects/spring-starter/jazzhub/SpringBootHTTPSession/manifest.yml

Creating app apples-sbsessions in org apples-pivotal-org / space development as papicella@pivotal.io...
OK

Creating route apples-sbsessions-unideal-pollinization.cfapps.io...
OK

Binding apples-sbsessions-unideal-pollinization.cfapps.io to apples-sbsessions...
OK

Uploading apples-sbsessions...
Uploading app files from: /var/folders/c3/27vscm613fjb6g8f5jmc2x_w0000gp/T/unzipped-app707487435
Uploading 1M, 131 files
Done uploading
OK
Binding service redis-session to app apples-sbsessions in org apples-pivotal-org / space development as papicella@pivotal.io...
OK

Starting app apples-sbsessions in org apples-pivotal-org / space development as papicella@pivotal.io...
Downloading nodejs_buildpack...
Downloading java_buildpack...
Downloading staticfile_buildpack...
Downloading ruby_buildpack...
Downloaded nodejs_buildpack
Downloading python_buildpack...
Downloaded ruby_buildpack
Downloading php_buildpack...
Downloading liberty_buildpack...
Downloading binary_buildpack...
Downloaded binary_buildpack
Downloading go_buildpack...
Downloaded staticfile_buildpack
Downloaded java_buildpack
Downloaded php_buildpack
Downloaded python_buildpack
Downloaded go_buildpack
Downloaded liberty_buildpack
Creating container
Downloading app package...
Successfully created container
Downloaded app package (18.2M)
Staging...
-----> Java Buildpack Version: v3.8.1 (offline) | https://github.com/cloudfoundry/java-buildpack.git#29c79f2
-----> Downloading Open Jdk JRE 1.8.0_91-unlimited-crypto from https://java-buildpack.cloudfoundry.org/openjdk/trusty/x86_64/openjdk-1.8.0_91-unlimited-crypto.tar.gz (found in cache)
       Expanding Open Jdk JRE to .java-buildpack/open_jdk_jre (1.0s)
-----> Downloading Open JDK Like Memory Calculator 2.0.2_RELEASE from https://java-buildpack.cloudfoundry.org/memory-calculator/trusty/x86_64/memory-calculator-2.0.2_RELEASE.tar.gz (found in cache)
       Memory Settings: -Xmx317161K -Xms317161K -XX:MetaspaceSize=64M -XX:MaxMetaspaceSize=64M -Xss228K
-----> Downloading Spring Auto Reconfiguration 1.10.0_RELEASE from https://java-buildpack.cloudfoundry.org/auto-reconfiguration/auto-reconfiguration-1.10.0_RELEASE.jar (found in cache)
Exit status 0
Staging complete
Uploading droplet, build artifacts cache...
Uploading build artifacts cache...
Uploading droplet...
Uploaded build artifacts cache (109B)
Uploading complete
Uploaded droplet (63.8M)
Destroying container
Successfully destroyed container

0 of 2 instances running, 2 starting
2 of 2 instances running

App started


OK

App apples-sbsessions was started using this command `CALCULATED_MEMORY=$($PWD/.java-buildpack/open_jdk_jre/bin/java-buildpack-memory-calculator-2.0.2_RELEASE -memorySizes=metaspace:64m..,stack:228k.. -memoryWeights=heap:65,metaspace:10,native:15,stack:10 -memoryInitials=heap:100%,metaspace:100% -stackThreads=300 -totMemory=$MEMORY_LIMIT) && JAVA_OPTS="-Djava.io.tmpdir=$TMPDIR -XX:OnOutOfMemoryError=$PWD/.java-buildpack/open_jdk_jre/bin/killjava.sh $CALCULATED_MEMORY" && SERVER_PORT=$PORT eval exec $PWD/.java-buildpack/open_jdk_jre/bin/java $JAVA_OPTS -cp $PWD/. org.springframework.boot.loader.JarLauncher`

Showing health and status for app apples-sbsessions in org apples-pivotal-org / space development as papicella@pivotal.io...
OK

requested state: started
instances: 2/2
usage: 512M x 2 instances
urls: apples-sbsessions-unideal-pollinization.cfapps.io
last uploaded: Sat Jul 23 12:21:07 UTC 2016
stack: unknown
buildpack: java-buildpack=v3.8.1-offline-https://github.com/cloudfoundry/java-buildpack.git#29c79f2 java-main open-jdk-like-jre=1.8.0_91-unlimited-crypto open-jdk-like-memory-calculator=2.0.2_RELEASE spring-auto-reconfiguration=1.10.0_RELEASE

     state     since                    cpu    memory           disk           details
#0   running   2016-07-23 10:21:49 PM   0.0%   296.2M of 512M   144.1M of 1G
#1   running   2016-07-23 10:21:53 PM   0.0%   240.6M of 512M   144.1M of 1G
```

- Invoke using the URL you used in your manifest.yml


![alt tag](https://dl.dropboxusercontent.com/u/15829935/platform-demos/images/piv-httpsession-springboot.png)

You can use the "Kill Me" link to kill the java container once you add some names to your session list, invoke the 
application again using a new browser tab and all your session data still exists. The best test is to actually 
use two seperate browsers to see how session data persists for each individual user even if the container crashes.

<hr />

Pas Apicella [papicella at pivotal.io] is a Senior Platform Architect at Pivotal






