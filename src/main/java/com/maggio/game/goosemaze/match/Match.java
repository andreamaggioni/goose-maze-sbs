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

    @Autowired private MessageConfiguration.CustomMessageSource messageSource;

    @Autowired private BeanFactory beanFactory;

    @Autowired private MoveFactory moveFactory;

    private Map<String, Integer> position = null;

    private final boolean withPlank;

    public Match(Set<String> list, boolean withPlank) {
        position = list.stream().collect(Collectors.toMap(Function.identity(), e -> 0));
        this.withPlank = withPlank;
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
        return this.changePosition(move);
    }

    private AbstractMove changePosition(AbstractMove move) {
        position.put(move.getPlayer(), move.getNewSpace());
        if(this.withPlank) {
            Optional<String> otherPlayer = this.position.entrySet()
                    .stream()
                    .filter(e -> !e.getKey().equals(move.getPlayer()))
                    .filter(e -> e.getValue() == move.getNewSpace())
                .map(e -> e.getKey())
            .findFirst();

            if(otherPlayer.isPresent()){
                this.position.put(otherPlayer.get(), move.getCurrentSpace());
                String plankMove = this.messageSource.getMessage("match.plank", move.getNewSpace(), otherPlayer.get(), move.getCurrentSpace() == 0 ? "Start" : move.getCurrentSpace());
                move.setPlankMove(plankMove);
            }
        }
        return move;
    }

    public Map<String, Integer> getPosition() {
        return new HashMap<>(this.position);
    }
}
