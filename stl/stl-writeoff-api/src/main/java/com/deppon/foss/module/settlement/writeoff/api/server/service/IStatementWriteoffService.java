package com.deppon.foss.module.settlement.writeoff.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto;

/**
 * 对账单核销服务接口类
 * 
 * @author foss-wangxuemin
 * @date Nov 7, 2012 5:00:22 PM
 */
public interface IStatementWriteoffService extends IService {

	/**
	 * 根据页面传入的对账单明细进行核销
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 7, 2012 5:01:10 PM
	 * @param statementDto,currentInfo
	 *  		对账单号,当前登录用户
	 * @return StatementOfAccountMakeQueryResultDto
	 * 			对账单及对账单明细返回dto
	 */
	StatementOfAccountMakeQueryResultDto writeoffStatement(
			StatementOfAccountMakeQueryResultDto statementDto,
			CurrentInfo currentInfo);
}
