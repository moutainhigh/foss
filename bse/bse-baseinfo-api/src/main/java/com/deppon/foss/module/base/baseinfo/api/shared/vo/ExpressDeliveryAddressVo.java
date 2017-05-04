package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryAddressEntity;


/**
 * 快递派送地址库Vo
 * @author 198771
 *
 */
public class ExpressDeliveryAddressVo  implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3506464679747148L;
	/**
	 * 查询实体
	 */
	private ExpressDeliveryAddressEntity condition;
	
	/**
	 * 新增修改
	 */
	private ExpressDeliveryAddressEntity expressDeliveryAddressEntity;
	
	/**
	 * 查询结果
	 */
	private List<ExpressDeliveryAddressEntity> expressDeliveryAddressEntityList;
	
	/**
	 * 删除，批量修改id
	 */
	private List<String> ids;
	

	public ExpressDeliveryAddressEntity getCondition() {
		return condition;
	}

	public void setCondition(ExpressDeliveryAddressEntity condition) {
		this.condition = condition;
	}

	public List<ExpressDeliveryAddressEntity> getExpressDeliveryAddressEntityList() {
		return expressDeliveryAddressEntityList;
	}

	public void setExpressDeliveryAddressEntityList(
			List<ExpressDeliveryAddressEntity> expressDeliveryAddressEntityList) {
		this.expressDeliveryAddressEntityList = expressDeliveryAddressEntityList;
	}

	public ExpressDeliveryAddressEntity getExpressDeliveryAddressEntity() {
		return expressDeliveryAddressEntity;
	}

	public void setExpressDeliveryAddressEntity(
			ExpressDeliveryAddressEntity expressDeliveryAddressEntity) {
		this.expressDeliveryAddressEntity = expressDeliveryAddressEntity;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}
