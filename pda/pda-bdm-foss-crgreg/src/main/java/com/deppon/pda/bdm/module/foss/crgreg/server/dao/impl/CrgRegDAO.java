package com.deppon.pda.bdm.module.foss.crgreg.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.foss.crgreg.server.dao.ICrgRegDAO;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ExcpCrgInInvtScanEntity;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ExcpCrgOutInvtScanEntity;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.InOutStockPostionScanEntity;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.SingleVoteScanEntity;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ValIninvtScanEntity;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ValOutinvtScanEntity;

/**  
 * 单票入库扫描DAO实现类
 * @author chenliang       
 * @version 1.0     
 * @created 2012-9-8 上午10:23:22
 */
public class CrgRegDAO extends iBatis3DaoImpl implements ICrgRegDAO {

	
	/**
	 * 描述   保存单票入库扫描信息
	 * @param singleVoteScanEntity
	 */
	@Override
	public void saveSingleVoteScan(SingleVoteScanEntity singleVoteScan) {
		getSqlSession().insert(getClass().getName() + ".saveSingleVoteScan", singleVoteScan);
	}
	
	/**
	 * 
	 * <p>TODO(保存贵重物品入库信息)</p> 
	 * @author Administrator
	 * @date 2012-12-1 下午1:48:56
	 * @param param 
	 * @see com.deppon.pda.bdm.module.foss.crgreg.server.dao.ICrgRegDAO#saveValIninvtScan(com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ValIninvtScanEntity)
	 */
	@Override
	public void saveValIninvtScan(ValIninvtScanEntity param) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getClass().getName() + ".saveValIninvtScan", param);
	}
	/**
	 * 
	 * <p>TODO(贵重物品出库)</p> 
	 * @author Administrator
	 * @date 2012-12-1 下午1:52:45
	 * @param param 
	 * @see com.deppon.pda.bdm.module.foss.crgreg.server.dao.ICrgRegDAO#saveValOutinvtScan(com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ValOutinvtScanEntity)
	 */
	@Override
	public void saveValOutinvtScan(ValOutinvtScanEntity param) {
		getSqlSession().insert(getClass().getName() + ".saveValOutinvtScan", param);
	}
	/**
	 * 
	 * <p>TODO(异常物品入库)</p> 
	 * @author Administrator
	 * @date 2012-12-1 下午3:05:24
	 * @param param 
	 * @see com.deppon.pda.bdm.module.foss.crgreg.server.dao.ICrgRegDAO#saveExcpCrgInInvtScan(com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ExcpCrgInInvtScanEntity)
	 */
	@Override
	public void saveExcpCrgInInvtScan(ExcpCrgInInvtScanEntity param) {
		getSqlSession().insert(getClass().getName() + ".saveExcpCrgInInvtScan", param);
	}
	/**
	 * 
	 * <p>TODO(异常物品出库)</p> 
	 * @author Administrator
	 * @date 2012-12-1 下午3:08:52
	 * @param param 
	 * @see com.deppon.pda.bdm.module.foss.crgreg.server.dao.ICrgRegDAO#saveExcpCrgOutInvtScan(com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ExcpCrgOutInvtScanEntity)
	 */
	@Override
	public void saveExcpCrgOutInvtScan(ExcpCrgOutInvtScanEntity param) {
		getSqlSession().insert(getClass().getName() + ".saveExcpCrgOutInvtScan", param);
	}

	@Override
	public void saveUpdateStockPostionScan(InOutStockPostionScanEntity param) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getClass().getName() + ".saveUpdateStockPostionScan", param);
	}

}
