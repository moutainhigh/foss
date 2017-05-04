package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 车辆任务明细单据信息表
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:163580,date:2013-12-6 上午10:58:54,content:车辆任务明细单据信息表 </p>
 * @author 163580
 * @date 2013-12-6 上午10:58:54
 * @since
 * @version
 */
public class TruckTaskBillInfoDto implements Serializable {
	private static final long serialVersionUID = 2216526876507097555L;

	/**
	 * 出发时间
	 */
	private Date actualDepartTime;
	
	/**
	 * 到达时间
	 */
	private Date actualArriveTime;
	
	/**
	 * 配载单号
	 */
	private String billNo;
	
	/**
	 * 主任务ID
	 */
	private String truckTaskId;

	/**
	 * @return set the actualDepartTime
	 */
	public Date getActualDepartTime() {
		return actualDepartTime;
	}

	/**
	 * @param actualDepartTime the actualDepartTime to set
	 */
	public void setActualDepartTime(Date actualDepartTime) {
		this.actualDepartTime = actualDepartTime;
	}

	/**
	 * @return set the actualArriveTime
	 */
	public Date getActualArriveTime() {
		return actualArriveTime;
	}

	/**
	 * @param actualArriveTime the actualArriveTime to set
	 */
	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}

	/**
	 * @return set the billNo
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * @param billNo the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * @return set the truckTaskId
	 */
	public String getTruckTaskId() {
		return truckTaskId;
	}

	/**
	 * @param truckTaskId the truckTaskId to set
	 */
	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}

}
