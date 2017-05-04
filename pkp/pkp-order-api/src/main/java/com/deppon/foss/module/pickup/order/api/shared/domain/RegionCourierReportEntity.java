package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * @ClassName: RegionCourierReportEntity
 * @Description: 快递员同小区每日统计数据
 * @author YANGBIN
 * @date 2014-5-9 下午2:01:48
 * 
 */
public class RegionCourierReportEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	// 收派小区编码
	private String regionCode;
	// 快递员工号
    private String courierCode;
    // 接货订单数
    private BigDecimal recieveOrders;
    // 操作时间
    private Date operateTime;
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getCourierCode() {
		return courierCode;
	}
	public void setCourierCode(String courierCode) {
		this.courierCode = courierCode;
	}
	public BigDecimal getRecieveOrders() {
		return recieveOrders;
	}
	public void setRecieveOrders(BigDecimal recieveOrders) {
		this.recieveOrders = recieveOrders;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
}