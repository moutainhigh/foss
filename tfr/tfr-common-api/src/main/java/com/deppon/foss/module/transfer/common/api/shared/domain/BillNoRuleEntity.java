package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;

public class BillNoRuleEntity implements Serializable {

	private static final long serialVersionUID = 7264300995426110915L;

	/**
	 * 单据编号的位数，不算前缀和时间
	 * 因为数据库序列是个数字，比如说交接单号需要8位，如果当前序列不足8位，就需要补位
	 */
	private int numLength;

	/**
	 * 数据库sequence名
	 */
	private String sequenceName;

	/**
	 * 是否需要前缀
	 */
	private boolean needPrefix;

	/**
	 * 前缀
	 */
	private String prefix;

	/**
	 * 是否需要时间
	 */
	private boolean needTime;

	/**
	 * 时间格式
	 */
	private String timePattern;

	public int getNumLength() {
		return numLength;
	}

	public void setNumLength(int numLength) {
		this.numLength = numLength;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public boolean isNeedPrefix() {
		return needPrefix;
	}

	public void setNeedPrefix(boolean needPrefix) {
		this.needPrefix = needPrefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean isNeedTime() {
		return needTime;
	}

	public void setNeedTime(boolean needTime) {
		this.needTime = needTime;
	}

	public String getTimePattern() {
		return timePattern;
	}

	public void setTimePattern(String timePattern) {
		this.timePattern = timePattern;
	}

	@Override
	public String toString() {
		return "BillNoRuleEntity [numLength=" + numLength + ", sequenceName=" + sequenceName + ", needPrefix="
				+ needPrefix + ", prefix=" + prefix + ", needTime=" + needTime + ", timePattern=" + timePattern + "]";
	}

}
