package com.deppon.pda.bdm.module.core.shared.constants.version;

/**
 * 接货
 * 
 * @author mt 2013年7月17日18:40:38
 */
public interface AcceptConstant {
	/**
	 * 查询接货订单
	 */
	interface OPER_TYPE_ACCT_ORDER_QRY {
		public final static String VERSION = "ACCT_01";
	}

	/**
	 * 反馈订单已接收
	 */
	interface OPER_TYPE_ACCT_ORDER_RCV {
		public final static String VERSION = "ACCT_02";
	}

	/**
	 * 反馈订单已读
	 */
	interface OPER_TYPE_ACCT_ORDER_READ {
		public final static String VERSION = "ACCT_03";
	}

	/**
	 * 接收约车任务
	 */
	interface OPER_TYPE_ACCT_TASK_ACCT {
		public final static String VERSION = "ACCT_04";
	}

	/**
	 * 订单退回
	 */
	interface OPER_TYPE_ACCT_ORDER_BACK {
		public final static String VERSION = "ACCT_05";
	}

	/**
	 * 现场开单
	 */
	interface OPER_TYPE_ACCT_BILLING {
		public final static String VERSION = "ACCT_06";
	}

	/**
	 * 标签打印
	 */
	interface OPER_TYPE_ACCT_LABEL_PRINT {
		public final static String VERSION = "ACCT_07";
	}

	/**
	 * 重打标签
	 */
	interface OPER_TYPE_ACCT_LABEL_REPRT {
		public final static String VERSION = "ACCT_08";
	}

	/**
	 * 完成接货
	 */
	interface OPER_TYPE_ACCT_FINISH {
		public final static String VERSION = "ACCT_09";
	}

	/**
	 * 使用优惠券
	 */
	interface OPER_TYPE_ACCT_USE_COUPON {
		public final static String VERSION = "ACCT_10";
	}

	/**
	 * 退回优惠券
	 */
	interface OPER_TYPE_ACCT_BACK_USE_COUPON {
		public final static String VERSION = "ACCT_11";
	}

	/**
	 * PDA承运报价
	 */
	interface OPER_TYPE_ACCT_PDA_QUOTES {
		public final static String VERSION = "ACCT_12";
	}

	/**
	 * 查询产品信息
	 */
	interface OPER_TYPE_ACCT_PRODUCT_INFO {
		public final static String VERSION = "ACCT_13";
	}

	/**
	 * 提交重量体积
	 */
	interface OPER_TYPE_ACCT_WGHT_VOLUE_INFO {
		public final static String VERSION = "ACCT_14";
	}

	/**
	 * 未开单录入
	 */
	interface OPER_TYPE_ACCT_UNBILLING {
		public final static String VERSION = "ACCT_15";
	}

	/**
	 * 开单运费查询
	 */
	interface OPER_TYPE_ACCT_COUNT_FREIGHT {
		public final static String VERSION = "ACCT_16";
	}

	/**
	 * 快递现场开单
	 */
	interface OPER_TYPE_ACCT_KD_BILLING {
		public final static String VERSION = "ACCT_17";
	}

	/**
	 * 快递查询客户信息
	 */
	interface OPER_TYPE_ACCT_KD_QUERY_CUSINFO {
		public final static String VERSION = "ACCT_18";
	}

	/**
	 * 快递在线计算运费
	 */
	interface OPER_TYPE_ACCT_KD_COUNT_FREIGHT {
		public final static String VERSION = "ACCT_19";
	}

	/**
	 * 快递查询接货订单
	 */
	interface OPER_TYPE_ACCT_KD_ORDER_QRY {
		public final static String VERSION = "ACCT_20";
	}

	/**
	 * 快递使用优惠券
	 */
	interface OPER_TYPE_ACCT_KD_USE_COUPON {
		public final static String VERSION = "ACCT_21";
	}

	/**
	 * 现场开单修改（零担）
	 */
	interface OPER_TYPE_ACCT_BILLING_UPDATE {
		public final static String VERSION = "ACCT_22";
	}

