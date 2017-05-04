package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EarlyWarningEntity;
/**
 * 
 * 提前预警线路Service接口
 * @author 262036 - huangwei
 * @date 2015-8-19 下午6:18:25
 * @since
 * @version
 */
public interface IEarlyWarningService  extends IService{

	/**
	 * 
	 * 根据传入对象查询提前预警线路信息
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午6:18:49
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
	 * @date 2015-8-19 下午6:18:53
	 * @param entity
	 * @return
	 * @see
	 */
	Long queryRecordCount(EarlyWarningEntity entity);
	/**
	 * 
	 * 导入提前预警线路信息
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午6:18:58
	 * @param earlyWarningEntity
	 * @see
	 */
	void importEarlyWarnings(List<EarlyWarningEntity> earlyWarningEntity);
	/**
	 * 
	 * 此接口供接送货调用，根据传入出发网点编码和到达网点编码查询提前预警线路信息，如有符合的信息则返回兑现率，如查询没有记录返回null
	 * @author 262036 - huangwei
	 * @date 2015-8-19 下午6:19:09
	 * @param startOrgCode 出发网点编码
	 * @param endOrgCode 到达网点编码
	 * @return
	 * @see
	 */
	String searchEarlyWarningInfo(String startOrgCode, String endOrgCode);
	
}
