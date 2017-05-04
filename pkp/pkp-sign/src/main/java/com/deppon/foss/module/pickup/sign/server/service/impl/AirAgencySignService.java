/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/AirAgencySignService.java
 * 
 * FILE NAME        	: AirAgencySignService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 *****************************************************************************
 *SUC	
 *修订记录 
	日期 	修订内容 	修订人员 	版本号 
	2012-06-30 
		新增	王辉	V0.1
	2012-07-12
		1.	修改前置条件	王辉	V0.2
	 2012-07-24
	 	业务评审完毕，升至0.9	王辉	V0.9
	 2012-10-12
	  	  根据RC-448修改，添加业务规则与查询规则	  邓亭亭	 
	2012-10-29
		根据RC-1105修改	王辉	
	2012-11-19
		根据ISSUE-450修改	王辉	
	
	1.	
		SUC-123-签收偏线空运货物
	1.1
		相关业务用例
	BUC_FOSS_5.70.20_110 录入偏线签收
	BUC_FOSS_5.70.30_130 确认签收
	1.2
		用例描述
	偏线操作员录入偏线签收信息，系统记录签收信息。
	空运总调录入空运签收信息，系统记录签收信息。
	1.3	
		用例条件
	条件类型	描述	引用系统用例
	前置条件	
	1.	
		货物已出库	
	后置条件		
	1.4	
		操作用户角色
	操作用户	描述
	偏线操作员	
		录入偏线签收信息
	空运总调	
		录入空运签收信息
	1.5	
		界面要求
	1.5.1	
		表现方式
	Web页面 
	1.5.2
		界面原型
	   
	偏线空运签收
	1.5.3	
		界面描述
		1.	
		货物已出库	
	后置条件		
	1.4	
		操作用户角色
	操作用户	描述
	偏线操作员	
		录入偏线签收信息
	空运总调	
		录入空运签收信息
	1.5	
		界面要求
	1.5.1	
		表现方式
	Web页面 
	1.5.2
		界面原型
	   
	偏线空运签收
	1.5.3	
		界面描述
	界面标题：
	偏线空运签收
	偏线空运签收界面分为4个部分，
	包括待处理信息、
	查询条件、
	签收信息、
	运单信息。
	
	
	1.	
		待处理信息：
		单号、
		外发单号
	2.	
		查询条件：
		a)	
			单号
		b)
			收货人
		c)
			收货人电话
		d)
			运输方式：
			包括汽运、空运
		e)d)
			运输性质：
			包括汽运偏线和精准空运
		f)
			到达时间
		g)e)
			查询按钮
		h)f)
			重置按钮
	3.	
		签收信息： 
		a)
			提货人名称：
			提货人名称
		b)
			证件类型：
			证件类型
		c)
			证件号码：
			提货人有效证件号码
		d)
			签收情况
		e)
			签收件数
		f)
			签收备注
		g)
			签收时间
	
	4.	
		运单基本信息：参考“运单基本信息”数据元素
	
	页面初始化时，系统自动载入信息： 
	到达时间默认为当前系统时间前三天至当前时间
	
	需要提供的相关用例链接或提示信息：
	
	1.6	
		操作步骤
	序号	基本步骤	相关数据	补充步骤
	1		用户输入查询条件，点击“查询”按钮	查询条件	
		1．	
			待处理信息自动加载符合条件的运单信息，查询规则参考SR1
		2．
			参考SR2
	2		
			用户点击选择待处理信息中一行	运单基本信息	
			1.
				系统加载运单基本信息，参考SR3
	3		
			用户录入签收信息		
			参考SR4，SR5
	4		
			用户点击“提交”按钮		
			系统提示“是否确定提交签收信息？”
	5		
			用户点击确定		
		1.	
			系统记录运单的签收信息
		2.	
			系统发送短信至发货人、
			收货人，
			通知内容包括：
			货物单号、
			此货签收时间
			、签收件数、
			签收人
			、是否有异常
		3.	
			系统发送在线通知给发货部门，通知内容包括：
			货物单号
			、此货签收时间
			、签收件数、
			签收人、
			是否有异常
	
	扩展事件写非典型或异常操作过程
	序号	扩展事件	相关数据	备注
	1a	
		用户输入单号查询，
		不符合查询规则，
		系统提示异常信息		
		1.	
			汽运偏线的运单未录外发单，
			系统提示“未录入外发单，
			请先录入外发单”
		2.	
		
			运单库存状态非已出库，
			系统提示“货物未出库，
			请确认货物状态”
	2a	
		用户点击外发单号超链接
				弹出外发单明细界面，
				参考系统用例《查询偏线外发单》（SUC-114）
	3a	
		签收件数大于运单开单件数，系统提示异常信息“签收件数不能大于运单开单件数！”
				用户点击确定，终止操作
	5a	
		用户点击取消
				返回偏线空运签收界面
	
	
	1.7	
		业务规则
	序号	描述
	SR1		
		查询规则：
	1.	
		运输性质为汽运偏线或精准空运
	2.	
		汽运偏线的运单必须已录入外发单
	3.	
		库存状态必须为已出库
	4.	
		4.
		 EDI未做签收录入
			查询条件中优先级 
			单号>
			收货人手机>
			收货人电话>
			收货人>
			其他。
	3.5.	
		到达时间时间跨度只能为3天超过则提示：
		“到达时间最大跨度为3天”
	SR2	
		待处理信息的外发单号列
	当汽运偏线时，显示运单对应的外发单号且可超链接
	当精准空运时，显示为空
	SR3	
		当选择的运单为精准空运时，
		系统调用空运EDI接口返回签收信息并填写在界面中的签收信息中
	SR4SR3		
		签收情况为正常签收，签收备注自动填写“正常签收”
	SR5SR4
		1.
			 签收情况选择为正常签收、
			 异常-破损、
			 异常-潮湿、
			 异常-污染、
			 异常-其他时，
			 签收件数
			 显示为开单件数且不可编辑
		2. 
			签收情况选择为部分签收时
			，签收件数显示为开单件数且可编辑,
			不能大于开单件数。
			签收件数不能大于运单开单件数
	
	
	1.8	
		数据元素
	1.8.1	
		待处理信息
	字段名称 	说明 	输入限制	长度	是否必填	备注
	单号		文本			
	外发单号		文本			
	
	1.8.2	
		查询条件
	字段名称 	说明 	输入限制	长度	是否必填	备注
	单号		文本	10	否	
	收货人		文本	20	否	
	收货人电话		文本	20	否	
	运输方式		下拉框	5	否	
	运输性质		下拉框	5	否	
	到达时间		日期选择	6	是	
	
	1.8.3	
		签收信息
	字段名称 	说明 	输入限制	长度	是否必填	备注
	提货人证件名称		文本		是	
	证件类型		下拉框		是	数据字典，
							包括身份证、
							护照、
							驾驶证、
							户口本、
							军官证
	证件号码		文本		是	
	签收情况		下拉框		是	
							数据字典，
							包括正常签收、
							异常-弃货、
							异常-丢货、
							异常违禁品、
							异常-一般异常、
							部分签收，
							默认是正常签收
	签收件数		文本（仅数字）		是	
	签收备注		文本		是	
	签收时间		日期选择		是	
	
	1.8.4	
		运单基本信息
	字段名称 	说明 	输入限制	长度	是否必填	备注
	收货人信息	收货人/地址/电话	文本			
	单号		文本			
	到达时间		文本			
	库存状态		文本			
	通知成功		文本			
	上次通知时间		文本			
	付款方式(出发部门)	运单的付款方式	文本			
	到付总额		文本			
	代收货款		文本			
	运费		文本			
	送货费		文本			
	货物价值		文本			
	保价费		文本			
	其他费用		文本			
	费用说明		文本			
	派送方式		文本			
	货物名称		文本			
	件数		文本			
	重量		文本			
	体积		文本			
	包装		文本			
	发货人		文本			
	始发站		文本			
	始发部门		文本			
	运输方式		文本			
	运输性质		文本			
	出发时间		文本			
	
	
		运单基本信息
	字段名称 	说明 	输入限制	长度	是否必填	备注
	收货人信息	收货人/地址/电话	文本			
	单号		文本			
	到达时间		文本			
	库存状态		文本			
	通知成功		文本			
	上次通知时间		文本			
	付款方式(出发部门)	运单的付款方式	文本			
	到付总额		文本			
	代收货款		文本			
	1.9	
		非功能性需求
	使用量
		200000票/天
	2012年全网估计用户数
		10000用户
	响应要求（如果与全系统要求 不一致的话）
		3s内响应（同步官网异步同步）
	使用时间段	
	全天
	高峰使用时间段	
	09：00-18：00
	
	
	1.10	
		接口描述：
	接口名称 	
	对方系统（外部系统或内部其他模块）
	接口描述
	同步官网接口	
	官网	
	同步签收信息至官网
	外发单接口
	Foss-中转子系统
	查询偏线运单对应的外发单明细界面
	短信接口
	短信平台
	发送短信给发货人、收货人
	在线通知接口
	Foss-综合管理子系统
	发送通知给发货部门
 *
 **/
package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AgencyBranchOrCompanyDto;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.StorageJobDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.dao.IAirAgencySignDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRecordErrorWaybillService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRookieService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISerialSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SerialSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.BatchSignDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ExternalBillInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorImportantWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordUnnormalSignWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SendElectronicInvoiceSystemDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillActualFreightDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.AirAgencySignException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.util.DateCompareUtil;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWeixinSendService;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WeixinConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WeixinSendDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICUBCReverseSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICUBCSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCSignOrRevSignResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FOSSSignOrRevSignRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCReverseSignException;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCSignException;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirQueryModifyPickupbillService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.partialline.api.server.service.IAgentWaybillTrackService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExpressPushInfoDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.ProductCodeUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 签收空运偏线货物
 * 			service
 * @author 
 * 		  foss-meiying
 * @date
 * 		 2012-10-16 上午11:53:57
 * @since
 * @version
 */
public class AirAgencySignService implements IAirAgencySignService {
	
	private IWaybillSignResultDao waybillSignResultDao;
	public void setWaybillSignResultDao(IWaybillSignResultDao waybillSignResultDao) {
		this.waybillSignResultDao = waybillSignResultDao;
	}
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.AirAgencySignService";
	/**
	 * add by 353654  注入CUBC签收服务
	 */
	private ICUBCSignService cUBCSignService;
	
	public void setcUBCSignService(ICUBCSignService cUBCSignService) {
		this.cUBCSignService = cUBCSignService;
	}
	/**
	 * add by 353654 注入CUBC反签收申请。反签收操作
	 */	
	private ICUBCReverseSignService cUBCReverseSignService;
	
	public void setcUBCReverseSignService(
			ICUBCReverseSignService cUBCReverseSignService) {
		this.cUBCReverseSignService = cUBCReverseSignService;
	}
	/**
	 * 记录
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AirAgencySignService.class);
	
	/**
	 * 空运/偏线/快递代理没有流水号， 默认为0001
	 * */
	private static final String SERIALNO = "0001";
	/**
	 * 可为空的excel列名
	 */
	private static final String CAN_EMPTY_TITLE = "货物拉回异常描述";
	
	/**
	 * 快递100推送敏感词code
	 */
	private static final String REASON_CODE = "777";
	
	/**
	 * 总列数
	 */
	private static final int TOTAL_COLUMN_NUM = 8;
	/**
	 * 最大行数
	 */
	private static final int MAX_ROW_NUMS = 2000;//2000
	
	/**
	 * 外发单开单件数
	 */
	private static final int EXTERNAL_COLUMN_NUM = 1;
	
	/**
	 * 空运开单天数临界点
	 */
	private static final long DAYS=31;
	
	/**
	 * 签收空运偏线
	 * dao接口
	 */
	private IAirAgencySignDao airAgencySignDao;
	/***
	 * 记录异常运单 上报OA的Service
	 */
	private IRecordErrorWaybillService recordErrorWaybillService; 
	
	/**
	 *  运单签收结果
	 *  接口
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 *  结算签收
	 *  Service接口
	 */
	private ILineSignService lineSignService;

	/**
	 * 偏线
	 * Service接口
	 */
	private IExternalBillService externalBillService;
	
	/**
	 * 国际化信息
	 */
	private IMessageBundle messageBundle; 
	
	/**
	 * 快递代理外发单服务接口
	 */
	private ILdpExternalBillService ldpExternalBillService;
	
	/**
	 * 快递代理服务接口
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;

	/**
	 * 员工 service接口
	 */
	private IEmployeeService employeeService;

	/**
	 * 数据字典服务接口
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	/**
	 * 查询偏线代理
	 * 接口
	 */
	private IVehicleAgencyDeptService vehicleAgencyDeptService;

	/**
	 *  运单
	 *  service接口
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 行政区域
	 *  Service接口
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	/**
	 * 组织信息
	 *  Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 运单完结状态操作
	 * Service接口
	 */
	private IWaybillTransactionService waybillTransactionService;
	/**
	 * 运单状态服务
	 * 接口
	 */
	private IActualFreightService actualFreightService;
	/**
	 * 计算&调整走货路径类
	 * 接口
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	/**
	 * 外场相关共通
	 * 接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	/**
	 * 部门复杂service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/** 更改单service. */
	private IWaybillRfcService waybillRfcService;
	private IAirQueryModifyPickupbillService airQueryModifyPickupbillService;
	
	/**
	 * 库存服务
	 */
	private IStockService stockService;

	/**
	 * 代办事项服务类
	 */
	private ITodoActionService todoActionService;
	
	/**
	 * 推送微信服务类
	 */
	private IWeixinSendService weixinSendService;
	private INotifyCustomerService notifyCustomerService;
	/**
	 * 综合空运配置项获取值接口
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 保价声明价值
	 */
	private static final BigDecimal INSURANCEAMOUNT_NUM = new BigDecimal("10000");
	
