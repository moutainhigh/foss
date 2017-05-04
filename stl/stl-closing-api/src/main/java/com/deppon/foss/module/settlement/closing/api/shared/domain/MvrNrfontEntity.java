package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 02普通业务始发月报表
 * 
 * @author luoyizhu@gmail.com
 * 
 *         2014-2-22 上午9:43:08
 */
public class MvrNrfontEntity implements Serializable {

	/**
	 * 序列化id
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

	private BigDecimal deoCh;

	private BigDecimal deoCd;

	private BigDecimal detCh;

	private BigDecimal detCd;

	private BigDecimal uroOrigChNpod;

	private BigDecimal uroOrigCdNpod;

	private BigDecimal uroOrigChPod;

	private BigDecimal uroOrigCdPod;

	private BigDecimal urtOrigChNpod;

	private BigDecimal urtOrigCdNpod;

	private BigDecimal urtOrigChPod;

	private BigDecimal urtOrigCdPod;

	private BigDecimal urtDestChNpod;

	private BigDecimal urtDestCdNpod;

	private BigDecimal urtDestChPod;

	private BigDecimal urtDestCdPod;

	private BigDecimal custDrOch;

	private BigDecimal custDrOcd;

	private BigDecimal custDrTch;

	private BigDecimal custDrTcd;

	private BigDecimal custDroWoOrigRcvtNpod;

	private BigDecimal custDrtWoOrigRcvoNpod;

	private BigDecimal custDroWoOrigRcvtPod;

	private BigDecimal custDrtWoOrigRcvoPod;

	private BigDecimal custDroPayApply;

	private BigDecimal custDrtPayApply;

	private BigDecimal custDroWoDestRcvtNpod;

	private BigDecimal custDrtWoDestRcvtNpod;

	private BigDecimal custDroWoDestRcvtPod;

	private BigDecimal custDrtWoDestRcvtPod;

	private BigDecimal custDrtWoOrigRcvtNpod;

	private BigDecimal custDrtWoOrigRcvtPod;

	private BigDecimal codPayWoDestRcvtPod;

	private BigDecimal codPayWoDestRcvtNpod;

	private BigDecimal codPayWoOrigRcvtPod;

	private BigDecimal codPayWoOrigRcvtNpod;

	private BigDecimal plCostWoDestRcvtPod;

	private BigDecimal plCostWoDestRcvtNpod;

	private BigDecimal plDrWoDestRcvtPod;

	private BigDecimal plDrWoDestRcvtNpod;

	private BigDecimal alDrWoDestRcvtPod;

	private BigDecimal alDrWoDestRcvtNpod;

	private BigDecimal alpwrWoDestRcvtPod;

	private BigDecimal alpwrWoDestRcvtNpod;

	private BigDecimal othPayWoDestRcvtPod;

	private BigDecimal othPayWoDestRcvtNpod;

	private BigDecimal podtCashCollectedFrt;

	private BigDecimal podtCashCollectedPup;

	private BigDecimal podtCashCollectedDel;

	private BigDecimal podtCashCollectedPkg;

	private BigDecimal podtCashCollectedCod;

	private BigDecimal podtCashCollectedDv;

	private BigDecimal podtCashCollectedOt;

	private BigDecimal podtOrigRcvWoFrt;

	private BigDecimal podtOrigRcvWoPup;

	private BigDecimal podtOrigRcvWoDel;

	private BigDecimal podtOrigRcvWoPkg;

	private BigDecimal podtOrigRcvWoCod;

	private BigDecimal podtOrigRcvWoDv;

	private BigDecimal podtOrigRcvWoOt;

	private BigDecimal podtOrigRcvNwoFrt;

	private BigDecimal podtOrigRcvNwoPup;

	private BigDecimal podtOrigRcvNwoDel;

	private BigDecimal podtOrigRcvNwoPkg;

	private BigDecimal podtOrigRcvNwoCod;

	private BigDecimal podtOrigRcvNwoDv;

	private BigDecimal podtOrigRcvNwoOt;

	private BigDecimal podtDestRcvWoFrt;

	private BigDecimal podtDestRcvWoPup;

	private BigDecimal podtDestRcvWoDel;

	private BigDecimal podtDestRcvWoPkg;

	private BigDecimal podtDestRcvWoCod;

	private BigDecimal podtDestRcvWoDv;

	private BigDecimal podtDestRcvWoOt;

	private BigDecimal podtDestRcvNwoFrt;

	private BigDecimal podtDestRcvNwoPup;

	private BigDecimal podtDestRcvNwoDel;

	private BigDecimal podtDestRcvNwoPkg;

	private BigDecimal podtDestRcvNwoCod;

	private BigDecimal podtDestRcvNwoDv;

	private BigDecimal podtDestRcvNwoOt;

	private BigDecimal updtCashCollectedFrt;

	private BigDecimal updtCashCollectedPup;

	private BigDecimal updtCashCollectedDel;

	private BigDecimal updtCashCollectedPkg;

	private BigDecimal updtCashCollectedCod;

	private BigDecimal updtCashCollectedDv;

	private BigDecimal updtCashCollectedOt;

	private BigDecimal updtOrigRcvWoFrt;

	private BigDecimal updtOrigRcvWoPup;

	private BigDecimal updtOrigRcvWoDel;

	private BigDecimal updtOrigRcvWoPkg;

	private BigDecimal updtOrigRcvWoCod;

	private BigDecimal updtOrigRcvWoDv;

	private BigDecimal updtOrigRcvWoOt;

	private BigDecimal updtOrigRcvNwoFrt;

	private BigDecimal updtOrigRcvNwoPup;

	private BigDecimal updtOrigRcvNwoDel;

	private BigDecimal updtOrigRcvNwoPkg;

	private BigDecimal updtOrigRcvNwoCod;

	private BigDecimal updtOrigRcvNwoDv;

	private BigDecimal updtOrigRcvNwoOt;

	private BigDecimal updtDestRcvWoFrt;

	private BigDecimal updtDestRcvWoPup;

	private BigDecimal updtDestRcvWoDel;

	private BigDecimal updtDestRcvWoPkg;

	private BigDecimal updtDestRcvWoCod;

	private BigDecimal updtDestRcvWoDv;

	private BigDecimal updtDestRcvWoOt;

	private BigDecimal updtDestRcvNwoFrt;

	private BigDecimal updtDestRcvNwoPup;

	private BigDecimal updtDestRcvNwoDel;

	private BigDecimal updtDestRcvNwoPkg;

	private BigDecimal updtDestRcvNwoCod;

	private BigDecimal updtDestRcvNwoDv;

	private BigDecimal updtDestRcvNwoOt;

	/**
	 * 其他应付冲02应收到付运费已签收
	 */
	private BigDecimal popWoDrtPod;

