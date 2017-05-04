package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.util.Date;

import com.deppon.foss.module.pickup.predeliver.api.shared.annotation.AllowBlank;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptHabitEntity;

/** 
 * @ClassName: CustomerReceiptHabitVo 
 * @Description: 收货习惯 VO
 * @author 237982-foss-fangwenjun 
 * @date 2015-3-27 下午2:38:29 
 *  
 */
public class CustomerReceiptHabitVo extends CustomerReceiptHabitEntity {

	/** 
	 * @Fields serialVersionUID : 序列化版本号 
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
	 * 获取operateDateStart
	 * @return the operateDateStart
	 */
	@AllowBlank
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
	@AllowBlank
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
