package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 更改单打印次数，添加实体（和接送货的关联）
 * @author 095793-foss-LiQin
 * @date 2013-4-25 下午3:15:49
 */
public class ModifyBillPrintInfoDto extends BaseEntity {
	/**
     * 序列化版本号
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 打应类型
	 */
    private String printType;
    /**
     * 运单id
     */
    private String waybillId;
    /**
     * 运单编码
     */
    private String waybillNo;
    /**
     * 答应时间
     */
    private Integer printTimes;
    /**
     * 打印人编码
     */
    private String printUserCode;
    /**
     * 打印人
     */
    private String printUser;
    /**
     * 打印人组织编码
     */
    private String printOrgCode;
    /**
     * 打印人组织
     */
    private String printOrg;
    /**
     * 打印时间
     */
    private Date printTime;
	/**
	 * @return the printType
	 */
	public String getPrintType() {
		return printType;
	}
	/**
	 * @return waybillId
	 */
	public String getWaybillId() {
		return waybillId;
	}
	/**
	 * @param waybillId
	 */
	public void setWaybillId(String waybillId) {
		this.waybillId = waybillId;
	}
	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return printTimes
	 */
	public Integer getPrintTimes() {
		return printTimes;
	}
	/**
	 * @param printTimes
	 */
	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}
	/**
	 * @return printUserCode
	 */
	public String getPrintUserCode() {
		return printUserCode;
	}
	/**
	 * @param printUserCode
	 */
	public void setPrintUserCode(String printUserCode) {
		this.printUserCode = printUserCode;
	}
	/**
	 * @return printUser
	 */
	public String getPrintUser() {
		return printUser;
	}
	/**
	 * @param printUser
	 */
	public void setPrintUser(String printUser) {
		this.printUser = printUser;
	}
	/**
	 * @return printOrgCode
	 */
	public String getPrintOrgCode() {
		return printOrgCode;
	}
	/**
	 * @param printOrgCode
	 */
	public void setPrintOrgCode(String printOrgCode) {
		this.printOrgCode = printOrgCode;
	}
	/**
	 * @return printOrg
	 */
	public String getPrintOrg() {
		return printOrg;
	}
	/**
	 * @param printOrg
	 */
	public void setPrintOrg(String printOrg) {
		this.printOrg = printOrg;
	}
	/**
	 * @return printTime
	 */
	public Date getPrintTime() {
		return printTime;
	}
	/**
	 * @param printTime
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}
	/**
	 * @param printType
	 */
	public void setPrintType(String printType) {
		this.printType = printType;
	}
}
