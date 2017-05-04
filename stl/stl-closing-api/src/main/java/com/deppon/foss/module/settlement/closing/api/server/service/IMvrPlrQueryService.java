package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPlrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPlrDto;

/**
 * @author 095793-foss-LiQin 偏线月报服务
 */
public interface IMvrPlrQueryService extends IService {

	/**
	 * 查询偏线月报
	 * 
	 * @author 095793-foss-LiQin
	 * @param
	 * @date 2013-3-12 上午11:40:13
	 * @return
	 */
	List<MvrPlrEntity> queryMvrPlrByParam(MvrPlrDto dto,int start, int limit);
	
	
	/**
	 * 查询偏线月报总数
	 * @author 095793-foss-LiQin
	 * @param 
	 * @date 2013-3-14 上午9:57:37
	 * @return 
	 */
	MvrPlrDto queryMvrPlrByParamTotal(MvrPlrDto dto);
	
	
	/**
	 * 导出偏线月报表
	 * @author 095793-foss-LiQin
	 * @date 2013-3-22 下午5:14:36
	 * @param dto
	 * @param cInfo
	 * @return
	 */
	ExportResource exportMvrPlr(MvrPlrDto dto ,CurrentInfo cInfo);
}
