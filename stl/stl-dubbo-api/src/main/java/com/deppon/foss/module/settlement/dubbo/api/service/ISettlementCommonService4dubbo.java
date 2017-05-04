package com.deppon.foss.module.settlement.dubbo.api.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.dubbo.api.define.SettlementNoRuleEnum;

/**
 * 结算通用服务
 * @author ibm-zhuwei
 * @date 2012-10-15 下午1:33:21
 */
public interface ISettlementCommonService4dubbo extends IService {
	
	/**
	 * 获取结算单据编号
	 * @author ibm-zhuwei
	 * @date 2012-10-24 上午8:54:56
	 * @param rule 结算单据编号规则
	 * @return
	 */
	String getSettlementNoRule(SettlementNoRuleEnum rule);
}
