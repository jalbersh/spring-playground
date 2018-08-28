package com.galvanize.jalbersh.springplayground.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
{"Title":"Little Miss Sunshine","Year":"2006","imdbID":"tt0449059","Type":"movie","Poster":"https://m.media-amazon.com/images/M/MV5BMTgzNTgzODU0NV5BMl5BanBnXkFtZTcwMjEyMjMzMQ@@._V1_SX300.jpg"}
 */
public class Result {
    private @JsonProperty("Title") String title;
    private @JsonProperty("Year") String year;
    private @JsonProperty("imdbID") String imdbId;
    private @JsonProperty(value="Type",access = JsonProperty.Access.WRITE_ONLY) String type;
    private @JsonProperty("Poster") String poster;

    public Result() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @JsonIgnore
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
