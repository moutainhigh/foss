package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrOrccEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrOrccQueryDto;

public interface IMvrOrccService extends IService {
	/**
	 * 查询始发外请车报表
	 * 
	 * @author 073615
	 * @param
	 * @date 2014-05-19
	 * @return
	 */
	List<MvrOrccEntity> queryMvrOrccByParam(MvrOrccQueryDto dto,int start, int limit);	

	/**
	 * 查询始发外请车报表总条数
	 * 
	 * @author 073615
	 * @param
	 * @date 2014-05-19
	 * @return
	 */
	Long queryMvrOrccByParamCount(MvrOrccQueryDto dto);
	
	/**
	 * 导出始发外请车报表
	 * @author 073615
	 * @param
	 * @date 2014-05-19
	 * @return
	 */
	ExportResource exportMvrOrcc(MvrOrccQueryDto dto ,CurrentInfo cInfo);

}
