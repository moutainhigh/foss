package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * PDAUserEntity：配合PDA绑定和校验封签JSON信息
 * @author 106162-foss-liping
 * @date 2016-05-03 上午8:52:54
 */
public class RequestSealBindANDCheckOutEntity implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 配合PDA同步用户
	 */
	private   PDAUserEntity pDAUserEntity;
	/**
	 * 封签集合
	 */
	private List<SealNumANDStateEntity> list;
	/**
	 * 车牌号
	 */
	private String carNumber;	
	/**
	 * 目的站
	 */
	private String destination;	
	/**
	 * 身份标识 判断是出发还是到达的一个标示
	 */
	private String sendType;	
	/**
	 * 操作类型
	 */
	private String operType;
	/**
	 * 封签类型
	 */
	private String sealsType;
	/**
	 * 出发部门
	 */
	private String departmentCode;

	public PDAUserEntity getpDAUserEntity() {
		return pDAUserEntity;
	}

	public void setpDAUserEntity(PDAUserEntity pDAUserEntity) {
		this.pDAUserEntity = pDAUserEntity;
	}

	public List<SealNumANDStateEntity> getList() {
		return list;
	}

	public void setList(List<SealNumANDStateEntity> list) {
		this.list = list;
	}

	

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSealsType() {
		return sealsType;
	}

	public void setSealsType(String sealsType) {
		this.sealsType = sealsType;
	}
	
	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	@Override
	public String toString() {
		return "RequestSealBindANDCheckOutEntity [pDAUserEntity="
				+ pDAUserEntity + ", list=" + list + ", carNumber=" + carNumber
				+ ", destination=" + destination + ", sendType=" + sendType
				+ ", operType=" + operType + ", sealsType=" + sealsType
				+ ", departmentCode=" + departmentCode + "]";
	}


}
