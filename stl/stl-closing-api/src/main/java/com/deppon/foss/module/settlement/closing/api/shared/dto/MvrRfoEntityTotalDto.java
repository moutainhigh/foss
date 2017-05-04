package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 始发应收月报统计信息Dto
 * 
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午3:01:07
 */
public class MvrRfoEntityTotalDto implements Serializable {

	/**
	 * 始发应收月报统计信息Dto序列号
	 */
	private static final long serialVersionUID = 8262633505754583079L;

	/**
	 * 始发应收统计总条数
	 */
	private Long mvrRfoEntityTotalRows;

	/**
	 * 其他费用
	 */
	private BigDecimal podDestRcvWoOt;

	/**
	 * 公布价运费
	 */
	private BigDecimal podDestRcvNwoFrt;

	/**
	 * 接货费
	 */
	private BigDecimal podDestRcvNwoPup;

	/**
	 * 送货费
	 */
	private BigDecimal podDestRcvNwoDel;

	/**
	 * 包装费
	 */
	private BigDecimal podDestRcvNwoPkg;

	/**
	 * 保价费
	 */
	private BigDecimal podDestRcvNwoDv;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal podDestRcvNwoCod;

	/**
	 * 其他费用
	 */
	private BigDecimal podDestRcvNwoOt;

	/**
	 * 公布价运费
	 */
	private BigDecimal updCashCollectedFrt;

	/**
	 * 接货费
	 */
	private BigDecimal updCashCollectedPup;

	/**
	 * 送货费
	 */
	private BigDecimal updCashCollectedDel;

	/**
	 * 包装费
	 */
	private BigDecimal updCashCollectedPkg;

	/**
	 * 保价费
	 */
	private BigDecimal updCashCollectedDv;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal updCashCollectedCod;

	/**
	 * 其他费用
	 */
	private BigDecimal updCashCollectedOt;

	/**
	 * 公布价运费
	 */
	private BigDecimal updOrigRcvWoFrt;

	/**
	 * 接货费
	 */
	private BigDecimal updOrigRcvWoPup;

	/**
	 * 送货费
	 */
	private BigDecimal updOrigRcvWoDel;

	/**
	 * 包装费
	 */
	private BigDecimal updOrigRcvWoPkg;

	/**
	 * 保价费
	 */
	private BigDecimal updOrigRcvWoDv;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal updOrigRcvWoCod;

	/**
	 * 其他费用
	 */
	private BigDecimal updOrigRcvWoOt;

	/**
	 * 公布价运费
	 */
	private BigDecimal updOrigRcvNwoFrt;

	/**
	 * 接货费
	 */
	private BigDecimal updOrigRcvNwoPup;

	/**
	 * 送货费
	 */
	private BigDecimal updOrigRcvNwoDel;

	/**
	 * 包装费
	 */
	private BigDecimal updOrigRcvNwoPkg;

	/**
	 * 保价费
	 */
	private BigDecimal updOrigRcvNwoDv;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal updOrigRcvNwoCod;

	/**
	 * 其他费用
	 */
	private BigDecimal updOrigRcvNwoOt;

	/**
	 * 公布价运费
	 */
	private BigDecimal updDestRcvWoFrt;

	/**
	 * 接货费
	 */
	private BigDecimal updDestRcvWoPup;

	/**
	 * 送货费
	 */
	private BigDecimal updDestRcvWoDel;

	/**
	 * 包装费
	 */
	private BigDecimal updDestRcvWoPkg;

	/**
	 * 保价费
	 */
	private BigDecimal updDestRcvWoDv;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal updDestRcvWoCod;

	/**
	 * 其他费用
	 */
	private BigDecimal updDestRcvWoOt;

	/**
	 * 公布价运费
	 */
	private BigDecimal updDestRcvNwoFrt;

	/**
	 * 接货费
	 */
	private BigDecimal updDestRcvNwoPup;

	/**
	 * 送货费
	 */
	private BigDecimal updDestRcvNwoDel;

	/**
	 * 包装费
	 */
	private BigDecimal updDestRcvNwoPkg;

	/**
	 * 保价费
	 */
	private BigDecimal updDestRcvNwoDv;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal updDestRcvNwoCod;

	/**
	 * 其他费用
	 */
	private BigDecimal updDestRcvNwoOt;

	/**
	 * 理赔冲收入
	 */
	private BigDecimal claimOrigWoIncome;

	/**
	 * 理赔入成本
	 */
	private BigDecimal claimOrigCost;

	/**
	 * 理赔冲始发应收已签收
	 */
	private BigDecimal claimWoOrigRcvPod;

	/**
	 * 理赔冲始发应收未签收
	 */
	private BigDecimal claimWoOrigRcvNpod;

	/**
	 * 理赔付款申请
	 */
	private BigDecimal claimOrigPayApply;

	/**
	 * 理赔冲收入
	 */
	private BigDecimal claimDestWoIncome;

	/**
	 * 理赔冲到达应收已签收
	 */
	private BigDecimal claimWoDestRcvPod;

	/**
	 * 理赔冲到达应收未签收
	 */
	private BigDecimal claimWoDestRcvNpod;

	/**
	 * 装卸费付款申请
	 */
	private BigDecimal sfPayApply;

	/**
	 * 应付代收货款冲应收到付运费已签收
	 */
	private BigDecimal codDestRcvPod;

	/**
	 * 应付代收货款冲应收到付运费未签收
	 */
	private BigDecimal codDestRcvNpod;

	/**
	 * 应付代收货款冲应收始发运费已签收
	 */
	private BigDecimal codOrigRcvPod;

	/**
	 * 应付代收货款冲应收始发运费未签收
	 */
	private BigDecimal codOrigRcvNpod;

	/**
	 * 预收客户现金
	 */
	private BigDecimal custDrCh;

	/**
	 * 预收客户银行
	 */
	private BigDecimal custDrCd;

	/**
	 * 预收客户冲应收到付运费未签收
	 */
	private BigDecimal custDrDestRcvNpod;

	/**
	 * 预收客户冲应收到付运费已签收
	 */
	private BigDecimal custDrDestRcvPod;

	/**
	 * 预收客户冲应收始发运费未签收
	 */
	private BigDecimal custDrOrigRcvNpod;

	/**
	 * 预收客户冲应收始发运费已签收
	 */
	private BigDecimal custDrOrigRcvPod;

	/**
	 * 始发退预收付款申请
	 */
	private BigDecimal custDrOrigPayApply;

	/**
	 * 应收始发运费已签收
	 */
	private BigDecimal exOrigRcvPod;

