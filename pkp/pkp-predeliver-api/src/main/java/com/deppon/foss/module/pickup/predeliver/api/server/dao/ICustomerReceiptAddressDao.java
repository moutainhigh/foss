package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptAddressEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.CustomerReceiptAddressVo;

/** 
 * @ClassName: ICustomerReceiptAddressDao 
 * @Description: 客户历史收货地址Dao 接口
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-14 下午2:15:01 
 *  
 */
public interface ICustomerReceiptAddressDao {

	/**
	 * @Title: insertReceiptAddress
	 * @Description: 添加客户历史收货地址
	 * @param customerReceiptAddressEntity
	 * @return 受影响的行数
	 */
	int insertReceiptAddress(CustomerReceiptAddressEntity customerReceiptAddressEntity);
	
	/**
	 * @Title: deleteByIdAndOrgCode
	 * @Description: 根据Id和部门编码删除客户历史收货地址
	 * @param idOrgCode id和部门编码
	 * @return 受影响的行数
	 */
	int deleteByIdAndOrgCode(Map<String, String> idOrgCode);
	
	/**
	 * @Title: selectListByParam
	 * @Description: 根据参数查询历史收货地址集合
	 * @param customerReceiptAddressEntity 客户收货地址对象
	 * @return 历史收货地址集合
	 */
	List<CustomerReceiptAddressVo> selectListByParam(CustomerReceiptAddressEntity customerReceiptAddressEntity);
}
