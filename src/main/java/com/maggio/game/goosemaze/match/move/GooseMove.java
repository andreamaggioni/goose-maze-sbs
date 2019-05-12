package com.maggio.game.goosemaze.match.move;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

public class GooseMove extends AbstractMove {

    public GooseMove(String player, Integer firstRoll, Integer secondRoll, int currentSpace, int newSpace, String message) {
        super(player, firstRoll, secondRoll, currentSpace, newSpace, message);
    }
}