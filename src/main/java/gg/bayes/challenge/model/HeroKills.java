package gg.bayes.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeroKills {
    private String hero;
    private Integer kills;
}
