package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 自动上报QMS差错时的请求参数 dto
 * @author 231434-FOSS-bieyexiong
 * @date 2015-5-5 下午17:36:50
 */
public class QmsErrorReportRequestDto implements Serializable{

	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 差错类型编号
	 */
	private String errorTypeId;
	
	/**
	 * 业务类别
	 */
	private String errCategoty;
	
	/**
	 * 差错上报主信息
	 */
	private Object mainInfo;
	
	/**
	 * 差错上报子信息
	 * 快递有单
	 */
	private Object kdsubHasInfo;
	
	/**
	 * 差错上报子信息
	 * 快递无单
	 */
	private Object kdsubNoInfo;
	
	/**
	 * 差错上报子信息
	 * 零担有单
	 */
	private Object ldsubHasInfo;
	
	/**
	 * 差错上报子信息
	 * 零担无单
	 */
	private Object ldsubNoInfo;

	/**
	 * 是否立即返回结果
	 */
	private boolean returnResult;
	
	public String getErrorTypeId() {
		return errorTypeId;
	}
	
	public void setErrorTypeId(String errorTypeId) {
		this.errorTypeId = errorTypeId;
	}
	
	public String getErrCategoty() {
		return errCategoty;
	}
	
	public void setErrCategoty(String errCategoty) {
		this.errCategoty = errCategoty;
	}
	
	public boolean isReturnResult() {
		return returnResult;
	}
	
	public void setReturnResult(boolean returnResult) {
		this.returnResult = returnResult;
	}
	
	public Object getMainInfo() {
		return mainInfo;
	}
	
	public void setMainInfo(Object mainInfo) {
		this.mainInfo = mainInfo;
	}
	
	public Object getKdsubHasInfo() {
		return kdsubHasInfo;
	}
	
	public void setKdsubHasInfo(Object kdsubHasInfo) {
		this.kdsubHasInfo = kdsubHasInfo;
	}

	public Object getKdsubNoInfo() {
		return kdsubNoInfo;
	}

	public void setKdsubNoInfo(Object kdsubNoInfo) {
		this.kdsubNoInfo = kdsubNoInfo;
	}

	public Object getLdsubHasInfo() {
		return ldsubHasInfo;
	}

	public void setLdsubHasInfo(Object ldsubHasInfo) {
		this.ldsubHasInfo = ldsubHasInfo;
	}

	public Object getLdsubNoInfo() {
		return ldsubNoInfo;
	}

	public void setLdsubNoInfo(Object ldsubNoInfo) {
		this.ldsubNoInfo = ldsubNoInfo;
	}
	
}
