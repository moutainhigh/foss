/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 更改单返回dto
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午8:30:15
 */

public class BillWriteoffResultDto implements Serializable {

	/**
	 * 序列号
	 */

	public static final long serialVersionUID = -8298697590255789231L;

	// 
	/**
	 * 更改ID
	 */
	private String waybillChangeId;

	
	/**
	 *  运单号
	 */
	private String waybillNumber;

	 
	/**
	 * 更改内容
	 */
	private String changeItems;

	 
	/**
	 * 更改原因
	 */
	private String changeReason;

	 
	/**
	 * 更改前到付
	 */
	private BigDecimal oldToPayAmount;

	 
	/**
	 * 更改后到付
	 */
	private BigDecimal newToPayAmount;


	/**
	 * 更改前预付
	 */
	private BigDecimal oldPrePayAmount;

	
	/**
	 * 更改后预付
	 */
	private BigDecimal newPrePayAmount;

	
	/**
	 * 核销状态
	 */
	private String writeoffStatus;

	/**
	 * 核销人姓名
	 */
	private String writeOffEmpName;
		
	/**
	 * 核销时间
	 */
	private Date writeOffTime;

	/**
	 *  起草部门
	 */
	private String darftOrgName;

	 
	/**
	 * 起草人
	 */
	private String darfter;

	
	/**
	 *  备注
	 */
	private String writeOffNote;
	
	
	/**
	 * 变更金额
	 */
	private BigDecimal changeAmount;
	
	
	/**
	 * 打印次数
	 */
	private String  printCounts ;

	//---------快递代理新增
	//产品类型
	private String productType; 
	
	/**
	 * 查询财务类更改单列表
	 */
	public List<BillWriteoffResultDto> waybillRfcList;

	 
	/**
	 * 界面勾选的更改单编号
	 */
	private String selectWaybillChangeNos;

	 
	// 总条数
	private Long totalCount;
	
	
	//更改类型
	private String rfcType;
	
	//运单开单时间
	private Date createTime;
	
	//更改单发起时间
	private Date draftTime;
	
	//更改单受理时间
	private Date rfcOperateTime;
	
	//运单出库时间
	private Date signTime;

    //更改类型
    private String rfcSource;

    public String getRfcSource() {
        return rfcSource;
    }

    public void setRfcSource(String rfcSource) {
        this.rfcSource = rfcSource;
    }

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
		printCounts
	 */
	public String getPrintCounts() {
		return printCounts;
	}

	/**
	 * @param 
		printCounts
	 */
	public void setPrintCounts(String printCounts) {
		this.printCounts = printCounts;
	}

	/**
	 * @return 
		waybillRfcList
	 */
	public List<BillWriteoffResultDto> getWaybillRfcList() {
		return waybillRfcList;
	}

	/**
	 * @param 
		waybillRfcList
	 */
	public void setWaybillRfcList(List<BillWriteoffResultDto> waybillRfcList) {
		this.waybillRfcList = waybillRfcList;
	}
	/**
	 * @return 
		selectWaybillChangeNos
	 */
	public String getSelectWaybillChangeNos() {
		return selectWaybillChangeNos;
	}
	/**
	 * @param 
		selectWaybillChangeNos
	 */
	public void setSelectWaybillChangeNos(String selectWaybillChangeNos) {
		this.selectWaybillChangeNos = selectWaybillChangeNos;
	}

	/**
	   Get . 
	
	 * @return the totalCount
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	  Set 
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType
	 */
	public void setProductType(String productType) {
		this.productType = productType;
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
