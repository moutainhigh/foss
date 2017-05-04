package com.deppon.pda.bdm.module.foss.clear.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.foss.clear.server.dao.IClearDao;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.ClearExceReportScanEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.ClearScanEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.FindGoodsAdminScanEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.FnshClrTaskScanEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.PackageClearScanEntity;

public class ClearDao extends iBatis3DaoImpl implements IClearDao{

	@Override
	public void saveScanMsg(ClearScanEntity clearScanEntity) {
		getSqlSession().insert(ClearDao.class.getName()+".saveScanMsg", clearScanEntity);
	}

	@Override
	public void saveFishClearMsg(FnshClrTaskScanEntity saveFishClearMsg) {
		getSqlSession().insert(ClearDao.class.getName()+".saveFishClearMsg", saveFishClearMsg);
	}

	@Override
	public boolean queryNoSyncScanMsgCount(String taskCode) {
		int count = 0;
		count = (Integer) getSqlSession().selectOne(getClass().getName() + ".queryNoSyncScanMsgCount", taskCode);
		return count>0?true:false;
	}

    @Override
    public void saveScanExceReportMsg(ClearExceReportScanEntity clearExceReportScanEntity) {
        getSqlSession().insert(ClearDao.class.getName()+".saveExceReportScanMsg", clearExceReportScanEntity);       
    }

	@Override
	public void savePackageScanMsg(PackageClearScanEntity clearScanEntity) {
		getSqlSession().insert(ClearDao.class.getName()+".savePackageScanMsg", clearScanEntity);		
	}

	/**
     * 保存找货扫描数据 245955 2015/08/14
     */
	@Override
	public void saveFindGoodsAdminScan(
			FindGoodsAdminScanEntity findGoodsAdminScanEntity) {
		getSqlSession().insert(ClearDao.class.getName()+".saveFindGoodsAdminScan", findGoodsAdminScanEntity);
	}
	
}
