package com.maggio.game.goosemaze;

import com.maggio.game.goosemaze.match.Match;
import com.maggio.game.goosemaze.match.move.AbstractMove;
import com.maggio.game.goosemaze.match.move.WinMove;
import com.maggio.game.goosemaze.service.PlayerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
		InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
		ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class MatchTest {

	@Autowired private BeanFactory beanFactory;

	private Match match = null;

	private static final Integer MAX_TRY = 100;

	private Random random = new Random();

	@Value("${test.players: p1,p2}")
	private Set<String> players;

	@Before
	public void before(){
		match = this.beanFactory.getBean(Match.class, players, false);
	}

	@Test
	public void run() {
		int count = 0;
		while (count < MAX_TRY){
			count++;
			for(String player : players){
				List<Integer> rolls = random.ints(2, 1,6)
						.boxed()
						.collect(Collectors.toList());
				AbstractMove move = this.match.move(player, rolls);
				System.out.println(move.getClass().getSimpleName() + ": " + move.getMessage());
				if(move instanceof WinMove){
					System.out.println("GAME END!!!");
					return;
				}
			}
		}
	}
}