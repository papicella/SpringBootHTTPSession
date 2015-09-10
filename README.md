<h1> IBM Bluemix - Spring Boot Portable Cloud Ready HTTP Session </h1>

Storing HTTPSession state for failed instances in cloud native applications requires session data to be stored 
in some data repository which application instances read/write from. In this example below we use Spring Session
to automatically store session state in a Redis instance. 

Spring Session provides a very nice solution for all of these problems. It’s a wrapper around the standard 
Servlet HTTP Session abstraction. It’s easy to plug in to any application, whether they’re Spring-based or not. 
It acts as a sort of proxy in front of the HTTP session that forwards requests to a strategy implementation

<h2>Run locally</h2> 

- Clone code as shown below.

- Package as shown below

- Edit the manifest.yml below to enter a unique name and host

- Push as shown below

- Invoke using 







