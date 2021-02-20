# Nick Bottom

Nick Bottom provides Shakespearean descriptions of Pokemon.

## Building

If you already have Java 11 installed, you can build using the provided
Maven wrapper (`mvnw`). Otherwise, you can build using only 
[Docker](https://www.docker.com/). Either way, the output of the build
will be a docker image called `nick-bottom:1.0.0`.

### with Docker

    docker run -it --rm --name my-maven-project -v /var/run/docker.sock:/var/run/docker.sock  -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven maven:3.6-jdk-11 mvn spring-boot:build-image

### with Maven 

Building requires Java 11 installed. Run `./mvnw -version` to check your
current Java version. If Java 11 is installed but not detected, make sure 
that the JAVA_HOME environment variable points to the correct location. 

You can build a docker image using the Spring Boot plugin:

    ./mnvw spring-boot:build-image
    
## Running

The easiest way is by using [Docker Compose](https://docs.docker.com/compose/).
Simply run:

    docker-compose up

from the project directory. If you have [httpie](https://httpie.io/) installed, 
you can check that everything works like this:

    
    http get http://localhost:8080/pokemon/charizard

You should get an output similar to

    HTTP/1.1 200 
    Connection: keep-alive
    Content-Type: application/json
    Date: Sat, 20 Feb 2021 13:27:03 GMT
    Keep-Alive: timeout=60
    Transfer-Encoding: chunked
    
    {
        "description": "At which hour expelling a fie of super hot fire,  the red flame at the tip of its tail burns moo intensely.",
        "name": "charizard"
    }
    
(the actual description is chosen randomly among the many available, so yours may be different).

## Future Improvements

There are two main areas in which Nick Bottom could be improved: configurability
and caching.

### Configurability

At the moment, the PokeApi and FunTranslations API endpoints are hardcoded and don't
use any credentials. Ideally, both the endpoint, and an optional secret key, should 
be provided by a configuration file. 

Customising the endpoint can be useful i.e.
if there is a corporate proxy that should be used, or if a different server is
required for e.g. UAT testing.

### Caching

Calling external APIs can be expensive, rate-limited, or both. Having a layer of
caching before PokeApi and FunTranslations can save money and improve performance.

A distributed cache such as [Redis](https://redis.io/) would be ideal.

## Behind the Name

In the enchanted forest of "A Midsummer Night's Dream", filled with fauns, fairies, and
other supernatural creatures, Pokemon don't appear too out of place.

Nick Bottom is a character from this comedy. He's a weaver, and the action of sending a
request back and forth between several back-ends reminds me of threading a needle through
fabric. Also, Nick Bottom was not gifted with great intelligence, very much like this
software (though, in the case of software, simplicity is a good thing.) 
