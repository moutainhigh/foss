package com.deppon.foss.module.pickup.sign.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;

public class DownloadFileAction extends AbstractAction {

	private static final long serialVersionUID = 3571130889767324255L;

	private InputStream excelStream;
	private String execlName;

	public String exportModelExcel() {
		//设置导出Excel名
		execlName = "template";
		//第1行内容
		String[] rowHeads = { "快递代理公司名称", "快递代理公司编码", "运单号", "	件数", "签收人(客户)",
				"签收情况", "货物拉回异常描述" };
		//第2行内容
		String[] row1 = { "西安骏驰公司", "34255", "5000050050", "1", "张三", "正常签收",
				null };
		//第3行内容
		String[] row2 = { "西安骏驰公司", "34255	", "5000050051", "	1", "李四", "异常签收",
				"货物超时" };

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		jxl.write.Label label;
		WritableWorkbook workbook;
		//行号
		int row = 0;
		try {
			workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			//设置列宽
			sheet.setRowView(0, SettlementReportNumber.FIVE_HUNDRED);
			sheet.setColumnView(0, SettlementReportNumber.TWENTY);
			sheet.setColumnView(1, SettlementReportNumber.TWENTY);
			sheet.setColumnView(2, SettlementReportNumber.TWENTY);
			sheet.setColumnView(SettlementReportNumber.THREE, SettlementReportNumber.TEN);
			sheet.setColumnView(SettlementReportNumber.FOUR, SettlementReportNumber.FIFTEEN);
			sheet.setColumnView(SettlementReportNumber.FIVE, SettlementReportNumber.FIFTEEN);
			sheet.setColumnView(SettlementReportNumber.SIX, SettlementReportNumber.TWENTY_FIVE);
			//设置字体
			WritableFont bold = new WritableFont(WritableFont.ARIAL, SettlementReportNumber.TEN,
					WritableFont.BOLD);
			//设置单元格样式
			WritableCellFormat titleFormate = new WritableCellFormat(bold);
			//设置单元格背景为黄色
			titleFormate.setBackground(jxl.format.Colour.YELLOW2);
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);
			titleFormate
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			//写第一行
			for (int i = 0; i < rowHeads.length; i++) {
				label = new jxl.write.Label(i, row, rowHeads[i], titleFormate);
				sheet.addCell(label);
			}
			//行号加1
			row += 1;
			//写第2行
			for (int i = 0; i < row1.length; i++) {
				label = new jxl.write.Label(i, row, row1[i]);
				sheet.addCell(label);
			}
			//行号加1
			row += 1;
			//写第3行
			for (int i = 0; i < row2.length; i++) {
				label = new jxl.write.Label(i, row, row2[i]);
				sheet.addCell(label);
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		excelStream = new ByteArrayInputStream(out.toByteArray());
		if (excelStream == null)
			return returnError("导出失败！");
		return returnSuccess();
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public String getExeclName() {
		return execlName;
	}

}
