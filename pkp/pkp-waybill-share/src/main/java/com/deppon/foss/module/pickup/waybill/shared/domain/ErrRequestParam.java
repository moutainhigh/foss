package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 差错上报主信息
 * @author foss-273279-Liding
 * @date 2015-8-17 16:41:05
 */
public class ErrRequestParam implements Serializable {

	private static final long serialVersionUID = -3183174737630778432L;

	//差错类型编号
	private String errorTypeId;
	//业务类别
	private String errCategoty;
	//差错上报主信息
	private ErrorMainEntity mainInfo;
	//差错子报主信息
//	//快递有单
//	private KDErrSubHasWaybillEntity kdsubHasInfo;
//	//快递无单
//	private KDErrSubNoWaybillEntity kdsubNoInfo;
	//零担有单
	private LDErrSubHasWaybillEntity ldsubHasInfo;
	//零担无单
	private LDErrSubNoWaybillEntity ldsubNoInfo;
	
	//是否立即返回结果
	private boolean returnResult;
//	//差错附件列表
//	private List<FileUploadEntity> fileInfo;
	
	/**
	 * errorTypeId <p>getter method</p>
	 * @author 273279
	 * @return  the errorTypeId
	 */
	public String getErrorTypeId() {
		return errorTypeId;
	}
	/**
	 * errorTypeId <p>setter method</p>
	 * @author 273279
	 * @param errorTypeId the errorTypeId to set
	 */
	public void setErrorTypeId(String errorTypeId) {
		this.errorTypeId = errorTypeId;
	}
	/**
	 * errCategoty <p>getter method</p>
	 * @author 273279
	 * @return  the errCategoty
	 */
	public String getErrCategoty() {
		return errCategoty;
	}
	/**
	 * errCategoty <p>setter method</p>
	 * @author 273279
	 * @param errCategoty the errCategoty to set
	 */
	public void setErrCategoty(String errCategoty) {
		this.errCategoty = errCategoty;
	}
	/**
	 * mainInfo <p>getter method</p>
	 * @author 273279
	 * @return  the mainInfo
	 */
	public ErrorMainEntity getMainInfo() {
		return mainInfo;
	}
	/**
	 * mainInfo <p>setter method</p>
	 * @author 273279
	 * @param mainInfo the mainInfo to set
	 */
	public void setMainInfo(ErrorMainEntity mainInfo) {
		this.mainInfo = mainInfo;
	}
	
	/**
	 * returnResult <p>getter method</p>
	 * @author 273279
	 * @return  the returnResult
	 */
	public boolean isReturnResult() {
		return returnResult;
	}
	/**
	 * returnResult <p>setter method</p>
	 * @author 273279
	 * @param returnResult the returnResult to set
	 */
	public void setReturnResult(boolean returnResult) {
		this.returnResult = returnResult;
	}
	
	/**
	 * ldsubHasInfo <p>getter method</p>
	 * @author 273279
	 * @return  the ldsubHasInfo
	 */
	public LDErrSubHasWaybillEntity getLdsubHasInfo() {
		return ldsubHasInfo;
	}
	/**
	 * ldsubHasInfo <p>setter method</p>
	 * @author 273279
	 * @param ldsubHasInfo the ldsubHasInfo to set
	 */
	public void setLdsubHasInfo(LDErrSubHasWaybillEntity ldsubHasInfo) {
		this.ldsubHasInfo = ldsubHasInfo;
	}
	/**
	 * ldsubNoInfo <p>getter method</p>
	 * @author 273279
	 * @return  the ldsubNoInfo
	 */
	public LDErrSubNoWaybillEntity getLdsubNoInfo() {
		return ldsubNoInfo;
	}
	/**
	 * ldsubNoInfo <p>setter method</p>
	 * @author 273279
	 * @param ldsubNoInfo the ldsubNoInfo to set
	 */
	public void setLdsubNoInfo(LDErrSubNoWaybillEntity ldsubNoInfo) {
		this.ldsubNoInfo = ldsubNoInfo;
	}

	
}
