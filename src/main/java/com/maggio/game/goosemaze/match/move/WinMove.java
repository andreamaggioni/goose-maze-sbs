package com.maggio.game.goosemaze.match.move;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class WinMove extends AbstractMove {

    @Value("${match.win-cell}") private Integer winCell;

    public WinMove(String player, Integer firstRoll, Integer secondRoll, int currentSpace) {
        super(player, firstRoll, secondRoll, currentSpace);
    }

    @Override
    public int getNewSpace() {
        return winCell;
    }

    @Override
    public String getMessage() {
        return messageSource.getMessage("match.win",
                getPlayer(),
                getFirstRoll(),
                getSecondRoll(),
                getCurrentSpace(),
                getNewSpace());
    }
}
