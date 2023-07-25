package com.mjc.school.conversation;

import com.mjc.school.controller.Operation;
import com.mjc.school.controller.command.Command;
import com.mjc.school.controller.utility.ConsoleReader;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Conversation {

	private static final String MAIN_MENU_HEADER = "Main menu:";
	private static final String ENTER_COMMAND_MESSAGE = "Please enter command number...";
	private static final String WRONG_COMMAND_MESSAGE = "No such command, please try again";
	private static final String ERROR_MESSAGE = "We've got an error: %s. Please try something else";
	private Map<String, Command> commands;

	public Conversation(Map<String, Command> commands) {
		this.commands = commands;
	}

	public void run() {
		boolean isFinished = false;
		while (!isFinished) {
			printMainMenu();
			int command = ConsoleReader.readPositiveLong(ENTER_COMMAND_MESSAGE).intValue();
			try {
				if (command < Operation.values().length) {
					commands.get(String.valueOf(command)).execute();
				} else if (command == Operation.values().length) {
					isFinished = true;
				} else {
					System.out.println(WRONG_COMMAND_MESSAGE);
				}
			} catch (Exception e) {
				System.out.println(String.format(ERROR_MESSAGE, e.getMessage()));
				e.printStackTrace();
			}
		}
	}

	private void printMainMenu() {
		System.out.println(MAIN_MENU_HEADER);
		for (Operation operation : Operation.values()) {
			System.out.println(operation.getNumber() + ". " + operation.getOperationName());
		}
	}
}