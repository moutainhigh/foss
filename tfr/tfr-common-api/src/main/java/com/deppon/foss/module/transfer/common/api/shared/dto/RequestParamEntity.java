package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.transfer.common.api.shared.domain.AutoOverWeightToQMSEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.AutoOverWeightToQMSMainEntity;

public class RequestParamEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//差错类型编号
		private String errorTypeId;
		//业务类别
		private String errCategoty;
		
		//上报主实体
		private AutoOverWeightToQMSMainEntity mainInfo;

		private AutoOverWeightToQMSEntity kdsubHasInfo;
		//是否立即返回结果
		private boolean returnResult;
		
		public AutoOverWeightToQMSEntity getKdsubHasInfo() {
			return kdsubHasInfo;
		}

		public void setKdsubHasInfo(AutoOverWeightToQMSEntity kdsubHasInfo) {
			this.kdsubHasInfo = kdsubHasInfo;
		}

		public boolean isReturnResult() {
			return returnResult;
		}

		public void setReturnResult(boolean returnResult) {
			this.returnResult = returnResult;
		}

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




		public AutoOverWeightToQMSMainEntity getMainInfo() {
			return mainInfo;
		}

		public void setMainInfo(AutoOverWeightToQMSMainEntity mainInfo) {
			this.mainInfo = mainInfo;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		
}
