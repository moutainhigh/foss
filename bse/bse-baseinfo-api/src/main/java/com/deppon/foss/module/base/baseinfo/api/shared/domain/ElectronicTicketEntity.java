package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
public class ElectronicTicketEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
    private String id;
    /**
     * 运单号
     */
    private String wayBillNo;
    /**
     * 电子小票图片地址
     */
    private String imageUrl;
    /**
     * 交易流水号
     */
    private String serialNo;
    
    /**
	 * 刷卡金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal cardMoney;
   
    
    /**
     * 刷卡时间
     */
    private Date cardTime;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 创建人
     */
    private String createuser;

   
    /**
     * 修改人
     */
    private String modifyuser;

   
    /**
     * 是否有效
     */
    private String active;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getWayBillNo() {
		return wayBillNo;
	}


	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getSerialNo() {
		return serialNo;
	}


	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}


	public BigDecimal getCardMoney() {
		return cardMoney;
	}


	public void setCardMoney(BigDecimal cardMoney) {
		this.cardMoney = cardMoney;
	}


	public Date getCardTime() {
		return cardTime;
	}


	public void setCardTime(Date cardTime) {
		this.cardTime = cardTime;
	}


	

	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getModifyTime() {
		return modifyTime;
	}


	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	public String getCreateuser() {
		return createuser;
	}


	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}


	public String getModifyuser() {
		return modifyuser;
	}


	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}


	public String getActive() {
		return active;
	}


	public void setActive(String active) {
		this.active = active;
	}
   
    
    
}