package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author 218392 zhangyongxue
 * @date 2016-06-12 16:11:20
 * VTS自动受理反签收反结清：请求参数对接VTS接口系统的
 *
 */
public class RequestAutoReverseSignSettle implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 单号
	 */
	private String waybillNo;

	/**
	 * 当前登陆部门编码
	 */
	private String currentDeptCode;
	
	/**
	 * 当前登陆部门名称
	 */
	private String currentDeptName;
	
	/**
	 * 当前登陆员工工号
	 */
	private String empCode;
	
	/**
	 * 当前登陆员工姓名
	 */
	private String empName;
	
	/**
	 * 当前登陆部门对应的标杆编码
	 */
	private String unifiedCodeFromVTS;
	
	/**
	 * 自动反签收反结清类型
	 * unsign-反签收  
	 * unsettle-反结清
	 */
	private String autoReverseType;
	
	/**
	 * 到达付款信息表的ID
	 *  pkp.t_srv_repayment
	 * @return
	 */
	private List<VTSResverSettleRepaymentEntity> settleEntityList;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
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

	public String getUnifiedCodeFromVTS() {
		return unifiedCodeFromVTS;
	}

	public void setUnifiedCodeFromVTS(String unifiedCodeFromVTS) {
		this.unifiedCodeFromVTS = unifiedCodeFromVTS;
	}

	public String getAutoReverseType() {
		return autoReverseType;
	}

	public void setAutoReverseType(String autoReverseType) {
		this.autoReverseType = autoReverseType;
	}

	public List<VTSResverSettleRepaymentEntity> getSettleEntityList() {
		return settleEntityList;
	}

	public void setSettleEntityList(
			List<VTSResverSettleRepaymentEntity> settleEntityList) {
		this.settleEntityList = settleEntityList;
	}


}
