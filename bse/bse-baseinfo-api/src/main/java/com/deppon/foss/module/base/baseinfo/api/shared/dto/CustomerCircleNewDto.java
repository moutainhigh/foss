package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainNewEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerNewEntity;
/**
 * 
 * 客户圈信息Dto(为接送货准备)
 * @author 308861 
 * @date 2017-1-10 下午5:43:50
 * @since
 * @version
 */
public class CustomerCircleNewDto implements Serializable{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否在客户圈内
	 */
	private String isCustCircle;
	/**
	 * 客户圈信息实体
	 */
	private CustomerCircleEntity customerCircleEntity=new CustomerCircleEntity();
	/**
	 * 客户合同信息实体
	 */
	private CusBargainNewEntity cusBargainNewEntity=new CusBargainNewEntity();
	/**
	 * 客户信息实体
	 */
	private CustomerNewEntity customerNewEntity = new CustomerNewEntity();
	
	public String getIsCustCircle() {
		return isCustCircle;
	}
	public void setIsCustCircle(String isCustCircle) {
		this.isCustCircle = isCustCircle;
	}
	public CustomerCircleEntity getCustomerCircleEntity() {
		return customerCircleEntity;
	}
	public void setCustomerCircleEntity(CustomerCircleEntity customerCircleEntity) {
		this.customerCircleEntity = customerCircleEntity;
	}
	public CusBargainNewEntity getCusBargainNewEntity() {
		return cusBargainNewEntity;
	}
	public void setCusBargainNewEntity(CusBargainNewEntity cusBargainNewEntity) {
		this.cusBargainNewEntity = cusBargainNewEntity;
	}
	public CustomerNewEntity getCustomerNewEntity() {
		return customerNewEntity;
	}
	public void setCustomerNewEntity(CustomerNewEntity customerNewEntity) {
		this.customerNewEntity = customerNewEntity;
	}
}
