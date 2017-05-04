package com.deppon.esb.pojo.domain.foss2qms;

import java.io.Serializable;
public class ErrRequestParam implements Serializable {

	private static final long serialVersionUID = -3183174737630778432L;

	//差错类型编号
	private String errorTypeId;
	//业务类别
	private String errCategoty;
	//零担无单
	private LDErrSubNoWaybillEntity ldsubNoInfo;
	//差错上报主信息
	private ErrorMainEntity mainInfo;
	//是否立即返回结果
	private boolean returnResult;
	
	/**
	 * errorTypeId <p>getter method</p>
	 * @author 150976
	 * @return  the errorTypeId
	 */
	public String getErrorTypeId() {
		return errorTypeId;
	}
	/**
	 * errorTypeId <p>setter method</p>
	 * @author 150976
	 * @param errorTypeId the errorTypeId to set
	 */
	public void setErrorTypeId(String errorTypeId) {
		this.errorTypeId = errorTypeId;
	}
	/**
	 * errCategoty <p>getter method</p>
	 * @author 150976
	 * @return  the errCategoty
	 */
	public String getErrCategoty() {
		return errCategoty;
	}
	/**
	 * errCategoty <p>setter method</p>
	 * @author 150976
	 * @param errCategoty the errCategoty to set
	 */
	public void setErrCategoty(String errCategoty) {
		this.errCategoty = errCategoty;
	}
	/**
	 * mainInfo <p>getter method</p>
	 * @author 150976
	 * @return  the mainInfo
	 */
	public ErrorMainEntity getMainInfo() {
		return mainInfo;
	}
	/**
	 * mainInfo <p>setter method</p>
	 * @author 150976
	 * @param mainInfo the mainInfo to set
	 */
	public void setMainInfo(ErrorMainEntity mainInfo) {
		this.mainInfo = mainInfo;
	}
	
	/**
	 * returnResult <p>getter method</p>
	 * @author 150976
	 * @return  the returnResult
	 */
	public boolean isReturnResult() {
		return returnResult;
	}
	/**
	 * returnResult <p>setter method</p>
	 * @author 150976
	 * @param returnResult the returnResult to set
	 */
	public void setReturnResult(boolean returnResult) {
		this.returnResult = returnResult;
	}
	/**
	 * ldsubNoInfo <p>getter method</p>
	 * @author 150976
	 * @return  the ldsubNoInfo
	 */
	public LDErrSubNoWaybillEntity getLdsubNoInfo() {
		return ldsubNoInfo;
	}
	/**
	 * ldsubNoInfo <p>setter method</p>
	 * @author 150976
	 * @param ldsubNoInfo the ldsubNoInfo to set
	 */
	public void setLdsubNoInfo(LDErrSubNoWaybillEntity ldsubNoInfo) {
		this.ldsubNoInfo = ldsubNoInfo;
	}

	
}
