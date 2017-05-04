package com.deppon.pda.bdm.module.foss.dprtarr.ws;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDAArriveService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;

public class ArriveImpl implements IPDAArriveService {
	private static final Log LOG = LogFactory.getLog(ArriveImpl.class);
	
	@Override
	public String writeArriveData(PDADepartDto pdaDto) {
		LOG.info("createUsera:"+pdaDto.getCreateUser());
		LOG.info("id:"+pdaDto.getId());
		LOG.info("modifyUser:"+pdaDto.getModifyUser());
		LOG.info("operator:"+pdaDto.getOperator());
		LOG.info("orgCode:"+pdaDto.getOrgCode());
		LOG.info("terminalNo:"+pdaDto.getPdaTerminalNo());
		LOG.info("vehicleNo:"+pdaDto.getVehicleNo());
		LOG.info("createDate:"+pdaDto.getCreateDate());
		LOG.info("modifyDate:"+pdaDto.getModifyDate());
		LOG.info("operatingTime:"+pdaDto.getOperatingTime());
		return "1";
	}

}
