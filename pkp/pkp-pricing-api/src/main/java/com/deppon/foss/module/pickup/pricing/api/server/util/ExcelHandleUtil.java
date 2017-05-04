package com.deppon.foss.module.pickup.pricing.api.server.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.util.DateUtils;

/**
 * 用于操作excel的工具类
 * @author 348757
 * @date 2016-11-09
 */
public class ExcelHandleUtil {
	
	/**
	 * 取出单元格的值以String样式表示的结果
	 * @param cell单元格
	 * @return String
	 */
	public static String obtainStringVal(Cell cell) {
		// 列值
		String cellVal = "";
		if (null != cell) {
			// 匹配单元格类型
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是date类型则 ，获取该cell的date值
					cellVal = DateUtils.convert(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()), DateUtils.DATE_FORMAT);
				} else {
					// 纯数字
					cellVal = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				cellVal = cell.getRichStringCellValue().toString();
				break;
			case HSSFCell.CELL_TYPE_FORMULA:// 公式
				cellVal = String.valueOf(cell.getNumericCellValue());
				// 如果获取的数据值为非法值,则转换为获取字符串
				if (cellVal.equals("NaN")) {
					cellVal = cell.getRichStringCellValue().toString();
				}
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
				cellVal = " " + cell.getBooleanCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				cellVal = "";
				break;
			default: // 默认
				cellVal = cell.getRichStringCellValue().toString();
			}
			if (StringUtil.isNotEmpty(cellVal)) {
				return cellVal.trim();
			}
		}
		return cellVal;
	}

}
