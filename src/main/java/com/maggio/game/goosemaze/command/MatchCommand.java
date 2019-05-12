package com.maggio.game.goosemaze.command;

import com.maggio.game.goosemaze.match.Match;
import com.maggio.game.goosemaze.config.MessageConfiguration;
import com.maggio.game.goosemaze.match.move.AbstractMove;
import com.maggio.game.goosemaze.match.move.WinMove;
import com.maggio.game.goosemaze.service.PlayerService;
import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.standard.*;
import org.springframework.util.CollectionUtils;

import javax.validation.ValidationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.join;

@ShellComponent
@ShellCommandGroup("match")
public class MatchCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchCommand.class);

    @Autowired private PlayerService playerService;

    @Autowired private MessageConfiguration.CustomMessageSource messageSource;

    @Value("${match.min-players:2}") private Integer minPlayers;

    @Autowired private BeanFactory beanFactory;

    private Match match;

    @ShellMethod(value = "start match", key = {"start match", "sm"})
    public String startMatch(boolean withPlank) {
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
        this.match = beanFactory.getBean(Match.class, players, withPlank);
        return messageSource.getMessage("match.started");
    }

    @ShellMethod(value = "stop match", key = "stop match")
    public String stopMatch() {
        this.match = null;
        return messageSource.getMessage("match.stop");
    }

    @ShellMethod(value = "status")
    public String status() {
        try {
            if(this.match == null){
                throw new ValidationException(messageSource.getMessage("match.no.start"));
            }
            return this.match.getPosition()
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue((v1, v2)-> -1 * v1.compareTo(v2)))
                    .map(e -> join(": ", e.getKey(), Integer.toString(e.getValue())))
                    .collect(Collectors.joining("\n"));
        } catch (ValidationException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "move")
    public String move(@NotEmpty String player, @ShellOption(defaultValue = "") List<Integer> rolls) {
        LOGGER.debug("move {} {}", player, rolls);
        if(CollectionUtils.isEmpty(rolls)){
            rolls.add(RandomUtils.nextInt(1,6));
            rolls.add(RandomUtils.nextInt(1,6));
            LOGGER.debug("move {} {}, rolls random generated", player, rolls);
        }
        try {
            validateInput(player, rolls);
            AbstractMove move = this.match.move(player, rolls);
            if(move instanceof WinMove){
                this.match = null;
            }
            return move.getMessage();
        } catch (ValidationException e) {
            return e.getMessage();
        }
    }

    public void validateInput(String player, List<Integer> rolls){
        if(this.match == null){
            throw new ValidationException(messageSource.getMessage("match.no.start"));
        }

        if(!playerService.exists(player)){
            throw new ValidationException(messageSource.getMessage("player.not.found", player));
        }

        if(rolls.size() > 2 || rolls.size() < 0){
            throw new ValidationException(messageSource.getMessage("rolls.size"));
        }

        rolls.stream().forEach((integer -> {
            if(integer > 6 || integer < 1){
                throw new ValidationException(messageSource.getMessage("rolls.not.valid", integer));
            }
        }));
    }
}