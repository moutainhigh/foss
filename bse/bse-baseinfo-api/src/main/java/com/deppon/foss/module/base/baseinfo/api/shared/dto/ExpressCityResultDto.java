package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;

/**
 * 试点城市结果Dto
 * @author foss-qiaolifeng
 * @date 2013-7-16 下午2:40:11
 */
public class ExpressCityResultDto extends ExpressCityEntity{

	private static final long serialVersionUID = 1L;
	
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
	
	public String getProvCode() {
		return provCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
