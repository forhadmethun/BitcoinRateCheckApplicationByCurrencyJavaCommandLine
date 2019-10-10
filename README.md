## About:
A command-line Java program that fetches data from the following public API:  
https://www.coindesk.com/api  
Once executed, the program request the user to input a currency code (USD, EUR, GBP, etc.)
Once the user provides the currency code, the application display the following information:
- The current Bitcoin rate, in the requested currency
- The lowest Bitcoin rate in the last 30 days, in the requested currency
- The highest Bitcoin rate in the last 30 days, in the requested currency
If the currency code provided is not supported by the API, a message is informed to the user.
Additional features:
- Application has unit test coverage. (src/test/java/BitCoinRateCheckApplicationByCurrencyTest.java)
- At the time of coding I tried to follow good practices and have comments.
- The final result of this exercise is an executable JAR file that can be run in any platform
in a standard Java VM (Java 8) along with a ZIP file with the source code.

## Installation
- maven is required to resolve all the dependency 
- The following command is needed in the root directory to package as jar
``` 
    mvn package
``` 
- The jar file will be stored in the following directory 
``` 
    target/BitcoinRateCheckApplicationByCurrency-1.0-SNAPSHOT-jar-with-dependencies.jar
```
- Run the following command to run the application
``` 
    cd target
    java -jar BitcoinRateCheckApplicationByCurrency-1.0-SNAPSHOT-jar-with-dependencies.jar
```