package com.maggio.game.goosemaze.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class Match {

    private static final Logger LOGGER = LoggerFactory.getLogger(Match.class);

    @Value("${match.win-cell:63}") private Integer winCell;

    private Map<String, Integer> position = null;

    public Match(Set<String> list) {
        position = list.stream().collect(Collectors.toMap(Function.identity(), e -> 0));
        LOGGER.debug("Create match with player: {}", position);
    }

    public void move(String player, int steps) {
        int currentSpace = position.get(player);
    }
}
