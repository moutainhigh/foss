package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillFreightToCollectQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillFreightToCollectResultDto;

/**
 * 到付清查VO
 * 
 * @author foss-zhangxiaohui
 * @date Oct 17, 2012 4:05:46 PM
 */
public class BillFreightToCollectVo implements Serializable {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 2154244784614957255L;

	/**
	 * 应收金额
	 */
	private BigDecimal amount;

	/**
	 * 实收金额
	 */
	private BigDecimal verifyAmount;

	/**
	 * 未收金额
	 */
	private BigDecimal unverifyAmount;

	/**
	 * 应收单实体List
	 */
	private List<BillFreightToCollectResultDto> billFreightToCollectList;

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
	 * Dto实例传参
	 */
	private BillFreightToCollectQueryDto dto;

	/**
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return verifyAmount
	 */
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	/**
	 * @param verifyAmount
	 */
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	/**
	 * @return unverifyAmount
	 */
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	/**
	 * @param unverifyAmount
	 */
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	/**
	 * @return billFreightToCollectList
	 */
	public List<BillFreightToCollectResultDto> getBillFreightToCollectList() {
		return billFreightToCollectList;
	}

	/**
	 * @param billFreightToCollectList
	 */
	public void setBillFreightToCollectList(
			List<BillFreightToCollectResultDto> billFreightToCollectList) {
		this.billFreightToCollectList = billFreightToCollectList;
	}

	/**
	 * @return totalRecordsInDB
	 */
	public int getTotalRecordsInDB() {
		return totalRecordsInDB;
	}

	/**
	 * @param totalRecordsInDB
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
	 * @param totalAmount
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
	 * @param totalVerifyAmount
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
	 * @param totalUnverifyAmount
	 */
	public void setTotalUnverifyAmount(BigDecimal totalUnverifyAmount) {
		this.totalUnverifyAmount = totalUnverifyAmount;
	}

	/**
	 * @return dto
	 */
	public BillFreightToCollectQueryDto getDto() {
		return dto;
	}

	/**
	 * @param dto
	 */
	public void setDto(BillFreightToCollectQueryDto dto) {
		this.dto = dto;
	}

}
