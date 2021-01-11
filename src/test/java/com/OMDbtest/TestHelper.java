package com.OMDbtest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestHelper {

    String baseURI = System.getProperty("baseURI","http://www.omdbapi.com/");

    public Movie findMovie(String key, String word, String title)
    {
        try
        {
            RestAssured.baseURI = baseURI;
            Response response = getResponseFromEndPoint(key,word);
            JsonPath jsonPath = response.jsonPath();
            List<Movie> info = jsonPath.getList("Search", Movie.class);
            //I use generic predicate interface to use filter. There is also primitive type(it does not require type)
            //it gets the movie title and compare with title value. It returns true/false
            Predicate<Movie> findRightTitle = movie -> movie.getTitle().equals(title);
            System.out.println(info.size()+" films are found related your keyword");

            return info.stream().filter(findRightTitle).collect(Collectors.toList()).get(0);
        }
        catch (Exception e)
        {
            System.out.println("Can not find the movie " + title);
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
            //response.prettyPrint();
            //JsonPath jsonPath = response.jsonPath();
            //String title = response.jsonPath.get("Title");
            //assertThat("",title != "");
        }
        catch (Exception e)
        {
            System.out.println("Invalid Response from API");
            return null;
        }
    }
    public Response getDirector(String key, String movieId, String director)
    {
        try
        {
            return given()
                    .param("apikey", key)
                    .param("i", movieId)
                    .when()
                    .get()
                    .then()
                    .log()
                    .all()
                    .contentType(ContentType.JSON)
                    .statusCode(200) //Standard response for successful HTTP requests(Checking http status)
                    .and()
                    .body("Director",equalTo(director))
                    .extract()
                    .response();
        }
        catch (Exception e)
        {
            System.out.println("Invalid Response from API");
            return null;
        }
    }
    public String IDSearch(String key, String movieId)
    {
        try
        {
            Response response = given()
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
                    .body("Title", not(emptyOrNullString()))
                    .extract()
                    .response();

            return response.jsonPath().get("Title");
        }
        catch (Exception e)
        {
            System.out.println("Can not find the movie " + movieId);
            return null;
        }
    }


}
