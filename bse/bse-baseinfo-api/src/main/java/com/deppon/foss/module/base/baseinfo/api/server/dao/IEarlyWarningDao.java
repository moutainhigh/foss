package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EarlyWarningEntity;
/**
 * 
 * 提前预警线路DAO接口
 * @author 262036 - huangwei
 * @date 2015-8-19 下午6:23:43
 * @since
 * @version
 */
public interface IEarlyWarningDao {
	/**
	 * 
	 * 根据传入对象查询提前预警线路信息
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午6:24:05
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @see
	 */
	List<EarlyWarningEntity> queryEarlyWarningEntitysByCityCode(EarlyWarningEntity entity, int limit, int start);
	/**
	 * 
	 * 根据传入对象查询总记录数
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午6:24:25
	 * @param entity
	 * @return
	 * @see
	 */
	Long queryRecordCount(EarlyWarningEntity entity);
	/**
	 * 
	 * 批量插入导入的提前预警线路信息
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午6:24:40
	 * @param earlyWarningEntitys
	 * @return
	 * @see
	 */
	List<EarlyWarningEntity> insertEarlyWarnings(List<EarlyWarningEntity> earlyWarningEntitys);
	/**
	 * 批量作废提前预警信息
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午6:25:10
	 * @param earlyWarningEntity
	 * @return
	 * @see
	 */
	int deleteEarlyWarnings(EarlyWarningEntity earlyWarningEntity);
	
}
