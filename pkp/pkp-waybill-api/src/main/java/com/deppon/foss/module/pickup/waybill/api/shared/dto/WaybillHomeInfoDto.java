package com.deppon.foss.module.pickup.waybill.api.shared.dto;

import java.util.Date;
import java.util.List;

public class WaybillHomeInfoDto {
	
	private String id; // FOSS传入的唯一标识

	private String mailNo;// 运单号

	private String logisticID; // 订单号

	private String orderSource; // 订单来源

	private String pickUpType; // 提货方式

	private String receiveName; // 收货联系人

	private String receivePhone; // 收货人电话

	private String receiveMobile; // 收货人手机

	private String receiveProvince; // 收货人所在省份

	private String receiveCity; // 收货人城市

	private String receiveCounty; // 收货人区县

	private String receiveAddress; // 收货人街道

	private String transCargoName; // 货物品名

	private Double totalWeight; // 总重量

	private Double totalVolume; // 总体积

	private Integer totalNumber; // 总件数

	private Integer overflowNumber; // 超标楼层数

	private Double moveCost; // 人工搬楼费

	private String pickNetWorkName; // 提货网点名称

	private String pickNetWorkCode; // 提货网点编码

	private String pickNetWorkAddress; // 提货网点地址

	private String pickNetWorkPhone; // 提货网点电话

	private String memo; // 对内备注

	private String departureDept; // （出发/收货部门）名称

	private String departureCode; // （出发/收货部门）Code

	private Integer operType; // 操作类型：Type=0，表示新增；Type=1，表示变更；Type=2，表示取消；

	private List<InstallDetail> detailList; // 安装物品详情
	
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

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Double getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Double totalVolume) {
		this.totalVolume = totalVolume;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Integer getOverflowNumber() {
		return overflowNumber;
	}

	public void setOverflowNumber(Integer overflowNumber) {
		this.overflowNumber = overflowNumber;
	}

	public Double getMoveCost() {
		return moveCost;
	}

	public void setMoveCost(Double moveCost) {
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

	public String getPickNetWorkPhone() {
		return pickNetWorkPhone;
	}

	public void setPickNetWorkPhone(String pickNetWorkPhone) {
		this.pickNetWorkPhone = pickNetWorkPhone;
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

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public List<InstallDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<InstallDetail> detailList) {
		this.detailList = detailList;
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
