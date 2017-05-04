package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 取消临时租车标记请求实体
 * @author 337470
 *
 */
public class CarCancelRequestDO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//单号集合
	private List<String> sourceBillNoList;
	//修改人姓名
	private String empCode;
	//修改人工号
	private String empName;
	
	public List<String> getSourceBillNoList() {
		return sourceBillNoList;
	}
	public void setSourceBillNoList(List<String> sourceBillNoList) {
		this.sourceBillNoList = sourceBillNoList;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	

}
