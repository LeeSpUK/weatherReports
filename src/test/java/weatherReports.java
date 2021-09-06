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