	/**
	 * 其他应付冲02应收到付运费未签收
	 */
	private BigDecimal popWoDrtNpod;

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

	public BigDecimal getDeoCh() {
		return deoCh;
	}

	public void setDeoCh(BigDecimal deoCh) {
		this.deoCh = deoCh;
	}

	public BigDecimal getDeoCd() {
		return deoCd;
	}

	public void setDeoCd(BigDecimal deoCd) {
		this.deoCd = deoCd;
	}

	public BigDecimal getDetCh() {
		return detCh;
	}

	public void setDetCh(BigDecimal detCh) {
		this.detCh = detCh;
	}

	public BigDecimal getDetCd() {
		return detCd;
	}

	public void setDetCd(BigDecimal detCd) {
		this.detCd = detCd;
	}

	public BigDecimal getUroOrigChNpod() {
		return uroOrigChNpod;
	}

	public void setUroOrigChNpod(BigDecimal uroOrigChNpod) {
		this.uroOrigChNpod = uroOrigChNpod;
	}

	public BigDecimal getUroOrigCdNpod() {
		return uroOrigCdNpod;
	}

	public void setUroOrigCdNpod(BigDecimal uroOrigCdNpod) {
		this.uroOrigCdNpod = uroOrigCdNpod;
	}

	public BigDecimal getUroOrigChPod() {
		return uroOrigChPod;
	}

