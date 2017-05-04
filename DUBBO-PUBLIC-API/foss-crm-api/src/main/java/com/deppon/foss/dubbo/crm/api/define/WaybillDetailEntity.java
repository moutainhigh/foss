package com.deppon.foss.dubbo.crm.api.define;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 335284
 *
 */
public class WaybillDetailEntity implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
      * @author WangZengming
      * @date 2015-9-2
      * @see:运单号
      */
    private String number;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:运输类型(一级产品)：汽运-VEHICLE；空运-AIRLINE
      */
    private String tranType;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:运输性质(三级产品)：
				精准汽运（长途）LRF
				精准卡航 FLF
				精准汽运（短途）SRF
				精准城运 FSF
				汽运偏线 PLF
				精准空运 AF
				整车 WVH
      */
    private String tranProperty;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:发货人
      */
    private String sender;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:发货人为大客户标识
      */
    private String isBigDeliverCustomer;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:返货类型
      */
    private String returnType;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:原运单号
      */
    private String originalWaybillNo;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:发货人电话
      */
    private String senderPhone;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:发货人手机
      */
    private String senderMobile;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:始发站
      */
    private String departure;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:发货人地址
      */
    private String senderAddress;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:发货人地址备注
      */
    private String senderAddressNote;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:收货人
      */
    private String consignee;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:收货人电话
      */
    private String consigneePhone;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:收货人手机
      */
    private String consigneeMobile;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:目的站
      */
    private String destination;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:收货人地址
      */
    private String consigneeAddress;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:收货人地址备注
      */
    private String consigneeAddressNote;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:货物名称
      */
    private String goodName;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:件数
      */
    private int pieces;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:重量
      */
    private Float weight;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:体积
      */
    private Float cubage;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:总费用(公布价运费 + 增值服务费用 - 优惠总费用=到付+预付—代收)
      */
    private BigDecimal totalCharge;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:付款方式
      */
    private String payment;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:预付（客户预付款金额）
      */
    private BigDecimal preCharge;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:到付（客户到付金额）
      */
    private BigDecimal arriveCharge;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:代收货款类型
      */
    private String refundType;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:代收货款
      */
    private BigDecimal refund;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:代收货款手续费
      */
    private BigDecimal refundFee;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:开单提货方式
      */
    private String deliveryType;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:接货费
      */
    private BigDecimal consignCharge;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:送货费
      */
    private BigDecimal deliveryCharge;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:签收回单费
      */
    private BigDecimal signBackCharge;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:包装费
      */
    private BigDecimal pickCharge;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:劳务费
      */
    private BigDecimal laborRebate;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:公布价运费（重量/体积 * 费率 * 折扣）
      */
    private BigDecimal publishCharge;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:出发部门名称
      */
    private String departureDeptName;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:出发部门标杆编码
      */
    private String departureDeptNumber;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:出发部门地址
      */
    private String departureDeptAddr;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:出发部门电话
      */
    private String departureDeptPhone;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:出发部门传真
      */
    private String departureDeptFax;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:到达网点名称
      */
    private String ladingStationName;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:到达网点标杆编码
      */
    private String ladingStationNumber;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:到达网点地址
      */
    private String ladingStationAddr;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:到达网点电话
      */
    private String ladingStationPhone;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:到达网点传真
      */
    private String ladingStationFax;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:是否全部签收
      */
    private Boolean isSigned;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:是否正常签收
      */
    private Boolean isNormalSigned;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:是否存在异常签收
      */
    private Boolean uuormaSign;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:是否存在弃货签收
      */
    private Boolean missingSign;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:签收录入人
      */
    private String signRecorderId;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:签收时间
      */
    private Date signedDate;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:第一次签收时间
      */
    private Date firstSignedDate;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:签收备注
      */
    private String signedDesc;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:订单号
      */
    private String orderNumber;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:保价声明
      */
    private BigDecimal insuranceValue;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:保价手续费
      */
    private BigDecimal insurance;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:货物包装
      */
    private String packing;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:运单状态
      */
    private String orderState;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:其它费用
      */
    private BigDecimal otherPayment;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:托运备注
      */
    private String tranDesc;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:发货客户编码
      */
    private String senderNumber;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:收货客户编码
      */
    private String consigneeNumber;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:是否已结款
      */
    private String isClear;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:返单类型 
      */
    private String signBackType;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:储运事项
      */
    private String transNotice;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:发货时间
      */
    private Date sendTime;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:收货部门名称
      */
    private String receiveDeptName;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:收货部门标杆代码
      */
    private String receiveDeptNumber;
    
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:配载部门
      */
    private String stowageDept;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:发货人城市编码
      */
    private String senderCityCode;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:发货人城市名称
      */
    private String senderCityName;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:发货人省份编码
      */
    private String senderProvinceCode;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:发货人省份名称
      */
    private String senderProvinceName;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:收货人城市编码
      */
    private String consigneeCityCode;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:收货人城市名称
      */
    private String consigneeCityName;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:收货人省份编码
      */
    private String consigneeProvinceCode;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:收货人省份名称
      */
    private String consigneeProvinceName;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:是否上门接货
      */
    private Boolean isDoorToDoorPick;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:短信通知结果：
		       1-未通知_NONE_NOTICE
		       2-通知成功_SUCCESS
		       3-通知失败_FAILURE
		       4-语音通知中_VOICE_NOTICING
		       5-短信通知中_SMS_NOTICING
		       6-通知未果_NOTICING_UNSUCCESSFU
      */
    private String smsNoticeResult;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:签收单返回方式:
				1-无需返单
				2-客户签收单原件返回
				3-客户签收单复印件返回
				4-运单签收单原件返回
				5-运单签收单复印件返回
				6-派送代理签收单原件返回
				7-派送代理签收单传真件返回
      */
    private String signBillBackWay;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:运单所属快递大区编码-出发
      */
    private String exDepartureRegionNubmer;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:运单所属快递大区名称-出发
      */
    private String exDepartureRegionName;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:运单所属快递大区标杆编码-出发
      */
    private String exDepartureRegionStandardNubmer;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:运单所属快递大区编码-到达
      */
    private String exDestinationRegionNubmer;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:运单所属快递大区名称-到达
      */
    private String exDestinationRegionName;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:运单所属快递大区标杆编码-到达
      */
    private String exDestinationRegionStandardNubmer;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:快递员CODE-出发
      */
    private String exDepartureCourierNumber;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:快递员名称-出发
      */
    private String exDepartureCourierName;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:快递点部CODE-出发
      */
    private String exDepartureDeptNumber;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:快递点部标杆编码-出发
      */
    private String exDepartureDeptStandardNumber;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:快递点部名称-出发
      */
    private String exDepartureDeptName;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:快递员CODE-到达
      */
    private String exDestinationCourierNumber;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:快递员名称-到达
      */
    private String exDestinationCourierName;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:快递点部CODE-到达
      */
    private String exDestinationDeptNumber;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:快递点部标杆编码-到达
      */
    private String exDestinationDeptStandardNumber;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:快递点部名称-到达
      */
    private String exDestinationDeptName;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:是否子母件
      */
    private Boolean isPicPackage;
    /**
      * @author WangZengming
      * @date 2015-9-2
      * @see:母件单号
      */
    private String parentWaybillNo;
    

	/**
     * 子件单号集合
     */
  	private List<String> childWaybillNoList;
  	
  	/**
     * 返货单号集合
     */
  	private List<String> returnWaybillNoList;
  	
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the tranType
	 */
	public String getTranType() {
		return tranType;
	}

	/**
	 * @param tranType the tranType to set
	 */
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	/**
	 * @return the tranProperty
	 */
	public String getTranProperty() {
		return tranProperty;
	}

	/**
	 * @param tranProperty the tranProperty to set
	 */
	public void setTranProperty(String tranProperty) {
		this.tranProperty = tranProperty;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @return the isBigDeliverCustomer
	 */
	public String getIsBigDeliverCustomer() {
		return isBigDeliverCustomer;
	}

	/**
	 * @param isBigDeliverCustomer the isBigDeliverCustomer to set
	 */
	public void setIsBigDeliverCustomer(String isBigDeliverCustomer) {
		this.isBigDeliverCustomer = isBigDeliverCustomer;
	}

	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the originalWaybillNo
	 */
	public String getOriginalWaybillNo() {
		return originalWaybillNo;
	}

	/**
	 * @param originalWaybillNo the originalWaybillNo to set
	 */
	public void setOriginalWaybillNo(String originalWaybillNo) {
		this.originalWaybillNo = originalWaybillNo;
	}

	/**
	 * @return the senderPhone
	 */
	public String getSenderPhone() {
		return senderPhone;
	}

	/**
	 * @param senderPhone the senderPhone to set
	 */
	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	/**
	 * @return the senderMobile
	 */
	public String getSenderMobile() {
		return senderMobile;
	}

	/**
	 * @param senderMobile the senderMobile to set
	 */
	public void setSenderMobile(String senderMobile) {
		this.senderMobile = senderMobile;
	}

	/**
	 * @return the departure
	 */
	public String getDeparture() {
		return departure;
	}

	/**
	 * @param departure the departure to set
	 */
	public void setDeparture(String departure) {
		this.departure = departure;
	}

	/**
	 * @return the senderAddress
	 */
	public String getSenderAddress() {
		return senderAddress;
	}

	/**
	 * @param senderAddress the senderAddress to set
	 */
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	/**
	 * @return the senderAddressNote
	 */
	public String getSenderAddressNote() {
		return senderAddressNote;
	}

	/**
	 * @param senderAddressNote the senderAddressNote to set
	 */
	public void setSenderAddressNote(String senderAddressNote) {
		this.senderAddressNote = senderAddressNote;
	}

	/**
	 * @return the consignee
	 */
	public String getConsignee() {
		return consignee;
	}

	/**
	 * @param consignee the consignee to set
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * @return the consigneePhone
	 */
	public String getConsigneePhone() {
		return consigneePhone;
	}

	/**
	 * @param consigneePhone the consigneePhone to set
	 */
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	/**
	 * @return the consigneeMobile
	 */
	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	/**
	 * @param consigneeMobile the consigneeMobile to set
	 */
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the consigneeAddress
	 */
	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	/**
	 * @param consigneeAddress the consigneeAddress to set
	 */
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	/**
	 * @return the consigneeAddressNote
	 */
	public String getConsigneeAddressNote() {
		return consigneeAddressNote;
	}

	/**
	 * @param consigneeAddressNote the consigneeAddressNote to set
	 */
	public void setConsigneeAddressNote(String consigneeAddressNote) {
		this.consigneeAddressNote = consigneeAddressNote;
	}

	/**
	 * @return the goodName
	 */
	public String getGoodName() {
		return goodName;
	}

	/**
	 * @param goodName the goodName to set
	 */
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	/**
	 * @return the pieces
	 */
	public int getPieces() {
		return pieces;
	}

	/**
	 * @param pieces the pieces to set
	 */
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	/**
	 * @return the weight
	 */
	public Float getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Float weight) {
		this.weight = weight;
	}

	/**
	 * @return the cubage
	 */
	public Float getCubage() {
		return cubage;
	}

	/**
	 * @param cubage the cubage to set
	 */
	public void setCubage(Float cubage) {
		this.cubage = cubage;
	}

	/**
	 * @return the totalCharge
	 */
	public BigDecimal getTotalCharge() {
		return totalCharge;
	}

	/**
	 * @param totalCharge the totalCharge to set
	 */
	public void setTotalCharge(BigDecimal totalCharge) {
		this.totalCharge = totalCharge;
	}

	/**
	 * @return the payment
	 */
	public String getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}

	/**
	 * @return the preCharge
	 */
	public BigDecimal getPreCharge() {
		return preCharge;
	}

	/**
	 * @param preCharge the preCharge to set
	 */
	public void setPreCharge(BigDecimal preCharge) {
		this.preCharge = preCharge;
	}

	/**
	 * @return the arriveCharge
	 */
	public BigDecimal getArriveCharge() {
		return arriveCharge;
	}

	/**
	 * @param arriveCharge the arriveCharge to set
	 */
	public void setArriveCharge(BigDecimal arriveCharge) {
		this.arriveCharge = arriveCharge;
	}

	/**
	 * @return the refundType
	 */
	public String getRefundType() {
		return refundType;
	}

	/**
	 * @param refundType the refundType to set
	 */
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	/**
	 * @return the refund
	 */
	public BigDecimal getRefund() {
		return refund;
	}

	/**
	 * @param refund the refund to set
	 */
	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}

	/**
	 * @return the refundFee
	 */
	public BigDecimal getRefundFee() {
		return refundFee;
	}

	/**
	 * @param refundFee the refundFee to set
	 */
	public void setRefundFee(BigDecimal refundFee) {
		this.refundFee = refundFee;
	}

	/**
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * @return the consignCharge
	 */
	public BigDecimal getConsignCharge() {
		return consignCharge;
	}

	/**
	 * @param consignCharge the consignCharge to set
	 */
	public void setConsignCharge(BigDecimal consignCharge) {
		this.consignCharge = consignCharge;
	}

	/**
	 * @return the deliveryCharge
	 */
	public BigDecimal getDeliveryCharge() {
		return deliveryCharge;
	}

	/**
	 * @param deliveryCharge the deliveryCharge to set
	 */
	public void setDeliveryCharge(BigDecimal deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	/**
	 * @return the signBackCharge
	 */
	public BigDecimal getSignBackCharge() {
		return signBackCharge;
	}

	/**
	 * @param signBackCharge the signBackCharge to set
	 */
	public void setSignBackCharge(BigDecimal signBackCharge) {
		this.signBackCharge = signBackCharge;
	}

	/**
	 * @return the pickCharge
	 */
	public BigDecimal getPickCharge() {
		return pickCharge;
	}

	/**
	 * @param pickCharge the pickCharge to set
	 */
	public void setPickCharge(BigDecimal pickCharge) {
		this.pickCharge = pickCharge;
	}

	/**
	 * @return the laborRebate
	 */
	public BigDecimal getLaborRebate() {
		return laborRebate;
	}

	/**
	 * @param laborRebate the laborRebate to set
	 */
	public void setLaborRebate(BigDecimal laborRebate) {
		this.laborRebate = laborRebate;
	}

	/**
	 * @return the publishCharge
	 */
	public BigDecimal getPublishCharge() {
		return publishCharge;
	}

	/**
	 * @param publishCharge the publishCharge to set
	 */
	public void setPublishCharge(BigDecimal publishCharge) {
		this.publishCharge = publishCharge;
	}

	/**
	 * @return the departureDeptName
	 */
	public String getDepartureDeptName() {
		return departureDeptName;
	}

	/**
	 * @param departureDeptName the departureDeptName to set
	 */
	public void setDepartureDeptName(String departureDeptName) {
		this.departureDeptName = departureDeptName;
	}

	/**
	 * @return the departureDeptNumber
	 */
	public String getDepartureDeptNumber() {
		return departureDeptNumber;
	}

	/**
	 * @param departureDeptNumber the departureDeptNumber to set
	 */
	public void setDepartureDeptNumber(String departureDeptNumber) {
		this.departureDeptNumber = departureDeptNumber;
	}

	/**
	 * @return the departureDeptAddr
	 */
	public String getDepartureDeptAddr() {
		return departureDeptAddr;
	}

	/**
	 * @param departureDeptAddr the departureDeptAddr to set
	 */
	public void setDepartureDeptAddr(String departureDeptAddr) {
		this.departureDeptAddr = departureDeptAddr;
	}

	/**
	 * @return the departureDeptPhone
	 */
	public String getDepartureDeptPhone() {
		return departureDeptPhone;
	}

	/**
	 * @param departureDeptPhone the departureDeptPhone to set
	 */
	public void setDepartureDeptPhone(String departureDeptPhone) {
		this.departureDeptPhone = departureDeptPhone;
	}

	/**
	 * @return the departureDeptFax
	 */
	public String getDepartureDeptFax() {
		return departureDeptFax;
	}

	/**
	 * @param departureDeptFax the departureDeptFax to set
	 */
	public void setDepartureDeptFax(String departureDeptFax) {
		this.departureDeptFax = departureDeptFax;
	}

	/**
	 * @return the ladingStationName
	 */
	public String getLadingStationName() {
		return ladingStationName;
	}

	/**
	 * @param ladingStationName the ladingStationName to set
	 */
	public void setLadingStationName(String ladingStationName) {
		this.ladingStationName = ladingStationName;
	}

	/**
	 * @return the ladingStationNumber
	 */
	public String getLadingStationNumber() {
		return ladingStationNumber;
	}

	/**
	 * @param ladingStationNumber the ladingStationNumber to set
	 */
	public void setLadingStationNumber(String ladingStationNumber) {
		this.ladingStationNumber = ladingStationNumber;
	}

	/**
	 * @return the ladingStationAddr
	 */
	public String getLadingStationAddr() {
		return ladingStationAddr;
	}

	/**
	 * @param ladingStationAddr the ladingStationAddr to set
	 */
	public void setLadingStationAddr(String ladingStationAddr) {
		this.ladingStationAddr = ladingStationAddr;
	}

	/**
	 * @return the ladingStationPhone
	 */
	public String getLadingStationPhone() {
		return ladingStationPhone;
	}

	/**
	 * @param ladingStationPhone the ladingStationPhone to set
	 */
	public void setLadingStationPhone(String ladingStationPhone) {
		this.ladingStationPhone = ladingStationPhone;
	}

	/**
	 * @return the ladingStationFax
	 */
	public String getLadingStationFax() {
		return ladingStationFax;
	}

	/**
	 * @param ladingStationFax the ladingStationFax to set
	 */
	public void setLadingStationFax(String ladingStationFax) {
		this.ladingStationFax = ladingStationFax;
	}

	/**
	 * @return the isSigned
	 */
	public Boolean getIsSigned() {
		return isSigned;
	}

	/**
	 * @param isSigned the isSigned to set
	 */
	public void setIsSigned(Boolean isSigned) {
		this.isSigned = isSigned;
	}

	/**
	 * @return the isNormalSigned
	 */
	public Boolean getIsNormalSigned() {
		return isNormalSigned;
	}

	/**
	 * @param isNormalSigned the isNormalSigned to set
	 */
	public void setIsNormalSigned(Boolean isNormalSigned) {
		this.isNormalSigned = isNormalSigned;
	}

	/**
	 * @return the uuormaSign
	 */
	public Boolean getUuormaSign() {
		return uuormaSign;
	}

	/**
	 * @param uuormaSign the uuormaSign to set
	 */
	public void setUuormaSign(Boolean uuormaSign) {
		this.uuormaSign = uuormaSign;
	}

	/**
	 * @return the missingSign
	 */
	public Boolean getMissingSign() {
		return missingSign;
	}

	/**
	 * @param missingSign the missingSign to set
	 */
	public void setMissingSign(Boolean missingSign) {
		this.missingSign = missingSign;
	}

	/**
	 * @return the signRecorderId
	 */
	public String getSignRecorderId() {
		return signRecorderId;
	}

	/**
	 * @param signRecorderId the signRecorderId to set
	 */
	public void setSignRecorderId(String signRecorderId) {
		this.signRecorderId = signRecorderId;
	}

	/**
	 * @return the signedDate
	 */
	public Date getSignedDate() {
		return signedDate;
	}

	/**
	 * @param signedDate the signedDate to set
	 */
	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}

	/**
	 * @return the firstSignedDate
	 */
	public Date getFirstSignedDate() {
		return firstSignedDate;
	}

	/**
	 * @param firstSignedDate the firstSignedDate to set
	 */
	public void setFirstSignedDate(Date firstSignedDate) {
		this.firstSignedDate = firstSignedDate;
	}

	/**
	 * @return the signedDesc
	 */
	public String getSignedDesc() {
		return signedDesc;
	}

	/**
	 * @param signedDesc the signedDesc to set
	 */
	public void setSignedDesc(String signedDesc) {
		this.signedDesc = signedDesc;
	}

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the insuranceValue
	 */
	public BigDecimal getInsuranceValue() {
		return insuranceValue;
	}

	/**
	 * @param insuranceValue the insuranceValue to set
	 */
	public void setInsuranceValue(BigDecimal insuranceValue) {
		this.insuranceValue = insuranceValue;
	}

	/**
	 * @return the insurance
	 */
	public BigDecimal getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance the insurance to set
	 */
	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}

	/**
	 * @return the packing
	 */
	public String getPacking() {
		return packing;
	}

	/**
	 * @param packing the packing to set
	 */
	public void setPacking(String packing) {
		this.packing = packing;
	}

	/**
	 * @return the orderState
	 */
	public String getOrderState() {
		return orderState;
	}

	/**
	 * @param orderState the orderState to set
	 */
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	/**
	 * @return the otherPayment
	 */
	public BigDecimal getOtherPayment() {
		return otherPayment;
	}

	/**
	 * @param otherPayment the otherPayment to set
	 */
	public void setOtherPayment(BigDecimal otherPayment) {
		this.otherPayment = otherPayment;
	}

	/**
	 * @return the tranDesc
	 */
	public String getTranDesc() {
		return tranDesc;
	}

	/**
	 * @param tranDesc the tranDesc to set
	 */
	public void setTranDesc(String tranDesc) {
		this.tranDesc = tranDesc;
	}

	/**
	 * @return the senderNumber
	 */
	public String getSenderNumber() {
		return senderNumber;
	}

	/**
	 * @param senderNumber the senderNumber to set
	 */
	public void setSenderNumber(String senderNumber) {
		this.senderNumber = senderNumber;
	}

	/**
	 * @return the consigneeNumber
	 */
	public String getConsigneeNumber() {
		return consigneeNumber;
	}

	/**
	 * @param consigneeNumber the consigneeNumber to set
	 */
	public void setConsigneeNumber(String consigneeNumber) {
		this.consigneeNumber = consigneeNumber;
	}

	/**
	 * @return the isClear
	 */
	public String getIsClear() {
		return isClear;
	}

	/**
	 * @param isClear the isClear to set
	 */
	public void setIsClear(String isClear) {
		this.isClear = isClear;
	}

	/**
	 * @return the signBackType
	 */
	public String getSignBackType() {
		return signBackType;
	}

	/**
	 * @param signBackType the signBackType to set
	 */
	public void setSignBackType(String signBackType) {
		this.signBackType = signBackType;
	}

	/**
	 * @return the transNotice
	 */
	public String getTransNotice() {
		return transNotice;
	}

	/**
	 * @param transNotice the transNotice to set
	 */
	public void setTransNotice(String transNotice) {
		this.transNotice = transNotice;
	}

	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * @return the receiveDeptName
	 */
	public String getReceiveDeptName() {
		return receiveDeptName;
	}

	/**
	 * @param receiveDeptName the receiveDeptName to set
	 */
	public void setReceiveDeptName(String receiveDeptName) {
		this.receiveDeptName = receiveDeptName;
	}

	/**
	 * @return the receiveDeptNumber
	 */
	public String getReceiveDeptNumber() {
		return receiveDeptNumber;
	}

	/**
	 * @param receiveDeptNumber the receiveDeptNumber to set
	 */
	public void setReceiveDeptNumber(String receiveDeptNumber) {
		this.receiveDeptNumber = receiveDeptNumber;
	}

	/**
	 * @return the stowageDept
	 */
	public String getStowageDept() {
		return stowageDept;
	}

	/**
	 * @param stowageDept the stowageDept to set
	 */
	public void setStowageDept(String stowageDept) {
		this.stowageDept = stowageDept;
	}

	/**
	 * @return the senderCityCode
	 */
	public String getSenderCityCode() {
		return senderCityCode;
	}

	/**
	 * @param senderCityCode the senderCityCode to set
	 */
	public void setSenderCityCode(String senderCityCode) {
		this.senderCityCode = senderCityCode;
	}

	/**
	 * @return the senderCityName
	 */
	public String getSenderCityName() {
		return senderCityName;
	}

	/**
	 * @param senderCityName the senderCityName to set
	 */
	public void setSenderCityName(String senderCityName) {
		this.senderCityName = senderCityName;
	}

	/**
	 * @return the senderProvinceCode
	 */
	public String getSenderProvinceCode() {
		return senderProvinceCode;
	}

	/**
	 * @param senderProvinceCode the senderProvinceCode to set
	 */
	public void setSenderProvinceCode(String senderProvinceCode) {
		this.senderProvinceCode = senderProvinceCode;
	}

	/**
	 * @return the senderProvinceName
	 */
	public String getSenderProvinceName() {
		return senderProvinceName;
	}

	/**
	 * @param senderProvinceName the senderProvinceName to set
	 */
	public void setSenderProvinceName(String senderProvinceName) {
		this.senderProvinceName = senderProvinceName;
	}

	/**
	 * @return the consigneeCityCode
	 */
	public String getConsigneeCityCode() {
		return consigneeCityCode;
	}

	/**
	 * @param consigneeCityCode the consigneeCityCode to set
	 */
	public void setConsigneeCityCode(String consigneeCityCode) {
		this.consigneeCityCode = consigneeCityCode;
	}

	/**
	 * @return the consigneeCityName
	 */
	public String getConsigneeCityName() {
		return consigneeCityName;
	}

	/**
	 * @param consigneeCityName the consigneeCityName to set
	 */
	public void setConsigneeCityName(String consigneeCityName) {
		this.consigneeCityName = consigneeCityName;
	}

	/**
	 * @return the consigneeProvinceCode
	 */
	public String getConsigneeProvinceCode() {
		return consigneeProvinceCode;
	}

	/**
	 * @param consigneeProvinceCode the consigneeProvinceCode to set
	 */
	public void setConsigneeProvinceCode(String consigneeProvinceCode) {
		this.consigneeProvinceCode = consigneeProvinceCode;
	}

	/**
	 * @return the consigneeProvinceName
	 */
	public String getConsigneeProvinceName() {
		return consigneeProvinceName;
	}

	/**
	 * @param consigneeProvinceName the consigneeProvinceName to set
	 */
	public void setConsigneeProvinceName(String consigneeProvinceName) {
		this.consigneeProvinceName = consigneeProvinceName;
	}

	/**
	 * @return the isDoorToDoorPick
	 */
	public Boolean getIsDoorToDoorPick() {
		return isDoorToDoorPick;
	}

	/**
	 * @param isDoorToDoorPick the isDoorToDoorPick to set
	 */
	public void setIsDoorToDoorPick(Boolean isDoorToDoorPick) {
		this.isDoorToDoorPick = isDoorToDoorPick;
	}

	/**
	 * @return the smsNoticeResult
	 */
	public String getSmsNoticeResult() {
		return smsNoticeResult;
	}

	/**
	 * @param smsNoticeResult the smsNoticeResult to set
	 */
	public void setSmsNoticeResult(String smsNoticeResult) {
		this.smsNoticeResult = smsNoticeResult;
	}

	/**
	 * @return the signBillBackWay
	 */
	public String getSignBillBackWay() {
		return signBillBackWay;
	}

	/**
	 * @param signBillBackWay the signBillBackWay to set
	 */
	public void setSignBillBackWay(String signBillBackWay) {
		this.signBillBackWay = signBillBackWay;
	}

	/**
	 * @return the exDepartureRegionNubmer
	 */
	public String getExDepartureRegionNubmer() {
		return exDepartureRegionNubmer;
	}

	/**
	 * @param exDepartureRegionNubmer the exDepartureRegionNubmer to set
	 */
	public void setExDepartureRegionNubmer(String exDepartureRegionNubmer) {
		this.exDepartureRegionNubmer = exDepartureRegionNubmer;
	}

	/**
	 * @return the exDepartureRegionName
	 */
	public String getExDepartureRegionName() {
		return exDepartureRegionName;
	}

	/**
	 * @param exDepartureRegionName the exDepartureRegionName to set
	 */
	public void setExDepartureRegionName(String exDepartureRegionName) {
		this.exDepartureRegionName = exDepartureRegionName;
	}

	/**
	 * @return the exDepartureRegionStandardNubmer
	 */
	public String getExDepartureRegionStandardNubmer() {
		return exDepartureRegionStandardNubmer;
	}

	/**
	 * @param exDepartureRegionStandardNubmer the exDepartureRegionStandardNubmer to set
	 */
	public void setExDepartureRegionStandardNubmer(
			String exDepartureRegionStandardNubmer) {
		this.exDepartureRegionStandardNubmer = exDepartureRegionStandardNubmer;
	}

	/**
	 * @return the exDestinationRegionNubmer
	 */
	public String getExDestinationRegionNubmer() {
		return exDestinationRegionNubmer;
	}

	/**
	 * @param exDestinationRegionNubmer the exDestinationRegionNubmer to set
	 */
	public void setExDestinationRegionNubmer(String exDestinationRegionNubmer) {
		this.exDestinationRegionNubmer = exDestinationRegionNubmer;
	}

	/**
	 * @return the exDestinationRegionName
	 */
	public String getExDestinationRegionName() {
		return exDestinationRegionName;
	}

	/**
	 * @param exDestinationRegionName the exDestinationRegionName to set
	 */
	public void setExDestinationRegionName(String exDestinationRegionName) {
		this.exDestinationRegionName = exDestinationRegionName;
	}

	/**
	 * @return the exDestinationRegionStandardNubmer
	 */
	public String getExDestinationRegionStandardNubmer() {
		return exDestinationRegionStandardNubmer;
	}

	/**
	 * @param exDestinationRegionStandardNubmer the exDestinationRegionStandardNubmer to set
	 */
	public void setExDestinationRegionStandardNubmer(
			String exDestinationRegionStandardNubmer) {
		this.exDestinationRegionStandardNubmer = exDestinationRegionStandardNubmer;
	}

	/**
	 * @return the exDepartureCourierNumber
	 */
	public String getExDepartureCourierNumber() {
		return exDepartureCourierNumber;
	}

	/**
	 * @param exDepartureCourierNumber the exDepartureCourierNumber to set
	 */
	public void setExDepartureCourierNumber(String exDepartureCourierNumber) {
		this.exDepartureCourierNumber = exDepartureCourierNumber;
	}

	/**
	 * @return the exDepartureCourierName
	 */
	public String getExDepartureCourierName() {
		return exDepartureCourierName;
	}

	/**
	 * @param exDepartureCourierName the exDepartureCourierName to set
	 */
	public void setExDepartureCourierName(String exDepartureCourierName) {
		this.exDepartureCourierName = exDepartureCourierName;
	}

	/**
	 * @return the exDepartureDeptNumber
	 */
	public String getExDepartureDeptNumber() {
		return exDepartureDeptNumber;
	}

	/**
	 * @param exDepartureDeptNumber the exDepartureDeptNumber to set
	 */
	public void setExDepartureDeptNumber(String exDepartureDeptNumber) {
		this.exDepartureDeptNumber = exDepartureDeptNumber;
	}

	/**
	 * @return the exDepartureDeptStandardNumber
	 */
	public String getExDepartureDeptStandardNumber() {
		return exDepartureDeptStandardNumber;
	}

	/**
	 * @param exDepartureDeptStandardNumber the exDepartureDeptStandardNumber to set
	 */
	public void setExDepartureDeptStandardNumber(
			String exDepartureDeptStandardNumber) {
		this.exDepartureDeptStandardNumber = exDepartureDeptStandardNumber;
	}

	/**
	 * @return the exDepartureDeptName
	 */
	public String getExDepartureDeptName() {
		return exDepartureDeptName;
	}

	/**
	 * @param exDepartureDeptName the exDepartureDeptName to set
	 */
	public void setExDepartureDeptName(String exDepartureDeptName) {
		this.exDepartureDeptName = exDepartureDeptName;
	}

	/**
	 * @return the exDestinationCourierNumber
	 */
	public String getExDestinationCourierNumber() {
		return exDestinationCourierNumber;
	}

	/**
	 * @param exDestinationCourierNumber the exDestinationCourierNumber to set
	 */
	public void setExDestinationCourierNumber(String exDestinationCourierNumber) {
		this.exDestinationCourierNumber = exDestinationCourierNumber;
	}

	/**
	 * @return the exDestinationCourierName
	 */
	public String getExDestinationCourierName() {
		return exDestinationCourierName;
	}

	/**
	 * @param exDestinationCourierName the exDestinationCourierName to set
	 */
	public void setExDestinationCourierName(String exDestinationCourierName) {
		this.exDestinationCourierName = exDestinationCourierName;
	}

	/**
	 * @return the exDestinationDeptNumber
	 */
	public String getExDestinationDeptNumber() {
		return exDestinationDeptNumber;
	}

	/**
	 * @param exDestinationDeptNumber the exDestinationDeptNumber to set
	 */
	public void setExDestinationDeptNumber(String exDestinationDeptNumber) {
		this.exDestinationDeptNumber = exDestinationDeptNumber;
	}

	/**
	 * @return the exDestinationDeptStandardNumber
	 */
	public String getExDestinationDeptStandardNumber() {
		return exDestinationDeptStandardNumber;
	}

	/**
	 * @param exDestinationDeptStandardNumber the exDestinationDeptStandardNumber to set
	 */
	public void setExDestinationDeptStandardNumber(
			String exDestinationDeptStandardNumber) {
		this.exDestinationDeptStandardNumber = exDestinationDeptStandardNumber;
	}

	/**
	 * @return the exDestinationDeptName
	 */
	public String getExDestinationDeptName() {
		return exDestinationDeptName;
	}

	/**
	 * @param exDestinationDeptName the exDestinationDeptName to set
	 */
	public void setExDestinationDeptName(String exDestinationDeptName) {
		this.exDestinationDeptName = exDestinationDeptName;
	}

	/**
	 * @return the isPicPackage
	 */
	public Boolean getIsPicPackage() {
		return isPicPackage;
	}

	/**
	 * @param isPicPackage the isPicPackage to set
	 */
	public void setIsPicPackage(Boolean isPicPackage) {
		this.isPicPackage = isPicPackage;
	}

	/**
	 * @return the parentWaybillNo
	 */
	public String getParentWaybillNo() {
		return parentWaybillNo;
	}

	/**
	 * @param parentWaybillNo the parentWaybillNo to set
	 */
	public void setParentWaybillNo(String parentWaybillNo) {
		this.parentWaybillNo = parentWaybillNo;
	}

	public List<String> getChildWaybillNoList() {
		return childWaybillNoList;
	}

	public void setChildWaybillNoList(List<String> childWaybillNoList) {
		this.childWaybillNoList = childWaybillNoList;
	}

	public List<String> getReturnWaybillNoList() {
		return returnWaybillNoList;
	}

	public void setReturnWaybillNoList(List<String> returnWaybillNoList) {
		this.returnWaybillNoList = returnWaybillNoList;
	}
	
	

}
