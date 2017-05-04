package com.deppon.pda.bdm.module.foss.login.server.dao;

import java.util.List;

import com.deppon.pda.bdm.module.foss.login.shared.domain.DeviceBundleEntity;

/*
 * 查询设备绑定信息接口
 *@author 354682 
 *@date 2016-11-26
 * 
 */
public interface IDeviceBundleDao {
	
	/*
	 * 设备首次登陆进行自动将车牌号与设备号绑定
	 */
	public void boundDvcTruck(DeviceBundleEntity deviceBundleEntity);
	 
	/*
	 * 查询设备绑定信息
	 * 
	 */
	public List<DeviceBundleEntity> queryDeviceBundleMsg(String devCode,String truckCode);

	/**查询顶级车队名称
	 * @param orgCode
	 * @return
	 */
	public String findtopFleet(String orgCode);
	

}
