package com.deppon.foss.module.transfer.stock.api.shared.domain;
/**
 * FOSS可以根据运单的库存状态匹配出相应的任务部门，并通过接口将任务部门传给CRM。
 * 此类是定义 CRM传递给FOSS中转接口之间参数
 * @author zwd 200968
 * @date 20150420
 */
public class TaskDeptRequest {

		/**
		 * Description 来电人-字符长度20
		 * @author 120750 
		 * @version 0.1 
		 */
		private String callMan;
		
		/**
		 * Description get 来电人-字符长度20
		 * @author 120750 
		 * @version 0.1 
		 * @return String
		 */
		public String getCallMan(){
			return (callMan == null?"":callMan);
		}
		
		/**
		 * Description set 来电人-字符长度20
		 * @author 120750 
		 * @version 0.1 
		 * @param f_call_man
		 */
		public void setCallMan(String callMan){
			this.callMan = callMan;
		}
		/**
		 * Description 来电人类型 - 发货方=SHIPMAN、收货方=RECEIVEMAN、第三方=THIRDPARTY、内部同事=INNERCOLLE
		 * @author 120750 
		 * @version 0.1 
		 */
		private String callType;
		
		/**
		 * Description get 来电人类型 - 发货方=SHIPMAN、收货方=RECEIVEMAN、第三方=THIRDPARTY、内部同事=INNERCOLLE
		 * @author 120750 
		 * @version 0.1 
		 * @return String
		 */
		public String getCallType(){
			return (callType == null?"":callType);
		}
		
		/**
		 * Description set 来电人类型 - 发货方=SHIPMAN、收货方=RECEIVEMAN、第三方=THIRDPARTY、内部同事=INNERCOLLE
		 * @author 120750 
		 * @version 0.1 
		 * @param f_call_type
		 */
		public void setCallType(String callType){
			this.callType = callType;
		}
		/**
		 * 运单
		 */
		private String waybillNumber;

		public String getWaybillNumber() {
			return waybillNumber;
		}

		public void setWaybillNumber(String waybillNumber) {
			this.waybillNumber = waybillNumber;
		}

		/**
		 * 调用类型
		 * 1、methodType == 1   匹配任务部门
		 * 2、methodType == 2   匹配工单短信部门
		 */
		private String methodType;

		public String getMethodType() {
			return methodType;
		}

		public void setMethodType(String methodType) {
			this.methodType = methodType;
		}
		
}
