package com.deppon.foss.module.settlement.common.api.server.dao;

import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;

/** 
 * 异步接口日志表dao
 * @author foss结算-306579-guoxinru 
 * @date 2016-6-17 上午11:46:58    
 */
public interface IEsbInterfaceLogDao {

	/**
	 * 接口日志表新增记录
	 */
	int addInterfaceLog(InterfaceLogEntity entity);
}
