package com.maggio.game.goosemaze.command;

import com.maggio.game.goosemaze.match.Match;
import com.maggio.game.goosemaze.config.MessageConfiguration;
import com.maggio.game.goosemaze.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.standard.*;

import javax.validation.ValidationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

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

    @ShellMethod(value = "status")
    public String status() {
        try {
            if(this.match == null){
                throw new ValidationException(messageSource.getMessage("match.no.start"));
            }
            StringBuilder sb = new StringBuilder();
            this.match.getPosition().forEach((k ,v) -> {sb.append(k).append(": ").append(v).append("\n");});
            return sb.toString();
        } catch (ValidationException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "move")
    public String move(@NotEmpty String player, @Size(min = 2, max = 2) @ShellOption(defaultValue = "") List<Integer> rolls) {
        LOGGER.debug("move {} {}", player, rolls);
        try {
            validateInput(player, rolls);
            return this.match.move(player, rolls).getMessage();
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

        rolls.stream().forEach((integer -> {
            if(integer > 6){
                throw new ValidationException(messageSource.getMessage("rolls.not.valid", integer));
            }
        }));
    }
}