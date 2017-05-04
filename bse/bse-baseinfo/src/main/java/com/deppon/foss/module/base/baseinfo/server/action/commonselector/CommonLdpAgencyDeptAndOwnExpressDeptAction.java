package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLdpAgencyDeptAndOwnExpressDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonLdpOuterBranchsAndOrginfoVo;

/**
 * 快递代理网点+公司快点部Action
 * 
 * @author WangPeng
 * @date   9:16 AM
 *
 */
public class CommonLdpAgencyDeptAndOwnExpressDeptAction extends AbstractAction  implements IQueryAction{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	//快递代理网点+公司快点部对象
	CommonLdpOuterBranchsAndOrginfoVo vo = new CommonLdpOuterBranchsAndOrginfoVo();
	
	public CommonLdpOuterBranchsAndOrginfoVo getVo() {
		return vo;
	}

	public void setVo(CommonLdpOuterBranchsAndOrginfoVo vo) {
		this.vo = vo;
	}

	//快递代理网点+公司快点部service接口
	ICommonLdpAgencyDeptAndOwnExpressDeptService  ldpService;
	
	public void setLdpService(
			ICommonLdpAgencyDeptAndOwnExpressDeptService ldpService) {
		this.ldpService = ldpService;
	}

	/**
	 * 快递代理网点+公司快点部查询
	 * 
	 * @author WangPeng
	 * @Date   2013-8-1 上午10:02:39
	 *
	 */
	@Override
	public String query() {
		if(StringUtils.isNotEmpty(vo.getTypeParam())){
			
			vo.getLdpOuterBranchsAndOrginfo().setType(vo.getTypeParam().split(","));
		}
		vo.setLdpOuterBranchsAndOrgList(ldpService.queryLdpAgencyDeptsByCondtion(vo.getLdpOuterBranchsAndOrginfo(), limit, start));
		this.setTotalCount(ldpService.countRecordByCondition(vo.getLdpOuterBranchsAndOrginfo()));
		return returnSuccess();
	}

}
