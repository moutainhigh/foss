package com.deppon.foss.module.pickup.predeliver.api.shared.define;

/** 
 * @ClassName: HandoverBillConstants 
 * @Description: 
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-12 上午8:32:54 
 *  
 */
public class HandoverBillExceptionConstants {
	
	
	/**
	 * 访问Gis接口出现异常
	 */
	public final static String EXCEPTION_GIS = "gis_exception";
	
	/**
	 * 定人定区service出现异常
	 */
	public final static String EXCEPTION_REGIONALVEHICLE = "regional_vehicle_exception";
	
	/**
	 * 排班任务Service出现异常
	 */
	public final static String EXCEPTION_PAIBAN = "paiban_exception";
	
	/**
	 * 已交单VoService 出现异常
	 */
	public final static String EXCEPTION_HANDOVERBILL = "handoverBill_exception";
	
	/**
	 * 特殊送货地址Service出现异常
	 */
	public final static String EXCEPTION_SPECIALADDRESS = "special_address_exception";
	
	/**
	 * 检查是否节假日Service出现异常
	 */
	public final static String EXCEPTION_ISHOLIDAY = "is_holiday_exception";
	
	/**
	 * 查询小区出现异常
	 */
	public final static String EXCEPTION_QUERY_SMALLZONE = "query_smallzone_exception";
	
	/**
	 * 调用GIS自动排序接口异常
	 */
	public final static String EXCEPTION_GIS_AUTO_SORT = "gis_auto_sort_exception"; 
	
	/**
	 * 根据坐标找小区调用GIS接口异常
	 */
	public final static String EXCEPTION_GIS_FIND_CROOD = "gis_findSmall_by_crood_exception";
	
	/**
	 * 调用GIS接口新增、修改历史库记录异常
	 */
	public final static String EXCEPTION_GIS_SAVE_UPDATE_FOR_HIS= "gis_save_update_his";
	
	/**
	 * 调用GIS接口作废历史库记录异常
	 */
	public final static String EXCEPTION_GIS_DELETE_FOR_HIS = "gis_delete_his";
	
}
