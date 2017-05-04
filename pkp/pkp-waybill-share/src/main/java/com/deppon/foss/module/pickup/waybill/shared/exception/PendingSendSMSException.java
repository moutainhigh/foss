package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 待处理发送信息异常
 * @author WangQianJin
 * @date 2013-4-11 上午11:26:54
 */
public class PendingSendSMSException extends BusinessException {

	/**
	 * 待处理发送短信实体为空！
	 */
	public static final String PENDING_SENDMAIL_ENTITY_IS_NULL = "foss.pendingSendmailException.pendingSendmailEntityIsNull";
	/**
	 * ID为空！
	 */
	public static final String PENDING_SENDMAIL_ID_IS_NULL = "foss.pendingSendmailException.pendingSendmailIdIsNull";
	/**
	 * 运单号为空！
	 */
	public static final String PENDING_SENDMAIL_WAYBILL_NO_IS_NULL = "foss.pendingSendmailException.pendingSendmailWaybillNoIsNull";
	/**
	 * 通知类型为空！
	 */
	public static final String PENDING_SENDMAIL_NOTICE_TYPE_IS_NULL = "foss.pendingSendmailException.pendingSendmailNoticeTypeIsNull";
	/**
	 * 通知内容为空！
	 */
	public static final String PENDING_SENDMAIL_NOTICE_CONTENT_IS_NULL = "foss.pendingSendmailException.pendingSendmailNoticeContentIsNull";
	/**
	 * 操作人为空！
	 */
	public static final String PENDING_SENDMAIL_OPERATOR_IS_NULL = "foss.pendingSendmailException.pendingSendmailOperatorIsNull";
	/**
	 * 操作人编码为空！
	 */
	public static final String PENDING_SENDMAIL_OPERATOR_NO_IS_NULL = "foss.pendingSendmailException.pendingSendmailOperatorNoIsNull";
	/**
	 * 操作部门名称为空！
	 */
	public static final String PENDING_SENDMAIL_OPERATE_ORG_NAME_IS_NULL = "foss.pendingSendmailException.pendingSendmailOperateOrgNameIsNull";
	/**
	 * 操作部门编码为空！
	 */
	public static final String PENDING_SENDMAIL_OPERATE_ORG_CODE_IS_NULL = "foss.pendingSendmailException.pendingSendmailOperateOrgCodeIsNull";
	/**
	 * 接收人姓名为空！
	 */
	public static final String PENDING_SENDMAIL_CONSIGNEE_IS_NULL = "foss.pendingSendmailException.pendingSendmailConsigneeIsNull";
	/**
	 * 手机号为空！
	 */
	public static final String PENDING_SENDMAIL_MOBILE_IS_NULL = "foss.pendingSendmailException.pendingSendmailMobileIsNull";
	/**
	 * 操作时间为空！
	 */
	public static final String PENDING_SENDMAIL_OPERATE_TIME_IS_NULL = "foss.pendingSendmailException.pendingSendmailOperateTimeIsNull";
	/**
	 * 模块名称为空！
	 */
	public static final String PENDING_SENDMAIL_MODULE_NAME_IS_NULL = "foss.pendingSendmailException.pendingSendmailModuleNameIsNull";	
	/**
	 * 发货人名称为空！
	 */
	public static final String PENDING_SENDMAIL_DELIVERY_CUSTOMER_CONTACT_IS_NULL = "foss.pendingSendmailException.pendingSendmailDeliveryCustomerContactIsNull";
	/**
	 * 总件数为空！
	 */
	public static final String PENDING_SENDMAIL_GOODS_QTY_TOTAL_IS_NULL = "foss.pendingSendmailException.pendingSendmailGoodsQtyTotalIsNull";
	/**
	 * 总费用为空！
	 */
	public static final String PENDING_SENDMAIL_TOTAL_FEE_IS_NULL = "foss.pendingSendmailException.pendingSendmailTotalFeeIsNull";
	
	
	
	
	/**
	 * 生成序列号信息
	 */
	private static final long serialVersionUID = -852125550461262599L;


	/**
	 *  （创建一个新的实例 ）PendingSendmailException
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	public PendingSendSMSException(String code) {
		super(code);
		this.errCode=code;
	}

	/**
	 *  （创建一个新的实例 ）PendingSendmailException
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	public PendingSendSMSException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode=msg;
	}

	/**
	 *  （创建一个新的实例 ）PendingSendmailException
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	public PendingSendSMSException(String code, String msg) {
		super(code, msg);
	}
	
	/**
	 *  （创建一个新的实例 ）PendingSendmailException
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	public PendingSendSMSException(String code,Object... args) {
		super(code,args);
		this.errCode = code;
	}

	/**
	 *  （创建一个新的实例 ）PendingSendmailException
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	public PendingSendSMSException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 *  （创建一个新的实例 ）PendingSendmailException
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	public PendingSendSMSException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 *  （创建一个新的实例 ）PendingSendmailException
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	public PendingSendSMSException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}
}
