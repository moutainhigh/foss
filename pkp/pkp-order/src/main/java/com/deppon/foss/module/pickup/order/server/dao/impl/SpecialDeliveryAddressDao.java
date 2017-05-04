package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.ISpecialDeliveryAddressDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialDeliveryAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.vo.SpecialDeliveryAddressVo;

/** 
 * @ClassName: SpecialDeliveryAddressDao 
 * @Description: 特殊送货地址dao实现类 
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-17 上午10:05:36 
 *  
 */
public class SpecialDeliveryAddressDao extends iBatis3DaoImpl implements
		ISpecialDeliveryAddressDao {
	
	private static final String SPECIALADDRESS_NAMESAPCE = "com.deppon.foss.module.pickup.order.api.shared.domain.SpecialDeliveryAddressEntityMapper.";

	/**
	 * @Title: insertSpecialDeliveryAddress
	 * @Description: 添加特殊送货地址
	 * @param specialDeliveryAddressEntity 特殊送货地址对象
	 * @return 受影响的行数
	 */
	@Override
	public int insertSpecialDeliveryAddress(
			SpecialDeliveryAddressEntity specialDeliveryAddressEntity) {
		return super.getSqlSession().insert(SPECIALADDRESS_NAMESAPCE + "insertOne",  specialDeliveryAddressEntity);
	}

	/**
	 * @Title: deleteById
	 * @Description: 根据id删除特殊送货地址
	 * @param id 特殊送货地址id
	 * @return 受影响的行数
	 */
	@Override
	public int deleteById(String id) {
		return super.getSqlSession().delete(SPECIALADDRESS_NAMESAPCE + "deleteByPrimaryKey", id);
	}

	/**
	 * @Title: updateSpecialDeliveryAddress
	 * @Description: 修改特殊送货地址
	 * @param specialDeliveryAddressEntity 特殊送货地址对象
	 * @return 受影响的行数
	 */
	@Override
	public int updateSpecialDeliveryAddress(
			SpecialDeliveryAddressEntity specialDeliveryAddressEntity) {
		return super.getSqlSession().update(SPECIALADDRESS_NAMESAPCE + "updateByPrimaryKey", specialDeliveryAddressEntity);
	}

	/**
	 * @Title: selectListByParam
	 * @Description: 根据特殊送货地址值对象分页查询特殊送货地址
	 * @param specialDeliveryAddressVo 特殊送货地址值对象
	 * @return 特殊送货地址对象集合
	 */
	@Override
	public List<SpecialDeliveryAddressEntity> selectListByParam(
			SpecialDeliveryAddressVo specialDeliveryAddressVo, int start,
			int limit) {
		
		return super.getSqlSession().selectList(SPECIALADDRESS_NAMESAPCE + "selectListByParam", specialDeliveryAddressVo, new RowBounds(start, limit));
	}

	/**
	 * @Title: selectCountByParam
	 * @Description: 根据特殊送货地址值对象查询特殊送货地址总数
	 * @param specialDeliveryAddressVo 特殊送货地址值对象
	 * @return 特殊送货地址总数
	 */
	@Override
	public Long selectCountByParam(
			SpecialDeliveryAddressVo specialDeliveryAddressVo) {
		return (Long) super.getSqlSession().selectOne(SPECIALADDRESS_NAMESAPCE + "selectCountByParam", specialDeliveryAddressVo);
	}

	/**
	 * @Title: selectOneByDeliveryAddress
	 * @Description: 根据送货地址查询特殊送货地址
	 * @param deliveryAddress 送货地址
	 * @return 特殊送货地址
	 */
	@Override
	public SpecialDeliveryAddressEntity selectOneByDeliveryAddress(
			String deliveryAddress) {
		return (SpecialDeliveryAddressEntity) super.getSqlSession().selectOne(SPECIALADDRESS_NAMESAPCE + "selectOneByDeliveryAddress", deliveryAddress);
	}

	/**
	 * @Title: updateSpecialDeliveryAddress
	 * @Description: 批量修改特殊送货地址
	 * @param  idsSpecicalAddress id数组和特殊地址对象
	 * @return 受影响的行数
	 */
	@Override
	public int updateSpecialDeliveryAddress(
			Map<String, Object> idsSpecicalAddress) {
		return super.getSqlSession().update(SPECIALADDRESS_NAMESAPCE + "updateByPrimaryKeys", idsSpecicalAddress);
	}

	/**
	 * @Title: deleteByAddress
	 * @Description: 根据送货地址删除特殊送货地址库信息
	 * @param deliveryAddress 送货地址
	 * @return 受影响的行数
	 */
	@Override
	public int deleteByAddress(String deliveryAddress) {
		return super.getSqlSession().delete(SPECIALADDRESS_NAMESAPCE + "deleteByAddress", deliveryAddress);
	}

}
