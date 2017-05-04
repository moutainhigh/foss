package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.delivery.shared.domain 
 * @file PdaSignDetailEntity.java 
 * @description 流水号签收
 * @author ChenLiang
 * @created 2012-12-27 下午9:00:53    
 * @version 1.0
 */
public class PdaSignDetailEntity  extends BaseEntity {

	private static final long serialVersionUID = -7997166300559777703L;

	//签收情况
	private String signStatus;
	//流水号
	private String labelCode;
	//运单号
	private String wblCode;
	//图片列表
	private List<PictureEntity> pictures;
	/**
	 * 签收状态
	 * 每个流水号对应一个签收状态
	 */
	private String signSituation;
	
	
	 /**
     *是否内物缺少
     */
    private String  goodShorts;
    
    
	public String getGoodShorts() {
		return goodShorts;
	}

	public void setGoodShorts(String goodShorts) {
		this.goodShorts = goodShorts;
	}

	/**
	 * @description：签收状态 :REFUSE为拒收;SIGN为签收
	 * @parameters：void
	 * @return：签收状态
	 * @time：2014-12-26 下午5:33:33
	 */
	public String getSignStatus() {
		return signStatus;
	}

	/**
	 * @description：签收状态 :REFUSE为拒收;SIGN为签收
	 * @parameters：签收状态
	 * @return：void
	 * @time：2014-12-26 下午5:33:47
	 */
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public List<PictureEntity> getPictures() {
		return pictures;
	}

	public void setPictures(List<PictureEntity> pictures) {
		this.pictures = pictures;
	}

	public String getWblCode() {
		return wblCode;
	}

	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}

	
	/**
	 * @description：一个流水号对应一个签收情况
	 * @parameters：void
	 * @return：签收情况
	 * @time：2014-12-26 下午5:31:15
	 */
	public String getSignSituation() {
		return signSituation;
	}

	/**
	 * @description：一个流水号对应一个签收情况
	 * @parameters：签收情况
	 * @return：void
	 * @time：2014-12-26 下午5:32:12
	 */
	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}

	
	
}
