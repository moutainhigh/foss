package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.math.BigDecimal;

/**
 * 02普通业务重分类报表entity
 * @author 340778 foss
 * @createTime 2016年8月17日 上午10:46:29
 */
public class MvrNrfontREntity {
	/**
	 * id
	 */
    private String id;
    /**
     * 会计期间
     */
    private String period;
    /**
     * 产品类型编码
     */
    private String productCode;
    /**
     * 始发部门编码
     */
    private String depCode;
    /**
     * 始发部门名称
     */
    private String depName;
    /**
     * 是否统一结算 Y or N
     */
    private String unifiedSettlementType;
    /**
     * 合同部门编码
     */
    private String contractOrgCode;
    /**
     * 合同部门名称
     */
    private String contractOrgName;
    /**
     * 收入所属子公司编码(统一结算>合同部门,非统一结算>始发部门)
     */
    private String subIncomeCpmpanyCode;
    /**
     * 收入所属子公司名称
     */
    private String subIncomeCpmpanyName;
    /**
     * 签收运单【02】-含税收入冲销
     */
    private BigDecimal amountStbtwoTro;
    /**
     * 签收运单【02】-物流辅助业金额
     */
    private BigDecimal amountStbtwoAola;
    /**
     * 签收运单【02】-物流运输业金额
     */
    private BigDecimal amountStbtwoLtra;
    /**
     * 签收运单【02】-物流辅助业税金
     */
    private BigDecimal amountStbtwoLsit;
    /**
     * 签收运单【02】-物流运输业税金
     */
    private BigDecimal amountStbtwoTrat;
    /**
     * 反签收运单【02】-含税收入冲销
     */
    private BigDecimal amountRstbtwoTro;

    private BigDecimal amountRstbtwoAola;

    private BigDecimal amountRstbtwoLtra;

    private BigDecimal amountRstbtwoLsit;

    private BigDecimal amountRstbtwoTrat;

    private BigDecimal amountStbhTro;

    private BigDecimal amountStbhAola;

    private BigDecimal amountStbhLtra;

    private BigDecimal amountStbhLsit;

    private BigDecimal amountStbhTrat;

    private BigDecimal amountRstbhTro;

    private BigDecimal amountRstbhAola;

    private BigDecimal amountRstbhLtra;

    private BigDecimal amountRstbhLsit;

    private BigDecimal amountRstbhTrat;

    private BigDecimal amountAirotrTro;

    private BigDecimal amountAirotrAola;

    private BigDecimal amountAirotrLtra;

    private BigDecimal amountAirotrLsit;

    private BigDecimal amountAirotrTrat;

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

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode == null ? null : depCode.trim();
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    public String getUnifiedSettlementType() {
        return unifiedSettlementType;
    }

    public void setUnifiedSettlementType(String unifiedSettlementType) {
        this.unifiedSettlementType = unifiedSettlementType == null ? null : unifiedSettlementType.trim();
    }

    public String getContractOrgCode() {
        return contractOrgCode;
    }

    public void setContractOrgCode(String contractOrgCode) {
        this.contractOrgCode = contractOrgCode == null ? null : contractOrgCode.trim();
    }

    public String getContractOrgName() {
        return contractOrgName;
    }

