package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.List;

public class FreightResEntity implements Serializable {

	private static final long serialVersionUID = 2767320250857169419L;
	// 计价条目编码
	private String priceEntityCode;
	// 计价条目名称
	private String priceEntityName;
	// 最终费率
	private double actualFeeRate;
	// 原始费用
	private double caculateFee;
	// 最终费用
	private double DiscountFee;
	// 价格计算表达式
	private String caculateExpression;
	// 是否可以修改
	private String canmodify;
	// 是否可以删除
	private String candelete;
	// 最低费用
	private double minFee;
	// 最高费用
	private double maxFee;
	
	 /**
     * 
     */
    private String  subType;
    /**
     * 
     */
    private String  subTypeName;
	
	// 采用的折扣方案（List）
	private List<DiscountProgramEntity> discountPrograms;
	/**
     * 是否接货
     */
    private String centralizePickup;

	public String getPriceEntityCode() {
		return priceEntityCode;
	}

	public void setPriceEntityCode(String priceEntityCode) {
		this.priceEntityCode = priceEntityCode;
	}

	public String getPriceEntityName() {
		return priceEntityName;
	}

	public void setPriceEntityName(String priceEntityName) {
		this.priceEntityName = priceEntityName;
	}

	public double getActualFeeRate() {
		return actualFeeRate;
	}

	public void setActualFeeRate(double actualFeeRate) {
		this.actualFeeRate = actualFeeRate;
	}

	public double getCaculateFee() {
		return caculateFee;
	}

	public void setCaculateFee(double caculateFee) {
		this.caculateFee = caculateFee;
	}

	public double getDiscountFee() {
		return DiscountFee;
	}

	public void setDiscountFee(double discountFee) {
		DiscountFee = discountFee;
	}

	public String getCaculateExpression() {
		return caculateExpression;
	}

	public void setCaculateExpression(String caculateExpression) {
		this.caculateExpression = caculateExpression;
	}

	public String getCanmodify() {
		return canmodify;
	}

	public void setCanmodify(String canmodify) {
		this.canmodify = canmodify;
	}

	public String getCandelete() {
		return candelete;
	}

	public void setCandelete(String candelete) {
		this.candelete = candelete;
	}

	public double getMinFee() {
		return minFee;
	}

	public void setMinFee(double minFee) {
		this.minFee = minFee;
	}

	public double getMaxFee() {
		return maxFee;
	}

	public void setMaxFee(double maxFee) {
		this.maxFee = maxFee;
	}

	public List<DiscountProgramEntity> getDiscountPrograms() {
		return discountPrograms;
	}

	public void setDiscountPrograms(List<DiscountProgramEntity> discountPrograms) {
		this.discountPrograms = discountPrograms;
	}

	public String getCentralizePickup() {
		return centralizePickup;
	}

	public void setCentralizePickup(String centralizePickup) {
		this.centralizePickup = centralizePickup;
	}
    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

}