	/**
	 * 子母件服务接口层
	 */
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
	/***
	 * 记录异常运单 上报QMS的Service
	 */
	private IRecordErrorWaybillDao recordErrorWaybillDao;
	/**
	 * 推送微信服务类
	 */
	public void setWeixinSendService(IWeixinSendService weixinSendService) {
		this.weixinSendService = weixinSendService;
	}
	/**
	 * 返货签收服务类
	 * */
	private IRookieService rookieService;
	public void setRookieService(IRookieService rookieService) {
		this.rookieService = rookieService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	/**
	 * 轨迹推送接口（快递100,、菜鸟）
	 */
	private ISendWaybillTrackService sendWaybillTrackService; 
	/**
	 * 返回工单送接口（判断工单处理状态）
	 */
	private IReturnGoodsRequestEntityService returnGoodsRequestEntityService;
	/**
	 * 轨迹推送接口 注入
	 */
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	private IWaybillExpressService waybillExpressService;
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	/**
	 * 外发流水签收
	 * service接口
	 */
	private ISerialSignResultService serialSignResultService;
	
	public void setSerialSignResultService(
			ISerialSignResultService serialSignResultService) {
		this.serialSignResultService = serialSignResultService;
	}
	
	/**
	 * 统计需要推送给快递100的运单轨迹类接口
	 */
	private IAgentWaybillTrackService agentWaybillTrackService;
	
	public void setAgentWaybillTrackService(
			IAgentWaybillTrackService agentWaybillTrackService) {
		this.agentWaybillTrackService = agentWaybillTrackService;
	}
	/**
	 * 运单dao接口
	 */
	private IWaybillDao waybillDao ;
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
	public void setRecordErrorWaybillDao(
			IRecordErrorWaybillDao recordErrorWaybillDao) {
		this.recordErrorWaybillDao = recordErrorWaybillDao;
	}

	/**
	 * 根据运单号查询运单基本信息
	 * @author 
	 * 		foss-meiying
	 * @date 
	 * 		2012-12-18 下午7:22:44
	 * @param waybillNo	
	 * 			运单号
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService
	 * #queryByWaybillNo(java.lang.String)
	 */
	@Override
	public WaybillDto queryByWaybillNo(String waybillNo) {
		if(StringUtil.isBlank(waybillNo)){//如果传入的运单号为空
			return null;//返回null
		}
		WaybillDto dto = airAgencySignDao.queryByWaybillNo(waybillNo);//根据运单号查询运单基本信息
		if(dto != null){
			if (dto.getStorageCharge() != null && dto.getStorageCharge().compareTo(BigDecimal.ZERO) > 0) {
				// 设置保管费计算的共通参数
				String[] codes = { NotifyCustomerConstants.STORAGE_CHARGE_MIN_DESC };// 最小仓储费
				// 系统配置参数获取
				StorageJobDto storageJobDto = notifyCustomerService.getConfigurationParams(codes);
				// 设置仓储费最小金额
				if (storageJobDto.getStorageChangeMin().compareTo(dto.getStorageCharge()) > 0) {
					dto.setStorageCharge(storageJobDto.getStorageChangeMin());
				}
			}
			//根据始发站Code查始发站name
			String deliveryCustomerCityName =administrativeRegionsService.queryAdministrativeRegionsNameByCode(dto.getDeliveryCustomerCityCode());
			if(StringUtil.isNotBlank(deliveryCustomerCityName)){//如果始发站名称不为空
				dto.setDeliveryCustomerCityName(deliveryCustomerCityName);//得到始发站名称
			}
			//根据始发部门code获取始发部门name
			String receiveOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(dto.getReceiveOrgCode());
			if(StringUtil.isNotBlank(receiveOrgName)){//如果始发部门名称不为空
				//得到始发部门名称 
				dto.setReceiveOrgName(receiveOrgName);
			}
			//收货客户的完整地址
			dto.setReceiveCustomerAddress(handleQueryOutfieldService.getCompleteAddressAttachAddrNote(dto.getReceiveCustomerProvCode(), dto.getReceiveCustomerCityCode(), dto.getReceiveCustomerDistCode(), dto.getReceiveCustomerVillageCode(), dto.getReceiveCustomerAddress(), dto.getReceiveCustomerAddressNote()));
		}
		return dto;//返回信息
	}
	/**
	 * 查询运单是否已经签收
	 * @author foss-meiying
	 * @date 2013-3-7 上午11:52:52
	 * @param waybillNo
	 * 		运单号
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService
	 * #queryByWaybillNoIsSign(java.lang.String)
	 */
	public WaybillDto queryByWaybillNoIsSign(String waybillNo,String serialNo) {
		WaybillDto waybillDto = new WaybillDto();
		if(StringUtil.isBlank(waybillNo)){//如果传入的运单号、流水号为空
			return null;//返回null
		}
		
		//判断当前运单是否是一票多件
		WaybillEntity waybill = waybillDao.queryWaybillByNo(waybillNo);//根据运单编号查询运单信息
		List<LdpExternalBillDto> ldpExternalBillDtos =  ldpExternalBillService.queryByWaybillNo(waybillNo);
		
		if(waybill != null && waybill.getGoodsQtyTotal() > EXTERNAL_COLUMN_NUM && ldpExternalBillDtos !=null && ldpExternalBillDtos.size() > 1){//是一票多件
			//根据运单号、流水号查询外发流水签收结果
			SerialSignResultEntity serial = new SerialSignResultEntity();
			serial.setWaybillNo(waybillNo);
			serial.setSerialNo(serialNo);
			SerialSignResultEntity serialEntity = serialSignResultService.queryByConditions(serial);
			if(serialEntity != null){
				throw new AirAgencySignException("该运单当前流水号已签收!");//该运单已经签收
			}
			waybillDto = this.queryByWaybillNo(waybillNo);//返回查询信息
			waybillDto.setIsOneInMore(FossConstants.YES);
		}else{//不是一票多件
			WaybillSignResultEntity  entity = new WaybillSignResultEntity(waybillNo,FossConstants.YES);
			//如果根据运单号查询运单签收结果有值
			if(waybillSignResultService.queryWaybillSignResultByWaybillNo(entity) != null){
				throw new AirAgencySignException(AirAgencySignException.WAYBILL_IS_SIGNED);//该运单已经签收
			}
			waybillDto = this.queryByWaybillNo(waybillNo);//返回查询信息
		}
		return waybillDto;		
	}
	/**
	 * 根据查询条件
	 * (单号，收货人，收货人电话，收货人手机,运输性质)
	 * 			查询空运运单
	 * @author 
	 * 			foss-meiying
	 * @date 
	 * 			2012-12-18 下午7:23:02
	 * @param entity 
	 * 		entity.waybillNo 
	 * 			运单号
	 * 		entity.receiveCustomerName
	 * 			收货人(收货客户名称)
	 * 		entity.receiveCustomerPhone
	 * 			货客户电话
	 * 		entity.receiveCustomerMobilephone
	 * 			收货人手机
	 * 		entity.active
	 * 			运单状态
	 * 		entity.settleStatus
	 * 			结清状态
	 * 		entity.lastLoadOrgCode
	 * 			最终配载部门（判断是否为本部门）
	 * 		entity.externalBillNo
	 * 			外发单号
	 * 		entity.productCode
	 * 			运输性质
	 * 		entity.transferExternal
	 * 			是否中转外发
	 * 		entity.storageTimeBegin
	 * 			入库时间(起)
	 * 		entity.storageTimeEnd
	 * 			入库时间(止)
	 * 		entity.storageQty
	 * 			件数
	 * 		entity.endStockOrgCode
	 * 			最后库存code
	 * 		entity.goodsAreaCode
	 * 			库区
	 * 		entity.auditStatus
	 * 			审核状态
	 * 		entity.arriveNotoutGoodsQty
	 * 			到达未出库件数
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService
	 * #queryAirInfobyParams(com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto)
	 */
	@Override
	public List<AirAgencyQueryDto> queryAirInfobyParams(AirAgencyQueryDto entity){
		List<AirAgencyQueryDto> airAgencyQueryDtos;
		// 如果所有查询条件都为空
		if (entity == null) {
			LOGGER.error("查询条件为空，不能进行查询");//记录日志
			throw new AirAgencySignException(AirAgencySignException.QUERY_EMPTY);//抛出异常
		}
		// 如果运单号不为空---收货人,收货人电话,收货人手机,为空
		if (StringUtil.isNotBlank(entity.getWaybillNo())) {
			entity.setReceiveCustomerName(null);//收货人为空
			entity.setReceiveCustomerMobilephone(null);//收货人手机为空
			entity.setReceiveCustomerPhone(null);//收货人电话为空
			entity.setArriveTimeBegin(null);//开单时间起为空
			entity.setArriveTimeEnd(null);//开单时间止为空
		} else if (StringUtil.isNotBlank(entity.getReceiveCustomerMobilephone())) {
			// 如果收货人手机不为空,让收货人,收货人电话为空
			entity.setReceiveCustomerName(null);//收货人为空
			entity.setReceiveCustomerPhone(null);//收货人电话为空
		} else if (StringUtil.isNotBlank(entity.getReceiveCustomerPhone())) {
			// 如果收货人电话不为空,让收货人为空
			entity.setReceiveCustomerName(null);
		}
		
		// 运输性质 为空
		if (StringUtil.isBlank(entity.getProductCode())) {
			LOGGER.error("运输性质为空，不能进行查询");//记录错误日志
			throw new AirAgencySignException(AirAgencySignException.PRODUCT_CODE_IS_NOT_NULL);//抛出异常
		}
		//有效
		entity.setActive(FossConstants.ACTIVE);
		List<String> waybillStatus = new ArrayList<String>();
		waybillStatus.add(WaybillConstants.OBSOLETE);//已作废
		waybillStatus.add(WaybillConstants.ABORTED);//已中止 
		entity.setWaybillStatus(waybillStatus);
		// 汽运偏线
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(entity.getProductCode())) {
			// 作废
			entity.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID);
			// 非中转外发
			entity.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
			//非营业部找到它上级所属外场的编码
			List<String> bizTypes = new ArrayList<String>();
			bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(entity.getLastLoadOrgCode(), bizTypes);
			if(orgAdministrativeInfoEntity != null){
				entity.setLastLoadOrgCode(orgAdministrativeInfoEntity.getCode());
			}
			entity.setEndStockOrgCode(entity.getLastLoadOrgCode());//最终库存部门是最终配载部门
			airAgencyQueryDtos = airAgencySignDao.queryWaybillNoByPartialLine(entity);
			
		} // 精准空运
		else if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(entity.getProductCode())) {
			entity.setAirCreateOrgCode(entity.getLastLoadOrgCode());
			entity.setLastLoadOrgCode(null);
			//获取空运总调
			List<String> list = new ArrayList<String>();
			list.add(BizTypeConstants.ORG_AIR_DISPATCH);
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(entity.getAirCreateOrgCode(), list);
			if(null == orgAdministrativeInfoEntity){
				IOrgAdministrativeInfoService orgAdministrativeInfoService = (IOrgAdministrativeInfoService) WebApplicationContextHolder
						.getWebApplicationContext().getBean(
								"orgAdministrativeInfoService");
				orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getAirCreateOrgCode());
			}
			if(orgAdministrativeInfoEntity != null){
				entity.setAirCreateOrgCode(orgAdministrativeInfoEntity.getCode());
			}
			entity.setEndStockOrgCodes(airAgencySignDao.queryAirTransferCenter());//得到空运对应的所有外场
			entity.setInOutStockType(StockConstants.AIR_HANDOVER_BILL_OUT_STOCK_TYPE);//航空交接单查询界面 出库
			airAgencyQueryDtos = airAgencySignDao.queryAirInfobyParams(entity);
		}
		// 经济快递
		else {
			/**
			 * BUGKD-1491 快递代理运单签收时，外发单应不要求被审核；即：快递代理外发单不是已审核的状态时，对应的运单是可以签收的，请开发修改
			 */
			// 已作废，只能这个状态的运单才不能进行签收
			entity.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID);
			// 非中转外发
			entity.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
			//判断是否查询的全部
			if(StringUtils.equals(entity.getProductCode(), SignConstants.EXPRESS_SIGN_STATUS_ALL)){
				entity.setProductCode("");
			}
			// 非营业部找到它上级所属外场的编码
			List<String> bizTypes = new ArrayList<String>();
			bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(entity.getLastLoadOrgCode(),
					bizTypes);
			if (orgAdministrativeInfoEntity != null) {
				entity.setLastLoadOrgCode(orgAdministrativeInfoEntity.getCode());
			}
			entity.setEndStockOrgCode(entity.getLastLoadOrgCode());// 最终库存部门是最终配载部门
			airAgencyQueryDtos = airAgencySignDao.queryExpressByPartialLine(entity);
		}
		//如果输入运单号查询结果为空,则找出查询结果为空的原因如下
		if(StringUtil.isNotBlank(entity.getWaybillNo()) && 0 == airAgencyQueryDtos.size() ){
			ActualFreightEntity actual = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
			if(actual !=null && WaybillConstants.OBSOLETE.equals(actual.getStatus())){// 已作废
				throw new AirAgencySignException(AirAgencySignException.WAYBILL_IS_OBSOLETE);//该运单状态已作废，不能进行签收
			}else if(actual !=null && WaybillConstants.ABORTED.equals(actual.getStatus())){// 已中止
				throw new AirAgencySignException(AirAgencySignException.WAYBILL_IS_ABORTED);//该运单状态已中止，不能进行签收
			}
			//查询运单信息  
			AirAgencyQueryDto dto =  new AirAgencyQueryDto();
			dto.setWaybillNo(entity.getWaybillNo());//运单号
			dto.setActive(FossConstants.ACTIVE);//有效
			AirAgencyDto airagencydto= airAgencySignDao.queryWaybillPartByCondition(dto);
			if(null == airagencydto){//如果查询的运单部分信息为空
				LOGGER.error("该运单号不存在!");//记录日志
				throw new AirAgencySignException(AirAgencySignException.WAYBILLNO_IS_NOT_EXIST);//抛出异常
			}else {
				//如果运单签收结果里已经存在该运单号
				if(StringUtil.isNotBlank(airagencydto.getWaybillNo())){
					LOGGER.error("运单号:"+entity.getWaybillNo()+"已经签收!");//记录日志
					throw new AirAgencySignException(AirAgencySignException.WAYBILL_IS_SIGNED);//抛出异常
				}
				//如果该运单的运输性质与所选的运输性质不一致!
				else if(!airagencydto.getProductCode().equals(entity.getProductCode())&&StringUtils.isNotBlank(entity.getProductCode())){
					LOGGER.error("运单号:"+entity.getWaybillNo()+"的运输性质与所选的运输性质不一致!");//记录日志
					throw new AirAgencySignException(AirAgencySignException.PRODUCT_CODE_IS_INCONSISTENCIES);//抛出异常
				}
				//如果当前登录部门与该运单的最终配载部门不一致
				else if(!airagencydto.getLastLoadOrgCode().equals(entity.getLastLoadOrgCode()) && ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(entity.getProductCode())){
					LOGGER.error("当前登录部门与该运单的最终配载部门不一致!");//记录日志
					throw new AirAgencySignException(AirAgencySignException.LAST_LOAD_ORGCODE_INCONSISTENCIES);//抛出异常
				}else {
					// 如果所选运输性质为汽运偏线
					//汽运偏线的运单未录外发单，系统提示“未录入外发单，请先录入外发单”
					if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(entity.getProductCode())){
						if(0 >= airAgencySignDao.queryIsTransferExternalByWaybillNo(entity)){
							LOGGER.error("运单号:"+entity.getWaybillNo()+"未录入外发单!");//记录日志
							throw new AirAgencySignException(AirAgencySignException.EXTERNAL_IS_NOT_INPUT);//抛出异常
						}else {
							//不作处理
						}
					} else if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(entity.getProductCode())){
						// 如果是空运
						if (airAgencySignDao.queryairWaybillOrgCodeIsSameByWaybillNo(entity) <= 0) {
							throw new AirAgencySignException(AirAgencySignException.AIR_ORG_CODE_NOT_SAME);// 抛出异常
																										   // 当前登录部门与航空正单的部门不一致
						}
						if(airagencydto.getEndStockOrgCode()!= null && entity.getEndStockOrgCodes() != null && entity.getEndStockOrgCodes().contains(airagencydto.getEndStockOrgCode())){
							//不作处理
						}else {
							LOGGER.error("当前运单最终库存部门不对");//记录日志
							throw new AirAgencySignException(AirAgencySignException.END_STOCK_ORG_CODE_WRONG);//当前运单最终库存部门不对
						}
					}
					else{									
						validEntityExtracted(entity);
						
						//最终配载部门和最终库存部门不一致
						String endCode = StringUtil.defaultIfNull(actual.getEndStockOrgCode());
						String loadCode = StringUtil.defaultIfNull(entity.getLastLoadOrgCode());
						if(!endCode.equals(loadCode)){
							throw new AirAgencySignException("最终配载部门["+ loadCode +"]和最终库存部门["+ endCode +"]不一致");// 抛出异常
						}
						
						
						List<InOutStockEntity> list = stockService.queryOutStockInfo(actual.getWaybillNo(), null, loadCode, actual.getCreateTime());
						//该单没有在最终配载部门出库
						if(CollectionUtils.isEmpty(list)){
							throw new AirAgencySignException("未在最终库存部门["+ endCode +"]出库！");// 抛出异常
						}
					}
					//运单库存状态非已出库，系统提示“货物未出库，请确认货物状态
					LOGGER.error("货物未出库，请确认货物状态!");//记录日志
					throw new AirAgencySignException(AirAgencySignException.GOODS_NOT_OUT);//抛出异常
				} 
			}
		}
		return airAgencyQueryDtos;//返回信息
	}

	private void validEntityExtracted(AirAgencyQueryDto entity) {
		if (0 >= airAgencySignDao.queryIsExpressExternalByWaybillNo(entity)) {
			LOGGER.error("运单号:" + entity.getWaybillNo() + "未录入外发单!");// 记录日志
			throw new AirAgencySignException(AirAgencySignException.EXTERNAL_IS_NOT_INPUT);// 抛出异常
		} else {
			//外发单审核状态
			String status = airAgencySignDao.queryExpressExternalBillStatusByNo(entity);
			//经济快递
			if (StringUtil.isBlank(status)) {
				LOGGER.error("运单号:" + entity.getWaybillNo() + "审核状态为[已作废]!");// 记录日志
				throw new AirAgencySignException("该运单对应的外发单状态为[已作废]");// 抛出异常
			}
		}
	}
	/**
	 * 
	 *  录入签收信息
	 * @author
	 * 		 foss-meiying
	 * @date 
	 * 		2013-3-8 上午10:21:27
	 * @param wayEntity 
	 * 			运单签收结果信息
	 * 		entity.waybillNo
	 * 			 运单号
	 * 		entity.signSituation
	 * 			签收情况
	 * 		entity.signNote 
	 * 			签收备注
	 * 		entity.identifyType
	 * 			证件类型
	 * 		entity.identifyCode
	 * 			证件号码
	 * 		entity.settleStatus
	 * 			结清状态
	 * 		entity.signGoodsQty
	 * 			签收件数
	 * 		entity.signTime
	 * 			签收时间
	 * 		entity.active
	 * 			是否有效
	 * 		entity.modifyTime
	 * 			时效时间
	 * 		entity.createTime
	 * 			生效时间
	 * 		entity.isPdaSign
	 * 			是否PDA签到
	 * 		entity.agentCode
	 * 			代理编码
	 * @param currentInfo 登录人信息
	 * 			currentInfo.operate 
	 * 				操作人
	 *          currentInfo.operatorCode 
	 *          	操作人编码
	 *          currentInfo.operateOrgName 
	 *          	操作部门名称
	 *          currentInfo.operateOrgCode 
	 *          	操作部门编码
	 * @param dto 结算出库参数
	 * 		dto.waybillNo
	 * 			运单号
	 * 		dto.productType
	 * 			运输性质
	 * 		dto.signOrgCode
	 * 			签收部门编码
	 * 		dto.signOrgName
	 * 			签收部门名称
	 * 		dto.signDate
	 * 			业务日期
	 * 		dto.isWholeVehicle
	 * 			是否整车运单
	 * 		dto.isPdaSign
	 * 			是否PDA签收
	 * 		dto.operatorCode
	 * 			操作人编码
	 * 		dto.operatorName
	 * 			操作人名称
	 * @param oldArriveNotoutGoodsQty
	 * 		  签收前的到达未出库件数
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService#addWaybillSignResult
	 * (com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo, com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto, java.lang.Integer)
	 */
	@Override
	@Transactional
	public String addWaybillSignResult(WaybillSignResultEntity wayEntity,CurrentInfo currentInfo, LineSignDto dto,Integer oldArriveNotoutGoodsQty) {
		if(!DateCompareUtil.isToday(wayEntity.getSignTime())){
			throw new AirAgencySignException(SignException.SIGNTIME_ISNOT_SYSTEMDATE,new Object[]{new SimpleDateFormat("yyyy-MM-dd").format(new Date())});//当前电脑时间有误，请调整日期为{0}
		}
		LOGGER.info("提交签收信息开始");//记录日志
		String resultMsg = "";//返回信息
		WaybillEntity waybill = waybillManagerService.queryPartWaybillByNo(dto.getWaybillNo());//根据运单号查询运单部分信息为空
		if(waybill != null){//如果返回结果不为空  
			if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())){
				if(StringUtils.isNotBlank(waybill.getFreightMethod()) && ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP.equals(waybill.getFreightMethod())){
					//调用中转接口判断该运单是否做过合大票
					if(CollectionUtils.isEmpty(airQueryModifyPickupbillService.queryAirPickupbillListForViewTrack(dto.getWaybillNo()))){//未做合大票  
						throw new AirAgencySignException(AirAgencySignException.WAYBILL_IS_NOT_FDP);
					}
				}
			}
			this.signOperat(wayEntity, currentInfo, dto,oldArriveNotoutGoodsQty,waybill);//执行签收操作
		}else {//如果为空
			throw new AirAgencySignException(AirAgencySignException.ENTITY_IS_NOT_EXIST);//抛出异常 该运单对应实体不存在 
		}
		WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
		BeanUtils.copyProperties(wayEntity,waybillSigndto);//把wayEntity里的内容复制到waybillSigndto里
		waybillSigndto.setSituation(wayEntity.getSignSituation());//签收情况
		if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybill.getProductCode())){//如果运输性质为汽运
			Boolean  result=false;
			try {
				result=waybillSignResultService.sendNotice(currentInfo, waybill.getReceiveOrgCode(), waybillSignResultService.getSmsContent(waybillSigndto,currentInfo,SignConstants.PKP_SIGN_NOTICE));
			} catch (BusinessException e) {
				// 异常处理
				LOGGER.error("--在线通知短信发送失败!" + e.getErrorCode(), e);//记录日志
				result=false;
			}
			if(result){//如果在线通知发送成功
				resultMsg=SignException.SIGN_NOTICE_OK;
			}else {
				resultMsg=SignException.SIGN_OK_NOTICE_FAILED;
			}
		}else if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())){
			long day=DateUtils.getTimeDiff(waybill.getBillTime(),new Date());
			String configValue=configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_SIGN_MESSAGE_SWITCH);
			if(!(day>DAYS&&FossConstants.YES.equals(configValue))){
				resultMsg =waybillSignResultService.sendMessNotice(waybill, currentInfo,waybillSigndto);
			}
		}else {
			//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
			resultMsg =waybillSignResultService.sendMessNotice(waybill, currentInfo,waybillSigndto);
		}
		LOGGER.info("提交签收信息完成");//记录日志
		//当签收情况为内物短少时，上报QMS内物短少差错
		if(wayEntity!=null&&ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT.equals(wayEntity.getSignSituation())){
			//保存QMS内物短少差错信息
			this.saveRecordShortErrorInfo(wayEntity,currentInfo);
		}else{
			//306548保存QMS重大货物异常差错信息(签收情况为异常，保价金额>=10000)
			if(wayEntity!=null&&wayEntity.getSignSituation().equals(ReportConstants.UNNORMAL_SIGN)&& (waybill.getInsuranceAmount().compareTo(INSURANCEAMOUNT_NUM)!=-1)){
				this.saveRecordImportantErrorInfo(wayEntity,currentInfo,waybill);
			}
		}
		return resultMsg;//返回信息
	}
	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.server.service.impl.AirAgencySignService.recordErrorWaybill
	 * @author: foss-yuting
	 * @description: foss记录内物短少差错 上报OA
	 * @date:2014年12月5日 下午15:59:21
	 */
	private void saveRecordShortErrorInfo(WaybillSignResultEntity wayEntity,CurrentInfo currentInfo) {
		LOGGER.info("*************保存上报QMS内物短少差错信息***********start");//记录日志
		RecordErrorWaybillDto recordErrorWaybillDto=new RecordErrorWaybillDto();
		if(wayEntity!=null && currentInfo != null){
			//主键
			recordErrorWaybillDto.setId(UUIDUtils.getUUID());
			//是否上报(Y:未上报)
			recordErrorWaybillDto.setActive(SignConstants.YES);
			//运单号
			recordErrorWaybillDto.setWaybillNo(wayEntity.getWaybillNo());
			//短少量
			recordErrorWaybillDto.setAlittleShort(null);
			//外包装是否完好
			recordErrorWaybillDto.setPackingResult(null);
			//创建时间
			recordErrorWaybillDto.setCreateTime(new Date());
			//修改时间
			recordErrorWaybillDto.setModifyTime(new Date());
			//上报人名字
			recordErrorWaybillDto.setOperateName(currentInfo.getEmpName());
			//上报人工号
			recordErrorWaybillDto.setOperateCode(currentInfo.getEmpCode());
			//上报人所在部门编码
			recordErrorWaybillDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			//上报人所在部门名称
			recordErrorWaybillDto.setOperateOrgName(currentInfo.getCurrentDeptName());
			//空运偏线没有流水号 默认为0001
			recordErrorWaybillDto.setSerialNo(SERIALNO);
			recordErrorWaybillService.recordErrorWaybillReportOA(recordErrorWaybillDto);
		}
		LOGGER.info("*************保存上报QMS内物短少差错信息***********end");//记录日志
	}
	
	/**
	 * @author 306548-foss-honglujun
	 * foss记录重大货物异常自动上报信息 OA
	 * PdaDeliverSignDto dto,ArriveSheetEntity entity,CurrentInfo currentInfo
	 */
	private void saveRecordImportantErrorInfo(WaybillSignResultEntity wayEntity,CurrentInfo currentInfo,WaybillEntity waybill){
		//记录日志
		LOGGER.info("*************保存QMS重大货物异常差错信息***********start");
		if(wayEntity != null && currentInfo != null && waybill != null){
			RecordErrorImportantWaybillDto recordErrorImportantWaybillDto = new RecordErrorImportantWaybillDto();
			//主键
			recordErrorImportantWaybillDto.setId(UUIDUtils.getUUID());
			//是否上报(Y:未上报)
			recordErrorImportantWaybillDto.setActive(SignConstants.YES);
			//运单号
			recordErrorImportantWaybillDto.setWaybillNo(wayEntity.getWaybillNo());
			//保价声明价值
			recordErrorImportantWaybillDto.setInsuranceAmount(waybill.getInsuranceAmount());
			//创建时间
			recordErrorImportantWaybillDto.setCreateTime(new Date());
			//修改时间
			recordErrorImportantWaybillDto.setModifyTime(new Date());
			//开单时间
			recordErrorImportantWaybillDto.setBillTime(waybill.getBillTime());
			//上报人名字
			recordErrorImportantWaybillDto.setOperateName(currentInfo.getEmpName());
			//上报人工号
			recordErrorImportantWaybillDto.setOperateCode(currentInfo.getEmpCode());
			//上报人所在部门编码
			recordErrorImportantWaybillDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			//上报人所在部门名称
			recordErrorImportantWaybillDto.setOperateOrgName(currentInfo.getCurrentDeptName());
			//foss记录重大货物异常自动上报信息 OA
			recordErrorWaybillService.recordErrorImportantWaybillReportOA(recordErrorImportantWaybillDto);
		}
		//记录日志
		LOGGER.info("*************保存QMS重大货物异常差错信息***********end");
	}
	
	/**
	 * @author: foss-231434-bieyexiong
	 * @description: foss记录异常 上报QMS
	 * @date:2016年02月18日 下午15:37:21
	 */
	private void saveRecordUnnormalErrorInfo(WaybillSignResultEntity wayEntity,String signSituation){
		if(wayEntity != null){
			LOGGER.info("*************保存QMS异常签收线上划责信息***********start");
			RecordUnnormalSignWaybillDto unnormalDto = new RecordUnnormalSignWaybillDto();
			//主键id
			unnormalDto.setId(UUIDUtils.getUUID());
			//运单号
			unnormalDto.setWaybillNo(wayEntity.getWaybillNo());
			//异常货物件数
			unnormalDto.setUnnormalNumber(NumberConstants.ONE);
			//签收情况
			unnormalDto.setSignSituation(signSituation);
			//签收时间
			unnormalDto.setSignTime(wayEntity.getSignTime()==null?new Date():wayEntity.getSignTime());
			//签收备注
			unnormalDto.setSignNote(wayEntity.getSignNote());
			//是否已上报(默认为Y，未上报)
			unnormalDto.setActive("Y");
			//创建时间
			unnormalDto.setCreateTime(new Date());
			//备注
			unnormalDto.setNote("未上报");
			
			//保存异常划责信息
			recordErrorWaybillDao.insertUnnormalEntity(unnormalDto);
			LOGGER.info("*************保存QMS异常签收线上划责信息***********end");
		}
	}
	
	/**
	 * 签收操作
	 * @author 
	 * 			foss-meiying
	 * @date 
	 * 			2013-3-8 上午10:22:18
	 * @param wayEntity
	 * 		entity.waybillNo
	 * 			 运单号
	 * 		entity.signSituation
	 * 			签收情况
	 * 		entity.signNote 
	 * 			签收备注
	 * 		entity.identifyType
	 * 			证件类型
	 * 		entity.identifyCode
	 * 			证件号码
	 * 		entity.settleStatus
	 * 			结清状态
	 * 		entity.signGoodsQty
	 * 			签收件数
	 * 		entity.signTime
	 * 			签收时间
	 * 		entity.active
	 * 			是否有效
	 * 		entity.modifyTime
	 * 			时效时间
	 * 		entity.createTime
	 * 			生效时间
	 * 		entity.isPdaSign
	 * 			是否PDA签到
	 * 		entity.agentCode
	 * 			代理编码
	 * @param currentInfo 当前登录人信息
	 * 			currentInfo.operate 
	 * 				操作人
	 *          currentInfo.operatorCode 
	 *          	操作人编码
	 *          currentInfo.operateOrgName 
	 *          	操作部门名称
	 *          currentInfo.operateOrgCode 
	 *          	操作部门编码
	 * @param dto
	 * 		dto.waybillNo
	 * 			运单号
	 * 		dto.productType
	 * 			运输性质
	 * 		dto.signOrgCode
	 * 			签收部门编码
	 * 		dto.signOrgName
	 * 			签收部门名称
	 * 		dto.signDate
	 * 			业务日期
	 * 		dto.isWholeVehicle
	 * 			是否整车运单
	 * 		dto.isPdaSign
	 * 			是否PDA签收
	 * 		dto.operatorCode
	 * 			操作人编码
	 * 		dto.operatorName
	 * 			操作人名称
	 * @param oldArriveNotoutGoodsQty
	 * 			签收前的到达未出库件数
	 * @param waybill
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService
	 * 			#signOperat(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity,
	 *  		com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo, 
	 *  		com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto,
	 *  		 java.lang.Integer)
	 */
	public SendElectronicInvoiceSystemDto signOperat(WaybillSignResultEntity wayEntity,CurrentInfo currentInfo, LineSignDto dto,Integer oldArriveNotoutGoodsQty,WaybillEntity waybill){
		//查询条件
		WaybillSignResultEntity wsignEntity = new WaybillSignResultEntity(dto.getWaybillNo(),FossConstants.YES);
		//判断当前运单是否是第一次签收
		WaybillSignResultEntity entity = waybillSignResultService.queryWaybillSignResultByWaybillNo(wsignEntity);
		if(null != entity){
			if(waybill.getGoodsQtyTotal() <= entity.getSignGoodsQty()){ //控制并发，
				throw new AirAgencySignException("当前运单已签收！");
			}
			if(waybill.getGoodsQtyTotal() < (entity.getSignGoodsQty() + wayEntity.getSignGoodsQty())){
				throw new AirAgencySignException("已签收件数+本次签收件数大于运单开单件数！");
			}
		}else{//开单件数和签收件数比较
			if(waybill.getGoodsQtyTotal() < wayEntity.getSignGoodsQty()){
				throw new AirAgencySignException("运单："+dto.getWaybillNo()+"签收件数 >开单件数");
			}
		}
		//校验未受理的更改单
		if(waybillRfcService.isExsitsWayBillRfc(dto.getWaybillNo())){
			// 抛出业务异常
			throw new AirAgencySignException(RepaymentException.EXIST_WAYBILLRFC);
		}
		WeixinSendDto sendDto = new WeixinSendDto();
		Integer result = 0;//默认值
		Date modifyTime = new Date();
		ActualFreightEntity actual = actualFreightService.queryByWaybillNo(dto.getWaybillNo());
		
		//DMANA-9716 add by chenjunying 2015-03-25  判断限制有投诉变更的运单 不让进行正常签收
		if(null==actual){
			//无实际承运表信息，抛出异常中断执行
			throw new AirAgencySignException("实际承运表中无该运单信息");
		}
		//限制 有投诉变更签收结果的运单，签收时再做正常签收
		if(SignConstants.YES.equals(actual.getComplainStatus()) && 
			SignConstants.NORMAL_SIGN.equals(wayEntity.getSignSituation())){ //有投诉自动变更，签收不让正常签收
			throw new AirAgencySignException("投诉自动变更异常签收的运单，反签收后不允许再做正常签收");
		}
		//签收情况
		String signSituation = wayEntity.getSignSituation();
		
		boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
		if(null != currentInfo){
			// 操作人名称
			dto.setOperatorName(currentInfo.getEmpName());
			// 操作人编码
			dto.setOperatorCode(currentInfo.getEmpCode());
			// 签收部门名称
			dto.setSignOrgName(currentInfo.getCurrentDeptName());
			// 签收部门编码
			dto.setSignOrgCode(currentInfo.getCurrentDeptCode());
		}else{
			// 操作人名称
			dto.setOperatorCode(wayEntity.getCreateUser());
		}
		// 签收时间
		dto.setSignDate(modifyTime);
		// 汽运偏线
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(dto.getProductType())) {
			dto.setSignType(SettlementConstants.PARTIAL_LINE_SIGN);
		}
		// 空运签收
		else if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(dto.getProductType())) {
			dto.setSignType(SettlementConstants.AIR_SIGN);
		}
		// 经济快递
		else if (waybillExpressService.onlineDetermineIsExpressByProductCode(dto.getProductType(),waybill.getBillTime())) {
			dto.setSignType(SettlementConstants.LAND_STOWAGE_SIGN);
		}
		// 不是PDA签收
		dto.setIsPdaSign(FossConstants.NO);
		//设置此次签收件数  add by 353654
		dto.setSignGoodsQty(wayEntity.getSignGoodsQty());
		try {
			LOGGER.info("--调用结算子系统“签收接口”开始传入参数：----"+ReflectionToStringBuilder.toString(dto));//记录日志
			WaybillSignResultEntity signEntity = new WaybillSignResultEntity();
			signEntity.setWaybillNo(waybill.getWaybillNo());
			signEntity.setActive(FossConstants.YES);
			//查询运单是否存在有效的签收结果（部分或全部，用来判断是否第一次签收）
			WaybillSignResultEntity wsEntity =  waybillSignResultService.queryWaybillSignResultByWaybillNo(signEntity);
			if(null == wsEntity){//运单第一次签收，调用结算接口
				// 调用结算出库接口
				this.confirmTaking(dto, currentInfo);
			}
		} catch (SettlementException se) {//捕获异常
			LOGGER.error("调用结算出库接口抛异常", se);//记录日志
			// 处理异常
			throw new AirAgencySignException(se.getErrorCode(), se);//抛出异常
		}
		// 判断代理网点是否为空
		if(StringUtils.isEmpty(wayEntity.getAgentCode())){
			OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybill.getCustomerPickupOrgCode(),FossConstants.ACTIVE);
			if (companyDto != null) {
				// 代理公司编码
				wayEntity.setAgentCode(StringUtil.defaultIfNull(companyDto.getAgentCompany()));
			}
		}
		wayEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());//签收部门编码
		wayEntity.setCreateOrgName(currentInfo.getCurrentDeptName());//签收部门名称
		wayEntity.setCreator(currentInfo.getEmpName());//操作人
		wayEntity.setCreatorCode(currentInfo.getEmpCode());//操作人编码
		// 生效时间为当前时间
		wayEntity.setCreateTime(modifyTime);
		// 时效时间为空，添加时采用默认值
		wayEntity.setModifyTime(null);
		// 运单号
		wayEntity.setWaybillNo(dto.getWaybillNo());
		wayEntity.setIsPdaSign(FossConstants.NO);// 不是PDA签收
		
		//根据签收情况设置签收状态
    	// 签收状态--部分签收
    	if (wayEntity.getSignSituation() != null && SignConstants.PARTIAL_SIGN.equals(wayEntity.getSignSituation())) {
    		wayEntity.setSignStatus(SignConstants.SIGN_STATUS_PARTIAL);
    	}
    	// 签收状态--全部签收
    	else{
    		if(FossConstants.YES.equals(wayEntity.getIsOneInMore()) && 
    				waybill.getGoodsQtyTotal() > (null != entity ? (entity.getSignGoodsQty() + wayEntity.getSignGoodsQty()) : wayEntity.getSignGoodsQty())){
    			// 签收状态--部分签收(一票多件按流水多次签收)
    			wayEntity.setSignStatus(SignConstants.SIGN_STATUS_PARTIAL);
    		}else{
    			//设置为正常签收
        		wayEntity.setSignStatus(SignConstants.SIGN_STATUS_ALL);
    		}
    		
    		waybillSignResultService.updateCrmOrder(waybill.getOrderNo(),currentInfo,wayEntity);//更新订单状态
    		//标识业务完结
    		WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
    		//运单号
    		waybillTransactionEntity.setWaybillNo(dto.getWaybillNo());
    		waybillTransactionService.updateBusinessOver(waybillTransactionEntity);//修改业务完结
    	}
    	//sendDto.setStateType(wayEntity.getSignSituation());
    	//sendDto.setWaybillNo(wayEntity.getWaybillNo());
    	//一票多件外发需求之前的逻辑：正常签收和部分签收只能被签收一次；现在一票多件按流水外发允许多次部分签收
    	sendDto.setCurrentPieces(wayEntity.getSignGoodsQty());
    	//一票多件货物外发，可分批多次签收；
    	if(wayEntity.getIsOneInMore() != null && wayEntity.getIsOneInMore().equals(FossConstants.YES)){
    		if(entity != null){
    			//签收情况
    			wayEntity.setSignSituation(SignConstants.NORMAL_SIGN.equals(entity.getSignSituation()) ? wayEntity.getSignSituation() : SignConstants.UNNORMAL_SIGN);
    			//签收件数
    			wayEntity.setSignGoodsQty(wayEntity.getSignGoodsQty() + entity.getSignGoodsQty());
    		}
    	}
    	sendDto.setStateType(wayEntity.getSignSituation());
    	sendDto.setProcessedPieces(wayEntity.getSignGoodsQty());
		//签收时间
		sendDto.setCreateTime(wayEntity.getSignTime()==null ? modifyTime:wayEntity.getSignTime());
		sendDto.setWaybillNo(wayEntity.getWaybillNo());
		//签收人
		sendDto.setSignName(wayEntity.getDeliverymanName());
		if(entity != null && FossConstants.YES.equals(wayEntity.getIsOneInMore())){//不是第一次签收且是一票多件
			// 运单签收结果里作废当前运单号
			waybillSignResultService.invalidWaybillSignResult(entity.getId(), new Date());
		}
		result = waybillSignResultService.addWaybillSignResult(wayEntity);
		//全部签收且非快递、非正常签收、非内物短少 上报异常线上划责差错
		if(wayEntity!=null
				&& !ProductCodeUtils.isExpress(waybill.getProductCode())
				&& !SignConstants.NORMAL_SIGN.equals(wayEntity.getSignSituation())
				&& !ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT.equals(wayEntity.getSignSituation())
				&& SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){
			//保存QMS异常线上划责差错信息
			this.saveRecordUnnormalErrorInfo(wayEntity,signSituation);
		}
		if(result >= 0){//如果添加成功
			//菜鸟轨迹 add by 231438
			//封装的轨迹方法 赋值，判断是否符合条件 调用中转的轨迹接口
			sendWaybillTrackService.rookieTrackingsForSign(wayEntity);

			//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，签收推送轨迹--add by 231438 快递100轨迹推送 
			sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);

			ActualFreightDto actualFreightDto = new ActualFreightDto(wayEntity.getWaybillNo(),wayEntity.getSignGoodsQty(),oldArriveNotoutGoodsQty);
			List<String> waybillStatus = new ArrayList<String>();
			waybillStatus.add(WaybillConstants.OBSOLETE);//已作废
			waybillStatus.add(WaybillConstants.ABORTED);//已中止 
			actualFreightDto.setWaybillStatus(waybillStatus);
			// 修改运单状态里的到达未出库件数
			if(actualFreightService.updateArriveNotoutGoodsQtyByWaybillNo(actualFreightDto)<=0){
				throw new AirAgencySignException(AirAgencySignException.SIGN_FAILED);//抛出异常
			}
			LOGGER.info("--运单状态里的到达未出库件数：", wayEntity.getSignGoodsQty());//记录日志
			todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),null);
			//应用监控签收调用
			waybillSignResultService.signCounterByWaybillNo(currentInfo, waybill);
			//如果该运单不是整车运单，执行以下操作(拒签不调用中转的走货线路接口，拒签什么都不做)
			if(FossConstants.NO.equals(dto.getIsWholeVehicle()) && !SignConstants.SIGN_STATUS_REFUSE.equals(wayEntity.getSignStatus())){
				// 调中转的走货路径接口
				try {
					calculateTransportPathService.signIn(dto.getWaybillNo(), TransportPathConstants.TRANSPORTPATH_STATUS_SIGNIN);
				} catch (TfrBusinessException e) {//捕获异常
					LOGGER.error("--调中转的走货路径接口有异常", e);//记录日志
					throw new AirAgencySignException(e.getMessage(), e);//抛出异常
				}
				LOGGER.info("--该 运单不是整车运单，调用中转走货路径接口完成,运单号"+dto.getWaybillNo());//记录日志
			}		
		}else {
			LOGGER.error("添加运单签收结果失败，签收操作不成功");//记录日志
			//签收失败，抛出异常信息
			throw new AirAgencySignException(AirAgencySignException.WAYBILL_SIGN_RESULT_ADD_FAILED);//抛出异常
		}	
		/**
		 * 新增MANA-771需求，添加微信消息推送的方法
		 * @author Foss-105888-Zhangxingwang
		 * @date 2014年3月10日 14:04:04
		 */
		LOGGER.info("偏线开始处理微信消息,单号为："+sendDto.getWaybillNo());
		ResultDto resultDto = weixinSendService.sysnWeixinInfoForWebSiteUnDirectly(sendDto, WeixinConstants.WEIXIN_SIGN_TYPE);
		if(ResultDto.FAIL.equals(resultDto.getCode())){
			LOGGER.info("偏线微信消息推送失败。错误详情："+resultDto.getMsg());
		}
		LOGGER.info("微信消息处理完毕哦，此处略去一万字");
		if(dto.getIsReturnNewWaybill() ==null ){
			//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
			if(isSendInvoiceInfo){
				waybillSignResultService.sendInvoiceInfo(waybill,actual);
			}
		}
		SendElectronicInvoiceSystemDto sendElectronicInvoiceSystemDto =new SendElectronicInvoiceSystemDto();
		sendElectronicInvoiceSystemDto.setIsSendInvoiceInfo(isSendInvoiceInfo);
		sendElectronicInvoiceSystemDto.setEntity(waybill);
		sendElectronicInvoiceSystemDto.setActual(actual);
		return sendElectronicInvoiceSystemDto;//返回信息
	}
	/**
	 * 根据运单查询偏线外发单信息
	 * 如果存在返回查询结果信息
	 * 如果不存在返回null
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:29:04
	 * @param waybillNo
	 *            运单号
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService
	 * 		#queryExternalBillByWaybillNo(java.lang.String)
	 */
	@Override
	public ExternalBillInfoDto queryExternalBillByWaybillNo(String waybillNo) {
		ExternalBillDto tempDto = new ExternalBillDto();
		// 运单号
		tempDto.setWaybillNo(waybillNo);
		// 通过运单号查询偏线外发单信息
		tempDto = externalBillService.queryExternalBillByWaybillNoForChange(tempDto);
		if(tempDto == null){//查询结果为空
			return null;//返回null
		}
		return this.getExternalBillInfoDto(tempDto);//返回偏线外发单信息
	}
	/** 
	 * 根据运单查询快递代理外发单信息，如果存在返回查询结果信息，如果不存在返回null
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-21 上午9:30:49
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService#queryExternalBillByWaybillNo(java.lang.String)
	 */
	@Override
	public ExternalBillInfoDto queryExpressExternalBillBNo(String waybillNo,String serialNo) {
		LdpExternalBillDto tempDto = new LdpExternalBillDto();
		// 运单号
		tempDto.setWaybillNo(waybillNo);
		//流水号
		tempDto.setSerialNo(serialNo);
		// 通过运单号查询偏线外发单信息
		tempDto = ldpExternalBillService.queryLdpExternalBillByWaybillNoForChange(tempDto);
		if (tempDto == null) {// 查询结果为空
			return null;// 返回null
		}
		return this.getExpressExternalBillDto(tempDto);// 返回偏线外发单信息
	}

	/**
	 * 得到偏线外发单部分信息
	 * 
	 * @author
	 * 				foss-meiying
	 * @date 
	 * 				2012-12-12 下午9:11:41
	 * @param tempDto
	 * 			tempDto.waybillNo 
	 * 				运单号
	 * 			tempDto.externalBillNo 
	 * 				外发单号
	 * 			tempDto.externalUserName
	 * 				 外发员
	 * 			tempDto.externalAgencyFee 
	 * 				外发代理费
	 * 			tempDto.deliveryFee
	 * 				 付送货费
	 * 			tempDto.costAmount 
	 * 				外发成本总额
	 * 			tempDto.receiveAgencyFee
	 * 				 实收代理费
	 * 			tempDto.payAgencyFee 
	 * 				实付代理费
	 * 			tempDto.isWriteOff 
	 * 				自动核销申请
	 * 			tempDto.notes 
	 * 				备注
	 * 			tempDto.transferExternal 
	 * 				中转外发
	 * 			tempDto.externalOrgName 
	 * 				外发部门
	 * 			tempDto.registerTime 
	 * 				录入日期
	 * @return
	 * @see
	 */
	private ExternalBillInfoDto getExternalBillInfoDto(ExternalBillDto tempDto) {
		ExternalBillInfoDto dto = new ExternalBillInfoDto();
		// 通过录入员编码查询姓名
		EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(tempDto.getRegisterUserCode());
		if (emp != null && StringUtils.isNotBlank(emp.getEmpName())) {
			// 录入人
			dto.setRegisterUser(emp.getEmpName());
		}
		// 运单号
		dto.setWaybillNo(tempDto.getWaybillNo());
		// 外发单号
		dto.setExternalBillNo(tempDto.getExternalBillNo());
		// 外发员
		dto.setExternalUserName(tempDto.getExternalUserName());
		// 外发代理费
		dto.setExternalAgencyFee(tempDto.getExternalAgencyFee());
		// 付送货费
		dto.setDeliveryFee(tempDto.getDeliveryFee());
		// 外发成本总额
		dto.setCostAmount(tempDto.getCostAmount());
		// 实收代理费
		dto.setReceiveAgencyFee(tempDto.getReceiveAgencyFee());
		// 实付代理费
		dto.setPayAgencyFee(tempDto.getPayAgencyFee());
		// 自动核销申请
		dto.setIsWriteOff(tempDto.getIsWriteOff());
		// 备注
		dto.setNotes(tempDto.getNotes());
		// 中转外发
		dto.setTransferExternal(tempDto.getTransferExternal());
		// 外发部门
		dto.setExternalOrgName(tempDto.getExternalOrgName());
		// 录入日期
		dto.setRegisterTime(tempDto.getRegisterTime());
		// 根据外发代理编号查询,外发代理,到达网点,到达网点电话,到达网点地址,代理电话
		// 提货网点CODE
		WaybillEntity waybill = waybillManagerService.queryPartWaybillByNo(tempDto.getWaybillNo());
		String agencyBranchCode = waybill.getCustomerPickupOrgCode();//提货网点
		// 币种
		dto.setCurrencyCode(waybill.getCurrencyCode());
		// 到付金额
		dto.setToPayAmount(waybill.getToPayAmount());
		// 付款方式
		dto.setPaidMethod(waybill.getPaidMethod());
		AgencyBranchOrCompanyDto companyDto = null;
		if (StringUtils.isNotBlank(agencyBranchCode)) {
			// 代理信息Dto
			companyDto = vehicleAgencyDeptService.queryAgencyBranchCompanyInfo(agencyBranchCode);
		}
		if (companyDto != null) {
			// 如果外发代理(代理公司名称)不为空
			if (StringUtils.isNotBlank(companyDto.getAgentCompanyName())) {
				dto.setAgentCompanyName(companyDto.getAgentCompanyName());//得到外发代理(代理公司名称)
			}
			// 如果到达网点不为空
			if (StringUtils.isNotBlank(companyDto.getAgentDeptName())) {
				dto.setAgentDeptName(companyDto.getAgentDeptName());//得到到达网点
			}
			// 如果到达网点电话不为空
			if (StringUtils.isNotBlank(companyDto.getBranchContactPhone())) {
				dto.setContactPhone(companyDto.getBranchContactPhone());//得到到达网点电话
			}
			// 如果到达网点地址不为空
			if (StringUtils.isNotBlank(companyDto.getBranchAddress())) {
				dto.setAddress(companyDto.getBranchAddress());//得到到达网点地址
			}
		}
		return dto;//返回信息
	}
	/**
	 * 得到快递代理外发单部分信息
	 * 
	 * @author foss-meiying
	 * @date 2012-12-12 下午9:11:41
	 * @param tempDto
	 *            tempDto.waybillNo 运单号 tempDto.externalBillNo 外发单号
	 *            tempDto.externalUserName 外发员 tempDto.externalAgencyFee 外发代理费
	 *            tempDto.deliveryFee 付送货费 tempDto.costAmount 外发成本总额
	 *            tempDto.receiveAgencyFee 实收代理费 tempDto.payAgencyFee 实付代理费
	 *            tempDto.isWriteOff 自动核销申请 tempDto.notes 备注
	 *            tempDto.transferExternal 中转外发 tempDto.externalOrgName 外发部门
	 *            tempDto.registerTime 录入日期
	 * @return
	 * @see
	 */
	private ExternalBillInfoDto getExpressExternalBillDto(LdpExternalBillDto tempDto) {
		ExternalBillInfoDto dto = new ExternalBillInfoDto();
		// 录入人
		dto.setRegisterUser(tempDto.getModifyUserName());

		// 运单号
		dto.setWaybillNo(tempDto.getWaybillNo());
		// 外发单号
		dto.setExternalBillNo(tempDto.getExternalBillNo());
		// 外发员
		dto.setExternalUserName(tempDto.getExternalUserName());
		// 代收货款
		dto.setExternalAgencyFee(tempDto.getCodAmount());
		// 外发运费
		dto.setDeliveryFee(tempDto.getFreightFee());
		// 到付金额
		dto.setCostAmount(tempDto.getPayDpFee());
		// 应收费用
		dto.setReceiveAgencyFee(tempDto.getBillReceiveable());
		// 应付费用
		dto.setPayAgencyFee(tempDto.getBillPayable());
		// // 自动核销申请
		// dto.setIsWriteOff(tempDto.getIsWriteOff());
		// 备注
		dto.setNotes(tempDto.getNotes());
		// 中转外发
		dto.setTransferExternal(tempDto.getTransferExternal());
		// 外发部门
		dto.setExternalOrgName(tempDto.getExternalOrgName());
		// 录入日期
		dto.setRegisterTime(tempDto.getRegisterTime());
		// 根据外发代理编号查询,外发代理,到达网点,到达网点电话,到达网点地址,代理电话
		// 提货网点CODE
		WaybillEntity waybill = waybillManagerService.queryPartWaybillByNo(tempDto.getWaybillNo());
		String agencyBranchCode = waybill.getCustomerPickupOrgCode();// 提货网点
		// 币种
		dto.setCurrencyCode(waybill.getCurrencyCode());
		// 到付金额
		dto.setToPayAmount(waybill.getToPayAmount());
		// 付款方式
		dto.setPaidMethod(waybill.getPaidMethod());
		if (StringUtils.isNotEmpty(agencyBranchCode)) {
			// 代理信息Dto
			OuterBranchExpressEntity  companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(agencyBranchCode, FossConstants.YES);
			if (companyDto != null) {
				// 代理公司名称
				dto.setAgentCompanyName(StringUtil.defaultIfNull(companyDto.getAgentCompanyName()));
				// 代理公司编码
				dto.setAgentCompanyCode(StringUtil.defaultIfNull(companyDto.getAgentCompany()));
				// 代理网点名称
				dto.setAgentDeptName(StringUtil.defaultIfNull(companyDto.getAgentDeptName()));
				// 代理网点电话
				dto.setContactPhone(StringUtil.defaultIfNull(companyDto.getContactPhone()));
				// 代理网点地址
				dto.setAddress(StringUtil.defaultIfNull(companyDto.getAddress()));
				// 代理网点编码
				dto.setAgentDeptCode(StringUtil.defaultIfNull(agencyBranchCode));
			}
		}
		return dto;// 返回信息
	}

	/**
	 * 反签收（偏线和空运） 接口
	 * 
	 * @author foss-meiying
	 * @date 2013-1-23 下午4:57:51
	 * @param id
	 * 			运单签收结果的id
	 * @param currentInfo 当前登录人的信息
	 * 			currentInfo.operate 
	 * 				操作人
	 * 			 currentInfo.operatorCode 
	 * 				操作人编码
	 * 			 currentInfo.operateOrgName
	 * 				 操作部门名称
	 * 			 currentInfo.operateOrgCode 
	 * 				操作部门编码
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService
	 * 		#reverseWaybillSignResult(java.lang.String, 
	 * 		com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	public void reverseWaybillSignResult(String id,CurrentInfo currentInfo) {
		if (StringUtil.isBlank(id) ) {//如果传入的参数为空
			LOGGER.error("传入的id为空，不能进行反签收操作");//记录日志
			throw new AirAgencySignException(AirAgencySignException.ID_ISNULL);//抛出异常
		}
		LOGGER.error("--反签收（偏线和空运）接口开始");//记录日志
		Date systemDate = new Date();//当前系统时间
		// 根据运单号查询运单签收结果
		WaybillSignResultEntity signResult = waybillSignResultService.queryWaybillSignResultById(id);
		if (signResult != null) {
			// 作废运单签收结果里当前运单号
			waybillSignResultService.invalidWaybillSignResult(signResult.getId(), systemDate);
			// 作废外发流水签收里当前运单号
			serialSignResultService.invalidWaybillSignResult(signResult.getWaybillNo(), new Date());
		}else {
			LOGGER.error("该运单在运单签收结果里不存在");//记录日志
			throw new AirAgencySignException(AirAgencySignException.WAYBILL_SIGN_RESULT_IS_NOT_EXIST);//抛出异常
		}
		WaybillEntity waybillEntity = waybillManagerService.queryPartWaybillByNo(signResult.getWaybillNo());
		if(null == waybillEntity){
			LOGGER.error("该运单对应实体不存在");//记录日志
			throw new AirAgencySignException(AirAgencySignException.ENTITY_IS_NOT_EXIST);//抛出异常
		}
		
		//调用结算的反签收接口
		try {
			String waybillNo = signResult.getWaybillNo();
			//判断是否子母件
			Map<String,Object> params = new HashMap<String,Object>();
			params.put(SignConstants.WAYBILL_NO,waybillNo);
			params.put(SignConstants.ACTIVE,FossConstants.YES);
			TwoInOneWaybillDto twoInOneDto = waybillRelateDetailEntityService.
															queryWaybillRelateByWaybillOrOrderNo(params);
			//开关，判断是否还需要调用结算反签收接口,默认为需要
			boolean isReverseConfirm = true;
			//是子母件,并且子件集合不为空
			if(twoInOneDto != null && FossConstants.YES.equals(twoInOneDto.getIsTwoInOne())
					&& CollectionUtils.isNotEmpty(twoInOneDto.getWaybillNoList())){
				boolean isFirst = this.isFirst(waybillNo,twoInOneDto);
				//是子件并且是第一个
				if(!waybillNo.equals(twoInOneDto.getMainWaybillNo()) && isFirst){
					WaybillEntity mainWaybillEntity = waybillManagerService.queryPartWaybillByNo(twoInOneDto.getMainWaybillNo());
					try{
						//调用结算的反签收接口将母件反签收
						this.reverseConfirmTaking(twoInOneDto.getMainWaybillNo(),mainWaybillEntity,currentInfo,systemDate);
					}catch(SettlementException e){
						LOGGER.error("母单号调用财务接口抛出异常",e);//记录日志
						throw new AirAgencySignException("母单号" + twoInOneDto.getMainWaybillNo() + "反签收异常：" + e.getErrorCode(),e);//抛出异常
					}
				}
				//是母件并且不是第一个,由于子件反签收时，已将母件的反结算，所以母件不用再反结算
				if(waybillNo.equals(twoInOneDto.getMainWaybillNo()) && !isFirst){
					isReverseConfirm = false;
				}
			}
			if(isReverseConfirm){
				//调用结算反签收
				this.reverseConfirmTaking(waybillNo, waybillEntity, currentInfo, systemDate);
			}
		} catch (SettlementException e) {//捕获异常
			LOGGER.error("调用财务接口抛出异常",e);//记录日志
			throw new AirAgencySignException(e.getErrorCode(),e);//抛出异常
		}
		// 更新ActualFreight中的 到达未出库件数
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		//签收件数
		actualFreightEntity.setArriveNotoutGoodsQty(signResult.getSignGoodsQty());
		//运单号
		actualFreightEntity.setWaybillNo(signResult.getWaybillNo());
		actualFreightService.updateActualFreightPartByWaybillNo(actualFreightEntity);//修改到达未出库件数
		//反标识业务完结
		WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
		//运单号
		waybillTransactionEntity.setWaybillNo(signResult.getWaybillNo());
		waybillTransactionService.updateReverseBusinessOver(waybillTransactionEntity);//反标识业务完结
		if(FossConstants.NO.equals(waybillEntity.getIsWholeVehicle())){
			// 根据运单号查运单运输性质
			try {
				calculateTransportPathService.rollBack(signResult.getWaybillNo(), null,null, TransportPathConstants.TRANSPORTPATH_STATUS_SIGNIN);
			} catch (TfrBusinessException e) {//捕获异常
				LOGGER.error(e.getMessage(), e);//记录日志
				throw new AirAgencySignException(e.getMessage(),e);//抛出异常
			}
			LOGGER.info("调用走货路径回退上一步完成");//记录日志
		}
		LOGGER.info("--反签收（偏线和空运）接口结束");//记录日志
	}
	/**
	 * Sets 
	 * 		the 查询偏线代理.
	 *
	 * @param vehicleAgencyDeptService 
	 * 		the new 查询偏线代理
	 */
	public void setVehicleAgencyDeptService(IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 		员工service.
	 *
	 * @param employeeService 
	 * 		the 
	 * 		new 员工service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public void setRecordErrorWaybillService(
			IRecordErrorWaybillService recordErrorWaybillService) {
		this.recordErrorWaybillService = recordErrorWaybillService;
	}

	public void setDataDictionaryValueService(IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	/**
	 * Sets 
	 * 		the 
	 * 		运单service.
	 *
	 * @param waybillManagerService 
	 * 		the 
	 * 		new 运单service
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	/**
	 * Sets 
	 * 		the 
	 * 		签收空运偏线dao.
	 *
	 * @param airAgencySignDao 
	 * 		the
	 * 		 new 签收空运偏线dao
	 */
	public void setAirAgencySignDao(IAirAgencySignDao airAgencySignDao) {
		this.airAgencySignDao = airAgencySignDao;
	}

	/**
	 * Sets 
	 * 		the 
	 * 		结算签收Service.
	 *
	 * @param lineSignService 
	 * 		the 
	 * 		new 结算签收Service
	 */
	public void setLineSignService(ILineSignService lineSignService) {
		this.lineSignService = lineSignService;
	}
	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}

	/**
	 * Sets 
	 * 		the 
	 * 		运单签收结果接口.
	 *
	 * @param waybillSignResultService 
	 * 		the 
	 * 		new 运单签收结果接口
	 */
	public void setWaybillSignResultService(IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * Sets 
	 * 		the 
	 * 		偏线Service.
	 *
	 * @param externalBillService 
	 * 		the 
	 * 		new 偏线Service
	 */
	public void setExternalBillService(IExternalBillService externalBillService) {
		this.externalBillService = externalBillService;
	}
	
	public void setLdpExternalBillService(ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}
	

	
	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	/**
	 * Gets 
	 * 		the
	 * 		 行政区域 Service.
	 *
	 * @return 
	 * 		the
	 * 		 行政区域 Service
	 */
	public IAdministrativeRegionsService getAdministrativeRegionsService() {
		return administrativeRegionsService;
	}
	
	/**
	 * Sets 
	 * 		the 
	 * 		行政区域 Service.
	 *
	 * @param administrativeRegionsService
	 * 		 the 
	 * 		new 行政区域 Service
	 */
	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	
	/**
	 * Gets 
	 * 		the 
	 * 		组织信息 Service.
	 *
	 * @return 
	 * 		the 
	 * 		组织信息 Service
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}
	
	/**
	 * Sets 
	 * 		the 组织信息 Service.
	 *
	 * @param orgAdministrativeInfoService
	 * 	   the new 组织信息 Service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	 * Sets 
	 * 		the 
	 * 		运单完结状态操作Service.
	 *
	 * @param waybillTransactionService 
	 * 		the new 运单完结状态操作Service
	 */
	public void setWaybillTransactionService(IWaybillTransactionService waybillTransactionService) {
		this.waybillTransactionService = waybillTransactionService;
	}
	
	/**
	 * Sets 
	 * 		the 运单状态服务接口.
	 *
	 * @param actualFreightService
	 * 
	 *  the new 运单状态服务接口
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	
	/**
	 * Sets
	 * 		 the 
	 * 		计算&调整走货路径类.
	 *
	 * @param calculateTransportPathService
	 * 		 the 
	 * 		new 计算&调整走货路径类
	 */
	public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	
	/**
	 * Sets 
	 * 		the 
	 * 	外场相关共通接口.
	 *
	 * @param handleQueryOutfieldService 
	 * 		the 
	 * 		new 外场相关共通接口
	 */
	public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	public void setAirQueryModifyPickupbillService(
			IAirQueryModifyPickupbillService airQueryModifyPickupbillService) {
		this.airQueryModifyPickupbillService = airQueryModifyPickupbillService;
	}
	public void setTodoActionService(ITodoActionService todoActionService) {
		this.todoActionService = todoActionService;
	}
	
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	
	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}
   
	/**
	 * 注入包含工单状态接口
	 * @param returnGoodsRequestEntityService
	 */
	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}
	/**
	 * 签收快递代理运单
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-12 上午10:17:07
	 */
	@Override
	@Transactional
	public String addExpressAgentSignResult(WaybillSignResultEntity wayEntity, CurrentInfo currentInfo, LineSignDto dto, Integer oldArriveNotoutGoodsQty) {
		// 判断签收时间是否正确
		if (!DateCompareUtil.isToday(wayEntity.getSignTime())) {
			throw new AirAgencySignException(SignException.SIGNTIME_ISNOT_SYSTEMDATE, new Object[] { new SimpleDateFormat("yyyy-MM-dd").format(new Date()) });// 当前电脑时间有误，请调整日期为{0}
		}			
		
		// 调用接口,根据原单号查询返货信息(受理状态) 判断是否有工单,有工单不允许签收
		ReturnGoodsRequestEntity  workOrderEntity = returnGoodsRequestEntityService.queryReturnGoodsEntityByWayBillNo(dto.getWaybillNo());
		if(null != workOrderEntity && FossConstants.NO.equals(workOrderEntity.getIsHandle())){
			throw new AirAgencySignException("该运单存在未处理工单!");//存在工单且未处理完结
		}
		// 记录日志
		LOGGER.info("提交签收信息开始");
		// 返回信息
		String resultMsg = "";
		// 根据运单号查询运单部分信息为空
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
		// 如果返回结果不为空
		if (waybill != null) {
			dto.setIsReturnNewWaybill(true); //返货新开单需求
			// update by foss-sunyanfei 2015-10-24 
			//SendElectronicInvoiceSystemDto sendElectronicInvoiceSystemDto= this.signOperat(wayEntity, currentInfo, dto, oldArriveNotoutGoodsQty, waybill);// 执行签收操作
			SendElectronicInvoiceSystemDto sendElectronicInvoiceSystemDto= this.signSerialOperatCheck(wayEntity, currentInfo, dto, oldArriveNotoutGoodsQty, waybill);// 执行签收操作
			// update by foss-sunyanfei 2015-10-24
			try {
				signBindWaybillNo(wayEntity,currentInfo);
				//268217
				if(SignConstants.TAIWANHAIYUN.equals(waybill.getCustomerPickupOrgCode())  || 
						SignConstants.TAIWANKONGYUN.equals(waybill.getCustomerPickupOrgCode()) ){
					//快递代理签收--发送短信
					sendExpressMSG(waybill, currentInfo, wayEntity);
				}
			} catch (AirAgencySignException e) {
				throw new AirAgencySignException(e.getErrorCode(),e);
			}
			resultMsg = AirAgencySignException.SIGN_SUCCESS;
			//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
			if(sendElectronicInvoiceSystemDto !=null){
				if(sendElectronicInvoiceSystemDto.getIsSendInvoiceInfo()){
					waybillSignResultService.sendInvoiceInfo(sendElectronicInvoiceSystemDto.getEntity(),sendElectronicInvoiceSystemDto.getActual());
				}
			}
			//如果签收情况为内物短少，保存QMS内物短少差错信息(JOB上报)
				if(wayEntity !=null && 
						ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT.equals(wayEntity.getSignSituation())){
				//保存QMS内物短少差错信息
				this.saveRecordShortErrorInfo(wayEntity,currentInfo);
			}
		} else {// 如果为空
			throw new AirAgencySignException(AirAgencySignException.ENTITY_IS_NOT_EXIST);// 抛出异常
		}
		LOGGER.info("提交签收信息完成");// 记录日志
		return resultMsg;// 返回信息
	}
	
	private void signBindWaybillNo(WaybillSignResultEntity newWayEntity,CurrentInfo currentInfo) {
		LOGGER.info("-------------------签收绑定单号 begin-------------------");//记录日志
		List<WaybillExpressEntity> expressList =waybillExpressService.queryWaybillListByWaybillNo(newWayEntity.getWaybillNo());
		WaybillExpressEntity newWaybillNoEntity = CollectionUtils.isNotEmpty(expressList) ?expressList.get(0) :null;
		//判断新单号
		if(newWaybillNoEntity!=null){
			String oldWaybillNo = newWaybillNoEntity.getOriginalWaybillNo();
			if(StringUtils.isNotEmpty(oldWaybillNo)){
				//判断是否是子母件，如果是子母件，重新查询判断返货原单是否结清；如果不是子母件，走原来逻辑
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("waybillNo", oldWaybillNo);
				params.put("active", FossConstants.YES);
				TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
				//判断当前运单是否子母件
				if(oneDto != null && oneDto.getIsTwoInOne().equals(FossConstants.YES)){
					//判断母件单号是否为空
					if(StringUtils.isEmpty(oneDto.getMainWaybillNo())){
						throw new SignException("子母件获取母件单号失败！");
					}
					//结清子母件并签收，如果未结清，则先结清然后再签收
					rookieService.returnTwoInOneSign(null, currentInfo, oneDto,newWayEntity);
					
				}else{//不是子母件,原逻辑不变
					WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(oldWaybillNo);
					if(waybill!= null){
						WaybillSignResultEntity resultWEntity=new WaybillSignResultEntity();
						resultWEntity.setDeliverymanName(newWayEntity.getDeliverymanName());
						resultWEntity.setIdentifyType(newWayEntity.getIdentifyType());
						resultWEntity.setIdentifyCode(newWayEntity.getIdentifyCode());
						resultWEntity.setSignSituation(newWayEntity.getSignSituation());
						resultWEntity.setSignTime(newWayEntity.getSignTime());
						resultWEntity.setSignNote(newWayEntity.getSignNote());
						resultWEntity.setWaybillNo(oldWaybillNo);
						resultWEntity.setSignGoodsQty(waybill.getGoodsQtyTotal());
						resultWEntity.setReceiveMethod(newWayEntity.getReceiveMethod());
						WaybillSignResultEntity oldresultWEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(resultWEntity);
						if(oldresultWEntity!=null&&SignConstants.SIGN_STATUS_ALL.equals(oldresultWEntity.getSignStatus())){
						}else{
							newWayEntity.setIsPdaSign(FossConstants.NO);
							rookieService.exceuteSignStatusPart(oldresultWEntity, waybill, newWayEntity, null,currentInfo, null,false);
						}
					}
				}
			}
		}else{
			//运单号
			if(StringUtils.isNotEmpty(newWayEntity.getWaybillNo())){
				WaybillExpressEntity waybillExpressEntity = waybillExpressService.queryWaybillByOriginalWaybillNo(newWayEntity.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
				if(waybillExpressEntity!=null){
					throw new SignException("该单号已经返货新开单,请使用新单号进行操作!");//该运单号不存在
				}
			}
		}
		LOGGER.info("-------------------签收绑定单号 end-------------------");//记录日志
	}

	/**
	 * 提供给中转的快递代理签收接口
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-13 下午5:40:40
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String addExpressAgentSignResultForTfr(WaybillSignResultEntity wayEntity) {
		// 返回信息
		String resultMsg = "";
		WaybillDto waybillDto = queryByWaybillNoIsSign(wayEntity.getWaybillNo(),wayEntity.getSerialNo());
		if(null == waybillDto){
			return null;
		}
		// 根据运单号查询运单部分信息
		WaybillEntity waybill = waybillManagerService.queryPartWaybillByNo(wayEntity.getWaybillNo());
		validateSignStatus(wayEntity,waybill);
		// 如果返回结果不为空
		if (waybill != null) {
			EmployeeEntity employee = new EmployeeEntity();
			employee.setEmpCode(wayEntity.getCreatorCode());
			employee.setEmpName(wayEntity.getCreator());
			
			UserEntity user = new UserEntity();
			user.setUserName(wayEntity.getExpressEmpName());
			user.setEmployee(employee);
			
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(wayEntity.getCreateOrgCode());
			// 获取当前用户
			CurrentInfo currentInfo = new CurrentInfo(user, orgInfo);
			
			// 签收确认收入服务
			LineSignDto dto = new LineSignDto();
			// 运输性质
			dto.setProductType(waybill.getProductCode());
			// 运单号
			dto.setWaybillNo(waybill.getWaybillNo());
			// 是否整车运单
			dto.setIsWholeVehicle(waybill.getIsWholeVehicle());
			// 签收部门编号
			dto.setSignOrgCode(wayEntity.getCreateOrgCode());
			
			dto.setOperatorName(currentInfo.getEmpName());
			// 操作人编码
			dto.setOperatorCode(currentInfo.getEmpCode());
			// 签收部门名称
			dto.setSignOrgName(currentInfo.getCurrentDeptName());
			// 签收部门编码
			dto.setSignOrgCode(currentInfo.getCurrentDeptCode());
			//返货新开单需求
			dto.setIsReturnNewWaybill(true);
			// update by foss-sunyanfei 2015-10-26
			//SendElectronicInvoiceSystemDto sendElectronicInvoiceSystemDto = this.signOperat(wayEntity,currentInfo , dto, waybillDto.getOldArriveNotoutGoodsQty(), waybill);// 执行签收操作
			//流水号
			dto.setSerialNo(wayEntity.getSerialNo());
			// 执行签收操作
			SendElectronicInvoiceSystemDto sendElectronicInvoiceSystemDto = this.signSerialOperatCheck(wayEntity,currentInfo , dto, waybillDto.getOldArriveNotoutGoodsQty(), waybill);
			// update by foss-sunyanfei 2015-10-26
			try {
				signBindWaybillNo(wayEntity,currentInfo);
			} catch (AirAgencySignException e) {
				throw new AirAgencySignException(e.getErrorCode(),e);
			}
			resultMsg = AirAgencySignException.SIGN_SUCCESS;
			//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
			if(sendElectronicInvoiceSystemDto !=null){
				if(sendElectronicInvoiceSystemDto.getIsSendInvoiceInfo()){
					waybillSignResultService.sendInvoiceInfo(sendElectronicInvoiceSystemDto.getEntity(),sendElectronicInvoiceSystemDto.getActual());
				}
			}
		} else {// 如果为空
			throw new AirAgencySignException(AirAgencySignException.ENTITY_IS_NOT_EXIST);// 抛出异常
		}
		return resultMsg;// 返回信息
	}
	
	
	/**
	 * 对外部传递过来的签收状态进行校验
	 * @author 026123-foss-lifengteng
	 * @date 2013-9-29 下午2:31:04
	 */
	public void validateSignStatus(WaybillSignResultEntity wayEntity,WaybillEntity waybill){
		//签收件数和总件数不一致
		int signPiece = wayEntity.getSignGoodsQty();
		int signTotalPiece = waybill.getGoodsQtyTotal();
		//正常签收
//		if(wayEntity.getSignStatus() == SignConstants.SIGN_STATUS_ALL ){
//			//件数不一致，签收状态不应该为正常签收
//			if(signPiece != signTotalPiece){
//				throw new AirAgencySignException("签收件数："+signPiece+"\n货物总件数："+signTotalPiece+"\n签收状态不正确，货物签收件数不一致，不应该为正常签收！");
//			}
//			
//			//签收情况
//			if(SignConstants.PARTIAL_SIGN.equals(wayEntity.getSignSituation())){
//				throw new AirAgencySignException("签收情况与签收状态不一致：签收情况为部分签收，签收状态对应也应该为部分签收！");
//			}
//		}
//		//部分签收
//		else if(wayEntity.getSignStatus() == SignConstants.SIGN_STATUS_PARTIAL ){
//			if(signPiece == signTotalPiece){
//				throw new AirAgencySignException("签收件数："+signPiece+"\n货物总件数："+signTotalPiece+"\n签收状态不正确，货物签收件数一致，不应该为部分签收！");
//			}
//		}
		
		//签收情况：部分签收
		if (SignConstants.PARTIAL_SIGN.equals(wayEntity.getSignSituation())) {
			//签收件数大于或等于总件数
			if (signTotalPiece <= signPiece) {
				// 签收情况
				throw new AirAgencySignException("签收情况错误：签收件数大于或等于总件数，不能为部分签收");
				
			}
		}
	}
	
	/**
	 * 提供给中转的快递代理签收接口
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-13 下午5:40:40
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String addExpressAgentSignResultForImport(WaybillSignResultEntity wayEntity) {
		// 返回信息
		String resultMsg = "";
		WaybillDto waybillDto = queryByWaybillNoIsSign(wayEntity.getWaybillNo(),wayEntity.getSerialNo());
		if(null == waybillDto){
			return null;
		}
		// 根据运单号查询运单部分信息
		WaybillEntity waybill = waybillManagerService.queryPartWaybillByNo(wayEntity.getWaybillNo());
		// 如果返回结果不为空
		if (waybill != null) {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 签收确认收入服务
			LineSignDto dto = new LineSignDto();
			// 运输性质
			dto.setProductType(waybill.getProductCode());
			// 运单号
			dto.setWaybillNo(waybill.getWaybillNo());
			// 是否整车运单
			dto.setIsWholeVehicle(waybill.getIsWholeVehicle());
			//返货新开单需求
			dto.setIsReturnNewWaybill(true);
			// update by foss-sunyanfei 2015-10-24
			//SendElectronicInvoiceSystemDto sendElectronicInvoiceSystemDto = this.signOperat(wayEntity,currentInfo , dto, waybillDto.getOldArriveNotoutGoodsQty(), waybill);// 执行签收操作
			SendElectronicInvoiceSystemDto sendElectronicInvoiceSystemDto= this.signSerialOperatCheck(wayEntity, currentInfo, dto, waybillDto.getOldArriveNotoutGoodsQty(), waybill);// 执行签收操作
			// update by foss-sunyanfei 2015-10-24

			try {
				signBindWaybillNo(wayEntity,currentInfo);
			} catch (AirAgencySignException e) {
				throw new AirAgencySignException(e.getErrorCode(),e);
			}
			resultMsg = AirAgencySignException.SIGN_SUCCESS;
			//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
			if(sendElectronicInvoiceSystemDto !=null){
				if(sendElectronicInvoiceSystemDto.getIsSendInvoiceInfo()){
					waybillSignResultService.sendInvoiceInfo(sendElectronicInvoiceSystemDto.getEntity(),sendElectronicInvoiceSystemDto.getActual());
				}
			}
		} else {// 如果为空
			throw new AirAgencySignException(AirAgencySignException.ENTITY_IS_NOT_EXIST);// 抛出异常
																						 // 该运单对应实体不存在
		}
		return resultMsg;// 返回信息
	}

	/**
	 * 导入签收信息名细
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-16 下午4:39:41
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public void importExpressSignedDetail(Workbook book) {
		// excel表格
		Workbook workbook = book;
		// 获得工作薄（Workbook）中工作表（Sheet）的个数
		int sheetNum = workbook.getNumberOfSheets();
		// 循环遍历工作薄
		for (int i = 0; i < sheetNum; i++) {
			// 获得工作薄中的工作表
			Sheet sheet = workbook.getSheetAt(i);
			// 记录错误工作薄的名称
			String sheetName = sheet.getSheetName();
			
			// 记录导入错误的行数
			int index = 1;
			// 记录错误列号
			int col = 0;

			// 定义签收结果信息
			WaybillSignResultEntity resultEntity = null;

			try {
				// 获得工作表的迭代器
				Iterator rows = sheet.rowIterator();
				// 加个计数器 count
				// 读取第一行的标题
				Row firstRow = null;  
				if(null == rows){
					continue;
				}else{
					try{
						firstRow = (Row)rows.next();
					}catch (Exception e) {
						break;
					}
				}
				// 循环遍历工作表中的行
				while (rows.hasNext()) {
					// 行对象
					Row row = (Row) rows.next();
					if(isEmptyRow(row, TOTAL_COLUMN_NUM)){
						break;
					}
					
					resultEntity = new WaybillSignResultEntity();
					//列号
					col=1;
					// 遍历行中的列
					for (Iterator cells = row.cellIterator(); cells.hasNext() && col<=TOTAL_COLUMN_NUM;col++) {
						//列标题
						String title = obtainStringVal(firstRow.getCell(col-1));
						// 单元格对象
						Cell cell = (Cell) cells.next();
						// 获得列值
						String colValue = obtainStringVal(cell);
						// 非空判断
						if (StringUtils.isEmpty(colValue)) {
							if(!CAN_EMPTY_TITLE.equals(title)){
								throw new AirAgencySignException("工作表["+ sheetName +"]的第" + index + "行，第" + col + "列的值为空", "[" + title + "]的值不能为空！");
							}
						}
						switch (cell.getColumnIndex()) {
						case 0:
							//  设置快递代理公司名
							break;
						case 1:
							//  设置快递代理公司编码
							break;
						case 2:
							// 运单号
							resultEntity.setWaybillNo(colValue);
							break;
						case SettlementReportNumber.THREE:
							// 件数
							resultEntity.setSignGoodsQty(Double.valueOf(colValue).intValue());
							break;
						case SettlementReportNumber.FOUR:
							// 签收人（客户）
							resultEntity.setDeliverymanName(colValue);
							break;
						case SettlementReportNumber.FIVE:
							// 签收时间
							SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
							try {
								resultEntity.setSignTime(sdf.parse(colValue));
							} catch (ParseException e) {
								throw new AirAgencySignException("解析[签收时间]的日期时出错："+e.getMessage());
							}
							break;
						case SettlementReportNumber.SIX:
							// 签收情况
							String status = convertSignStatus(colValue);
							if (StringUtils.isEmpty(status)) {
								throw new AirAgencySignException("工作表["+ sheetName +"]的第" + index + "行，第" + col + "列的值错误", "[" + title + "]的值不正确，请确认是否录入错误！");
							} else {
								resultEntity.setSignStatus(status);
							}
							break;
						case SettlementReportNumber.SEVEN:
							// 货物拉回异常描述
							resultEntity.setSignNote("[快递代理签收导入]" + colValue);
							break;
						}
					}
					addExpressAgentSignResultForImport(resultEntity);
					index ++;
				}
			} catch (BusinessException e) {
				e.printStackTrace();
				String message = "";
				String code1 = StringUtil.defaultIfNull(e.getErrorCode());
				code1 = messageBundle.getMessage(code1);
				String code2 = StringUtil.defaultIfNull(e.getMessage());
				code2 = messageBundle.getMessage(code2);
				if(!code1.equals(code2)){
					message = code1+code2;
				}else{
					message = code1;
				}
				throw new AirAgencySignException("工作表["+ sheetName +"]的第" + index + "行数据出现异常情况。失败原因："+message);
			}
		}
	}
	
	/**
	 * 判断是否为空行
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-21 下午3:00:51
	 */
	private boolean isEmptyRow(Row row,int cols){
		if(null == row || cols <= 0){
			return true;
		}else{
			int count = 0;
			//遍历行的单元格
			for (int i = 0; i < cols ; i++) {
				// 单元格对象
				Cell cell = (Cell)row.getCell(i) ;
				String colValue = obtainStringVal(cell);
				if(StringUtils.isEmpty(colValue)){
					count++;
				}
			}
			//空格个数大于等于总的列数，则为空行
			if(count >= cols){
				return true;
			}else{
				return false;
			}
		}
	}

	/**
	 * 根据签收情况来转换对应签收状态
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-19 上午9:05:11
	 */
	private String convertSignStatus(String value) {
		// 签收情况
		String name = StringUtil.defaultIfNull(value).trim();
		// 签收情况对应的词典
		List<DataDictionaryValueEntity> dataList = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.PKP_SIGN_SITUATION);
		// 判空
		if (CollectionUtils.isEmpty(dataList)) {
			return "";
		}

		// 遍历集合
		for (DataDictionaryValueEntity entity : dataList) {
			// 值名称是否与excel中的签收情况列中的值相同
			if (entity.getValueName().equals(name)) {
				return entity.getValueCode();
			}
		}
		return name;
	}

	/**
	 * 单元格取值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-16 下午4:59:14
	 */
	private String obtainStringVal(Cell cell) {
		if(null == cell){
			return "";
		}
		// 列值
		String cellVal = "";
		// 单元格类型
		switch (cell.getCellType()) {
		// 数值型
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				cellVal = DateUtils.convert(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()), DateUtils.DATE_TIME_FORMAT);
				// 纯数字
			} else {
				 // 取得当前Cell的数值
				cellVal = String.valueOf(new BigDecimal(cell.getNumericCellValue()));
			}
			break;
		// 此行表示单元格的内容为string类型
		// 字符串型
		case HSSFCell.CELL_TYPE_STRING:
			cellVal = cell.getRichStringCellValue().toString();
			break;
		// 公式型
		case HSSFCell.CELL_TYPE_FORMULA:
			// 读公式计算值
			cellVal = String.valueOf(cell.getNumericCellValue());
			if (cellVal.equals("NaN")) {
				// 如果获取的数据值为非法值,则转换为获取字符串
				cellVal = cell.getRichStringCellValue().toString();
			}
			break;
		// 布尔
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellVal = " " + cell.getBooleanCellValue();
			break;
		// 此行表示该单元格值为空
		// 空值
		case HSSFCell.CELL_TYPE_BLANK:
			cellVal = "";
			break;
		// 故障
		case HSSFCell.CELL_TYPE_ERROR:
			cellVal = "";
			break;
		default:
			cellVal = cell.getRichStringCellValue().toString();
		}
		return cellVal;
	}
	
	/**
	 * 导入签收信息名细
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-16 下午4:39:41
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<AirAgencyQueryDto> importQueryExpressSignedDetail(Workbook book) {
		Map<String, AirAgencyQueryDto> mapDto = new HashMap<String, AirAgencyQueryDto>(); 
		List<String> waybillNos = new ArrayList<String>();
		// excel表格
		Workbook workbook = book;
		// 获得工作薄（Workbook）中工作表（Sheet）的个数
		int sheetNum = workbook.getNumberOfSheets();
		// 循环遍历工作薄
		for (int i = 0; i < sheetNum; i++) {
			// 获得工作薄中的工作表
			Sheet sheet = workbook.getSheetAt(i);
			// 记录错误工作薄的名称
			String sheetName = sheet.getSheetName();

			// 记录导入错误的行数
			int index = 2;
			// 记录错误列号
			int col = 0;
			
			// 定义签收结果信息
			AirAgencyQueryDto dto = null;
			
			try {
				// 获得工作表的迭代器
				Iterator rows = sheet.rowIterator();
	        	//解決第一张Excel表格为results的问题
				if (sheet.rowIterator().hasNext() == false) {
					continue;
				}
				// 加个计数器 count
				// 读取第一行的标题
				Row firstRow = null;  
				if(null == rows){
					continue;
				}else{
					try{
						firstRow = (Row) rows.next();
					}catch (Exception e) {
						break;
					}
				}
				int rowCount=0;
				// 循环遍历工作表中的行
				while (rows.hasNext()) {
					rowCount++;
					// 行对象
					Row row = (Row) rows.next();
					if(isEmptyRow(row, TOTAL_COLUMN_NUM)){
						break;
					}
					if(rowCount>MAX_ROW_NUMS){
						throw new AirAgencySignException("导入的运单数据量不能超过2000条！");
					}
					dto = new AirAgencyQueryDto();
					String waybillNo = "";
					String serialNo ="";
					//列号
					col=1;
					// 遍历行中的列
					for (Iterator cells = row.cellIterator(); cells.hasNext() && col<=TOTAL_COLUMN_NUM;col++) {
						//列标题
						String title = obtainStringVal(firstRow.getCell(col-1));
						// 单元格对象
						Cell cell = (Cell) cells.next();
						// 获得列值
						String colValue = obtainStringVal(cell);
						// 非空判断
						if (cell.getColumnIndex() > 1 && StringUtils.isEmpty(colValue)) {
							// 快递代理公司不做非空验证
							if(!CAN_EMPTY_TITLE.equals(title)){
								throw new AirAgencySignException("工作表["+ sheetName +"]的第" + index + "行，第" + col + "列的值为空", "[" + title + "]的值不能为空！");
							}
						}
						switch (cell.getColumnIndex()) {
						case 0:
							// 设置快递代理公司名
							dto.setExpAirAgencySignOrgName(colValue);
							break;
						case 1:
							// 设置快递代理公司编码
							dto.setExpAirAgencySignOrgCode(colValue);
							break;
						case 2:
							waybillNo = colValue;
							// 运单号
							dto.setWaybillNo(colValue);
							break;
						case SettlementReportNumber.THREE:
							serialNo = colValue;
							// 流水号
							dto.setSerialNo(colValue);
							break;
						case SettlementReportNumber.FOUR:
							// 件数
							dto.setSignGoodsQty(Double.valueOf(colValue).intValue());
							break;
						case SettlementReportNumber.FIVE:
							// 签收人（客户）
							dto.setDeliverymanName(colValue);
							break;
						case SettlementReportNumber.SIX:
							// 签收情况
							String status = convertSignStatus(colValue);
							if (StringUtils.isEmpty(status)) {
								throw new AirAgencySignException("工作表["+ sheetName +"]的第" + index + "行，第" + col + "列的值错误", "[" + title + "]的值不正确，请确认是否录入错误！");
							} else {
								dto.setSignSituation(status);
							}
							break;
						case SettlementReportNumber.SEVEN:
							// 货物拉回异常描述
							dto.setSignNote("[快递代理签收导入]" + colValue);
							break;
						}
					}
					mapDto.put(waybillNo+serialNo, dto);
					waybillNos.add(waybillNo);
					index ++;
				}
			} catch (BusinessException e) {
				e.printStackTrace();
				String message = "";
				String code1 = StringUtil.defaultIfNull(e.getErrorCode());
				code1 = messageBundle.getMessage(code1);
				String code2 = StringUtil.defaultIfNull(e.getMessage());
				code2 = messageBundle.getMessage(code2);
				if(!code1.equals(code2)){
					message = code1+code2;
				}else{
					message = code1;
				}
				throw new AirAgencySignException("工作表["+ sheetName +"]的第" + index + "行数据出现异常情况。失败原因："+message);
			} catch (Exception e) {
				e.printStackTrace();
				throw new AirAgencySignException("工作表["+ sheetName +"]的第" + index + "行数据出现异常情况。失败原因："+StringUtil.defaultIfNull(e.getMessage()));
			}
		}
		List<AirAgencyQueryDto> returnDtos = queryAirInfobyImportWaybillNo(waybillNos);
		List<AirAgencyQueryDto> returnList = new ArrayList<AirAgencyQueryDto>();
		if (CollectionUtils.isNotEmpty(returnDtos) && mapDto != null) {
			// 按照导入的运单号查询符合条件的记录
			for (AirAgencyQueryDto dto : returnDtos) {
				if(StringUtils.isNotBlank(dto.getWaybillNo()) && StringUtils.isNotBlank(dto.getSerialNo())){
					if (mapDto.containsKey(dto.getWaybillNo()+dto.getSerialNo())) {
						AirAgencyQueryDto importDto = mapDto.get(dto.getWaybillNo()+dto.getSerialNo());
						// 设置快递代理公司名
						dto.setExpAirAgencySignOrgName(importDto.getExpAirAgencySignOrgName());
						// 设置快递代理公司编码
						dto.setExpAirAgencySignOrgCode(importDto.getExpAirAgencySignOrgCode());
						//流水号
						dto.setSerialNo(importDto.getSerialNo());
						// 件数
						dto.setSignGoodsQty(importDto.getSignGoodsQty());
						// 签收人（客户）
						dto.setDeliverymanName(importDto.getDeliverymanName());
						// 签收情况
						dto.setSignSituation(importDto.getSignSituation());
						// 货物拉回异常描述
						dto.setSignNote(importDto.getSignNote());
						
						//判断当前运单是否是一票多件
						WaybillEntity waybill = waybillDao.queryWaybillByNo(dto.getWaybillNo());//根据运单编号查询运单信息
						List<LdpExternalBillDto> ldpExternalBillDtos =  ldpExternalBillService.queryByWaybillNo(dto.getWaybillNo());
						if(waybill != null && waybill.getGoodsQtyTotal() > EXTERNAL_COLUMN_NUM && ldpExternalBillDtos !=null && ldpExternalBillDtos.size() > 1){//是一票多件
							dto.setIsOneInMore(FossConstants.YES);
						}
						
						//根据运单号和敏感词code，判断该运单是否存在敏感词信息
						List<ExpressPushInfoDto> expressPushInfoDtos = agentWaybillTrackService.queryPushExpressListByWaybillNo(dto.getWaybillNo(), REASON_CODE);
						if(null != expressPushInfoDtos && expressPushInfoDtos.size() > 0){
							dto.setReasonCode(expressPushInfoDtos.get(0).getReasonCode());
						}
						
						returnList.add(dto);
					}
				}
			}
			return returnList;
		} else {
			return null;
		}
	}
	
	/**
	 * 根据查询条件 (单号，收货人，收货人电话，收货人手机,运输性质) 查询空运运单
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:23:02
	 * @param entity
	 *            entity.waybillNo 运单号 entity.receiveCustomerName 收货人(收货客户名称)
	 *            entity.receiveCustomerPhone 货客户电话
	 *            entity.receiveCustomerMobilephone 收货人手机 entity.active 运单状态
	 *            entity.settleStatus 结清状态 entity.lastLoadOrgCode
	 *            最终配载部门（判断是否为本部门） entity.externalBillNo 外发单号 entity.productCode
	 *            运输性质 entity.transferExternal 是否中转外发 entity.storageTimeBegin
	 *            入库时间(起) entity.storageTimeEnd 入库时间(止) entity.storageQty 件数
	 *            entity.endStockOrgCode 最后库存code entity.goodsAreaCode 库区
	 *            entity.auditStatus 审核状态 entity.arriveNotoutGoodsQty 到达未出库件数
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService
	 *      #queryAirInfobyParams(com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto)
	 */
	private List<AirAgencyQueryDto> queryAirInfobyImportWaybillNo(List<String> waybillNos) {
		if (CollectionUtils.isEmpty(waybillNos)) {
			return null;
		}
		List<AirAgencyQueryDto> airAgencyQueryDtos = null;
		AirAgencyQueryDto entity = new AirAgencyQueryDto();
		entity.setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());//当前登录部门
		entity.setWaybillNos(waybillNos);
		// 经济快递
