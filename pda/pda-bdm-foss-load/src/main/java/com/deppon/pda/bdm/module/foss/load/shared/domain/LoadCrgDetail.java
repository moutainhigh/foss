package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.util.Date;
import java.util.List;

/**
 * 建立装车任务货物信息
 * 
 * @author gaojia
 * @date Sep 6,2012 14:33:30 PM
 * @version 1.0
 * @since
 */
/**  
 *@author 201638
 *@date   2014-9-23
 */
public class LoadCrgDetail {


	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 装车任务编号
	 */
	private String taskCode;
	/**
	 * 流水号
	 */
	private List<SerialNoModel> serialNo;
	/**
	 * 重量
	 */
	private double weight;
	/**
	 * 体积
	 */
	private double volume;
	/**
	 * 货名
	 */
	private String cargoName;
	/**
	 * 运输性质
	 */
	private String transType;
	/**
	 * 收货部门
	 */
	private String acctDeptCode;
	/**
	 * 接货部门名称
	 */
	private String acctDeptName;
	/**
	 * 到达部门
	 */
	private String arrDeptCode;
	/**
	 * 到达部门名称
	 */
	private String arrDeptName;
	/**
	 * 入库时间
	 */
	private Date inInvtTime;
	/**
	 * 更改单处理状态
	 */
	private String chgStatus;
	/**
	 * 
	 * 是否比走货
	 */
	private String isNessary;
	private String remark;
	/**
	 * 包装 11-26
	 */
	private String packing;
	/**
	 * 是否贵重物品
	 */
	private String isVal;
	//private String isModify;
	/**
	 * 是否合车
	 */
	private String beJoinCar;
	/**
	 * 库存件数
	 */
	private int stockQty;
	/**
	 * 已装车件数
	 */
	private int loadPieces;
	/**
	 * 开单件数
	 */
	private int pieces;
	/**
	 * 流水号二进制字符串
	 */
	private String serialNoStr;
	/**
	 * 未打包装状态为“Y”的二进制字符串
	 */
	private String unPackingStr;
	/**
	 * 未出贵重物品货区
	 */
	private String valAreaSerNoStr;
	/**
	 * 未出包装货区
	 */
	private String packAreaSerNoStr;
	/**
	 * 提货站点编码
	 */
	private String stationNumber;
	/**
	 * 是否有更改字符串
	 */
	private String isModifyStr;
	
	/**
	 *行政区域 
	 */
	private String custDistName;
	
	/**货物位置*/
	private String freightPosition;
	
	//是否电子面单 author:245960 Date:2015-06-29
	private String beEWaybill;
	
	public String getBeEWaybill() {
		return beEWaybill;
	}

	public void setBeEWaybill(String beEWaybill) {
		this.beEWaybill = beEWaybill;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getWblCode() {
		return wblCode;
	}

	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}


	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getAcctDeptCode() {
		return acctDeptCode;
	}

	public void setAcctDeptCode(String acctDeptCode) {
		this.acctDeptCode = acctDeptCode;
	}

	public String getArrDeptCode() {
		return arrDeptCode;
	}

	public void setArrDeptCode(String arrDeptCode) {
		this.arrDeptCode = arrDeptCode;
	}

	public Date getInInvtTime() {
		return inInvtTime;
	}

	public void setInInvtTime(Date inInvtTime) {
		this.inInvtTime = inInvtTime;
	}

	public String getIsNessary() {
		return isNessary;
	}

	public void setIsNessary(String isNessary) {
		this.isNessary = isNessary;
	}

	public String getChgStatus() {
		return chgStatus;
	}

	public void setChgStatus(String chgStatus) {
		this.chgStatus = chgStatus;
	}

	public String getAcctDeptName() {
		return acctDeptName;
	}

	public void setAcctDeptName(String acctDeptName) {
		this.acctDeptName = acctDeptName;
	}

	public String getArrDeptName() {
		return arrDeptName;
	}

	public void setArrDeptName(String arrDeptName) {
		this.arrDeptName = arrDeptName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
	

	public String getIsVal() {
		return isVal;
	}

	public void setIsVal(String isVal) {
		this.isVal = isVal;
	}


	public List<SerialNoModel> getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(List<SerialNoModel> serialNo) {
		this.serialNo = serialNo;
	}

	/*public void setIsModify(String isModify) {
		this.isModify = isModify;
	}

	public String getIsModify() {
		return isModify;
	}*/

	public String getBeJoinCar() {
		return beJoinCar;
	}

	public void setBeJoinCar(String beJoinCar) {
		this.beJoinCar = beJoinCar;
	}

	public int getStockQty() {
		return stockQty;
	}

	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}



	public int getLoadPieces() {
		return loadPieces;
	}

	public void setLoadPieces(int loadPieces) {
		this.loadPieces = loadPieces;
	}

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
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

	public String getValAreaSerNoStr() {
		return valAreaSerNoStr;
	}

	public void setValAreaSerNoStr(String valAreaSerNoStr) {
		this.valAreaSerNoStr = valAreaSerNoStr;
	}

	public String getPackAreaSerNoStr() {
		return packAreaSerNoStr;
	}

	public void setPackAreaSerNoStr(String packAreaSerNoStr) {
		this.packAreaSerNoStr = packAreaSerNoStr;
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

	public String getCustDistName() {
		return custDistName;
	}

	public void setCustDistName(String custDistName) {
		this.custDistName = custDistName;
	}

	public String getFreightPosition() {
		return freightPosition;
	}

	public void setFreightPosition(String freightPosition) {
		this.freightPosition = freightPosition;
	}

	
}