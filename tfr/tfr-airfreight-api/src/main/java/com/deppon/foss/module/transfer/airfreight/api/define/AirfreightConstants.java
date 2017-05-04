/**
 *  initial comments.
 */

/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirfreightConstants.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.define;

public class AirfreightConstants {
	
    /**
     * 精准空运
     */
	 public static final String PRECISION_AIR = "PRECISION_AIR";
    /**
     * 快递空运
     */
    public static final String PACKAGE_AIR = "PACKAGE_AIR";
    
    /**
     * 快递空运(传给悟空系统时是商务专递)
     */
    public static final String DEAP = "DEAP";
    
    /**
     * 快递空运(一些值取不到用空格代替)
     */
    public static final String NULL = " ";

	/**
	 * 单独开单
	 */
	public static final String AIRFREIGHT_INDEPENDENTBILLING = "DDKD";
	
	/**
	 * 合大票
	 */
	public static final String AIRFREIGHT_TOTALBIGBILLNO = "HDP"; 
	
	/**
	 * 单独开单外发
	 */
	public static final String AIRFREIGHT_INDEPENDENTBILLINGOUTSOURCE = "DDWFD";
	
	/**
	 * 合大票外发
	 */
	public static final String AIRFREIGHT_TOTALBIGBILLNOOUTSOURCE = "HDPWF";
	
	/**
	 * 已付款
	 */
	public static final String AIRFREIGHT_PAYMENT = "Y";
	
	/**
	 * 已付款
	 */
	public static final String AIRFREIGHT_NOTPAYMENT = "N";
	
	/**
	 * 已交接
	 */
	public static final String AIRFREIGHT_ALREADYHANDOVER = "Y";
	
	/**
	 * 未交接
	 */
	public static final String AIRFREIGHT_NOTHANDOVER = "N";
	
	/**
	 * update、insert操作统一返回成功code
	 */
	public static final int AIRFREIGHT_SUCCESS = 0;
	
	/**
	 * 航班类型：早班
	 * @author zwd 200968 2015-05-06
	 * 航班类型：即日达
	 */
	public static final String MORNING_FLIGHT = "MORNING_FLIGHT";
	
	/**
	 * 航班类型：中班
	 * @author zwd 200968 2015-05-06
	 * 航班类型：次日达
	 */
	public static final String MIDDLE_FLIGHT = "MIDDLE_FLIGHT";
	
	/**
	 * 航班类型：晚班
	 * @author zwd 200968 2015-05-06
	 * 航班类型：次日达
	 */
	public static final String NIGHT_FLIGHT = "NIGHT_FLIGHT";
	
	/**
	 * 航班类型：中转
	 * @author zwd 200968 2015-05-06
	 * 航班类型：航空普运
	 */
	public static final String TRANSFER_FLIGHT = "TRANSFER_FLIGHT";
	
	/**
	 * 空运到达类型:代理到机场提货  200968 2015-08-07
	 */
		public static final String AGENT_TO_AIRPORT_PICK_UP = "AGENT_TO_AIRPORT_PICK_UP";
	/**
	 * 空运到达类型:货物到达代理处  200968 2015-08-07
	 */
		public static final String GOODS_ARRIVE_AGENCY = "GOODS_ARRIVE_AGENCY";	
		/**
		 * 空运通知客户  查询类型:正单号0          运单号1            一般查询 2
		 * 200968 2015-8-25	
		 */
			public static final int AIR_NOTIFY_CUSTOMERS_AIRWAYBILLNO = 0;
	
			public static final int AIR_NOTIFY_CUSTOMERS_WAYBILLNO = 1;
	
			public static final int AIR_NOTIFY_CUSTOMERS_COMMON = 2;
		
		/**
		 * 5中短信模板
		 */
		/**
		 * 合大票送货--A	空运通知客户 2015-9-9	
		 */
		public static final String AIR_NOTIFY_CUSTOMERS_A = "AIR_NOTIFY_CUSTOMERS_A";
		
		/**
		 * 合大票自提无到付费用--B     空运通知客户 2015-9-9	
		 */
		public static final String AIR_NOTIFY_CUSTOMERS_B = "AIR_NOTIFY_CUSTOMERS_B";
		
		/**
		 * 合大票自提有到付费用--C   空运通知客户 2015-9-9	
		 */
		public static final String AIR_NOTIFY_CUSTOMERS_C = "AIR_NOTIFY_CUSTOMERS_C";
		
