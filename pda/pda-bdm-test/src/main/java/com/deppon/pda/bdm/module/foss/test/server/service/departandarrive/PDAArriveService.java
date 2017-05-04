package com.deppon.pda.bdm.module.foss.test.server.service.departandarrive;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDAArriveService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;

public class PDAArriveService implements IPDAArriveService {
	private static final Log LOG = LogFactory.getLog(PDAArriveService.class);
	
	@Override
	public String writeArriveData(PDADepartDto pdaDto) {
		LOG.info("创建人："+pdaDto.getCreateUser());
		LOG.info("车牌号："+pdaDto.getVehicleNo());
		LOG.info("封签破损数量："+pdaDto.getDamagedSealCount());
		LOG.info("操作人："+pdaDto.getOperator());
		LOG.info("所在外场部门："+pdaDto.getOrgCode());
		LOG.info("PDA设备号："+pdaDto.getPdaTerminalNo());
		LOG.info("封签状态："+pdaDto.getSealStatus());
		LOG.info("创建日期："+pdaDto.getCreateDate());
		return "success";
	}

}
