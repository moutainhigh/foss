package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

public class ResponsePEntity implements Serializable{
	private static final long serialVersionUID = 3761476707437674022L;
	    // 公共实体,可为数组类型
		private Object ResponseEntity;
		
		// 是否成功标志
		private boolean resultFlag;
		
		// 失败原因
		private String failureReason;

		public Object getResponseEntity() {
			return ResponseEntity;
		}

		public void setResponseEntity(Object responseEntity) {
			ResponseEntity = responseEntity;
		}

		public boolean isResultFlag() {
			return resultFlag;
		}

		public void setResultFlag(boolean resultFlag) {
			this.resultFlag = resultFlag;
		}

		public String getFailureReason() {
			return failureReason;
		}

		public void setFailureReason(String failureReason) {
			this.failureReason = failureReason;
		}
}
