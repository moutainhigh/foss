package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirAgentService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AirAgentVo;
/**
 * 公共查询组件--航空代理ACTION.
 *
 * @author 130346-foss-lifanghong
 * @date 2013-07-27 下午2:10:45
 */
public class CommonAirAgentAction extends AbstractAction implements
IQueryAction{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private AirAgentVo airAgentVo;
private ICommonAirAgentService commonAirAgentService;
/**
 * 航空代理查询.
 * 
 * @return the string
 * @author 130346-foss-lifanghong
 * @date 2013-07-27
 * @see com.deppon.foss.module.base.baseinfo.api.server.action.commonselector.CommonAirAgentAction#query()
 */
@JSON
	public String query() {
	airAgentVo.setAirAgentEntityList(
			commonAirAgentService.queryAirAgentEntityByEntity(airAgentVo.getAirAgentEntity(), limit, start));
	setTotalCount(commonAirAgentService.countAirAgentEntityByEntity(airAgentVo.getAirAgentEntity()));	
	return returnSuccess();
	}
public AirAgentVo getAirAgentVo() {
	return airAgentVo;
}
public void setAirAgentVo(AirAgentVo airAgentVo) {
	this.airAgentVo = airAgentVo;
}
public void setCommonAirAgentService(
		ICommonAirAgentService commonAirAgentService) {
	this.commonAirAgentService = commonAirAgentService;
}

}
