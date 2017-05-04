package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfiDto;

/**
 * 
 * 查询始发到达往来报表
 * @author 045738-foss-maojianqiang
 * @date 2013-3-7 下午2:06:45
 */
public interface IMvrRfiEntityService extends IService {

	/** 
	 * 查询始发到达往来报表
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:50:57
	 * @param start
	 * @param limit
	 * @param dto
	 */
	List<MvrRfiEntity> queryReportByConditions(int start, int limit, MvrRfiDto dto);
	
	/**
	 * 查询始发到达往来报表总条数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:50:57
	 * @param start
	 * @param limit
	 * @param dto
	 */
	MvrRfiEntity queryTotalCounts(MvrRfiDto dto);
	
	/**
	 * 导出excel
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:50:57
	 * @param start
	 * @param limit
	 * @param dto
	 */
	ExportResource export(MvrRfiDto dto);
}