	/**
	 * 应收到付运费已签收
	 */
	private BigDecimal exDestRcvPod;

	/**
	 * 坏账冲应收始发运费已签收
	 */
	private BigDecimal bdOrigRcvPod;

	/**
	 * 坏账冲应收到付运费已签收
	 */
	private BigDecimal bdDestRcvPod;

	/**
	 * 小票现金之事故赔款
	 */
	private BigDecimal orChAc;

	/**
	 * 小票现金之变卖废品收入
	 */
	private BigDecimal orChSi;

	/**
	 * 小票现金之客户多付运费或盘点长款金额
	 */
	private BigDecimal orChOpay;

	/**
	 * 小票现金之其他
	 */
	private BigDecimal orChOther;

	/**
	 * 小票现金主营业务收入
	 */
	private BigDecimal orChMbi;

	/**
	 * 小票银行之事故赔款
	 */
	private BigDecimal orCdAc;

	/**
	 * 小票银行之收银员卡利息
	 */
	private BigDecimal orCdBankIt;

	/**
	 * 小票银行之客户多付运费或盘点长款金额
	 */
	private BigDecimal orCdOpay;

	/**
	 * 小票银行之其他
	 */
	private BigDecimal orCdOther;

	/**
	 * 小票银行主营业务收入
	 */
	private BigDecimal orCdMbi;

	/**
	 * 小票应收主营业务收入
	 */
	private BigDecimal orRcvMbi;

	/**
	 * 还款现金冲小票应收
	 */
	private BigDecimal orRcvWoUrCh;

	/**
	 * 还款银行冲小票应收
	 */
	private BigDecimal orRcvWoUrCd;

	/**
	 * 应付代收货款冲小票应收
	 */
	private BigDecimal orRcvWoCodPay;

	/**
	 * 应付理赔冲小票应收
	 */
	private BigDecimal orRcvWoClaimPay;

	/**
	 * 预收客户冲小票应收
	 */
	private BigDecimal orRcvWoCustDr;

	/**
	 * 坏账之保险理赔冲小票应收
	 */
	private BigDecimal orRcvWoBdDebt;

	/**
	 * 坏账之坏账损失冲小票应收
	 */
	private BigDecimal orRcvWoBdIncome;

	/**
	 * 开单为月结临时欠款网上支付未核销
	 */
	private BigDecimal acCtdtolNwo;

	/**
	 * 开单为月结临时欠款网上支付已核销
	 */
	private BigDecimal acCtdtolWo;

	/**
	 * 开单为现金银行卡
	 */
	private BigDecimal acChcd;

	/**
	 * 退运费冲收入
	 */
	private BigDecimal rdOrigWoIncome;

	/**
	 * 退运费入成本
	 */
	private BigDecimal rdOrigCost;

	/**
	 * 退运费付款申请
	 */
	private BigDecimal rdOrigPayApply;

	/**
	 * 退运费冲收入
	 */
	private BigDecimal rdDestWoIncome;

	/**
	 * 始发服务补救付款申请
	 */
	private BigDecimal cnOrigPayApply;

	/**
	 * 应付偏线代理成本冲应收到付运费已签收
	 */
	private BigDecimal plCostWoDestRcvPod;

	/**
	 * 应付偏线代理成本冲应收到付运费未签收
	 */
	private BigDecimal plCostWoDestRcvNpod;

	/**
	 * 预收偏线代理冲应收到付运费已签收
	 */
	private BigDecimal plDrWoDestRcvPod;

	/**
	 * 预收偏线代理冲应收到付运费未签收
	 */
	private BigDecimal plDrWoDestRcvNpod;

	/**
	 * 预收空运代理冲应收到付运费已签收
	 */
	private BigDecimal airDrDestRcvPod;

	/**
	 * 预收空运代理冲应收到付运费未签收
	 */
	private BigDecimal airDrDestRcvNpod;

	/**
	 * 应付到达代理成本冲应收到付运费已签收
	 */
	private BigDecimal airPrAgencyWoDestRcvPod;

	/**
	 * 应付到达代理成本冲应收到付运费未签收
	 */
	private BigDecimal airPrAgencyWoDestRcvNpod;

	/**
	 * 其他应付冲应收到付运费已签收
	 */
	private BigDecimal airPrOtWoDestRcvPod;

	/**
	 * 其他应付冲应收到付运费未签收
	 */
	private BigDecimal airPrOthWoDestRcvNpod;

	/**
	 * 开单现金
	 */
	private BigDecimal deCh;

	/**
	 * 开单银行卡
	 */
	private BigDecimal deCd;

	/**
	 * 还款现金未签收
	 */
	private BigDecimal urOrigChNpod;

	/**
	 * 还款银行未签收
	 */
	private BigDecimal urOrigCdNpod;

	/**
	 * 还款现金已签收
	 */
	private BigDecimal urOrigChPod;

	/**
	 * 还款银行已签收
	 */
	private BigDecimal urOrigCdPod;

	/**
	 * 还款现金未签收
	 */
	private BigDecimal urDestChNpod;

	/**
	 * 还款银行未签收
	 */
	private BigDecimal urDestCdNpod;

	/**
	 * 还款现金已签收
	 */
	private BigDecimal urDestChPod;

	/**
	 * 还款银行已签收
	 */
	private BigDecimal urDestCdPod;

	/**
	 * 公布价运费
	 */
	private BigDecimal podCashCollectedFrt;

	/**
	 * 接货费
	 */
	private BigDecimal podCashCollectedPup;

	/**
	 * 送货费
	 */
	private BigDecimal podCashCollectedDel;

	/**
	 * 包装费
	 */
	private BigDecimal podCashCollectedPkg;

	/**
	 * 保价费
	 */
	private BigDecimal podCashCollectedDv;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal podCashCollectedCod;

	/**
	 * 其他费用
	 */
	private BigDecimal podCashCollectedOt;

	/**
	 * 公布价运费
	 */
	private BigDecimal podOrigRcvWoFrt;

	/**
	 * 接货费
	 */
	private BigDecimal podOrigRcvWoPup;

	/**
	 * 送货费
	 */
	private BigDecimal podOrigRcvWoDel;

	/**
	 * 包装费
	 */
	private BigDecimal podOrigRcvWoPkg;

	/**
	 * 保价费
	 */
	private BigDecimal podOrigRcvWoDv;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal podOrigRcvWoCod;

	/**
	 * 其他费用
	 */
	private BigDecimal podOrigRcvWoOt;

	/**
	 * 公布价运费
	 */
	private BigDecimal podOrigRcvNwoFrt;

