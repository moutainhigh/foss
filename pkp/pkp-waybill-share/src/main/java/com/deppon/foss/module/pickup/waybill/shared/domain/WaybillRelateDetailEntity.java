package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;

public class WaybillRelateDetailEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2355476272164140331L;

	//ID
	private String id;

	//母单号
    private String parentWaybillNo;

    //子单号
    private String waybillNo;

    //订单号
    private String orderNo;

    //母订单号
    private String parentOrderNo;

    //处理类型:"PDA_ACTIVE"--PDA已补录 ，"PC_ACTIVE"--暂存已开单
    private String pendingType;

    //创建人编码
    private String createUserCode;

    //修改人编码
    private String modifyUserCode;

    //修改人组织
    private String modifyOrgCode;

    //运单类型
    private String waybillType;

    //开单部门组织
    private String createOrgCode;

    //收货部门组织
    private String receiveOrgCode;

	//货物总重量
	private BigDecimal goodsWeightTotal;

	//货物总体积
	private BigDecimal goodsVolumeTotal;

    //有效状态
    private String active;

    //开单时间
    private Date createTime;

    //修改时间
    private Date modifyTime;

    //业务开单时间
    private Date billTime;
    
    //是否子母件
    private String isPicPackage;
    
    //单件重量
    private BigDecimal weight;
    
    //单件体积
    private BigDecimal volume;
    
    //是否更改
    private boolean isChange;
    
    //任务号
    private String taskId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentWaybillNo() {
        return parentWaybillNo;
    }

    public void setParentWaybillNo(String parentWaybillNo) {
        this.parentWaybillNo = parentWaybillNo == null ? null : parentWaybillNo.trim();
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo == null ? null : waybillNo.trim();
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
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

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode == null ? null : createUserCode.trim();
    }

    public String getModifyUserCode() {
        return modifyUserCode;
    }

    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode == null ? null : modifyUserCode.trim();
    }

    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode == null ? null : modifyOrgCode.trim();
    }

    public String getWaybillType() {
        return waybillType;
    }

    public void setWaybillType(String waybillType) {
        this.waybillType = waybillType == null ? null : waybillType.trim();
    }

    public String getCreateOrgCode() {
        return createOrgCode;
    }

    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode == null ? null : createOrgCode.trim();
    }

    public String getReceiveOrgCode() {
        return receiveOrgCode;
    }

    public void setReceiveOrgCode(String receiveOrgCode) {
        this.receiveOrgCode = receiveOrgCode == null ? null : receiveOrgCode.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getPendingType() {
        return pendingType;
    }

    public void setPendingType(String pendingType) {
        this.pendingType = pendingType == null ? null : pendingType.trim();
    }

    public String getParentOrderNo() {
        return parentOrderNo;
    }

    public void setParentOrderNo(String parentOrderNo) {
        this.parentOrderNo = parentOrderNo == null ? null : parentOrderNo.trim();
    }

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public String getIsPicPackage() {
		return isPicPackage;
	}

	public void setIsPicPackage(String isPicPackage) {
		this.isPicPackage = isPicPackage;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public boolean isChange() {
		return isChange;
	}

	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}
	
	
	
}