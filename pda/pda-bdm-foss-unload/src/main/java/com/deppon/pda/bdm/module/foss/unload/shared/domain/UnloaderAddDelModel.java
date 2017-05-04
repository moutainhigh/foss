package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * TODO(增加删除理货员)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-3 上午10:41:14,content:TODO </p>
 * @author Administrator
 * @date 2012-12-3 上午10:41:14
 * @since
 * @version
 */
public class UnloaderAddDelModel extends ScanMsgEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 员工编号
	 */
	private List<UnloaderModel> userCodes;
	/**
	 * 状态
	 */
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<UnloaderModel> getUserCodes() {
		return userCodes;
	}
	public void setUserCodes(List<UnloaderModel> userCodes) {
		this.userCodes = userCodes;
	}
	
	
}
