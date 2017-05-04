/**
 * @author foss 257200
 * 2015-8-21
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.util.Date;

/**
 * 打印提示标签历史记录
 * @author 257220
 *	
 */
public class PrintCZMTipsHisEntity {
	private String id;
	private String printTipsId;
	private String waybillNo;
	private String serialNo;
	private String agentWaybillNo;
	private String parentWaybillNo;
	private String goodsName;
	private String isPrint;
	private Date printTime;
	private int printNum;
	private String printUserCode;
	private String printUserName;
	private String printOrgCode;
	private String printOrgName;
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
	 * @return 打印标签id
	 */
	public String getPrintTipsId() {
		return printTipsId;
	}
	/**
	 * @param printTipsId the printTipsId to set
	 */
	public void setPrintTipsId(String printTipsId) {
		this.printTipsId = printTipsId;
	}
	/**
	 * @return 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param wayBillNo the wayBillNo to set
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
	 * @param parentWayBillNo the parentWayBillNo to set
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
}
