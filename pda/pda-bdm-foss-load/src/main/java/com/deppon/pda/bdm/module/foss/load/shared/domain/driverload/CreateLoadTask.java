package com.deppon.pda.bdm.module.foss.load.shared.domain.driverload;

import java.util.Date;
import java.util.List;

import com.deppon.pda.bdm.module.foss.load.shared.domain.LoaderModel;

/**
 * 司机PDA接驳装车实体类
 * @ClassName KdCreateLoadTask.java 
 * @Description 
 * @author 245955
 * @date 2015-4-21
 */
public class CreateLoadTask {
	/**
	* 到达部门
	 */
	private List<String> arrDeptCode;
	/**
	 * 派送单号
	 */
	private String deryListCode;
	/**
	 * 扫描时间
	 */
	private Date scanTime;
	
	/**
	 * PDA编号
	 */
	private String pdaCode;
	/**
	 * 员工编号
	 */
	private String courierCode;
	/**
	 * 操作人
	 */
	private String userCode;
	/**
	 * 月台号
	 */
	private String platformCode;
	/**
	 * 任务类型
	 */
	private String loadType;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 理货员工工号
	 */
	private List<LoaderModel> userCodes;
	/**
	 * 货物类型
	 */
	private String crgType;
	/**
	 * 
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 
	 * transitGoodsType 转货类型:转货-TRANSITGOODS;带货-CARRYGOODS
	 */
	private String transitGoodsType;
	
	/**
	 * 
	 * 到达部门部门类型  
	 *   1：
	 *   2：
	 *   4：外场到分部部门类型，
	 *   5：营业部建分部装车到对应点部部门类型
	 */
	private String deptType;
	
	/**
	 * 目的站编码
	 */
	private String destCode;

	public List<String> getArrDeptCode() {
		return arrDeptCode;
	}

	public void setArrDeptCode(List<String> arrDeptCode) {
		this.arrDeptCode = arrDeptCode;
	}

	public String getDeryListCode() {
		return deryListCode;
	}

	public void setDeryListCode(String deryListCode) {
		this.deryListCode = deryListCode;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	public String getPdaCode() {
		return pdaCode;
	}

	public void setPdaCode(String pdaCode) {
		this.pdaCode = pdaCode;
	}

	public String getCourierCode() {
		return courierCode;
	}

	public void setCourierCode(String courierCode) {
		this.courierCode = courierCode;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	
	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public List<LoaderModel> getUserCodes() {
		return userCodes;
	}

	public void setUserCodes(List<LoaderModel> userCodes) {
		this.userCodes = userCodes;
	}

	public String getCrgType() {
		return crgType;
	}

	public void setCrgType(String crgType) {
		this.crgType = crgType;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getTransitGoodsType() {
		return transitGoodsType;
	}

	public void setTransitGoodsType(String transitGoodsType) {
		this.transitGoodsType = transitGoodsType;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getDestCode() {
		return destCode;
	}

	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
}
