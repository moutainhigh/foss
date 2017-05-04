package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteStockInEntity;

/**
 * 小票单据入库DTO
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-23 上午8:14:03
 */
public class NoteStockInDto implements Serializable {

	private static final long serialVersionUID = 8818471780270462041L;

	private String id;

	/**
	 * 申请单号
	 */
	private String noteAppId;

	/**
	 * 下发时间
	 */
	private Date issuedTime;

	/**
	 * 下发人编码
	 */
	private String issuedUserCode;

	/**
	 * 下发人名称
	 */
	private String issuedUserName;

	/**
	 * 下发部门编码
	 */
	private String issuedOrgCode;

	/**
	 * 下发部门名称
	 */
	private String issuedOrgName;

	/**
	 * 发放方式
	 */
	private String issuedType;

	/**
	 * 快递代理单号
	 */
	private String expressDeliveryNumber;

	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 修改的数据类型
	 */
	private String modifyDataType;

	/**
	 * 下发实体列表
	 */
	private List<NoteStockInEntity> stockInList;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return noteAppId
	 */
	public String getNoteAppId() {
		return noteAppId;
	}

	/**
	 * @param noteAppId
	 */
	public void setNoteAppId(String noteAppId) {
		this.noteAppId = noteAppId;
	}

	/**
	 * @return issuedTime
	 */
	public Date getIssuedTime() {
		return issuedTime;
	}

	/**
	 * @param issuedTime
	 */
	public void setIssuedTime(Date issuedTime) {
		this.issuedTime = issuedTime;
	}

	/**
	 * @return issuedUserCode
	 */
	public String getIssuedUserCode() {
		return issuedUserCode;
	}

	/**
	 * @param issuedUserCode
	 */
	public void setIssuedUserCode(String issuedUserCode) {
		this.issuedUserCode = issuedUserCode;
	}

	/**
	 * @return issuedUserName
	 */
	public String getIssuedUserName() {
		return issuedUserName;
	}

	/**
	 * @param issuedUserName
	 */
	public void setIssuedUserName(String issuedUserName) {
		this.issuedUserName = issuedUserName;
	}

	/**
	 * @return issuedOrgCode
	 */
	public String getIssuedOrgCode() {
		return issuedOrgCode;
	}

	/**
	 * @param issuedOrgCode
	 */
	public void setIssuedOrgCode(String issuedOrgCode) {
		this.issuedOrgCode = issuedOrgCode;
	}

	/**
	 * @return issuedOrgName
	 */
	public String getIssuedOrgName() {
		return issuedOrgName;
	}

	/**
	 * @param issuedOrgName
	 */
	public void setIssuedOrgName(String issuedOrgName) {
		this.issuedOrgName = issuedOrgName;
	}

	/**
	 * @return issuedType
	 */
	public String getIssuedType() {
		return issuedType;
	}

	/**
	 * @param issuedType
	 */
	public void setIssuedType(String issuedType) {
		this.issuedType = issuedType;
	}

	/**
	 * @return expressDeliveryNumber
	 */
	public String getExpressDeliveryNumber() {
		return expressDeliveryNumber;
	}

	/**
	 * @param expressDeliveryNumber
	 */
	public void setExpressDeliveryNumber(String expressDeliveryNumber) {
		this.expressDeliveryNumber = expressDeliveryNumber;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return modifyDataType
	 */
	public String getModifyDataType() {
		return modifyDataType;
	}

	/**
	 * @param modifyDataType
	 */
	public void setModifyDataType(String modifyDataType) {
		this.modifyDataType = modifyDataType;
	}

	/**
	 * @return stockInList
	 */
	public List<NoteStockInEntity> getStockInList() {
		return stockInList;
	}

	/**
	 * @param stockInList
	 */
	public void setStockInList(List<NoteStockInEntity> stockInList) {
		this.stockInList = stockInList;
	}

}
