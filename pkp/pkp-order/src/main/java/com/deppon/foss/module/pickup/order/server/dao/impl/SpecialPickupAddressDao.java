package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.ISpecialPickupAddressDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.SpecialAddressConditionDto;

public class SpecialPickupAddressDao extends iBatis3DaoImpl implements ISpecialPickupAddressDao {

	public static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.SpecialAddressEntity.";
	@Override
	public int addSpecialAddress(SpecialAddressEntity record) {
		int result = this.getSqlSession().insert(
				NAMESPACE + "insertSelective", record);
		return result > 0 ? result : 0;
	}

	@Override
	public int deleteSpecialAddress(String id) {
		int result = this.getSqlSession().update(
				NAMESPACE + "deleteByPrimaryKey", id);
		return result > 0 ? result : 0;
	}

	@Override
	public SpecialAddressEntity selectByAddress(String address) {
		return  (SpecialAddressEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByAddress", address);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpecialAddressEntity> querySpecialAddressByCommon(SpecialAddressConditionDto conditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "selectByCondition", conditionDto, rowBounds);
	}

	@Override
	public Long queryCountByCondition(SpecialAddressConditionDto conditionDto) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "selectCountByCondition", conditionDto);
	}

	@Override
	public int updateVehicleByid(SpecialAddressEntity record) {
		int result = this.getSqlSession().update(
				NAMESPACE + "updateByPrimaryKeySelective", record);
		return result > 0 ? result : 0;
	}

	@Override
	public int updateVehicleByAddress(SpecialAddressEntity record) {
		int result = this.getSqlSession().update(
				NAMESPACE + "updateByAddress", record);
		return result > 0 ? result : 0;
	}

}
