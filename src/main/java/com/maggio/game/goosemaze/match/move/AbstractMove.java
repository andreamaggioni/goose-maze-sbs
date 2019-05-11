package com.maggio.game.goosemaze.match.move;

import com.maggio.game.goosemaze.config.MessageConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractMove {

    @Autowired protected MessageConfiguration.CustomMessageSource messageSource;

    private String player;

    private Integer firstRoll;

    private Integer secondRoll;

    private int currentSpace;

    private int newSpace;

    public AbstractMove(String player, Integer firstRoll, Integer secondRoll, int currentSpace) {
        this.player = player;
        this.firstRoll = firstRoll;
        this.secondRoll = secondRoll;
        this.currentSpace = currentSpace;
        this.newSpace = currentSpace + firstRoll + secondRoll;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Integer getFirstRoll() {
        return firstRoll;
    }

    public void setFirstRoll(Integer firstRoll) {
        this.firstRoll = firstRoll;
    }

    public Integer getSecondRoll() {
        return secondRoll;
    }

    public void setSecondRoll(Integer secondRoll) {
        this.secondRoll = secondRoll;
    }

    public int getCurrentSpace() {
        return currentSpace;
    }

    public void setCurrentSpace(int currentSpace) {
        this.currentSpace = currentSpace;
    }

    public int getNewSpace() {
        return newSpace;
    }

    public void setNewSpace(int newSpace) {
        this.newSpace = newSpace;
    }

    public abstract String getMessage();

    @Override
    public String toString() {
        return "Move{" +
                "player='" + player + '\'' +
                ", firstRoll=" + firstRoll +
                ", secondRoll=" + secondRoll +
                ", currentSpace=" + currentSpace +
                ", newSpace=" + newSpace +
                '}';
    }
}
