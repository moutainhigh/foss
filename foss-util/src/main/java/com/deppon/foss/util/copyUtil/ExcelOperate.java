/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.server.action
 * FILE    NAME: ExcelOperate.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.util.copyUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 读取excel内容
 * 
 * @author 096598-foss-zhongyubing
 * @date 2013-3-18 上午10:56:05
 */
public class ExcelOperate {

	private List<String> duplicFiles;

	public List<String> getDuplicFiles() {
		return duplicFiles;
	}

	public void setDuplicFiles(List<String> duplicFiles) {
		this.duplicFiles = duplicFiles;
	}

	public static void main(String[] args) throws Exception {

		ExcelOperate operate = new ExcelOperate();
		operate.readFilePack("D:\\test.xls");

	}

	/**
	 * 返回文件名list内容
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-18 下午1:34:07
	 */
	public List<String> readFileNames(String readExcelFile) {
		File file = new File(readExcelFile);
		List<String> tempJavaFiles = new ArrayList<String>();

		String[][] result = null;
		try {
			result = getData(file, 1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int rowLength = -1;
		if (result != null) {
			rowLength = result.length;
		}

		String fileName = "";
		String javaFileName = "";
		String tail = ".java";
		for (int i = 0; i < rowLength; i++) {
			fileName = result[i][0];
			javaFileName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) + tail;
			tempJavaFiles.add(javaFileName);
			System.out.println(javaFileName);
		}
		return tempJavaFiles;
	}

	/**
	 * 返回文件名Map内容
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-18 下午1:34:24
	 */
	public Map<String, String> readFileNamesMap(String readExcelFile) {
		File file = new File(readExcelFile);
		duplicFiles = new ArrayList<String>();
		Map<String, String> tempJavaFiles = new HashMap<String, String>();

		String[][] result = null;
		try {
			result = getData(file, 1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int rowLength = -1;
		if (result != null) {
			rowLength = result.length;
		}

		String fileName = "";
		String pkgName = "";
		String javaFileName = "";
		String javaPkgName = "";
		String tail = ".java";
		for (int i = 0; i < rowLength; i++) {
			fileName = result[i][0];
			// 文件名
			javaFileName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) + tail;
			// 报名
			pkgName = fileName.substring(0, fileName.lastIndexOf(":"));
			javaPkgName = pkgName.substring(pkgName.lastIndexOf(":") + 1, pkgName.length());

			String key = javaFileName;
			if (tempJavaFiles.get(key) != null) {
				duplicFiles.add(javaFileName + "#" + javaPkgName);
				duplicFiles.add(key + "#" + tempJavaFiles.get(key));
			} else {
				tempJavaFiles.put(key, javaPkgName);
			}

			System.out.println(key);
		}
		return tempJavaFiles;
	}

	public Map<String, String> readFilePack(String readExcelFile) {
		File file = new File(readExcelFile);
		Map<String, String> tempJavaPacks = new HashMap<String, String>();

		String[][] result = null;
		try {
			result = getData(file, 1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int rowLength = -1;
		if (result != null) {
			rowLength = result.length;
		}

		String fileName = "";
		String javaFileName = "";

		for (int i = 0; i < rowLength; i++) {
			fileName = result[i][0];
			fileName = fileName.substring(0, fileName.lastIndexOf(":"));
			javaFileName = fileName.substring(fileName.lastIndexOf(":") + 1, fileName.length());
			tempJavaPacks.put(javaFileName, "init");
		}
		return tempJavaPacks;
	}

	/**
	 * 
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * 
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * 
	 * @return 读出的Excel中数据的内容
	 * 
	 * @throws FileNotFoundException
	 * 
	 * @throws IOException
	 */

	public static String[][] getData(File file, int ignoreRows)

	throws FileNotFoundException, IOException {

		List<String[]> result = new ArrayList<String[]>();

		int rowSize = 0;

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(

		file));

		// 打开HSSFWorkbook
		FileInputStream inputStream = new FileInputStream(file);
		Workbook wb = null;
		try {

			wb = XlsImpUtil.create(inputStream);
		} catch (Exception ex) {

		}

		Cell cell = null;

		for (int sheetIndex = 1; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

			Sheet st = wb.getSheetAt(sheetIndex);

			// 第一行为标题，不取

			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {

				Row row = st.getRow(rowIndex);

				if (row == null) {

					continue;

				}

				int tempRowSize = row.getLastCellNum() + 1;

				if (tempRowSize > rowSize) {

					rowSize = tempRowSize;

				}

				String[] values = new String[rowSize];

				Arrays.fill(values, "");

				boolean hasValue = false;

				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {

					String value = "";

					cell = row.getCell(columnIndex);

					if (cell != null) {

						// 注意：一定要设成这个，否则可能会出现乱码

						// cell.setEncoding(HSSFCell.ENCODING_UTF_16);

						switch (cell.getCellType()) {

						case HSSFCell.CELL_TYPE_STRING:

							value = cell.getStringCellValue();

							break;

						case HSSFCell.CELL_TYPE_NUMERIC:

							if (HSSFDateUtil.isCellDateFormatted(cell)) {

								Date date = cell.getDateCellValue();

								if (date != null) {

									value = new SimpleDateFormat("yyyy-MM-dd")

									.format(date);

								} else {

									value = "";

								}

							} else {

								value = new DecimalFormat("0").format(cell

								.getNumericCellValue());

							}

							break;

						case HSSFCell.CELL_TYPE_FORMULA:

							// 导入时如果为公式生成的数据则无值

							if (!cell.getStringCellValue().equals("")) {

								value = cell.getStringCellValue();

							} else {

								value = cell.getNumericCellValue() + "";

							}

							break;

						case HSSFCell.CELL_TYPE_BLANK:

							break;

						case HSSFCell.CELL_TYPE_ERROR:

							value = "";

							break;

						case HSSFCell.CELL_TYPE_BOOLEAN:

							value = (cell.getBooleanCellValue() == true ? "Y"

							: "N");

							break;

						default:

							value = "";

						}

					}

					if (columnIndex == 0 && value.trim().equals("")) {

						break;

					}

					values[columnIndex] = rightTrim(value);

					hasValue = true;

				}

				if (hasValue) {

					result.add(values);

				}

			}

		}

		in.close();

		String[][] returnArray = new String[result.size()][rowSize];

		for (int i = 0; i < returnArray.length; i++) {

			returnArray[i] = result.get(i);

		}

		return returnArray;

	}

	/**
	 * 
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 处理后的字符串
	 */

	public static String rightTrim(String str) {

		if (str == null) {

			return "";

		}

		int length = str.length();

		for (int i = length - 1; i >= 0; i--) {

			if (str.charAt(i) != 0x20) {

				break;

			}

			length--;

		}

		return str.substring(0, length);

	}

}
