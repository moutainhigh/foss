package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* @ClassName: PlatformOpeEffiCondiDto 
* @Description: 月台操作效率查询条件dto
* @author 105944
* @date 2015-3-23 上午9:49:36
 */
public class PlatformOpeEffiCondiDto implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	//经营本部编码
	private String businessDeptCode;
	//经营本部名称
	private String businessDeptName;
	//外场编码
	private String outfieldCode;
	//外场名称
	private String outfieldName;
	//查询时间
	private Date statisticDate;
	//月台编码
	private String platformNumber;
	//查询类型：判断是查询月台操作效率(platform)还是月台操作效率明细(platformDetail)
	private String queryType;
	
	public String getBusinessDeptName() {
		return businessDeptName;
	}
	public void setBusinessDeptName(String businessDeptName) {
		this.businessDeptName = businessDeptName;
	}
	public String getOutfieldName() {
		return outfieldName;
	}
	public void setOutfieldName(String outfieldName) {
		this.outfieldName = outfieldName;
	}
	public String getBusinessDeptCode() {
		return businessDeptCode;
	}
	public void setBusinessDeptCode(String businessDeptCode) {
		this.businessDeptCode = businessDeptCode;
	}
	public String getOutfieldCode() {
		return outfieldCode;
	}
	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}
	public Date getStatisticDate() {
		return statisticDate;
	}
	public void setStatisticDate(Date statisticDate) {
		this.statisticDate = statisticDate;
	}
	public String getPlatformNumber() {
		return platformNumber;
	}
	public void setPlatformNumber(String platformNumber) {
		this.platformNumber = platformNumber;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
}
