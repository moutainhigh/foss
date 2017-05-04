package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.util.Date;

import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrAbsenteeInfoEntity;

public class TfrCtrAbsenteeInfoQcDto extends TfrCtrAbsenteeInfoEntity{

	private static final long serialVersionUID = -5445074007094203767L;

	/**
	 * 创建开始时间 
	 */
	private Date createBeginDate;
	
	/**
	 * 创建结束时间 
	 */
	private Date createEndDate;
	
	
	public Date getCreateBeginDate() {
		return createBeginDate;
	}

	public void setCreateBeginDate(Date createBeginDate) {
		this.createBeginDate = createBeginDate;
	}

	public Date getCreateEndDate() {
		return createEndDate;
	}

	public void setCreateEndDate(Date createEndDate) {
		this.createEndDate = createEndDate;
	}

	
}
