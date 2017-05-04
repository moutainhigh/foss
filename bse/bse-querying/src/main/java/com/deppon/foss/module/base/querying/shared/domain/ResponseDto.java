package com.deppon.foss.module.base.querying.shared.domain;

import java.util.Collection;
import java.util.Map;

public class ResponseDto<T> {

	private boolean success;
	private String errorCode;
	private String errorMsg;
	private String remark;
	private Collection<T> results;
	private Map<String, T> mapResults;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Collection<T> getResults() {
		return results;
	}

	public void setResults(Collection<T> results) {
		this.results = results;
	}

	public Map<String, T> getMapResults() {
		return mapResults;
	}

	public void setMapResults(Map<String, T> mapResults) {
		this.mapResults = mapResults;
	}

}
