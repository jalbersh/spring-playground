package com.galvanize.jalbersh.springplayground.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
public class OMDBServiceTest {

    private OMDBService service;

    @Before
    public void setup() {
        service = new OMDBService();
    }

    @Test
    public void testOMDBServiceRequests() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(service.getRestTemplate());
        String url = service.getUrl();
        String query = "Antarctica";
        url = url+"&s="+query;
        String expectedResult = "[{\\\"Title\\\":\\\"Antarctica: A Year on Ice\\\",\\\"Year\\\":\\\"2013\\\",\\\"imdbID\\\":\\\"tt2361700\\\",\\\"Poster\\\":\\\"https://m.media-amazon.com/images/M/MV5BMjI5MzI0Nzk3OV5BMl5BanBnXkFtZTgwNTAyODExMzE@._V1_SX300.jpg\\\"},{\\\"Title\\\":\\\"Antarctica: A Year on Ice\\\",\\\"Year\\\":\\\"2013\\\",\\\"imdbID\\\":\\\"tt2773308\\\",\\\"Poster\\\":\\\"http://ia.media-imdb.com/images/M/MV5BMjI5MzI0Nzk3OV5BMl5BanBnXkFtZTgwNTAyODExMzE@._V1_SX300.jpg\\\"}]";

        mockServer
                .expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        assertThat(service.doQuery(query), equalTo(expectedResult));
        mockServer.verify();
    }

}