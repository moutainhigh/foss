package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AgentEntity;
/**
 * (司代理人VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:078838-foss-zhangbin,date:2013-4-25
 * </p>
 * 
 * @author 078838-foss-lifanghong
 * @date 2013-4-25
 * @since
 * @version
 */
public class AgentVo implements Serializable{

	/**
     *
     */
	private static final long serialVersionUID = 3875916350859197349L;
	/**
	 * 航空公司代理人实体
	 */
	private AgentEntity AgentEntity;
	/**
	 * 航空公司代理人实体List
	 */
	private List<AgentEntity> AgentEntityList;	
	public AgentEntity getAgentEntity() {
		return AgentEntity;
	}
	public void setAgentEntity(AgentEntity agentEntity) {
		this.AgentEntity = agentEntity;
	}
	public List<AgentEntity> getAgentEntityList() {
		return AgentEntityList;
	}
	public void setAgentEntityList(
			List<AgentEntity> agentEntityList) {
		this.AgentEntityList = agentEntityList;
	}
	


}
