package gg.bayes.challenge.service;

import gg.bayes.challenge.service.impl.MatchServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


public class MatchServiceImplTest {

    public static final String LOGSTASH_URL = "http://127.0.0.1:31311";

    @Test
    void testaIngestMatch() {

        final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        final MatchServiceImpl matchService = new MatchServiceImpl(restTemplate, LOGSTASH_URL);

        final long match_id = matchService.ingestMatch("log 1");

        final ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<HttpMethod> httpMethodCaptor = ArgumentCaptor.forClass(HttpMethod.class);
        final ArgumentCaptor<HttpEntity> httpEntity = ArgumentCaptor.forClass(HttpEntity.class);
        final ArgumentCaptor<Class> voidArgumentCaptor = ArgumentCaptor.forClass(Class.class);

        Mockito.verify(restTemplate).exchange(urlCaptor.capture(), httpMethodCaptor.capture(), httpEntity.capture(), voidArgumentCaptor.capture());
        assertEquals(LOGSTASH_URL, urlCaptor.getValue());
        assertEquals(HttpMethod.PUT, httpMethodCaptor.getValue());
        assertEquals("log 1", httpEntity.getValue().getBody().toString());
        assertEquals(String.valueOf(match_id), httpEntity.getValue().getHeaders().get("match").get(0));

    }


    @Test
    void testIngestMatchMultilineFile() {

        final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        final MatchServiceImpl matchService = new MatchServiceImpl(restTemplate, LOGSTASH_URL);

        final long match_id = matchService.ingestMatch("log 1\nlog2");

        Mockito.verify(restTemplate, times(2)).exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class));

    }
}
