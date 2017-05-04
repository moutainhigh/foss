package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.util.Date;
import java.util.List;

public class ClearCrgDetail {
	/**
	 * 清仓任务编号
	 */
	private String taskCode;
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 类型
	 */
	private String transType;
	/**
	 * 库存件数
	 */
	private int invtPieces;
	/**
	 * 清仓件数
	 */
	private int clearPieces;
	/**
	 * 扫描件数
	 *//*
	private int scanPieces;*/
	/**
	 * 重量
	 *//*
	private double weight;*/
	/**
	 * 体积
	 */
	private double volume;
	/**
	 * 比重
	 *//*
	private double specGrvt;*/
	/**
	 * 货名
	 */
	private String crgName;
	/**
	 * 包装类型
	 */
	private String wrapType;
/*	*//**
	 * 收货部门
	 *//*
	private String acctDeptCode;
	*//**
	 * 收货部门
	 *//*
	private String acctDeptName;*/
	/**
	 * 入库时间
	 */
	private Date inInvtTime;
	/**
	 * 到达网点
	 */
	private String arrDeptCode;
	/**
	 * 到达网点
	 */
	private String arrDeptName;
	/**
	 * 是否贵重物品
	 */
	private String isVal;
	/**
	 * 是否需代打木架
	 */
	private String isWrap;
	/**
	 * 是否需要更换标签
	 */
	private String isChgLabel;
	/**
	 * 是否标签作废
	 */
	private String isLabelAbolish;
	/**
	 * 是否违禁物品
	 */
	private String isCtrabdGoods;
	
	//private String isModify;
	
	//private String chgStatus;
	private List<ClearSerialNo> serialNos;
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
	 *行政区域 
	 */
	private String custDistName;
	
	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getWblCode() {
		return wblCode;
	}

	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public int getInvtPieces() {
		return invtPieces;
	}

	public void setInvtPieces(int invtPieces) {
		this.invtPieces = invtPieces;
	}

	public int getClearPieces() {
		return clearPieces;
	}

	public void setClearPieces(int clearPieces) {
		this.clearPieces = clearPieces;
	}

	/*public int getScanPieces() {
		return scanPieces;
	}

	public void setScanPieces(int scanPieces) {
		this.scanPieces = scanPieces;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}*/

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	/*public double getSpecGrvt() {
		return specGrvt;
	}

	public void setSpecGrvt(double specGrvt) {
		this.specGrvt = specGrvt;
	}*/

	public String getCrgName() {
		return crgName;
	}

	public void setCrgName(String crgName) {
		this.crgName = crgName;
	}

	public String getWrapType() {
		return wrapType;
	}

	public void setWrapType(String wrapType) {
		this.wrapType = wrapType;
	}

	/*public String getAcctDeptCode() {
		return acctDeptCode;
	}

	public void setAcctDeptCode(String acctDeptCode) {
		this.acctDeptCode = acctDeptCode;
	}*/

	public Date getInInvtTime() {
		return inInvtTime;
	}

	public void setInInvtTime(Date inInvtTime) {
		this.inInvtTime = inInvtTime;
	}

	

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	public String getIsVal() {
		return isVal;
	}

	public void setIsVal(String isVal) {
		this.isVal = isVal;
	}

	public String getIsWrap() {
		return isWrap;
	}

	public void setIsWrap(String isWrap) {
		this.isWrap = isWrap;
	}

	public String getIsChgLabel() {
		return isChgLabel;
	}

	public void setIsChgLabel(String isChgLabel) {
		this.isChgLabel = isChgLabel;
	}

	public String getIsLabelAbolish() {
		return isLabelAbolish;
	}

	public void setIsLabelAbolish(String isLabelAbolish) {
		this.isLabelAbolish = isLabelAbolish;
	}

	public String getIsCtrabdGoods() {
		return isCtrabdGoods;
	}

	public void setIsCtrabdGoods(String isCtrabdGoods) {
		this.isCtrabdGoods = isCtrabdGoods;
	}

/*	public String getAcctDeptName() {
		return acctDeptName;
	}

	public void setAcctDeptName(String acctDeptName) {
		this.acctDeptName = acctDeptName;
	}
*/
	public String getArrDeptName() {
		return arrDeptName;
	}

	public void setArrDeptName(String arrDeptName) {
		this.arrDeptName = arrDeptName;
	}


	/*public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}*/

	/*public String getChgStatus() {
		return chgStatus;
	}

	public void setChgStatus(String chgStatus) {
		this.chgStatus = chgStatus;
	}
*/
	public List<ClearSerialNo> getSerialNos() {
		return serialNos;
	}

	public void setSerialNos(List<ClearSerialNo> serialNos) {
		this.serialNos = serialNos;
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

	public String getArrDeptCode() {
		return arrDeptCode;
	}

	public void setArrDeptCode(String arrDeptCode) {
		this.arrDeptCode = arrDeptCode;
	}

	public String getIsModifyStr() {
		return isModifyStr;
	}

	public void setIsModifyStr(String isModifyStr) {
		this.isModifyStr = isModifyStr;
	}

	/**
	 * @return the custDistName
	 */
	public String getCustDistName() {
		return custDistName;
	}

	/**
	 * @param custDistName the custDistName to set
	 */
	public void setCustDistName(String custDistName) {
		this.custDistName = custDistName;
	}

	
	
	
	
}
