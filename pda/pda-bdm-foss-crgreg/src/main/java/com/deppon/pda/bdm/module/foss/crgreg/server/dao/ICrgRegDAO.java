package com.deppon.pda.bdm.module.foss.crgreg.server.dao;

import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ExcpCrgInInvtScanEntity;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ExcpCrgOutInvtScanEntity;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.InOutStockPostionScanEntity;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.SingleVoteScanEntity;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ValIninvtScanEntity;
import com.deppon.pda.bdm.module.foss.crgreg.shared.domain.ValOutinvtScanEntity;

/**  
 * @author chenliang       
 * @version 1.0     
 * @created 2012-9-8 上午10:06:18
 */

public interface ICrgRegDAO {

	/**
	 * 描述   保存单票入库扫描信息
	 * @param singleVoteScanEntity
	 */
	public void saveSingleVoteScan(SingleVoteScanEntity singleVoteScan);
	
	
	/**
	 * 
	 * <p>TODO(贵重物品入库)</p> 
	 * @author Administrator
	 * @date 2012-12-1 下午1:48:18
	 * @param param
	 * @see
	 */
	public void saveValIninvtScan(ValIninvtScanEntity param);

	/**
	 * 
	 * <p>TODO(贵重物品出库)</p> 
	 * @author Administrator
	 * @date 2012-12-1 下午1:52:21
	 * @param param
	 * @see
	 */
	public void saveValOutinvtScan(ValOutinvtScanEntity param);
	
	/**
	 * 
	 * <p>TODO(异常物品入库)</p> 
	 * @author Administrator
	 * @date 2012-12-1 下午3:04:59
	 * @param param
	 * @see
	 */
	public void saveExcpCrgInInvtScan(ExcpCrgInInvtScanEntity param);

	/**
	 * 
	 * <p>TODO(异常物品出库)</p> 
	 * @author Administrator
	 * @date 2012-12-1 下午3:08:25
	 * @param param
	 * @see
	 */
	public void saveExcpCrgOutInvtScan(ExcpCrgOutInvtScanEntity param);

	/**
	 * 
	 * <p>TODO(保存入库位扫描信息)</p> 
	 * @author Administrator
	 * @date 2013-07-18 下午3:08:25
	 * @param param
	 * @see
	 */
	public void saveUpdateStockPostionScan(InOutStockPostionScanEntity param);
	
}
