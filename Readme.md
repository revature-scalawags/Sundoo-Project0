# Estimate Requests Analyzer
A simple application to grab data from the **ERS(Eestimate Requests Submission).csv** file and to analyze the data at user's disposal. Users have an option to save the analyzed data into .csv file.

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

## Features
- Shows a list of files in a directory
- Goes through the entire file line by line and exclude any empty rows or empty columns
- Catches most, if not all, of errors or exceptions when users type in invalid type input and re-prompt users to type in valid input
- Allows users to select information of how many requests are submitted by month, by car make, by car model, and by a vehicle type
- Calcuate the percentage of a specific car make's submissions out of the total submissions
- Gives users an option to save the result in .csv file or in mongoDB

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