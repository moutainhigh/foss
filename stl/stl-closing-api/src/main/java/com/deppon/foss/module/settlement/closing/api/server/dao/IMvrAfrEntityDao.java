package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrAfrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfrDto;

/**
 * 空运月报DAO
 * 
 * @author ibm-zhuwei
 * @date 2013-3-5 下午6:16:45
 */
public interface IMvrAfrEntityDao {

	/**
	 * 
	 * 查询空运月报表列表
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午8:57:23
	 */
	List<MvrAfrEntity> queryMvrAfr(MvrAfrDto dto, int offset, int limit);

	/**
	 * 
	 * 查询空运月报表汇总
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午9:54:51
	 */
	MvrAfrDto queryMvrAfrTotal(MvrAfrDto dto);

}
