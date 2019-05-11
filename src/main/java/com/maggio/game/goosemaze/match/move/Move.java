package com.maggio.game.goosemaze.match.move;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Move extends AbstractMove {

    public Move(String player, Integer firstRoll, Integer secondRoll, int currentSpace) {
        super(player, firstRoll, secondRoll, currentSpace);
    }

    @Override
    public int getNewSpace() {
        return getCurrentSpace() + getFirstRoll() + getSecondRoll();
    }

    @Override
    public String getMessage() {
        return messageSource.getMessage("match.move",
                getPlayer(),
                getFirstRoll(),
                getSecondRoll(),
                getCurrentSpace(),
                getNewSpace());
    }
}
