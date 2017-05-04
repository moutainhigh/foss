package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 快递代理理网点运价方案明细实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-18 上午9:39:43 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-18 上午9:39:43
 * @since
 * @version
 */
public class OubrPlanDetailEntity extends BaseEntity{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;


    /**
     *快递代理网点运价方案ID.
     */
    private String expressOutbranchPlanId;

    /**
     * 计费类别WEIGHT ：重量计费VOLUME：体积计费.
     */
    private String caculateType;

    /**
     * 计价左区间.
     */
    private BigDecimal leftrange;

    /**
     * 计价右区间.
     */
    private BigDecimal rightrange;

    /**
     * 固定费用.
     */
    private BigDecimal fee;

    /**
     * 费率或者单价.
     */
    private BigDecimal feeRate;

    /**
     * 量纲.
     */
    private BigDecimal dimension;

    /**
     * 最低一票.
     */
    private Long minFee;

    /**
     * 数据状态.
     */
    private String active;

    /**
     * 数据版本.
     */
    private Long versionNo;

    /**
     * 创建组织.
     */
    private String createOrgCode;

    /**
     * 更新组织.
     */
    private String modifyOrgCode;

    /**
     * 币种.
     */
    private String currencyCode;

    /**
     * 获快递代理递代理网点运价方案ID.
     *
     * @return t快递代理快递代理网点运价方案ID
     */
    public String getExpressOutbranchPlanId() {
        return expressOutbranchPlanId;
    }

    /**
     *快递代理 快递代理网点运价方案ID.
     *
     * @param expressOutbranchPlanId the快递代理w 快递代理网点运价方案ID
     */
    public void setExpressOutbranchPlanId(String expressOutbranchPlanId) {
        this.expressOutbranchPlanId = expressOutbranchPlanId;
    }

    /**
     * 获取 计费类别WEIGHT ：重量计费VOLUME：体积计费.
     *
     * @return the 计费类别WEIGHT ：重量计费VOLUME：体积计费
     */
    public String getCaculateType() {
        return caculateType;
    }

    /**
     * 设置 计费类别WEIGHT ：重量计费VOLUME：体积计费.
     *
     * @param caculateType the new 计费类别WEIGHT ：重量计费VOLUME：体积计费
     */
    public void setCaculateType(String caculateType) {
        this.caculateType = caculateType;
    }

    /**
     * 获取 计价左区间.
     *
     * @return the 计价左区间
     */
    public BigDecimal getLeftrange() {
	if(leftrange==null)
	    leftrange=BigDecimal.ZERO;
        return leftrange;
    }

    /**
     * 设置 计价左区间.
     *
     * @param leftrange the new 计价左区间
     */
    public void setLeftrange(BigDecimal leftrange) {
        this.leftrange = leftrange;
    }

    /**
     * 获取 计价右区间.
     *
     * @return the 计价右区间
     */
    public BigDecimal getRightrange() {
	if(rightrange==null)
	    rightrange=BigDecimal.ZERO;
        return rightrange;
    }

    /**
     * 设置 计价右区间.
     *
     * @param rightrange the new 计价右区间
     */
    public void setRightrange(BigDecimal rightrange) {
        this.rightrange = rightrange;
    }
    
    /**
     * @return  the fee
     */
    public BigDecimal getFee() {
        return fee;
    }

    
    /**
     * @param fee the fee to set
     */
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /**
     * 获取 费率或者单价.
     *
     * @return the 费率或者单价
     */
    public BigDecimal getFeeRate() {
        return feeRate;
    }

    /**
     * 设置 费率或者单价.
     *
     * @param feeRate the new 费率或者单价
     */
    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    /**
     * 获取 量纲.
     *
     * @return the 量纲
     */
    public BigDecimal getDimension() {
        return dimension;
    }

    /**
     * 设置 量纲.
     *
     * @param dimension the new 量纲
     */
    public void setDimension(BigDecimal dimension) {
        this.dimension = dimension;
    }

    /**
     * 获取 最低一票.
     *
     * @return the 最低一票
     */
    public Long getMinFee() {
        return minFee;
    }

    /**
     * 设置 最低一票.
     *
     * @param minFee the new 最低一票
     */
    public void setMinFee(Long minFee) {
        this.minFee = minFee;
    }

    /**
     * 获取 数据状态.
     *
     * @return the 数据状态
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置 数据状态.
     *
     * @param active the new 数据状态
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * 获取 数据版本.
     *
     * @return the 数据版本
     */
    public Long getVersionNo() {
        return versionNo;
    }

    /**
     * 设置 数据版本.
     *
     * @param versionNo the new 数据版本
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 获取 创建组织.
     *
     * @return the 创建组织
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 创建组织.
     *
     * @param createOrgCode the new 创建组织
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取 更新组织.
     *
     * @return the 更新组织
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    /**
     * 设置 更新组织.
     *
     * @param modifyOrgCode the new 更新组织
     */
    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode;
    }

    /**
     * 获取 币种.
     *
     * @return the 币种
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 设置 币种.
     *
     * @param currencyCode the new 币种
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}