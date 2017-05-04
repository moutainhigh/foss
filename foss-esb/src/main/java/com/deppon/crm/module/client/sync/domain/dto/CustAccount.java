package com.deppon.crm.module.client.sync.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *客户银行信息
 *
 */
public class CustAccount extends BaseEntity implements Serializable {
 
	private static final long serialVersionUID = -3063354567189488669L;

	/**
	 * 主键
	 */
    private BigDecimal fid;

    /**
     * 创建人ID
     */
    private BigDecimal fcreateuserid;

    /**
     *创建人部门ID
     */
    private BigDecimal fcreatedeptid;

    /**
    *创建时间
    */
    private Date fcreatetime;

    /**
    * 最后更新人id
    */
    private BigDecimal flastupdateuserid;

    /**
    *最后更新部门id
    */
    private BigDecimal flastupdatedeptid;

    /**
    *最后更新时间
    */
    private Date flastupdatetime;

    /**
    *开户行
    */
    private String fbank;

    /**
    *是否默认帐号
    */
    private String fisdefaultaccount;

    /**
    *开户姓名
    */
    private String fcountname;

    /**
    *开户省份
    */
    private String fbankprovinceid;

    /**
    *开户区县
    */
    private String fbankarea;

    /**
    *开户城市
    */
    private String fbankcityid;

    /**
    *财务联系人
    */ 
    private String ffinancelinkman;

    /**
    *联系手机
    */
    private String flinkmanmobile;

    /**
    *支行名称
    */
    private String fsubbankname;

    /**
     *银行帐号
     *
     */
    private String fbankaccount;

    /**
     *帐号与客户关系
     */
    private String frelation;

    /**
     * 所属客户
     */
    private BigDecimal fbelongcustom;

    /**
     * 状态
     */
    private BigDecimal fstatus;

    /**
     * 账户性质
     */
    private String faccountnature;

    /**
     * 账户用途
     */
    private String faccountuse;

    /**
     * 固定电话
     */
    private String flinkmanphone;

    /**
     * 开户行ID
     */
    private String fbankid;

    /**
     * 支行名称ID
     */
    private String fsubbanknameid;

    /**
     * 财务联系人ID
     */
    private BigDecimal ffinancelinkmanid;

    /**
     * 开户省份名称
     */
    private String fbankprovincename;

    /**
     * 开户城市名称
     */
    private String fbankcityname;

    /**
     * 
     */
    public BigDecimal getFid() {
        return fid;
    }

    /**
     * 
     */
    public void setFid(BigDecimal fid) {
        this.fid = fid;
    }

    /**
     *
     */
    public BigDecimal getFcreateuserid() {
        return fcreateuserid;
    }

    /**
     * 
     */
    public void setFcreateuserid(BigDecimal fcreateuserid) {
        this.fcreateuserid = fcreateuserid;
    }

    /**
     * 
     */
    public BigDecimal getFcreatedeptid() {
        return fcreatedeptid;
    }

    /**
     *
     */
    public void setFcreatedeptid(BigDecimal fcreatedeptid) {
        this.fcreatedeptid = fcreatedeptid;
    }

    /**
     * 
     */
    public Date getFcreatetime() {
        return fcreatetime;
    }

    /**
     *
     */
    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    /**
     * 
     */
    public BigDecimal getFlastupdateuserid() {
        return flastupdateuserid;
    }

    /**
     *
     */
    public void setFlastupdateuserid(BigDecimal flastupdateuserid) {
        this.flastupdateuserid = flastupdateuserid;
    }

    /**
     * 
     */
    public BigDecimal getFlastupdatedeptid() {
        return flastupdatedeptid;
    }

    /**
     * 
     */
    public void setFlastupdatedeptid(BigDecimal flastupdatedeptid) {
        this.flastupdatedeptid = flastupdatedeptid;
    }

    /**
     *
     */
    public Date getFlastupdatetime() {
        return flastupdatetime;
    }

