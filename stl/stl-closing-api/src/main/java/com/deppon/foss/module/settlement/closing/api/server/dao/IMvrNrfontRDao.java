/**
 * 
 */
package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfontREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontRDto;

/**
 * 02普通业务重分类报表Dao
 * @author 340778 foss
 * @createTime 2016年8月17日 上午10:47:09
 */
public interface IMvrNrfontRDao {
	/**
	 * 条件多记录查询-02普通业务重分类报表
	 * @author 340778 foss
	 * @createTime 2016年8月17日 上午10:48:18
	 * @param dto 
	 * @param start
	 * @param limit
	 * @return
	 */
	List<MvrNrfontREntity> queryByConditions(MvrNrfontRDto dto,int start,int limit);

	/**
	 * 条件汇总单记录查询-02普通业务重分类报表
	 * @author 340778 foss
	 * @createTime 2016年8月17日 上午10:48:09
	 * @param dto
	 * @return
	 */
	MvrNrfontRDto queryTotalByCondition(MvrNrfontRDto dto);
}
