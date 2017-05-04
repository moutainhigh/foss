package com.deppon.foss.module.pickup.pickup.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.module.pickup.pickup.api.shared.dto.DriverPickupBillPrintDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.RtDriverPickupBillPrintDto;

/**
 * 司机接货单号打印Service
 *
 */
public interface IDriverPickupBillPrintService {

	/**
	 * 查询司机接货单号
	 * @param dto
	 * @return
	 */
	List<RtDriverPickupBillPrintDto> queryDriverPickupBillPrint(
			DriverPickupBillPrintDto driverPickupBillPrintDto, int start,
			int limit);
	List<RtDriverPickupBillPrintDto> queryDriverPickupBillPrintNoPage(
			DriverPickupBillPrintDto driverPickupBillPrintDto);


	/**
	 * 查询司机接货单号 Total
	 * @param dto
	 * @return
	 */
	Long queryDriverPickupBillPrintTotal(
			DriverPickupBillPrintDto driverPickupBillPrintDto);


	InputStream queryDriverPickupBillPrint(
			DriverPickupBillPrintDto driverPickupBillPrintDto);

}