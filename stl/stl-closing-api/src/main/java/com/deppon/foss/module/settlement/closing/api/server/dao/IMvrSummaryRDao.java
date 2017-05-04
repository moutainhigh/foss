/**
 * 
 */
package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrSummaryREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrSummaryRDto;

/**
 * 02业务重分类汇总报表Dao
 * @author 340778 foss
 * @createTime 2016年8月18日 下午4:44:05
 */
public interface IMvrSummaryRDao {
	/** 
	 * 按条件查询02业务重分类汇总报表 
	 * */
	List<MvrSummaryREntity> queryByConditions(MvrSummaryRDto dto,int start,int limit);

	/**
	 * 查询总数
	 * @param dto
	 * @return
	 */
	MvrSummaryRDto queryTotalByCondition(MvrSummaryRDto dto);
}
