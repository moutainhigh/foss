/**
 * 
 */
package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpRpEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpRpDto;

/**
 * @author 231438
 * 合伙人奖罚月报表服务
 * @date 2016-02-28 下午3:43:01
 */
public interface IMvrPtpRpService {
	/** 
	 * 查询合伙人奖罚月报表列表总数
	 * */
	MvrPtpRpDto queryTotalByConditions(MvrPtpRpDto dto);
	/**
	 * 查询合伙人奖罚月报表 分页
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<MvrPtpRpEntity> queryByConditions(MvrPtpRpDto dto,int offset, int limit);
	
}
