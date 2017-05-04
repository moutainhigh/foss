package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.esb.inteface.domain.crm.CusOrderSourceInfo;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.ws.syncdata.CommonException;

/**
 * 对CRM行业、客户等级、订单来源信息进行操作的接口
 * @author dujunhui-187862
 * @date   2014-9-26 下午2:19:28
 *
 */
public interface ISyncCusOrderSourceFromCrmService extends IService {

	public void syncCusOrderSourceInfo(CusOrderSourceInfo requestInfo)throws CommonException;
}
