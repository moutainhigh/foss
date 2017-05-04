package com.deppon.foss.module.settlement.closing.api.server.service;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpStDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-3-18 下午2:26:41    
 */
public interface IMvrPartnerStockTfrService {

	/**
	 * 查询合伙人股份中转月报表分页条数
	 * @param dto
	 * @return dtp
	 * @throws SettlementException
     */
	Long queryMvrPtpStCount(MvrPtpStDto mvrPtpStDto);
	
	/**
	 * 查询合伙人股份中转月报表集合
	 * @param dto
	 * @return dtp
	 * @throws SettlementException
     */
	MvrPtpStDto queryMvrPtpStEntityList(MvrPtpStDto mvrPtpStDto, int start,int limit);


}
