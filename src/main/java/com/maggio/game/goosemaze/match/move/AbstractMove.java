package com.maggio.game.goosemaze.match.move;

public abstract class AbstractMove {

    private String player;

    private Integer firstRoll;

    private Integer secondRoll;

    private int currentSpace;

    private int newSpace;

    private String message;

    private String plankMove = "";

    public AbstractMove(String player, Integer firstRoll, Integer secondRoll, int currentSpace, int newSpace, String message) {
        this.player = player;
        this.firstRoll = firstRoll;
        this.secondRoll = secondRoll;
        this.currentSpace = currentSpace;
        this.newSpace = newSpace;
        this.message = message;
    }

    public String getPlayer() {
        return player;
    }

    public Integer getFirstRoll() {
        return firstRoll;
    }

    public Integer getSecondRoll() {
        return secondRoll;
    }

    public int getCurrentSpace() {
        return currentSpace;
    }

    public int getNewSpace() {
        return newSpace;
    }

    public String getMessage() {
        return message + this.plankMove;
    }

    public void setPlankMove(String plankMove){
        this.plankMove = plankMove;
    }
}