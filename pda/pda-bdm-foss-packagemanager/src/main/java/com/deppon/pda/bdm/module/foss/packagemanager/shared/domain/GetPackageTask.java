package com.deppon.pda.bdm.module.foss.packagemanager.shared.domain;

/**
 * 
  * @ClassName GetLoadTask 
  * @Description TODO 获取装车指令实体
  * @author mt 
  * @date 2013-7-29 下午5:25:35
 */
public class GetPackageTask {
	//状态
	private String State;
	//包号	
	private String packageCode;
	//操作人	
	private String userCode;
	//目的地
	private String destination;
	//目的地名称
	private String destinationName;
	//包类型  直达包/普通包
	private String expressPackageType;
	
//	private String wayBillCode;
	
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
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
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getExpressPackageType() {
		return expressPackageType;
	}
	public void setExpressPackageType(String expressPackageType) {
		this.expressPackageType = expressPackageType;
	}
	
}
