package gg.bayes.challenge.service.impl;

import com.google.common.base.Splitter;
import gg.bayes.challenge.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class MatchServiceImpl implements MatchService {

    public static final String MATCH = "match";

    private RestTemplate restTemplate;

    private String logstashUrl;

    @Autowired
    public MatchServiceImpl(final RestTemplate restTemplate, @Value("${logstash.url}") String logstashUrl) {
        this.logstashUrl = logstashUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public Long ingestMatch(String payload) {

        long matchId = ThreadLocalRandom.current().nextLong(0, 9999999999l);

        Splitter.on("\n").split(payload).forEach(event -> ingestEvent(event, matchId));

        return matchId;
    }

    private void ingestEvent(String event, long matchId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(MATCH, String.valueOf(matchId));

        final HttpEntity<String> request = new HttpEntity<>(event, headers);
        restTemplate
                .exchange(logstashUrl, HttpMethod.PUT, request, Void.class);
    }
}
