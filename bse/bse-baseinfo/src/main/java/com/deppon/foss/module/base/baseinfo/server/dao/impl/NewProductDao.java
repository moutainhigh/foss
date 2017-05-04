package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INewProductDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NewProductEntity;

/**
 * 新产品客户协议dao实现类
 * @author 198771
 *
 */
public class NewProductDao extends SqlSessionDaoSupport implements INewProductDao {

	private static final String NAMESPACE= ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX+".newProduct.";
	
	/**
	 * 增加新产品客户协议
	 */
	@Override
	public int addNewProductEntity(NewProductEntity newProduct) {
		return this.getSqlSession().insert(NAMESPACE+"addNewProduct", newProduct);
	}

	/**
	 * 根据客户编码查询新产品
	 */
	@Override
	public NewProductEntity queryNewProductByConstomerCode(String customerCode) {
		return (NewProductEntity)this.getSqlSession().selectOne(NAMESPACE+"queryNewProductByConstomerCode", customerCode);
	}

	/**
	 * 根据客户编码进行修改
	 */
	@Override
	public int updateNewProductByConstomerCode(NewProductEntity newProduct) {
		return this.getSqlSession().update(NAMESPACE+"updateNewProductByConstomerCode", newProduct);
	}

}
