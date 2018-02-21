# Build locally
1. `mvn clean install -DskipTests`
2. `cd SurveyClient/`
3. `mvn gwt:run-codeserver`
4. Open new terminal window
4. `cd SurveyServer/`
5. `sbt run`
7. Compile in browser: http://www.gwtproject.org/articles/superdevmode.html