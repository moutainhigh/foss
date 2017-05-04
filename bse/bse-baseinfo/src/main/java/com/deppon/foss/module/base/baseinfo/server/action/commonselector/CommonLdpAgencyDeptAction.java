package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AgencyCompanyOrDeptExpressVo;

public class CommonLdpAgencyDeptAction extends AbstractAction implements
		IQueryAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// 公共选择器-快递代理公司Service
	private ICommonLdpAgencyDeptService commonLdpAgencyDeptService;

	// 快递代理公司VO
	private AgencyCompanyOrDeptExpressVo expressDeptVo = new AgencyCompanyOrDeptExpressVo();

	/**
	 * 公共选择器查询 - 快递代理网点
	 * 
	 * @author WangPeng
	 * @Date 2013-7-23 下午6:32:30
	 * @return String
	 * 
	 */
	@Override
	public String query() {
		expressDeptVo
				.setOuterBranchExpressEntityList(commonLdpAgencyDeptService
						.queryLdpAgencyDeptsByCondtion(
								expressDeptVo.getOuterBranchExpressEntity(),
								limit, start));
		setTotalCount(commonLdpAgencyDeptService
				.countRecordByCondition(expressDeptVo
						.getOuterBranchExpressEntity()));
		return returnSuccess();
	}

	public void setCommonLdpAgencyDeptService(
			ICommonLdpAgencyDeptService commonLdpAgencyDeptService) {
		this.commonLdpAgencyDeptService = commonLdpAgencyDeptService;
	}

	public AgencyCompanyOrDeptExpressVo getExpressDeptVo() {
		return expressDeptVo;
	}

	public void setExpressDeptVo(AgencyCompanyOrDeptExpressVo expressDeptVo) {
		this.expressDeptVo = expressDeptVo;
	}

}
