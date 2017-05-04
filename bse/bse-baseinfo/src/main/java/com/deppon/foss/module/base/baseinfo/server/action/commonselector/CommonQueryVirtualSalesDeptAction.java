package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.Arrays;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonQueryVirtualSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OrgAdministrativeInfoVo;

public class CommonQueryVirtualSalesDeptAction extends AbstractAction implements
		IQueryAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 组织Vo
	 */
	OrgAdministrativeInfoVo  orgVo = new OrgAdministrativeInfoVo();
	
	/**
	 * 查询虚拟营业部
	 */
	ICommonQueryVirtualSalesDeptService commonQueryVirtualSalesDeptService;
	
	/**
	 * 查询虚拟营业部的相关信息
	 * 
	 * @author WangPeng
	 * @Date   2013-8-7 下午12:28:01
	 * @return String
	 *
	 */
	@Override
	public String query() {
		
		orgVo.getCommonQueryVirtualSalesDeptDto().setCodes(Arrays.asList(orgVo.getCodes()));
		orgVo.setOrgAdministrativeInfoEntityList(commonQueryVirtualSalesDeptService.queryVirtualSalesDeptList(orgVo.getCommonQueryVirtualSalesDeptDto(), start, limit));
		this.setTotalCount(commonQueryVirtualSalesDeptService.queryVirtualSalesDeptList(orgVo.getCommonQueryVirtualSalesDeptDto()));
		return returnSuccess();
	}

	public OrgAdministrativeInfoVo getOrgVo() {
		return orgVo;
	}

	public void setOrgVo(OrgAdministrativeInfoVo orgVo) {
		this.orgVo = orgVo;
	}

	public void setCommonQueryVirtualSalesDeptService(
			ICommonQueryVirtualSalesDeptService commonQueryVirtualSalesDeptService) {
		this.commonQueryVirtualSalesDeptService = commonQueryVirtualSalesDeptService;
	}

	
}
