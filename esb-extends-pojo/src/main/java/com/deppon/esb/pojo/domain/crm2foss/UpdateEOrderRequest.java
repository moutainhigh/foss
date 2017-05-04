package com.deppon.esb.pojo.domain.crm2foss;

import java.util.Date;

/**
 * @ClassName: updateOrderRquest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lvchognxin
 * @date 2015年6月5日 上午9:23:10
 * 
 */
public class UpdateEOrderRequest {

	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * ID
	 */
	private String id;
	/**
	 * 修改时间
	 */
	private Date modifyDate;
	/**
	 * 修改人
	 */
	private String modifyUser;
	/**
	 * 受理部门
	 */
	private String acceptDept;//
	/**
	 * 受理部门名称
	 */
	private String acceptDeptName;
	/**
	 * 接货员手机
	 */
	private String accepterPersonMobile;
	/**
	 * 接货起始时间
	 */
	private Date beginAcceptTime;
	/**
	 * 渠道客户Id
	 */
	private String channelCustId;
	/**
	 * 渠道单号
	 */
	private String channelNumber;
	/**
	 * 渠道类型
	 */
	private String channelType;
	/**
	 * 联系人id
	 */
	private String contactManId;
	/**
	 * 联系人手机
	 */
	private String contactMobile;
	/**
	 * 发货联系人名称
	 */
	private String contactName;
	/**
	 * 联系人电话
	 */
	private String contactPhone;
	/**
	 * 联系人省份
	 */
	private String contactProvince;
	/**
	 * 联系地址
	 */
	private String contactAddress;
	/**
	 * 联系区域
	 */
	private String contactArea;
	/**
	 * 联系城市
	 */
	private String contactCity;
	/**
	 * 联系人省份编码
	 */
	private String contactProvinceCode;
	/**
	 * 联系城市编码
	 */
	private String contactCityCode;
	/**
	 * 联系区编码
	 */
	private String contactAreaCode;
	/**
	 * 受理人
	 */
	private String dealPerson;
	/**
	 * 提货方式
	 */
	private String deliveryMode;
	/**
	 * 接货结束时间
	 */
	private Date endAcceptTime;
	/**
	 * 反馈信息
	 */
	private String feedbackInfo;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 托运货物总件数
	 */
	private Integer goodsNumber;
	/**
	 * 货物总体积
	 */
	private Double totalVolume;
	/**
	 * 托运货物总重量
	 */
	private Double totalWeight;
	/**
	 * 货物类型
	 */
	private String goodsType;
	/**
	 * 报价申明价值
	 */
	private Double insuredAmount;
	/**
	 * 是否接货
	 */
	private Boolean isReceiveGoods;
	/**
	 * 是否短信通知
	 */
	private Boolean isSendmessage;
	/**
	 * 阿里巴巴订单的会员类型
	 */
	private String memberType;
	/**
	 * 官网收货人德邦用户名
	 */
	private String onlineName;
	/**
	 * 订单受理时间
	 */
	private Date orderAcceptTime;
	/**
	 * 订单号
	 */
	private String orderNumber;
	/**
	 * 下单人
	 */
	private String orderPerson;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 下单时间
	 */
	private Date orderTime;
	/**
	 * 货物包装类型
	 */
	private String packing;
	/**
	 * 付款方式
	 */
	private String paymentType;
	/**
	 * 接货员
	 */
	private String receiver;
	/**
	 * 收货人详细地址
	 */
	private String receiverCustAddress;
	/**
	 * 收货人区县
	 */
	private String receiverCustArea;
	/**
	 * 收货人城市
	 */
	private String receiverCustCity;
	/**
	 * 收货人省份
	 */
	private String receiverCustProvince;

