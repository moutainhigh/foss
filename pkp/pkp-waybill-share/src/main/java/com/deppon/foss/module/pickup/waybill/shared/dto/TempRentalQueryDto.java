package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 临时租车查询参数
 * @author HeHaisu
 * @date 2014-07-23 下午3:48:45
 * @since
 * @version
 */
public class TempRentalQueryDto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    /**
     * 开始时间（可选）
     */
    private Date startTime;
    /**
     * 结束时间（可选）
     */
    private Date endTime;
    /**
     * 车牌号（可选）
     */
    private String vehicleNo;
    /**
     * 组织编号（必填）
     */
    private String orgCode;
    /**
     * 运单号（可选）
     */
    private List<String> waybillNos;
    /**
     * 派单号（可选）
     */
    private List<String> deliverbillNos;
	/**
     * 分页数据开始位置（可选）
     */
    private int start;
	/**
     * 每页显示条数（可选,默认50）
     */
    private int limit;
    /**
     * 查询类型：按单号：QBB 按日期：QBD(必填)
     */
    private String queryType;
    /**
     * 租车编号
     */
    private String temprentalMarkNo;
    /**
     * 运单为开单部门，派送单为创建部门
     * @return
     */
    private String createOrgCode;
    /**
     * 快递运单号（可选）
     */
    private List<String> expressWaybillNos;
    
    /**
     * 快递交接单号（可选）
     */
    private List<String> expressDeliveryNos ;
    
    
	public List<String> getExpressWaybillNos() {
		return expressWaybillNos;
	}
	public void setExpressWaybillNos(List<String> expressWaybillNos) {
		this.expressWaybillNos = expressWaybillNos;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public List<String> getWaybillNos() {
		return waybillNos;
	}
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}
	public List<String> getDeliverbillNos() {
		return deliverbillNos;
	}
	public void setDeliverbillNos(List<String> deliverbillNos) {
		this.deliverbillNos = deliverbillNos;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getTemprentalMarkNo() {
		return temprentalMarkNo;
	}
	public void setTemprentalMarkNo(String temprentalMarkNo) {
		this.temprentalMarkNo = temprentalMarkNo;
	}
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	public List<String> getExpressDeliveryNos() {
		return expressDeliveryNos;
	}
	public void setExpressDeliveryNos(List<String> expressDeliveryNos) {
		this.expressDeliveryNos = expressDeliveryNos;
	}
	
}