    /**
     * 
     */
    public void setFlastupdatetime(Date flastupdatetime) {
        this.flastupdatetime = flastupdatetime;
    }

    /**
     * 
     */
    public String getFbank() {
        return fbank;
    }

    /**
     * 
     */
    public void setFbank(String fbank) {
        this.fbank = fbank;
    }

    /**
     *
     */
    public String getFisdefaultaccount() {
        return fisdefaultaccount;
    }

    /**
     * 
     */
    public void setFisdefaultaccount(String fisdefaultaccount) {
        this.fisdefaultaccount = fisdefaultaccount;
    }

    /**
     * 
     */
    public String getFcountname() {
        return fcountname;
    }

    /**
     *
     */
    public void setFcountname(String fcountname) {
        this.fcountname = fcountname;
    }

    /**
     * 
     */
    public String getFbankprovinceid() {
        return fbankprovinceid;
    }

    /**
     * 
     */
    public void setFbankprovinceid(String fbankprovinceid) {
        this.fbankprovinceid = fbankprovinceid;
    }

    /**
     *
     */
    public String getFbankarea() {
        return fbankarea;
    }

    /**
     * 
     */
    public void setFbankarea(String fbankarea) {
        this.fbankarea = fbankarea;
    }

    /**
     * 
     */
    public String getFbankcityid() {
        return fbankcityid;
    }

    /**
     * 
     */
    public void setFbankcityid(String fbankcityid) {
        this.fbankcityid = fbankcityid;
    }

    /**
     * 
     */
    public String getFfinancelinkman() {
        return ffinancelinkman;
    }

    /**
     *
     */
    public void setFfinancelinkman(String ffinancelinkman) {
        this.ffinancelinkman = ffinancelinkman;
    }

    /**
     *
     */
    public String getFlinkmanmobile() {
        return flinkmanmobile;
    }

    /**
     *
     */
    public void setFlinkmanmobile(String flinkmanmobile) {
        this.flinkmanmobile = flinkmanmobile;
    }

    /**
     * 
     */
    public String getFsubbankname() {
        return fsubbankname;
    }

    /**
     * 
     */
    public void setFsubbankname(String fsubbankname) {
        this.fsubbankname = fsubbankname;
    }

    /**
     *
     */
    public String getFbankaccount() {
        return fbankaccount;
    }

    /**
     * 
     */
    public void setFbankaccount(String fbankaccount) {
        this.fbankaccount = fbankaccount;
    }

