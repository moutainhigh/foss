package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;

/**
 * TODO电子运单正反扫实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:200689,date:2015-1-27 下午6:04:06,content:TODO </p>
 * @author 200689
 * @date 2015-1-27 下午6:04:06
 * @since
 * @version
 */
public class ScanOrCaclScanEntity implements Serializable{

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -7351690632556832981L;
	
    
	//任务号
	private String  taskCode;
	//运单号+流水号
	private String wblCodeLabelCode;
	//扫描类型
	private String scanType;
	
	//扫描人工号
	private String scanUser;
	//重量
	private double weight;
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getWblCodeLabelCode() {
		return wblCodeLabelCode;
	}
	public void setWblCodeLabelCode(String wblCodeLabelCode) {
		this.wblCodeLabelCode = wblCodeLabelCode;
	}
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public String getScanUser() {
		return scanUser;
	}
	public void setScanUser(String scanUser) {
		this.scanUser = scanUser;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
}
