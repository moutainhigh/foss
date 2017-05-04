package com.deppon.foss.module.pickup.pickup.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pickup.api.shared.dto.DriverPickupBillPrintDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.RtDriverPickupBillPrintDto;


/**
 * 接送货司机接货单号打印Vo
 *
 */
public class DriverPickupBillPrintVo implements Serializable{

	//序列化版本号
	private static final long serialVersionUID = 1L;
		
	
	 //查询条件DTO
	private DriverPickupBillPrintDto driverPickupBillPrintDto = new DriverPickupBillPrintDto();
	
	//返回查询的结果集合
	private List<RtDriverPickupBillPrintDto> rtDriverPickupBillPrintDtoList;

	public DriverPickupBillPrintDto getDriverPickupBillPrintDto() {
		return driverPickupBillPrintDto;
	}

	public void setDriverPickupBillPrintDto(
			DriverPickupBillPrintDto driverPickupBillPrintDto) {
		this.driverPickupBillPrintDto = driverPickupBillPrintDto;
	}

	public List<RtDriverPickupBillPrintDto> getRtDriverPickupBillPrintDtoList() {
		return rtDriverPickupBillPrintDtoList;
	}

	public void setRtDriverPickupBillPrintDtoList(
			List<RtDriverPickupBillPrintDto> rtDriverPickupBillPrintDtoList) {
		this.rtDriverPickupBillPrintDtoList = rtDriverPickupBillPrintDtoList;
	}

	
	
	
}