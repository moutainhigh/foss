package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;

/**
 * 预收单Dto
 * @author foss-pengzhen
 * @date 2012-11-19 下午4:41:37
 * @since
 * @version
 */
public class BillDepositReceivedPayVo implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1126807791190749615L;

	/**
	 * 预收单dto
	 */
	private BillDepositReceivedPayDto billDepositReceivedPayDto;
	
	private List<BillDepositReceivedEntity> billDepositReceivedEntities;

	
	/**
	 * @return  the billDepositReceivedPayDto
	 */
	public BillDepositReceivedPayDto getBillDepositReceivedPayDto() {
		return billDepositReceivedPayDto;
	}

	
	/**
	 * @param billDepositReceivedPayDto the billDepositReceivedPayDto to set
	 */
	public void setBillDepositReceivedPayDto(
			BillDepositReceivedPayDto billDepositReceivedPayDto) {
		this.billDepositReceivedPayDto = billDepositReceivedPayDto;
	}


	
	/**
	 * @return  the billDepositReceivedEntities
	 */
	public List<BillDepositReceivedEntity> getBillDepositReceivedEntities() {
		return billDepositReceivedEntities;
	}


	
	/**
	 * @param billDepositReceivedEntities the billDepositReceivedEntities to set
	 */
	public void setBillDepositReceivedEntities(
			List<BillDepositReceivedEntity> billDepositReceivedEntities) {
		this.billDepositReceivedEntities = billDepositReceivedEntities;
	}

	
	
}
