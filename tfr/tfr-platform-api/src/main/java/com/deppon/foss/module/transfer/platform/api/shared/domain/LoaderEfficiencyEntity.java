package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 装卸车效率Entity
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:163580,date:2014-4-29 上午9:11:13,content:
 * </p>
 * 
 * @author 163580
 * @date 2014-4-29 上午9:11:13
 * @since
 * @version
 */
public class LoaderEfficiencyEntity extends BaseEntity {
	private static final long serialVersionUID = -5865863540124898637L;

	/** 查询日期 **/
	private Date queryDate;

	/** 查询月份 **/
	private String queryMonth;

	/** 查询开始日期 **/
	private String beginDate;

	/** 查询结束日期 **/
	private String endDate;

	/** 理货员工号 **/
	private String loaderCode;

	/** 理货员姓名 **/
	private String loaderName;

	/** 理货员所属部门 **/
	private String orgCode;

	/** 理货员所属部门 **/
	private String orgName;

	/** 理货员所属装卸车小队(队组别) **/
	private String loaderOrgCode;

	/** 理货员所属装卸车小队(队组别) **/
	private String loaderOrgName;

	/** 操作类型 **/
	private String handleType;

	/** 重量 **/
	private BigDecimal weight;

	/** 操作时长 **/
	private BigDecimal duration;

	/** 日均效率 **/
	private BigDecimal efficiencyOfDay;

	/** 月均效率 **/
	private BigDecimal efficiencyOfMonth;

	/** 当前部门(LoaderOrgCode)下所有子部门 **/
	private List<String> loaderOrgCodes;

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public String getQueryMonth() {
		return queryMonth;
	}

	public void setQueryMonth(String queryMonth) {
		this.queryMonth = queryMonth;
	}

	public String getLoaderCode() {
		return loaderCode;
	}

	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}

	public String getLoaderName() {
		return loaderName;
	}

	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLoaderOrgCode() {
		return loaderOrgCode;
	}

	public void setLoaderOrgCode(String loaderOrgCode) {
		this.loaderOrgCode = loaderOrgCode;
	}

	public String getLoaderOrgName() {
		return loaderOrgName;
	}

	public void setLoaderOrgName(String loaderOrgName) {
		this.loaderOrgName = loaderOrgName;
	}

	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getDuration() {
		return duration;
	}

	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}

	public BigDecimal getEfficiencyOfDay() {
		return efficiencyOfDay;
	}

	public void setEfficiencyOfDay(BigDecimal efficiencyOfDay) {
		this.efficiencyOfDay = efficiencyOfDay;
	}

	public BigDecimal getEfficiencyOfMonth() {
		return efficiencyOfMonth;
	}

	public void setEfficiencyOfMonth(BigDecimal efficiencyOfMonth) {
		this.efficiencyOfMonth = efficiencyOfMonth;
	}

	public List<String> getLoaderOrgCodes() {
		return loaderOrgCodes;
	}

	public void setLoaderOrgCodes(List<String> loaderOrgCodes) {
		this.loaderOrgCodes = loaderOrgCodes;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}