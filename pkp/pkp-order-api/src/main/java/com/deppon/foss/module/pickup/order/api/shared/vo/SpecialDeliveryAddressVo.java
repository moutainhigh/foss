package com.deppon.foss.module.pickup.order.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialDeliveryAddressEntity;

/** 
 * @ClassName: SpecialDeliveryAddressVo 
 * @Description: 特殊送货地址值对象类 
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-16 下午6:03:54 
 *  
 */
public class SpecialDeliveryAddressVo extends SpecialDeliveryAddressEntity {

	/**
	 * 特殊送货地址Vo序列号
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * @Fields operateDateStart : 操作时间开始 
	 */ 
	private Date operateDateStart;
	
	/** 
	 * @Fields operateDateEnd : 操作时间结束 
	 */ 
	private Date operateDateEnd;
	
	/**
	 * 部门编码集合
	 */
	public List<String> orgList;

	/**
	 * 获取orgList
	 * @return the orgList
	 */
	public List<String> getOrgList() {
		return orgList;
	}

	/**
	 * 设置orgList
	 * @param orgList 要设置的orgList
	 */
	public void setOrgList(List<String> orgList) {
		this.orgList = orgList;
	}

	/**
	 * 获取operateDateStart
	 * @return the operateDateStart
	 */
	public Date getOperateDateStart() {
		return operateDateStart;
	}

	/**
	 * 设置operateDateStart
	 * @param operateDateStart 要设置的operateDateStart
	 */
	public void setOperateDateStart(Date operateDateStart) {
		this.operateDateStart = operateDateStart;
	}

	/**
	 * 获取operateDateEnd
	 * @return the operateDateEnd
	 */
	public Date getOperateDateEnd() {
		return operateDateEnd;
	}

	/**
	 * 设置operateDateEnd
	 * @param operateDateEnd 要设置的operateDateEnd
	 */
	public void setOperateDateEnd(Date operateDateEnd) {
		this.operateDateEnd = operateDateEnd;
	}
	
}