//		entity.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
		//添加是否快递字段
		entity.setIsExpress(FossConstants.YES);
		// 有效
		entity.setActive(FossConstants.ACTIVE);
		List<String> waybillStatus = new ArrayList<String>();
		waybillStatus.add(WaybillConstants.OBSOLETE);// 已作废
		waybillStatus.add(WaybillConstants.ABORTED);// 已中止
		entity.setWaybillStatus(waybillStatus);
		/**
		 * BUGKD-1491 快递代理运单签收时，外发单应不要求被审核；即：快递代理外发单不是已审核的状态时，对应的运单是可以签收的，请开发修改
		 */
		// 已作废，只能这个状态的运单才不能进行签收
		entity.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID);
		// 非中转外发
		entity.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
		
		// 非营业部找到它上级所属外场的编码
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(entity.getLastLoadOrgCode(),
				bizTypes);
		if (orgAdministrativeInfoEntity != null) {
			entity.setLastLoadOrgCode(orgAdministrativeInfoEntity.getCode());
		}
		entity.setEndStockOrgCode(entity.getLastLoadOrgCode());// 最终库存部门是最终配载部门
		airAgencyQueryDtos = airAgencySignDao.queryExpressByImportWaybillNo(entity);
		return airAgencyQueryDtos;// 返回信息
	}
	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}
	
	/**
	 * @Description: 批量保存签收落地配运单
	 * @author meiying 159231  
	 * @date 2014-11-25 上午9:34:37
	 * @return String    
	 * @throws
	 */
	@Override
	@Transactional
	public String batchAddExpressSign(List<BatchSignDto> batchSignDtos, CurrentInfo currentInfo) {
		if(CollectionUtils.isNotEmpty(batchSignDtos)){
			// 记录日志
			LOGGER.info("批量提交落地配签收信息开始"+batchSignDtos.toString());
			List<WeixinSendDto> sendDtos=new ArrayList<WeixinSendDto>();
			List<WaybillActualFreightDto> wayActuals=new ArrayList<WaybillActualFreightDto>();
			for (BatchSignDto batchSignDto : batchSignDtos) {
				
				//  调用接口,根据原单号查询返货信息(受理状态) 判断是否有工单,有工单不允许签收
				ReturnGoodsRequestEntity  workOrderEntity = returnGoodsRequestEntityService.queryReturnGoodsEntityByWayBillNo( batchSignDto.getWaybillSignResultEntity().getWaybillNo());
				if(null != workOrderEntity && FossConstants.NO.equals(workOrderEntity.getIsHandle())){
					throw new AirAgencySignException("该运单: "+ batchSignDto.getWaybillSignResultEntity().getWaybillNo()+" 存在未处理工单!");//存在工单且未处理完结
				}
				
				LineSignDto lineSigndto = new LineSignDto();
				// 根据运单号查询运单部分信息为空
				WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(batchSignDto.getWaybillSignResultEntity().getWaybillNo());
				// 如果返回结果不为空
				if (waybill != null) {
					lineSigndto.setWaybillNo(waybill.getWaybillNo());
					lineSigndto.setSerialNo(batchSignDto.getWaybillSignResultEntity().getSerialNo());
					lineSigndto.setExternalWaybillNo(batchSignDto.getWaybillSignResultEntity().getExternalWaybillNo());
					lineSigndto.setIsWholeVehicle(waybill.getIsWholeVehicle());
					lineSigndto.setProductType(waybill.getProductCode());
					ActualFreightEntity actual = actualFreightService.queryByWaybillNo(waybill.getWaybillNo());
					
					//DMANA-9716 add by chenjunying 2015-03-25  判断限制有投诉变更的运单 不让进行正常签收
					if(actual==null){
						throw new AirAgencySignException("实际承运表中无该运单信息");
					}
					//限制 有投诉变更签收结果的运单，签收时再做正常签收
					if(SignConstants.YES.equals(actual.getComplainStatus()) && 
						SignConstants.NORMAL_SIGN.equals(batchSignDto.getWaybillSignResultEntity().getSignSituation())){ //有投诉自动变更，签收不让正常签收
						throw new AirAgencySignException("投诉自动变更异常签收的运单，反签收后不允许再做正常签收");
					}
					
					boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
					// update by foss-sunyanfei 2015-10-26
					//WeixinSendDto sendDto =this.expressSignOperat(batchSignDto.getWaybillSignResultEntity(), currentInfo, lineSigndto, batchSignDto.getOldArriveNotoutGoodsQty(), waybill);// 执行签收操作
					WeixinSendDto sendDto =this.expressSignOperatCheck(batchSignDto.getWaybillSignResultEntity(), currentInfo, lineSigndto, batchSignDto.getOldArriveNotoutGoodsQty(), waybill);// 执行签收操作
					// update by foss-sunyanfei 2015-10-26
					sendDtos.add(sendDto);
					//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
					if(isSendInvoiceInfo){
						WaybillActualFreightDto waDto=new WaybillActualFreightDto();
						waDto.setActualFreightEntity(actual);
						waDto.setWaybillEntity(waybill);
						wayActuals.add(waDto);
					}
					//如果签收情况为内物短少，上报QMS内物短少差错
					WaybillSignResultEntity wayEntity = batchSignDto.getWaybillSignResultEntity();
						if(wayEntity !=null && 
								ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT.equals(wayEntity.getSignSituation())){
						//保存QMS内物短少差错信息
						this.saveRecordShortErrorInfo(wayEntity,currentInfo);
					}
					
				} else {// 如果为空
					throw new AirAgencySignException(AirAgencySignException.ENTITY_IS_NOT_EXIST);// 抛出异常
				}
				try {
					signBindWaybillNo(batchSignDto.getWaybillSignResultEntity(),currentInfo);
					//268217
					if(waybill != null & SignConstants.TAIWANHAIYUN.equals(waybill.getCustomerPickupOrgCode())  || 
							SignConstants.TAIWANKONGYUN.equals(waybill.getCustomerPickupOrgCode()) ){
					//快递代理签收--发送短信
					sendExpressMSG(waybill, currentInfo, batchSignDto.getWaybillSignResultEntity());
					}
				} catch (AirAgencySignException e) {
					throw new AirAgencySignException(e.getErrorCode(),e);
				}
			}
			for (WeixinSendDto sendDto : sendDtos) {
				/**
				 * 新增MANA-771需求，添加微信消息推送的方法
				 * @author Foss-105888-Zhangxingwang
				 * @date 2014年3月10日 14:04:04
				 */
				LOGGER.info("偏线开始处理微信消息,单号为："+sendDto.getWaybillNo());
				ResultDto resultDto = weixinSendService.sysnWeixinInfoForWebSiteUnDirectly(sendDto, WeixinConstants.WEIXIN_SIGN_TYPE);
				if(ResultDto.FAIL.equals(resultDto.getCode())){
					LOGGER.info("偏线微信消息推送失败。错误详情："+resultDto.getMsg());
				}
				LOGGER.info("微信消息处理完毕哦，此处略去一万字");
			}
			if(CollectionUtils.isNotEmpty(wayActuals)&&wayActuals.size()>0){
				for (WaybillActualFreightDto waybillActualFreightDto : wayActuals) {
					waybillSignResultService.sendInvoiceInfo(waybillActualFreightDto.getWaybillEntity(),waybillActualFreightDto.getActualFreightEntity());
				}
			}
			LOGGER.info("批量提交落地配签收信息完成");//记录日志
			return AirAgencySignException.SIGN_SUCCESS;//返回信息
		}else {
			throw new AirAgencySignException(AirAgencySignException.SIGN_FAILED);
		}
		
	}
	/**
	 * @Description: 批量签收用－－批量签收后由于某个运单签收失败导致部分运单签收成功的会回滚，所以重新写该方法
	 * @author meiying 159231  
	 * @date 2014-11-27 上午8:24:48
	 * @return Integer    
	 * @throws
	 */
	public WeixinSendDto expressSignOperat(WaybillSignResultEntity wayEntity,CurrentInfo currentInfo, LineSignDto dto,Integer oldArriveNotoutGoodsQty,WaybillEntity waybill){
		//查询条件
		WaybillSignResultEntity wsignEntity = new WaybillSignResultEntity(dto.getWaybillNo(),FossConstants.YES);
		//判断当前运单是否是第一次签收
		WaybillSignResultEntity entity = waybillSignResultService.queryWaybillSignResultByWaybillNo(wsignEntity);
		if(null != entity){
			if(waybill.getGoodsQtyTotal() <= entity.getSignGoodsQty()){ //控制并发，
				throw new AirAgencySignException("当前运单已签收！");
			}
			if(waybill.getGoodsQtyTotal() < (entity.getSignGoodsQty() + wayEntity.getSignGoodsQty())){
				throw new AirAgencySignException("已签收件数+本次签收件数大于运单开单件数！");
			}
		}else{//开单件数和签收件数比较
			if(waybill.getGoodsQtyTotal() < wayEntity.getSignGoodsQty()){
				throw new AirAgencySignException("运单："+dto.getWaybillNo()+"签收件数 >开单件数");
			}
		}
		//校验未受理的更改单
		//if(waybillRfcService.isExsitsWayBillRfc(dto.getWaybillNo())){
			// 抛出业务异常
		//	throw new AirAgencySignException(RepaymentException.EXIST_WAYBILLRFC);
		//}
		WeixinSendDto sendDto = new WeixinSendDto();
		Integer result = 0;//默认值
		Date modifyTime = new Date();
		if(null != currentInfo){
			// 操作人名称
			dto.setOperatorName(currentInfo.getEmpName());
			// 操作人编码
			dto.setOperatorCode(currentInfo.getEmpCode());
			// 签收部门名称
			dto.setSignOrgName(currentInfo.getCurrentDeptName());
			// 签收部门编码
			dto.setSignOrgCode(currentInfo.getCurrentDeptCode());
		}else{
			// 操作人名称
			dto.setOperatorCode(wayEntity.getCreateUser());
		}
		// 签收时间
		dto.setSignDate(modifyTime);
		dto.setSignType(SettlementConstants.LAND_STOWAGE_SIGN);
		// 不是PDA签收
		dto.setIsPdaSign(FossConstants.NO);
		try {
			LOGGER.info("--调用结算子系统“签收接口”开始传入参数：----"+ReflectionToStringBuilder.toString(dto));//记录日志
			if(entity == null){//第一次签收
				// 调用结算出库接口
				this.confirmTaking(dto, currentInfo);
			}
		} catch (SettlementException se) {//捕获异常
			LOGGER.error("调用结算出库接口抛异常", se);//记录日志
			// 处理异常
			throw new AirAgencySignException("运单号:"+dto.getWaybillNo()+se.getErrorCode(), se);//抛出异常
		}
		// 判断代理网点是否为空
		if(StringUtils.isEmpty(wayEntity.getAgentCode())){
			OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybill.getCustomerPickupOrgCode(),FossConstants.ACTIVE);
			if (companyDto != null) {
				// 代理公司编码
				wayEntity.setAgentCode(StringUtil.defaultIfNull(companyDto.getAgentCompany()));
			}
		}
		wayEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());//签收部门编码
		wayEntity.setCreateOrgName(currentInfo.getCurrentDeptName());//签收部门名称
		wayEntity.setCreator(currentInfo.getEmpName());//操作人
		wayEntity.setCreatorCode(currentInfo.getEmpCode());//操作人编码
		// 生效时间为当前时间
		wayEntity.setCreateTime(modifyTime);
		//签收时间
		wayEntity.setSignTime(modifyTime);
		// 时效时间为空，添加时采用默认值
		wayEntity.setModifyTime(null);
		// 运单号
		wayEntity.setWaybillNo(dto.getWaybillNo());
		wayEntity.setIsPdaSign(FossConstants.NO);// 不是PDA签收
		
		//根据签收情况设置签收状态
    	// 签收状态--部分签收
    	if (wayEntity.getSignSituation() != null && SignConstants.PARTIAL_SIGN.equals(wayEntity.getSignSituation())) {
    		wayEntity.setSignStatus(SignConstants.SIGN_STATUS_PARTIAL);
    	}
    	// 签收状态--全部签收
    	else{
    		if(FossConstants.YES.equals(wayEntity.getIsOneInMore()) && waybill.getGoodsQtyTotal() > (null != entity ? (entity.getSignGoodsQty() + wayEntity.getSignGoodsQty()) : wayEntity.getSignGoodsQty())){
    			//设置为部分签收(一票多件按流水多次签收)
    			wayEntity.setSignStatus(SignConstants.SIGN_STATUS_PARTIAL);
    		}else{
    			//设置为正常签收
        		wayEntity.setSignStatus(SignConstants.SIGN_STATUS_ALL);
    		}
    		waybillSignResultService.updateCrmOrder(waybill.getOrderNo(),currentInfo,wayEntity);//更新订单状态
    		//标识业务完结
    		WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
    		//运单号
    		waybillTransactionEntity.setWaybillNo(dto.getWaybillNo());
    		waybillTransactionService.updateBusinessOver(waybillTransactionEntity);//修改业务完结
    	}
    	//sendDto.setStateType(wayEntity.getSignSituation());
    	//sendDto.setWaybillNo(wayEntity.getWaybillNo());
    	//确认过，正常签收和部分签收只能被签收一次
		sendDto.setCurrentPieces(wayEntity.getSignGoodsQty());
		//一票多件货物外发，可分批多次签收；
    	if(wayEntity.getIsOneInMore() != null && wayEntity.getIsOneInMore().equals(FossConstants.YES)){
    		//sendDto.setProcessedPieces(entity.getSignGoodsQty());
    		if(entity != null){
    			//签收情况
    			wayEntity.setSignSituation(SignConstants.NORMAL_SIGN.equals(entity.getSignSituation()) ? wayEntity.getSignSituation() : SignConstants.UNNORMAL_SIGN);
    			//签收件数
    			wayEntity.setSignGoodsQty(wayEntity.getSignGoodsQty() + entity.getSignGoodsQty());
    		}
    	}
    	sendDto.setStateType(wayEntity.getSignSituation());

		sendDto.setProcessedPieces(wayEntity.getSignGoodsQty());
		//签收时间
		sendDto.setCreateTime(wayEntity.getSignTime()==null ? modifyTime:wayEntity.getSignTime());
		sendDto.setWaybillNo(wayEntity.getWaybillNo());
		//签收人
		sendDto.setSignName(wayEntity.getDeliverymanName());
		if(entity != null && FossConstants.YES.equals(wayEntity.getIsOneInMore())){//不是第一次签收且是一票多件
			// 运单签收结果里作废当前运单号
			waybillSignResultService.invalidWaybillSignResult(entity.getId(), new Date());
		}
		result = waybillSignResultService.addWaybillSignResult(wayEntity);
		if(result >= 0){//如果添加成功
			//菜鸟轨迹 add by 231438
			//封装的轨迹方法 赋值，判断是否符合条件 调用中转的轨迹接口
			sendWaybillTrackService.rookieTrackingsForSign(wayEntity);


			//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，推送轨迹--add by 231438 快递100轨迹推送
			sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
			
			// 修改运单状态里的到达未出库件数
			List<String> waybillStatus = new ArrayList<String>();
			waybillStatus.add(WaybillConstants.OBSOLETE);//已作废
			waybillStatus.add(WaybillConstants.ABORTED);//已中止 
			if(FossConstants.YES.equals(wayEntity.getIsOneInMore())){//是一票多件 
				ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(wayEntity.getWaybillNo());
				if(null != actualFreightEntity){
					ActualFreightDto actualDto = new ActualFreightDto(wayEntity.getWaybillNo(),wayEntity.getSignGoodsQty(),actualFreightEntity.getArriveNotoutGoodsQty());
					actualDto.setWaybillStatus(waybillStatus);
					if(actualFreightService.updateArriveNotoutGoodsQtyByWaybillNo(actualDto)<=0){
						throw new AirAgencySignException(AirAgencySignException.SIGN_FAILED);//抛出异常
					}
				}else{
					throw new AirAgencySignException("运单无实际承运表信息");//抛出异常
				}
			}else{//不是一票多件
				ActualFreightDto actualFreightDto = new ActualFreightDto(wayEntity.getWaybillNo(),wayEntity.getSignGoodsQty(),oldArriveNotoutGoodsQty);
				actualFreightDto.setWaybillStatus(waybillStatus);
				if(actualFreightService.updateArriveNotoutGoodsQtyByWaybillNo(actualFreightDto)<=0){
					throw new AirAgencySignException(AirAgencySignException.SIGN_FAILED);//抛出异常
				}
			}
			LOGGER.info("--运单状态里的到达未出库件数：", wayEntity.getSignGoodsQty());//记录日志
			todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),null);
			//应用监控签收调用
			waybillSignResultService.signCounterByWaybillNo(currentInfo, waybill);
			//如果该运单不是整车运单，执行以下操作(拒签不调用中转的走货线路接口，拒签什么都不做)
			if(FossConstants.NO.equals(dto.getIsWholeVehicle()) && !SignConstants.SIGN_STATUS_REFUSE.equals(wayEntity.getSignStatus())){
				// 调中转的走货路径接口
				try {
					calculateTransportPathService.signIn(dto.getWaybillNo(), TransportPathConstants.TRANSPORTPATH_STATUS_SIGNIN);
				} catch (TfrBusinessException e) {//捕获异常
					LOGGER.error("--调中转的走货路径接口有异常", e);//记录日志
					throw new AirAgencySignException(e.getMessage(), e);//抛出异常
				}
				LOGGER.info("--该 运单不是整车运单，调用中转走货路径接口完成,运单号"+dto.getWaybillNo());//记录日志
			}		
		}else {
			LOGGER.error("添加运单签收结果失败，签收操作不成功");//记录日志
			//签收失败，抛出异常信息
			throw new AirAgencySignException(AirAgencySignException.WAYBILL_SIGN_RESULT_ADD_FAILED);//抛出异常
		}	
		return sendDto;
	}
	
	/**
	 * 根据查询条件 (单号，收货人，收货人电话，收货人手机,运输性质)
	 * 查询快递运单
	 * 一次可以查询多个运单号
	 * @author foss-WeiXing
	 * @date 2012-12-18 下午7:23:02
	 * @param entity 
	 * 		entity.waybillNo 
	 * 			运单号
	 * 		entity.receiveCustomerName
	 * 			收货人(收货客户名称)
	 * 		entity.receiveCustomerPhone
	 * 			货客户电话
	 * 		entity.receiveCustomerMobilephone
	 * 			收货人手机
	 * 		entity.active
	 * 			运单状态
	 * 		entity.settleStatus
	 * 			结清状态
	 * 		entity.lastLoadOrgCode
	 * 			最终配载部门（判断是否为本部门）
	 * 		entity.externalBillNo
	 * 			外发单号
	 * 		entity.productCode
	 * 			运输性质
	 * 		entity.transferExternal
	 * 			是否中转外发
	 * 		entity.storageTimeBegin
	 * 			入库时间(起)
	 * 		entity.storageTimeEnd
	 * 			入库时间(止)
	 * 		entity.storageQty
	 * 			件数
	 * 		entity.endStockOrgCode
	 * 			最后库存code
	 * 		entity.goodsAreaCode
	 * 			库区
	 * 		entity.auditStatus
	 * 			审核状态
	 * 		entity.arriveNotoutGoodsQty
	 * 			到达未出库件数
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService
	 * #queryAirInfobyParams(com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto)
	 */
	@Override
	public List<AirAgencyQueryDto> queryAirInfobyParamsWaybillNos(AirAgencyQueryDto entity){
		// 如果所有查询条件都为空
		if (entity == null) {
			LOGGER.error("查询条件为空，不能进行查询");//记录日志
			throw new AirAgencySignException(AirAgencySignException.QUERY_EMPTY);//抛出异常
		}
		List<AirAgencyQueryDto> airAgencyQueryDtos;
		//获取运单号集合
		String waybillNoTmp =  entity.getWaybillNo();
		if(StringUtil.isNotBlank(entity.getWaybillNo())){
			String[] waybillNoArr = waybillNoTmp.split("\\n");
			if(waybillNoArr !=null && waybillNoArr.length>0){
				List<String> waybillNosList = new ArrayList<String>();
				for(int x=0;x<waybillNoArr.length;x++){
					waybillNosList.add(waybillNoArr[x]);
				}
				entity.setWaybillNos(waybillNosList);
			}
		}
	
		// 如果运单号不为空---收货人,收货人电话,收货人手机,为空
		if (CollectionUtils.isNotEmpty(entity.getWaybillNos())) {
			entity.setReceiveCustomerName(null);//收货人为空
			entity.setReceiveCustomerMobilephone(null);//收货人手机为空
			entity.setReceiveCustomerPhone(null);//收货人电话为空
			entity.setArriveTimeBegin(null);//开单时间起为空
			entity.setArriveTimeEnd(null);//开单时间止为空
			entity.setSuccessionTimingBegin(null);	//外场与外发交接时间(起) 268377 yl
			entity.setSuccessionTimingEnd(null);	//外场与外发交接时间(止) 268377 yl
		} else if (StringUtil.isNotBlank(entity.getReceiveCustomerMobilephone())) {
			// 如果收货人手机不为空,让收货人,收货人电话为空
			entity.setReceiveCustomerName(null);//收货人为空
			entity.setReceiveCustomerPhone(null);//收货人电话为空
		} else if (StringUtil.isNotBlank(entity.getReceiveCustomerPhone())) {
			// 如果收货人电话不为空,让收货人为空
			entity.setReceiveCustomerName(null);
		}
		
		// 运输性质 为空
		if (StringUtil.isBlank(entity.getProductCode())) {
			LOGGER.error("运输性质为空，不能进行查询");//记录错误日志
			throw new AirAgencySignException(AirAgencySignException.PRODUCT_CODE_IS_NOT_NULL);//抛出异常
		}
		//有效
		entity.setActive(FossConstants.ACTIVE);
		List<String> waybillStatus = new ArrayList<String>();
		waybillStatus.add(WaybillConstants.OBSOLETE);//已作废
		waybillStatus.add(WaybillConstants.ABORTED);//已中止 
		entity.setWaybillStatus(waybillStatus);
		// 经济快递
		/**
		 * BUGKD-1491 快递代理运单签收时，外发单应不要求被审核；即：快递代理外发单不是已审核的状态时，对应的运单是可以签收的，请开发修改
		 */
		// 已作废，只能这个状态的运单才不能进行签收
		entity.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID);
		// 非中转外发
		entity.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
		//判断是否查询的全部
		if(StringUtils.equals(entity.getProductCode(), SignConstants.EXPRESS_SIGN_STATUS_ALL)){
			entity.setProductCode("");
		}
		// 非营业部找到它上级所属外场的编码
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(entity.getLastLoadOrgCode(),
				bizTypes);
		if (orgAdministrativeInfoEntity != null) {
			entity.setLastLoadOrgCode(orgAdministrativeInfoEntity.getCode());
		}
		entity.setEndStockOrgCode(entity.getLastLoadOrgCode());// 最终库存部门是最终配载部门
		airAgencyQueryDtos = airAgencySignDao.queryExpressByPartialLineWaybillNos(entity);
		//只有运单号只有一个的时候,当查询出来的结果为空时,才找出原因
		if(entity.getWaybillNos() != null && entity.getWaybillNos().size() == 1){
			//如果输入运单号查询结果为空,则找出查询结果为空的原因如下
			if(StringUtil.isNotBlank(entity.getWaybillNo()) && 0 == airAgencyQueryDtos.size() ){
				ActualFreightEntity actual = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
				if(actual !=null && WaybillConstants.OBSOLETE.equals(actual.getStatus())){// 已作废
					throw new AirAgencySignException(AirAgencySignException.WAYBILL_IS_OBSOLETE);//该运单状态已作废，不能进行签收
				}else if(actual !=null && WaybillConstants.ABORTED.equals(actual.getStatus())){// 已中止
					throw new AirAgencySignException(AirAgencySignException.WAYBILL_IS_ABORTED);//该运单状态已中止，不能进行签收
				}
				//查询运单信息  
				AirAgencyQueryDto dto =  new AirAgencyQueryDto();
				dto.setWaybillNo(entity.getWaybillNo());//运单号
				dto.setActive(FossConstants.ACTIVE);//有效
				AirAgencyDto airagencydto= airAgencySignDao.queryWaybillPartByCondition(dto);
				if(null == airagencydto){//如果查询的运单部分信息为空
					LOGGER.error("该运单号不存在!");//记录日志
					throw new AirAgencySignException(AirAgencySignException.WAYBILLNO_IS_NOT_EXIST);//抛出异常
				}else {
					//如果运单签收结果里已经存在该运单号
					if(StringUtil.isNotBlank(airagencydto.getWaybillNo())){
						LOGGER.error("运单号:"+entity.getWaybillNo()+"已经签收!");//记录日志
						throw new AirAgencySignException(AirAgencySignException.WAYBILL_IS_SIGNED);//抛出异常
					}
					//如果该运单的运输性质与所选的运输性质不一致!
					else if(!airagencydto.getProductCode().equals(entity.getProductCode())&&StringUtils.isNotBlank(entity.getProductCode())){
						LOGGER.error("运单号:"+entity.getWaybillNo()+"的运输性质与所选的运输性质不一致!");//记录日志
						throw new AirAgencySignException(AirAgencySignException.PRODUCT_CODE_IS_INCONSISTENCIES);//抛出异常
					}
					//如果当前登录部门与该运单的最终配载部门不一致
					else if(!airagencydto.getLastLoadOrgCode().equals(entity.getLastLoadOrgCode()) && ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(entity.getProductCode())){
						LOGGER.error("当前登录部门与该运单的最终配载部门不一致!");//记录日志
						throw new AirAgencySignException(AirAgencySignException.LAST_LOAD_ORGCODE_INCONSISTENCIES);//抛出异常
					}else {
							validDtoExtracted(entity);
							
							//最终配载部门和最终库存部门不一致
							String endCode = StringUtil.defaultIfNull(actual.getEndStockOrgCode());
							String loadCode = StringUtil.defaultIfNull(entity.getLastLoadOrgCode());
							if(!endCode.equals(loadCode)){
								throw new AirAgencySignException("最终配载部门["+ loadCode +"]和最终库存部门["+ endCode +"]不一致");// 抛出异常
							}
							
							
							List<InOutStockEntity> list = stockService.queryOutStockInfo(actual.getWaybillNo(), null, loadCode, actual.getCreateTime());
							//该单没有在最终配载部门出库
							if(CollectionUtils.isEmpty(list)){
								throw new AirAgencySignException("未在最终库存部门["+ endCode +"]出库！");// 抛出异常
							}

							//运单库存状态非已出库，系统提示“货物未出库，请确认货物状态
						LOGGER.error("货物未出库，请确认货物状态!");//记录日志
						throw new AirAgencySignException(AirAgencySignException.GOODS_NOT_OUT);//抛出异常
					} 
				}
			}
		}
		//判断是否是一票多件，如果是一票多件，给是否是一票多件字段赋值
		//使用CopyOnWriteArrayList是为了在遍历的同时修改签收一票多件按流水签收的签收件数，ArrayList不支持在遍历的同时修改（ConcurrentModificationException）
		CopyOnWriteArrayList<AirAgencyQueryDto> copyAirAgencyDtos = new CopyOnWriteArrayList<AirAgencyQueryDto>();
		copyAirAgencyDtos.addAll(airAgencyQueryDtos);
		if(null != airAgencyQueryDtos && airAgencyQueryDtos.size() > 0 ){
			for (AirAgencyQueryDto airAgencyQueryDto : copyAirAgencyDtos) {
				//判断当前运单是否是一票多件
				WaybillEntity waybill = waybillDao.queryWaybillByNo(airAgencyQueryDto.getWaybillNo());//根据运单编号查询运单信息
				List<LdpExternalBillDto> ldpExternalBillDtos =  ldpExternalBillService.queryByWaybillNo(airAgencyQueryDto.getWaybillNo());
				if(waybill != null && waybill.getGoodsQtyTotal() > EXTERNAL_COLUMN_NUM && ldpExternalBillDtos !=null && ldpExternalBillDtos.size() > 1){//是一票多件
					airAgencyQueryDto.setIsOneInMore(FossConstants.YES);
				}
				//根据运单号和敏感词code，判断该运单是否存在敏感词信息
				List<ExpressPushInfoDto> expressPushInfoDtos = agentWaybillTrackService.queryPushExpressListByWaybillNo(airAgencyQueryDto.getWaybillNo(), REASON_CODE);
				if(null != expressPushInfoDtos && expressPushInfoDtos.size() > 0){
					airAgencyQueryDto.setReasonCode(expressPushInfoDtos.get(0).getReasonCode());
				}
			}
		}
		
		return airAgencyQueryDtos;//返回信息
	}

	private void validDtoExtracted(AirAgencyQueryDto entity) {
		if (0 >= airAgencySignDao.queryIsExpressExternalByWaybillNo(entity)) {
			LOGGER.error("运单号:" + entity.getWaybillNo() + "未录入外发单!");// 记录日志
			throw new AirAgencySignException(AirAgencySignException.EXTERNAL_IS_NOT_INPUT);// 抛出异常
		} else {
			//外发单审核状态
			List<String> statusList = airAgencySignDao.queryExpressExternalBillStatusListByNo(entity);
			//经济快递
			if (CollectionUtils.isEmpty(statusList)) {
				LOGGER.error("运单号:" + entity.getWaybillNo() + "审核状态为[已作废]!");// 记录日志
				throw new AirAgencySignException("该运单对应的外发单状态为[已作废]");// 抛出异常
			}
		}
	}
	
	/**
	 * 268217
	 * 短信发送
	 */
	@Override
	public boolean sendMess(CurrentInfo currentInfo,String customerSms,WaybillEntity waybill) {
		boolean sendMessResult=false;
		LOGGER.info("短信发送开始!");//记录日志
		try {
			NotificationEntity notificationEntity = new NotificationEntity();
			// 运单号
			notificationEntity.setWaybillNo(waybill.getWaybillNo());
			// 通知类型为短信通知
			notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);	
			// 通知内容  
			notificationEntity.setNoticeContent(customerSms);
			// 操作人
			notificationEntity.setOperator(currentInfo.getEmpName());
			// 操作人编码
			notificationEntity.setOperatorNo(currentInfo.getEmpCode());
			// 操作部门
			notificationEntity.setOperateOrgName(currentInfo.getCurrentDeptName());
			// 操作部门编码
			notificationEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			// 发货联系人
			notificationEntity.setConsignee(waybill.getDeliveryCustomerContact());
			// 手机号
			notificationEntity.setMobile(waybill.getDeliveryCustomerMobilephone());
			// 操作时间
			notificationEntity.setOperateTime(new Date());
			// 模块名称
			notificationEntity.setModuleName(NotifyCustomerConstants.SMS_PKP_SIGN_EXP);
			// 发送短信
			notifyCustomerService.sendMessage(notificationEntity);
			sendMessResult = true;
		} catch (NotifyCustomerException e) {//捕获异常
			// 异常处理
			LOGGER.error("--短信发送失败!" + e.getErrorCode(), e);//记录日志
			sendMessResult =false;
		}
		return sendMessResult ;
	}
	
	/**
	 * 268217
	 * 快递代理签收-发送短信
	 */
	@Override
	public String sendExpressMSG(WaybillEntity waybill,CurrentInfo currentinfo, WaybillSignResultEntity dto) {
		boolean toDeliveryResult= false; //给发货人发送短信返回结果
		//返回值
		String resultMsg = "";
		WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
		if(dto != null){
		BeanUtils.copyProperties(dto,waybillSigndto);//把wayEntity里的内容复制到waybillSigndto里
		if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(waybillSigndto.getSituation())){
			// 无异常
			waybillSigndto.setIsException(SignConstants.IS_EXCEPTION_NO);
		}else {//有异常
			waybillSigndto.setIsException(SignConstants.IS_EXCEPTION_YES);
		}
		//取得提货网点
		OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybill.getCustomerPickupOrgCode(),FossConstants.ACTIVE);
		//重新设置是否读缓存
		SqlUtil.loadCache = true;  
		String arriveCity = "";
		//判断提货网点是否为空对象
		if(null != companyDto){
			if(StringUtils.isNotEmpty(companyDto.getCityName())){
				arriveCity = companyDto.getCityName();
			}
		}
		LOGGER.info("--到达城市：  waybillSignResultDto:"+arriveCity);//记录日志
		//设置到达城市名称
		waybillSigndto.setArriveCity(arriveCity); 
		String customerSms=getSmsContent(waybillSigndto, currentinfo, SignConstants.EXPRESS_WAYBILL_CONSIGNTW);
		if( StringUtils.isNotBlank(waybill.getDeliveryCustomerMobilephone())){
			try {
				toDeliveryResult=sendMess(currentinfo,customerSms,waybill);
			} catch (SignException e) {// 捕获异常
				// 异常处理
				LOGGER.error("--短信发送失败!" + e.getErrorCode(), e);// 记录日志
				toDeliveryResult = false;
			}
		}
		}
		if(toDeliveryResult){
			resultMsg="发货人短信，发送成功!";
			LOGGER.info("--发货人短信，发送成功!");// 记录日志
		}else{
			resultMsg="发货人短信，发送失败!";
			LOGGER.info("--发货人短信，发送失败!");// 记录日志
		}
		return resultMsg;
	}
	
	/**
	 * 268217
	 * 获取短信模板信息
	 * @param 
	 */
	private String getSmsContent(WaybillSignResultDto entity,CurrentInfo currentInfo,String smscode){
		String sms="";//返回短信
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smscode);
		smsParamDto.setMap(getSmsParam(entity));
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (SMSTempleteException e) {//捕获异常
			LOGGER.error("短信内容为空", e);//记录日志
			throw new SignException(SignException.MESS_CONTENT_ISNULL, e);//短信内容为空
		}
		if (StringUtil.isBlank(sms)) {
			LOGGER.error("没有对应的短信模版");//记录日志
			throw new SignException(SignException.NO_SMS_TEMPLATES);//没有对应的短信模版
		}
		return sms;
	}
	
	/**
	 * 268217
	 * 替换短信内容参数
	 * @param 
	 */
	private Map<String, String> getSmsParam(WaybillSignResultDto entity){
		Map<String, String> map = new HashMap<String, String>();
		//map.put("arriveCity", entity.getArriveCity());
		map.put("waybillNo", entity.getWaybillNo());
		return map;
	}
	
	/**
	 * 短信模板service接口
	 */
	private ISMSTempleteService sMSTempleteService;

	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}
	
	private void confirmTaking(LineSignDto dto,CurrentInfo currentInfo){
		Map<String ,Object> params = new HashMap<String ,Object>();
		params.put("waybillNo", dto.getWaybillNo());
		params.put("active", FossConstants.YES);
		//查询是否子母件，以及获取子母件运单列表
		TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
		if(null != oneDto && FossConstants.YES.equals(oneDto.getIsTwoInOne())){
		//判断当前运单是否子母件
			List<WaybillSignResultEntity> list = new ArrayList<WaybillSignResultEntity>();
			for(String signWaybillNo:oneDto.getWaybillNoList()){
				// 传入参数(运单号,当前运单号生效)
				WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(signWaybillNo, FossConstants.ACTIVE);
				// 根据运单号 查询运单签收结果：子母件都只有一件，签收结果表有且只能有一条签收数据(只能是全部签收)
				WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(wayEntity);
				if( null != waybillSign){
					list.add(waybillSign);
				}
			}
			//当前签收件是母件
			if(oneDto.getMainWaybillNo().equals(dto.getWaybillNo())){
				if(oneDto.getWaybillNoList().size() - list.size()  > 1){ 
					//母件不是最后一件签收，不做确认收入
				}else{ //母件签收是最后一件
					//母件确认收入
					//CUBC签收   灰度改造    353654 ---------------------------start
					String vestSystemCode = null;
					try {
		              	List<String> arrayList = new ArrayList<String>();
		              	arrayList.add(dto.getWaybillNo());
		              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".confirmTaking",
		              			SettlementConstants.TYPE_FOSS);
		              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		              	List<VestBatchResult> list1 = response.getVestBatchResult();
		              	vestSystemCode = list1.get(0).getVestSystemCode();	
			  			} catch (Exception e) {
			  				LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
							throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			  			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						lineSignService.confirmTaking(dto, currentInfo);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
							FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
							reqDto.setCurrentInfo(currentInfo);
							reqDto.setLineSignDto(dto);
							CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
							if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
								if(StringUtils.isNotBlank(resultDto1.getMeg())){
									LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
									throw new CUBCSignException(resultDto1.getMeg());	
								}else{
									throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
								}
							}
					}
					//CUBC签收   灰度改造    353654 ---------------------------end	
				}
			}else{//是子件
				//当前子件不是子母件签收的最后一件
				if(oneDto.getWaybillNoList().size() - list.size()  > 1){
					//CUBC签收   灰度改造    353654 ---------------------------start
					String vestSystemCode = null;
					try {
		              	List<String> arrayList = new ArrayList<String>();
		              	arrayList.add(dto.getWaybillNo());
		              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".confirmTaking",
		              			SettlementConstants.TYPE_FOSS);
		              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		              	List<VestBatchResult> list1 = response.getVestBatchResult();
		              	vestSystemCode = list1.get(0).getVestSystemCode();	
			  			} catch (Exception e) {
			  				LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
							throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			  			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						lineSignService.confirmTaking(dto, currentInfo);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
						reqDto.setCurrentInfo(currentInfo);
						reqDto.setLineSignDto(dto);
						CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMeg())){
								LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
								throw new CUBCSignException(resultDto1.getMeg());
							}else{
								throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
							}
						}			
					}
					//CUBC签收   灰度改造    353654 ---------------------------end
				}else{//当前子件是子母件签收的最后一件
					//子件确认收入
					//CUBC签收   灰度改造    353654 ---------------------------start
					String vestSystemCode = null;
					try {
		              	List<String> arrayList = new ArrayList<String>();
		              	arrayList.add(dto.getWaybillNo());
		              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".confirmTaking",
		              			SettlementConstants.TYPE_FOSS);
		              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		              	List<VestBatchResult> list1 = response.getVestBatchResult();
		              	vestSystemCode = list1.get(0).getVestSystemCode();	
			  			} catch (Exception e) {
			  				LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
							throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			  			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						lineSignService.confirmTaking(dto, currentInfo);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
						FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
						reqDto.setCurrentInfo(currentInfo);
						reqDto.setLineSignDto(dto);
						CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMeg())){
								LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
								throw new CUBCSignException(resultDto1.getMeg());
							}else{
								throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
							}
						}		
					}
					//CUBC签收   灰度改造    353654 ---------------------------end		
					LineSignDto newDto = new LineSignDto();
					BeanUtils.copyProperties(dto, newDto);//复制信息给新的newDto
					newDto.setWaybillNo(oneDto.getMainWaybillNo()); //设置单号为母件单号
					//母件确认收入
					//CUBC签收   灰度改造    353654 ---------------------------start
					String vestSystemCode1 = null;
					try {
		              	List<String> arrayList = new ArrayList<String>();
		              	arrayList.add(newDto.getWaybillNo());
		              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".confirmTaking",
		              			SettlementConstants.TYPE_FOSS);
		              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		              	List<VestBatchResult> list1 = response.getVestBatchResult();
		              	vestSystemCode1 = list1.get(0).getVestSystemCode();	
			  			} catch (Exception e) {
			  				LOGGER.info("灰度分流失败,"+"运单号："+newDto.getWaybillNo());
							throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			  			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
						lineSignService.confirmTaking(newDto, currentInfo);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
						FOSSSignOrRevSignRequestDto reqDto1 = new FOSSSignOrRevSignRequestDto();
						reqDto1.setCurrentInfo(currentInfo);
						reqDto1.setLineSignDto(newDto);
						CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto1);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMeg())){
								LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
								throw new CUBCSignException(resultDto1.getMeg());	
							}else{
								throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
							}
						}			
					}
					//CUBC签收   灰度改造    353654 ---------------------------end
				}
			}
		}else{
			//CUBC签收   灰度改造    353654 ---------------------------start
			String vestSystemCode1 = null;
			try {
              	List<String> arrayList = new ArrayList<String>();
              	arrayList.add(dto.getWaybillNo());
              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".confirmTaking",
              			SettlementConstants.TYPE_FOSS);
              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
              	List<VestBatchResult> list1 = response.getVestBatchResult();
              	vestSystemCode1 = list1.get(0).getVestSystemCode();	
	  			} catch (Exception e) {
	  				LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
	  			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
				lineSignService.confirmTaking(dto, currentInfo);
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
//				//查询运单总件数
//				WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
//				//查询签收结果表
//		        WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(dto.getWaybillNo(),FossConstants.ACTIVE);
//		        WaybillSignResultEntity waybillSignResultEntity = waybillSignResultDao.queryWaybillSignResult(wayEntity);
//		        //获取已签收件数+此次签收件数与运单总件数比较判断全部和部分签收
//		        int signCount = (waybillSignResultEntity == null ? 0 : waybillSignResultEntity.getSignGoodsQty()) + dto.getSignGoodsQty();
//		        LOGGER.info("运单号:"+dto.getWaybillNo()+",AirAgencySignService.confirmTaking传递签收件数为:"+ dto.getSignGoodsQty());
//				String signStatus = signCount >= waybill.getGoodsQtyTotal() ? SignConstants.SIGN_STATUS_ALL
//						: SignConstants.SIGN_STATUS_PARTIAL;
//				if(SignConstants.SIGN_STATUS_ALL.equals(signStatus)){
					FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
					reqDto.setCurrentInfo(currentInfo);
					reqDto.setLineSignDto(dto);
					CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
					if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
						if(StringUtils.isNotBlank(resultDto1.getMeg())){
							LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
							throw new CUBCSignException(resultDto1.getMeg());	
						}else{
							throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
						}
					}			
