# Vapor benchmark - Spring-boot edition

## What it is
[Vapor](https://qutheory.io/vapor/) is a Web framework written in Swift. The authors of [this benchmarking article](https://medium.com/@qutheory/server-side-swift-vs-the-other-guys-2-speed-ca65b2f79505) put out a call for help getting some 
benchmarks run using various popular Web frameworks from other languages; this is the one for Java's [Spring Boot](https://spring.io) framework.

## Test Methodology
This is the description of the benchmark tests each framework was to run. You can see that these each beecame a simple request handler in `ApplicationController.java`:

### Plaintext
Request:
`GET /plaintext HTTP/1.1`

Response:
`HTTP/1.1 200 OK`

`Hello, World!`

### JSON
Request:
`GET /json HTTP/1.1`

Response:
`HTTP/1.1 200 OK`

```javascript
{
  "array": [1, 2, 3]
  "dict": {"one": 1, "two": 2, "three": 3}
  "int": 42,
  "string": "test",
  "double": 3.14,
  "null": null
}
```

### SQLite Fetch

Grab a random user from the 3 users in the provided `test.sqlite` database:

```
sqlite> select * from users;
1|Foo|foo@gmail.com
2|Bar|bar@gmail.com
3|Baz|baz@gmail.com
```

This will require randomly choosing from 1, 2, or 3.

Request:
`GET /sqlite-fetch HTTP/1.1`

Response:
`HTTP/1.1 200 OK`

```javascript
{
  "id": 2,
  "name": "Bar",
  "email": "bar@gmail.com"
}
```



### Benchmark
```
wrk -d 10 -t 4 -c 128 http://<host>:<port>/plaintext
wrk -d 10 -t 4 -c 128 http://<host>:<port>/json
wrk -d 10 -t 4 -c 128 http://<host>:<port>/sqlite-fetch
```


## How it is built
I started by cloning the [Spring REST service starter guide](https://github.com/spring-guides/gs-rest-service.git) to get a 
REST service up and running as quickly as possible. I added code and tests for the three REST endpoints described above, then
ran it locally in IntelliJ IDEA. Once I was satisfied that the code was working and had good-enough test coverage, I uploaded
it to the server and ran it with `java -Dspring.profiles.active=prod -jar ./vapor-benchmark-0.1.0.jar` - you can see in 
`application.yml` that I define a `dev` profile that creates a fresh SQLite DB locally, and a `prod` profile that expects to find
the one provided for the benchmark.

# Challenges
The single biggest challenge was working with SQLite instead of a "blessed" low-footprint DB like Derby or H2. In the end I had to 
crib from a StackOverflow answer to get a Hibernate dialect that was compliant with Hibernate 4; the SQLite dialects you can find
on Maven Central are not up to date, and lead to a runtime crash with a suitably inscrutable exception. 

# Conclusion
I have over a decade of experience writing Web and backend processing code in Java. It would be foolish to dismiss the power and 
battle-hardened nature of the JVM as a platform, but the language itself has never been anything but needlessly verbose and painful,
with situations just like this being _the norm_ rather than an surprising outlier. In the last few years I have turned my attention
gratefully to Clojure and, to a lesser degree, Scala, as "escape hatches" that let me leverage the JVM while still working in a 
language that satisfies my desire for beauty and concision. I have very high hopes for Swift, and Vapor, to take a place among the
top tier of beautiful, powerful tools for doing real work beyond the confines of iOS development.
