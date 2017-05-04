package com.deppon.pda.bdm.module.foss.delivery.shared.domain;
import com.deppon.foss.framework.entity.BaseEntity;
/**
 * PDA签收流水号
 * @author 245955
 *
 */
public class PdaSignLabelCodeEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//流水号
	private String labelCode;
	//签收情况
	private String signSituation;

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getSignSituation() {
		return signSituation;
	}

	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}
	
	
}
