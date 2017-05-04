package com.deppon.foss.module.settlement.common.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IFossConfigEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;

/**
 *  esb地址配置DAO
 *  @author 269044
 *  @date 2015-11-04
 */
public class FossConfigEntityDao extends iBatis3DaoImpl 
        implements IFossConfigEntityDao {

	//命名空间路径
	private static final String NAMESPACE = "foss.stl.FossConfigEntityDao.";
	
	/**
	 * 查询esb地址信息
	 * @author 269044
	 * @date 2015-11-04 
	 * @param serverCode
	 * @return FossConfigEntity
	 */
	@Override
	public FossConfigEntity queryFossConfigEntityByServerCode(String serverCode) {
		return (FossConfigEntity) this.getSqlSession().selectOne( 
				NAMESPACE + "queryFossConfigEntityByServerCode", serverCode);
	}

}
