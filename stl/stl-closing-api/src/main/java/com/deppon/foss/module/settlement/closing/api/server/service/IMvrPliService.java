package com.deppon.foss.module.settlement.closing.api.server.service;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPliDto;

/**
 * 始发偏线往返报表接口
 * @author foss-pengzhen
 * @date 2013-3-6 上午11:15:32
 * @since
 * @version
 */
public interface IMvrPliService{
	
	/**
	 * 根据多个参数查询始发偏线信息
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @param mvrAfiDto
	 * @return
	 * @see
	 */
	MvrPliDto queryMvrPliByConditions(MvrPliDto mvrPliDto,int start,int limit);
	
	/**
	 * 导出始发偏线往来列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	ExportResource exportMvrPlis(MvrPliDto mvrPliDto,CurrentInfo currentInfo);
}
