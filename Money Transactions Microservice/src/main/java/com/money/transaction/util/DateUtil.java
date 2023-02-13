package com.money.transaction.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.money.transaction.constants.DateConstant;

public class DateUtil {

	public static String getCurrentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstant.DATE_FORMAT);
		return dateFormat.format(new Date());
	}

}
