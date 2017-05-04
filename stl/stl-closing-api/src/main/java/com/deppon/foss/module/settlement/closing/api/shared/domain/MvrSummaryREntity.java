package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.math.BigDecimal;

/**
 * 02业务重分类汇总报表Entity
 * @author 340778 foss
 * @createTime 2016年8月18日 下午4:39:17
 */
public class MvrSummaryREntity {
    private String id;

    private String period;

    private String productCode;

    private String subIncomeCpmpanyCode;

    private String subIncomeCpmpanyName;

    private String sourceData;

    private BigDecimal amountFre;

    private BigDecimal amountFreTax;

    private BigDecimal amountFreTotal;

    private BigDecimal amountTra;

    private BigDecimal amountTraTax;

    private BigDecimal amountTraTotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getSubIncomeCpmpanyCode() {
        return subIncomeCpmpanyCode;
    }

    public void setSubIncomeCpmpanyCode(String subIncomeCpmpanyCode) {
        this.subIncomeCpmpanyCode = subIncomeCpmpanyCode == null ? null : subIncomeCpmpanyCode.trim();
    }

    public String getSubIncomeCpmpanyName() {
        return subIncomeCpmpanyName;
    }

    public void setSubIncomeCpmpanyName(String subIncomeCpmpanyName) {
        this.subIncomeCpmpanyName = subIncomeCpmpanyName == null ? null : subIncomeCpmpanyName.trim();
    }

    public String getSourceData() {
        return sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData == null ? null : sourceData.trim();
    }

    public BigDecimal getAmountFre() {
        return amountFre;
    }

    public void setAmountFre(BigDecimal amountFre) {
        this.amountFre = amountFre;
    }

    public BigDecimal getAmountFreTax() {
        return amountFreTax;
    }

    public void setAmountFreTax(BigDecimal amountFreTax) {
        this.amountFreTax = amountFreTax;
    }

    public BigDecimal getAmountFreTotal() {
        return amountFreTotal;
    }

    public void setAmountFreTotal(BigDecimal amountFreTotal) {
        this.amountFreTotal = amountFreTotal;
    }

    public BigDecimal getAmountTra() {
        return amountTra;
    }

    public void setAmountTra(BigDecimal amountTra) {
        this.amountTra = amountTra;
    }

    public BigDecimal getAmountTraTax() {
        return amountTraTax;
    }

    public void setAmountTraTax(BigDecimal amountTraTax) {
        this.amountTraTax = amountTraTax;
    }

    public BigDecimal getAmountTraTotal() {
        return amountTraTotal;
    }

    public void setAmountTraTotal(BigDecimal amountTraTotal) {
        this.amountTraTotal = amountTraTotal;
    }
}