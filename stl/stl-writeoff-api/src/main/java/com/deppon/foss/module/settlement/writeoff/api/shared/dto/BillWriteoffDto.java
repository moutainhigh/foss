package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 更改单DTO
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午8:30:15
 */
public class BillWriteoffDto implements Serializable {

	/**
	 * 更改单序列号
	 */
	private static final long serialVersionUID = -5450085240043617968L;

	// 更改单ID
	private String waybillChangeId;

	// 更改单受理开始日期
	private Date acceptStartDate;

	// 更改单受理结速日期
	private Date acceptEndDate;

	// 部门
	private String department;

	// 大区
	private String largeArea;
	
	/**
	 * 大区编码
	 */
	private String largeAreaCode;

	// 小区名称
	private String smallArea;
	
	/**
	 * 小区编码编号
	 */
	private String smallAreaCode;

	// 核销状态
	private String writeoffStatus;

	// 运单号
	private String waybillNumber;

	// 更改内容
	private String changeItems;

	// 更改原因
	private String changeReason;

	// 更改前到付
	private BigDecimal oldToPayAmount;

	// 更改后到付
	private BigDecimal newToPayAmount;

	// 更改前预付
	private BigDecimal oldPrePayAmount;

	// 更改后预付
	private BigDecimal newPrePayAmount;
	/**
	 * 核销人姓名
	 */
	private String writeOffEmpName;
		
	/**
	 * 核销时间
	 */
	private Date writeOffTime;

	/**
	 * 起草部门名称
	 */
	private String darftOrgName;
	
	/**
	 * 起草部门编码
	 */
	private String darftOrgCode;
	
	// 起草人
	private String darfter;

	// 界面核销时，勾选的更改单
	private List<String> selectWaybillChangeNos;

	// 界面输入的多个运单
	private List<String> waybillNos;

	// 界面按钮,核销通过
	private String writeoffSuccess;

	// 界面按钮，核销不通过
	private String writeoffFail;

	// 界面按钮，反核销
	private String reverse;
	
	//备注
	private String notes;
	
	
	//备注
	private String writeOffNote;
	
	
	
	/**
	 * 变更金额
	 */
	private BigDecimal changeAmount;
	
	
	/**
	 * 部门集合
	 */
	private List<String> collectionOrgCodeList;
	
	
	
	/**
	 * 更改单导出自定义导出列头
	 */
	private String[] arrayColumns;

	/**
	 * 更改单导出自定义导出列中文名称
	 */
	private String[] arrayColumnNames;
	
	/**
	 * 当前登录用户员工编码
	 */
	private String empCode;
	
	
	
	/**
	 * 更改类型
	 */
	private String rfcType;

	
	//--------------快递代理新增属性
	/**
	 * 产品类型
	 */
	private List<String> productTypeList;
	
	
	
	//运单开单时间
	private Date createTime;
	
	//更改单发起时间
	private Date draftTime;
	
	//更改单受理时间
	private Date rfcOperateTime;
	
	//运单出库时间
	private Date signTime;
	
	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getDraftTime() {
		return draftTime;
	}


	public void setDraftTime(Date draftTime) {
		this.draftTime = draftTime;
	}


	public Date getRfcOperateTime() {
		return rfcOperateTime;
	}


	public void setRfcOperateTime(Date rfcOperateTime) {
		this.rfcOperateTime = rfcOperateTime;
	}


	public Date getSignTime() {
		return signTime;
	}


	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * @return rfcType
	 */
	public String getRfcType() {
		return rfcType;
	}


