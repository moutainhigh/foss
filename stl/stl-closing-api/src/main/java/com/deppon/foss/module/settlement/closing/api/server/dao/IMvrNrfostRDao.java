/**
 * 
 */
package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfostREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostRDto;

/**
 * 02特殊业务重分类报表Dao
 * @author 340778 foss
 * @createTime 2016年8月17日 下午8:19:07
 */
public interface IMvrNrfostRDao {
	/** 
	 * 按条件查询
	 */
	List<MvrNrfostREntity> queryByConditions(MvrNrfostRDto dto,int start,int limit);

	/**
	 * 查询总数
	 */
	MvrNrfostRDto queryTotalByCondition(MvrNrfostRDto dto);
}
