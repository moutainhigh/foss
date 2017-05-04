package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PDAOnlineUsingEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.PDAOnlineUsingDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TransferCenterDto;

/**
 * 统计PDA在线情况
 * @author foss中转-105795-wqh
 * @date 2015-01-26 上午8:16:40
 */
public interface IQueryPDAonLineService extends IService{

	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 调存储过程，生成PDA在线量日统计与月统计
	 *@param date
	 *@return int
	 *@author 105795
	 *@date 2015年1月26日下午3:36:54 
	 */
	int calculatePDAonline();
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 查询全国所有外场剔除掉分部
	 *@param 
	 *@return List<TransferCenterDto>
	 *@author 105795
	 *@date 2015年1月28日下午5:06:39 
	 */
	List<TransferCenterDto> queryAllTransferCenter();
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 查当前登录员工对应的外场或经营本部
	 *@param 
	 *@return List<TransferCenterDto>
	 *@author 105795
	 *@date 2015年1月30日上午9:03:47 
	 */
	PDAOnlineUsingEntity queryHqAndTransferCenter();
	
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 根据转入条件查询pda的使用情况
	 *@param pdaOnlineUsingEntity 其中统计日期不能为空
	 *@return List<PDAOnlineUsingEntity>
	 *@author 105795
	 *@date 2015年1月31日下午4:14:03 
	 */
	List<PDAOnlineUsingEntity> queryPDAOnlineUsing(PDAOnlineUsingEntity pdaOnlineUsingEntity);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 查询理货员 叉车司机所有人员使用PDA的情况  
	 *@param pdaOnlineUsingEntity 
	 *@return List<PDAOnlineUsingDto>
	 *@author 105795
	 *@date 2015年2月2日下午2:40:54 
	 */
	List<PDAOnlineUsingDto> queryAllCategoryPDAUsing(PDAOnlineUsingEntity pdaOnlineUsingEntity);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 查询理货员 叉车司机所有人员使用PDA的情况  分页
	 *@param pdaOnlineUsingEntity 
	 *@return List<PDAOnlineUsingDto>
	 *@author 105795
	 *@date 2015年2月2日下午2:40:54 
	 */
	List<PDAOnlineUsingDto> queryAllCategoryPDAUsing(PDAOnlineUsingEntity pdaOnlineUsingEntity,int start, int limit);
	
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 查询某一个外场下面的从月初到查询日期的所有pda使用情况
	 *@param transferCenterCode 外场编码
	 *@params startStaDate 月初统计日期，endStaDate查询日期 当start和limit为0时表示不分页
	 *@return List<PDAOnlineUsingDto>
	 *@author 105795
	 *@date 2015年2月3日上午9:51:01 
	 */
	List<PDAOnlineUsingDto> queryAllCategoryPDAUsingDetail(String transferCenterCode,Date startStaDate,Date endStaDate,int start, int limit );
	
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 根据部门code查询经营部门
	 *@param code
	 *@return TransferCenterDto
	 *@author 105795
	 *@date 2015年2月4日上午11:18:33 
	 */
	TransferCenterDto queryOrgByOrgCode(String code);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 导出pda使用明细  
	 *@param transferCenterCode  startStaDate endStaDate
	 *@return ExportResource
	 *@author 105795
	 *@date 2015年2月6日上午9:40:18 
	 */
	ExportResource exportPDAUsingDetail(String transferCenterCode,Date startStaDate,Date endStaDate);
	
}
