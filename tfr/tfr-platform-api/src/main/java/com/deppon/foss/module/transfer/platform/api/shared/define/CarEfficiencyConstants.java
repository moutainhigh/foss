package com.deppon.foss.module.transfer.platform.api.shared.define;


/**
* @description 车辆装卸车效率的 常量定义
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年5月6日 下午3:02:58
*/
public class CarEfficiencyConstants {
	
	/**  导出Excel文件的sheet最大行数*/
	public static final int SHEET_SIZE = 5000;
	
	
	/**长途  导出Excel文件的表头*/
	public static final String[] CAREFFICIENCY_LONG_WAY_ROW_HEADS = {"日期","车次号","车牌号","外场","操作类型","重量(T)","任务建立时间","任务完成时间","时长(H)","效率","是否合格"};
	/**短途  导出Excel文件的表头*/
	public static final String[] CAREFFICIENCY_SHORT_WAY_ROW_HEADS ={"日期","车牌号","外场","操作类型","重量(T)","任务建立时间","任务完成时间","时长(H)","效率","是否合格"};
	
	
	/**
	 * 长途车辆装卸车效率
	* @fields CAREFFICIENCY_LONG_WAY_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午3:06:47
	* @version V1.0
	*/
	public static final String CAREFFICIENCY_LONG_WAY_SHEET_NAME = "长途车辆装卸车效率";
	
	/**
	 * 短途车辆装卸车效率
	* @fields CAREFFICIENCY_SHOET_WAY_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午3:06:49
	* @version V1.0
	*/
	public static final String CAREFFICIENCY_SHOET_WAY_SHEET_NAME = "短途车辆装卸车效率";
	
	
	/**
	 * 装车
	* @fields CAREFFICIENCY_TYPE_LOAD
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午3:19:20
	* @version V1.0
	*/
	public static final String CAREFFICIENCY_TYPE_LOAD="1";
	
	/**
	 * 卸车
	* @fields CAREFFICIENCY_TYPE_UNLOAD
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午3:19:23
	* @version V1.0
	*/
	public static final String CAREFFICIENCY_TYPE_UNLOAD="2";
	
	
	/**
	 * 合格
	* @fields CAREFFICIENCY_FLAG_YES
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午3:19:27
	* @version V1.0
	*/
	public static final String CAREFFICIENCY_FLAG_YES="1";
	
	/**
	 * 不合格
	* @fields CAREFFICIENCY_FLAG_NO
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午3:19:30
	* @version V1.0
	*/
	public static final String CAREFFICIENCY_FLAG_NO="0";

}
