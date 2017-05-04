package com.deppon.pda.bdm.module.core.shared.constants.version;

/**
 * 装车
 * 
 * @author mt 2013年7月17日18:42:05
 */
public interface LoadConstant {
	/**
	 * 获取装车指令
	 */
	interface OPER_TYPE_LOAD_TASK_GET {
		public final static String VERSION = "LOAD_01";
	}

	/**
	 * 建立装车任务
	 */
	interface OPER_TYPE_LOAD_TASK_CREATE {
		public final static String VERSION = "LOAD_02";
	}

	/**
	 * 刷新装车任务
	 */
	interface OPER_TYPE_LOAD_TASK_RFSH {
		public final static String VERSION = "LOAD_03";
	}

	/**
	 * 装车扫描
	 */
	interface OPER_TYPE_LOAD_SCAN {
		public final static String VERSION = "LOAD_04";
	}

	/**
	 * 完成装车任务
	 */
	interface OPER_TYPE_LOAD_FINISH {
		public final static String VERSION = "LOAD_05";
	}

	/**
	 * 提交装车任务
	 */
	interface OPER_TYPE_LOAD_SUBMIT {
		public final static String VERSION = "LOAD_06";
	}

	/**
	 * 未装车备注
	 */
	interface OPER_TYPE_LOAD_REMARK {
		public final static String VERSION = "LOAD_07";
	}

	/**
	 * 撤销装车扫描
	 */
	interface OPER_TYPE_LOAD_CANCEL {
		public final static String VERSION = "LOAD_08";
	}

	/**
	 * 录入封签
	 */
	interface OPER_TYPE_LOAD_SEALS_ADD {
		public final static String VERSION = "LOAD_09";
	}

	/**
	 * 上报封签异常
	 */
	interface OPER_TYPE_LOAD_SEALS_EXCP {
		public final static String VERSION = "LOAD_10";
	}

	/**
	 * 增加/删除理货员
	 */
	interface OPER_TYPE_LOAD_OPR_DEL_ADD {
		public final static String VERSION = "LOAD_11";
	}

	/**
	 * 撤销装车任务
	 */
	interface OPER_TYPE_LOAD_TASK_CACL {
		public final static String VERSION = "LOAD_12";
	}

	/**
	 * 多货强装扫描
	 */
	interface OPER_TYPE_LOAD_CHECK_LOAD {
		public final static String VERSION = "LOAD_13";
	}

	/**
	 * 获取快递派送装车指令
	 */
	interface OPER_TYPE_KD_LOAD_TASK_GET {
		public final static String VERSION = "LOAD_14";
	}

	/**
	 * 建立快递派送装车任务接口
	 */
	interface OPER_TYPE_KD_LOAD_TASK_CREATE {
		public final static String VERSION = "LOAD_15";
	}

	/**
	 * 撤销快递派送装车任务
	 */
	interface OPER_TYPE_KD_LOAD_TASK_CACL {
		public final static String VERSION = "LOAD_16";
	}

	/**
	 * 提交快递派送装车任务
	 */
	interface OPER_TYPE_KD_LOAD_SUBMIT {
		public final static String VERSION = "LOAD_17";
	}

	/**
	 * 快递派送装车扫描
	 */
	interface OPER_TYPE_KD_LOAD_SCAN {
		public final static String VERSION = "LOAD_18";
	}

	/**
	 * 快递派送装车扫描-撤销
	 */
	interface OPER_TYPE_KD_LOAD_CANCEL {
		public final static String VERSION = "LOAD_19";
	}

	/**
	 * 零担派送装车刷新任务
	 */
	interface OPER_TYPE_LOAD_TASK_DISP {
		public final static String VERSION = "LOAD_DISP_REF";
	}

	/**
	 * 快递装车提交校验（是否存在多件货物异常）
	 */
	interface OPER_TYPE_EXP_LOAD_SUB_CHE {
		public final static String VERSION = "EXP_LOAD_SUBCHECK";
	}

	/**
	 * 刷新包明细（装车）
	 */
	interface OPER_TYPE_LOAD_RFSHLOAD {
		public final static String VERSION = "LOAD_REF_PACK_LIST";
	}

