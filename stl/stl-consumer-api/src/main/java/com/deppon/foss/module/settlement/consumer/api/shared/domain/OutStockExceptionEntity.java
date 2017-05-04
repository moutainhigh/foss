package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 异常出库类
 * @author foss-qiaolifeng
 * @date 2012-12-17 下午4:00:47
 */
public class OutStockExceptionEntity extends BaseEntity{
    
	/**
	 * 异常出库类序列号
	 */
	private static final long serialVersionUID = 8786993015789574971L;


    /**
     * 异常出库单据号
     */
    private String outStockBillNo;

    /**
     * 异常出库晕单号
     */
    private String waybillNo;

    /**
     * 异常出库类型
     */
    private String exceptionType;

    /**
     * 创建者编码
     */
    private String createUserCode;

    /**
     * 创建者名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

	
	/**
	 * @return outStockBillNo
	 */
	public String getOutStockBillNo() {
		return outStockBillNo;
	}

	/**
	 * @param  outStockBillNo  
	 */
	public void setOutStockBillNo(String outStockBillNo) {
		this.outStockBillNo = outStockBillNo;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param  waybillNo  
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return exceptionType
	 */
	public String getExceptionType() {
		return exceptionType;
	}

	/**
	 * @param  exceptionType  
	 */
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	/**
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param  createUserCode  
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param  createUserName  
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param  createTime  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
