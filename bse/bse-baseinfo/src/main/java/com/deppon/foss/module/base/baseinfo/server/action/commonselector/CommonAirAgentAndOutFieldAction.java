package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirAgentAndOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonAirAgentAndOutfieldVo;
/**
 * 查询空运代理网点(xin)组件
 * @author 130566
 *
 */
public class CommonAirAgentAndOutFieldAction extends AbstractAction implements IQueryAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 交互vo
	 */
	private CommonAirAgentAndOutfieldVo vo;
	/**
	 * 查询组件service
	 */
	private ICommonAirAgentAndOutfieldService agentAndOutfieldService;
	
	
	public CommonAirAgentAndOutfieldVo getVo() {
		return vo;
	}


	public void setVo(CommonAirAgentAndOutfieldVo vo) {
		this.vo = vo;
	}


	public void setAgentAndOutfieldService(
			ICommonAirAgentAndOutfieldService agentAndOutfieldService) {
		this.agentAndOutfieldService = agentAndOutfieldService;
	}

	/**
	 * 查询方法
	 */
	@Override
	public String query() {
		vo.setEntityList(agentAndOutfieldService.queryDepartmentsByEntity(vo.getEntity(), start, limit));
		this.setTotalCount(agentAndOutfieldService.queryCount(vo.getEntity()));
		return returnSuccess();
	}

}
