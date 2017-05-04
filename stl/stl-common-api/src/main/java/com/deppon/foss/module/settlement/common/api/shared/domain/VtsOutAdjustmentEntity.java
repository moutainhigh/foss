package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 整车费用调整实体
 * @author 340403 foss
 * @createTime 2016年9月20日 下午3:08:20
 */
public class VtsOutAdjustmentEntity implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
    private String id;
    /**
     * 运单号
     */
    private String waybillNo;
    /**
     * 合同编码
     */
    private String contractCode;
    /**
     * 开单日期
     */
    private Date billTime;
    /**
     * 合同打印日期
     */
    private Date contractDate;
    /**
     * 员工工号
     */
    private String empCode;
    /**
     * 员工姓名
     */
    private String empName;
    /**
     * 修改人部门编码
     */
    private String modifyDeptCode;
    /**
     * 修改人部门名称
     */
    private String modifyDeptName;
    /**
     * 增减类型
     */
    private String adjustType;
    /**
     * 调整费用
     */

    private BigDecimal adjustAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo == null ? null : waybillNo.trim();
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode == null ? null : empCode.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getModifyDeptCode() {
        return modifyDeptCode;
    }

    public void setModifyDeptCode(String modifyDeptCode) {
        this.modifyDeptCode = modifyDeptCode == null ? null : modifyDeptCode.trim();
    }

    public String getModifyDeptName() {
        return modifyDeptName;
    }

    public void setModifyDeptName(String modifyDeptName) {
        this.modifyDeptName = modifyDeptName == null ? null : modifyDeptName.trim();
    }

    public String getAdjustType() {
        return adjustType;
    }

    public void setAdjustType(String adjustType) {
        this.adjustType = adjustType == null ? null : adjustType.trim();
    }

	public BigDecimal getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(BigDecimal adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

}