	/**
	 * @param rfcType
	 */
	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}


	/**
	 * @return 
		waybillChangeId
	 */
	public String getWaybillChangeId() {
		return waybillChangeId;
	}

	
	/**
	 * @param 
		waybillChangeId
	 */
	public void setWaybillChangeId(String waybillChangeId) {
		this.waybillChangeId = waybillChangeId;
	}

	
	/**
	 * @return 
		acceptStartDate
	 */
	public Date getAcceptStartDate() {
		return acceptStartDate;
	}

	
	/**
	 * @param 
		acceptStartDate
	 */
	public void setAcceptStartDate(Date acceptStartDate) {
		this.acceptStartDate = acceptStartDate;
	}

	
	/**
	 * @return 
		acceptEndDate
	 */
	public Date getAcceptEndDate() {
		return acceptEndDate;
	}

	
	/**
	 * @param 
		acceptEndDate
	 */
	public void setAcceptEndDate(Date acceptEndDate) {
		this.acceptEndDate = acceptEndDate;
	}

	
	/**
	 * @return 
		department
	 */
	public String getDepartment() {
		return department;
	}

	
	/**
	 * @param 
		department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	
	/**
	 * @return 
		largeArea
	 */
	public String getLargeArea() {
		return largeArea;
	}

	
	/**
	 * @param 
		largeArea
	 */
	public void setLargeArea(String largeArea) {
		this.largeArea = largeArea;
	}

	
	/**
	 * @return 
		largeAreaCode
	 */
	public String getLargeAreaCode() {
		return largeAreaCode;
	}

	
	/**
	 * @param 
		largeAreaCode
	 */
	public void setLargeAreaCode(String largeAreaCode) {
		this.largeAreaCode = largeAreaCode;
	}

	
	/**
	 * @return 
		smallArea
	 */
	public String getSmallArea() {
		return smallArea;
	}

	
	/**
	 * @param 
		smallArea
	 */
	public void setSmallArea(String smallArea) {
		this.smallArea = smallArea;
	}

	
	/**
	 * @return 
		smallAreaCode
	 */
	public String getSmallAreaCode() {
		return smallAreaCode;
	}

	
	/**
	 * @param 
		smallAreaCode
	 */
	public void setSmallAreaCode(String smallAreaCode) {
		this.smallAreaCode = smallAreaCode;
	}

	
	/**
	 * @return 
		writeoffStatus
	 */
	public String getWriteoffStatus() {
		return writeoffStatus;
	}

	
	/**
	 * @param 
		writeoffStatus
	 */
	public void setWriteoffStatus(String writeoffStatus) {
		this.writeoffStatus = writeoffStatus;
	}

	
	/**
	 * @return 
		waybillNumber
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}

	
	/**
	 * @param 
		waybillNumber
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	
	/**
	 * @return 
		changeItems
	 */
	public String getChangeItems() {
		return changeItems;
	}

	
	/**
	 * @param 
		changeItems
	 */
	public void setChangeItems(String changeItems) {
		this.changeItems = changeItems;
	}

	
	/**
	 * @return 
		changeReason
	 */
	public String getChangeReason() {
		return changeReason;
	}

	
	/**
	 * @param 
		changeReason
	 */
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	
	/**
	 * @return 
		oldToPayAmount
	 */
	public BigDecimal getOldToPayAmount() {
		return oldToPayAmount;
	}

	
	/**
	 * @param 
		oldToPayAmount
	 */
	public void setOldToPayAmount(BigDecimal oldToPayAmount) {
		this.oldToPayAmount = oldToPayAmount;
	}

	
	/**
	 * @return 
		newToPayAmount
	 */
	public BigDecimal getNewToPayAmount() {
		return newToPayAmount;
	}

	
	/**
	 * @param 
		newToPayAmount
	 */
	public void setNewToPayAmount(BigDecimal newToPayAmount) {
		this.newToPayAmount = newToPayAmount;
	}

	
	/**
	 * @return 
		oldPrePayAmount
	 */
	public BigDecimal getOldPrePayAmount() {
		return oldPrePayAmount;
	}

	
	/**
	 * @param 
		oldPrePayAmount
	 */
	public void setOldPrePayAmount(BigDecimal oldPrePayAmount) {
		this.oldPrePayAmount = oldPrePayAmount;
	}

	
	/**
	 * @return 
		newPrePayAmount
	 */
	public BigDecimal getNewPrePayAmount() {
		return newPrePayAmount;
	}

	
	/**
	 * @param 
		newPrePayAmount
	 */
	public void setNewPrePayAmount(BigDecimal newPrePayAmount) {
		this.newPrePayAmount = newPrePayAmount;
	}

	
	/**
	 * @return 
		darftOrgName
	 */
	public String getDarftOrgName() {
		return darftOrgName;
	}

	
	/**
	 * @param 
		darftOrgName
	 */
	public void setDarftOrgName(String darftOrgName) {
		this.darftOrgName = darftOrgName;
	}

	
	/**
	 * @return 
		darftOrgCode
	 */
	public String getDarftOrgCode() {
		return darftOrgCode;
	}

	
	/**
	 * @param 
		darftOrgCode
	 */
	public void setDarftOrgCode(String darftOrgCode) {
		this.darftOrgCode = darftOrgCode;
	}

	
	/**
	 * @return 
		darfter
	 */
	public String getDarfter() {
		return darfter;
	}

	
	/**
	 * @param 
		darfter
	 */
	public void setDarfter(String darfter) {
		this.darfter = darfter;
	}

	
	/**
	 * @return 
		selectWaybillChangeNos
	 */
	public List<String> getSelectWaybillChangeNos() {
		return selectWaybillChangeNos;
	}

	
	/**
	 * @param 
		selectWaybillChangeNos
	 */
	public void setSelectWaybillChangeNos(List<String> selectWaybillChangeNos) {
		this.selectWaybillChangeNos = selectWaybillChangeNos;
	}

	
	/**
	 * @return 
		waybillNos
	 */
	public List<String> getWaybillNos() {
		return waybillNos;
	}

	
	/**
	 * @param 
		waybillNos
	 */
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	
	/**
	 * @return 
		writeoffSuccess
	 */
	public String getWriteoffSuccess() {
		return writeoffSuccess;
	}

	
	/**
	 * @param 
		writeoffSuccess
	 */
	public void setWriteoffSuccess(String writeoffSuccess) {
		this.writeoffSuccess = writeoffSuccess;
	}

	
	/**
	 * @return 
		writeoffFail
	 */
	public String getWriteoffFail() {
		return writeoffFail;
	}

	
	/**
	 * @param 
		writeoffFail
	 */
	public void setWriteoffFail(String writeoffFail) {
		this.writeoffFail = writeoffFail;
	}

	
	/**
	 * @return 
		reverse
	 */
	public String getReverse() {
		return reverse;
	}

	
	/**
	 * @param 
		reverse
	 */
	public void setReverse(String reverse) {
		this.reverse = reverse;
	}

	
	/**
	 * @return 
		notes
	 */
	public String getNotes() {
		return notes;
	}

	
	/**
	 * @param 
		notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	
	/**
	 * @return 
		writeOffNote
	 */
	public String getWriteOffNote() {
		return writeOffNote;
	}

	
	/**
	 * @param 
		writeOffNote
	 */
	public void setWriteOffNote(String writeOffNote) {
		this.writeOffNote = writeOffNote;
	}

	
	/**
	 * @return 
		changeAmount
	 */
	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	
	/**
	 * @param 
		changeAmount
	 */
	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}

	
	/**
	 * @return 
		collectionOrgCodeList
	 */
	public List<String> getCollectionOrgCodeList() {
		return collectionOrgCodeList;
	}

	
	/**
	 * @param 
		collectionOrgCodeList
	 */
	public void setCollectionOrgCodeList(List<String> collectionOrgCodeList) {
		this.collectionOrgCodeList = collectionOrgCodeList;
	}

	
	/**
	 * @return 
		arrayColumns
	 */
	public String[] getArrayColumns() {
		return arrayColumns;
	}

	
	/**
	 * @param 
		arrayColumns
	 */
	public void setArrayColumns(String[] arrayColumns) {
		this.arrayColumns = arrayColumns;
	}

	
	/**
	 * @return 
		arrayColumnNames
	 */
	public String[] getArrayColumnNames() {
		return arrayColumnNames;
	}

	
	/**
	 * @param 
		arrayColumnNames
	 */
	public void setArrayColumnNames(String[] arrayColumnNames) {
		this.arrayColumnNames = arrayColumnNames;
	}

	
	/**
	 * @return 
		empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	
	/**
	 * @param 
		empCode
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}


	/**
	 * @return productTypeList
	 */
	public List<String> getProductTypeList() {
		return productTypeList;
	}


	/**
	 * @param productTypeList
	 */
	public void setProductTypeList(List<String> productTypeList) {
		this.productTypeList = productTypeList;
	}
	
	public String getWriteOffEmpName() {
		return writeOffEmpName;
	}


	public void setWriteOffEmpName(String writeOffEmpName) {
		this.writeOffEmpName = writeOffEmpName;
	}


	public Date getWriteOffTime() {
		return writeOffTime;
	}


	public void setWriteOffTime(Date writeOffTime) {
		this.writeOffTime = writeOffTime;
	}
}
