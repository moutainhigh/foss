package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * 坏账单
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-24 下午8:38:22
 */
public class BillBadWriteoffDto implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 8010560526477266396L;
	
	/**
	 * 工作流号
	 */
	private String serialNo;

	/**
	 * 应收单号列表
	 */
	private List<String> receivableNos;

	/**
	 * 核销金额
	 */
	private BigDecimal writeoffAmount;

	/**
	 * 冲账方式
	 */
	private String billType;

	/**
	 * @return serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return receivableNos
	 */
	public List<String> getReceivableNos() {
		return receivableNos;
	}

	/**
	 * @param receivableNos
	 */
	public void setReceivableNos(List<String> receivableNos) {
		this.receivableNos = receivableNos;
	}

	/**
	 * @return writeoffAmount
	 */
	public BigDecimal getWriteoffAmount() {
		return writeoffAmount;
	}

	/**
	 * @param writeoffAmount
	 */
	public void setWriteoffAmount(BigDecimal writeoffAmount) {
		this.writeoffAmount = writeoffAmount;
	}

	/**
	 * @return billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

}
