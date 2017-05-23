# Circuit Breakers using Spring Boot + Spring Retry

This project shows an example of how configure your project to have the CircuitBreaker from Spring Retry using Spring Boot.

### What are Circuit Breakers?

* Keep your system working even if some error happens in other services.
* It's not just a try/catch.
* Avoid overloading a service that is having problem to process the requests (or Connection timeouts that takes time to return and block the thread).

**How to use the Circuit Breaker with Spring Retry?**

* To enable the Spring Retry you need no annotate the Application / Configuration class with @EnableRetry.
* The annotation for CircuitBreaker is: @CircuitBreaker.

The @CircuitBreaker is an annotation that encapsulates the @Retryable(statefull = true), that means the same request will return the same response.

**The principal properties for the @CircuitBreaker are:**

* maxAttempts - Max attempts before starting calling the @Recover method annotated.
* openTimeout - If the maxAttemps fails inside this timeout, the recover method starts to been called.
* resetTimeout - If the circuit is open after this timeout, the next call will be to the system to gives the chance to return.

For example, if the maxAttempts is reached inside the openTimeout, then the circuit is open and the next request goes direct to the @Recover method. After the resetTimeout, the circuit closes and the method is called again.

This is very useful when you are calling a 3rd party and the system is failing the requests. The time of resetTimeout is the time that the system has to recovery (too many request, IO lock or all threads in use, for example).

## About the Example
This project is using:

* Spring Boot 1.5.3.RELEASE 
* Spring Retry 1.2.0.RELEASE
* Spring 4.3.8.RELEASE

This Circuit Breaker was implemented on the service *ExternalSystemService*, where there is a method annotated with @CircuitBreaker and another one with @Recover.
<br/>If an error happens in the CircuitBreaker method, then the Recover method is called to keep your system running.

About the Recover method, it needs to have the same signature (params and return type) that the method to be recovered.

### Running the Example
This project contains an Embedded gradle. 
In a terminal, navigate to the project folder and run:

`./gradlew bootRun`
  
On the web browser, call the Endpoint: 

`http://localhostL8080/callExternal`

And in the logs you should see what is going on:

`[  XNIO-2 task-1] c.b.g.services.ExternalSystemService     : Calling call method...`<br/>
`[  XNIO-2 task-1] c.b.g.services.ExternalSystemService     : Fallback for call invoked`<br/>
`[  XNIO-2 task-2] c.b.g.services.ExternalSystemService     : Calling call method...`<br/>
`[  XNIO-2 task-2] c.b.g.services.ExternalSystemService     : Fallback for call invoked`<br/>
`[  XNIO-2 task-4] c.b.g.services.ExternalSystemService     : Fallback for call invoked`<br/>
`[  XNIO-2 task-6] c.b.g.services.ExternalSystemService     : Fallback for call invoked`<br/>
`[  XNIO-2 task-8] c.b.g.services.ExternalSystemService     : Calling call method...`<br/>
`[  XNIO-2 task-8] c.b.g.services.ExternalSystemService     : Success calling external system`<br/>
`[ XNIO-2 task-10] c.b.g.services.ExternalSystemService     : Calling call method...`<br/>
`[ XNIO-2 task-10] c.b.g.services.ExternalSystemService     : Fallback for call invoked`<br/>

As we can see, after 2 failures, the call started going to the Recover method and not calling the main method anymore.
And after some time (resetTimeout), started calling again and a success call happened. 

