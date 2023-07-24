package com.mjc.school;

import com.mjc.school.controller.conversation.Conversation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

		Conversation conversation = context.getBean(Conversation.class);
		conversation.run();
	}
}