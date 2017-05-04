package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
/**
 * PDAUserEntity：配合PDA绑定和校验封签JSON信息
 * @author 106162-foss-liping
 * @date 2016-05-03 上午8:52:54
 */
public class SealNumANDStateEntity implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 封签号
	 */
	private String sealNumber;
	
	/**
	 * 封签状态
	 */
	private String sealInputState;

	
	public String getSealNumber() {
		return sealNumber;
	}

	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}

	public String getSealInputState() {
		return sealInputState;
	}

	public void setSealInputState(String sealInputState) {
		this.sealInputState = sealInputState;
	}

	@Override
	public String toString() {
		return "SealNumANDStateEntity [sealNumber=" + sealNumber
				+ ", sealInputState=" + sealInputState + "]";
	}
	
	

}
