package com.deppon.foss.print.labelprint.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintWorker;

public class GODEXNewLabelPrintWorker extends LabelPrintWorker {

	// 左边距
	private Integer leftDistance = new Integer("130");
	// 上边距
	private Integer topDistance = new Integer("0");

	// 送货情况,分为"送",不送货则为"",如果是送货时,会打印个"送"字
	String deliver = "\t";
	// 包装
	String packing = "\t";

	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
		lblPrintContext.setPrintForm(printNewLabel(lblPrintContext,
				lblPrintContext.getLabelPrintInfo()));
	}

	@Override
	public void prepareLabelPrintService(LabelPrintContext lblPrintContext)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeExecuteLabelPrint(LabelPrintContext lblPrintContext)
			throws Exception {
		// TODO Auto-generated method stub

	}

	private int addLength(int stringLength, int length, int returnLength) {
		if (returnLength > 0) {
			if (stringLength >= length)
				return 0;
		}
		return returnLength;
	}

	private String printNewLabel(LabelPrintContext lblPrintContext,
			LabelPrintInfo labelPrintInfo) throws Exception {

		if (labelPrintInfo.isIsUnusual()) {
			packing += "【异】";
		}

		int printIndexFrom = labelPrintInfo.getStartPieceIndex();
		int printIndexEnd = labelPrintInfo.getEndPieceIndex();
		String beginPieces = "" + printIndexFrom;
		if (beginPieces.length() == 1) {
			beginPieces = "000" + beginPieces;
		} else if (beginPieces.length() == 2) {
			beginPieces = "00" + beginPieces;
		} else if (beginPieces.length() == 3) {
			beginPieces = "0" + beginPieces;
		}

		BigDecimal printPiece = new BigDecimal(printIndexEnd - printIndexFrom);

		// 获得打印件数
		int pieces = lblPrintContext.getPrintPiece();

		String str = String.valueOf(1 + pieces);
		String serial_no = getDigit(str, 4);

		StringBuffer strq = new StringBuffer("");
		strq.append("^Y96,N,8,1\r\n");
		strq.append("~MDEL\r\n");
		strq.append("^L\r\n");
		strq.append("C1," + beginPieces + ",+1,A1\r\n");

		strq.append("C0," + serial_no + ",+1,A1\r\n");
		strq.append("^E10\r\n");
		strq.append("^R" + leftDistance + "\r\n");
		strq.append("^P" + printPiece.add(new BigDecimal(1)) + "\r\n");
		strq.append("^H13\r\n");
		strq.append("^Q55,5\r\n");
		strq.append("Q900,550\r\n");

		strq.append("AZ,40,20,1,1,8,0," + KEY_DPWL_CN + "\r\n");
		strq.append("Le,40,47,165,49\r\n");
		strq.append("AZ,45,50,1,1,8,0,DEPPON\r\n");
		strq.append("BQ,175,9,2,9,60,0,0," + labelPrintInfo.getNumber() + "^C0"
				+ labelPrintInfo.getWeight() + labelPrintInfo.getFinalCode()
				+ labelPrintInfo.getDestCode() + "\r\n");
		strq.append("BQ,720,25,2,9,50,1,0," + labelPrintInfo.getNumber()
				+ "^C0" + labelPrintInfo.getWeight()
				+ labelPrintInfo.getFinalCode() + labelPrintInfo.getDestCode()
				+ "\r\n");
		strq.append("R24,80,660,380,3,3\r\n");
		strq.append("Le,24,140,660,142\r\n");
		strq.append("Le,24,195,660,197\r\n");
		strq.append("Le,24,255,660,257\r\n");
		strq.append("Le,24,318,660,320\r\n");

		strq.append("Le,81,80,83,197\r\n");
		strq.append("Le,193,80,195,197\r\n");
		strq.append("Le,305,80,307,197\r\n");
		strq.append("Le,417,80,419,197\r\n");
		strq.append("Le,529,80,531,197\r\n");

		strq.append("Le,81,255,83,320\r\n");
		strq.append("Le,305,255,307,320\r\n");
		strq.append("Le,367,255,369,320\r\n");
		strq.append("Le,104,195,106,255\r\n");
		strq.append("Le,385,195,387,255\r\n");
		strq.append("Le,457,195,459,255\r\n");
		strq.append("Le,128,320,130,380\r\n");

		strq.append("AZ,30,88,2,1,8,0,地\r\n");
		strq.append("AZ,30,113,2,1,8,0,区\r\n");
		strq.append("AZ,30,145,2,1,8,0,编\r\n");
		strq.append("AZ,30,170,2,1,8,0,号\r\n");
		strq.append("AZ,30,255,2,1,8,0,件\r\n");
		strq.append("AZ,30,282,2,1,8,0,数\r\n");
		strq.append("AZ,310,258,2,1,8,0,单\r\n");
		strq.append("AZ,310,282,2,1,8,0,号\r\n");
		strq.append("AZ,32,202,1,2,8,0,目的\r\n");
		strq.append("AZ,394,202,1,2,8,0,包装\r\n");
		strq.append("AZ,40,326,1,2,0,0,收货人\r\n");

		if (labelPrintInfo.getLocationOne().length() > 2) {
			strq.append("AZ,89,85,1,2,0,0," + labelPrintInfo.getLocationOne()
					+ "\r\n");
		} else {
			strq.append("AZ,89,85,2,2,0,0," + labelPrintInfo.getLocationOne()
					+ "\r\n");
		}

		if (labelPrintInfo.getLocationTwo().length() > 2) {
			strq.append("AZ,200,85,1,2,0,0," + labelPrintInfo.getLocationTwo()
					+ "\r\n");
		} else {
			strq.append("AZ,200,85,2,2,0,0," + labelPrintInfo.getLocationTwo()
					+ "\r\n");
		}

		if (labelPrintInfo.getLocationThree().length() > 2) {
			strq.append("AZ,310,85,1,2,0,0,"
					+ labelPrintInfo.getLocationThree() + "\r\n");
		} else {
			strq.append("AZ,303,85,2,2,0,0,"
					+ labelPrintInfo.getLocationThree() + "\r\n");
		}

		if (labelPrintInfo.getLocationFour().length() > 2) {
			strq.append("AZ,425,85,1,2,0,0," + labelPrintInfo.getLocationFour()
					+ "\r\n");
		} else {
			strq.append("AZ,425,85,2,2,0,0," + labelPrintInfo.getLocationFour()
					+ "\r\n");
		}

		if (labelPrintInfo.getLocationFive().length() > 2) {
			strq.append("AZ,537,85,1,2,0,0," + labelPrintInfo.getLocationFive()
					+ "\r\n");
		} else {
			strq.append("AZ,537,85,2,2,0,0," + labelPrintInfo.getLocationFive()
					+ "\r\n");
		}
		strq.append("AI,97,148,2,2,0,0," + labelPrintInfo.getStoreOneNum()
				+ "\r\n");
		strq.append("AI,209,148,2,2,0,0," + labelPrintInfo.getStoreTwoNum()
				+ "\r\n");
		strq.append("AI,311,148,2,2,0,0," + labelPrintInfo.getStoreThreeNum()
				+ "\r\n");
		strq.append("AI,433,148,2,2,0,0," + labelPrintInfo.getStoreFourNum()
				+ "\r\n");
		strq.append("AI,545,148,2,2,0,0," + labelPrintInfo.getStoreFiveNum()
				+ "\r\n");

		/*
		 * ---------------------------------------------------------
		 * 
		 * @author 寻 港
		 * 
		 * @date 2009-09-22
		 * 
		 * @description 最多支持9个字符; 随着字数增多, 逐渐压缩字符大小;
		 * ---------------------------------------------------------
		 */
		int txtLength = labelPrintInfo.getDestination().trim().length();
		if (txtLength <= 6) {
			strq.append("AZ,100,202,2,2,1,0," + labelPrintInfo.getDestination()
					+ "\r\n");
		} else {
			strq.append("AZ,100,202,1,2,0,0," + labelPrintInfo.getDestination()
					+ "\r\n");
		}

		// 必须打印件数
		int printIndexEndlength = ("" + printIndexEnd).length();
		strq.append("AZ," + (100 + addLength(printIndexEndlength, 3, 10))
				+ ",250,2,2,1,0," + labelPrintInfo.getPieces() + "\r\n");

		if (printIndexEndlength == 1) {
			strq.append("AB," + (157 + printIndexEndlength * 40)
					+ ",290,1,1,0,0,^C1" + "\r\n");
			strq.append("AZ," + (110 + printIndexEndlength * 40)
					+ ",290,1,1,1,0,第" + "\r\n");
		} else if (printIndexEndlength == 2) {
			strq.append("AB," + (125 + printIndexEndlength * 38)
					+ ",290,1,1,0,0,^C1" + "\r\n");
			strq.append("AZ," + (70 + printIndexEndlength * 40)
					+ ",290,1,1,1,0,第" + "\r\n");
		} else {
			strq.append("AB," + (70 + printIndexEndlength * 38)
					+ ",290,1,1,0,0,^C1" + "\r\n");
			strq.append("AZ," + (20 + printIndexEndlength * 40)
					+ ",290,2,1,0,0,第" + "\r\n");
		}
		strq.append("AI,380,263,2,2,0,0," + labelPrintInfo.getNumber() + "\r\n");

		String consigneeLength = deliver + labelPrintInfo.getConsignee();
		if (consigneeLength.length() > 11) {
			strq.append("AZ,100,320,1,2,0,0," + deliver
					+ labelPrintInfo.getConsignee() + "\r\n");
		} else {
			strq.append("AZ,100,320,2,2,0,0," + deliver
					+ labelPrintInfo.getConsignee() + "\r\n");
		}
		if (packing.length() <= 4) {
			strq.append("AZ,466,202,2,2,0,0," + packing + "\r\n");
		} else {
			strq.append("AZ,466,202,1,2,0,0," + packing + "\r\n");
		}

		// 输出运输类型
		strq.append("AZ,350,376,2,2,1,0," + labelPrintInfo.getTransProperty()
				+ "\r\n");
		if ("精 准 卡 航".equals(labelPrintInfo.getTransProperty())
				|| "精 准 城 运".equals(labelPrintInfo.getTransProperty())) {
			strq.append("Le,350,376,637,437" + "\r\n");
		}

		strq.append("AZ,30,383,1,1,1,0," + labelPrintInfo.getUserName() + "*"
				+ labelPrintInfo.getCurrentDept() + "\r\n");

		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern("yy-MM-dd");// 设置打印时间的格式
		String printDate = format.format(new Date());// 打印时间

		strq.append("AZ,30,410,1,1,1,0," + printDate + "\r\n");
		strq.append("E" + "\r\n");
		strq.append("~MDEL" + "\r\n");

		return strq.toString();
	}

}
