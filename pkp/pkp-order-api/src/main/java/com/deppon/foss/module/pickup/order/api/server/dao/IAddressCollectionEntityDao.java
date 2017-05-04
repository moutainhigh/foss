package com.deppon.foss.module.pickup.order.api.server.dao;

import com.deppon.foss.module.pickup.order.api.shared.domain.AddressCollectionEntity;

public interface IAddressCollectionEntityDao {

    int insert(AddressCollectionEntity record);

    int insertSelective(AddressCollectionEntity record);
    /**
     * 根据主键更新
     * */
    int updateByPrimaryKeySelective (AddressCollectionEntity record);
}