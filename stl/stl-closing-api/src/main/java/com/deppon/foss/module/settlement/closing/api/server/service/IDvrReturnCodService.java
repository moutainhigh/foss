package com.deppon.foss.module.settlement.closing.api.server.service;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvrReturnCodDto;

/**
 * 退代收货款报表接口
 * @author foss-pengzhen
 * @date 2013-3-6 上午11:15:32
 * @since
 * @version
 */
public interface IDvrReturnCodService{
	
	/**
	 * 根据多个参数查询退代收货款信息
	 * @author foss-pengzhen
	 * @date 2013-4-3 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	DvrReturnCodDto queryDvrReturnCodByConditions(DvrReturnCodDto dvrReturnCodDto,int start,int limit);
	
	/**
	 * 导出退代收货款列表信息
	 * @author foss-pengzhen
	 * @date 2013-4-2 上午11:02:59
	 * @param mvrAfiDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	ExportResource exportDvrReturnCods(DvrReturnCodDto dvrReturnCodDto,CurrentInfo currentInfo);
}
