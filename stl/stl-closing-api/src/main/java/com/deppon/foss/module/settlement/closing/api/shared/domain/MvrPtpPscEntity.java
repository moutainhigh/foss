package com.deppon.foss.module.settlement.closing.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合伙人子公司月报.
 *
 * @author foss-youkun
 * @date 2016-3-18 下午5:57:28
 */
public class MvrPtpPscEntity extends BaseEntity {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 7955138508201273787L;

	private String id;

    /**
     * 凭证开始日期
     */
    private Date voucherBeginTime;

    /**
     * 凭证结束日期
     */
    private Date voucherEndTime;

    /**
     * 期间
     */
    private String period;

    /**
     * 业务类型（运输性质）
     */
    private String productCode;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 始发部门名称
     */
    private String origOrgName;

    /**
     * 始发部门编码
     */
    private String origOrgCode;

    /**
     * 到达部门名称
     */
    private String destOrgName;

    /**
     * 到达部门编码
     */
    private String destOrgCode;

    /**
     * 预收部门名称
     */
    private String generatingOrgName;

    /**
     * 预收部门编码
     */
    private String generatingOrgCode;

    /**
     * 应付部门
     */
    private String payableOrgName;

    /**
     * 应付部门编码
     */
    private String payableOrgCode;

    /**
     * 应收编码
     */
    private String receivableOrgCode;

    /**
     * 应收部门
     */
    private String receivableOrgName;
    
    /**
     * 快递始发部门编码
     */
    private String expressOrigOrgCode;
    
    /**
     * 快递始发部门名称
     */
    private String expressOrigOrgName;
    
    /**
     * 快递到达部门编码
     */
    private String expressDestOrgCode;
    
    /**
     * 快递到达部门名称
     */
    private String expressDestOrgName;
    
    /**
     * 客户名称快递点部编码
     */
    private String expressCustomerOrgCode;
    
    /**
     * 客户名称快递点部名称
     */
    private String expressCustomerOrgName;
    
    /**
     * 预存款项充值
     */
    private BigDecimal ptpPdeFcus;

    /**
     * 预收加盟商款项
     */
    private BigDecimal ptpPdeApf;

    /**
     * 预收加盟冲应收始发提成已签收
     */
    private BigDecimal ptpPdeAra;

    /**
     * 预收加盟冲应收委托派费已签收
     */
    private BigDecimal ptpPdeApc;

    /**
     * 预收加盟冲应收到付运费已签收(D)
     */
    private BigDecimal ptpPdeCsrd;

    /**
     * 预收加盟冲应收到付运费已签收（H）
     */
    private BigDecimal ptpPdeCsrh;

    /**
     * 预收加盟冲应收代收货款已签收
     */
    private BigDecimal ptpPdeRrpc;

    /**
     * 应收罚款已核销
     */
    private BigDecimal ptpPdeFhbw;

    /**
     * 退预收付款申请
     */
    private BigDecimal ptpPdeBapa;

    /**
     * 还款到付运费
     */
    private BigDecimal ptpRtfRtp;

    /**
     * 还款电汇未签收
     */
    private BigDecimal ptpWfrRtr;

    /**
     * 还款电汇已签收
     */
    private BigDecimal ptpWfrRtpr;
    
    /**
     * 还款电汇（H）
     */
    private BigDecimal pscTtReph;

    /**
     * 零担到达提成入成本
     */
    private BigDecimal ptpSctLcrc;

    /**
     * 快递到达提成入成本
     */
    private BigDecimal ptpSctEac;

    /**
     * 零担到达提成反确认
     */
    private BigDecimal ptpSctZld;

    /**
     * 快递到达提成反确认
     */
    private BigDecimal ptpSctUeac;

    /**
     * 委托派费代付已签收
     */
    private BigDecimal ptpCphFpc;

    /**
     * 委托派费代付反签收
     */
    private BigDecimal ptpCphPrc;

    /**
     * 到达提成付款申请
     */
    private BigDecimal ptpIprAcpa;

    /**
     * 委托派费代付申请
     */
    private BigDecimal ptpIprPafc;

    /**
     * 到付运费代付申请
     */
    private BigDecimal ptpIprPfpa;

    /**
     * 签收后非月结部分始发提成应收已核销金额  公布价运费
     */
    private BigDecimal ptpQfyjOcrfyPpf;

    /**
     * 签收后非月结部分始发提成应收已核销金额  开单操作费
     */
    private BigDecimal ptpQfyjOcrfyBof;

    /**
     * 签收后非月结部分始发提成应收已核销金额 包装费
     */
    private BigDecimal ptpQfyjOcrfyPc;

    /**
     * 签收后非月结部分始发提成应收已核销金额  保价费
     */
    private BigDecimal ptpQfyjOcrfyTif;

    /**
     * 签收后非月结部分始发提成应收已核销金额 代收货款手续费
     */
    private BigDecimal ptpQfyjOcrfyCf;

    /**
     * 签收后非月结部分始发提成应收已核销金额 送货费（不含上楼）
     */
    private BigDecimal ptpQfyjOcrfyDf;

    /**
     * 签收后非月结部分始发提成应收已核销金额  基础送货费
     */
    private BigDecimal ptpQfyjOcrfyBdc;

    /**
     * 签收后非月结部分始发提成应收已核销金额  其他费用
     */
    private BigDecimal ptpQfyjOcrfyOe;

    /**
     * 签收后非月结部分到达应收已核销金额   公布价运费
     */
    private BigDecimal ptpQfyjArbfyFrt;

    /**
     * 签收后非月结部分到达应收已核销金额   接货费
     */
    private BigDecimal ptpQfyjArbfyPup;

    /**
     * 签收后非月结部分到达应收已核销金额  送货费
     */
    private BigDecimal ptpQfyjArbfyDel;

    /**
     * 签收后非月结部分到达应收已核销金额   包装费
     */
    private BigDecimal ptpQfyjArbfyPkg;

    /**
     * 签收后非月结部分到达应收已核销金额  代收货款手续费
     */
    private BigDecimal ptpQfyjArbfyCod;

    /**
     * 签收后非月结部分到达应收已核销金额  保价费
     */
    private BigDecimal ptpQfyjArbfyDv;


    /**
     * 签收后非月结部分到达应收已核销金额   其它费用
     */
    private BigDecimal ptpQfyjArbfyOt;

    /**
     * 签收后非月结部分委托派费应收已核销金额   送货上楼费
     */
    private BigDecimal ptpQfyjWtpfyDf;

    /**
     * 签收后非月结部分委托派费应收已核销金额   送货进仓费
     */
    private BigDecimal ptpQfyjWtpfyDc;

    /**
     * 签收后非月结部分委托派费应收已核销金额    大件上楼费
     */
    private BigDecimal ptpQfyjWtpfyLuf;

    /**
     * 签收后非月结部分委托派费应收已核销金额    超远派送费
     */
    private BigDecimal ptpQfyjWtpfyUld;

    /**
     * 签收后非月结部分委托派费应收已核销金额   签收单返回
     */
    private BigDecimal ptpQfyjWtpfySr;

    /**
     * 签收时月结部分始发提成应收已核销金额  公布价运费
     */
    private BigDecimal ptpQyjSftcyPpf;

    /**
     * 签收时月结部分始发提成应收已核销金额  开单操作费
     */
    private BigDecimal ptpQyjSftcyBof;

