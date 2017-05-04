/**
 * @author foss 257200
 * 2015-8-29
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 257220
 *
 */
public class InputWeightVolumnDto {
	private String id;
	private String parentWaybillNo;
	private String waybillNo;
	private String serialNo;
	private String isPicPackage;
	private int totalNum;//总票数
	private BigDecimal totalWeight;
	private BigDecimal totalVolumn;
	private String modifyUserCode;
	private String modifyUserName;
	private String modifyOrgCode;
	private String modifyOrgName;
	private String modifyTime;
	private BigDecimal weight;
	private BigDecimal volumn;
	//是否能修改
	private String canModify;
	//交接单号
	private List<String> handOverBillList;
	//运单号
	private List<String> waybillNoList;
	//交接开始时间
	private Date beginHandOverTime;
	//交接结束时间
	private Date endHandOverTime;
	//入库开始时间
	private Date beginInStockTime;
	//入库结束时间
	private Date endInStockTime;
	//外场code
	private String deptCode;
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
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
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
	 * @return the totalNum
	 */
	public int getTotalNum() {
		return totalNum;
	}
	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}
	public BigDecimal getTotalVolumn() {
		return totalVolumn;
	}
	public void setTotalVolumn(BigDecimal totalVolumn) {
		this.totalVolumn = totalVolumn;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}
	/**
	 * @return the modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	/**
	 * @param modifyUserCode the modifyUserCode to set
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	/**
	 * @return the modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}
	/**
	 * @param modifyUserName the modifyUserName to set
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	/**
	 * @return the modifyTime
	 */
	public String getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * @return the handOverBillList
	 */
	public List<String> getHandOverBillList() {
		return handOverBillList;
	}
	/**
	 * @param handOverBillList the handOverBillList to set
	 */
	public void setHandOverBillList(List<String> handOverBillList) {
		this.handOverBillList = handOverBillList;
	}
	/**
	 * @return the waybillNoList
	 */
	public List<String> getWaybillNoList() {
		return waybillNoList;
	}
	/**
	 * @param waybillNoList the waybillNoList to set
	 */
	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
	/**
	 * @return the beginHandOverTime
	 */
	public Date getBeginHandOverTime() {
		return beginHandOverTime;
	}
	/**
	 * @param beginHandOverTime the beginHandOverTime to set
	 */
	public void setBeginHandOverTime(Date beginHandOverTime) {
		this.beginHandOverTime = beginHandOverTime;
	}
	/**
	 * @return the endHandOverTime
	 */
	public Date getEndHandOverTime() {
		return endHandOverTime;
	}
	/**
	 * @param endHandOverTime the endHandOverTime to set
	 */
	public void setEndHandOverTime(Date endHandOverTime) {
		this.endHandOverTime = endHandOverTime;
	}
	/**
	 * @return the beginInStockTime
	 */
	public Date getBeginInStockTime() {
		return beginInStockTime;
	}
	/**
	 * @param beginInStockTime the beginInStockTime to set
	 */
	public void setBeginInStockTime(Date beginInStockTime) {
		this.beginInStockTime = beginInStockTime;
	}
	/**
	 * @return the endInStockTime
	 */
	public Date getEndInStockTime() {
		return endInStockTime;
	}
	/**
	 * @param endInStockTime the endInStockTime to set
	 */
	public void setEndInStockTime(Date endInStockTime) {
		this.endInStockTime = endInStockTime;
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
	 * @return the modifyOrgCode
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}
	/**
	 * @param modifyOrgCode the modifyOrgCode to set
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}
	/**
	 * @return the modifyOrgName
	 */
	public String getModifyOrgName() {
		return modifyOrgName;
	}
	/**
	 * @param modifyOrgName the modifyOrgName to set
	 */
	public void setModifyOrgName(String modifyOrgName) {
		this.modifyOrgName = modifyOrgName;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public BigDecimal getVolumn() {
		return volumn;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getCanModify() {
		return canModify;
	}
	public void setCanModify(String canModify) {
		this.canModify = canModify;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
}
