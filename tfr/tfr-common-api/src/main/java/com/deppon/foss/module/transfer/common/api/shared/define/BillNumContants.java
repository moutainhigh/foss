package com.deppon.foss.module.transfer.common.api.shared.define;

public class BillNumContants {

	public final static String EIGHT_DIGITS_PATTERN = "yyyyMMdd";
	
	/******* 各种单据编号位数 *******/
	public final static int LENGTH_HANDOVERBILL = 8;
	public final static int LENGTH_LOAD_TASK = 6;
	public final static int LENGTH_UNLOAD_TASK = 6;
	public final static int LENGTH_ST_TASK = 5;

	/******* 各种sequence *******/
	public final static String SEQ_HANDOVERBILL = "TFR.SEQ_SN_JJD";
	public final static String SEQ_LOAD_TASK = "TFR.SEQ_SN_LOAD_TASK";
	public final static String SEQ_UNLOAD_TASK = "TFR.SEQ_SN_UNLOAD_TASK";
	public final static String SEQ_ST_TASK = "TFR.SEQ_SN_ST_TASK";

	/******* 各种前缀 *******/
	public final static String PREFIX_LOAD_TASK_TFR = "01";
	public final static String PREFIX_LOAD_TASK_NO_TFR = "05";
	public final static String PREFIX_LOAD_TASK_SEVEN = "07";

	public final static String PREFIX_UNLOAD_TASK_TFR = "02";
	public final static String PREFIX_UNLOAD_TASK_NO_TFR = "06";
	
	public final static String PREFIX_ST_TASK_TFR = "03";
	public final static String PREFIX_ST_TASK_NO_TFR = "04";
	public final static String PREFIX_SALES_HANDOVER_TFR = "yy";
}
