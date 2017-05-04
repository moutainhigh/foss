package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAgentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AgentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AgentVo;

public class CommonAgentConditionAction extends AbstractAction {
	private static final long serialVersionUID = 2883644272419312426L;
    //vo
	public AgentVo agentVo ;
	//航空代理人service
	private ICommonAgentService commonAgentService;
	/**
	 * .
	 * <p>
	 * 查询所有的航空公司代理人根据条件<br/>
	 * 方法名：queryAirlinesAgentListBySelectiveCondition
	 * </p>
	 * 
	 * @author 078838-foss-lifanghong
	 * @时间 2012-12-03
	 * @since JDK1.6
	 */
	@JSON
	public String query() {		
			List<AgentEntity> agentEntity = commonAgentService.queryAgentListBySelectiveCondition(agentVo.getAgentEntity(), start, limit);
			agentVo.setAgentEntityList(agentEntity);
			long total = commonAgentService.queryAgentRecordCountBySelectiveCondition(agentVo.getAgentEntity());
			setTotalCount(total);
			return returnSuccess();		
	}
	public void setCommonAgentService(ICommonAgentService commonAgentService) {
		this.commonAgentService = commonAgentService;
	}
	public AgentVo getAgentVo() {
		return agentVo;
	}
	public void setAgentVo(AgentVo agentVo) {
		this.agentVo = agentVo;
	}
	
	


}
