package com.deppon.foss.module.base.bankitf.server.esb;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.fins.PartnerAccountInfo;
import com.deppon.esb.inteface.domain.fins.PartnerAccountInfoRequest;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPartnerAccountInfoService;


/**
 * 同步合伙人账户数据信息服务处理
 * 
 * @author 302302
 * @date 2016-1-08
 */

public class ReceivePartnerAccountSyncRequestProcess implements IProcess {
	 /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceivePartnerAccountSyncRequestProcess.class);
    
	@Autowired
	private IPartnerAccountInfoService partnerAccountInfoService;

	/**
	 * 接收同步合伙人账户数据信息
	 * 
	 * @author 302302
	 * @date 2016-1-08
	 * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
	 */
	@Override
	public Object process(Object req) throws ESBBusinessException {
		PartnerAccountInfoRequest request = (PartnerAccountInfoRequest) req;
		LOGGER.info("接收财务系统同步合伙人账户信息"+request.getPartnerAccountInfoList());
		List<PartnerAccountInfo> list = request.getPartnerAccountInfoList();
		try {
			partnerAccountInfoService.operation(list);
		} catch (Exception e) {
			LOGGER.info("接收财务系统同步合伙人账户信息异常"+e.getMessage());
			throw new ESBBusinessException (e.getMessage());
		}
		return null;
	}
	/**
	 * @author 302302
	 * @date 2016-1-08
	 * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
	 */
	@Override
	public Object errorHandler(Object arg0) throws ESBBusinessException {
		return null;
	}

}
