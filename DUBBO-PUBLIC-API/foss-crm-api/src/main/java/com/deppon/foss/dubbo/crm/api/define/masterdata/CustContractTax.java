package com.deppon.foss.dubbo.crm.api.define.masterdata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustContractTax extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -861536522192294616L;
	
	/**
     * 主键ID
     */
    private BigDecimal fid;

    /**
     * 创建时间
     */
    private Date fcreatetime;

    /**
     * 创建人
     */
    private BigDecimal fcreateuserid;

    /**
     * 最后修改时间
     */
    private Date flastupdatetime;

    /**
     * 最后修改人
     */
    private BigDecimal flastmodifyuserid;

    /**
     * 对应合同id
     */
    private BigDecimal fcontractid;
    
    /**
     * 开始时间
     */
    private Date fbegintime;
    
    /**
     * 结束时间
     */
    private Date fendtime;
    
    /**
     * 发票标记
     */
    private String finvoiceType;
    
    /**
     * 签署合同公司
     */
    private String signCompany;
    
    /**
     * 获取FID
     * @return
     */
    public BigDecimal getFid() {
		return fid;
	}
    
    /**
     * 设置FID
     * @param fid
     */
	public void setFid(BigDecimal fid) {
		this.fid = fid;
	}
	
	/**
	 * 获取创建时间
	 * @return
	 */
	public Date getFcreatetime() {
		return fcreatetime;
	}
	
	/**
	 * 设置创建时间
	 * @param fcreatetime
	 */
	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}
	
	/**
	 * 获取创建人ID
	 * @return
	 */
	public BigDecimal getFcreateuserid() {
		return fcreateuserid;
	}
	
	/**
	 * 设置创建人ID
	 * @param fcreateuserid
	 */
	public void setFcreateuserid(BigDecimal fcreateuserid) {
		this.fcreateuserid = fcreateuserid;
	}
	
	/**
	 * 获取最后修改时间
	 * @return
	 */
	public Date getFlastupdatetime() {
		return flastupdatetime;
	}
	
	/**
	 * 设置最后修改时间
	 * @param flastupdatetime
	 */
	public void setFlastupdatetime(Date flastupdatetime) {
		this.flastupdatetime = flastupdatetime;
	}
	
	/**
	 * 获取最后修改人
	 * @return
	 */
	public BigDecimal getFlastmodifyuserid() {
		return flastmodifyuserid;
	}
	
	/**
	 * 设置最后修改人
	 * @param flastmodifyuserid
	 */
	public void setFlastmodifyuserid(BigDecimal flastmodifyuserid) {
		this.flastmodifyuserid = flastmodifyuserid;
	}
	
	/**
	 * 获取合同ID
	 * @return
	 */
	public BigDecimal getFcontractid() {
		return fcontractid;
	}
	
	/**
	 * 设置合同ID
	 * @param fcontractid
	 */
	public void setFcontractid(BigDecimal fcontractid) {
		this.fcontractid = fcontractid;
	}
	
	/**
	 * 获取发票标记开始使用时间
	 * @return
	 */
	public Date getFbegintime() {
		return fbegintime;
	}
	
	/**
	 * 设置发票标记开始使用时间
	 * @param fbegintime
	 */
	public void setFbegintime(Date fbegintime) {
		this.fbegintime = fbegintime;
	}
	
	/**
	 * 获取发票标记结束使用时间
	 * @return
	 */
	public Date getFendtime() {
		return fendtime;
	}
	
	/**
	 * 设置发票标记结束使用时间
	 * @param fendtime
	 */
	public void setFendtime(Date fendtime) {
		this.fendtime = fendtime;
	}
	
	/**
	 * 获取发票标记
	 * @return
	 */
	public String getFinvoiceType() {
		return finvoiceType;
	}
	
	/**
	 * 设置发票标记
	 * @param finvoiceType
	 */
	public void setFinvoiceType(String finvoiceType) {
		this.finvoiceType = finvoiceType;
	}
	
	/**
	 * 获取合同签署公司
	 * @return
	 */
	public String getSignCompany() {
		return signCompany;
	}
	
	/**
	 * 设置合同签署公司
	 * @param signCompany
	 */
	public void setSignCompany(String signCompany) {
		this.signCompany = signCompany;
	}

	@Override
	public String toString() {
		return "CustContractTax [fid=" + fid + ", fcreatetime=" + fcreatetime + ", fcreateuserid=" + fcreateuserid
				+ ", flastupdatetime=" + flastupdatetime + ", flastmodifyuserid=" + flastmodifyuserid + ", fcontractid="
				+ fcontractid + ", fbegintime=" + fbegintime + ", fendtime=" + fendtime + ", finvoiceType="
				+ finvoiceType + ", signCompany=" + signCompany + "]";
	}


}
