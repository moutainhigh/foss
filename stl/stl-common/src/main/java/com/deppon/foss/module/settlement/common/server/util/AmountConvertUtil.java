package com.deppon.foss.module.settlement.common.server.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;

public class AmountConvertUtil {

	/**
	 * 显示小数点位数
	 */
	private static final int NUMBER_OF_DECIMAL = 2;

	public static void main(String[] args) {
		System.out.println(digitUppercase(0.83d));
		BigDecimal amount = new BigDecimal("23.7812");
		System.out.println(amountUppercase(amount));
		System.out.println(amountCurrency(amount));
	}

	/**
	 * 转换成金额显示
	 * 
	 * @param amount
	 * @return
	 */
	public static String amountCurrency(BigDecimal amount) {

		if (amount == null) {
			return null;
		}

		return digitCurrency(amount, NUMBER_OF_DECIMAL);
	}

	/**
	 * 转换成金额大写
	 * 
	 * @param amount
	 * @return
	 */
	public static String amountUppercase(BigDecimal amount) {

		if (amount == null) {
			return null;
		}

		return digitUppercase(amount.doubleValue());
	}

	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
	 */
	private static String digitUppercase(double n) {
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

		String head = n < 0 ? "负" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(m(m(n, BigDecimal.TEN),
					(int) Math.pow(SettlementReportNumber.TEN, i))) % SettlementReportNumber.TEN)] + fraction[i]).replaceAll(
					"(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % SettlementReportNumber.TEN] + unit[1][j] + p;
				integerPart = integerPart / SettlementReportNumber.TEN;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i]
					+ s;
		}
		return head
				+ s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "")
						.replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}

	/**
	 *  * 金额格式化  * @param s 金额  * @param len 小数位数  * @return 格式后的金额  
	 */
	private static String digitCurrency(BigDecimal amount, int len) {
		NumberFormat format = null;
		double num = amount.doubleValue();
		if (len == 0) {
			format = new DecimalFormat("###,###");
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("###,###.");
			for (int i = 0; i < len; i++) {
				sb.append("0");
			}
			format = new DecimalFormat(sb.toString());
		}
		return format.format(num);
	}

	private static double m(double a, BigDecimal b) {
		return new BigDecimal(String.valueOf(a)).multiply(b).doubleValue();
	}

	private static double m(double a, int b) {
		return new BigDecimal(String.valueOf(a)).multiply(
				new BigDecimal(String.valueOf(b))).doubleValue();
	}

}
