package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 在线支付接口dto
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-26 下午8:14:29
 */
public class OnlionMonitorReportDto implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 7004774092402621436L;

	/**
	 * 对账单号
	 */
	private String[] accoutNumber;

	/**
	 * 运单号集合
	 */
	private String[] billNumber;

	/**
	 * 支付类型
	 */
	private String billtype;

	/**
	 * 部门编码
	 */
	private String deptCode;

	/**
	 * 结束日期
	 */
	private Date endDate;

	/**
	 * 起始日期
	 */
	private Date startDate;

	/**
	 * 付款状态
	 */
	private String payState;

	/**
	 * 核销状态
	 */
	private String refundState;

	/**
	 * 查询类型
	 */
	private String searchType;

	/**
	 * @return accoutNumber
	 */
	public String[] getAccoutNumber() {
		return accoutNumber;
	}

	/**
	 * @param accoutNumber
	 */
	public void setAccoutNumber(String[] accoutNumber) {
		this.accoutNumber = accoutNumber;
	}

	/**
	 * @return billNumber
	 */
	public String[] getBillNumber() {
		return billNumber;
	}

	/**
	 * @param billNumber
	 */
	public void setBillNumber(String[] billNumber) {
		this.billNumber = billNumber;
	}

	/**
	 * @return billtype
	 */
	public String getBilltype() {
		return billtype;
	}

	/**
	 * @param billtype
	 */
	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	/**
	 * @return deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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
	 * @return startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return payState
	 */
	public String getPayState() {
		return payState;
	}

	/**
	 * @param payState
	 */
	public void setPayState(String payState) {
		this.payState = payState;
	}

	/**
	 * @return refundState
	 */
	public String getRefundState() {
		return refundState;
	}

	/**
	 * @param refundState
	 */
	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	/**
	 * @return searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

}
