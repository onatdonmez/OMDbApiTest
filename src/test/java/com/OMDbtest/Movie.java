package com.OMDbtest;

public class Movie {
    public String imdbID;
    public String Year;
    public String Title;
    public String Poster;
    public String Type;

    public String getImdbID()
    {
        return imdbID;
    }

    public void setImdbID(String imdbID)
    {
        this.imdbID = imdbID;
    }
    public String getYear()
    {
        return Year;
    }

    public void setYear(String year)
    {
        this.Year = year;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        this.Title = title;
    }
    public String getPoster()
    {
        return Poster;
    }
    public void setPoster(String poster)
    {
        this.Poster = poster;
    }
    public String getType()
    {
        return Type;
    }
    public void setType(String type)
    {
        this.Type = type;
    }
}

