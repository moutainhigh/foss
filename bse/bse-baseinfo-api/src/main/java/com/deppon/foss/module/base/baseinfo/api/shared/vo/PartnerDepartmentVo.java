package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

public class PartnerDepartmentVo implements Serializable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 条件
	 */
	private SaleDepartmentEntity depEntity;
	
	/**
	 * 返回结果
	 */
	private List<SaleDepartmentEntity> depEntitys;
	
	public SaleDepartmentEntity getDepEntity() {
		return depEntity;
	}

	public void setDepEntity(SaleDepartmentEntity depEntity) {
		this.depEntity = depEntity;
	}

	public List<SaleDepartmentEntity> getDepEntitys() {
		return depEntitys;
	}

	public void setDepEntitys(List<SaleDepartmentEntity> depEntitys) {
		this.depEntitys = depEntitys;
	}
}
