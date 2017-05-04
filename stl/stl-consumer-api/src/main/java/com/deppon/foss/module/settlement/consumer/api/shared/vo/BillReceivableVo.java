package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;

/**
 * 查询应收单按单号查询
 * 
 * @author foss-zhangxiaohui
 * @date Oct 17, 2012 3:17:41 PM
 */
public class BillReceivableVo implements Serializable {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = -27067558658169125L;

	/**
	 * 应收单单号
	 */
	private String receivableNo;
	
	/**
	 * 应收编号数组
	 */
	private String[] receivableNosArray;

	/**
	 * 运单编号数组
	 */
	private String[] wayBillNosArray;
	
	/**
	 * 来源单号数组
	 */
	private String[] sourceBillNosArray;

	/**
	 * 应收单实体List
	 */
	private List<BillReceivableEntity> billReceivableList;

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
	 * 核销总金额/实收总金额
	 */
	private BigDecimal totalVerifyAmount;

	/**
	 * 未核销总金额/未收金额
	 */
	private BigDecimal totalUnverifyAmount;

	/**
	 * dto实例传参
	 */
	private BillReceivableDto dto;

	/**
	 * @return receivableNo
	 */
	public String getReceivableNo() {
		return receivableNo;
	}

	/**
	 * @param  receivableNo  
	 */
	public void setReceivableNo(String receivableNo) {
		this.receivableNo = receivableNo;
	}

	public String[] getReceivableNosArray() {
		return receivableNosArray;
	}

	public void setReceivableNosArray(String[] receivableNosArray) {
		this.receivableNosArray = receivableNosArray;
	}

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
	 * @return billReceivableList
	 */
	public List<BillReceivableEntity> getBillReceivableList() {
		return billReceivableList;
	}

	/**
	 * @param  billReceivableList  
	 */
	public void setBillReceivableList(List<BillReceivableEntity> billReceivableList) {
		this.billReceivableList = billReceivableList;
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
	 * @return totalVerifyAmount
	 */
	public BigDecimal getTotalVerifyAmount() {
		return totalVerifyAmount;
	}

	/**
	 * @param  totalVerifyAmount  
	 */
	public void setTotalVerifyAmount(BigDecimal totalVerifyAmount) {
		this.totalVerifyAmount = totalVerifyAmount;
	}

	/**
	 * @return totalUnverifyAmount
	 */
	public BigDecimal getTotalUnverifyAmount() {
		return totalUnverifyAmount;
	}

	/**
	 * @param  totalUnverifyAmount  
	 */
	public void setTotalUnverifyAmount(BigDecimal totalUnverifyAmount) {
		this.totalUnverifyAmount = totalUnverifyAmount;
	}

	/**
	 * @return dto
	 */
	public BillReceivableDto getDto() {
		return dto;
	}

	/**
	 * @param  dto  
	 */
	public void setDto(BillReceivableDto dto) {
		this.dto = dto;
	}
}
