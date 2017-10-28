package org.stempeluhr.util;

import java.util.Date;

public class DateHelper {

	public static double getDifferenceInHours(Date d1, Date d2) {
		double diff = d2.getTime() - d1.getTime();
		diff /= (1000 * 60 * 60);

		return Math.round(diff, 2);
	}
}
