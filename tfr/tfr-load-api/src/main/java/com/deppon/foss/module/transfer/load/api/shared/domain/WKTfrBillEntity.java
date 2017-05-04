package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;


/**
* @description 这里用一句话描述这个类的作用
* @version 1.0
* @author 328864-foss-xieyang
* @update 2016年5月13日 上午11:32:53
*/
public class WKTfrBillEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 创建人工号
	 */
	private String createNo;
	/**
	 * 更新人工号
	 */
	private String updateNo;
	/**
	 * 创建人名称
	 */
	private String createName;
	/**
	 * 更新人名称
	 */
	private String updateName;
	/**
	 * 创建组织编号
	 */
	private String createOrgCode;
	/**
	 * 更新组织编号
	 */
	private String updateOrgCode;
	/**
	 * 创建组织名称
	 */
	private String createOrgName;
	/**
	 * 更新组织名称
	 */
	private String updateOrgName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 数据状态
	 */
	private String dataStatus;
	/**
	 * 操作设备
	 */
	private String operationDevice;
	/**
	 * 操作设备编码
	 */
	private String operationDeviceCode;

	/**
	 * 交接单号
	 */
	private String handoverBillNo;

	/**
	 * 装车任务号
	 */
	private String loadTaskNo;

	/**
	 * 交接类型 长途交接单：LONG_DISTANCE_HANDOVER 短途交接单：SHORT_DISTANCE_HANDOVER
	 * 航空交接单：AIR_TRANS_HANDOVER
	 */
	private String handoverType;

	/**
	 * 交接状态 已交接：HAVE_PLACED ，已出发：HAVE_DEPART，已到达：HAVE_ARRIVE，已入库：HAVE_STOCK
	 */
	private String handoverState;

	/**
	 * 运输类型 汽运：TRUCK 空运：AIR
	 */
	private String transportType;

	/**
	 * 交接时间
	 */
	private Date handoverTime;

	/**
	 * 出发部门编码
	 */
	private String departOrgCode;

	/**
	 * 出发部门名称
	 */
	private String departOrgName;

	/**
	 * 到达部门编码
	 */
	private String arriveOrgCode;

	/**
	 * 到达部门名称
	 */
	private String arriveOrgName;

	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 车辆所有权
	 */
	private String carOwnershipType;

	/**
	 * 司机编码
	 */
	private String driverCode;

	/**
	 * 司机名称
	 */
	private String driverName;

	/**
	 * 司机电弧
	 */
	private String driverPhone;

	/**
	 * 车次
	 */
	private String shift;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 获取货物总票数(总件数)
	 */
	private BigDecimal totalQty;

	/**
	 * 总体积
	 */
	private BigDecimal totalVolumn;

	/**
	 * 总重量
	 */
	private BigDecimal totalWeight;

	/**
	 * 空运总票数
	 */
	private BigDecimal airliftTotalQty;

	/**
	 * 空运总体积
	 */
	private BigDecimal airliftTotalVolumn;

	/**
	 * 空运总重量
	 */
	private BigDecimal airliftTotalWeight;

	/**
	 * 额定载重
	 */
	private BigDecimal ratedLoad;

	/**
	 * 额定净空
	 */
	private BigDecimal ratedClearance;

	/**
	 * 装载率
	 */
	private BigDecimal loadingRate;

	/**
	 * 货柜号
	 */
	private String containerNo;

	/**
	 * 货柜信息
	 */
	private String containerInfo;

	/**
	 * 车型
	 */
	private String vehicleModel;

	/**
	 * 是否与零担合车
	 */
	private String truckloadStatus;
	/**
	 * 总费用
	 */
	private BigDecimal totalFee;

	/**
	 * 预付费用
	 */
	private BigDecimal totalPrepaidFee;

	/**
	 * 到付费用
	 */
	private BigDecimal totalArriveFee;

	/**
	 * 付款方式 月结：CT 现金：CH 电汇：TT
	 */
	private String paymentType;

	/**
	 * 代收货款
	 */
	private BigDecimal codFee;

	/**
	 * 装车金额
	 */
	private BigDecimal loadFee;

	/**
	 * 是否时效条款
	 */
	private String limitationSignStatus;

	/**
	 * 修改总费用备注
	 */
	private String modifyTotalFeeRemark;

	/** 运行时数 */
	private BigDecimal runHours;

	/** 车辆类型 */
	private String vechicleType;

	/** 车型 */
	private String vechileModel;

	/** 约车编号 */
	private String aboutVehicleNo;

	/** 业务类型 */
	private String bizType;

	/** 暂存点车牌号 */
	private String terminalstationVehicleNo;
	
	/** 出发时间 **/
	private Date departTime;
	/** 到达时间 **/
	private Date arriveTime;
	/** 发车计划id*/
	private String truckPlanId;
	/** 单据类型 */
	private String billType;
	/** 租车编号  */
	private String   rentalNum;
	/** 租车用途 */
	private String  rentalUse;
	/** 租车标记时间 */
	private Date  createDate;
	/**租车标记部门 */
	private String markDeptName;
	/**标记部门编码 */
	private String  markDeptCode;
	/**租车标记操作人 */
	private String   markOperator; 
	/** 约车编号 */
	private String   inviteVehicleNo;
	/** 租车金额 */
	private BigDecimal  rentalAmount;
	/** 询价编号 */
	private String  consultPriceNo;
	/**是否上门接货*/
	private String isDoorDeliver; 
	/**发货地址*/
	private String sendAddress;
	/**提货方式名称*/
	private String pickUpWayName;
	/**
	 * 货物名称
	 * @return
	 */
	private String goodsName;
	
	/**
	 * 寄件联系人(发货客户名称)
	 * @return
	 */
	private String customerName;
	

    /**
     * 目的站
     * @return
     */
    private String destination;
    
    /**
     * 收货联系人
     * @return
     */
    private String receiptContacts;
    /**
     * 收货地址
     * @return
     */
    private String receiptAddress;
    /**
     *包装
     * @return
     */
    private String packing;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCreateNo() {
		return createNo;
	}

	public void setCreateNo(String createNo) {
		this.createNo = createNo;
	}

	public String getUpdateNo() {
		return updateNo;
	}

	public void setUpdateNo(String updateNo) {
		this.updateNo = updateNo;
	}

	public String getCreateName() {
		return createName;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getUpdateOrgCode() {
		return updateOrgCode;
	}

	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getUpdateOrgName() {
		return updateOrgName;
	}

	public void setUpdateOrgName(String updateOrgName) {
		this.updateOrgName = updateOrgName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getPickUpWayName() {
		return pickUpWayName;
	}

	public void setPickUpWayName(String pickUpWayName) {
		this.pickUpWayName = pickUpWayName;
	}

	public String getRentalNum() {
		return rentalNum;
	}

	public String getIsDoorDeliver() {
		return isDoorDeliver;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public void setIsDoorDeliver(String isDoorDeliver) {
		this.isDoorDeliver = isDoorDeliver;
	}

	public void setRentalNum(String rentalNum) {
		this.rentalNum = rentalNum;
	}

	public String getRentalUse() {
		return rentalUse;
	}

	public void setRentalUse(String rentalUse) {
		this.rentalUse = rentalUse;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMarkDeptName() {
		return markDeptName;
	}

	public void setMarkDeptName(String markDeptName) {
		this.markDeptName = markDeptName;
	}

	public String getMarkDeptCode() {
		return markDeptCode;
	}

	public void setMarkDeptCode(String markDeptCode) {
		this.markDeptCode = markDeptCode;
	}

	public String getMarkOperator() {
		return markOperator;
	}

	public void setMarkOperator(String markOperator) {
		this.markOperator = markOperator;
	}

	public String getInviteVehicleNo() {
		return inviteVehicleNo;
	}

	public void setInviteVehicleNo(String inviteVehicleNo) {
		this.inviteVehicleNo = inviteVehicleNo;
	}

	public BigDecimal getRentalAmount() {
		return rentalAmount;
	}

	public void setRentalAmount(BigDecimal rentalAmount) {
		this.rentalAmount = rentalAmount;
	}

	public String getConsultPriceNo() {
		return consultPriceNo;
	}

	public void setConsultPriceNo(String consultPriceNo) {
		this.consultPriceNo = consultPriceNo;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getOperationDevice() {
		return operationDevice;
	}

	public void setOperationDevice(String operationDevice) {
		this.operationDevice = operationDevice;
	}

	public String getOperationDeviceCode() {
		return operationDeviceCode;
	}

	public String getReceiptAddress() {
		return receiptAddress;
	}

	public void setReceiptAddress(String receiptAddress) {
		this.receiptAddress = receiptAddress;
	}

	public String getReceiptContacts() {
		return receiptContacts;
	}

	public void setReceiptContacts(String receiptContacts) {
		this.receiptContacts = receiptContacts;
	}

	public void setOperationDeviceCode(String operationDeviceCode) {
		this.operationDeviceCode = operationDeviceCode;
	}

	public String getHandoverBillNo() {
		return handoverBillNo;
	}

	public void setHandoverBillNo(String handoverBillNo) {
		this.handoverBillNo = handoverBillNo;
	}

	public String getLoadTaskNo() {
		return loadTaskNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public void setLoadTaskNo(String loadTaskNo) {
		this.loadTaskNo = loadTaskNo;
	}

	public String getHandoverType() {
		return handoverType;
	}

	public void setHandoverType(String handoverType) {
		this.handoverType = handoverType;
	}

	public String getHandoverState() {
		return handoverState;
	}

	public void setHandoverState(String handoverState) {
		this.handoverState = handoverState;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public Date getHandoverTime() {
		return handoverTime;
	}

	public void setHandoverTime(Date handoverTime) {
		this.handoverTime = handoverTime;
	}

	public String getDepartOrgCode() {
		return departOrgCode;
	}

	public void setDepartOrgCode(String departOrgCode) {
		this.departOrgCode = departOrgCode;
	}

	public String getDepartOrgName() {
		return departOrgName;
	}

	public void setDepartOrgName(String departOrgName) {
		this.departOrgName = departOrgName;
	}

	public String getArriveOrgCode() {
		return arriveOrgCode;
	}

	public void setArriveOrgCode(String arriveOrgCode) {
		this.arriveOrgCode = arriveOrgCode;
	}

	public String getArriveOrgName() {
		return arriveOrgName;
	}

	public void setArriveOrgName(String arriveOrgName) {
		this.arriveOrgName = arriveOrgName;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getCarOwnershipType() {
		return carOwnershipType;
	}

	public void setCarOwnershipType(String carOwnershipType) {
		this.carOwnershipType = carOwnershipType;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(BigDecimal totalQty) {
		this.totalQty = totalQty;
	}

	public BigDecimal getTotalVolumn() {
		return totalVolumn;
	}

	public void setTotalVolumn(BigDecimal totalVolumn) {
		this.totalVolumn = totalVolumn;
	}

	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	public BigDecimal getAirliftTotalQty() {
		return airliftTotalQty;
	}

	public void setAirliftTotalQty(BigDecimal airliftTotalQty) {
		this.airliftTotalQty = airliftTotalQty;
	}

	public BigDecimal getAirliftTotalVolumn() {
		return airliftTotalVolumn;
	}

	public void setAirliftTotalVolumn(BigDecimal airliftTotalVolumn) {
		this.airliftTotalVolumn = airliftTotalVolumn;
	}

	public BigDecimal getAirliftTotalWeight() {
		return airliftTotalWeight;
	}

	public void setAirliftTotalWeight(BigDecimal airliftTotalWeight) {
		this.airliftTotalWeight = airliftTotalWeight;
	}

	public BigDecimal getRatedLoad() {
		return ratedLoad;
	}

	public void setRatedLoad(BigDecimal ratedLoad) {
		this.ratedLoad = ratedLoad;
	}

	public BigDecimal getRatedClearance() {
		return ratedClearance;
	}

	public void setRatedClearance(BigDecimal ratedClearance) {
		this.ratedClearance = ratedClearance;
	}

	public BigDecimal getLoadingRate() {
		return loadingRate;
	}

	public void setLoadingRate(BigDecimal loadingRate) {
		this.loadingRate = loadingRate;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getContainerInfo() {
		return containerInfo;
	}

	public void setContainerInfo(String containerInfo) {
		this.containerInfo = containerInfo;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getTruckloadStatus() {
		return truckloadStatus;
	}

	public void setTruckloadStatus(String truckloadStatus) {
		this.truckloadStatus = truckloadStatus;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getTotalPrepaidFee() {
		return totalPrepaidFee;
	}

	public void setTotalPrepaidFee(BigDecimal totalPrepaidFee) {
		this.totalPrepaidFee = totalPrepaidFee;
	}

	
	/**
	* @description 到付费用
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:05:19
	*/
	public BigDecimal getTotalArriveFee() {
		return totalArriveFee;
	}

	
	/**
	* @description 到付费用
	* @param totalArriveFee
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:05:11
	*/
	public void setTotalArriveFee(BigDecimal totalArriveFee) {
		this.totalArriveFee = totalArriveFee;
	}

	
	/**
	* @description 付款方式 月结：CT 现金：CH 电汇：TT
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:04:57
	*/
	public String getPaymentType() {
		return paymentType;
	}

	
	/**
	* @description 付款方式 月结：CT 现金：CH 电汇：TT
	* @param paymentType
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:04:53
	*/
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	
	/**
	* @description 代收货款
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:04:40
	*/
	public BigDecimal getCodFee() {
		return codFee;
	}

	
	/**
	* @description 代收货款
	* @param codFee
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:04:31
	*/
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	
	/**
	* @description 装车金额
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:04:13
	*/
	public BigDecimal getLoadFee() {
		return loadFee;
	}

	
	/**
	* @description 装车金额
	* @param loadFee
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:04:10
	*/
	public void setLoadFee(BigDecimal loadFee) {
		this.loadFee = loadFee;
	}

	
	/**
	* @description 是否时效条款
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:03:53
	*/
	public String getLimitationSignStatus() {
		return limitationSignStatus;
	}

	
	/**
	* @description 是否时效条款
	* @param limitationSignStatus
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:03:51
	*/
	public void setLimitationSignStatus(String limitationSignStatus) {
		this.limitationSignStatus = limitationSignStatus;
	}

	
	/**
	* @description 修改总费用备注
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:03:29
	*/
	public String getModifyTotalFeeRemark() {
		return modifyTotalFeeRemark;
	}

	
	/**
	* @description 修改总费用备注
	* @param modifyTotalFeeRemark
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:03:26
	*/
	public void setModifyTotalFeeRemark(String modifyTotalFeeRemark) {
		this.modifyTotalFeeRemark = modifyTotalFeeRemark;
	}

	
	/**
	* @description 运行时数
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:02:51
	*/
	public BigDecimal getRunHours() {
		return runHours;
	}

	
	/**
	* @description 运行时数
	* @param runHours
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:02:53
	*/
	public void setRunHours(BigDecimal runHours) {
		this.runHours = runHours;
	}

	
	/**
	* @description 车辆类型
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:02:32
	*/
	public String getVechicleType() {
		return vechicleType;
	}

	
	/**
	* @description 车辆类型
	* @param vechicleType
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:02:30
	*/
	public void setVechicleType(String vechicleType) {
		this.vechicleType = vechicleType;
	}

	
	/**
	* @description 车型
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:02:13
	*/
	public String getVechileModel() {
		return vechileModel;
	}

	
	/**
	* @description 车型
	* @param vechileModel
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:02:05
	*/
	public void setVechileModel(String vechileModel) {
		this.vechileModel = vechileModel;
	}

	
	/**
	* @description 约车编号
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:01:47
	*/
	public String getAboutVehicleNo() {
		return aboutVehicleNo;
	}

	
	/**
	* @description 约车编号
	* @param aboutVehicleNo
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:01:45
	*/
	public void setAboutVehicleNo(String aboutVehicleNo) {
		this.aboutVehicleNo = aboutVehicleNo;
	}

	
	/**
	* @description 业务类型
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:01:30
	*/
	public String getBizType() {
		return bizType;
	}

	
	/**
	* @description 业务类型
	* @param bizType
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:01:11
	*/
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	
	/**
	* @description 暂存点车牌号
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:01:05
	*/
	public String getTerminalstationVehicleNo() {
		return terminalstationVehicleNo;
	}

	
	/**
	* @description 暂存点车牌号
	* @param terminalstationVehicleNo
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:00:59
	*/
	public void setTerminalstationVehicleNo(String terminalstationVehicleNo) {
		this.terminalstationVehicleNo = terminalstationVehicleNo;
	}

	
	/**
	* @description 发车计划id
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:00:44
	*/
	public String getTruckPlanId() {
		return truckPlanId;
	}

	
	/**
	* @description 发车计划id
	* @param truckPlanId
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午5:00:37
	*/
	public void setTruckPlanId(String truckPlanId) {
		this.truckPlanId = truckPlanId;
	}

	
	/**
	* @description 出发时间
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月13日 上午11:33:44
	*/
	public Date getDepartTime() {
		if(departTime == null) {
			return null;
		}
		return departTime;
	}

	
	/**
	* @description 出发时间
	* @param departTime
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月13日 上午11:33:47
	*/
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	
	/**
	* @description 到达时间
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月13日 上午11:33:50
	*/
	public Date getArriveTime() {
		return arriveTime;
	}

	
	/**
	* @description 到达时间
	* @param arriveTime
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月13日 上午11:33:53
	*/
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	@Override
	public String toString() {
		return "WKTfrBillEntity [createNo=" + createNo + ", updateNo="
				+ updateNo + ", createName=" + createName + ", updateName="
				+ updateName + ", createOrgCode=" + createOrgCode
				+ ", updateOrgCode=" + updateOrgCode + ", createOrgName="
				+ createOrgName + ", updateOrgName=" + updateOrgName
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", dataStatus=" + dataStatus + ", operationDevice="
				+ operationDevice + ", operationDeviceCode="
				+ operationDeviceCode + ", handoverBillNo=" + handoverBillNo
				+ ", loadTaskNo=" + loadTaskNo + ", handoverType="
				+ handoverType + ", handoverState=" + handoverState
				+ ", transportType=" + transportType + ", handoverTime="
				+ handoverTime + ", departOrgCode=" + departOrgCode
				+ ", departOrgName=" + departOrgName + ", arriveOrgCode="
				+ arriveOrgCode + ", arriveOrgName=" + arriveOrgName
				+ ", vehicleNo=" + vehicleNo + ", carOwnershipType="
				+ carOwnershipType + ", driverCode=" + driverCode
				+ ", driverName=" + driverName + ", driverPhone=" + driverPhone
				+ ", shift=" + shift + ", remark=" + remark + ", totalQty="
				+ totalQty + ", totalVolumn=" + totalVolumn + ", totalWeight="
				+ totalWeight + ", airliftTotalQty=" + airliftTotalQty
				+ ", airliftTotalVolumn=" + airliftTotalVolumn
				+ ", airliftTotalWeight=" + airliftTotalWeight + ", ratedLoad="
				+ ratedLoad + ", ratedClearance=" + ratedClearance
				+ ", loadingRate=" + loadingRate + ", containerNo="
				+ containerNo + ", containerInfo=" + containerInfo
				+ ", vehicleModel=" + vehicleModel + ", truckloadStatus="
				+ truckloadStatus + ", totalFee=" + totalFee
				+ ", totalPrepaidFee=" + totalPrepaidFee + ", totalArriveFee="
				+ totalArriveFee + ", paymentType=" + paymentType + ", codFee="
				+ codFee + ", loadFee=" + loadFee + ", limitationSignStatus="
				+ limitationSignStatus + ", modifyTotalFeeRemark="
				+ modifyTotalFeeRemark + ", runHours=" + runHours
				+ ", vechicleType=" + vechicleType + ", vechileModel="
				+ vechileModel + ", aboutVehicleNo=" + aboutVehicleNo
				+ ", bizType=" + bizType + ", terminalstationVehicleNo="
				+ terminalstationVehicleNo + ", departTime=" + departTime
				+ ", arriveTime=" + arriveTime + ", truckPlanId=" + truckPlanId
				+ ", billType=" + billType + ", rentalNum=" + rentalNum
				+ ", rentalUse=" + rentalUse + ", createDate=" + createDate
				+ ", markDeptName=" + markDeptName + ", markDeptCode="
				+ markDeptCode + ", markOperator=" + markOperator
				+ ", inviteVehicleNo=" + inviteVehicleNo + ", rentalAmount="
				+ rentalAmount + ", consultPriceNo=" + consultPriceNo
				+ ", isDoorDeliver=" + isDoorDeliver + ", sendAddress="
				+ sendAddress + ", pickUpWayName=" + pickUpWayName
				+ ", goodsName=" + goodsName + ", customerName=" + customerName
				+ ", destination=" + destination + ", receiptContacts="
				+ receiptContacts + ", receiptAddress=" + receiptAddress
				+ ", packing=" + packing + "]";
	}

}