    /**
     * 签收时月结部分始发提成应收已核销金额  包装费
     */
    private BigDecimal ptpQyjSftcyPc;

    /**
     * 签收时月结部分始发提成应收已核销金额  保价费
     */
    private BigDecimal ptpQyjSftcyTif;

    /**
     * 签收时月结部分始发提成应收已核销金额  代收货款手续费
     */
    private BigDecimal ptpQyjSftcyCf;

    /**
     * 签收时月结部分始发提成应收已核销金额  送货费（不含上楼）
     */
    private BigDecimal ptpQyjSftcyDf;

    /**
     * 签收时月结部分始发提成应收已核销金额  基础送货费
     */
    private BigDecimal ptpQyjSftcyBdc;

    /**
     * 签收时月结部分始发提成应收已核销金额  其他费用
     */
    private BigDecimal ptpQyjSftcyOe;

    /**
     * 签收时月结部分委托派费应收已核销金额  送货上楼费
     */
    private BigDecimal ptpQyjWtpfyDf;

    /**
     * 签收时月结部分委托派费应收已核销金额  送货进仓费
     */
    private BigDecimal ptpQyjWtpfyDc;

    /**
     * 签收时月结部分委托派费应收已核销金额  大件上楼费
     */
    private BigDecimal ptpQyjWtpfyLuf;

    /**
     * 签收时月结部分委托派费应收已核销金额  超远派送费
     */
    private BigDecimal ptpQyjWtpfyUld;

    /**
     * 签收时月结部分委托派费应收已核销金额  签收单返回
     */
    private BigDecimal ptpQyjWtpfySr;

    /**
     *  签收时月结部分始发提成应收未核销金额公布价运费
     */
    private BigDecimal ptpQyjSftcwPpf;

    /**
     * 签收时月结部分始发提成应收未核销金额开单操作费
     */
    private BigDecimal ptpQyjSftcwBof;

    /**
     * 签收时月结部分始发提成应收未核销金额包装费
     */
    private BigDecimal ptpQyjSftcwPc;

    /**
     * 签收时月结部分始发提成应收未核销金额保价费
     */
    private BigDecimal ptpQyjSftcwTif;

    /**
     *  签收时月结部分始发提成应收未核销金额代收货款手续费
     */
    private BigDecimal ptpQyjSftcwCf;

    /**
     * 签收时月结部分始发提成应收未核销金额送货费（不含上楼）
     */
    private BigDecimal ptpQyjSftcwDf;

    /**
     * 签收时月结部分始发提成应收未核销金额基础送货费
     */
    private BigDecimal ptpQyjSftcwBdc;

    /**
     * 签收时月结部分始发提成应收未核销金额其他费用
     */
    private BigDecimal ptpQyjSftcwOe;

    /**
     * 签收时月结部分委托派费应收未核销金额送货上楼费
     */
    private BigDecimal ptpQyjWtpfwDf;

    /**
     * 签收时月结部分委托派费应收未核销金额送货进仓费
     */
    private BigDecimal ptpQyjWtpfwDc;

    /**
     * 签收时月结部分委托派费应收未核销金额大件上楼费
     */
    private BigDecimal ptpQyjWtpfwLuf;

    /**
     * 签收时月结部分委托派费应收未核销金额超远派送费
     */
    private BigDecimal ptpQyjWtpfwUld;

    /**
     * 签收时月结部分委托派费应收未核销金额签收单返回
     */
    private BigDecimal ptpQyjWtpfwSr;

    /**
     * 反签收后非月结部分始发提成应收已核销金额公布价运费
     */
    private BigDecimal ptpFqfyjSftcyPpf;

    /**
     * 反签收后非月结部分始发提成应收已核销金额开单操作费
     */
    private BigDecimal ptpFqfyjSftcyBof;

    /**
     * 反签收后非月结部分始发提成应收已核销金额包装费
     */
    private BigDecimal ptpFqfyjSftcyPc;

    /**
     * 反签收后非月结部分始发提成应收已核销金额保价费
     */
    private BigDecimal ptpFqfyjSftcyTif;

    /**
     * 反签收后非月结部分始发提成应收已核销金额代收货款手续费
     */
    private BigDecimal ptpFqfyjSftcyCf;

    /**
     * 反签收后非月结部分始发提成应收已核销金额送货费（不含上楼）
     */
    private BigDecimal ptpFqfyjSftcyDf;

    /**
     * 反签收后非月结部分始发提成应收已核销金额基础送货费
     */
    private BigDecimal ptpFqfyjSftcyBdc;

    /**
     * 反签收后非月结部分始发提成应收已核销金额其他费用
     */
    private BigDecimal ptpFqfyjSftcyOe;

    /**
     * 反签收后非月结部分到达应收已核销金额 公布价运费
     */
    private BigDecimal ptpFqfyjDdysyFrt;

    /**
     * 反签收后非月结部分到达应收已核销金额 接货费
     */
    private BigDecimal ptpFqfyjDdysyPup;

    /**
     * 反签收后非月结部分到达应收已核销金额 送货费
     */
    private BigDecimal ptpFqfyjDdysyDel;

    /**
     * 反签收后非月结部分到达应收已核销金额 包装费
     */
    private BigDecimal ptpFqfyjDdysyPkg;

    /**
     * 反签收后非月结部分到达应收已核销金额代收货款手续费
     */
    private BigDecimal ptpFqfyjDdysyCod;

    /**
     * 反签收后非月结部分到达应收已核销金额 保价费
     */
    private BigDecimal ptpFqfyjDdysyDv;

    /**
     * 反签收后非月结部分到达应收已核销金额 其它费用
     */
    private BigDecimal ptpFqfyjDdysyOt;

    /**
     * 反签收后非月结部分委托派费应收已核销金额送货上楼费
     */
    private BigDecimal ptpFqfyjWtpfyDf;

    /**
     * 反签收后非月结部分委托派费应收已核销金额送货进仓费
     */
    private BigDecimal ptpFqfyjWtpfyDc;

    /**
     *  反签收后非月结部分委托派费应收已核销金额大件上楼费
     */
    private BigDecimal ptpFqfyjWtpfyLuf;

    /**
     *  反签收后非月结部分委托派费应收已核销金额超远派送费
     */
    private BigDecimal ptpFqfyjWtpfyUld;

    /**
     * 反签收后非月结部分委托派费应收已核销金额签收单返回
     */
    private BigDecimal ptpFqfyjWtpfySr;

    /**
     * 反签收时月结部分始发提成应收已核销金额公布价运费
     */
    private BigDecimal ptpFqyjSftcyPpf;

    /**
     * 反签收时月结部分始发提成应收已核销金额开单操作费
     */
    private BigDecimal ptpFqyjSftcyBof;

    /**
     * 反签收时月结部分始发提成应收已核销金额包装费
     */
    private BigDecimal ptpFqyjSftcyPc;

    /**
     * 反签收时月结部分始发提成应收已核销金额保价费
     */
    private BigDecimal ptpFqyjSftcyTif;

    /**
     * 反签收时月结部分始发提成应收已核销金额代收货款手续费
     */
    private BigDecimal ptpFqyjSftcyCf;

    /**
     * 反签收时月结部分始发提成应收已核销金额送货费（不含上楼）
     */
    private BigDecimal ptpFqyjSftcyDf;