	/**
	 * 接货费
	 */
	private BigDecimal podOrigRcvNwoPup;

	/**
	 * 送货费
	 */
	private BigDecimal podOrigRcvNwoDel;

	/**
	 * 包装费
	 */
	private BigDecimal podOrigRcvNwoPkg;

	/**
	 * 保价费
	 */
	private BigDecimal podOrigRcvNwoDv;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal podOrigRcvNwoCod;

	/**
	 * 其他费用
	 */
	private BigDecimal podOrigRcvNwoOt;

	/**
	 * 公布价运费
	 */
	private BigDecimal podDestRcvWoFrt;

	/**
	 * 接货费
	 */
	private BigDecimal podDestRcvWoPup;

	/**
	 * 送货费
	 */
	private BigDecimal podDestRcvWoDel;

	/**
	 * 包装费
	 */
	private BigDecimal podDestRcvWoPkg;

	/**
	 * 保价费
	 */
	private BigDecimal podDestRcvWoDv;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal podDestRcvWoCod;
	
	/**
	 * 退运费冲始发应收已签收
	 */
	private BigDecimal rdWoOrigRcvPod;

	/**
	 * 退运费冲始发应收未签收
	 */
	private BigDecimal rdWoOrigRcvNpod;

	/**
	 * 退运费冲到达应收已签收
	 */
	private BigDecimal rdWoDestRcvPod;

	/**
	 * 退运费冲到达应收未签收
	 */
	private BigDecimal rdWoDestRcvNpod;

	public Long getMvrRfoEntityTotalRows(){
		return mvrRfoEntityTotalRows;
	}

	public void setMvrRfoEntityTotalRows(Long mvrRfoEntityTotalRows){
		this.mvrRfoEntityTotalRows = mvrRfoEntityTotalRows;
	}

	public BigDecimal getPodDestRcvWoOt(){
		return podDestRcvWoOt;
	}

	public void setPodDestRcvWoOt(BigDecimal podDestRcvWoOt){
		this.podDestRcvWoOt = podDestRcvWoOt;
	}

	public BigDecimal getPodDestRcvNwoFrt()	{
		return podDestRcvNwoFrt;
	}

	public void setPodDestRcvNwoFrt(BigDecimal podDestRcvNwoFrt){
		this.podDestRcvNwoFrt = podDestRcvNwoFrt;
	}

	public BigDecimal getPodDestRcvNwoPup() {
		return podDestRcvNwoPup;
	}

	public void setPodDestRcvNwoPup(BigDecimal podDestRcvNwoPup) {
		this.podDestRcvNwoPup = podDestRcvNwoPup;
	}

	public BigDecimal getPodDestRcvNwoDel()	{
		return podDestRcvNwoDel;
	}

	public void setPodDestRcvNwoDel(BigDecimal podDestRcvNwoDel){
		this.podDestRcvNwoDel = podDestRcvNwoDel;
	}

	public BigDecimal getPodDestRcvNwoPkg(){
		return podDestRcvNwoPkg;
	}

	public void setPodDestRcvNwoPkg(BigDecimal podDestRcvNwoPkg){
		this.podDestRcvNwoPkg = podDestRcvNwoPkg;
	}

	public BigDecimal getPodDestRcvNwoDv(){
		return podDestRcvNwoDv;
	}

	public void setPodDestRcvNwoDv(BigDecimal podDestRcvNwoDv){
		this.podDestRcvNwoDv = podDestRcvNwoDv;
	}

	public BigDecimal getPodDestRcvNwoCod()	{
		return podDestRcvNwoCod;
	}

	public void setPodDestRcvNwoCod(BigDecimal podDestRcvNwoCod){
		this.podDestRcvNwoCod = podDestRcvNwoCod;
	}

	public BigDecimal getPodDestRcvNwoOt(){
		return podDestRcvNwoOt;
	}

	public void setPodDestRcvNwoOt(BigDecimal podDestRcvNwoOt){
		this.podDestRcvNwoOt = podDestRcvNwoOt;
	}

	public BigDecimal getUpdCashCollectedFrt(){
		return updCashCollectedFrt;
	}

	public void setUpdCashCollectedFrt(BigDecimal updCashCollectedFrt){
		this.updCashCollectedFrt = updCashCollectedFrt;
	}

	public BigDecimal getUpdCashCollectedPup(){
		return updCashCollectedPup;
	}

	public void setUpdCashCollectedPup(BigDecimal updCashCollectedPup){
		this.updCashCollectedPup = updCashCollectedPup;
	}

	public BigDecimal getUpdCashCollectedDel(){
		return updCashCollectedDel;
	}

	public void setUpdCashCollectedDel(BigDecimal updCashCollectedDel){
		this.updCashCollectedDel = updCashCollectedDel;
	}

	public BigDecimal getUpdCashCollectedPkg(){
		return updCashCollectedPkg;
	}

	public void setUpdCashCollectedPkg(BigDecimal updCashCollectedPkg){
		this.updCashCollectedPkg = updCashCollectedPkg;
	}

	public BigDecimal getUpdCashCollectedDv(){
		return updCashCollectedDv;
	}

	public void setUpdCashCollectedDv(BigDecimal updCashCollectedDv){
		this.updCashCollectedDv = updCashCollectedDv;
	}

	public BigDecimal getUpdCashCollectedCod(){
		return updCashCollectedCod;
	}

	public void setUpdCashCollectedCod(BigDecimal updCashCollectedCod){
		this.updCashCollectedCod = updCashCollectedCod;
	}

	public BigDecimal getUpdCashCollectedOt(){
		return updCashCollectedOt;
	}

	public void setUpdCashCollectedOt(BigDecimal updCashCollectedOt){
		this.updCashCollectedOt = updCashCollectedOt;
	}

	public BigDecimal getUpdOrigRcvWoFrt(){
		return updOrigRcvWoFrt;
	}

	public void setUpdOrigRcvWoFrt(BigDecimal updOrigRcvWoFrt){
		this.updOrigRcvWoFrt = updOrigRcvWoFrt;
	}

	public BigDecimal getUpdOrigRcvWoPup(){
		return updOrigRcvWoPup;
	}

	public void setUpdOrigRcvWoPup(BigDecimal updOrigRcvWoPup){
		this.updOrigRcvWoPup = updOrigRcvWoPup;
	}

	public BigDecimal getUpdOrigRcvWoDel(){
		return updOrigRcvWoDel;
	}

	public void setUpdOrigRcvWoDel(BigDecimal updOrigRcvWoDel){
		this.updOrigRcvWoDel = updOrigRcvWoDel;
	}

