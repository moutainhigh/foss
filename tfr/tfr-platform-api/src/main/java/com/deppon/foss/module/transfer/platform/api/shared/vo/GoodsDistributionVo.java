package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsDistributionEntity;

/**
 * 转运场货量流动分布VO
 * 
 * @author 200978
 * 2015-3-12
 */
public class GoodsDistributionVo {

	private List<GoodsDistributionEntity> goodsDistributionList;
	
	private String operationDeptCode;
	
	private String operationDeptName;
	
	private String transferCenterCode;
	
	private String transferCenterName;
	
	private Date staDate;

	public List<GoodsDistributionEntity> getGoodsDistributionList() {
		return goodsDistributionList;
	}

	public void setGoodsDistributionList(
			List<GoodsDistributionEntity> goodsDistributionList) {
		this.goodsDistributionList = goodsDistributionList;
	}

	public String getOperationDeptCode() {
		return operationDeptCode;
	}

	public void setOperationDeptCode(String operationDeptCode) {
		this.operationDeptCode = operationDeptCode;
	}

	public String getOperationDeptName() {
		return operationDeptName;
	}

	public void setOperationDeptName(String operationDeptName) {
		this.operationDeptName = operationDeptName;
	}

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getTransferCenterName() {
		return transferCenterName;
	}

	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}
	
	
	
	
	
}
