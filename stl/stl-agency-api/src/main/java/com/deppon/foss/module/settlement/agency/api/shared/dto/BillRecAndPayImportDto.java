package com.deppon.foss.module.settlement.agency.api.shared.dto;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 导入其他应收、应付Dto
 * @author foss-pengzhen
 * @date 2012-11-13 下午7:33:24
 * @since
 * @version
 */
public class BillRecAndPayImportDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6351741994779582025L;

	/**
	 * id
	 */
	public String id;
	
	/**
	 * 导入操作文件
	 */
	private File file;
	
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * @author 218392 zhangyongxue 2015-0-30 16:16:50
	 * 其他应付类型
	 */
	private String otherPayType;
	
	/**
	 * 应收单实体
	 */
	private BillReceivableEntity billReceivableEntity;
	
	/**
	 * 应收单集合
	 */
	private List<BillReceivableEntity> billReceivableEntityList;
	
	/**
	 * 待导入的应收应付DTO
	 */
	private List<BillRecAndPayImportDto> billRecAndPayImportDtoList;
	
	/**
	 * 应付单实体
	 */
	private BillPayableEntity billPayableEntity;
	
	/**
	 * 应付单集合
	 */
	private List<BillPayableEntity> billPayableEntityList;
	
	/**
	 * 正单号
	 */
	private String sourceBillNo;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 代理名称
	 */
	private String customerName;
	
	/**
	 * 代理code
	 */
	private String customerCode;
	
	/**
	 * 收货部门名称
	 */
	private String origOrgName;
	
	/**
	 * 收货部门code
	 */
	private String origOrgCode;
	
	/**
	 * 是否存在
	 */
	private String isExist;
	
	/**
	 * 导入时间
	 */
	private Date date;
	
	/**
	 * 应收单金额
	 */
	private BigDecimal recAmount;
	
	/**
	 * 应付单金额
	 */
	private BigDecimal payAmount;
	
	/**
	 * 总条数
	 */
	private int total;
	
	/**
	 * 应收单总金额
	 */
	private BigDecimal totalRecAmount;
	
	/**
	 * 应付单总金额
	 */
	private BigDecimal totalPayAmount;
	
	/**
	 * 描述
	 */
	private String notes;
	
	/**
	 * 运输类型
	 */
	private String productCode;
	
	/**
	 * 运输类型
	 */
	private String productName;

	
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
	 * @return  the file
	 */
	public File getFile() {
		return file;
	}

	
	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	
	/**
	 * @return  the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	/**
	 * @return  the billReceivableEntity
	 */
	public BillReceivableEntity getBillReceivableEntity() {
		return billReceivableEntity;
	}

	
	/**
	 * @param billReceivableEntity the billReceivableEntity to set
	 */
	public void setBillReceivableEntity(BillReceivableEntity billReceivableEntity) {
		this.billReceivableEntity = billReceivableEntity;
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
	 * @return  the billRecAndPayImportDtoList
	 */
	public List<BillRecAndPayImportDto> getBillRecAndPayImportDtoList() {
		return billRecAndPayImportDtoList;
	}

	
	/**
	 * @param billRecAndPayImportDtoList the billRecAndPayImportDtoList to set
	 */
	public void setBillRecAndPayImportDtoList(
			List<BillRecAndPayImportDto> billRecAndPayImportDtoList) {
		this.billRecAndPayImportDtoList = billRecAndPayImportDtoList;
	}

	
	/**
	 * @return  the billPayableEntity
	 */
	public BillPayableEntity getBillPayableEntity() {
		return billPayableEntity;
	}

	
	/**
	 * @param billPayableEntity the billPayableEntity to set
	 */
	public void setBillPayableEntity(BillPayableEntity billPayableEntity) {
		this.billPayableEntity = billPayableEntity;
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
	 * @return  the isExist
	 */
	public String getIsExist() {
		return isExist;
	}


	
	/**
	 * @param isExist the isExist to set
	 */
	public void setIsExist(String isExist) {
		this.isExist = isExist;
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
	 * @return  the date
	 */
	public Date getDate() {
		return date;
	}

	
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	
	/**
	 * @return  the recAmount
	 */
	public BigDecimal getRecAmount() {
		return recAmount;
	}

	
	/**
	 * @param recAmount the recAmount to set
	 */
	public void setRecAmount(BigDecimal recAmount) {
		this.recAmount = recAmount;
	}

	
	/**
	 * @return  the payAmount
	 */
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	
	/**
	 * @param payAmount the payAmount to set
	 */
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	
	/**
	 * @return  the total
	 */
	public int getTotal() {
		return total;
	}

	
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	
	/**
	 * @return  the totalRecAmount
	 */
	public BigDecimal getTotalRecAmount() {
		return totalRecAmount;
	}

	
	/**
	 * @param totalRecAmount the totalRecAmount to set
	 */
	public void setTotalRecAmount(BigDecimal totalRecAmount) {
		this.totalRecAmount = totalRecAmount;
	}

	
	/**
	 * @return  the totalPayAmount
	 */
	public BigDecimal getTotalPayAmount() {
		return totalPayAmount;
	}

	
	/**
	 * @param totalPayAmount the totalPayAmount to set
	 */
	public void setTotalPayAmount(BigDecimal totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}

	
	
	/**
	 * @return  the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}


	
	/**
	 * @param origOrgName the origOrgName to set
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}


	
	/**
	 * @return  the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}


	
	/**
	 * @param origOrgCode the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
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