	/**
	 * 刷新快递装车任务明细
	 */
	interface OPER_TYPE_LOAD_RFSEXPDETAIL {
		public final static String VERSION = "LOAD_REF_EXP_LIST";
	}

	/**
	 * 派送装车运单退回
	 */
	interface OPER_TYPE_LOAD_WAYBILLRETURN {
		public final static String VERSION = "LOAD_21";
	}

	// 理货员PDA快递接驳装车
	/**
	 * 获取装车任务
	 */
	interface OPER_TYPE_LOAD_TRAN_GET {
		public final static String VERSION = "TWOWAY_LOAD_01";
	}

	/**
	 * 下拉接驳点
	 */
	interface OPER_TYPE_LOAD_DOWN_TRAN {
		public final static String VERSION = "TWOWAY_LOAD_02";
	}

	/**
	 * 创建理货员接驳任务
	 */
	interface OPER_TYPE_LOAD_CREATE_TRAN {
		public final static String VERSION = "TWOWAY_LOAD_03";
	}

	/**
	 * 刷新装车任务明细
	 */
	interface OPER_TYPE_LOAD_RFSH_TRAN {
		public final static String VERSION = "TWOWAY_LOAD_04";
	}

	/**
	 * 接驳装车扫描
	 */
	interface OPER_TYPE_LOAD_SCAN_TRAN {
		public final static String VERSION = "TWOWAY_LOAD_05";
	}

	/**
	 * 撤销装车扫描
	 */
	interface OPER_TYPE_LOAD_TRAN_CANCEL {
		public final static String VERSION = "TWOWAY_LOAD_06";
	}

	/**
	 * 提交接驳装车任务
	 */
	interface OPER_TYPE_LOAD_SMT_TRAN {
		public final static String VERSION = "TWOWAY_LOAD_07";
	}

	/**
	 * 撤销装车任务
	 */
	interface OPER_TYPE_LOAD_SANCEL_TRAN {
		public final static String VERSION = "TWOWAY_LOAD_08";
	}

	/**
	 * 完成接驳装车任务
	 */
	interface OPER_TYPE_LOAD_TRAN_FNSH {
		public final static String VERSION = "TWOWAY_LOAD_09";
	}

	// 快递员PDA派件交接
	/**
	 * 获取派件指令
	 */
	interface OPER_TYPE_DELIVERY_TASK_GET {
		public final static String VERSION = "TWOWAY_DERY_01";
	}

	/**
	 * 创建派件交接任务
	 */
	interface OPER_TYPE_DELIVERY_TASK_CAEATE {
		public final static String VERSION = "TWOWAY_DERY_02";
	}

	/**
	 * 下拉派件交接明细
	 */
	interface OPER_TYPE_DELIVERY__TASK_DETAIL_GET {
		public final static String VERSION = "TWOWAY_DERY_03";
	}

	/**
	 * 派件交接扫描
	 */
	interface OPER_TYPE_DELIVERY_TASK_SCAN {
		public final static String VERSION = "TWOWAY_DERY_04";
	}

	/**
	 * 提交派件交接任务
	 */
	interface OPER_TYPE_DELIVERY_TASK_SUBMIT {
		public final static String VERSION = "TWOWAY_DERY_05";
	}

	/**
	 * 删除任务
	 */
	interface OPER_TYPE_DELIVERY_TASK_DELETE {
		public final static String VERSION = "TWOWAY_DERY_06";
	}

	/**
	 * 派件交接扫描-撤销
	 */
	interface OPER_TYPE_DELIVERY_TASK_SCAN_CANCEL {
		public final static String VERSION = "TWOWAY_DERY_07";
	}

	// 司机PDA接驳装车
	/**
	 * 获取装车指令
	 */
	interface OPER_TYPE_DRIVER_TASK_GET {
		public final static String VERSION = "TWOWAY_DRIV_LOAD_01";
	}

	/**
	 * 创建交接任务
	 */
	interface OPER_TYPE_DRIVER_TASK_CREATE {
		public final static String VERSION = "TWOWAY_DRIV_LOAD_02";
	}

