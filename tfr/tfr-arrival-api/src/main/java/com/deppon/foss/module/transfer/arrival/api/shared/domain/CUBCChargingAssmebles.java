package com.deppon.foss.module.transfer.arrival.api.shared.domain;

import java.util.List;

public class CUBCChargingAssmebles{
		private List<String> chargingAssmebleNos;
		private String clearingStatus;
		private boolean isSuccess;
		private String exceptionMsg;
		private List<FossTransterEntity> fossTransterEntities;

		public boolean isSuccess() {
			return isSuccess;
		}

		public void setSuccess(boolean isSuccess) {
			this.isSuccess = isSuccess;
		}

		public String getExceptionMsg() {
			return exceptionMsg;
		}

		public void setExceptionMsg(String exceptionMsg) {
			this.exceptionMsg = exceptionMsg;
		}
		
		public List<String> getChargingAssmebleNos() {
			return chargingAssmebleNos;
		}
		
		public void setChargingAssmebleNos(List<String> chargingAssmebleNos) {
			this.chargingAssmebleNos = chargingAssmebleNos;
		}
		
		public String getClearingStatus() {
			return clearingStatus;
		}
		
		public void setClearingStatus(String clearingStatus) {
			this.clearingStatus = clearingStatus;
		} 
		public List<FossTransterEntity> getFossTransterEntities() {
			return fossTransterEntities;
		}

		public void setFossTransterEntities(List<FossTransterEntity> fossTransterEntities) {
			this.fossTransterEntities = fossTransterEntities;
		}
	}