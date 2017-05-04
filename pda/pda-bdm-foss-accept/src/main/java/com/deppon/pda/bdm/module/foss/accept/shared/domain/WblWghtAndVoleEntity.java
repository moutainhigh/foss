package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.vo
 * @file WblWghtAndVoleVo.java
 * @description 上传重量体积
 * @author ChenLiang
 * @created 2012-12-26 上午10:05:52
 * @version 1.0
 */
public class WblWghtAndVoleEntity extends ScanMsgEntity {

	private static final long serialVersionUID = -1164762715798728402L;

	/**
	 * 重量
	 */
	private double weight;

	/**
	 * 体积
	 */
	private double volume;

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

}
