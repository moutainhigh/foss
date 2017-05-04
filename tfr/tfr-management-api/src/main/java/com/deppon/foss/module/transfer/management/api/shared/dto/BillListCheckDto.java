package com.deppon.foss.module.transfer.management.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BillListCheckDto implements Serializable {

	/**
	 * 序号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 账单日期
	 */
	private Date billDate;
	/**
	 * 燃油费金额
	 */
	private BigDecimal fuelFee;
	/**
	 * 燃油费优惠金额
	 */
	private BigDecimal fuelFeeSell;
	/**
	 * 燃油费优惠后金额
	 */
	private BigDecimal fuelFeeSellAfter;
	/**
	 * 路桥费金额
	 */
	private BigDecimal roadTollFee;
	/**
	 * 路桥费优惠金额 
	 */
	private BigDecimal roadTollFeeSell;
	/**
	 * 路桥费优惠后金额 
	 */
	private BigDecimal roadTollFeeSellAfter;
	/**
	 * 所属事业部code
	 */
	private String divisionOrgCode;
	/**
	 * 所属事业部Name
	 */
	private String divisionOrgName;
	/**
	 * 所属车队code
	 */
	private String transDepartmentCode;
	/**
	 * 所属车队Name
	 */
	private String transDepartmentName;
	/**
	 * 操作人code
	 */
	private String operatorCode;
	/**
	 * 操作人name
	 */
	private String operatorName;
	/**
	 * 操作日期
	 */
	private Date operateDate;
	
	
	/**
	 * 队列ID
	* @fields queueID
	* @author 14022-foss-songjie 
	* @update 2013年12月6日 上午9:36:14
	* @version V1.0
	*/
	private String queueID;
	
	/**
	 * 获取主键ID
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置主键ID
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取账单日期
	 * @return
	 */
	public Date getBillDate() {
		return billDate;
	}
	/**
	 * 设置账单日期
	 * @param billDate
	 */
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	/**
	 * 获取燃油费金额
	 * @return
	 */
	public BigDecimal getFuelFee() {
		return fuelFee;
	}
	/**
	 * 设置燃油费金额
	 * @param fuelFee
	 */
	public void setFuelFee(BigDecimal fuelFee) {
		this.fuelFee = fuelFee;
	}
	/**
	 * 获取燃油费优惠金额
	 * @return
	 */
	public BigDecimal getFuelFeeSell() {
		return fuelFeeSell;
	}
	/**
	 * 设置燃油费优惠金额
	 * @param fuelFeeSell
	 */
	public void setFuelFeeSell(BigDecimal fuelFeeSell) {
		this.fuelFeeSell = fuelFeeSell;
	}
	/**
	 * 获取燃油费优惠后金额
	 * @return
	 */
	public BigDecimal getFuelFeeSellAfter() {
		return fuelFeeSellAfter;
	}
	/**
	 * 设置燃油费优惠后金额
	 * @param fuelFeeSellAfter
	 */
	public void setFuelFeeSellAfter(BigDecimal fuelFeeSellAfter) {
		this.fuelFeeSellAfter = fuelFeeSellAfter;
	}
	/**
	 * 获取路桥费金额
	 * @return
	 */
	public BigDecimal getRoadTollFee() {
		return roadTollFee;
	}
	/**
	 * 设置路桥费金额
	 * @param roadTollFee
	 */
	public void setRoadTollFee(BigDecimal roadTollFee) {
		this.roadTollFee = roadTollFee;
	}
	/**
	 * 获取路桥费优惠金额
	 * @return
	 */
	public BigDecimal getRoadTollFeeSell() {
		return roadTollFeeSell;
	}
	/**
	 * 设置路桥费优惠金额
	 * @param roadTollFeeSell
	 */
	public void setRoadTollFeeSell(BigDecimal roadTollFeeSell) {
		this.roadTollFeeSell = roadTollFeeSell;
	}
	/**
	 * 获取路桥费优惠后金额
	 * @return
	 */
	public BigDecimal getRoadTollFeeSellAfter() {
		return roadTollFeeSellAfter;
	}
	/**
	 * 设置路桥费优惠后金额
	 * @param roadTollFeeSellAfter
	 */
	public void setRoadTollFeeSellAfter(BigDecimal roadTollFeeSellAfter) {
		this.roadTollFeeSellAfter = roadTollFeeSellAfter;
	}
	/**
	 * 获取所属事业部code
	 * @return
	 */
	public String getDivisionOrgCode() {
		return divisionOrgCode;
	}
	/**
	 * 设置所属事业部code
	 * @param divisionOrgCode
	 */
	public void setDivisionOrgCode(String divisionOrgCode) {
		this.divisionOrgCode = divisionOrgCode;
	}
	/**
	 * 获取所属事业部Name
	 * @return
	 */
	public String getDivisionOrgName() {
		return divisionOrgName;
	}
	/**
	 * 设置所属事业部name
	 * @param divisionOrgName
	 */
	public void setDivisionOrgName(String divisionOrgName) {
		this.divisionOrgName = divisionOrgName;
	}
	/**
	 * 获取所属车队code
	 * @return
	 */
	public String getTransDepartmentCode() {
		return transDepartmentCode;
	}
	/**
	 * 设置所属车队code
	 * @param transDepartmentCode
	 */
	public void setTransDepartmentCode(String transDepartmentCode) {
		this.transDepartmentCode = transDepartmentCode;
	}
	/**
	 * 获取所属车队Name
	 * @return
	 */
	public String getTransDepartmentName() {
		return transDepartmentName;
	}
	/**
	 * 设置所属车队Name
	 * @param transDepartmentName
	 */
	public void setTransDepartmentName(String transDepartmentName) {
		this.transDepartmentName = transDepartmentName;
	}
	/**
	 * 获取操作人code
	 * @return
	 */
	public String getOperatorCode() {
		return operatorCode;
	}
	/**
	 * 设置操作人code
	 * @param operatorCode
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	/**
	 * 获取操作人name
	 * @return
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * 设置操作人name
	 * @param operatorName
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * 获取操作日期
	 * @return
	 */
	public Date getOperateDate() {
		return operateDate;
	}
	/**
	 * 设置操作日期
	 * @param operateDate
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public String getQueueID() {
		return queueID;
	}
	public void setQueueID(String queueID) {
		this.queueID = queueID;
	}
	
	
	
	
}
