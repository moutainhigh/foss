package com.deppon.foss.module.settlement.agency.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;

/**
 * 应收单参数dto
 * @author foss-pengzhen
 * @date 2012-11-6 上午10:47:19
 * @since
 * @version
 */
public class BillPayableAgencyDto implements Serializable {

    /**
     * 应收单参数类序列号
     */
    private static final long serialVersionUID = 501822517414376782L;

    /**
     * 主键
     */
    private String id;
    
    /**
	 * 应付单ID集合
	 */
	private List<String> ids;
	
	/**
	 * @author 218392 zhangyongxue 2015-07-27 16:14:08
	 * 其他应付类型
	 */
	private String otherPayType;
	
	/**
	 * 限制条件状态（审核时，设置为未审核；反审核时，设置为已审核）
	 */
	private String conApproveStatus;
	
	/**
	 * 查询页签
	 */
	private String queryByTab;

	/**
	 * 应付单列表
	 */
	private List<BillPayableEntity> billPayableEntityList = new ArrayList<BillPayableEntity>();
	
	/**
	 * 正单号
	 */
	private String sourceBillNo;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 客户信息
	 */
	private String customerCode;
	
	/**
	 * 客户名称
	 */
	private String customerName;
	
	/**
	 * 应付单号
	 */
	private String payableNo;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 是否红单
	 */
	private String isRedBack;
	
	/**
	 * 类型
	 */
	private String billType;
	
	/**
	 * 当前登录人编码
	 */
	private String empCode;
	
	/**
	 * 应付组织编码
	 */
	private String payableOrgCode;
	
	/**
	 * 应付组织名称
	 */
	private String payableOrgName;
	
	/**
	 * 审核状态
	 */
	private String approveStatus;
	
	/**
	 * 审核时间
	 */
	private Date auditDate;
	
	/**
	 * 应付页面最大显示条数
	 */
	private int maxShowNum;
	
	/**
     * 登录用户所属部门
     */
    private String generatingOrgCode;
    
    /**
     * 运输性质
     */
    private String productCode;
    
    /**
     * 运输性质
     */
    private String productName;
    /**
     * 应付单号集
     */
    private List<String> payableNos;
    
	/**
     * 空运正单号集
     */
    private List<String> sourceBillNos;

    /**
     * 应付单是否已经制作对账单
     */
    private String isGrenerateStatement;
    
    /**
     * 应付单的类型集合
     */
    private List<String> billTypeList;
    
    /**
	 * 应付单总条数
	 */
	private long totalNum;
	
	/**
	 * 应付单金额
	 */
	private BigDecimal amount;

	/**
	 * 应付单已核消金额
	 */
	private BigDecimal verifyAmount;
	
	/**
	 * 应付单未核消金额
	 */
	private BigDecimal unVerifyAmount;

	/**
	 * 应付单总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 应收单未核消总金额
	 */
	private BigDecimal unverifyTotalAmount;

	/**
	 * 应付单已核消总金额
	 */
	private BigDecimal verifyTotalAmount;
    
	/**
     * 应付单开始时间
     */
    private Date startBusinessDate;
    
	/**
     * 应付单结束时间
     */
    private Date endBusinessDate;

    /**
     * 修改人名称
     */
    private String modifyUserName;
    
    /**
     * 修改人编码
     */
    private String modifyUserCode;
    
    /**
     * 应收组织编码   
     */
    private String receiveDeptCode;
    
    /**
     * 应收组织名称
     */
    private String receiveDeptName;
    
    /**
     * 是否禁用
     */
    private String isDisable;
    
    /**
     * 版本号
     */
    private short versionNo;
    
    /**
     * 备注
     */
    private String notes;
	
    /**
	 * @return  the id
	 */
	public String getId() {
		return id;
	}


	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
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



