package com.deppon.foss.module.transfer.partialline.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.SalesdeptDeliveryEntity;

public class SalesDeptDeliveryProcVo {

	/**
	 * NON_DE_CONFIRM
	 */
	private SalesdeptDeliveryEntity salesdeptDeliveryEntity;

	/**
	 * 
	 */
	private List<SalesdeptDeliveryEntity> salesdeptDeliveryEntitys;

	private String waynoStr;

	private String status;

	public SalesdeptDeliveryEntity getSalesdeptDeliveryEntity() {
		return salesdeptDeliveryEntity;
	}

	public void setSalesdeptDeliveryEntity(
			SalesdeptDeliveryEntity salesdeptDeliveryEntity) {
		this.salesdeptDeliveryEntity = salesdeptDeliveryEntity;
	}

	public List<SalesdeptDeliveryEntity> getSalesdeptDeliveryEntitys() {
		return salesdeptDeliveryEntitys;
	}

	public void setSalesdeptDeliveryEntitys(
			List<SalesdeptDeliveryEntity> salesdeptDeliveryEntitys) {
		this.salesdeptDeliveryEntitys = salesdeptDeliveryEntitys;
	}

	public String getWaynoStr() {
		return waynoStr;
	}

	public void setWaynoStr(String waynoStr) {
		this.waynoStr = waynoStr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
