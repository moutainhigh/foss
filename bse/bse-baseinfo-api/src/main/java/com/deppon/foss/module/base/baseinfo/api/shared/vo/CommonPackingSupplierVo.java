package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
/**
 * 供应商实体vo
 * @author 130566
 *
 */
public class CommonPackingSupplierVo {
	/**
	 * 包装供应商实体
	 */
	private PackagingSupplierEntity packagingSupplierEntity;
	/**
	 * 集合
	 */
	private List<PackagingSupplierEntity> packagingSupplierEntities;
	public PackagingSupplierEntity getPackagingSupplierEntity() {
		return packagingSupplierEntity;
	}
	public void setPackagingSupplierEntity(
			PackagingSupplierEntity packagingSupplierEntity) {
		this.packagingSupplierEntity = packagingSupplierEntity;
	}
	public List<PackagingSupplierEntity> getPackagingSupplierEntities() {
		return packagingSupplierEntities;
	}
	public void setPackagingSupplierEntities(
			List<PackagingSupplierEntity> packagingSupplierEntities) {
		this.packagingSupplierEntities = packagingSupplierEntities;
	}
	
	
}
