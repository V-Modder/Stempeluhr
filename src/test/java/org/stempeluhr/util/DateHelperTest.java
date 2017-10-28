package org.stempeluhr.util;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateHelperTest {

	@Test
	public void diffTest() {
		Date d1 = new Date();
		d1.setTime(1508081423019L);
		Date d2 = new Date();
		d2.setTime(1508110223019L);

		double diff = DateHelper.getDifferenceInHours(d1, d2);
		Assert.assertEquals(diff, 8, 0.009);
	}
}
