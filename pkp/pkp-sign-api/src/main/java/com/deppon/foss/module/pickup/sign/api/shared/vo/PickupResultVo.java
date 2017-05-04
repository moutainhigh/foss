package com.deppon.foss.module.pickup.sign.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto;

/***
 * 提货清单结果Vo
 * @author foss-yuting
 * @date 2014-11-12 上午14:17:49
 * @since
 * @version
 */
@SuppressWarnings("serial")
public class PickupResultVo implements Serializable  {
	
	/**
	 * 提货清单数据
	 */
	private List<PickupResultDto> pickupResultDtoList;
	/**
	 * 提货清单条数
	 */
	private Long totalCount;
	
	public List<PickupResultDto> getPickupResultDtoList() {
		return pickupResultDtoList;
	}

	public void setPickupResultDtoList(List<PickupResultDto> pickupResultDtoList) {
		this.pickupResultDtoList = pickupResultDtoList;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
}
