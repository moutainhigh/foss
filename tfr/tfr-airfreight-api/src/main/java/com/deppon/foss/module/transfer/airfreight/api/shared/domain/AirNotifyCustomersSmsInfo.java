
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 短信信息实体类
 * 2015-08-20 200968
 * @since
 * @version
 */
public class AirNotifyCustomersSmsInfo implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -54784123695822L;
    
    private  String Id;
    
    public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	/**
     * 运单号
     */
    private String waybillNo;
    
    /**
	 * 通知方式:默认为短信,不可编辑
	 */
	private String noticeType;
    
	/**
	 * 通知内容-信息内容
	 */
	private String noticeContent;
	
	/**
	 * 派送方式
	 */
	private String deliverType;
	
	/**
	 * 通知结果
	 */
	private String noticeResult;
	
	/**
	 * 收货客户编码
	 */
	private String receiveCustomerCode;

	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName;

	/**
	 * 收货客户电话
	 */
	private String receiveCustomerPhone;
	
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * 收货客户手机
	 */
	private String receiveCustomerMobilephone;
	
	/**
	 * 发货客户编码
	 */
	private String deliveryCustomerCode;
	/**
	 * 发货客户名称
	 */
	private String deliveryCustomerName;

	/**
	 * 发货客户手机
	 */
	private String deliveryCustomerMobilephone;
	
	/**
	 * 操作时间
	 */
	private Date operateTime;
	
	/**
	 * 通知次数
	 */
	private Integer notifyQty;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 操作部门部门编码
	 */
	private String orgCode;
	
	/**
	 * 操作部门名称
	 */
	private String orgName;
	
	/**
	 * 操作人工号
	 */
	private String operatorNo;
	
	/**
	 * 操作人
	 */
	private String operatorName;
	
	/**
	 * 运单件数
	 */
	private Integer goodsQtyTotal;
	
	/**
	 * 正单联系电话  D
	 */
	
	private String airwaybillphone;
	
     /**
      * 几天内提货
      */
	private Integer daysPickUp;
	
	/**
	 * 空运代理地址
	 */
	private String airAgentAddress;
	
	/**
	 * 运单到付金额
	 */
	private BigDecimal toPayAmount;
	
	/**
	 * 储运事项
	 */
	private String transportationRemark;

	public String getTransportationRemark() {
		return transportationRemark;
	}

	public void setTransportationRemark(String transportationRemark) {
		this.transportationRemark = transportationRemark;
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

	public String getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public String getNoticeResult() {
		return noticeResult;
	}

	public void setNoticeResult(String noticeResult) {
		this.noticeResult = noticeResult;
	}

	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Integer getNotifyQty() {
		return notifyQty;
	}

	public void setNotifyQty(Integer notifyQty) {
		this.notifyQty = notifyQty;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public String getAirwaybillphone() {
		return airwaybillphone;
	}

	public void setAirwaybillphone(String airwaybillphone) {
		this.airwaybillphone = airwaybillphone;
	}

	public Integer getDaysPickUp() {
		return daysPickUp;
	}

	public void setDaysPickUp(Integer daysPickUp) {
		this.daysPickUp = daysPickUp;
	}

	public String getAirAgentAddress() {
		return airAgentAddress;
	}

	public void setAirAgentAddress(String airAgentAddress) {
		this.airAgentAddress = airAgentAddress;
	}

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	
}
