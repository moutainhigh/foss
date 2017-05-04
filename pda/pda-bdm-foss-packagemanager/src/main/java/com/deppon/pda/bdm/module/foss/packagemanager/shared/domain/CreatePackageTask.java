package com.deppon.pda.bdm.module.foss.packagemanager.shared.domain;

import java.util.Date;
/**
 * 
  * @ClassName ExpressPackage 
  * @Description TODO 快递创建任务实体
  * @author mt 
  * @date 2013-7-29 下午3:13:49
 */
public class CreatePackageTask {
	//包号
	private String packageCode;
	//建包员工号
	private String userCode;
	//到达部门
	private String arriveOrg;
	//设备号
	private String deviceNo;
	//建包时间
	private Date createTime;
	//出发部门
	private String startOrg;
	//快递建包类型
	//"THROUGH_ARRIVE";//直达包
	//"NORMAL_ARRIVE";//普通包
	//"SECONDCAR_ARRIVE";二程接驳建包
	//"AIRTHROUGH_ARRIVE";空运直达包
	private String expressPackageType;

	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getArriveOrg() {
		return arriveOrg;
	}
	public void setArriveOrg(String arriveOrg) {
		this.arriveOrg = arriveOrg;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStartOrg() {
		return startOrg;
	}
	public void setStartOrg(String startOrg) {
		this.startOrg = startOrg;
	}
	public String getExpressPackageType() {
		return expressPackageType;
	}
	public void setExpressPackageType(String expressPackageType) {
		this.expressPackageType = expressPackageType;
	}
	
}
