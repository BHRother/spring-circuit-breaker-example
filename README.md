**Spring Boot + Spring Retry**

This project shows an example of how configure your project to have the CircuitBreaker from Spring Retry.

The @CircuitBreaker is an annotation that encapsulate the @Retryable(statefull = true), that means the same request will return the same response. If the request with that Header fails, it's going to use the Recover method next time (if the max attemps is reached).

To use the @CircuitBreaker annotation, you need to use @EnableRetry in your Application.

The principal properties for the @CircuitBreaker are:

* maxAttempts - Max attempts before starting calling the @Recover method annotated.
* openTimeout - If the maxAttemps fails inside this timeout, the recover method starts to been called.
* resetTimeout - If the circuit is open after this timeout, the next call will be to the system to gives the chance to return.

This is very useful when you are calling a 3rd party and the system is failing the requests. The time of resetTimeout is the time that the system has to recovery (too many request, IO lock or all threads in use, for example).
  
