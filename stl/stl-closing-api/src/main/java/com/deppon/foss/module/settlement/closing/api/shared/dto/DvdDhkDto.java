package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.module.settlement.closing.api.shared.domain.DvdDhkEntity;


/**
 * 代汇款报表DTO.
 *
 * @author 163576-foss-guxinhua
 * @date 2013-12-05 下午4:38:04
 */
public class DvdDhkDto extends DvdDhkEntity implements Serializable {
	/** serialVersionUID. */
	private static final long serialVersionUID = 6128359801326059811L;

	// 查询条件
	/** 统计时间. */
	private Date statisticalTime;
	
	private Date startDate;
	
	private Date endDate;
	
	/** 用户编码-设置用户数据查询权限. */
	private String userCode;
	
	/** 总条数. */
	private Long count;

	/**
	 * @return the statisticalTime
	 */
	public Date getStatisticalTime() {
		return statisticalTime;
	}

	/**
	 * @param statisticalTime the statisticalTime to set
	 */
	public void setStatisticalTime(Date statisticalTime) {
		this.statisticalTime = statisticalTime;
	}

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Long count) {
		this.count = count;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
