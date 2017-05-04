package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.List;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryTrayOfflineScanConditionDto;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanExpressEntity;

public interface ITrayOfflineScanService {
	/**
	 * 查询叉车离线扫描信息 零担
	 * @author foss-heyongdong
	 * @param j 
	 * @param i 
	 * @Date 2014年1月6日 14:14:43
	 * */
	List<TrayOfflineScanEntity> querytrayOfflineScanInfo(
			QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto, int i, int j);
	
	/**
	 * 查询叉车离线扫描信息 快递
	 * @author foss-heyongdong
	 * @param j 
	 * @param i 
	 * @Date 2014年1月6日 14:14:43
	 * */
	List<TrayOfflineScanExpressEntity> querytrayOfflineScanInfoExpress(
			QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto, int i, int j);
	
	
	/**
	 * 查询叉车离线扫描信息总条数 零担
	 * @author foss-heyongdong
	 * @Date 2014年1月8日 09:36:46
	 * @param queryTrayOfflineScanConditionDto
	 * @return Long
	 */
	Long querytrayOfflineScanTotal(
			QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto);
	
	/**
	 * 查询叉车离线扫描信息总条数 快递
	 * @author foss-heyongdong
	 * @Date 2014年1月8日 09:36:46
	 * @param queryTrayOfflineScanConditionDto
	 * @return Long
	 */
	Long querytrayOfflineScanTotalExpress(
			QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto);
	
	/**
	 * 导出叉车离线扫描信息
	 * @author foss-heyongdong
	 * @Date 2014年1月8日 10:54:51
	 * @param queryTrayOfflineScanConditionDto
	 * @return List
	 */
	List getTrayOfflineScanTaskInputStream(QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto);

	List getTrayOfflineScanTaskInputStreamExpress(
			QueryTrayOfflineScanConditionDto queryTrayOfflineScanConditionDto);

}
