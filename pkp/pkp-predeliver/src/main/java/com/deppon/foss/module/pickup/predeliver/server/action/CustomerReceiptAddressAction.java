package com.deppon.foss.module.pickup.predeliver.server.action;

import java.util.List;

import javax.annotation.Resource;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ICustomerReceiptAddressService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptAddressEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.CustomerReceiptAddressVo;

/** 
 * @ClassName: CustomerReceiptAddressAction 
 * @Description: 客户历史收货地址Action
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-30 上午8:15:44 
 *  
 */
public class CustomerReceiptAddressAction extends AbstractAction {

	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 客户历史收货地址Entity
	 */
	private CustomerReceiptAddressEntity customerReceiptAddressEntity;
	
	/**
	 * 客户历史收货地址Id
	 */
	private String id;
	
	/**
	 * 客户历史收货地址Vo集合
	 */
	private List<CustomerReceiptAddressVo> customerReceiptAddresses;

	/**
	 * 客户历史收货地址Service
	 */
	private ICustomerReceiptAddressService customerReceiptAddressService;
	
	
	/**
	 * @Title: findReceiptAddress
	 * @Description: 查询收货地址
	 * @return
	 */
	public String findReceiptAddress(){
		try {
			this.customerReceiptAddresses = customerReceiptAddressService.selectReceiptAddressList(customerReceiptAddressEntity);
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * @Title: deleteReceiptAddress
	 * @Description: 删除收货地址
	 * @return
	 */
	public String deleteReceiptAddress() {
		try {
			int result =  customerReceiptAddressService.deleteReceiptAddressById(id);
			if (result == 1) {
				return returnSuccess();
			} else {
				return  returnError("删除内容不存在");
			}
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
	}

	/**
	 * 获取customerReceiptAddressEntity
	 * @return the customerReceiptAddressEntity
	 */
	public CustomerReceiptAddressEntity getCustomerReceiptAddressEntity() {
		return customerReceiptAddressEntity;
	}

	/**
	 * 设置customerReceiptAddressEntity
	 * @param customerReceiptAddressEntity 要设置的customerReceiptAddressEntity
	 */
	public void setCustomerReceiptAddressEntity(
			CustomerReceiptAddressEntity customerReceiptAddressEntity) {
		this.customerReceiptAddressEntity = customerReceiptAddressEntity;
	}

	/**
	 * 设置customerReceiptAddressService
	 * @param customerReceiptAddressService 要设置的customerReceiptAddressService
	 */
	@Resource
	public void setCustomerReceiptAddressService(
			ICustomerReceiptAddressService customerReceiptAddressService) {
		this.customerReceiptAddressService = customerReceiptAddressService;
	}

	/**
	 * 获取id
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置id
	 * @param id 要设置的id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取customerReceiptAddresses
	 * @return the customerReceiptAddresses
	 */
	public List<CustomerReceiptAddressVo> getCustomerReceiptAddresses() {
		return customerReceiptAddresses;
	}

	/**
	 * 设置customerReceiptAddresses
	 * @param customerReceiptAddresses 要设置的customerReceiptAddresses
	 */
	public void setCustomerReceiptAddresses(
			List<CustomerReceiptAddressVo> customerReceiptAddresses) {
		this.customerReceiptAddresses = customerReceiptAddresses;
	}
}
