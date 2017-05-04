package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNAfrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNAfrDto;

/**
 * 空运月报表查询服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-7 上午8:53:51
 */
public interface IMvrNAfrQueryService extends IService {

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