	/**
	 * 出发省编码
	 */
	private String receiveProvinceCode;
	/**
	 * 出发市编码
	 */
	private String receiveCityCode;
	/**
	 * 出发区编码
	 */
	private String receiveCountyCode;
	/**
	 * 收货客户id
	 */
	private String receiverCustId;
	/**
	 * 收货人联系手机
	 */
	private String receiverCustMobile;
	/**
	 * 收货人客户姓名
	 */
	private String receiverCustName;
	/**
	 * 收货客户编号
	 */
	private String receiverCustNumber;
	/**
	 * 接货人联系电话
	 */
	private String receiverCustPhone;
	/**
	 * 收货客户所属公司
	 */
	private String receiverCustcompany;
	/**
	 * 到达网点
	 */
	private String receivingToPoint;
	/**
	 * 到达网点名称
	 */
	private String receivingToPointName;
	/**
	 * 代收货款类型
	 */
	private String reciveLoanType;
	/**
	 * 订单来源
	 */
	private String resource;
	/**
	 * 签收单
	 */
	private String returnBillType;
	/**
	 * 代收货款金额
	 */
	private Double reviceMoneyAmount;
	/**
	 * 发货id
	 */
	private String shipperId;
	/**
	 * 发货名称
	 */
	private String shipperName;
	/**
	 * 发货编码
	 */
	private String shipperNumber;
	/**
	 * 始发网点
	 */
	private String startStation;
	/**
	 * 始发网点名称
	 */
	private String startStationName;

	/**
	 * 储运事项
	 */
	private String transNote;
	/**
	 * 货物运输方式
	 */
	private String transportMode;
	/**
	 * 运单号
	 */
	private String waybillNumber;
	/**
	 * 优惠券
	 */
	private String couponNumber;
	/**
	 * 是否电子运单大客户 add by 2014-08-22
	 */
	private String ifElecBillBigCust;
	/**
	 * 订单类型 add by 2014-08-22
	 */
	private String orderType;
	/**
	 * 代收货款账号 add by 2014-08-22
	 */
	private String reciveLoanAccount;
	/**
	 * 开户名 add by 2014-08-22
	 */
	private String accountName;

	/**
	 * 发货地址备注
	 */
	private String contactAddrRemark;
	/**
	 * 收货地址备注
	 */
	private String receiverCustAddrRemark;

	/**
	 * 是否电子发票 add by 2014-10-24
	 */
	private String ifEInvoice;
	/**
	 * 电子发票手机号 add by 2014-10-24
	 */
	private String invoicePhone;
	/**
	 * 电子发票邮箱 add by 2014-10-24
	 */
	private String invoiceMail;

	// 原始发货地址
	private String originalsaddress;

	// 原始发货街道镇
	private String originalraddress;

	// 原始收货地址
	private String originalsStreet;

	// 原始收货街道镇
	private String originalrStreet;

	// 是否推荐物流
	private String recommend;

	// 纯运费
	private Double freight;

	// 采购单号
	private String procurementNumber;

	// 是否淘系订单
	private String ifTaoBao;

	// 大头笔
	private String markerPen;

	// 路由信息
	private String routInfo;

	// 修改类型
	private String modifyType;

	// 版本号
	private Double versionId;
	
	//是否子母件
	private String isPicPackage;
	
	// 原始单号
	private String originalNumber;

	/** 
	 * @return createDate 
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 
	 * @return createUser 
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/** 
	 * @return id 
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/** 
	 * @return modifyDate 
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/** 
	 * @return modifyUser 
	 */
	public String getModifyUser() {
		return modifyUser;
	}

	/**
	 * @param modifyUser the modifyUser to set
	 */
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	/** 
	 * @return acceptDept 
	 */
	public String getAcceptDept() {
		return acceptDept;
	}

	/**
	 * @param acceptDept the acceptDept to set
	 */
	public void setAcceptDept(String acceptDept) {
		this.acceptDept = acceptDept;
	}

	/** 
	 * @return acceptDeptName 
	 */
	public String getAcceptDeptName() {
		return acceptDeptName;
	}

	/**
	 * @param acceptDeptName the acceptDeptName to set
	 */
	public void setAcceptDeptName(String acceptDeptName) {
		this.acceptDeptName = acceptDeptName;
	}

	/** 
	 * @return accepterPersonMobile 
	 */
	public String getAccepterPersonMobile() {
		return accepterPersonMobile;
	}

	/**
	 * @param accepterPersonMobile the accepterPersonMobile to set
	 */
	public void setAccepterPersonMobile(String accepterPersonMobile) {
		this.accepterPersonMobile = accepterPersonMobile;
	}

	/** 
	 * @return beginAcceptTime 
	 */
	public Date getBeginAcceptTime() {
		return beginAcceptTime;
	}

