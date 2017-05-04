package com.deppon.foss.module.base.crm.itf.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.crm.MarketingActivityRequest;
import com.deppon.esb.inteface.domain.crm.MarketingActivityResponse;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncMarkActivitiesFromCrmService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.ws.syncdata.CommonException;


/**
 * 接收从CRM系统过来的市场活动信息，主要包括活动主信息，活动开展部门信息，活动线路信息，活动
 * 价格折扣信息
 * 交互方式：异步
 *
 * @author 078816
 * @date   2014-04-09
 *
 */
public class MarkActivitiesResultProcessor implements IProcess {
	
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(MarkActivitiesResultProcessor.class);
    
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
    /**
     * 设置 业务互斥锁服务.
     *
     * @param businessLockService the businessLockService to set
     */
    public void setBusinessLockService(IBusinessLockService businessLockService) {
        this.businessLockService = businessLockService;
    }
    
    private ISyncMarkActivitiesFromCrmService syncMarkActivitiesFromCrmService;
    
    public void setSyncMarkActivitiesFromCrmService(
    		ISyncMarkActivitiesFromCrmService syncMarkActivitiesFromCrmService) {
    	this.syncMarkActivitiesFromCrmService = syncMarkActivitiesFromCrmService;
    }
    /**
     * 提供给CRM的接口，用来传递市场营销活动的相关数据信息
     * 异步交互
     *
     * auther:wangpeng_078816
     * date:2014-4-14
     *
     */
	@Override
	public Object process(Object req) throws ESBBusinessException {


		//市场活动类接收到的请求消息类
		MarketingActivityRequest request = (MarketingActivityRequest) req;
		//市场活动类响应消息类
		MarketingActivityResponse response = new MarketingActivityResponse();

		if(null != request){
		    // 业务锁
		    MutexElement mutex = new MutexElement(String.valueOf(request.getFid()), "MARKACTIVITY_CRMID",
			    MutexElementType.MARKACTIVITY_CRMID);
		    LOGGER.info("开始加锁：" + mutex.getBusinessNo());
		    boolean result = businessLockService.lock(mutex,
			    NumberConstants.ZERO);
		    if (result) {
			LOGGER.info("成功加锁：" + mutex.getBusinessNo());
		    } else {
			LOGGER.info("失败加锁：" + mutex.getBusinessNo());
			return response;
		    }
		    try {
				response = syncMarkActivitiesFromCrmService.syncMarkActivitiesInfo(request);
			} catch (CommonException e) {
				response.setErrorMsg(e.getMessage());
				response.setResult(Boolean.FALSE);
				response.setFid(request.getFid());
				LOGGER.info(e.getMessage());
				LOGGER.info(e.getStackTrace().toString());
			}
		    LOGGER.info("开始解锁：" + mutex.getBusinessNo());
		    // 解业务锁
		    businessLockService.unlock(mutex);
		    LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		}else{
			response.setErrorMsg("发送的请求对象为空");
			response.setResult(Boolean.FALSE);
			response.setFid(null);
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
