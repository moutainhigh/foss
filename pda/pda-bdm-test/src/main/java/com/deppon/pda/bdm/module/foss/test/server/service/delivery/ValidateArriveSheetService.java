package com.deppon.pda.bdm.module.foss.test.server.service.delivery;

import java.math.BigDecimal;

import com.deppon.foss.module.pickup.pda.api.server.service.IValidateArriveSheetService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.ValidateArriveSheetDto;

public class ValidateArriveSheetService implements IValidateArriveSheetService {

	public ValidateArriveSheetDto validateArriveSheet(String arriveSheetNo) {
		ValidateArriveSheetDto arriveSheetDto = new ValidateArriveSheetDto();
		arriveSheetDto.setArriveSheetGoodsQty(4);
		arriveSheetDto.setCustomerPickupOrgCode("上海派送部");
		arriveSheetDto.setGoodsVolumeTotal(new BigDecimal(10));
		arriveSheetDto.setProductCode("精准卡航");
		arriveSheetDto.setReceiveMethod("自提");
		arriveSheetDto.setWaybillNo("12345678");
		arriveSheetDto.setComplainStatus("Y");
		return arriveSheetDto;
	}

	@Override
	public ValidateArriveSheetDto validateArriveSheet(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
