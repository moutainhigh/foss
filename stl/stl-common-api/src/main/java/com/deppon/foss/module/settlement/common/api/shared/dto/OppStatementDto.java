package com.deppon.foss.module.settlement.common.api.shared.dto;

import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 302307 on 2016/4/26.
 */
public class OppStatementDto implements Serializable{

    private static final long serialVersionUID = 1440535872766177514L;

    private List<StatementOfAccountEntity> accounts;

    private List<StatementOfAccountDEntity> accountsDetail;

    private List<String> statementBillNos = new ArrayList<String>();

    private String statementBillNo;

    private Date startDate;

    private Date endDate;

    private String settleStatus;

    private String customerCode;

    private String orgCode;

    private Integer count;

    private Integer start;

    private Integer limit;

    public List<StatementOfAccountEntity> getAccounts() {
        return accounts;
    }
    public void setAccounts(List<StatementOfAccountEntity> accounts) {
        this.accounts = accounts;
    }

    public List<StatementOfAccountDEntity> getAccountsDetail() {
        return accountsDetail;
    }

    public void setAccountsDetail(List<StatementOfAccountDEntity> accountsDetail) {
        this.accountsDetail = accountsDetail;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List<String> getStatementBillNos() {
        return statementBillNos;
    }

    public void setStatementBillNos(List<String> statementBillNos) {
        this.statementBillNos = statementBillNos;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getStatementBillNo() {
        return statementBillNo;
    }

    public void setStatementBillNo(String statementBillNo) {
        this.statementBillNo = statementBillNo;
    }
}
