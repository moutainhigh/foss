package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;

@SuppressWarnings("serial")
public class WaybillLogEntityQueryDto extends WaybillLogEntity{
	/**
	 * 运单集合
	 */
	private List<String> waybillNos ;
	
	/**
	 * 查询开始时间
	 */
	private Date startTime;
		
	/**
	 * 查询结束时间
	 */
	private Date endTime;

	public List<String> getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
