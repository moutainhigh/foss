package com.deppon.foss.module.settlement.closing.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.ClaimsPayBillGenerateRes;

/**
 * 
 * CRM到FOSS应付单服务（理赔、服务补救、退运费）
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-12-4 下午4:09:32
 */
public interface ICRMPayableBillService extends IService {

	/**
	 * 
	 * 新增CRM发送到FOSS的应付单
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-4 下午4:11:32
	 */
	void addCRMBillPayable(ClaimsPayBillGenerateRes request,
			CurrentInfo currentInfo);
	
	/**
	 * 理赔应付单直接付款，对接到报账平台生成工作流 
	 * 
	 * @author 269044
	 * @date 2016-01-22
	 */
	void crmBillPayableToFSSC(ClaimsPayBillGenerateRes request,
			CurrentInfo currentInfo);

}
