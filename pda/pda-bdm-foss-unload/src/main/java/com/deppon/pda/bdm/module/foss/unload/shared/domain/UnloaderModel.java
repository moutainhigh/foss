package com.deppon.pda.bdm.module.foss.unload.shared.domain;
/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-3-20 下午6:39:21,content:TODO </p>
 * @author Administrator
 * @date 2013-3-20 下午6:39:21
 * @since
 * @version
 */
public class UnloaderModel {
	/**理货员编号*/
	private String userCode;
	/**标识*/
	private String flag;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
