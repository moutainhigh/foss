package com.deppon.foss.module.transfer.platform.api.shared.domain;


import com.deppon.foss.framework.entity.BaseEntity;


/**
* @description 仓库饱和度预警信息推送
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年4月24日 下午1:54:40
*/
public class StockSaturationSmsEntity extends BaseEntity {
    
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年4月24日 下午1:54:36
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;

	/**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column T_OPT_STOCK_SATURATION_SMS.SATURATION_SMS_ID
     *
     * @ibatorgenerated Thu Apr 24 13:47:57 CST 2014
     */
    private String saturationSmsId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column T_OPT_STOCK_SATURATION_SMS.ORG_CODE
     *
     * @ibatorgenerated Thu Apr 24 13:47:57 CST 2014
     */
    private String orgCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column T_OPT_STOCK_SATURATION_SMS.SMSSENDTIME
     *
     * @ibatorgenerated Thu Apr 24 13:47:57 CST 2014
     */
    private String smssendtime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column T_OPT_STOCK_SATURATION_SMS.SMSSTATUS
     *
     * @ibatorgenerated Thu Apr 24 13:47:57 CST 2014
     */
    private Integer smsstatus;
    
    /**
     * @author 140222
     * 手机号
     */
    private String mobileTelephone;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column T_OPT_STOCK_SATURATION_SMS.SATURATION_SMS_ID
     *
     * @return the value of T_OPT_STOCK_SATURATION_SMS.SATURATION_SMS_ID
     *
     * @ibatorgenerated Thu Apr 24 13:47:57 CST 2014
     */
    public String getSaturationSmsId() {
        return saturationSmsId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column T_OPT_STOCK_SATURATION_SMS.SATURATION_SMS_ID
     *
     * @param saturationSmsId the value for T_OPT_STOCK_SATURATION_SMS.SATURATION_SMS_ID
     *
     * @ibatorgenerated Thu Apr 24 13:47:57 CST 2014
     */
    public void setSaturationSmsId(String saturationSmsId) {
        this.saturationSmsId = saturationSmsId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column T_OPT_STOCK_SATURATION_SMS.ORG_CODE
     *
     * @return the value of T_OPT_STOCK_SATURATION_SMS.ORG_CODE
     *
     * @ibatorgenerated Thu Apr 24 13:47:57 CST 2014
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column T_OPT_STOCK_SATURATION_SMS.ORG_CODE
     *
     * @param orgCode the value for T_OPT_STOCK_SATURATION_SMS.ORG_CODE
     *
     * @ibatorgenerated Thu Apr 24 13:47:57 CST 2014
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column T_OPT_STOCK_SATURATION_SMS.SMSSENDTIME
     *
     * @return the value of T_OPT_STOCK_SATURATION_SMS.SMSSENDTIME
     *
     * @ibatorgenerated Thu Apr 24 13:47:57 CST 2014
     */
    public String getSmssendtime() {
        return smssendtime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column T_OPT_STOCK_SATURATION_SMS.SMSSENDTIME
     *
     * @param smssendtime the value for T_OPT_STOCK_SATURATION_SMS.SMSSENDTIME
     *
     * @ibatorgenerated Thu Apr 24 13:47:57 CST 2014
     */
    public void setSmssendtime(String smssendtime) {
        this.smssendtime = smssendtime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column T_OPT_STOCK_SATURATION_SMS.SMSSTATUS
     *
     * @return the value of T_OPT_STOCK_SATURATION_SMS.SMSSTATUS
     *
     * @ibatorgenerated Thu Apr 24 13:47:57 CST 2014
     */
    public Integer getSmsstatus() {
        return smsstatus;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column T_OPT_STOCK_SATURATION_SMS.SMSSTATUS
     *
     * @param smsstatus the value for T_OPT_STOCK_SATURATION_SMS.SMSSTATUS
     *
     * @ibatorgenerated Thu Apr 24 13:47:57 CST 2014
     */
    public void setSmsstatus(Integer smsstatus) {
        this.smsstatus = smsstatus;
    }

	/**
	 * @author 140222
	 * @return
	 */
	public String getMobileTelephone() {
		return mobileTelephone;
	}

	/**
	 * @author 140222
	 * @param mobileTelephone
	 */
	public void setMobileTelephone(String mobileTelephone) {
		this.mobileTelephone = mobileTelephone;
	}
}