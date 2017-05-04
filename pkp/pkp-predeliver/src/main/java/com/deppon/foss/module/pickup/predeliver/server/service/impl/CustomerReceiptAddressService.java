package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.ICustomerReceiptAddressDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ICustomerReceiptAddressService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptAddressEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.ReflectUtils;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.CustomerReceiptAddressVo;
import com.deppon.foss.util.UUIDUtils;

/** 
 * @ClassName: CustomerReceiptAddressService 
 * @Description: 客户历史收货地址Service 实现 
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-14 下午3:19:36 
 *  
 */
public class CustomerReceiptAddressService implements
		ICustomerReceiptAddressService {
	
	/**
	 * 注入客户收货地址dao
	 */
	private ICustomerReceiptAddressDao customerReceiptAddressDao;

	/**
	 * 获取customerReceiptAddressDao
	 * @return the customerReceiptAddressDao
	 */
	public ICustomerReceiptAddressDao getCustomerReceiptAddressDao() {
		return customerReceiptAddressDao;
	}

	/**
	 * 设置customerReceiptAddressDao
	 * @param customerReceiptAddressDao 要设置的customerReceiptAddressDao
	 */
	@Resource
	public void setCustomerReceiptAddressDao(
			ICustomerReceiptAddressDao customerReceiptAddressDao) {
		this.customerReceiptAddressDao = customerReceiptAddressDao;
	}

	/**
	 * @Title: insertReceiptAddress
	 * @Description: 添加客户历史收货地址
	 * @param customerReceiptAddressEntity
	 * @return 受影响的行数
	 */
	@Override
	public int insertReceiptAddress(
			CustomerReceiptAddressEntity customerReceiptAddressEntity) {
		// 判断传入对象是否为空
		if(customerReceiptAddressEntity == null) {
			return -1;
		}
		// 判断手机号码和固话都为空时返回 -1
		if(StringUtil.isEmpty(customerReceiptAddressEntity.getCustomerMobilePhone()) && StringUtil.isEmpty(customerReceiptAddressEntity.getCustomerPhone())){
			return -1;
		}
		// 设置收货地址Id
		customerReceiptAddressEntity.setId(UUIDUtils.getUUID());
		// 判断字段是否为空
		if (ReflectUtils.fieldValueIsNull(customerReceiptAddressEntity, true)) {
			return -1;
		}
		// 返回结果
		return customerReceiptAddressDao.insertReceiptAddress(customerReceiptAddressEntity);
	}

	/**
	 * @Title: deleteByIdAndOrgCode
	 * @Description: 根据Id删除客户历史收货地址
	 * @param id 收货地址Id
	 * @return 受影响的行数
	 */
	@Override
	public int deleteReceiptAddressById(String id) {
		// 判断传入Id是否为空
		if (id == null || "".equals(id.trim())) {
			return 0;
		}
		// 创建传入参数Map
		Map<String, String> parmMap = new HashMap<String, String>();
		// 添加Id
		parmMap.put("id", id);
		// 添加操作部门编码
		parmMap.put("orgCode", FossUserContext.getCurrentDeptCode());
		// 返回结果
		return customerReceiptAddressDao.deleteByIdAndOrgCode(parmMap);
	}

	/**
	 * @Title: selectListByParam
	 * @Description: 根据参数查询历史收货地址集合
	 * @param customerReceiptAddressEntity 客户收货地址对象
	 * @return 历史收货地址集合
	 */
	@Override
	public List<CustomerReceiptAddressVo> selectReceiptAddressList(
			CustomerReceiptAddressEntity customerReceiptAddressEntity) {
		// 判断传入对象是否为空
		if(customerReceiptAddressEntity == null) {
			return null;
		}
		customerReceiptAddressEntity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
		// 返回结果
		return customerReceiptAddressDao.selectListByParam(customerReceiptAddressEntity);
	}

}
