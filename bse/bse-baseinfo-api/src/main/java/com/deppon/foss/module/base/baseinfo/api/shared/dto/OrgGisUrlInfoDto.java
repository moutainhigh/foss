package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * GIS地址Dto
 * 
 * @ClassName: OrgGisUrlInfoDto
 * @author 200664-yangjinheng
 * @date 2014年10月20日 下午4:35:35
 */
@XmlRootElement(name = "OrgGisUrlInfoDto")
public class OrgGisUrlInfoDto {

	/**
	 * 组织地址坐标
	 */
	private String depCoodinate;

	/**
	 * 是否成功 <br/>
	 * true 正常 返回短链接 <br/>
	 * false 异常 未正确返回短链接
	 */
	private boolean state;

	/**
	 * 消息
	 */
	private String message;

	public String getDepCoodinate() {
		return depCoodinate;
	}

	public void setDepCoodinate(String depCoodinate) {
		this.depCoodinate = depCoodinate;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
