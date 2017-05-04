package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonQueryLdpAndExpressAndOrgInfosService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonLdpOuterBranchsAndOrginfoVo;

public class CommonQueryLdpAndExpressAndOrgInfosAction extends AbstractAction
		implements IQueryAction {
	private static final long serialVersionUID = 1L;

	@Override
	public String query() {
		return null;
	}

	ICommonQueryLdpAndExpressAndOrgInfosService infosService;
	
	
	public CommonLdpOuterBranchsAndOrginfoVo getVo() {
		return vo;
	}
	public void setVo(CommonLdpOuterBranchsAndOrginfoVo vo) {
		this.vo = vo;
	}
	public void setInfosService(
			ICommonQueryLdpAndExpressAndOrgInfosService infosService) {
		this.infosService = infosService;
	}

	CommonLdpOuterBranchsAndOrginfoVo vo = new CommonLdpOuterBranchsAndOrginfoVo();
	/**
	 * 根据条件查询虚拟网点+快递代理网点+自有网点
	 * @return
	 */
	@JSON
	public String queryLdpAndExpressAndOrgInfos(){
		vo.setLdpOuterBranchsAndOrgList(infosService.
				queryLdpAndExpressAndOrgInfoList(vo.getLdpOuterBranchsAndOrginfo(), start, limit));
		this.setTotalCount(infosService.recordRowCount(vo.getLdpOuterBranchsAndOrginfo()));
		return returnSuccess();
	}
}
