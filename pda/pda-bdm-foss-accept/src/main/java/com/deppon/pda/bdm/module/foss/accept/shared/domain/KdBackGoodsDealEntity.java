package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 
 * TODO(快递返货工单受理  返回给pda的实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:245960,date:2015-8-19 下午2:03:11,content:TODO </p>
 * @author 245960
 * @date 2015-8-19 下午2:03:11
 * @since
 * @version
 */
public class KdBackGoodsDealEntity implements Serializable{

	/**
	 * TODO（序列化标识）
	 */
	private static final long serialVersionUID = 1L;
	
	//工单编号
	private String workOrderCode;
	
	//运单号
	private String wblCode;
	
	//返货原因
	private String returnReason;
	
	//返货类型
	private String returnType;
	
	//调查原因
	private String surveyReason;
	
	//处理状态
	private String handleStatus;
   
	//返货方式
	private String returnWay;
	
	public String getReturnWay() {
		return returnWay;
	}

	public void setReturnWay(String returnWay) {
		this.returnWay = returnWay;
	}

	/**
	 * @return  the handleStatus
	 */
	public String getHandleStatus() {
		return handleStatus;
	}

	/**
	 * @param handleStatus the handleStatus to set
	 */
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	/**
	 * @return  the workOrderCode
	 */
	public String getWorkOrderCode() {
		return workOrderCode;
	}

	/**
	 * @param workOrderCode the workOrderCode to set
	 */
	public void setWorkOrderCode(String workOrderCode) {
		this.workOrderCode = workOrderCode;
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
	 * @return  the surveyReason
	 */
	public String getSurveyReason() {
		return surveyReason;
	}

	/**
	 * @param surveyReason the surveyReason to set
	 */
	public void setSurveyReason(String surveyReason) {
		this.surveyReason = surveyReason;
	}
	

}
