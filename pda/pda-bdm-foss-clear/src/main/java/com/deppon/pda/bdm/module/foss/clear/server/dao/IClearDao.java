package com.deppon.pda.bdm.module.foss.clear.server.dao;

import com.deppon.pda.bdm.module.foss.clear.shared.domain.ClearExceReportScanEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.ClearScanEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.FindGoodsAdminScanEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.FnshClrTaskScanEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.PackageClearScanEntity;
/**
 * 
  * @ClassName IClearDao 
  * @Description TODO 
  * @author xujun cometzb@126.com 
  * @date 2012-12-27
 */
public interface IClearDao {
	void saveScanMsg(ClearScanEntity clearScanEntity);
	void savePackageScanMsg(PackageClearScanEntity clearScanEntity);
	void saveFishClearMsg(FnshClrTaskScanEntity param);
	boolean queryNoSyncScanMsgCount(String taskCode);
    void saveScanExceReportMsg(ClearExceReportScanEntity clearExceReportScanEntity);
    /**
     * 保存找货扫描数据 245955 2015/08/14
     */
    void saveFindGoodsAdminScan(FindGoodsAdminScanEntity findGoodsAdminScanEntity);
}
