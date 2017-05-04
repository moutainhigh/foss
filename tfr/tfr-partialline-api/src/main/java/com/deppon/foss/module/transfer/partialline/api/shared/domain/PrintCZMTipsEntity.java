/**
 * @author foss 257200
 * 2015-8-21
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.util.Date;

/**
 * @author 257220
 *
 */
public class PrintCZMTipsEntity {
	private String id;
	private String waybillNo;
	private String serialNo;
	private String agentWaybillNo;
	private String parentWaybillNo;
	private String goodsName;
	private String isPrint;
	private String isBind;
	private String isPicPackage;
	private Date printTime;
	private int printNum;
	private String printUserCode;
	private String printUserName;
	private String printOrgCode;
	private String printOrgName;
	private Date inputTime;
	private String inputUserCode;
	private String inputOrgcode;
	private String updateUserCode;
	private String updateOrgCode;
	private Date updateTime;

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
	 * @return 运单号
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
	 * @return 运单流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return 代理单号
	 */
	public String getAgentWaybillNo() {
		return agentWaybillNo;
	}
	/**
	 * @param agentWaybillNo the agentWaybillNo to set
	 */
	public void setAgentWaybillNo(String agentWaybillNo) {
		this.agentWaybillNo = agentWaybillNo;
	}
	/**
	 * @return 母件单号
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
	 * @return 货物名称
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
	 * @return 是否打印
	 */
	public String getIsPrint() {
		return isPrint;
	}
	/**
	 * @param isPrint the isPrint to set
	 */
	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}
	
	/**
	 * @return 打印时间
	 */
	public Date getPrintTime() {
		return printTime;
	}
	/**
	 * @param printTime the printTime to set
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}
	/**
	 * @return 打印次数
	 */
	public int getPrintNum() {
		return printNum;
	}
	/**
	 * @param printNum the printNum to set
	 */
	public void setPrintNum(int printNum) {
		this.printNum = printNum;
	}
	/**
	 * @return 打印人编号
	 */
	public String getPrintUserCode() {
		return printUserCode;
	}
	/**
	 * @param printUserCode the printUserCode to set
	 */
	public void setPrintUserCode(String printUserCode) {
		this.printUserCode = printUserCode;
	}
	/**
	 * @return 打印人
	 */
	public String getPrintUserName() {
		return printUserName;
	}
	/**
	 * @param printUserName the printUserName to set
	 */
	public void setPrintUserName(String printUserName) {
		this.printUserName = printUserName;
	}
	/**
	 * @return 打印机构
	 */
	public String getPrintOrgCode() {
		return printOrgCode;
	}
	/**
	 * @param printOrgCode the printOrgCode to set
	 */
	public void setPrintOrgCode(String printOrgCode) {
		this.printOrgCode = printOrgCode;
	}
	/**
	 * @return 打印机构
	 */
	public String getPrintOrgName() {
		return printOrgName;
	}
	/**
	 * @param printOrgName the printOrgName to set
	 */
	public void setPrintOrgName(String printOrgName) {
		this.printOrgName = printOrgName;
	}
	/**
	 * @return 录入时间
	 */
	public Date getInputTime() {
		return inputTime;
	}
	/**
	 * @param inputTime the inputTime to set
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	/**
	 * @return 录入人code
	 */
	public String getInputUserCode() {
		return inputUserCode;
	}
	/**
	 * @param inputUserCode the inputUserCode to set
	 */
	public void setInputUserCode(String inputUserCode) {
		this.inputUserCode = inputUserCode;
	}
	/**
	 * @return 录入人机构
	 */
	public String getInputOrgcode() {
		return inputOrgcode;
	}
	/**
	 * @param inputOrgcode the inputOrgcode to set
	 */
	public void setInputOrgcode(String inputOrgcode) {
		this.inputOrgcode = inputOrgcode;
	}
	/**
	 * @return 更新人
	 */
	public String getUpdateUserCode() {
		return updateUserCode;
	}
	/**
	 * @param updateUserCode the updateUserCode to set
	 */
	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
	}
	/**
	 * @return 更新人机构编号
	 */
	public String getUpdateOrgCode() {
		return updateOrgCode;
	}
	/**
	 * @param updateOrgCode the updateOrgCode to set
	 */
	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}
	/**
	 * @return 更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the isBind
	 */
	public String getIsBind() {
		return isBind;
	}
	/**
	 * @param isBind the isBind to set
	 */
	public void setIsBind(String isBind) {
		this.isBind = isBind;
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
}
