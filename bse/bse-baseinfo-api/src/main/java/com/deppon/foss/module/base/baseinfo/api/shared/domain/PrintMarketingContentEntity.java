package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * TODO(打印营销内容Entity实体)
 * @author 187862-dujunhui
 * @date 2014-8-26 上午8:42:44
 * @since
 * @version
 */
public class PrintMarketingContentEntity extends BaseEntity {

	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = 3294375553459355448L;
	
	/**
	 * 城市编码
	 */
	private String cityCode;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 城市类型
	 */
	private String cityPattern;
	/**
	 * 营销内容
	 */
	private String content;
	/**
	 * 冗余字段
	 */
	private String remark;
	/**
	 * 版本号
	 */
	private Long versionNo;
	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * @return  the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * @return  the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return  the cityPattern
	 */
	public String getCityPattern() {
		return cityPattern;
	}
	/**
	 * @param cityPattern the cityPattern to set
	 */
	public void setCityPattern(String cityPattern) {
		this.cityPattern = cityPattern;
	}
	/**
	 * @return  the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return  the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return  the versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}
	/**
	 * @param versionNo the versionNo to set
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	/**
	 * @return  the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
}