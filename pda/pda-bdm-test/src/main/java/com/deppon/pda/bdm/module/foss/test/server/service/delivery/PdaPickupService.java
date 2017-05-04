/*package com.deppon.pda.bdm.module.foss.test.server.service.delivery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPickupService;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPriceService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupResultDto;

public class PdaPickupService implements IPdaPickupService{

	@Override
	public List<PadPickupResultDto> getPadPickupList(PadPickupDto pickupDto) {
		List<PadPickupResultDto> dtos= new ArrayList<PadPickupResultDto>();
		
		PadPickupResultDto dto = new PadPickupResultDto();
		dto.setBillingGoodsQty(1);
		dto.setGoodPackage("ddd");
		dto.setGoodSize("2");
		dto.setGoodVolume(new BigDecimal(23));
		dto.setGoodWeight(BigDecimal.valueOf(3223));
		dto.setId("23423423432");
		dtos.add(dto);
		
		return dtos;
	}

	@Override
	public void pdaChangeWaybillState(List<String> waybillNos) {
		// TODO Auto-generated method stub
		
	}

}
*/