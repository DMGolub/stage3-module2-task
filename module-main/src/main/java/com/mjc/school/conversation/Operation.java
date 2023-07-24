package com.mjc.school.conversation;

public enum Operation {
	CREATE_NEWS(1, "Create news"),
	CREATE_AUTHOR(2, "Create author"),
	GET_ALL_NEWS(3, "Get all news"),
	GET_ALL_AUTHORS(4, "Get all authors"),
	GET_NEWS_BY_ID(5, "Get news by id"),
	GET_AUTHOR_BY_ID(6, "Get author by id"),
	UPDATE_NEWS_BY_ID(7, "Update news by id"),
	UPDATE_AUTHOR_BY_ID(8, "Update author by id"),
	DELETE_NEWS_BY_ID(9, "Delete news by id"),
	DELETE_AUTHOR_BY_ID(10, "Delete author by id"),
	QUIT_PROGRAM(11, "Quit program");

	private Operation(final Integer operationNumber, final String operation) {
		this.operationNumber = operationNumber;
		this.operation = operation;
	}

	private final Integer operationNumber;
	private final String operation;

	public Integer getNumber() {
		return operationNumber;
	}

	public String getOperation() {
		return operation;
	}
}