		/**
		 * 合大票免费无到付运费--D   空运通知客户 2015-9-9	
		 */
		public static final String AIR_NOTIFY_CUSTOMERS_D = "AIR_NOTIFY_CUSTOMERS_D";
		
		
		/**
		 * 合大票免费有到付运费--E  空运通知客户 2015-9-9	
		 */
		public static final String AIR_NOTIFY_CUSTOMERS_E = "AIR_NOTIFY_CUSTOMERS_E";
		
		
	

	
	
	
	
	/**
	 * 受理状态：未受理
	 */
	public static final String UN_ACCEPT = "UN_ACCEPT";
	
	/**
	 * 受理状态：同意受理
	 */
	public static final String AGREE_ACCEPT = "AGREE_ACCEPT";
	
	/**
	 * 受理状态：拒绝受理
	 */
	public static final String REFUSE_ACCEPT = "REFUSE_ACCEPT";
	
	/**
	 * 跟踪状态：待跟踪
	 */
	public static final String UN_TRACK = "UN_TRACK";
	
	/**
	 * 跟踪状态：继续跟踪
	 */
	public static final String TRACKING = "TRACKING";
	
	/**
	 * 跟踪状态：完成跟踪
	 */
	public static final String TRACKEND = "TRACKEND";
	
	/**
	 * 根据流水号查询返回异常信息
	 */
	public static final String AIRFREIGHT_NOTSEIALNODETAIL = "foss.airfreight.exception.notSeialnoDetail";
	
	/**
	 * 运单明细为空
	 */
	public static final String AIRFREIGHT_NOTAIRWAYBILLINFO = "foss.airfreight.exception.notairWaybillInfo";
	
	/**
	 * 正单号重复
	 */
	public static final String AIRFREIGHT_NOTAIRWAYBILLNO = "foss.airfreight.exception.notairWaybilLNo";
	
	/**
	 * 0打印航空正单金额明细
	 */
	public static final String AIRFREIGHT_ISNOTMONEY = "Y";
	
	/**
	 * 0打印录入航空正单明细
	 */
	public static final String AIRFREIGHT_NOPRINTMODULE = "Y";
	
	/**
	 * 1不打印录入航空正单费用明细
	 */
	public static final String AIRFREIGHT_YESPRINTMODULE = "N";
	
	/**
	 *0打印收货部门1不打印收货部门 
	 */
	public static final String AIRFREIGHT_ISNOTRECEIVERNAME = "N";
	
	/**
	 * 只能配载部门人员进行删除操作
	 */
	public static final String AIRFREIGHT_EXCEPTION_ONLYDELETEOWNERDEPARTSPACE = "foss.airfreight.exception.onlyDeleteOwnerDepartSpace";

	/**
	 * 未找到提货清单信息
	 */
	public static final String AIRFREIGHT_EXCEPTION_NOTFOUNDTRANSFERPICKUPBILL = "foss.airfreight.exception.nofoundPickupBillSpace";
	
	/**
	 * 未找到提货清单明细信息
	 */
	public static final String  AIRFREIGHT_EXCEPTION_NOTFOUNDTRANSFERPICKUPDETAILBILL = "foss.airfreight.exception.nofoundPickupDetailBillSpace";
	/**
	 * 未找到变更清单信息
	 */
	public static final String  AIRFREIGHT_EXCEPTION_NOTFOUNDCHANGEBILL = "foss.airfreight.exception.nofoundChangeBill";
	/**
	 * 未找到航空正单信息
	 */
	public static final String  AIRFREIGHT_EXCEPTION_NOTFOUNDAIRWAYBILL = "foss.airfreight.exception.nofoundAirWayBill";
	/**
	 * 未找到变更清单明细信息
	 */
	public static final String  AIRFREIGHT_EXCEPTION_NOTFOUNDCHANGEDETAILBILL = "foss.airfreight.exception.nofoundChangeDetailBill";
	/**
	 * 调用结算合大票预收应付接口信息
	 */
	public static final String  AIRFREIGHT_EXCEPTION_STLJOINTTICKETINFO= "foss.airfreight.exception.stlJointTicketInfo";
	
	/**
	 * 合大票清单明细为空!
	 */
	public static final String  AIRFREIGHT_EXCEPTION_AIRPICKUPBILLDETAIL = "foss.airfreight.exception.nofoundPickupDetailBillSpace";
	
