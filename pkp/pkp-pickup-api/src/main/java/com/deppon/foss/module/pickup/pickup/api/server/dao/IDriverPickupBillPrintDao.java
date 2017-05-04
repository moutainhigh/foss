package com.deppon.foss.module.pickup.pickup.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pickup.api.shared.dto.DriverPickupBillPrintDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.RtDriverPickupBillPrintDto;

public interface IDriverPickupBillPrintDao {

	List<RtDriverPickupBillPrintDto> queryDriverPickupBillPrint(
			DriverPickupBillPrintDto driverPickupBillPrintDto, int start,
			int limit);

	Long queryDriverPickupBillPrintTotal(
			DriverPickupBillPrintDto driverPickupBillPrintDto);


	List<RtDriverPickupBillPrintDto> queryDriverPickupBillPrintList(
			DriverPickupBillPrintDto dto);

}
