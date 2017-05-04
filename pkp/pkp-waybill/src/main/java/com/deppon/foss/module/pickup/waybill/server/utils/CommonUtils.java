package com.deppon.foss.module.pickup.waybill.server.utils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

public class CommonUtils {
	/**
	 * 根据提货方式判断：是否自提
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-26 下午3:52:17
	 */
	public static boolean verdictPickUpSelf(String pickupType){
		String type = StringUtil.defaultIfNull(pickupType);
		if (WaybillConstants.SELF_PICKUP.equals(type)
				|| WaybillConstants.INNER_PICKUP.equals(type)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(type)
				|| WaybillConstants.AIRPORT_PICKUP.equals(type)
				|| WaybillConstants.AIR_SELF_PICKUP.equals(type)) 
		{
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据提货方式判断：是否送货上门 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-26 下午3:32:48
	 */
	public static boolean verdictPickUpDoor(String pickupType) {
		String type = StringUtil.defaultIfNull(pickupType);
		/**
		 * DEFECT-6897，根据业务要求增加判断条件，其它不变
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-19下午18:07
		 */
		if (WaybillConstants.DELIVER_FREE.equals(type) 
				|| WaybillConstants.DELIVER_STORAGE.equals(type) 
				|| WaybillConstants.DELIVER_UP.equals(type)
				|| WaybillConstants.DELIVER_NOUP.equals(type) 
				|| WaybillConstants.DELIVER_FREE_AIR.equals(type)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(type) 
				|| WaybillConstants.DELIVER_UP_AIR.equals(type)
				|| WaybillConstants.DELIVER_INGA_AIR.equals(type)
				|| WaybillConstants.LARGE_DELIVER_UP.equals(type)
				|| WaybillConstants.LARGE_DELIVER_UP_AIR.equals(type)) 
		{
			return true;
		} else {
			return false;
		}
	}
}