	/**
	 * @param beginAcceptTime the beginAcceptTime to set
	 */
	public void setBeginAcceptTime(Date beginAcceptTime) {
		this.beginAcceptTime = beginAcceptTime;
	}

	/** 
	 * @return channelCustId 
	 */
	public String getChannelCustId() {
		return channelCustId;
	}

	/**
	 * @param channelCustId the channelCustId to set
	 */
	public void setChannelCustId(String channelCustId) {
		this.channelCustId = channelCustId;
	}

	/** 
	 * @return channelNumber 
	 */
	public String getChannelNumber() {
		return channelNumber;
	}

	/**
	 * @param channelNumber the channelNumber to set
	 */
	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	/** 
	 * @return channelType 
	 */
	public String getChannelType() {
		return channelType;
	}

	/**
	 * @param channelType the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/** 
	 * @return contactManId 
	 */
	public String getContactManId() {
		return contactManId;
	}

	/**
	 * @param contactManId the contactManId to set
	 */
	public void setContactManId(String contactManId) {
		this.contactManId = contactManId;
	}

	/** 
	 * @return contactMobile 
	 */
	public String getContactMobile() {
		return contactMobile;
	}

	/**
	 * @param contactMobile the contactMobile to set
	 */
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	/** 
	 * @return contactName 
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/** 
	 * @return contactPhone 
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param contactPhone the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/** 
	 * @return contactProvince 
	 */
	public String getContactProvince() {
		return contactProvince;
	}

	/**
	 * @param contactProvince the contactProvince to set
	 */
	public void setContactProvince(String contactProvince) {
		this.contactProvince = contactProvince;
	}

	/** 
	 * @return contactAddress 
	 */
	public String getContactAddress() {
		return contactAddress;
	}

	/**
	 * @param contactAddress the contactAddress to set
	 */
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	/** 
	 * @return contactArea 
	 */
	public String getContactArea() {
		return contactArea;
	}

	/**
	 * @param contactArea the contactArea to set
	 */
	public void setContactArea(String contactArea) {
		this.contactArea = contactArea;
	}

	/** 
	 * @return contactCity 
	 */
	public String getContactCity() {
		return contactCity;
	}

	/**
	 * @param contactCity the contactCity to set
	 */
	public void setContactCity(String contactCity) {
		this.contactCity = contactCity;
	}

	/** 
	 * @return contactProvinceCode 
	 */
	public String getContactProvinceCode() {
		return contactProvinceCode;
	}

	/**
	 * @param contactProvinceCode the contactProvinceCode to set
	 */
	public void setContactProvinceCode(String contactProvinceCode) {
		this.contactProvinceCode = contactProvinceCode;
	}

	/** 
	 * @return contactCityCode 
	 */
	public String getContactCityCode() {
		return contactCityCode;
	}

	/**
	 * @param contactCityCode the contactCityCode to set
	 */
	public void setContactCityCode(String contactCityCode) {
		this.contactCityCode = contactCityCode;
	}

	/** 
	 * @return contactAreaCode 
	 */
	public String getContactAreaCode() {
		return contactAreaCode;
	}

	/**
	 * @param contactAreaCode the contactAreaCode to set
	 */
	public void setContactAreaCode(String contactAreaCode) {
		this.contactAreaCode = contactAreaCode;
	}

	/** 
	 * @return dealPerson 
	 */
	public String getDealPerson() {
		return dealPerson;
	}

	/**
	 * @param dealPerson the dealPerson to set
	 */
	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	/** 
	 * @return deliveryMode 
	 */
	public String getDeliveryMode() {
		return deliveryMode;
	}

	/**
	 * @param deliveryMode the deliveryMode to set
	 */
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	/** 
	 * @return endAcceptTime 
	 */
	public Date getEndAcceptTime() {
		return endAcceptTime;
	}

	/**
	 * @param endAcceptTime the endAcceptTime to set
	 */
	public void setEndAcceptTime(Date endAcceptTime) {
		this.endAcceptTime = endAcceptTime;
	}

	/** 
	 * @return feedbackInfo 
	 */
	public String getFeedbackInfo() {
		return feedbackInfo;
	}

	/**
	 * @param feedbackInfo the feedbackInfo to set
	 */
	public void setFeedbackInfo(String feedbackInfo) {
		this.feedbackInfo = feedbackInfo;
	}

