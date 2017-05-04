package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

/**
 * 查询虚拟营业部的Dto
 * 
 * @author WangPeng
 * @date   4:48 PM
 *
 */
public class CommonQueryVirtualSalesDeptDto {

	/**
	 * 组织对象
	 */
	OrgAdministrativeInfoEntity  orgAdministrativeInfoEntity;
	
	/**
	 * 组织对象的集合
	 */
	List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList;
	
	/**
	 * 营业部编码的集合
	 */
	private List<String> codes;

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public OrgAdministrativeInfoEntity getOrgAdministrativeInfoEntity() {
		return orgAdministrativeInfoEntity;
	}

	public void setOrgAdministrativeInfoEntity(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		this.orgAdministrativeInfoEntity = orgAdministrativeInfoEntity;
	}

	public List<OrgAdministrativeInfoEntity> getOrgAdministrativeInfoEntityList() {
		return orgAdministrativeInfoEntityList;
	}

	public void setOrgAdministrativeInfoEntityList(
			List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList) {
		this.orgAdministrativeInfoEntityList = orgAdministrativeInfoEntityList;
	}

	
	
}
