# REST-Assured-Java-Part-2

## Pre-requisites
- Follow [Prerequisite Part 1](https://github.com/mrisqiamiruladieb/REST-Assured-Java-Part-1/blob/master/README.md#pre-requisites)
- Go to **pom.xml**, _in the dependencies tag_, _add the following dependencies_:
  - Go to [JSON Schema Validator](https://mvnrepository.com/artifact/io.rest-assured/json-schema-validator/5.4.0), [Allure TestNG](https://mvnrepository.com/artifact/io.qameta.allure/allure-testng/2.27.0), and _copy_ the content on **the Maven navbar** both, and _paste_ it inside the dependencies tag 
  - Click _Load Maven Changes (logo)_ to install or sync the dependency
- _Folder Structure :_ [References](https://github.com/arbiminanda19/rest-assured-api-test)

## Validate JSON Schema Response Body
- Configuring **files** to make _requests_
- Import _the required libraries_ in [the prerequisites](#pre-requisites)
- **Create** a json schema response body _file_ to verify using [the Helper](https://github.com/mrisqiamiruladieb/REST-Assured-Java-Part-1/blob/master/README.md#helper)
- Code ([References 1](https://github.com/arbiminanda19/rest-assured-api-test/blob/main/src/test/java/scenarios/Login.java), [References 2](https://medium.com/@myskill.id/rest-assured-java-part-2-89889dab1bac)) and click **run** _(the play logo)_

## HTML Report
- Configuring **files** to make _requests_
- **Run** Tests
  - _After executing the test_, _a directory/folder_ called **allure-results** _will be created_ that _contains a collection of json files_ that can be **compiled using allure to become an HTML report**.
- **Run command** to view **Allure Report**
  - To view _the Allure Report results_ in **HTML**, **run** _the following command_ in the **terminal**.
    - `allure serve allure-results`

## Run simultaneously using the .xml file
- **Create a file** with _the directory :_ src/test/java/azurewebsites/runner/**TestRunner.xml**
- Code ([References](https://www.softwaretestinghelp.com/testng-example-to-create-testng-xml/)) 
- _Right click_ on the file and select **run** _(the play logo)_

## Issues and solutions
- Cannot resolve symbol 'module' when _importing io.restassured.module.jsv.JsonSchemaValidator;_
  - **Solution :** Add `<scope>test</scope>` to _the corresponding dependency_ in **pom.xml**
- _Prioritizing tests_ in **TestNG**
  - **Solution :** [Code](https://www.browserstack.com/guide/prioritizing-tests-in-testng-with-selenium)
- **Error** when _running the command_ **to view the Allure Report**
  - **_Error messages :_** allure : The term 'allure' is not recognized as the name of a cmdlet, function, script file, or operable program. Check the spelling of the name, or if a path was included, verify
    that the path is correct and try again...bla..bla..
  - **Solution :** 
    - **Run** in _command line/terminal_: [References](https://stackoverflow.com/questions/70885555/allure-report-generation-fails-with-message-allure-is-not-recognized-as-the-n)
      - `npm install -g allure-commandline --save-dev`