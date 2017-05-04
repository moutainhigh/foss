package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.List;
/**
 * 
 * TODO(获取卸车指令实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-3-20 下午6:36:51,content:TODO </p>
 * @author Administrator
 * @date 2013-3-20 下午6:36:51
 * @since
 * @version
 */
public class GetUnldTask {
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 月台号
	 */
	private String platformNo;
	/**
	 * 单据
	 */
	private List<UnldBillModel> billNos;
	/**
	 * 理货员
	 */
	private List<String> userCodes;
	
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public List<UnldBillModel> getBillNos() {
		return billNos;
	}
	public void setBillNos(List<UnldBillModel> billNos) {
		this.billNos = billNos;
	}
	public List<String> getUserCodes() {
		return userCodes;
	}
	public void setUserCodes(List<String> userCodes) {
		this.userCodes = userCodes;
	}
	

}
