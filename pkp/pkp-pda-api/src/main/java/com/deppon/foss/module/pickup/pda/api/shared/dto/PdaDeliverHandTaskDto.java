package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.util.List;
/**
 * Pda 查询快递派件交接任务DTO
 * @author 243921-foss-zhangtingting
 * @date 2015-04-15 上午9:36:01
 */
public class PdaDeliverHandTaskDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 派送单号
	 */
	private String deliverbillNo;
	/**
	 * 派送单明细
	 */
	private List<PdaDeliverHandTaskDetailsDto> deliverHandTaskDetailsDtos;
	
	public String getDeliverbillNo() {
		return deliverbillNo;
	}
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}
	public List<PdaDeliverHandTaskDetailsDto> getDeliverHandTaskDetailsDtos() {
		return deliverHandTaskDetailsDtos;
	}
	public void setDeliverHandTaskDetailsDtos(
			List<PdaDeliverHandTaskDetailsDto> deliverHandTaskDetailsDtos) {
		this.deliverHandTaskDetailsDtos = deliverHandTaskDetailsDtos;
	}
	
}
