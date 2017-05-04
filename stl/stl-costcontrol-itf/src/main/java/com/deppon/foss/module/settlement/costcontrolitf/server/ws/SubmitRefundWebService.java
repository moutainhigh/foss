package com.deppon.foss.module.settlement.costcontrolitf.server.ws;

import javax.xml.ws.Holder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.inteface.domain.stl.SubmitRefundRequest;
import com.deppon.foss.inteface.domain.stl.SubmitRefundResponse;
import com.deppon.foss.module.settlement.pay.api.server.service.ISubmitRefundConcreteService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.SubmitRefundDto;
import com.deppon.foss.submitrefund.ISubmitRefundService;
import com.deppon.foss.util.define.FossConstants;

/**
 * 转报销接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-10 下午3:12:09,content/p>
 * @author 105762
 * @date 2014-7-10 下午3:12:09
 * @since 1.6
 * @version 1.0
 */
public class SubmitRefundWebService implements ISubmitRefundService {
	private ISubmitRefundConcreteService submitRefundConcreteService;
	
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(SubmitRefundWebService.class);


	/** 
	 * <p>转报销接口</p> 
	 * @author 105762
	 * @date 2014-7-10 下午4:04:51
	 * @param esbHeader
	 * @param submitRefundRequest
	 * @return
	 * @see com.deppon.foss.submitrefund.ISubmitRefundService#submitRefund(javax.xml.ws.Holder, com.deppon.foss.inteface.domain.stl.SubmitRefundRequest)
	 */
	@Override
	@Transactional
	public SubmitRefundResponse submitRefund(Holder<ESBHeader> esbHeader, SubmitRefundRequest submitRefundRequest)
			throws com.deppon.foss.submitrefund.CommonException {
		logger.info("转报销接口开始 :"+ submitRefundRequest.getWorkflowNo());
		SubmitRefundDto dto = new SubmitRefundDto();
		dto.setWorkflowNo(submitRefundRequest.getWorkflowNo());
		dto.setIsSuccess(submitRefundRequest.getIsSuccess().equals("1")?FossConstants.YES:FossConstants.NO);
		SubmitRefundResponse response = new SubmitRefundResponse();

		if (submitRefundConcreteService.submitRefund(dto)) {
			response.setIsSuccess("1");
		} else {
			response.setIsSuccess("0");
		}
		logger.info("转报销接口结束");
		return response;
	}

	/**
	 * @param submitRefundConcreteService the submitRefundConcreteService to set
	 */
	public void setSubmitRefundConcreteService(ISubmitRefundConcreteService submitRefundConcreteService) {
		this.submitRefundConcreteService = submitRefundConcreteService;
	}
	
}
