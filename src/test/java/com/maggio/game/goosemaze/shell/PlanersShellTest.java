package com.maggio.game.goosemaze.shell;

import com.maggio.game.goosemaze.service.PlayerService;
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

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
		InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
		ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class PlanersShellTest {

	@Autowired private Shell shell;

	@Autowired private PlayerService playerService;

	@Before
	public void before(){
		playerService.clear();
	}

	@Test
	public void contextLoads() {
		Object help = shell.evaluate(() -> "help");
		assertNotEquals(Shell.NO_INPUT, help);
		assertNotNull(help);
	}

	@Test
	public void addPlayer() {
		String output = (String) shell.evaluate(() -> "add player c");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("players: c"));

		output = (String) shell.evaluate(() -> "add player c1");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("players: c, c1"));
	}

	@Test
	public void addDupPlayer() {
		String output = (String) shell.evaluate(() -> "add player c");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("players: c"));

		output = (String) shell.evaluate(() -> "add player c");
		assertNotEquals(Shell.NO_INPUT, output);
		assertNotNull(output);
		assertTrue(output.contains("c: already existing player"));
	}
}