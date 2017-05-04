package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 小票单据发放入库
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-11 下午5:23:25
 */
public class NoteStockInEntity extends BaseEntity {

	private static final long serialVersionUID = 5831261306047794432L;

	/**
	 * 小票单据申请记录ID
	 */
	private String noteAppId;

	/**
	 * 下发起始编号
	 */
	private Integer beginNo;

	/**
	 * 下发终止编号
	 */
	private Integer endNo;

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
	 * @return noteAppId
	 */
	public String getNoteAppId() {
		return noteAppId;
	}

	/**
	 * @param  noteAppId  
	 */
	public void setNoteAppId(String noteAppId) {
		this.noteAppId = noteAppId;
	}

	/**
	 * @return beginNo
	 */
	public Integer getBeginNo() {
		return beginNo;
	}

	/**
	 * @param  beginNo  
	 */
	public void setBeginNo(Integer beginNo) {
		this.beginNo = beginNo;
	}

	/**
	 * @return endNo
	 */
	public Integer getEndNo() {
		return endNo;
	}

	/**
	 * @param  endNo  
	 */
	public void setEndNo(Integer endNo) {
		this.endNo = endNo;
	}

	/**
	 * @return issuedTime
	 */
	public Date getIssuedTime() {
		return issuedTime;
	}

	/**
	 * @param  issuedTime  
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
	 * @param  issuedUserCode  
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
	 * @param  issuedUserName  
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
	 * @param  issuedOrgCode  
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
	 * @param  issuedOrgName  
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
	 * @param  issuedType  
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
	 * @param  expressDeliveryNumber  
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
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

}
