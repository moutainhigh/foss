package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpStEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpStDto;

/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-3-18 下午2:26:58    
 */
public interface IMvrPartnerStockTfrDao {

	/**
	 * 分页查询总数
	 * @param mvrPtpStDto
	 * @return
	 */
	Long queryMvrPtpStCount(MvrPtpStDto mvrPtpStDto);

	/**
	 * 查询合伙人股份中转月报表集合
	 * @param mvrPtpStDto
	 * @param start
	 * @param limit
	 * @return
	 */
	List<MvrPtpStEntity> queryMvrPtpStEntityList(MvrPtpStDto mvrPtpStDto,int start, int limit);


}
