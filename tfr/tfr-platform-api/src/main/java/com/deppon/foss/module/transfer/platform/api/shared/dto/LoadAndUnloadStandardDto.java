package com.deppon.foss.module.transfer.platform.api.shared.dto;
import java.math.BigDecimal;

/**
 * 部门装卸车标准
 * @author 046130-foss-xuduowei
 * @date 2012-12-18 下午9:13:46
 */
public class LoadAndUnloadStandardDto {
	/**
	 * 卸车重量标准
	 */
	private BigDecimal unloadWeightStd;
	/**
	 * 卸车体积标准
	 */
	private BigDecimal unloadVolumeStd;
	
	/**
	 * 获取 卸车重量标准.
	 *
	 * @return the 卸车重量标准
	 */
	public BigDecimal getUnloadWeightStd() {
		return unloadWeightStd;
	}
	
	/**
	 * 设置 卸车重量标准.
	 *
	 * @param unloadWeightStd the new 卸车重量标准
	 */
	public void setUnloadWeightStd(BigDecimal unloadWeightStd) {
		this.unloadWeightStd = unloadWeightStd;
	}
	
	/**
	 * 获取 卸车体积标准.
	 *
	 * @return the 卸车体积标准
	 */
	public BigDecimal getUnloadVolumeStd() {
		return unloadVolumeStd;
	}
	
	/**
	 * 设置 卸车体积标准.
	 *
	 * @param unloadVolumeStd the new 卸车体积标准
	 */
	public void setUnloadVolumeStd(BigDecimal unloadVolumeStd) {
		this.unloadVolumeStd = unloadVolumeStd;
	}
	
	
}