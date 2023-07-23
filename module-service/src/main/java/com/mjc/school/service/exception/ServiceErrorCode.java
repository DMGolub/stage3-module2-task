package com.mjc.school.service.exception;

public enum ServiceErrorCode {
	POSITIVE_CONSTRAINT_VIOLATION(Constants.ERROR_000001, "%s must be positive. %s value = %s"),
	NOT_NULL_CONSTRAINT_VIOLATION(Constants.ERROR_000002, "%s can not be null"),
	RANGE_CONSTRAINT_VIOLATION(Constants.ERROR_000003, "%s value must be from %d to %d"),
	ENTITY_NOT_FOUND_BY_ID(Constants.ERROR_000004, "Can not find %s by id: %s");

	private final String errorCode;
	private final String errorMessage;

	ServiceErrorCode(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getCode() {
		return errorCode;
	}

	public String getMessage() {
		return errorMessage;
	}

	private static class Constants {
		private static final String ERROR_000001 = "000001";
		private static final String ERROR_000002 = "000002";
		private static final String ERROR_000003 = "000003";
		private static final String ERROR_000004 = "000004";
	}
}