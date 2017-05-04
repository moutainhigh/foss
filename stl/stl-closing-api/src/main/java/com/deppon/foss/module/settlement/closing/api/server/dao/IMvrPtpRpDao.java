/**
 * 
 */
package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpRpEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpRpDto;

/**
 * @author 231438
 * 合伙人奖罚月报表Dao
 * @Date 2016-02-28
 */
public interface IMvrPtpRpDao {
	/** 
	 * 按条件查询合伙人奖罚月报表 
	 * */
	List<MvrPtpRpEntity> queryByConditions(MvrPtpRpDto dto,int start,int limit);

	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	MvrPtpRpDto queryTotalByCondition(MvrPtpRpDto dto);
}