	public void setUroOrigChPod(BigDecimal uroOrigChPod) {
		this.uroOrigChPod = uroOrigChPod;
	}

	public BigDecimal getUroOrigCdPod() {
		return uroOrigCdPod;
	}

	public void setUroOrigCdPod(BigDecimal uroOrigCdPod) {
		this.uroOrigCdPod = uroOrigCdPod;
	}

	public BigDecimal getUrtOrigChNpod() {
		return urtOrigChNpod;
	}

	public void setUrtOrigChNpod(BigDecimal urtOrigChNpod) {
		this.urtOrigChNpod = urtOrigChNpod;
	}

	public BigDecimal getUrtOrigCdNpod() {
		return urtOrigCdNpod;
	}

	public void setUrtOrigCdNpod(BigDecimal urtOrigCdNpod) {
		this.urtOrigCdNpod = urtOrigCdNpod;
	}

	public BigDecimal getUrtOrigChPod() {
		return urtOrigChPod;
	}

	public void setUrtOrigChPod(BigDecimal urtOrigChPod) {
		this.urtOrigChPod = urtOrigChPod;
	}

	public BigDecimal getUrtOrigCdPod() {
		return urtOrigCdPod;
	}

	public void setUrtOrigCdPod(BigDecimal urtOrigCdPod) {
		this.urtOrigCdPod = urtOrigCdPod;
	}

	public BigDecimal getUrtDestChNpod() {
		return urtDestChNpod;
	}

	public void setUrtDestChNpod(BigDecimal urtDestChNpod) {
		this.urtDestChNpod = urtDestChNpod;
	}

	public BigDecimal getUrtDestCdNpod() {
		return urtDestCdNpod;
	}

	public void setUrtDestCdNpod(BigDecimal urtDestCdNpod) {
		this.urtDestCdNpod = urtDestCdNpod;
	}

	public BigDecimal getUrtDestChPod() {
		return urtDestChPod;
	}

	public void setUrtDestChPod(BigDecimal urtDestChPod) {
		this.urtDestChPod = urtDestChPod;
	}

	public BigDecimal getUrtDestCdPod() {
		return urtDestCdPod;
	}

