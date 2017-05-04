
package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 状态信息DTO
 * 
* @author 200942
 * @date 2016-5-10 下午8:37:39
 */
public class GeneralInformation implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 2503911558468096776L;
	/**
	 * 货物状态
	 */
	private String action;
	/**
	 * 件数
	 */
	private Integer goodsCount;
	/**
	 * 当前位置
	 */
	private String nowPosition;
	/**
	 * 下一部门
	 */
	private String nextOrgCode;
	/**
	 * 预计到达下一部门时间
	 */
	private Date planArriveTime;
	/**
	 * 提货时间
	 */
	private Date planPickupTime;
	/**
	 * 是否可提货
	 */
	private String ifAvailedPickup;
	
	// add by liangfuxiang 2013-4-24上午10:45:39 begin: 增加货物状态编码
	/**
	 * 货物状态编码
	 */
	private String actionCode;
	

	/**
	 * actionCode
	 * 
	 * @return the actionCode
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getActionCode() {
		return actionCode;
	}

	/**
	 * @param actionCode the actionCode to set Date:2013-4-24上午10:46:02
	 */

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	// add by liangfuxiang 2013-4-24上午10:45:56 end;

	/**
	 * 获取 货物状态.
	 * 
	 * @return the 货物状态
	 */
	public String getAction() {
		return action;
	}

	/**
	 * 设置 货物状态.
	 * 
	 * @param action the new 货物状态
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * goodsCount
	 * 
	 * @return the goodsCount
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public Integer getGoodsCount() {
		return goodsCount;
	}

	/**
	 * @param goodsCount the goodsCount to set Date:2013-4-10下午4:58:40
	 */

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	/**
	 * 获取 当前位置.
	 * 
	 * @return the 当前位置
	 */
	public String getNowPosition() {
		return nowPosition;
	}

	/**
	 * 设置 当前位置.
	 * 
	 * @param nowPosition the new 当前位置
	 */
	public void setNowPosition(String nowPosition) {
		this.nowPosition = nowPosition;
	}

	/**
	 * 获取 下一部门.
	 * 
	 * @return the 下一部门
	 */
	public String getNextOrgCode() {
		return nextOrgCode;
	}

	/**
	 * 设置 下一部门.
	 * 
	 * @param nextOrgCode the new 下一部门
	 */
	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}

	/**
	 * 获取 预计到达下一部门时间.
	 * 
	 * @return the 预计到达下一部门时间
	 */
	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	/**
	 * 设置 预计到达下一部门时间.
	 * 
	 * @param planArriveTime the new 预计到达下一部门时间
	 */
	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	/**
	 * 获取 提货时间.
	 * 
	 * @return the 提货时间
	 */
	public Date getPlanPickupTime() {
		return planPickupTime;
	}

	/**
	 * 设置 提货时间.
	 * 
	 * @param planPickupTime the new 提货时间
	 */
	public void setPlanPickupTime(Date planPickupTime) {
		this.planPickupTime = planPickupTime;
	}

	/**
	 * 获取 是否可提货.
	 * 
	 * @return the 是否可提货
	 */
	public String getIfAvailedPickup() {
		return ifAvailedPickup;
	}

	/**
	 * 设置 是否可提货.
	 * 
	 * @param ifAvailedPickup the new 是否可提货
	 */
	public void setIfAvailedPickup(String ifAvailedPickup) {
		this.ifAvailedPickup = ifAvailedPickup;
	}

	

}