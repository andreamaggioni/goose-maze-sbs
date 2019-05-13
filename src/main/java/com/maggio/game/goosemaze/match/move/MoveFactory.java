package com.maggio.game.goosemaze.match.move;

import com.maggio.game.goosemaze.config.MessageConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MoveFactory {

    @Autowired
    protected MessageConfiguration.CustomMessageSource messageSource;

    @Value("${match.win-cell}") private Integer winCell;

    @Value("${match.bridgeSpace}") private Integer bridgeSpace;

    @Value("${match.bridgeSpaceEnd}") private Integer bridgeSpaceEnd;

    @Value("${march.gooseSpaces}") private Set<Integer> gooseSpaces;

    @Value("${match.startCellName:Start}") private String startCellName;

    public WinMove winMove(String player, int firstRoll, int secondRoll, int currentSpace){
        String message = messageSource.getMessage("match.win", player, firstRoll, secondRoll, this.getCurrentSpace(currentSpace), winCell);
        return new WinMove(player, firstRoll, secondRoll, currentSpace, winCell, message);
    }

    public BridgeMove bridgeMove(String player, int firstRoll, int secondRoll, int currentSpace) {
        String message = messageSource.getMessage("match.bridge", player, firstRoll, secondRoll, this.getCurrentSpace(currentSpace), bridgeSpaceEnd);
        return new BridgeMove(player, firstRoll, secondRoll, currentSpace, bridgeSpaceEnd, message);
    }

    public GooseMove gooseMove(String player, int firstRoll, int secondRoll, int currentSpace){
        int newSpace = firstRoll + secondRoll + currentSpace;

        StringBuilder sb = new StringBuilder();
        sb.append(messageSource.getMessage("match.goose", player, firstRoll, secondRoll, this.getCurrentSpace(currentSpace), newSpace)).append(" ");

        while(gooseSpaces.contains(newSpace)){
            newSpace = newSpace + firstRoll + secondRoll;
            sb.append(messageSource.getMessage("match.goose.jump", player, newSpace));
            sb.append(" ");
        }

        return new GooseMove(player, firstRoll, secondRoll, currentSpace, newSpace, sb.toString());
    }

    public Move move(String player, int firstRoll, int secondRoll, int currentSpace){
        int newSpace = firstRoll + secondRoll + currentSpace;
        String message = messageSource.getMessage("match.move", player, firstRoll, secondRoll, this.getCurrentSpace(currentSpace), newSpace);
        return new Move(player, firstRoll, secondRoll, currentSpace, newSpace, message);
    }

    public BounceMove bounceMove(String player, Integer firstRoll, Integer secondRoll, int currentSpace) {
        int bounceSpace = winCell - (currentSpace + firstRoll + secondRoll - winCell);
        String message = messageSource.getMessage("match.bounce", player, firstRoll, secondRoll, this.getCurrentSpace(currentSpace), winCell, bounceSpace);
        return new BounceMove(player, firstRoll, secondRoll, currentSpace, bounceSpace, message);
    }

    private Object getCurrentSpace(int currentSpace) {
        if(currentSpace == 0){
            return startCellName;
        }
        return currentSpace;
    }
}