    /**
     * 
     */
    public String getFrelation() {
        return frelation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_ACCOUNT.FRELATION
     *
     * @param frelation the value for DEVELOP.T_CUST_ACCOUNT.FRELATION
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public void setFrelation(String frelation) {
        this.frelation = frelation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_ACCOUNT.FBELONGCUSTOM
     *
     * @return the value of DEVELOP.T_CUST_ACCOUNT.FBELONGCUSTOM
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public BigDecimal getFbelongcustom() {
        return fbelongcustom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_ACCOUNT.FBELONGCUSTOM
     *
     * @param fbelongcustom the value for DEVELOP.T_CUST_ACCOUNT.FBELONGCUSTOM
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public void setFbelongcustom(BigDecimal fbelongcustom) {
        this.fbelongcustom = fbelongcustom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_ACCOUNT.FSTATUS
     *
     * @return the value of DEVELOP.T_CUST_ACCOUNT.FSTATUS
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public BigDecimal getFstatus() {
        return fstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_ACCOUNT.FSTATUS
     *
     * @param fstatus the value for DEVELOP.T_CUST_ACCOUNT.FSTATUS
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public void setFstatus(BigDecimal fstatus) {
        this.fstatus = fstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_ACCOUNT.FACCOUNTNATURE
     *
     * @return the value of DEVELOP.T_CUST_ACCOUNT.FACCOUNTNATURE
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public String getFaccountnature() {
        return faccountnature;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_ACCOUNT.FACCOUNTNATURE
     *
     * @param faccountnature the value for DEVELOP.T_CUST_ACCOUNT.FACCOUNTNATURE
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public void setFaccountnature(String faccountnature) {
        this.faccountnature = faccountnature;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_ACCOUNT.FACCOUNTUSE
     *
     * @return the value of DEVELOP.T_CUST_ACCOUNT.FACCOUNTUSE
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public String getFaccountuse() {
        return faccountuse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_ACCOUNT.FACCOUNTUSE
     *
     * @param faccountuse the value for DEVELOP.T_CUST_ACCOUNT.FACCOUNTUSE
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public void setFaccountuse(String faccountuse) {
        this.faccountuse = faccountuse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_ACCOUNT.FLINKMANPHONE
     *
     * @return the value of DEVELOP.T_CUST_ACCOUNT.FLINKMANPHONE
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public String getFlinkmanphone() {
        return flinkmanphone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_ACCOUNT.FLINKMANPHONE
     *
     * @param flinkmanphone the value for DEVELOP.T_CUST_ACCOUNT.FLINKMANPHONE
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public void setFlinkmanphone(String flinkmanphone) {
        this.flinkmanphone = flinkmanphone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_ACCOUNT.FBANKID
     *
     * @return the value of DEVELOP.T_CUST_ACCOUNT.FBANKID
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public String getFbankid() {
        return fbankid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_ACCOUNT.FBANKID
     *
     * @param fbankid the value for DEVELOP.T_CUST_ACCOUNT.FBANKID
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public void setFbankid(String fbankid) {
        this.fbankid = fbankid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_ACCOUNT.FSUBBANKNAMEID
     *
     * @return the value of DEVELOP.T_CUST_ACCOUNT.FSUBBANKNAMEID
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public String getFsubbanknameid() {
        return fsubbanknameid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_ACCOUNT.FSUBBANKNAMEID
     *
     * @param fsubbanknameid the value for DEVELOP.T_CUST_ACCOUNT.FSUBBANKNAMEID
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public void setFsubbanknameid(String fsubbanknameid) {
        this.fsubbanknameid = fsubbanknameid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_ACCOUNT.FFINANCELINKMANID
     *
     * @return the value of DEVELOP.T_CUST_ACCOUNT.FFINANCELINKMANID
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public BigDecimal getFfinancelinkmanid() {
        return ffinancelinkmanid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_ACCOUNT.FFINANCELINKMANID
     *
     * @param ffinancelinkmanid the value for DEVELOP.T_CUST_ACCOUNT.FFINANCELINKMANID
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public void setFfinancelinkmanid(BigDecimal ffinancelinkmanid) {
        this.ffinancelinkmanid = ffinancelinkmanid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_ACCOUNT.FBANKPROVINCENAME
     *
     * @return the value of DEVELOP.T_CUST_ACCOUNT.FBANKPROVINCENAME
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public String getFbankprovincename() {
        return fbankprovincename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_ACCOUNT.FBANKPROVINCENAME
     *
     * @param fbankprovincename the value for DEVELOP.T_CUST_ACCOUNT.FBANKPROVINCENAME
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public void setFbankprovincename(String fbankprovincename) {
        this.fbankprovincename = fbankprovincename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_ACCOUNT.FBANKCITYNAME
     *
     * @return the value of DEVELOP.T_CUST_ACCOUNT.FBANKCITYNAME
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public String getFbankcityname() {
        return fbankcityname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_ACCOUNT.FBANKCITYNAME
     *
     * @param fbankcityname the value for DEVELOP.T_CUST_ACCOUNT.FBANKCITYNAME
     *
     * @mbggenerated Mon May 28 16:56:56 CST 2012
     */
    public void setFbankcityname(String fbankcityname) {
        this.fbankcityname = fbankcityname;
    }
}