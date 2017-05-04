package com.deppon.foss.module.base.crm.itf.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.crm.FailTaxpayerInfo;
import com.deppon.esb.inteface.domain.crm.GeneralTaxpayerInfo;
import com.deppon.esb.pojo.transformer.json.SyncGeneralTaxpayerInfoRequestTrans;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgGeneralTaxpayerInfoService;


/**
 * 同步一般纳税人信息接口
 * @author 308861
 *
 */
public class GeneralTaxPayerInfoProcessor implements IProcess{
	
	/**
	 * 日志类
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GeneralTaxPayerInfoProcessor.class);
	
	/**
	 * 同步"一般纳税人信息"接口结果操作Service
	 */
	@Autowired
	private IOrgGeneralTaxpayerInfoService orgGeneralTaxpayerInfoService;
	
	/**
	 * 业务处理
	 * @author 308861
	 */
	@Override
	public Object process(Object req) throws ESBBusinessException {
		//声明GeneralTaxpayerInfo对象
		GeneralTaxpayerInfo taxpayerRequest=(GeneralTaxpayerInfo) req;
		LOGGER.info("开始调用  同步一般纳税人信息： \n"
				+ new SyncGeneralTaxpayerInfoRequestTrans()
				.fromMessage(taxpayerRequest));
		//操作逻辑
		FailTaxpayerInfo taxpayerResponse=orgGeneralTaxpayerInfoService.operation(taxpayerRequest);
		return taxpayerResponse;
	}

	/**
	 * 异常处理
	 * 
	 * @author 308861
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("ESB处理错误");                                                                                    
		return req;
	}

}
