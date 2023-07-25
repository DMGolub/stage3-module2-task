package com.mjc.school.controller;

import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.command.Command;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@Component
public class RequestHandler {

	private final BaseController[] controllers;

	public RequestHandler(BaseController[] controllers) {
		this.controllers = controllers;
	}

	public Object handleRequest(final Command command, Object... params)
			throws InvocationTargetException, IllegalAccessException {

		String commandBody = command.getClass().getAnnotation(Component.class).value();
		var controller = Arrays.stream(controllers)
			.filter(c -> Arrays.stream(c.getClass().getDeclaredMethods())
				.anyMatch(m -> m.getAnnotation(CommandHandler.class).value().equals(commandBody)))
			.findFirst().get();

		var method = Arrays.stream(controller.getClass().getDeclaredMethods())
			.filter(m -> m.isAnnotationPresent(CommandHandler.class))
			.filter(m -> m.getAnnotation(CommandHandler.class).value().equals(commandBody))
			.toList().get(0);

		return (params.length > 0) ? method.invoke(controller, params[0]) : method.invoke(controller);
	}
}