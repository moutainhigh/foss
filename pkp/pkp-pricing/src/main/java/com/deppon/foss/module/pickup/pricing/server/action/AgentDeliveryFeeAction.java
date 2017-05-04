package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IAgentDeliveryFeeSchemeService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeSchemeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.AgentDeliveryFeeSchemeException;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.AgentDeliveryFeeSchemeVo;

/**
 * @author 092020-lipengfei
 *	偏线代理送货费方案Action
 */
public class AgentDeliveryFeeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6497220201233794896L;
	/**
	 * 偏线代理送货费方案VO
	 */
	private AgentDeliveryFeeSchemeVo agentDeliveryFeeSchemeVo;
	/**
	 * 偏线代理送货费方案service
	 */
	private IAgentDeliveryFeeSchemeService agentDeliveryFeeSchemeService;
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AgentDeliveryFeeAction.class);
	
	public void setAgentDeliveryFeeSchemeService(
			IAgentDeliveryFeeSchemeService agentDeliveryFeeSchemeService) {
		this.agentDeliveryFeeSchemeService = agentDeliveryFeeSchemeService;
	}
	public AgentDeliveryFeeSchemeVo getAgentDeliveryFeeSchemeVo() {
		return agentDeliveryFeeSchemeVo;
	}
	public void setAgentDeliveryFeeSchemeVo(
			AgentDeliveryFeeSchemeVo agentDeliveryFeeSchemeVo) {
		this.agentDeliveryFeeSchemeVo = agentDeliveryFeeSchemeVo;
	}
	/**
	 * @author 092020-lipengfei
	 * 根据参数查询偏线代理送货费方案
	 * @return String
	 */
	@JSON
	public String queryAgentDeliveryFeeScheme(){
		AgentDeliveryFeeSchemeEntity entity=agentDeliveryFeeSchemeVo.getSchemeEntity();
		List<AgentDeliveryFeeSchemeEntity> entityList=agentDeliveryFeeSchemeService.queryAgentDeliveryFeeSchemeByParams(entity, limit, start);
		agentDeliveryFeeSchemeVo.setSchemeEntityList(entityList);
		totalCount=agentDeliveryFeeSchemeService.queryRecordCount(entity);
		return SUCCESS;
	}
	/**
	 * @author 092020-lipengfei
	 * 根据ID查询偏线代理送货费方案
	 * @return String
	 */
	@JSON
	public String queryAgentDeliveryFeeSchemeById(){
		String schemeId=agentDeliveryFeeSchemeVo.getId();
		AgentDeliveryFeeSchemeEntity entity=agentDeliveryFeeSchemeService.queryAgentDeliveryFeeSchemeById(schemeId);
		agentDeliveryFeeSchemeVo.setSchemeEntity(entity);
		return SUCCESS;
	}
	/**
	 * @author 092020-lipengfei
	 * 新增偏线代理送货费方案
	 * @return String
	 */
	@JSON
	public String addAgentDeliveryFeeScheme(){
		AgentDeliveryFeeSchemeEntity entity=agentDeliveryFeeSchemeVo.getSchemeEntity();
		try {
			agentDeliveryFeeSchemeService.addAgentDeliveryFeeScheme(entity);
		} catch (AgentDeliveryFeeSchemeException e) {
			 LOGGER.debug(e.getMessage(), e);
			 return returnError(e);
		}
		return SUCCESS;
	}
	/**
	 * @author 092020-lipengfei
	 * 删除偏线代理送货费方案
	 * @return String
	 */
	@JSON
	public String deleteAgentDeliveryFeeScheme(){
		List<String> idList=agentDeliveryFeeSchemeVo.getIdList();
		agentDeliveryFeeSchemeService.deleteAgentDeliveryFeeSchemeById(idList);
		return SUCCESS;
	}
	/**
	 * @author 092020-lipengfei
	 * 修改偏线代理送货费方案
	 * @return String
	 */
	@JSON
	public String updateAgentDeliveryFeeScheme(){
		AgentDeliveryFeeSchemeEntity entity=agentDeliveryFeeSchemeVo.getSchemeEntity();
		try {
			agentDeliveryFeeSchemeService.updateAgentDeliveryFeeScheme(entity);
		} catch (AgentDeliveryFeeSchemeException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
		return SUCCESS;
	}
}
