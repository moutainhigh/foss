package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.ICustomerReceiptAddressDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptAddressEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.CustomerReceiptAddressVo;

/** 
 * @ClassName: CustomerReceiptAddressDao 
 * @Description: 客户历史收货地址Dao实现类 
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-14 下午2:13:30 
 *  
 */
public class CustomerReceiptAddressDao extends iBatis3DaoImpl implements
		ICustomerReceiptAddressDao {
	
	/** 
	 *  收货地址命名空间 
	 */ 
	private static final String RECEIPTADDRESS_NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptAddressEntityMapper.";

	/**
	 * @Title: insertReceiptAddress
	 * @Description: 添加客户历史收货地址
	 * @param customerReceiptAddressEntity
	 * @return 受影响的行数
	 */
	@Override
	public int insertReceiptAddress(CustomerReceiptAddressEntity customerReceiptAddressEntity) {
		return super.getSqlSession().insert(RECEIPTADDRESS_NAMESPACE + "insertOne", customerReceiptAddressEntity);
	}

	/**
	 * @Title: deleteByIdAndOrgCode
	 * @Description: 根据Id和部门编码删除客户历史收货地址
	 * @param idOrgCode id和部门编码
	 * @return 受影响的行数
	 */
	@Override
	public int deleteByIdAndOrgCode(Map<String, String> idOrgCode) {
		return super.getSqlSession().delete(RECEIPTADDRESS_NAMESPACE + "deleteByPrimaryKey", idOrgCode);
	}

	/**
	 * @Title: selectListByParam
	 * @Description: 根据参数查询历史收货地址集合
	 * @param customerReceiptAddressEntity 客户收货地址对象
	 * @return 历史收货地址集合
	 */
	@Override
	public List<CustomerReceiptAddressVo> selectListByParam(CustomerReceiptAddressEntity customerReceiptAddressEntity) {
		return super.getSqlSession().selectList(RECEIPTADDRESS_NAMESPACE + "selectListByParam", customerReceiptAddressEntity);
	}

}
