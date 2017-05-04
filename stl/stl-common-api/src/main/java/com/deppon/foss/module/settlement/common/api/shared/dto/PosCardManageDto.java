package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;

/**
 * POS刷卡DTO
 * 
 * @ClassName: PosCardManageDto
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-12 下午4:07:50
 * 
 */
public class PosCardManageDto {
	/**
	 * POS刷卡数据集合
	 */
	private List<PosCardEntity> posCardEntitys;
	
	/**
	 * 刷卡数据明细集合
	 */
	private List<PosCardDetailEntity> posCardDetailEntitys;

	/**
	 * 开始时间
	 */
	private Date periodBeginDate;

	/**
	 * 结束时间
	 */
	private Date periodEndDate;

	/**
	 * 交易流水号
	 */
	private String tradeSerialNo;

	/**
	 * 所属模块
	 */
	private String belongMoudleCode;

	/**
	 * 大区
	 */
	private String largeAreaCode;

	/**
	 * 小区
	 */
	private String smallAreaCode;

	/**
	 * 未使用金额
	 */
	private String unUsedAmount;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 单据号
	 */
	private String invoiceNo;

	/**
	 * 自定义导出列头
	 */
	private String[] arrayColumns;

	/**
	 * 自定义导出列中文名称
	 */
	private String[] arrayColumnNames;

	/**
	 * 当前登录人编码
	 */
	private String empCode;

	/**
	 * 流水号集合
	 */
	private String[] serialNos;

	/**
	 * 单据号
	 */
	private String[] invoices;
	/**
	 * 总行数
	 */
	private int count;
	
	/**
	 * 部门集合
	 */
	private List<String> deptCodes;

	/**
	 * 是否为资金部导出
	 */
	private String isExport;
	
	/**
	 * 查询类型
	 */
	private String queryTabType;
	
	/**
	 * 异常数据字符串
	 */ 
	private String exceptionMessage;
	
	/**
	 * 返回数据
	 */
	private String retMessage;
	
	/******** getter/setter ******/
	public String getRetMessage() {
		return retMessage;
	}
	
	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}
	public String getIsExport() {
		return isExport;
	}

	public void setIsExport(String isExport) {
		this.isExport = isExport;
	}

	public List<PosCardEntity> getPosCardEntitys() {
		return posCardEntitys;
	}

	public void setPosCardEntitys(List<PosCardEntity> posCardEntitys) {
		this.posCardEntitys = posCardEntitys;
	}

	public List<PosCardDetailEntity> getPosCardDetailEntitys() {
		return posCardDetailEntitys;
	}

	public void setPosCardDetailEntitys(
			List<PosCardDetailEntity> posCardDetailEntitys) {
		this.posCardDetailEntitys = posCardDetailEntitys;
	}

	public Date getPeriodBeginDate() {
		return periodBeginDate;
	}

	public void setPeriodBeginDate(Date periodBeginDate) {
		this.periodBeginDate = periodBeginDate;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Date getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}


	public String getBelongMoudleCode() {
		return belongMoudleCode;
	}

	public void setBelongMoudleCode(String belongMoudleCode) {
		this.belongMoudleCode = belongMoudleCode;
	}

	public String getLargeAreaCode() {
		return largeAreaCode;
	}

	public void setLargeAreaCode(String largeAreaCode) {
		this.largeAreaCode = largeAreaCode;
	}

	public String getSmallAreaCode() {
		return smallAreaCode;
	}

	public void setSmallAreaCode(String smallAreaCode) {
		this.smallAreaCode = smallAreaCode;
	}

	public String getUnUsedAmount() {
		return unUsedAmount;
	}

	public void setUnUsedAmount(String unUsedAmount) {
		this.unUsedAmount = unUsedAmount;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String[] getSerialNos() {
		return serialNos;
	}

	public void setSerialNos(String[] serialNos) {
		this.serialNos = serialNos;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String[] getInvoices() {
		return invoices;
	}

	public void setInvoices(String[] invoices) {
		this.invoices = invoices;
	}

	public String getTradeSerialNo() {
		return tradeSerialNo;
	}

	public void setTradeSerialNo(String tradeSerialNo) {
		this.tradeSerialNo = tradeSerialNo;
	}

	public List<String> getDeptCodes() {
		return deptCodes;
	}

	public void setDeptCodes(List<String> deptCodes) {
		this.deptCodes = deptCodes;
	}
	
	public String getQueryTabType() {
		return queryTabType;
	}

	public void setQueryTabType(String queryTabType) {
		this.queryTabType = queryTabType;
	}

	@Override
	public String toString() {
		return "PosCardManageDto [posCardEntitys=" + posCardEntitys
				+ ", posCardDetailEntitys=" + posCardDetailEntitys
				+ ", periodBeginDate=" + periodBeginDate + ", periodEndDate="
				+ periodEndDate + ", tradeSerialNo=" + tradeSerialNo
				+ ", belongMoudleCode=" + belongMoudleCode + ", largeAreaCode="
				+ largeAreaCode + ", smallAreaCode=" + smallAreaCode
				+ ", unUsedAmount=" + unUsedAmount + ", orgCode=" + orgCode
				+ ", invoiceNo=" + invoiceNo + ", empCode=" + empCode
				+ ", serialNos=" + Arrays.toString(serialNos) + ", invoices="
				+ Arrays.toString(invoices) + ", count=" + count
				+ ", deptCodes=" + deptCodes + ", isExport=" + isExport
				+ ", queryTabType=" + queryTabType + "]";
	}

	 

}
