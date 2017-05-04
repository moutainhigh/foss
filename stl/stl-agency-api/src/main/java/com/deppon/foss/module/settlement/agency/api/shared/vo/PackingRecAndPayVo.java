package com.deppon.foss.module.settlement.agency.api.shared.vo;

import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayQueryDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayResultDto;

/**
 * 包装其他应收应付查询界面 vo
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-6-7 上午11:41:38,content: </p>
 * @author 105762
 * @date 2014-6-7 上午11:41:38
 * @since 1.6
 * @version 1.0
 */
public class PackingRecAndPayVo {
	private PackingRecAndPayResultDto packingRecAndPayResultDto;
	private PackingRecAndPayQueryDto packingRecAndPayQueryDto;

	/**
	  * @return  the packingRecAndPayResultDto
	 */
	public PackingRecAndPayResultDto getPackingRecAndPayResultDto() {
		return packingRecAndPayResultDto;
	}

	/**
	 * @param packingRecAndPayResultDto the packingRecAndPayResultDto to set
	 */
	public void setPackingRecAndPayResultDto(PackingRecAndPayResultDto packingRecAndPayResultDto) {
		this.packingRecAndPayResultDto = packingRecAndPayResultDto;
	}

	/**
	  * @return  the packingRecAndPayQueryDto
	 */
	public PackingRecAndPayQueryDto getPackingRecAndPayQueryDto() {
		return packingRecAndPayQueryDto;
	}

	/**
	 * @param packingRecAndPayQueryDto the packingRecAndPayQueryDto to set
	 */
	public void setPackingRecAndPayQueryDto(PackingRecAndPayQueryDto packingRecAndPayQueryDto) {
		this.packingRecAndPayQueryDto = packingRecAndPayQueryDto;
	}

}
