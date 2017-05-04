/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.shared.define;

public final class PlatformConstants {

	/** 导出Excel文件的sheet最大行数*/
	public static final int SHEET_SIZE = 20000;
	
	/**
	 * 外场异常人员状态代码
	 */
	public static final String[] ABSENTEESTATUS_CODES = { "RESIGNATION",
			"ABSENTEEISM", "INDUSTRIAL_INJURY", "LONG_HOLIDAYS" };

	/**
	 * 外场异常人员状态名称
	 */
	public static final String[] ABSENTEESTATUS_VALUES = { "自离", "旷工", "工伤",
			"长假" };

	/**
	 * 外场异常人员导出excel名
	 */
	public static final String TFRCTRABSENTEEINFO_EXCEL_NAME = "人员异常信息.xls";

	/**
	 * 外场异常人员导出excel表头
	 */
	public static final String[] TFRCTRABSENTEEINFO_EXCEL_TITLE = { "外场", "部门",
			"姓名", "工号", "岗位", "入职时间", "异常开始时间", "异常结束时间", "异常状态", "录入人",
			"录入日期", "修改人", "修改时间" };

	/**
	 * 03excel文件后缀名
	 */
	public static final String EXCEL_FILE_EXT_03 = ".xls";

	/**
	 * 07excel文件后缀名
	 */
	public static final String EXCEL_FILE_EXT_07 = ".xlsx";

	/**
	 * 外场异常人员信息导入有效的excel列数，前4列
	 */
	public static final int EXCEL_TEMPLATE_CLOUMN_NUM = 4;

	/**
	 * 外场异常人员信息导入表头行数，占两行
	 */
	public static final int EXCEL_TEMPLATE_HEADER_NUM = 2;

	public final static int QUERY_DATE_SPAN_HALF_MONTH = 16;

	public final static int QUERY_DATE_SPAN_1MONTH = 31;

	public final static int QUERY_DATE_SPAN_2MONTH = 60;

	public final static int QUERY_DATE_SPAN_1YEAR = 366;

	/**
	 * 货区密度导出excel名
	 */
	public static final String EXCEL_FILE_NAME_GAD = "货区密度.xls";

	/**
	 * 货区密度导出excel表头
	 */
	public static final String[] EXCEL_TITLE_GAD_4SUM = { "本部", "事业部", "外场",
			" 日库区总容量(F)", "日库区总货量(F)", "日库区平均密度", "月库区总货量(F)", "月库区平均密度(F)",
			"日期" };

	/**
	 * 各外场各货区整点货区密度导出excel表头
	 */
	public static final String[] EXCEL_TITLE_GAD_4TIMELY = { "外场", "货区类型",
			"货区名称", "日期", "库区容量(F)", "库区货量(F)", "库区密度", "整点数" };

	/**
	 * 各外场各货区各天货区平均密度导出excel表头
	 */
	public static final String[] EXCEL_TITLE_GAD_4DAILY = { "外场", "货区类型",
			"货区名称", "库区总容量(F)", "库区总货量(F)", "库区平均密度", "日期" };
	/**
	 * 
	 * 各外场各货区各月货区平均密度导出excel表头
	 */
	public static final String[] EXCEL_TITLE_GAD_4MONTHLY = { "外场", "货区类型",
			"货区名称", "月", "库区总容量(F)", "库区总货量(F)", "库区平均密度" };

	/**
	 * 综合职位表中理货员、经营外场组长、运营外场组长code
	 */
	public static final String[] POST_LOADER_CODES = { "05020017", "05020005",
			"05020007" };

	/**
	 * 综合职位表中电叉司机、电叉司机组长code
	 */
	public static final String[] POST_FORKLIFT_DRIVER_CODES = { "05020010",
			"05020001" };

	/**
	 * 对应TallyClerkDutyEntity中type的装卸车员
	 */
	public static final String LOADER = "LOADER";

	/**
	 * 对应TallyClerkDutyEntity中type的电叉司机
	 */
	public static final String DRIVER = "DRIVER";

	/**
	 * 外场人员情况，月累计连续3日未出勤名单Excel表头
	 */
	public static final String[] EXCEL_HEADER_TFRCTRSTAFF3DAYNODUT = { "外场",
			"部门", "工号", "姓名", "岗位", "新增时间" };
	
	/**
	 * 外场pda最高峰使用明细
	 * */
	public static final String TFRCTRPDAUSINGINFO_EXCEL_NAME = "pda最高峰使用明细信息.xls";

	/**
	 * 外场pda最高峰使用明细Excel表头
	 * */
	public static final String[] TFRCTRPDAUSINGINFO_EXCEL_TITLE = { "转运场", "日期",
			"当日最高峰电叉司机使用pda台数", "当日最高峰电叉司机使用pda时间", "当日最高峰理货员使用pda台数", "当日最高峰理货员使用pda时间", "当日最高峰所有人员使用pda台数", "当日最高峰所有人员使用pda时间"};
	
	
	/**
	 * 月台操作效率统计表单Excel表头
	 */
	public static final String[] EXCEL_HEADER_EXPORTPLATFORMOPEEFFIDATA = { "日期","月份",  "经营本部", "外场" ,
			"当日装车吞吐量(T)", "当日卸车吞吐量(T)", "当日装车有效操作时长(H)", "当日卸车有效操作时长(H)", "当日月台操作效率(T/H)" ,
			"当月装车吞吐量(T)", "当月卸车吞吐量(T)", "当月装车有效操作时长(H)", "当月卸车有效操作时长(H)", "当月月台操作效率(T/H)" };
	
