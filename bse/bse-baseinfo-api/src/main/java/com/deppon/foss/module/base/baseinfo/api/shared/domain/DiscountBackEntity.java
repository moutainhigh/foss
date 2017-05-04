package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 时候折信息类
 * @author 132599 ShenWeiHua 2015-02-06
 *
 */
public class DiscountBackEntity extends BaseEntity{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * crm系统的信息id
	 */
	private BigDecimal crmId; 
	/**
	 * 是否启用
	 */
	private String active;
	/**
	 * 客户编码
	 */
    private String cusCode;
    /**
     * 客户名称
     */
    private String cusName;
    /**
     * 方案名称
     */
    private String schemeName;
    /**
     * 方案编码
     */
    private String schemeCode;
    /**
     * 方案生效日期.
     */
    private Date beginTime; 
    
    /**
     * 方案失效日期.
     */
    private Date endTime;
    /**
     * 折扣类型
     */
    private String preferType;
    
    /**
     * 获取crmID
     * @return
     */
	public BigDecimal getCrmId() {
		return crmId;
	}
	/**
	 * 设置crmID
	 * @param crmId
	 */
	public void setCrmId(BigDecimal crmId) {
		this.crmId = crmId;
	}
	/**
	 * 获取状态信息
	 * @return
	 */
	public String getActive() {
		return active;
	}
	/**
	 * 设置状态信息
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * 获取客户编码
	 * @return
	 */
	public String getCusCode() {
		return cusCode;
	}
	/**
	 * 设置客户编码
	 * @param cusCode
	 */
	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
	/**
	 * 获取客户名称
	 * @return
	 */
	public String getCusName() {
		return cusName;
	}
	/**
	 * 设置客户名称
	 * @param cusName
	 */
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	/**
	 * 获取方案名称
	 * @return
	 */
	public String getSchemeName() {
		return schemeName;
	}
	/**
	 * 设置方案名称
	 * @param schemeName
	 */
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	/**
	 * 获取方案编码
	 * @return
	 */
	public String getSchemeCode() {
		return schemeCode;
	}
	/**
	 * 设置方案编码
	 * @param schemeCode
	 */
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	/**
	 * 获取方案开始时间
	 * @return
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	/**
	 * 设置方案开始时间
	 * @param beginTime
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * 获取方案结束时间
	 * @return
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置方案结束时间
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取折扣类型
	 * @return
	 */
	public String getPreferType() {
		return preferType;
	}
	/**
	 * 设置折扣类型
	 * @param preferType
	 */
	public void setPreferType(String preferType) {
		this.preferType = preferType;
	}
    
}
