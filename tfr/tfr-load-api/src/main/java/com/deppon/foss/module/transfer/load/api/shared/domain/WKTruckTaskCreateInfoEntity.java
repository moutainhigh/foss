package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;

/**
 * 
 * 类描述：	发车确认/到达确认/取消发车操作记录保存到：先查询车辆任务明细具体信息
 *          t_opt_truck_arrive、t_opt_truck_depart表中去
 * 创建人：	106162-FOSS-LIPING
 * 创建时间：	2016-07-204  上午10:13:39
 * 
 */
public class WKTruckTaskCreateInfoEntity implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 出发放行ID
	 */
	private String truckDepartId;
	/**
	 * 到达ID
	 */
	private String truckArriveId;

	/**
	 * 车辆任务ID
	 */
	private String truckTaskId;
	
    /**
     * toString()方法
     */
	@Override
	public String toString() {
		return "WKTruckTaskCreateInfoEntity [truckDepartId=" + truckDepartId
				+ ", truckArriveId=" + truckArriveId + ", truckTaskId="
				+ truckTaskId + "]";
	}
	
	/**
	 * set\get 方法
	 * @return
	 */
	public String getTruckDepartId() {
		return truckDepartId;
	}
	
	public void setTruckDepartId(String truckDepartId) {
		this.truckDepartId = truckDepartId;
	}
	public String getTruckArriveId() {
		return truckArriveId;
	}
	public void setTruckArriveId(String truckArriveId) {
		this.truckArriveId = truckArriveId;
	}

	public String getTruckTaskId() {
		return truckTaskId;
	}

	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}
	
}
