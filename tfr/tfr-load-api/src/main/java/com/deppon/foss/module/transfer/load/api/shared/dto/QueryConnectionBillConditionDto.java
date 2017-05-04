package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/** 
 * @className: QueryConnectionBillDto
 * @author: zenghaibin foss 205109
 * @description: 查询交接单，交接单信息dto，用于接收接驳交接单查询条件参数
 * @date: 2012-10-23 上午11:22:27
 */
public class QueryConnectionBillConditionDto   implements Serializable {
	/**序列号**/
	private static final long serialVersionUID = -2066355499072497176L;
	
	/**交接单号 **/
	private String connectionBillNo;
	
	/**出发部门编码**/
	private String departDeptCode;
	
	/**到达接驳点编码 **/
	private String arriveDeptCode;
	
	/**车牌号 **/
	private String vehicleNo;
	
	/**交接类型 **/
	private String handOverType;
	
	/**开始交接时间 **/
	private Date beginHandOverTime;
	
	/**结束交接时间 **/
	private Date endHandOverTime;
	
	/**是否已到达 **/
	private String isArrived;

	public String getConnectionBillNo() {
		return connectionBillNo;
	}

	public void setConnectionBillNo(String connectionBillNo) {
		this.connectionBillNo = connectionBillNo;
	}

	public String getDepartDeptCode() {
		return departDeptCode;
	}

	public void setDepartDeptCode(String departDeptCode) {
		this.departDeptCode = departDeptCode;
	}

	public String getArriveDeptCode() {
		return arriveDeptCode;
	}

	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getHandOverType() {
		return handOverType;
	}

	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}

	public Date getBeginHandOverTime() {
		return beginHandOverTime;
	}

	public void setBeginHandOverTime(Date beginHandOverTime) {
		this.beginHandOverTime = beginHandOverTime;
	}

	public Date getEndHandOverTime() {
		return endHandOverTime;
	}

	public void setEndHandOverTime(Date endHandOverTime) {
		this.endHandOverTime = endHandOverTime;
	}

	public String getIsArrived() {
		return isArrived;
	}

	public void setIsArrived(String isArrived) {
		this.isArrived = isArrived;
	}

	
	
}