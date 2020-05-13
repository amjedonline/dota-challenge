package gg.bayes.challenge.model.repo.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import gg.bayes.challenge.model.HeroDamage;
import gg.bayes.challenge.model.HeroItems;
import gg.bayes.challenge.model.HeroKills;
import gg.bayes.challenge.model.HeroSpells;
import gg.bayes.challenge.model.repo.HeroRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Repository
public class HeroRepoImpl implements HeroRepo {

    private NamedParameterJdbcTemplate template;

    @Autowired
    public HeroRepoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    public List<HeroKills> getHeroKillsForMatch(final String matchId) {

        final String sql = "SELECT hero, count(*) FROM kills WHERE match_id = :match_id GROUP BY hero";
        final Map<String, Object> args = ImmutableMap.of("match_id", matchId);
        return template.query(sql, args,
                (resultSet, i) -> new HeroKills(resultSet.getString("hero"), resultSet.getInt("count")));

    }

    public List<HeroItems> getHeroItems(final String matchId, final String heroName) {
        final String sql = "SELECT item, timestamp FROM items WHERE match_id = :match_id and hero = :hero";
        final Map<String, Object> args = ImmutableMap.of("match_id", matchId, "hero", heroName);
        return template.query(sql, args,
                (resultSet, i) -> {
                    String item = resultSet.getString("item");

                    // TODO: ingest timestamp as Long in Logstash
                    String timestamp = resultSet.getString("timestamp");
                    List<String> split = Splitter.on(".").splitToList(timestamp);
                    LocalTime localTime = LocalTime.parse(split.get(0), DateTimeFormatter.ofPattern("HH:mm:ss"));
                    long millis = localTime.toSecondOfDay() * 1000 + Long.valueOf(split.get(1));
                    return new HeroItems(item, millis);
                });
    }

    public List<HeroSpells> getHeroSpells(final String matchId, final String heroName) {

        final String sql = "SELECT spell, count(*) FROM spells WHERE match_id = :match_id and hero = :hero GROUP BY spell";
        final Map<String, Object> args = ImmutableMap.of("match_id", matchId, "hero", heroName);
        return template.query(sql, args,
                (resultSet, i) ->
                     new HeroSpells(
                             resultSet.getString("spell"),
                             resultSet.getInt("count")));

    }

    public List<HeroDamage> getHeroDamages(final String matchId, final String heroName) {

        final String sql = "SELECT damaged_hero, count(*), sum(damage_score) FROM damages WHERE match_id = :match_id and hero = :hero GROUP BY damaged_hero";
        final Map<String, Object> args = ImmutableMap.of("match_id", matchId, "hero", heroName);
        return template.query(sql, args,
                (resultSet, i) ->
                        new HeroDamage(
                                resultSet.getString("damaged_hero"),
                                resultSet.getInt("count"),
                                resultSet.getInt("sum")));

    }

}