	/**
	 * GPS地址采集
	 */
	interface OPER_TYPE_ACCT_COLLECT_GPS {
		public final static String VERSION = "ACCT_23";
	}

	/**
	 * 营销活动
	 */
	interface OPER_TYPE_ACCT_MARKETING_ACTIVITY {
		public final static String VERSION = "ACCT_24";
	}

	/**
	 * CRM营销活动
	 */
	interface OPER_TYPE_ACCT_CRMMARKETING_ACTIVITY {
		public final static String VERSION = "ACCT_25";
	}

	/**
	 * 快递点部对应营业部（即快递收入部门）
	 */
	interface OPER_TYPE_ACCT_KD_PARTSALESDEPT_ACTIVITY {
		public final static String VERSION = "ACCT_26";
	}

	/**
	 * 工作 开启/暂停接口
	 */
	interface OPER_TYPE_ACCT_WORKERSTATUS_ACTIVITY {
		public final static String VERSION = "ACCT_27";
	}

	/**
	 * 根据工号查看快递员状态
	 */
	interface OPER_TYPE_ACCT_QUERYONEBYEMPCODE_ACTIVITY {
		public final static String VERSION = "ACCT_28";
	}

	/**
	 * PDA转发人员集合
	 */
	interface OPER_TYPE_ACCT_QUERYFORWARDLIST_ACTIVITY {
		public final static String VERSION = "ACCT_29";
	}

	/*********************************************
	 * 电子运单 1、下拉待激活大客户 ACCT_30 2、下拉单个大客户运单明细 ACCT_31 3、下拉散客订单 ACCT_32
	 * 4、大客户电子运单扫描激活 ACCT_33 5、电子运单退回 ACCT_34 6、电子运单提交 ACCT_35 7、电子运单打印标签
	 * ACCT_36 8、散客电子运单扫描激活 ACCT_38
	 *********************************************/
	/**
	 * 下拉待激活大客户
	 */
	interface OPER_TYPE_ACCT_KD_BIGCUSTLIST {
		public final static String VERSION = "ACCT_30";
	}

	/**
	 * 下拉单个大客户运单明细
	 */
	interface OPER_TYPE_ACCT_KD_BIGCUSEWAYBILL {
		public final static String VERSION = "ACCT_31";
	}

	/**
	 * 下拉散客订单
	 */
	interface OPER_TYPE_ACCT_KD_INDIVIDUALCUST {
		public final static String VERSION = "ACCT_32";
	}

	/**
	 * 电子运单扫描
	 */
	interface OPER_TYPE_ACCT_KD_EWAYBILLSCAN {
		public final static String VERSION = "ACCT_33";
	}

	/**
	 * 电子运单状态反馈(退回)
	 */
	interface OPER_TYPE_ACCT_KD_EWAYBILLSTATE {
		public final static String VERSION = "ACCT_34";
	}

	/**
	 * 电子运单提交
	 */
	interface OPER_TYPE_ACCT_KD_EWAYBILLSUBMIT {
		public final static String VERSION = "ACCT_35";
	}

	/**
	 * 电子运单打印标签
	 */
	interface OPER_TYPE_ACCT_KD_EWAYBILLLABELPRINT {
		public final static String VERSION = "ACCT_36";
	}

	/**
	 * 电子运单扫描
	 */
	interface OPER_TYPE_ACCT_KD_INDIVIDUALEWAYBILLSCAN {
		public final static String VERSION = "ACCT_38";
	}

	/**
	 * 快递点部对应营业部（即快递收入部门）新 添加出发部门属性
	 */
	interface OPER_TYPE_ACCT_KD_NEWPARTSALESDEPT_ACTIVITY {
		public final static String VERSION = "ACCT_37";
	}

