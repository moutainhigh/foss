package com.deppon.crm.module.client.sync.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同-部门关系对应表
 */
public class CustContractdept extends BaseEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5867383679876758744L;

	/**
     * 主键ID
     */
    private BigDecimal fid;

    /**
     * 创建时间
     */
    private Date fcreatetime;

    /**
     * 创建人
     */
    private BigDecimal fcreateuserid;

    /**
     * 最后修改时间
     */
    private Date flastupdatetime;

    /**
     * 最后修改人
     */
    private BigDecimal flastmodifyuserid;

    /**
     * 对应合同id
     */
    private BigDecimal fcontractid;

    /**
     * 对应部门id
     */
    private BigDecimal fdeptid;

    /**
     * 开始时间
     */
    private Date fbegintime;

    /**
     * 结束时间
     */
    private Date fendtime;

    /**
     * 工作流号
     */
    private String fworkflowid;

    /**
     * 审批人
     */
    private String fapprovalman;

    /**
     * 工作流类型
     */
    private String fworkflowtype;

    /**
     * 是否归属部门
     */
    private String fisdept;

    /**
     * 状态 有效、无效
     */
    private String fstate;

    /**
     * OA审批状态
     *
     */
    private String fapprovalstate;

    /**
     * 会员所属部门的标杆编码
     */
 	private String fdeptidStandardcode;

     public String getFdeptidStandardcode() {
 		return fdeptidStandardcode;
 	}

 	public void setFdeptidStandardcode(String fdeptidStandardcode) {
 		this.fdeptidStandardcode = fdeptidStandardcode;
 	}

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FID
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFid() {
        return fid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FID
     *
     * @param fid the value for DEVELOP.T_CUST_CONTRACTDEPT.FID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFid(BigDecimal fid) {
        this.fid = fid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FCREATETIME
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FCREATETIME
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public Date getFcreatetime() {
        return fcreatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FCREATETIME
     *
     * @param fcreatetime the value for DEVELOP.T_CUST_CONTRACTDEPT.FCREATETIME
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FCREATEUSERID
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FCREATEUSERID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFcreateuserid() {
        return fcreateuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FCREATEUSERID
     *
     * @param fcreateuserid the value for DEVELOP.T_CUST_CONTRACTDEPT.FCREATEUSERID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFcreateuserid(BigDecimal fcreateuserid) {
        this.fcreateuserid = fcreateuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FLASTUPDATETIME
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FLASTUPDATETIME
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public Date getFlastupdatetime() {
        return flastupdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FLASTUPDATETIME
     *
     * @param flastupdatetime the value for DEVELOP.T_CUST_CONTRACTDEPT.FLASTUPDATETIME
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFlastupdatetime(Date flastupdatetime) {
        this.flastupdatetime = flastupdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FLASTMODIFYUSERID
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FLASTMODIFYUSERID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFlastmodifyuserid() {
        return flastmodifyuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FLASTMODIFYUSERID
     *
     * @param flastmodifyuserid the value for DEVELOP.T_CUST_CONTRACTDEPT.FLASTMODIFYUSERID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFlastmodifyuserid(BigDecimal flastmodifyuserid) {
        this.flastmodifyuserid = flastmodifyuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FCONTRACTID
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FCONTRACTID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFcontractid() {
        return fcontractid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FCONTRACTID
     *
     * @param fcontractid the value for DEVELOP.T_CUST_CONTRACTDEPT.FCONTRACTID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFcontractid(BigDecimal fcontractid) {
        this.fcontractid = fcontractid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FDEPTID
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FDEPTID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFdeptid() {
        return fdeptid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FDEPTID
     *
     * @param fdeptid the value for DEVELOP.T_CUST_CONTRACTDEPT.FDEPTID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFdeptid(BigDecimal fdeptid) {
        this.fdeptid = fdeptid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FBEGINTIME
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FBEGINTIME
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public Date getFbegintime() {
        return fbegintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FBEGINTIME
     *
     * @param fbegintime the value for DEVELOP.T_CUST_CONTRACTDEPT.FBEGINTIME
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFbegintime(Date fbegintime) {
        this.fbegintime = fbegintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FENDTIME
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FENDTIME
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public Date getFendtime() {
        return fendtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FENDTIME
     *
     * @param fendtime the value for DEVELOP.T_CUST_CONTRACTDEPT.FENDTIME
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFendtime(Date fendtime) {
        this.fendtime = fendtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FWORKFLOWID
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FWORKFLOWID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public String getFworkflowid() {
        return fworkflowid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FWORKFLOWID
     *
     * @param fworkflowid the value for DEVELOP.T_CUST_CONTRACTDEPT.FWORKFLOWID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFworkflowid(String fworkflowid) {
        this.fworkflowid = fworkflowid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FAPPROVALMAN
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FAPPROVALMAN
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public String getFapprovalman() {
        return fapprovalman;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FAPPROVALMAN
     *
     * @param fapprovalman the value for DEVELOP.T_CUST_CONTRACTDEPT.FAPPROVALMAN
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFapprovalman(String fapprovalman) {
        this.fapprovalman = fapprovalman;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FWORKFLOWTYPE
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FWORKFLOWTYPE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public String getFworkflowtype() {
        return fworkflowtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FWORKFLOWTYPE
     *
     * @param fworkflowtype the value for DEVELOP.T_CUST_CONTRACTDEPT.FWORKFLOWTYPE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFworkflowtype(String fworkflowtype) {
        this.fworkflowtype = fworkflowtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FISDEPT
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FISDEPT
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public String getFisdept() {
        return fisdept;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FISDEPT
     *
     * @param fisdept the value for DEVELOP.T_CUST_CONTRACTDEPT.FISDEPT
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFisdept(String fisdept) {
        this.fisdept = fisdept;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FSTATE
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FSTATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public String getFstate() {
        return fstate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FSTATE
     *
     * @param fstate the value for DEVELOP.T_CUST_CONTRACTDEPT.FSTATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFstate(String fstate) {
        this.fstate = fstate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FAPPROVALSTATE
     *
     * @return the value of DEVELOP.T_CUST_CONTRACTDEPT.FAPPROVALSTATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public String getFapprovalstate() {
        return fapprovalstate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_CONTRACTDEPT.FAPPROVALSTATE
     *
     * @param fapprovalstate the value for DEVELOP.T_CUST_CONTRACTDEPT.FAPPROVALSTATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFapprovalstate(String fapprovalstate) {
        this.fapprovalstate = fapprovalstate;
    }
}