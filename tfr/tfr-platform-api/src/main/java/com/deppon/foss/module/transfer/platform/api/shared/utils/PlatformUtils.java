package com.deppon.foss.module.transfer.platform.api.shared.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;

public class PlatformUtils {

	private static List<String> getFieldNames(Object obj) {

		if (obj == null) {
			return null;
		}

		Field[] fields = obj.getClass().getDeclaredFields();
		List<String> fieldNames = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			if (!"serialVersionUID".equals(fields[i].getName())) {
				fieldNames.add(fields[i].getName());
			}
		}
		return fieldNames;
	}

	private static boolean checkFiledValueIsNull(String fieldName, Object obj) {

		if (StringUtils.isBlank(fieldName) || obj == null) {
			throw new IllegalArgumentException();
		}

		String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		try {
			Method method = obj.getClass().getMethod(methodName);
			Object result = method.invoke(obj);
			if (result == null) {
				return true;
			}

			if (result.getClass() == String.class
					&& StringUtils.isBlank((String) result)) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 返回obj中为空的属性，不包含父类
	 * @param obj
	 * @return
	 * @date 2014-3-10
	 * @author Ouyang
	 */
	public static String getNullField(Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException();
		}

		List<String> fieldNames = getFieldNames(obj);
		if (CollectionUtils.isEmpty(fieldNames)) {
			return null;
		}

		for (String fieldName : fieldNames) {
			if (checkFiledValueIsNull(fieldName, obj)) {
				return fieldName;
			}
		}

		return null;
	}

	public static Workbook createWorkbook(InputStream is) {
		if (!is.markSupported()) {
			is = new PushbackInputStream(is, PlatformConstants.SONAR_NUMBER_8);
		}
		try {
			if (POIFSFileSystem.hasPOIFSHeader(is)) {
				return new HSSFWorkbook(is);
			}
		} catch (IOException e) {
			return null;
		}
		try {
			if (POIXMLDocument.hasOOXMLHeader(is)) {
				return new XSSFWorkbook(OPCPackage.open(is));
			}
		} catch (InvalidFormatException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

		return null;
	}

	/**
	 * 将excel中cell的值转换成String
	 * @param cell
	 * @param pattern 用于转换成时间时，指定的格式，如yyyy-MM-dd
	 * @return
	 * @date 2014-3-10
	 * @author Ouyang
	 */
	public static String parseExcelCell(Cell cell, DateFormat dateFormat,
			NumberFormat numberFormat) {
		if (cell == null) {
			return null;
		}

		String value = null;

		int cellType = cell.getCellType();
		switch (cellType) {
		case HSSFCell.CELL_TYPE_BLANK:
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue() + "";
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				value = dateFormat.format(date);
			} else {
				value = String.valueOf(numberFormat.format(cell
						.getNumericCellValue()));
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue().trim();
			break;
		default:
			break;
		}

		return value;
	}

	/**
	 * 将Date转换成指定格式的String
	 * @param date
	 * @param pattern
	 * @return
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	public static String formatDate2String(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将String转化成date
	 * @param source
	 * @param pattern
	 * @return
	 * @date 2014-3-17
	 * @author Ouyang
	 */
	public static Date parseString2Date(String source, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(source);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 判断toDate是否在fromDate的n field之后
	 * @param fromDate
	 * @param toDate
	 * @param n
	 * @return
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	public static boolean is2DatesDifferN(Date fromDate, Date toDate,
			int field, int n) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(fromDate);
		calendar.add(field, n);

		if (toDate.after(calendar.getTime())) {
			return true;
		}

		return false;
	}

	/**
	 * 获取传如日期的所在天的第一刻
	 * @param date
	 * @return
	 * @date 2014-3-7
	 * @author Ouyang
	 */
	public static Date getFirstMomentOfDay(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取传入日期的所在天最后一刻
	 * @param date
	 * @return
	 * @date 2014-3-7
	 * @author Ouyang
	 */
	public static Date getLastMomentOfDay(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, PlatformConstants.SONAR_NUMBER_23);
		calendar.set(Calendar.MINUTE, PlatformConstants.SONAR_NUMBER_59);
		calendar.set(Calendar.SECOND, PlatformConstants.SONAR_NUMBER_59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取传入时间所在月份的第一天
	 * @param date
	 * @return
	 * @date 2014-3-17
	 * @author Ouyang
	 */
	public static Date getFirstDayOfMonth(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取传入时间所在月份的最后一天
	 * @param date
	 * @return
	 * @date 2014-3-17
	 * @author Ouyang
	 */
	public static Date getLastDayOfMonth(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	public static Date addDate(Date date, int field, int amount) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * @description 转换数字格式 四舍五入 ==保留2位小数点
	 * @param obj
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年4月2日 下午7:41:20
	 */
	public static String decimalTwoFormatForString(Object obj) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		try {
			return df.format(obj);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @description 转换数字格式 四舍五入 ==保留2位小数点
	 * @param obj
	 * @return BigDecimal
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年4月2日 下午7:49:28
	 */
	public static BigDecimal decimalTwoFormatForBigDecimal(Object obj) {
		String bigDecimalStr = decimalTwoFormatForString(obj);
		if (bigDecimalStr != null) {
			return new BigDecimal(bigDecimalStr);
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * @description 转换数字格式 四舍五入 ==保留2位小数点
	 * @param obj
	 * @return Float
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年4月2日 下午7:49:28
	 */
	public static Float decimalTwoFormatForFloat(Object obj) {
		String floatStr = decimalTwoFormatForString(obj);
		if (floatStr != null) {
			return new Float(floatStr);
		} else {
			return new Float("0");
		}
	}

}
