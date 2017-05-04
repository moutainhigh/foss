package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.inteface.domain.cubc.revisebill.AirRevisebillToCubcRequest;
import com.deppon.esb.inteface.domain.cubc.transferwaybill.AirTransferWaybillToCubcRequest;
import com.deppon.esb.inteface.domain.cubc.waybill.AirWaybillToCubcRequest;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillToCubcService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.util.UUIDUtils;

public class AirWaybillToCubcService implements IAirWaybillToCubcService {

	private static Log logger = LogFactory.getLog(AirWaybillToCubcService.class);

	/**
	 * 同步航空正单信息至CUBC
	 * 
	 * @param requestStr
	 */
	@Override
	public void pushAddAirWaybill(String requestStr) {
		String uuid = UUIDUtils.getUUID();
		logger.info("pushAddAirWaybill开始"+uuid);
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode("ESB_FOSS2ESB_FOSS_CUBC_ADDAIRWAYBILL");
		accessHeader.setBusinessId("316759著：正式数据" + uuid);
		accessHeader.setBusinessDesc1("同步航空正单信息至CUBC");
		accessHeader.setBusinessDesc2(uuid);
		accessHeader.setVersion("0.1");
		try {

			AirWaybillToCubcRequest request = new AirWaybillToCubcRequest();
			request = JSONObject.parseObject(requestStr, AirWaybillToCubcRequest.class);
			logger.info("pushAddAirWaybill " + requestStr);
			ESBJMSAccessor.asynReqeust(accessHeader, request);
		} catch (ESBException e) {
			logger.info("异常类型：" + e.getExceptionType());
			logger.info("异常编码" + e.getExceptionCode());
			throw new TfrBusinessException("同步航空正单信息至CUBC异常", e.getMessage());
		}
	}

	/**
	 * 同步合大票信息至CUBC
	 * 
	 * @param requestStr
	 */
	@Override
	public void pushAddAirRevisebill(String requestStr) {
		String uuid = UUIDUtils.getUUID();
		logger.info("pushAddAirRevisebill开始" + uuid);
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode("ESB_FOSS2ESB_FOSS_CUBC_ADDAIRPICKBILL");
		accessHeader.setBusinessId("316759著：正式数据" + uuid);
		accessHeader.setBusinessDesc1("同步合大票信息至CUBC");
		accessHeader.setBusinessDesc2(uuid);
		accessHeader.setVersion("0.1");
		try {

			AirRevisebillToCubcRequest request = new AirRevisebillToCubcRequest();
			request = JSONObject.parseObject(requestStr, AirRevisebillToCubcRequest.class);
			logger.info("pushAddAirRevisebill " + requestStr);
			ESBJMSAccessor.asynReqeust(accessHeader, request);
		} catch (ESBException e) {
			logger.info("异常类型：" + e.getExceptionType());
			logger.info("异常编码" + e.getExceptionCode());
			throw new TfrBusinessException("同步合大票信息信息至CUBC异常", e.getMessage());
		}
	}

	/**
	 * 同步中转提货清单至CUBC
	 * 
	 * @param requestStr
	 */
	@Override
	public void pushAddTransferWaybill(String requestStr) {
		String uuid = UUIDUtils.getUUID();
		logger.info("pushAddTransferWaybill开始" + uuid);
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode("ESB_FOSS2ESB_FOSS_CUBC_ADDAIRPICKUP");
		accessHeader.setBusinessId("316759著：正式数据" + uuid);
		accessHeader.setBusinessDesc1("同步中转提货清单至CUBC");
		accessHeader.setBusinessDesc2(uuid);
		accessHeader.setVersion("0.1");
		try {

			AirTransferWaybillToCubcRequest request = new AirTransferWaybillToCubcRequest();
			request = JSONObject.parseObject(requestStr, AirTransferWaybillToCubcRequest.class);
			logger.info("pushAddTransferWaybill " + requestStr);
			ESBJMSAccessor.asynReqeust(accessHeader, request);
		} catch (ESBException e) {
			logger.info("异常类型：" + e.getExceptionType());
			logger.info("异常编码" + e.getExceptionCode());
			throw new TfrBusinessException("同步中转提货清单信息信息至CUBC异常", e.getMessage());
		}
	}

}
