package com.mjc.school.utility;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleReader {

	private ConsoleReader() {
		// empty. Hides default public constructor
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static String readText(final String message, final int minLength, final int maxLength) {
		System.out.println(message);
		String result = null;
		do {
			System.out.print(">");
			String tmp = scanner.nextLine();
			if (tmp.length() < minLength || tmp.length() > maxLength) {
				System.out.println(
					String.format("Text length must be between %d and %d. Please try again:", minLength, maxLength));
				System.out.print(">");
				continue;
			}
			result = tmp;
		} while (result == null);
		return result;
	}

	public static Long readPositiveLong(final String message) {
		System.out.println(message);
		Long result = null;
		do {
			System.out.print(">");
			try {
				result = scanner.nextLong();
				if (result < 1) {
					System.out.println("Number must be positive. Please try again:");
					result = null;
				}
			} catch (InputMismatchException e) {
				System.out.println("Input is not a number. Please try again:");
			}
			scanner.nextLine();
		} while (result == null);
		return result;
	}
}