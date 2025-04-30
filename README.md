# APIAssignment
API Automation for endpoints available in https://petstore.swagger.io/


This project demonstrates building a robust API automation framework from scratch using:

Java,
Serenity BDD,
Rest Assured,
Cucumber,
JUnit,
Jackson(to deal with json and POJOs)

Framework Development from Scratch

1. Project Initialization
   Maven Setup: Created a Maven project with dependencies for Serenity BDD, Rest Assured, Cucumber, and JUnit.
Directory Structure: Organized packages into POJOs, StepDefs, TestData, Utils, Payloads, Constants and Runner for clarity and maintainability.

2. POJO Creation
   Defined a Plain Old Java Object (POJO) representing the modules available in swagger(Pet,Store,User).

3. Test Data Generation
   Implemented utility methods to generate random data for each field in the User POJO, ensuring uniqueness and variability in test data.
   Also, used json for respective usecases from Payloads(folder) 
4. Step Definitions
   Mapped Gherkin steps to Java methods using Cucumber annotations. Included methods to:Generate input payloads.
5.Steps
   Send HTTP requests using Rest Assured + Serenity.
   Validate responses and status codes.

6. Feature Files
   covered all the three modules available in swagger. follows Gherkin syntax

7. Test Runner
   TestRunner.java: Configured Cucumber options and integrated with Serenity BDD to execute feature files and generate reports.
8. Serenity Conf 
   provided required config details


Prerequisites
Java 8 or higher
Maven installed and configured

command to run - mvn clean verify
/* we can give different options in command */


Reports available at -
target/site/serenity/index.html

