package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 快递派件交接 提交任务后生成派送单 dto
 * @author 243921-foss-zhangtingting
 * @date 2015-05-11 下午3:12:20
 */
public class DeliverbillExpressTaskDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 运单信息列表
	 */
	private List<DeliverbillExpressTaskDto> list;
	/**
	 * 派送单号
	 */
	private String deliverbillNo;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 运单件数
	 */
	private Integer goodQtyTotal;
	/**
	 *  创建人工号
	 */
	private String createUserCode;
	/**
	 * 创建人名字
	 */
	private String createUserName;
	/**
	 * 创建部门编码
	 */
	private String createOrgCode;
	/**
	 * 创建部门
	 */
	private String createOrgName;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 *  生成的新的派送单号  返回给外部中转使用
	 */
	private String deliveryNo;

	public List<DeliverbillExpressTaskDto> getList() {
		return list;
	}

	public void setList(List<DeliverbillExpressTaskDto> list) {
		this.list = list;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}

	public Integer getGoodQtyTotal() {
		return goodQtyTotal;
	}

	public void setGoodQtyTotal(Integer goodQtyTotal) {
		this.goodQtyTotal = goodQtyTotal;
	}
}
