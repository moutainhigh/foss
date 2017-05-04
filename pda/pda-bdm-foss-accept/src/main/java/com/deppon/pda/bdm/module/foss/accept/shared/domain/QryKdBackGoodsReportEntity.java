package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 
 * TODO(快递返货工单上报 pda上传实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:245960,date:2015-8-19 下午2:21:03,content:TODO </p>
 * @author 245960
 * @date 2015-8-19 下午2:21:03
 * @since
 * @version
 */
public class QryKdBackGoodsReportEntity implements Serializable{
	/**
	 * TODO（序列化标识）
	 */
	private static final long serialVersionUID = 1L;

	//运单号
	private String wblCode;
	
	//返货原因
	private String returnReason;
	
	//返货类型
	private String returnType;
	
	//发货人联系方式
	private String telephone;
	
	//申请事由
	private String ApplicationReason;
	
	// 员工编号
	private String userCode;
	
	// 部门编号
	private String deptCode;

	//员工姓名
	private String userName;
	
	//返货方式
	private String returnWay;
	//原因明细
	private String returnDetail;
	
	public String getReturnDetail() {
		return returnDetail;
	}

	public void setReturnDetail(String returnDetail) {
		this.returnDetail = returnDetail;
	}

	public String getReturnWay() {
		return returnWay;
	}

	public void setReturnWay(String returnWay) {
		this.returnWay = returnWay;
	}

	/**
	 * @return  the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return  the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return  the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return  the wblCode
	 */
	public String getWblCode() {
		return wblCode;
	}

	/**
	 * @param wblCode the wblCode to set
	 */
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}

	/**
	 * @return  the returnReason
	 */
	public String getReturnReason() {
		return returnReason;
	}

	/**
	 * @param returnReason the returnReason to set
	 */
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	/**
	 * @return  the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return  the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return  the applicationReason
	 */
	public String getApplicationReason() {
		return ApplicationReason;
	}

	/**
	 * @param applicationReason the applicationReason to set
	 */
	public void setApplicationReason(String applicationReason) {
		ApplicationReason = applicationReason;
	}
	
	
}
