package com.deppon.foss.module.transfer.dubbo.api.define;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 
 * 运单详细信息
 * @author 043258-foss-zhaobin
 * @date 2013-1-6 下午3:31:26
 */
public class WaybillInfoDto implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 运输类型
	 */
	private String transportType;
	
	/**
	 * 运输性质
	 */
	private String productCode;
	
	/**
	 * 发货客户名称
	 */
	private String deliveryCustomerName;
	
	/**
	 * 发货客户电话
	 */
	private String deliveryCustomerPhone;
	
	/**
	 * 发货客户手机
	 */
	private String deliveryCustomerMobilephone;
	
	/**
	 * 始发站
	 */
	private String deliveryCustomerCityCode;
	
	/**
	 * 始发站名称
	 */
	private String deliveryCustomerCityName;
	
	/**
	 * 发货人地址
	 */
	private String deliveryCustomerAddress;
	
	/**
	 * 发货人地址备注
	 */
	private String deliveryCustomerAddressNote;
	
	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName;
	
	/**
	 * 收货客户电话
	 */
	private String receiveCustomerPhone;
	
	/**
	 * 收货客户手机
	 */
	private String receiveCustomerMobilephone;
	
	/**
	 * 目的站
	 */
	private String targetOrgCode;
	
	/**
	 * 目的站名称
	 */
	private String targetOrgName;
	
	/**
	 * 收货人地址
	 */
	private String receiveCustomerAddress;   
	
	/**
	 * 收货人地址
	 */
	private String receiveCustomerAddressNote;  
	
	/**
	 * 货物名称
	 */
	private String goodsName;
	
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;
	
	/**
	 * 货物总重量
	 */
	private BigDecimal goodsWeightTotal;

	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;
	
	/**
	 * 总费用
	 */
	private BigDecimal totalFee;
	
	/**
	 * 开单付款方式
	 */
	private String paidMethod;

	/**
	 * 预付金额
	 */
	private BigDecimal prePayAmount;
	
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;
	
	/**
	 * 代收货款类型
	 */
	private String refundType;
	
	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal codFee;
	
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	
	/**
	 * 接货费
	 */
	private BigDecimal pickupFee;
	
	/**
	 * 送货费
	 */
	private BigDecimal deliveryGoodsFee;

	/**
	 * 包装手续费
	 */
	private BigDecimal packageFee;
	
	/**
	 * 装卸费
	 */
	private BigDecimal serviceFee;
	
	/**
	 * 公布价运费
	 */
	private BigDecimal transportFee;
	
	/**
	 * 收货部门
	 */
	private String receiveOrgCode;
	
	/**
	 * 收货部门名称
	 */
	private String receiveOrgName;
	
	/**
	 * 收货部门标杆编码
	 */
	private String receiveOrgNumber;
	
	/**
	 * 出发部门名称
	 */
	private String departureDeptName;
	
	/**
	 * 出发部门标杆编码
	 */
	private String departureDeptNumber;
	
	/**
	 * 出发部门地址
	 */
	private String departureDeptAddr;
	
	/**
	 * 出发部门电话
	 */
	private String departureDeptPhone;
	
	/**
	 * 出发部门传真
	 */
	private String departureDeptFax;
	
	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;
	
	/**
	 * 到达网点名称
	 */
	private String ladingStationName;
	
	/**
	 * 到达网点标杆编码
	 */
	private String ladingStationNumber;
	
	/**
	 * 到达网点地址
	 */
	private String ladingStationAddr;
	
	/**
	 * 到达网点电话
	 */
	private String ladingStationPhone;
	
	/**
	 * 到达网点传真
	 */
	private String ladingStationFax;
	
	
	/**
	 *  签收情况
	 */
	private String signSituation;
	
	/**
	 * 是否签收
	 */
	private boolean isSigned;
	
	/**
	 * 是否正常签收
	 */
	private boolean isNormalSigned;
	
	/**
	 * 是否异常签收
	 */
	private boolean isAbnormalSigned;
	
	/**
	 * 是否弃货签收
	 */
	private boolean isAbandonGoodsSigned;
	
	/**
	 *  签收录入人
	 */
	private String deliverymanName;
	
	/**
	 *  签收时间
	 */
	private Date signTime;
	
	/**
	 * 第一次签收时间
	 */
	private Date firstSignTime;
	/**
	 *  签收备注
	 */
	private String signNote;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 保价声明价值
	 */
	private BigDecimal insuranceAmount;

	/**
	 * 保价费
	 */
	private BigDecimal insuranceFee;
	
	/**
	 * 货物包装
	 */
	private String goodsPackage;
	
	/**
	 * 运单状态
	 */
	private String active;
	
	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;
	
	/**
	 * 对外备注
	 */
	private String outerNotes;
	
	/**
	 * 发货客户编码
	 */
	private String deliveryCustomerCode;
	
	/**
	 * 收货客户编码
	 */
	private String receiveCustomerCode;
	
	/**
	 * 结清状态
	 */
	private String settleStatus;
	
	/**
	 * 返单类别
	 */
	private String returnBillType;
	
	/**
	 * 储运事项
	 */
	private String transportationRemark;
	
	/**
	 * 发货时间
	 */
	private Date billTime;
	
	/**
	 * 配载部门
	 */
	private String loadOrgCode;
	
	/**
	 * 配载部门编码
	 */
	private String loadOrgNumber;
	
	/**
	 * 配载部门名称
	 */
	private String loadOrgName;
	
	/**
	 * 发货省份
	 */
	private String deliveryCustomerProvCode;
	
	/**
	 * 发货省份名称
	 */
	private String deliveryCustomerProvName;

	/**
	 * 发货市
	 */
	private String deliveryCustomerCityCode1;
	
	/**
	 * 发货市名称
	 */
	private String deliveryCustomerCityName1;
	
	/**
	 * 发货区
	 */
	private String deliveryCustomerDistCode;
	
	/**
	 * 收货省份 
	 */
	private String receiveCustomerProvCode;
	
	/**
	 * 收货省份名称
	 */
	private String receiveCustomerProvName;

	/**
	 * 收货市
	 */
	private String receiveCustomerCityCode;
	
	/** 
	 * 收货区
	 */
	private String receiveCustomerDistCode;
	
	/**
	 * 收货市名称
	 */
	private String receiveCustomerCityName;
	
	/**
	 * 是否上门接货
	 */
	private String pickupToDoor;
	
	/**
	 * 是否上门接货
	 */
	private boolean pickup;
	
	/**
	 * 通知状态
	 */
	private String notificationResult;
	
	/**
	 * 是否通知
	 */
	private boolean notification;
	
	/**
	 * @author -- wutao
	 * 官网需要的大客户标识：需要的是发货客户是否大客户
	 * @date 2014-10-29
	 */
	private String isBigDeliverCustomer;
	/**
	 * @author wutao
	 * @date 2015-02-04
	 * @see:这个字段字段返回给官网：返货类型
	 */
	private String returnType;
	/**
	 * @author wutao
	 * @date 2015-02-04
	 * @see:这个字段字段返回给官网：原运单号
	 */
	private String originalWaybillNo;
	/**
	 * 运单费用明细
	 */
    private List<WaybillChargeCostDto> waybillChargeCostDto;
    
    //
   //-----------------------快递接口新添加的字段
    /**
     * 运单所属快递大区编码
     */
    private String districtCode;
    
    /**
     * 运单所属快递大区名称
     */
    private String districtName;
    
    /**
     * 运单所属快递大区标杆编码
     */
    private String districtUnifiedCode;
    
    
    /**
     * 运单到达所属快递大区编码
     */
    private String endDistrictCode;
    
    /**
     * 运单到达所属快递大区名称
     */
    private String endDistrictName;
    
    /**
     * 运单到达所属快递大区标杆编码
     */
    private String endDistrictUnifiedCode;


    /**
     * 快递员CODE-出发
     */
    private String startExpressEmpCode;
    
    /**
     * 快递员名称-出发
     */
    private String startExpressEmpName;
    
    /**
     * 快递点部CODE-出发
     */
    private String startExpressOrgCode; 
    
    /**
     * 快递点部标杆编码-出发
     */
    private String startExpressUnfiedCode;
    
    /**
     * 快递点部名称-出发
     */
    private String startExpressOrgName;
    
    /**
     * 快递员CODE-到达
     */
    private String endExpressEmpCode;
    
    /**
     * 快递员名称-到达
     */
    private String endExpressEmpName;
    
    /**
     * 快递点部CODE-到达
     */
    private String endExpressOrgCode; 
    
    /**
     * 快递点部标杆编码-到达
     */
    private String endExpressUnfiedCode;
    
    /**
     * 快递点部名称-到达
     */
    private String endExpressOrgName;
    
    /**
     * 开单部门编码
     */
    private String createOrgCode;
    /**
     * 开单部门名称
     */
    private String createOrgName;
    //-----------------------快递接口新添加的字段end
    
    //-----------------------OA差错细分及自动化接口新添的字段
    /**
     * 
     * 补码操作部门编码
     */
    private String complementOperationOrgCode;
    
    /**
     * 补码操作部门名称
     */
    private String complementOperationOrgName;
    
    /**
     * 部门负责人工号
     */
    private String principalNo;
    
    /**
     * 部门负责人名称
     */
    private String principalName;
    
    /**
     * 部门名称
     */
    private String principalOrgName;
    
    /**
     * 部门编码
     */
    private String  principalOrgCode;
    
    /**
     * 开单人code
     */
    private String createUserCode;
    
    /**
     * 开单人姓名
     */
    private String createUserName;
    
    /**
     * 开单人姓名
     */
    private String createUserDeptName;
    
    /**
     * 是否集中接货
     */
    private String pickupCentralized;
    
    /**
     * 司机工号
     */
    private String driverCode;
    
    /**
     * 司机姓名
     */
    private String driverName;
    
    /**
     * 司机所在部门编号
     */
    private String driverOrgCode;
    
    /**
     * 司机所在部门名称
     */
    private String driverOrgName;
    
    /**
     * 补录时间
     */
    private String modifyTime;
    
    /**
     * 补录人工号
     */
    private String modifyUserCode;
    
    /**
     * 补录人姓名
     */
    private String modifyUserName;
    
    
    /**
     * 补录部门编码
     */
    private String modifyOrgCode;
    
    
    /**
     * 补录部门名称
     */
    private String modifyOrgName;
    
    /**
     * 货物尺寸
     */
    private String goodsSize;
    
    /**
     * 是否快递
     */
    private String isExpress;
    
    /**
     * 车牌号
     */
    private String licensePlateNum;
    
    
    /**
     * 开单部门负责人工号
     */
    private String  createOrgPrincipalCode;
    
    /**
     * 开单部门负责人姓名
     */
    private String  createOrgPrincipalName;
    
    /**
     * 快递补录创建时间
     */
    private String  expCreateTime;
    
    /**
     * 快递修改时间
     */
    private String  expModifyTime;
    
    /**
     * 快递开单时间
     */
    private String  expBillTime;
    
    /**
     * 快递快递到达部门编号
     */
    private String  expArriveOrgCode;
    
    /**
     * 快递快递到达部门名称
     */
    private String  expArriveOrgName;
    
    /**
     * 预计到达时间
     */
  	private Date preArriveTime;
  	
  	/**
     * 补录类型
     */
  	private String pendingType;
 	/**
     * 补录类型
     */
  	private Date preCustomerPickupTime;
	/**
     * 返货单号
     */
  	private String returnWaybillNo;
  	  	
  	/**
     * 是否子母件
     */
  	private boolean isParentChildWaybill;
  	
  	/**
     * 母件单号
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
    
    //-----------------------OA差错细分及自动化接口新添的字段end
  	
  	//DN201512110012 QMS差错上报新增字段[开单快递员],[是否快递集中补录]
  	/**
     * 是否快递集中补录
     */
  	private String isExpressFocus;
  	
	/**
     * 开单快递员
     */
  	private String billingCourier;
  	
  	/**
  	 * 添加运单状态字段
  	 * 
  	 */
  	private String waybillStatus;
  	
  	
    
	public String getWaybillStatus() {
		return waybillStatus;
	}

	public void setWaybillStatus(String waybillStatus) {
		this.waybillStatus = waybillStatus;
	}
    
	public String getExpCreateTime() {
		return expCreateTime;
	}

	public void setExpCreateTime(String expCreateTime) {
		this.expCreateTime = expCreateTime;
	}

	public String getExpModifyTime() {
		return expModifyTime;
	}

	public void setExpModifyTime(String expModifyTime) {
		this.expModifyTime = expModifyTime;
	}

	public String getExpBillTime() {
		return expBillTime;
	}

	public void setExpBillTime(String expBillTime) {
		this.expBillTime = expBillTime;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public String getPrincipalOrgCode() {
		return principalOrgCode;
	}

	public void setPrincipalOrgCode(String principalOrgCode) {
		this.principalOrgCode = principalOrgCode;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictUnifiedCode() {
		return districtUnifiedCode;
	}

	public void setDistrictUnifiedCode(String districtUnifiedCode) {
		this.districtUnifiedCode = districtUnifiedCode;
	}

	public String getStartExpressEmpCode() {
		return startExpressEmpCode;
	}

	public void setStartExpressEmpCode(String startExpressEmpCode) {
		this.startExpressEmpCode = startExpressEmpCode;
	}

	public String getStartExpressEmpName() {
		return startExpressEmpName;
	}

	public void setStartExpressEmpName(String startExpressEmpName) {
		this.startExpressEmpName = startExpressEmpName;
	}

	public String getStartExpressOrgCode() {
		return startExpressOrgCode;
	}

	public void setStartExpressOrgCode(String startExpressOrgCode) {
		this.startExpressOrgCode = startExpressOrgCode;
	}

	public String getStartExpressUnfiedCode() {
		return startExpressUnfiedCode;
	}

	public void setStartExpressUnfiedCode(String startExpressUnfiedCode) {
		this.startExpressUnfiedCode = startExpressUnfiedCode;
	}

	public String getStartExpressOrgName() {
		return startExpressOrgName;
	}

	public void setStartExpressOrgName(String startExpressOrgName) {
		this.startExpressOrgName = startExpressOrgName;
	}

	public String getEndExpressEmpCode() {
		return endExpressEmpCode;
	}

	public void setEndExpressEmpCode(String endExpressEmpCode) {
		this.endExpressEmpCode = endExpressEmpCode;
	}

	public String getEndExpressEmpName() {
		return endExpressEmpName;
	}

	public void setEndExpressEmpName(String endExpressEmpName) {
		this.endExpressEmpName = endExpressEmpName;
	}

	public String getEndExpressOrgCode() {
		return endExpressOrgCode;
	}

	public void setEndExpressOrgCode(String endExpressOrgCode) {
		this.endExpressOrgCode = endExpressOrgCode;
	}

	public String getEndExpressUnfiedCode() {
		return endExpressUnfiedCode;
	}

	public void setEndExpressUnfiedCode(String endExpressUnfiedCode) {
		this.endExpressUnfiedCode = endExpressUnfiedCode;
	}

	public String getEndExpressOrgName() {
		return endExpressOrgName;
	}

	public void setEndExpressOrgName(String endExpressOrgName) {
		this.endExpressOrgName = endExpressOrgName;
	}

	/**
	 * Gets the waybill no.
	 *
	 * @return the waybill no
	 */
	public String getWaybillNo()
	{
		return waybillNo;
	}

	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the new waybill no
	 */
	public void setWaybillNo(String waybillNo)
	{
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the transport type.
	 *
	 * @return the transport type
	 */
	public String getTransportType()
	{
		return transportType;
	}

	/**
	 * Sets the transport type.
	 *
	 * @param transportType the new transport type
	 */
	public void setTransportType(String transportType)
	{
		this.transportType = transportType;
	}

	/**
	 * Gets the product code.
	 *
	 * @return the product code
	 */
	public String getProductCode()
	{
		return productCode;
	}

	/**
	 * Sets the product code.
	 *
	 * @param productCode the new product code
	 */
	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	/**
	 * Gets the delivery customer name.
	 *
	 * @return the delivery customer name
	 */
	public String getDeliveryCustomerName()
	{
		return deliveryCustomerName;
	}

	/**
	 * Sets the delivery customer name.
	 *
	 * @param deliveryCustomerName the new delivery customer name
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName)
	{
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * Gets the delivery customer phone.
	 *
	 * @return the delivery customer phone
	 */
	public String getDeliveryCustomerPhone()
	{
		return deliveryCustomerPhone;
	}

	/**
	 * Sets the delivery customer phone.
	 *
	 * @param deliveryCustomerPhone the new delivery customer phone
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone)
	{
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	/**
	 * Gets the delivery customer mobilephone.
	 *
	 * @return the delivery customer mobilephone
	 */
	public String getDeliveryCustomerMobilephone()
	{
		return deliveryCustomerMobilephone;
	}

	/**
	 * Sets the delivery customer mobilephone.
	 *
	 * @param deliveryCustomerMobilephone the new delivery customer mobilephone
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone)
	{
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * Gets the delivery customer city code.
	 *
	 * @return the delivery customer city code
	 */
	public String getDeliveryCustomerCityCode()
	{
		return deliveryCustomerCityCode;
	}

	/**
	 * Sets the delivery customer city code.
	 *
	 * @param deliveryCustomerCityCode the new delivery customer city code
	 */
	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode)
	{
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}

	
	/**
	 * Gets the delivery customer city name.
	 *
	 * @return the delivery customer city name
	 */
	public String getDeliveryCustomerCityName()
	{
		return deliveryCustomerCityName;
	}

	/**
	 * Sets the delivery customer city name.
	 *
	 * @param deliveryCustomerCityName the new delivery customer city name
	 */
	public void setDeliveryCustomerCityName(String deliveryCustomerCityName)
	{
		this.deliveryCustomerCityName = deliveryCustomerCityName;
	}

	/**
	 * Gets the delivery customer address.
	 *
	 * @return the delivery customer address
	 */
	public String getDeliveryCustomerAddress()
	{
		return deliveryCustomerAddress;
	}

	/**
	 * Sets the delivery customer address.
	 *
	 * @param deliveryCustomerAddress the new delivery customer address
	 */
	public void setDeliveryCustomerAddress(String deliveryCustomerAddress)
	{
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}

	/**
	 * Gets the receive customer name.
	 *
	 * @return the receive customer name
	 */
	public String getReceiveCustomerName()
	{
		return receiveCustomerName;
	}

	/**
	 * Sets the receive customer name.
	 *
	 * @param receiveCustomerName the new receive customer name
	 */
	public void setReceiveCustomerName(String receiveCustomerName)
	{
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * Gets the receive customer phone.
	 *
	 * @return the receive customer phone
	 */
	public String getReceiveCustomerPhone()
	{
		return receiveCustomerPhone;
	}

	/**
	 * Sets the receive customer phone.
	 *
	 * @param receiveCustomerPhone the new receive customer phone
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone)
	{
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * Gets the receive customer mobilephone.
	 *
	 * @return the receive customer mobilephone
	 */
	public String getReceiveCustomerMobilephone()
	{
		return receiveCustomerMobilephone;
	}

	/**
	 * Sets the receive customer mobilephone.
	 *
	 * @param receiveCustomerMobilephone the new receive customer mobilephone
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone)
	{
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * Gets the target org code.
	 *
	 * @return the target org code
	 */
	public String getTargetOrgCode()
	{
		return targetOrgCode;
	}

	/**
	 * Sets the target org code.
	 *
	 * @param targetOrgCode the new target org code
	 */
	public void setTargetOrgCode(String targetOrgCode)
	{
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * Gets the target org name.
	 *
	 * @return the target org name
	 */
	public String getTargetOrgName()
	{
		return targetOrgName;
	}

	/**
	 * Sets the target org name.
	 *
	 * @param targetOrgName the new target org name
	 */
	public void setTargetOrgName(String targetOrgName)
	{
		this.targetOrgName = targetOrgName;
	}

	/**
	 * Gets the receive customer address.
	 *
	 * @return the receive customer address
	 */
	public String getReceiveCustomerAddress()
	{
		return receiveCustomerAddress;
	}

	/**
	 * Sets the receive customer address.
	 *
	 * @param receiveCustomerAddress the new receive customer address
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress)
	{
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * Gets the goods name.
	 *
	 * @return the goods name
	 */
	public String getGoodsName()
	{
		return goodsName;
	}

	/**
	 * Sets the goods name.
	 *
	 * @param goodsName the new goods name
	 */
	public void setGoodsName(String goodsName)
	{
		this.goodsName = goodsName;
	}

	/**
	 * Gets the goods qty total.
	 *
	 * @return the goods qty total
	 */
	public Integer getGoodsQtyTotal()
	{
		return goodsQtyTotal;
	}

	/**
	 * Sets the goods qty total.
	 *
	 * @param goodsQtyTotal the new goods qty total
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal)
	{
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * Gets the goods weight total.
	 *
	 * @return the goods weight total
	 */
	public BigDecimal getGoodsWeightTotal()
	{
		return goodsWeightTotal;
	}

	/**
	 * Sets the goods weight total.
	 *
	 * @param goodsWeightTotal the new goods weight total
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal)
	{
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * Gets the goods volume total.
	 *
	 * @return the goods volume total
	 */
	public BigDecimal getGoodsVolumeTotal()
	{
		return goodsVolumeTotal;
	}

	/**
	 * Sets the goods volume total.
	 *
	 * @param goodsVolumeTotal the new goods volume total
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal)
	{
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * Gets the total fee.
	 *
	 * @return the total fee
	 */
	public BigDecimal getTotalFee()
	{
		return totalFee;
	}

	/**
	 * Sets the total fee.
	 *
	 * @param totalFee the new total fee
	 */
	public void setTotalFee(BigDecimal totalFee)
	{
		this.totalFee = totalFee;
	}

	/**
	 * Gets the paid method.
	 *
	 * @return the paid method
	 */
	public String getPaidMethod()
	{
		return paidMethod;
	}

	/**
	 * Sets the paid method.
	 *
	 * @param paidMethod the new paid method
	 */
	public void setPaidMethod(String paidMethod)
	{
		this.paidMethod = paidMethod;
	}

	/**
	 * Gets the pre pay amount.
	 *
	 * @return the pre pay amount
	 */
	public BigDecimal getPrePayAmount()
	{
		return prePayAmount;
	}

	/**
	 * Sets the pre pay amount.
	 *
	 * @param prePayAmount the new pre pay amount
	 */
	public void setPrePayAmount(BigDecimal prePayAmount)
	{
		this.prePayAmount = prePayAmount;
	}

	/**
	 * Gets the to pay amount.
	 *
	 * @return the to pay amount
	 */
	public BigDecimal getToPayAmount()
	{
		return toPayAmount;
	}

	/**
	 * Sets the to pay amount.
	 *
	 * @param toPayAmount the new to pay amount
	 */
	public void setToPayAmount(BigDecimal toPayAmount)
	{
		this.toPayAmount = toPayAmount;
	}

	/**
	 * Gets the refund type.
	 *
	 * @return the refund type
	 */
	public String getRefundType()
	{
		return refundType;
	}

	/**
	 * Sets the refund type.
	 *
	 * @param refundType the new refund type
	 */
	public void setRefundType(String refundType)
	{
		this.refundType = refundType;
	}

	/**
	 * Gets the cod amount.
	 *
	 * @return the cod amount
	 */
	public BigDecimal getCodAmount()
	{
		return codAmount;
	}

	/**
	 * Sets the cod amount.
	 *
	 * @param codAmount the new cod amount
	 */
	public void setCodAmount(BigDecimal codAmount)
	{
		this.codAmount = codAmount;
	}

	/**
	 * Gets the cod fee.
	 *
	 * @return the cod fee
	 */
	public BigDecimal getCodFee()
	{
		return codFee;
	}

	/**
	 * Sets the cod fee.
	 *
	 * @param codFee the new cod fee
	 */
	public void setCodFee(BigDecimal codFee)
	{
		this.codFee = codFee;
	}

	/**
	 * Gets the receive method.
	 *
	 * @return the receive method
	 */
	public String getReceiveMethod()
	{
		return receiveMethod;
	}

	/**
	 * Sets the receive method.
	 *
	 * @param receiveMethod the new receive method
	 */
	public void setReceiveMethod(String receiveMethod)
	{
		this.receiveMethod = receiveMethod;
	}

	/**
	 * Gets the pickup fee.
	 *
	 * @return the pickup fee
	 */
	public BigDecimal getPickupFee()
	{
		return pickupFee;
	}

	/**
	 * Sets the pickup fee.
	 *
	 * @param pickupFee the new pickup fee
	 */
	public void setPickupFee(BigDecimal pickupFee)
	{
		this.pickupFee = pickupFee;
	}

	/**
	 * Gets the delivery goods fee.
	 *
	 * @return the delivery goods fee
	 */
	public BigDecimal getDeliveryGoodsFee()
	{
		return deliveryGoodsFee;
	}

	/**
	 * Sets the delivery goods fee.
	 *
	 * @param deliveryGoodsFee the new delivery goods fee
	 */
	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee)
	{
		this.deliveryGoodsFee = deliveryGoodsFee;
	}

	/**
	 * Gets the package fee.
	 *
	 * @return the package fee
	 */
	public BigDecimal getPackageFee()
	{
		return packageFee;
	}

	/**
	 * Sets the package fee.
	 *
	 * @param packageFee the new package fee
	 */
	public void setPackageFee(BigDecimal packageFee)
	{
		this.packageFee = packageFee;
	}

	/**
	 * Gets the service fee.
	 *
	 * @return the service fee
	 */
	public BigDecimal getServiceFee()
	{
		return serviceFee;
	}

	/**
	 * Sets the service fee.
	 *
	 * @param serviceFee the new service fee
	 */
	public void setServiceFee(BigDecimal serviceFee)
	{
		this.serviceFee = serviceFee;
	}

	/**
	 * Gets the transport fee.
	 *
	 * @return the transport fee
	 */
	public BigDecimal getTransportFee()
	{
		return transportFee;
	}

	/**
	 * Sets the transport fee.
	 *
	 * @param transportFee the new transport fee
	 */
	public void setTransportFee(BigDecimal transportFee)
	{
		this.transportFee = transportFee;
	}

	/**
	 * Gets the receive org code.
	 *
	 * @return the receive org code
	 */
	public String getReceiveOrgCode()
	{
		return receiveOrgCode;
	}

	/**
	 * Sets the receive org code.
	 *
	 * @param receiveOrgCode the new receive org code
	 */
	public void setReceiveOrgCode(String receiveOrgCode)
	{
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * Gets the customer pickup org code.
	 *
	 * @return the customer pickup org code
	 */
	public String getCustomerPickupOrgCode()
	{
		return customerPickupOrgCode;
	}

	/**
	 * Sets the customer pickup org code.
	 *
	 * @param customerPickupOrgCode the new customer pickup org code
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode)
	{
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	 * Gets the sign situation.
	 *
	 * @return the sign situation
	 */
	public String getSignSituation()
	{
		return signSituation;
	}

	/**
	 * Sets the sign situation.
	 *
	 * @param signSituation the new sign situation
	 */
	public void setSignSituation(String signSituation)
	{
		this.signSituation = signSituation;
	}

	/**
	 * Gets the deliveryman name.
	 *
	 * @return the deliveryman name
	 */
	public String getDeliverymanName()
	{
		return deliverymanName;
	}

	/**
	 * Sets the deliveryman name.
	 *
	 * @param deliverymanName the new deliveryman name
	 */
	public void setDeliverymanName(String deliverymanName)
	{
		this.deliverymanName = deliverymanName;
	}

	/**
	 * Gets the sign time.
	 *
	 * @return the sign time
	 */
	public Date getSignTime()
	{
		return signTime;
	}

	/**
	 * Sets the sign time.
	 *
	 * @param signTime the new sign time
	 */
	public void setSignTime(Date signTime)
	{
		this.signTime = signTime;
	}

	/**
	 * Gets the sign note.
	 *
	 * @return the sign note
	 */
	public String getSignNote()
	{
		return signNote;
	}

	/**
	 * Sets the sign note.
	 *
	 * @param signNote the new sign note
	 */
	public void setSignNote(String signNote)
	{
		this.signNote = signNote;
	}

	/**
	 * Gets the order no.
	 *
	 * @return the order no
	 */
	public String getOrderNo()
	{
		return orderNo;
	}

	/**
	 * Sets the order no.
	 *
	 * @param orderNo the new order no
	 */
	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	/**
	 * Gets the insurance amount.
	 *
	 * @return the insurance amount
	 */
	public BigDecimal getInsuranceAmount()
	{
		return insuranceAmount;
	}

	/**
	 * Sets the insurance amount.
	 *
	 * @param insuranceAmount the new insurance amount
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount)
	{
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * Gets the insurance fee.
	 *
	 * @return the insurance fee
	 */
	public BigDecimal getInsuranceFee()
	{
		return insuranceFee;
	}

	/**
	 * Sets the insurance fee.
	 *
	 * @param insuranceFee the new insurance fee
	 */
	public void setInsuranceFee(BigDecimal insuranceFee)
	{
		this.insuranceFee = insuranceFee;
	}

	/**
	 * Gets the goods package.
	 *
	 * @return the goods package
	 */
	public String getGoodsPackage()
	{
		return goodsPackage;
	}

	/**
	 * Sets the goods package.
	 *
	 * @param goodsPackage the new goods package
	 */
	public void setGoodsPackage(String goodsPackage)
	{
		this.goodsPackage = goodsPackage;
	}

	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getActive()
	{
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(String active)
	{
		this.active = active;
	}

	/**
	 * Gets the other fee.
	 *
	 * @return the other fee
	 */
	public BigDecimal getOtherFee()
	{
		return otherFee;
	}

	/**
	 * Sets the other fee.
	 *
	 * @param otherFee the new other fee
	 */
	public void setOtherFee(BigDecimal otherFee)
	{
		this.otherFee = otherFee;
	}

	/**
	 * Gets the outer notes.
	 *
	 * @return the outer notes
	 */
	public String getOuterNotes()
	{
		return outerNotes;
	}

	/**
	 * Sets the outer notes.
	 *
	 * @param outerNotes the new outer notes
	 */
	public void setOuterNotes(String outerNotes)
	{
		this.outerNotes = outerNotes;
	}

	/**
	 * Gets the delivery customer code.
	 *
	 * @return the delivery customer code
	 */
	public String getDeliveryCustomerCode()
	{
		return deliveryCustomerCode;
	}

	/**
	 * Sets the delivery customer code.
	 *
	 * @param deliveryCustomerCode the new delivery customer code
	 */
	public void setDeliveryCustomerCode(String deliveryCustomerCode)
	{
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	/**
	 * Gets the receive customer code.
	 *
	 * @return the receive customer code
	 */
	public String getReceiveCustomerCode()
	{
		return receiveCustomerCode;
	}

	/**
	 * Sets the receive customer code.
	 *
	 * @param receiveCustomerCode the new receive customer code
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode)
	{
		this.receiveCustomerCode = receiveCustomerCode;
	}

	/**
	 * Gets the settle status.
	 *
	 * @return the settle status
	 */
	public String getSettleStatus()
	{
		return settleStatus;
	}

	/**
	 * Sets the settle status.
	 *
	 * @param settleStatus the new settle status
	 */
	public void setSettleStatus(String settleStatus)
	{
		this.settleStatus = settleStatus;
	}

	/**
	 * Gets the return bill type.
	 *
	 * @return the return bill type
	 */
	public String getReturnBillType()
	{
		return returnBillType;
	}

	/**
	 * Sets the return bill type.
	 *
	 * @param returnBillType the new return bill type
	 */
	public void setReturnBillType(String returnBillType)
	{
		this.returnBillType = returnBillType;
	}


	/**
	 * Gets the transportation remark.
	 *
	 * @return the transportation remark
	 */
	public String getTransportationRemark()
	{
		return transportationRemark;
	}

	/**
	 * Sets the transportation remark.
	 *
	 * @param transportationRemark the new transportation remark
	 */
	public void setTransportationRemark(String transportationRemark)
	{
		this.transportationRemark = transportationRemark;
	}

	/**
	 * Gets the load org code.
	 *
	 * @return the load org code
	 */
	public String getLoadOrgCode()
	{
		return loadOrgCode;
	}

	/**
	 * Sets the load org code.
	 *
	 * @param loadOrgCode the new load org code
	 */
	public void setLoadOrgCode(String loadOrgCode)
	{
		this.loadOrgCode = loadOrgCode;
	}

	/**
	 * Gets the delivery customer prov code.
	 *
	 * @return the delivery customer prov code
	 */
	public String getDeliveryCustomerProvCode()
	{
		return deliveryCustomerProvCode;
	}

	/**
	 * Sets the delivery customer prov code.
	 *
	 * @param deliveryCustomerProvCode the new delivery customer prov code
	 */
	public void setDeliveryCustomerProvCode(String deliveryCustomerProvCode)
	{
		this.deliveryCustomerProvCode = deliveryCustomerProvCode;
	}

	/**
	 * Gets the delivery customer city code1.
	 *
	 * @return the delivery customer city code1
	 */
	public String getDeliveryCustomerCityCode1()
	{
		return deliveryCustomerCityCode1;
	}

	/**
	 * Sets the delivery customer city code1.
	 *
	 * @param deliveryCustomerCityCode1 the new delivery customer city code1
	 */
	public void setDeliveryCustomerCityCode1(String deliveryCustomerCityCode1)
	{
		this.deliveryCustomerCityCode1 = deliveryCustomerCityCode1;
	}

	/**
	 * Gets the receive customer prov code.
	 *
	 * @return the receive customer prov code
	 */
	public String getReceiveCustomerProvCode()
	{
		return receiveCustomerProvCode;
	}

	/**
	 * Sets the receive customer prov code.
	 *
	 * @param receiveCustomerProvCode the new receive customer prov code
	 */
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode)
	{
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	/**
	 * Gets the receive customer city code.
	 *
	 * @return the receive customer city code
	 */
	public String getReceiveCustomerCityCode()
	{
		return receiveCustomerCityCode;
	}

	/**
	 * Sets the receive customer city code.
	 *
	 * @param receiveCustomerCityCode the new receive customer city code
	 */
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode)
	{
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	/**
	 * Gets the pickup to door.
	 *
	 * @return the pickup to door
	 */
	public String getPickupToDoor()
	{
		return pickupToDoor;
	}

	/**
	 * Sets the pickup to door.
	 *
	 * @param pickupToDoor the new pickup to door
	 */
	public void setPickupToDoor(String pickupToDoor)
	{
		this.pickupToDoor = pickupToDoor;
	}

	/**
	 * Gets the notification result.
	 *
	 * @return the notification result
	 */
	public String getNotificationResult()
	{
		return notificationResult;
	}

	/**
	 * Sets the notification result.
	 *
	 * @param notificationResult the new notification result
	 */
	public void setNotificationResult(String notificationResult)
	{
		this.notificationResult = notificationResult;
	}
	
	/**
	 * Gets the departure dept name.
	 *
	 * @return the departure dept name
	 */
	public String getDepartureDeptName()
	{
		return departureDeptName;
	}

	/**
	 * Sets the departure dept name.
	 *
	 * @param departureDeptName the new departure dept name
	 */
	public void setDepartureDeptName(String departureDeptName)
	{
		this.departureDeptName = departureDeptName;
	}

	/**
	 * Gets the departure dept number.
	 *
	 * @return the departure dept number
	 */
	public String getDepartureDeptNumber()
	{
		return departureDeptNumber;
	}

	/**
	 * Sets the departure dept number.
	 *
	 * @param departureDeptNumber the new departure dept number
	 */
	public void setDepartureDeptNumber(String departureDeptNumber)
	{
		this.departureDeptNumber = departureDeptNumber;
	}

	/**
	 * Gets the departure dept addr.
	 *
	 * @return the departure dept addr
	 */
	public String getDepartureDeptAddr()
	{
		return departureDeptAddr;
	}

	/**
	 * Sets the departure dept addr.
	 *
	 * @param departureDeptAddr the new departure dept addr
	 */
	public void setDepartureDeptAddr(String departureDeptAddr)
	{
		this.departureDeptAddr = departureDeptAddr;
	}

	/**
	 * Gets the departure dept phone.
	 *
	 * @return the departure dept phone
	 */
	public String getDepartureDeptPhone()
	{
		return departureDeptPhone;
	}

	/**
	 * Sets the departure dept phone.
	 *
	 * @param departureDeptPhone the new departure dept phone
	 */
	public void setDepartureDeptPhone(String departureDeptPhone)
	{
		this.departureDeptPhone = departureDeptPhone;
	}

	/**
	 * Gets the departure dept fax.
	 *
	 * @return the departure dept fax
	 */
	public String getDepartureDeptFax()
	{
		return departureDeptFax;
	}

	/**
	 * Sets the departure dept fax.
	 *
	 * @param departureDeptFax the new departure dept fax
	 */
	public void setDepartureDeptFax(String departureDeptFax)
	{
		this.departureDeptFax = departureDeptFax;
	}

	/**
	 * Gets the lading station name.
	 *
	 * @return the lading station name
	 */
	public String getLadingStationName()
	{
		return ladingStationName;
	}

	/**
	 * Sets the lading station name.
	 *
	 * @param ladingStationName the new lading station name
	 */
	public void setLadingStationName(String ladingStationName)
	{
		this.ladingStationName = ladingStationName;
	}

	/**
	 * Gets the lading station number.
	 *
	 * @return the lading station number
	 */
	public String getLadingStationNumber()
	{
		return ladingStationNumber;
	}

	/**
	 * Sets the lading station number.
	 *
	 * @param ladingStationNumber the new lading station number
	 */
	public void setLadingStationNumber(String ladingStationNumber)
	{
		this.ladingStationNumber = ladingStationNumber;
	}

	/**
	 * Gets the lading station addr.
	 *
	 * @return the lading station addr
	 */
	public String getLadingStationAddr()
	{
		return ladingStationAddr;
	}

	/**
	 * Sets the lading station addr.
	 *
	 * @param ladingStationAddr the new lading station addr
	 */
	public void setLadingStationAddr(String ladingStationAddr)
	{
		this.ladingStationAddr = ladingStationAddr;
	}

	/**
	 * Gets the lading station phone.
	 *
	 * @return the lading station phone
	 */
	public String getLadingStationPhone()
	{
		return ladingStationPhone;
	}

	/**
	 * Sets the lading station phone.
	 *
	 * @param ladingStationPhone the new lading station phone
	 */
	public void setLadingStationPhone(String ladingStationPhone)
	{
		this.ladingStationPhone = ladingStationPhone;
	}

	/**
	 * Gets the lading station fax.
	 *
	 * @return the lading station fax
	 */
	public String getLadingStationFax()
	{
		return ladingStationFax;
	}

	/**
	 * Sets the lading station fax.
	 *
	 * @param ladingStationFax the new lading station fax
	 */
	public void setLadingStationFax(String ladingStationFax)
	{
		this.ladingStationFax = ladingStationFax;
	}

	/**
	 * Checks if is signed.
	 *
	 * @return true, if is signed
	 */
	public boolean isSigned()
	{
		return isSigned;
	}

	/**
	 * Sets the signed.
	 *
	 * @param isSigned the new signed
	 */
	public void setSigned(boolean isSigned)
	{
		this.isSigned = isSigned;
	}

	/**
	 * Checks if is normal signed.
	 *
	 * @return true, if is normal signed
	 */
	public boolean isNormalSigned()
	{
		return isNormalSigned;
	}

	/**
	 * Sets the normal signed.
	 *
	 * @param isNormalSigned the new normal signed
	 */
	public void setNormalSigned(boolean isNormalSigned)
	{
		this.isNormalSigned = isNormalSigned;
	}

	/**
	 * Gets the bill time.
	 *
	 * @return the bill time
	 */
	public Date getBillTime()
	{
		return billTime;
	}

	/**
	 * Sets the bill time.
	 *
	 * @param billTime the new bill time
	 */
	public void setBillTime(Date billTime)
	{
		this.billTime = billTime;
	}

	/**
	 * Gets the delivery customer prov name.
	 *
	 * @return the delivery customer prov name
	 */
	public String getDeliveryCustomerProvName()
	{
		return deliveryCustomerProvName;
	}

	/**
	 * Sets the delivery customer prov name.
	 *
	 * @param deliveryCustomerProvName the new delivery customer prov name
	 */
	public void setDeliveryCustomerProvName(String deliveryCustomerProvName)
	{
		this.deliveryCustomerProvName = deliveryCustomerProvName;
	}

	/**
	 * Gets the delivery customer city name1.
	 *
	 * @return the delivery customer city name1
	 */
	public String getDeliveryCustomerCityName1()
	{
		return deliveryCustomerCityName1;
	}

	/**
	 * Sets the delivery customer city name1.
	 *
	 * @param deliveryCustomerCityName1 the new delivery customer city name1
	 */
	public void setDeliveryCustomerCityName1(String deliveryCustomerCityName1)
	{
		this.deliveryCustomerCityName1 = deliveryCustomerCityName1;
	}

	/**
	 * Gets the receive customer prov name.
	 *
	 * @return the receive customer prov name
	 */
	public String getReceiveCustomerProvName()
	{
		return receiveCustomerProvName;
	}

	/**
	 * Sets the receive customer prov name.
	 *
	 * @param receiveCustomerProvName the new receive customer prov name
	 */
	public void setReceiveCustomerProvName(String receiveCustomerProvName)
	{
		this.receiveCustomerProvName = receiveCustomerProvName;
	}

	/**
	 * Gets the receive customer city name.
	 *
	 * @return the receive customer city name
	 */
	public String getReceiveCustomerCityName()
	{
		return receiveCustomerCityName;
	}

	/**
	 * Sets the receive customer city name.
	 *
	 * @param receiveCustomerCityName the new receive customer city name
	 */
	public void setReceiveCustomerCityName(String receiveCustomerCityName)
	{
		this.receiveCustomerCityName = receiveCustomerCityName;
	}

	/**
	 * Checks if is pickup.
	 *
	 * @return true, if is pickup
	 */
	public boolean isPickup()
	{
		return pickup;
	}

	/**
	 * Sets the pickup.
	 *
	 * @param pickup the new pickup
	 */
	public void setPickup(boolean pickup)
	{
		this.pickup = pickup;
	}

	/**
	 * Checks if is notification.
	 *
	 * @return true, if is notification
	 */
	public boolean isNotification()
	{
		return notification;
	}

	/**
	 * Sets the notification.
	 *
	 * @param notification the new notification
	 */
	public void setNotification(boolean notification)
	{
		this.notification = notification;
	}

	/**
	 * Gets the load org name.
	 *
	 * @return the load org name
	 */
	public String getLoadOrgName()
	{
		return loadOrgName;
	}

	/**
	 * Sets the load org name.
	 *
	 * @param loadOrgName the new load org name
	 */
	public void setLoadOrgName(String loadOrgName)
	{
		this.loadOrgName = loadOrgName;
	}

	/**
	 * Gets the waybill charge cost dto.
	 *
	 * @return the waybill charge cost dto
	 */
	public List<WaybillChargeCostDto> getWaybillChargeCostDto()
	{
		return waybillChargeCostDto;
	}

	/**
	 * Sets the waybill charge cost dto.
	 *
	 * @param waybillChargeCostDto the new waybill charge cost dto
	 */
	public void setWaybillChargeCostDto(List<WaybillChargeCostDto> waybillChargeCostDto)
	{
		this.waybillChargeCostDto = waybillChargeCostDto;
	}

	public String getLoadOrgNumber() {
		return loadOrgNumber;
	}

	public void setLoadOrgNumber(String loadOrgNumber) {
		this.loadOrgNumber = loadOrgNumber;
	}

	public String getDeliveryCustomerDistCode() {
		return deliveryCustomerDistCode;
	}

	public void setDeliveryCustomerDistCode(String deliveryCustomerDistCode) {
		this.deliveryCustomerDistCode = deliveryCustomerDistCode;
	}

	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	public Date getFirstSignTime() {
		return firstSignTime;
	}

	public void setFirstSignTime(Date firstSignTime) {
		this.firstSignTime = firstSignTime;
	}

	public String getEndDistrictCode() {
		return endDistrictCode;
	}

	public void setEndDistrictCode(String endDistrictCode) {
		this.endDistrictCode = endDistrictCode;
	}

	public String getEndDistrictName() {
		return endDistrictName;
	}

	public void setEndDistrictName(String endDistrictName) {
		this.endDistrictName = endDistrictName;
	}

	public String getEndDistrictUnifiedCode() {
		return endDistrictUnifiedCode;
	}

	public void setEndDistrictUnifiedCode(String endDistrictUnifiedCode) {
		this.endDistrictUnifiedCode = endDistrictUnifiedCode;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public String getReceiveOrgNumber() {
		return receiveOrgNumber;
	}

	public void setReceiveOrgNumber(String receiveOrgNumber) {
		this.receiveOrgNumber = receiveOrgNumber;
	}

	public String getIsBigDeliverCustomer() {
		return isBigDeliverCustomer;
	}

	public void setIsBigDeliverCustomer(String isBigDeliverCustomer) {
		this.isBigDeliverCustomer = isBigDeliverCustomer;
	}

	public String getDeliveryCustomerAddressNote() {
		return deliveryCustomerAddressNote;
	}

	public void setDeliveryCustomerAddressNote(String deliveryCustomerAddressNote) {
		this.deliveryCustomerAddressNote = deliveryCustomerAddressNote;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}
	public String getComplementOperationOrgCode() {
		return complementOperationOrgCode;
	}

	public void setComplementOperationOrgCode(String complementOperationOrgCode) {
		this.complementOperationOrgCode = complementOperationOrgCode;
	}

	public String getComplementOperationOrgName() {
		return complementOperationOrgName;
	}

	public void setComplementOperationOrgName(String complementOperationOrgName) {
		this.complementOperationOrgName = complementOperationOrgName;
	}

	public String getPrincipalNo() {
		return principalNo;
	}

	public void setPrincipalNo(String principalNo) {
		this.principalNo = principalNo;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getPrincipalOrgName() {
		return principalOrgName;
	}

	public void setPrincipalOrgName(String principalOrgName) {
		this.principalOrgName = principalOrgName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getOriginalWaybillNo() {
		return originalWaybillNo;
	}

	public void setOriginalWaybillNo(String originalWaybillNo) {
		this.originalWaybillNo = originalWaybillNo;
	}

	public String getPickupCentralized() {
		return pickupCentralized;
	}

	public void setPickupCentralized(String pickupCentralized) {
		this.pickupCentralized = pickupCentralized;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getCreateUserDeptName() {
		return createUserDeptName;
	}

	public void setCreateUserDeptName(String createUserDeptName) {
		this.createUserDeptName = createUserDeptName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public String getModifyOrgName() {
		return modifyOrgName;
	}

	public void setModifyOrgName(String modifyOrgName) {
		this.modifyOrgName = modifyOrgName;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getDriverOrgCode() {
		return driverOrgCode;
	}

	public void setDriverOrgCode(String driverOrgCode) {
		this.driverOrgCode = driverOrgCode;
	}

	public String getDriverOrgName() {
		return driverOrgName;
	}

	public void setDriverOrgName(String driverOrgName) {
		this.driverOrgName = driverOrgName;
	}

	public String getLicensePlateNum() {
		return licensePlateNum;
	}

	public void setLicensePlateNum(String licensePlateNum) {
		this.licensePlateNum = licensePlateNum;
	}

	public String getCreateOrgPrincipalCode() {
		return createOrgPrincipalCode;
	}

	public void setCreateOrgPrincipalCode(String createOrgPrincipalCode) {
		this.createOrgPrincipalCode = createOrgPrincipalCode;
	}

	public String getCreateOrgPrincipalName() {
		return createOrgPrincipalName;
	}

	public void setCreateOrgPrincipalName(String createOrgPrincipalName) {
		this.createOrgPrincipalName = createOrgPrincipalName;
	}

	public String getExpArriveOrgCode() {
		return expArriveOrgCode;
	}

	public void setExpArriveOrgCode(String expArriveOrgCode) {
		this.expArriveOrgCode = expArriveOrgCode;
	}

	public String getExpArriveOrgName() {
		return expArriveOrgName;
	}

	public void setExpArriveOrgName(String expArriveOrgName) {
		this.expArriveOrgName = expArriveOrgName;
	}

	public Date getPreArriveTime() {
		return preArriveTime;
	}

	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}

	public String getPendingType() {
		return pendingType;
	}

	public void setPendingType(String pendingType) {
		this.pendingType = pendingType;
	}
	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}

	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}
	/**
	 * @return the isAbnormalSigned
	 */
	public boolean isAbnormalSigned() {
		return isAbnormalSigned;
	}

	/**
	 * @param isAbnormalSigned the isAbnormalSigned to set
	 */
	public void setAbnormalSigned(boolean isAbnormalSigned) {
		this.isAbnormalSigned = isAbnormalSigned;
	}

	/**
	 * @return the isAbandonGoodsSigned
	 */
	public boolean isAbandonGoodsSigned() {
		return isAbandonGoodsSigned;
	}

	/**
	 * @param isAbandonGoodsSigned the isAbandonGoodsSigned to set
	 */
	public void setAbandonGoodsSigned(boolean isAbandonGoodsSigned) {
		this.isAbandonGoodsSigned = isAbandonGoodsSigned;
	}

	/**
	 * @return the returnWaybillNo
	 */
	public String getReturnWaybillNo() {
		return returnWaybillNo;
	}

	/**
	 * @param returnWaybillNo the returnWaybillNo to set
	 */
	public void setReturnWaybillNo(String returnWaybillNo) {
		this.returnWaybillNo = returnWaybillNo;
	}

	/**
	 * @return the isParentChildWaybill
	 */
	public boolean isParentChildWaybill() {
		return isParentChildWaybill;
	}

	/**
	 * @param isParentChildWaybill the isParentChildWaybill to set
	 */
	public void setParentChildWaybill(boolean isParentChildWaybill) {
		this.isParentChildWaybill = isParentChildWaybill;
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

	/**
	 * @return the childWaybillNoList
	 */
	public List<String> getChildWaybillNoList() {
		return childWaybillNoList;
	}

	/**
	 * @param childWaybillNoList the childWaybillNoList to set
	 */
	public void setChildWaybillNoList(List<String> childWaybillNoList) {
		this.childWaybillNoList = childWaybillNoList;
	}

	public List<String> getReturnWaybillNoList() {
		return returnWaybillNoList;
	}

	public void setReturnWaybillNoList(List<String> returnWaybillNoList) {
		this.returnWaybillNoList = returnWaybillNoList;
	}
	
	public String getIsExpressFocus() {
		return isExpressFocus;
	}

	public void setIsExpressFocus(String isExpressFocus) {
		this.isExpressFocus = isExpressFocus;
	}

	public String getBillingCourier() {
		return billingCourier;
	}

	public void setBillingCourier(String billingCourier) {
		this.billingCourier = billingCourier;
	}

	
	
}