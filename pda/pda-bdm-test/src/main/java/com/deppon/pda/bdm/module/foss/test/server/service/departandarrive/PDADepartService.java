package com.deppon.pda.bdm.module.foss.test.server.service.departandarrive;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDADepartService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartResultDTO;

public class PDADepartService implements IPDADepartService {

	@Override
	public PDADepartResultDTO writeDepartData(PDADepartDto pdaDto) {
		PDADepartResultDTO departResultDTO = new PDADepartResultDTO();
		departResultDTO.setCreateDate(new Date());
		departResultDTO.setCreateUser("076544");
		departResultDTO.setDepartItems("放行事项");
		departResultDTO.setDepartTime(new Date());
		departResultDTO.setDepartType("放行类型");
		departResultDTO.setDepartUser("076544");
		departResultDTO.setDriverName("xujun");
		departResultDTO.setDriverPhone("123456789");
		List<String> handoverbills = new ArrayList<String>();
		handoverbills.add("11111111");
		handoverbills.add("222222");
		departResultDTO.setHandoverbills(handoverbills);
		departResultDTO.setId("123455");
		departResultDTO.setModifyDate(new Date());
		departResultDTO.setModifyUser("076544");
		List<String> sealNos = new ArrayList<String>();
		sealNos.add("1111");
		sealNos.add("2222");
		departResultDTO.setSealNos(sealNos);
		departResultDTO.setStatus("1");
		departResultDTO.setVehicleNo("鄂AH2081");
		departResultDTO.setVehicleStatus("破损");
		return departResultDTO;
	}

}
