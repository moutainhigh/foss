package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RequestGreenHandWrapEntity implements Serializable {

    /**
     * 定义从DOP传过来的数据进行封装
     */
    private static final long serialVersionUID = 1L;

    // 推送财务自助编码
    private String zfbBillNum;

    // 定义运单号
    private String waybillNo;

    // 定义从dop传过来的金额
    private BigDecimal dopAmount;

    // 定义费用类型；补贴是1，运费是0
    private String costType;

    // 定义dop传的时间
    private Date doptime;

    // 定义dop传过来的收款账号
    private String receivableNo;

    // 运单号集合
    private List<String> statementBillNos;

    /**
     * 是否连接异常,FOSS与财务数据异常，否则DOP重新推送数据
     */
    private String isException;

    /**
     * 数据来源：GG、ZFB、ZFBCODE
     */
    private String resource;

    /**
     * 果果是否要推送
     */
    private String isPush;

    /**
     * 催款部门标杆编码
     */
    private String dunningOrgCode;

    /**
     * 是否到付，查询到付应收单
     */
    private String isDr;

    /**
     * 是否要将暂存表数据放到转移表
     */
    private String isDelete;

    public String getZfbBillNum() {
        return zfbBillNum;
    }

    public void setZfbBillNum(String zfbBillNum) {
        this.zfbBillNum = zfbBillNum;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getIsDr() {
        return isDr;
    }

    public void setIsDr(String isDr) {
        this.isDr = isDr;
    }

    public String getDunningOrgCode() {
        return dunningOrgCode;
    }

    public void setDunningOrgCode(String dunningOrgCode) {
        this.dunningOrgCode = dunningOrgCode;
    }

    public String getIsPush() {
        return isPush;
    }

    public void setIsPush(String isPush) {
        this.isPush = isPush;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getIsException() {
        return isException;
    }

    public void setIsException(String isException) {
        this.isException = isException;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public BigDecimal getDopAmount() {
        return dopAmount;
    }

    public void setDopAmount(BigDecimal dopAmount) {
        this.dopAmount = dopAmount;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public Date getDoptime() {
        return doptime;
    }

    public void setDoptime(Date doptime) {
        this.doptime = doptime;
    }

    public String getReceivableNo() {
        return receivableNo;
    }

    public void setReceivableNo(String receivableNo) {
        this.receivableNo = receivableNo;
    }

    public List<String> getStatementBillNos() {
        return statementBillNos;
    }

    public void setStatementBillNos(List<String> statementBillNos) {
        this.statementBillNos = statementBillNos;
    }

}
