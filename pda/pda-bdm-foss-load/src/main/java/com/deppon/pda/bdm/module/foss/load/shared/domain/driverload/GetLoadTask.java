package com.deppon.pda.bdm.module.foss.load.shared.domain.driverload;

import java.util.List;

/**
 * 
 * TODO(获取装车指令返回实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-3 上午10:30:54,content:TODO </p>
 * @author Administrator
 * @date 2012-12-3 上午10:30:54
 * @since
 * @version
 */
public class GetLoadTask {
	/**
	 * 月台号
	 */
	private String platformCode;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 派送单号
	 */
	private String deryListCode;
	/**
	 * 装车状态
	 */
	private String status;
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 装车类型
	 */
	private String loadType;
	/**
	 * 目的站编码
	 */
	private List<String> arrDeptCode;
	/**
	 * 目的站名称
	 */
	private List<String> arrDeptName;
	/**
	 * 快递员编码
	 */
	private String courierCode;
	/**
	 * 总件数
	 */
	private int picesTotal;
	/**
	 * 扫描件数
	 */
	private int picesScan;
	
	
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getDeryListCode() {
		return deryListCode;
	}
	public void setDeryListCode(String deryListCode) {
		this.deryListCode = deryListCode;
	}
	public String getPlatformCode() {
		return platformCode;
	}
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	public List<String> getArrDeptCode() {
		return arrDeptCode;
	}
	public void setArrDeptCode(List<String> arrDeptCode) {
		this.arrDeptCode = arrDeptCode;
	}
	public List<String> getArrDeptName() {
		return arrDeptName;
	}
	public void setArrDeptName(List<String> arrDeptName) {
		this.arrDeptName = arrDeptName;
	}
	public String getCourierCode() {
		return courierCode;
	}
	public void setCourierCode(String courierCode) {
		this.courierCode = courierCode;
	}
	public int getPicesTotal() {
		return picesTotal;
	}
	public void setPicesTotal(int picesTotal) {
		this.picesTotal = picesTotal;
	}
	public int getPicesScan() {
		return picesScan;
	}
	public void setPicesScan(int picesScan) {
		this.picesScan = picesScan;
	}
}	
