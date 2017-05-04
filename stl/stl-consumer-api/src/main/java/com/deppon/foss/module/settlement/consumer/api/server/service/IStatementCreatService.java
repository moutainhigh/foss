package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceManagementAddDto;

public interface IStatementCreatService extends IService{
	/**
	 * 查询对账单明细包含的（应收、应付、预收、预付）单据及对账单信息服务接口
	 * 
	 * @author 218371-foss-zhaoyanjun
	 * @date 2012-10-16 下午8:38:10
	 * @param statementOfAccountMakeQueryDto,
	 * 			cInfo
	 * 			制作对账单参数Dto,
	 *  		当前登录用户
	 * @return StatementOfAccountMakeQueryResultDto
	 * 			对账单及对账单明细Dto
	 */
	InvoiceManagementAddDto queryForInvoiceStatementAddStatementMake(
			InvoiceManagementAddDto queryDto,
			CurrentInfo info);
	
	//保存对账单
	InvoiceManagementAddDto addStatement(InvoiceManagementAddDto resultDto,
			CurrentInfo info);
	
	//查询的对账单集合
	List<InvoiceManagementAddDto> queryStatements(
			InvoiceManagementAddDto queryDto);
	
	//删除对账单
	void deleteStatementNo(String[] strings);
	
	//修改对账单状态
	int updateInvoiceState(Map<String, Object> receiveMap);
	
	//按对账单号查询应收单
	InvoiceManagementAddDto queryReceivablesByStatementBillNo(
			String statementBillNo);
}
