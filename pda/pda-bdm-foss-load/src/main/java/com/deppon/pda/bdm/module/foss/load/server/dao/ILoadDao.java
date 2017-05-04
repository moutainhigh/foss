package com.deppon.pda.bdm.module.foss.load.server.dao;


import java.util.List;

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
 * 装车模块接口
 * @author gaojia
 * @date Sep 10,2012 14:42:30 PM
 * @version 1.0
 * @since
 */
public interface ILoadDao {
	/**
	 * 保存装车扫描
	 * @author gaojia
	 * @date Sep 10,2012 14:42:30 PM
	 * @version 1.0
	 * @since
	 */
	public void saveLoadScan(LoadScanEntity loadScanEntity);
	/**
	 *保存完成装车任务信息
	 * @author gaojia
	 * @date Sep 11,2012 14:42:30 PM
	 * @version 1.0
	 * @since
	 */
	public void saveFnshLoadTaskScan(
			FnshLoadTaskScanEntity fnshLoadTaskScanEntity);
	/**
	 *保存撤销装车扫描信息
	 * @author gaojia
	 * @date Sep 11,2012 14:42:30 PM
	 * @version 1.0
	 * @since
	 */
	public void saveLoadCaclScan(LoadCancelScanEntity loadCancelScanEntity);
	/**
	 * 保存装车封签信息
	 * @author gaojia
	 * @date Sep 12,2012 10:19:30 AM
	 * @version 1.0
	 * @since
	 */
	public void saveLoadLabelScan(LoadLabelScanEntity loadLabelScanEntity);
	
	/**
	 * 保存封签检查信息
	 * @author cbb
	 * @date 2014-03-06 08:49:00
	 * @version 1.0
	 * @since
	 */	
	public void saveCheckSeals(CheckLoadSeals checkLoadSeals);
	
	/**
	 * 保存检查封签对应关系
	 * @param scanMsgId
	 * @return
	 */
	public void saveSealDestDetail(SealDestDetail sealDestDetail);
	/**
	 * 
	 * <p>TODO(保存未装车备注信息)</p> 
	 * @author Administrator
	 * @date 2012-12-3 上午10:52:17
	 * @param param
	 * @see
	 */
	public void saveNoLoadScan(NoLoadRemarkScanEntity param);
	
	
	public boolean queryNoSyncScanMsgCount(String taskCode);
	
	/** 
	* @Description: TODO保存快递撤销装车扫描信息
	* @param loadCancelScanEntity
	* @return void    
	* @author cbb
	* @date 2013-7-26 上午10:32:15
	*/ 	
	public void saveKdLoadCaclScan(KdLoadCancelScanEntity loadCancelScanEntity);
	
	/** 
	* @Description: TODO保存快递装车扫描信息
	* @param loadCancelScanEntity
	* @return void    
	* @author cbb
	* @date 2013-7-26 下午5:06:41
	*/ 
	public void saveKdLoadScan(KdLoadScanEntity loadScanEntity);
	
	
	/** 
	* @Description: TODO保存快递装车扫描出现业务异常信息
	* @param KdScanBusinessErrorLog
	* @return void    
	* @author cbb
	* @date 2013-9-2 下午5:06:41
	*/ 
	public void saveKdScanBusinessErrorLog(KdScanBusinessErrorLog kdScanBusinessErrorLog);
	
	/** 
	* @Description: TODO删除快递装车扫描出现业务异常信息
	* @param KdScanBusinessErrorLog
	* @return void    
	* @author cbb
	* @date 2013-9-2 下午5:06:41
	*/ 
	public void deleteKdScanBusinessErrorLog(String taskCode);
	
	/** 
	* @Description: TODO查询快递装车扫描出现业务异常信息
	* @param KdScanBusinessErrorLog
	* @return List<KdScanBusinessErrorLog>    
	* @author cbb
	* @date 2013-9-2 下午5:06:41
	*/ 
	public List<KdScanBusinessErrorLog> selectKdScanBusinessErrorLogByTaskCode(KdScanBusinessErrorLog kdScanBusinessErrorLog);
	/**
	 * 
	 * <p>TODO(保存添加理货员信息)</p> 
	 * @author Administrator
	 * @date 2013-11-12 上午11:20:43
	 * @param taskCode
	 * @param unloaders
	 * @param parse
	 * @param status
	 * @see
	 */
	public void saveLoader(LoaderAddDelModel param);
	/**
	 * 二程接驳  保存派件扫描数据
	 */
	public void saveDeryLoadScan(DeryLoadScanEntity deryLoadScanEntity);
	
	/**
	 * 二程接驳  保存派件扫描数据 --撤销
	 */
	public void saveDeryCancelScan(DeryCancelScanEntity deryCancelScanEntity);
	
	/**
	 * 二程接驳  保存司机-理货员装车扫描数据
	 */
	public void saveDriverLoadScan(LoadScanEntity loadScanEntity);
	
	/**
	 * 二程接驳  保存司机-理货员装车扫描数据 --撤销
	 */
	public void saveDriverLoadCaclScan(LoadCancelScanEntity loadCancelScanEntity);
}
