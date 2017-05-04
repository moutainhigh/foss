package com.deppon.foss.module.pickup.sign.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto;

/***
 * 提货清单Vo
 * @author foss-yuting
 * @date 2014-11-12 上午11:22:49
 * @since
 * @version
 */
@SuppressWarnings("serial")
public class PickupVo implements Serializable{
	
	/**
	 * 提货清单Dto  接受前台参数
	 */
	private PickupDto pickupDto;
	/**
	 * 批量更新 更新参数
	 */
	private List<PickupDto> pickupList;
	
	public PickupDto getPickupDto() {
		return pickupDto;
	}

	public void setPickupDto(PickupDto pickupDto) {
		this.pickupDto = pickupDto;
	}

	public List<PickupDto> getPickupList() {
		return pickupList;
	}

	public void setPickupList(List<PickupDto> pickupList) {
		this.pickupList = pickupList;
	}
	
}
