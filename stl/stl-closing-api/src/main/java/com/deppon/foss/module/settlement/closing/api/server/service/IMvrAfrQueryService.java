package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrAfrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfrDto;

/**
 * 空运月报表查询服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-7 上午8:53:51
 */
public interface IMvrAfrQueryService extends IService {

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