	public BigDecimal getUpdOrigRcvWoPkg(){
		return updOrigRcvWoPkg;
	}

	public void setUpdOrigRcvWoPkg(BigDecimal updOrigRcvWoPkg){
		this.updOrigRcvWoPkg = updOrigRcvWoPkg;
	}

	public BigDecimal getUpdOrigRcvWoDv(){
		return updOrigRcvWoDv;
	}

	public void setUpdOrigRcvWoDv(BigDecimal updOrigRcvWoDv){
		this.updOrigRcvWoDv = updOrigRcvWoDv;
	}

	public BigDecimal getUpdOrigRcvWoCod(){
		return updOrigRcvWoCod;
	}

	public void setUpdOrigRcvWoCod(BigDecimal updOrigRcvWoCod){
		this.updOrigRcvWoCod = updOrigRcvWoCod;
	}

	public BigDecimal getUpdOrigRcvWoOt(){
		return updOrigRcvWoOt;
	}

	public void setUpdOrigRcvWoOt(BigDecimal updOrigRcvWoOt){
		this.updOrigRcvWoOt = updOrigRcvWoOt;
	}

	public BigDecimal getUpdOrigRcvNwoFrt(){
		return updOrigRcvNwoFrt;
	}

	public void setUpdOrigRcvNwoFrt(BigDecimal updOrigRcvNwoFrt){
		this.updOrigRcvNwoFrt = updOrigRcvNwoFrt;
	}

	public BigDecimal getUpdOrigRcvNwoPup(){
		return updOrigRcvNwoPup;
	}

	public void setUpdOrigRcvNwoPup(BigDecimal updOrigRcvNwoPup){
		this.updOrigRcvNwoPup = updOrigRcvNwoPup;
	}

	public BigDecimal getUpdOrigRcvNwoDel(){
		return updOrigRcvNwoDel;
	}

	public void setUpdOrigRcvNwoDel(BigDecimal updOrigRcvNwoDel){	
		this.updOrigRcvNwoDel = updOrigRcvNwoDel;
	}

	public BigDecimal getUpdOrigRcvNwoPkg(){
		return updOrigRcvNwoPkg;
	}

	public void setUpdOrigRcvNwoPkg(BigDecimal updOrigRcvNwoPkg){
		this.updOrigRcvNwoPkg = updOrigRcvNwoPkg;
	}

	public BigDecimal getUpdOrigRcvNwoDv(){
		return updOrigRcvNwoDv;
	}

	public void setUpdOrigRcvNwoDv(BigDecimal updOrigRcvNwoDv){
		this.updOrigRcvNwoDv = updOrigRcvNwoDv;
	}

	public BigDecimal getUpdOrigRcvNwoCod(){
		return updOrigRcvNwoCod;
	}

	public void setUpdOrigRcvNwoCod(BigDecimal updOrigRcvNwoCod){
		this.updOrigRcvNwoCod = updOrigRcvNwoCod;
	}

	public BigDecimal getUpdOrigRcvNwoOt(){
		return updOrigRcvNwoOt;
	}

	public void setUpdOrigRcvNwoOt(BigDecimal updOrigRcvNwoOt){
		this.updOrigRcvNwoOt = updOrigRcvNwoOt;
	}

	public BigDecimal getUpdDestRcvWoFrt(){
		return updDestRcvWoFrt;
	}

	public void setUpdDestRcvWoFrt(BigDecimal updDestRcvWoFrt){
		this.updDestRcvWoFrt = updDestRcvWoFrt;
	}

	public BigDecimal getUpdDestRcvWoPup(){
		return updDestRcvWoPup;
	}

	public void setUpdDestRcvWoPup(BigDecimal updDestRcvWoPup){
		this.updDestRcvWoPup = updDestRcvWoPup;
	}

	public BigDecimal getUpdDestRcvWoDel(){
		return updDestRcvWoDel;
	}

	public void setUpdDestRcvWoDel(BigDecimal updDestRcvWoDel){
		this.updDestRcvWoDel = updDestRcvWoDel;
	}

	public BigDecimal getUpdDestRcvWoPkg(){
		return updDestRcvWoPkg;
	}

	public void setUpdDestRcvWoPkg(BigDecimal updDestRcvWoPkg){
		this.updDestRcvWoPkg = updDestRcvWoPkg;
	}

	public BigDecimal getUpdDestRcvWoDv(){
		return updDestRcvWoDv;
	}

	public void setUpdDestRcvWoDv(BigDecimal updDestRcvWoDv){
		this.updDestRcvWoDv = updDestRcvWoDv;
	}

	public BigDecimal getUpdDestRcvWoCod(){
		return updDestRcvWoCod;
	}

	public void setUpdDestRcvWoCod(BigDecimal updDestRcvWoCod){
		this.updDestRcvWoCod = updDestRcvWoCod;
	}

	public BigDecimal getUpdDestRcvWoOt(){
		return updDestRcvWoOt;
	}

	public void setUpdDestRcvWoOt(BigDecimal updDestRcvWoOt){
		this.updDestRcvWoOt = updDestRcvWoOt;
	}

	public BigDecimal getUpdDestRcvNwoFrt(){
		return updDestRcvNwoFrt;
	}

	public void setUpdDestRcvNwoFrt(BigDecimal updDestRcvNwoFrt){
		this.updDestRcvNwoFrt = updDestRcvNwoFrt;
	}

	public BigDecimal getUpdDestRcvNwoPup(){
		return updDestRcvNwoPup;
	}

	public void setUpdDestRcvNwoPup(BigDecimal updDestRcvNwoPup){
		this.updDestRcvNwoPup = updDestRcvNwoPup;
	}

	public BigDecimal getUpdDestRcvNwoDel(){
		return updDestRcvNwoDel;
	}

	public void setUpdDestRcvNwoDel(BigDecimal updDestRcvNwoDel){
		this.updDestRcvNwoDel = updDestRcvNwoDel;
	}

	public BigDecimal getUpdDestRcvNwoPkg(){
		return updDestRcvNwoPkg;
	}

	public void setUpdDestRcvNwoPkg(BigDecimal updDestRcvNwoPkg){
		this.updDestRcvNwoPkg = updDestRcvNwoPkg;
	}

	public BigDecimal getUpdDestRcvNwoDv(){
		return updDestRcvNwoDv;
	}

	public void setUpdDestRcvNwoDv(BigDecimal updDestRcvNwoDv){
		this.updDestRcvNwoDv = updDestRcvNwoDv;
	}

	public BigDecimal getUpdDestRcvNwoCod(){
		return updDestRcvNwoCod;
	}

