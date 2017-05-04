package com.deppon.pda.bdm.module.core.shared.constants.version;

/**
 * PDA登陆
 * @author mt
 * 2013年7月17日18:48:49
 */
public interface LoginConstant {
	/**
	 * PDA登录
	 */
	interface OPER_TYPE_SYS_LOGIN{
		public final static String VERSION = "SYS_01";
	}

	/**
	 * PDA登出
	 */
	interface OPER_TYPE_SYS_LOGIN_OUT{
		public final static String VERSION = "SYS_02";
	}

	/**
	 * 基础数据下载
	 */
	interface OPER_TYPE_SYS_DATA_DNLD{
		public final static String VERSION = "SYS_03";
	}

	/**
	 * 程序版本更新
	 */
	interface OPER_TYPE_SYS_VER_DNLD{
		public final static String VERSION = "SYS_04";
	}
	
	/**
	 * 程序模块版本更新 mt 2013年8月16日14:46:43
	 */
	interface OPER_TYPE_SYS_MODULE_VER_DNLD{
		public final static String VERSION = "SYS_05";
	}
	
	/**
	 * pdam项目基础资料下载 yangdm 2015/5/13
	 */
	interface OPER_TYPE_DPAM_SYS_DATA_DNLD{
	public final static String VERSION = "SYS_06";
	}

	/**
	 * pdam项目经纬度数据保存 yangdm 2015/5/22
	 */
	interface OPER_TYPE_DPAM_SYS_POSITION{
	public final static String VERSION = "GPS_02";
	}
	//非现金盘点项目操作
	/**
	 * 登录
	 */
	interface OPER_TYPE_NCI_LOGIN{
		public final static String VERSION = "NCI_01";
	}
	/**
	 * 登出
	 */
	interface OPER_TYPE_NCI_LOGIN_OUT{
		public final static String VERSION = "NCI_02";
	}
	/**
	 * 外请车司机登录
	 */
	interface OPER_TYPE_TPS_LOGIN{
		public final static String VERSION = "TPS_01";
	}

}
