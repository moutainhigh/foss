package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.domain.RequestGreenHandWrapEntity;

/**
 * 提供FOSS到财务自助第三方支付数据的公共接口类
 * 
 * @ClassName: IFossToFinsCommonService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-7-3 上午11:26:17
 */
public interface IFossToFinsRemitCommonService {
	/**
	 * 推送第三方付款数据到财务自助
	 * 
	 * @Title: pushRemittanceMessageToFince
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-7-3 上午11:28:21
	 */
	void pushRemittanceMessToFins(RequestGreenHandWrapEntity param);
}
