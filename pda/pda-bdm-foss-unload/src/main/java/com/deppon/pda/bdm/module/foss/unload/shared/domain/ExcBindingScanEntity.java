package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.List;

/**
 * 
 * TODO(叉车异常扫描运单信息)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2014-1-3 下午2:12:29,content:TODO </p>
 * @author Administrator
 * @date 2014-1-3 下午2:12:29
 * @since
 * @version
 */
public class ExcBindingScanEntity {
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 流水号
	 */
	private List<String> serial;
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public List<String> getSerial() {
		return serial;
	}
	public void setSerial(List<String> serial) {
		this.serial = serial;
	}
	
}
