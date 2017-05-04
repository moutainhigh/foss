package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

/**
 * 
 * TODO(修改设备表中当前版本号与实际版本号实体类)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-5-21 上午9:33:11,content:TODO </p>
 * @author chengang
 * @date 2013-5-21 上午9:33:11
 * @since
 * @version
 */
public class DeviceParamEntity {
	/**
	 * 设备号
	 */
	public String pdaCode;
	
	/**
	 * 当前设备版本号
	 */
	public String current_device_version;
	
	/**
	 * 版本表中的版本号
	 */
	public String newest_version;
	
	/**
	 * 当前版本号转换成long类型
	 */
	public long current_vesion_to_long;
	
	/**
	 * 需更新版本号转换成long类型
	 */
	public long newest_vesion_to_long;
	
	
	

	public String getPdaCode() {
		return pdaCode;
	}

	public void setPdaCode(String pdaCode) {
		this.pdaCode = pdaCode;
	}

	public String getCurrent_device_version() {
		return current_device_version;
	}

	public void setCurrent_device_version(String current_device_version) {
		this.current_device_version = current_device_version;
	}

	public String getNewest_version() {
		return newest_version;
	}

	public void setNewest_version(String newest_version) {
		this.newest_version = newest_version;
	}

	public long getCurrent_vesion_to_long() {
		return current_vesion_to_long;
	}

	public void setCurrent_vesion_to_long(long current_vesion_to_long) {
		this.current_vesion_to_long = current_vesion_to_long;
	}

	public long getNewest_vesion_to_long() {
		return newest_vesion_to_long;
	}

	public void setNewest_vesion_to_long(long newest_vesion_to_long) {
		this.newest_vesion_to_long = newest_vesion_to_long;
	}
	
	

}
