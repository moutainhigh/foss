package com.deppon.foss.module.transfer.packaging.server.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.tool.domain.AccessHeader;
import com.deppon.dpap.esb.mqc.tool.util.ESBJMSAccessor;
import com.deppon.esb.inteface.domain.cubc.packing.PackingToCubcEntity;
import com.deppon.esb.inteface.domain.cubc.packing.PackingToCubcRequest;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackagePriceToCubcService;
import com.deppon.foss.util.UUIDUtils;

public class PackagePriceToCubcService implements IPackagePriceToCubcService {

	private static Log logger = LogFactory
			.getLog(PackagePriceToCubcService.class);

	/**
	 * 同步打木架信息至CUBC
	 * 
	 * @param packingToCubcEntitys
	 */
	@Override
	public void pushAddPackingRecAndPay(List<com.deppon.foss.module.transfer.packaging.api.shared.domain.PackingToCubcEntity> packingToCubcEntitys) {
		AccessHeader accessHeader = new AccessHeader();
		String desc = UUIDUtils.getUUID();
		logger.info("pushAddPackingRecAndPay>>>" + desc);
		accessHeader.setEsbServiceCode("ESB_FOSS2ESB_FOSS_CUBC_ADDPACKING");
		accessHeader.setBusinessId("pushAddPackingRecAndPay>>>" + desc);
		accessHeader.setBusinessDesc1("同步打木架信息至CUBC");
		accessHeader.setBusinessDesc2(desc);
		accessHeader.setVersion("0.1");
		try {

			PackingToCubcRequest request = new PackingToCubcRequest();
			for (int i = 0; i < packingToCubcEntitys.size(); i++) {
				String requestStr = JSONObject.toJSONString(packingToCubcEntitys.get(i));
				logger.info("packingToCubcEntitys " + i + "==" +requestStr);
				PackingToCubcEntity waybillToCubcEntity = JSONObject.parseObject(requestStr, PackingToCubcEntity.class);
				request.getPackingToCubcEntityList().add(waybillToCubcEntity);
			}
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			logger.info("endof pushAddPackingRecAndPay");
		} catch (ESBException e) {
			logger.info("异常类型：" + e.getExceptionType());
			logger.info("异常编码" + e.getExceptionCode());
			throw new BusinessException("esb - " + e.getMessage(), e);
		}

	}

}
