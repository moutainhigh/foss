package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.dto.WriteoffInformationDto;


/**
 * @author 218392 张永雪
 * @date 2016-02-15 10:15:12
 * 裹裹项目：FOSS结算核销后把核销信息传给财务自助，传的字段有：DOP传过单的金额、
 * FOSS核销金额、应收部门、DOP时间、运单号
 */
public interface IGreenHandWrapToFinanceClient {
	
	public void sendWriteoffWrapToFinance(WriteoffInformationDto writeoffInformationDto);
	
}
