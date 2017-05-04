package com.deppon.foss.module.pickup.waybill.shared.domain;

public class RewardFineEntity {
	// 差错类型
		private String typeName;
		
		// 运单号
		private String waybillId;
		
		// 责任部门（负激励人所在部门）
		private String orgName;
		
		// 调查内容
		private String surveyContent;
		
		// 负激励人
		private String empName;
		
		// 金额
		private String money;
		
		// 差错ID
		private String errorId;
		
		// 处理日期
		private String operatorTime;
		
		// 差错处理人
		private String operator;

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public String getWaybillId() {
			return waybillId;
		}

		public void setWaybillId(String waybillId) {
			this.waybillId = waybillId;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

		public String getSurveyContent() {
			return surveyContent;
		}

		public void setSurveyContent(String surveyContent) {
			this.surveyContent = surveyContent;
		}

		public String getEmpName() {
			return empName;
		}

		public void setEmpName(String empName) {
			this.empName = empName;
		}

		public String getMoney() {
			return money;
		}

		public void setMoney(String money) {
			this.money = money;
		}

		public String getErrorId() {
			return errorId;
		}

		public void setErrorId(String errorId) {
			this.errorId = errorId;
		}

		public String getOperatorTime() {
			return operatorTime;
		}

		public void setOperatorTime(String operatorTime) {
			this.operatorTime = operatorTime;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}
			@Override
			public String toString() {
				return "RewardFineDetailEntity [typeName=" + typeName
						+ ", operatorTime=" + operatorTime + ",operator=" + operator + ",waybillId=" + waybillId + ", orgName=" + orgName
						+ ", surveyContent=" + surveyContent + ", empName="
						+ empName + ", money=" + money + ", errorId=" + errorId
						+ "]";
			}

}
