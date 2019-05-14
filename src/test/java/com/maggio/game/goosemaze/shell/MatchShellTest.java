package com.maggio.game.goosemaze.shell;

import com.maggio.game.goosemaze.service.PlayerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
		InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
		ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class MatchShellTest {

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
		assertNotEquals(Shell.NO_INPUT, help);
		assertNotNull(help);
	}

	@Test
	public void startStop() {
		String output = (String) shell.evaluate(() -> "start match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("Game started!"));

		output = (String) shell.evaluate(() -> "stop match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("The game is over!"));
	}

	@Test
	public void playerNotPresent() {
		String output = (String) shell.evaluate(() -> "start match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("Game started!"));

		output = (String) shell.evaluate(() -> "move p3");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("Player p3 not found!"));

		output = (String) shell.evaluate(() -> "stop match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("The game is over!"));
	}

	@Test
	public void rollsValidation() {
		String output = (String) shell.evaluate(() -> "start match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("Game started!"));

		output = (String) shell.evaluate(() -> "move p2 2,2,2");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("You can not roll more then 2 dice!"));

		output = (String) shell.evaluate(() -> "move p1 12,12");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("12 is not a valid roll!"));

		output = (String) shell.evaluate(() -> "move p1 2, 3");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("12 is not a valid roll!"));

		output = (String) shell.evaluate(() -> "stop match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("The game is over!"));
	}


	@Test
	public void bridgeMove() {
		String output = (String) shell.evaluate(() -> "start match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("Game started!"));

		output = (String) shell.evaluate(() -> "move p1 3,3");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("The Bridge."));

		output = (String) shell.evaluate(() -> "stop match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("The game is over!"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void rollsSpaceValidation() {
		String output = (String) shell.evaluate(() -> "start match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("Game started!"));

		try {
			IllegalArgumentException exception = (IllegalArgumentException) shell.evaluate(() -> "move p1 2, 3");
			throw  exception;
		} catch (IllegalArgumentException e) {
			output = (String) shell.evaluate(() -> "stop match");
			assertNotEquals(Shell.NO_INPUT, output);
			assertNotNull(output);
			assertTrue(output.contains("The game is over!"));
			throw e;
		}
	}

	@Test
	public void gooseMove() {
		String output = (String) shell.evaluate(() -> "start match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("Game started!"));

		output = (String) shell.evaluate(() -> "move p1 3,3");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("The Bridge."));

		output = (String) shell.evaluate(() -> "move p2 5,5");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("Start to 10"));

		output = (String) shell.evaluate(() -> "move p1");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);

		output = (String) shell.evaluate(() -> "move p2 2,2");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("The Goose"));

		output = (String) shell.evaluate(() -> "stop match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("The game is over!"));
	}

	@Test
	public void bounceMove() {
		String output = (String) shell.evaluate(() -> "start match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("Game started!"));

		output = (String) shell.evaluate(() -> "move p1 6,6");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("Start to 12"));

		output = (String) shell.evaluate(() -> "move p1 6,6");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("12 to 24"));

		output = (String) shell.evaluate(() -> "move p1 6,6");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("24 to 36"));

		output = (String) shell.evaluate(() -> "move p1 6,6");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("36 to 48"));

		output = (String) shell.evaluate(() -> "move p1 6,6");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("48 to 60"));

		output = (String) shell.evaluate(() -> "move p1 6,6");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("p1 bounces! p1 returns to 54."));

		output = (String) shell.evaluate(() -> "stop match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("The game is over!"));
	}

	@Test
	public void plankMove() {
		String output = (String) shell.evaluate(() -> "start match --with-plank");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("Game started!"));

		output = (String) shell.evaluate(() -> "move p1 6,6");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("Start to 12"));

		output = (String) shell.evaluate(() -> "move p2 6,6");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("On 12 there is p1, who returns to Start."));

		output = (String) shell.evaluate(() -> "stop match");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("The game is over!"));
	}
}