package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author 092020-lipengfei
 * @version V1.0
 * @Description 待叉区距离实体
 * @Time 2014-4-25
 */
public class WaitForkAreaDistanceEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -688823855113190179L;
	/**
	 * 外场编码
	 */
	private String transferCode;
	/**
	 * 待叉区编码
	 */
	private String waitForkAreaCode;
	/**
	 * 目标编码
	 */
	private String targetCode;
	/**
	 * 目标类型（当前包括库区、月台）
	 */
	private String targetType;
	/**
	 * 与目标的距离
	 */
	private String distance;
	/**
	 * 是否有效
	 */
	private String active;
	public String getWaitForkAreaCode() {
		return waitForkAreaCode;
	}
	public void setWaitForkAreaCode(String waitForkAreaCode) {
		this.waitForkAreaCode = waitForkAreaCode;
	}
	public String getTargetCode() {
		return targetCode;
	}
	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getTransferCode() {
		return transferCode;
	}
	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}
}
