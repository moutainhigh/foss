package com.deppon.foss.module.base.crm.itf.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.crm.XcpSyncRequest;
import com.deppon.esb.inteface.domain.crm.XcpSyncResponse;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.INewProductService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;

/**
 * 新产品客户协议服务端
 * @author 198771
 *
 */
public class NewProductResultProcessor implements IProcess{
	
	/*
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(NewProductResultProcessor.class);
	
	private INewProductService newProductService;
	
	/**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;

	public void setNewProductService(INewProductService newProductService) {
		this.newProductService = newProductService;
	}
	
	public void setBusinessLockService(IBusinessLockService businessLockService) {
        this.businessLockService = businessLockService;
    }

	@Override
	public Object process(Object req) throws ESBBusinessException {
		//新产品客户请求消息
		XcpSyncRequest request = (XcpSyncRequest)req;
		//新产品客户响应消息
		XcpSyncResponse response = new XcpSyncResponse();
		if(request!=null){
			//业务锁
			MutexElement mutex = new MutexElement(String.valueOf(request.getCustomerCode()), "NEW_PRODUCT_CUSTOMER",
			    MutexElementType.NEW_PRODUCT_CUSTOMER);
		    LOGGER.info("开始加锁：" + mutex.getBusinessNo());
		    boolean result = businessLockService.lock(mutex,
			    NumberConstants.ZERO);
		    if (result) {
		    	LOGGER.info("成功加锁：" + mutex.getBusinessNo());
		    } else {
		    	LOGGER.info("失败加锁：" + mutex.getBusinessNo());
		    	response.setIfSuccess("0");
		    	response.setErrorMsg("业务锁添加失败");
		    	return response;
		    }
		    response = newProductService.addNewProductEntity(request);
		}
		return response;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("ESB处理错误");
		return null;
	}

}
