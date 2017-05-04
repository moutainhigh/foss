/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-consumer-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.consumer.api.shared.dto
 * FILE    NAME: WaybillPickupWriteBackDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;

/**
 * 运单更改红冲的单据信息
 * 
 * @author ibm-zhuwei
 * @date 2013-1-15 下午3:05:50
 */
public class WaybillPickupWriteBackDto {

	/**
	 * 红冲的应付单
	 */
	private List<BillPayableEntity> writeBackBillPayables;

	/**
	 * 红冲的应收单
	 */
	private List<BillReceivableEntity> writeBackBillReceivables;

	/**
	 * 红冲的现金收款单
	 */
	private List<BillCashCollectionEntity> writeBackBillCashCollections;

	/**
	 * 红冲的代收货款
	 */
	private CODEntity writeBackCOD;

	/**
	 * 更改前到达部门是否合伙人
	 */
	private boolean isPartnerDeptBeforeChange;
	
	/**
	 * @return writeBackBillPayables
	 */
	public List<BillPayableEntity> getWriteBackBillPayables() {
		return writeBackBillPayables;
	}

	/**
	 * @param writeBackBillPayables
	 */
	public void setWriteBackBillPayables(
			List<BillPayableEntity> writeBackBillPayables) {
		this.writeBackBillPayables = writeBackBillPayables;
	}

	/**
	 * @return writeBackBillReceivables
	 */
	public List<BillReceivableEntity> getWriteBackBillReceivables() {
		return writeBackBillReceivables;
	}

	/**
	 * @param writeBackBillReceivables
	 */
	public void setWriteBackBillReceivables(
			List<BillReceivableEntity> writeBackBillReceivables) {
		this.writeBackBillReceivables = writeBackBillReceivables;
	}

	/**
	 * @return writeBackBillCashCollections
	 */
	public List<BillCashCollectionEntity> getWriteBackBillCashCollections() {
		return writeBackBillCashCollections;
	}

	/**
	 * @param writeBackBillCashCollections
	 */
	public void setWriteBackBillCashCollections(
			List<BillCashCollectionEntity> writeBackBillCashCollections) {
		this.writeBackBillCashCollections = writeBackBillCashCollections;
	}

	/**
	 * @return writeBackCOD
	 */
	public CODEntity getWriteBackCOD() {
		return writeBackCOD;
	}

	/**
	 * @param writeBackCOD
	 */
	public void setWriteBackCOD(CODEntity writeBackCOD) {
		this.writeBackCOD = writeBackCOD;
	}

	public boolean isPartnerDeptBeforeChange() {
		return isPartnerDeptBeforeChange;
	}

	public void setPartnerDeptBeforeChange(boolean isPartnerDeptBeforeChange) {
		this.isPartnerDeptBeforeChange = isPartnerDeptBeforeChange;
	}

}
