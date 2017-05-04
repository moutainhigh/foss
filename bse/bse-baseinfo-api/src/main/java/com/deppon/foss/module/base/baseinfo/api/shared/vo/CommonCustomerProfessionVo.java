package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonCustomerProfessionEntity;

/**
 * action和前台传递值实体
 * 
 * @author dujunhui-187862
 * @date 2014-9-23 上午8:19:52
 */
public class CommonCustomerProfessionVo implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 2629160509193950089L;

	/**
	 * 传递到前台的组织集合
	 */
	private List<CommonCustomerProfessionEntity> customerProfessionEntityList;
	
	/**
	 * 公共选择器实体
	 */
	private CommonCustomerProfessionEntity entity;

	public List<CommonCustomerProfessionEntity> getCustomerProfessionEntityList() {
		return customerProfessionEntityList;
	}

	public void setCustomerProfessionEntityList(
			List<CommonCustomerProfessionEntity> customerProfessionEntityList) {
		this.customerProfessionEntityList = customerProfessionEntityList;
	}

	public CommonCustomerProfessionEntity getEntity() {
		return entity;
	}

	public void setEntity(CommonCustomerProfessionEntity entity) {
		this.entity = entity;
	}

}