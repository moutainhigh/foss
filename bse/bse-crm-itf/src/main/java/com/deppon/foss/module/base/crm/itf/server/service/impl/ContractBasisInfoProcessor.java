package com.deppon.foss.module.base.crm.itf.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.ptp.ContractBasisInfo;
import com.deppon.esb.pojo.transformer.json.ContractBasisInfoRequestTrans;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncContractBasisInfoService;


/**
 * 
 * 同步合同基础信息接口
 * @author 308861 
 * @date 2016-8-15 上午11:07:34
 */
public class ContractBasisInfoProcessor implements IProcess{
	
	/**
	 * 日志类
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContractBasisInfoProcessor.class);
	
	/**
	 * 同步"合同基础信息"接口结果操作Service
	 */
	@Autowired
	private ISyncContractBasisInfoService syncContractBasisInfoService;


	/**
	 * 业务处理
	 * @author 308861
	 */
	@Override
	public Object process(Object req) throws ESBBusinessException {
		//声明对象
		ContractBasisInfo contractRequest=(ContractBasisInfo)req;
		LOGGER.info("开始调用  同步合同基本信息： \n"
				+ new ContractBasisInfoRequestTrans()
				.fromMessage(contractRequest));
		//操作逻辑
		syncContractBasisInfoService.operation(contractRequest);
		return null;
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
