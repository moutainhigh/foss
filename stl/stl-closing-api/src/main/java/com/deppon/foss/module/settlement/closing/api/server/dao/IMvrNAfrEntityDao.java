package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNAfrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNAfrDto;

/**
 * 空运月报DAO
 * 
 * @author ibm-zhuwei
 * @date 2013-3-5 下午6:16:45
 */
public interface IMvrNAfrEntityDao {

	/**
	 * 
	 * 查询空运月报表列表
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午8:57:23
	 */
	List<MvrNAfrEntity> queryMvrNAfr(MvrNAfrDto dto, int offset, int limit);

	/**
	 * 
	 * 查询空运月报表汇总
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午9:54:51
	 */
	MvrNAfrDto queryMvrNAfrTotal(MvrNAfrDto dto);

}
