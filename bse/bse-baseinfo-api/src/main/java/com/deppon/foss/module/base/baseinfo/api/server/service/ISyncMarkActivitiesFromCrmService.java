package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.esb.inteface.domain.crm.MarketingActivityRequest;
import com.deppon.esb.inteface.domain.crm.MarketingActivityResponse;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.ws.syncdata.CommonException;

/**
 * 对市场活动信息进行操作的接口
 * @author 078816
 * @date   2014-04-14
 *
 */
public interface ISyncMarkActivitiesFromCrmService extends IService {

	public MarketingActivityResponse syncMarkActivitiesInfo(MarketingActivityRequest requestInfo)throws CommonException;
}
