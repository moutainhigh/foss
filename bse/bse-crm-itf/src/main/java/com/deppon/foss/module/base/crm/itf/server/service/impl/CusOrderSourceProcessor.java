package com.deppon.foss.module.base.crm.itf.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.crm.CusOrderSourceInfo;
import com.deppon.esb.inteface.domain.crm.CusOrderSourceSyncProcessDetail;
import com.deppon.esb.inteface.domain.crm.CusOrderSourceSyncRequest;
import com.deppon.esb.inteface.domain.crm.CusOrderSourceSyncResponse;
import com.deppon.esb.pojo.transformer.jaxb.CusOrderSourceSyncRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.CusOrderSourceSyncResponseTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncCusOrderSourceFromCrmService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.ws.syncdata.CommonException;
/**
 * 从CRM系统过来的行业、客户等级、订单来源信息
 * 交互方式：异步
 * @author dujunhui-187862
 * @date   2014-9-26 下午2:16:43
 */
public class CusOrderSourceProcessor implements IProcess {
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(CusOrderSourceProcessor.class);
    
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
     * 对CRM行业、客户等级、订单来源信息进行操作的Service接口
     */
    private ISyncCusOrderSourceFromCrmService syncCusOrderSourceFromCrmService;
    
    /**
     * 设置信息进行操作的Service服务
     */
    public void setSyncCusOrderSourceFromCrmService(
			ISyncCusOrderSourceFromCrmService syncCusOrderSourceFromCrmService) {
		this.syncCusOrderSourceFromCrmService = syncCusOrderSourceFromCrmService;
	}

	/**
     * 提供给CRM的接口，用来传递行业、客户等级、订单来源的相关数据信息
     * 异步交互
     * @author dujunhui-187862
     * @date   2014-9-26 下午2:16:43
    */
	@Override
	public Object process(Object req) throws ESBBusinessException {

		LOGGER.info(" ***************************** 同步相关信息开始 ***************************** ");
		//接收到的请求消息类
		CusOrderSourceSyncRequest request = (CusOrderSourceSyncRequest) req;
		//响应消息类
		CusOrderSourceSyncResponse response = new CusOrderSourceSyncResponse();

		LOGGER.info(new CusOrderSourceSyncRequestTrans().fromMessage(request));//请求转换类
		
		if(null != request){
			int successCount = 0, failCount  = 0;
			List<CusOrderSourceSyncProcessDetail> detail=response.getDetail();
			for(CusOrderSourceInfo cinfo: request.getCusOrderSourceList()){
				// 业务锁
				if(cinfo==null){
					LOGGER.info("实体为空！");
					continue;
				}
				//定义业务号
				String businessNo="";
				if(StringUtils.isNotEmpty(cinfo.getSecIndustryCode())){
					businessNo=cinfo.getSecIndustryCode();
				}else if(StringUtils.isNotEmpty(cinfo.getCustomerDegreeCode())){
					businessNo=cinfo.getCustomerDegreeCode();
				}else if(StringUtils.isNotEmpty(cinfo.getOrderSourceCode())){
					businessNo=cinfo.getOrderSourceCode();
				}else{
					LOGGER.info("编码为空！");
					continue;
				}
			    MutexElement mutex = new MutexElement(businessNo, 
			    		"CUSORDERSOURCEINFO_VERSIONNO",
				    MutexElementType.CUSORDERSOURCEINFO_VERSIONNO);
			    LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			    boolean result = businessLockService.lock(mutex,
				    NumberConstants.ZERO);
			    if (result) {
			    	LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			    } else {
			    	LOGGER.info("失败加锁：" + mutex.getBusinessNo());
			    	detail.add(this.recordSynchronizedRequestDataError(
			    			cinfo, false, "出现了高并发操作！"));
					failCount++;
					continue;
			    }
			    try {
					syncCusOrderSourceFromCrmService.
							syncCusOrderSourceInfo(cinfo);//执行同步Service操作
					successCount++;
					//做成功记录
					detail.add(this.recordSynchronizedRequestDataError(cinfo, true, null));
				} catch (CommonException e) {
					failCount++;
					//做失败记录
					detail.add(this.recordSynchronizedRequestDataError(cinfo, false, e.
							getMessage()));
					LOGGER.info(e.getMessage());
					LOGGER.info(e.getStackTrace().toString());
				}
			    LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			    // 解业务锁
			    businessLockService.unlock(mutex);
			    LOGGER.info("完成解锁：" + mutex.getBusinessNo());
				}
			response.setSuccessCount(successCount);
			response.setFailCount(failCount);
			LOGGER.info(new CusOrderSourceSyncResponseTrans().fromMessage(response));//响应转换类
		}
		LOGGER.info(" ***************************** 同步相关信息结束************************** ");
		return response;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("ESB处理错误");
		return null;
	}
	
	/**
     * <p>对同步的CRM对象做成功和失败的记录</p> 
     * @author 187862-dujunhui
     * @date 2014-11-14 下午2:13:22
     * @param result 0表示失败，1表示成功
     * @param reason 失败的原因
     * @return
     * @see
     */
    private CusOrderSourceSyncProcessDetail recordSynchronizedRequestDataError(CusOrderSourceInfo 
    		rInfo, boolean result, String reason){
    	CusOrderSourceSyncProcessDetail rSyncProcessDetail = new CusOrderSourceSyncProcessDetail();
    	if(StringUtils.equals(rInfo.getImportPattern(), NumberConstants.
    			NUMERAL_S_ONE)){//CRM行业信息
    		rSyncProcessDetail.setImportCode(rInfo.getSecIndustryCode());//传入编码
    	}else if(StringUtils.equals(rInfo.getImportPattern(), NumberConstants.
    			NUMERAL_S_TWO)){//客户等级信息
    		rSyncProcessDetail.setImportCode(rInfo.getCustomerDegreeCode());//传入编码
    	}else if(StringUtils.equals(rInfo.getImportPattern(), NumberConstants.
    			NUMERAL_S_THREE)){//订单来源信息
    		rSyncProcessDetail.setImportCode(rInfo.getOrderSourceCode());//传入编码
    	}
    	rSyncProcessDetail.setOperateType(rInfo.getOperateType());//操作类型
    	rSyncProcessDetail.setVersion(new Date().toString());//版本号
	
    	//判断操作是否失败，失败则必须填写原因
    	if (!result) {
    		rSyncProcessDetail.setReason(reason);
    	}
    	rSyncProcessDetail.setResult(result);
    	return rSyncProcessDetail;
    }

}
