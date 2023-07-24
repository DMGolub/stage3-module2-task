package com.mjc.school.command;

import com.mjc.school.controller.BaseController;

public abstract class Command<T, R, K> {

	protected final BaseController<T, R, K> controller;

	protected Command(final BaseController<T, R, K> controller) {
		this.controller = controller;
	}

	public abstract void execute();
}