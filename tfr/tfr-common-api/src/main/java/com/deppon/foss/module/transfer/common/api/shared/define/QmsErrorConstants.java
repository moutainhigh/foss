package com.deppon.foss.module.transfer.common.api.shared.define;
/**
 * @author niuly
 * @function QMS项目-差错相关定义
 * @date 2015年4月30日上午9:01:05
 */
public class QmsErrorConstants {
	//业务类型
	//快递
	public static final String QMS_K = "K";
	//零担
	public static final String QMS_L = "L";
	
	//业务类型编码
	//快递异常货物管理
	public static final String QMS_K_YCH = "QMS_K_YCH";
	//零担异常货物管理
	public static final String QMS_L_YCH = "QMS_L_YCH";
	
	//差错类型编码
	//K内物短少
	public static final String EXPRESS_IN_SHORT_CODE = "K201503250001"; 
	//K货单不符（找到）
	public static final String EXPRESS_LESS_GOODS_FIND_CODE = "K201503250002"; 
	//K货单不符（多货）
	public static final String EXPRESS_MORE_GOODS_CODE = "K201503250003"; 
	//K标签差错
	public static final String EXPRESS_LABEL_CODE = "K201503250004";
	//K建包差错
	public static final String EXPRESS_PACKAGE_CODE = "K201503250005";
	//K违规装卸货
	public static final String EXPRESS_ILLEGAL_DISCHARGE_CODE = "K201503250006";
	//K违规装码货
	public static final String EXPRESS_ILLEGAL_LOADING_CODE = "K201503250007";
	//K反签收差错
	public static final String EXPRESS_SIGN_RFC_CODE = "K201503250008";
	//K违规返货
	public static final String EXPRESS_ILLEGAL_RETURN_CODE = "K201503250009";
	//K验视章差错
	public static final String EXPRESS_INSPECTION_CHAPTER_CODE = "K201503250010";
	//K运收违禁品
	public static final String EXPRESS_CONTRABAND_CODE = "K201503250011";
	//K运收拒收品
	public static final String EXPRESS_REJECTS_CODE = "K201503250012";
	//K包装不合格
	public static final String EXPRESS_PACKAGE_UNQUALIFIED_CODE = "K201503250013";
	//K超重超方
	public static final String EXPRESS_OVERWEIGHT_CODE = "K201503250014";
	//K虚开内物带货
	public static final String EXPRESS_INNER_PICKUP_CODE = "K201503250015";
	//K开单款项不详
	public static final String EXPRESS_WAYBILL_INFO_UNKNOWN_CODE = "K201503250016";
	//K开单款项不一致
	public static final String EXPRESS_WAYBILL_INFO_DIFFER_CODE = "K201503250017";
	//K开单地址不详
	public static final String EXPRESS_WAYBILL_ADDRESS_UNKNOWN_CODE = "K201503250018";
	//K开单目的站错误
	public static final String EXPRESS_WAYBILL_DESTINATION_ERROR_CODE = "K201503250019";
	//K开单快递超派
	public static final String EXPRESS_BEYOND_DELIVERY_CODE = "K201503250020";
	//K开单补录超时
	public static final String EXPRESS_MAKEUP_TIMEOUT_CODE = "K201503250021";
	//K线上补码差错
	public static final String EXPRESS_ONLINE_COMPLETE_CODE = "K201503250022";
	//K线下补码差错
	public static final String EXPRESS_OFFLINE_COMPLETE_CODE = "K201503250023";
	//K理赔付款超时
	public static final String EXPRESS_CLAIM_PAY_TIMEOUT_CODE = "K201503250024";
	//K单据差错(财务)
	public static final String EXPRESS_BILL_ERROR_CODE = "K201503250025";
	//KPDA管理差错(无需提供接口)
//	K201503250026   K  KPDA管理差错
	//K代收货款差错
	public static final String EXPRESS_CODEAMOUNT_CODE = "K201503250027";
	
//	K201503250028	K	K坏账差错
//	K201503250029	K	K员工自离
//	K201503250030	K	K绩效差错
//	K201503250031	K	K考勤差错
//	K201503250032	K	K车辆被扣差错
//	K201503250033	K	K交通事故
//  K201503250034	K	K未开单理赔
//	K201503250035	K	K合同差错
//	K201503250036	K	K其他差错
	
