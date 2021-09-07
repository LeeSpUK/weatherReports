# QA Technical Test - Metaweather API 

A simple suite of automated tests checking the Metaweather API using Java and Rest Assured library. <br /> 

The API url and documentation url are available here - https://www.metaweather.com/api <br /> 

The automated tests check the following: 
- is the API is accessible?
- can users perform location searches
- can you receive 5 day weather forecast for Nottingham 
- can receive weather report for Nottingham for specific dates. <br /> 

You can find the test scenarios listed below.
<br /> 


## Tools / libraries used: 
1. Java
2. Rest Assured
3. JUnit
4. Maven
5. IntelliJ IDE 
<br /> 


## Test Scenarios

````
Feature: As a MetaWeather API client, I want to retrieve “tomorrows” weather for “Nottingham” Acceptance

  Scenario: Confirm the Metaweather API is accessible 
    Given I have the URL for the Metaweather API 
    When I make a GET request to API server 
    Then I see the API is accessible 
    And the server returns a 200 response code 

  Scenario : Confirm that Nottingham is one of the locations in the API
    Given I have access to the Metaweather API 
    When I perform query search for Nottingham 
    Then the API returns Nottingham as the location 
    And the response includes title, location type, latt_long, woeid and distance fields 
	
  Scenario 3: Search for Nottingham by DMS latitude and longitude coordinates 
    Given I have the DMS latitude and longitude coordinates for Nottingham from the query search
    When I search for Nottingham by its coordinates in the API
    Then I see that Nottingham is present in the API
    And the response includes title, location type, latt_long, woeid and distance fields 

  Scenario 4: Get five day weather report for Nottingham 
    Given I have the Where On Earth ID for Nottingham from the query or coordinates search
    When I search for Nottingham by its woeid  
    Then the API returns a date ordered 5 day weather forecast for Nottingham 
    And the response includes location, parent, consolidated weather and sources data as per API documentation

  Scenario 5: Confirm I can get today’s weather report for Nottingham 
    Given I have the Where On Earth ID for Nottingham 
    When I search for Nottingham by its woeid and today’s date
    Then the response shows today’s weather report for Nottingham 
    And the consolidated weather fields data as per API documentation

  Scenario 6: Confirm I can get tomorrow’s weather report for Nottingham 
    Given I have the Where On Earth ID for Nottingham 
    When I search for Nottingham by its woeid and tomorrow’s date
    Then the response shows tomorrow’s weather report for Nottingham 
    And the consolidated weather fields data as per API documentation

````
<br /> 

## Tests 

````
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.hasItem;

public class weatherReports {

    // this is test scenario 1 - confirm API is accessible
    @Test
    void confirmApiAvailability() {
        given().get("https://www.metaweather.com/api/").
                then().
                statusCode(200);
    }

    // this is test scenario 2 - confirm Nottingham is one of the locations listed in the API
    @Test
    void confirmLocationByQuerySearch() {
        when().get("https://www.metaweather.com/api/location/search/?query=Nottingham")
                .then().log().body().statusCode(200);
    }

    // this is test scenario 3 - search for Nottingham by DMS long/lat coordinates
    @Test
    void assertLocationByCoordinatesSearch() {
        when().get("https://www.metaweather.com/api/location/search/?lattlong=52.9548,1.1581").
                then().statusCode(200).and().body("title", hasItem("Nottingham"));
    }

    @Test
    void confirmLocationByCoordinatesSearch() {
        when().get("https://www.metaweather.com/api/location/search/?lattlong=52.949219,-1.143920").
                then().statusCode(200).log().body();
    }

    // this is test scenario 4 - get 5-day weather report for Nottingham
    @Test
    void getFiveDayWeatherReportNottingham() {
        when().get("https://www.metaweather.com/api/location/30720").
                then().statusCode(200).log().body();
    }

    // this is test scenario 5 - get today's weather report for Nottingham
    @Test
    void getWeatherReportTodayNottingham() {
        when().get("https://www.metaweather.com/api/location/30720/2021/9/6/").
                then().statusCode(200).log().body();
    }

    // this is test scenario 6 - get tomorrow's weather report for Nottingham
    @Test
    void retrieveTomorrowsWeatherNottingham() {
        when().get("https://www.metaweather.com/api/location/30720/2021/9/7/").
                then().statusCode(200).log().body();
    }

}

````
<br /> 

## Steps to start: 

1. Clone / Download the project onto your local machine 
2. Install IntelliJ IDE 
3. Start Intellij IDE
4. Go to File > Open file or Project > Navigate to project location in directory > Open project 
5. Build > Build project 
6. Navigate to src/test/java/weatherReports.java in project window 
7. Run > Run 'weatherReports'

<br /> 

## Part 2: Questions
            
  - Explain why you chose to use those particular tools/frameworks in the technical <br /> 
  
  I didn't have much time to complete the technical task so I needed to use a programming language, IDE and test framework which I was already familiar with or would be easy to set-up. 
  I knew I was going to use IntelliJ as my IDE as the GUI is very close to Android Studio, which is the most recent IDE I've used, so it wouldn't take much effort to navigate around the tool. 
  I wasn't sure about the programming language or test framework. Initially, I thought I'd write the tests using Cucumber, as its a test framework I'm familiar with for web automation, essentially creating a feature file with Gherkin BDD test cases and then writing the step definitions in Java. 
  However, I hadn't done any web automation in years and didn't have the time to relearn / remember the basics. I searched online for an API test framework and cheat sheets to get me up and running quickly. Some online videos suggested RestAssured library as a way to quickly write the tests so I decided to go with this strategy. 
  <br /> 
  
  - Describe the reason for the scope of your solution. Why did you test what you did, and why didn’t you test other <br /> 
  
  The aim of the solution was to confirm that the API was accessible, to do some exploratory testing arond the api specifically what JSON objects, fields and values are listed in the API, what data is returned when I make a call to the listed endpoints and paths, does the data in the response body match the expected dataset listed in the api documentation 
  and also I wanted verify that 1. Nottingham was one of the listed locations via a query search or by longitude and lattitude coordinates, 2. I could get a 5 day weather report for Nottingham and other locations and 3. I could get today's or tomorrow's weather report for Nottingham and other locations. 
  There were several other tests that I wanted to conduct such as seeing if I could get weather reports for multiple locations but I ran out of time. 
  <br /> 
  
  - What questions would you ask your Product Owner / what information would you need to know, to be able to test the whole MetaWeather API service <br /> 
  1. Is the API documentation up to date? Is there anything missing?
  2. What are the use cases for the API? Trying to get a sense why, how and what it is being for and by whom?
  3. What is the roadmap for the API? How long do I have to test it and confirm that it is working as expected?
  4. Is there a list of error cases or error codes?
  <br /> 
  
  - After completing the technical challenge, what would you do differently if you were asked to do the same challenge again? <br /> 
  1. I probably would have created the test using Cucumber framework. Despite managing to create some tests using Rest Assured, a lot of time was spent in the initial set up i.e reading about RestAssured, watching videos, setting dependencies etc. 
  2. Given myself more time to do some more exploratory testing to determine the scope of the API i.e. create an options request test (even though the get request on base url provides you with some data but not everything) 

  
