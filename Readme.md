# Estimate Requests Analyzer
A simple application to grab data from the **Eestimate Requests Submission(ERS).csv** file and to analyze the data at user's disposal. Users have an option to save the analyzed data into .csv file.

## Objective
- For businesses whose majority of leads come from estimate requests via email or a website, it is very crucial to save the submitted requests in an organized form and analyze them for marketing/budgeting purposes. With the help of this application, businesses can now see how many requests have been submitted by month, by car make or model, and by a type of a vehicle.

## Requirements
- JDK Version 8 or 11
- Scala and SBT
- OpenCSV API 2.4 (***libraryDependencies += "au.com.bytecode" % "opencsv" % "2.4"***)
- Scala Logging Library 
  - (***libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"***)
  - (***libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"***)

## Usage
- Users can get the information of how many requests are submitted by month, by car make, by car model, and by a type of a vehicle
- Users can get the percentage of a specific car make's submissions out of the total submissions

## Commands
- Please use the following commands in order to properly compile, test, and run the application.

  ### 1. Compile
  ```
  sbt compile
  ```

  ### 2. Test
  ```
  sbt test
  ```

  ### 3. Run
  ```
  sbt run
  ```


## ETC
