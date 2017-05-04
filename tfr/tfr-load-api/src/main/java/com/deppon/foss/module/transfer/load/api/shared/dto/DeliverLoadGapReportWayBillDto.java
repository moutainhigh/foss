/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.dto
 * FILE    NAME: DeliverLoadGapReportWayBillDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.dto;

import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;

/**
 * 导出派送装车差异 报告明细
 * @author dp-duyi
 * @date 2013-7-12 下午12:43:45
 */
public class DeliverLoadGapReportWayBillDto extends DeliverLoadGapReportWayBillEntity{

	private static final long serialVersionUID = -7647910443870877793L;
	/**开单目的站*/
	private String destination;
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
}
