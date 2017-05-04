package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.util.Date;
import java.util.List;

public class SalesdeptDeliveryEntity {
	/**
	 * ID
	 */
    private String id;
    /**
     * 运单号
     */
    private String wayBillNo;
    /**
     * 运单状态
     */
    private String waystaus;
    /**
     * 库存件数
     */
    private Long stock;
    /**
     * 
     */
    private Long billing;

    /**
     * 供应商名称
     */
    private String suppliers;

    /**
     * 供应商订单
     */
    private String supplierOrder;

    /**
     * 收派方式
     */
    private String deliveryMethod;

    /**
     * 货物名称
     */
    private String goodsName;

    /**
     * 重量
     */
    private String weight;

    /**
     * 体积
     */
    private String volume;

    /**
     * 确认时间
     */
    private Date confirmationTime;

    /**
     * 是否损坏
     */
    private String isbroken;

    /**
     * 损坏备注
     */
    private String brokennote;

    /**
     * 货物明细
     */
    private String goodsDetails;

    /**
     * 提货人身份证号
     */
    private String picPersoncard;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 创建人
     */
    private String createuser;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 修改人
     */
    private String modifyuser;

    /**
     * 
     */
    private String[] wayNos;
    
    /**
     * 
     */
    private List<String> wayBillNos;
    /**
     * 是否有效
     */
    private String active;
    /**
     * 交货确认部门
     */
    private String orgCode;
    

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

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

	public String getWaystaus() {
        return waystaus;
    }

    public void setWaystaus(String waystaus) {
        this.waystaus = waystaus;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getBilling() {
        return billing;
    }

    public void setBilling(Long billing) {
        this.billing = billing;
    }

    public String getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(String suppliers) {
        this.suppliers = suppliers;
    }

    public String getSupplierOrder() {
        return supplierOrder;
    }

    public void setSupplierOrder(String supplierOrder) {
        this.supplierOrder = supplierOrder;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Date getConfirmationTime() {
        return confirmationTime;
    }

    public void setConfirmationTime(Date confirmationTime) {
        this.confirmationTime = confirmationTime;
    }

    public String getIsbroken() {
        return isbroken;
    }

    public void setIsbroken(String isbroken) {
        this.isbroken = isbroken;
    }

    public String getBrokennote() {
        return brokennote;
    }

    public void setBrokennote(String brokennote) {
        this.brokennote = brokennote;
    }

    public String getGoodsDetails() {
        return goodsDetails;
    }

    public void setGoodsDetails(String goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    public String getPicPersoncard() {
        return picPersoncard;
    }

    public void setPicPersoncard(String picPersoncard) {
        this.picPersoncard = picPersoncard;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getModifyuser() {
        return modifyuser;
    }

    public void setModifyuser(String modifyuser) {
        this.modifyuser = modifyuser;
    }

	public String[] getWayNos() {
		return wayNos;
	}

	public void setWayNos(String[] wayNos) {
		this.wayNos = wayNos;
	}

	public List<String> getWayBillNos() {
		return wayBillNos;
	}

	public void setWayBillNos(List<String> wayBillNos) {
		this.wayBillNos = wayBillNos;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
    
}