package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptAddressEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.CustomerReceiptAddressVo;

/** 
 * @ClassName: ICustomerReceiptAddressService 
 * @Description: 客户历史收货地址Service 接口
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-14 下午2:14:21 
 *  
 */
public interface ICustomerReceiptAddressService extends IService {

	/**
	 * @Title: insertReceiptAddress
	 * @Description: 添加客户历史收货地址
	 * @param customerReceiptAddressEntity
	 * @return 受影响的行数
	 */
	int insertReceiptAddress(CustomerReceiptAddressEntity customerReceiptAddressEntity);
	
	/**
	 * @Title: deleteByIdAndOrgCode
	 * @Description: 根据Id删除客户历史收货地址
	 * @param id 收货地址Id
	 * @return 受影响的行数
	 */
	int deleteReceiptAddressById(String id);
	
	/**
	 * @Title: selectListByParam
	 * @Description: 根据参数查询历史收货地址集合
	 * @param customerReceiptAddressEntity 客户收货地址对象
	 * @return 历史收货地址集合
	 */
	List<CustomerReceiptAddressVo> selectReceiptAddressList(CustomerReceiptAddressEntity customerReceiptAddressEntity);
}
