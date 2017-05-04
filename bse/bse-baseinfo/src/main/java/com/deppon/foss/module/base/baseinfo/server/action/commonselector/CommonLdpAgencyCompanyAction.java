package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLdpAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AgencyCompanyOrDeptExpressVo;

/**
 * 公共选择器-快递代理公司Action
 * 
 * @author WangPeng
 * @date 2013-07-19 4:47 PM
 * 
 */
public class CommonLdpAgencyCompanyAction extends AbstractAction implements
		IQueryAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// 公共选择器-快递代理公司Service
	private ICommonLdpAgencyCompanyService commonLdpAgencyCompanyService;

	// 快递代理公司VO
	private AgencyCompanyOrDeptExpressVo expressCompanyVo = new AgencyCompanyOrDeptExpressVo();

	public AgencyCompanyOrDeptExpressVo getExpressCompanyVo() {
		return expressCompanyVo;
	}

	public void setExpressCompanyVo(AgencyCompanyOrDeptExpressVo expressCompanyVo) {
		this.expressCompanyVo = expressCompanyVo;
	}

	/**
	 * 公共选择器查询 - 快递代理
	 * 
	 * @author WangPeng
	 * @Date   2013-7-23 下午6:32:30
	 * @return String
	 *
	 */
	@Override
	public String query() {
		expressCompanyVo
				.setBusinessPartnerExpressEntityList(commonLdpAgencyCompanyService
						.queryLdpAgencyCompanysByCondtion(expressCompanyVo
								.getBusinessPartnerExpressEntity(), limit,
								start));
		setTotalCount(commonLdpAgencyCompanyService
				.countRecordByCondition(expressCompanyVo
						.getBusinessPartnerExpressEntity()));
		return returnSuccess();
	}

	/**
	 * @param commonLdpAgencyCompanyService
	 *            the commonLdpAgencyCompanyService to set
	 */
	public void setCommonLdpAgencyCompanyService(
			ICommonLdpAgencyCompanyService commonLdpAgencyCompanyService) {
		this.commonLdpAgencyCompanyService = commonLdpAgencyCompanyService;
	}

}
