package com.goods.tools.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {


	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}
}
