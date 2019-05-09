package com.maggio.game.goosemaze.command;

import com.maggio.game.goosemaze.match.Match;
import com.maggio.game.goosemaze.config.MessageConfiguration;
import com.maggio.game.goosemaze.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.MethodTarget;
import org.springframework.shell.standard.*;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Completion;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("match")
public class MatchCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchCommand.class);

    @Autowired private PlayerService playerService;

    @Autowired private MessageConfiguration.CustomMessageSource messageSource;

    @Value("${match.min-players:2}") private Integer minPlayers;

    @Autowired private BeanFactory beanFactory;

    private Match match;

    @ShellMethod(value = "start match", key = "start match")
    public String startMatch() {
        if(!playerService.hasPlayers()){
            return messageSource.getMessage("match.no.players");
        }
        if(this.match != null){
            return messageSource.getMessage("match.already.start");
        }
        Set<String> players = playerService.list();
        if(players.size() < minPlayers){
            return messageSource.getMessage("match.need.players", minPlayers);
        }
        this.match = beanFactory.getBean(Match.class, players);
        return messageSource.getMessage("match.started");
    }

    @ShellMethod(value = "stop match", key = "stop match")
    public String stopMatch() {
        this.match = null;
        return messageSource.getMessage("match.stop");
    }

    @ShellMethod(value = "move")
    public String move(String player, @ShellOption(arity = 2, defaultValue = "") List<Integer> rolls) {
        LOGGER.info("move {} {}", player, rolls);
        if(this.match == null){
            return messageSource.getMessage("match.no.start");
        }
        if(playerService.exists(player)){
            return messageSource.getMessage("player.not.found", player);
        }

        int steps = rolls.stream().mapToInt(e -> e).sum();
        this.match.move(player, steps);
        return messageSource.getMessage("match.stop");
    }
}