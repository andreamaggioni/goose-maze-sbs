package com.maggio.game.goosemaze.command;

import com.maggio.game.goosemaze.config.MessageConfiguration;
import com.maggio.game.goosemaze.service.PlayerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.validation.constraints.NotEmpty;

@ShellComponent
public class PlayerCommand {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MessageConfiguration.CustomMessageSource messageSource;

    @ShellMethod(value = "add player", group = "player", key = {"add player", "ap"})
    public @NotEmpty String addPlayer(@NotEmpty(message = "notempty.player") String player) {
        if(!playerService.add(player)){
            return messageSource.getMessage("player.already.exist", player);
        }
        return messageSource.getMessage("player.added", StringUtils.join(playerService.list(), ", "));
    }
}