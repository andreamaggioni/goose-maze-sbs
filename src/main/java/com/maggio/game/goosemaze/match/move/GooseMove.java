package com.maggio.game.goosemaze.match.move;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class GooseMove extends AbstractMove {

    @Value("${march.gooseSpaces}") private List<Integer> gooseSpaces;

    public GooseMove(String player, Integer firstRoll, Integer secondRoll, int currentSpace) {
        super(player, firstRoll, secondRoll, currentSpace);
    }

    @Override
    public int getNewSpace() {
        int space = super.getNewSpace();
        while(gooseSpaces.contains(space)){
            space = space + this.getFirstRoll() + this.getSecondRoll();
        }
        return space;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(messageSource.getMessage("match.goose",
                getPlayer(),
                getFirstRoll(),
                getSecondRoll(),
                getCurrentSpace(),
                super.getNewSpace()));

        int space = super.getNewSpace();
        while(gooseSpaces.contains(space)){
            space = space + this.getFirstRoll() + this.getSecondRoll();
            sb.append(messageSource.getMessage("match.goose.jump",
                    getPlayer(),
                    space));
            sb.append(" ");
        }

        return sb.toString();
    }
}