	public void setUpdDestRcvNwoCod(BigDecimal updDestRcvNwoCod){
		this.updDestRcvNwoCod = updDestRcvNwoCod;
	}

	public BigDecimal getUpdDestRcvNwoOt(){
		return updDestRcvNwoOt;
	}

	public void setUpdDestRcvNwoOt(BigDecimal updDestRcvNwoOt){
		this.updDestRcvNwoOt = updDestRcvNwoOt;
	}

	public BigDecimal getClaimOrigWoIncome(){
		return claimOrigWoIncome;
	}

	public void setClaimOrigWoIncome(BigDecimal claimOrigWoIncome){
		this.claimOrigWoIncome = claimOrigWoIncome;
	}

	public BigDecimal getClaimOrigCost(){
		return claimOrigCost;
	}

	public void setClaimOrigCost(BigDecimal claimOrigCost){
		this.claimOrigCost = claimOrigCost;
	}

	public BigDecimal getClaimWoOrigRcvPod(){
		return claimWoOrigRcvPod;
	}

	public void setClaimWoOrigRcvPod(BigDecimal claimWoOrigRcvPod){
		this.claimWoOrigRcvPod = claimWoOrigRcvPod;
	}

	public BigDecimal getClaimWoOrigRcvNpod(){
		return claimWoOrigRcvNpod;
	}

	public void setClaimWoOrigRcvNpod(BigDecimal claimWoOrigRcvNpod){
		this.claimWoOrigRcvNpod = claimWoOrigRcvNpod;
	}

	public BigDecimal getClaimOrigPayApply(){
		return claimOrigPayApply;
	}

	public void setClaimOrigPayApply(BigDecimal claimOrigPayApply){
		this.claimOrigPayApply = claimOrigPayApply;
	}

	public BigDecimal getClaimDestWoIncome(){
		return claimDestWoIncome;
	}

	public void setClaimDestWoIncome(BigDecimal claimDestWoIncome){
		this.claimDestWoIncome = claimDestWoIncome;
	}

	public BigDecimal getClaimWoDestRcvPod(){
		return claimWoDestRcvPod;
	}

	public void setClaimWoDestRcvPod(BigDecimal claimWoDestRcvPod){
		this.claimWoDestRcvPod = claimWoDestRcvPod;
	}

	public BigDecimal getClaimWoDestRcvNpod(){
		return claimWoDestRcvNpod;
	}

	public void setClaimWoDestRcvNpod(BigDecimal claimWoDestRcvNpod){
		this.claimWoDestRcvNpod = claimWoDestRcvNpod;
	}

	public BigDecimal getSfPayApply(){
		return sfPayApply;
	}

	public void setSfPayApply(BigDecimal sfPayApply){
		this.sfPayApply = sfPayApply;
	}

	public BigDecimal getCodDestRcvPod(){
		return codDestRcvPod;
	}

	public void setCodDestRcvPod(BigDecimal codDestRcvPod){
		this.codDestRcvPod = codDestRcvPod;
	}

	public BigDecimal getCodDestRcvNpod(){
		return codDestRcvNpod;
	}

	public void setCodDestRcvNpod(BigDecimal codDestRcvNpod){
		this.codDestRcvNpod = codDestRcvNpod;
	}

	public BigDecimal getCodOrigRcvPod(){
		return codOrigRcvPod;
	}

	public void setCodOrigRcvPod(BigDecimal codOrigRcvPod){
		this.codOrigRcvPod = codOrigRcvPod;
	}

	public BigDecimal getCodOrigRcvNpod(){
		return codOrigRcvNpod;
	}

	public void setCodOrigRcvNpod(BigDecimal codOrigRcvNpod){
		this.codOrigRcvNpod = codOrigRcvNpod;
	}

	public BigDecimal getCustDrCh(){
		return custDrCh;
	}

	public void setCustDrCh(BigDecimal custDrCh){
		this.custDrCh = custDrCh;
	}

	public BigDecimal getCustDrCd(){
		return custDrCd;
	}

	public void setCustDrCd(BigDecimal custDrCd){
		this.custDrCd = custDrCd;
	}

	public BigDecimal getCustDrDestRcvNpod(){
		return custDrDestRcvNpod;
	}

	public void setCustDrDestRcvNpod(BigDecimal custDrDestRcvNpod){
		this.custDrDestRcvNpod = custDrDestRcvNpod;
	}

	public BigDecimal getCustDrDestRcvPod(){
		return custDrDestRcvPod;
	}

	public void setCustDrDestRcvPod(BigDecimal custDrDestRcvPod){
		this.custDrDestRcvPod = custDrDestRcvPod;
	}

	public BigDecimal getCustDrOrigRcvNpod(){
		return custDrOrigRcvNpod;
	}

	public void setCustDrOrigRcvNpod(BigDecimal custDrOrigRcvNpod){
		this.custDrOrigRcvNpod = custDrOrigRcvNpod;
	}

	public BigDecimal getCustDrOrigRcvPod(){
		return custDrOrigRcvPod;
	}

	public void setCustDrOrigRcvPod(BigDecimal custDrOrigRcvPod){
		this.custDrOrigRcvPod = custDrOrigRcvPod;
	}

	public BigDecimal getCustDrOrigPayApply(){
		return custDrOrigPayApply;
	}

	public void setCustDrOrigPayApply(BigDecimal custDrOrigPayApply){
		this.custDrOrigPayApply = custDrOrigPayApply;
	}

	public BigDecimal getExOrigRcvPod(){
		return exOrigRcvPod;
	}

	public void setExOrigRcvPod(BigDecimal exOrigRcvPod){
		this.exOrigRcvPod = exOrigRcvPod;
	}

	public BigDecimal getExDestRcvPod(){
		return exDestRcvPod;
	}

	public void setExDestRcvPod(BigDecimal exDestRcvPod){
		this.exDestRcvPod = exDestRcvPod;
	}

	public BigDecimal getBdOrigRcvPod(){
		return bdOrigRcvPod;
	}

	public void setBdOrigRcvPod(BigDecimal bdOrigRcvPod){
		this.bdOrigRcvPod = bdOrigRcvPod;
	}

	public BigDecimal getBdDestRcvPod(){
		return bdDestRcvPod;
	}

	public void setBdDestRcvPod(BigDecimal bdDestRcvPod){
		this.bdDestRcvPod = bdDestRcvPod;
	}

	public BigDecimal getOrChAc(){
		return orChAc;
	}

	public void setOrChAc(BigDecimal orChAc){
		this.orChAc = orChAc;
	}

	public BigDecimal getOrChSi(){
		return orChSi;
	}

