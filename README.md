# Ksubaka-Film-Info-Retriever-Test

Required:
- Java 8
- Gradle (I'm using 2.13, but earlier versions should work)

To build:
./gradlew clean jar

Print usage:
java -jar build/libs/Ksubaka.jar

Run with:
java -Dapi=imdb "-Dmovie=Indiana Jones" -jar build/libs/Ksubaka.jar

CAVEATS:
- the results from the movie DB services are paginated, but in this test pagination is ignored, for simplicity, so the result sets are partial
- TMDB: does not always provide information about the director or the release date.
- TMDB: search criteria is "fussy", returning "similar" results which try to compensate for potential typos.
- TMDB: requires an "API key", and the embedded one is from my account.

GENERAL CONSIDERATIONS:
I could have used Maps and strings instead of typed objects, reducing the number of classes required to interpret the JSON responses, but I prefer strong typing and POJOs to Maps of Strings of Maps of Strings, for readability.
The 'jar' task actually builds an 'uberjar' or 'fatJar'.
Tests are partial, as some classes are just POJOs (which should not be tested except for the implementations of hashCode() and equals(), which a better testing framework like Groovy with @Canonical would make superfluous), and most of the others are "boundary" classes, which makes them more suitable for *integration* testing with something like Apiary, a real server or a mock server, but I wanted to keep the code simple enough..
