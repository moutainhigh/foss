package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryTrayOfflineScanConditionDto;

public interface ITrayOfflineScanDao {
	
	/**
	 * 查询叉车离线扫描信息（离线扫描与托盘任务匹配）零担
	 * @author foss-heyongdong
	 * @param start 
	 * @param limit 
	 * @Date 2014年1月6日 14:20:55
	 * */
	List<TrayOfflineScanEntity> querytrayOfflineScanInfo(
			QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto, int limit, int start);
	
	/**
	 * 查询叉车离线扫描信息（离线扫描与托盘任务匹配）快递
	 * @author foss-heyongdong
	 * @param start 
	 * @param limit 
	 * @Date 2014年1月6日 14:20:55
	 * */
	String querytrayOfflineScanInfoExpress(
			QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto, int limit, int start);
	
	
	/**
	 * 查询叉车离线扫描信息不分页 零担
	 * @author foss-heyongdong
	 * @Date 2014年1月8日 09:56:58
	 * @param queryTrayOfflineScanConditionDto
	 * @return List
	 */
	List<TrayOfflineScanEntity> querytrayOfflineScanInfoNoPage(QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto);

	
	/**
	 * 查询叉车离线扫描信息不分页 快递
	 * @author foss-heyongdong
	 * @Date 2014年1月8日 09:56:58
	 * @param queryTrayOfflineScanConditionDto
	 * @return List
	 */
	List<TrayOfflineScanExpressEntity> querytrayOfflineScanInfoNoPageExpress(QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto);


}
