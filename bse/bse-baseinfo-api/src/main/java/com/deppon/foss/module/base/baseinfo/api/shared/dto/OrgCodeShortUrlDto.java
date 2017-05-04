package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 通过部门编号查询shortUrL的封装实体
 *  @ClassName: OrgCodeShortUrlDto
 * @author 232608-wusuhua
 * @date 2015年6月23日 下午4:35:35
 */
@XmlRootElement(name="OrgCodeShortUrlDto")
public class OrgCodeShortUrlDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3701443946775783127L;

	/**
	 * 短URL
	 */
	private String shortUrl;
	
	/**
	 * 是否成功
	 * true返回成功
	 * false返回失败
	 */
	private boolean state;
	
	/**
	 * 消息
	 */
	private String message;

	
	/**
	 * get set  方法
	 * @return
	 */
	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
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
