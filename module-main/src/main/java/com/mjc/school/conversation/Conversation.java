package com.mjc.school.conversation;

import com.mjc.school.command.impl.AuthorCreateCommand;
import com.mjc.school.command.impl.AuthorDeleteCommand;
import com.mjc.school.command.impl.AuthorGetAllCommand;
import com.mjc.school.command.impl.AuthorGetByIdCommand;
import com.mjc.school.command.impl.AuthorUpdateCommand;
import com.mjc.school.command.Command;
import com.mjc.school.command.impl.NewsCreateCommand;
import com.mjc.school.command.impl.NewsDeleteCommand;
import com.mjc.school.command.impl.NewsGetAllCommand;
import com.mjc.school.command.impl.NewsGetByIdCommand;
import com.mjc.school.command.impl.NewsUpdateCommand;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.utility.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

import static com.mjc.school.conversation.Operation.CREATE_AUTHOR;
import static com.mjc.school.conversation.Operation.CREATE_NEWS;
import static com.mjc.school.conversation.Operation.DELETE_AUTHOR_BY_ID;
import static com.mjc.school.conversation.Operation.DELETE_NEWS_BY_ID;
import static com.mjc.school.conversation.Operation.GET_ALL_AUTHORS;
import static com.mjc.school.conversation.Operation.GET_ALL_NEWS;
import static com.mjc.school.conversation.Operation.GET_AUTHOR_BY_ID;
import static com.mjc.school.conversation.Operation.GET_NEWS_BY_ID;
import static com.mjc.school.conversation.Operation.UPDATE_AUTHOR_BY_ID;
import static com.mjc.school.conversation.Operation.UPDATE_NEWS_BY_ID;

@Component
public class Conversation {

	private static final String MAIN_MENU_HEADER = "Main menu:";
	private static final String ENTER_COMMAND_MESSAGE = "Please enter command number...";
	private static final String NO_COMMAND_MESSAGE = "No such command, please try again";
	private static final String ERROR_MESSAGE = "We've got an error. Please try something else";

	private final BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController;
	private final BaseController<NewsRequestDto, NewsResponseDto, Long> newsController;
	private Map<Integer, Command> commands;

	@Autowired
	public Conversation(
		final BaseController<AuthorRequestDto, AuthorResponseDto, Long> authorController,
		final BaseController<NewsRequestDto, NewsResponseDto, Long> newsController
	) {
		this.newsController = newsController;
		this.authorController = authorController;
	}

	@PostConstruct
	private void init() {
		commands = Map.of(
			CREATE_NEWS.getNumber(), new NewsCreateCommand(newsController),				// 1
			CREATE_AUTHOR.getNumber(), new AuthorCreateCommand(authorController),		// 2
			GET_ALL_NEWS.getNumber(), new NewsGetAllCommand(newsController),			// 3
			GET_ALL_AUTHORS.getNumber(), new AuthorGetAllCommand(authorController),		// 4
			GET_NEWS_BY_ID.getNumber(), new NewsGetByIdCommand(newsController),			// 5
			GET_AUTHOR_BY_ID.getNumber(), new AuthorGetByIdCommand(authorController), 	// 6
			UPDATE_NEWS_BY_ID.getNumber(), new NewsUpdateCommand(newsController),		// 7
			UPDATE_AUTHOR_BY_ID.getNumber(), new AuthorUpdateCommand(authorController), // 8
			DELETE_NEWS_BY_ID.getNumber(), new NewsDeleteCommand(newsController),		// 9
			DELETE_AUTHOR_BY_ID.getNumber(), new AuthorDeleteCommand(authorController)	// 10
		);
	}

	public void run() {
		boolean isFinished = false;
		while (!isFinished) {
			printMainMenu();
			int command = ConsoleReader.readPositiveLong(ENTER_COMMAND_MESSAGE).intValue();
			try {
				switch (command) {
					case 1 -> commands.get(1).execute();
					case 2 -> commands.get(2).execute();
					case 3 -> commands.get(3).execute();
					case 4 -> commands.get(4).execute();
					case 5 -> commands.get(5).execute();
					case 6 -> commands.get(6).execute();
					case 7 -> commands.get(7).execute();
					case 8 -> commands.get(8).execute();
					case 9 -> commands.get(9).execute();
					case 10 -> commands.get(10).execute();
					case 11 -> isFinished = true;
					default -> System.out.println(NO_COMMAND_MESSAGE);
				}
			} catch (Exception e) {
				System.out.println(ERROR_MESSAGE);
			}
		}
	}

	private void printMainMenu() {
		System.out.println(MAIN_MENU_HEADER);
		for (Operation operation : Operation.values()) {
			System.out.println(operation.getNumber() + ". " + operation.getOperation());
		}
	}
}