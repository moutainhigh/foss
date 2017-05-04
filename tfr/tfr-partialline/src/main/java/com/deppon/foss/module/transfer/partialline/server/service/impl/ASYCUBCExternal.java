package com.deppon.foss.module.transfer.partialline.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcExternalBillRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcExternalBillResponse;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.partialline.api.server.service.IASYCUBCExternal;
import com.deppon.foss.util.UUIDUtils;

public class ASYCUBCExternal implements IASYCUBCExternal {
	private static Log logger = LogFactory.getLog(ASYCUBCExternal.class);

	@Override
	public CubcExternalBillResponse pushAddExternalBill(CubcExternalBillRequest requestExternalBillDto) {
		String bid = UUIDUtils.getUUID();
		logger.info("start of pushAddExternalBill" + bid);
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode("ESB_FOSS2ESB_FOSS_CUBC_ADDPARTIALLINE");
		accessHeader.setBusinessId("测试" + bid);
		accessHeader.setBusinessDesc1("同步偏线外发单至CUBC");
		accessHeader.setBusinessDesc2(bid);
		accessHeader.setVersion("0.1");
		try {
			com.deppon.cubc.module.foss.shared.domain.RequestExternalBillDto requestExternalBillDto2 = new com.deppon.cubc.module.foss.shared.domain.RequestExternalBillDto();
			String requestExternalBillDto1 = JSONObject.toJSONString(requestExternalBillDto);
			logger.info("pushAddExternalBill>>>>" + requestExternalBillDto1);
			requestExternalBillDto2 = JSONObject.parseObject(requestExternalBillDto1,
					com.deppon.cubc.module.foss.shared.domain.RequestExternalBillDto.class);

			ESBJMSAccessor.asynReqeust(accessHeader, requestExternalBillDto2);
			logger.info("end of pushAddExternalBill");
		} catch (ESBException e) {
			logger.error("esb - 接口异常", e);
			throw new TfrBusinessException("esb - 接口异常", e);
		}
		return null;
	}

}
