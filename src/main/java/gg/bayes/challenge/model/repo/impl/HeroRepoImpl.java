package gg.bayes.challenge.model.repo.impl;

import gg.bayes.challenge.model.HeroDamage;
import gg.bayes.challenge.model.HeroItems;
import gg.bayes.challenge.model.HeroKills;
import gg.bayes.challenge.model.HeroSpells;
import gg.bayes.challenge.model.repo.HeroRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HeroRepoImpl implements HeroRepo {

    private NamedParameterJdbcTemplate template;

    @Autowired
    public HeroRepoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    public List<HeroKills> getHeroKillsForMatch(final String matchId) {
        return template.query("SELECT hero, count(*) FROM kills group by hero",
                (resultSet, i) -> new HeroKills(resultSet.getString("hero"), resultSet.getInt("count")));

    }

    public List<HeroItems> getHeroItems(final String matchId, final String heroName) {
        return null;
    }

    public List<HeroSpells> getHeroSpells(final String matchId, final String heroName) {
        return null;
    }

    public List<HeroDamage> getHeroDamages(final String matchId, final String heroName) {
        return null;
    }

}