	/**
	 * 合大票基础信息为空!
	 */
	public static final String  AIRFREIGHT_EXCEPTION_AIRPICKUPBILL_ISNULL = "foss.airfreight.exception.airPickupbillIsNull";
	
	/**
	 * 大票更改证明
	 */
	public static final String  AIRFREIGHT_PICKUPBILL_MODIFYDETAILINFO = "大票更改证明";
	
	/**
	 * 币种
	 */
	public static final String AIRFREIGHT_CURRENCYCODE = "foss.airfreight.currencycode";
	
	/**
	 * 上传合大票主题
	 */
	public static final String AIRFREIGHT_UPLOAD_PICKUP_EDI_ACCESSORY="合票清单";
	
	/**
	 * 上传中转提货清单主题
	 */
	public static final String AIRFREIGHT_UPLOAD_TRAN_PICKUP_EDI_ACCESSORY = "中转提货清单";
	/**
	 * 变更清单主题
	 */
	public static final String AIRFREIGHT_UPLOAD_CHANGE_EDI_ACCESSORY = "变更清单";
	/**
	 * 调用edi出错
	 */
	public static final String AIRFREIGHT_CALL_EDI_EXCEPTION = "foss.airfreight.exception.callEdiException";
	

	/**
	 * 调用费率出错
	 */
	public static final String AIRFREIGHT_PKP_QUERY_RATE_NOT_FOUND_EXCEPTION = "foss.airfreight.exception.pkpQueryRateNotFound"; 
	
	/**
	 * 未配载
	 */
	public static final String AIRFREIGHT_ISNOLOADING = "N";
	
	/**
	 * 已配载
	 */
	public static final String AIRFREIGHT_ISYESLOADING = "Y";
	
	/**
	 * 已配载且到达
	 */
	public static final String AIRFREIGHT_ISLOADING_AND_ARRIVE = "ARRIVE,Y";
	
	/**
	 * "N" 删除流水
	 */
	public static final String AIRFREIGHT_DELETE_FLAG_YES = "Y";
	
	/**
	 * "Y" 需要保存的流水
	 */
	public static final String AIRFREIGHT_DELETE_FLAG_NO = "N";
	
	
	/**
	 * "N" 删除流水
	 */
	public static final String AIRFREIGHT_ADD_FLAG_YES = "Y";
	
	/**
	 * "Y" 需要保存的流水
	 */
	public static final String AIRFREIGHT_ADD_FLAG_NO = "N";
	
	public static final String AIRFREIGHT_FLAG_ADD = "add";
	public static final String AIRFREIGHT_FLAG_DEL = "del";
	/**
	 * N添加显示灰色
	 */
	public static final String AIRFREIGHT_VIEW_FLAG_NO = "N";
	
	/**
	 * Y添加显示蓝色
	 */
	public static final String AIRFREIGHT_VIEW_FLAG_YES = "Y";
	
	/**
	 * "AIR_WAY_BILL_NO"表示正单号
	 */
	public static final String AIR_WAY_BILL_NO = "AIR_WAY_BILL_NO";
	
	/**
	 * "WAY_BILL_NO"表示运单号
	 */
	public static final String WAY_BILL_NO = "WAY_BILL_NO";
	
	/**
	 * "STOCKING_STATUS"表示出库状态，Y为已出库
	 */
	public static final String STOCKING_STATUS_Y = "Y";
	
	/**
	 * "STOCKING_STATUS"表示出库状态，N为未出库
	 */
	public static final String STOCKING_STATUS_N = "N";
	
	/**
	 * YES发送edit
	 */
	public static final String AIRFREIGHT_YES_SEND_EDI = "YES";
	
	/**
	 * return出库成功
	 */
	public static final int OUT_STOCK_SUCCUSS = 1;
	
	/**
	 * Y中转
	 */
	public static final String AIRFREIGHT_YES_BETRANSFER = "Y";

	/**
	 * 托运人编码 
	 */
	public static final String AIRFREIGHT_SHIPPERCODE = "000000";
	
	/**
	 * all查询全部
	 */
	public static final String AIRFREIGHT_DEST_ORG_NAME = "ALL";
	
	/**
	 * 已签收
	 */
	public static final String AIRFREIGHT_EDI_SIGNED = "1";
	
	/**
	 * 未签收
	 */
	public static final String AIRFREIGHT_EDI_NOT_SIGN = "0";
	
