package com.maggio.game.goosemaze.match.move;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class BridgeMove extends AbstractMove {

    @Value("${match.bridgeSpaceEnd}") private Integer bridgeSpaceEnd;

    public BridgeMove(String player, Integer firstRoll, Integer secondRoll, int currentSpace) {
        super(player, firstRoll, secondRoll, currentSpace);
    }

    @Override
    public int getNewSpace() {
        return bridgeSpaceEnd;
    }

    @Override
    public String getMessage() {
        return messageSource.getMessage("match.bridge",
                getPlayer(),
                getFirstRoll(),
                getSecondRoll(),
                getCurrentSpace(),
                getNewSpace());
    }
}