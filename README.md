<h1> IBM Bluemix - Spring Boot Portable Cloud Ready HTTP Session </h1>

Storing HTTPSession state for failed instances in cloud native applications requires session data to be stored 
in some data repository which application instances read/write from. In this example below we use Spring Session
to automatically store session state in a Redis instance. 

Spring Session provides a very nice solution for all of these problems. It’s a wrapper around the standard 
Servlet HTTP Session abstraction. It’s easy to plug in to any application, whether they’re Spring-based or not. 
It acts as a sort of proxy in front of the HTTP session that forwards requests to a strategy implementation

[![Deploy to Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy?repository=https://hub.jazz.net/project/pasapples/SpringBootHTTPSession)

<a href="https://bluemix.net/deploy?repository=https://hub.jazz.net/project/pasapples/SpringBootHTTPSession" target="_blank"><img src="http://bluemix.net/deploy/button.png" alt="Bluemix button" /></a>

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

- Edit the manifest.yml below to enter a unique name and host and ensure you use the correct service name

```
applications:
 - name: yyyy
   memory: 512M
   instances: 2
   path: ./target/SpringBootHTTPSession-0.0.1-SNAPSHOT.jar
   host: yyyy
   domain: mybluemix.net
   buildpack: java_buildpack
   services:
    - redis-session
```

- Create a service on IBM Bluemix for storing the HTTPSEssion data

```
$ cf create-service rediscloud 30mb redis-session
```

- Push as shown below

```
pasapicella@pas-macbook-pro:~/ibm/DemoProjects/spring-starter/jazzhub/SpringBootHTTPSession$ cf push
Using manifest file /Users/pasapicella/ibm/DemoProjects/spring-starter/jazzhub/SpringBootHTTPSession/manifest.yml

Creating app pas-sbsessions in org pasapi@au1.ibm.com / space dev as pasapi@au1.ibm.com...
OK

Using route pas-sbsessions.mybluemix.net
Binding pas-sbsessions.mybluemix.net to pas-sbsessions...
OK

Uploading pas-sbsessions...
Uploading app files from: /Users/pasapicella/ibm/DemoProjects/spring-starter/jazzhub/SpringBootHTTPSession/target/SpringBootHTTPSession-0.0.1-SNAPSHOT.jar
Uploading 1M, 130 files
Done uploading
OK
Binding service redis-session to app pas-sbsessions in org pasapi@au1.ibm.com / space dev as pasapi@au1.ibm.com...
OK

Starting app pas-sbsessions in org pasapi@au1.ibm.com / space dev as pasapi@au1.ibm.com...
-----> Downloaded app package (19M)
-----> Java Buildpack Version: v3.0 | https://github.com/cloudfoundry/java-buildpack.git#3bd15e1
-----> Downloading Open Jdk JRE 1.8.0_60 from https://download.run.pivotal.io/openjdk/lucid/x86_64/openjdk-1.8.0_60.tar.gz (2.7s)
       Expanding Open Jdk JRE to .java-buildpack/open_jdk_jre (1.4s)
-----> Downloading Spring Auto Reconfiguration 1.10.0_RELEASE from https://download.run.pivotal.io/auto-reconfiguration/auto-reconfiguration-1.10.0_RELEASE.jar (1.4s)

-----> Uploading droplet (63M)

0 of 2 instances running, 2 starting
0 of 2 instances running, 2 starting
2 of 2 instances running

App started


OK

App pas-sbsessions was started using this command `SERVER_PORT=$PORT $PWD/.java-buildpack/open_jdk_jre/bin/java -cp $PWD/.:$PWD/.java-buildpack/spring_auto_reconfiguration/spring_auto_reconfiguration-1.10.0_RELEASE.jar -Djava.io.tmpdir=$TMPDIR -XX:OnOutOfMemoryError=$PWD/.java-buildpack/open_jdk_jre/bin/killjava.sh -Xmx382293K -Xms382293K -XX:MaxMetaspaceSize=64M -XX:MetaspaceSize=64M -Xss995K org.springframework.boot.loader.JarLauncher`

Showing health and status for app pas-sbsessions in org pasapi@au1.ibm.com / space dev as pasapi@au1.ibm.com...
OK

requested state: started
instances: 2/2
usage: 512M x 2 instances
urls: pas-sbsessions.mybluemix.net
last uploaded: Thu Sep 10 05:11:35 UTC 2015
stack: lucid64
buildpack: java_buildpack

     state     since                    cpu    memory           disk           details
#0   running   2015-09-10 03:12:51 PM   0.9%   400.8M of 512M   139.9M of 1G
#1   running   2015-09-10 03:12:52 PM   0.8%   410.6M of 512M   139.9M of 1G
```

- Invoke using the URL you used in your manifest.yml

eg: 

http://pas-sbsessions.mybluemix.net

![alt tag](https://dl.dropboxusercontent.com/u/15829935/spring-session.png)

You can use the "Kill Me" link to kill the java container once you add some names to your session list, invoke the 
application again using a new browser tab and all your session data still exists. The best test is to actually 
use two seperate browsers to see how session data persists for each individual user even if the container crashes.








