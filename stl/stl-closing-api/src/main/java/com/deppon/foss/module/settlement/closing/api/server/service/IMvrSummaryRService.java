/**
 * 
 */
package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrSummaryREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrSummaryRDto;

/**
 * 02业务重分类汇总报表Service
 * @author 340778 foss
 * @createTime 2016年8月18日 下午4:44:46
 */
public interface IMvrSummaryRService {
	/** 
	 * 查询02业务重分类汇总报表列表总数
	 * */
	MvrSummaryRDto queryTotalByConditions(MvrSummaryRDto dto);
	/**
	 * 查询02业务重分类汇总报表 分页
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<MvrSummaryREntity> queryByConditions(MvrSummaryRDto dto,int offset, int limit);
	
}
