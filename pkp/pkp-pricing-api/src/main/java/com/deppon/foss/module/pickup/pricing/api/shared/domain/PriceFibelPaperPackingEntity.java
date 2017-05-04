package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 纸纤包装单价
 *@author:218371-foss-zhaoyanjun
 *@date:2014-11-14上午10:09
 */
public class PriceFibelPaperPackingEntity  extends BaseEntity {
	
	private static final long serialVersionUID = 8178707383441942729L;
	
	//计价规则表ID
	private String valuationId;
	
	//计价方式明细表ID
	private String critcriaDetailId;
	
	//创建时间
	private Date createDate;
	
	//修改时间
	private Date modifyDate;
	
	//纸箱1号客户
	private BigDecimal paperBoxOne;
	
	//纸箱2号客户
	private BigDecimal paperBoxTwo;
	
	//纸箱3号客户
	private BigDecimal paperBoxThree;
	
	//纸箱4号客户
	private BigDecimal paperBoxFour;
	
	//纤袋1号蓝
	private BigDecimal fibelBagOneBlue;
	
	//纤袋2号蓝
	private BigDecimal fibelBagTwoBlue;
		
	//纤袋3号蓝
	private BigDecimal fibelBagThreeBlue;
	
	//纤袋4号蓝
	private BigDecimal fibelBagFourBlue;
	
	//纤袋1号白
	private BigDecimal fibelBagOneWhite;
	
	//纤袋2号白
	private BigDecimal fibelBagTwoWhite;
	
	//纤袋3号白
	private BigDecimal fibelBagThreeWhite;
	
	//纤袋4号白
	private BigDecimal fibelBagFourWhite;
	
	//纤袋5号白
	private BigDecimal fibelBagFiveWhite;
	
	//纤袋6号白
	private BigDecimal fibelBagSixWhite;
	
	//基础/区域
	private String kind;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getValuationId() {
		return valuationId;
	}

	public void setValuationId(String valuationId) {
		this.valuationId = valuationId;
	}

	public String getCritcriaDetailId() {
		return critcriaDetailId;
	}

	public void setCritcriaDetailId(String critcriaDetailId) {
		this.critcriaDetailId = critcriaDetailId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public BigDecimal getPaperBoxOne() {
		return paperBoxOne;
	}

	public void setPaperBoxOne(BigDecimal paperBoxOne) {
		this.paperBoxOne = paperBoxOne;
	}

	public BigDecimal getPaperBoxTwo() {
		return paperBoxTwo;
	}

	public void setPaperBoxTwo(BigDecimal paperBoxTwo) {
		this.paperBoxTwo = paperBoxTwo;
	}

	public BigDecimal getPaperBoxThree() {
		return paperBoxThree;
	}

	public void setPaperBoxThree(BigDecimal paperBoxThree) {
		this.paperBoxThree = paperBoxThree;
	}

	public BigDecimal getPaperBoxFour() {
		return paperBoxFour;
	}

	public void setPaperBoxFour(BigDecimal paperBoxFour) {
		this.paperBoxFour = paperBoxFour;
	}

	public BigDecimal getFibelBagOneBlue() {
		return fibelBagOneBlue;
	}

	public void setFibelBagOneBlue(BigDecimal fibelBagOneBlue) {
		this.fibelBagOneBlue = fibelBagOneBlue;
	}

	public BigDecimal getFibelBagTwoBlue() {
		return fibelBagTwoBlue;
	}

	public void setFibelBagTwoBlue(BigDecimal fibelBagTwoBlue) {
		this.fibelBagTwoBlue = fibelBagTwoBlue;
	}

	public BigDecimal getFibelBagThreeBlue() {
		return fibelBagThreeBlue;
	}

	public void setFibelBagThreeBlue(BigDecimal fibelBagThreeBlue) {
		this.fibelBagThreeBlue = fibelBagThreeBlue;
	}

	public BigDecimal getFibelBagFourBlue() {
		return fibelBagFourBlue;
	}

	public void setFibelBagFourBlue(BigDecimal fibelBagFourBlue) {
		this.fibelBagFourBlue = fibelBagFourBlue;
	}

	public BigDecimal getFibelBagOneWhite() {
		return fibelBagOneWhite;
	}

	public void setFibelBagOneWhite(BigDecimal fibelBagOneWhite) {
		this.fibelBagOneWhite = fibelBagOneWhite;
	}

	public BigDecimal getFibelBagTwoWhite() {
		return fibelBagTwoWhite;
	}

	public void setFibelBagTwoWhite(BigDecimal fibelBagTwoWhite) {
		this.fibelBagTwoWhite = fibelBagTwoWhite;
	}

	public BigDecimal getFibelBagThreeWhite() {
		return fibelBagThreeWhite;
	}

	public void setFibelBagThreeWhite(BigDecimal fibelBagThreeWhite) {
		this.fibelBagThreeWhite = fibelBagThreeWhite;
	}

	public BigDecimal getFibelBagFourWhite() {
		return fibelBagFourWhite;
	}

	public void setFibelBagFourWhite(BigDecimal fibelBagFourWhite) {
		this.fibelBagFourWhite = fibelBagFourWhite;
	}

	public BigDecimal getFibelBagFiveWhite() {
		return fibelBagFiveWhite;
	}

	public void setFibelBagFiveWhite(BigDecimal fibelBagFiveWhite) {
		this.fibelBagFiveWhite = fibelBagFiveWhite;
	}

	public BigDecimal getFibelBagSixWhite() {
		return fibelBagSixWhite;
	}

	public void setFibelBagSixWhite(BigDecimal fibelBagSixWhite) {
		this.fibelBagSixWhite = fibelBagSixWhite;
	}
}
