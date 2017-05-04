package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.List;

public class PdaOrderWorkerOmsDto implements Serializable{

	/**
	 * 开启/暂停接口实体类
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 发起系统
	 * */
	private String operateSystem; 
	/**
	 * 快递员工号
	 * */
    private List<String> expressEmpCodes;
	/**
	 * 创建人工号
	 * */
    private String creatorCode; 
	/**
	 * 操作类型 开启/暂停
	 * */
    private String operateType; 
	/**
	 * PDA编号
	 * */
    private String pdaNum ;
    
    /**
     * 业务范围：零担司机/快递员
     * 
     */
  //zxy 20140707 内部优化  修改:删除无用字段(PDA类调用)
    private String businessArea;  
    //14.7.17 gcl AUTO-177 开启暂停操作都是在快递员登陆中操作
    /**
     * 车牌号集合
     */
    private List<String> vehicleNoList;
	public String getOperateSystem() {
		return operateSystem;
	}
	public void setOperateSystem(String operateSystem) {
		this.operateSystem = operateSystem;
	}
	public List<String> getExpressEmpCodes() {
		return expressEmpCodes;
	}
	public void setExpressEmpCodes(List<String> expressEmpCodes) {
		this.expressEmpCodes = expressEmpCodes;
	}
	public String getCreatorCode() {
		return creatorCode;
	}
	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getBusinessArea() {
		return businessArea;
	}
	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}
	public List<String> getVehicleNoList() {
		return vehicleNoList;
	}
	public void setVehicleNoList(List<String> vehicleNoList) {
		this.vehicleNoList = vehicleNoList;
	}
	public String getPdaNum() {
		return pdaNum;
	}
	public void setPdaNum(String pdaNum) {
		this.pdaNum = pdaNum;
	}
}
