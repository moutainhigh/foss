package com.deppon.foss.module.transfer.management.api.shared.define;

public class BillListCheckConstants {
	/**
	 * 是，否
	 */
	public static final String YES = "Y";
	public static final String NO = "N";
	
	/** 电子对账单  导出Excel文件的sheet最大行数*/
	public static final int SHEET_SIZE = 5000;
	/** 电子对账单 导出Excel文件的表头*/
	public static final String[] ROW_HEADS = {"日期","所属事业部","所属车队","燃油费金额","燃油费优惠金额","路桥费金额","路桥费优惠金额"};
	
	/** 电子对账单 导出Excel文件的表头*/
	public static final String[] ROW_HEADS_MODEL = {"日期","燃油费金额","燃油费优惠金额","路桥费金额","路桥费优惠金额"};
	
	/**
	* @fields SHEET_NAME
	* @author 14022-foss-songjie 
	* @update 2013年11月29日 下午3:58:48
	* @version V1.0
	*/
	public static final String SHEET_NAME = "对账单";
}
