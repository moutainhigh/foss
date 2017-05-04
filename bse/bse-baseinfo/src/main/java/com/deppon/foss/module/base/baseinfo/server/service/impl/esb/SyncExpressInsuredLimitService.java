package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;


import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.inteface.domain.crm.ExpressInsuredLimitInfo;
import com.deppon.esb.inteface.domain.crm.ExpressInsuredLimitRequest;
import com.deppon.esb.pojo.transformer.jaxb.KdbjsxRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncExpressInsuredLimitService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ConfigurationParamsEntity;


public class SyncExpressInsuredLimitService implements ISyncExpressInsuredLimitService  {
	// 记录日志
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncExpressInsuredLimitService.class);
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYNCEXPRESS_OUTPRICE";
	private static final String VERSION = "1.0";
	
	/** 
	 * <p>同步快递保价上线到CRM、CC、官网 </p> 
	 * @author 232607 
	 * @date 2015-6-19 上午8:58:32
	 * @param entity 
	 * @see com.deppon.foss.module.base.dict.api.server.service.esb.ISyncExpressInsuredLimitService#synInfoToCRMCCGW(com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity)
	 */
	@Override
	public void synInfoToCRMCCGW(ConfigurationParamsEntity entity) {
		if (null==entity) {
			throw new BusinessException("传入的对象为空");
		}
		ExpressInsuredLimitRequest request = converter(entity);
		// 创建服务头信息
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(UUID.randomUUID().toString());
		accessHeader.setBusinessDesc1("同步快递保价申明价值上限到crm");
		accessHeader.setBusinessDesc2("同步快递保价申明价值上限到cc");
		accessHeader.setBusinessDesc3("同步快递保价申明价值上限到官网");
		accessHeader.setVersion(VERSION);
		try {
			LOGGER.info("开始调用，同步快递保价申明价值上限至CRM：\n"
					+ new KdbjsxRequestTrans().fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			LOGGER.info("结束调用，同步快递保价申明价值上限至CRM：\n"
					+ new KdbjsxRequestTrans().fromMessage(request));
		} catch (ESBException e) {
			e.printStackTrace();
		}
		LOGGER.info("send ExpressInsuredLimit to CRM System ：同步数据到CRM \n");
	}
	
	/**
	 * <p>转换方法，将配置参数实体转换成将要发送的信息实体，并装入请求当中</p> 
	 * @author 232607 
	 * @date 2015-6-19 上午8:57:24
	 * @param entity
	 * @return
	 * @see
	 */
	private ExpressInsuredLimitRequest converter(ConfigurationParamsEntity entity){
		ExpressInsuredLimitRequest request = new ExpressInsuredLimitRequest();
		ExpressInsuredLimitInfo info = new ExpressInsuredLimitInfo();
		//配置参数名（快递保价申明价值上限、快递外发保价申明价值上限）
		info.setConfigParamName(entity.getConfName());
		//配置参数值
		info.setConfigParamValue(entity.getConfValue());
		request.setExpressInsuredLimitInfo(info);
		return request;
	}
	
	
}
