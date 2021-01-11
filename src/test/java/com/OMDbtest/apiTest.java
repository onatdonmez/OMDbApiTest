package com.OMDbtest;

import org.junit.Assert;
import org.junit.Test;

public class apiTest
{
    TestHelper testHelper = new TestHelper();

    String title = System.getProperty("title","Harry Potter and the Sorcerer's Stone");
    String word = System.getProperty("s","Harry Potter");
    String key = System.getProperty("apikey","c9b0e068"); // I created a free api key
    @Test
    public void shouldFindMovieWithId(){
        Movie movie = testHelper.findMovie(key, word, title);
        Assert.assertEquals("tt0241527", movie.getImdbID());
        String IdSearchTitle = testHelper.IDSearch(key, movie.getImdbID());
        Assert.assertEquals("Harry Potter and the Sorcerer's Stone",IdSearchTitle);
    }
    @Test
    public void movieDirectorShouldMatch()
    {
        Movie movie = testHelper.findMovie(key, word, title);
        Assert.assertEquals("Harry Potter and the Sorcerer's Stone",movie.getTitle());
        testHelper.getDirector(key, movie.getImdbID(),"Chris Columbus");
    }
}
