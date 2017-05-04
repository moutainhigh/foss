package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-24 上午9:24:07,content: </p>
 * @author 316759 
 * @date 2017-2-24 上午9:24:07
 * @since
 * @version
 */
public class LttRateDubboDto implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
    private String sectionId;
    
    private BigDecimal weightStart;
    
    private BigDecimal weightEnd;
    
    private BigDecimal volumeStart;
    
    private BigDecimal volumeEnd;
    
    private GeneralPriceDubboDto generateRate;

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public BigDecimal getWeightStart() {
		return weightStart;
	}

	public void setWeightStart(BigDecimal weightStart) {
		this.weightStart = weightStart;
	}

	public BigDecimal getWeightEnd() {
		return weightEnd;
	}

	public void setWeightEnd(BigDecimal weightEnd) {
		this.weightEnd = weightEnd;
	}

	public BigDecimal getVolumeStart() {
		return volumeStart;
	}

	public void setVolumeStart(BigDecimal volumeStart) {
		this.volumeStart = volumeStart;
	}

	public BigDecimal getVolumeEnd() {
		return volumeEnd;
	}

	public void setVolumeEnd(BigDecimal volumeEnd) {
		this.volumeEnd = volumeEnd;
	}

	public GeneralPriceDubboDto getGenerateRate() {
		return generateRate;
	}

	public void setGenerateRate(GeneralPriceDubboDto generateRate) {
		this.generateRate = generateRate;
	}
    
}