	/**
	 * 电子运单二期<br>
	 * 1、扫描接口 ACCT_39<br>
	 * 2、完成任务接口 ACCT_40<br>
	 * 3、创建装车任务 ACCT_41<br>
	 * 4、获取交接部门 ACCT_42<br>
	 * 5、装车提交 ACCT_43<br>
	 * 6、接货装车扫描 ACCT_44<br>
	 */

	/**
	 * @Description 电子运单二期 <br>
	 *              接货收件 扫描接口 <br>
	 *              ACCT_39
	 * @author 201638
	 * @date 2015-1-27
	 */
	interface OPER_TYPE_EXP_RCV_SCAN {
		public final static String VERSION = "ACCT_39";
	}

	/**
	 * @Description 电子运单二期 <br>
	 *              接货收件 完成任务接口<br>
	 *              ACCT_40
	 * @author 201638
	 * @date 2015-1-27
	 */
	interface OPER_TYPE_EXP_RCV_FINISH_TASK {
		public final static String VERSION = "ACCT_40";
	}

	/**
	 * @Description 电子运单二期 <br>
	 *              接货装车 创建装车任务 <br>
	 *              ACCT_41
	 * @author 201638
	 * @date 2015-1-27
	 */
	interface OPER_TYPE_EXP_RCV_CREATE_LOAD_TASK {
		public final static String VERSION = "ACCT_41";
	}

	/**
	 * @Description 电子运单二期 <br>
	 *              接货装车 获取交接部门<br>
	 *              ACCT_42
	 * @author 201638
	 * @date 2015-1-27
	 */
	interface OPER_TYPE_EXP_RCV_GET_HANDOVER_DEPT {
		public final static String VERSION = "ACCT_42";
	}

	/**
	 * @Description 电子运单二期 <br>
	 *              接货装车 装车提交 <br>
	 *              ACCT_43
	 * @author 201638
	 * @date 2015-1-27
	 */
	interface OPER_TYPE_EXP_RCV_LOAD_TASK_SUBMIT {
		public final static String VERSION = "ACCT_43";
	}

	/**
	 * @Description 电子运单二期<br>
	 *              接货装车 接货装车扫描 <br>
	 *              ACCT_44
	 * @author 201638
	 * @date 2015-1-27
	 */
	interface OPER_TYPE_EXP_RCV_LOAD_SCAN {
		public final static String VERSION = "ACCT_44";
	}
	
	
	/**
	 * @ClassName AcceptConstant.java 
	 * @Description 下来返货开单信息
	 * @author 201638
	 * @date 2015-3-9
	 */
	interface OPER_TYPE_EXP_RCV_RETURN_BILLING {
		public final static String VERSION = "ACCT_45";
	}
	
	/**
	 * 扫描收件任务提交，交接到营业部，生成交接单
	 * @author 201638
	 * @date 2015-3-21
	 */
	interface OPER_TYPE_EXP_SCAN_RCV_TASK_SUBMIT{
		public final static String VERSION ="ACCT_46";
		}
	
	
	/**
	 * 快递在查询时效线计算运费
	 */
	interface OPER_TYPE_ACCT_KD_EFFECT_FREIGHT {
		public final static String VERSION = "ACCT_47";
	}
	
	/**
	 * 快递返货工单上报
	 * 
	 * author:245960 Date:2015-08-19 comment:2015-09-08日常版本需求,pda前端对接人：袁苗苗,foss对接人：刘金伟
	 */
	interface OPER_TYPE_ACCT_KD_BACKGOODS_REPORT {
		public final static String VERSION = "ACCT_48";
	}
	
	/**
	 * 快递返货工单受理
	 * 
	 * author:245960 Date:2015-08-19 comment:2015-09-08日常版本需求,pda前端对接人：袁苗苗,foss对接人：刘金伟
	 */
	interface OPER_TYPE_ACCT_KD_BACKGOODS_DEAL {
		public final static String VERSION = "ACCT_49";
	}
	
	/**
	 * 订单短信发送优化   9月24版本  
	 * author:245955  Date:2015-09-16 
	 */
	interface OPER_TYPE_ACCT_KD_ORDER_SEND_SMS {
		public final static String VERSION = "ACCT_50";
	}
	
