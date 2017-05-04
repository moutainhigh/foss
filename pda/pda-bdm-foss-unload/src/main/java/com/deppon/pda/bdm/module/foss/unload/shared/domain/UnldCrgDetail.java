package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.List;

/**
 * 卸车货物详细
 * 
 * @author gaojia
 * @date Sep 6,2012 14:33:30 PM
 * @version 1.0
 * @since
 */
public class UnldCrgDetail {
	/**
	 * 到达部门
	 */
	private String arrDeptCode;
	/**
	 * 到达部门名称
	 */
	private String arrDeptName;

	private List<UnldSerialNoModel> serialNo;
	/**
	 * 货名
	 */
	private String cargoName;
	/**
	 * 更改单处理状态
	 */
	private String chgStatus;
	/**
	 * 是否贵重物品
	 */
	private String isVal;
	/**
	 * 卸车任务号
	 */
	private String taskCode;
	/**
	 * 运输性质
	 */
	private String transType;
	/**
	 * 体积
	 */
	private double volume;
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 重量
	 */
	private double weight;
	/**
	 * 是否必走货
	 */
	private String isNessary;
	/**
	 * 收货部门
	 */
	private String acctDeptCode;
	/**
	 * 接货部门名称
	 */
	private String acctDeptName;
	private String remark;
	/**
	 * 包装 11-26
	 */
	private String packing;

	// private String isModify;
	private String beContraband; // 是否违禁品
	private String origOrgCode; // 出发部门编号
	private String destOrgCode; // 到达部门编号
	private String billNo; // 单据编号
	/**
	 * 开单件数
	 */
	private int pieces;
	/**
	 * 交接件数
	 */
	private int rcptPieces;
	/**
	 * 已卸车件数
	 */
	private int unldPieces;

	/**
	 * 是否分批配载
	 */
	private String bePartial;

	/**
	 * 流水号二进制字符串
	 */
	private String serialNoStr;
	/**
	 * 状态为“Y”的二进制字符串
	 */
	private String unPackingStr;
	/**
	 * 提货站点编码
	 */
	private String stationNumber;
	/**
	 * 是否有更改字符串
	 */
	private String isModifyStr;

	/**
	 * 包装备注
	 */
	private String packageRemark;

	/**
	 * 行政区域
	 */
	private String custDistName;

	/** zwd 200968 运单生效状态 - YES NO */
	private String wayBillStatus;
	//是否电子面单 author:245960 Date:2015-06-29
	private String beEWaybill;
	private String isSevenDaysReturn;
	/**
	 * 是否转寄退回件
	 */
	private String isHandle;
	
	public String getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}

	public String getIsSevenDaysReturn() {
		return isSevenDaysReturn;
	}

	public void setIsSevenDaysReturn(String isSevenDaysReturn) {
		this.isSevenDaysReturn = isSevenDaysReturn;
	}

	public String getBeEWaybill() {
		return beEWaybill;
	}

	public void setBeEWaybill(String beEWaybill) {
		this.beEWaybill = beEWaybill;
	}

	public String getPacking() {
		return packing;
	}

	/* public String getIsModify() { return isModify; } public void
	 * setIsModify(String isModify) { this.isModify = isModify; } */

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getIsNessary() {
		return isNessary;
	}

	public void setIsNessary(String isNessary) {
		this.isNessary = isNessary;
	}

	public String getArrDeptCode() {
		return arrDeptCode;
	}

	public void setArrDeptCode(String arrDeptCode) {
		this.arrDeptCode = arrDeptCode;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public String getIsVal() {
		return isVal;
	}

	public void setIsVal(String isVal) {
		this.isVal = isVal;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getWblCode() {
		return wblCode;
	}

	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getChgStatus() {
		return chgStatus;
	}

	public void setChgStatus(String chgStatus) {
		this.chgStatus = chgStatus;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getArrDeptName() {
		return arrDeptName;
	}

	public void setArrDeptName(String arrDeptName) {
		this.arrDeptName = arrDeptName;
	}

	public String getAcctDeptCode() {
		return acctDeptCode;
	}

	public void setAcctDeptCode(String acctDeptCode) {
		this.acctDeptCode = acctDeptCode;
	}

	public String getAcctDeptName() {
		return acctDeptName;
	}

	public void setAcctDeptName(String acctDeptName) {
		this.acctDeptName = acctDeptName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBeContraband() {
		return beContraband;
	}

	public void setBeContraband(String beContraband) {
		this.beContraband = beContraband;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	public int getRcptPieces() {
		return rcptPieces;
	}

	public void setRcptPieces(int rcptPieces) {
		this.rcptPieces = rcptPieces;
	}

	public int getUnldPieces() {
		return unldPieces;
	}

	public void setUnldPieces(int unldPieces) {
		this.unldPieces = unldPieces;
	}

	public List<UnldSerialNoModel> getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(List<UnldSerialNoModel> serialNo) {
		this.serialNo = serialNo;
	}

	public String getBePartial() {
		return bePartial;
	}

	public void setBePartial(String bePartial) {
		this.bePartial = bePartial;
	}

	public String getSerialNoStr() {
		return serialNoStr;
	}

	public void setSerialNoStr(String serialNoStr) {
		this.serialNoStr = serialNoStr;
	}

	public String getUnPackingStr() {
		return unPackingStr;
	}

	public void setUnPackingStr(String unPackingStr) {
		this.unPackingStr = unPackingStr;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	public String getIsModifyStr() {
		return isModifyStr;
	}

	public void setIsModifyStr(String isModifyStr) {
		this.isModifyStr = isModifyStr;
	}

	/**
	 * @return 获得刷新卸车任务时的行政区域
	 */
	public String getCustDistName() {
		return custDistName;
	}

	/**
	 * @param 设置刷新卸车任务时的行政区域字段
	 */
	public void setCustDistName(String custDistName) {
		this.custDistName = custDistName;
	}

	public String getPackageRemark() {
		return packageRemark;
	}

	public void setPackageRemark(String packageRemark) {
		this.packageRemark = packageRemark;
	}

	public String getWayBillStatus() {
		return wayBillStatus;
	}

	public void setWayBillStatus(String wayBillStatus) {
		this.wayBillStatus = wayBillStatus;
	}

}