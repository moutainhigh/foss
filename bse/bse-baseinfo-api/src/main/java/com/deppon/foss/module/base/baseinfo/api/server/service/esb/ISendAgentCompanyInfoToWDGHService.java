package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;

public interface ISendAgentCompanyInfoToWDGHService {
	/**
	 * 
	 *<p>接口同步方法</p>
	 *@author 269231 -qirongsheng
	 *@date 2016-4-1下午04:33:28
	 *@param dtos
	 *@param OperateType  操作类型
	 */
	void syncAgentCompanyInfo(BusinessPartnerEntity dtos,String operateType);
}
