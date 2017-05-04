package com.deppon.pda.bdm.module.core.shared.constants.version;

/**
 * 包管理
 * @author mt
 * 2013年7月17日18:43:14
 */
public interface PackageManagerConstant {
	/**
	 * 创建装车建包任务
	 */
	interface OPER_TYPE_PACKAGE_TASK_CREATE{
		public final static String VERSION = "PM_01";
	}
	
	/**
	 * 撤销建包任务
	 */
	interface OPER_TYPE_PACKAGE_TASK_CACL{
		public final static String VERSION = "PM_02";
	}
	
	/**
	 * 撤销-建包扫描
	 */
	interface OPER_TYPE_PACKAGE_SCAN_CANCEL{
		public final static String VERSION = "PM_03";
	}
	
	/**
	 * 建包扫描
	 */
	interface OPER_TYPE_PACKAGE_SCAN{
		public final static String VERSION = "PM_04";
	}
	
	/**
	 * 获取建包指令
	 */
	interface OPER_TYPE_PACKAGE_TASK_GET{
		public final static String VERSION = "PM_05";
	}
	
	/**
	 * 刷新建包货物明细
	 */
	interface OPER_TYPE_PACKAGE_TASK_RFSH{
		public final static String VERSION = "PM_06";
	}
	
	/**
	 * 强扫接口
	 */
	interface OPER_TYPE_PACKAGE_CHECK_SCAN{
		public final static String VERSION = "PM_07";
	}
	
	/**
	 * 提交建包任务
	 */
	interface OPER_TYPE_PACKAGE_SUBMIT_TASK{
		public final static String VERSION = "PM_08";
	}
	
	/**
	 * @author 245955
	 *建包扫描生成目的站
	 */
	interface OPER_TYPE_PACKAGE_SITE_CREATE{
		public final static String VERSION = "PM_09";
	}
	
	/**
	 * 直达包
	 */
	interface OPER_TYPE_PACKAGE_ThroughPack{
		public final static String VERSION = "PM_through";
	}
}
