package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PartnerRelationEntity;

/**
 * 
 * 根据一级合伙人网点code查询所属子公司的VO
 * @author 308861 
 * @date 2016-8-25 下午5:18:18
 * @since
 * @version
 */
public class PartnerRelationOnePartnerNameVo {
	//查询条件
	private String code;
	//返回结果
	private PartnerRelationEntity entityByOneCode;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public PartnerRelationEntity getEntityByOneCode() {
		return entityByOneCode;
	}
	public void setEntityByOneCode(PartnerRelationEntity entityByOneCode) {
		this.entityByOneCode = entityByOneCode;
	}
}
