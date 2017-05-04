package com.deppon.foss.module.transfer.platform.api.shared.define;


/**
* @description 仓库饱和度统计常量类
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 上午9:49:50
*/
public class StockSaturationConstants {
	
	/**
	 * 成功/失败
	 * 对于过程性的方法，返回整型来代表成功或者失败；
	 */
	public static final int SUCCESS = 1;
	public static final int FAILURE = -1;

	
	
	/**
	 * 千克转换成吨
	* @fields kILOTOTONYES
	* @author 14022-foss-songjie
	* @update 2014年5月16日 下午2:28:28
	* @version V1.0
	*/
	public static final int KILOTOTONYES=1;
	
	/**
	 * 单位无需转换
	* @fields kILOTOTONNO
	* @author 14022-foss-songjie
	* @update 2014年5月16日 下午2:28:50
	* @version V1.0
	*/
	public static final int KILOTOTONNO=0;
	
	
	
	/**
	 * 警戒值超出规定的次数就发送短信给外场负责人
	* @fields WARN_SEND_NUM
	* @author 14022-foss-songjie
	* @update 2014年4月24日 下午4:52:37
	* @version V1.0
	*/
	public static final int WARN_SEND_NUM = 3;
	
	/**  导出Excel文件的sheet最大行数*/
	public static final int SHEET_SIZE = 5000;
	
	/**仓库饱和度*/
	public static final String[] STOCK_SATURATION_ROW_HEADS = {"本部","事业部","外场","日承载货量(T)","当日操作货量(T)","当日仓库饱和度","当月操作货量(T)","当月仓库饱和度","当月预警天数","当月危险预警天数"};
	/**日仓库饱和度*/
	public static final String[] STOCK_SATURATION_DAY_ROW_HEADS = {"日期","当日的操作货量(T)","当日承载货量(T)","当日仓库饱和度"};
	/**月仓库饱和度*/
	public static final String[] STOCK_SATURATION_MONTH_ROW_HEADS = {"月份","当月的操作货量(T)","当月承载货量(T)","当月仓库饱和度"};
	
	/**仓库饱和度数据监控报表*/
	public static final String[] STOCK_SATURATION_REPORT_ROW_HEADS = {"本部","事业部","大区","转运场","日期","仓库饱和度日数据","仓库饱和度月累计","派送率日数据","派送率度月累计","派送拉回率日数据","派送拉回率月累计","货区密度峰值日数据","货区密度峰值月累计","退单率日数据","退单率月累计"};
	
	
	/**
	 * 仓库饱和度
	* @fields SEND_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年3月19日 上午9:20:44
	* @version V1.0
	*/
	public static final String STOCK_SATURATION_SHEET_NAME = "仓库饱和度";
	
	/**
	 * 日仓库饱和度明细
	* @fields STOCK_SATURATION_DAY_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年4月4日 上午11:12:36
	* @version V1.0
	*/
	public static final String STOCK_SATURATION_DAY_SHEET_NAME = "日仓库饱和度明细";
	
	/**
	 * 月仓库饱和度明细
	* @fields STOCK_SATURATION_MONTH_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年4月4日 上午11:12:39
	* @version V1.0
	*/
	public static final String STOCK_SATURATION_MONTH_SHEET_NAME = "月仓库饱和度明细";
	
	
	
	/**
	 * 仓库饱和度数据监控报表
	* @fields STOCK_SATURATION_REPORT_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午9:21:23
	* @version V1.0
	*/
	public static final String STOCK_SATURATION_REPORT_SHEET_NAME = "仓库预警数据报表";
	/**仓库饱和度超过危险值时短信模板编码*/
	public static final String WAREHOUSE_SATURATION_DANGER = "WAREHOUSE_SATURATION_EXCEED_DANGER";
	/**仓库饱和度有三日超过警戒值时短信模板编码*/
	public static final String WAREHOUSE_SATURATION_WARN = "WAREHOUSE_SATURATION_EXCEED_WARN";
	
}
