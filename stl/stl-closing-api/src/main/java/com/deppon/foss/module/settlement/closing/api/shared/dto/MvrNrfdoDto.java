package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfdoEntity;


/**
 * 01到达月报表DTO.
 *
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public class MvrNrfdoDto extends MvrNrfdoEntity implements Serializable {
	/** serialVersionUID. */
	private static final long serialVersionUID = 6128359801326059811L;

	// 查询条件
	/** 业务类型集合. */
	private List<String> productCodeList;
	
	/** 统计时间. */
	private Date statisticalTime;
	
	/** 用户编码-设置用户数据查询权限. */
	private String userCode;
	
	/** 总条数. */
	private Long count;

	/**
	 * @return the productCodeList
	 */
	public List<String> getProductCodeList() {
		return productCodeList;
	}

	/**
	 * @param productCodeList the productCodeList to set
	 */
	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}

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

}
