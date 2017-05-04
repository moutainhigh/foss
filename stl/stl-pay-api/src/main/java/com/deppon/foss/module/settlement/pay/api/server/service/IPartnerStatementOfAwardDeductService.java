package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;


/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-3-5 上午10:40:43    
 */
public interface IPartnerStatementOfAwardDeductService extends IService {

	/**
	 * 对账单批量扣款
	 * @author foss结算-306579-guoxinru 
     * @date 2016-3-5 
	 */
	void partnerStatementOfAwardToDeduct(List<String> statementNoList);

}
