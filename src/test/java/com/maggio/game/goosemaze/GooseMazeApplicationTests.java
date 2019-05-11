package com.maggio.game.goosemaze;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.shell.result.DefaultResultHandler;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
		InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
		ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class GooseMazeApplicationTests {

	@Autowired
	private Shell shell;

	@Test
	public void contextLoads() {
		Object help = shell.evaluate(() -> "help");
		assertFalse(Shell.NO_INPUT.equals(help));
		assertNotNull(help);
		System.out.println(help);
	}

	@Test
	public void addPlayer() {
		Object help = shell.evaluate(() -> "ap c");
		assertFalse(Shell.NO_INPUT.equals(help));
		assertNotNull(help);
		System.out.println(help);
	}

}
