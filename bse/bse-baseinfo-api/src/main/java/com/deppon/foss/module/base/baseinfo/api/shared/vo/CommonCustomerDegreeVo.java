package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonCustomerDegreeEntity;

/**
 * action和前台传递值实体
 * 
 * @author dujunhui-187862
 * @date 2014-9-23 上午8:19:52
 */
public class CommonCustomerDegreeVo implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 2629160509193950089L;

	/**
	 * 传递到前台的组织集合
	 */
	private List<CommonCustomerDegreeEntity> customerDegreeEntityList;
	
	/**
	 * 公共选择器实体
	 */
	private CommonCustomerDegreeEntity entity;

	public List<CommonCustomerDegreeEntity> getCustomerDegreeEntityList() {
		return customerDegreeEntityList;
	}

	public void setCustomerDegreeEntityList(
			List<CommonCustomerDegreeEntity> customerDegreeEntityList) {
		this.customerDegreeEntityList = customerDegreeEntityList;
	}

	public CommonCustomerDegreeEntity getEntity() {
		return entity;
	}

	public void setEntity(CommonCustomerDegreeEntity entity) {
		this.entity = entity;
	}

}