	public void setOrChSi(BigDecimal orChSi){
		this.orChSi = orChSi;
	}

	public BigDecimal getOrChOpay(){
		return orChOpay;
	}

	public void setOrChOpay(BigDecimal orChOpay){
		this.orChOpay = orChOpay;
	}

	public BigDecimal getOrChOther(){
		return orChOther;
	}

	public void setOrChOther(BigDecimal orChOther){
		this.orChOther = orChOther;
	}

	public BigDecimal getOrChMbi(){
		return orChMbi;
	}

	public void setOrChMbi(BigDecimal orChMbi){
		this.orChMbi = orChMbi;
	}

	public BigDecimal getOrCdAc(){
		return orCdAc;
	}

	public void setOrCdAc(BigDecimal orCdAc){
		this.orCdAc = orCdAc;
	}

	public BigDecimal getOrCdBankIt(){
		return orCdBankIt;
	}

	public void setOrCdBankIt(BigDecimal orCdBankIt){
		this.orCdBankIt = orCdBankIt;
	}

	public BigDecimal getOrCdOpay(){
		return orCdOpay;
	}

	public void setOrCdOpay(BigDecimal orCdOpay){
		this.orCdOpay = orCdOpay;
	}

	public BigDecimal getOrCdOther(){
		return orCdOther;
	}

	public void setOrCdOther(BigDecimal orCdOther){
		this.orCdOther = orCdOther;
	}

	public BigDecimal getOrCdMbi(){
		return orCdMbi;
	}

	public void setOrCdMbi(BigDecimal orCdMbi){
		this.orCdMbi = orCdMbi;
	}

	public BigDecimal getOrRcvMbi(){
		return orRcvMbi;
	}

	public void setOrRcvMbi(BigDecimal orRcvMbi){
		this.orRcvMbi = orRcvMbi;
	}

	public BigDecimal getOrRcvWoUrCh(){
		return orRcvWoUrCh;
	}

	public void setOrRcvWoUrCh(BigDecimal orRcvWoUrCh){
		this.orRcvWoUrCh = orRcvWoUrCh;
	}

	public BigDecimal getOrRcvWoUrCd(){
		return orRcvWoUrCd;
	}

	public void setOrRcvWoUrCd(BigDecimal orRcvWoUrCd){
		this.orRcvWoUrCd = orRcvWoUrCd;
	}

	public BigDecimal getOrRcvWoCodPay(){
		return orRcvWoCodPay;
	}

	public void setOrRcvWoCodPay(BigDecimal orRcvWoCodPay){
		this.orRcvWoCodPay = orRcvWoCodPay;
	}

	public BigDecimal getOrRcvWoClaimPay(){
		return orRcvWoClaimPay;
	}

	public void setOrRcvWoClaimPay(BigDecimal orRcvWoClaimPay){
		this.orRcvWoClaimPay = orRcvWoClaimPay;
	}

	public BigDecimal getOrRcvWoCustDr(){
		return orRcvWoCustDr;
	}

	public void setOrRcvWoCustDr(BigDecimal orRcvWoCustDr){
		this.orRcvWoCustDr = orRcvWoCustDr;
	}

	public BigDecimal getOrRcvWoBdDebt(){
		return orRcvWoBdDebt;
	}

	public void setOrRcvWoBdDebt(BigDecimal orRcvWoBdDebt){
		this.orRcvWoBdDebt = orRcvWoBdDebt;
	}

	public BigDecimal getOrRcvWoBdIncome(){
		return orRcvWoBdIncome;
	}

	public void setOrRcvWoBdIncome(BigDecimal orRcvWoBdIncome){
		this.orRcvWoBdIncome = orRcvWoBdIncome;
	}

	public BigDecimal getAcCtdtolNwo(){
		return acCtdtolNwo;
	}

	public void setAcCtdtolNwo(BigDecimal acCtdtolNwo){
		this.acCtdtolNwo = acCtdtolNwo;
	}

	public BigDecimal getAcCtdtolWo(){
		return acCtdtolWo;
	}

	public void setAcCtdtolWo(BigDecimal acCtdtolWo){
		this.acCtdtolWo = acCtdtolWo;
	}

	public BigDecimal getAcChcd(){
		return acChcd;
	}

	public void setAcChcd(BigDecimal acChcd){
		this.acChcd = acChcd;
	}

	public BigDecimal getRdOrigWoIncome(){
		return rdOrigWoIncome;
	}

	public void setRdOrigWoIncome(BigDecimal rdOrigWoIncome){
		this.rdOrigWoIncome = rdOrigWoIncome;
	}

	public BigDecimal getRdOrigCost(){
		return rdOrigCost;
	}

	public void setRdOrigCost(BigDecimal rdOrigCost){
		this.rdOrigCost = rdOrigCost;
	}

	public BigDecimal getRdOrigPayApply(){
		return rdOrigPayApply;
	}

	public void setRdOrigPayApply(BigDecimal rdOrigPayApply){
		this.rdOrigPayApply = rdOrigPayApply;
	}

	public BigDecimal getRdDestWoIncome(){
		return rdDestWoIncome;
	}

	public void setRdDestWoIncome(BigDecimal rdDestWoIncome){
		this.rdDestWoIncome = rdDestWoIncome;
	}

	public BigDecimal getCnOrigPayApply(){
		return cnOrigPayApply;
	}

	public void setCnOrigPayApply(BigDecimal cnOrigPayApply){
		this.cnOrigPayApply = cnOrigPayApply;
	}

	public BigDecimal getPlCostWoDestRcvPod(){
		return plCostWoDestRcvPod;
	}

	public void setPlCostWoDestRcvPod(BigDecimal plCostWoDestRcvPod){
		this.plCostWoDestRcvPod = plCostWoDestRcvPod;
	}

	public BigDecimal getPlCostWoDestRcvNpod(){
		return plCostWoDestRcvNpod;
	}

	public void setPlCostWoDestRcvNpod(BigDecimal plCostWoDestRcvNpod){
		this.plCostWoDestRcvNpod = plCostWoDestRcvNpod;
	}

	public BigDecimal getPlDrWoDestRcvPod(){
		return plDrWoDestRcvPod;
	}

	public void setPlDrWoDestRcvPod(BigDecimal plDrWoDestRcvPod){
		this.plDrWoDestRcvPod = plDrWoDestRcvPod;
	}

	public BigDecimal getPlDrWoDestRcvNpod(){
		return plDrWoDestRcvNpod;
	}

	public void setPlDrWoDestRcvNpod(BigDecimal plDrWoDestRcvNpod){
		this.plDrWoDestRcvNpod = plDrWoDestRcvNpod;
	}

