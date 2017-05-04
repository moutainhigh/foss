package com.deppon.foss.dubbo.crm.api.define.masterdata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 联系人-接送地址-关系
 *
 */
public class CustPreferenceaddress extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6436410599149336804L;

	/**
	 * 主键ID
	 */
	private BigDecimal fid;

	/**
	 *创建人
	 */
	private BigDecimal fcreateuserid;

	/**
	 *创建时间
	 */
	private Date fcreatetime;

	/**
	 * 最后修改人
	 */
	private BigDecimal flastupdateuserid;

	/**
	 *最后修改时间
	 */
	private Date flastupdatetime;

	/**
	 * 接送货地址id
	 */
	private BigDecimal fshuttleaddressid;

	/**
	 * 联系人id
	 */
	private BigDecimal flinkmanid;

	/**
	 * 地址类型
	 */
	private String faddresstype;

	/**
	 * 地址
	 */
	private String faddress;

	/**
	 * 偏好起始时间
	 */
	private Date fstarttime;

	/**
	 * 偏好结束时间
	 */
	private Date fendtime;

	/**
	 * 发票要求
	 */
	private String fbillrequest;

	/**
	 * 停车费用
	 */
	private String fhasstopcost;

	/**
	 * 付款方式
	 */
	private String fpaytype;

	/**
	 * 是否送货上楼
	 */
	private String fissendtofloor;

	/**
	 * 其它要求
	 */
	private String fotherneed;

	/**
	 * 是否主地址
	 */
	private String fismainaddress;

	/**
	 * 状态 正常：0；  审批中：1  ；无效 ：2；
	 */
	private BigDecimal fstatus;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FID
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FID
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public BigDecimal getFid() {
		return fid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FID
	 * 
	 * @param fid
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FID
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFid(BigDecimal fid) {
		this.fid = fid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FCREATEUSERID
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FCREATEUSERID
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public BigDecimal getFcreateuserid() {
		return fcreateuserid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FCREATEUSERID
	 * 
	 * @param fcreateuserid
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FCREATEUSERID
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFcreateuserid(BigDecimal fcreateuserid) {
		this.fcreateuserid = fcreateuserid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FCREATETIME
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FCREATETIME
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public Date getFcreatetime() {
		return fcreatetime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FCREATETIME
	 * 
	 * @param fcreatetime
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FCREATETIME
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FLASTUPDATEUSERID
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FLASTUPDATEUSERID
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public BigDecimal getFlastupdateuserid() {
		return flastupdateuserid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FLASTUPDATEUSERID
	 * 
	 * @param flastupdateuserid
	 *            the value for
	 *            DEVELOP.T_CUST_PREFERENCEADDRESS.FLASTUPDATEUSERID
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFlastupdateuserid(BigDecimal flastupdateuserid) {
		this.flastupdateuserid = flastupdateuserid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FLASTUPDATETIME
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FLASTUPDATETIME
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public Date getFlastupdatetime() {
		return flastupdatetime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FLASTUPDATETIME
	 * 
	 * @param flastupdatetime
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FLASTUPDATETIME
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFlastupdatetime(Date flastupdatetime) {
		this.flastupdatetime = flastupdatetime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FSHUTTLEADDRESSID
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FSHUTTLEADDRESSID
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public BigDecimal getFshuttleaddressid() {
		return fshuttleaddressid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FSHUTTLEADDRESSID
	 * 
	 * @param fshuttleaddressid
	 *            the value for
	 *            DEVELOP.T_CUST_PREFERENCEADDRESS.FSHUTTLEADDRESSID
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFshuttleaddressid(BigDecimal fshuttleaddressid) {
		this.fshuttleaddressid = fshuttleaddressid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FLINKMANID
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FLINKMANID
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public BigDecimal getFlinkmanid() {
		return flinkmanid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FLINKMANID
	 * 
	 * @param flinkmanid
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FLINKMANID
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFlinkmanid(BigDecimal flinkmanid) {
		this.flinkmanid = flinkmanid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FADDRESSTYPE
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FADDRESSTYPE
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public String getFaddresstype() {
		return faddresstype;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FADDRESSTYPE
	 * 
	 * @param faddresstype
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FADDRESSTYPE
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFaddresstype(String faddresstype) {
		this.faddresstype = faddresstype;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FADDRESS
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FADDRESS
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public String getFaddress() {
		return faddress;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FADDRESS
	 * 
	 * @param faddress
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FADDRESS
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFaddress(String faddress) {
		this.faddress = faddress;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FSTARTTIME
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FSTARTTIME
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public Date getFstarttime() {
		return fstarttime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FSTARTTIME
	 * 
	 * @param fstarttime
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FSTARTTIME
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFstarttime(Date fstarttime) {
		this.fstarttime = fstarttime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FENDTIME
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FENDTIME
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public Date getFendtime() {
		return fendtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FENDTIME
	 * 
	 * @param fendtime
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FENDTIME
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFendtime(Date fendtime) {
		this.fendtime = fendtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FBILLREQUEST
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FBILLREQUEST
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public String getFbillrequest() {
		return fbillrequest;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FBILLREQUEST
	 * 
	 * @param fbillrequest
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FBILLREQUEST
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFbillrequest(String fbillrequest) {
		this.fbillrequest = fbillrequest;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FHASSTOPCOST
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FHASSTOPCOST
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public String getFhasstopcost() {
		return fhasstopcost;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FHASSTOPCOST
	 * 
	 * @param fhasstopcost
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FHASSTOPCOST
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFhasstopcost(String fhasstopcost) {
		this.fhasstopcost = fhasstopcost;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FPAYTYPE
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FPAYTYPE
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public String getFpaytype() {
		return fpaytype;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FPAYTYPE
	 * 
	 * @param fpaytype
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FPAYTYPE
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFpaytype(String fpaytype) {
		this.fpaytype = fpaytype;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FISSENDTOFLOOR
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FISSENDTOFLOOR
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public String getFissendtofloor() {
		return fissendtofloor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FISSENDTOFLOOR
	 * 
	 * @param fissendtofloor
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FISSENDTOFLOOR
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFissendtofloor(String fissendtofloor) {
		this.fissendtofloor = fissendtofloor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FOTHERNEED
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FOTHERNEED
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public String getFotherneed() {
		return fotherneed;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FOTHERNEED
	 * 
	 * @param fotherneed
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FOTHERNEED
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFotherneed(String fotherneed) {
		this.fotherneed = fotherneed;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FISMAINADDRESS
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FISMAINADDRESS
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public String getFismainaddress() {
		return fismainaddress;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * DEVELOP.T_CUST_PREFERENCEADDRESS.FISMAINADDRESS
	 * 
	 * @param fismainaddress
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FISMAINADDRESS
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFismainaddress(String fismainaddress) {
		this.fismainaddress = fismainaddress;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FSTATUS
	 * 
	 * @return the value of DEVELOP.T_CUST_PREFERENCEADDRESS.FSTATUS
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public BigDecimal getFstatus() {
		return fstatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column DEVELOP.T_CUST_PREFERENCEADDRESS.FSTATUS
	 * 
	 * @param fstatus
	 *            the value for DEVELOP.T_CUST_PREFERENCEADDRESS.FSTATUS
	 * 
	 * @mbggenerated Mon May 28 16:56:56 CST 2012
	 */
	public void setFstatus(BigDecimal fstatus) {
		this.fstatus = fstatus;
	}

	@Override
	public String toString() {
		return "CustPreferenceaddress [fid=" + fid + ", fcreateuserid=" + fcreateuserid + ", fcreatetime=" + fcreatetime
				+ ", flastupdateuserid=" + flastupdateuserid + ", flastupdatetime=" + flastupdatetime
				+ ", fshuttleaddressid=" + fshuttleaddressid + ", flinkmanid=" + flinkmanid + ", faddresstype="
				+ faddresstype + ", faddress=" + faddress + ", fstarttime=" + fstarttime + ", fendtime=" + fendtime
				+ ", fbillrequest=" + fbillrequest + ", fhasstopcost=" + fhasstopcost + ", fpaytype=" + fpaytype
				+ ", fissendtofloor=" + fissendtofloor + ", fotherneed=" + fotherneed + ", fismainaddress="
				+ fismainaddress + ", fstatus=" + fstatus + "]";
	}
}