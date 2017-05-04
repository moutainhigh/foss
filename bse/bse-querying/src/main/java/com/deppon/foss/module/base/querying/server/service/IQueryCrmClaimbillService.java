package com.deppon.foss.module.base.querying.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.querying.shared.dto.QueryClaimbillResultDto;

/**
 * 查询CRM理赔信息
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-4-24 下午3:18:53
 */
public interface IQueryCrmClaimbillService extends IService {

	/**
	 * 根据运单号查询CRM理赔信息
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-4-24 下午5:08:52
	 */
	QueryClaimbillResultDto queryCrmClaimbillByWaybillNo(String waybillNo);

}
