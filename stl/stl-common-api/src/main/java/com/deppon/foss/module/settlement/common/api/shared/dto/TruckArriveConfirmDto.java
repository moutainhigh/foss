package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.module.settlement.common.api.shared.domain.TruckArriveConfirmEntity;
/**
 * 车辆到达确认传递参数
 * @author 073615
 *
 */
public class TruckArriveConfirmDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 配载单单号
	 */
	private String handleNo ;
	/**
	 * 确认到达/反到达时间
	 */
	private Date confirmTime;
	/**
	 * 确认类型 到达/反到达
	 */
	private String confirmType;
	
	/**
	 *车辆到达/反到达实体 
	 */
	private TruckArriveConfirmEntity truckArriveConfirmEntity;
	
	
	public TruckArriveConfirmEntity getTruckArriveConfirmEntity() {
		return truckArriveConfirmEntity;
	}
	public void setTruckArriveConfirmEntity(
			TruckArriveConfirmEntity truckArriveConfirmEntity) {
		this.truckArriveConfirmEntity = truckArriveConfirmEntity;
	}
	public String getHandleNo() {
		return handleNo;
	}
	public void setHandleNo(String handleNo) {
		this.handleNo = handleNo;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getConfirmType() {
		return confirmType;
	}
	public void setConfirmType(String confirmType) {
		this.confirmType = confirmType;
	}
	
	

}
