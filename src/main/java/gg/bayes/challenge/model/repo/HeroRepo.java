package gg.bayes.challenge.model.repo;

import gg.bayes.challenge.model.HeroDamage;
import gg.bayes.challenge.model.HeroItems;
import gg.bayes.challenge.model.HeroKills;
import gg.bayes.challenge.model.HeroSpells;

import java.util.List;

public interface HeroRepo {

    List<HeroKills> getHeroKillsForMatch(String matchId);

    List<HeroItems> getHeroItems(String matchId, String heroName);

    List<HeroSpells> getHeroSpells(String matchId, String heroName);

    List<HeroDamage> getHeroDamages(String matchId, String heroName);

}
