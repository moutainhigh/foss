package com.deppon.foss.module.settlement.agency.api.shared.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 包装其他应收应付查询 dto <p style="display:none">modifyRecord</p> <p style="display:none">version:V1.0,author:105762,date:2014-5-16 下午3:09:10,content:TODO</p>
 * 
 * @author 105762
 * @date 2014-5-16 下午3:09:10
 * @since 1.6
 * @version 1.0
 */
public class PackingRecAndPayResultDto {

	// DtoList
	List<PackingRecAndPayDto> PackingRecAndPayDtos;
	// 总数
	int totalCount;
    // 应收总金额
    BigDecimal recTotalAmount;
    // 应收总未核销金额
    BigDecimal recTotalVerifyAmount;
    // 应收总未核销金额
    BigDecimal recTotalUnverifyAmount;
    // 应付总金额
    BigDecimal payTotalAmount;
    // 应付总未核销金额
    BigDecimal payTotalVerifyAmount;
    // 应付总未核销金额
    BigDecimal payTotalUnverifyAmount;

	/**
	  * @return  the packingRecAndPayDtos
	 */
	public List<PackingRecAndPayDto> getPackingRecAndPayDtos() {
		return PackingRecAndPayDtos;
	}

	/**
	 * @param packingRecAndPayDtos the packingRecAndPayDtos to set
	 */
	public void setPackingRecAndPayDtos(List<PackingRecAndPayDto> packingRecAndPayDtos) {
		PackingRecAndPayDtos = packingRecAndPayDtos;
	}

	/**
	  * @return  the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

    public void setRecTotalAmount(BigDecimal recTotalAmount) {
        this.recTotalAmount = recTotalAmount;
    }

    public void setRecTotalUnverifyAmount(BigDecimal recTotalUnverifyAmount) {
        this.recTotalUnverifyAmount = recTotalUnverifyAmount;
    }

    public void setPayTotalAmount(BigDecimal payTotalAmount) {
        this.payTotalAmount = payTotalAmount;
    }

    public void setPayTotalUnverifyAmount(BigDecimal payTotalUnverifyAmount) {
        this.payTotalUnverifyAmount = payTotalUnverifyAmount;
    }

    public void setRecTotalVerifyAmount(BigDecimal recTotalVerifyAmount) {
        this.recTotalVerifyAmount = recTotalVerifyAmount;
    }

    public void setPayTotalVerifyAmount(BigDecimal payTotalVerifyAmount) {
        this.payTotalVerifyAmount = payTotalVerifyAmount;
    }

    public BigDecimal getRecTotalAmount() {
        return recTotalAmount;
    }

    public BigDecimal getRecTotalVerifyAmount() {
        return recTotalVerifyAmount;
    }

    public BigDecimal getRecTotalUnverifyAmount() {
        return recTotalUnverifyAmount;
    }

    public BigDecimal getPayTotalAmount() {
        return payTotalAmount;
    }

    public BigDecimal getPayTotalVerifyAmount() {
        return payTotalVerifyAmount;
    }

    public BigDecimal getPayTotalUnverifyAmount() {
        return payTotalUnverifyAmount;
    }
}