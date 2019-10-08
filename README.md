A command-line Java program that fetches data from the following public API:
https://www.coindesk.com/api
Once executed, the program should request the user to input a currency code (USD, EUR, GBP, etc.)
Once the user provides the currency code, the application should display the following information:
- The current Bitcoin rate, in the requested currency
- The lowest Bitcoin rate in the last 30 days, in the requested currency
- The highest Bitcoin rate in the last 30 days, in the requested currency
If the currency code provided is not supported by the API, a message should inform the user.
Additional requirements:
- Application has unit test coverage.
- At the time of coding I tried to follow good practices and have comments.
- The final result of this exercise is an executable JAR file that can be run in any platform
in a standard Java VM (Java 8) along with a ZIP file with the source code.