/**
 * 
 */
package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfostREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostRDto;

/**
 * 
 * @author 340778 foss
 * @createTime 2016年8月17日 下午8:20:15
 */
public interface IMvrNrfostRService {
	/** 
	 * 查询总数
	 * */
	MvrNrfostRDto queryTotalByConditions(MvrNrfostRDto dto);
	/**
	 * 查询分页
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<MvrNrfostREntity> queryByConditions(MvrNrfostRDto dto,int offset, int limit);
	
}
