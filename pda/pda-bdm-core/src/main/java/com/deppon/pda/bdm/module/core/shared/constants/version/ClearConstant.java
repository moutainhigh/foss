package com.deppon.pda.bdm.module.core.shared.constants.version;

/**
 * 清仓
 * 
 * @author mt 2013年7月17日18:43:36
 */
public interface ClearConstant {
	/**
	 * 获取库区信息
	 */
	interface OPER_TYPE_CLEAR_AREA_GET {
		public final static String VERSION = "CLEAR_01";
	}

	/**
	 * 建立清仓任务
	 */
	interface OPER_TYPE_CLEAR_TASK_CREATE {
		public final static String VERSION = "CLEAR_02";
	}

	/**
	 * 刷新清仓任务
	 */
	interface OPER_TYPE_CLEAR_TASK_RFSH {
		public final static String VERSION = "CLEAR_03";
	}

	/**
	 * 清仓扫描
	 */
	interface OPER_TYPE_CLEAR_SCAN {
		public final static String VERSION = "CLEAR_04";
	}

	/**
	 * 提交清仓任务
	 */
	interface OPER_TYPE_CLEAR_TASK_SUBMIT {
		public final static String VERSION = "CLEAR_05";
	}

	/**
	 * 完成清仓任务
	 */
	interface OPER_TYPE_CLEAR_TASK_FINISH {
		public final static String VERSION = "CLEAR_06";
	}

	/**
	 * 撤销清仓任务
	 */
	interface OPER_TYPE_CLEAR_TASK_CANCEL {
		public final static String VERSION = "CLEAR_07";
	}

	/**
	 * 获取清仓差异报告接口
	 */
	interface OPER_TYPE_CLEAR_EXCE_REPORT {
		public final static String VERSION = "CLEAR_08";
	}

	/**
	 * 获取清仓差异报告明细接口
	 */
	interface OPER_TYPE_CLEAR_EXCE__DETAIL {
		public final static String VERSION = "CLEAR_09";
	}

	/**
	 * 上传清仓差异报告扫描明细
	 */
	interface OPER_TYPE_CLEAR_EXCE_SCAN {
		public final static String VERSION = "CLEAR_10";
	}

	/**
	 * 提交清仓差异报告
	 */
	interface OPER_TYPE_CLEAR_EXCE_SUBMIT {
		public final static String VERSION = "CLEAR_11";
	}

	/**
	 * 获取清仓时分区
	 */
	interface OPER_TYPE_CLEAR_ADMINISTRATIVEREGION {
		public final static String VERSION = "CLEAR_12";
	}
	
	/**
	 * 获取包清仓列表
	 */
	interface OPER_TYPE_CLEAR_REFRESH_PACKAGE {
		public final static String VERSION = "CLEAR_13";
	}
	
	/**
	 * 包扫描提交
	 */
	interface OPER_TYPE_CLEAR_PACAGE_SCAN {
		public final static String VERSION = "CLEAR_14";
	}
	
	
	/**
	 * 获取找货任务货区
	 */
	interface OPER_TYPE_CLEAR_SEARCH_GET {
		public final static String VERSION = "CLEAR_15";
	}
	
	/**
	 * 获取找货任务明细
	 */
	interface OPER_TYPE_CLEAR_SEARCH_DETAIL {
		public final static String VERSION = "CLEAR_16";
	}
	
	/**
	 *  找货数据扫描上传
	 */
	interface OPER_TYPE_CLEAR_SEARCH_SCAN {
		public final static String VERSION = "CLEAR_17";
	}
	
	/**
	 * 提交找货任务
	 */
	interface OPER_TYPE_CLEAR_SEARCH_SUBMIT {
		public final static String VERSION = "CLEAR_18";
	}
	
	/**
	 * 判断异常货区
	 * author:268974 Date:2015-11-14 comment:2015-11-26日常版本需求
	 */
	interface OPER_TYPE_CLEAR_GOODSAREA_DECIDE{
		public final static String VERSION = "CLEAR_19";
	}
	/**
	 * 提交清仓任务校验用户提交密码
	 */
	interface OPER_TYPE_CLEAR_CHECK_PWD{
		public final static String VERSION = "CLEAR_20";
	}
	/**
	 * 提交清仓任务修改用户提交密码
	 */
	interface OPER_TYPE_CLEAR_UPDATE_PWD{
		public final static String VERSION = "CLEAR_21";
	}
	/**
	 * 重置密码为登录APP的密码
	 */
	interface OPER_TYPE_CLEAR_RESET_PWD{
		public final static String VERSION = "CLEAR_22";
	}
}
