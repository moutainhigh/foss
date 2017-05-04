package com.deppon.pda.bdm.module.foss.packaging.server.dao;

import com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedInInvtEntity;
import com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedOutInvtEntity;
import com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedScanEntity;

public interface IWrapDao {
	/**
	 * 
	 * <p>TODO(包装入库)</p> 
	 * @author Administrator
	 * @date 2012-12-3 下午2:01:02
	 * @param param
	 * @see
	 */
	public void saveWrapIninvtScan(WrapedInInvtEntity param);
	/**
	 * 
	 * <p>TODO(包装出库)</p> 
	 * @author Administrator
	 * @date 2012-12-3 下午4:46:10
	 * @param param
	 * @see
	 */
	public void saveWrapOutinvtScan(WrapedOutInvtEntity param);
	/**
	 * 
	 * <p>TODO(保存打包扫描信息)</p> 
	 * @author Administrator
	 * @date 2012-12-3 下午8:14:58
	 * @param param
	 * @see
	 */
	public void saveWrapScan(WrapedScanEntity param);

}
