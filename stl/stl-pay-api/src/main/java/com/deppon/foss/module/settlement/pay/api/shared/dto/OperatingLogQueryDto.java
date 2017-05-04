package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 操作日志参数dto
 * 
 * @author foss-qiaolifeng
 * @date 2012-12-10 下午1:38:16
 */
public class OperatingLogQueryDto implements Serializable {

	/**
	 * 操作日志参数dto序列号
	 */
	private static final long serialVersionUID = -2822765674513685063L;

	/**
	 * 操作开始时间
	 */
	private Date startOperateTime;

	/**
	 * 操作结束时间
	 */
	private Date endOperateTime;

	/**
	 * 业务大区编码
	 */
	private String businessBigRegionCode;

	/**
	 * 业务大区名称
	 */
	private String businessBigRegionName;

	/**
	 * 业务小区编码
	 */
	private String businessSmallRegionCode;

	/**
	 * 业务小区名称
	 */
	private String businessSmallRegionName;

	/**
	 * 操作部门编码
	 */
	private String operateOrgCode;

	/**
	 * 操作人编码
	 */
	private String operatorCode;

	/**
	 * 所选大区或小区的下属部门编码
	 */
	private List<String> businessRegionCodeList;

	/**
	 * 当前登录用户员工编码
	 */
	private String empCode;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return startOperateTime
	 */
	public Date getStartOperateTime() {
		return startOperateTime;
	}

	/**
	 * @param startOperateTime
	 */
	public void setStartOperateTime(Date startOperateTime) {
		this.startOperateTime = startOperateTime;
	}

	/**
	 * @return endOperateTime
	 */
	public Date getEndOperateTime() {
		return endOperateTime;
	}

	/**
	 * @param endOperateTime
	 */
	public void setEndOperateTime(Date endOperateTime) {
		this.endOperateTime = endOperateTime;
	}

	/**
	 * @return businessBigRegionCode
	 */
	public String getBusinessBigRegionCode() {
		return businessBigRegionCode;
	}

	/**
	 * @param businessBigRegionCode
	 */
	public void setBusinessBigRegionCode(String businessBigRegionCode) {
		this.businessBigRegionCode = businessBigRegionCode;
	}

	/**
	 * @return businessBigRegionName
	 */
	public String getBusinessBigRegionName() {
		return businessBigRegionName;
	}

	/**
	 * @param businessBigRegionName
	 */
	public void setBusinessBigRegionName(String businessBigRegionName) {
		this.businessBigRegionName = businessBigRegionName;
	}

	/**
	 * @return businessSmallRegionCode
	 */
	public String getBusinessSmallRegionCode() {
		return businessSmallRegionCode;
	}

	/**
	 * @param businessSmallRegionCode
	 */
	public void setBusinessSmallRegionCode(String businessSmallRegionCode) {
		this.businessSmallRegionCode = businessSmallRegionCode;
	}

	/**
	 * @return businessSmallRegionName
	 */
	public String getBusinessSmallRegionName() {
		return businessSmallRegionName;
	}

	/**
	 * @param businessSmallRegionName
	 */
	public void setBusinessSmallRegionName(String businessSmallRegionName) {
		this.businessSmallRegionName = businessSmallRegionName;
	}

	/**
	 * @return operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * @param operateOrgCode
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * @return operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * @param operatorCode
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * @return businessRegionCodeList
	 */
	public List<String> getBusinessRegionCodeList() {
		return businessRegionCodeList;
	}

	/**
	 * @param businessRegionCodeList
	 */
	public void setBusinessRegionCodeList(List<String> businessRegionCodeList) {
		this.businessRegionCodeList = businessRegionCodeList;
	}

}
