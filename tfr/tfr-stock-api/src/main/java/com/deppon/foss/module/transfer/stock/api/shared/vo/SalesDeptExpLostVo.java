package com.deppon.foss.module.transfer.stock.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.stock.api.shared.domain.SalesDeptExpLostEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.SalesDeptExpLostWaybillSerialNosDto;

public class SalesDeptExpLostVo implements Serializable {

	private static final long serialVersionUID = 9255509183718340L;

	/**
	 * 查询条件
	 */
	private SalesDeptExpLostEntity qcEntity;

	/**
	 * 查询结果
	 */
	private List<SalesDeptExpLostEntity> salesDeptExpLostEntities;

	/**
	 * 运单对应的流水号集合
	 */
	private List<SalesDeptExpLostWaybillSerialNosDto> waybillSerialNos;

	public SalesDeptExpLostEntity getQcEntity() {
		return qcEntity;
	}

	public void setQcEntity(SalesDeptExpLostEntity qcEntity) {
		this.qcEntity = qcEntity;
	}

	public List<SalesDeptExpLostEntity> getSalesDeptExpLostEntities() {
		return salesDeptExpLostEntities;
	}

	public void setSalesDeptExpLostEntities(
			List<SalesDeptExpLostEntity> salesDeptExpLostEntities) {
		this.salesDeptExpLostEntities = salesDeptExpLostEntities;
	}

	public List<SalesDeptExpLostWaybillSerialNosDto> getWaybillSerialNos() {
		return waybillSerialNos;
	}

	public void setWaybillSerialNos(
			List<SalesDeptExpLostWaybillSerialNosDto> waybillSerialNos) {
		this.waybillSerialNos = waybillSerialNos;
	}

}