	//L货单不符（有单无货）
	public static final String LESS_GOODS_CODE = "L201503250001"; 
	//L有货无单
	public static final String MORE_GOODS_CODE = "L201503250047"; 
	//L丢货（上报）（BI项目）
	//public static final String REPORT_LESS_GOODS_CODE = "L201503250003"; 
	//L内物短少
	public static final String IN_SHORT_CODE = "L201503250004"; 
	//L分批配载
	public static final String BATCH_LOADING_CODE = "L201503250005"; 
	//L标签差错
	public static final String LABEL_CODE = "L201503250006";
	//L违规装卸码货
	public static final String ILLEGAL_LOAD_UNLOAD_CODE = "L201503250007";
	//L违规返货
	public static final String ILLEGAL_RETURN_CODE = "L201503250008";
	//L反签收差错
	public static final String SIGN_RFC_CODE = "L201503250009";
	//L打木架不规范
	public static final String WOOD_PACKGE_UNQUALIFIED_CODE = "L201503250010";
	//L货物包装本身不合格
	public static final String PACKAGE_UNQUALIFIED_CODE = "L201503250011";
	//L运收拒收品
	public static final String REJECTS_CODE = "L201503250012";
	//K运收违禁品
	public static final String CONTRABAND_CODE = "L201503250013";
	//L开单差错
	public static final String CREATE_WAYBILL_CODE = "L201503250014";
	//L超重超方
	public static final String OVERWEIGHT_CODE = "L201503250015";
	//L虚开内部带货
	public static final String INNER_PICKUP_CODE = "L201503250016";
	
//	L201503250017	L	L其他财务差错
//	L201503250018	L	L报销超时
//	L201503250019	L	L押金差错
//	L201503250020	L	L营业款回款超时
//	L201503250021	L	L营业款认领超时/认领错误
//	L201503250022	L	L发票差错
//	L201503250023	L	L单据差错
//	L201503250024	L	L坏账差错
	
	//L代收货款差错
	public static final String CODEAMOUNT_CODE = "L201503250025";
	
//	L201503250026	L	L固定资产差错
//	L201503250027	L	L坐支营业款
//	L201503250028	L	L小票差错
//	L201503250029	L	L短款差错
//	L201503250030	L	L盘点表差错
//	L201503250031	L	L员工自离
//	L201503250032	L	L绩效差错
//	L201503250033	L	L员工违纪差错
//	L201503250034	L	L考勤差错
	
	//L超派差错
	public static final String BEYOND_DELIVERY_CODE = "L201503250035";
	
//	L201503250036	L	L未开单理赔
//	L201503250037	L	L交通事故
	//L封签差错
	public static final String SEAL_CODE = "L201503250038";
//	L201503250039	L	L异常支出
//	L201503250040	L	L其他差错
//	L201503250041	L	L物料申请超时差错
//	L201503250042	L	L商务车保养差错
//	L201503250043	L	L合同类差错

	//标准编码,文件编码F20150512002022，《快15-010快递返货管理规定》
	//K违规返货标准1
	public static final String EXPRESS_ILLEGAL_RETURN_STANDARD_CODE1 = "2132";
	//K违规返货标准2
	public static final String EXPRESS_ILLEGAL_RETURN_STANDARD_CODE2 = "2133";
	//K违规返货标准3
	public static final String EXPRESS_ILLEGAL_RETURN_STANDARD_CODE3 = "2134";



	//约束总体积与总重量
	/**
	 * 分批配载差错约束--体积约束
	 */
	public static final int GOODSVOLUMNTOTAL = 5;
	/**
	 * 分批配载差错约束--重量约束
	 */
	public static final int GOODSWEIGHTTOTAL = 1;
	