	/**
	 * 装卸车效率管理
	 */
	public static final String TRUCK_EFFICIENCY_DAY_SHEET_NAME = "日均装卸车效率";
	public static final String TRUCK_EFFICIENCY_MONTH_SHEET_NAME = "月均装卸车效率";
	public static final String[] EXCEL_HEADER_TRUCK_EFFICIENCY_DAY = { "日期",
		"转运场", "装车操作货量", "卸车操作货量", "装车操作时长", "卸车操作时长" ,"装车效率", "卸车效率","总效率" 
		};
	public static final String[] EXCEL_HEADER_TRUCK_EFFICIENCY_MONTH = { "月份",
		"转运场", "装车操作货量", "卸车操作货量", "装车操作时长", "卸车操作时长" ,"装车效率", "卸车效率","总效率" 
		};
	
	/**
	 * 转运场货物流动分布 管理 的导出
	 */
	public static final String GOODS_DISTRIBUTION_DAY_SHEET_NAME = "外场每日货物流动分布情况";
	public static final String GOODS_DISTRIBUTION_MONTH_SHEET_NAME = "外场月均货物流动分布情况";
	public static final String[] EXCEL_HEADER_GOODS_DISTRIBUTION_DAY = {"日期",
		"时间", "转运场", "到达货量（吨）", "出发货量（吨）", "实际流入量（吨）" ,"实际流出量（吨）", "货台库存（吨）"};
	public static final String[] EXCEL_HEADER_GOODS_DISTRIBUTION_MONTH = { "月份",
		"时间", "转运场", "到达货量（吨）", "出发货量（吨）", "实际流入量（吨）" ,"实际流出量（吨）", "货台库存（吨）"};
	
	
	/**sonar_constants_number*/
	public final static int SONAR_NUMBER_0 = 0;
	public final static int SONAR_NUMBER_1 = 1;
	public final static int SONAR_NUMBER_2 = 2;
	public final static int SONAR_NUMBER_3 = 3;
	public final static int SONAR_NUMBER_4 = 4;
	public final static int SONAR_NUMBER_5 = 5;
	public final static int SONAR_NUMBER_6 = 6;
	public final static int SONAR_NUMBER_7 = 7;
	public final static int SONAR_NUMBER_8 = 8;
	public final static int SONAR_NUMBER_9 = 9;
	public final static int SONAR_NUMBER_10 = 10;
	public final static int SONAR_NUMBER_11 = 11;
	public final static int SONAR_NUMBER_12 = 12;
	public final static int SONAR_NUMBER_13 = 13;
	public final static int SONAR_NUMBER_14 = 14;
	public final static int SONAR_NUMBER_15 = 15;
	public final static int SONAR_NUMBER_16 = 16;
	public final static int SONAR_NUMBER_17 = 17;
	public final static int SONAR_NUMBER_18 = 18;
	public final static int SONAR_NUMBER_19 = 19;
	public final static int SONAR_NUMBER_20 = 20;
	public final static int SONAR_NUMBER_21 = 21;
	public final static int SONAR_NUMBER_22 = 22;
	public static final int SONAR_NUMBER_23 = 23;
	public static final int SONAR_NUMBER_24 = 24;
	public static final int SONAR_NUMBER_25 = 25;
	public static final int SONAR_NUMBER_26 = 26;
	public static final int SONAR_NUMBER_27 = 27;
	public static final int SONAR_NUMBER_28 = 28;
	public static final int SONAR_NUMBER_29 = 29;
	public final static int SONAR_NUMBER_30 = 30;
	public final static int SONAR_NUMBER_31 = 31;
	public final static int SONAR_NUMBER_32 = 32;
	public final static int SONAR_NUMBER_33 = 33;
	public final static int SONAR_NUMBER_34 = 34;
	public final static int SONAR_NUMBER_35 = 35;
	public final static int SONAR_NUMBER_36 = 36;
	public final static int SONAR_NUMBER_37 = 37;
	public final static int SONAR_NUMBER_38 = 38;
	public final static int SONAR_NUMBER_39 = 39;
	public static final int SONAR_NUMBER_40 = 40;
	public static final int SONAR_NUMBER_41 = 41;
	public static final int SONAR_NUMBER_42 = 42;
	public static final int SONAR_NUMBER_43 = 43;
	public static final int SONAR_NUMBER_44 = 44;
	public static final int SONAR_NUMBER_45 = 45;
	public static final int SONAR_NUMBER_46 = 46;
	public static final int SONAR_NUMBER_47 = 47;
	public static final int SONAR_NUMBER_48 = 48;
	public static final int SONAR_NUMBER_49 = 49;
	public static final int SONAR_NUMBER_50 = 50;
	public static final int SONAR_NUMBER_51 = 51;
	public static final int SONAR_NUMBER_52 = 52;
	public static final int SONAR_NUMBER_53 = 53;
	public static final int SONAR_NUMBER_54 = 54;
	public static final int SONAR_NUMBER_55 = 55;
	public static final int SONAR_NUMBER_56 = 56;
	public static final int SONAR_NUMBER_57 = 57;
	public static final int SONAR_NUMBER_58 = 58;
	public static final int SONAR_NUMBER_59 = 59;
	public static final int SONAR_NUMBER_60 = 60;
	public static final int SONAR_NUMBER_61 = 61;
	public static final int SONAR_NUMBER_62 = 62;
	public final static int SONAR_NUMBER_100 = 100;
	public final static int SONAR_NUMBER_384 = 384;
	public final static int SONAR_NUMBER_1000 = 1000;
	public static final int SONAR_NUMBER_5000 = 5000;
}
