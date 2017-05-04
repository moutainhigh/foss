package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NewProductEntity;

/**
 * 新产品协议dao
 * @author 198771
 *
 */
public interface INewProductDao {
	//增加新产品客户协议
	int addNewProductEntity(NewProductEntity newProduct);
	//根据客户编码查询新产品客户协议
	NewProductEntity queryNewProductByConstomerCode(String customerCode);
	//修改客户协议
	int updateNewProductByConstomerCode(NewProductEntity newProduct);
}