	/**
	 * 大客户标签打印
	 * (APP)GXG接货解决方案优化
	 * author:245960 Date:2015-08-19 comment:2015-09-28日常版本需求
	 *
	 */
	interface OPER_TYPE_BIG_CUSTMER_LABLE {
		public final static String VERSION = "ACCT_51";
	}
	
	/**
	 * 返货上报逻辑判断
	 * author:268974 Date:2015-10-14
	 */
	interface OPER_TYPE_EXP_RCV_RETURN_GOODS_WAY_BILL{
		public final static String VERSION = "ACCT_52";
	}
	/**
	 * PDA为大客户发件系统提供扫描信息
	 * author:268974 Date:2015-12-17 comment:2016-01-05日常版本需求
	 */
	interface OPER_TYPE_BIG_CUSTMER_SCAN_INFO{
		public final static String VERSION = "ACCT_55";
	} 

	/**
	 * 裹裹系统对接PDA
	 * author:245955 Date:2015-12-20
	 */
	interface OPER_TYPE_EXP_GOU_PDA_JMS{
		public final static String VERSION = "ACCT_54";
	}
	/**
	 * 快递员当前城市的所有营业区及对应营业部
	 * author:268974 Date:2015-12-17 comment:2016-01-05日常版本需求
	 */
	interface OPER_TYPE_EXP_RCV_QUDE_YINGYEQU_YINGYEBU{
		public final static String VERSION = "ACCT_53";
	}
	
	/**
	 * 获取转寄件退回打印信息 
	 * author:245955 Date:2016-03-15  comment:2016-03-20日常版本需求
	 */
	interface  OPER_TYPE_EXP_RCV_RETURN_PRINT{
		public final static String VERSION = "ACCT_56";
	}
	/**
	 * 零担电子运单查询订单及标签信息
	 * author:268974 Date:2016-05-17 零担电子面单项目
	 */
	interface OPER_TYPE_ERP_WAYBILL_LABLE{
		public final static String VERSION = "ACCT_64";
	}
	/**
	 * 零担电子运单扫描数据上传
	 * author:268974 Date:2016-05-17 零担电子面单项目
	 */
	interface OPER_TYPE_ERP_SCAN_UPL{
		public final static String VERSION = "ACCT_65";
	}
	/**
	 * 零担电子面单生成交接单
	 * author:268974 Date:2016-05-17 零担电子面单项目
	 */
	interface OPER_TYPE_ERP_CREATE_EIR{
		public final static String VERSION = "ACCT_66";
	}
	
	
	
	/**
	 * PDA与OMS快递接口对接
	 */
	/**
	 *  快递反馈订单已读   author:245955
	 */
	interface OPER_TYPE_ACCT_KD_ORDER_READ {
		public final static String VERSION = "ACCT_57";
	}
	
	/**
	 *  快递开启/暂停接口   author:245955
	 */
	interface OPER_TYPE_ACCT_KD_WORKERSTATUS_ACTIVITY {
		public final static String VERSION = "ACCT_58";
	}
	
	/**
	 * PDA转发人员集合   author:245955
	 */
	interface OPER_TYPE_ACCT_KD_QUERYFORWARDLIST_ACTIVITY {
		public final static String VERSION = "ACCT_59";
	}
	
	/**
	 * 快递反馈订单已接收 author:245955
	 */
	interface OPER_TYPE_ACCT_KD_ORDER_RCV {
		public final static String VERSION = "ACCT_60";
	}
	
	/**
	 * 快递订单退回/转发 author:245955
	 */
	interface OPER_TYPE_ACCT_KD_ORDER_BACK {
		public final static String VERSION = "ACCT_61";
	}
	
	/**
	 * 快递订单退回/转发 author:314587
	 */
	interface OPER_TYPE_ACCT_KD_VALIDATION_CODE {
		public final static String VERSION = "ACCT_63";
	}
}