package com.deppon.foss.module.settlement.closing.api.shared.domain;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MvrNrfostEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String period;

    private String productCode;

    private String customerCode;

    private String customerName;

    private String origOrgCode;

    private String origOrgName;

    private String destOrgCode;

    private String destOrgName;

    private String origUnifiedCode;

    private String destUnifiedCode;

    private Date voucherTime;

    private String customerType;
	
	private String unifiedSettlementType;
	
	private String contractOrgCode;
	
	private String contractOrgName;

    private BigDecimal claimOrigtIncome;

    private BigDecimal claimOrigtCost;

    private BigDecimal claimOrigoWoOrigRcvtPod;

    private BigDecimal claimOrigtOrigRcvoPod;

    private BigDecimal claimOrigoWoOrigRcvtNpod;

    private BigDecimal claimOrigtWoOrigRcvoNpod;

    private BigDecimal claimOrigoPayApply;

    private BigDecimal claimOrigtPayApply;

    private BigDecimal claimOrigtWoOrigRcvtPod;

    private BigDecimal claimOrigtWoOrigRcvtNpod;

    private BigDecimal claimDesttIncome;

    private BigDecimal claimDestoWoDestRcvtPod;

    private BigDecimal claimDesttWoDestRcvtPod;

    private BigDecimal claimDestoWoDestRcvtNpod;

    private BigDecimal claimDesttWoDestRcvtNpod;

    private BigDecimal exOrigRcvtPod;

    private BigDecimal exDestRcvtPod;

    private BigDecimal bdWoOrigRcvtPod;

    private BigDecimal bdWoDestRcvtPod;

    private BigDecimal orChAc;

    private BigDecimal orChSi;

    private BigDecimal orChOpay;

    private BigDecimal orChOther;

    private BigDecimal orChPbio;

    private BigDecimal orChPbit;

    private BigDecimal orCdAc;

    private BigDecimal orCdBankInt;

    private BigDecimal orCdOpay;

    private BigDecimal orCdOther;

    private BigDecimal orCdPbio;

    private BigDecimal orCdPbit;

    private BigDecimal orRcvtPbi;

    private BigDecimal orChUrRcvo;

    private BigDecimal orCdUrRcvo;

    private BigDecimal orChUrRcvt;

    private BigDecimal orCdUrRcvt;

    private BigDecimal orCodPayWoRcvt;

    private BigDecimal orClaimPayoWoRcvt;

    private BigDecimal orClaimPaytWoRcvt;

    private BigDecimal orClaimPaytWoRcvo;

    private BigDecimal orCustDroWoRcvt;

    private BigDecimal orCustDrtWoRcvo;

    private BigDecimal orCustDrtWoRcvt;

    private BigDecimal orExWoRcvt;

    private BigDecimal orBadWoRcvt;

    private BigDecimal acOrigRcvNwo;

    private BigDecimal acOrigRcvWo;

    private BigDecimal acCash;

    private BigDecimal rdOrigoPayApply;

    private BigDecimal rdOrigtIncome;

    private BigDecimal rdOrigtCost;

    private BigDecimal rdOrigtPayApply;

    private BigDecimal rdOrigoWoOrigRcvtPod;

    private BigDecimal rdOrigtWoOrigRcvoPod;

    private BigDecimal rdOrigtWoOrigRcvtPod;

    private BigDecimal rdOrigoWoOrigRcvtNpod;

    private BigDecimal rdOrigtWoOrigRcvoNpod;

    private BigDecimal rdOrigtWoOrigRcvtNpod;

    private BigDecimal rdDesttIncome;

    private BigDecimal rdDestoDestRcvtPod;

    private BigDecimal rdDesttWoDestRcvtPod;

    private BigDecimal rdDestoWoDestRcvtNpod;

    private BigDecimal rdDesttWoDestRcvtNpod;

    private BigDecimal sfoPayApply;

    private BigDecimal sftPayApply;

    private BigDecimal cpoOrigPayApply;

    private BigDecimal cptOrigPayApply;
    
    /**
     * 小票现金之富余仓库出租收入
     */
    private BigDecimal orChRentIncome;
    
    /**
     * 小票现金之收银员卡利息
     */
    private BigDecimal orChBankInt;
    
    /**
     * 小票银行之富余仓库出租收入
     */
    private BigDecimal orCdRentIncome;
    
    /**
     * 预收客户现金
     */
    private BigDecimal custDrOch;
    
    /**
     * 预收客户银行
     */
    private BigDecimal custDrOcd;
    
    /**
     * 预收客户现金
     */
    private BigDecimal custDrTch;
    
    /**
     * 预收客户银行
     */
    private BigDecimal custDrTcd;
    
    /**
     * 01预收客户冲02应收始发运费未签收
     */
    private BigDecimal custDroWoOrigRcvtNpod;
    
    /**
     * 02预收客户冲01应收始发运费未签收
     */
    private BigDecimal custDrtWoOrigRcvoNpod;
    
    /**
     * 01预收客户冲02应收始发运费已签收
     */
    private BigDecimal custDroWoOrigRcvtPod;
    
    /**
     * 02预收客户冲01应收始发运费已签收
     */
    private BigDecimal custDrtWoOrigRcvoPod;
    
    /**
     * 01始发退预收付款申请
     */
    private BigDecimal custDroPayApply;
    
    /**
     * 02始发退预收付款申请
     */
    private BigDecimal custDrtPayApply;
    
    /**
     * 01预收客户冲02应收到付运费未签收
     */
    private BigDecimal custDroWoDestRcvtNpod;
    
    /**
     * 02预收客户冲02应收到付运费未签收
     */
    private BigDecimal custDrtWoDestRcvtNpod;
    
    /**
     * 01预收客户冲02应收到付运费已签收
     */
    private BigDecimal custDroWoDestRcvtPod;
    
    /**
     * 02预收客户冲02应收到付运费已签收
     */
    private BigDecimal custDrtWoDestRcvtPod;
    
    /**
     * 02预收客户冲02应收始发运费未签收
     */
    private BigDecimal custDrtWoOrigRcvtNpod;
    
    /**
     * 02预收客户冲02应收始发运费已签收
     */
    private BigDecimal custDrtWoOrigRcvtPod;
    
    /**
     * 应付偏线代理成本冲02应收到付运费已签收
     */
    private BigDecimal plCostWoDestRcvtPod;
    
    /**
     * 预收偏线代理冲02应收到付运费已签收
     */
    private BigDecimal plDrWoDestRcvtPod;
    
    /**
     * 预收偏线代理冲02应收到付运费未签收
     */
    private BigDecimal plDrWoDestRcvtNpod;
    
    /**
     * 预收空运/快递代理冲02应收到付运费已签收
     */
    private BigDecimal alDrWoDestRcvtPod;
    
    /**
     * 预收空运/快递代理冲02应收到付运费未签收
     */
    private BigDecimal alDrWoDestRcvtNpod;
    
    /**
     * 应付到达代理/快递代理成本冲02应收到付运费已签收
     */
    private BigDecimal alpwrWoDestRcvtPod;
    
    /**
     * 应付到达代理/快递代理成本冲02应收到付运费未签收
     */
    private BigDecimal alpwrWoDestRcvtNpod;
    
    /**
     * 其他应付冲02应收到付运费已签收
     */
    private BigDecimal othPayWoDestRcvtPod;
    
    /**
     * 其他应付冲02应收到付运费未签收
     */
    private BigDecimal othPayWoDestRcvtNpod;
    
    /**
     * 其他应付冲02应收到付运费已签收
     */
    private BigDecimal popWoDrtPod;
    
    /**
     * 其他应付冲02应收到付运费已签收
     */
    private BigDecimal popWoDrtNpod;
    
    /**
     * 应付偏线代理成本冲02应收到付运费未签收
     */
    private BigDecimal plCostWoDestRcvtNpod;
    
    /**
     * 小票现金之叉车费
     */
    private BigDecimal orChForklift;
    
    /**
     * 小票银行之叉车费
     */
    private BigDecimal orCdForklift;

    /**
     * 小票现金之手续费
     */
    private BigDecimal orChHc;

    /**
     * 小票银行之手续费
     */
    private BigDecimal orCdHc;

    /**
     * 02小票现金之仓储费
     */
    private BigDecimal orChStorage;

    /**
     * 02小票现金之外发收入
     */
    private BigDecimal orChWs;

    /**
     * 02小票银行之仓储费
     */
    private BigDecimal orCdStorage;

    /**
     * 02小票银行之外发收入
     */
    private BigDecimal orCdWs;

    /**
     * 02小票应收之仓储费
     */
    private BigDecimal orRcvtStorage;

    /**
     * 02小票应收之外发收入
     */
    private BigDecimal orRcvtWs;

    /**
     * 异常冲收入冲02小票应收之仓储费
     */
    private BigDecimal orExWoStorage;

    /**
     * 异常冲收入冲02小票应收之外发收入
     */
    private BigDecimal orExWoWs;

    /**
     * 小票银行之系统使用费
     */
    private BigDecimal orCdSu;

    /**
     * 小票银行之品牌使用费
     */
    private BigDecimal orCdBu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrigOrgCode() {
        return origOrgCode;
    }

    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode;
    }

    public String getOrigOrgName() {
        return origOrgName;
    }

    public void setOrigOrgName(String origOrgName) {
        this.origOrgName = origOrgName;
    }

    public String getDestOrgCode() {
        return destOrgCode;
    }

    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode;
    }

    public String getDestOrgName() {
        return destOrgName;
    }

    public void setDestOrgName(String destOrgName) {
        this.destOrgName = destOrgName;
    }

    public String getOrigUnifiedCode() {
        return origUnifiedCode;
    }

    public void setOrigUnifiedCode(String origUnifiedCode) {
        this.origUnifiedCode = origUnifiedCode;
    }

    public String getDestUnifiedCode() {
        return destUnifiedCode;
    }

    public void setDestUnifiedCode(String destUnifiedCode) {
        this.destUnifiedCode = destUnifiedCode;
    }

    public Date getVoucherTime() {
        return voucherTime;
    }

    public void setVoucherTime(Date voucherTime) {
        this.voucherTime = voucherTime;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getUnifiedSettlementType() {
		return unifiedSettlementType;
	}

	public void setUnifiedSettlementType(String unifiedSettlementType) {
		this.unifiedSettlementType = unifiedSettlementType;
	}

	public String getContractOrgCode() {
		return contractOrgCode;
	}

	public void setContractOrgCode(String contractOrgCode) {
		this.contractOrgCode = contractOrgCode;
	}

	public String getContractOrgName() {
		return contractOrgName;
	}

	public void setContractOrgName(String contractOrgName) {
		this.contractOrgName = contractOrgName;
	}

	public BigDecimal getClaimOrigtIncome() {
        return claimOrigtIncome;
    }

    public void setClaimOrigtIncome(BigDecimal claimOrigtIncome) {
        this.claimOrigtIncome = claimOrigtIncome;
    }

    public BigDecimal getClaimOrigtCost() {
        return claimOrigtCost;
    }

    public void setClaimOrigtCost(BigDecimal claimOrigtCost) {
        this.claimOrigtCost = claimOrigtCost;
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

    public BigDecimal getClaimOrigtPayApply() {
        return claimOrigtPayApply;
    }

    public void setClaimOrigtPayApply(BigDecimal claimOrigtPayApply) {
        this.claimOrigtPayApply = claimOrigtPayApply;
    }

    public BigDecimal getClaimOrigtWoOrigRcvtPod() {
        return claimOrigtWoOrigRcvtPod;
    }

    public void setClaimOrigtWoOrigRcvtPod(BigDecimal claimOrigtWoOrigRcvtPod) {
        this.claimOrigtWoOrigRcvtPod = claimOrigtWoOrigRcvtPod;
    }

    public BigDecimal getClaimOrigtWoOrigRcvtNpod() {
        return claimOrigtWoOrigRcvtNpod;
    }

    public void setClaimOrigtWoOrigRcvtNpod(BigDecimal claimOrigtWoOrigRcvtNpod) {
        this.claimOrigtWoOrigRcvtNpod = claimOrigtWoOrigRcvtNpod;
    }

    public BigDecimal getClaimDesttIncome() {
        return claimDesttIncome;
    }

    public void setClaimDesttIncome(BigDecimal claimDesttIncome) {
        this.claimDesttIncome = claimDesttIncome;
    }

    public BigDecimal getClaimDestoWoDestRcvtPod() {
        return claimDestoWoDestRcvtPod;
    }

    public void setClaimDestoWoDestRcvtPod(BigDecimal claimDestoWoDestRcvtPod) {
        this.claimDestoWoDestRcvtPod = claimDestoWoDestRcvtPod;
    }

    public BigDecimal getClaimDesttWoDestRcvtPod() {
        return claimDesttWoDestRcvtPod;
    }

    public void setClaimDesttWoDestRcvtPod(BigDecimal claimDesttWoDestRcvtPod) {
        this.claimDesttWoDestRcvtPod = claimDesttWoDestRcvtPod;
    }

    public BigDecimal getClaimDestoWoDestRcvtNpod() {
        return claimDestoWoDestRcvtNpod;
    }

    public void setClaimDestoWoDestRcvtNpod(BigDecimal claimDestoWoDestRcvtNpod) {
        this.claimDestoWoDestRcvtNpod = claimDestoWoDestRcvtNpod;
    }

    public BigDecimal getClaimDesttWoDestRcvtNpod() {
        return claimDesttWoDestRcvtNpod;
    }

    public void setClaimDesttWoDestRcvtNpod(BigDecimal claimDesttWoDestRcvtNpod) {
        this.claimDesttWoDestRcvtNpod = claimDesttWoDestRcvtNpod;
    }

    public BigDecimal getExOrigRcvtPod() {
        return exOrigRcvtPod;
    }

    public void setExOrigRcvtPod(BigDecimal exOrigRcvtPod) {
        this.exOrigRcvtPod = exOrigRcvtPod;
    }

    public BigDecimal getExDestRcvtPod() {
        return exDestRcvtPod;
    }

    public void setExDestRcvtPod(BigDecimal exDestRcvtPod) {
        this.exDestRcvtPod = exDestRcvtPod;
    }

    public BigDecimal getBdWoOrigRcvtPod() {
        return bdWoOrigRcvtPod;
    }

    public void setBdWoOrigRcvtPod(BigDecimal bdWoOrigRcvtPod) {
        this.bdWoOrigRcvtPod = bdWoOrigRcvtPod;
    }

    public BigDecimal getBdWoDestRcvtPod() {
        return bdWoDestRcvtPod;
    }

    public void setBdWoDestRcvtPod(BigDecimal bdWoDestRcvtPod) {
        this.bdWoDestRcvtPod = bdWoDestRcvtPod;
    }

    public BigDecimal getOrChAc() {
        return orChAc;
    }

    public void setOrChAc(BigDecimal orChAc) {
        this.orChAc = orChAc;
    }

    public BigDecimal getOrChSi() {
        return orChSi;
    }

    public void setOrChSi(BigDecimal orChSi) {
        this.orChSi = orChSi;
    }

    public BigDecimal getOrChOpay() {
        return orChOpay;
    }

    public void setOrChOpay(BigDecimal orChOpay) {
        this.orChOpay = orChOpay;
    }

    public BigDecimal getOrChOther() {
        return orChOther;
    }

    public void setOrChOther(BigDecimal orChOther) {
        this.orChOther = orChOther;
    }

    public BigDecimal getOrChPbio() {
        return orChPbio;
    }

    public void setOrChPbio(BigDecimal orChPbio) {
        this.orChPbio = orChPbio;
    }

    public BigDecimal getOrChPbit() {
        return orChPbit;
    }

    public void setOrChPbit(BigDecimal orChPbit) {
        this.orChPbit = orChPbit;
    }

    public BigDecimal getOrCdAc() {
        return orCdAc;
    }

    public void setOrCdAc(BigDecimal orCdAc) {
        this.orCdAc = orCdAc;
    }

    public BigDecimal getOrCdBankInt() {
        return orCdBankInt;
    }

    public void setOrCdBankInt(BigDecimal orCdBankInt) {
        this.orCdBankInt = orCdBankInt;
    }

    public BigDecimal getOrCdOpay() {
        return orCdOpay;
    }

    public void setOrCdOpay(BigDecimal orCdOpay) {
        this.orCdOpay = orCdOpay;
    }

    public BigDecimal getOrCdOther() {
        return orCdOther;
    }

    public void setOrCdOther(BigDecimal orCdOther) {
        this.orCdOther = orCdOther;
    }

    public BigDecimal getOrCdPbio() {
        return orCdPbio;
    }

    public void setOrCdPbio(BigDecimal orCdPbio) {
        this.orCdPbio = orCdPbio;
    }

    public BigDecimal getOrCdPbit() {
        return orCdPbit;
    }

    public BigDecimal getOrCdSu() {
		return orCdSu;
	}

	public void setOrCdSu(BigDecimal orCdSu) {
		this.orCdSu = orCdSu;
	}

	public BigDecimal getOrCdBu() {
		return orCdBu;
	}

	public void setOrCdBu(BigDecimal orCdBu) {
		this.orCdBu = orCdBu;
	}

	public void setOrCdPbit(BigDecimal orCdPbit) {
        this.orCdPbit = orCdPbit;
    }

    public BigDecimal getOrRcvtPbi() {
        return orRcvtPbi;
    }

    public void setOrRcvtPbi(BigDecimal orRcvtPbi) {
        this.orRcvtPbi = orRcvtPbi;
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

    public BigDecimal getOrChUrRcvt() {
        return orChUrRcvt;
    }

    public void setOrChUrRcvt(BigDecimal orChUrRcvt) {
        this.orChUrRcvt = orChUrRcvt;
    }

    public BigDecimal getOrCdUrRcvt() {
        return orCdUrRcvt;
    }

    public void setOrCdUrRcvt(BigDecimal orCdUrRcvt) {
        this.orCdUrRcvt = orCdUrRcvt;
    }

    public BigDecimal getOrCodPayWoRcvt() {
        return orCodPayWoRcvt;
    }

    public void setOrCodPayWoRcvt(BigDecimal orCodPayWoRcvt) {
        this.orCodPayWoRcvt = orCodPayWoRcvt;
    }

    public BigDecimal getOrClaimPayoWoRcvt() {
        return orClaimPayoWoRcvt;
    }

    public void setOrClaimPayoWoRcvt(BigDecimal orClaimPayoWoRcvt) {
        this.orClaimPayoWoRcvt = orClaimPayoWoRcvt;
    }

    public BigDecimal getOrClaimPaytWoRcvt() {
        return orClaimPaytWoRcvt;
    }

    public void setOrClaimPaytWoRcvt(BigDecimal orClaimPaytWoRcvt) {
        this.orClaimPaytWoRcvt = orClaimPaytWoRcvt;
    }

    public BigDecimal getOrClaimPaytWoRcvo() {
        return orClaimPaytWoRcvo;
    }

    public void setOrClaimPaytWoRcvo(BigDecimal orClaimPaytWoRcvo) {
        this.orClaimPaytWoRcvo = orClaimPaytWoRcvo;
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

    public BigDecimal getOrCustDrtWoRcvt() {
        return orCustDrtWoRcvt;
    }

    public void setOrCustDrtWoRcvt(BigDecimal orCustDrtWoRcvt) {
        this.orCustDrtWoRcvt = orCustDrtWoRcvt;
    }

    public BigDecimal getOrExWoRcvt() {
        return orExWoRcvt;
    }

    public void setOrExWoRcvt(BigDecimal orExWoRcvt) {
        this.orExWoRcvt = orExWoRcvt;
    }

    public BigDecimal getOrBadWoRcvt() {
        return orBadWoRcvt;
    }

    public void setOrBadWoRcvt(BigDecimal orBadWoRcvt) {
        this.orBadWoRcvt = orBadWoRcvt;
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

    public BigDecimal getRdOrigoPayApply() {
        return rdOrigoPayApply;
    }

    public void setRdOrigoPayApply(BigDecimal rdOrigoPayApply) {
        this.rdOrigoPayApply = rdOrigoPayApply;
    }

    public BigDecimal getRdOrigtIncome() {
        return rdOrigtIncome;
    }

    public void setRdOrigtIncome(BigDecimal rdOrigtIncome) {
        this.rdOrigtIncome = rdOrigtIncome;
    }

    public BigDecimal getRdOrigtCost() {
        return rdOrigtCost;
    }

    public void setRdOrigtCost(BigDecimal rdOrigtCost) {
        this.rdOrigtCost = rdOrigtCost;
    }

    public BigDecimal getRdOrigtPayApply() {
        return rdOrigtPayApply;
    }

    public void setRdOrigtPayApply(BigDecimal rdOrigtPayApply) {
        this.rdOrigtPayApply = rdOrigtPayApply;
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

    public BigDecimal getRdOrigtWoOrigRcvtPod() {
        return rdOrigtWoOrigRcvtPod;
    }

    public void setRdOrigtWoOrigRcvtPod(BigDecimal rdOrigtWoOrigRcvtPod) {
        this.rdOrigtWoOrigRcvtPod = rdOrigtWoOrigRcvtPod;
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

    public BigDecimal getRdOrigtWoOrigRcvtNpod() {
        return rdOrigtWoOrigRcvtNpod;
    }

    public void setRdOrigtWoOrigRcvtNpod(BigDecimal rdOrigtWoOrigRcvtNpod) {
        this.rdOrigtWoOrigRcvtNpod = rdOrigtWoOrigRcvtNpod;
    }

    public BigDecimal getRdDesttIncome() {
        return rdDesttIncome;
    }

    public void setRdDesttIncome(BigDecimal rdDesttIncome) {
        this.rdDesttIncome = rdDesttIncome;
    }

    public BigDecimal getRdDestoDestRcvtPod() {
        return rdDestoDestRcvtPod;
    }

    public void setRdDestoDestRcvtPod(BigDecimal rdDestoDestRcvtPod) {
        this.rdDestoDestRcvtPod = rdDestoDestRcvtPod;
    }

    public BigDecimal getRdDesttWoDestRcvtPod() {
        return rdDesttWoDestRcvtPod;
    }

    public void setRdDesttWoDestRcvtPod(BigDecimal rdDesttWoDestRcvtPod) {
        this.rdDesttWoDestRcvtPod = rdDesttWoDestRcvtPod;
    }

    public BigDecimal getRdDestoWoDestRcvtNpod() {
        return rdDestoWoDestRcvtNpod;
    }

    public void setRdDestoWoDestRcvtNpod(BigDecimal rdDestoWoDestRcvtNpod) {
        this.rdDestoWoDestRcvtNpod = rdDestoWoDestRcvtNpod;
    }

    public BigDecimal getRdDesttWoDestRcvtNpod() {
        return rdDesttWoDestRcvtNpod;
    }

    public void setRdDesttWoDestRcvtNpod(BigDecimal rdDesttWoDestRcvtNpod) {
        this.rdDesttWoDestRcvtNpod = rdDesttWoDestRcvtNpod;
    }

    public BigDecimal getSfoPayApply() {
        return sfoPayApply;
    }

    public void setSfoPayApply(BigDecimal sfoPayApply) {
        this.sfoPayApply = sfoPayApply;
    }

    public BigDecimal getSftPayApply() {
        return sftPayApply;
    }

    public void setSftPayApply(BigDecimal sftPayApply) {
        this.sftPayApply = sftPayApply;
    }

    public BigDecimal getCpoOrigPayApply() {
        return cpoOrigPayApply;
    }

    public void setCpoOrigPayApply(BigDecimal cpoOrigPayApply) {
        this.cpoOrigPayApply = cpoOrigPayApply;
    }

    public BigDecimal getCptOrigPayApply() {
        return cptOrigPayApply;
    }

    public void setCptOrigPayApply(BigDecimal cptOrigPayApply) {
        this.cptOrigPayApply = cptOrigPayApply;
    }

	public BigDecimal getOrChRentIncome() {
		return orChRentIncome;
	}

	public void setOrChRentIncome(BigDecimal orChRentIncome) {
		this.orChRentIncome = orChRentIncome;
	}

	public BigDecimal getOrChBankInt() {
		return orChBankInt;
	}

	public void setOrChBankInt(BigDecimal orChBankInt) {
		this.orChBankInt = orChBankInt;
	}

	public BigDecimal getOrCdRentIncome() {
		return orCdRentIncome;
	}

	public void setOrCdRentIncome(BigDecimal orCdRentIncome) {
		this.orCdRentIncome = orCdRentIncome;
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

	public BigDecimal getCustDrTch() {
		return custDrTch;
	}

	public void setCustDrTch(BigDecimal custDrTch) {
		this.custDrTch = custDrTch;
	}

	public BigDecimal getCustDrTcd() {
		return custDrTcd;
	}

	public void setCustDrTcd(BigDecimal custDrTcd) {
		this.custDrTcd = custDrTcd;
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

	public BigDecimal getCustDrtPayApply() {
		return custDrtPayApply;
	}

	public void setCustDrtPayApply(BigDecimal custDrtPayApply) {
		this.custDrtPayApply = custDrtPayApply;
	}

	public BigDecimal getCustDroWoDestRcvtNpod() {
		return custDroWoDestRcvtNpod;
	}

	public void setCustDroWoDestRcvtNpod(BigDecimal custDroWoDestRcvtNpod) {
		this.custDroWoDestRcvtNpod = custDroWoDestRcvtNpod;
	}

	public BigDecimal getCustDrtWoDestRcvtNpod() {
		return custDrtWoDestRcvtNpod;
	}

	public void setCustDrtWoDestRcvtNpod(BigDecimal custDrtWoDestRcvtNpod) {
		this.custDrtWoDestRcvtNpod = custDrtWoDestRcvtNpod;
	}

	public BigDecimal getCustDroWoDestRcvtPod() {
		return custDroWoDestRcvtPod;
	}

	public void setCustDroWoDestRcvtPod(BigDecimal custDroWoDestRcvtPod) {
		this.custDroWoDestRcvtPod = custDroWoDestRcvtPod;
	}

	public BigDecimal getCustDrtWoDestRcvtPod() {
		return custDrtWoDestRcvtPod;
	}

	public void setCustDrtWoDestRcvtPod(BigDecimal custDrtWoDestRcvtPod) {
		this.custDrtWoDestRcvtPod = custDrtWoDestRcvtPod;
	}

	public BigDecimal getCustDrtWoOrigRcvtNpod() {
		return custDrtWoOrigRcvtNpod;
	}

	public void setCustDrtWoOrigRcvtNpod(BigDecimal custDrtWoOrigRcvtNpod) {
		this.custDrtWoOrigRcvtNpod = custDrtWoOrigRcvtNpod;
	}

	public BigDecimal getCustDrtWoOrigRcvtPod() {
		return custDrtWoOrigRcvtPod;
	}

	public void setCustDrtWoOrigRcvtPod(BigDecimal custDrtWoOrigRcvtPod) {
		this.custDrtWoOrigRcvtPod = custDrtWoOrigRcvtPod;
	}

	public BigDecimal getPlCostWoDestRcvtPod() {
		return plCostWoDestRcvtPod;
	}

	public void setPlCostWoDestRcvtPod(BigDecimal plCostWoDestRcvtPod) {
		this.plCostWoDestRcvtPod = plCostWoDestRcvtPod;
	}

	public BigDecimal getPlDrWoDestRcvtPod() {
		return plDrWoDestRcvtPod;
	}

	public void setPlDrWoDestRcvtPod(BigDecimal plDrWoDestRcvtPod) {
		this.plDrWoDestRcvtPod = plDrWoDestRcvtPod;
	}

	public BigDecimal getPlDrWoDestRcvtNpod() {
		return plDrWoDestRcvtNpod;
	}

	public void setPlDrWoDestRcvtNpod(BigDecimal plDrWoDestRcvtNpod) {
		this.plDrWoDestRcvtNpod = plDrWoDestRcvtNpod;
	}

	public BigDecimal getAlDrWoDestRcvtPod() {
		return alDrWoDestRcvtPod;
	}

	public void setAlDrWoDestRcvtPod(BigDecimal alDrWoDestRcvtPod) {
		this.alDrWoDestRcvtPod = alDrWoDestRcvtPod;
	}

	public BigDecimal getAlDrWoDestRcvtNpod() {
		return alDrWoDestRcvtNpod;
	}

	public void setAlDrWoDestRcvtNpod(BigDecimal alDrWoDestRcvtNpod) {
		this.alDrWoDestRcvtNpod = alDrWoDestRcvtNpod;
	}

	public BigDecimal getAlpwrWoDestRcvtPod() {
		return alpwrWoDestRcvtPod;
	}

	public void setAlpwrWoDestRcvtPod(BigDecimal alpwrWoDestRcvtPod) {
		this.alpwrWoDestRcvtPod = alpwrWoDestRcvtPod;
	}

	public BigDecimal getAlpwrWoDestRcvtNpod() {
		return alpwrWoDestRcvtNpod;
	}

	public void setAlpwrWoDestRcvtNpod(BigDecimal alpwrWoDestRcvtNpod) {
		this.alpwrWoDestRcvtNpod = alpwrWoDestRcvtNpod;
	}

	public BigDecimal getOthPayWoDestRcvtPod() {
		return othPayWoDestRcvtPod;
	}

	public void setOthPayWoDestRcvtPod(BigDecimal othPayWoDestRcvtPod) {
		this.othPayWoDestRcvtPod = othPayWoDestRcvtPod;
	}

	public BigDecimal getOthPayWoDestRcvtNpod() {
		return othPayWoDestRcvtNpod;
	}

	public void setOthPayWoDestRcvtNpod(BigDecimal othPayWoDestRcvtNpod) {
		this.othPayWoDestRcvtNpod = othPayWoDestRcvtNpod;
	}

	public BigDecimal getPopWoDrtPod() {
		return popWoDrtPod;
	}

	public void setPopWoDrtPod(BigDecimal popWoDrtPod) {
		this.popWoDrtPod = popWoDrtPod;
	}

	public BigDecimal getPopWoDrtNpod() {
		return popWoDrtNpod;
	}

	public void setPopWoDrtNpod(BigDecimal popWoDrtNpod) {
		this.popWoDrtNpod = popWoDrtNpod;
	}

	public BigDecimal getPlCostWoDestRcvtNpod() {
		return plCostWoDestRcvtNpod;
	}

	public void setPlCostWoDestRcvtNpod(BigDecimal plCostWoDestRcvtNpod) {
		this.plCostWoDestRcvtNpod = plCostWoDestRcvtNpod;
	}

	public BigDecimal getOrChForklift() {
		return orChForklift;
	}

	public void setOrChForklift(BigDecimal orChForklift) {
		this.orChForklift = orChForklift;
	}

	public BigDecimal getOrCdForklift() {
		return orCdForklift;
	}

	public void setOrCdForklift(BigDecimal orCdForklift) {
		this.orCdForklift = orCdForklift;
	}

    public BigDecimal getOrChHc() {
        return orChHc;
    }

    public void setOrChHc(BigDecimal orChHc) {
        this.orChHc = orChHc;
    }

    public BigDecimal getOrCdHc() {
        return orCdHc;
    }

    public void setOrCdHc(BigDecimal orCdHc) {
        this.orCdHc = orCdHc;
    }

    public BigDecimal getOrChStorage() {
        return orChStorage;
    }

    public void setOrChStorage(BigDecimal orChStorage) {
        this.orChStorage = orChStorage;
    }

    public BigDecimal getOrChWs() {
        return orChWs;
    }

    public void setOrChWs(BigDecimal orChWs) {
        this.orChWs = orChWs;
    }

    public BigDecimal getOrCdStorage() {
        return orCdStorage;
    }

    public void setOrCdStorage(BigDecimal orCdStorage) {
        this.orCdStorage = orCdStorage;
    }

    public BigDecimal getOrCdWs() {
        return orCdWs;
    }

    public void setOrCdWs(BigDecimal orCdWs) {
        this.orCdWs = orCdWs;
    }

    public BigDecimal getOrRcvtStorage() {
        return orRcvtStorage;
    }

    public void setOrRcvtStorage(BigDecimal orRcvtStorage) {
        this.orRcvtStorage = orRcvtStorage;
    }

    public BigDecimal getOrRcvtWs() {
        return orRcvtWs;
    }

    public void setOrRcvtWs(BigDecimal orRcvtWs) {
        this.orRcvtWs = orRcvtWs;
    }

    public BigDecimal getOrExWoStorage() {
        return orExWoStorage;
    }

    public void setOrExWoStorage(BigDecimal orExWoStorage) {
        this.orExWoStorage = orExWoStorage;
    }

    public BigDecimal getOrExWoWs() {
        return orExWoWs;
    }

    public void setOrExWoWs(BigDecimal orExWoWs) {
        this.orExWoWs = orExWoWs;
    }
}