	/**
	 * @return  the billPayableEntityList
	 */
	public List<BillPayableEntity> getBillPayableEntityList() {
		return billPayableEntityList;
	}

	
	/**
	 * @param billPayableEntityList the billPayableEntityList to set
	 */
	public void setBillPayableEntityList(
			List<BillPayableEntity> billPayableEntityList) {
		this.billPayableEntityList = billPayableEntityList;
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
	 * @return  the auditDate
	 */
	public Date getAuditDate() {
		return auditDate;
	}



	
	/**
	 * @param auditDate the auditDate to set
	 */
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
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
	 * @return  the payableNo
	 */
	public String getPayableNo() {
		return payableNo;
	}

	
	/**
	 * @param payableNo the payableNo to set
	 */
	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
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
	 * @return  the payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}


	
	/**
	 * @param payableOrgCode the payableOrgCode to set
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}


	
	/**
	 * @return  the payableOrgName
	 */
	public String getPayableOrgName() {
		return payableOrgName;
	}


	
	/**
	 * @param payableOrgName the payableOrgName to set
	 */
	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
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
	 * @return  the payableNos
	 */
	public List<String> getPayableNos() {
		return payableNos;
	}

	
	/**
	 * @param payableNos the payableNos to set
	 */
	public void setPayableNos(List<String> payableNos) {
		this.payableNos = payableNos;
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
	 * @return  the totalNum
	 */
	public long getTotalNum() {
		return totalNum;
	}

	
	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
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
	 * @return  the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	
	/**
	 * @return  the unverifyTotalAmount
	 */
	public BigDecimal getUnverifyTotalAmount() {
		return unverifyTotalAmount;
	}

	
	/**
	 * @param unverifyTotalAmount the unverifyTotalAmount to set
	 */
	public void setUnverifyTotalAmount(BigDecimal unverifyTotalAmount) {
		this.unverifyTotalAmount = unverifyTotalAmount;
	}

	
	/**
	 * @return  the verifyTotalAmount
	 */
	public BigDecimal getVerifyTotalAmount() {
		return verifyTotalAmount;
	}

	
	/**
	 * @param verifyTotalAmount the verifyTotalAmount to set
	 */
	public void setVerifyTotalAmount(BigDecimal verifyTotalAmount) {
		this.verifyTotalAmount = verifyTotalAmount;
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
	 * @return  the verifyAmount
	 */
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}


	
	/**
	 * @param verifyAmount the verifyAmount to set
	 */
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}




	
	
	/**
	 * @return  the unVerifyAmount
	 */
	public BigDecimal getUnVerifyAmount() {
		return unVerifyAmount;
	}



	
	/**
	 * @param unVerifyAmount the unVerifyAmount to set
	 */
	public void setUnVerifyAmount(BigDecimal unVerifyAmount) {
		this.unVerifyAmount = unVerifyAmount;
	}



	/**
	 * @return  the modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}


	
	/**
	 * @param modifyUserName the modifyUserName to set
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}


	
	
	/**
	 * @return  the modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}



	
	/**
	 * @param modifyUserCode the modifyUserCode to set
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
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
	 * @return  the isDisable
	 */
	public String getIsDisable() {
		return isDisable;
	}



	
	/**
	 * @param isDisable the isDisable to set
	 */
	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}



	/**
	 * @return  the receiveDeptCode
	 */
	public String getReceiveDeptCode() {
		return receiveDeptCode;
	}


	
	/**
	 * @param receiveDeptCode the receiveDeptCode to set
	 */
	public void setReceiveDeptCode(String receiveDeptCode) {
		this.receiveDeptCode = receiveDeptCode;
	}


	
	/**
	 * @return  the receiveDeptName
	 */
	public String getReceiveDeptName() {
		return receiveDeptName;
	}


	
	/**
	 * @param receiveDeptName the receiveDeptName to set
	 */
	public void setReceiveDeptName(String receiveDeptName) {
		this.receiveDeptName = receiveDeptName;
	}


	
	/**
	 * @return  the versionNo
	 */
	public short getVersionNo() {
		return versionNo;
	}


	
	/**
	 * @param versionNo the versionNo to set
	 */
	public void setVersionNo(short versionNo) {
		this.versionNo = versionNo;
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



	public String getOtherPayType() {
		return otherPayType;
	}



	public void setOtherPayType(String otherPayType) {
		this.otherPayType = otherPayType;
	}
    
	


}
