# Nick Bottom

Nick Bottom provides Shakespearean descriptions of Pokemons.

## Building

This project provides a Maven wrapper (`mvnw`) to ease the build process.

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

## Behind the Name

In the enchanted forest of "A Midsummer Night's Dream", filled with fauns, fairies, and
other supernatural creatures, Pokemons don't appear too out of place.

Nick Bottom is a character from this comedy. He's a weaver, and the action of sending a
request back and forth between several back-ends reminds me of threading a needle through
fabric. Also, Nick Bottom was not gifted with great intelligence, very much like this
software (though, in the case of software, simplicity is a good thing.) 

