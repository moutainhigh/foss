package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 待处理发送短信表日志类
 * @author WangQianJin
 * @date 2013-4-11 上午9:26:00
 */
public class PendingSendSMSLogEntity {

	/**
	 * 编号
	 */
	private String id;
	/**
	 * 任务编号
	 */
    private String jobId;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 通知类型
	 */
	private String noticeType;
	/**
	 * 通知内容
	 */
	private String noticeContent;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 操作人编码
	 */
	private String operatorNo;
	/**
	 * 操作部门
	 */
	private String operateOrgName;
	/**
	 * 操作部门编码
	 */
	private String operateOrgCode;
	/**
	 * 接收人姓名
	 */
	private String consignee;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 模块名称
	 */
	private String moduleName;
	/**
	 * 发货客户联系人
	 */
	private String deliveryCustomerContact;
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 收货具体地址
	 */
	private String receiveCustomerAddress;
	/**
	 * 总费用
	 */
	private BigDecimal totalFee;
	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;
	/**
	 * 发送目标(发货人或者收货人) 
	 */
	private String sendTarget;
	/**
	 * 操作类型
	 */
    private String operateType;
    /**
	 * 失败原因
	 */
    private String faileReason;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getJobId() {
		return jobId;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getWaybillNo() {
		return waybillNo;
	}
	
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	public String getNoticeType() {
		return noticeType;
	}
	
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	
	public String getNoticeContent() {
		return noticeContent;
	}
	
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getOperatorNo() {
		return operatorNo;
	}
	
	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}
	
	public String getOperateOrgName() {
		return operateOrgName;
	}
	
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}
	
	public String getOperateOrgCode() {
		return operateOrgCode;
	}
	
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}
	
	public String getConsignee() {
		return consignee;
	}
	
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Date getOperateTime() {
		return operateTime;
	}
	
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	
	public String getSendTarget() {
		return sendTarget;
	}

	
	public void setSendTarget(String sendTarget) {
		this.sendTarget = sendTarget;
	}

	
	public String getOperateType() {
		return operateType;
	}

	
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	
	public String getFaileReason() {
		return faileReason;
	}

	
	public void setFaileReason(String faileReason) {
		this.faileReason = faileReason;
	}
	
}
