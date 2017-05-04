package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 始发应收月报统计信息Dto
 * 
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfosoEntityTotalDto implements Serializable {

	/**
	 * 始发应收月报统计信息Dto序列号
	 */
	private static final long serialVersionUID = 8262633505754583079L;

	/**
	 * 始发应收统计总条数
	 */
	private Long mvrNrfosoEntityTotalRows;
	
	/**
	 * 到达部门名称
	 */
    private String destOrgName;
	
    /**
     *01理赔冲收入
     */
    private BigDecimal claimOrigoIncome;

    /**
     *01理赔入成本
     */
    private BigDecimal claimOrigoCost;

    /**
     *01理赔冲01始发应收已签收
     */
    private BigDecimal claimOrigoWoOrigRcvoPod;

    /**
     *01理赔冲02始发应收已签收
     */
    private BigDecimal claimOrigoWoOrigRcvtPod;

    /**
     *02理赔冲01始发应收已签收
     */
    private BigDecimal claimOrigtOrigRcvoPod;

    /**
     *01理赔冲01始发应收未签收
     */
    private BigDecimal claimOrigoOrigRcvoNpod;

    /**
     *01理赔冲02始发应收未签收
     */
    private BigDecimal claimOrigoWoOrigRcvtNpod;

    /**
     *02理赔冲01始发应收未签收
     */
    private BigDecimal claimOrigtWoOrigRcvoNpod;

    /**
     *01理赔付款申请
     */
    private BigDecimal claimOrigoPayApply;

    /**
     *01理赔冲收入
     */
    private BigDecimal claimDestoIncome;

    /**
     *01理赔冲到01达应收已签收
     */
    private BigDecimal claimDestoWoDestRcvoPod;

    /**
     *02理赔冲01到达应收已签收
     */
    private BigDecimal claimDesttWoDestRcvoPod;

    /**
     *01理赔冲01到达应收未签收
     */
    private BigDecimal claimDestoWoDestRcvoNpod;

    /**
     *02理赔冲01到达应收未签收
     */
    private BigDecimal claimDesttWoDestRcvoNpod;

    /**
     *01应收始发运费已签收
     */
    private BigDecimal exOrigRcvoPod;

    /**
     *01应收到付运费已签收
     */
    private BigDecimal exDestRcvoPod;

    /**
     *坏账冲01应收始发运费已签收
     */
    private BigDecimal bdWoOrigRcvoPod;

    /**
     *坏账冲01应收到付运费已签收
     */
    private BigDecimal bdWoDestRcvoPod;

    /**
     *01小票现金主营业务收入
     */
    private BigDecimal orChPbio;

    /**
     *01小票银行主营业务收入
     */
    private BigDecimal orCdPbio;

    /**
     *01小票应收主营业务收入
     */
    private BigDecimal orRcvoPbi;

    /**
     *还款现金冲01小票应收
     */
    private BigDecimal orChUrRcvo;

    /**
     *还款银行冲01小票应收
     */
    private BigDecimal orCdUrRcvo;

    /**
     *应付代收货款冲01小票应收
     */
    private BigDecimal orCodPayWoRcvo;

    /**
     *01应付理赔冲01小票应收
     */
    private BigDecimal orClaimPayoWoRcvo;

    /**
     *01应付理赔冲02小票应收
     */
    private BigDecimal orClaimPayoWoRcvt;

    /**
     *02应付理赔冲01小票应收
     */
    private BigDecimal orClaimPaytWoRcvo;

    /**
     *01预收客户冲01小票应收
     */
    private BigDecimal orCustDroWoRcvo;

    /**
     *01预收客户冲02小票应收
     */
    private BigDecimal orCustDroWoRcvt;

    /**
     *02预收客户冲01小票应收
     */
    private BigDecimal orCustDrtWoRcvo;

    /**
     *异常冲收入冲01小票应收
     */
    private BigDecimal orExWoRcvo;

    /**
     *坏账损失冲01小票应收
     */
    private BigDecimal orBadWoRcvo;

    /**
     *开单且为月结临时欠款网上支付未核销
     */
    private BigDecimal acOrigRcvNwo;

    /**
     *开单且为月结临时欠款网上支付已核销
     */
    private BigDecimal acOrigRcvWo;

    /**
     *开单且为现金银行卡
     */
    private BigDecimal acCash;

    /**
     *01退运费冲收入
     */
    private BigDecimal rdOrigoIncome;

    /**
     *01退运费入成本
     */
    private BigDecimal rdOrigoCost;

    /**
     *01退运费付款申请
     */
    private BigDecimal rdOrigoPayApply;

    /**
     *01退运费冲01始发应收已签收
     */
    private BigDecimal rdOrigoWoOrigRcvoPod;

    /**
     *01退运费冲02始发应收已签收
     */
    private BigDecimal rdOrigoWoOrigRcvtPod;

    /**
     *02退运费冲01始发应收已签收
     */
    private BigDecimal rdOrigtWoOrigRcvoPod;

    /**
     *01退运费冲01始发应收未签收
     */
    private BigDecimal rdOrigoWoOrigRcvoNpod;

    /**
     *01退运费冲02始发应收未签收
     */
    private BigDecimal rdOrigoWoOrigRcvtNpod;

    /**
     *02退运费冲01始发应收未签收
     */
    private BigDecimal rdOrigtWoOrigRcvoNpod;

    /**
     *01退运费冲收入
     */
    private BigDecimal rdDestoIncome;

    /**
     *01退运费冲01到达应收已签收
     */
    private BigDecimal rdDestoDestRcvoPod;

    /**
     *02退运费冲01到达应收已签收
     */
    private BigDecimal rdDesttWoDestRcvoPod;

    /**
     *01退运费冲01到达应收未签收
     */
    private BigDecimal rdDestoWoDestRcvoNpod;

    /**
     *02退运费冲01到达应收未签收
     */
    private BigDecimal rdDesttWoDestRcvoNpod;

    /**
     *01装卸费付款申请
     */
    private BigDecimal sfoPayApply;

    /**
     *01始发服务补救付款申请
     */
    private BigDecimal cpoOrigPayApply;
    
    /**
     *预收客户现金
     */
    private BigDecimal custDrOch;
    
    /**
     *预收客户银行
     */
    private BigDecimal custDrOcd;
    
    /**
     *01预收客户冲01应收到付运费未签收
     */
    private BigDecimal custDroWoDestRcvoNpod;
    
    /**
     *02预收客户冲01应收到付运费未签收
     */
    private BigDecimal custDrtWoDestRcvoNpod;
    
    /**
     *01预收客户冲01应收到付运费已签收
     */
    private BigDecimal custDroWoDestRcvoPod;
    
    /**
     *02预收客户冲01应收到付运费已签收
     */
    private BigDecimal custDrtWoDestRcvoPod;
    
    /**
     *01预收客户冲01应收始发运费未签收
     */
    private BigDecimal custDroWoOrigRcvoNpod;
    
    /**
     *01预收客户冲02应收始发运费未签收
     */
    private BigDecimal custDroWoOrigRcvtNpod;
    
    /**
     *02预收客户冲01应收始发运费未签收
     */
    private BigDecimal custDrtWoOrigRcvoNpod;
    
    /**
     *01预收客户冲01应收始发运费已签收
     */
    private BigDecimal custDroWoOrigRcvoPod;
    
    /**
     *01预收客户冲02应收始发运费已签收
     */
    private BigDecimal custDroWoOrigRcvtPod;
    
    /**
     *02预收客户冲01应收始发运费已签收
     */
    private BigDecimal custDrtWoOrigRcvoPod;
    
    /**
     *01始发退预收付款申请
     */
    private BigDecimal custDroPayApply;
    
    /**
     *应付偏线代理成本冲01应收到付运费已签收
     */
    private BigDecimal plCostWoDestRcvoPod;
    
    /**
     *应付偏线代理成本冲01应收到付运费未签收
     */
    private BigDecimal plCostWoDestRcvoNpod;
    
    /**
     *预收偏线代理冲01应收到付运费已签收
     */
    private BigDecimal plDrWoDestRcvoPod;
    
    /**
     *预收偏线代理冲01应收到付运费未签收
     */
    private BigDecimal plDrWoDestRcvoNpod;
    
    /**
     *预收空运/快递代理冲01应收到付运费已签收
     */
    private BigDecimal alDrWoDestRcvoPod;
    
    /**
     *预收空运/快递代理冲01应收到付运费未签收
     */
    private BigDecimal alDrWoDestRcvoNpod;
    
    /**
     *应付到达代理/快递代理成本冲01应收到付运费已签收
     */
    private BigDecimal alpwrWoDestRcvoPod;
    
    /**
     *应付到达代理/快递代理成本冲01应收到付运费未签收
     */
    private BigDecimal alpwrWoDestRcvoNpod;
    
    /**
     *其他应付冲01应收到付运费已签收
     */
    private BigDecimal othPayWoDestRcvoPod;
    
    /**
     *其他应付冲01应收到付运费未签收
     */
    private BigDecimal othPayWoDestRcvoNpod;
    
    /**
     *其他应付冲01应收到付运费已签收
     */
    private BigDecimal popWoDroPod;
    
    /**
     *其他应付冲01应收到付运费未签收
     */
    private BigDecimal popWoDroNpod;

    public Long getMvrNrfosoEntityTotalRows() {
		return mvrNrfosoEntityTotalRows;
	}

	public void setMvrNrfosoEntityTotalRows(Long mvrNrfosoEntityTotalRows) {
		this.mvrNrfosoEntityTotalRows = mvrNrfosoEntityTotalRows;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public BigDecimal getClaimOrigoIncome() {
        return claimOrigoIncome;
    }

    public void setClaimOrigoIncome(BigDecimal claimOrigoIncome) {
        this.claimOrigoIncome = claimOrigoIncome;
    }

    public BigDecimal getClaimOrigoCost() {
        return claimOrigoCost;
    }

    public void setClaimOrigoCost(BigDecimal claimOrigoCost) {
        this.claimOrigoCost = claimOrigoCost;
    }

    public BigDecimal getClaimOrigoWoOrigRcvoPod() {
        return claimOrigoWoOrigRcvoPod;
    }

    public void setClaimOrigoWoOrigRcvoPod(BigDecimal claimOrigoWoOrigRcvoPod) {
        this.claimOrigoWoOrigRcvoPod = claimOrigoWoOrigRcvoPod;
    }

    public BigDecimal getClaimOrigoWoOrigRcvtPod() {
        return claimOrigoWoOrigRcvtPod;
    }

    public void setClaimOrigoWoOrigRcvtPod(BigDecimal claimOrigoWoOrigRcvtPod) {
        this.claimOrigoWoOrigRcvtPod = claimOrigoWoOrigRcvtPod;
    }

    public BigDecimal getClaimOrigtOrigRcvoPod() {
        return claimOrigtOrigRcvoPod;
    }

    public void setClaimOrigtOrigRcvoPod(BigDecimal claimOrigtOrigRcvoPod) {
        this.claimOrigtOrigRcvoPod = claimOrigtOrigRcvoPod;
    }

    public BigDecimal getClaimOrigoOrigRcvoNpod() {
        return claimOrigoOrigRcvoNpod;
    }

    public void setClaimOrigoOrigRcvoNpod(BigDecimal claimOrigoOrigRcvoNpod) {
        this.claimOrigoOrigRcvoNpod = claimOrigoOrigRcvoNpod;
    }

    public BigDecimal getClaimOrigoWoOrigRcvtNpod() {
        return claimOrigoWoOrigRcvtNpod;
    }

    public void setClaimOrigoWoOrigRcvtNpod(BigDecimal claimOrigoWoOrigRcvtNpod) {
        this.claimOrigoWoOrigRcvtNpod = claimOrigoWoOrigRcvtNpod;
    }

    public BigDecimal getClaimOrigtWoOrigRcvoNpod() {
        return claimOrigtWoOrigRcvoNpod;
    }

    public void setClaimOrigtWoOrigRcvoNpod(BigDecimal claimOrigtWoOrigRcvoNpod) {
        this.claimOrigtWoOrigRcvoNpod = claimOrigtWoOrigRcvoNpod;
    }

    public BigDecimal getClaimOrigoPayApply() {
        return claimOrigoPayApply;
    }

    public void setClaimOrigoPayApply(BigDecimal claimOrigoPayApply) {
        this.claimOrigoPayApply = claimOrigoPayApply;
    }

    public BigDecimal getClaimDestoIncome() {
        return claimDestoIncome;
    }

    public void setClaimDestoIncome(BigDecimal claimDestoIncome) {
        this.claimDestoIncome = claimDestoIncome;
    }

    public BigDecimal getClaimDestoWoDestRcvoPod() {
        return claimDestoWoDestRcvoPod;
    }

    public void setClaimDestoWoDestRcvoPod(BigDecimal claimDestoWoDestRcvoPod) {
        this.claimDestoWoDestRcvoPod = claimDestoWoDestRcvoPod;
    }

    public BigDecimal getClaimDesttWoDestRcvoPod() {
        return claimDesttWoDestRcvoPod;
    }

    public void setClaimDesttWoDestRcvoPod(BigDecimal claimDesttWoDestRcvoPod) {
        this.claimDesttWoDestRcvoPod = claimDesttWoDestRcvoPod;
    }

    public BigDecimal getClaimDestoWoDestRcvoNpod() {
        return claimDestoWoDestRcvoNpod;
    }

    public void setClaimDestoWoDestRcvoNpod(BigDecimal claimDestoWoDestRcvoNpod) {
        this.claimDestoWoDestRcvoNpod = claimDestoWoDestRcvoNpod;
    }

    public BigDecimal getClaimDesttWoDestRcvoNpod() {
        return claimDesttWoDestRcvoNpod;
    }

    public void setClaimDesttWoDestRcvoNpod(BigDecimal claimDesttWoDestRcvoNpod) {
        this.claimDesttWoDestRcvoNpod = claimDesttWoDestRcvoNpod;
    }

    public BigDecimal getExOrigRcvoPod() {
        return exOrigRcvoPod;
    }

    public void setExOrigRcvoPod(BigDecimal exOrigRcvoPod) {
        this.exOrigRcvoPod = exOrigRcvoPod;
    }

    public BigDecimal getExDestRcvoPod() {
        return exDestRcvoPod;
    }

    public void setExDestRcvoPod(BigDecimal exDestRcvoPod) {
        this.exDestRcvoPod = exDestRcvoPod;
    }

    public BigDecimal getBdWoOrigRcvoPod() {
        return bdWoOrigRcvoPod;
    }

    public void setBdWoOrigRcvoPod(BigDecimal bdWoOrigRcvoPod) {
        this.bdWoOrigRcvoPod = bdWoOrigRcvoPod;
    }

    public BigDecimal getBdWoDestRcvoPod() {
        return bdWoDestRcvoPod;
    }

    public void setBdWoDestRcvoPod(BigDecimal bdWoDestRcvoPod) {
        this.bdWoDestRcvoPod = bdWoDestRcvoPod;
    }

    public BigDecimal getOrChPbio() {
        return orChPbio;
    }

    public void setOrChPbio(BigDecimal orChPbio) {
        this.orChPbio = orChPbio;
    }

    public BigDecimal getOrCdPbio() {
        return orCdPbio;
    }

    public void setOrCdPbio(BigDecimal orCdPbio) {
        this.orCdPbio = orCdPbio;
    }

    public BigDecimal getOrRcvoPbi() {
        return orRcvoPbi;
    }

    public void setOrRcvoPbi(BigDecimal orRcvoPbi) {
        this.orRcvoPbi = orRcvoPbi;
    }

    public BigDecimal getOrChUrRcvo() {
        return orChUrRcvo;
    }

    public void setOrChUrRcvo(BigDecimal orChUrRcvo) {
        this.orChUrRcvo = orChUrRcvo;
    }

    public BigDecimal getOrCdUrRcvo() {
        return orCdUrRcvo;
    }

    public void setOrCdUrRcvo(BigDecimal orCdUrRcvo) {
        this.orCdUrRcvo = orCdUrRcvo;
    }

    public BigDecimal getOrCodPayWoRcvo() {
        return orCodPayWoRcvo;
    }

    public void setOrCodPayWoRcvo(BigDecimal orCodPayWoRcvo) {
        this.orCodPayWoRcvo = orCodPayWoRcvo;
    }

    public BigDecimal getOrClaimPayoWoRcvo() {
        return orClaimPayoWoRcvo;
    }

    public void setOrClaimPayoWoRcvo(BigDecimal orClaimPayoWoRcvo) {
        this.orClaimPayoWoRcvo = orClaimPayoWoRcvo;
    }

    public BigDecimal getOrClaimPayoWoRcvt() {
        return orClaimPayoWoRcvt;
    }

    public void setOrClaimPayoWoRcvt(BigDecimal orClaimPayoWoRcvt) {
        this.orClaimPayoWoRcvt = orClaimPayoWoRcvt;
    }

    public BigDecimal getOrClaimPaytWoRcvo() {
        return orClaimPaytWoRcvo;
    }

    public void setOrClaimPaytWoRcvo(BigDecimal orClaimPaytWoRcvo) {
        this.orClaimPaytWoRcvo = orClaimPaytWoRcvo;
    }

    public BigDecimal getOrCustDroWoRcvo() {
        return orCustDroWoRcvo;
    }

    public void setOrCustDroWoRcvo(BigDecimal orCustDroWoRcvo) {
        this.orCustDroWoRcvo = orCustDroWoRcvo;
    }

    public BigDecimal getOrCustDroWoRcvt() {
        return orCustDroWoRcvt;
    }

    public void setOrCustDroWoRcvt(BigDecimal orCustDroWoRcvt) {
        this.orCustDroWoRcvt = orCustDroWoRcvt;
    }

    public BigDecimal getOrCustDrtWoRcvo() {
        return orCustDrtWoRcvo;
    }

    public void setOrCustDrtWoRcvo(BigDecimal orCustDrtWoRcvo) {
        this.orCustDrtWoRcvo = orCustDrtWoRcvo;
    }

    public BigDecimal getOrExWoRcvo() {
        return orExWoRcvo;
    }

    public void setOrExWoRcvo(BigDecimal orExWoRcvo) {
        this.orExWoRcvo = orExWoRcvo;
    }

    public BigDecimal getOrBadWoRcvo() {
        return orBadWoRcvo;
    }

    public void setOrBadWoRcvo(BigDecimal orBadWoRcvo) {
        this.orBadWoRcvo = orBadWoRcvo;
    }

    public BigDecimal getAcOrigRcvNwo() {
        return acOrigRcvNwo;
    }

    public void setAcOrigRcvNwo(BigDecimal acOrigRcvNwo) {
        this.acOrigRcvNwo = acOrigRcvNwo;
    }

    public BigDecimal getAcOrigRcvWo() {
        return acOrigRcvWo;
    }

    public void setAcOrigRcvWo(BigDecimal acOrigRcvWo) {
        this.acOrigRcvWo = acOrigRcvWo;
    }

    public BigDecimal getAcCash() {
        return acCash;
    }

    public void setAcCash(BigDecimal acCash) {
        this.acCash = acCash;
    }

    public BigDecimal getRdOrigoIncome() {
        return rdOrigoIncome;
    }

    public void setRdOrigoIncome(BigDecimal rdOrigoIncome) {
        this.rdOrigoIncome = rdOrigoIncome;
    }

    public BigDecimal getRdOrigoCost() {
        return rdOrigoCost;
    }

    public void setRdOrigoCost(BigDecimal rdOrigoCost) {
        this.rdOrigoCost = rdOrigoCost;
    }

    public BigDecimal getRdOrigoPayApply() {
        return rdOrigoPayApply;
    }

    public void setRdOrigoPayApply(BigDecimal rdOrigoPayApply) {
        this.rdOrigoPayApply = rdOrigoPayApply;
    }

    public BigDecimal getRdOrigoWoOrigRcvoPod() {
        return rdOrigoWoOrigRcvoPod;
    }

    public void setRdOrigoWoOrigRcvoPod(BigDecimal rdOrigoWoOrigRcvoPod) {
        this.rdOrigoWoOrigRcvoPod = rdOrigoWoOrigRcvoPod;
    }

    public BigDecimal getRdOrigoWoOrigRcvtPod() {
        return rdOrigoWoOrigRcvtPod;
    }

    public void setRdOrigoWoOrigRcvtPod(BigDecimal rdOrigoWoOrigRcvtPod) {
        this.rdOrigoWoOrigRcvtPod = rdOrigoWoOrigRcvtPod;
    }

    public BigDecimal getRdOrigtWoOrigRcvoPod() {
        return rdOrigtWoOrigRcvoPod;
    }

    public void setRdOrigtWoOrigRcvoPod(BigDecimal rdOrigtWoOrigRcvoPod) {
        this.rdOrigtWoOrigRcvoPod = rdOrigtWoOrigRcvoPod;
    }

    public BigDecimal getRdOrigoWoOrigRcvoNpod() {
        return rdOrigoWoOrigRcvoNpod;
    }

    public void setRdOrigoWoOrigRcvoNpod(BigDecimal rdOrigoWoOrigRcvoNpod) {
        this.rdOrigoWoOrigRcvoNpod = rdOrigoWoOrigRcvoNpod;
    }

    public BigDecimal getRdOrigoWoOrigRcvtNpod() {
        return rdOrigoWoOrigRcvtNpod;
    }

    public void setRdOrigoWoOrigRcvtNpod(BigDecimal rdOrigoWoOrigRcvtNpod) {
        this.rdOrigoWoOrigRcvtNpod = rdOrigoWoOrigRcvtNpod;
    }

    public BigDecimal getRdOrigtWoOrigRcvoNpod() {
        return rdOrigtWoOrigRcvoNpod;
    }

    public void setRdOrigtWoOrigRcvoNpod(BigDecimal rdOrigtWoOrigRcvoNpod) {
        this.rdOrigtWoOrigRcvoNpod = rdOrigtWoOrigRcvoNpod;
    }

    public BigDecimal getRdDestoIncome() {
        return rdDestoIncome;
    }

    public void setRdDestoIncome(BigDecimal rdDestoIncome) {
        this.rdDestoIncome = rdDestoIncome;
    }

    public BigDecimal getRdDestoDestRcvoPod() {
        return rdDestoDestRcvoPod;
    }

    public void setRdDestoDestRcvoPod(BigDecimal rdDestoDestRcvoPod) {
        this.rdDestoDestRcvoPod = rdDestoDestRcvoPod;
    }

    public BigDecimal getRdDesttWoDestRcvoPod() {
        return rdDesttWoDestRcvoPod;
    }

    public void setRdDesttWoDestRcvoPod(BigDecimal rdDesttWoDestRcvoPod) {
        this.rdDesttWoDestRcvoPod = rdDesttWoDestRcvoPod;
    }

    public BigDecimal getRdDestoWoDestRcvoNpod() {
        return rdDestoWoDestRcvoNpod;
    }

    public void setRdDestoWoDestRcvoNpod(BigDecimal rdDestoWoDestRcvoNpod) {
        this.rdDestoWoDestRcvoNpod = rdDestoWoDestRcvoNpod;
    }

    public BigDecimal getRdDesttWoDestRcvoNpod() {
        return rdDesttWoDestRcvoNpod;
    }

    public void setRdDesttWoDestRcvoNpod(BigDecimal rdDesttWoDestRcvoNpod) {
        this.rdDesttWoDestRcvoNpod = rdDesttWoDestRcvoNpod;
    }

    public BigDecimal getSfoPayApply() {
        return sfoPayApply;
    }

    public void setSfoPayApply(BigDecimal sfoPayApply) {
        this.sfoPayApply = sfoPayApply;
    }

    public BigDecimal getCpoOrigPayApply() {
        return cpoOrigPayApply;
    }

    public void setCpoOrigPayApply(BigDecimal cpoOrigPayApply) {
        this.cpoOrigPayApply = cpoOrigPayApply;
    }

	public BigDecimal getCustDrOch() {
		return custDrOch;
	}

	public void setCustDrOch(BigDecimal custDrOch) {
		this.custDrOch = custDrOch;
	}

	public BigDecimal getCustDrOcd() {
		return custDrOcd;
	}

	public void setCustDrOcd(BigDecimal custDrOcd) {
		this.custDrOcd = custDrOcd;
	}

	public BigDecimal getCustDroWoDestRcvoNpod() {
		return custDroWoDestRcvoNpod;
	}

	public void setCustDroWoDestRcvoNpod(BigDecimal custDroWoDestRcvoNpod) {
		this.custDroWoDestRcvoNpod = custDroWoDestRcvoNpod;
	}

	public BigDecimal getCustDrtWoDestRcvoNpod() {
		return custDrtWoDestRcvoNpod;
	}

	public void setCustDrtWoDestRcvoNpod(BigDecimal custDrtWoDestRcvoNpod) {
		this.custDrtWoDestRcvoNpod = custDrtWoDestRcvoNpod;
	}

	public BigDecimal getCustDroWoDestRcvoPod() {
		return custDroWoDestRcvoPod;
	}

	public void setCustDroWoDestRcvoPod(BigDecimal custDroWoDestRcvoPod) {
		this.custDroWoDestRcvoPod = custDroWoDestRcvoPod;
	}

	public BigDecimal getCustDrtWoDestRcvoPod() {
		return custDrtWoDestRcvoPod;
	}

	public void setCustDrtWoDestRcvoPod(BigDecimal custDrtWoDestRcvoPod) {
		this.custDrtWoDestRcvoPod = custDrtWoDestRcvoPod;
	}

	public BigDecimal getCustDroWoOrigRcvoNpod() {
		return custDroWoOrigRcvoNpod;
	}

	public void setCustDroWoOrigRcvoNpod(BigDecimal custDroWoOrigRcvoNpod) {
		this.custDroWoOrigRcvoNpod = custDroWoOrigRcvoNpod;
	}

	public BigDecimal getCustDroWoOrigRcvtNpod() {
		return custDroWoOrigRcvtNpod;
	}

	public void setCustDroWoOrigRcvtNpod(BigDecimal custDroWoOrigRcvtNpod) {
		this.custDroWoOrigRcvtNpod = custDroWoOrigRcvtNpod;
	}

	public BigDecimal getCustDrtWoOrigRcvoNpod() {
		return custDrtWoOrigRcvoNpod;
	}

	public void setCustDrtWoOrigRcvoNpod(BigDecimal custDrtWoOrigRcvoNpod) {
		this.custDrtWoOrigRcvoNpod = custDrtWoOrigRcvoNpod;
	}

	public BigDecimal getCustDroWoOrigRcvoPod() {
		return custDroWoOrigRcvoPod;
	}

	public void setCustDroWoOrigRcvoPod(BigDecimal custDroWoOrigRcvoPod) {
		this.custDroWoOrigRcvoPod = custDroWoOrigRcvoPod;
	}

	public BigDecimal getCustDroWoOrigRcvtPod() {
		return custDroWoOrigRcvtPod;
	}

	public void setCustDroWoOrigRcvtPod(BigDecimal custDroWoOrigRcvtPod) {
		this.custDroWoOrigRcvtPod = custDroWoOrigRcvtPod;
	}

	public BigDecimal getCustDrtWoOrigRcvoPod() {
		return custDrtWoOrigRcvoPod;
	}

	public void setCustDrtWoOrigRcvoPod(BigDecimal custDrtWoOrigRcvoPod) {
		this.custDrtWoOrigRcvoPod = custDrtWoOrigRcvoPod;
	}

	public BigDecimal getCustDroPayApply() {
		return custDroPayApply;
	}

	public void setCustDroPayApply(BigDecimal custDroPayApply) {
		this.custDroPayApply = custDroPayApply;
	}

	public BigDecimal getPlCostWoDestRcvoPod() {
		return plCostWoDestRcvoPod;
	}

	public void setPlCostWoDestRcvoPod(BigDecimal plCostWoDestRcvoPod) {
		this.plCostWoDestRcvoPod = plCostWoDestRcvoPod;
	}

	public BigDecimal getPlCostWoDestRcvoNpod() {
		return plCostWoDestRcvoNpod;
	}

	public void setPlCostWoDestRcvoNpod(BigDecimal plCostWoDestRcvoNpod) {
		this.plCostWoDestRcvoNpod = plCostWoDestRcvoNpod;
	}

	public BigDecimal getPlDrWoDestRcvoPod() {
		return plDrWoDestRcvoPod;
	}

	public void setPlDrWoDestRcvoPod(BigDecimal plDrWoDestRcvoPod) {
		this.plDrWoDestRcvoPod = plDrWoDestRcvoPod;
	}

	public BigDecimal getPlDrWoDestRcvoNpod() {
		return plDrWoDestRcvoNpod;
	}

	public void setPlDrWoDestRcvoNpod(BigDecimal plDrWoDestRcvoNpod) {
		this.plDrWoDestRcvoNpod = plDrWoDestRcvoNpod;
	}

	public BigDecimal getAlDrWoDestRcvoPod() {
		return alDrWoDestRcvoPod;
	}

	public void setAlDrWoDestRcvoPod(BigDecimal alDrWoDestRcvoPod) {
		this.alDrWoDestRcvoPod = alDrWoDestRcvoPod;
	}

	public BigDecimal getAlDrWoDestRcvoNpod() {
		return alDrWoDestRcvoNpod;
	}

	public void setAlDrWoDestRcvoNpod(BigDecimal alDrWoDestRcvoNpod) {
		this.alDrWoDestRcvoNpod = alDrWoDestRcvoNpod;
	}

	public BigDecimal getAlpwrWoDestRcvoPod() {
		return alpwrWoDestRcvoPod;
	}

	public void setAlpwrWoDestRcvoPod(BigDecimal alpwrWoDestRcvoPod) {
		this.alpwrWoDestRcvoPod = alpwrWoDestRcvoPod;
	}

	public BigDecimal getAlpwrWoDestRcvoNpod() {
		return alpwrWoDestRcvoNpod;
	}

	public void setAlpwrWoDestRcvoNpod(BigDecimal alpwrWoDestRcvoNpod) {
		this.alpwrWoDestRcvoNpod = alpwrWoDestRcvoNpod;
	}

	public BigDecimal getOthPayWoDestRcvoPod() {
		return othPayWoDestRcvoPod;
	}

	public void setOthPayWoDestRcvoPod(BigDecimal othPayWoDestRcvoPod) {
		this.othPayWoDestRcvoPod = othPayWoDestRcvoPod;
	}

	public BigDecimal getOthPayWoDestRcvoNpod() {
		return othPayWoDestRcvoNpod;
	}

	public void setOthPayWoDestRcvoNpod(BigDecimal othPayWoDestRcvoNpod) {
		this.othPayWoDestRcvoNpod = othPayWoDestRcvoNpod;
	}

	public BigDecimal getPopWoDroPod() {
		return popWoDroPod;
	}

	public void setPopWoDroPod(BigDecimal popWoDroPod) {
		this.popWoDroPod = popWoDroPod;
	}

	public BigDecimal getPopWoDroNpod() {
		return popWoDroNpod;
	}

	public void setPopWoDroNpod(BigDecimal popWoDroNpod) {
		this.popWoDroNpod = popWoDroNpod;
	}
    
}
