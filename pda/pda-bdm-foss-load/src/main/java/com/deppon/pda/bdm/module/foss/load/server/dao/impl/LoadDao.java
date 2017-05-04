package com.deppon.pda.bdm.module.foss.load.server.dao.impl;


import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.CheckLoadSeals;
import com.deppon.pda.bdm.module.foss.load.shared.domain.FnshLoadTaskScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdLoadCancelScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdLoadScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdScanBusinessErrorLog;
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoadCancelScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoadLabelScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoadScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoaderAddDelModel;
import com.deppon.pda.bdm.module.foss.load.shared.domain.NoLoadRemarkScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.SealDestDetail;
import com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload.DeryCancelScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload.DeryLoadScanEntity;

/**
 * 装车模块接口实现
 * @author gaojia
 * @date Sep 10,2012 14:42:30 PM
 * @version 1.0
 * @since
 */
public class LoadDao extends iBatis3DaoImpl implements ILoadDao{

	/**
	 * 保存装车扫描
	 * @author gaojia
	 * @date Sep 10,2012 14:42:30 PM
	 * @version 1.0
	 * @since
	 */
	@Override
	public void saveLoadScan(LoadScanEntity loadScanEntity) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getClass().getName()+".saveLoadScan", loadScanEntity);
	}
	/**
	 * 保存完成装车任务信息
	 * @author gaojia
	 * @date Sep 10,2012 14:42:30 PM
	 * @version 1.0
	 * @since
	 */
	@Override
	public void saveFnshLoadTaskScan(
			FnshLoadTaskScanEntity fnshLoadTaskScanEntity) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getClass().getName()+".saveFnshLoadTaskScan", fnshLoadTaskScanEntity);
	}
	/**
	 *保存撤销装车任务信息
	 * @author gaojia
	 * @date Sep 11,2012 14:42:30 PM
	 * @version 1.0
	 * @since
	 */
	@Override
	public void saveLoadCaclScan(LoadCancelScanEntity loadCancelScanEntity) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getClass().getName()+".saveLoadCaclScan", loadCancelScanEntity);
	}
	
	
	/**
	 * 保存装车封签信息
	 * @author gaojia
	 * @date Sep 12,2012 10:19:30 AM
	 * @version 1.0
	 * @since
	 */
	@Override
	public void saveLoadLabelScan(LoadLabelScanEntity loadLabelScanEntity) {
		getSqlSession().insert(getClass().getName()+".saveLoadLabelScan", loadLabelScanEntity);
	}

	/**
	 * 保存封签检查信息
	 * @author cbb
	 * @date 2014-03-06 08:49:00
	 * @version 1.0
	 * @since
	 */	
	@Override
	public void saveCheckSeals(CheckLoadSeals checkLoadSeals) {
		getSqlSession().insert(getClass().getName()+".saveCheckSeals", checkLoadSeals);
	}
	
	/**
	 * 保存检查封签对应关系
	 * @param scanMsgId
	 * @return
	 */
	@Override
	public void saveSealDestDetail(SealDestDetail sealDestDetail) {
		getSqlSession().insert(getClass().getName() + ".sealDestDetail", sealDestDetail);
	}
	/**
	 * 
	 * <p>TODO(保存未装车备注信息)</p> 
	 * @author Administrator
	 * @date 2012-12-3 上午10:54:02
	 * @param param 
	 * @see com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao#saveNoLoadScan(com.deppon.pda.bdm.module.foss.load.shared.domain.NoLoadRemarkScanEntity)
	 */
	@Override
	public void saveNoLoadScan(NoLoadRemarkScanEntity param) {
		getSqlSession().insert(getClass().getName() + ".saveNoLoadScan", param);
	}
	@Override
	public boolean queryNoSyncScanMsgCount(String taskCode) {
		int count = 0;
		count = (Integer) getSqlSession().selectOne(getClass().getName() + ".queryNoSyncScanMsgCount", taskCode);
		return count>0?true:false;
	}

	/** 
	* @Description: TODO保存快递装车扫描信息
	* @param loadCancelScanEntity
	* @return void    
	* @author cbb
	* @date 2013-7-26 下午5:06:41
	*/ 
	@Override
	public void saveKdLoadScan(KdLoadScanEntity loadScanEntity) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getClass().getName()+".saveKdLoadScan", loadScanEntity);
	}	
	
	/** 
	* @Description: TODO保存快递撤销装车扫描信息
	* @param loadCancelScanEntity
	* @return void    
	* @author cbb
	* @date 2013-7-26 上午10:32:15
	*/ 	
	@Override
	public void saveKdLoadCaclScan(KdLoadCancelScanEntity loadCancelScanEntity) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getClass().getName()+".saveKdLoadCaclScan", loadCancelScanEntity);
	}
	
	@Override
	public void saveKdScanBusinessErrorLog(
			KdScanBusinessErrorLog kdScanBusinessErrorLog) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getClass().getName()+".saveKdScanBusinessErrorLog", kdScanBusinessErrorLog);
	}
	
	@Override
	public void deleteKdScanBusinessErrorLog(
			String taskCode) {
		// TODO Auto-generated method stub
		getSqlSession().delete(getClass().getName()+".deleteKdScanBusinessErrorLog", taskCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KdScanBusinessErrorLog> selectKdScanBusinessErrorLogByTaskCode(
			KdScanBusinessErrorLog kdScanBusinessErrorLog) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getClass().getName()+".selectKdScanBusinessErrorLogByTaskCode", kdScanBusinessErrorLog);
	}
	@Override
	public void saveLoader(LoaderAddDelModel param) {
		String userStr = JsonUtil.encapsulateJsonObject(param.getUserCodes());
		if(!StringUtils.isEmpty(userStr)){
			param.setWblCode(userStr.length()>4000?userStr.substring(0,3999):userStr);
		}
		getSqlSession().insert(getClass().getName()+".saveLoader", param);
	}
	/**
	 * 二程接驳 保存派件交接扫描信息
	 */
	@Override
	public void saveDeryLoadScan(DeryLoadScanEntity deryLoadScanEntity) {
	  getSqlSession().insert(getClass().getName()+".saveDeryLoadScan", deryLoadScanEntity);		
	}
	/**
	 * 二程接驳 保存派件交接扫描信息--撤销
	 */
	@Override
	public void saveDeryCancelScan(DeryCancelScanEntity deryCancelScanEntity) {
		getSqlSession().insert(getClass().getName()+".saveDeryCancelScan", deryCancelScanEntity);			
	}
	/**
	 * 二程接驳  保存司机-理货员装车扫描数据
	 */
	@Override
	public void saveDriverLoadScan(LoadScanEntity loadScanEntity) {
		getSqlSession().insert(getClass().getName()+".saveDriverLoadScan", loadScanEntity);
	}
	/**
	 * 二程接驳  保存司机-理货员装车扫描数据 --撤销
	 */
	@Override
	public void saveDriverLoadCaclScan(LoadCancelScanEntity loadCancelScanEntity) {
		getSqlSession().insert(getClass().getName()+".saveDriverLoadCaclScan", loadCancelScanEntity);
	}
}
