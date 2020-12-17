package com.OMDbtest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;

public class TestHelper {

    String baseURI = System.getProperty("baseURI","http://www.omdbapi.com/");

    public String MovieID(String key, String word, String title)
    {
        RestAssured.baseURI = baseURI;
        String movieId = null;
        try
        {
            Response response = getResponseFromEndPoint(key,word);
            JsonPath jsonPath = response.jsonPath();
            List<Movie> info = jsonPath.getList("Search", Movie.class);
            System.out.println(info.size()+" films are found related your keyword");
            for (Movie object : info) { //get id if title matches
                if (object.getTitle().equals(title))
                {
                    movieId = object.getImdbID();
                    System.out.println("ID: " + movieId);
                    break;
                }
            }
            return movieId;
        }
        catch (Exception e)
        {
            System.out.println("Error occurred");
            return null;
        }
    }
    public Response getResponseFromEndPoint(String key, String word)
    {
        try
        {
            return given()
                    .param("apikey", key)
                    .param("s", word)
                    .when()
                    .get()
                    .then()
                    .log()
                    .all()
                    .contentType(ContentType.JSON)
                    .statusCode(200) //Standard response for successful HTTP requests(Checking http status)
                    .and()
                    //Checking whether the title, year, released fields of the data are included in the response.
                    .body("Search.Year",not(emptyOrNullString()))
                    .body("Search.Released",not(emptyOrNullString()))
                    .body("Search.Title",not(emptyOrNullString()))
                    .extract()
                    .response();
        }
        catch (Exception e)
        {
            System.out.println("Error occurred");
            return null;
        }
    }

    public void IDSearch(String key, String movieId)
    {
        try
        {
            given()
                    .param("apikey", key)
                    .param("i",movieId)
                    .when()
                    .get()
                    .then()
                    .log()
                    .all()
                    .statusCode(200)
                    .body("Year", not(emptyOrNullString()))
                    .body("Released", not(emptyOrNullString()))
                    .body("Title", not(emptyOrNullString()));
        }
        catch (Exception e)
        {
            System.out.println("Error occurred");
        }
    }


}