    /**
     *  反签收时月结部分始发提成应收已核销金额基础送货费
     */
    private BigDecimal ptpFqyjSftcyBdc;

    /**
     * 反签收时月结部分始发提成应收已核销金额其他费用
     */
    private BigDecimal ptpFqyjSftcyOe;

    /**
     * 反签收时月结部分委托派费应收已核销金额送货上楼费
     */
    private BigDecimal ptpFqyjWtpfyDf;

    /**
     *  反签收时月结部分委托派费应收已核销金额送货进仓费
     */
    private BigDecimal ptpFqyjWtpfyDc;

    /**
     * 反签收时月结部分委托派费应收已核销金额大件上楼费
     */
    private BigDecimal ptpFqyjWtpfyLuf;

    /**
     * 反签收时月结部分委托派费应收已核销金额超远派送费
     */
    private BigDecimal ptpFqyjWtpfyUld;

    /**
     * 反签收时月结部分委托派费应收已核销金额签收单返回
     */
    private BigDecimal ptpFqyjWtpfySr;

    /**
     * 反签收时月结部分始发提成应收未核销金额公布价运费
     */
    private BigDecimal ptpFqyjSftcwPpf;

    /**
     *  反签收时月结部分始发提成应收未核销金额开单操作费
     */
    private BigDecimal ptpFqyjSftcwBof;

    /**
     * 反签收时月结部分始发提成应收未核销金额包装费
     */
    private BigDecimal ptpFqyjSftcwPc;

    /**
     * 反签收时月结部分始发提成应收未核销金额保价费
     */
    private BigDecimal ptpFqyjSftcwTif;

    /**
     * 反签收时月结部分始发提成应收未核销金额代收货款手续费
     */
    private BigDecimal ptpFqyjSftcwCf;

    /**
     *  反签收时月结部分始发提成应收未核销金额送货费（不含上楼）
     */
    private BigDecimal ptpFqyjSftcwDf;

    /**
     * 反签收时月结部分始发提成应收未核销金额基础送货费
     */
    private BigDecimal ptpFqyjSftcwBdc;

    /**
     * 反签收时月结部分始发提成应收未核销金额其他费用
     */
    private BigDecimal ptpFqyjSftcwOe;

    /**
     *  反签收时月结部分委托派费应收未核销金额送货上楼费
     */
    private BigDecimal ptpFqyjWtpfwDf;

    /**
     *  反签收时月结部分委托派费应收未核销金额送货进仓费
     */
    private BigDecimal ptpFqyjWtpfwDc;

    /**
     *  反签收时月结部分委托派费应收未核销金额大件上楼费
     */
    private BigDecimal ptpFqyjWtpfwLuf;

    /**
     * 反签收时月结部分委托派费应收未核销金额超远派送费
     */
    private BigDecimal ptpFqyjWtpfwUld;

    /**
     * 反签收时月结部分委托派费应收未核销金额签收单返回
     */
    private BigDecimal ptpFqyjWtpfwSr;
    
    
    /**
     * 还款空运到付运费未签收
     */
    private BigDecimal pscAaRepUpd;
    
    /**
     * 还款空运到付运费已签收
     */
    private BigDecimal pscAaRepPod;
    
    /**
     * 预收客户冲空运到付运费未签收
     */
    private BigDecimal pscAaDesUpd;
    
    /**
     * 预收客户冲空运到付运费已签收
     */
    private BigDecimal pscAaDesPod;
    
    /**
     * 应付到达代理/其他应付冲到付运费未签收
     */
    private BigDecimal pscPaOtUpd;
    
    /**
     * 应付到达代理/其他应付冲到付运费已签收
     */
    private BigDecimal pscPaOtPod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getVoucherBeginTime() {
        return voucherBeginTime;
    }

    public void setVoucherBeginTime(Date voucherBeginTime) {
        this.voucherBeginTime = voucherBeginTime;
    }

    public Date getVoucherEndTime() {
        return voucherEndTime;
    }

    public void setVoucherEndTime(Date voucherEndTime) {
        this.voucherEndTime = voucherEndTime;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getOrigOrgName() {
        return origOrgName;
    }

    public void setOrigOrgName(String origOrgName) {
        this.origOrgName = origOrgName == null ? null : origOrgName.trim();
    }

    public String getOrigOrgCode() {
        return origOrgCode;
    }

    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode == null ? null : origOrgCode.trim();
    }

    public String getDestOrgName() {
        return destOrgName;
    }

    public void setDestOrgName(String destOrgName) {
        this.destOrgName = destOrgName == null ? null : destOrgName.trim();
    }

