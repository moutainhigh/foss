package com.deppon.pda.bdm.module.foss.packagemanager.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;
import com.deppon.pda.bdm.module.foss.packagemanager.server.dao.IPackageDao;

public class PackageDaoImpl extends iBatis3DaoImpl implements IPackageDao{
	
	@Override
	public void saveCaclPackageScan(ScanMsgEntity scanMsgEntity) {
		getSqlSession().insert(getClass().getName()+".saveCaclPackageScan", scanMsgEntity);
	}

	@Override
	public void savePackageScan(ScanMsgEntity scanMsgEntity) {
		getSqlSession().insert(getClass().getName()+".savePackageScan", scanMsgEntity);
	}

	@Override
	public boolean queryNoSyncScanMsgCount(String taskCode) {
		// TODO Auto-generated method stub
		int count = 0;
		count = (Integer) getSqlSession().selectOne(getClass().getName() + ".queryNoSyncScanMsgCount", taskCode);
		return count>0?true:false;
	}
}