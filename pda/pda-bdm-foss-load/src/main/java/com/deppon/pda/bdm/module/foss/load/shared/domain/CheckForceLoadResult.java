package com.deppon.pda.bdm.module.foss.load.shared.domain;
/**
 * 
 * TODO(检验强装返回实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-3-20 下午6:22:47,content:TODO </p>
 * @author Administrator
 * @date 2013-3-20 下午6:22:47
 * @since
 * @version
 */
public class CheckForceLoadResult {
	/**
	 * 是否可装（1：是 0：否）
	 */
	private String retStatus;
	/**
	 * 错误信息
	 */
	private String errMsg;
	/**
	 * 返回运单明细
	 */
	private Object detail;
	
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public Object getDetail() {
		return detail;
	}
	public void setDetail(Object detail) {
		this.detail = detail;
	}
	public String getRetStatus() {
		return retStatus;
	}
	public void setRetStatus(String retStatus) {
		this.retStatus = retStatus;
	}
}
