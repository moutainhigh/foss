package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 作废运单记录表
 * @author Foss-105888-Zhangxingwang
 * @date 2014-8-12 11:24:44
 */
public class WaybillSupplementLogEntity extends BaseEntity{
	/**
     * 生成序列化标识
     * （用一句话描述这个变量表示什么）
     */
	private static final long serialVersionUID = 7645763793802365606L;

	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 运单号ID
	 */
    private String waybillId;
    
    /**
     * 待补录运单ID
     */
    private String waybillPendingId;
    
    /**
     * 实际承运信息表ID
     */
    private String actualFreightId;
   
    /**
     * 作废原因
     */
    private String invalidReason;
    
    /**
     * 操作人
     */
    private String operateCode;

    /**
     * 作废人所在组织
     */
    private String invalidOrgCode;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 修改时间
     */
    private Date modifyTime;
    
    /**
     * 作废类别
     */
    private String invalidType;
    
    /**
     * 工作流号
     */
    private String workflowNo;
    
    /**
     * 旧运单号
     */
    private String oldWaybillNo;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 新运单号
     */
    private String newWaybillNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? UUIDUtils.getUUID() : id.trim();
    }

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId == null ? null : waybillId.trim();
    }

    public String getWaybillPendingId() {
        return waybillPendingId;
    }

    public void setWaybillPendingId(String waybillPendingId) {
        this.waybillPendingId = waybillPendingId == null ? null : waybillPendingId.trim();
    }

    public String getActualFreightId() {
        return actualFreightId;
    }

    public void setActualFreightId(String actualFreightId) {
        this.actualFreightId = actualFreightId == null ? null : actualFreightId.trim();
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason == null ? null : invalidReason.trim();
    }

    public String getOperateCode() {
        return operateCode;
    }

    public void setOperateCode(String operateCode) {
        this.operateCode = operateCode == null ? null : operateCode.trim();
    }

    public String getInvalidOrgCode() {
        return invalidOrgCode;
    }

    public void setInvalidOrgCode(String invalidOrgCode) {
        this.invalidOrgCode = invalidOrgCode == null ? null : invalidOrgCode.trim();
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

	public String getInvalidType() {
		return invalidType;
	}

	public void setInvalidType(String invalidType) {
		this.invalidType = invalidType;
	}

	public String getWorkflowNo() {
		return workflowNo;
	}

	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOldWaybillNo() {
		return oldWaybillNo;
	}

	public void setOldWaybillNo(String oldWaybillNo) {
		this.oldWaybillNo = oldWaybillNo;
	}

	public String getNewWaybillNo() {
		return newWaybillNo;
	}

	public void setNewWaybillNo(String newWaybillNo) {
		this.newWaybillNo = newWaybillNo;
	}
}