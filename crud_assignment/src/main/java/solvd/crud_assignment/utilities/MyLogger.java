package solvd.crud_assignment.utilities;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger extends Logger {
	public MyLogger(String name) {
		super(name, null);

		this.setLevel(Level.ALL);

		ConsoleHandler ch = new ConsoleHandler();
		ch.setFormatter(new SimpleFormatter());
		ch.setLevel(Level.INFO);

		addHandler(ch);
	}
}