	/** 
	 * @return goodsName 
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/** 
	 * @return goodsNumber 
	 */
	public Integer getGoodsNumber() {
		return goodsNumber;
	}

	/**
	 * @param goodsNumber the goodsNumber to set
	 */
	public void setGoodsNumber(Integer goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	/** 
	 * @return totalVolume 
	 */
	public Double getTotalVolume() {
		return totalVolume;
	}

	/**
	 * @param totalVolume the totalVolume to set
	 */
	public void setTotalVolume(Double totalVolume) {
		this.totalVolume = totalVolume;
	}

	/** 
	 * @return totalWeight 
	 */
	public Double getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param totalWeight the totalWeight to set
	 */
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	/** 
	 * @return goodsType 
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * @param goodsType the goodsType to set
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/** 
	 * @return insuredAmount 
	 */
	public Double getInsuredAmount() {
		return insuredAmount;
	}

	/**
	 * @param insuredAmount the insuredAmount to set
	 */
	public void setInsuredAmount(Double insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	/** 
	 * @return isReceiveGoods 
	 */
	public Boolean getIsReceiveGoods() {
		return isReceiveGoods;
	}

	/**
	 * @param isReceiveGoods the isReceiveGoods to set
	 */
	public void setIsReceiveGoods(Boolean isReceiveGoods) {
		this.isReceiveGoods = isReceiveGoods;
	}

	/** 
	 * @return isSendmessage 
	 */
	public Boolean getIsSendmessage() {
		return isSendmessage;
	}

	/**
	 * @param isSendmessage the isSendmessage to set
	 */
	public void setIsSendmessage(Boolean isSendmessage) {
		this.isSendmessage = isSendmessage;
	}

	/** 
	 * @return memberType 
	 */
	public String getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType the memberType to set
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	/** 
	 * @return onlineName 
	 */
	public String getOnlineName() {
		return onlineName;
	}

	/**
	 * @param onlineName the onlineName to set
	 */
	public void setOnlineName(String onlineName) {
		this.onlineName = onlineName;
	}

	/** 
	 * @return orderAcceptTime 
	 */
	public Date getOrderAcceptTime() {
		return orderAcceptTime;
	}

	/**
	 * @param orderAcceptTime the orderAcceptTime to set
	 */
	public void setOrderAcceptTime(Date orderAcceptTime) {
		this.orderAcceptTime = orderAcceptTime;
	}

	/** 
	 * @return orderNumber 
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
	 * @return orderPerson 
	 */
	public String getOrderPerson() {
		return orderPerson;
	}

	/**
	 * @param orderPerson the orderPerson to set
	 */
	public void setOrderPerson(String orderPerson) {
		this.orderPerson = orderPerson;
	}

	/** 
	 * @return orderStatus 
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/** 
	 * @return orderTime 
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime the orderTime to set
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/** 
	 * @return packing 
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
	 * @return paymentType 
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/** 
	 * @return receiver 
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/** 
	 * @return receiverCustAddress 
	 */
	public String getReceiverCustAddress() {
		return receiverCustAddress;
	}

	/**
	 * @param receiverCustAddress the receiverCustAddress to set
	 */
	public void setReceiverCustAddress(String receiverCustAddress) {
		this.receiverCustAddress = receiverCustAddress;
	}

	/** 
	 * @return receiverCustArea 
	 */
	public String getReceiverCustArea() {
		return receiverCustArea;
	}

	/**
	 * @param receiverCustArea the receiverCustArea to set
	 */
	public void setReceiverCustArea(String receiverCustArea) {
		this.receiverCustArea = receiverCustArea;
	}

	/** 
	 * @return receiverCustCity 
	 */
	public String getReceiverCustCity() {
		return receiverCustCity;
	}

	/**
	 * @param receiverCustCity the receiverCustCity to set
	 */
	public void setReceiverCustCity(String receiverCustCity) {
		this.receiverCustCity = receiverCustCity;
	}

	/** 
	 * @return receiverCustProvince 
	 */
	public String getReceiverCustProvince() {
		return receiverCustProvince;
	}

	/**
	 * @param receiverCustProvince the receiverCustProvince to set
	 */
	public void setReceiverCustProvince(String receiverCustProvince) {
		this.receiverCustProvince = receiverCustProvince;
	}

	/** 
	 * @return receiveProvinceCode 
	 */
	public String getReceiveProvinceCode() {
		return receiveProvinceCode;
	}

	/**
	 * @param receiveProvinceCode the receiveProvinceCode to set
	 */
	public void setReceiveProvinceCode(String receiveProvinceCode) {
		this.receiveProvinceCode = receiveProvinceCode;
	}

	/** 
	 * @return receiveCityCode 
	 */
	public String getReceiveCityCode() {
		return receiveCityCode;
	}

	/**
	 * @param receiveCityCode the receiveCityCode to set
	 */
	public void setReceiveCityCode(String receiveCityCode) {
		this.receiveCityCode = receiveCityCode;
	}

	/** 
	 * @return receiveCountyCode 
	 */
	public String getReceiveCountyCode() {
		return receiveCountyCode;
	}

	/**
	 * @param receiveCountyCode the receiveCountyCode to set
	 */
	public void setReceiveCountyCode(String receiveCountyCode) {
		this.receiveCountyCode = receiveCountyCode;
	}

	/** 
	 * @return receiverCustId 
	 */
	public String getReceiverCustId() {
		return receiverCustId;
	}

	/**
	 * @param receiverCustId the receiverCustId to set
	 */
	public void setReceiverCustId(String receiverCustId) {
		this.receiverCustId = receiverCustId;
	}

	/** 
	 * @return receiverCustMobile 
	 */
	public String getReceiverCustMobile() {
		return receiverCustMobile;
	}

	/**
	 * @param receiverCustMobile the receiverCustMobile to set
	 */
	public void setReceiverCustMobile(String receiverCustMobile) {
		this.receiverCustMobile = receiverCustMobile;
	}

	/** 
	 * @return receiverCustName 
	 */
	public String getReceiverCustName() {
		return receiverCustName;
	}

	/**
	 * @param receiverCustName the receiverCustName to set
	 */
	public void setReceiverCustName(String receiverCustName) {
		this.receiverCustName = receiverCustName;
	}

	/** 
	 * @return receiverCustNumber 
	 */
	public String getReceiverCustNumber() {
		return receiverCustNumber;
	}

	/**
	 * @param receiverCustNumber the receiverCustNumber to set
	 */
	public void setReceiverCustNumber(String receiverCustNumber) {
		this.receiverCustNumber = receiverCustNumber;
	}

	/** 
	 * @return receiverCustPhone 
	 */
	public String getReceiverCustPhone() {
		return receiverCustPhone;
	}

	/**
	 * @param receiverCustPhone the receiverCustPhone to set
	 */
	public void setReceiverCustPhone(String receiverCustPhone) {
		this.receiverCustPhone = receiverCustPhone;
	}

	/** 
	 * @return receiverCustcompany 
	 */
	public String getReceiverCustcompany() {
		return receiverCustcompany;
	}

	/**
	 * @param receiverCustcompany the receiverCustcompany to set
	 */
	public void setReceiverCustcompany(String receiverCustcompany) {
		this.receiverCustcompany = receiverCustcompany;
	}

	/** 
	 * @return receivingToPoint 
	 */
	public String getReceivingToPoint() {
		return receivingToPoint;
	}

	/**
	 * @param receivingToPoint the receivingToPoint to set
	 */
	public void setReceivingToPoint(String receivingToPoint) {
		this.receivingToPoint = receivingToPoint;
	}

	/** 
	 * @return receivingToPointName 
	 */
	public String getReceivingToPointName() {
		return receivingToPointName;
	}

	/**
	 * @param receivingToPointName the receivingToPointName to set
	 */
	public void setReceivingToPointName(String receivingToPointName) {
		this.receivingToPointName = receivingToPointName;
	}

	/** 
	 * @return reciveLoanType 
	 */
	public String getReciveLoanType() {
		return reciveLoanType;
	}

	/**
	 * @param reciveLoanType the reciveLoanType to set
	 */
	public void setReciveLoanType(String reciveLoanType) {
		this.reciveLoanType = reciveLoanType;
	}

	/** 
	 * @return resource 
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	/** 
	 * @return returnBillType 
	 */
	public String getReturnBillType() {
		return returnBillType;
	}

	/**
	 * @param returnBillType the returnBillType to set
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	/** 
	 * @return reviceMoneyAmount 
	 */
	public Double getReviceMoneyAmount() {
		return reviceMoneyAmount;
	}

	/**
	 * @param reviceMoneyAmount the reviceMoneyAmount to set
	 */
	public void setReviceMoneyAmount(Double reviceMoneyAmount) {
		this.reviceMoneyAmount = reviceMoneyAmount;
	}

	/** 
	 * @return shipperId 
	 */
	public String getShipperId() {
		return shipperId;
	}

	/**
	 * @param shipperId the shipperId to set
	 */
	public void setShipperId(String shipperId) {
		this.shipperId = shipperId;
	}

	/** 
	 * @return shipperName 
	 */
	public String getShipperName() {
		return shipperName;
	}

	/**
	 * @param shipperName the shipperName to set
	 */
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	/** 
	 * @return shipperNumber 
	 */
	public String getShipperNumber() {
		return shipperNumber;
	}

	/**
	 * @param shipperNumber the shipperNumber to set
	 */
	public void setShipperNumber(String shipperNumber) {
		this.shipperNumber = shipperNumber;
	}

	/** 
	 * @return startStation 
	 */
	public String getStartStation() {
		return startStation;
	}

	/**
	 * @param startStation the startStation to set
	 */
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}

	/** 
	 * @return startStationName 
	 */
	public String getStartStationName() {
		return startStationName;
	}

	/**
	 * @param startStationName the startStationName to set
	 */
	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}

