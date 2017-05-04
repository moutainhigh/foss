package com.deppon.foss.module.settlement.agency.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 应收单参数dto
 * 
 * @author foss-pengzhen
 * @date 2012-10-12 下午3:42:05
 */
public class BillReceivableAgencyDto implements Serializable {

    /**
     * 应收单参数类序列号
     */
    private static final long serialVersionUID = 501822517414376782L;

    /**
     * 查询条件
     */
    private String queryByTab;
    
    /**
	 * 应收单ID集合
	 */
	private List<String> ids;
	
	/**
	 * 限制条件状态（审核时，设置为未审核；反审核时，设置为已审核）
	 */
	private String conApproveStatus;
	
	/**
	 * 审核状态
	 */
	private String approveStatus;

    /**
     * 应收单列表
     */
    private List<BillReceivableEntity> billReceivableEntityList = new ArrayList<BillReceivableEntity>();
    
    /**
     * 应收单业务开始时间
     */
    private Date startBusinessDate;

    /**
     * 应收单业务结束时间
     */
    private Date endBusinessDate;

    /**
     * 应收单记账开始时间
     */
    private Date startAccountDate;

    /**
     * 运输性质
     */
    private String productCode;
    
    /**
     * 运输性质名称
     */
    private String productName;
   
    /**
     * 应收单记账结束时间
     */
    private Date endAccountDate;

    /**
     * 应收单页面最大显示条数
     */
    private int maxShowNum;

    /**
     * 应收单客户编码
     */
    private String customerCode;
    
    /**
     * 应收单客户名称
     */
    private String customerName;

    /**
     * 登录用户所属部门
     */
    private String generatingOrgCode;
    
    /**
     * 应收单部门编码
     */
    private String receivableOrgCode;
    
    /**
     * 应收单部门名称
     */
    private String receivableOrgName;
    
    /**
     * 应收单号
     */
    private String receivableNo;

    /**
     * 应收单号集
     */
    private List<String> receivableNos;

    /**
     * 空运正单号
     */
    private String sourceBillNo;
    
    /**
     * 运单号
     */
    private String waybillNo;
    
    /**
     * 空运正单号集
     */
    private List<String> sourceBillNos;

    /**
     * 当前登录用户
     */
    private String empCode;
    
	/**
     * 应收单是否有效
     */
    private String active;

    /**
     * 应收单是否红单
     */
    private String isRedBack;

    /**
     * 应收单是否已经制作对账单
     */
    private String isGrenerateStatement;
    
    /**
     * 应收单的类型集合
     */
    private List<String> billTypeList;

    /**
	 * 应收单总条数
	 */
	private long totalNumRec;

	/**
	 * 应收单金额
	 */
	private BigDecimal amount;
	
	/**
	 * 应收单总金额
	 */
	private BigDecimal totalAmountRec;

	/**
	 * 应收单未核消总金额
	 */
	private BigDecimal unverifyTotalAmountRec;

	/**
	 * 应收单已核消总金额
	 */
	private BigDecimal verifyTotalAmountRec;
	
	/**
     * 备注
     */
    private String notes;

    private String billType;
	
	/**
	 * @return  the ids
	 */
	public List<String> getIds() {
		return ids;
	}

	
	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	
	/**
	 * @return  the conApproveStatus
	 */
	public String getConApproveStatus() {
		return conApproveStatus;
	}

	
	/**
	 * @param conApproveStatus the conApproveStatus to set
	 */
	public void setConApproveStatus(String conApproveStatus) {
		this.conApproveStatus = conApproveStatus;
	}

	
	/**
	 * @return  the billReceivableEntityList
	 */
	public List<BillReceivableEntity> getBillReceivableEntityList() {
		return billReceivableEntityList;
	}

	
	/**
	 * @param billReceivableEntityList the billReceivableEntityList to set
	 */
	public void setBillReceivableEntityList(
			List<BillReceivableEntity> billReceivableEntityList) {
		this.billReceivableEntityList = billReceivableEntityList;
	}

	
	
	/**
	 * @return  the receivableOrgCode
	 */
	public String getReceivableOrgCode() {
		return receivableOrgCode;
	}


	
	/**
	 * @param receivableOrgCode the receivableOrgCode to set
	 */
	public void setReceivableOrgCode(String receivableOrgCode) {
		this.receivableOrgCode = receivableOrgCode;
	}


	
	/**
	 * @return  the receivableOrgName
	 */
	public String getReceivableOrgName() {
		return receivableOrgName;
	}


	
	/**
	 * @param receivableOrgName the receivableOrgName to set
	 */
	public void setReceivableOrgName(String receivableOrgName) {
		this.receivableOrgName = receivableOrgName;
	}


