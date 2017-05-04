package com.deppon.pda.bdm.module.foss.unload.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
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
 * 卸车任务Dao实现类
 * @author gaojia
 * @date Sep 7,2012 9:48:30 AM
 * @version 1.0
 * @since
 */

public class UnloadDao extends iBatis3DaoImpl implements IUnloadDao{

	@Override
	public void saveUnldScan(UnldScanEntity unldScanEntity) {
		getSqlSession().insert(getClass().getName()+".saveUnldScan", unldScanEntity);
	}

	@Override
	public void saveCaclUnldScan(UnloadCancelScanEntity param) {
		getSqlSession().insert(getClass().getName()+".saveCaclUnldScan", param);
	}

	@Override
	public void saveFnshUnldScan(FnshUnldTaskScanEntity param) {
		getSqlSession().insert(getClass().getName()+".saveFnshUnldScan", param);
	}

	@Override
	public boolean queryNoSyncScanMsgCount(String taskCode) {
		int count = 0;
		count = (Integer) getSqlSession().selectOne(getClass().getName() + ".queryNoSyncScanMsgCount", taskCode);
		return count>0?true:false;
	}

	@Override
	public void saveUnldSindingScan(PalletBindingEntity entity) {
		getSqlSession().insert(getClass().getName()+".saveUnldSindingScan", entity);
		
	}

	@Override
	public void saveUnldSindingScanDetail(BindingScanEntity scanEntity) {
		getSqlSession().insert(getClass().getName()+".saveUnldSindingScanDetail", scanEntity);		
	}

	@Override
	public void saveFinishUnldPalletBindingTask(FinishPalletBindingTask param) {
		getSqlSession().insert(getClass().getName()+".saveFinishUnldPalletBindingTask", param);		

	}

	@Override
	public void saveLoader(UnloaderAddDelModel param) {
		String userStr = JsonUtil.encapsulateJsonObject(param.getUserCodes());
		if(!StringUtils.isEmpty(userStr)){
			param.setWblCode(userStr.length()>4000?userStr.substring(0,3999):userStr);
		}
		getSqlSession().insert(getClass().getName()+".saveLoader", param);
	}

	@Override
	public void saveExcPalletBoundScan(ExcPalletBoundScanEntity param) {
		getSqlSession().insert(getClass().getName()+".saveExcPalletBoundScan", param);
	}

	@Override
	public void saveExcPalletBoundScanDetail(BindingScanEntity scanEntity) {
		getSqlSession().insert(getClass().getName()+".saveExcPalletBoundScanDetail", scanEntity);		
	}

	@Override
	public void saveScanExceReportMsg(
			UnldExceReportScanEntity unldExceReportScanEntity) {
		 getSqlSession().insert(getClass().getName()+".saveExceReportScanMsg", unldExceReportScanEntity);
		
	}
	
	@Override
	public void saveWeightandVolume(
			UnldWoodenRequireEntity woodenRequirePdaEntity) {
		getSqlSession().insert(getClass().getName()+".saveWeightandVolume", woodenRequirePdaEntity);
		
	}

	@Override
	public void saveWeightandNullVolume(
			UnldWoodenRequireEntity woodenRequirePdaEntity) {
		getSqlSession().insert(getClass().getName()+".saveWeightandNullVolume", woodenRequirePdaEntity);
	}

	@Override
	public void saveStockPosition(
			StockPositionList stockPositionList) {
		getSqlSession().insert(getClass().getName()+".saveStockPosition", stockPositionList);
	}
	
}