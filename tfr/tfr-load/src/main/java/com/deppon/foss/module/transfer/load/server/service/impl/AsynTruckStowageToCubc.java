package com.deppon.foss.module.transfer.load.server.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcVehicleAssembleBillRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcVehicleAssembleBillResponse;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IAsynTruckStowageToCubc;
import com.deppon.foss.util.UUIDUtils;

public class AsynTruckStowageToCubc implements IAsynTruckStowageToCubc {
	private static final Logger logger = LogManager.getLogger(AsynTruckStowageToCubc.class);

	@Override
	public CubcVehicleAssembleBillResponse pushAddTruckStowage(
			CubcVehicleAssembleBillRequest cubcVehicleAssembleBillRequest) {
		AccessHeader accessHeader = new AccessHeader();
		String bid = UUIDUtils.getUUID();
		logger.info("start of pushAddTruckStowage" + bid);

		accessHeader.setEsbServiceCode("ESB_FOSS2ESB_FOSS_CUBC_ADDSTOWAGE");
		accessHeader.setBusinessId("ESB_FOSS2ESB_FOSS_CUBC_ADDSTOWAGE" + bid);
		accessHeader.setBusinessDesc1("外请车配载单新增调用CUBC");
		accessHeader.setBusinessDesc2(bid);
		accessHeader.setVersion("0.1");
		try {
			com.deppon.cubc.module.foss.shared.domain.CubcVehicleAssembleBillRequest cubcVehicleAssembleBillRequest2 = new com.deppon.cubc.module.foss.shared.domain.CubcVehicleAssembleBillRequest();
			String cubcVehicleAssembleBillDto1 = JSONObject.toJSONString(cubcVehicleAssembleBillRequest);
			logger.info("pushAddTruckStowage>>>>" + cubcVehicleAssembleBillDto1);
			cubcVehicleAssembleBillRequest2 = JSONObject.parseObject(cubcVehicleAssembleBillDto1,
					com.deppon.cubc.module.foss.shared.domain.CubcVehicleAssembleBillRequest.class);
			ESBJMSAccessor.asynReqeust(accessHeader, cubcVehicleAssembleBillRequest2);
			logger.info("end of pushAddTruckStowage");
		} catch (ESBException e) {
			logger.error("esb - 接口异常", e);
			throw new TfrBusinessException("esb - 接口异常", e);
		}
		return null;
	}

}