	/**
	 * @return  the startBusinessDate
	 */
	public Date getStartBusinessDate() {
		return startBusinessDate;
	}

	
	/**
	 * @param startBusinessDate the startBusinessDate to set
	 */
	public void setStartBusinessDate(Date startBusinessDate) {
		this.startBusinessDate = startBusinessDate;
	}

	
	/**
	 * @return  the endBusinessDate
	 */
	public Date getEndBusinessDate() {
		return endBusinessDate;
	}

	
	/**
	 * @param endBusinessDate the endBusinessDate to set
	 */
	public void setEndBusinessDate(Date endBusinessDate) {
		this.endBusinessDate = endBusinessDate;
	}

	
	/**
	 * @return  the startAccountDate
	 */
	public Date getStartAccountDate() {
		return startAccountDate;
	}

	
	/**
	 * @param startAccountDate the startAccountDate to set
	 */
	public void setStartAccountDate(Date startAccountDate) {
		this.startAccountDate = startAccountDate;
	}

	
	/**
	 * @return  the endAccountDate
	 */
	public Date getEndAccountDate() {
		return endAccountDate;
	}

	
	/**
	 * @param endAccountDate the endAccountDate to set
	 */
	public void setEndAccountDate(Date endAccountDate) {
		this.endAccountDate = endAccountDate;
	}

	
	/**
	 * @return  the maxShowNum
	 */
	public int getMaxShowNum() {
		return maxShowNum;
	}

	
	/**
	 * @param maxShowNum the maxShowNum to set
	 */
	public void setMaxShowNum(int maxShowNum) {
		this.maxShowNum = maxShowNum;
	}

	
	/**
	 * @return  the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	/**
	 * @return  the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	/**
	 * @return  the generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	
	/**
	 * @param generatingOrgCode the generatingOrgCode to set
	 */
	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}



	
	/**
	 * @return  the billType
	 */
	public String getBillType() {
		return billType;
	}


	
	/**
	 * @param billType the billType to set
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}


	/**
	 * @return  the receivableNo
	 */
	public String getReceivableNo() {
		return receivableNo;
	}

	
	/**
	 * @param receivableNo the receivableNo to set
	 */
	public void setReceivableNo(String receivableNo) {
		this.receivableNo = receivableNo;
	}

	
	/**
	 * @return  the receivableNos
	 */
	public List<String> getReceivableNos() {
		return receivableNos;
	}

	
	/**
	 * @param receivableNos the receivableNos to set
	 */
	public void setReceivableNos(List<String> receivableNos) {
		this.receivableNos = receivableNos;
	}

	
	/**
	 * @return  the sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	
	/**
	 * @param sourceBillNo the sourceBillNo to set
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	
	/**
	 * @return  the sourceBillNos
	 */
	public List<String> getSourceBillNos() {
		return sourceBillNos;
	}

	
	/**
	 * @param sourceBillNos the sourceBillNos to set
	 */
	public void setSourceBillNos(List<String> sourceBillNos) {
		this.sourceBillNos = sourceBillNos;
	}

	
	
	/**
	 * @return  the approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}


	
	/**
	 * @param approveStatus the approveStatus to set
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}


	/**
	 * @return  the active
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
	/**
	 * @return  the isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	
	/**
	 * @param isRedBack the isRedBack to set
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	
	/**
	 * @return  the isGrenerateStatement
	 */
	public String getIsGrenerateStatement() {
		return isGrenerateStatement;
	}

	
	/**
	 * @param isGrenerateStatement the isGrenerateStatement to set
	 */
	public void setIsGrenerateStatement(String isGrenerateStatement) {
		this.isGrenerateStatement = isGrenerateStatement;
	}

	
	/**
	 * @return  the billTypeList
	 */
	public List<String> getBillTypeList() {
		return billTypeList;
	}

	
	/**
	 * @param billTypeList the billTypeList to set
	 */
	public void setBillTypeList(List<String> billTypeList) {
		this.billTypeList = billTypeList;
	}

	
	
	/**
	 * @return  the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}


	
	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}


	/**
	 * @return  the totalNumRec
	 */
	public long getTotalNumRec() {
		return totalNumRec;
	}

	
	/**
	 * @param totalNumRec the totalNumRec to set
	 */
	public void setTotalNumRec(long totalNumRec) {
		this.totalNumRec = totalNumRec;
	}

	
	/**
	 * @return  the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	/**
	 * @return  the totalAmountRec
	 */
	public BigDecimal getTotalAmountRec() {
		return totalAmountRec;
	}

	
	/**
	 * @param totalAmountRec the totalAmountRec to set
	 */
	public void setTotalAmountRec(BigDecimal totalAmountRec) {
		this.totalAmountRec = totalAmountRec;
	}

	
	/**
	 * @return  the unverifyTotalAmountRec
	 */
	public BigDecimal getUnverifyTotalAmountRec() {
		return unverifyTotalAmountRec;
	}

	
	/**
	 * @param unverifyTotalAmountRec the unverifyTotalAmountRec to set
	 */
	public void setUnverifyTotalAmountRec(BigDecimal unverifyTotalAmountRec) {
		this.unverifyTotalAmountRec = unverifyTotalAmountRec;
	}

	
	/**
	 * @return  the verifyTotalAmountRec
	 */
	public BigDecimal getVerifyTotalAmountRec() {
		return verifyTotalAmountRec;
	}

	
	/**
	 * @param verifyTotalAmountRec the verifyTotalAmountRec to set
	 */
	public void setVerifyTotalAmountRec(BigDecimal verifyTotalAmountRec) {
		this.verifyTotalAmountRec = verifyTotalAmountRec;
	}

	
	/**
	 * @return  the notes
	 */
	public String getNotes() {
		return notes;
	}

	
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}


	
	
	/**
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}


	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}


	/**
	 * @return  the queryByTab
	 */
	public String getQueryByTab() {
		return queryByTab;
	}


	
	/**
	 * @param queryByTab the queryByTab to set
	 */
	public void setQueryByTab(String queryByTab) {
		this.queryByTab = queryByTab;
	}


	public String getProductCode() {
		return productCode;
	}


	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
