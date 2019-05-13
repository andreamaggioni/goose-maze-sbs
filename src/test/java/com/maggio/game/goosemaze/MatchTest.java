package com.maggio.game.goosemaze;

import com.maggio.game.goosemaze.service.PlayerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
		InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
		ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class MatchTest {

	@Autowired private Shell shell;

	@Autowired private PlayerService playerService;

	@Before
	public void before(){
		playerService.add("p1");
		playerService.add("p2");
	}

	@After
	public void after(){
		playerService.clear();
	}

	@Test
	public void contextLoads() {
		Object help = shell.evaluate(() -> "help");
		assertFalse(Shell.NO_INPUT.equals(help));
		assertNotNull(help);
	}

	@Test
	public void startStop() {
		String output = (String) shell.evaluate(() -> "start match");
		assertFalse(Shell.NO_INPUT.equals(output));
		assertNotNull(output);
		assertTrue(output.contains("Game started!"));

		output = (String) shell.evaluate(() -> "stop match");
		assertFalse(Shell.NO_INPUT.equals(output));
		assertNotNull(output);
		assertTrue(output.contains("The game is over!"));
	}

}
