package com.deppon.foss.module.base.crm.itf.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.pojo.transformer.json.OneticketornotRequestTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOneticketornotService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.ws.syncdata.CommonException;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.OneticketornotSyncRequest;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.OneticketornotSyncResponse;
/**
 * 从CRM系统过来的是否一票多件信息
 * 交互方式：异步
 * @author 273311
 * @date   2015-12-15
 */
public class OneticketornotProcessor implements IProcess {
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(OneticketornotProcessor.class);
    
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
    /**
     * 设置业务互斥锁服务.
     * @param businessLockService the businessLockService to set
     */
    public void setBusinessLockService(IBusinessLockService businessLockService) {
        this.businessLockService = businessLockService;
    }

    /**
     * 对CRM是否一票多件信息进行操作的Service接口
     */
    private IOneticketornotService oneticketornotService;
    
	public void setOneticketornotService(
			IOneticketornotService oneticketornotService) {
		this.oneticketornotService = oneticketornotService;
	}

	/**
     * 提供给CRM的接口，用来传递是否一票多件信息
     * 异步交互
     * @author 273311
     * @date   2015-12-15 
    */
	@Override
	public Object process(Object req) throws ESBBusinessException {

		LOGGER.info(" ***************************** 同步相关信息开始 ***************************** ");
		//接收到的请求消息类
		OneticketornotSyncRequest request = (OneticketornotSyncRequest) req;
		//响应消息类
		OneticketornotSyncResponse response = new OneticketornotSyncResponse();

		LOGGER.info(new OneticketornotRequestTrans().fromMessage(request));//请求转换类
		
		if(null != request){
		    // 业务锁
		    MutexElement mutex = new MutexElement(String.valueOf(request.getCode()), "ONETICKETORNOTINFO_CODE",
			    MutexElementType.ONETICKETORNOTINFO_CODE);
		    LOGGER.info("开始加锁：" + mutex.getBusinessNo());
		    boolean result = businessLockService.lock(mutex,
			    NumberConstants.ZERO);
		    if (result) {
			LOGGER.info("成功加锁：" + mutex.getBusinessNo());
		    } else {
			LOGGER.info("失败加锁：" + mutex.getBusinessNo());
			response.setIfSuccess("0");
			response.setErrorMsg("加锁失败");
			return response;
		    }
		    try {
				response = oneticketornotService.syncOneticketornotInfo(request);
			} catch (CommonException e) {
				response.setErrorMsg(e.getMessage());
				response.setIfSuccess("0");
				LOGGER.info(e.getMessage());
				LOGGER.info(e.getStackTrace().toString());
			}
		    LOGGER.info("开始解锁：" + mutex.getBusinessNo());
		    // 解业务锁
		    businessLockService.unlock(mutex);
		    LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		}else{
			response.setErrorMsg("发送的请求对象为空");
			response.setIfSuccess("0");
			return response;
		}
		return response;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("ESB处理错误");
		return null;
	
	}
}