package com.deppon.pda.bdm.module.foss.unload.shared.domain;
/**
 * 
 * @description 这里用一句话描述这个类的作用
 * @version 1.0
 * @author wenwuneng 
 * @update 2013-8-12 下午4:47:55
 */
public class UnldPalletBingingResEntity {
	/**
	 * 绑定件数
	 */
	private String bindingNum;
	/**
	 * 托盘绑定唯一编号
	 */
	private String bindingNo;
	/**
	 * 目的站
	 */
	private String deptDestName;
	/**
	 * 备注
	 */
	private String remark;
	public String getBindingNum() {
		return bindingNum;
	}
	public void setBindingNum(String bindingNum) {
		this.bindingNum = bindingNum;
	}
	public String getBindingNo() {
		return bindingNo;
	}
	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}
	public String getDeptDestName() {
		return deptDestName;
	}
	public void setDeptDestName(String deptDestName) {
		this.deptDestName = deptDestName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
