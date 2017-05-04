package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 日承载货量
 * 
 * @author 130346-foss-lifanghong
 * @date 2014-4-09 下午2:19:30
 */
public class TransferVolumeDayEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 外场编码
	 */
	private String code;
	
	/**
	 * 外场名称
	 */
	private String name;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date modifyTime;
	
	/**
	 * 查询时间（供查询条件使用）
	 */
	private Date queryTime;
	
	/**
	 *月承载货量（）
	 * @return
	 */
	private BigDecimal volumeMonth;
	
	
	
	public Date getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}

	/**
	 * 日承载货量
	 */
	private BigDecimal volumeDay;
	
	/**
	 * 仓库饱和度预警值
	 */
	private BigDecimal fullValue;
	
	/**
	 * 仓库饱和度危险值
	 */
	private BigDecimal dangerValue;
	
	/**
	 * 仓库饱和度预警值(供显示用)
	 */
	private String lookFullValue;
	
	/**
	 * 仓库饱和度危险值(供显示用)
	 */
	private String lookDangerValue;	
	/**
	 * 创建人
	 */
	private String createUserCode;
	
	/**
	 * 修改人
	 */
	private String modifyUserCode;
	
	/**
	 * 是否启用
	 */
	private String active;
	
	
	
	

	public String getLookFullValue() {
		return lookFullValue;
	}

	public void setLookFullValue(String lookFullValue) {
		this.lookFullValue = lookFullValue;
	}

	public String getLookDangerValue() {
		return lookDangerValue;
	}

	public void setLookDangerValue(String lookDangerValue) {
		this.lookDangerValue = lookDangerValue;
	}

	public BigDecimal getVolumeMonth() {
		return volumeMonth;
	}

	public void setVolumeMonth(BigDecimal volumeMonth) {
		this.volumeMonth = volumeMonth;
	}

	public BigDecimal getVolumeDay() {
		return volumeDay;
	}

	public void setVolumeDay(BigDecimal volumeDay) {
		this.volumeDay = volumeDay;
	}

	public BigDecimal getFullValue() {
		return fullValue;
	}

	public void setFullValue(BigDecimal fullValue) {
		this.fullValue = fullValue;
	}

	public BigDecimal getDangerValue() {
		return dangerValue;
	}

	public void setDangerValue(BigDecimal dangerValue) {
		this.dangerValue = dangerValue;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	


}
