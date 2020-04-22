# Spring Boot Hystrix example project

#### Docs:
* [Hystrix wiki](https://github.com/Netflix/Hystrix/wiki)
* [Hystrix Configuration](https://github.com/Netflix/Hystrix/wiki/Configuration)

#### Hystrix Dashboard:
* http://localhost:8080/hystrix/monitor?stream=localhost%3A8080%2Factuator%2Fhystrix.stream

#### Services:
* http://localhost:8080/api/echo?data=asd
* http://localhost:8080/api/sleep?seconds=1
* http://localhost:8080/api/error?throwException=true

using https://github.com/mgebra/spring-rest project for external services