package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 用来存储交互“航空代理”（包含航空公司代理人信息，空运代理公司）的数据库对应实体：
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:130346-foss-lifanghong,date:2013-07-27</p>
 * @author 130346-foss-lifanghong
 * @date 2013-07-27
 * @since
 * @version
 */
public class AirAgentEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 代理名字
	 */
	private String agentName;
	/**
	 * 代理编码
	 */
	private String agentCode;
	/**
	 *是否有效 
	 */
	private String active;
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	
}
