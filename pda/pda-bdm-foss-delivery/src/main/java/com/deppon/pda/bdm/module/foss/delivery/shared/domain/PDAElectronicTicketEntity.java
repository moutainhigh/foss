package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.shared.util.string.StringUtil;

public class PDAElectronicTicketEntity extends BaseEntity {

	private static final long serialVersionUID = -4644143069957635244L;
	private static final int NOTE_LENGTH = 250;
	//\"cardMoney\":100.0,\"cardTime\":\"2016-03-16 08:58:34\",\"id\":\"123456789\",\"imageUrl\":\"/s/wanggang/aaa.jpg\",\"serialNo\":\"123456789\",\"type\":\"1\",\"wayBillNo\":\"123456789\"}","pdaInfo":"{\"deptCode\":null,\"operType\":\"IMAG_UPLOAD_01\",\"pdaCode\":null,\"pdaType\":null,\"pgmVer\":null,\"userCode\":null,\"userType\
    
	/**
	 * UUID，用当前毫秒数代替
	 */
    private String id;
	/**
     * 运单号
     */
    private List<String> wayBillNo;
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
     * 报错信息
     */
    private String note;
    
    /**
     * 上传状态
     */
    private Integer status ;
    
    /**
     * 操作类型：表示是刷卡还是撤销刷卡(1:表示刷卡成功，0:撤销刷卡）
     */
    private String type;
    
    
	public List<String> getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(List<String> wayBillNo) {
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		if(StringUtil.isNotEmpty(note)&&note.length() > NOTE_LENGTH){
			note = note.substring(0, NOTE_LENGTH);
		}
		this.note = note;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
