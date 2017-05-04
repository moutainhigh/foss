package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 始发应收月报统计信息Dto
 * 
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfonoEntityTotalDto implements Serializable {

	/**
	 * 始发应收月报统计信息Dto序列号
	 */
	private static final long serialVersionUID = 8262633505754583079L;

	/**
	 * 始发应收统计总条数
	 */
	private Long mvrNrfonoEntityTotalRows;

	/**
	 * 到达部门名称
	 */
	private String destOrgName;

	/**
	 * 签收时现付已收款金额_公布价运费
	 */
	private BigDecimal podoCashCollectedFrt;

	/**
	 * 签收时现付已收款金额_接货费
	 */
	private BigDecimal podoCashCollectedPup;

	/**
	 * 签收时现付已收款金额_送货费
	 */
	private BigDecimal podoCashCollectedDel;

	/**
	 * 签收时现付已收款金额_包装费
	 */
	private BigDecimal podoCashCollectedPkg;

	/**
	 * 签收时现付已收款金额_代收货款手续费
	 */
	private BigDecimal podoCashCollectedCod;

	/**
	 * 签收时现付已收款金额_保价费
	 */
	private BigDecimal podoCashCollectedDv;

	/**
	 * 签收时现付已收款金额_其它费用
	 */
	private BigDecimal podoCashCollectedOt;

	/**
	 * 签收时始发应收已核销金额_公布价运费
	 */
	private BigDecimal podoOrigRcvWoFrt;

	/**
	 * 签收时始发应收已核销金额_接货费
	 */
	private BigDecimal podoOrigRcvWoPup;

	/**
	 * 签收时始发应收已核销金额_送货费
	 */
	private BigDecimal podoOrigRcvWoDel;

	/**
	 * 签收时始发应收已核销金额_包装费
	 */
	private BigDecimal podoOrigRcvWoPkg;

	/**
	 * 签收时始发应收已核销金额_代收货款手续费
	 */
	private BigDecimal podoOrigRcvWoCod;

	/**
	 * 签收时始发应收已核销金额_保价费
	 */
	private BigDecimal podoOrigRcvWoDv;

	/**
	 * 签收时始发应收已核销金额_其它费用
	 */
	private BigDecimal podoOrigRcvWoOt;

	/**
	 * 签收时始发应收未核销金额_公布价运费
	 */
	private BigDecimal podoOrigRcvNwoFrt;

	/**
	 * 签收时始发应收未核销金额_接货费
	 */
	private BigDecimal podoOrigRcvNwoPup;

	/**
	 * 签收时始发应收未核销金额_送货费
	 */
	private BigDecimal podoOrigRcvNwoDel;

	/**
	 * 签收时始发应收未核销金额_包装费
	 */
	private BigDecimal podoOrigRcvNwoPkg;

	/**
	 * 签收时始发应收未核销金额_代收货款手续费
	 */
	private BigDecimal podoOrigRcvNwoCod;

	/**
	 * 签收时始发应收未核销金额_保价费
	 */
	private BigDecimal podoOrigRcvNwoDv;

	/**
	 * 签收时始发应收未核销金额_其它费用
	 */
	private BigDecimal podoOrigRcvNwoOt;

	/**
	 * 签收时到达应收已核销金额_公布价运费
	 */
	private BigDecimal podoDestRcvWoFrt;

	/**
	 * 签收时到达应收已核销金额_接货费
	 */
	private BigDecimal podoDestRcvWoPup;

	/**
	 * 签收时到达应收已核销金额_送货费
	 */
	private BigDecimal podoDestRcvWoDel;

	/**
	 * 签收时到达应收已核销金额_包装费
	 */
	private BigDecimal podoDestRcvWoPkg;

	/**
	 * 签收时到达应收已核销金额_代收货款手续费
	 */
	private BigDecimal podoDestRcvWoCod;

	/**
	 * 签收时到达应收已核销金额_保价费
	 */
	private BigDecimal podoDestRcvWoDv;

	/**
	 * 签收时到达应收已核销金额_其它费用
	 */
	private BigDecimal podoDestRcvWoOt;

	/**
	 * 签收时到达应收未核销金额_公布价运费
	 */
	private BigDecimal podoDestRcvNwoFrt;

	/**
	 * 签收时到达应收未核销金额_接货费
	 */
	private BigDecimal podoDestRcvNwoPup;

	/**
	 * 签收时到达应收未核销金额_送货费
	 */
	private BigDecimal podoDestRcvNwoDel;

	/**
	 * 签收时到达应收未核销金额_包装费
	 */
	private BigDecimal podoDestRcvNwoPkg;

	/**
	 * 签收时到达应收未核销金额_代收货款手续费
	 */
	private BigDecimal podoDestRcvNwoCod;

	/**
	 * 签收时到达应收未核销金额_保价费
	 */
	private BigDecimal podoDestRcvNwoDv;

	/**
	 * 签收时到达应收未核销金额_其它费用
	 */
	private BigDecimal podoDestRcvNwoOt;

	/**
	 * 反签收时现付已收款金额_公布价运费
	 */
	private BigDecimal updoCashCollectedFrt;

	/**
	 * 反签收时现付已收款金额_接货费
	 */
	private BigDecimal updoCashCollectedPup;

	/**
	 * 反签收时现付已收款金额_送货费
	 */
	private BigDecimal updoCashCollectedDel;

	/**
	 * 反签收时现付已收款金额_包装费
	 */
	private BigDecimal updoCashCollectedPkg;

	/**
	 * 反签收时现付已收款金额_代收货款手续费
	 */
	private BigDecimal updoCashCollectedCod;

	/**
	 * 反签收时现付已收款金额_保价费
	 */
	private BigDecimal updoCashCollectedDv;

	/**
	 * 反签收时现付已收款金额_其它费用
	 */
	private BigDecimal updoCashCollectedOt;

	/**
	 * 反签收时始发应收已核销金额_公布价运费
	 */
	private BigDecimal updoOrigRcvWoFrt;

	/**
	 * 反签收时始发应收已核销金额_接货费
	 */
	private BigDecimal updoOrigRcvWoPup;

	/**
	 * 反签收时始发应收已核销金额_送货费
	 */
	private BigDecimal updoOrigRcvWoDel;

	/**
	 * 反签收时始发应收已核销金额_包装费
	 */
	private BigDecimal updoOrigRcvWoPkg;

	/**
	 * 反签收时始发应收已核销金额_代收货款手续费
	 */
	private BigDecimal updoOrigRcvWoCod;

	/**
	 * 反签收时始发应收已核销金额_保价费
	 */
	private BigDecimal updoOrigRcvWoDv;

	/**
	 * 反签收时始发应收已核销金额_其它费用
	 */
	private BigDecimal updoOrigRcvWoOt;

	/**
	 * 反签收时始发应收未核销金额_公布价运费
	 */
	private BigDecimal updoOrigRcvNwoFrt;

	/**
	 * 反签收时始发应收未核销金额_接货费
	 */
	private BigDecimal updoOrigRcvNwoPup;

	/**
	 * 反签收时始发应收未核销金额_送货费
	 */
	private BigDecimal updoOrigRcvNwoDel;

	/**
	 * 反签收时始发应收未核销金额_包装费
	 */
	private BigDecimal updoOrigRcvNwoPkg;

	/**
	 * 反签收时始发应收未核销金额_代收货款手续费
	 */
	private BigDecimal updoOrigRcvNwoCod;

	/**
	 * 反签收时始发应收未核销金额_保价费
	 */
	private BigDecimal updoOrigRcvNwoDv;

	/**
	 * 反签收时始发应收未核销金额_其它费用
	 */
	private BigDecimal updoOrigRcvNwoOt;

	/**
	 * 反签收时到达应收已核销金额_公布价运费
	 */
	private BigDecimal updoDestRcvWoFrt;

	/**
	 * 反签收时到达应收已核销金额_接货费
	 */
	private BigDecimal updoDestRcvWoPup;

	/**
	 * 反签收时到达应收已核销金额_送货费
	 */
	private BigDecimal updoDestRcvWoDel;

	/**
	 * 反签收时到达应收已核销金额_包装费
	 */
	private BigDecimal updoDestRcvWoPkg;

	/**
	 * 反签收时到达应收已核销金额_代收货款手续费
	 */
	private BigDecimal updoDestRcvWoCod;

	/**
	 * 反签收时到达应收已核销金额_保价费
	 */
	private BigDecimal updoDestRcvWoDv;

	/**
	 * 反签收时到达应收已核销金额_其它费用
	 */
	private BigDecimal updoDestRcvWoOt;

	/**
	 * 反签收时到达应收未核销金额_公布价运费
	 */
	private BigDecimal updoDestRcvNwoFrt;

	/**
	 * 反签收时到达应收未核销金额_接货费
	 */
	private BigDecimal updoDestRcvNwoPup;

	/**
	 * 反签收时到达应收未核销金额_送货费
	 */
	private BigDecimal updoDestRcvNwoDel;

	/**
	 * 反签收时到达应收未核销金额_包装费
	 */
	private BigDecimal updoDestRcvNwoPkg;

	/**
	 * 反签收时到达应收未核销金额_代收货款手续费
	 */
	private BigDecimal updoDestRcvNwoCod;

	/**
	 * 反签收时到达应收未核销金额_保价费
	 */
	private BigDecimal updoDestRcvNwoDv;

	/**
	 * 反签收时到达应收未核销金额_其它费用
	 */
	private BigDecimal updoDestRcvNwoOt;

	/**
	 * 开单现金
	 */
	private BigDecimal deoCh;

	/**
	 * 开单银行卡
	 */
	private BigDecimal deoCd;

	/**
	 * 还款现金未签收
	 */
	private BigDecimal uroOrigChNpod;

	/**
	 * 还款银行未签收
	 */
	private BigDecimal uroOrigCdNpod;

	/**
	 * 还款现金已签收
	 */
	private BigDecimal uroOrigChPod;

	/**
	 * 还款银行已签收
	 */
	private BigDecimal uroOrigCdPod;

	/**
	 * 还款现金未签收
	 */
	private BigDecimal uroDestChNpod;

	/**
	 * 还款银行未签收
	 */
	private BigDecimal uroDestCdNpod;

	/**
	 * 还款现金已签收
	 */
	private BigDecimal uroDestChPod;

	/**
	 * 还款银行已签收
	 */
	private BigDecimal uroDestCdPod;

	/**
	 * 预收客户现金
	 */
	private BigDecimal custDrOch;

	/**
	 * 预收客户银行
	 */
	private BigDecimal custDrOcd;

	/**
	 * 01预收客户冲01应收到付运费未签收
	 */
	private BigDecimal custDroWoDestRcvoNpod;

	/**
	 * 02预收客户冲01应收到付运费未签收
	 */
	private BigDecimal custDrtWoDestRcvoNpod;

	/**
	 * 01预收客户冲01应收到付运费已签收
	 */
	private BigDecimal custDroWoDestRcvoPod;

	/**
	 * 02预收客户冲01应收到付运费已签收
	 */
	private BigDecimal custDrtWoDestRcvoPod;

	/**
	 * 01预收客户冲01应收始发运费未签收
	 */
	private BigDecimal custDroWoOrigRcvoNpod;

	/**
	 * 01预收客户冲02应收始发运费未签收
	 */
	private BigDecimal custDroWoOrigRcvtNpod;

	/**
	 * 02预收客户冲01应收始发运费未签收
	 */
	private BigDecimal custDrtWoOrigRcvoNpod;

	/**
	 * 01预收客户冲01应收始发运费已签收
	 */
	private BigDecimal custDroWoOrigRcvoPod;

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
	 * 应付代收货款冲01应收到付运费已签收
	 */
	private BigDecimal codPayWoDestRcvoPod;

	/**
	 * 应付代收货款冲01应收到付运费未签收
	 */
	private BigDecimal codPayWoDestRcvoNpod;

	/**
	 * 应付代收货款冲01应收始发运费已签收
	 */
	private BigDecimal codPayWoOrigRcvoPod;

	/**
	 * 应付代收货款冲01应收始发运费未签收
	 */
	private BigDecimal codPayWoOrigRcvoNpod;

	/**
	 * 应付偏线代理成本冲01应收到付运费已签收
	 */
	private BigDecimal plCostWoDestRcvoPod;

	/**
	 * 应付偏线代理成本冲01应收到付运费未签收
	 */
	private BigDecimal plCostWoDestRcvoNpod;

	/**
	 * 预收偏线代理冲01应收到付运费已签收
	 */
	private BigDecimal plDrWoDestRcvoPod;

	/**
	 * 预收偏线代理冲01应收到付运费未签收
	 */
	private BigDecimal plDrWoDestRcvoNpod;

	/**
	 * 预收空运/快递代理冲01应收到付运费已签收
	 */
	private BigDecimal alDrWoDestRcvoPod;

	/**
	 * 预收空运/快递代理冲01应收到付运费未签收
	 */
	private BigDecimal alDrWoDestRcvoNpod;

	/**
	 * 应付到达代理/快递代理成本冲01应收到付运费已签收
	 */
	private BigDecimal alpwrWoDestRcvoPod;

	/**
	 * 应付到达代理/快递代理成本冲01应收到付运费未签收
	 */
	private BigDecimal alpwrWoDestRcvoNpod;

	/**
	 * 其他应付冲01应收到付运费已签收
	 */
	private BigDecimal othPayWoDestRcvoPod;

	/**
	 * 其他应付冲01应收到付运费未签收
	 */
	private BigDecimal othPayWoDestRcvoNpod;

	/**
	 * 其他应付冲01应收到付运费已签收
	 */
	private BigDecimal popWoDroPod;

	/**
	 * 其他应付冲01应收到付运费未签收
	 */
	private BigDecimal popWoDroNpod;

	public Long getMvrNrfonoEntityTotalRows() {
		return mvrNrfonoEntityTotalRows;
	}

	public void setMvrNrfonoEntityTotalRows(Long mvrNrfonoEntityTotalRows) {
		this.mvrNrfonoEntityTotalRows = mvrNrfonoEntityTotalRows;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public BigDecimal getPodoCashCollectedFrt() {
		return podoCashCollectedFrt;
	}

	public void setPodoCashCollectedFrt(BigDecimal podoCashCollectedFrt) {
		this.podoCashCollectedFrt = podoCashCollectedFrt;
	}

	public BigDecimal getPodoCashCollectedPup() {
		return podoCashCollectedPup;
	}

	public void setPodoCashCollectedPup(BigDecimal podoCashCollectedPup) {
		this.podoCashCollectedPup = podoCashCollectedPup;
	}

	public BigDecimal getPodoCashCollectedDel() {
		return podoCashCollectedDel;
	}

	public void setPodoCashCollectedDel(BigDecimal podoCashCollectedDel) {
		this.podoCashCollectedDel = podoCashCollectedDel;
	}

	public BigDecimal getPodoCashCollectedPkg() {
		return podoCashCollectedPkg;
	}

	public void setPodoCashCollectedPkg(BigDecimal podoCashCollectedPkg) {
		this.podoCashCollectedPkg = podoCashCollectedPkg;
	}

	public BigDecimal getPodoCashCollectedCod() {
		return podoCashCollectedCod;
	}

	public void setPodoCashCollectedCod(BigDecimal podoCashCollectedCod) {
		this.podoCashCollectedCod = podoCashCollectedCod;
	}

	public BigDecimal getPodoCashCollectedDv() {
		return podoCashCollectedDv;
	}

	public void setPodoCashCollectedDv(BigDecimal podoCashCollectedDv) {
		this.podoCashCollectedDv = podoCashCollectedDv;
	}

	public BigDecimal getPodoCashCollectedOt() {
		return podoCashCollectedOt;
	}

	public void setPodoCashCollectedOt(BigDecimal podoCashCollectedOt) {
		this.podoCashCollectedOt = podoCashCollectedOt;
	}

	public BigDecimal getPodoOrigRcvWoFrt() {
		return podoOrigRcvWoFrt;
	}

	public void setPodoOrigRcvWoFrt(BigDecimal podoOrigRcvWoFrt) {
		this.podoOrigRcvWoFrt = podoOrigRcvWoFrt;
	}

	public BigDecimal getPodoOrigRcvWoPup() {
		return podoOrigRcvWoPup;
	}

	public void setPodoOrigRcvWoPup(BigDecimal podoOrigRcvWoPup) {
		this.podoOrigRcvWoPup = podoOrigRcvWoPup;
	}

	public BigDecimal getPodoOrigRcvWoDel() {
		return podoOrigRcvWoDel;
	}

	public void setPodoOrigRcvWoDel(BigDecimal podoOrigRcvWoDel) {
		this.podoOrigRcvWoDel = podoOrigRcvWoDel;
	}

	public BigDecimal getPodoOrigRcvWoPkg() {
		return podoOrigRcvWoPkg;
	}

	public void setPodoOrigRcvWoPkg(BigDecimal podoOrigRcvWoPkg) {
		this.podoOrigRcvWoPkg = podoOrigRcvWoPkg;
	}

	public BigDecimal getPodoOrigRcvWoCod() {
		return podoOrigRcvWoCod;
	}

	public void setPodoOrigRcvWoCod(BigDecimal podoOrigRcvWoCod) {
		this.podoOrigRcvWoCod = podoOrigRcvWoCod;
	}

	public BigDecimal getPodoOrigRcvWoDv() {
		return podoOrigRcvWoDv;
	}

	public void setPodoOrigRcvWoDv(BigDecimal podoOrigRcvWoDv) {
		this.podoOrigRcvWoDv = podoOrigRcvWoDv;
	}

	public BigDecimal getPodoOrigRcvWoOt() {
		return podoOrigRcvWoOt;
	}

	public void setPodoOrigRcvWoOt(BigDecimal podoOrigRcvWoOt) {
		this.podoOrigRcvWoOt = podoOrigRcvWoOt;
	}

	public BigDecimal getPodoOrigRcvNwoFrt() {
		return podoOrigRcvNwoFrt;
	}

	public void setPodoOrigRcvNwoFrt(BigDecimal podoOrigRcvNwoFrt) {
		this.podoOrigRcvNwoFrt = podoOrigRcvNwoFrt;
	}

	public BigDecimal getPodoOrigRcvNwoPup() {
		return podoOrigRcvNwoPup;
	}

	public void setPodoOrigRcvNwoPup(BigDecimal podoOrigRcvNwoPup) {
		this.podoOrigRcvNwoPup = podoOrigRcvNwoPup;
	}

	public BigDecimal getPodoOrigRcvNwoDel() {
		return podoOrigRcvNwoDel;
	}

	public void setPodoOrigRcvNwoDel(BigDecimal podoOrigRcvNwoDel) {
		this.podoOrigRcvNwoDel = podoOrigRcvNwoDel;
	}

	public BigDecimal getPodoOrigRcvNwoPkg() {
		return podoOrigRcvNwoPkg;
	}

	public void setPodoOrigRcvNwoPkg(BigDecimal podoOrigRcvNwoPkg) {
		this.podoOrigRcvNwoPkg = podoOrigRcvNwoPkg;
	}

	public BigDecimal getPodoOrigRcvNwoCod() {
		return podoOrigRcvNwoCod;
	}

	public void setPodoOrigRcvNwoCod(BigDecimal podoOrigRcvNwoCod) {
		this.podoOrigRcvNwoCod = podoOrigRcvNwoCod;
	}

	public BigDecimal getPodoOrigRcvNwoDv() {
		return podoOrigRcvNwoDv;
	}

	public void setPodoOrigRcvNwoDv(BigDecimal podoOrigRcvNwoDv) {
		this.podoOrigRcvNwoDv = podoOrigRcvNwoDv;
	}

	public BigDecimal getPodoOrigRcvNwoOt() {
		return podoOrigRcvNwoOt;
	}

	public void setPodoOrigRcvNwoOt(BigDecimal podoOrigRcvNwoOt) {
		this.podoOrigRcvNwoOt = podoOrigRcvNwoOt;
	}

	public BigDecimal getPodoDestRcvWoFrt() {
		return podoDestRcvWoFrt;
	}

	public void setPodoDestRcvWoFrt(BigDecimal podoDestRcvWoFrt) {
		this.podoDestRcvWoFrt = podoDestRcvWoFrt;
	}

	public BigDecimal getPodoDestRcvWoPup() {
		return podoDestRcvWoPup;
	}

	public void setPodoDestRcvWoPup(BigDecimal podoDestRcvWoPup) {
		this.podoDestRcvWoPup = podoDestRcvWoPup;
	}

	public BigDecimal getPodoDestRcvWoDel() {
		return podoDestRcvWoDel;
	}

	public void setPodoDestRcvWoDel(BigDecimal podoDestRcvWoDel) {
		this.podoDestRcvWoDel = podoDestRcvWoDel;
	}

	public BigDecimal getPodoDestRcvWoPkg() {
		return podoDestRcvWoPkg;
	}

	public void setPodoDestRcvWoPkg(BigDecimal podoDestRcvWoPkg) {
		this.podoDestRcvWoPkg = podoDestRcvWoPkg;
	}

	public BigDecimal getPodoDestRcvWoCod() {
		return podoDestRcvWoCod;
	}

	public void setPodoDestRcvWoCod(BigDecimal podoDestRcvWoCod) {
		this.podoDestRcvWoCod = podoDestRcvWoCod;
	}

	public BigDecimal getPodoDestRcvWoDv() {
		return podoDestRcvWoDv;
	}

	public void setPodoDestRcvWoDv(BigDecimal podoDestRcvWoDv) {
		this.podoDestRcvWoDv = podoDestRcvWoDv;
	}

	public BigDecimal getPodoDestRcvWoOt() {
		return podoDestRcvWoOt;
	}

	public void setPodoDestRcvWoOt(BigDecimal podoDestRcvWoOt) {
		this.podoDestRcvWoOt = podoDestRcvWoOt;
	}

	public BigDecimal getPodoDestRcvNwoFrt() {
		return podoDestRcvNwoFrt;
	}

	public void setPodoDestRcvNwoFrt(BigDecimal podoDestRcvNwoFrt) {
		this.podoDestRcvNwoFrt = podoDestRcvNwoFrt;
	}

	public BigDecimal getPodoDestRcvNwoPup() {
		return podoDestRcvNwoPup;
	}

	public void setPodoDestRcvNwoPup(BigDecimal podoDestRcvNwoPup) {
		this.podoDestRcvNwoPup = podoDestRcvNwoPup;
	}

	public BigDecimal getPodoDestRcvNwoDel() {
		return podoDestRcvNwoDel;
	}

	public void setPodoDestRcvNwoDel(BigDecimal podoDestRcvNwoDel) {
		this.podoDestRcvNwoDel = podoDestRcvNwoDel;
	}

	public BigDecimal getPodoDestRcvNwoPkg() {
		return podoDestRcvNwoPkg;
	}

	public void setPodoDestRcvNwoPkg(BigDecimal podoDestRcvNwoPkg) {
		this.podoDestRcvNwoPkg = podoDestRcvNwoPkg;
	}

	public BigDecimal getPodoDestRcvNwoCod() {
		return podoDestRcvNwoCod;
	}

	public void setPodoDestRcvNwoCod(BigDecimal podoDestRcvNwoCod) {
		this.podoDestRcvNwoCod = podoDestRcvNwoCod;
	}

	public BigDecimal getPodoDestRcvNwoDv() {
		return podoDestRcvNwoDv;
	}

	public void setPodoDestRcvNwoDv(BigDecimal podoDestRcvNwoDv) {
		this.podoDestRcvNwoDv = podoDestRcvNwoDv;
	}

	public BigDecimal getPodoDestRcvNwoOt() {
		return podoDestRcvNwoOt;
	}

	public void setPodoDestRcvNwoOt(BigDecimal podoDestRcvNwoOt) {
		this.podoDestRcvNwoOt = podoDestRcvNwoOt;
	}

	public BigDecimal getUpdoCashCollectedFrt() {
		return updoCashCollectedFrt;
	}

	public void setUpdoCashCollectedFrt(BigDecimal updoCashCollectedFrt) {
		this.updoCashCollectedFrt = updoCashCollectedFrt;
	}

	public BigDecimal getUpdoCashCollectedPup() {
		return updoCashCollectedPup;
	}

	public void setUpdoCashCollectedPup(BigDecimal updoCashCollectedPup) {
		this.updoCashCollectedPup = updoCashCollectedPup;
	}

	public BigDecimal getUpdoCashCollectedDel() {
		return updoCashCollectedDel;
	}

	public void setUpdoCashCollectedDel(BigDecimal updoCashCollectedDel) {
		this.updoCashCollectedDel = updoCashCollectedDel;
	}

	public BigDecimal getUpdoCashCollectedPkg() {
		return updoCashCollectedPkg;
	}

	public void setUpdoCashCollectedPkg(BigDecimal updoCashCollectedPkg) {
		this.updoCashCollectedPkg = updoCashCollectedPkg;
	}

	public BigDecimal getUpdoCashCollectedCod() {
		return updoCashCollectedCod;
	}

	public void setUpdoCashCollectedCod(BigDecimal updoCashCollectedCod) {
		this.updoCashCollectedCod = updoCashCollectedCod;
	}

	public BigDecimal getUpdoCashCollectedDv() {
		return updoCashCollectedDv;
	}

	public void setUpdoCashCollectedDv(BigDecimal updoCashCollectedDv) {
		this.updoCashCollectedDv = updoCashCollectedDv;
	}

	public BigDecimal getUpdoCashCollectedOt() {
		return updoCashCollectedOt;
	}

	public void setUpdoCashCollectedOt(BigDecimal updoCashCollectedOt) {
		this.updoCashCollectedOt = updoCashCollectedOt;
	}

	public BigDecimal getUpdoOrigRcvWoFrt() {
		return updoOrigRcvWoFrt;
	}

	public void setUpdoOrigRcvWoFrt(BigDecimal updoOrigRcvWoFrt) {
		this.updoOrigRcvWoFrt = updoOrigRcvWoFrt;
	}

	public BigDecimal getUpdoOrigRcvWoPup() {
		return updoOrigRcvWoPup;
	}

	public void setUpdoOrigRcvWoPup(BigDecimal updoOrigRcvWoPup) {
		this.updoOrigRcvWoPup = updoOrigRcvWoPup;
	}

	public BigDecimal getUpdoOrigRcvWoDel() {
		return updoOrigRcvWoDel;
	}

	public void setUpdoOrigRcvWoDel(BigDecimal updoOrigRcvWoDel) {
		this.updoOrigRcvWoDel = updoOrigRcvWoDel;
	}

	public BigDecimal getUpdoOrigRcvWoPkg() {
		return updoOrigRcvWoPkg;
	}

	public void setUpdoOrigRcvWoPkg(BigDecimal updoOrigRcvWoPkg) {
		this.updoOrigRcvWoPkg = updoOrigRcvWoPkg;
	}

	public BigDecimal getUpdoOrigRcvWoCod() {
		return updoOrigRcvWoCod;
	}

	public void setUpdoOrigRcvWoCod(BigDecimal updoOrigRcvWoCod) {
		this.updoOrigRcvWoCod = updoOrigRcvWoCod;
	}

	public BigDecimal getUpdoOrigRcvWoDv() {
		return updoOrigRcvWoDv;
	}

	public void setUpdoOrigRcvWoDv(BigDecimal updoOrigRcvWoDv) {
		this.updoOrigRcvWoDv = updoOrigRcvWoDv;
	}

	public BigDecimal getUpdoOrigRcvWoOt() {
		return updoOrigRcvWoOt;
	}

	public void setUpdoOrigRcvWoOt(BigDecimal updoOrigRcvWoOt) {
		this.updoOrigRcvWoOt = updoOrigRcvWoOt;
	}

	public BigDecimal getUpdoOrigRcvNwoFrt() {
		return updoOrigRcvNwoFrt;
	}

	public void setUpdoOrigRcvNwoFrt(BigDecimal updoOrigRcvNwoFrt) {
		this.updoOrigRcvNwoFrt = updoOrigRcvNwoFrt;
	}

	public BigDecimal getUpdoOrigRcvNwoPup() {
		return updoOrigRcvNwoPup;
	}

	public void setUpdoOrigRcvNwoPup(BigDecimal updoOrigRcvNwoPup) {
		this.updoOrigRcvNwoPup = updoOrigRcvNwoPup;
	}

	public BigDecimal getUpdoOrigRcvNwoDel() {
		return updoOrigRcvNwoDel;
	}

	public void setUpdoOrigRcvNwoDel(BigDecimal updoOrigRcvNwoDel) {
		this.updoOrigRcvNwoDel = updoOrigRcvNwoDel;
	}

	public BigDecimal getUpdoOrigRcvNwoPkg() {
		return updoOrigRcvNwoPkg;
	}

	public void setUpdoOrigRcvNwoPkg(BigDecimal updoOrigRcvNwoPkg) {
		this.updoOrigRcvNwoPkg = updoOrigRcvNwoPkg;
	}

	public BigDecimal getUpdoOrigRcvNwoCod() {
		return updoOrigRcvNwoCod;
	}

	public void setUpdoOrigRcvNwoCod(BigDecimal updoOrigRcvNwoCod) {
		this.updoOrigRcvNwoCod = updoOrigRcvNwoCod;
	}

	public BigDecimal getUpdoOrigRcvNwoDv() {
		return updoOrigRcvNwoDv;
	}

	public void setUpdoOrigRcvNwoDv(BigDecimal updoOrigRcvNwoDv) {
		this.updoOrigRcvNwoDv = updoOrigRcvNwoDv;
	}

	public BigDecimal getUpdoOrigRcvNwoOt() {
		return updoOrigRcvNwoOt;
	}

	public void setUpdoOrigRcvNwoOt(BigDecimal updoOrigRcvNwoOt) {
		this.updoOrigRcvNwoOt = updoOrigRcvNwoOt;
	}

	public BigDecimal getUpdoDestRcvWoFrt() {
		return updoDestRcvWoFrt;
	}

	public void setUpdoDestRcvWoFrt(BigDecimal updoDestRcvWoFrt) {
		this.updoDestRcvWoFrt = updoDestRcvWoFrt;
	}

	public BigDecimal getUpdoDestRcvWoPup() {
		return updoDestRcvWoPup;
	}

	public void setUpdoDestRcvWoPup(BigDecimal updoDestRcvWoPup) {
		this.updoDestRcvWoPup = updoDestRcvWoPup;
	}

	public BigDecimal getUpdoDestRcvWoDel() {
		return updoDestRcvWoDel;
	}

	public void setUpdoDestRcvWoDel(BigDecimal updoDestRcvWoDel) {
		this.updoDestRcvWoDel = updoDestRcvWoDel;
	}

	public BigDecimal getUpdoDestRcvWoPkg() {
		return updoDestRcvWoPkg;
	}

	public void setUpdoDestRcvWoPkg(BigDecimal updoDestRcvWoPkg) {
		this.updoDestRcvWoPkg = updoDestRcvWoPkg;
	}

	public BigDecimal getUpdoDestRcvWoCod() {
		return updoDestRcvWoCod;
	}

	public void setUpdoDestRcvWoCod(BigDecimal updoDestRcvWoCod) {
		this.updoDestRcvWoCod = updoDestRcvWoCod;
	}

	public BigDecimal getUpdoDestRcvWoDv() {
		return updoDestRcvWoDv;
	}

	public void setUpdoDestRcvWoDv(BigDecimal updoDestRcvWoDv) {
		this.updoDestRcvWoDv = updoDestRcvWoDv;
	}

	public BigDecimal getUpdoDestRcvWoOt() {
		return updoDestRcvWoOt;
	}

	public void setUpdoDestRcvWoOt(BigDecimal updoDestRcvWoOt) {
		this.updoDestRcvWoOt = updoDestRcvWoOt;
	}

	public BigDecimal getUpdoDestRcvNwoFrt() {
		return updoDestRcvNwoFrt;
	}

	public void setUpdoDestRcvNwoFrt(BigDecimal updoDestRcvNwoFrt) {
		this.updoDestRcvNwoFrt = updoDestRcvNwoFrt;
	}

	public BigDecimal getUpdoDestRcvNwoPup() {
		return updoDestRcvNwoPup;
	}

	public void setUpdoDestRcvNwoPup(BigDecimal updoDestRcvNwoPup) {
		this.updoDestRcvNwoPup = updoDestRcvNwoPup;
	}

	public BigDecimal getUpdoDestRcvNwoDel() {
		return updoDestRcvNwoDel;
	}

	public void setUpdoDestRcvNwoDel(BigDecimal updoDestRcvNwoDel) {
		this.updoDestRcvNwoDel = updoDestRcvNwoDel;
	}

	public BigDecimal getUpdoDestRcvNwoPkg() {
		return updoDestRcvNwoPkg;
	}

	public void setUpdoDestRcvNwoPkg(BigDecimal updoDestRcvNwoPkg) {
		this.updoDestRcvNwoPkg = updoDestRcvNwoPkg;
	}

	public BigDecimal getUpdoDestRcvNwoCod() {
		return updoDestRcvNwoCod;
	}

	public void setUpdoDestRcvNwoCod(BigDecimal updoDestRcvNwoCod) {
		this.updoDestRcvNwoCod = updoDestRcvNwoCod;
	}

	public BigDecimal getUpdoDestRcvNwoDv() {
		return updoDestRcvNwoDv;
	}

	public void setUpdoDestRcvNwoDv(BigDecimal updoDestRcvNwoDv) {
		this.updoDestRcvNwoDv = updoDestRcvNwoDv;
	}

	public BigDecimal getUpdoDestRcvNwoOt() {
		return updoDestRcvNwoOt;
	}

	public void setUpdoDestRcvNwoOt(BigDecimal updoDestRcvNwoOt) {
		this.updoDestRcvNwoOt = updoDestRcvNwoOt;
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

	public BigDecimal getUroDestChNpod() {
		return uroDestChNpod;
	}

	public void setUroDestChNpod(BigDecimal uroDestChNpod) {
		this.uroDestChNpod = uroDestChNpod;
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

	public BigDecimal getUroDestCdPod() {
		return uroDestCdPod;
	}

	public void setUroDestCdPod(BigDecimal uroDestCdPod) {
		this.uroDestCdPod = uroDestCdPod;
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

	public BigDecimal getCodPayWoDestRcvoPod() {
		return codPayWoDestRcvoPod;
	}

	public void setCodPayWoDestRcvoPod(BigDecimal codPayWoDestRcvoPod) {
		this.codPayWoDestRcvoPod = codPayWoDestRcvoPod;
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

	public BigDecimal getCodPayWoOrigRcvoNpod() {
		return codPayWoOrigRcvoNpod;
	}

	public void setCodPayWoOrigRcvoNpod(BigDecimal codPayWoOrigRcvoNpod) {
		this.codPayWoOrigRcvoNpod = codPayWoOrigRcvoNpod;
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
