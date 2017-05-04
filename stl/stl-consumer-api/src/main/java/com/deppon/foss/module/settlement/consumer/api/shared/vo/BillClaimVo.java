package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimResultDto;

/**
 * 理赔Vo
 * 
 * @author foss-qiaolifeng
 * @date 2013-1-28 下午4:19:21
 */
public class BillClaimVo implements Serializable {

	/**
	 * 理赔Vo序列号
	 */
	private static final long serialVersionUID = 1251669064772458633L;

	/**
	 * 理赔查询参数Dto
	 */
	private BillClaimQueryDto billClaimQueryDto;

	/**
	 * 理赔返回值结果Dto
	 */
	private BillClaimResultDto billClaimResultDto;

	public BillClaimQueryDto getBillClaimQueryDto() {
		return billClaimQueryDto;
	}

	public void setBillClaimQueryDto(BillClaimQueryDto billClaimQueryDto) {
		this.billClaimQueryDto = billClaimQueryDto;
	}

	public BillClaimResultDto getBillClaimResultDto() {
		return billClaimResultDto;
	}

	public void setBillClaimResultDto(BillClaimResultDto billClaimResultDto) {
		this.billClaimResultDto = billClaimResultDto;
	}

}
