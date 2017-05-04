package com.deppon.pda.bdm.module.foss.dprtarr.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDADepartService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartResultDTO;

public class DepartImpl implements IPDADepartService {

	private static final Log LOG = LogFactory.getLog(DepartImpl.class); 
	
	@Override
	public PDADepartResultDTO writeDepartData(PDADepartDto pdaDto) {
		LOG.info("车牌号:"+pdaDto.getVehicleNo());
		LOG.info("操作时间:"+pdaDto.getOperatingTime());
		LOG.info("操作人:"+pdaDto.getOperator());
		LOG.info("放行条码:"+pdaDto.getOrgCode());
		LOG.info("PDA设备号:"+pdaDto.getPdaTerminalNo());
		
		PDADepartResultDTO departResultDTO = new PDADepartResultDTO();
		departResultDTO.setCreateDate(new Date());
		departResultDTO.setCreateUser("xujun");
		departResultDTO.setDepartItems("");
		departResultDTO.setDepartTime(new Date());
		departResultDTO.setDepartType("1");
		departResultDTO.setDepartUser("076544");
		departResultDTO.setDriverName("xj");
		departResultDTO.setDriverPhone("13818524587");
		List<String> hands = new ArrayList<String>();
		hands.add("123456789");
		hands.add("123456788");
		departResultDTO.setHandoverbills(hands);
		departResultDTO.setId("abcedgdafsdf=");
		departResultDTO.setVehicleStatus("1");
		departResultDTO.setVehicleNo("鄂AH2081");
		departResultDTO.setStatus("1");
		List<String> sealNos = new ArrayList<String>();
		sealNos.add("1111111");
		sealNos.add("1111112");
		sealNos.add("1111113");
		departResultDTO.setSealNos(sealNos);
		departResultDTO.setModifyUser("076544");
		departResultDTO.setModifyDate(new Date());
		return departResultDTO;
	}

}
