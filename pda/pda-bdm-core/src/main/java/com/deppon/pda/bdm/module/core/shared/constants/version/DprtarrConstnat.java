package com.deppon.pda.bdm.module.core.shared.constants.version;

/**
 * 车辆出发到达
 * @author mt
 * 2013年7月17日18:46:52
 */
public interface DprtarrConstnat {
	/**
	 * 记录车辆出发信息
	 */
	interface OPER_TYPE_DPRT_INFO_SCAN{
		public final static String VERSION = "DERT_01";
	}

	/**
	 * 记录车辆到达信息
	 */
	interface OPER_TYPE_ARR_INFO_SCAN{
		public final static String VERSION = "ARR_01";
	}
	
	/**
	 * 获取月台预分配
	 */
	interface OPER_TYPE_QRY_PLATFORM_NO{
		public final static String VERSION = "PLAT_01";
	}
	
}
