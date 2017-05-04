package com.deppon.foss.module.settlement.common.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IEsbInterfaceLogDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;

/** 
 * 异步接口日志表dao
 * @author foss结算-306579-guoxinru 
 * @date 2016-6-17 上午11:49:21    
 */
public class EsbInterfaceLogDao extends iBatis3DaoImpl  implements IEsbInterfaceLogDao {

	//命名空间路径
	private static final String NAMESPACE = "foss.stl.EsbInterfaceLogEntity.";
	/**
	 * 接口日志表新增记录
	 */
	@Override
	public int addInterfaceLog(InterfaceLogEntity entity) {
		 return this.getSqlSession().insert(NAMESPACE+"addInterfaceLog", entity);
	}
	
}
