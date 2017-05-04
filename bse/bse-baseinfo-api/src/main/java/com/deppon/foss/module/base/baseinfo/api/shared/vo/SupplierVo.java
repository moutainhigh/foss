package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity;


public class SupplierVo {
	
	private SupplierEntity supplierEntity;
	
	private List<SupplierEntity> supplierEntitys;
	
	public SupplierEntity getSupplierEntity() {
		return supplierEntity;
	}
	public void setSupplierEntity(SupplierEntity supplierEntity) {
		this.supplierEntity = supplierEntity;
	}
	public List<SupplierEntity> getSupplierEntitys() {
		return supplierEntitys;
	}
	public void setSupplierEntitys(List<SupplierEntity> supplierEntitys) {
		this.supplierEntitys = supplierEntitys;
	}
	
	
}
