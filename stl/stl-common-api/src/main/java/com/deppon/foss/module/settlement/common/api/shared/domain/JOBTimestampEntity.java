package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.util.Date;

/**
 * 
 * 定时任务时间戳
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-4-7 上午10:34:40
 */
public class JOBTimestampEntity {

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 时间戳
	 */
	private Date timestamp;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

}
