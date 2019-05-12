package com.maggio.game.goosemaze.match.move;

public class WinMove extends AbstractMove {

    public WinMove(String player, Integer firstRoll, Integer secondRoll, int currentSpace, int newSpace, String message) {
        super(player, firstRoll, secondRoll, currentSpace, newSpace, message);
    }
}
