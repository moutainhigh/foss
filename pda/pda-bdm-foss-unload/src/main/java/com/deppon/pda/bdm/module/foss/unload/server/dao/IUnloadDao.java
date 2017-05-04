package com.deppon.pda.bdm.module.foss.unload.server.dao;

import com.deppon.pda.bdm.module.foss.unload.shared.domain.BindingScanEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.ExcPalletBoundScanEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.FinishPalletBindingTask;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.FnshUnldTaskScanEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.PalletBindingEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.StockPositionList;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnldExceReportScanEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnldScanEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnldWoodenRequireEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnloadCancelScanEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnloaderAddDelModel;

/**
 * 卸车任务Dao接口
 * @author gaojia
 * @date Sep 7,2012 9:48:30 AM
 * @version 1.0
 * @since
 */
public interface IUnloadDao {
	
	
	
	
	/**
	 * 保存卸车扫描信息
	 * @author gaojia
	 * @date Sep 7,2012 9:48:30 AM
	 * @param unldScanEntity 卸车扫描信息
	 * @version 1.0
	 * 
	 */
	public void saveUnldScan(UnldScanEntity unldScanEntity);
	
	/**
	 * 保存撤销卸车扫描
	 * @author gaojia
	 * @date Sep 9,2012 15:07:30 PM
	 * @param 撤销卸车扫描
	 * @version 1.0
	 * 
	 */

	public void saveCaclUnldScan(UnloadCancelScanEntity param);

	public void saveFnshUnldScan(FnshUnldTaskScanEntity param);

	public boolean queryNoSyncScanMsgCount(String taskCode);

	public void saveUnldSindingScan(PalletBindingEntity entity);

	public void saveUnldSindingScanDetail(BindingScanEntity scanEntity);

	public void saveFinishUnldPalletBindingTask(FinishPalletBindingTask param);

	public void saveLoader(UnloaderAddDelModel param);
	/**
	 * 
	 * <p>TODO(保存叉车异常绑定)</p> 
	 * @author Administrator
	 * @date 2014-1-13 上午10:53:18
	 * @param param
	 * @see
	 */
	public void saveExcPalletBoundScan(ExcPalletBoundScanEntity param);
	/**
	 * <p>TODO(保存异常叉车绑定明细)</p> 
	 * @author Administrator
	 * @date 2014-1-13 上午11:15:36
	 * @param scanEntity
	 * @see
	 */
	public void saveExcPalletBoundScanDetail(BindingScanEntity scanEntity);

	
	void saveScanExceReportMsg(UnldExceReportScanEntity unldExceReportScanEntity);
	
	/**   
	 * @Title: saveWeightandVolume  
	 * @Description: (保存集中开单补录重量体积)
	 * @param @param unldWoodenRequireEntity    设定文件   
	 * @return void    返回类型  
	 * @throws  
	 */
	public  void saveWeightandVolume(UnldWoodenRequireEntity woodenRequirePdaEntity);
	
	
	/**   
	 * @Title: saveWeightandVolume  
	 * @Description: (保存集中开单补录重量无提交明细)
	 * @param @param unldWoodenRequireEntity    设定文件   
	 * @return void    返回类型  
	 * @throws  
	 */
	public  void saveWeightandNullVolume(UnldWoodenRequireEntity woodenRequirePdaEntity);
	
	
	/**   
	 * @Title: saveStockPosition  
	 * @Description: (保存货区定位)
	 * @param @param uploadStockPositionEntity    设定文件   
	 * @return void    返回类型  
	 * @throws  
	 */
	public  void saveStockPosition(StockPositionList stockPositionList);
	
	
}
