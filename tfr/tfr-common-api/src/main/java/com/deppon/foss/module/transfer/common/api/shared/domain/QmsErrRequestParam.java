package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;


public class QmsErrRequestParam implements Serializable {

	private static final long serialVersionUID = 1L;
	//差错类型编号
	private String errorTypeId;
	//业务类别
	private String errCategoty;
	//差错上报主信息
	private QmsErrorMainEntity mainInfo;
	//差错子报主信息
	//快递有单
	private QmsKDErrSubHasWaybillEntity kdsubHasInfo;
	//快递无单
	//private KDErrSubNoWaybillEntity kdsubNoInfo;
	//零担有单
	private QmsLDErrSubHasWaybillEntity ldsubHasInfo;
	//零担无单
	//private LDErrSubNoWaybillEntity ldsubNoInfo;
	
	//是否立即返回结果
	private boolean returnResult;
	//差错附件列表
	//private List<FileUploadEntity> fileInfo;

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

	public QmsErrorMainEntity getMainInfo() {
		return mainInfo;
	}

	public void setMainInfo(QmsErrorMainEntity mainInfo) {
		this.mainInfo = mainInfo;
	}

	public QmsKDErrSubHasWaybillEntity getKdsubHasInfo() {
		return kdsubHasInfo;
	}

	public void setKdsubHasInfo(QmsKDErrSubHasWaybillEntity kdsubHasInfo) {
		this.kdsubHasInfo = kdsubHasInfo;
	}

	public QmsLDErrSubHasWaybillEntity getLdsubHasInfo() {
		return ldsubHasInfo;
	}

	public void setLdsubHasInfo(QmsLDErrSubHasWaybillEntity ldsubHasInfo) {
		this.ldsubHasInfo = ldsubHasInfo;
	}

	public boolean isReturnResult() {
		return returnResult;
	}

	public void setReturnResult(boolean returnResult) {
		this.returnResult = returnResult;
	}
	
}
