package com.deppon.pda.bdm.module.foss.accept.shared.domain;


import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public class WorkerStatusEntity extends ScanMsgEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6138924041921182867L;
	
	/**
	 * 快递员工号
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
     * 业务范围：零担司机/快递员
     * 
     */
    private String businessArea;
    
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
    
    

}