//				}else{
//					LOGGER.info("AirAgencySignService.confirmTaking(3914行)："+dto.getWaybillNo()+",部分签收,不调CUBC签收接口");
//				}
			}
			//CUBC签收   灰度改造    353654 ---------------------------end
		}
	}
	
	//反签收
	private void reverseConfirmTaking(String waybillNo,WaybillEntity waybillEntity,CurrentInfo currentInfo,Date systemDate){
		LineSignDto dto = new LineSignDto();
		// 运单号
		dto.setWaybillNo(waybillNo);
		// 运输性质
		dto.setProductType(waybillEntity.getProductCode());
		// 反签收部门编码
		dto.setSignOrgCode(currentInfo.getCurrentDeptCode());
		// 反签收部门名称
		dto.setSignOrgName(currentInfo.getCurrentDeptName());
		// 汽运偏线
		if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(dto.getProductType())) {
			dto.setSignType(SettlementConstants.PARTIAL_LINE_SIGN);//签收类型为汽运偏线
		} else if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(dto.getProductType())) {
			// 签收类型 -----空运签收
			dto.setSignType(SettlementConstants.AIR_SIGN);
		}else if(waybillExpressService.onlineDetermineIsExpressByProductCode(dto.getProductType(),waybillEntity.getBillTime())){
			// 签收类型 -----快递代理签收
			dto.setSignType(SettlementConstants.LAND_STOWAGE_SIGN);
		}else {
			throw new AirAgencySignException("该运单对应的运输性质不对，不能进行反签收");//抛出异常
		}
		// 业务日期                           
		dto.setSignDate(systemDate);
		//CUBC反签收   灰度改造    353654 ---------------------------start
		String vestSystemCode = null;
        try {
        	ArrayList<String> arrayList = new ArrayList<String>();
        	arrayList.add(dto.getWaybillNo());
        	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
        			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".reverseConfirmTaking",
        			SettlementConstants.TYPE_FOSS);
        	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
        	List<VestBatchResult> list = response.getVestBatchResult();
        	vestSystemCode = list.get(0).getVestSystemCode();		
		} catch (Exception e) {
			LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
			throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
		}
		if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
			lineSignService.reverseConfirmTaking(dto, currentInfo);
		}
		if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
			FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
			reqDto.setCurrentInfo(currentInfo);
			reqDto.setLineSignDto(dto);
			LOGGER.info("调用CUBC的反签收接口" + ReflectionToStringBuilder.toString(dto));//记录日志
			CUBCSignOrRevSignResultDto resultDto1 = cUBCReverseSignService.sendReverseSignReqToCUBC(reqDto);
			if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
				if(StringUtils.isNotBlank(resultDto1.getMeg())){
					LOGGER.error("调用CUBC反签收接口异常信息如下："+resultDto1.getMeg());
					throw new CUBCReverseSignException(resultDto1.getMeg());	
				}else{
					throw new CUBCReverseSignException("调用CUBC反签收接口失败但未获取到异常信息");
				}
			}		
		}
		//CUBC反签收   灰度改造    353654 ---------------------------end
	}
	
	/**
	 * 判断当前运单是否是子母件中第一个做反签收
	 * @author foss-231434 bieyexiong
	 * @date 2015-09-08
	 * @param waybillNo
	 * @param twoInOneDto
	 * @return
	 */
	private boolean isFirst(String waybillNo,TwoInOneWaybillDto twoInOneDto){
		//是子母件并且子件集合不为空
		if(twoInOneDto != null && FossConstants.YES.equals(twoInOneDto.getIsTwoInOne())
				&& CollectionUtils.isNotEmpty(twoInOneDto.getWaybillNoList())){
			// 传入参数
			WaybillSignResultEntity wayEntity = new WaybillSignResultEntity();
			// 根据运单号 查询运单签收结果
			WaybillSignResultEntity waybillSign = null;
			//是否第一个
			boolean isFirst = true;
			//将母单放进子单集合，用于判断所有单号是否
			twoInOneDto.getWaybillNoList().add(twoInOneDto.getMainWaybillNo());
			for(String sonWaybillNo : twoInOneDto.getWaybillNoList()){
				//如果是本单号，则跳过进入下一循环
				if(waybillNo.equals(sonWaybillNo)){
					continue;
				}
				//传入参数(运单号,当前运单号生效)
				wayEntity.setWaybillNo(sonWaybillNo);
				wayEntity.setActive(FossConstants.ACTIVE);
				//根据运单号 查询运单有效签收结果
				waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(wayEntity);
				//没有签收结果信息，即有单号已做反签收，则不是第一个
				if(waybillSign == null){
					isFirst = false;
				}
			}
			return isFirst;
		}
		return false;
	}
	
	/**
	 * 签收入口之前，判断单个运单是否是一票多件外发签收，是添加外发流水签收信息然后调用签收；
	 * 不是走之前的外发签收逻辑
	 * @author foss-sunyanfei
	 * @date 2015-10-24 下午15:21:34
	 * @param wayEntity
	 * @param currentInfo
	 * @param dto
	 * @param oldArriveNotoutGoodsQty
	 * @param waybill
	 * @return
	 */
	private SendElectronicInvoiceSystemDto signSerialOperatCheck(WaybillSignResultEntity wayEntity,CurrentInfo currentInfo, LineSignDto dto,Integer oldArriveNotoutGoodsQty,WaybillEntity waybill){
		//流水签收的签收件数
		Integer serialSignQty = wayEntity.getSignGoodsQty();
		//流水签收的签收情况
		String serialSignSituation = wayEntity.getSignSituation();
		//定义dto
		SendElectronicInvoiceSystemDto sendElectronicInvoiceSystemDto = new SendElectronicInvoiceSystemDto();
		List<LdpExternalBillDto> ldpExternalBillDtos =  ldpExternalBillService.queryByWaybillNo(waybill.getWaybillNo());
		//判断该运单是否是一票多件
		if(waybill.getGoodsQtyTotal() - EXTERNAL_COLUMN_NUM > 0 && ldpExternalBillDtos != null && ldpExternalBillDtos.size() > 1){ //是一票多件
			//是一票多件
			wayEntity.setIsOneInMore(FossConstants.YES);
			// 执行签收操作
			sendElectronicInvoiceSystemDto = this.signOperat(wayEntity, currentInfo, dto, oldArriveNotoutGoodsQty, waybill);
			
			//初始化外发流水签收实体
			SerialSignResultEntity serialSignResultEntity = new SerialSignResultEntity();
			
			serialSignResultEntity.setWaybillNo(dto.getWaybillNo());							//运单号
			serialSignResultEntity.setSerialNo(dto.getSerialNo());								//流水号
			serialSignResultEntity.setActive(FossConstants.YES);								//是否有效
			serialSignResultEntity.setExternalWaybillNo(dto.getExternalWaybillNo());			//外发单号
			serialSignResultEntity.setDeliverymanName(wayEntity.getDeliverymanName());			//签收人
			serialSignResultEntity.setSignGoodsQty(serialSignQty);								//签收件数
			serialSignResultEntity.setSignSituation(serialSignSituation);						//流水号签收情况
			if(null != currentInfo){
				serialSignResultEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());		//签收部门编码
				serialSignResultEntity.setCreateOrgName(currentInfo.getCurrentDeptName());		//签收部门名称
				serialSignResultEntity.setCreatorCode(currentInfo.getEmpCode());				//操作人编码
				serialSignResultEntity.setCreator(currentInfo.getEmpName());					//操作人
			}else{
				serialSignResultEntity.setCreator(wayEntity.getCreateUser());					//操作人
			}
			
			//验证传的值
			this.validateSerialSignResultEntity(serialSignResultEntity);//验证传的值
			
			//同一运单号、流水号、是否有效，在外发流水签收表单中只能有一条数据
			SerialSignResultEntity serialEntity = serialSignResultService.queryByConditions(serialSignResultEntity);
			if(serialEntity == null){
				//生成外发流水签收信息
				serialSignResultService.addSerialSignResultInfo(serialSignResultEntity);
			}else{
				throw new SignException("运单号："+dto.getWaybillNo()+"的"+dto.getSerialNo()+"流水号已签收。");
			}
		}else{ //不是一票多件
			sendElectronicInvoiceSystemDto = this.signOperat(wayEntity, currentInfo, dto, oldArriveNotoutGoodsQty, waybill);// 执行签收操作
		}
		return sendElectronicInvoiceSystemDto;
	}
	
	/**
	 * 签收入口之前，判断多个运单是否是一票多件外发签收，是添加外发流水签收信息然后调用签收；
	 * 不是走之前的外发签收逻辑
	 * @author foss-sunyanfei
	 * @date 2015-10-26 下午15:21:34
	 * @param wayEntity
	 * @param currentInfo
	 * @param dto
	 * @param oldArriveNotoutGoodsQty
	 * @param waybill
	 * @return
	 */
	private WeixinSendDto expressSignOperatCheck(WaybillSignResultEntity wayEntity,CurrentInfo currentInfo, LineSignDto dto,Integer oldArriveNotoutGoodsQty,WaybillEntity waybill){
		//流水签收的签收件数
		Integer serialSignQty = wayEntity.getSignGoodsQty();
		//流水签收的签收情况
		String serialSignSituation = wayEntity.getSignSituation();
		//定义微信推送dto
		WeixinSendDto sendDto = new WeixinSendDto();
		List<LdpExternalBillDto> ldpExternalBillDtos =  ldpExternalBillService.queryByWaybillNo(waybill.getWaybillNo());
		//判断该运单是否是一票多件
		if(waybill.getGoodsQtyTotal() - EXTERNAL_COLUMN_NUM > 0 && ldpExternalBillDtos != null && ldpExternalBillDtos.size() > 1){ //是一票多件
			//是一票多件
			wayEntity.setIsOneInMore(FossConstants.YES);
			// 执行签收操作
			sendDto =this.expressSignOperat(wayEntity, currentInfo, dto, oldArriveNotoutGoodsQty, waybill);
			//初始化外发流水签收实体
			SerialSignResultEntity serialSignResultEntity = new SerialSignResultEntity();
			
			serialSignResultEntity.setWaybillNo(dto.getWaybillNo());							//运单号
			serialSignResultEntity.setSerialNo(dto.getSerialNo());								//流水号
			serialSignResultEntity.setActive(FossConstants.YES);								//是否有效
			serialSignResultEntity.setExternalWaybillNo(dto.getExternalWaybillNo());			//外发单号
			serialSignResultEntity.setDeliverymanName(wayEntity.getDeliverymanName());			//签收人
			serialSignResultEntity.setSignGoodsQty(serialSignQty);								//签收件数
			serialSignResultEntity.setSignSituation(serialSignSituation);						//流水号签收情况
			if(null != currentInfo){
				serialSignResultEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());		//签收部门编码
				serialSignResultEntity.setCreateOrgName(currentInfo.getCurrentDeptName());		//签收部门名称
				serialSignResultEntity.setCreatorCode(currentInfo.getEmpCode());				//操作人编码
				serialSignResultEntity.setCreator(currentInfo.getEmpName());					//操作人
			}else{
				serialSignResultEntity.setCreator(wayEntity.getCreateUser());					//操作人
			}
			
			//验证传的值
			this.validateSerialSignResultEntity(serialSignResultEntity);//验证传的值
			
			//同一运单号、流水号、是否有效，在外发流水签收表单中只能有一条数据
			SerialSignResultEntity serialEntity = serialSignResultService.queryByConditions(serialSignResultEntity);
			if(serialEntity == null){
				//生成外发流水签收信息
				serialSignResultService.addSerialSignResultInfo(serialSignResultEntity);
			}else{
				throw new SignException("运单号："+dto.getWaybillNo()+"的"+dto.getSerialNo()+"流水号已签收。");
			}
		}else{ //不是一票多件
			// 执行签收操作
			sendDto =this.expressSignOperat(wayEntity, currentInfo, dto, oldArriveNotoutGoodsQty, waybill);
		}
		
		return sendDto;
	}
	
	/**
	 * 验证添加外发流水签收结果时的参数
	 * @author foss-sunyanfei
	 * @param serialSignResultEntity
	 */
	private void validateSerialSignResultEntity(SerialSignResultEntity serialSignResultEntity){
		if (null == serialSignResultEntity) {
			LOGGER.error("接口传入的数据为空");//记录日志
			throw new AirAgencySignException("接口传入的数据为空");
		}else if(StringUtils.isBlank(serialSignResultEntity.getWaybillNo())) {//运单号为空
			LOGGER.error("运单号为空，不能添加外发流水签收结果");//记录日志
			throw new AirAgencySignException("运单号为空，不能添加外发流水签收结果");//运单号为空
		}else if(StringUtils.isBlank(serialSignResultEntity.getSerialNo())) {//流水号为空
			LOGGER.error("流水号为空，不能添加外发流水签收结果");//记录日志
			throw new AirAgencySignException("流水号为空，不能添加外发流水签收结果");//流水号为空
		}else if(StringUtils.isBlank(serialSignResultEntity.getSignSituation())) {//流水号签收情况为空
			LOGGER.error("流水号签收情况为空，不能添加外发流水签收结果");//记录日志
			throw new AirAgencySignException("流水号签收情况为空，不能添加外发流水签收结果");//流水号签收情况为空
		}else if(serialSignResultEntity.getSignGoodsQty() == null || serialSignResultEntity.getSignGoodsQty().intValue() == 0) {//签收件数为空
			LOGGER.error("签收件数为空，不能添加外发流水签收结果");//记录日志
			throw new AirAgencySignException("签收件数为空，不能添加外发流水签收结果");//签收件数为空
		}else if(StringUtils.isBlank(serialSignResultEntity.getActive())) {//是否有效为空
			LOGGER.error("是否有效为空，不能添加外发流水签收结果");//记录日志
			throw new AirAgencySignException("是否有效为空，不能添加外发流水签收结果");//是否有效为空
		}
	}
}