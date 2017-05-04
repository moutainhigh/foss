package com.deppon.foss.module.settlement.closing.api.server.service;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfiDto;

/**
 * 始发、空运接口
 * @author foss-pengzhen
 * @date 2013-3-6 上午11:15:32
 * @since
 * @version
 */
public interface IMvrAfiService{
	
	/**
	 * 根据多个参数查询始发空运信息
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	MvrAfiDto queryMvrAfiByConditions(MvrAfiDto mvrAfiDto,int start,int limit);
	
	/**
	 * 导出始发空运往来列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	ExportResource exportMvrAfis(MvrAfiDto mvrAfiDto,CurrentInfo currentInfo);
}
