package com.deppon.foss.module.base.baseinfo.api.shared.domain;


import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 提前预警线路实体类
 * @author 262036 - huangwei
 * @date 2015-8-19 下午6:16:03
 * @since
 * @version
 */
public class EarlyWarningEntity extends BaseEntity{

	private static final long serialVersionUID = 1789463434342576672L;
	/**
	 * 出发城市编码
	 */
	private String startCityCode;
	/**
	 * 出发城市名称
	 */
	private String startCityName;
	/**
	 * 到达城市编码
	 */
	private String endCityCode;
	/**
	 * 到达城市名称
	 */
	private String endCityName;
	/**
	 * 操作票数
	 */
	private int operaTickets;
	/**
	 * 兑现票数
	 */
	private int promiseTickets;
	/**
	 * 兑现率
	 */
	private String promiseRate;
	/**
	 * 是否有效
	 */
	private String active;
	

	public String getStartCityCode() {
		return startCityCode;
	}

	public void setStartCityCode(String startCityCode) {
		this.startCityCode = startCityCode;
	}

	public String getStartCityName() {
		return startCityName;
	}

	public void setStartCityName(String startCityName) {
		this.startCityName = startCityName;
	}

	public String getEndCityCode() {
		return endCityCode;
	}

	public void setEndCityCode(String endCityCode) {
		this.endCityCode = endCityCode;
	}

	public String getEndCityName() {
		return endCityName;
	}

	public void setEndCityName(String endCityName) {
		this.endCityName = endCityName;
	}

	public int getOperaTickets() {
		return operaTickets;
	}

	public void setOperaTickets(int operaTickets) {
		this.operaTickets = operaTickets;
	}

	public int getPromiseTickets() {
		return promiseTickets;
	}

	public void setPromiseTickets(int promiseTickets) {
		this.promiseTickets = promiseTickets;
	}

	public String getPromiseRate() {
		return promiseRate;
	}

	public void setPromiseRate(String promiseRate) {
		this.promiseRate = promiseRate;
	}


	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	
	
}
