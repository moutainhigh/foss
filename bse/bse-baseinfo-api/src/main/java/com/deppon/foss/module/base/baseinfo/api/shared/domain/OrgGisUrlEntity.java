package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * GIS地址Entiy，发给移动开发
 * 
 * @ClassName: OrgGisUrlEntity
 * @author 200664-yangjinheng
 * @date 2014年10月20日 下午4:26:56
 */
public class OrgGisUrlEntity extends BaseEntity {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 组织编码
	 */
	private String orgCode;
	/**
	 * 组织地址坐标
	 */
	private String depCoodinate;

	/**
	 * 转换后的短地址
	 */
	private String shortUrl;

	/**
	 * 是否启用
	 */
	private String active;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDepCoodinate() {
		return depCoodinate;
	}

	public void setDepCoodinate(String depCoodinate) {
		this.depCoodinate = depCoodinate;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
