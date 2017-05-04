package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 网上支付应收单查询dto
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-19 下午2:42:04
 */
public class BillReceivableOnlineQueryDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5871418897281550177L;

	/**
	 * *****************************查询应收单变量****************************
	 */
	/**
	 * 查询类型："客户编码"、"运单号+手机号"、"日期"、"客户编码+运单号"
	 */
	private String queryType;

	/**
	 * 运单号集合
	 */
	private List<String> wayBillNos;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 手机号码(收货人或发货人手机号码)
	 */
	private List<String> telphoneNos;

	/**
	 * 手机号码
	 */
	private String telphoneNo;

	/**
	 * 收货人
	 */
	private String consigneeName;

	/**
	 * 货物名称
	 */
	private String cargoName;

	/**
	 * 业务开始日期
	 */
	private Date beginDate;

	/**
	 * 业务结束日期
	 */
	private Date endDate;

	/**
	 * 分页的页号
	 */
	private int pageNo;

	/**
	 * 分页每页大小
	 */
	private int pageSize;

	/**
	 * 付款方式：应收类型(1-到付、2-月结、3-临欠、4-网上支付)
	 */
	private String payWay;

	/**
	 * *****************************锁定应收单变量****************************
	 */
	/**
	 * 应收单号
	 */
	private String receivableNo;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * *****************************按应收单还款变量****************************
	 */
	/**
	 * 还款金额
	 */
	private BigDecimal amount;

	/**
	 * 在线支付编号
	 */
	private String onlineNo;

	/**
	 * *****************************常量设置****************************
	 */
	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 应收单类型
	 */
	private List<String> receivableTypeList;

	/**
	 * 应收单来源类型
	 */
	private String sourceType;

	/**
	 * 单据单号默认单号
	 */
	private String defaultBillNo;

	/**
	 * 应收单类型：到达应收
	 */
	private String receivableBillType;

	/**
	 * 对账单确认状态
	 */
	private String statementConfirmStatus;

	/**
	 * 更改单状态
	 */
	private List<String> waybillRFCStatus;

	/**
	 * 是否财务类更改单
	 */
	private String isFinanceChange;

	/**
	 * 网上营业厅锁定时间
	 */
	private int lockTime;

	/**
	 * 汇款账号 youkun
	 */
	private String remitAccount;

    /**
     * 费用类型 youkun
     */
    private String costType;
	/**
	 * @return queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param queryType
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * @return wayBillNos
	 */
	public List<String> getWayBillNos() {
		return wayBillNos;
	}

	/**
	 * @param wayBillNos
	 */
	public void setWayBillNos(List<String> wayBillNos) {
		this.wayBillNos = wayBillNos;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return telphoneNos
	 */
	public List<String> getTelphoneNos() {
		return telphoneNos;
	}

	/**
	 * @param telphoneNos
	 */
	public void setTelphoneNos(List<String> telphoneNos) {
		this.telphoneNos = telphoneNos;
	}

	/**
	 * @return telphoneNo
	 */
	public String getTelphoneNo() {
		return telphoneNo;
	}

	/**
	 * @param telphoneNo
	 */
	public void setTelphoneNo(String telphoneNo) {
		this.telphoneNo = telphoneNo;
	}

	/**
	 * @return consigneeName
	 */
	public String getConsigneeName() {
		return consigneeName;
	}

	/**
	 * @param consigneeName
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	/**
	 * @return cargoName
	 */
	public String getCargoName() {
		return cargoName;
	}

	/**
	 * @param cargoName
	 */
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	/**
	 * @return beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return payWay
	 */
	public String getPayWay() {
		return payWay;
	}

	/**
	 * @param payWay
	 */
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	/**
	 * @return receivableNo
	 */
	public String getReceivableNo() {
		return receivableNo;
	}

	/**
	 * @param receivableNo
	 */
	public void setReceivableNo(String receivableNo) {
		this.receivableNo = receivableNo;
	}

	/**
	 * @return accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * @param accountDate
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	/**
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return onlineNo
	 */
	public String getOnlineNo() {
		return onlineNo;
	}

	/**
	 * @param onlineNo
	 */
	public void setOnlineNo(String onlineNo) {
		this.onlineNo = onlineNo;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	/**
	 * @param isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	/**
	 * @return receivableTypeList
	 */
	public List<String> getReceivableTypeList() {
		return receivableTypeList;
	}

	/**
	 * @param receivableTypeList
	 */
	public void setReceivableTypeList(List<String> receivableTypeList) {
		this.receivableTypeList = receivableTypeList;
	}

	/**
	 * @return sourceType
	 */
	public String getSourceType() {
		return sourceType;
	}

	/**
	 * @param sourceType
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * @return defaultBillNo
	 */
	public String getDefaultBillNo() {
		return defaultBillNo;
	}

	/**
	 * @param defaultBillNo
	 */
	public void setDefaultBillNo(String defaultBillNo) {
		this.defaultBillNo = defaultBillNo;
	}

	/**
	 * @return receivableBillType
	 */
	public String getReceivableBillType() {
		return receivableBillType;
	}

	/**
	 * @param receivableBillType
	 */
	public void setReceivableBillType(String receivableBillType) {
		this.receivableBillType = receivableBillType;
	}

	/**
	 * @return statementConfirmStatus
	 */
	public String getStatementConfirmStatus() {
		return statementConfirmStatus;
	}

	/**
	 * @param statementConfirmStatus
	 */
	public void setStatementConfirmStatus(String statementConfirmStatus) {
		this.statementConfirmStatus = statementConfirmStatus;
	}

	/**
	 * @return waybillRFCStatus
	 */
	public List<String> getWaybillRFCStatus() {
		return waybillRFCStatus;
	}

	/**
	 * @param waybillRFCStatus
	 */
	public void setWaybillRFCStatus(List<String> waybillRFCStatus) {
		this.waybillRFCStatus = waybillRFCStatus;
	}

	/**
	 * @return isFinanceChange
	 */
	public String getIsFinanceChange() {
		return isFinanceChange;
	}

	/**
	 * @param isFinanceChange
	 */
	public void setIsFinanceChange(String isFinanceChange) {
		this.isFinanceChange = isFinanceChange;
	}

	/**
	 * @return lockTime
	 */
	public int getLockTime() {
		return lockTime;
	}

	/**
	 * @param lockTime
	 */
	public void setLockTime(int lockTime) {
		this.lockTime = lockTime;
	}

	public String getRemitAccount() {
		return remitAccount;
	}

	public void setRemitAccount(String remitAccount) {
		this.remitAccount = remitAccount;
	}

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }
}
