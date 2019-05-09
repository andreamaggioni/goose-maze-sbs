package com.maggio.game.goosemaze.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Service
public class PlayerService {

    private Set<String> players = new HashSet<>();

    public boolean hasPlayers() {
        return !players.isEmpty();
    }

    public Set<String> list() {
        return new TreeSet<>(this.players);
    }

    public boolean add(String player) {
        if(players.contains(player)){
            return false;
        }
        players.add(player);
        return true;
    }

    public boolean exists(String player) {
        return players.contains(player);
    }
}
