package gg.bayes.challenge.service.impl;

import com.google.common.base.Splitter;
import gg.bayes.challenge.model.HeroDamage;
import gg.bayes.challenge.model.HeroItems;
import gg.bayes.challenge.model.HeroKills;
import gg.bayes.challenge.model.HeroSpells;
import gg.bayes.challenge.model.repo.HeroRepo;
import gg.bayes.challenge.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class MatchServiceImpl implements MatchService {

    public static final String MATCH = "match";

    private HeroRepo repository;

    private RestTemplate restTemplate;

    private String logstashUrl;

    @Autowired
    public MatchServiceImpl(final HeroRepo repository, final RestTemplate restTemplate, @Value("${logstash.url}") String logstashUrl) {
        this.repository = repository;
        this.logstashUrl = logstashUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public Long ingestMatch(final String payload) {

        log.info("Received new payload for injestion");
        long matchId = ThreadLocalRandom.current().nextLong(0, 9999999999l);

        log.info("Now splitting payload and injesting under new matchId {}", matchId);
        Splitter.on("\n").split(payload).forEach(event -> ingestEvent(event, matchId));
        log.info("Successfully injested the events for matchId {}", matchId);
        return matchId;
    }

    @Override
    public List<HeroKills> getHeroKills(final String matchId) {
        return repository.getHeroKillsForMatch(matchId);
    }

    @Override
    public List<HeroItems> getHeroItems(String matchId, String heroName) {
        return repository.getHeroItems(matchId, heroName);
    }

    @Override
    public List<HeroSpells> getHeroSpells(String matchId, String heroName) {
        return repository.getHeroSpells(matchId, heroName);
    }

    @Override
    public List<HeroDamage> getHeroDamages(String matchId, String heroName) {
        return repository.getHeroDamages(matchId, heroName);
    }

    private void ingestEvent(String event, long matchId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(MATCH, String.valueOf(matchId));

        final HttpEntity<String> request = new HttpEntity<>(event, headers);
        restTemplate
                .exchange(logstashUrl, HttpMethod.PUT, request, Void.class);
    }


}
