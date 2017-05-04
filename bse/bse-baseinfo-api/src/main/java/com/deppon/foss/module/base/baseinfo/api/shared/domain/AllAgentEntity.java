package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 用来存储交互“代理”的数据库对应实体：
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-lifanghong,date:2013-05-14</p>
 * @author 100847-foss-lifanghong
 * @date 2013-05-14
 * @since
 * @version
 */
public class AllAgentEntity extends BaseEntity {
	 /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -4503437420515904950L;
	/**
	 * 代理名字
	 */  
	private String agentName;

	/**
	 * 是否启用
	 */   
	private String active;
	/**
	 * 代理编码
	 */   
	private String agentCode;
	/**
	 * 查询全部（包含无效的）
	 */   
	private String all;
	
	
	
	
	
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
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
	
	
	
}
