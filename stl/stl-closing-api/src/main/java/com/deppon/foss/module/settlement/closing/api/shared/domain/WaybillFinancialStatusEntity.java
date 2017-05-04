package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 财务完结-运单财务状态实体
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-3 上午11:35:03
 * @since
 * @version
 */
public class WaybillFinancialStatusEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6933788324147982662L;

    /**
     * 运单号
     */
    private String waybillNo;

    /**
     * 时间
     */
    private Date accountDate;

    /**
     * 财务完结计数器
     */
    private Integer fcCount;

	
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
	 * @return  the accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	
	/**
	 * @param accountDate the accountDate to set
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	
	/**
	 * @return  the fcCount
	 */
	public Integer getFcCount() {
		return fcCount;
	}

	
	/**
	 * @param fcCount the fcCount to set
	 */
	public void setFcCount(Integer fcCount) {
		this.fcCount = fcCount;
	}

    
}
