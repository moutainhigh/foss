package com.deppon.pda.bdm.module.foss.unload.shared.domain;
/**
 * 
 * @description 获取托盘绑定任务请求实体
 * @version 1.0
 * @author wenwuneng 
 * @update 2013-8-12 下午4:47:55
 */
public class GetUnldPalletBingingReqEntity {
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 流水号
	 */
	private String serialNo;
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
}
