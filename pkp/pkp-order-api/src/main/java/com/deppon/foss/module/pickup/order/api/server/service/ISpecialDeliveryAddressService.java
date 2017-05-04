package com.deppon.foss.module.pickup.order.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialDeliveryAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.vo.SpecialDeliveryAddressVo;

/** 
 * @ClassName: ISpecialDeliveryAddressService 
 * @Description: 特殊送货地址Service
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-17 上午10:15:56 
 *  
 */
public interface ISpecialDeliveryAddressService extends IService{
	
	/**
	 * @Title: insertSpecialDeliveryAddress
	 * @Description: 添加特殊送货地址
	 * @param specialDeliveryAddressEntity 特殊送货地址对象
	 * @return 受影响的行数
	 */
	int insertSpecialDeliveryAddress(SpecialDeliveryAddressEntity specialDeliveryAddressEntity);
	
	/**
	 * @Title: deleteSpecialDeliveryAddressById
	 * @Description: 根据id删除特殊送货地址
	 * @param id 特殊送货地址主键
	 * @return 受影响的行数
	 */
	int deleteSpecialDeliveryAddressById(String id);
	
	/**
	 * @Title: deleteSpecialDeliveryAddressByAddress
	 * @Description: 根据送货地址删除特殊送货地址库信息
	 * @param deliveryAddress 送货地址
	 * @return 受影响的行数
	 */
	int deleteSpecialDeliveryAddressByAddress(String deliveryAddress);

	/**
	 * @Title: updateSpecialDeliveryAddress
	 * @Description: 修改特殊送货地址
	 * @param specialDeliveryAddressEntity 特殊送货地址对象
	 * @return 受影响的行数
	 */
	int updateSpecialDeliveryAddress(SpecialDeliveryAddressEntity specialDeliveryAddressEntity);
	
	/**
	 * @Title: updateSpecialDeliveryAddress
	 * @Description: 批量修改特殊送货地址
	 * @param ids 特殊送货地址id数组
	 * @param specialDeliveryAddressEntity 特殊地址对象
	 * @return 受影响的行数
	 */
	int updateSpecialDeliveryAddress(String[] ids,SpecialDeliveryAddressEntity specialDeliveryAddressEntity);
	
	/**
	 * @Title: selectListByParam
	 * @Description: 根据特殊送货地址值对象分页查询特殊送货地址
	 * @param specialDeliveryAddressVo 特殊送货地址值对象
	 * @return 特殊送货地址对象集合
	 */
	List<SpecialDeliveryAddressEntity> selectSpecialDeliveryAddressList(SpecialDeliveryAddressVo specialDeliveryAddressVo, int start, int limit);
	
	/**
	 * @Title: selectCountByParam
	 * @Description: 根据特殊送货地址值对象查询特殊送货地址总数
	 * @param specialDeliveryAddressVo 特殊送货地址值对象
	 * @return 特殊送货地址总数
	 */
	Long selectSpecialDeliveryAddressCount(SpecialDeliveryAddressVo specialDeliveryAddressVo);
	
	/**
	 * @Title: selectOneByDeliveryAddress
	 * @Description: 根据送货地址查询特殊送货地址
	 * @param deliveryAddress 送货地址
	 * @return 特殊送货地址
	 */
	SpecialDeliveryAddressEntity  selectSpecialDeliveryAddress(String deliveryAddress);
	
}
