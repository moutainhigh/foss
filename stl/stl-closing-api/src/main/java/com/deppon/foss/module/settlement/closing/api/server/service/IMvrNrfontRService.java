/**
 * 
 */
package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfontREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontRDto;


/**
 * 02普通业务重分类报表Service
 * @author 340778 foss
 * @createTime 2016年8月17日 上午10:51:02
 */
public interface IMvrNrfontRService {
	/**
	 * 条件汇总单记录查询-02普通业务重分类报表
	 * @author 340778 foss
	 * @createTime 2016年8月17日 上午10:51:28
	 * @param dto
	 * @return
	 */
	MvrNrfontRDto queryTotalByConditions(MvrNrfontRDto dto);
	/**
	 * 条件多记录查询
	 * @author 340778 foss
	 * @createTime 2016年8月17日 上午10:52:39
	 * @param dto
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<MvrNrfontREntity> queryByConditions(MvrNrfontRDto dto,int offset, int limit);
	
}