    public String getDestOrgCode() {
        return destOrgCode;
    }

    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode == null ? null : destOrgCode.trim();
    }

    public String getGeneratingOrgName() {
        return generatingOrgName;
    }

    public void setGeneratingOrgName(String generatingOrgName) {
        this.generatingOrgName = generatingOrgName == null ? null : generatingOrgName.trim();
    }

    public String getGeneratingOrgCode() {
        return generatingOrgCode;
    }

    public void setGeneratingOrgCode(String generatingOrgCode) {
        this.generatingOrgCode = generatingOrgCode == null ? null : generatingOrgCode.trim();
    }

    public String getPayableOrgName() {
        return payableOrgName;
    }

    public void setPayableOrgName(String payableOrgName) {
        this.payableOrgName = payableOrgName == null ? null : payableOrgName.trim();
    }

    public String getPayableOrgCode() {
        return payableOrgCode;
    }

    public void setPayableOrgCode(String payableOrgCode) {
        this.payableOrgCode = payableOrgCode == null ? null : payableOrgCode.trim();
    }

    public String getReceivableOrgCode() {
        return receivableOrgCode;
    }

    public void setReceivableOrgCode(String receivableOrgCode) {
        this.receivableOrgCode = receivableOrgCode == null ? null : receivableOrgCode.trim();
    }

    public String getReceivableOrgName() {
        return receivableOrgName;
    }

    public void setReceivableOrgName(String receivableOrgName) {
        this.receivableOrgName = receivableOrgName == null ? null : receivableOrgName.trim();
    }

    public BigDecimal getPtpPdeApf() {
        return ptpPdeApf;
    }

    public void setPtpPdeApf(BigDecimal ptpPdeApf) {
        this.ptpPdeApf = ptpPdeApf;
    }

    public BigDecimal getPtpPdeAra() {
        return ptpPdeAra;
    }

    public void setPtpPdeAra(BigDecimal ptpPdeAra) {
        this.ptpPdeAra = ptpPdeAra;
    }

    public BigDecimal getPtpPdeApc() {
        return ptpPdeApc;
    }

    public void setPtpPdeApc(BigDecimal ptpPdeApc) {
        this.ptpPdeApc = ptpPdeApc;
    }

    public BigDecimal getPtpPdeCsrd() {
        return ptpPdeCsrd;
    }

    public void setPtpPdeCsrd(BigDecimal ptpPdeCsrd) {
        this.ptpPdeCsrd = ptpPdeCsrd;
    }

    public BigDecimal getPtpPdeCsrh() {
        return ptpPdeCsrh;
    }

    public void setPtpPdeCsrh(BigDecimal ptpPdeCsrh) {
        this.ptpPdeCsrh = ptpPdeCsrh;
    }

    public BigDecimal getPtpPdeRrpc() {
        return ptpPdeRrpc;
    }

    public void setPtpPdeRrpc(BigDecimal ptpPdeRrpc) {
        this.ptpPdeRrpc = ptpPdeRrpc;
    }

    public BigDecimal getPtpPdeFhbw() {
        return ptpPdeFhbw;
    }

    public void setPtpPdeFhbw(BigDecimal ptpPdeFhbw) {
        this.ptpPdeFhbw = ptpPdeFhbw;
    }

    public BigDecimal getPtpPdeBapa() {
        return ptpPdeBapa;
    }

    public void setPtpPdeBapa(BigDecimal ptpPdeBapa) {
        this.ptpPdeBapa = ptpPdeBapa;
    }

    public BigDecimal getPtpRtfRtp() {
        return ptpRtfRtp;
    }

    public void setPtpRtfRtp(BigDecimal ptpRtfRtp) {
        this.ptpRtfRtp = ptpRtfRtp;
    }

    public BigDecimal getPtpWfrRtr() {
        return ptpWfrRtr;
    }

    public void setPtpWfrRtr(BigDecimal ptpWfrRtr) {
        this.ptpWfrRtr = ptpWfrRtr;
    }

    public BigDecimal getPtpWfrRtpr() {
        return ptpWfrRtpr;
    }

    public void setPtpWfrRtpr(BigDecimal ptpWfrRtpr) {
        this.ptpWfrRtpr = ptpWfrRtpr;
    }

    public BigDecimal getPtpSctLcrc() {
        return ptpSctLcrc;
    }

    public void setPtpSctLcrc(BigDecimal ptpSctLcrc) {
        this.ptpSctLcrc = ptpSctLcrc;
    }

    public BigDecimal getPtpSctEac() {
        return ptpSctEac;
    }

    public void setPtpSctEac(BigDecimal ptpSctEac) {
        this.ptpSctEac = ptpSctEac;
    }

    public BigDecimal getPtpSctZld() {
        return ptpSctZld;
    }

    public void setPtpSctZld(BigDecimal ptpSctZld) {
        this.ptpSctZld = ptpSctZld;
    }

    public BigDecimal getPtpSctUeac() {
        return ptpSctUeac;
    }

    public void setPtpSctUeac(BigDecimal ptpSctUeac) {
        this.ptpSctUeac = ptpSctUeac;
    }

    public BigDecimal getPtpCphFpc() {
        return ptpCphFpc;
    }

    public void setPtpCphFpc(BigDecimal ptpCphFpc) {
        this.ptpCphFpc = ptpCphFpc;
    }

    public BigDecimal getPtpCphPrc() {
        return ptpCphPrc;
    }

    public void setPtpCphPrc(BigDecimal ptpCphPrc) {
        this.ptpCphPrc = ptpCphPrc;
    }

    public BigDecimal getPtpIprAcpa() {
        return ptpIprAcpa;
    }

    public void setPtpIprAcpa(BigDecimal ptpIprAcpa) {
        this.ptpIprAcpa = ptpIprAcpa;
    }

    public BigDecimal getPtpIprPafc() {
        return ptpIprPafc;
    }

    public void setPtpIprPafc(BigDecimal ptpIprPafc) {
        this.ptpIprPafc = ptpIprPafc;
    }

    public BigDecimal getPtpIprPfpa() {
        return ptpIprPfpa;
    }

    public void setPtpIprPfpa(BigDecimal ptpIprPfpa) {
        this.ptpIprPfpa = ptpIprPfpa;
    }

    public BigDecimal getPtpQfyjOcrfyPpf() {
        return ptpQfyjOcrfyPpf;
    }

    public void setPtpQfyjOcrfyPpf(BigDecimal ptpQfyjOcrfyPpf) {
        this.ptpQfyjOcrfyPpf = ptpQfyjOcrfyPpf;
    }

    public BigDecimal getPtpQfyjOcrfyBof() {
        return ptpQfyjOcrfyBof;
    }

    public void setPtpQfyjOcrfyBof(BigDecimal ptpQfyjOcrfyBof) {
        this.ptpQfyjOcrfyBof = ptpQfyjOcrfyBof;
    }

    public BigDecimal getPtpQfyjOcrfyPc() {
        return ptpQfyjOcrfyPc;
    }

    public void setPtpQfyjOcrfyPc(BigDecimal ptpQfyjOcrfyPc) {
        this.ptpQfyjOcrfyPc = ptpQfyjOcrfyPc;
    }

    public BigDecimal getPtpQfyjOcrfyTif() {
        return ptpQfyjOcrfyTif;
    }

    public void setPtpQfyjOcrfyTif(BigDecimal ptpQfyjOcrfyTif) {
        this.ptpQfyjOcrfyTif = ptpQfyjOcrfyTif;
    }

    public BigDecimal getPtpQfyjOcrfyCf() {
        return ptpQfyjOcrfyCf;
    }

    public void setPtpQfyjOcrfyCf(BigDecimal ptpQfyjOcrfyCf) {
        this.ptpQfyjOcrfyCf = ptpQfyjOcrfyCf;
    }

    public BigDecimal getPtpQfyjOcrfyDf() {
        return ptpQfyjOcrfyDf;
    }

    public void setPtpQfyjOcrfyDf(BigDecimal ptpQfyjOcrfyDf) {
        this.ptpQfyjOcrfyDf = ptpQfyjOcrfyDf;
    }

    public BigDecimal getPtpQfyjOcrfyBdc() {
        return ptpQfyjOcrfyBdc;
    }

    public void setPtpQfyjOcrfyBdc(BigDecimal ptpQfyjOcrfyBdc) {
        this.ptpQfyjOcrfyBdc = ptpQfyjOcrfyBdc;
    }

    public BigDecimal getPtpQfyjOcrfyOe() {
        return ptpQfyjOcrfyOe;
    }

    public void setPtpQfyjOcrfyOe(BigDecimal ptpQfyjOcrfyOe) {
        this.ptpQfyjOcrfyOe = ptpQfyjOcrfyOe;
    }

    public BigDecimal getPtpQfyjArbfyFrt() {
        return ptpQfyjArbfyFrt;
    }

    public void setPtpQfyjArbfyFrt(BigDecimal ptpQfyjArbfyFrt) {
        this.ptpQfyjArbfyFrt = ptpQfyjArbfyFrt;
    }

    public BigDecimal getPtpQfyjArbfyPup() {
        return ptpQfyjArbfyPup;
    }

    public void setPtpQfyjArbfyPup(BigDecimal ptpQfyjArbfyPup) {
        this.ptpQfyjArbfyPup = ptpQfyjArbfyPup;
    }

    public BigDecimal getPtpQfyjArbfyDel() {
        return ptpQfyjArbfyDel;
    }

    public void setPtpQfyjArbfyDel(BigDecimal ptpQfyjArbfyDel) {
        this.ptpQfyjArbfyDel = ptpQfyjArbfyDel;
    }

    public BigDecimal getPtpQfyjArbfyPkg() {
        return ptpQfyjArbfyPkg;
    }

    public void setPtpQfyjArbfyPkg(BigDecimal ptpQfyjArbfyPkg) {
        this.ptpQfyjArbfyPkg = ptpQfyjArbfyPkg;
    }

    public BigDecimal getPtpQfyjArbfyCod() {
        return ptpQfyjArbfyCod;
    }

    public void setPtpQfyjArbfyCod(BigDecimal ptpQfyjArbfyCod) {
        this.ptpQfyjArbfyCod = ptpQfyjArbfyCod;
    }

    public BigDecimal getPtpQfyjArbfyDv() {
        return ptpQfyjArbfyDv;
    }

    public void setPtpQfyjArbfyDv(BigDecimal ptpQfyjArbfyDv) {
        this.ptpQfyjArbfyDv = ptpQfyjArbfyDv;
    }

    public BigDecimal getPtpQfyjArbfyOt() {
        return ptpQfyjArbfyOt;
    }

    public void setPtpQfyjArbfyOt(BigDecimal ptpQfyjArbfyOt) {
        this.ptpQfyjArbfyOt = ptpQfyjArbfyOt;
    }

    public BigDecimal getPtpQfyjWtpfyDf() {
        return ptpQfyjWtpfyDf;
    }

    public void setPtpQfyjWtpfyDf(BigDecimal ptpQfyjWtpfyDf) {
        this.ptpQfyjWtpfyDf = ptpQfyjWtpfyDf;
    }

    public BigDecimal getPtpQfyjWtpfyDc() {
        return ptpQfyjWtpfyDc;
    }

    public void setPtpQfyjWtpfyDc(BigDecimal ptpQfyjWtpfyDc) {
        this.ptpQfyjWtpfyDc = ptpQfyjWtpfyDc;
    }

    public BigDecimal getPtpQfyjWtpfyLuf() {
        return ptpQfyjWtpfyLuf;
    }

    public void setPtpQfyjWtpfyLuf(BigDecimal ptpQfyjWtpfyLuf) {
        this.ptpQfyjWtpfyLuf = ptpQfyjWtpfyLuf;
    }

    public BigDecimal getPtpQfyjWtpfyUld() {
        return ptpQfyjWtpfyUld;
    }

    public void setPtpQfyjWtpfyUld(BigDecimal ptpQfyjWtpfyUld) {
        this.ptpQfyjWtpfyUld = ptpQfyjWtpfyUld;
    }

    public BigDecimal getPtpQfyjWtpfySr() {
        return ptpQfyjWtpfySr;
    }

    public void setPtpQfyjWtpfySr(BigDecimal ptpQfyjWtpfySr) {
        this.ptpQfyjWtpfySr = ptpQfyjWtpfySr;
    }

    public BigDecimal getPtpQyjSftcyPpf() {
        return ptpQyjSftcyPpf;
    }

    public void setPtpQyjSftcyPpf(BigDecimal ptpQyjSftcyPpf) {
        this.ptpQyjSftcyPpf = ptpQyjSftcyPpf;
    }

    public BigDecimal getPtpQyjSftcyBof() {
        return ptpQyjSftcyBof;
    }

    public void setPtpQyjSftcyBof(BigDecimal ptpQyjSftcyBof) {
        this.ptpQyjSftcyBof = ptpQyjSftcyBof;
    }

    public BigDecimal getPtpQyjSftcyPc() {
        return ptpQyjSftcyPc;
    }

    public void setPtpQyjSftcyPc(BigDecimal ptpQyjSftcyPc) {
        this.ptpQyjSftcyPc = ptpQyjSftcyPc;
    }

    public BigDecimal getPtpQyjSftcyTif() {
        return ptpQyjSftcyTif;
    }

    public void setPtpQyjSftcyTif(BigDecimal ptpQyjSftcyTif) {
        this.ptpQyjSftcyTif = ptpQyjSftcyTif;
    }

    public BigDecimal getPtpQyjSftcyCf() {
        return ptpQyjSftcyCf;
    }

    public void setPtpQyjSftcyCf(BigDecimal ptpQyjSftcyCf) {
        this.ptpQyjSftcyCf = ptpQyjSftcyCf;
    }

    public BigDecimal getPtpQyjSftcyDf() {
        return ptpQyjSftcyDf;
    }

    public void setPtpQyjSftcyDf(BigDecimal ptpQyjSftcyDf) {
        this.ptpQyjSftcyDf = ptpQyjSftcyDf;
    }

    public BigDecimal getPtpQyjSftcyBdc() {
        return ptpQyjSftcyBdc;
    }

    public void setPtpQyjSftcyBdc(BigDecimal ptpQyjSftcyBdc) {
        this.ptpQyjSftcyBdc = ptpQyjSftcyBdc;
    }

    public BigDecimal getPtpQyjSftcyOe() {
        return ptpQyjSftcyOe;
    }

    public void setPtpQyjSftcyOe(BigDecimal ptpQyjSftcyOe) {
        this.ptpQyjSftcyOe = ptpQyjSftcyOe;
    }

    public BigDecimal getPtpQyjWtpfyDf() {
        return ptpQyjWtpfyDf;
    }

    public void setPtpQyjWtpfyDf(BigDecimal ptpQyjWtpfyDf) {
        this.ptpQyjWtpfyDf = ptpQyjWtpfyDf;
    }

    public BigDecimal getPtpQyjWtpfyDc() {
        return ptpQyjWtpfyDc;
    }

    public void setPtpQyjWtpfyDc(BigDecimal ptpQyjWtpfyDc) {
        this.ptpQyjWtpfyDc = ptpQyjWtpfyDc;
    }

    public BigDecimal getPtpQyjWtpfyLuf() {
        return ptpQyjWtpfyLuf;
    }

    public void setPtpQyjWtpfyLuf(BigDecimal ptpQyjWtpfyLuf) {
        this.ptpQyjWtpfyLuf = ptpQyjWtpfyLuf;
    }

    public BigDecimal getPtpQyjWtpfyUld() {
        return ptpQyjWtpfyUld;
    }

    public void setPtpQyjWtpfyUld(BigDecimal ptpQyjWtpfyUld) {
        this.ptpQyjWtpfyUld = ptpQyjWtpfyUld;
    }

    public BigDecimal getPtpQyjWtpfySr() {
        return ptpQyjWtpfySr;
    }

    public void setPtpQyjWtpfySr(BigDecimal ptpQyjWtpfySr) {
        this.ptpQyjWtpfySr = ptpQyjWtpfySr;
    }

    public BigDecimal getPtpQyjSftcwPpf() {
        return ptpQyjSftcwPpf;
    }

    public void setPtpQyjSftcwPpf(BigDecimal ptpQyjSftcwPpf) {
        this.ptpQyjSftcwPpf = ptpQyjSftcwPpf;
    }

    public BigDecimal getPtpQyjSftcwBof() {
        return ptpQyjSftcwBof;
    }

    public void setPtpQyjSftcwBof(BigDecimal ptpQyjSftcwBof) {
        this.ptpQyjSftcwBof = ptpQyjSftcwBof;
    }

    public BigDecimal getPtpQyjSftcwPc() {
        return ptpQyjSftcwPc;
    }

    public void setPtpQyjSftcwPc(BigDecimal ptpQyjSftcwPc) {
        this.ptpQyjSftcwPc = ptpQyjSftcwPc;
    }

    public BigDecimal getPtpQyjSftcwTif() {
        return ptpQyjSftcwTif;
    }

    public void setPtpQyjSftcwTif(BigDecimal ptpQyjSftcwTif) {
        this.ptpQyjSftcwTif = ptpQyjSftcwTif;
    }

    public BigDecimal getPtpQyjSftcwCf() {
        return ptpQyjSftcwCf;
    }

    public void setPtpQyjSftcwCf(BigDecimal ptpQyjSftcwCf) {
        this.ptpQyjSftcwCf = ptpQyjSftcwCf;
    }

    public BigDecimal getPtpQyjSftcwDf() {
        return ptpQyjSftcwDf;
    }

    public void setPtpQyjSftcwDf(BigDecimal ptpQyjSftcwDf) {
        this.ptpQyjSftcwDf = ptpQyjSftcwDf;
    }

    public BigDecimal getPtpQyjSftcwBdc() {
        return ptpQyjSftcwBdc;
    }

    public void setPtpQyjSftcwBdc(BigDecimal ptpQyjSftcwBdc) {
        this.ptpQyjSftcwBdc = ptpQyjSftcwBdc;
    }

    public BigDecimal getPtpQyjSftcwOe() {
        return ptpQyjSftcwOe;
    }

    public void setPtpQyjSftcwOe(BigDecimal ptpQyjSftcwOe) {
        this.ptpQyjSftcwOe = ptpQyjSftcwOe;
    }

    public BigDecimal getPtpQyjWtpfwDf() {
        return ptpQyjWtpfwDf;
    }

    public void setPtpQyjWtpfwDf(BigDecimal ptpQyjWtpfwDf) {
        this.ptpQyjWtpfwDf = ptpQyjWtpfwDf;
    }

    public BigDecimal getPtpQyjWtpfwDc() {
        return ptpQyjWtpfwDc;
    }

    public void setPtpQyjWtpfwDc(BigDecimal ptpQyjWtpfwDc) {
        this.ptpQyjWtpfwDc = ptpQyjWtpfwDc;
    }

    public BigDecimal getPtpQyjWtpfwLuf() {
        return ptpQyjWtpfwLuf;
    }

    public void setPtpQyjWtpfwLuf(BigDecimal ptpQyjWtpfwLuf) {
        this.ptpQyjWtpfwLuf = ptpQyjWtpfwLuf;
    }

    public BigDecimal getPtpQyjWtpfwUld() {
        return ptpQyjWtpfwUld;
    }

    public void setPtpQyjWtpfwUld(BigDecimal ptpQyjWtpfwUld) {
        this.ptpQyjWtpfwUld = ptpQyjWtpfwUld;
    }

    public BigDecimal getPtpQyjWtpfwSr() {
        return ptpQyjWtpfwSr;
    }

    public void setPtpQyjWtpfwSr(BigDecimal ptpQyjWtpfwSr) {
        this.ptpQyjWtpfwSr = ptpQyjWtpfwSr;
    }

    public BigDecimal getPtpFqfyjSftcyPpf() {
        return ptpFqfyjSftcyPpf;
    }

    public void setPtpFqfyjSftcyPpf(BigDecimal ptpFqfyjSftcyPpf) {
        this.ptpFqfyjSftcyPpf = ptpFqfyjSftcyPpf;
    }

    public BigDecimal getPtpFqfyjSftcyBof() {
        return ptpFqfyjSftcyBof;
    }

    public void setPtpFqfyjSftcyBof(BigDecimal ptpFqfyjSftcyBof) {
        this.ptpFqfyjSftcyBof = ptpFqfyjSftcyBof;
    }

    public BigDecimal getPtpFqfyjSftcyPc() {
        return ptpFqfyjSftcyPc;
    }

    public void setPtpFqfyjSftcyPc(BigDecimal ptpFqfyjSftcyPc) {
        this.ptpFqfyjSftcyPc = ptpFqfyjSftcyPc;
    }

    public BigDecimal getPtpFqfyjSftcyTif() {
        return ptpFqfyjSftcyTif;
    }

    public void setPtpFqfyjSftcyTif(BigDecimal ptpFqfyjSftcyTif) {
        this.ptpFqfyjSftcyTif = ptpFqfyjSftcyTif;
    }

    public BigDecimal getPtpFqfyjSftcyCf() {
        return ptpFqfyjSftcyCf;
    }

    public void setPtpFqfyjSftcyCf(BigDecimal ptpFqfyjSftcyCf) {
        this.ptpFqfyjSftcyCf = ptpFqfyjSftcyCf;
    }

    public BigDecimal getPtpFqfyjSftcyDf() {
        return ptpFqfyjSftcyDf;
    }

    public void setPtpFqfyjSftcyDf(BigDecimal ptpFqfyjSftcyDf) {
        this.ptpFqfyjSftcyDf = ptpFqfyjSftcyDf;
    }

    public BigDecimal getPtpFqfyjSftcyBdc() {
        return ptpFqfyjSftcyBdc;
    }

    public void setPtpFqfyjSftcyBdc(BigDecimal ptpFqfyjSftcyBdc) {
        this.ptpFqfyjSftcyBdc = ptpFqfyjSftcyBdc;
    }

    public BigDecimal getPtpFqfyjSftcyOe() {
        return ptpFqfyjSftcyOe;
    }

    public void setPtpFqfyjSftcyOe(BigDecimal ptpFqfyjSftcyOe) {
        this.ptpFqfyjSftcyOe = ptpFqfyjSftcyOe;
    }

    public BigDecimal getPtpFqfyjDdysyFrt() {
        return ptpFqfyjDdysyFrt;
    }

    public void setPtpFqfyjDdysyFrt(BigDecimal ptpFqfyjDdysyFrt) {
        this.ptpFqfyjDdysyFrt = ptpFqfyjDdysyFrt;
    }

    public BigDecimal getPtpFqfyjDdysyPup() {
        return ptpFqfyjDdysyPup;
    }

    public void setPtpFqfyjDdysyPup(BigDecimal ptpFqfyjDdysyPup) {
        this.ptpFqfyjDdysyPup = ptpFqfyjDdysyPup;
    }

    public BigDecimal getPtpFqfyjDdysyDel() {
        return ptpFqfyjDdysyDel;
    }

    public void setPtpFqfyjDdysyDel(BigDecimal ptpFqfyjDdysyDel) {
        this.ptpFqfyjDdysyDel = ptpFqfyjDdysyDel;
    }

    public BigDecimal getPtpFqfyjDdysyPkg() {
        return ptpFqfyjDdysyPkg;
    }

    public void setPtpFqfyjDdysyPkg(BigDecimal ptpFqfyjDdysyPkg) {
        this.ptpFqfyjDdysyPkg = ptpFqfyjDdysyPkg;
    }

    public BigDecimal getPtpFqfyjDdysyCod() {
        return ptpFqfyjDdysyCod;
    }

    public void setPtpFqfyjDdysyCod(BigDecimal ptpFqfyjDdysyCod) {
        this.ptpFqfyjDdysyCod = ptpFqfyjDdysyCod;
    }

    public BigDecimal getPtpFqfyjDdysyDv() {
        return ptpFqfyjDdysyDv;
    }

    public void setPtpFqfyjDdysyDv(BigDecimal ptpFqfyjDdysyDv) {
        this.ptpFqfyjDdysyDv = ptpFqfyjDdysyDv;
    }

    public BigDecimal getPtpFqfyjDdysyOt() {
        return ptpFqfyjDdysyOt;
    }

    public void setPtpFqfyjDdysyOt(BigDecimal ptpFqfyjDdysyOt) {
        this.ptpFqfyjDdysyOt = ptpFqfyjDdysyOt;
    }

    public BigDecimal getPtpFqfyjWtpfyDf() {
        return ptpFqfyjWtpfyDf;
    }

    public void setPtpFqfyjWtpfyDf(BigDecimal ptpFqfyjWtpfyDf) {
        this.ptpFqfyjWtpfyDf = ptpFqfyjWtpfyDf;
    }

    public BigDecimal getPtpFqfyjWtpfyDc() {
        return ptpFqfyjWtpfyDc;
    }

    public void setPtpFqfyjWtpfyDc(BigDecimal ptpFqfyjWtpfyDc) {
        this.ptpFqfyjWtpfyDc = ptpFqfyjWtpfyDc;
    }

    public BigDecimal getPtpFqfyjWtpfyLuf() {
        return ptpFqfyjWtpfyLuf;
    }

    public void setPtpFqfyjWtpfyLuf(BigDecimal ptpFqfyjWtpfyLuf) {
        this.ptpFqfyjWtpfyLuf = ptpFqfyjWtpfyLuf;
    }

    public BigDecimal getPtpFqfyjWtpfyUld() {
        return ptpFqfyjWtpfyUld;
    }

    public void setPtpFqfyjWtpfyUld(BigDecimal ptpFqfyjWtpfyUld) {
        this.ptpFqfyjWtpfyUld = ptpFqfyjWtpfyUld;
    }

    public BigDecimal getPtpFqfyjWtpfySr() {
        return ptpFqfyjWtpfySr;
    }

    public void setPtpFqfyjWtpfySr(BigDecimal ptpFqfyjWtpfySr) {
        this.ptpFqfyjWtpfySr = ptpFqfyjWtpfySr;
    }

    public BigDecimal getPtpFqyjSftcyPpf() {
        return ptpFqyjSftcyPpf;
    }

    public void setPtpFqyjSftcyPpf(BigDecimal ptpFqyjSftcyPpf) {
        this.ptpFqyjSftcyPpf = ptpFqyjSftcyPpf;
    }

    public BigDecimal getPtpFqyjSftcyBof() {
        return ptpFqyjSftcyBof;
    }

    public void setPtpFqyjSftcyBof(BigDecimal ptpFqyjSftcyBof) {
        this.ptpFqyjSftcyBof = ptpFqyjSftcyBof;
    }

    public BigDecimal getPtpFqyjSftcyPc() {
        return ptpFqyjSftcyPc;
    }

    public void setPtpFqyjSftcyPc(BigDecimal ptpFqyjSftcyPc) {
        this.ptpFqyjSftcyPc = ptpFqyjSftcyPc;
    }

    public BigDecimal getPtpFqyjSftcyTif() {
        return ptpFqyjSftcyTif;
    }

    public void setPtpFqyjSftcyTif(BigDecimal ptpFqyjSftcyTif) {
        this.ptpFqyjSftcyTif = ptpFqyjSftcyTif;
    }

    public BigDecimal getPtpFqyjSftcyCf() {
        return ptpFqyjSftcyCf;
    }

    public void setPtpFqyjSftcyCf(BigDecimal ptpFqyjSftcyCf) {
        this.ptpFqyjSftcyCf = ptpFqyjSftcyCf;
    }

    public BigDecimal getPtpFqyjSftcyDf() {
        return ptpFqyjSftcyDf;
    }

    public void setPtpFqyjSftcyDf(BigDecimal ptpFqyjSftcyDf) {
        this.ptpFqyjSftcyDf = ptpFqyjSftcyDf;
    }

    public BigDecimal getPtpFqyjSftcyBdc() {
        return ptpFqyjSftcyBdc;
    }

    public void setPtpFqyjSftcyBdc(BigDecimal ptpFqyjSftcyBdc) {
        this.ptpFqyjSftcyBdc = ptpFqyjSftcyBdc;
    }

    public BigDecimal getPtpFqyjSftcyOe() {
        return ptpFqyjSftcyOe;
    }

    public void setPtpFqyjSftcyOe(BigDecimal ptpFqyjSftcyOe) {
        this.ptpFqyjSftcyOe = ptpFqyjSftcyOe;
    }

    public BigDecimal getPtpFqyjWtpfyDf() {
        return ptpFqyjWtpfyDf;
    }

    public void setPtpFqyjWtpfyDf(BigDecimal ptpFqyjWtpfyDf) {
        this.ptpFqyjWtpfyDf = ptpFqyjWtpfyDf;
    }

    public BigDecimal getPtpFqyjWtpfyDc() {
        return ptpFqyjWtpfyDc;
    }

    public void setPtpFqyjWtpfyDc(BigDecimal ptpFqyjWtpfyDc) {
        this.ptpFqyjWtpfyDc = ptpFqyjWtpfyDc;
    }

    public BigDecimal getPtpFqyjWtpfyLuf() {
        return ptpFqyjWtpfyLuf;
    }

    public void setPtpFqyjWtpfyLuf(BigDecimal ptpFqyjWtpfyLuf) {
        this.ptpFqyjWtpfyLuf = ptpFqyjWtpfyLuf;
    }

    public BigDecimal getPtpFqyjWtpfyUld() {
        return ptpFqyjWtpfyUld;
    }

    public void setPtpFqyjWtpfyUld(BigDecimal ptpFqyjWtpfyUld) {
        this.ptpFqyjWtpfyUld = ptpFqyjWtpfyUld;
    }

    public BigDecimal getPtpFqyjWtpfySr() {
        return ptpFqyjWtpfySr;
    }

    public void setPtpFqyjWtpfySr(BigDecimal ptpFqyjWtpfySr) {
        this.ptpFqyjWtpfySr = ptpFqyjWtpfySr;
    }

    public BigDecimal getPtpFqyjSftcwPpf() {
        return ptpFqyjSftcwPpf;
    }

    public void setPtpFqyjSftcwPpf(BigDecimal ptpFqyjSftcwPpf) {
        this.ptpFqyjSftcwPpf = ptpFqyjSftcwPpf;
    }

    public BigDecimal getPtpFqyjSftcwBof() {
        return ptpFqyjSftcwBof;
    }

    public void setPtpFqyjSftcwBof(BigDecimal ptpFqyjSftcwBof) {
        this.ptpFqyjSftcwBof = ptpFqyjSftcwBof;
    }

    public BigDecimal getPtpFqyjSftcwPc() {
        return ptpFqyjSftcwPc;
    }

    public void setPtpFqyjSftcwPc(BigDecimal ptpFqyjSftcwPc) {
        this.ptpFqyjSftcwPc = ptpFqyjSftcwPc;
    }

    public BigDecimal getPtpFqyjSftcwTif() {
        return ptpFqyjSftcwTif;
    }

    public void setPtpFqyjSftcwTif(BigDecimal ptpFqyjSftcwTif) {
        this.ptpFqyjSftcwTif = ptpFqyjSftcwTif;
    }

    public BigDecimal getPtpFqyjSftcwCf() {
        return ptpFqyjSftcwCf;
    }

    public void setPtpFqyjSftcwCf(BigDecimal ptpFqyjSftcwCf) {
        this.ptpFqyjSftcwCf = ptpFqyjSftcwCf;
    }

    public BigDecimal getPtpFqyjSftcwDf() {
        return ptpFqyjSftcwDf;
    }

    public void setPtpFqyjSftcwDf(BigDecimal ptpFqyjSftcwDf) {
        this.ptpFqyjSftcwDf = ptpFqyjSftcwDf;
    }

    public BigDecimal getPtpFqyjSftcwBdc() {
        return ptpFqyjSftcwBdc;
    }

    public void setPtpFqyjSftcwBdc(BigDecimal ptpFqyjSftcwBdc) {
        this.ptpFqyjSftcwBdc = ptpFqyjSftcwBdc;
    }

    public BigDecimal getPtpFqyjSftcwOe() {
        return ptpFqyjSftcwOe;
    }

    public void setPtpFqyjSftcwOe(BigDecimal ptpFqyjSftcwOe) {
        this.ptpFqyjSftcwOe = ptpFqyjSftcwOe;
    }

    public BigDecimal getPtpFqyjWtpfwDf() {
        return ptpFqyjWtpfwDf;
    }

    public void setPtpFqyjWtpfwDf(BigDecimal ptpFqyjWtpfwDf) {
        this.ptpFqyjWtpfwDf = ptpFqyjWtpfwDf;
    }

    public BigDecimal getPtpFqyjWtpfwDc() {
        return ptpFqyjWtpfwDc;
    }

    public void setPtpFqyjWtpfwDc(BigDecimal ptpFqyjWtpfwDc) {
        this.ptpFqyjWtpfwDc = ptpFqyjWtpfwDc;
    }

    public BigDecimal getPtpFqyjWtpfwLuf() {
        return ptpFqyjWtpfwLuf;
    }

    public void setPtpFqyjWtpfwLuf(BigDecimal ptpFqyjWtpfwLuf) {
        this.ptpFqyjWtpfwLuf = ptpFqyjWtpfwLuf;
    }

    public BigDecimal getPtpFqyjWtpfwUld() {
        return ptpFqyjWtpfwUld;
    }

    public void setPtpFqyjWtpfwUld(BigDecimal ptpFqyjWtpfwUld) {
        this.ptpFqyjWtpfwUld = ptpFqyjWtpfwUld;
    }

    public BigDecimal getPtpFqyjWtpfwSr() {
        return ptpFqyjWtpfwSr;
    }

    public void setPtpFqyjWtpfwSr(BigDecimal ptpFqyjWtpfwSr) {
        this.ptpFqyjWtpfwSr = ptpFqyjWtpfwSr;
    }

	/**
	 * @return the expressOrigOrgCode
	 */
	public String getExpressOrigOrgCode() {
		return expressOrigOrgCode;
	}

	/**
	 * @param expressOrigOrgCode the expressOrigOrgCode to set
	 */
	public void setExpressOrigOrgCode(String expressOrigOrgCode) {
		this.expressOrigOrgCode = expressOrigOrgCode;
	}

	/**
	 * @return the expressOrigOrgName
	 */
	public String getExpressOrigOrgName() {
		return expressOrigOrgName;
	}

	/**
	 * @param expressOrigOrgName the expressOrigOrgName to set
	 */
	public void setExpressOrigOrgName(String expressOrigOrgName) {
		this.expressOrigOrgName = expressOrigOrgName;
	}

	/**
	 * @return the expressDestOrgCode
	 */
	public String getExpressDestOrgCode() {
		return expressDestOrgCode;
	}

	/**
	 * @param expressDestOrgCode the expressDestOrgCode to set
	 */
	public void setExpressDestOrgCode(String expressDestOrgCode) {
		this.expressDestOrgCode = expressDestOrgCode;
	}

	/**
	 * @return the expressDestOrgName
	 */
	public String getExpressDestOrgName() {
		return expressDestOrgName;
	}

	/**
	 * @param expressDestOrgName the expressDestOrgName to set
	 */
	public void setExpressDestOrgName(String expressDestOrgName) {
		this.expressDestOrgName = expressDestOrgName;
	}
	
	/**
	 * @return the expressCustomerOrgCode
	 */
	public String getExpressCustomerOrgCode() {
		return expressCustomerOrgCode;
	}
	
	/**
	 * @param expressCustomerOrgCode the expressCustomerOrgCode to set
	 */
	public void setExpressCustomerOrgCode(String expressCustomerOrgCode) {
		this.expressCustomerOrgCode = expressCustomerOrgCode;
	}
	
	/**
	 * @return the expressCustomerOrgName
	 */
	public String getExpressCustomerOrgName() {
		return expressCustomerOrgName;
	}
	
	/**
	 * @param expressCustomerOrgName the expressCustomerOrgName to set
	 */
	public void setExpressCustomerOrgName(String expressCustomerOrgName) {
		this.expressCustomerOrgName = expressCustomerOrgName;
	}
	
	/**
	 * @return the ptpPdeFcus
	 */
	public BigDecimal getPtpPdeFcus() {
		return ptpPdeFcus;
	}

	/**
	 * @param ptpPdeFcus the ptpPdeFcus to set
	 */
	public void setPtpPdeFcus(BigDecimal ptpPdeFcus) {
		this.ptpPdeFcus = ptpPdeFcus;
	}

	/**
	 * @return the pscTtReph
	 */
	public BigDecimal getPscTtReph() {
		return pscTtReph;
	}

	/**
	 * @param pscTtReph the pscTtReph to set
	 */
	public void setPscTtReph(BigDecimal pscTtReph) {
		this.pscTtReph = pscTtReph;
	}

	/**
	 * @return the pscAaRepUpd
	 */
	public BigDecimal getPscAaRepUpd() {
		return pscAaRepUpd;
	}

	/**
	 * @param pscAaRepUpd the pscAaRepUpd to set
	 */
	public void setPscAaRepUpd(BigDecimal pscAaRepUpd) {
		this.pscAaRepUpd = pscAaRepUpd;
	}

	/**
	 * @return the pscAaRepPod
	 */
	public BigDecimal getPscAaRepPod() {
		return pscAaRepPod;
	}

	/**
	 * @param pscAaRepPod the pscAaRepPod to set
	 */
	public void setPscAaRepPod(BigDecimal pscAaRepPod) {
		this.pscAaRepPod = pscAaRepPod;
	}

	/**
	 * @return the pscAaDesUpd
	 */
	public BigDecimal getPscAaDesUpd() {
		return pscAaDesUpd;
	}

	/**
	 * @param pscAaDesUpd the pscAaDesUpd to set
	 */
	public void setPscAaDesUpd(BigDecimal pscAaDesUpd) {
		this.pscAaDesUpd = pscAaDesUpd;
	}

	/**
	 * @return the pscAaDesPod
	 */
	public BigDecimal getPscAaDesPod() {
		return pscAaDesPod;
	}

	/**
	 * @param pscAaDesPod the pscAaDesPod to set
	 */
	public void setPscAaDesPod(BigDecimal pscAaDesPod) {
		this.pscAaDesPod = pscAaDesPod;
	}

	/**
	 * @return the pscPaOtUpd
	 */
	public BigDecimal getPscPaOtUpd() {
		return pscPaOtUpd;
	}

	/**
	 * @param pscPaOtUpd the pscPaOtUpd to set
	 */
	public void setPscPaOtUpd(BigDecimal pscPaOtUpd) {
		this.pscPaOtUpd = pscPaOtUpd;
	}

	/**
	 * @return the pscPaOtPod
	 */
	public BigDecimal getPscPaOtPod() {
		return pscPaOtPod;
	}

	/**
	 * @param pscPaOtPod the pscPaOtPod to set
	 */
	public void setPscPaOtPod(BigDecimal pscPaOtPod) {
		this.pscPaOtPod = pscPaOtPod;
	}

	
    
}