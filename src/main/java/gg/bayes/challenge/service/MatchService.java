package gg.bayes.challenge.service;

import gg.bayes.challenge.model.HeroKills;

import java.util.List;

public interface MatchService {
    Long ingestMatch(String payload);

    List<HeroKills> getHeroKills(String matchId);

    // TODO add more methods as needed
}
