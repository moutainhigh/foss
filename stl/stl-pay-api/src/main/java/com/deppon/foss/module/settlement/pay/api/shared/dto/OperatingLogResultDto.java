package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;

/**
 * 操作日志返回dto
 * 
 * @author foss-qiaolifeng
 * @date 2012-12-10 下午2:24:23
 */
public class OperatingLogResultDto extends OperatingLogEntity implements
		Serializable {

	/**
	 * 操作日志返回dto序列号
	 */
	private static final long serialVersionUID = -321532151965385053L;

	/**
	 * 事业部code
	 */
	private String divisionCode;

	/**
	 * 事业部编码
	 */
	private String divisionName;

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
	 * @return divisionCode
	 */
	public String getDivisionCode() {
		return divisionCode;
	}

	/**
	 * @param divisionCode
	 */
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	/**
	 * @return divisionName
	 */
	public String getDivisionName() {
		return divisionName;
	}

	/**
	 * @param divisionName
	 */
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
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

}
