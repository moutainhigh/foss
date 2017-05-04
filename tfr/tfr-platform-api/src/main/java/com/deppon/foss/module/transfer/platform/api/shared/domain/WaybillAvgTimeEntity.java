package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class WaybillAvgTimeEntity extends BaseEntity {

	/**
	* @fields serialVersionUID
	* @author 218427-foss-hongwy
	* @update 2015年3月24日 上午10:22:06
	* @version V1.0
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	* @fields waybillAvgTimeId
	* @author 218427-foss-hongwy
	* @update 2015年3月24日 上午10:22:06
	* @version V1.0
	*/
	private String waybillAvgTimeId;
	
	/**
	 * 部门Code
	* @fields orgName
	* @author 218427-foss-hongwy
	* @update 2015年3月24日 上午10:22:06
	* @version V1.0
	*/
	private String orgCode;
	 
	/**
	 * 部门Name
	* @fields orgName
	* @author 218427-foss-hongwy
	* @update 2015年3月24日 上午10:22:06
	* @version V1.0
	*/
	private String orgName;
	 
	/**
	 * 理论的统计时间
	* @fields statisticsTimeTheory
	* @author 218427-foss-hongwy
	* @update 2015年3月24日 上午10:22:06
	* @version V1.0
	*/
	private Date statisticsTimeTheory;
	
	/**
	 * 日票均装车时间
	* @fields waybillAvgTimeDay
	* @author 218427-foss-hongwy
	* @update 2015年3月24日 上午10:22:06
	* @version V1.0
	*/
	private Float waybillAvgTimeDay;
	
	/**
	 * 月票均装车时间
	* @fields waybillAvgTimeDayMonth
	* @author 218427-foss-hongwy
	* @update 2015年3月24日 上午10:22:06
	* @version V1.0
	*/
	private Float waybillAvgTimeMonth;
	
	/**
	 * 查询的开始时间
	* @fields beginDate
	* @author 218427-foss-hongwy
	* @update 2015年3月24日 上午10:22:06
	* @version V1.0
	*/
	
	private String queryDate;
	
	/**
	 * 当日有效装车时长
	* @fields waybillValidTimeDay
	* @author gongjp
	* @update 2015.06.09
	* @version V1.0
	*/
	
	private Float waybillValidTimeDay;
	
	/**
	 * 当日装车票数
	* @fields waybillDay
	* @author gongjp
	* @update 2015.06.09
	* @version V1.0
	*/
	private  Float waybillDay;
	
	/**
	 * 当月有效装车时长
	* @fields waybillValidTimeMonth
	* @author gongjp
	* @update 2015.06.09
	* @version V1.0
	*/
	private Float waybillValidTimeMonth;
	
	
	/**
	 * 当月装车票数
	* @fields waybillMonth
	* @author gongjp
	* @update 2015.06.09
	* @version V1.0
	*/
	private Float waybillMonth;

	public Float getWaybillValidTimeDay() {
		return waybillValidTimeDay;
	}

	public void setWaybillValidTimeDay(Float waybillValidTimeDay) {
		this.waybillValidTimeDay = waybillValidTimeDay;
	}

	public Float getWaybillDay() {
		return waybillDay;
	}

	public void setWaybillDay(Float waybillDay) {
		this.waybillDay = waybillDay;
	}

	public Float getWaybillValidTimeMonth() {
		return waybillValidTimeMonth;
	}

	public void setWaybillValidTimeMonth(Float waybillValidTimeMonth) {
		this.waybillValidTimeMonth = waybillValidTimeMonth;
	}

	public Float getWaybillMonth() {
		return waybillMonth;
	}

	public void setWaybillMonth(Float waybillMonth) {
		this.waybillMonth = waybillMonth;
	}

	/**
	 * 是否营业分析员
	*/

	
	
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

	public Date getStatisticsTimeTheory() {
		return statisticsTimeTheory;
	}

	public void setStatisticsTimeTheory(Date statisticsTimeTheory) {
		this.statisticsTimeTheory = statisticsTimeTheory;
	}

	public Float getWaybillAvgTimeDay() {
		return waybillAvgTimeDay;
	}

	public void setWaybillAvgTimeDay(float l) {
		this.waybillAvgTimeDay =  l;
	}

	public Float getWaybillAvgTimeMonth() {
		return waybillAvgTimeMonth;
	}

	public void setWaybillAvgTimeMonth(float l) {
		this.waybillAvgTimeMonth = l;
	}

	public String getWaybillAvgTimeId() {
		return waybillAvgTimeId;
	}

	public void setWaybillAvgTimeId(String waybillAvgTimeId) {
		this.waybillAvgTimeId = waybillAvgTimeId;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	public void setWaybillAvgTimeDay(Float waybillAvgTimeDay) {
		this.waybillAvgTimeDay = waybillAvgTimeDay;
	}

	public void setWaybillAvgTimeMonth(Float waybillAvgTimeMonth) {
		this.waybillAvgTimeMonth = waybillAvgTimeMonth;
	}

	

    
	
}