	/**
	 * 分批配载差错约束--卸车任务提交时间约束
	 */
	public static final int SUBMIT_HOURS = 8;
	//分批配载差错数据是否需要上报
	/**
	 * 分批配载差错数据是否需要上报--需要上报
	 */
	public static final String IS_NEED_REPORT_NEED = "Y";
	/**
	 * 分批配载差错数据是否需要上报--无需上报
	 */
	public static final String IS_NEED_REPORT_NONEED = "N";
	//事件经过
	/**
	 * 事件经过--分批配载
	 */
	public static final String EVENT_BATCH_LOADING = "分批配载";
	/**
	 * 事件经过--卸车多货
	 */
	public static final String EVENT_UNLOAD_MORE = "卸车多货";
	//默认值
	/**
	 * 常量，当传给QMS的数据字典缺失时，传该常量
	 */
	public static final String UNKNOWN_TYPE = "未知类型";
	/**
	 * 默认值 -- 空字符串
	 */
	public static final String DEFAULT_VALUE_EMPTY = "";
	//无需上报原因
	/**
	 * 无需上报原因编号--上报成功，无需再次上报
	 */
	public static final String NO_NEED_REPORT_REASON_NO_REPORT_SUCCESS = "SUCCESS";
	/**
	 * 无需上报原因--上报成功，无需再次上报
	 */
	public static final String NO_NEED_REPORT_REASON_REPORT_SUCCESS = "已经上报成功";
	/**
	 * 无需上报原因编号--已经全部签收
	 */
	public static final String NO_NEED_REPORT_REASON_NO_SIGN = "SIGN";
	/**
	 * 无需上报原因编号--已经全部签收
	 */
	public static final String NO_NEED_REPORT_REASON_SIGN = "已经签收";
	//qms端返回错误编码
	/**
	 * 返回信息编码-成功
	 */
	public static final String HANDLECODE_SUCCESS = "0";
	/**
	 * 返回信息编码-已经上报
	 */
	public static final String HANDLECODE_REPORTED = "9";
	/**
	 * 返回信息编码-有货无单
	 */
	public static final String HANDLECODE_HASGOODS_NOBILLS  = "10";
	/**
	 * 返回信息编码-已经上报卸车丢货
	 */
	public static final String HANDLECODE_UNLOADLESSGOODS_REPORTED = "40";
	
	//本地存储上报状态
	/**
	 * 上报成功
	 */
	public static final String REPORT_STATUS_SUCCESS = "SUCCESS";
	/**
	 * 上报失败
	 */
	public static final String REPORT_STATUS_FAIL = "FAIL";
	
	//文件标准
	/**
	 * 文件标准 (ID)-分批配载
	 */
	public static final int FILE_STANDARD_ID_BATCHLOADING = 1044;
	/**
	 * 文件标准 (ID)-卸车多货(快递)经手部门发现
	 */
	public static final int FILE_STANDARD_ID_UNLOADING_MORE_HANDING = 2005;
	/**
	 * 文件标准 (ID)-卸车多货(快递)非经手部门发现
	 */
	public static final int FILE_STANDARD_ID_UNLOADING_MORE_NOHANDING= 2006;
	/**
	 * 文件标准 (ID)-卸车多货(零担)经手部门发现
	 */
	public static final int FILE_STANDARD_ID_L_UNLOADING_MORE_HANDING = 3313;
	/**
	 * 文件标准 (ID)-卸车多货(零担)非经手部门发现
	 */
	public static final int FILE_STANDARD_ID_L_UNLOADING_MORE_NOHANDING= 3312;
	/**
	 * 文件标准 (NAME)-分批配载
	 */
	public static final String FILE_STANDARD_NAME_BATCHLOADING = "分批配载";
	/**
	 * 文件标准 (NAME)-卸车多货(零担)经手部门发现
	 */
	public static final String FILE_STANDARD_NAME_L_UNLOADING_MORE_HANDING = "有货无交接经手部门发现";
	/**
	 * 文件标准 (NAME)-卸车多货(零担)非经手部门发现
	 */
	public static final String FILE_STANDARD_NAME_L_UNLOADING_MORE_NOHANDING = "有货无交非接经手部门发现";
	/**
	 * 文件标准 (NAME)-卸车多货(快递)经手部门发现
	 */
	public static final String FILE_STANDARD_NAME_UNLOADING_MORE_HANDING = "卸车多货经手部门发现";
	/**
	 * 文件标准 (NAME)-卸车多货(快递)非经手部门发现
	 */
	public static final String FILE_STANDARD_NAME_UNLOADING_MORE_NOHANDING = "卸车多货非经手部门发现";
	
	//有货无交接 发现方式
	/**
	 * 发现方式-经手部门发现
	 */
	public static final String FIND_WAY_HANDING = "1";
	/**
	 * 发现方式-非经手部门发现
	 */
	public static final String FIND_WAY_NOHANDING = "2";
	//分隔符
	/**
	 * 上报时数据分隔符
	 */
	public static final String REPORT_SEPARATOR = ",";
	//多货类型 qms定义的码
	/***卸车多货**/
	public static final String MORECARGOTYPE_UNLOAD_MORE = "1";
	
	//上报无标签多货
	/**无标签多货上传图片等待时间**/
	public static final int CONNECTION_TIMEOUT = 50000;
}
