package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLddEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLddDto;

/**
 * 快递代理月报表查询服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-7 上午8:53:51
 */
public interface IMvrLddService extends IService {

	/**
	 * 
	 * 查询快递代理月报表列表
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午8:57:23
	 */
	List<MvrLddEntity> queryMvrLdd(MvrLddDto dto, int offset, int limit);

	/**
	 * 
	 * 查询快递代理月报表汇总
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午9:54:51
	 */
	MvrLddDto queryMvrLddTotal(MvrLddDto dto);

}
