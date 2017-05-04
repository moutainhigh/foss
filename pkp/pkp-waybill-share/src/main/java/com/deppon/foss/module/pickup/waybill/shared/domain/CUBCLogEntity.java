package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 推送CUBC接口日志
 * CUBCLogEntity
 * @author 198771-zhangwei
 * 2016-10-12 下午6:39:06
 */
public class CUBCLogEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//id
	private String id;
	//运单号
	private String waybillNo;
	//请求内容
	private String requestContent;
	//响应内容
	private String responseContent;
	//响应时间
	private Long responseTime;
	//版本号
	private Long versionNo;
	//服务编码（异步接口）或者请求路径（同步接口）
	private String codeOrUrl;
	//创建时间
	private Date createTime;
	//是否成功
	private String isSuccess;
	//响应状态码(同步接口)
	private String statu;
	//异常信息
	private String errorMsg;
	//接口描述
	private String desc1;
	//预留字段
	private String desc2;
	//是否启用
	private String active;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the requestContent
	 */
	public String getRequestContent() {
		return requestContent;
	}
	/**
	 * @param requestContent the requestContent to set
	 */
	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}
	/**
	 * @return the responseContent
	 */
	public String getResponseContent() {
		return responseContent;
	}
	/**
	 * @param responseContent the responseContent to set
	 */
	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}
	/**
	 * @return the responseTime
	 */
	public Long getResponseTime() {
		return responseTime;
	}
	/**
	 * @param responseTime the responseTime to set
	 */
	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}
	/**
	 * @return the codeOrUrl
	 */
	public String getCodeOrUrl() {
		return codeOrUrl;
	}
	/**
	 * @param codeOrUrl the codeOrUrl to set
	 */
	public void setCodeOrUrl(String codeOrUrl) {
		this.codeOrUrl = codeOrUrl;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the isSuccess
	 */
	public String getIsSuccess() {
		return isSuccess;
	}
	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	/**
	 * @return the desc1
	 */
	public String getDesc1() {
		return desc1;
	}
	/**
	 * @param desc1 the desc1 to set
	 */
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	/**
	 * @return the desc2
	 */
	public String getDesc2() {
		return desc2;
	}
	/**
	 * @param desc2 the desc2 to set
	 */
	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}
	/**
	 * @return the statu
	 */
	public String getStatu() {
		return statu;
	}
	/**
	 * @param statu the statu to set
	 */
	public void setStatu(String statu) {
		this.statu = statu;
	}
	/**
	 * @return the waybillNo
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
	 * @return the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * @return the versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}
	/**
	 * @param versionNo the versionNo to set
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
}
