package com.deppon.pda.bdm.module.foss.packagemanager.server.dao;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public interface IPackageDao {
	public void saveCaclPackageScan(ScanMsgEntity scanMsgEntity);
	
	public void savePackageScan(ScanMsgEntity scanMsgEntity);
	
	public boolean queryNoSyncScanMsgCount(String taskCode);
}