	/**
	 * 下拉对于快递员接货明细
	 */
	interface OPER_TYPE_DRIVER_RASK_DETAIL {
		public final static String VERSION = "TWOWAY_DRIV_LOAD_03";
	}

	/**
	 * 交接扫描
	 */
	interface OPER_TYPE_DRIVER_TASK_SCAN {
		public final static String VERSION = "TWOWAY_DRIV_LOAD_04";
	}

	/**
	 * 撤销扫描
	 */
	interface OPER_TYPE_DRIVER_TASK_CANCEL {
		public final static String VERSION = "TWOWAY_DRIV_LOAD_05";
	}

	/**
	 * 提交当前交接任务
	 */
	interface OPER_TYPE_DRIVER_TASK_SUBMIT {
		public final static String VERSION = "TWOWAY_DRIV_LOAD_06";
	}

	/**
	 * 完成所有任务
	 */
	interface OPER_TYPE_DRIVER_TASK_FNSH {
		public final static String VERSION = "TWOWAY_DRIV_LOAD_07";
	}

	/**
	 * 撤销任务
	 */
	interface OPER_TYPE_DRIVER_TASK_DELETE {
		public final static String VERSION = "TWOWAY_DRIV_LOAD_08";
	}

	/**
	 * 绑定封签 314587
	 * 
	 */
	interface WK_LOAD_SAVE_LABLE {
		public final static String VERSION = "WK_PDALOAD_01";
	}

	/**
	 * 
	 * 校验封签 314587
	 * 
	 */
	interface WK_LOAD_DELETE_LABLE {
		public final static String VERSION = "WK_PDALOAD_02";
	}

	/**
	 * 
	 * 查询车辆到达/出发信息 314587
	 * 
	 */
	interface WK_LOAD_SEARCH_CAR_ARRIVE_OR_DEPART {
		public final static String VERSION = "WK_PDALOAD_03";
	}

	/**
	 * 
	 * 发车/取消发车/确认车辆到达 314587
	 * 
	 */
	interface WK_LOAD_DEPART_OR_CANCEL_OR_CANCEL_DEPART {
		public final static String VERSION = "WK_PDALOAD_04";
	}

	// ************灰度***************
	/**
	 * 灰度-快递创建装车任务
	 */
	interface CRETE_LOAD_TASK {
		public final static String VERSION = "LOAD_ADAPTER_02";
	}

	/**
	 * @需求：营业部与营业部交接
	 * @功能：建立营业部交接装车任务
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-11-10上午9:51
	 */
	interface OPER_TYPE_SALES_DEPARTMENT_TASK_CREATE {
		public final static String VERSION = "LOAD_SALEDEPT_01";// LOAD_SALEDEPT_02
	}

	/**
	 * @需求：营业部与营业部交接
	 * @功能：快递员当前城市的所有营业区及对应营业部 OPER_TYPE_EXP_RCV_QUDE_YINGYEQU_YINGYEBU
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-11-10上午9:51
	 */
	interface OPER_TYPE_SALES_DEPARTMENT_QUDE_YINGYEQU_YINGYEBU {
		public final static String VERSION = "LOAD_SALEDEPT_02";// LOAD_SALEDEPT_04
	}

	/**
	 * @需求：营业部与营业部交接
	 * @功能：下拉营业部装车任务零担明细
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-11-10上午9:51
	 */
	interface OPER_TYPE_SALES_DEPARTMENT_TASK_RFSH {
		public final static String VERSION = "LOAD_SALEDEPT_03";// LOAD_SALEDEPT_05
	}

	/**
	 * @需求：营业部与营业部交接
	 * @功能：下拉营业部装车任务快递明细
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-11-10上午9:51
	 */
	interface OPER_TYPE_SALES_DEPARTMENT_RFSEXPDETAIL {
		public final static String VERSION = "LOAD_SALEDEPT_04";// LOAD_SALEDEPT_06
	}

	/**
	 * @需求：营业部与营业部交接
	 * @功能：下拉营业部装车任务包列表
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-11-10上午9:51
	 */
	interface OPER_TYPE_SALES_DEPARTMENT_RFSHLOAD {
		public final static String VERSION = "LOAD_SALEDEPT_05";// LOAD_SALEDEPT_07
	}

	// ************灰度***************
}