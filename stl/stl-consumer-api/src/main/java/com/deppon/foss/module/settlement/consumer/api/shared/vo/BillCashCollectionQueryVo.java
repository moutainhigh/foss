package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCashCollectionQueryDto;

/**
 * 查询现金收款单Vo
 * 
 * @author foss-zhangxiaohui
 * @date Nov 2, 2012 11:46:50 AM
 */
public class BillCashCollectionQueryVo implements Serializable {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 612390744457667280L;

	/**
	 * 运单单号数组
	 */
	private String[] wayBillNosArray;
	
	/**
	 * 来源单号数组
	 */
	private String[] sourceBillNosArray;
	/**
	 * 应收单实体List
	 */
	private List<BillCashCollectionEntity> billCashCollectionList;

	/**
	 * 页面总记录条数
	 */
	private int totalRecords;

	/**
	 * 数据库总记录条数
	 */
	private int totalRecordsInDB;

	/**
	 * 合计总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * Dto实例传参
	 */
	private BillCashCollectionQueryDto dto;
	
	/**
	 * 银联交易流水号 
	 */
	private List<String> batchNos;

	/**
	 * @return wayBillNosArray
	 */
	public String[] getWayBillNosArray() {
		return wayBillNosArray;
	}

	/**
	 * @param  wayBillNosArray  
	 */
	public void setWayBillNosArray(String[] wayBillNosArray) {
		this.wayBillNosArray = wayBillNosArray;
	}

	/**
	 * @return sourceBillNosArray
	 */
	public String[] getSourceBillNosArray() {
		return sourceBillNosArray;
	}

	/**
	 * @param  sourceBillNosArray  
	 */
	public void setSourceBillNosArray(String[] sourceBillNosArray) {
		this.sourceBillNosArray = sourceBillNosArray;
	}

	/**
	 * @return billCashCollectionList
	 */
	public List<BillCashCollectionEntity> getBillCashCollectionList() {
		return billCashCollectionList;
	}

	/**
	 * @param  billCashCollectionList  
	 */
	public void setBillCashCollectionList(
			List<BillCashCollectionEntity> billCashCollectionList) {
		this.billCashCollectionList = billCashCollectionList;
	}

	/**
	 * @return totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param  totalRecords  
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return totalRecordsInDB
	 */
	public int getTotalRecordsInDB() {
		return totalRecordsInDB;
	}

	/**
	 * @param  totalRecordsInDB  
	 */
	public void setTotalRecordsInDB(int totalRecordsInDB) {
		this.totalRecordsInDB = totalRecordsInDB;
	}

	/**
	 * @return totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param  totalAmount  
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return dto
	 */
	public BillCashCollectionQueryDto getDto() {
		return dto;
	}

	/**
	 * @param  dto  
	 */
	public void setDto(BillCashCollectionQueryDto dto) {
		this.dto = dto;
	}

	/**
	 * @GET
	 * @return batchNos
	 */
	public List<String> getBatchNos() {
		/*
		 *@get
		 *@ return batchNos
		 */
		return batchNos;
	}

	/**
	 * @SET
	 * @param batchNos
	 */
	public void setBatchNos(List<String> batchNos) {
		/*
		 *@set
		 *@this.batchNos = batchNos
		 */
		this.batchNos = batchNos;
	}
}
