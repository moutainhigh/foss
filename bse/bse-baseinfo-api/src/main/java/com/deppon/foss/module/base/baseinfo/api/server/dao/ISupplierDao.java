package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SupplierEntity;

public interface ISupplierDao {

	/**
	 * <p>根据编码查询供应商信息</p> 
	 * @author 232607 
	 * @date 2015-12-22 下午2:19:32
	 * @param code
	 * @return
	 * @see
	 */
	SupplierEntity querySupplierByCode(String code);

	/**
	 * <p>新增供应商</p> 
	 * @author 232607 
	 * @date 2015-12-22 下午2:19:38
	 * @param entity
	 * @see
	 */
	void addSupplier(SupplierEntity entity);

	/**
	 * <p>根据编码作废供应商</p> 
	 * @author 232607 
	 * @date 2015-12-22 下午2:19:50
	 * @param supplierCode
	 * @see
	 */
	void deleteSupplierByCode(String supplierCode);

	/**
	 * <p>选择器——供应商选择器的分页查询</p> 
	 * @author 232607 
	 * @date 2015-12-23 上午10:26:43
	 * @param supplierEntity
	 * @return
	 * @see
	 */
	List<SupplierEntity> comboQuerySupplier(SupplierEntity supplierEntity);

	/**
	 * <p>选择器——供应商选择器的分页查询</p> 
	 * @author 232607 
	 * @date 2015-12-23 上午10:26:45
	 * @param supplierEntity
	 * @return
	 * @see
	 */
	Long countComboQuerySupplier(SupplierEntity supplierEntity);
	
	
	
	
	
	
}
