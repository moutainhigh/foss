package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LdpOuterBranchsAndOrginfoEntity;

/**
 * //快递代理网点+公司快递点部Vo
 * 
 * @author WangPeng
 * @date   2013-08-01 10:07 AM
 *
 */
public class CommonLdpOuterBranchsAndOrginfoVo {
	
	//快递代理网点+公司快递点部对象
	private LdpOuterBranchsAndOrginfoEntity  ldpOuterBranchsAndOrginfo;
	
	//快递代理网点+公司快递点部的对象集合
	private List<LdpOuterBranchsAndOrginfoEntity>   ldpOuterBranchsAndOrgList;
	
	//类型参数
	private String typeParam;

	public LdpOuterBranchsAndOrginfoEntity getLdpOuterBranchsAndOrginfo() {
		return ldpOuterBranchsAndOrginfo;
	}

	public void setLdpOuterBranchsAndOrginfo(
			LdpOuterBranchsAndOrginfoEntity ldpOuterBranchsAndOrginfo) {
		this.ldpOuterBranchsAndOrginfo = ldpOuterBranchsAndOrginfo;
	}

	public List<LdpOuterBranchsAndOrginfoEntity> getLdpOuterBranchsAndOrgList() {
		return ldpOuterBranchsAndOrgList;
	}

	public void setLdpOuterBranchsAndOrgList(
			List<LdpOuterBranchsAndOrginfoEntity> ldpOuterBranchsAndOrgList) {
		this.ldpOuterBranchsAndOrgList = ldpOuterBranchsAndOrgList;
	}

	public String getTypeParam() {
		return typeParam;
	}

	public void setTypeParam(String typeParam) {
		this.typeParam = typeParam;
	}

	
}
