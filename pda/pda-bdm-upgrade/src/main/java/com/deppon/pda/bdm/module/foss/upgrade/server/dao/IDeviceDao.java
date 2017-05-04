package com.deppon.pda.bdm.module.foss.upgrade.server.dao;

import com.deppon.pda.bdm.module.core.shared.domain.PdaDeviceEntity;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.DeviceParamEntity;

  
/**
 * 
 * TODO(操作设备表DAO)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-5-21 上午9:52:34,content:TODO </p>
 * @author chengang
 * @date 2013-5-21 上午9:52:34
 * @since
 * @version
 */

public interface IDeviceDao {
	/**
	 * <p>TODO(根据设备号获取对应的设备信息)</p> 
	 * @author chengang
	 * @date 2013-5-21 上午9:55:17
	 * @param pdaCode
	 * @return
	 * @see
	 */
	public PdaDeviceEntity getDeviceInfo(String pdaCode);
	
	/**
	 * 
	 * <p>TODO(根据设备号修改对应的设备信息)</p> 
	 * @author chengang
	 * @date 2013-5-21 上午10:17:16
	 * @param paramer
	 * @see
	 */
	public void updDeviceInfo(DeviceParamEntity parameter);
}
