package com.deppon.foss.module.pkp.pda.job.server.callBack;


import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.core.process.ErrorResponse;
import com.deppon.dpap.esb.mqc.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.foss2cubc.WaybillInfoToCUBCResponse;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISyncWaybillLogService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;

public class SynWaybillInfoToCUBCCallBackProcessor implements
		ICallBackProcess {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(SynWaybillInfoToCUBCCallBackProcessor.class);
    
    private ObjectMapper mapper = new ObjectMapper();    
    /**
	 * 日志service
	 */
	private ISyncWaybillLogService syncWaybillLogService;

	/**
	 * @param syncWaybillLogService the syncWaybillLogService to set
	 */
	public void setSyncWaybillLogService(
			ISyncWaybillLogService syncWaybillLogService) {
		this.syncWaybillLogService = syncWaybillLogService;
	}

	@Override
	public void callback(Object response) throws ESBException {
		try {
			if (null == response) {
				return;			
			}
			WaybillInfoToCUBCResponse waybillInfoResponse = (WaybillInfoToCUBCResponse) response;
			WaybillLogEntity waybillLogEntity = new WaybillLogEntity();
			waybillLogEntity.setId(waybillInfoResponse.getId());
			if(waybillInfoResponse.isResult()){
				waybillLogEntity.setStatu(WaybillConstants.SUCCESS);
			}else{
				waybillLogEntity.setStatu(WaybillConstants.FAIL);
			}
			waybillLogEntity.setResponseContent(mapper.writeValueAsString(response));
			syncWaybillLogService.updateById(waybillLogEntity);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("cubc回调处理异常"+e.getMessage());
		}
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		ErrorResponse info = (ErrorResponse)errorResponse;
		StringBuilder nb =new StringBuilder();
		nb.append(info.getBusinessId()).append(",描述1：")
			.append(info.getBusinessDesc1()).append(",描述2：")
			.append(info.getBusinessDesc2()).append(",描述3：")
			.append(info.getBusinessDesc3());
		LOGGER.error("ESB处理错误", nb.toString());
	}

}
