package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CustomerInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto;

/**
 * 小票操作与ACTION交互VO
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-23 上午8:14:35
 */
public class OtherRevenueVo implements Serializable {

	private static final long serialVersionUID = -8368576667915195072L;

	/**
	 * 小票Dto
	 */
	private OtherRevenueDto dto;

	/**
	 * 小票记录集合
	 */
	private List<OtherRevenueEntity> list;

	/**
	 * 客户信息
	 */
	private CustomerInfoDto custDto;

	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * @return dto
	 */
	public OtherRevenueDto getDto() {
		return dto;
	}

	/**
	 * @param dto
	 */
	public void setDto(OtherRevenueDto dto) {
		this.dto = dto;
	}

	/**
	 * @return list
	 */
	public List<OtherRevenueEntity> getList() {
		return list;
	}

	/**
	 * @param list
	 */
	public void setList(List<OtherRevenueEntity> list) {
		this.list = list;
	}

	/**
	 * @return custDto
	 */
	public CustomerInfoDto getCustDto() {
		return custDto;
	}

	/**
	 * @param custDto
	 */
	public void setCustDto(CustomerInfoDto custDto) {
		this.custDto = custDto;
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

}
