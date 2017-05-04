package com.deppon.foss.module.pickup.order.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IAddressCollectionEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.AddressCollectionEntity;

public  class  AddressCollectionEntityDao extends iBatis3DaoImpl implements IAddressCollectionEntityDao{
	 static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.addresscollection." ;
	
    public int deleteByPrimaryKey(String id) {
		return 0;
	}

    /**
     * author lianghaisheng
     * 地址信息插入
     * */
    public int insert(AddressCollectionEntity record) {
    	int rows = getSqlSession().insert(NAMESPACE+"insertAddress", record);
		return rows;
	}

    public int insertSelective(AddressCollectionEntity record) {
    	int rows = getSqlSession().insert(NAMESPACE+"insertSelective", record);
		return rows;
	}

	@Override
	public int updateByPrimaryKeySelective(AddressCollectionEntity record) {
    	int rows = getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective", record);
		return rows;
	}


}