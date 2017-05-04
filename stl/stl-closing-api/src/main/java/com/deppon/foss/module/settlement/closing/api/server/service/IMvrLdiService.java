package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLdiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLdiDto;

/**
 * 快递代理往来接口
 * @author foss-pengzhen
 * @date 2013-3-6 上午11:15:32
 * @since
 * @version
 */
public interface IMvrLdiService{
	
	/**
	 * 根据多个参数查询快递代理信息
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:17:17
	 * @param MvrLdiDto
	 * @return
	 * @see
	 */
	List<MvrLdiEntity> queryMvrLdiByConditions(MvrLdiDto mvrLdiDto,int start,int limit);
	
	/**
	 * 导出快递代理往来列表信息
	 * @author foss-pengzhen
	 * @date 2013-3-19 下午4:44:47
	 * @param billCashCashierConfirmDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	ExportResource exportMvrLdis(MvrLdiDto mvrLdiDto,CurrentInfo currentInfo);
	
	 /**
	 * 查询快递代理往来报表总条数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:50:57
	 * @param start
	 * @param limit
	 * @param dto
	 */
	MvrLdiDto queryTotalCounts(MvrLdiDto mvrLdiDto);
}