	public BigDecimal getAirDrDestRcvPod(){
		return airDrDestRcvPod;
	}

	public void setAirDrDestRcvPod(BigDecimal airDrDestRcvPod){
		this.airDrDestRcvPod = airDrDestRcvPod;
	}

	public BigDecimal getAirDrDestRcvNpod(){
		return airDrDestRcvNpod;
	}

	public void setAirDrDestRcvNpod(BigDecimal airDrDestRcvNpod){
		this.airDrDestRcvNpod = airDrDestRcvNpod;
	}

	public BigDecimal getAirPrAgencyWoDestRcvPod(){
		return airPrAgencyWoDestRcvPod;
	}

	public void setAirPrAgencyWoDestRcvPod(BigDecimal airPrAgencyWoDestRcvPod){
		this.airPrAgencyWoDestRcvPod = airPrAgencyWoDestRcvPod;
	}

	public BigDecimal getAirPrAgencyWoDestRcvNpod(){
		return airPrAgencyWoDestRcvNpod;
	}

	public void setAirPrAgencyWoDestRcvNpod(BigDecimal airPrAgencyWoDestRcvNpod){
		this.airPrAgencyWoDestRcvNpod = airPrAgencyWoDestRcvNpod;
	}

	public BigDecimal getAirPrOtWoDestRcvPod(){
		return airPrOtWoDestRcvPod;
	}

	public void setAirPrOtWoDestRcvPod(BigDecimal airPrOtWoDestRcvPod){
		this.airPrOtWoDestRcvPod = airPrOtWoDestRcvPod;
	}

	public BigDecimal getAirPrOthWoDestRcvNpod(){
		return airPrOthWoDestRcvNpod;
	}

	public void setAirPrOthWoDestRcvNpod(BigDecimal airPrOthWoDestRcvNpod){
		this.airPrOthWoDestRcvNpod = airPrOthWoDestRcvNpod;
	}

	public BigDecimal getDeCh(){
		return deCh;
	}

	public void setDeCh(BigDecimal deCh){
		this.deCh = deCh;
	}

	public BigDecimal getDeCd(){
		return deCd;
	}

	public void setDeCd(BigDecimal deCd){
		this.deCd = deCd;
	}

	public BigDecimal getUrOrigChNpod(){
		return urOrigChNpod;
	}

	public void setUrOrigChNpod(BigDecimal urOrigChNpod){
		this.urOrigChNpod = urOrigChNpod;
	}

	public BigDecimal getUrOrigCdNpod(){
		return urOrigCdNpod;
	}

	public void setUrOrigCdNpod(BigDecimal urOrigCdNpod){
		this.urOrigCdNpod = urOrigCdNpod;
	}

	public BigDecimal getUrOrigChPod(){
		return urOrigChPod;
	}

	public void setUrOrigChPod(BigDecimal urOrigChPod){
		this.urOrigChPod = urOrigChPod;
	}

	public BigDecimal getUrOrigCdPod(){
		return urOrigCdPod;
	}

	public void setUrOrigCdPod(BigDecimal urOrigCdPod){
		this.urOrigCdPod = urOrigCdPod;
	}

	public BigDecimal getUrDestChNpod(){
		return urDestChNpod;
	}

	public void setUrDestChNpod(BigDecimal urDestChNpod){
		this.urDestChNpod = urDestChNpod;
	}

	public BigDecimal getUrDestCdNpod(){
		return urDestCdNpod;
	}

	public void setUrDestCdNpod(BigDecimal urDestCdNpod){
		this.urDestCdNpod = urDestCdNpod;
	}

	public BigDecimal getUrDestChPod(){
		return urDestChPod;
	}

	public void setUrDestChPod(BigDecimal urDestChPod){
		this.urDestChPod = urDestChPod;
	}

	public BigDecimal getUrDestCdPod() {
		return urDestCdPod;
	}

	public void setUrDestCdPod(BigDecimal urDestCdPod) {
		this.urDestCdPod = urDestCdPod;
	}

	public BigDecimal getPodCashCollectedFrt() {
		return podCashCollectedFrt;
	}

	public void setPodCashCollectedFrt(BigDecimal podCashCollectedFrt) {
		this.podCashCollectedFrt = podCashCollectedFrt;
	}

	public BigDecimal getPodCashCollectedPup() {
		return podCashCollectedPup;
	}

	public void setPodCashCollectedPup(BigDecimal podCashCollectedPup) {
		this.podCashCollectedPup = podCashCollectedPup;
	}

	public BigDecimal getPodCashCollectedDel() {
		return podCashCollectedDel;
	}

	public void setPodCashCollectedDel(BigDecimal podCashCollectedDel) {
		this.podCashCollectedDel = podCashCollectedDel;
	}

	public BigDecimal getPodCashCollectedPkg() {
		return podCashCollectedPkg;
	}

	public void setPodCashCollectedPkg(BigDecimal podCashCollectedPkg) {
		this.podCashCollectedPkg = podCashCollectedPkg;
	}

	public BigDecimal getPodCashCollectedDv() {
		return podCashCollectedDv;
	}

	public void setPodCashCollectedDv(BigDecimal podCashCollectedDv) {
		this.podCashCollectedDv = podCashCollectedDv;
	}

	public BigDecimal getPodCashCollectedCod() {
		return podCashCollectedCod;
	}

	public void setPodCashCollectedCod(BigDecimal podCashCollectedCod) {
		this.podCashCollectedCod = podCashCollectedCod;
	}

	public BigDecimal getPodCashCollectedOt() {
		return podCashCollectedOt;
	}

	public void setPodCashCollectedOt(BigDecimal podCashCollectedOt) {
		this.podCashCollectedOt = podCashCollectedOt;
	}

	public BigDecimal getPodOrigRcvWoFrt() {
		return podOrigRcvWoFrt;
	}

	public void setPodOrigRcvWoFrt(BigDecimal podOrigRcvWoFrt) {
		this.podOrigRcvWoFrt = podOrigRcvWoFrt;
	}

	public BigDecimal getPodOrigRcvWoPup() {
		return podOrigRcvWoPup;
	}

	public void setPodOrigRcvWoPup(BigDecimal podOrigRcvWoPup) {
		this.podOrigRcvWoPup = podOrigRcvWoPup;
	}

	public BigDecimal getPodOrigRcvWoDel() {
		return podOrigRcvWoDel;
	}

	public void setPodOrigRcvWoDel(BigDecimal podOrigRcvWoDel) {
		this.podOrigRcvWoDel = podOrigRcvWoDel;
	}

