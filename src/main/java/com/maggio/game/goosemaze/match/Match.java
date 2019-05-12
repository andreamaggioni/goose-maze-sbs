package com.maggio.game.goosemaze.match;

import com.maggio.game.goosemaze.config.MessageConfiguration;
import com.maggio.game.goosemaze.match.move.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class Match {

    private static final Logger LOGGER = LoggerFactory.getLogger(Match.class);

    @Value("${match.win-cell}") private Integer winCell;

    @Value("${match.bridgeSpace}") private Integer bridgeSpace;

    @Value("${match.bridgeSpaceEnd}") private Integer bridgeSpaceEnd;

    @Value("${march.gooseSpaces}") private List<Integer> gooseSpaces;

    @Autowired
    private MessageConfiguration.CustomMessageSource messageSource;

    @Autowired private BeanFactory beanFactory;

    @Autowired private MoveFactory moveFactory;

    private Map<String, Integer> position = null;

    public Match(Set<String> list) {
        position = list.stream().collect(Collectors.toMap(Function.identity(), e -> 0));
        LOGGER.debug("Create match with player: {}", position);
    }

    public AbstractMove move(String player, @Size(min = 2, max = 2) List<Integer> rolls) {
        int currentSpace = position.get(player);
        int newSpace = currentSpace + rolls.stream().mapToInt(Integer::valueOf).sum();

        AbstractMove move = null;
        if(newSpace > winCell) {
            move = this.moveFactory.bounceMove(player, rolls.get(0), rolls.get(1), currentSpace);
        } else if(newSpace == winCell){
            move = this.moveFactory.winMove(player, rolls.get(0), rolls.get(1), currentSpace);
        } else if(newSpace == bridgeSpace){
            move = this.moveFactory.bridgeMove(player, rolls.get(0), rolls.get(1), currentSpace);
        } else if (this.gooseSpaces.contains(newSpace)){
            move = this.moveFactory.gooseMove(player, rolls.get(0), rolls.get(1), currentSpace);
        } else {
            move = this.moveFactory.move(player, rolls.get(0), rolls.get(1), currentSpace);
        }
        position.put(player, move.getNewSpace());
        return move;
    }

    public Map<String, Integer> getPosition() {
        return this.position;
    }
}
