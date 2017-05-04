package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;

/**
 * 
 * 收入确认、反确认服务（确认现金收款单、应收单的确认收入日期，更新应付单的签收日期，反确认反之操作）
 * @author dp-wujiangtao
 * @date 2012-10-13 上午10:54:37
 * @since
 * @version
 */
public interface ITakingService extends IService{
	/**
	 * 
	 * 确认收入
	 * @author dp-wujiangtao
	 * @date 2012-10-13 上午10:55:43
	 * @return
	 * @see
	 */
	void confirmIncome(LineSignDto dto,CurrentInfo currentInfo);
	
	/**
	 * 反确认收入
	 */
	void reverseConfirmIncome(LineSignDto dto,CurrentInfo currentInfo);
	
	/**
	 * 校验反签收服务
	 * @param dto
	 * @param currentInfo
	 */
	public void validReverseConfirmIncome(LineSignDto dto,
			CurrentInfo currentInfo);
    /**
     * 开单网上支付但是尚未支付的单据
     * @param waybill_Nos
     * @return
     */
	public List<String> unpaidOnlinePay(List<String> waybillNos);

}