	public void setUrtDestCdPod(BigDecimal urtDestCdPod) {
		this.urtDestCdPod = urtDestCdPod;
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

	public BigDecimal getCodPayWoDestRcvtPod() {
		return codPayWoDestRcvtPod;
	}

	public void setCodPayWoDestRcvtPod(BigDecimal codPayWoDestRcvtPod) {
		this.codPayWoDestRcvtPod = codPayWoDestRcvtPod;
	}

	public BigDecimal getCodPayWoDestRcvtNpod() {
		return codPayWoDestRcvtNpod;
	}

	public void setCodPayWoDestRcvtNpod(BigDecimal codPayWoDestRcvtNpod) {
		this.codPayWoDestRcvtNpod = codPayWoDestRcvtNpod;
	}

	public BigDecimal getCodPayWoOrigRcvtPod() {
		return codPayWoOrigRcvtPod;
	}

	public void setCodPayWoOrigRcvtPod(BigDecimal codPayWoOrigRcvtPod) {
		this.codPayWoOrigRcvtPod = codPayWoOrigRcvtPod;
	}

	public BigDecimal getCodPayWoOrigRcvtNpod() {
		return codPayWoOrigRcvtNpod;
	}

	public void setCodPayWoOrigRcvtNpod(BigDecimal codPayWoOrigRcvtNpod) {
		this.codPayWoOrigRcvtNpod = codPayWoOrigRcvtNpod;
	}

	public BigDecimal getPlCostWoDestRcvtPod() {
		return plCostWoDestRcvtPod;
	}

	public void setPlCostWoDestRcvtPod(BigDecimal plCostWoDestRcvtPod) {
		this.plCostWoDestRcvtPod = plCostWoDestRcvtPod;
	}

	public BigDecimal getPlCostWoDestRcvtNpod() {
		return plCostWoDestRcvtNpod;
	}

	public void setPlCostWoDestRcvtNpod(BigDecimal plCostWoDestRcvtNpod) {
		this.plCostWoDestRcvtNpod = plCostWoDestRcvtNpod;
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

	public BigDecimal getPodtCashCollectedFrt() {
		return podtCashCollectedFrt;
	}

	public void setPodtCashCollectedFrt(BigDecimal podtCashCollectedFrt) {
		this.podtCashCollectedFrt = podtCashCollectedFrt;
	}

	public BigDecimal getPodtCashCollectedPup() {
		return podtCashCollectedPup;
	}

	public void setPodtCashCollectedPup(BigDecimal podtCashCollectedPup) {
		this.podtCashCollectedPup = podtCashCollectedPup;
	}

	public BigDecimal getPodtCashCollectedDel() {
		return podtCashCollectedDel;
	}

	public void setPodtCashCollectedDel(BigDecimal podtCashCollectedDel) {
		this.podtCashCollectedDel = podtCashCollectedDel;
	}

	public BigDecimal getPodtCashCollectedPkg() {
		return podtCashCollectedPkg;
	}

	public void setPodtCashCollectedPkg(BigDecimal podtCashCollectedPkg) {
		this.podtCashCollectedPkg = podtCashCollectedPkg;
	}

	public BigDecimal getPodtCashCollectedCod() {
		return podtCashCollectedCod;
	}

	public void setPodtCashCollectedCod(BigDecimal podtCashCollectedCod) {
		this.podtCashCollectedCod = podtCashCollectedCod;
	}

	public BigDecimal getPodtCashCollectedDv() {
		return podtCashCollectedDv;
	}

	public void setPodtCashCollectedDv(BigDecimal podtCashCollectedDv) {
		this.podtCashCollectedDv = podtCashCollectedDv;
	}

	public BigDecimal getPodtCashCollectedOt() {
		return podtCashCollectedOt;
	}

	public void setPodtCashCollectedOt(BigDecimal podtCashCollectedOt) {
		this.podtCashCollectedOt = podtCashCollectedOt;
	}

	public BigDecimal getPodtOrigRcvWoFrt() {
		return podtOrigRcvWoFrt;
	}

	public void setPodtOrigRcvWoFrt(BigDecimal podtOrigRcvWoFrt) {
		this.podtOrigRcvWoFrt = podtOrigRcvWoFrt;
	}

	public BigDecimal getPodtOrigRcvWoPup() {
		return podtOrigRcvWoPup;
	}

	public void setPodtOrigRcvWoPup(BigDecimal podtOrigRcvWoPup) {
		this.podtOrigRcvWoPup = podtOrigRcvWoPup;
	}

	public BigDecimal getPodtOrigRcvWoDel() {
		return podtOrigRcvWoDel;
	}

	public void setPodtOrigRcvWoDel(BigDecimal podtOrigRcvWoDel) {
		this.podtOrigRcvWoDel = podtOrigRcvWoDel;
	}

	public BigDecimal getPodtOrigRcvWoPkg() {
		return podtOrigRcvWoPkg;
	}

	public void setPodtOrigRcvWoPkg(BigDecimal podtOrigRcvWoPkg) {
		this.podtOrigRcvWoPkg = podtOrigRcvWoPkg;
	}

	public BigDecimal getPodtOrigRcvWoCod() {
		return podtOrigRcvWoCod;
	}

	public void setPodtOrigRcvWoCod(BigDecimal podtOrigRcvWoCod) {
		this.podtOrigRcvWoCod = podtOrigRcvWoCod;
	}

	public BigDecimal getPodtOrigRcvWoDv() {
		return podtOrigRcvWoDv;
	}

	public void setPodtOrigRcvWoDv(BigDecimal podtOrigRcvWoDv) {
		this.podtOrigRcvWoDv = podtOrigRcvWoDv;
	}

	public BigDecimal getPodtOrigRcvWoOt() {
		return podtOrigRcvWoOt;
	}

	public void setPodtOrigRcvWoOt(BigDecimal podtOrigRcvWoOt) {
		this.podtOrigRcvWoOt = podtOrigRcvWoOt;
	}

	public BigDecimal getPodtOrigRcvNwoFrt() {
		return podtOrigRcvNwoFrt;
	}

	public void setPodtOrigRcvNwoFrt(BigDecimal podtOrigRcvNwoFrt) {
		this.podtOrigRcvNwoFrt = podtOrigRcvNwoFrt;
	}

	public BigDecimal getPodtOrigRcvNwoPup() {
		return podtOrigRcvNwoPup;
	}

	public void setPodtOrigRcvNwoPup(BigDecimal podtOrigRcvNwoPup) {
		this.podtOrigRcvNwoPup = podtOrigRcvNwoPup;
	}

	public BigDecimal getPodtOrigRcvNwoDel() {
		return podtOrigRcvNwoDel;
	}

	public void setPodtOrigRcvNwoDel(BigDecimal podtOrigRcvNwoDel) {
		this.podtOrigRcvNwoDel = podtOrigRcvNwoDel;
	}

	public BigDecimal getPodtOrigRcvNwoPkg() {
		return podtOrigRcvNwoPkg;
	}

	public void setPodtOrigRcvNwoPkg(BigDecimal podtOrigRcvNwoPkg) {
		this.podtOrigRcvNwoPkg = podtOrigRcvNwoPkg;
	}

	public BigDecimal getPodtOrigRcvNwoCod() {
		return podtOrigRcvNwoCod;
	}

	public void setPodtOrigRcvNwoCod(BigDecimal podtOrigRcvNwoCod) {
		this.podtOrigRcvNwoCod = podtOrigRcvNwoCod;
	}

	public BigDecimal getPodtOrigRcvNwoDv() {
		return podtOrigRcvNwoDv;
	}

	public void setPodtOrigRcvNwoDv(BigDecimal podtOrigRcvNwoDv) {
		this.podtOrigRcvNwoDv = podtOrigRcvNwoDv;
	}

	public BigDecimal getPodtOrigRcvNwoOt() {
		return podtOrigRcvNwoOt;
	}

	public void setPodtOrigRcvNwoOt(BigDecimal podtOrigRcvNwoOt) {
		this.podtOrigRcvNwoOt = podtOrigRcvNwoOt;
	}

	public BigDecimal getPodtDestRcvWoFrt() {
		return podtDestRcvWoFrt;
	}

	public void setPodtDestRcvWoFrt(BigDecimal podtDestRcvWoFrt) {
		this.podtDestRcvWoFrt = podtDestRcvWoFrt;
	}

	public BigDecimal getPodtDestRcvWoPup() {
		return podtDestRcvWoPup;
	}

	public void setPodtDestRcvWoPup(BigDecimal podtDestRcvWoPup) {
		this.podtDestRcvWoPup = podtDestRcvWoPup;
	}

	public BigDecimal getPodtDestRcvWoDel() {
		return podtDestRcvWoDel;
	}

	public void setPodtDestRcvWoDel(BigDecimal podtDestRcvWoDel) {
		this.podtDestRcvWoDel = podtDestRcvWoDel;
	}

	public BigDecimal getPodtDestRcvWoPkg() {
		return podtDestRcvWoPkg;
	}

	public void setPodtDestRcvWoPkg(BigDecimal podtDestRcvWoPkg) {
		this.podtDestRcvWoPkg = podtDestRcvWoPkg;
	}

	public BigDecimal getPodtDestRcvWoCod() {
		return podtDestRcvWoCod;
	}

	public void setPodtDestRcvWoCod(BigDecimal podtDestRcvWoCod) {
		this.podtDestRcvWoCod = podtDestRcvWoCod;
	}

	public BigDecimal getPodtDestRcvWoDv() {
		return podtDestRcvWoDv;
	}

	public void setPodtDestRcvWoDv(BigDecimal podtDestRcvWoDv) {
		this.podtDestRcvWoDv = podtDestRcvWoDv;
	}

	public BigDecimal getPodtDestRcvWoOt() {
		return podtDestRcvWoOt;
	}

	public void setPodtDestRcvWoOt(BigDecimal podtDestRcvWoOt) {
		this.podtDestRcvWoOt = podtDestRcvWoOt;
	}

	public BigDecimal getPodtDestRcvNwoFrt() {
		return podtDestRcvNwoFrt;
	}

	public void setPodtDestRcvNwoFrt(BigDecimal podtDestRcvNwoFrt) {
		this.podtDestRcvNwoFrt = podtDestRcvNwoFrt;
	}

	public BigDecimal getPodtDestRcvNwoPup() {
		return podtDestRcvNwoPup;
	}

	public void setPodtDestRcvNwoPup(BigDecimal podtDestRcvNwoPup) {
		this.podtDestRcvNwoPup = podtDestRcvNwoPup;
	}

	public BigDecimal getPodtDestRcvNwoDel() {
		return podtDestRcvNwoDel;
	}

	public void setPodtDestRcvNwoDel(BigDecimal podtDestRcvNwoDel) {
		this.podtDestRcvNwoDel = podtDestRcvNwoDel;
	}

	public BigDecimal getPodtDestRcvNwoPkg() {
		return podtDestRcvNwoPkg;
	}

	public void setPodtDestRcvNwoPkg(BigDecimal podtDestRcvNwoPkg) {
		this.podtDestRcvNwoPkg = podtDestRcvNwoPkg;
	}

	public BigDecimal getPodtDestRcvNwoCod() {
		return podtDestRcvNwoCod;
	}

	public void setPodtDestRcvNwoCod(BigDecimal podtDestRcvNwoCod) {
		this.podtDestRcvNwoCod = podtDestRcvNwoCod;
	}

	public BigDecimal getPodtDestRcvNwoDv() {
		return podtDestRcvNwoDv;
	}

	public void setPodtDestRcvNwoDv(BigDecimal podtDestRcvNwoDv) {
		this.podtDestRcvNwoDv = podtDestRcvNwoDv;
	}

	public BigDecimal getPodtDestRcvNwoOt() {
		return podtDestRcvNwoOt;
	}

	public void setPodtDestRcvNwoOt(BigDecimal podtDestRcvNwoOt) {
		this.podtDestRcvNwoOt = podtDestRcvNwoOt;
	}

	public BigDecimal getUpdtCashCollectedFrt() {
		return updtCashCollectedFrt;
	}

	public void setUpdtCashCollectedFrt(BigDecimal updtCashCollectedFrt) {
		this.updtCashCollectedFrt = updtCashCollectedFrt;
	}

	public BigDecimal getUpdtCashCollectedPup() {
		return updtCashCollectedPup;
	}

	public void setUpdtCashCollectedPup(BigDecimal updtCashCollectedPup) {
		this.updtCashCollectedPup = updtCashCollectedPup;
	}

	public BigDecimal getUpdtCashCollectedDel() {
		return updtCashCollectedDel;
	}

	public void setUpdtCashCollectedDel(BigDecimal updtCashCollectedDel) {
		this.updtCashCollectedDel = updtCashCollectedDel;
	}

	public BigDecimal getUpdtCashCollectedPkg() {
		return updtCashCollectedPkg;
	}

	public void setUpdtCashCollectedPkg(BigDecimal updtCashCollectedPkg) {
		this.updtCashCollectedPkg = updtCashCollectedPkg;
	}

	public BigDecimal getUpdtCashCollectedCod() {
		return updtCashCollectedCod;
	}

	public void setUpdtCashCollectedCod(BigDecimal updtCashCollectedCod) {
		this.updtCashCollectedCod = updtCashCollectedCod;
	}

	public BigDecimal getUpdtCashCollectedDv() {
		return updtCashCollectedDv;
	}

	public void setUpdtCashCollectedDv(BigDecimal updtCashCollectedDv) {
		this.updtCashCollectedDv = updtCashCollectedDv;
	}

	public BigDecimal getUpdtCashCollectedOt() {
		return updtCashCollectedOt;
	}

	public void setUpdtCashCollectedOt(BigDecimal updtCashCollectedOt) {
		this.updtCashCollectedOt = updtCashCollectedOt;
	}

	public BigDecimal getUpdtOrigRcvWoFrt() {
		return updtOrigRcvWoFrt;
	}

	public void setUpdtOrigRcvWoFrt(BigDecimal updtOrigRcvWoFrt) {
		this.updtOrigRcvWoFrt = updtOrigRcvWoFrt;
	}

	public BigDecimal getUpdtOrigRcvWoPup() {
		return updtOrigRcvWoPup;
	}

	public void setUpdtOrigRcvWoPup(BigDecimal updtOrigRcvWoPup) {
		this.updtOrigRcvWoPup = updtOrigRcvWoPup;
	}

	public BigDecimal getUpdtOrigRcvWoDel() {
		return updtOrigRcvWoDel;
	}

	public void setUpdtOrigRcvWoDel(BigDecimal updtOrigRcvWoDel) {
		this.updtOrigRcvWoDel = updtOrigRcvWoDel;
	}

	public BigDecimal getUpdtOrigRcvWoPkg() {
		return updtOrigRcvWoPkg;
	}

	public void setUpdtOrigRcvWoPkg(BigDecimal updtOrigRcvWoPkg) {
		this.updtOrigRcvWoPkg = updtOrigRcvWoPkg;
	}

	public BigDecimal getUpdtOrigRcvWoCod() {
		return updtOrigRcvWoCod;
	}

	public void setUpdtOrigRcvWoCod(BigDecimal updtOrigRcvWoCod) {
		this.updtOrigRcvWoCod = updtOrigRcvWoCod;
	}

	public BigDecimal getUpdtOrigRcvWoDv() {
		return updtOrigRcvWoDv;
	}

	public void setUpdtOrigRcvWoDv(BigDecimal updtOrigRcvWoDv) {
		this.updtOrigRcvWoDv = updtOrigRcvWoDv;
	}

	public BigDecimal getUpdtOrigRcvWoOt() {
		return updtOrigRcvWoOt;
	}

	public void setUpdtOrigRcvWoOt(BigDecimal updtOrigRcvWoOt) {
		this.updtOrigRcvWoOt = updtOrigRcvWoOt;
	}

	public BigDecimal getUpdtOrigRcvNwoFrt() {
		return updtOrigRcvNwoFrt;
	}

	public void setUpdtOrigRcvNwoFrt(BigDecimal updtOrigRcvNwoFrt) {
		this.updtOrigRcvNwoFrt = updtOrigRcvNwoFrt;
	}

	public BigDecimal getUpdtOrigRcvNwoPup() {
		return updtOrigRcvNwoPup;
	}

	public void setUpdtOrigRcvNwoPup(BigDecimal updtOrigRcvNwoPup) {
		this.updtOrigRcvNwoPup = updtOrigRcvNwoPup;
	}

	public BigDecimal getUpdtOrigRcvNwoDel() {
		return updtOrigRcvNwoDel;
	}

	public void setUpdtOrigRcvNwoDel(BigDecimal updtOrigRcvNwoDel) {
		this.updtOrigRcvNwoDel = updtOrigRcvNwoDel;
	}

	public BigDecimal getUpdtOrigRcvNwoPkg() {
		return updtOrigRcvNwoPkg;
	}

	public void setUpdtOrigRcvNwoPkg(BigDecimal updtOrigRcvNwoPkg) {
		this.updtOrigRcvNwoPkg = updtOrigRcvNwoPkg;
	}

	public BigDecimal getUpdtOrigRcvNwoCod() {
		return updtOrigRcvNwoCod;
	}

	public void setUpdtOrigRcvNwoCod(BigDecimal updtOrigRcvNwoCod) {
		this.updtOrigRcvNwoCod = updtOrigRcvNwoCod;
	}

	public BigDecimal getUpdtOrigRcvNwoDv() {
		return updtOrigRcvNwoDv;
	}

	public void setUpdtOrigRcvNwoDv(BigDecimal updtOrigRcvNwoDv) {
		this.updtOrigRcvNwoDv = updtOrigRcvNwoDv;
	}

	public BigDecimal getUpdtOrigRcvNwoOt() {
		return updtOrigRcvNwoOt;
	}

	public void setUpdtOrigRcvNwoOt(BigDecimal updtOrigRcvNwoOt) {
		this.updtOrigRcvNwoOt = updtOrigRcvNwoOt;
	}

	public BigDecimal getUpdtDestRcvWoFrt() {
		return updtDestRcvWoFrt;
	}

	public void setUpdtDestRcvWoFrt(BigDecimal updtDestRcvWoFrt) {
		this.updtDestRcvWoFrt = updtDestRcvWoFrt;
	}

	public BigDecimal getUpdtDestRcvWoPup() {
		return updtDestRcvWoPup;
	}

	public void setUpdtDestRcvWoPup(BigDecimal updtDestRcvWoPup) {
		this.updtDestRcvWoPup = updtDestRcvWoPup;
	}

	public BigDecimal getUpdtDestRcvWoDel() {
		return updtDestRcvWoDel;
	}

	public void setUpdtDestRcvWoDel(BigDecimal updtDestRcvWoDel) {
		this.updtDestRcvWoDel = updtDestRcvWoDel;
	}

	public BigDecimal getUpdtDestRcvWoPkg() {
		return updtDestRcvWoPkg;
	}

	public void setUpdtDestRcvWoPkg(BigDecimal updtDestRcvWoPkg) {
		this.updtDestRcvWoPkg = updtDestRcvWoPkg;
	}

	public BigDecimal getUpdtDestRcvWoCod() {
		return updtDestRcvWoCod;
	}

	public void setUpdtDestRcvWoCod(BigDecimal updtDestRcvWoCod) {
		this.updtDestRcvWoCod = updtDestRcvWoCod;
	}

	public BigDecimal getUpdtDestRcvWoDv() {
		return updtDestRcvWoDv;
	}

	public void setUpdtDestRcvWoDv(BigDecimal updtDestRcvWoDv) {
		this.updtDestRcvWoDv = updtDestRcvWoDv;
	}

	public BigDecimal getUpdtDestRcvWoOt() {
		return updtDestRcvWoOt;
	}

	public void setUpdtDestRcvWoOt(BigDecimal updtDestRcvWoOt) {
		this.updtDestRcvWoOt = updtDestRcvWoOt;
	}

	public BigDecimal getUpdtDestRcvNwoFrt() {
		return updtDestRcvNwoFrt;
	}

	public void setUpdtDestRcvNwoFrt(BigDecimal updtDestRcvNwoFrt) {
		this.updtDestRcvNwoFrt = updtDestRcvNwoFrt;
	}

	public BigDecimal getUpdtDestRcvNwoPup() {
		return updtDestRcvNwoPup;
	}

	public void setUpdtDestRcvNwoPup(BigDecimal updtDestRcvNwoPup) {
		this.updtDestRcvNwoPup = updtDestRcvNwoPup;
	}

	public BigDecimal getUpdtDestRcvNwoDel() {
		return updtDestRcvNwoDel;
	}

	public void setUpdtDestRcvNwoDel(BigDecimal updtDestRcvNwoDel) {
		this.updtDestRcvNwoDel = updtDestRcvNwoDel;
	}

	public BigDecimal getUpdtDestRcvNwoPkg() {
		return updtDestRcvNwoPkg;
	}

	public void setUpdtDestRcvNwoPkg(BigDecimal updtDestRcvNwoPkg) {
		this.updtDestRcvNwoPkg = updtDestRcvNwoPkg;
	}

	public BigDecimal getUpdtDestRcvNwoCod() {
		return updtDestRcvNwoCod;
	}

	public void setUpdtDestRcvNwoCod(BigDecimal updtDestRcvNwoCod) {
		this.updtDestRcvNwoCod = updtDestRcvNwoCod;
	}

	public BigDecimal getUpdtDestRcvNwoDv() {
		return updtDestRcvNwoDv;
	}

	public void setUpdtDestRcvNwoDv(BigDecimal updtDestRcvNwoDv) {
		this.updtDestRcvNwoDv = updtDestRcvNwoDv;
	}

	public BigDecimal getUpdtDestRcvNwoOt() {
		return updtDestRcvNwoOt;
	}

	public void setUpdtDestRcvNwoOt(BigDecimal updtDestRcvNwoOt) {
		this.updtDestRcvNwoOt = updtDestRcvNwoOt;
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

}