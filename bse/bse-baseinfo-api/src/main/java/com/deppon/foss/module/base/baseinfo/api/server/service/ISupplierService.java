package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity;

public interface ISupplierService{

	/**
	 * <p>根据编码查询供应商信息</p> 
	 * @author 232607 
	 * @date 2015-12-22 下午2:18:16
	 * @param supplierCode
	 * @return
	 * @see
	 */
	SupplierEntity querySupplierByCode(String supplierCode);

	/**
	 * <p>新增供应商</p> 
	 * @author 232607 
	 * @date 2015-12-22 下午2:18:21
	 * @param entity
	 * @see
	 */
	void addSupplier(SupplierEntity entity);

	/**
	 * <p>根据编码作废供应商</p> 
	 * @author 232607 
	 * @date 2015-12-22 下午2:18:33
	 * @param supplierCode
	 * @see
	 */
	void deleteSupplierByCode(String supplierCode);

	/**
	 * <p>选择器——供应商选择器的分页查询</p> 
	 * @author 232607 
	 * @date 2015-12-23 上午10:25:49
	 * @param supplierEntity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<SupplierEntity> comboQuerySupplier(SupplierEntity supplierEntity,
			int start, int limit);

	/**
	 * <p>选择器——供应商选择器的分页查询</p> 
	 * @author 232607 
	 * @date 2015-12-23 上午10:25:54
	 * @param supplierEntity
	 * @return
	 * @see
	 */
	Long countComboQuerySupplier(SupplierEntity supplierEntity);
	
	
	
	
}
