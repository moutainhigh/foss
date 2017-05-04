package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lizhonglin 2015-09-08
 * 德邦家装运单信息推送实体
 */
public class WaybillHomeImpEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6585773699100996375L;
	
	/**
	 * ID
	 */
	private String id;
	/**
	 * 运单号
	 */
	private String mailNo;
	/**
	 * 我司运单对应的订单号
	 */
	private String logisticID;
	/**
	 * 订单来源
	 */
	private String orderSource;
	/**
	 * 送货方式
	 */
	private String pickUpType;
	/**
	 * 收货联系人
	 */
	private String receiveName;
	/**
	 * 收货人手机
	 */
	private String receivePhone;
	/**
	 * 收货人电话
	 */
	private String receiveMobile;
	/**
	 * 收货人所在省份
	 */
	private String receiveProvince;
	/**
	 * 收货人所在城市
	 */
	private String receiveCity;
	/**
	 * 收货人所在区县
	 */
	private String receiveCounty;
	/**
	 * 收货人镇街道
	 */
	private String receiveAddress;
	/**
	 * 托运货物品名
	 */
	private String transCargoName;
	/**
	 * 需要安装的货物品名
	 */
	private String installCargoName;
	/**
	 * 托运货物重量
	 */
	private BigDecimal totalWeight;
	/**
	 * 托运货物体积
	 */
	private BigDecimal totalVolume;
	/**
	 * 托运货物件数
	 */
	private int totalNumber;
	/**
	 * 需要安装的货物件数
	 */
	private int installNumber;
	/**
	 * 超标楼层数
	 */
	private int overflowNumber;
	/**
	 * 人工搬楼费
	 */
	private BigDecimal moveCost;
	/**
	 * 提货网点名称
	 */
	private String pickNetWorkName;
	/**
	 * 提货网点编码
	 */
	private String pickNetWorkCode;
	/**
	 * 提货网点地址
	 */
	private String pickNetWorkAddress;
	/**
	 * 提货网点电话
	 */
	private String pickNetWorkMobile;
	
	/**
	 * 对内备注
	 */
	private String memo;
	/**
	 * （出发部门）名称
	 */
	private String departureDept;
	/**
	 * （出发部门）Code
	 */
	private String departureCode;
	/**
	 * 操作类型：
	 * Type=0，表示新增；
	 * Type=1，表示变更；
	 * Type=2，表示取消；
	 */
	private int operType;
	/**
	 * 信息推送次数
	 */
	private int pushTimes;
	/**
	 * 信息推送状态
	 */
	private String pushStatus;
	/**
	 * 定时任务编号
	 */
	private String jobId;
	/**
	 * 失败原因
	 */
	private String failReason;
	/**
	 * 操作人
	 */
	private String operatorName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 开单时间
	 */
	private Date createWayBillTime;
	/**
	 * 出发城市
	 */
	private String startCity;
	/**
	 * 承诺到达时间
	 */
	private Date commitmentArriveTime;
	/**
	 * 收取客户的特殊增值服务费总和
	 */
	private String totalSpecialServiceCharge;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	public String getLogisticID() {
		return logisticID;
	}
	public void setLogisticID(String logisticID) {
		this.logisticID = logisticID;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getPickUpType() {
		return pickUpType;
	}
	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	public String getReceiveMobile() {
		return receiveMobile;
	}
	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}
	public String getReceiveProvince() {
		return receiveProvince;
	}
	public void setReceiveProvince(String receiveProvince) {
		this.receiveProvince = receiveProvince;
	}
	public String getReceiveCity() {
		return receiveCity;
	}
	public void setReceiveCity(String receiveCity) {
		this.receiveCity = receiveCity;
	}
	public String getReceiveCounty() {
		return receiveCounty;
	}
	public void setReceiveCounty(String receiveCounty) {
		this.receiveCounty = receiveCounty;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getTransCargoName() {
		return transCargoName;
	}
	public void setTransCargoName(String transCargoName) {
		this.transCargoName = transCargoName;
	}
	public String getInstallCargoName() {
		return installCargoName;
	}
	public void setInstallCargoName(String installCargoName) {
		this.installCargoName = installCargoName;
	}
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}
	public BigDecimal getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getInstallNumber() {
		return installNumber;
	}
	public void setInstallNumber(int installNumber) {
		this.installNumber = installNumber;
	}
	public int getOverflowNumber() {
		return overflowNumber;
	}
	public void setOverflowNumber(int overflowNumber) {
		this.overflowNumber = overflowNumber;
	}
	public BigDecimal getMoveCost() {
		return moveCost;
	}
	public void setMoveCost(BigDecimal moveCost) {
		this.moveCost = moveCost;
	}
	public String getPickNetWorkName() {
		return pickNetWorkName;
	}
	public void setPickNetWorkName(String pickNetWorkName) {
		this.pickNetWorkName = pickNetWorkName;
	}
	public String getPickNetWorkCode() {
		return pickNetWorkCode;
	}
	public void setPickNetWorkCode(String pickNetWorkCode) {
		this.pickNetWorkCode = pickNetWorkCode;
	}
	public String getPickNetWorkAddress() {
		return pickNetWorkAddress;
	}
	public void setPickNetWorkAddress(String pickNetWorkAddress) {
		this.pickNetWorkAddress = pickNetWorkAddress;
	}
	public String getPickNetWorkMobile() {
		return pickNetWorkMobile;
	}
	public void setPickNetWorkMobile(String pickNetWorkMobile) {
		this.pickNetWorkMobile = pickNetWorkMobile;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getDepartureDept() {
		return departureDept;
	}
	public void setDepartureDept(String departureDept) {
		this.departureDept = departureDept;
	}
	public String getDepartureCode() {
		return departureCode;
	}
	public void setDepartureCode(String departureCode) {
		this.departureCode = departureCode;
	}
	public int getOperType() {
		return operType;
	}
	public void setOperType(int operType) {
		this.operType = operType;
	}
	public int getPushTimes() {
		return pushTimes;
	}
	public void setPushTimes(int pushTimes) {
		this.pushTimes = pushTimes;
	}
	public String getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Date getCreateWayBillTime() {
		return createWayBillTime;
	}
	public void setCreateWayBillTime(Date createWayBillTime) {
		this.createWayBillTime = createWayBillTime;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public Date getCommitmentArriveTime() {
		return commitmentArriveTime;
	}
	public void setCommitmentArriveTime(Date commitmentArriveTime) {
		this.commitmentArriveTime = commitmentArriveTime;
	}
	public String getTotalSpecialServiceCharge() {
		return totalSpecialServiceCharge;
	}
	public void setTotalSpecialServiceCharge(String totalSpecialServiceCharge) {
		this.totalSpecialServiceCharge = totalSpecialServiceCharge;
	}
	
}