/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.settlement.costcontrolitf.server.ws;

import javax.xml.ws.Holder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.inteface.domain.stl.WithholdingRequest;
import com.deppon.foss.inteface.domain.stl.WithholdingResponse;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IWithholdingEntityService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.WithholdingDto;
import com.deppon.foss.withholdingservice.CommonException;
import com.deppon.foss.withholdingservice.IWithholdingService;

/**
 * 反写预提状态到预提单接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-10 下午3:12:09,content:</p>
 * @author 105762
 * @date 2014-7-10 下午3:12:09
 * @since 1.6
 * @version 1.0
 */
public class WithholdingWebService implements IWithholdingService {
	private IWithholdingEntityService withholdingEntityService;
	
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(WithholdingWebService.class);
	/** 
	 * <p>反写预提状态到预提单</p> 
	 * @author 105762
	 * @date 2014-7-10 下午3:22:59
	 * @param esbHeader
	 * @param parameters
	 * @return
	 * @throws CommonException 
	 * @see com.deppon.foss.withholdingservice.IWithholdingService#rewriteWithholdingState(javax.xml.ws.Holder, com.deppon.foss.inteface.domain.stl.WithholdingRequest)
	 */
	@Override
	@Transactional(rollbackFor=CommonException.class)
	public WithholdingResponse rewriteWithholdingState(Holder<ESBHeader> esbHeader, WithholdingRequest parameters) throws CommonException {
		logger.info("预提接口开始 :"+ parameters.getWorkflowNo());
		WithholdingDto dto = new WithholdingDto();
		dto.setWorkflowNo(parameters.getWorkflowNo());

		if ("1".equals(parameters.getIsSuccess())) {
			dto.setSuccess(true);
		} else {
			dto.setSuccess(false);
		}
		dto.setReason(parameters.getReason());

		WithholdingResponse r = new WithholdingResponse();
		r.setIsSuccess("1");

		// 返回 成功 1 失败 0
		try {
			withholdingEntityService.rewriteWithholdingState(dto);
		} catch (SettlementException e) {
			throw new CommonException(e.getErrorCode());
		}
		logger.info("预提接口结束 ");
		return r;
	}
	
	/**
	 * @param withholdingConcreteService the withholdingConcreteService to set
	 */
	public IWithholdingEntityService getWithholdingEntityService() {
		return withholdingEntityService;
	}

	public void setWithholdingEntityService(
			IWithholdingEntityService withholdingEntityService) {
		this.withholdingEntityService = withholdingEntityService;
	}

}
