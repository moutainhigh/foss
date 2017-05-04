package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 始发专线到达往来月报表
 * 
 * @author luoyizhu@gmail.com
 * 
 *         2013-12-6 下午2:35:49
 */
public class MvrNrfiEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6410688763637940967L;

	private long count;

	private String id;

	private String period;

	private String productCode;

	private String customerCode;

	private String customerName;

	private String orgCode;

	private String orgName;

	private String orgType;

	private String orgUnifiedCode;

	private Date voucherBeginTime;

	private Date voucherEndTime;

	private String invoiceMark;

	private BigDecimal codPayWoOthRcvt;

	private BigDecimal claimOrigoWoOrigRcvtPod;

	private BigDecimal claimOrigoWoOrigRcvtNpod;

	private BigDecimal claimOrigoPayApply;

	private BigDecimal claimDesttIncome;

	private BigDecimal claimDestoWoDestRcvoPod;

	private BigDecimal claimDestoWoDestRcvoNpod;

	private BigDecimal claimDestoWoDestRcvtPod;

	private BigDecimal claimDesttWoDestRcvtPod;

	private BigDecimal claimDesttWoDestRcvtNpod;

	private BigDecimal orChPbio;

	private BigDecimal orChUrRcvo;

	private BigDecimal orCdUrRcvo;

	private BigDecimal orClaimPayoWoRcvt;

	private BigDecimal orCustDroWoRcvt;

	private BigDecimal orCustDrtWoRcvo;

	private BigDecimal rdOrigoWoOrigRcvtPod;

	private BigDecimal uroOrigCdNpod;

	private BigDecimal uroDestChNpod;

	private BigDecimal uroDestCdPod;

	private BigDecimal urtDestCdNpod;

	private BigDecimal custDrOch;

	private BigDecimal custDroWoDestRcvoPod;

	private BigDecimal custDrtWoOrigRcvoNpod;

	private BigDecimal custDroPayApply;

	private BigDecimal custDrtWoDestRcvtNpod;

	private BigDecimal codPayWoDestRcvoPod;

	private BigDecimal codPayWoOrigRcvoNpod;

	private BigDecimal codPayWoOrigRcvtNpod;

	private BigDecimal codPayWoOthRcvo;

	private BigDecimal claimOrigtOrigRcvoPod;

	private BigDecimal claimOrigtWoOrigRcvoNpod;

	private BigDecimal claimDestoIncome;

	private BigDecimal claimDesttWoDestRcvoPod;

	private BigDecimal claimDesttWoDestRcvoNpod;

	private BigDecimal claimDestoWoDestRcvtNpod;

	private BigDecimal claimDestoPayApply;

	private BigDecimal orCdPbio;

	private BigDecimal orClaimPaytWoRcvo;

	private BigDecimal rdOrigoPayApply;

	private BigDecimal rdOrigtWoOrigRcvoPod;

	private BigDecimal rdDestoIncome;

	private BigDecimal rdDestoPayApply;

	private BigDecimal rdDesttWoDestRcvoPod;

	private BigDecimal rdDestoDestRcvtPod;

	private BigDecimal rdDestoWoDestRcvtNpod;

	private BigDecimal sfoPayApply;

	private BigDecimal deoCh;

	private BigDecimal deoCd;

	private BigDecimal uroOrigChNpod;

	private BigDecimal uroOrigChPod;

	private BigDecimal uroOrigCdPod;

	private BigDecimal uroDestCdNpod;

	private BigDecimal uroDestChPod;

	private BigDecimal urtDestChNpod;

	private BigDecimal urtDestChPod;

	private BigDecimal urtDestCdPod;

	private BigDecimal custDrOcd;

	private BigDecimal custDroWoDestRcvoNpod;

	private BigDecimal custDrtWoDestRcvoNpod;

	private BigDecimal custDrtWoDestRcvoPod;

	private BigDecimal custDroWoOrigRcvtNpod;

	private BigDecimal custDroWoOrigRcvtPod;

	private BigDecimal custDrtWoOrigRcvoPod;

	private BigDecimal custDroWoDestRcvtNpod;

	private BigDecimal custDroWoDestRcvtPod;

	private BigDecimal custDrtWoDestRcvtPod;

	private BigDecimal codPayWoDestRcvoNpod;

	private BigDecimal codPayWoOrigRcvoPod;

	private BigDecimal codPayWoDestRcvtPod;

	private BigDecimal codPayWoDestRcvtNpod;

	private BigDecimal codPayWoOrigRcvtPod;

	private BigDecimal codUrChNpod;

	private BigDecimal codUrCdNpod;

	private BigDecimal rdOrigoWoOrigRcvtNpod;

	private BigDecimal rdOrigtWoOrigRcvoNpod;

	private BigDecimal rdDesttIncome;

	private BigDecimal rdDestoDestRcvoPod;

	private BigDecimal rdDestoWoDestRcvoNpod;

	private BigDecimal rdDesttWoDestRcvoNpod;

	private BigDecimal rdDesttWoDestRcvtPod;

	private BigDecimal rdDesttWoDestRcvtNpod;

	private BigDecimal cpoOrigPayApply;

	private BigDecimal cpoDestPayApply;

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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgUnifiedCode() {
		return orgUnifiedCode;
	}

	public void setOrgUnifiedCode(String orgUnifiedCode) {
		this.orgUnifiedCode = orgUnifiedCode;
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

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public BigDecimal getCodPayWoOthRcvt() {
		return codPayWoOthRcvt;
	}

	public void setCodPayWoOthRcvt(BigDecimal codPayWoOthRcvt) {
		this.codPayWoOthRcvt = codPayWoOthRcvt;
	}

	public BigDecimal getClaimOrigoWoOrigRcvtPod() {
		return claimOrigoWoOrigRcvtPod;
	}

	public void setClaimOrigoWoOrigRcvtPod(BigDecimal claimOrigoWoOrigRcvtPod) {
		this.claimOrigoWoOrigRcvtPod = claimOrigoWoOrigRcvtPod;
	}

	public BigDecimal getClaimOrigoWoOrigRcvtNpod() {
		return claimOrigoWoOrigRcvtNpod;
	}

	public void setClaimOrigoWoOrigRcvtNpod(BigDecimal claimOrigoWoOrigRcvtNpod) {
		this.claimOrigoWoOrigRcvtNpod = claimOrigoWoOrigRcvtNpod;
	}

	public BigDecimal getClaimOrigoPayApply() {
		return claimOrigoPayApply;
	}

	public void setClaimOrigoPayApply(BigDecimal claimOrigoPayApply) {
		this.claimOrigoPayApply = claimOrigoPayApply;
	}

	public BigDecimal getClaimDesttIncome() {
		return claimDesttIncome;
	}

	public void setClaimDesttIncome(BigDecimal claimDesttIncome) {
		this.claimDesttIncome = claimDesttIncome;
	}

	public BigDecimal getClaimDestoWoDestRcvoPod() {
		return claimDestoWoDestRcvoPod;
	}

	public void setClaimDestoWoDestRcvoPod(BigDecimal claimDestoWoDestRcvoPod) {
		this.claimDestoWoDestRcvoPod = claimDestoWoDestRcvoPod;
	}

	public BigDecimal getClaimDestoWoDestRcvoNpod() {
		return claimDestoWoDestRcvoNpod;
	}

	public void setClaimDestoWoDestRcvoNpod(BigDecimal claimDestoWoDestRcvoNpod) {
		this.claimDestoWoDestRcvoNpod = claimDestoWoDestRcvoNpod;
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

	public BigDecimal getClaimDesttWoDestRcvtNpod() {
		return claimDesttWoDestRcvtNpod;
	}

	public void setClaimDesttWoDestRcvtNpod(BigDecimal claimDesttWoDestRcvtNpod) {
		this.claimDesttWoDestRcvtNpod = claimDesttWoDestRcvtNpod;
	}

	public BigDecimal getOrChPbio() {
		return orChPbio;
	}

	public void setOrChPbio(BigDecimal orChPbio) {
		this.orChPbio = orChPbio;
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

	public BigDecimal getOrClaimPayoWoRcvt() {
		return orClaimPayoWoRcvt;
	}

	public void setOrClaimPayoWoRcvt(BigDecimal orClaimPayoWoRcvt) {
		this.orClaimPayoWoRcvt = orClaimPayoWoRcvt;
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

	public BigDecimal getRdOrigoWoOrigRcvtPod() {
		return rdOrigoWoOrigRcvtPod;
	}

	public void setRdOrigoWoOrigRcvtPod(BigDecimal rdOrigoWoOrigRcvtPod) {
		this.rdOrigoWoOrigRcvtPod = rdOrigoWoOrigRcvtPod;
	}

	public BigDecimal getUroOrigCdNpod() {
		return uroOrigCdNpod;
	}

	public void setUroOrigCdNpod(BigDecimal uroOrigCdNpod) {
		this.uroOrigCdNpod = uroOrigCdNpod;
	}

	public BigDecimal getUroDestChNpod() {
		return uroDestChNpod;
	}

	public void setUroDestChNpod(BigDecimal uroDestChNpod) {
		this.uroDestChNpod = uroDestChNpod;
	}

	public BigDecimal getUroDestCdPod() {
		return uroDestCdPod;
	}

	public void setUroDestCdPod(BigDecimal uroDestCdPod) {
		this.uroDestCdPod = uroDestCdPod;
	}

	public BigDecimal getUrtDestCdNpod() {
		return urtDestCdNpod;
	}

	public void setUrtDestCdNpod(BigDecimal urtDestCdNpod) {
		this.urtDestCdNpod = urtDestCdNpod;
	}

	public BigDecimal getCustDrOch() {
		return custDrOch;
	}

	public void setCustDrOch(BigDecimal custDrOch) {
		this.custDrOch = custDrOch;
	}

	public BigDecimal getCustDroWoDestRcvoPod() {
		return custDroWoDestRcvoPod;
	}

	public void setCustDroWoDestRcvoPod(BigDecimal custDroWoDestRcvoPod) {
		this.custDroWoDestRcvoPod = custDroWoDestRcvoPod;
	}

	public BigDecimal getCustDrtWoOrigRcvoNpod() {
		return custDrtWoOrigRcvoNpod;
	}

	public void setCustDrtWoOrigRcvoNpod(BigDecimal custDrtWoOrigRcvoNpod) {
		this.custDrtWoOrigRcvoNpod = custDrtWoOrigRcvoNpod;
	}

	public BigDecimal getCustDroPayApply() {
		return custDroPayApply;
	}

	public void setCustDroPayApply(BigDecimal custDroPayApply) {
		this.custDroPayApply = custDroPayApply;
	}

	public BigDecimal getCustDrtWoDestRcvtNpod() {
		return custDrtWoDestRcvtNpod;
	}

	public void setCustDrtWoDestRcvtNpod(BigDecimal custDrtWoDestRcvtNpod) {
		this.custDrtWoDestRcvtNpod = custDrtWoDestRcvtNpod;
	}

	public BigDecimal getCodPayWoDestRcvoPod() {
		return codPayWoDestRcvoPod;
	}

	public void setCodPayWoDestRcvoPod(BigDecimal codPayWoDestRcvoPod) {
		this.codPayWoDestRcvoPod = codPayWoDestRcvoPod;
	}

	public BigDecimal getCodPayWoOrigRcvoNpod() {
		return codPayWoOrigRcvoNpod;
	}

	public void setCodPayWoOrigRcvoNpod(BigDecimal codPayWoOrigRcvoNpod) {
		this.codPayWoOrigRcvoNpod = codPayWoOrigRcvoNpod;
	}

	public BigDecimal getCodPayWoOrigRcvtNpod() {
		return codPayWoOrigRcvtNpod;
	}

	public void setCodPayWoOrigRcvtNpod(BigDecimal codPayWoOrigRcvtNpod) {
		this.codPayWoOrigRcvtNpod = codPayWoOrigRcvtNpod;
	}

	public BigDecimal getCodPayWoOthRcvo() {
		return codPayWoOthRcvo;
	}

	public void setCodPayWoOthRcvo(BigDecimal codPayWoOthRcvo) {
		this.codPayWoOthRcvo = codPayWoOthRcvo;
	}

	public BigDecimal getClaimOrigtOrigRcvoPod() {
		return claimOrigtOrigRcvoPod;
	}

	public void setClaimOrigtOrigRcvoPod(BigDecimal claimOrigtOrigRcvoPod) {
		this.claimOrigtOrigRcvoPod = claimOrigtOrigRcvoPod;
	}

	public BigDecimal getClaimOrigtWoOrigRcvoNpod() {
		return claimOrigtWoOrigRcvoNpod;
	}

	public void setClaimOrigtWoOrigRcvoNpod(BigDecimal claimOrigtWoOrigRcvoNpod) {
		this.claimOrigtWoOrigRcvoNpod = claimOrigtWoOrigRcvoNpod;
	}

	public BigDecimal getClaimDestoIncome() {
		return claimDestoIncome;
	}

	public void setClaimDestoIncome(BigDecimal claimDestoIncome) {
		this.claimDestoIncome = claimDestoIncome;
	}

	public BigDecimal getClaimDesttWoDestRcvoPod() {
		return claimDesttWoDestRcvoPod;
	}

	public void setClaimDesttWoDestRcvoPod(BigDecimal claimDesttWoDestRcvoPod) {
		this.claimDesttWoDestRcvoPod = claimDesttWoDestRcvoPod;
	}

	public BigDecimal getClaimDesttWoDestRcvoNpod() {
		return claimDesttWoDestRcvoNpod;
	}

	public void setClaimDesttWoDestRcvoNpod(BigDecimal claimDesttWoDestRcvoNpod) {
		this.claimDesttWoDestRcvoNpod = claimDesttWoDestRcvoNpod;
	}

	public BigDecimal getClaimDestoWoDestRcvtNpod() {
		return claimDestoWoDestRcvtNpod;
	}

	public void setClaimDestoWoDestRcvtNpod(BigDecimal claimDestoWoDestRcvtNpod) {
		this.claimDestoWoDestRcvtNpod = claimDestoWoDestRcvtNpod;
	}

	public BigDecimal getClaimDestoPayApply() {
		return claimDestoPayApply;
	}

	public void setClaimDestoPayApply(BigDecimal claimDestoPayApply) {
		this.claimDestoPayApply = claimDestoPayApply;
	}

	public BigDecimal getOrCdPbio() {
		return orCdPbio;
	}

	public void setOrCdPbio(BigDecimal orCdPbio) {
		this.orCdPbio = orCdPbio;
	}

	public BigDecimal getOrClaimPaytWoRcvo() {
		return orClaimPaytWoRcvo;
	}

	public void setOrClaimPaytWoRcvo(BigDecimal orClaimPaytWoRcvo) {
		this.orClaimPaytWoRcvo = orClaimPaytWoRcvo;
	}

	public BigDecimal getRdOrigoPayApply() {
		return rdOrigoPayApply;
	}

	public void setRdOrigoPayApply(BigDecimal rdOrigoPayApply) {
		this.rdOrigoPayApply = rdOrigoPayApply;
	}

	public BigDecimal getRdOrigtWoOrigRcvoPod() {
		return rdOrigtWoOrigRcvoPod;
	}

	public void setRdOrigtWoOrigRcvoPod(BigDecimal rdOrigtWoOrigRcvoPod) {
		this.rdOrigtWoOrigRcvoPod = rdOrigtWoOrigRcvoPod;
	}

	public BigDecimal getRdDestoIncome() {
		return rdDestoIncome;
	}

	public void setRdDestoIncome(BigDecimal rdDestoIncome) {
		this.rdDestoIncome = rdDestoIncome;
	}

	public BigDecimal getRdDestoPayApply() {
		return rdDestoPayApply;
	}

	public void setRdDestoPayApply(BigDecimal rdDestoPayApply) {
		this.rdDestoPayApply = rdDestoPayApply;
	}

	public BigDecimal getRdDesttWoDestRcvoPod() {
		return rdDesttWoDestRcvoPod;
	}

	public void setRdDesttWoDestRcvoPod(BigDecimal rdDesttWoDestRcvoPod) {
		this.rdDesttWoDestRcvoPod = rdDesttWoDestRcvoPod;
	}

	public BigDecimal getRdDestoDestRcvtPod() {
		return rdDestoDestRcvtPod;
	}

	public void setRdDestoDestRcvtPod(BigDecimal rdDestoDestRcvtPod) {
		this.rdDestoDestRcvtPod = rdDestoDestRcvtPod;
	}

	public BigDecimal getRdDestoWoDestRcvtNpod() {
		return rdDestoWoDestRcvtNpod;
	}

	public void setRdDestoWoDestRcvtNpod(BigDecimal rdDestoWoDestRcvtNpod) {
		this.rdDestoWoDestRcvtNpod = rdDestoWoDestRcvtNpod;
	}

	public BigDecimal getSfoPayApply() {
		return sfoPayApply;
	}

	public void setSfoPayApply(BigDecimal sfoPayApply) {
		this.sfoPayApply = sfoPayApply;
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

	public BigDecimal getUroOrigChNpod() {
		return uroOrigChNpod;
	}

	public void setUroOrigChNpod(BigDecimal uroOrigChNpod) {
		this.uroOrigChNpod = uroOrigChNpod;
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

	public BigDecimal getUroDestCdNpod() {
		return uroDestCdNpod;
	}

	public void setUroDestCdNpod(BigDecimal uroDestCdNpod) {
		this.uroDestCdNpod = uroDestCdNpod;
	}

	public BigDecimal getUroDestChPod() {
		return uroDestChPod;
	}

	public void setUroDestChPod(BigDecimal uroDestChPod) {
		this.uroDestChPod = uroDestChPod;
	}

	public BigDecimal getUrtDestChNpod() {
		return urtDestChNpod;
	}

	public void setUrtDestChNpod(BigDecimal urtDestChNpod) {
		this.urtDestChNpod = urtDestChNpod;
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

	public BigDecimal getCustDrtWoDestRcvoPod() {
		return custDrtWoDestRcvoPod;
	}

	public void setCustDrtWoDestRcvoPod(BigDecimal custDrtWoDestRcvoPod) {
		this.custDrtWoDestRcvoPod = custDrtWoDestRcvoPod;
	}

	public BigDecimal getCustDroWoOrigRcvtNpod() {
		return custDroWoOrigRcvtNpod;
	}

	public void setCustDroWoOrigRcvtNpod(BigDecimal custDroWoOrigRcvtNpod) {
		this.custDroWoOrigRcvtNpod = custDroWoOrigRcvtNpod;
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

	public BigDecimal getCustDroWoDestRcvtNpod() {
		return custDroWoDestRcvtNpod;
	}

	public void setCustDroWoDestRcvtNpod(BigDecimal custDroWoDestRcvtNpod) {
		this.custDroWoDestRcvtNpod = custDroWoDestRcvtNpod;
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

	public BigDecimal getCodPayWoDestRcvoNpod() {
		return codPayWoDestRcvoNpod;
	}

	public void setCodPayWoDestRcvoNpod(BigDecimal codPayWoDestRcvoNpod) {
		this.codPayWoDestRcvoNpod = codPayWoDestRcvoNpod;
	}

	public BigDecimal getCodPayWoOrigRcvoPod() {
		return codPayWoOrigRcvoPod;
	}

	public void setCodPayWoOrigRcvoPod(BigDecimal codPayWoOrigRcvoPod) {
		this.codPayWoOrigRcvoPod = codPayWoOrigRcvoPod;
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

	public BigDecimal getCodUrChNpod() {
		return codUrChNpod;
	}

	public void setCodUrChNpod(BigDecimal codUrChNpod) {
		this.codUrChNpod = codUrChNpod;
	}

	public BigDecimal getCodUrCdNpod() {
		return codUrCdNpod;
	}

	public void setCodUrCdNpod(BigDecimal codUrCdNpod) {
		this.codUrCdNpod = codUrCdNpod;
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

	public BigDecimal getRdDesttIncome() {
		return rdDesttIncome;
	}

	public void setRdDesttIncome(BigDecimal rdDesttIncome) {
		this.rdDesttIncome = rdDesttIncome;
	}

	public BigDecimal getRdDestoDestRcvoPod() {
		return rdDestoDestRcvoPod;
	}

	public void setRdDestoDestRcvoPod(BigDecimal rdDestoDestRcvoPod) {
		this.rdDestoDestRcvoPod = rdDestoDestRcvoPod;
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

	public BigDecimal getRdDesttWoDestRcvtPod() {
		return rdDesttWoDestRcvtPod;
	}

	public void setRdDesttWoDestRcvtPod(BigDecimal rdDesttWoDestRcvtPod) {
		this.rdDesttWoDestRcvtPod = rdDesttWoDestRcvtPod;
	}

	public BigDecimal getRdDesttWoDestRcvtNpod() {
		return rdDesttWoDestRcvtNpod;
	}

	public void setRdDesttWoDestRcvtNpod(BigDecimal rdDesttWoDestRcvtNpod) {
		this.rdDesttWoDestRcvtNpod = rdDesttWoDestRcvtNpod;
	}

	public BigDecimal getCpoOrigPayApply() {
		return cpoOrigPayApply;
	}

	public void setCpoOrigPayApply(BigDecimal cpoOrigPayApply) {
		this.cpoOrigPayApply = cpoOrigPayApply;
	}

	public BigDecimal getCpoDestPayApply() {
		return cpoDestPayApply;
	}

	public void setCpoDestPayApply(BigDecimal cpoDestPayApply) {
		this.cpoDestPayApply = cpoDestPayApply;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}