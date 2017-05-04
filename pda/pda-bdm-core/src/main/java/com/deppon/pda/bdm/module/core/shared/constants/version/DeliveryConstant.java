package com.deppon.pda.bdm.module.core.shared.constants.version;

/**
 * 派送
 * @author mt
 * 2013年7月17日18:40:52
 */
public interface DeliveryConstant {
	/**
	 * 快递-获取派送任务
	 * @author 245955
	 *
	 */
	interface OPER_TYPE_KDDERY_TASK_GET{
		public final static String VERSION = "EXP_DERY_01";
	}
	/**
	 *子母件正常签收
	 * @author 245955 
	 *
	 */
	interface OPER_TYPE_KDDERY_PARENT_SIGN{
		public final static String VERSION = "EXP_DERY_02";
	}
	
	/**
	 * 获取派送任务
	 */
	interface OPER_TYPE_DERY_TASK_GET{
		public final static String VERSION = "DERY_01";
	}

	/**
	 * 按件正常签收
	 */
	interface OPER_TYPE_DERY_PC_SIGN_NORM{
		public final static String VERSION = "DERY_02";
	}

	/**
	 * 按票正常签收
	 */
	interface OPER_TYPE_DERY_VOTE_SIGN_NORM{
		public final static String VERSION = "DERY_03";
	}

	/**
	 * 异常签收
	 */
	interface OPER_TYPE_DERY_SIGN_EXCP{
		public final static String VERSION = "DERY_04";
	}

	/**
	 * 派送异常
	 */
	interface OPER_TYPE_DERY_EXCP{
		public final static String VERSION = "DERY_05";
	}

	/**
	 * 获取自提货物信息（到达联校验）
	 */
	interface OPER_TYPE_DERY_WBL_INFO_GET{
		public final static String VERSION = "DERY_06";
	}
	
	/**
	 * 客户自提
	 */
	interface OPER_TYPE_DERY_SFDEL{
		public final static String VERSION = "DERY_07";
	}

	/**
	 * 反馈任务已接收状态
	 */
	interface OPER_TYPE_DERY_FINISH_DERY{
		public final static String VERSION = "DERY_08";
	}
	
	/**
	 * 快递按件正常签收
	 */
	interface OPER_TYPE_KD_DERY_PC_SIGN_NORM{
		public final static String VERSION = "DERY_09";
	}
	
	/**
	 * 快递异常按件签收接口
	 */
	interface OPER_TYPE_KD_DERY_SIGN_EXCP{
		public final static String VERSION = "DERY_10";
	}
	
	
	/**
	 * 提货清单接口
	 */
	interface OPER_TYPE_SELF_PROVIDED_LIST{
		public final static String VERSION = "DERY_11";
	}
	
	/**
	 * 撤销提货清单
	 */
	interface OPER_TYPE_CAL_SELF_PROVIDED{
		public final static String VERSION = "DERY_12";
	}
	
	/**
	 * 撤销提货清单
	 */
	interface OPER_TYPE_RETURN_BILLING_PROVIDED{
		public final static String VERSION = "RVC_DERY_11";
	}
	
	/**
	 * 零担获取运单付款信息
	 */
	interface OPER_TYPE_RETURN_BILLING_PAY_MESSAGE{
		public final static String VERSION = "DERY_13";
	}
	/**
	 * 批量插入T+0
	 */
	interface OPER_TYPE_SWIPING_CARD_DETAIL{
		public final static String VERSION = "DERY_14";
	}
	//非现金盘点项目所用到的操作类型
	/**
	 * 查询登录人对应部门的所有待刷卡数据
	 */
	interface OPER_TYPE_All_NOT_BUSH_CARD{
		public final static String VERSION = "NCI_DERY_01";
	}
	/**
	 * 获得待刷卡模块刷卡成功数据
	 */
	interface OPER_TYPE_NOT_CARD_SUCCESS{
		public final static String  VERSION = "NCI_DERY_02";
	}
	/**
	 * 通过单号查询对账单数据
	 */
	interface OPER_TYPE_ACCOUNT_STATEMENT{
		public final static String VERSION = "NCI_DERY_03";
	}
	/**
	 * 获得对账单模块刷卡成功数据
	 */
	interface OPER_TYPE_ACCOUNT_STATEMENT_SUCCESS{
		public final static String VERSION = "NCI_DERY_04";
	}
	/**
	 * 通过单号查询结清货款模块数据
	 */
	interface OPER_TYPE_SETTLE_PAYMENT_BY_NO{
		public final static String VERSION = "NCI_DERY_05";
	}
	/**
	 * 结清货款模块刷卡成功数据
	 */
	interface OPER_TYPE_SETTLE_PAYMENT_SUCCESS{
		public final static String VERSION = "NCI_DERY_06";
	}
	/**
	 * 查询司机的服务部门
	 */
	interface OPER_TYPE_DRIVEY_DEPT{
		public final static String VERSION = "NCI_DERY_07";
	}
	/**
	 * 预存卡刷卡成功
	 */
	interface OPER_TYPE_PRE_PAYMENT{
		public final static String VERSION = "NCI_DERY_08";
	}

	/**
	 * 运单核销检验
	 */
	interface OPER_TYPE_WAYBILL_CHECK{
		public final static String VERSION = "NCI_DERY_09";
	}
	/**
	 * 支付宝扫码支付
	 */
	interface OPER_TYPE_ALIPAY_SCAN{
		public final static String VERSION = "NCI_DERY_10";
	}
	/**
	 * 零担获取运单付款信息
	 */
	interface OPER_TYPE_UPLOAD_ELECTRONIC_TICKET{
		public final static String VERSION = "IMAG_UPLOAD_01";
	}
	/**
	  * 接送货异常短信推送
	  *  
	  */
	interface OPER_TYPE_DELIVER_SEND_SMS{
		public final static String VERSION = "SMS_DELIVER_01";
	}
	/**
	 * 获取接送货司机电话号码
	 */
	interface OPER_TYPE_DRIVERPHONE_GET{
		public final static  String VERSION="GET_DRIVERPHONE_01";
		
	}
 
}
