package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutgoingBigCustomersEntity;

/**
 * 外派大客户VO
 * 
 * @author 310854
 * @date 2016-2-26 上午9:39:14
 */
public class OutgoingBigCustomersVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3165101038408549708L;

	/**
	 * 查询实体
	 */
	private OutgoingBigCustomersEntity condition;

	/**
	 * 修改新增实体
	 */
	private OutgoingBigCustomersEntity outgoingBigCustomersEntity;

	/**
	 * 查询列表
	 */
	private List<OutgoingBigCustomersEntity> outgoingBigCustomersEntityList;

	/**
	 * 客户code
	 */
	private String code;

	public OutgoingBigCustomersEntity getCondition() {
		return condition;
	}

	public void setCondition(OutgoingBigCustomersEntity condition) {
		this.condition = condition;
	}

	public OutgoingBigCustomersEntity getOutgoingBigCustomersEntity() {
		return outgoingBigCustomersEntity;
	}

	public void setOutgoingBigCustomersEntity(
			OutgoingBigCustomersEntity outgoingBigCustomersEntity) {
		this.outgoingBigCustomersEntity = outgoingBigCustomersEntity;
	}

	public List<OutgoingBigCustomersEntity> getOutgoingBigCustomersEntityList() {
		return outgoingBigCustomersEntityList;
	}

	public void setOutgoingBigCustomersEntityList(
			List<OutgoingBigCustomersEntity> outgoingBigCustomersEntityList) {
		this.outgoingBigCustomersEntityList = outgoingBigCustomersEntityList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
