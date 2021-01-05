package com.OMDbtest;
import org.junit.Test;

public class apiTest
{
    TestHelper testHelper = new TestHelper();

    String title = System.getProperty("title","Harry Potter and the Sorcerer's Stone");
    String word = System.getProperty("s","Harry Potter");
    String key = System.getProperty("apikey","c9b0e068"); // I created a free api key
    @Test
    public void shouldFindMovieWithId(){
        String id = testHelper.MovieID(key, word, title);
        testHelper.IDSearch(key, id);
    }
}