	public BigDecimal getPodOrigRcvWoPkg() {
		return podOrigRcvWoPkg;
	}

	public void setPodOrigRcvWoPkg(BigDecimal podOrigRcvWoPkg) {
		this.podOrigRcvWoPkg = podOrigRcvWoPkg;
	}

	public BigDecimal getPodOrigRcvWoDv() {
		return podOrigRcvWoDv;
	}

	public void setPodOrigRcvWoDv(BigDecimal podOrigRcvWoDv) {
		this.podOrigRcvWoDv = podOrigRcvWoDv;
	}

	public BigDecimal getPodOrigRcvWoCod() {
		return podOrigRcvWoCod;
	}

	public void setPodOrigRcvWoCod(BigDecimal podOrigRcvWoCod) {
		this.podOrigRcvWoCod = podOrigRcvWoCod;
	}

	public BigDecimal getPodOrigRcvWoOt() {
		return podOrigRcvWoOt;
	}

	public void setPodOrigRcvWoOt(BigDecimal podOrigRcvWoOt) {
		this.podOrigRcvWoOt = podOrigRcvWoOt;
	}

	public BigDecimal getPodOrigRcvNwoFrt() {
		return podOrigRcvNwoFrt;
	}

	public void setPodOrigRcvNwoFrt(BigDecimal podOrigRcvNwoFrt) {
		this.podOrigRcvNwoFrt = podOrigRcvNwoFrt;
	}

	public BigDecimal getPodOrigRcvNwoPup() {
		return podOrigRcvNwoPup;
	}

	public void setPodOrigRcvNwoPup(BigDecimal podOrigRcvNwoPup) {
		this.podOrigRcvNwoPup = podOrigRcvNwoPup;
	}

	public BigDecimal getPodOrigRcvNwoDel() {
		return podOrigRcvNwoDel;
	}

	public void setPodOrigRcvNwoDel(BigDecimal podOrigRcvNwoDel) {
		this.podOrigRcvNwoDel = podOrigRcvNwoDel;
	}

	public BigDecimal getPodOrigRcvNwoPkg() {
		return podOrigRcvNwoPkg;
	}

	public void setPodOrigRcvNwoPkg(BigDecimal podOrigRcvNwoPkg) {
		this.podOrigRcvNwoPkg = podOrigRcvNwoPkg;
	}

	public BigDecimal getPodOrigRcvNwoDv() {
		return podOrigRcvNwoDv;
	}

	public void setPodOrigRcvNwoDv(BigDecimal podOrigRcvNwoDv) {
		this.podOrigRcvNwoDv = podOrigRcvNwoDv;
	}

	public BigDecimal getPodOrigRcvNwoCod() {
		return podOrigRcvNwoCod;
	}

	public void setPodOrigRcvNwoCod(BigDecimal podOrigRcvNwoCod) {
		this.podOrigRcvNwoCod = podOrigRcvNwoCod;
	}

	public BigDecimal getPodOrigRcvNwoOt() {
		return podOrigRcvNwoOt;
	}

	public void setPodOrigRcvNwoOt(BigDecimal podOrigRcvNwoOt) {
		this.podOrigRcvNwoOt = podOrigRcvNwoOt;
	}

	public BigDecimal getPodDestRcvWoFrt() {
		return podDestRcvWoFrt;
	}

	public void setPodDestRcvWoFrt(BigDecimal podDestRcvWoFrt) {
		this.podDestRcvWoFrt = podDestRcvWoFrt;
	}

	public BigDecimal getPodDestRcvWoPup() {
		return podDestRcvWoPup;
	}

	public void setPodDestRcvWoPup(BigDecimal podDestRcvWoPup) {
		this.podDestRcvWoPup = podDestRcvWoPup;
	}

	public BigDecimal getPodDestRcvWoDel() {
		return podDestRcvWoDel;
	}

	public void setPodDestRcvWoDel(BigDecimal podDestRcvWoDel) {
		this.podDestRcvWoDel = podDestRcvWoDel;
	}

	public BigDecimal getPodDestRcvWoPkg() {
		return podDestRcvWoPkg;
	}

	public void setPodDestRcvWoPkg(BigDecimal podDestRcvWoPkg) {
		this.podDestRcvWoPkg = podDestRcvWoPkg;
	}

	public BigDecimal getPodDestRcvWoDv() {
		return podDestRcvWoDv;
	}

	public void setPodDestRcvWoDv(BigDecimal podDestRcvWoDv) {
		this.podDestRcvWoDv = podDestRcvWoDv;
	}

	public BigDecimal getPodDestRcvWoCod() {
		return podDestRcvWoCod;
	}

	public void setPodDestRcvWoCod(BigDecimal podDestRcvWoCod) {
		this.podDestRcvWoCod = podDestRcvWoCod;
	}

	/**
	 * @return the rdWoOrigRcvPod
	 */
	public BigDecimal getRdWoOrigRcvPod() {
		return rdWoOrigRcvPod;
	}

	/**
	 * @param rdWoOrigRcvPod the rdWoOrigRcvPod to set
	 */
	public void setRdWoOrigRcvPod(BigDecimal rdWoOrigRcvPod) {
		this.rdWoOrigRcvPod = rdWoOrigRcvPod;
	}

	/**
	 * @return the rdWoOrigRcvNpod
	 */
	public BigDecimal getRdWoOrigRcvNpod() {
		return rdWoOrigRcvNpod;
	}

	/**
	 * @param rdWoOrigRcvNpod the rdWoOrigRcvNpod to set
	 */
	public void setRdWoOrigRcvNpod(BigDecimal rdWoOrigRcvNpod) {
		this.rdWoOrigRcvNpod = rdWoOrigRcvNpod;
	}

	/**
	 * @return the rdWoDestRcvPod
	 */
	public BigDecimal getRdWoDestRcvPod() {
		return rdWoDestRcvPod;
	}

	/**
	 * @param rdWoDestRcvPod the rdWoDestRcvPod to set
	 */
	public void setRdWoDestRcvPod(BigDecimal rdWoDestRcvPod) {
		this.rdWoDestRcvPod = rdWoDestRcvPod;
	}

	/**
	 * @return the rdWoDestRcvNpod
	 */
	public BigDecimal getRdWoDestRcvNpod() {
		return rdWoDestRcvNpod;
	}

	/**
	 * @param rdWoDestRcvNpod the rdWoDestRcvNpod to set
	 */
	public void setRdWoDestRcvNpod(BigDecimal rdWoDestRcvNpod) {
		this.rdWoDestRcvNpod = rdWoDestRcvNpod;
	}
}
