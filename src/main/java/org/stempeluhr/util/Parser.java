package org.stempeluhr.util;

public class Parser {

	public static long stringToLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException e) {
			return 0L;
		}
	}
}
