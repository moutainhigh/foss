package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.SpecialAddressConditionDto;

/**
 * 
 * 特殊地质管理
 * @author 043258-foss-zhaobin
 * @date 2014-4-18 下午5:24:58
 */
public interface ISpecialPickupAddressDao  {

	int addSpecialAddress(SpecialAddressEntity record);

	int deleteSpecialAddress(String id);

	SpecialAddressEntity selectByAddress(String address);
	
	List<SpecialAddressEntity> querySpecialAddressByCommon(SpecialAddressConditionDto conditionDto, int start, int limit);

	Long queryCountByCondition(SpecialAddressConditionDto conditionDto);
	
	int updateVehicleByid(SpecialAddressEntity record);
	
	int updateVehicleByAddress(SpecialAddressEntity record);
}
