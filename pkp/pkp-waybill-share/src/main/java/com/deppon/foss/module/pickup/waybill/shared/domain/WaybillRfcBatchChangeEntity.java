package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 批量更改实体
 * @author Foss-105888-Zhangxingwang
 * @date 2015-2-4 09:08:11
 */
public class WaybillRfcBatchChangeEntity extends BaseEntity{
	private static final long serialVersionUID = 1L;

	//ID
	private String id;

	//运单号
    private String waybillNo;
    
    //更改的体积
    private BigDecimal weightChange;

    //体积更改
    private BigDecimal volumnChange;

    //公布价运费更改
    private BigDecimal transportFeeChange;

    //产品类型更改
    private String productChange;

    //更改状态
    private String changeStatus;

    //发货客户编码
    private String deliverCustomerCode;

    //导入时间
    private Date importTime;

    //修改时间
    private Date modifyTime;

    //创建人编码
    private String createUserCode;

    //修改人编码
    private String modifyUserCode;

    //定时任务处理ID
    private String jobId;

    //更改
    private String failReason;

    //更改备注
    private String changeNote;
    //新增  装卸费   --  zhoupengyu 265041
    private BigDecimal servicefee;
    public BigDecimal getServicefee() {
		return servicefee;
	}

	public void setServicefee(BigDecimal servicefee) {
		this.servicefee = servicefee;
	}
    //get、set方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo == null ? null : waybillNo.trim();
    }

    public BigDecimal getWeightChange() {
        return weightChange;
    }

    public void setWeightChange(BigDecimal weightChange) {
        this.weightChange = weightChange;
    }

    public BigDecimal getVolumnChange() {
        return volumnChange;
    }

    public void setVolumnChange(BigDecimal volumnChange) {
        this.volumnChange = volumnChange;
    }

    public BigDecimal getTransportFeeChange() {
        return transportFeeChange;
    }

    public void setTransportFeeChange(BigDecimal transportFeeChange) {
        this.transportFeeChange = transportFeeChange;
    }

    public String getProductChange() {
        return productChange;
    }

    public void setProductChange(String productChange) {
        this.productChange = productChange == null ? null : productChange.trim();
    }

    public String getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(String changeStatus) {
        this.changeStatus = changeStatus == null ? null : changeStatus.trim();
    }

    public String getDeliverCustomerCode() {
        return deliverCustomerCode;
    }

    public void setDeliverCustomerCode(String deliverCustomerCode) {
        this.deliverCustomerCode = deliverCustomerCode == null ? null : deliverCustomerCode.trim();
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    public String getChangeNote() {
        return changeNote;
    }

    public void setChangeNote(String changeNote) {
        this.changeNote = changeNote == null ? null : changeNote.trim();
    }
}