    public void setContractOrgName(String contractOrgName) {
        this.contractOrgName = contractOrgName == null ? null : contractOrgName.trim();
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

    public BigDecimal getAmountStbtwoTro() {
        return amountStbtwoTro;
    }

    public void setAmountStbtwoTro(BigDecimal amountStbtwoTro) {
        this.amountStbtwoTro = amountStbtwoTro;
    }

    public BigDecimal getAmountStbtwoAola() {
        return amountStbtwoAola;
    }

    public void setAmountStbtwoAola(BigDecimal amountStbtwoAola) {
        this.amountStbtwoAola = amountStbtwoAola;
    }

    public BigDecimal getAmountStbtwoLtra() {
        return amountStbtwoLtra;
    }

    public void setAmountStbtwoLtra(BigDecimal amountStbtwoLtra) {
        this.amountStbtwoLtra = amountStbtwoLtra;
    }

    public BigDecimal getAmountStbtwoLsit() {
        return amountStbtwoLsit;
    }

    public void setAmountStbtwoLsit(BigDecimal amountStbtwoLsit) {
        this.amountStbtwoLsit = amountStbtwoLsit;
    }

    public BigDecimal getAmountStbtwoTrat() {
        return amountStbtwoTrat;
    }

    public void setAmountStbtwoTrat(BigDecimal amountStbtwoTrat) {
        this.amountStbtwoTrat = amountStbtwoTrat;
    }

    public BigDecimal getAmountRstbtwoTro() {
        return amountRstbtwoTro;
    }

    public void setAmountRstbtwoTro(BigDecimal amountRstbtwoTro) {
        this.amountRstbtwoTro = amountRstbtwoTro;
    }

    public BigDecimal getAmountRstbtwoAola() {
        return amountRstbtwoAola;
    }

    public void setAmountRstbtwoAola(BigDecimal amountRstbtwoAola) {
        this.amountRstbtwoAola = amountRstbtwoAola;
    }

    public BigDecimal getAmountRstbtwoLtra() {
        return amountRstbtwoLtra;
    }

    public void setAmountRstbtwoLtra(BigDecimal amountRstbtwoLtra) {
        this.amountRstbtwoLtra = amountRstbtwoLtra;
    }

    public BigDecimal getAmountRstbtwoLsit() {
        return amountRstbtwoLsit;
    }

    public void setAmountRstbtwoLsit(BigDecimal amountRstbtwoLsit) {
        this.amountRstbtwoLsit = amountRstbtwoLsit;
    }

    public BigDecimal getAmountRstbtwoTrat() {
        return amountRstbtwoTrat;
    }

    public void setAmountRstbtwoTrat(BigDecimal amountRstbtwoTrat) {
        this.amountRstbtwoTrat = amountRstbtwoTrat;
    }

    public BigDecimal getAmountStbhTro() {
        return amountStbhTro;
    }

    public void setAmountStbhTro(BigDecimal amountStbhTro) {
        this.amountStbhTro = amountStbhTro;
    }

    public BigDecimal getAmountStbhAola() {
        return amountStbhAola;
    }

    public void setAmountStbhAola(BigDecimal amountStbhAola) {
        this.amountStbhAola = amountStbhAola;
    }

    public BigDecimal getAmountStbhLtra() {
        return amountStbhLtra;
    }

    public void setAmountStbhLtra(BigDecimal amountStbhLtra) {
        this.amountStbhLtra = amountStbhLtra;
    }

    public BigDecimal getAmountStbhLsit() {
        return amountStbhLsit;
    }

    public void setAmountStbhLsit(BigDecimal amountStbhLsit) {
        this.amountStbhLsit = amountStbhLsit;
    }

    public BigDecimal getAmountStbhTrat() {
        return amountStbhTrat;
    }

    public void setAmountStbhTrat(BigDecimal amountStbhTrat) {
        this.amountStbhTrat = amountStbhTrat;
    }

    public BigDecimal getAmountRstbhTro() {
        return amountRstbhTro;
    }

    public void setAmountRstbhTro(BigDecimal amountRstbhTro) {
        this.amountRstbhTro = amountRstbhTro;
    }

    public BigDecimal getAmountRstbhAola() {
        return amountRstbhAola;
    }

    public void setAmountRstbhAola(BigDecimal amountRstbhAola) {
        this.amountRstbhAola = amountRstbhAola;
    }

    public BigDecimal getAmountRstbhLtra() {
        return amountRstbhLtra;
    }

    public void setAmountRstbhLtra(BigDecimal amountRstbhLtra) {
        this.amountRstbhLtra = amountRstbhLtra;
    }

    public BigDecimal getAmountRstbhLsit() {
        return amountRstbhLsit;
    }

    public void setAmountRstbhLsit(BigDecimal amountRstbhLsit) {
        this.amountRstbhLsit = amountRstbhLsit;
    }

    public BigDecimal getAmountRstbhTrat() {
        return amountRstbhTrat;
    }

    public void setAmountRstbhTrat(BigDecimal amountRstbhTrat) {
        this.amountRstbhTrat = amountRstbhTrat;
    }

    public BigDecimal getAmountAirotrTro() {
        return amountAirotrTro;
    }

    public void setAmountAirotrTro(BigDecimal amountAirotrTro) {
        this.amountAirotrTro = amountAirotrTro;
    }

    public BigDecimal getAmountAirotrAola() {
        return amountAirotrAola;
    }

    public void setAmountAirotrAola(BigDecimal amountAirotrAola) {
        this.amountAirotrAola = amountAirotrAola;
    }

    public BigDecimal getAmountAirotrLtra() {
        return amountAirotrLtra;
    }

    public void setAmountAirotrLtra(BigDecimal amountAirotrLtra) {
        this.amountAirotrLtra = amountAirotrLtra;
    }

    public BigDecimal getAmountAirotrLsit() {
        return amountAirotrLsit;
    }

    public void setAmountAirotrLsit(BigDecimal amountAirotrLsit) {
        this.amountAirotrLsit = amountAirotrLsit;
    }

    public BigDecimal getAmountAirotrTrat() {
        return amountAirotrTrat;
    }

    public void setAmountAirotrTrat(BigDecimal amountAirotrTrat) {
        this.amountAirotrTrat = amountAirotrTrat;
    }
}