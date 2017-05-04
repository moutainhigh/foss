package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * TODO(时效区域实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:wenwuneng,date:2013-11-05 下午4:22:31,content:TODO </p>
 * @author wenwuneng
 * @date 2013-11-05 下午4:22:31
 * @since
 * @version
 */
public class EffectiveRegionEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 区域编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 所在国家
	 */
	private String nationCode;
	/**
	 * 所在省份
	 */
	private String provCode;
	/**
	 * 所在市
	 */
	private String cityCode;
	/**
	 * 所在区
	 */
	private String countyCode;
	/**
	 *分为 所在地 和 部门 类型
	 */
	private String type;
	/**
	 *生效日期
	 */
	private Date beginTime;
	/**
	 *结束日期
	 */
	private Date endTime;
	/**
	 *操作标示
	 */
	private String operflag;
	
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
	public String getNationCode() {
		return nationCode;
	}
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}
	public String getProvCode() {
		return provCode;
	}
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getOperflag() {
		return operflag;
	}
	public void setOperflag(String operflag) {
		this.operflag = operflag;
	}
	
	
	
}
