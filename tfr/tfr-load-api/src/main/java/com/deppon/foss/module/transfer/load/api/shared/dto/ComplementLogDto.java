package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.util.Date;

import com.deppon.foss.module.transfer.load.api.shared.domain.ComplementLogEntity;

public class ComplementLogDto extends ComplementLogEntity{

	private static final long serialVersionUID = -672568569192050874L;

	/**
	 * 补码开始时间
	 */
	private Date beginOperateTime;
	
	/**
	 * 补码结束时间
	 */
	private Date endOperateTime;

	public Date getBeginOperateTime() {
		return beginOperateTime;
	}

	public void setBeginOperateTime(Date beginOperateTime) {
		this.beginOperateTime = beginOperateTime;
	}

	public Date getEndOperateTime() {
		return endOperateTime;
	}

	public void setEndOperateTime(Date endOperateTime) {
		this.endOperateTime = endOperateTime;
	}
}
