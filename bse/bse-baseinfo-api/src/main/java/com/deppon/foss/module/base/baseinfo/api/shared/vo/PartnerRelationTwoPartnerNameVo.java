package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PartnerRelationEntity;

/**
 * 
 * 根据二级合伙人网点code查询所属子公司的VO
 * @author 308861 
 * @date 2016-8-25 下午5:18:18
 * @since
 * @version
 */
public class PartnerRelationTwoPartnerNameVo {
	//查询条件
	private String code;
	//返回结果
	private PartnerRelationEntity entityByTwoCode;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public PartnerRelationEntity getEntityByTwoCode() {
		return entityByTwoCode;
	}
	public void setEntityByTwoCode(PartnerRelationEntity entityByTwoCode) {
		this.entityByTwoCode = entityByTwoCode;
	}
}
