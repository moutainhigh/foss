package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillClaimEntity;

/**
 * 理赔返回结果Dto
 * 
 * @author foss-qiaolifeng
 * @date 2013-1-28 下午2:52:00
 */
public class BillClaimResultDto implements Serializable {

	/**
	 * 理赔返回结果Dto序列号
	 */
	private static final long serialVersionUID = 6605483272707557279L;

	/**
	 * 理赔单实体
	 */
	private BillClaimEntity billClaimEntity = new BillClaimEntity();

	/**
	 * 理赔单返回list
	 */
	private List<BillClaimEntity> billClaimEntityList = new ArrayList<BillClaimEntity>();

	/**
	 * 总条数
	 */
	private Long billClaimTotalRows;

	public BillClaimEntity getBillClaimEntity() {
		return billClaimEntity;
	}

	public void setBillClaimEntity(BillClaimEntity billClaimEntity) {
		this.billClaimEntity = billClaimEntity;
	}

	public List<BillClaimEntity> getBillClaimEntityList() {
		return billClaimEntityList;
	}

	public void setBillClaimEntityList(List<BillClaimEntity> billClaimEntityList) {
		this.billClaimEntityList = billClaimEntityList;
	}

	public Long getBillClaimTotalRows() {
		return billClaimTotalRows;
	}

	public void setBillClaimTotalRows(Long billClaimTotalRows) {
		this.billClaimTotalRows = billClaimTotalRows;
	}

}
