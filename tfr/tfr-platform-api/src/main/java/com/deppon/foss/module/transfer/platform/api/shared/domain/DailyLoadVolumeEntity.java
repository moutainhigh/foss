/**   
* @Title: DailyLoadVolumeEntity.java 
* @Package com.deppon.foss.module.transfer.platform.api.shared.domain 
* @Description: 转运场日承载货量实体
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年6月25日 下午6:19:26 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * @ClassName: DailyLoadVolumeEntity 
 * @Description: 转运场日承载货量实体
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年6月25日 下午6:19:26 
 *  
 */
public class DailyLoadVolumeEntity extends BaseEntity {

	/** 
	 * 
	*/ 
	private static final long serialVersionUID = -1920873979037021649L;
	
	/**
	 * 转运场code
	 */
	private String orgCode;
	
	/**
	 * 转运场name
	 */
	private String orgName;
	
	/**
	 * 日承载货量
	 */
	private BigDecimal dailyLoadVolume;
	
	/**
	 * 警戒值
	 */
	private double fullValue;
	
	/**
	 * 危险值
	 */
	private double dangerValue;
	
	/**
	 * 生效日期
	 */
	private Date availabilityDate;
	
	/**
	 * 失效日期
	 */
	private Date expiryDate;
	
	/**
	 * 是否最新版本
	 */
	private String beNewest;
	
	/**
	 * 创建人工号
	 */
	private String createUserCode;
	
	/**
	 * 创建人姓名
	 */
	private String createUserName;
	
	/**
	 * 修改人工号
	 */
	private String modifyUserCode;
	
	/**
	 * 修改人姓名
	 */
	private String modifyUserName;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/** 
	 * @return orgCode 
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/** 
	 * @param orgCode 要设置的 orgCode 
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/** 
	 * @return orgName 
	 */
	public String getOrgName() {
		return orgName;
	}

	/** 
	 * @param orgName 要设置的 orgName 
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/** 
	 * @return dailyLoadVolume 
	 */
	public BigDecimal getDailyLoadVolume() {
		return dailyLoadVolume;
	}

	/** 
	 * @param dailyLoadVolume 要设置的 dailyLoadVolume 
	 */
	public void setDailyLoadVolume(BigDecimal dailyLoadVolume) {
		this.dailyLoadVolume = dailyLoadVolume;
	}

	/** 
	 * @return fullValue 
	 */
	public double getFullValue() {
		return fullValue;
	}

	/** 
	 * @param fullValue 要设置的 fullValue 
	 */
	public void setFullValue(double fullValue) {
		this.fullValue = fullValue;
	}

	/** 
	 * @return dangerValue 
	 */
	public double getDangerValue() {
		return dangerValue;
	}

	/** 
	 * @param dangerValue 要设置的 dangerValue 
	 */
	public void setDangerValue(double dangerValue) {
		this.dangerValue = dangerValue;
	}

	/** 
	 * @return availabilityDate 
	 */
	public Date getAvailabilityDate() {
		return availabilityDate;
	}

	/** 
	 * @param availabilityDate 要设置的 availabilityDate 
	 */
	public void setAvailabilityDate(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	/** 
	 * @return expiryDate 
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/** 
	 * @param expiryDate 要设置的 expiryDate 
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/** 
	 * @return beNewest 
	 */
	public String getBeNewest() {
		return beNewest;
	}

	/** 
	 * @param beNewest 要设置的 beNewest 
	 */
	public void setBeNewest(String beNewest) {
		this.beNewest = beNewest;
	}

	/** 
	 * @return createUserCode 
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/** 
	 * @param createUserCode 要设置的 createUserCode 
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/** 
	 * @return createUserName 
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/** 
	 * @param createUserName 要设置的 createUserName 
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/** 
	 * @return modifyUserCode 
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/** 
	 * @param modifyUserCode 要设置的 modifyUserCode 
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/** 
	 * @return modifyUserName 
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/** 
	 * @param modifyUserName 要设置的 modifyUserName 
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/** 
	 * @return createTime 
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/** 
	 * @param createTime 要设置的 createTime 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/** 
	 * @return modifyTime 
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/** 
	 * @param modifyTime 要设置的 modifyTime 
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
