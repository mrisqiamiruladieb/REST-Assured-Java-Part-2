# REST-Assured-Java-Part-2

## Pre-requisites
- Follow [Prerequisite Part 1](https://github.com/mrisqiamiruladieb/REST-Assured-Java-Part-1/blob/master/README.md#pre-requisites)
- Go to **pom.xml**, _in the dependencies tag_, _add the following dependencies_:
  - Go to [JSON Schema Validator](https://mvnrepository.com/artifact/io.rest-assured/json-schema-validator/5.4.0), _copy_ the content on **the Maven navbar**, and _paste_ it inside the dependencies tag 
  - Click _Load Maven Changes (logo)_ to install or sync the dependency
- _Folder Structure :_ [References](https://github.com/arbiminanda19/rest-assured-api-test)

## Validate JSON Schema Response Body
- Configuring **files** to make _requests_
- Import _the required libraries_ in [the prerequisites](#pre-requisites)
- **Create** a json schema response body _file_ to verify using [the Helper](https://github.com/mrisqiamiruladieb/REST-Assured-Java-Part-1/blob/master/README.md#helper)
- Code ([References 1](https://github.com/arbiminanda19/rest-assured-api-test/blob/main/src/test/java/scenarios/Login.java), [References 2](https://medium.com/@myskill.id/rest-assured-java-part-2-89889dab1bac)) and click **run** _(the play logo)_

## Issues and solutions
- Cannot resolve symbol 'module' when _importing io.restassured.module.jsv.JsonSchemaValidator;_
  - **Solution :** Add `<scope>test</scope>` to _the corresponding dependency_ in **pom.xml**
- _Prioritizing tests_ in **TestNG**
  - **Solution :** [Code](https://www.browserstack.com/guide/prioritizing-tests-in-testng-with-selenium)