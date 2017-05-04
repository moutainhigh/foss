package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
* @description 同步给快递系统的:交接单实体类
* @version 1.0
* @author 332209-foss ruilibao
* @update 2016年4月27日 上午10:06:21
*/
public class UpdateWkHandOverBillEntity implements Serializable{
	
	/**
	* @fields serialVersionUID
	* @author 332209-foss ruilibao
	* @update 2016年4月27日 上午10:08:09
	* @version V1.0
	*/
	private static final long serialVersionUID = 1L;

	/*悟空交接单实体,包含（交接单号,操作部门）*/
	private List<WkHandOverBillEntity> truckHandoverBillList;
	/*交接单状态*/
	private String handoverState;
	/*出发时间*/
	private Date departTime;
	/*到达时间*/
	private Date arriveTime;
	/*预计到达时间*/
	private Date planArriveTime;
	/*操作人工号*/
	private String operatorCode;
	/*操作人名称*/
	private String operatorName;
	/*当前操作部门编码*/
	private String orgCode;
	/*当前操作部门名称*/
	private String orgName;
	/*请求类型 1、发车确认 2、取消出发 3、到达确认 4、取消到达 5、交接单作废*/
	private String operationType;
	
	/**
	 * @return the wkHandOverBill
	 */
	public List<WkHandOverBillEntity> getTruckHandoverBillList() {
		return truckHandoverBillList;
	}
	/**
	 * @param wkHandOverBill the wkHandOverBill to set
	 */
	public void setTruckHandoverBillList(List<WkHandOverBillEntity> truckHandoverBillList) {
		this.truckHandoverBillList = truckHandoverBillList;
	}
	/**
	 * @return the handoverState
	 */
	public String getHandoverState() {
		return handoverState;
	}
	/**
	 * @param handoverState the handoverState to set
	 */
	public void setHandoverState(String handoverState) {
		this.handoverState = handoverState;
	}
	/**
	 * @return the departTime
	 */
	public Date getDepartTime() {
		return departTime;
	}
	/**
	 * @param departTime the departTime to set
	 */
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}
	/**
	 * @return the arriveTime
	 */
	public Date getArriveTime() {
		return arriveTime;
	}
	/**
	 * @param arriveTime the arriveTime to set
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	/**
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}
	/**
	 * @param operatorCode the operatorCode to set
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the operationType
	 */
	public String getOperationType() {
		return operationType;
	}
	/**
	 * @param operationType the operationType to set
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	/**
	 * @return the planArriveTime
	 */
	public Date getPlanArriveTime() {
		return planArriveTime;
	}
	/**
	 * @param planArriveTime the planArriveTime to set
	 */
	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}
}
