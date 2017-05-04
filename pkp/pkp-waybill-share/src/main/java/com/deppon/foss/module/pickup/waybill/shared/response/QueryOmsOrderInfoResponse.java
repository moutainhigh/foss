package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description oms订单结果集返回信息与FOSS，OrderInfo类个别字段不一样，故建此类 区别字段
 *              receivingToPointName - shipperName ， receivingtoPointid -
 *              startStation
 * @author 297064
 *
 */
public class QueryOmsOrderInfoResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 原订单地址
	private String consigneeCompany;
	// 代收货款账号
	private String dshkAccount;
	// 代收货款开户名
	private String dshkAccountName;
	// 是否电子运单大客户
	private String isEbigcust;
	// 大头笔
	private String markerPen;
	// 线下运单号
	private String offlineWaybillnum;
	// 原始收货人地址
	private String originalRaddress;
	// 到达镇街道
	private String originalRstreet;
	// 原始发货人地址
	private String originalSaddress;
	// 发货镇街道
	private String originalSstreet;
	// 其他费用
	private BigDecimal otherfee;
	// 费用承担部门
	private String paymentOrgcode;

	// 收货联系人区县编码
	private String receiverCustAreaCode;
	// 收货联系人城市编码
	private String receiverCustCityCode;
	// 收货联系人省份编码
	private String receiverCustProvinceCode;
	// 收货联系人镇街道
	private String receiveTown;
	// 收货联系人镇街道编码
	private String receiveTownCode;
	// 是否推荐物流
	private String recommend;
	// 集包地
	private String routInfo;
	// 发货人工号
	private String senderCode;
	// 特安上限
	private Double teanlimit;
	// 版本号
	private Double versionId;
	// 联系人id
	private String contactManId;

	// 受理部门
	private String acceptDept;
	// 受理部门名称
	private String acceptDeptName;
	//
	private String tableType;
	// 制单员工部门标杆编码
	private String createUserDeptnum;
	//
	// 是否字母件(Y/N)
	private String isPicPackage;

	// 是否淘系订单
	private String isTaobao;
	// 原始订单号
	private String originalNumber;
	// 服务类型
	private String serviceType;
	// 零担重泡比基数
	private Double weightBubbleRate;
	// 快递重泡比基数
	private Double exWeightBubbleRate;
	/**
	 * 到达网点名称
	 */
	protected String receivingToPointName;
	/**
	 * 始发网点名称
	 */
	protected String startStationName;
	// 受理人
	private String accepter;
	// 接货员联系方式
	private String accepterMobile;
	// 接货员姓名
	private String accepterName;
	// 受理部门ID
	private String acceptDeptid;
	// 订单受理时间
	private Date acceptTime;
	// 收货人联系人编码
	private String arriveLinkmannum;
	// 接货起始时间
	private Date beginaccepTime;
	// 保价声明价值
	private Double bjsmjz;
	// 渠道客户ID
	private String channelCustId;
	// 渠道单号
	private String channelNumber;
	// 渠道类型
	private String channelType;
	// 收件客户所属公司
	private String company;
	// 发货联系人详细地址
	private String contactAddress;
	// 发货联系人区县
	private String contactArea;
	// 发货联系人区县编码
	private String contactAreaCode;
	// 发货联系人城市
	private String contactCity;
	// 发货联系人城市编码
	private String contactCityCode;
	// 发货放地址备注
	private String contactComments;
	// 发货联系人手机
	private String contactMobile;
	// 发货联系人名称
	private String contactName;
	// 发货联系人固话
	private String contactPhone;
	// 发货联系人省份
	private String contactProvince;
	// 发货联系人省份编码
	private String contactProvinceCode;
	// 优惠券
	private String coupon;
	// 制单员工工号
	private String createUsernum;
	// 客户分群
	private String custGroup;
	// 延迟时间
	private Date delayorderTime;
	// 提货方式
	private String deliveryMode;
	// 始发网点ID
	private String departureId;
	// 发货联系人编码
	private String departLinkmannum;
	// 代收货款金额
	private Double dshk;
	// 代收货款类型
	private String dshkType;
	// 接货结束时间
	private Date endaccpTime;
	// 反馈信息
	private String feedbackInfo;
	// 纯运费
	private Double freight;
	// 货物名称
	private String goodsName;
	// 货物类型
	private String goodsType;
	// 催单次数
	private String hastenCount;
	// ID
	private String id;
	// 是否电子发票
	private String ifeinvoice;
	// 电子发票邮箱
	private String invoiceMail;
	// 电子发票手机号
	private String invoicePhone;
	// 是否接货
	private Boolean isreceivegood;
	// 是否短信通知
	private Boolean issendms;
	// 最后催单时间
	private Date lastHastenTime;
	// 联系人ID
	private String linkmanId;
	// 阿里巴巴会员类型
	private String memberType;
	// 官网收货人德邦用户名
	private String onlineName;
	// 订单创建时间
	private Date ordercreateTime;
	// 订单号
	private String orderNumber;
	// 下单人
	private String orderPerson;
	// 订单状态
	private String orderStatus;
	// 下单时间
	private Date orderTime;
	// 订单类型
	private String orderType;
	// 包装材料
	private String packing;
	// 付款方式
	private String paymentType;
	// 采购单号
	private String procurementNumber;
	// 收货联系人区县编码
	private String receiveAreaCode;
	// 收货联系人城市编码
	private String receiveCityCode;
	// 收货联系人省份编码
	private String receiveProvinceCode;
	// 说活联系人详细地址
	private String receiveAddress;
	// 收货联系人区县
	private String receiveArea;
	// 收获联系人城市
	private String receiveCity;
	// 收货方地址备注
	private String receiveComments;
	// 收货客户ID
	private String receiveId;
	// 收货联系人手机
	private String receiveMobile;
	// 修改类型
	private String modifyType;

	// 收货联系人名称
	private String receiveName;
	// 收货客户编码
	private String receiveNumber;
	// 收货联系人固话
	private String receivePhone;
	// 收货联系人省份
	private String receiveProvince;
	// 到达网点ID
	private String receivingtoPointid;
	// 备注（网点名称、电话、快递员手机、电话）
	private String remark;
	// 订单来源
	private String resource;
	// 签收单方式
	private String returnbillType;
	// 发货客户ID
	private String shipperId;
	// 发货客户名称
	private String shipperName;
	// 发货客户编码
	private String shipperNumber;

	// 托运货物总件数
	private Integer totalPiece;
	// 托运货物总体积
	private Double totalVolume;
	// 托运货物总重量
	private Double totalWeight;
	// 最后一个受理时间
	private Date towaitAcceptTime;
	// 运输方式
	private String transportMode;
	// 储运事项
	private String transNote;
	// 运单号
	private String waybillNumber;
	// 客户订单号
	private String custOrderline;
	// 最后修改时间
	private Date lastUpdateTime;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	// 制单员姓名
	private String createUserName;

	// 收货客户名称
	private String receiveCustName;

	// 是否打印过
	private String isNotPrint;

	private String createUserid;
	// 发货联系人镇街道编码
	private String contactTownCode;
	// 发货联系人镇街道
	private String contactTown;
	//门店编号  OMS查询网上订单接口有该字段，新增防止json解析错误   2016-08-08 16:00 355673 陈鹏 
	private String storeCode;
	/**
	 * @return the consigneeCompany
	 */
	public String getConsigneeCompany() {
		return consigneeCompany;
	}
	/**
	 * @param consigneeCompany the consigneeCompany to set
	 */
	public void setConsigneeCompany(String consigneeCompany) {
		this.consigneeCompany = consigneeCompany;
	}
	/**
	 * @return the dshkAccount
	 */
	public String getDshkAccount() {
		return dshkAccount;
	}
	/**
	 * @param dshkAccount the dshkAccount to set
	 */
	public void setDshkAccount(String dshkAccount) {
		this.dshkAccount = dshkAccount;
	}
	/**
	 * @return the dshkAccountName
	 */
	public String getDshkAccountName() {
		return dshkAccountName;
	}
	/**
	 * @param dshkAccountName the dshkAccountName to set
	 */
	public void setDshkAccountName(String dshkAccountName) {
		this.dshkAccountName = dshkAccountName;
	}
	/**
	 * @return the isEbigcust
	 */
	public String getIsEbigcust() {
		return isEbigcust;
	}
	/**
	 * @param isEbigcust the isEbigcust to set
	 */
	public void setIsEbigcust(String isEbigcust) {
		this.isEbigcust = isEbigcust;
	}
	/**
	 * @return the markerPen
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
	 * @return the offlineWaybillnum
	 */
	public String getOfflineWaybillnum() {
		return offlineWaybillnum;
	}
	/**
	 * @param offlineWaybillnum the offlineWaybillnum to set
	 */
	public void setOfflineWaybillnum(String offlineWaybillnum) {
		this.offlineWaybillnum = offlineWaybillnum;
	}
	/**
	 * @return the originalRaddress
	 */
	public String getOriginalRaddress() {
		return originalRaddress;
	}
	/**
	 * @param originalRaddress the originalRaddress to set
	 */
	public void setOriginalRaddress(String originalRaddress) {
		this.originalRaddress = originalRaddress;
	}
	/**
	 * @return the originalRstreet
	 */
	public String getOriginalRstreet() {
		return originalRstreet;
	}
	/**
	 * @param originalRstreet the originalRstreet to set
	 */
	public void setOriginalRstreet(String originalRstreet) {
		this.originalRstreet = originalRstreet;
	}
	/**
	 * @return the originalSaddress
	 */
	public String getOriginalSaddress() {
		return originalSaddress;
	}
	/**
	 * @param originalSaddress the originalSaddress to set
	 */
	public void setOriginalSaddress(String originalSaddress) {
		this.originalSaddress = originalSaddress;
	}
	/**
	 * @return the originalSstreet
	 */
	public String getOriginalSstreet() {
		return originalSstreet;
	}
	/**
	 * @param originalSstreet the originalSstreet to set
	 */
	public void setOriginalSstreet(String originalSstreet) {
		this.originalSstreet = originalSstreet;
	}
	/**
	 * @return the otherfee
	 */
	public BigDecimal getOtherfee() {
		return otherfee;
	}
	/**
	 * @param otherfee the otherfee to set
	 */
	public void setOtherfee(BigDecimal otherfee) {
		this.otherfee = otherfee;
	}
	/**
	 * @return the paymentOrgcode
	 */
	public String getPaymentOrgcode() {
		return paymentOrgcode;
	}
	/**
	 * @param paymentOrgcode the paymentOrgcode to set
	 */
	public void setPaymentOrgcode(String paymentOrgcode) {
		this.paymentOrgcode = paymentOrgcode;
	}
	/**
	 * @return the receiverCustAreaCode
	 */
	public String getReceiverCustAreaCode() {
		return receiverCustAreaCode;
	}
	/**
	 * @param receiverCustAreaCode the receiverCustAreaCode to set
	 */
	public void setReceiverCustAreaCode(String receiverCustAreaCode) {
		this.receiverCustAreaCode = receiverCustAreaCode;
	}
	/**
	 * @return the receiverCustCityCode
	 */
	public String getReceiverCustCityCode() {
		return receiverCustCityCode;
	}
	/**
	 * @param receiverCustCityCode the receiverCustCityCode to set
	 */
	public void setReceiverCustCityCode(String receiverCustCityCode) {
		this.receiverCustCityCode = receiverCustCityCode;
	}
	/**
	 * @return the receiverCustProvinceCode
	 */
	public String getReceiverCustProvinceCode() {
		return receiverCustProvinceCode;
	}
	/**
	 * @param receiverCustProvinceCode the receiverCustProvinceCode to set
	 */
	public void setReceiverCustProvinceCode(String receiverCustProvinceCode) {
		this.receiverCustProvinceCode = receiverCustProvinceCode;
	}
	/**
	 * @return the receiveTown
	 */
	public String getReceiveTown() {
		return receiveTown;
	}
	/**
	 * @param receiveTown the receiveTown to set
	 */
	public void setReceiveTown(String receiveTown) {
		this.receiveTown = receiveTown;
	}
	/**
	 * @return the receiveTownCode
	 */
	public String getReceiveTownCode() {
		return receiveTownCode;
	}
	/**
	 * @param receiveTownCode the receiveTownCode to set
	 */
	public void setReceiveTownCode(String receiveTownCode) {
		this.receiveTownCode = receiveTownCode;
	}
	/**
	 * @return the recommend
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
	 * @return the routInfo
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
	 * @return the senderCode
	 */
	public String getSenderCode() {
		return senderCode;
	}
	/**
	 * @param senderCode the senderCode to set
	 */
	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}
	/**
	 * @return the teanlimit
	 */
	public Double getTeanlimit() {
		return teanlimit;
	}
	/**
	 * @param teanlimit the teanlimit to set
	 */
	public void setTeanlimit(Double teanlimit) {
		this.teanlimit = teanlimit;
	}
	/**
	 * @return the versionId
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
	/**
	 * @return the contactManId
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
	 * @return the acceptDept
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
	 * @return the acceptDeptName
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
	 * @return the tableType
	 */
	public String getTableType() {
		return tableType;
	}
	/**
	 * @param tableType the tableType to set
	 */
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	/**
	 * @return the createUserDeptnum
	 */
	public String getCreateUserDeptnum() {
		return createUserDeptnum;
	}
	/**
	 * @param createUserDeptnum the createUserDeptnum to set
	 */
	public void setCreateUserDeptnum(String createUserDeptnum) {
		this.createUserDeptnum = createUserDeptnum;
	}
	/**
	 * @return the isPicPackage
	 */
	public String getIsPicPackage() {
		return isPicPackage;
	}
	/**
	 * @param isPicPackage the isPicPackage to set
	 */
	public void setIsPicPackage(String isPicPackage) {
		this.isPicPackage = isPicPackage;
	}
	/**
	 * @return the isTaobao
	 */
	public String getIsTaobao() {
		return isTaobao;
	}
	/**
	 * @param isTaobao the isTaobao to set
	 */
	public void setIsTaobao(String isTaobao) {
		this.isTaobao = isTaobao;
	}
	/**
	 * @return the originalNumber
	 */
	public String getOriginalNumber() {
		return originalNumber;
	}
	/**
	 * @param originalNumber the originalNumber to set
	 */
	public void setOriginalNumber(String originalNumber) {
		this.originalNumber = originalNumber;
	}
	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * @return the weightBubbleRate
	 */
	public Double getWeightBubbleRate() {
		return weightBubbleRate;
	}
	/**
	 * @param weightBubbleRate the weightBubbleRate to set
	 */
	public void setWeightBubbleRate(Double weightBubbleRate) {
		this.weightBubbleRate = weightBubbleRate;
	}
	/**
	 * @return the exWeightBubbleRate
	 */
	public Double getExWeightBubbleRate() {
		return exWeightBubbleRate;
	}
	/**
	 * @param exWeightBubbleRate the exWeightBubbleRate to set
	 */
	public void setExWeightBubbleRate(Double exWeightBubbleRate) {
		this.exWeightBubbleRate = exWeightBubbleRate;
	}
	/**
	 * @return the receivingToPointName
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
	 * @return the startStationName
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
	 * @return the accepter
	 */
	public String getAccepter() {
		return accepter;
	}
	/**
	 * @param accepter the accepter to set
	 */
	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}
	/**
	 * @return the accepterMobile
	 */
	public String getAccepterMobile() {
		return accepterMobile;
	}
	/**
	 * @param accepterMobile the accepterMobile to set
	 */
	public void setAccepterMobile(String accepterMobile) {
		this.accepterMobile = accepterMobile;
	}
	/**
	 * @return the accepterName
	 */
	public String getAccepterName() {
		return accepterName;
	}
	/**
	 * @param accepterName the accepterName to set
	 */
	public void setAccepterName(String accepterName) {
		this.accepterName = accepterName;
	}
	/**
	 * @return the acceptDeptid
	 */
	public String getAcceptDeptid() {
		return acceptDeptid;
	}
	/**
	 * @param acceptDeptid the acceptDeptid to set
	 */
	public void setAcceptDeptid(String acceptDeptid) {
		this.acceptDeptid = acceptDeptid;
	}
	/**
	 * @return the acceptTime
	 */
	public Date getAcceptTime() {
		return acceptTime;
	}
	/**
	 * @param acceptTime the acceptTime to set
	 */
	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}
	/**
	 * @return the arriveLinkmannum
	 */
	public String getArriveLinkmannum() {
		return arriveLinkmannum;
	}
	/**
	 * @param arriveLinkmannum the arriveLinkmannum to set
	 */
	public void setArriveLinkmannum(String arriveLinkmannum) {
		this.arriveLinkmannum = arriveLinkmannum;
	}
	/**
	 * @return the beginaccepTime
	 */
	public Date getBeginaccepTime() {
		return beginaccepTime;
	}
	/**
	 * @param beginaccepTime the beginaccepTime to set
	 */
	public void setBeginaccepTime(Date beginaccepTime) {
		this.beginaccepTime = beginaccepTime;
	}
	/**
	 * @return the bjsmjz
	 */
	public Double getBjsmjz() {
		return bjsmjz;
	}
	/**
	 * @param bjsmjz the bjsmjz to set
	 */
	public void setBjsmjz(Double bjsmjz) {
		this.bjsmjz = bjsmjz;
	}
	/**
	 * @return the channelCustId
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
	 * @return the channelNumber
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
	 * @return the channelType
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
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return the contactAddress
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
	 * @return the contactArea
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
	 * @return the contactAreaCode
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
	 * @return the contactCity
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
	 * @return the contactCityCode
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
	 * @return the contactComments
	 */
	public String getContactComments() {
		return contactComments;
	}
	/**
	 * @param contactComments the contactComments to set
	 */
	public void setContactComments(String contactComments) {
		this.contactComments = contactComments;
	}
	/**
	 * @return the contactMobile
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
	 * @return the contactName
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
	 * @return the contactPhone
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
	 * @return the contactProvince
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
	 * @return the contactProvinceCode
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
	 * @return the coupon
	 */
	public String getCoupon() {
		return coupon;
	}
	/**
	 * @param coupon the coupon to set
	 */
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	/**
	 * @return the createUsernum
	 */
	public String getCreateUsernum() {
		return createUsernum;
	}
	/**
	 * @param createUsernum the createUsernum to set
	 */
	public void setCreateUsernum(String createUsernum) {
		this.createUsernum = createUsernum;
	}
	/**
	 * @return the custGroup
	 */
	public String getCustGroup() {
		return custGroup;
	}
	/**
	 * @param custGroup the custGroup to set
	 */
	public void setCustGroup(String custGroup) {
		this.custGroup = custGroup;
	}
	/**
	 * @return the delayorderTime
	 */
	public Date getDelayorderTime() {
		return delayorderTime;
	}
	/**
	 * @param delayorderTime the delayorderTime to set
	 */
	public void setDelayorderTime(Date delayorderTime) {
		this.delayorderTime = delayorderTime;
	}
	/**
	 * @return the deliveryMode
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
	 * @return the departureId
	 */
	public String getDepartureId() {
		return departureId;
	}
	/**
	 * @param departureId the departureId to set
	 */
	public void setDepartureId(String departureId) {
		this.departureId = departureId;
	}
	/**
	 * @return the departLinkmannum
	 */
	public String getDepartLinkmannum() {
		return departLinkmannum;
	}
	/**
	 * @param departLinkmannum the departLinkmannum to set
	 */
	public void setDepartLinkmannum(String departLinkmannum) {
		this.departLinkmannum = departLinkmannum;
	}
	/**
	 * @return the dshk
	 */
	public Double getDshk() {
		return dshk;
	}
	/**
	 * @param dshk the dshk to set
	 */
	public void setDshk(Double dshk) {
		this.dshk = dshk;
	}
	/**
	 * @return the dshkType
	 */
	public String getDshkType() {
		return dshkType;
	}
	/**
	 * @param dshkType the dshkType to set
	 */
	public void setDshkType(String dshkType) {
		this.dshkType = dshkType;
	}
	/**
	 * @return the endaccpTime
	 */
	public Date getEndaccpTime() {
		return endaccpTime;
	}
	/**
	 * @param endaccpTime the endaccpTime to set
	 */
	public void setEndaccpTime(Date endaccpTime) {
		this.endaccpTime = endaccpTime;
	}
	/**
	 * @return the feedbackInfo
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
	 * @return the freight
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
	 * @return the goodsName
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
	 * @return the goodsType
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
	 * @return the hastenCount
	 */
	public String getHastenCount() {
		return hastenCount;
	}
	/**
	 * @param hastenCount the hastenCount to set
	 */
	public void setHastenCount(String hastenCount) {
		this.hastenCount = hastenCount;
	}
	/**
	 * @return the id
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
	 * @return the ifeinvoice
	 */
	public String getIfeinvoice() {
		return ifeinvoice;
	}
	/**
	 * @param ifeinvoice the ifeinvoice to set
	 */
	public void setIfeinvoice(String ifeinvoice) {
		this.ifeinvoice = ifeinvoice;
	}
	/**
	 * @return the invoiceMail
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
	 * @return the invoicePhone
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
	 * @return the isreceivegood
	 */
	public Boolean getIsreceivegood() {
		return isreceivegood;
	}
	/**
	 * @param isreceivegood the isreceivegood to set
	 */
	public void setIsreceivegood(Boolean isreceivegood) {
		this.isreceivegood = isreceivegood;
	}
	/**
	 * @return the issendms
	 */
	public Boolean getIssendms() {
		return issendms;
	}
	/**
	 * @param issendms the issendms to set
	 */
	public void setIssendms(Boolean issendms) {
		this.issendms = issendms;
	}
	/**
	 * @return the lastHastenTime
	 */
	public Date getLastHastenTime() {
		return lastHastenTime;
	}
	/**
	 * @param lastHastenTime the lastHastenTime to set
	 */
	public void setLastHastenTime(Date lastHastenTime) {
		this.lastHastenTime = lastHastenTime;
	}
	/**
	 * @return the linkmanId
	 */
	public String getLinkmanId() {
		return linkmanId;
	}
	/**
	 * @param linkmanId the linkmanId to set
	 */
	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}
	/**
	 * @return the memberType
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
	 * @return the onlineName
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
	 * @return the ordercreateTime
	 */
	public Date getOrdercreateTime() {
		return ordercreateTime;
	}
	/**
	 * @param ordercreateTime the ordercreateTime to set
	 */
	public void setOrdercreateTime(Date ordercreateTime) {
		this.ordercreateTime = ordercreateTime;
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
	 * @return the orderPerson
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
	 * @return the orderStatus
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
	 * @return the orderTime
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
	 * @return the orderType
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
	 * @return the paymentType
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
	 * @return the procurementNumber
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
	 * @return the receiveAreaCode
	 */
	public String getReceiveAreaCode() {
		return receiveAreaCode;
	}
	/**
	 * @param receiveAreaCode the receiveAreaCode to set
	 */
	public void setReceiveAreaCode(String receiveAreaCode) {
		this.receiveAreaCode = receiveAreaCode;
	}
	/**
	 * @return the receiveCityCode
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
	 * @return the receiveProvinceCode
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
	 * @return the receiveAddress
	 */
	public String getReceiveAddress() {
		return receiveAddress;
	}
	/**
	 * @param receiveAddress the receiveAddress to set
	 */
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	/**
	 * @return the receiveArea
	 */
	public String getReceiveArea() {
		return receiveArea;
	}
	/**
	 * @param receiveArea the receiveArea to set
	 */
	public void setReceiveArea(String receiveArea) {
		this.receiveArea = receiveArea;
	}
	/**
	 * @return the receiveCity
	 */
	public String getReceiveCity() {
		return receiveCity;
	}
	/**
	 * @param receiveCity the receiveCity to set
	 */
	public void setReceiveCity(String receiveCity) {
		this.receiveCity = receiveCity;
	}
	/**
	 * @return the receiveComments
	 */
	public String getReceiveComments() {
		return receiveComments;
	}
	/**
	 * @param receiveComments the receiveComments to set
	 */
	public void setReceiveComments(String receiveComments) {
		this.receiveComments = receiveComments;
	}
	/**
	 * @return the receiveId
	 */
	public String getReceiveId() {
		return receiveId;
	}
	/**
	 * @param receiveId the receiveId to set
	 */
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}
	/**
	 * @return the receiveMobile
	 */
	public String getReceiveMobile() {
		return receiveMobile;
	}
	/**
	 * @param receiveMobile the receiveMobile to set
	 */
	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}
	/**
	 * @return the modifyType
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
	 * @return the receiveName
	 */
	public String getReceiveName() {
		return receiveName;
	}
	/**
	 * @param receiveName the receiveName to set
	 */
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	/**
	 * @return the receiveNumber
	 */
	public String getReceiveNumber() {
		return receiveNumber;
	}
	/**
	 * @param receiveNumber the receiveNumber to set
	 */
	public void setReceiveNumber(String receiveNumber) {
		this.receiveNumber = receiveNumber;
	}
	/**
	 * @return the receivePhone
	 */
	public String getReceivePhone() {
		return receivePhone;
	}
	/**
	 * @param receivePhone the receivePhone to set
	 */
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	/**
	 * @return the receiveProvince
	 */
	public String getReceiveProvince() {
		return receiveProvince;
	}
	/**
	 * @param receiveProvince the receiveProvince to set
	 */
	public void setReceiveProvince(String receiveProvince) {
		this.receiveProvince = receiveProvince;
	}
	/**
	 * @return the receivingtoPointid
	 */
	public String getReceivingtoPointid() {
		return receivingtoPointid;
	}
	/**
	 * @param receivingtoPointid the receivingtoPointid to set
	 */
	public void setReceivingtoPointid(String receivingtoPointid) {
		this.receivingtoPointid = receivingtoPointid;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the resource
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
	 * @return the returnbillType
	 */
	public String getReturnbillType() {
		return returnbillType;
	}
	/**
	 * @param returnbillType the returnbillType to set
	 */
	public void setReturnbillType(String returnbillType) {
		this.returnbillType = returnbillType;
	}
	/**
	 * @return the shipperId
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
	 * @return the shipperName
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
	 * @return the shipperNumber
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
	 * @return the totalPiece
	 */
	public Integer getTotalPiece() {
		return totalPiece;
	}
	/**
	 * @param totalPiece the totalPiece to set
	 */
	public void setTotalPiece(Integer totalPiece) {
		this.totalPiece = totalPiece;
	}
	/**
	 * @return the totalVolume
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
	 * @return the totalWeight
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
	 * @return the towaitAcceptTime
	 */
	public Date getTowaitAcceptTime() {
		return towaitAcceptTime;
	}
	/**
	 * @param towaitAcceptTime the towaitAcceptTime to set
	 */
	public void setTowaitAcceptTime(Date towaitAcceptTime) {
		this.towaitAcceptTime = towaitAcceptTime;
	}
	/**
	 * @return the transportMode
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
	 * @return the transNote
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
	 * @return the waybillNumber
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
	 * @return the custOrderline
	 */
	public String getCustOrderline() {
		return custOrderline;
	}
	/**
	 * @param custOrderline the custOrderline to set
	 */
	public void setCustOrderline(String custOrderline) {
		this.custOrderline = custOrderline;
	}
	/**
	 * @return the lastUpdateTime
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 * @return the receiveCustName
	 */
	public String getReceiveCustName() {
		return receiveCustName;
	}
	/**
	 * @param receiveCustName the receiveCustName to set
	 */
	public void setReceiveCustName(String receiveCustName) {
		this.receiveCustName = receiveCustName;
	}
	/**
	 * @return the isNotPrint
	 */
	public String getIsNotPrint() {
		return isNotPrint;
	}
	/**
	 * @param isNotPrint the isNotPrint to set
	 */
	public void setIsNotPrint(String isNotPrint) {
		this.isNotPrint = isNotPrint;
	}
	/**
	 * @return the createUserid
	 */
	public String getCreateUserid() {
		return createUserid;
	}
	/**
	 * @param createUserid the createUserid to set
	 */
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}
	/**
	 * @return the contactTownCode
	 */
	public String getContactTownCode() {
		return contactTownCode;
	}
	/**
	 * @param contactTownCode the contactTownCode to set
	 */
	public void setContactTownCode(String contactTownCode) {
		this.contactTownCode = contactTownCode;
	}
	/**
	 * @return the contactTown
	 */
	public String getContactTown() {
		return contactTown;
	}
	/**
	 * @param contactTown the contactTown to set
	 */
	public void setContactTown(String contactTown) {
		this.contactTown = contactTown;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
	
}
