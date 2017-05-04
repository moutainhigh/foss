package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 合伙人对账单DTO
 * @author foss-youkun
 * @date 2016/1/22
 */
public class StatementRecivableDto  implements Serializable {

    /**
     * 类序列号
     */
    private static final long serialVersionUID = -8196100711549354750L;
    /**
     * 合伙人部门编码
     */
    private String contractOrgCode;

    /**
     * 合伙人部门名称
     */
    private String contractOrgName;

    /**
     * 业务开始日期
     */
    private Date businessStartDate;

    /**
     * 业务结束日期
     */
    private Date businessEndDate;

    /**
     * 单号集合
     */
    private List<String> waybillNoList;

    /**
     * 对账单用于和客户或者代理进行账款核对与结算
     */
    private StatementOfAccountEntity statementOfAccountEntity;

    /**
     *应收单明细实体
     * */
    private List<BillReceivableEntity> billReceivableEntityList;

    /**
     * 合伙人收款对账单实体
     */
    private StatementRecivableEntity statementRecivableEntity;

    /**
     * 合伙人收款对账单明细实体
     */
    private StatementRecivableDEntity statementRecivableDEntity;

    /**
     * 对账单号
     */
    private String statementBillNo;

    /**
     * 应收单号
     */
    private String receivableNo;

    /**
     * 总记录数
     */
    private Long TotalCount;
    /**
     * 合伙人收款对账单明细集合
     */
    private List<StatementRecivableDEntity> statementRecivableDEntityList;

    /**
     * 合伙人收款对账单集合
     */
    private List<StatementRecivableEntity> statementRecivableEntityList;

    /**
     * 可查询部门列表
     */
    private List<String> orgCodeList;

    /**
     * 大区
     */
    private String largeRegion;

    /**
     * 小区
     */
    private String smallRegion;

    /**
     * 结账状态
     */
    private String settleStatus;

    /**
     * 已结清
     */
    private String  statementSettleStatus;

    /**
     * 未结清
     */
    private String statementUnSettleStatus;
    /**
     * 对账单状态
     */
    private String statementStatus;

    /**
     * 部门
     */
    private String createOrgCode;

    /**
     * 用于区分是根据什么单号查询
     */
    private String  tab;
    /**
     * 对账单号集合
     */
    private List<String> statementBillNoList;

    /**
     * 导出对账单列头英文名称
     */
    private String[] arrayColumns;
    /**
     * 导出对账单列头中文名称
     */
    private String[] arrayColumnNames;



    public List<String> getStatementBillNoList() {
        return statementBillNoList;
    }

    public void setStatementBillNoList(List<String> statementBillNoList) {
        this.statementBillNoList = statementBillNoList;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getCreateOrgCode() {
        return createOrgCode;
    }

    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    public String getContractOrgName() {
        return contractOrgName;
    }

    public void setContractOrgName(String contractOrgName) {
        this.contractOrgName = contractOrgName;
    }

    public StatementOfAccountEntity getStatementOfAccountEntity() {
        return statementOfAccountEntity;
    }

    public void setStatementOfAccountEntity(StatementOfAccountEntity statementOfAccountEntity) {
        this.statementOfAccountEntity = statementOfAccountEntity;
    }

    public String getContractOrgCode() {
        return contractOrgCode;
    }

    public void setContractOrgCode(String contractOrgCode) {
        this.contractOrgCode = contractOrgCode;
    }

    public Date getBusinessStartDate() {
        return businessStartDate;
    }

    public void setBusinessStartDate(Date businessStartDate) {
        this.businessStartDate = businessStartDate;
    }

    public Date getBusinessEndDate() {
        return businessEndDate;
    }

    public void setBusinessEndDate(Date businessEndDate) {
        this.businessEndDate = businessEndDate;
    }

    public List<String> getWaybillNoList() {
        return waybillNoList;
    }

    public void setWaybillNoList(List<String> waybillNoList) {
        this.waybillNoList = waybillNoList;
    }

    public List<BillReceivableEntity> getBillReceivableEntityList() {
        return billReceivableEntityList;
    }

    public void setBillReceivableEntityList(List<BillReceivableEntity> billReceivableEntityList) {
        this.billReceivableEntityList = billReceivableEntityList;
    }

    public StatementRecivableEntity getStatementRecivableEntity() {
        return statementRecivableEntity;
    }

    public void setStatementRecivableEntity(StatementRecivableEntity statementRecivableEntity) {
        this.statementRecivableEntity = statementRecivableEntity;
    }

    public StatementRecivableDEntity getStatementRecivableDEntity() {
        return statementRecivableDEntity;
    }

    public void setStatementRecivableDEntity(StatementRecivableDEntity statementRecivableDEntity) {
        this.statementRecivableDEntity = statementRecivableDEntity;
    }

    public List<StatementRecivableDEntity> getStatementRecivableDEntityList() {
        return statementRecivableDEntityList;
    }

    public void setStatementRecivableDEntityList(List<StatementRecivableDEntity> statementRecivableDEntityList) {
        this.statementRecivableDEntityList = statementRecivableDEntityList;
    }

    public String getStatementBillNo() {
        return statementBillNo;
    }

    public void setStatementBillNo(String statementBillNo) {
        this.statementBillNo = statementBillNo;
    }

    public Long getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(Long totalCount) {
        TotalCount = totalCount;
    }

    public List<String> getOrgCodeList() {
        return orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getLargeRegion() {
        return largeRegion;
    }

    public void setLargeRegion(String largeRegion) {
        this.largeRegion = largeRegion;
    }

    public String getSmallRegion() {
        return smallRegion;
    }

    public void setSmallRegion(String smallRegion) {
        this.smallRegion = smallRegion;
    }

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus;
    }

    public List<StatementRecivableEntity> getStatementRecivableEntityList() {
        return statementRecivableEntityList;
    }

    public void setStatementRecivableEntityList(List<StatementRecivableEntity> statementRecivableEntityList) {
        this.statementRecivableEntityList = statementRecivableEntityList;
    }

    public String getStatementUnSettleStatus() {
        return statementUnSettleStatus;
    }

    public void setStatementUnSettleStatus(String statementUnSettleStatus) {
        this.statementUnSettleStatus = statementUnSettleStatus;
    }

    public String getStatementSettleStatus() {
        return statementSettleStatus;
    }

    public void setStatementSettleStatus(String statementSettleStatus) {
        this.statementSettleStatus = statementSettleStatus;
    }

    public String getStatementStatus() {
        return statementStatus;
    }

    public void setStatementStatus(String statementStatus) {
        this.statementStatus = statementStatus;
    }

    public String getReceivableNo() {
        return receivableNo;
    }

    public void setReceivableNo(String receivableNo) {
        this.receivableNo = receivableNo;
    }

    public String[] getArrayColumns() {
        return arrayColumns;
    }

    public void setArrayColumns(String[] arrayColumns) {
        this.arrayColumns = arrayColumns;
    }

    public String[] getArrayColumnNames() {
        return arrayColumnNames;
    }

    public void setArrayColumnNames(String[] arrayColumnNames) {
        this.arrayColumnNames = arrayColumnNames;
    }
}
