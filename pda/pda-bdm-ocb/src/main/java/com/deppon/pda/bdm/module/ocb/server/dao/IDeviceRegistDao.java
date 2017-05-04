package com.deppon.pda.bdm.module.ocb.server.dao;

import com.deppon.pda.bdm.module.ocb.shared.domain.DeviceRegistBean;


public interface IDeviceRegistDao {

	void addDevice(DeviceRegistBean device);
	
	//检查设备号是否存在
	boolean checkDeviceIsExist(String dvcCode);
	
}
