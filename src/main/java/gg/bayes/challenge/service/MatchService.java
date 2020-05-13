package gg.bayes.challenge.service;

import gg.bayes.challenge.model.HeroDamage;
import gg.bayes.challenge.model.HeroItems;
import gg.bayes.challenge.model.HeroKills;
import gg.bayes.challenge.model.HeroSpells;

import java.util.List;

public interface MatchService {
    Long ingestMatch(String payload);

    List<HeroKills> getHeroKills(String matchId);

    List<HeroItems> getHeroItems(String matchId, String heroName);

    List<HeroSpells> getHeroSpells(String matchId, String heroName);

    List<HeroDamage> getHeroDamages(String matchId, String heroName);
}
