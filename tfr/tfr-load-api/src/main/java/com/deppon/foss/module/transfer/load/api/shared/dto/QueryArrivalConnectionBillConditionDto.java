package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/** 
 * @className: QueryArrivalConnectionBillDto
 * @author: hongwy foss 218427
 * @description: 查询交接单，交接单信息dto，用于接收接驳交接单查询条件参数
 * @date: 2015-10-27 下午17:22:27
 */
public class QueryArrivalConnectionBillConditionDto   implements Serializable {
	/**序列号**/
	private static final long serialVersionUID = -2066355499072497176L;
	
	/**交接单号 **/
	private String connectionBillNo;
	
	/**到达接驳点编码 **/
	private String arriveDeptCode;
	
	/**车牌号 **/
	private String vehicleNo;
	
	/**交接类型 **/
	private String handOverType;
	
	/**出发时间 **/
	private Date departTime;
	
	/**到达时间 **/
	private Date arriveTime;
	
	/**是否已到达 **/
	private String isArrived;

	public String getConnectionBillNo() {
		return connectionBillNo;
	}

	public void setConnectionBillNo(String connectionBillNo) {
		this.connectionBillNo = connectionBillNo;
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

	public Date getDepartTime() {
		return departTime;
	}

	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getIsArrived() {
		return isArrived;
	}

	public void setIsArrived(String isArrived) {
		this.isArrived = isArrived;
	}

	
	
}