package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 218392 zhangyongxue
 * on 2016/06/12.
 */
public class VTSCodAuditDto implements Serializable {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	//运单号
    private List<String> waybillNos;
    /**
     * 代收货款类别
     */
    private String codType;
    /**
     * 签收时间截止
     */
    private Date signTime;
    
    /**
     * 开单时间
     */
    private Date billTime;
    
    /**
     * 退款金额起
     */
    private BigDecimal codAmountBegin;
    /**
     *退款金额终止
     */
    private BigDecimal codAmountEnd;
    /**
     * 付款方式
     */
    private String paymentType;
    /**
     * 签收-开单时长
     */
    private long signBillDiffer;
    /**
     * 审核状态
     */
    private String lockStatus;
    /**
     * 到达部门
     */
    private String destOrgCod;
    /**
     * 代收货款更改金额
     */
    private BigDecimal codAmountDiffer;
    /**
     * 是否有货物轨迹
     */
    private String hasTrack;
    /**
     * 出发部门是否等于到达部门
     */
    private String destEqOrig;

    /**
     * 合计条数
     */
    private int totalCount;
    /**
     * 锁定条数
     */
    private int lockCount;
    /**
     * 未锁定条数
     */
    private int unlockCount;

    private String modifyUserCode;


    private String modifyUerName;

    public String getModifyUserCode() {
        return modifyUserCode;
    }

    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }

    public String getModifyUerName() {
        return modifyUerName;
    }

    public void setModifyUerName(String modifyUerName) {
        this.modifyUerName = modifyUerName;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getLockCount() {
        return lockCount;
    }

    public void setLockCount(int lockCount) {
        this.lockCount = lockCount;
    }

    public int getUnlockCount() {
        return unlockCount;
    }

    public void setUnlockCount(int unlockCount) {
        this.unlockCount = unlockCount;
    }

    public List<String> getWaybillNos() {
        return waybillNos;
    }

    public void setWaybillNos(List<String> waybillNos) {
        this.waybillNos = waybillNos;
    }

    public String getCodType() {
        return codType;
    }

    public void setCodType(String codType) {
        this.codType = codType;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public BigDecimal getCodAmountBegin() {
        return codAmountBegin;
    }

    public void setCodAmountBegin(BigDecimal codAmountBegin) {
        this.codAmountBegin = codAmountBegin;
    }

    public BigDecimal getCodAmountEnd() {
        return codAmountEnd;
    }

    public void setCodAmountEnd(BigDecimal codAmountEnd) {
        this.codAmountEnd = codAmountEnd;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public long getSignBillDiffer() {
        return signBillDiffer;
    }

    public void setSignBillDiffer(long signBillDiffer) {
        this.signBillDiffer = signBillDiffer;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getDestOrgCod() {
        return destOrgCod;
    }

    public void setDestOrgCod(String destOrgCod) {
        this.destOrgCod = destOrgCod;
    }

    public BigDecimal getCodAmountDiffer() {
        return codAmountDiffer;
    }

    public void setCodAmountDiffer(BigDecimal codAmountDiffer) {
        this.codAmountDiffer = codAmountDiffer;
    }

    public String getHasTrack() {
        return hasTrack;
    }

    public void setHasTrack(String hasTrack) {
        this.hasTrack = hasTrack;
    }

    public String getDestEqOrig() {
        return destEqOrig;
    }

    public void setDestEqOrig(String destEqOrig) {
        this.destEqOrig = destEqOrig;
    }

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
    
}
