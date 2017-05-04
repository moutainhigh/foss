package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.util.Date;

import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;

/** 
 * @className: ExpressPackageQueryDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 快递包查询Dto类
 * @date: 2013-7-18 下午3:34:23
 * 
 */
public class ExpressPackageQueryDto extends ExpressPackageEntity {

	private static final long serialVersionUID = 1561552125455152108L;
	
	/**
	 * 查询条件中，起始创建时间
	 */
	private Date beginCreateTime;
	
	/**
	 * 查询条件中，结束创建时间
	 */
	private Date endCreateTime;

	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

}
