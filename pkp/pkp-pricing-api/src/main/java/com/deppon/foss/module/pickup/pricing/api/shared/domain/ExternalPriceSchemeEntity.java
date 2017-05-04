package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 092020-lipengfei
 *	偏线外发价格方案实体
 */
public class ExternalPriceSchemeEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5928358183607052583L;
    /**
     * 方案名称.
     */
	private String schemeName;
	/**
	 * 目的站：来源于偏线代理网点基础资料中的“偏线代理网点名称”字段
	 */
	private String agentDeptName;
	/**
	 * 目的站编码
	 */
	private String agentDeptCode;
	/**
	 * 省份编码 
	 */
	private String provCode;
	/**
	 * 省份名称
	 */
	private String provName;
	/**
	 * 城市编码
	 */
	private String cityCode;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 区县编码
	 */
	private String countyCode;
	/**
	 * 区县名称
	 */
	private String countyName;
	/**
	 * 运输类型
	 */
	private String transportType;
	/**
	 * 外发外场
	 */
	private String externalOrgName;
	/**
	 * 外发外场编码
	 */
	private String externalOrgCode;
	/**
	 * 生效日期：生效日期不能小于当日日期
	 */
	private Date beginTime;
	/**
	 * 截止日期
	 */
	private Date endTime;
	/**
	 * 重货价格
	 */
	private BigDecimal heavyPrice;
	/**
	 * 轻货价格
	 */
	private BigDecimal lightPrice; 
	/**
	 * 最低一票
	 */
	private BigDecimal lowestPrice;
	/**
	 * 数据状态：激活、未激活
	 */
	private String active;
	/**
	 * 版本号
	 */
	private Long versionNo;
	/**
	 * 是否当前版本：是、否，做立即激活或中止时系统自动记录
	 */
	private String currentUsedVersion;
	
	
    /**
     * for currentUsedVersion
     */
    private static final String YES="是";
    
    /**
     * for currentUsedVersion
     */
    private static final String NO="否";
	/*=======================getter/setter==============================*/
	/**
	 * 方案名称
	 * @return schemeName
	 */
	public String getSchemeName() {
		return schemeName;
	}
	/**
	 * 方案名称
	 * @param schemeName
	 */
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	/**
	 * 目的站
	 * @return agentDeptName
	 */
	public String getAgentDeptName() {
		return agentDeptName;
	}
	/**
	 * 目的站
	 * @param agentDeptName
	 */
	public void setAgentDeptName(String agentDeptName) {
		this.agentDeptName = agentDeptName;
	}
	/**
	 * 目的站编码
	 * @return agentDeptCode
	 */
	public String getAgentDeptCode() {
		return agentDeptCode;
	}
	/**
	 * 目的站编码
	 * @param agentDeptCode
	 */
	public void setAgentDeptCode(String agentDeptCode) {
		this.agentDeptCode = agentDeptCode;
	}
	/**
	 * 省份编码
	 * @return provCode
	 */
	public String getProvCode() {
		return provCode;
	}
	/**
	 * 省份编码
	 * @param provCode
	 */
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}
	/**
	 * 省份名称
	 * @return provName
	 */ 
	public String getProvName() {
		return provName;
	}
	/**
	 * 省份名称
	 * @param provName
	 */
	public void setProvName(String provName) {
		this.provName = provName;
	}
	/**
	 * 城市编码
	 * @return cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * 城市编码
	 * @param cityCode
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * 城市名称
	 * @return cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * 城市名称
	 * @param cityName
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 区县编码
	 * @return
	 */
	public String getCountyCode() {
		return countyCode;
	}
	/**
	 * 区县编码
	 * @param countyCode
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	/**
	 * 区县名称
	 * @return countyName
	 */
	public String getCountyName() {
		return countyName;
	}
	/**
	 * 区县名称
	 * @param countyName
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	/**
	 * 运输方式
	 * @return transportType
	 */
	public String getTransportType() {
		return transportType;
	}
	/**
	 * 运输方式
	 * @param transportType
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	/**
	 * 外发外场名称
	 * @return externalOrgName
	 */
	public String getExternalOrgName() {
		return externalOrgName;
	}
	/**
	 * 外发外场名称
	 * @param externalOrgName
	 */
	public void setExternalOrgName(String externalOrgName) {
		this.externalOrgName = externalOrgName;
	}
	/**
	 * 外发外场编码
	 * @return externalOrgCode
	 */
	public String getExternalOrgCode() {
		return externalOrgCode;
	}
	/**
	 * 外发外场编码
	 * @param externalOrgCode
	 */
	public void setExternalOrgCode(String externalOrgCode) {
		this.externalOrgCode = externalOrgCode;
	}
	/**
	 * 生效日期
	 * @return beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	/**
	 * 生效日期
	 * @param beginTime
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * 截止日期
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 截止日期
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 重货价格
	 * @return heavyPrice
	 */
	public BigDecimal getHeavyPrice() {
		return heavyPrice;
	}
	/**
	 * 重货价格
	 * @param heavyPrice
	 */
	public void setHeavyPrice(BigDecimal heavyPrice) {
		this.heavyPrice = heavyPrice;
	}
	/**
	 * 轻货价格
	 * @return lightPrice
	 */
	public BigDecimal getLightPrice() {
		return lightPrice;
	}
	/**
	 * 轻货价格
	 * @param lightPrice
	 */
	public void setLightPrice(BigDecimal lightPrice) {
		this.lightPrice = lightPrice;
	}
	/**
	 * 最低一票价格
	 * @return lowestPrice
	 */

	/**
	 * 最低一票价格
	 * @param lowestPrice
	 */

	/**
	 * 数据状态
	 * @return active
	 */
	public String getActive() {
		return active;
	}
	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}
	public void setLowestPrice(BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	/**
	 * 数据状态
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * 是否当前版本
	 * @return currentUsedVersion
	 */
    /**
     *获得是否当前版本 
     */
    public String getCurrentUsedVersion() {
	Date now =new Date();
	if(this.getBeginTime()!=null&&this.getEndTime()!=null)
	{
		//当状态为激活，当前时间在起止时间之间时，为当前版本
	 if(StringUtil.equals(FossConstants.ACTIVE, this.active)&&now.compareTo(this.getBeginTime())>=0 &&now.compareTo(this.getEndTime())<0)  
	 {
	     currentUsedVersion = YES;
	     return YES;
	 }
	 else
	 {
	     currentUsedVersion = NO;
	     return NO;
	 }
	}else
	{
	    currentUsedVersion = "";
	    return currentUsedVersion;
	}
    }
	/**
	 * 是否当前版本
	 * @param currentUsedVersion
	 */
	public void setCurrentUsedVersion(String currentUsedVersion) {
		this.currentUsedVersion = currentUsedVersion;
	}
	/**
	 * 版本号
	 * @return versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}
	/**
	 * 版本号
	 * @param versionNo
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
}
