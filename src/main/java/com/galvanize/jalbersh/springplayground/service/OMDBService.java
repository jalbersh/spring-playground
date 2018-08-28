package com.galvanize.jalbersh.springplayground.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.jalbersh.springplayground.model.Result;
import com.galvanize.jalbersh.springplayground.model.SearchResults;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OMDBService {
    private String url="http://www.omdbapi.com/?i=tt3896198&apikey=9f1b9e8e";

    private final RestTemplate restTemplate = new RestTemplate();

    public String getUrl() {
        return url;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public String doQuery(String query) {
        System.out.println("in OMCBService.doQuery");
        String path="";
        String info="";
        try {
            path = url+"&s="+query;
            System.out.println("uri Path="+path);
            info = restTemplate.getForObject(
                    path,
                    String.class
            );
        } catch (Exception e) {
            System.out.println("Exception caught calling "+path+": "+e.getMessage());
            return "{\"error\": \""+e.getMessage()+"\"}";
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            SearchResults searchResults = mapper.readValue(info, new TypeReference<SearchResults>(){});
            Result[] results = searchResults.getSearch();
            String json = mapper.writeValueAsString(results);
            return json;
        } catch (Exception e) {
            System.out.println("Caught exception parsing value: "+e.getMessage());
            return info;
        }
    }
}
