package com.galvanize.jalbersh.springplayground.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchResults {
    private @JsonProperty("Search") Result [] search;
    private @JsonProperty("totalResults") String totalResults;
    private @JsonProperty("Response") String response;

    public SearchResults() {
    }

    public SearchResults(Result [] search, String totalResults, String response) {
        this.search = search;
        this.totalResults = totalResults;
        this.response = response;
    }

    public Result [] getSearch() {
        return search;
    }

    public void setSearch(Result [] search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