	/** 
	 * @return transNote 
	 */
	public String getTransNote() {
		return transNote;
	}

	/**
	 * @param transNote the transNote to set
	 */
	public void setTransNote(String transNote) {
		this.transNote = transNote;
	}

	/** 
	 * @return transportMode 
	 */
	public String getTransportMode() {
		return transportMode;
	}

	/**
	 * @param transportMode the transportMode to set
	 */
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}

	/** 
	 * @return waybillNumber 
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}

	/**
	 * @param waybillNumber the waybillNumber to set
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	/** 
	 * @return couponNumber 
	 */
	public String getCouponNumber() {
		return couponNumber;
	}

	/**
	 * @param couponNumber the couponNumber to set
	 */
	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}

	/** 
	 * @return ifElecBillBigCust 
	 */
	public String getIfElecBillBigCust() {
		return ifElecBillBigCust;
	}

	/**
	 * @param ifElecBillBigCust the ifElecBillBigCust to set
	 */
	public void setIfElecBillBigCust(String ifElecBillBigCust) {
		this.ifElecBillBigCust = ifElecBillBigCust;
	}

	/** 
	 * @return orderType 
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/** 
	 * @return reciveLoanAccount 
	 */
	public String getReciveLoanAccount() {
		return reciveLoanAccount;
	}

	/**
	 * @param reciveLoanAccount the reciveLoanAccount to set
	 */
	public void setReciveLoanAccount(String reciveLoanAccount) {
		this.reciveLoanAccount = reciveLoanAccount;
	}

	/** 
	 * @return accountName 
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/** 
	 * @return contactAddrRemark 
	 */
	public String getContactAddrRemark() {
		return contactAddrRemark;
	}

	/**
	 * @param contactAddrRemark the contactAddrRemark to set
	 */
	public void setContactAddrRemark(String contactAddrRemark) {
		this.contactAddrRemark = contactAddrRemark;
	}

	/** 
	 * @return receiverCustAddrRemark 
	 */
	public String getReceiverCustAddrRemark() {
		return receiverCustAddrRemark;
	}

	/**
	 * @param receiverCustAddrRemark the receiverCustAddrRemark to set
	 */
	public void setReceiverCustAddrRemark(String receiverCustAddrRemark) {
		this.receiverCustAddrRemark = receiverCustAddrRemark;
	}

	/** 
	 * @return ifEInvoice 
	 */
	public String getIfEInvoice() {
		return ifEInvoice;
	}

	/**
	 * @param ifEInvoice the ifEInvoice to set
	 */
	public void setIfEInvoice(String ifEInvoice) {
		this.ifEInvoice = ifEInvoice;
	}

	/** 
	 * @return invoicePhone 
	 */
	public String getInvoicePhone() {
		return invoicePhone;
	}

	/**
	 * @param invoicePhone the invoicePhone to set
	 */
	public void setInvoicePhone(String invoicePhone) {
		this.invoicePhone = invoicePhone;
	}

	/** 
	 * @return invoiceMail 
	 */
	public String getInvoiceMail() {
		return invoiceMail;
	}

	/**
	 * @param invoiceMail the invoiceMail to set
	 */
	public void setInvoiceMail(String invoiceMail) {
		this.invoiceMail = invoiceMail;
	}

	/** 
	 * @return originalsaddress 
	 */
	public String getOriginalsaddress() {
		return originalsaddress;
	}

	/**
	 * @param originalsaddress the originalsaddress to set
	 */
	public void setOriginalsaddress(String originalsaddress) {
		this.originalsaddress = originalsaddress;
	}

	/** 
	 * @return originalraddress 
	 */
	public String getOriginalraddress() {
		return originalraddress;
	}

	/**
	 * @param originalraddress the originalraddress to set
	 */
	public void setOriginalraddress(String originalraddress) {
		this.originalraddress = originalraddress;
	}

	/** 
	 * @return originalsStreet 
	 */
	public String getOriginalsStreet() {
		return originalsStreet;
	}

	/**
	 * @param originalsStreet the originalsStreet to set
	 */
	public void setOriginalsStreet(String originalsStreet) {
		this.originalsStreet = originalsStreet;
	}

	/** 
	 * @return originalrStreet 
	 */
	public String getOriginalrStreet() {
		return originalrStreet;
	}

	/**
	 * @param originalrStreet the originalrStreet to set
	 */
	public void setOriginalrStreet(String originalrStreet) {
		this.originalrStreet = originalrStreet;
	}

	/** 
	 * @return recommend 
	 */
	public String getRecommend() {
		return recommend;
	}

	/**
	 * @param recommend the recommend to set
	 */
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	/** 
	 * @return freight 
	 */
	public Double getFreight() {
		return freight;
	}

	/**
	 * @param freight the freight to set
	 */
	public void setFreight(Double freight) {
		this.freight = freight;
	}

	/** 
	 * @return procurementNumber 
	 */
	public String getProcurementNumber() {
		return procurementNumber;
	}

	/**
	 * @param procurementNumber the procurementNumber to set
	 */
	public void setProcurementNumber(String procurementNumber) {
		this.procurementNumber = procurementNumber;
	}

	/** 
	 * @return ifTaoBao 
	 */
	public String getIfTaoBao() {
		return ifTaoBao;
	}

	/**
	 * @param ifTaoBao the ifTaoBao to set
	 */
	public void setIfTaoBao(String ifTaoBao) {
		this.ifTaoBao = ifTaoBao;
	}

	/** 
	 * @return markerPen 
	 */
	public String getMarkerPen() {
		return markerPen;
	}

	/**
	 * @param markerPen the markerPen to set
	 */
	public void setMarkerPen(String markerPen) {
		this.markerPen = markerPen;
	}

	/** 
	 * @return routInfo 
	 */
	public String getRoutInfo() {
		return routInfo;
	}

	/**
	 * @param routInfo the routInfo to set
	 */
	public void setRoutInfo(String routInfo) {
		this.routInfo = routInfo;
	}

	/** 
	 * @return modifyType 
	 */
	public String getModifyType() {
		return modifyType;
	}

	/**
	 * @param modifyType the modifyType to set
	 */
	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}

	/** 
	 * @return versionId 
	 */
	public Double getVersionId() {
		return versionId;
	}

	/**
	 * @param versionId the versionId to set
	 */
	public void setVersionId(Double versionId) {
		this.versionId = versionId;
	}

	@Override
	public String toString() {
		return "UpdateEOrderRequest [createDate=" + createDate
				+ ", createUser=" + createUser + ", id=" + id + ", modifyDate="
				+ modifyDate + ", modifyUser=" + modifyUser + ", acceptDept="
				+ acceptDept + ", acceptDeptName=" + acceptDeptName
				+ ", accepterPersonMobile=" + accepterPersonMobile
				+ ", beginAcceptTime=" + beginAcceptTime + ", channelCustId="
				+ channelCustId + ", channelNumber=" + channelNumber
				+ ", channelType=" + channelType + ", contactManId="
				+ contactManId + ", contactMobile=" + contactMobile
				+ ", contactName=" + contactName + ", contactPhone="
				+ contactPhone + ", contactProvince=" + contactProvince
				+ ", contactAddress=" + contactAddress + ", contactArea="
				+ contactArea + ", contactCity=" + contactCity
				+ ", contactProvinceCode=" + contactProvinceCode
				+ ", contactCityCode=" + contactCityCode + ", contactAreaCode="
				+ contactAreaCode + ", dealPerson=" + dealPerson
				+ ", deliveryMode=" + deliveryMode + ", endAcceptTime="
				+ endAcceptTime + ", feedbackInfo=" + feedbackInfo
				+ ", goodsName=" + goodsName + ", goodsNumber=" + goodsNumber
				+ ", totalVolume=" + totalVolume + ", totalWeight="
				+ totalWeight + ", goodsType=" + goodsType + ", insuredAmount="
				+ insuredAmount + ", isReceiveGoods=" + isReceiveGoods
				+ ", isSendmessage=" + isSendmessage + ", memberType="
				+ memberType + ", onlineName=" + onlineName
				+ ", orderAcceptTime=" + orderAcceptTime + ", orderNumber="
				+ orderNumber + ", orderPerson=" + orderPerson
				+ ", orderStatus=" + orderStatus + ", orderTime=" + orderTime
				+ ", packing=" + packing + ", paymentType=" + paymentType
				+ ", receiver=" + receiver + ", receiverCustAddress="
				+ receiverCustAddress + ", receiverCustArea="
				+ receiverCustArea + ", receiverCustCity=" + receiverCustCity
				+ ", receiverCustProvince=" + receiverCustProvince
				+ ", receiveProvinceCode=" + receiveProvinceCode
				+ ", receiveCityCode=" + receiveCityCode
				+ ", receiveCountyCode=" + receiveCountyCode
				+ ", receiverCustId=" + receiverCustId
				+ ", receiverCustMobile=" + receiverCustMobile
				+ ", receiverCustName=" + receiverCustName
				+ ", receiverCustNumber=" + receiverCustNumber
				+ ", receiverCustPhone=" + receiverCustPhone
				+ ", receiverCustcompany=" + receiverCustcompany
				+ ", receivingToPoint=" + receivingToPoint
				+ ", receivingToPointName=" + receivingToPointName
				+ ", reciveLoanType=" + reciveLoanType + ", resource="
				+ resource + ", returnBillType=" + returnBillType
				+ ", reviceMoneyAmount=" + reviceMoneyAmount + ", shipperId="
				+ shipperId + ", shipperName=" + shipperName
				+ ", shipperNumber=" + shipperNumber + ", startStation="
				+ startStation + ", startStationName=" + startStationName
				+ ", transNote=" + transNote + ", transportMode="
				+ transportMode + ", waybillNumber=" + waybillNumber
				+ ", couponNumber=" + couponNumber + ", ifElecBillBigCust="
				+ ifElecBillBigCust + ", orderType=" + orderType
				+ ", reciveLoanAccount=" + reciveLoanAccount + ", accountName="
				+ accountName + ", contactAddrRemark=" + contactAddrRemark
				+ ", receiverCustAddrRemark=" + receiverCustAddrRemark
				+ ", ifEInvoice=" + ifEInvoice + ", invoicePhone="
				+ invoicePhone + ", invoiceMail=" + invoiceMail
				+ ", originalsaddress=" + originalsaddress
				+ ", originalraddress=" + originalraddress
				+ ", originalsStreet=" + originalsStreet + ", originalrStreet="
				+ originalrStreet + ", recommend=" + recommend + ", freight="
				+ freight + ", procurementNumber=" + procurementNumber
				+ ", ifTaoBao=" + ifTaoBao + ", markerPen=" + markerPen
				+ ", routInfo=" + routInfo + ", modifyType=" + modifyType
				+ ", versionId=" + versionId + ",isPicPackage=" + isPicPackage 
				+",originalNumber=" + originalNumber +"]";
	}

	public String getIsPicPackage() {
		return isPicPackage;
	}

	public void setIsPicPackage(String isPicPackage) {
		this.isPicPackage = isPicPackage;
	}

	public String getOriginalNumber() {
		return originalNumber;
	}

	public void setOriginalNumber(String originalNumber) {
		this.originalNumber = originalNumber;
	}

	
}
