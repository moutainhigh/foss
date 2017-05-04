package com.deppon.pda.bdm.module.foss.packagemanager.shared.domain;
/**
 * 
  * @ClassName SerialNo 
  * @Description TODO 刷新建包货物明细-流水号列表
  * @author mt 
  * @date 2013-7-30 上午9:10:44
 */
public class SerialNo {
	//流水号
	String serialNo;
	//扫描状态
	String state;
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}