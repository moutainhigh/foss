package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FinancialDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.SettlementPayToVtsDto;

/** 
 * vts参数校验及结清货款
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-6 下午4:42:21    
 */
public interface IVtsValidateAndSettlementService extends IService{

	
	/**
	 * vts参数校验及结清货款
	 * @param dto
	 * @param currentInfo
	 * @return
	 */
	void ValidateAndSettlement(SettlementPayToVtsDto dto,CurrentInfo currentInfo);
	
	/**
	 * 是否结清货款.
	 *
	 * @param waybillNo 
	 * 		the waybill no
	 * @return true, 
	 * 		if is payment
	 */
	public boolean isPayment(String waybillNo);
	
	/**
	 * 返回财务应收信息.
	 *
	 * @param waybillNo the waybill no
	 * @return the financial dto
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-11 下午3:22:00
	 */
	public FinancialDto queryFinanceSign(String waybillNo);

	/**
	 * 快速结清--对接整车
	 * @param dto
	 * @param currentInfo
	 */
	void quickSettlement(SettlementPayToVtsDto dto, CurrentInfo currentInfo);
	
	/**
	 * 354830 孙小雪
	 * 根据运单号查询实际承运表结清状态
	 * @param waybillNo
	 * @return
	 */
	public String querySettlementStatus(String waybillNo);
}
