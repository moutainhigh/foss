package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrderSourceEntity;

/**
 * action和前台传递值实体
 * 
 * @author dujunhui-187862
 * @date 2014-9-22 下午2:32:51
 */
public class CommonOrderSourceVo implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 2629160509193950089L;

	/**
	 * 传递到前台的组织集合
	 */
	private List<CommonOrderSourceEntity> orderSourceEntityList;
	
	/**
	 * 公共选择器实体
	 */
	private CommonOrderSourceEntity entity;

	public List<CommonOrderSourceEntity> getOrderSourceEntityList() {
		return orderSourceEntityList;
	}

	public void setOrderSourceEntityList(
			List<CommonOrderSourceEntity> orderSourceEntityList) {
		this.orderSourceEntityList = orderSourceEntityList;
	}

	public CommonOrderSourceEntity getEntity() {
		return entity;
	}

	public void setEntity(CommonOrderSourceEntity entity) {
		this.entity = entity;
	}

}