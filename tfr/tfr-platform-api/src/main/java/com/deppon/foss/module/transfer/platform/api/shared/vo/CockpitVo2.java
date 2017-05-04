package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.dto.CockpitResultDto;

public class CockpitVo2 implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 外场名称
	 */
	private String tfrCtrName;

	/**
	 * 当前时间,yyyy-MM-dd HH:mm:ss
	 */
	private String currentTimeStr;

	private List<CockpitResultDto> reuslt;

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public String getTfrCtrName() {
		return tfrCtrName;
	}

	public void setTfrCtrName(String tfrCtrName) {
		this.tfrCtrName = tfrCtrName;
	}

	public String getCurrentTimeStr() {
		return currentTimeStr;
	}

	public void setCurrentTimeStr(String currentTimeStr) {
		this.currentTimeStr = currentTimeStr;
	}

	public List<CockpitResultDto> getReuslt() {
		return reuslt;
	}

	public void setReuslt(List<CockpitResultDto> reuslt) {
		this.reuslt = reuslt;
	}

}
