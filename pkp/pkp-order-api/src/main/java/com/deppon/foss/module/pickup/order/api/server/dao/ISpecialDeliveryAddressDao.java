package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialDeliveryAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.vo.SpecialDeliveryAddressVo;

/** 
 * @ClassName: ISpecialDeliveryAddressDao 
 * @Description: 特殊送货地址Dao接口类 
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-16 下午6:37:40 
 *  
 */
public interface ISpecialDeliveryAddressDao {
	
	/**
	 * @Title: insertSpecialDeliveryAddress
	 * @Description: 添加特殊送货地址
	 * @param specialDeliveryAddressEntity 特殊送货地址对象
	 * @return 受影响的行数
	 */
	int insertSpecialDeliveryAddress(SpecialDeliveryAddressEntity specialDeliveryAddressEntity);
	
	/**
	 * @Title: deleteById
	 * @Description: 根据id删除特殊送货地址
	 * @param id 特殊送货地址id
	 * @return 受影响的行数
	 */
	int deleteById(String id);
	
	/**
	 * @Title: deleteByAddress
	 * @Description: 根据送货地址删除特殊送货地址库信息
	 * @param deliveryAddress 送货地址
	 * @return 受影响的行数
	 */
	int deleteByAddress(String deliveryAddress);

	/**
	 * @Title: updateSpecialDeliveryAddress
	 * @Description: 批量修改特殊送货地址
	 * @param  idsSpecicalAddress id数组和特殊地址对象
	 * @return 受影响的行数
	 */
	int updateSpecialDeliveryAddress(Map<String,Object> idsSpecicalAddress);
	
	/**
	 * @Title: updateSpecialDeliveryAddress
	 * @Description: 修改特殊送货地址
	 * @param specialDeliveryAddressEntity 特殊送货地址对象
	 * @return 受影响的行数
	 */
	int updateSpecialDeliveryAddress(SpecialDeliveryAddressEntity specialDeliveryAddressEntity);
	
	/**
	 * @Title: selectListByParam
	 * @Description: 根据特殊送货地址值对象分页查询特殊送货地址
	 * @param specialDeliveryAddressVo 特殊送货地址值对象
	 * @return 特殊送货地址对象集合
	 */
	List<SpecialDeliveryAddressEntity> selectListByParam(SpecialDeliveryAddressVo specialDeliveryAddressVo, int start, int limit);
	
	/**
	 * @Title: selectCountByParam
	 * @Description: 根据特殊送货地址值对象查询特殊送货地址总数
	 * @param specialDeliveryAddressVo 特殊送货地址值对象
	 * @return 特殊送货地址总数
	 */
	Long selectCountByParam(SpecialDeliveryAddressVo specialDeliveryAddressVo);
	
	/**
	 * @Title: selectOneByDeliveryAddress
	 * @Description: 根据送货地址查询特殊送货地址
	 * @param deliveryAddress 送货地址
	 * @return 特殊送货地址
	 */
	SpecialDeliveryAddressEntity  selectOneByDeliveryAddress(String deliveryAddress);
}
