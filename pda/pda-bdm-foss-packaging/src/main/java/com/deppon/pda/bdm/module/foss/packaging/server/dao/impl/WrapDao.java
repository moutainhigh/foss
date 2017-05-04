package com.deppon.pda.bdm.module.foss.packaging.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.foss.packaging.server.dao.IWrapDao;
import com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedInInvtEntity;
import com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedOutInvtEntity;
import com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedScanEntity;

public class WrapDao extends iBatis3DaoImpl implements IWrapDao{
	/**
	 * 
	 * <p>TODO(包装入库)</p> 
	 * @author Administrator
	 * @date 2012-12-3 下午2:01:18
	 * @param param 
	 * @see com.deppon.pda.bdm.module.foss.packaging.server.dao.IWrapDao#saveWrapIninvtScan(com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedInInvtEntity)
	 */
	@Override
	public void saveWrapIninvtScan(WrapedInInvtEntity param) {
		getSqlSession().insert(getClass().getName()+".saveWrapIninvtScan", param);
	}
	/**
	 * 
	 * <p>TODO(包装出库)</p> 
	 * @author Administrator
	 * @date 2012-12-3 下午4:46:34
	 * @param param 
	 * @see com.deppon.pda.bdm.module.foss.packaging.server.dao.IWrapDao#saveWrapOutinvtScan(com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedOutInvtEntity)
	 */
	@Override
	public void saveWrapOutinvtScan(WrapedOutInvtEntity param) {
		getSqlSession().insert(getClass().getName()+".saveWrapOutinvtScan", param);
		
	}
	@Override
	public void saveWrapScan(WrapedScanEntity param) {
		getSqlSession().insert(getClass().getName()+".saveWrapScan", param);
	}

}
