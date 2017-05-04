package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public class LoadLabelScanEntity extends ScanMsgEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9099875203074384593L;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 到达部门
	 */
	private List<String> arrDeptCode;
	
	/**
     * 绑定封签方式(SCANED, BY_HAND) @see SealConstant
     */
	private String sealType;
	   
    /**
     * 绑定封签明细
     */	
	List<SealOrigDetail> sealOrigDetails;
	
	
	/**
	 * 
	 * 到达部门部门类型  
	 *   1：
	 *   2：
	 *   4：外场到分部部门类型，
	 *   5：营业部建分部装车到对应点部部门类型
	 */
	private String deptType;
	
	
	
	
	public String getDeptType() {
		return deptType;
	}
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public List<String> getArrDeptCode() {
		return arrDeptCode;
	}
	public void setArrDeptCode(List<String> arrDeptCode) {
		this.arrDeptCode = arrDeptCode;
	}
    public String getSealType() {
        return sealType;
    }
    public void setSealType(String sealType) {
        this.sealType = sealType;
    }
    public List<SealOrigDetail> getSealOrigDetails() {
        return sealOrigDetails;
    }
    public void setSealOrigDetails(List<SealOrigDetail> sealOrigDetails) {
        this.sealOrigDetails = sealOrigDetails;
    }
	
	
}
