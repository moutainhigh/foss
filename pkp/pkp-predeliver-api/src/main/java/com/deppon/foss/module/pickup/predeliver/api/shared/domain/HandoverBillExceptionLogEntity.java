package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.annotation.AllowBlank;

/** 
 * @ClassName: HandoverBillExceptionLog 
 * @Description: 交单自动匹配小区和车辆异常日志类 
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-9 上午9:43:13 
 *  
 */
public class HandoverBillExceptionLogEntity extends BaseEntity {

	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键Id
	 */
	private String id;
	
	/**
	 * 交单Id
	 */
	private String handoverBillId;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 异常类型
	 */
	private String exceptionType;
	
	/**
	 * 异常原因
	 */
	private String exceptionReason;
	
	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * 获取id
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置id
	 * @param id 要设置的id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	

	/**
	 * 获取handoverBillId
	 * @return the handoverBillId
	 */
	@AllowBlank
	public String getHandoverBillId() {
		return handoverBillId;
	}

	/**
	 * 设置handoverBillId
	 * @param handoverBillId 要设置的handoverBillId
	 */
	public void setHandoverBillId(String handoverBillId) {
		this.handoverBillId = handoverBillId;
	}

	/**
	 * 获取waybillNo
	 * @return the waybillNo
	 */
	@AllowBlank
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置waybillNo
	 * @param waybillNo 要设置的waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取exceptionType
	 * @return the exceptionType
	 */
	@AllowBlank
	public String getExceptionType() {
		return exceptionType;
	}

	/**
	 * 设置exceptionType
	 * @param exceptionType 要设置的exceptionType
	 */
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	/**
	 * 获取exceptionReason
	 * @return the exceptionReason
	 */
	public String getExceptionReason() {
		return exceptionReason;
	}

	/**
	 * 设置exceptionReason
	 * @param exceptionReason 要设置的exceptionReason
	 */
	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}

	/**
	 * 获取createTime
	 * @return the createTime
	 */
	@AllowBlank
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置createTime
	 * @param createTime 要设置的createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 无参构造函数
	 */
	public HandoverBillExceptionLogEntity() {
		super();
	}

	/**
	 * 带参构造函数
	 * @param handoverBillId 交单Id
	 * @param waybillNo 运单号
	 * @param exceptionType 异常类型
	 * @param exceptionReason 异常原因
	 * @param createTime 创建时间
	 */
	public HandoverBillExceptionLogEntity(String handoverBillId,
			String waybillNo, String exceptionType, String exceptionReason,
			Date createTime) {
		super();
		this.handoverBillId = handoverBillId;
		this.waybillNo = waybillNo;
		this.exceptionType = exceptionType;
		this.exceptionReason = exceptionReason;
		this.createTime = createTime;
	}
	
}
