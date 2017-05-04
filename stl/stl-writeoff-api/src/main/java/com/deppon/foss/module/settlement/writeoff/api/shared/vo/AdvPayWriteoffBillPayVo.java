/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.AdvPayWriteoffBillPayDto;

/**
 * 预付冲应付VO
 * @author foss-pengzhen
 * @date 2012-10-16 下午8:18:09
 * @since
 * @version
 */
public class AdvPayWriteoffBillPayVo extends BillAdvancedPaymentEntity{
	
	/**
	 * VO序列号
	 */
	private static final long serialVersionUID = -5495516538180496912L;

	/**
	 * 预付冲应付dto
	 */
	private AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto;
	
	/**
	 * 预付冲应付dtoList
	 */
	private List<AdvPayWriteoffBillPayDto> advPayWriteoffBillPayDtos;

	/**
	 * 预收单list
	 */
	private List<BillAdvancedPaymentEntity> advancedPaymentEntities;
	
	/**
	 * 应收单list
	 */
	private List<BillPayableEntity> billPayableEntities;
	
	/**
	 * @return  the advPayWriteoffBillPayDto
	 */
	public AdvPayWriteoffBillPayDto getAdvPayWriteoffBillPayDto() {
		return advPayWriteoffBillPayDto;
	}

	
	/**
	 * @param advPayWriteoffBillPayDto the advPayWriteoffBillPayDto to set
	 */
	public void setAdvPayWriteoffBillPayDto(
			AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto) {
		this.advPayWriteoffBillPayDto = advPayWriteoffBillPayDto;
	}

	
	/**
	 * @return  the advPayWriteoffBillPayDtos
	 */
	public List<AdvPayWriteoffBillPayDto> getAdvPayWriteoffBillPayDtos() {
		return advPayWriteoffBillPayDtos;
	}

	
	/**
	 * @param advPayWriteoffBillPayDtos the advPayWriteoffBillPayDtos to set
	 */
	public void setAdvPayWriteoffBillPayDtos(
			List<AdvPayWriteoffBillPayDto> advPayWriteoffBillPayDtos) {
		this.advPayWriteoffBillPayDtos = advPayWriteoffBillPayDtos;
	}


	
	/**
	 * @return  the advancedPaymentEntities
	 */
	public List<BillAdvancedPaymentEntity> getAdvancedPaymentEntities() {
		return advancedPaymentEntities;
	}


	
	/**
	 * @param advancedPaymentEntities the advancedPaymentEntities to set
	 */
	public void setAdvancedPaymentEntities(
			List<BillAdvancedPaymentEntity> advancedPaymentEntities) {
		this.advancedPaymentEntities = advancedPaymentEntities;
	}


	
	/**
	 * @return  the billPayableEntities
	 */
	public List<BillPayableEntity> getBillPayableEntities() {
		return billPayableEntities;
	}


	
	/**
	 * @param billPayableEntities the billPayableEntities to set
	 */
	public void setBillPayableEntities(List<BillPayableEntity> billPayableEntities) {
		this.billPayableEntities = billPayableEntities;
	}
	
	

}
