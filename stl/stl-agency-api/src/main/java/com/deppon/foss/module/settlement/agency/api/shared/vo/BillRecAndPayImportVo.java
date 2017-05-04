package com.deppon.foss.module.settlement.agency.api.shared.vo;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.agency.api.shared.dto.BillRecAndPayImportDto;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 导入其他应收、应付VO
 * @author foss-pengzhen
 * @date 2012-11-13 下午7:33:24
 * @since
 * @version
 */
public class BillRecAndPayImportVo implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -850899033430677924L;

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
	 * 注入DTO
	 */
	private BillRecAndPayImportDto billRecAndPayImportDto;

	/**
	 * DTO集合
	 */
	private List<BillRecAndPayImportDto> recAndPaylist;

	/**
	 * 应收单实体
	 */
	private BillReceivableEntity billReceivableEntity;
	
	/**
	 * 应收单集合
	 */
	private List<BillReceivableEntity> billReceivableEntityList;
	
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
	 * 代理名称
	 */
	private String customerName;
	
	/**
	 * 代理code
	 */
	private String customerCode;
	
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
	 * @return  the billRecAndPayImportDto
	 */
	public BillRecAndPayImportDto getBillRecAndPayImportDto() {
		return billRecAndPayImportDto;
	}

	
	/**
	 * @param billRecAndPayImportDto the billRecAndPayImportDto to set
	 */
	public void setBillRecAndPayImportDto(
			BillRecAndPayImportDto billRecAndPayImportDto) {
		this.billRecAndPayImportDto = billRecAndPayImportDto;
	}

	
	/**
	 * @return  the recAndPaylist
	 */
	public List<BillRecAndPayImportDto> getRecAndPaylist() {
		return recAndPaylist;
	}

	
	/**
	 * @param recAndPaylist the recAndPaylist to set
	 */
	public void setRecAndPaylist(List<BillRecAndPayImportDto> recAndPaylist) {
		this.recAndPaylist = recAndPaylist;
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
	
}