	/**
	 * 签收状态，Y表示已签收 
	 */
	public static final String PKP_SIGN_ACTIVE = "Y";
	
	public static final String STOCKSTATUS20 = "20";
	
	public static final String STOCKSTATUS30 = "30";
	
	public static final String StATUS_NULL = "";
	
	public static final String TANGYI_RATECLASS_LESS = "M";
	
	public static final String TANGYI_RATECLASS_BIG = "Q";
	
	/**
	 * 046130-foss-xuduowei,2013-03-20,
	 * 增加对清单明细的地址转换，增加格式符
	 */
	public static final String AIRFREIGHT_ADDRESS_WHIPPLETREE = "-";
	
	/**
	 * 拉货通知模板代码
	 */
	public static final String TFR_UNSHIPPED_GOODS_NOTICE = "TFR_UNSHIPPED_GOODS_NOTICE";
	
	/**
	 * 营业部订舱待办模板代码
	 */
	public static final String TFR_AIR_BOOKING_SPACE = "TFR_AIR_BOOKING_SPACE";
	
	/**
	 * 空运货量统计导出模板名称
	 */
	public static final String AIR_CARGOVOLUME_EXCEL_NAME = "空运货量统计";
	
	/**
	 * 导出起始编号
	 */
	public static final int AIR_CARGOVOLUME_EXCEL_START_NUM = 0;
	
	/**
	 * 最大导出数据,默认为10万
	 */
	public static final int AIR_CARGOVOLUME_EXCEL_MAX_NUM = 100000;
	
	/**
	 * sheetName
	 */
	public static final String AIR_CARGOVOLUME_EXCEL_SHEETNAME = "sheet";
	
	/**
	 * 协议航班
	 */
	public static final String AIR_PROTOCOLFLIGHT_Y = "Y";
	
	/**
	 * 非协议航班
	 */
	public static final String AIR_PROTOCOLFLIGHT_N = "N";
	
	/**
	 * 当商品代号为为空默认为 ITEM_CODE
	 */
	public static final String AIR_WAYBILL_ITEM_CODE = "ITEM_CODE";
	
	/**
	 * 空运锁票状态——加锁
	 */
	public static final String AIR_WAYBILL_LOCK = "Y";
	
	/**
	 * 空运锁票状态——解锁
	 */
	public static final String AIR_WAYBILL_UNLOCK = "N";
	
	
	/**
	 * 快递卸车状态 取消:CANCLED，未开始:UNSTART,卸车中:UNLOADING  完成:FINISHED
	 */
	public static final String AIR_EXPRESS_UNLOAD_STATUS_CANCLED = "CANCLED";//取消
	
	public static final String AIR_EXPRESS_UNLOAD_STATUS_UNSTART = "UNSTART";//未开始

	public static final String AIR_EXPRESS_UNLOAD_STATUS_UNLOADING = "UNLOADING";//卸车中

	public static final String AIR_EXPRESS_UNLOAD_STATUS_FINISHED = "FINISHED";//完成

	public static final String AIR_PRECISION_UNLOAD_STATUS = "N/A";//完成

	
	
	/**
	 * 快递卸车任务分配状态 已分配-ASSINGED 未分配-UNASSIGN
	 */
	public static final String AIR_EXPRESS_UNLOAD_ASSINGED_STATUS_ASSINGED = "ASSINGED";//已分配
	
	public static final String AIR_EXPRESS_UNLOAD_ASSINGED_STATUS_UNASSIGN = "UNASSIGN";//未分配
	
	
	/***
	 * 空运交接类型 零担or快递 PRECISION_HANDOVER 表示零担 ,PACKAGE_HANDOVER 表示快递
	 * */

	public static final String AIR_HANDOVER_TYPE_PRECISION_HANDOVER = "PRECISION_HANDOVER";//零担交接
	
	public static final String AIR_HANDOVER_TYPE_PACKAGE_HANDOVER = "PACKAGE_HANDOVER";//快递交接
	//精准空运产品code
	public static final String AIR_PROCDUCT_CODE="AF";
	//商务专递产品code
	public static final String AIR_PACKAGE_PROCDUCT_CODE="DEAP";

	public static final String NEW_AIR_PICKUP_DETAIL_ID = "N/A";//新增未保存合票明细ID
	
	/**
	 * 同步航空证单信息至CUBC返回结果
	 * 0、失败；1、成功
	 */
	public static final String AIRFREIGHT_CUBC_FAILURE = "0";

}