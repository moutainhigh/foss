package com.deppon.foss.module.transfer.platform.api.shared.define;


/**
* @description 派送率统计常量类
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 上午9:49:50
*/
public class DeliverConstants {
	
	/**
	 * 成功/失败
	 * 对于过程性的方法，返回整型来代表成功或者失败；
	 */
	public static final int SUCCESS = 1;
	public static final int FAILURE = -1;
	
	public static final String YES ="Y";
	public static final String NO ="N";
	
	/**  导出Excel文件的sheet最大行数*/
	public static final int SHEET_SIZE = 5000;
	
	public static final String[] SEND_ROW_HEADS = {"本部","事业部","外场","日期","前一日剩余派送量票数","前一日剩余派送量重量","前一日剩余派送量体积","当日入库货量票数","当日入库货量重量","当日入库货量体积","已派送量票数","已派送量重量","已派送量体积","派送率(F)","预计后一日派送量票数","预计后一日派送量重量","预计后一日派送量体积"};
	/** 日退单率  导出Excel文件的表头*/
	public static final String[] RETURN_ROW_HEADS = {"本部","事业部","外场","日期","排单票数","装车票数","退单票数","退单率"};
	/** 日拉回率  导出Excel文件的表头*/
	public static final String[] PULLBACK_ROW_HEADS = {"本部","事业部","外场","日期","装车票数","拉回票数","拉回率"};
	/** 累计派送率查询  导出Excel文件的表头*/
	public static final String[] SEND_LOG_ROW_HEADS = {"本部","事业部","外场","日期","前一日剩余派送量票数","前一日剩余派送量重量","前一日剩余派送量体积","当日入库货量票数","当日入库货量重量","当日入库货量体积","已派送量票数","已派送量重量","已派送量体积","日派送率(F)","月累计派送率(F)"};
	/** 累计拉回率  导出Excel文件的表头*/
	public static final String[] PULLBACK_LOG_ROW_HEADS = {"本部","事业部","外场","日期","装车票数","拉回票数","日拉回率","月累计拉回率"};
	/** 累计退单率  导出Excel文件的表头*/
	public static final String[] RETURN_LOG_ROW_HEADS = {"本部","事业部","外场","日期","排单票数","装车票数","退单票数","日退单率","月累计退单率"};
	
	
	/**
	 * 日派送量
	* @fields SEND_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年3月19日 上午9:20:44
	* @version V1.0
	*/
	public static final String SEND_SHEET_NAME = "日派送率";
	/**
	 * 日退单率
	* @fields SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午4:27:05
	* @version V1.0
	*/
	public static final String RETURN_SHEET_NAME = "日退单率";
	
	/**
	 * 日拉回率
	* @fields PULLBACK_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午4:50:34
	* @version V1.0
	*/
	public static final String PULLBACK_SHEET_NAME = "日拉回率";
	
	
	/**
	 * 累计派送率
	* @fields SEND_LOG_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午4:44:39
	* @version V1.0
	*/
	public static final String SEND_LOG_SHEET_NAME = "累计派送率";
	
	
	/**
	 * 累计拉回率
	* @fields PULLBACK_LOG_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年3月17日 下午2:43:05
	* @version V1.0
	*/
	public static final String PULLBACK_LOG_SHEET_NAME = "累计拉回率"; 
	
	
	/**
	 * 累计退单率
	* @fields RETURN_LOG_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午10:22:04
	* @version V1.0
	*/
	public static final String RETURN_LOG_SHEET_NAME = "累计退单率"; 
}
