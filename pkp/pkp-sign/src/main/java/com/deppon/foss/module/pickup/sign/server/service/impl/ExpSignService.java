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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/SignService.java
 * 
 * FILE NAME        	: SignService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 *****************************************************************************
 *	修订记录 
	日期 	修订内容 	修订人员 	版本号 
	2012-07-03 
		新增	王辉	V0.1
	2012-7-19
		根据ITA建议修改：
	
	1.	
		用例描述
	2.
		反结清操作修改为“拒收”	王辉	V0.6
	 2012-07-24	业务评审完毕，升至0.9	王辉	V0.9
	 2012-10-13
	根据RC-567修订
	王辉
	 
	2012-10-29	
	根据RC-1110修改	王辉	
	2012-11-19
		根据ISSUE-653修改	王辉	
	
	1.	
		SUC-789-签收出库
	1.1	
		相关业务用例
	BUC_FOSS_5.50.30_530 验货签收
	1.2	
		用例描述
		客户自提结清货款后，
		无PDA扫描出库时，
		货物管理员在系统中做签收出库操作。
	1.3	
		用例条件
	条件类型	描述	引用系统用例
	前置条件
		1.
			运单已结清货款
		2.
			到达联状态为“派送中”	
	后置条件		
	1.4	
		操作用户角色
	操作用户	描述
	 货物管理员	签收出库
	1.5
		界面要求
	1.5.1
		表现方式
	Web页面 
	1.5.2
		界面原型	 
	 
	图1
	 
	图2
	1.5.3
		界面描述
	图1界面标题：签收出库
	签收出库界面分为2个部分，包括查询条件、待处理。	
	1.	
		查询条件：
		a)
			运单号
		b)
			到达联编号
		c)
			收货人
		d)
			收货人手机
	
		e)
			收货人电话
		f)
			结清时间： 结清货款的时间
		g)
			所有未提货物：
			用户勾选所有未提货物，
			运单号、
			到达联编号、
			收货人、
			收货人电话不可编辑
	2.	
		待处理： 
		a)
			单号
		b)
			到达联编号
		c)
			到达联件数
		d)
			库存件数
		e)
			提货人
		f)
			证件号码
	
	
	页面初始化时，系统自动载入信息： 
	结清时间默认为当前系统时间0:00至24:00
	
	用户输入查询条件后，点击“查询”按钮，
	待处理列表中加载符合条件的到达联，点击“ ”按钮，
	列出库存中所有货物流水号，用户根据实际情况勾选需要签收出库的件并录入签收情况、
	签收件数、签收时间，点击“签收”按钮，
	系统调用中转接口出库货物，调用财务接口完成财务签收并发送短信给发货人、收货人，在线通知发货部门。	
	
	1.6	
		操作步骤
	序号	基本步骤	相关数据	补充步骤
	1	
		用户输入查询条件，点击“查询”按钮	查询条件	
		1．
			待处理信息自动加载符合条件的到达联信息，查询规则参考SR1
		2．
			参考SR2
	2	
		用户点击待处理信息中的一条记录“ ”	待处理信息	系统弹出图2界面，参考SR3，SR9
	3	
		用户录入签收情况、签收件数、签收备注、签收时间，勾选需要签收出库的件，点击“签收”按钮	签收信息
			1.
				签收时间限制，参考SR5
		2.
			签收情况及件数规则，参考SR7,SR8
		3.	
			系统提示“是否确定签收出库？”
	4	
		用户点击“确定”
			1.
				系统调用中转接口（SUC-238）出库货物
			2.
				系统调用财务接口完成财务出库，参考SR4
			3.
				系统发送短信至发货人、收货人，通知内容包括：
				货物单号、
				此货签收时间、
				签收件数、
				签收人、
				是否有异常
			4.
				系统发送在线通知给发货部门，通知内容包括：
				货物单号、
				此货签收时间、
				签收件数、
				签收人、
				是否有异常
			5.	
				系统提示“签收出库成功！”
			6.
				系统更新运单签收状态，参考SR10
			7.
				列表移除此行记录
	
	扩展事件写非典型或异常操作过程
	序号	扩展事件	相关数据	备注
	3a
		签收件数不大于0，则系统弹窗提示“签收件数必须大于0，请重新录入！”	
			用户点击确定，终止当前操作
	3b	
		签收时间为空，则系统弹窗提示“签收时间不能为空，请录入签收时间！”	
			用户点击确定，终止当前操作
	4a	
		签收情况为“正常签收”，签收件中的签收情况有“异常签收”，系统提示异常信息	
			系统提示“到达联签收情况为正常签收，货物件签收情况应均为正常签收，请确认！”
	4b	
		用户勾选的货物件总数大于到达联签收件数，系统提示异常信息		
		系统提示“签收出库件数不能大于到达联签收件数，请确认！”	
	
	1.7	
		业务规则
	序号	描述
	SR1	
		查询规则：
	1.	
		运单已结清货款
	2.	
		到达联状态为“派送中”
	3.	
		到达部门为当前部门
	SR2	
		结清时间默认为当前系统时间0:00至24:00
	SR3	
		显示的流水号为运单库存中的流水号
	SR4
		专线正常签收接口：SUC-597
		偏线空运正常签收接口：SUC-651
		异常丢货、弃货、违禁品签收接口：SUC-159
	SR5	
		签收时间默认为当前系统时间，营业员可修改时分秒，不可修改日期		
	SR7	
	1.
		签收情况选择为正常签收、异常-破损、异常-潮湿、异常-污染、异常-其他时，签收件数显示为到达联件数且不可编辑
	2.	
		签收情况选择为部分签收时，签收件数显示为到达联件数且可编辑
	SR8	
		部分签收录入签收件数校验规则：
	1.	
		签收件数不能大于等于到达联件数
	SR9	
		签收情况默认为正常签收，签收件数默认为到达联件数
	SR10
		若签收最后一个到达联，系统更新运单签收状态
	1.
		到达联签收状态全部为“正常签收”，运单签收状态为“正常签收”
	2.
		到达联签收状态中存在非“正常签收”，运单签收状态为“异常签收”
	SR11
		1.	
		查询条件中单号、到达联编号、收货人、收货人手机、收货人电话具有排他性，优先级按单号、到达联编号、收货人、收货人手机、收货人电话
		2.
			“所有未提货物”具有排他性且优先级最高，但受时间跨度限制
	SR12
		1.
			当用单号查询时，先将异步调用的所有消息全部调用，再判断是否已结清，
		a)
			若未结清货款，则不可查询出对应的到达联信息。
		b)
			若已结清货款，则可查询出对应的到达联信息进行签收
	1.8	
		数据元素
	1.8.1
		查询条件
	字段名称 	说明 	输入限制	长度	是否必填	备注
	运单号		文本	10	否	
	到达联编号		文本	10	否	
	收货人		文本	20	否	
	收货人手机		文本	20	否	
	收货人电话		文本	20	否	
	结清时间		日期选择	6	是	
	
	1.8.2
		待处理信息
	字段名称 	说明 	输入限制	长度	是否必填	备注
	单号		文本			
	到达联编号		文本			
	到达联件数		文本			
	签收件数		文本			
	提货人		文本			
	证件号码		文本			
	1.8.3	
		签收信息
	字段名称 	说明 	输入限制	长度	是否必填	备注
	签收情况		下拉框		是	数据字典，
									包括正常签收、
									异常-破损、
									异常-潮湿、
									异常-污染、
									异常-其他、
									部分签收，
									默认是正常签收
	签收件数		文本（仅数字）		是	
	签收备注		文本		是	
	签收时间		日期选择		是	
	
	1.9	
		非功能性需求
	使用量	每天签收80W，自提占40%，80W*40%=32W
	2012年全网估计用户数	10000用户
	响应要求（如果与全系统要求 不一致的话）
		3s内响应
	使用时间段	
		全天
	高峰使用时间段
		09：00-18：00	
	1.10	
		接口描述：
	接口名称 	对方系统（外部系统或内部其他模块）	接口描述
	同步官网接口	官网	同步信息至官网
	短信接口	短信平台	发送短信给发货人、收货人
	在线通知接口	Foss-综合管理子系统	发送通知给发货部门
	签收接口	Foss-结算子系统	结算的签收接口
	出库接口	Foss-中转子系统	中转货物出库接口
 **/
package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IExpSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRecordErrorWaybillService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRookieService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignStockJobService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.BtachExecPramsDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.CzmSignDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.CzmSignListDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.util.DateCompareUtil;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICUBCSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCSignOrRevSignResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FOSSSignOrRevSignRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCSignException;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeByHandService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 签收出库服务
 * @author 
 *		foss-meiying
 * @date 
 *      2012-10-17 上午10:37:11
 * @since
 * @version
 */
public class ExpSignService implements IExpSignService {
	
	private IWaybillSignResultDao waybillSignResultDao;
	public void setWaybillSignResultDao(IWaybillSignResultDao waybillSignResultDao) {
		this.waybillSignResultDao = waybillSignResultDao;
	}
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.ExpSignService";
	
	/**
	 * add by 353654  注入CUBC签收服务
	 */
	private ICUBCSignService cUBCSignService;
	
	public void setcUBCSignService(ICUBCSignService cUBCSignService) {
		this.cUBCSignService = cUBCSignService;
	}
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpSignService.class);
	/**
	 *  签收出库Dao
	 */
	private ISignDao signDao;

	/**
	 *  到达联service
	 */
	private IArriveSheetManngerService arriveSheetManngerService;

	/**
	 *  签收明细service
	 */
	private ISignDetailService signDetailService;

	/**
	 *  运单签收结果service
	 */
	private IWaybillSignResultService waybillSignResultService;

	/**
	 *  结算签收Service
	 */
	private ILineSignService lineSignService;

	/**
	 * 结清货款Service
	 */
	private IRepaymentService repaymentService;
	/**
	 *  中转出库接口
	 */
	private IStockService stockService;
	
	/**
	 *  运单状态服务接口
	 */
	private IActualFreightService actualFreightService;
	
	/**
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 签收反签收同步改异步接口
	 */
	private ISignStockJobService signStockJobService;
	/**
	 * 签收变更结果接口
	 */
	private ISignChangeService signChangeService;
	/**张新
	 * 打印代理面单service
	 */
	private IPrintAgentWaybillService printAgentWaybillService;
	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 代办事项服务类
	 */
	private ITodoActionService todoActionService;
	/** 配载单模块service类 */
	private IVehicleAssembleBillService vehicleAssembleBillService;
	private IDepartureService departureService;
	/**
	 * 运单快递相关服务类接口
	 */
	private IWaybillExpressService waybillExpressService;
	/**
	 * 菜鸟相关服务类接口
	 */
	private IRookieService rookieService;
	/**
	 * 记录异常运单 上报QMS的Service
	 */
	private IRecordErrorWaybillService recordErrorWaybillService;
	
	/**
	 * 轨迹推送接口 
	 * 轨迹推送接口（快递100,、菜鸟）
	 */
	private ISendWaybillTrackService sendWaybillTrackService; 
	/**
	 * 派送单明细DAO接口
	 */
	private IDeliverbillDetailDao deliverbillDetailDao;
	/**
	 * 货物轨迹推送Service
	 */
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;
	/**
	 * 员工信息服务
	 */
	private IEmployeeService  employeeService;
	/**
	 * 综合服务接口 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 *结清货款Dao用于子母件，自提签收时，结清 
	 */
	private IRepaymentDao repaymentDao;
	
	 /**
     * 返回工单送接口（判断工单处理状态）
	  */
	private IReturnGoodsRequestEntityService returnGoodsRequestEntityService;


	/**
	 * 注入包含工单状态接口
	 * @param returnGoodsRequestEntityService
	 */
	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}

	
	//待人工补码记录service
	private IAutoAddCodeByHandService autoAddCodeByHandService;

	public void setAutoAddCodeByHandService(
			IAutoAddCodeByHandService autoAddCodeByHandService) {
		this.autoAddCodeByHandService = autoAddCodeByHandService;
	}
	private IConfigurationParamsService configurationParamsService; //数据字典service
	
	/**
	 * 轨迹推送接口 注入
	 */
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	/**
	 * 结清Dao注入 
	 */
	public void setRepaymentDao(IRepaymentDao repaymentDao) {
		this.repaymentDao = repaymentDao;
	}
	/**
	 * 根据条件分页查询到达联信息
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:07:27
	 * @param dto
	 * 		dto.waybillNo 
	 * 			运单号
	 * 		dto.receiveCustomerName
	 * 			收货人(收货客户名称)
	 * 		dto.receiveCustomerPhone
	 * 			货客户电话
	 * 		dto.receiveCustomerMobilephone
	 * 			收货人手机
	 * 		dto.active
	 * 			运单状态
	 * 		dto.settleTimeStart
	 * 			结清时间起
	 * 		dto.settleTimeEnd
	 * 			 结清时间止
	 * 		dto.settleStatus
	 * 			结清状态
	 * 		dto.lastLoadOrgCode
	 * 			最终配载部门（判断是否为本部门）
	 * 		dto.isWholeVehicle
	 * 			是否整车运单
	 * 		dto.identifyType
	 * 			证件类型
	 * 		dto.productCode
	 * 			运输性质
	 * 		dto.deliverymanName
	 * 			提货人名称
	 * 		dto.identifyCode
	 * 			 证件号码
	 * 		dto.destroyed
	 * 			是否作废
	 * 		dto.arriveSheetGoodsQty
	 * 			到达联件数
	 * 		dto.endStockOrgCode
	 * 			最后库存code
	 * 		dto.goodsAreaCode
	 * 			库区
	 * 		dto.orderNo
	 * 			订单号
	 * @param start
	 * 		第几页
	 * @param limit
	 * 		每页显示多少条
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignService
	 * #queryArriveSheetInfoByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto,
	 *      int, int)
	 */
	@Override
	public List<SignArriveSheetDto> queryArriveSheetInfoByParamsExp(SignDto dto,
			int start, int limit) {
		return arriveSheetManngerService.queryArriveSheetInfoByParamsExp(dto,start, limit);
	}
	/**
	 * 签收出库---根据条件分页查询到达联总数
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:08:58
	 * @param dto
	 * 			dto.waybillNo 
	 * 			运单号
	 * 		dto.receiveCustomerName
	 * 			收货人(收货客户名称)
	 * 		dto.receiveCustomerPhone
	 * 			货客户电话
	 * 		dto.receiveCustomerMobilephone
	 * 			收货人手机
	 * 		dto.active
	 * 			运单状态
	 * 		dto.settleTimeStart
	 * 			结清时间起
	 * 		dto.settleTimeEnd
	 * 			 结清时间止
	 * 		dto.settleStatus
	 * 			结清状态
	 * 		dto.lastLoadOrgCode
	 * 			最终配载部门（判断是否为本部门）
	 * 		dto.isWholeVehicle
	 * 			是否整车运单
	 * 		dto.identifyType
	 * 			证件类型
	 * 		dto.productCode
	 * 			运输性质
	 * 		dto.deliverymanName
	 * 			提货人名称
	 * 		dto.identifyCode
	 * 			 证件号码
	 * 		dto.destroyed
	 * 			是否作废
	 * 		dto.arriveSheetGoodsQty
	 * 			到达联件数
	 * 		dto.endStockOrgCode
	 * 			最后库存code
	 * 		dto.goodsAreaCode
	 * 			库区
	 * 		dto.orderNo
	 * 			订单号
	 * @param currentInfo 当前登录人信息
	 * 			currentInfo.operate 
	 * 				操作人
	 *          currentInfo.operatorCode 
	 *          	操作人编码
	 *          currentInfo.operateOrgName 
	 *          	操作部门名称
	 *          currentInfo.operateOrgCode 
	 *          	操作部门编码
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignService
	 * #queryArriveSheetInfoCountByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	public Long queryArriveSheetInfoCountByParams(SignDto dto){
		Long count = Long.valueOf(SignConstants.ZERO);//默认值 为0
		try {
			count = arriveSheetManngerService.queryArriveSheetInfoCountByParamsExp(dto);
			return count; //返回查询总数量
		} catch (RepaymentException e) {//捕获异常
			//记录日志
			LOGGER.error(e.getErrorCode(),e);
			// 异常处理
			throw new SignException(e.getErrorCode(),e);
		}
	}
	
	public ArriveSheetVo queryArriveSheetByParams(SignDto dto,int start,int limet){
		ArriveSheetVo arriveSheetVo = new ArriveSheetVo();
		arriveSheetVo.setTotalCount(Long.valueOf(SignConstants.ZERO));//默认值 为0
		this.initSignQuery(dto);//加载传入的信息
		// 如果运单号不为空
		if (StringUtil.isNotBlank(dto.getWaybillNo())) {
			RepaymentEntity repayment = new RepaymentEntity();
			repayment.setWaybillNo(dto.getWaybillNo());
			// 调用结清货款接口判断当前运单是否已结清
			boolean result = repaymentService.paymentOperate(repayment);
			if (!result) {// 如果未结清
				LOGGER.error("运单号:"+dto.getWaybillNo()+ "未结清");//记录日志
				arriveSheetVo.setSignArriveSheetDtoList(null);
				return arriveSheetVo;
			}
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
			if(waybillEntity != null && FossConstants.YES.equals(waybillEntity.getIsWholeVehicle()) && dto.getLastLoadOrgCode().equals(waybillEntity.getReceiveOrgCode())
					&& FossConstants.NO.equals(waybillEntity.getIsPassOwnDepartment()))//判断如果当前部门是整车，当前登录部门是收货部门，且不经过到达部门
			{
				//中转接口校验通过
				if(vehicleAssembleBillService.queryVehicleAssemblyBillByWaybillNo(dto.getWaybillNo()))
				{
					List<SignArriveSheetDto> dtos= new ArrayList<SignArriveSheetDto>();
					dtos=signDao.queryArrivesheetListByWaybillWVH(dto);
					if(CollectionUtils.isNotEmpty(dtos)){
						for (SignArriveSheetDto signArriveSheetDto : dtos) {
							signArriveSheetDto.setIsCurrentOrgCodeReceiveOrgCodeWVH(FossConstants.YES);
						}
						arriveSheetVo.setSignArriveSheetDtoList(dtos);
						arriveSheetVo.setTotalCount(Long.valueOf(SignConstants.ONE));
						
					}else {
						arriveSheetVo.setSignArriveSheetDtoList(null);
					}
					// 查询待处理列表
					return arriveSheetVo;
				}else
				{
					// 抛出业务异常
					throw new SignException(SignException.VEHICLEASSEMBLEBILL_INVALID);
				}
			}
		}
		arriveSheetVo.setTotalCount(this.queryArriveSheetInfoCountByParams(dto));
		// 如果存在信息
		if (arriveSheetVo.getTotalCount() != null && arriveSheetVo.getTotalCount() >SignConstants.ZERO ) {
			arriveSheetVo.setSignArriveSheetDtoList(this.queryArriveSheetInfoByParamsExp(dto,start, limet));
		}else {
			arriveSheetVo.setSignArriveSheetDtoList(null);
		}
		return arriveSheetVo;
	}
	
	/**
	 * 设置签收出库--
	 * 		查询条件 前置条件(到达联状态为生成, 到达联active有效,不按运单号查时(运单必须已结清货款))
	 *      查询条件中单号、到达联编号、收货人、收货人手机、收货人电话具有排他性 
	 *      优先级按单号、到达联编号、收货人、收货人手机、收货人电话
	 * @author foss-meiying
	 * @date 2012-11-27 下午4:53:34
	 * @param dto
	 * 		dto.waybillNo 
	 * 			运单号
	 * 		dto.receiveCustomerName
	 * 			收货人(收货客户名称)
	 * 		dto.receiveCustomerPhone
	 * 			货客户电话
	 * 		dto.receiveCustomerMobilephone
	 * 			收货人手机
	 * 		dto.active
	 * 			运单状态
	 * 		dto.settleTimeStart
	 * 			结清时间起
	 * 		dto.settleTimeEnd
	 * 			 结清时间止
	 * 		dto.settleStatus
	 * 			结清状态
	 * 		dto.lastLoadOrgCode
	 * 			最终配载部门（判断是否为本部门）
	 * 		dto.isWholeVehicle
	 * 			是否整车运单
	 * 		dto.identifyType
	 * 			证件类型
	 * 		dto.productCode
	 * 			运输性质
	 * 		dto.deliverymanName
	 * 			提货人名称
	 * 		dto.identifyCode
	 * 			 证件号码
	 * 		dto.destroyed
	 * 			是否作废
	 * 		dto.arriveSheetGoodsQty
	 * 			到达联件数
	 * 		dto.endStockOrgCode
	 * 			最后库存code
	 * 		dto.goodsAreaCode
	 * 			库区
	 * 		dto.orderNo
	 * 			订单号
	 * @param currOrgCode
	 * 			当前登录部门编码
	 * @see
	 */
	private void initSignQuery(SignDto dto) {
		// 到达联状态为生成
		dto.setArriveSheetstatus(new String[] {ArriveSheetConstants.STATUS_GENERATE});  
		// 运输性质 （偏线和空运）
		List<String> productCodeList = new ArrayList<String>();
		//只能查询出‘标准快递’和‘3.60特惠件’运单号,商务专递
		productCodeList.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
		productCodeList.add(ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE);
		productCodeList.add(ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);
		productCodeList.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT);
		
		dto.setProductCodeList(productCodeList);
		// 有效
		dto.setActive(FossConstants.ACTIVE);
		//未作废
		dto.setDestroyed(FossConstants.NO);
		// 运单已结清货款 状态为Y时已结清货款
		dto.setSettleStatus(FossConstants.YES);
		// ibm-meiying 2013-2-27 上午10:11:27添加库存外场、库区默认查询条件
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(dto.getLastLoadOrgCode());
		if (CollectionUtils.isNotEmpty(list)) {
			List<String> ld=new ArrayList<String>();
			ld.add(list.get(1));
			ld.add(list.get(2));
			dto.setEndStockOrgCode(list.get(0)); //最终库存部门编码
			dto.setGoodsAreaCodes(ld);// 获取库区
		}
		// 如果运单号不为空:其他条件都设为空
		if (StringUtil.isNotBlank(dto.getWaybillNo())) {
			dto.setArrivesheetNo(null); //到达联编号为空
			dto.setReceiveCustomerName(null);//收货人为空
			dto.setReceiveCustomerMobilephone(null);//收货人手机为空
			dto.setReceiveCustomerPhone(null);//收货人电话为空
			dto.setSettleTimeStart(null);//结清时间起为空
			dto.setSettleTimeEnd(null);// 结清时间止为空
		} else if (StringUtil.isNotBlank(dto.getArrivesheetNo())) {
			// 如果到达联不为空:收货人,收货人电话,收货人手机,结清时间起止为空
			dto.setReceiveCustomerName(null); //
			dto.setReceiveCustomerMobilephone(null);
			dto.setReceiveCustomerPhone(null);
			dto.setSettleTimeStart(null);//结清时间起为空
			dto.setSettleTimeEnd(null);//结清时间止为空
		} else if (StringUtils.isNotBlank(dto.getReceiveCustomerName())) {
			// 如果收货人不为空:收货人电话,收货人手机,结清时间起止为空
			dto.setReceiveCustomerMobilephone(null);//收货人手机为空
			dto.setReceiveCustomerPhone(null);//收货人电话为空
			dto.setSettleTimeStart(null);//结清时间起为空
			dto.setSettleTimeEnd(null);//结清时间止为空
		} else if (StringUtils.isNotBlank(dto.getReceiveCustomerMobilephone())) {
			// 如果收货人手机不为空:收货人电话为空,结清时间起止为空
			dto.setReceiveCustomerPhone(null);
			dto.setSettleTimeStart(null);//结清时间起为空
			dto.setSettleTimeEnd(null);//结清时间止为空
		}else if (StringUtils.isNotBlank(dto.getReceiveCustomerPhone())) {
			// 如果收货人电话不为空:结清时间起止为空
			dto.setSettleTimeStart(null);//结清时间起为空
			dto.setSettleTimeEnd(null);//结清时间止为空
		}else {
			//这里不作处理
		}
	}
	/**
	 * 查询签收出库流水号
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:10:15
	 * @param dto (运单号,当前登录人的部门编码)
	 *      dto.waybillNo 
	 * 			运单号
	 * 		dto.receiveCustomerName
	 * 			收货人(收货客户名称)
	 * 		dto.receiveCustomerPhone
	 * 			货客户电话
	 * 		dto.receiveCustomerMobilephone
	 * 			收货人手机
	 * 		dto.active
	 * 			运单状态
	 * 		dto.settleTimeStart
	 * 			结清时间起
	 * 		dto.settleTimeEnd
	 * 			 结清时间止
	 * 		dto.settleStatus
	 * 			结清状态
	 * 		dto.lastLoadOrgCode
	 * 			最终配载部门（判断是否为本部门）
	 * 		dto.isWholeVehicle
	 * 			是否整车运单
	 * 		dto.identifyType
	 * 			证件类型
	 * 		dto.productCode
	 * 			运输性质
	 * 		dto.deliverymanName
	 * 			提货人名称
	 * 		dto.identifyCode
	 * 			 证件号码
	 * 		dto.destroyed
	 * 			是否作废
	 * 		dto.arriveSheetGoodsQty
	 * 			到达联件数
	 * 		dto.endStockOrgCode
	 * 			最后库存code
	 * 		dto.goodsAreaCode
	 * 			库区
	 * 		dto.orderNo
	 * 			订单号   
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignService
	 * #queryStock(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto)
	 */
	@Override
	public List<StockDto> queryStock(SignDto dto) {
		//如果传入的对象为空
		if (dto == null){
			return null;
		}
		// ibm-meiying 2013-2-27 上午10:11:27添加库存外场、库区默认查询条件
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(dto.getLastLoadOrgCode());
		if (CollectionUtils.isNotEmpty(list)) {//如果查询的结果不为空
			List<String> ld=new ArrayList<String>();
			ld.add(list.get(1));
			ld.add(list.get(2));
			dto.setEndStockOrgCode(list.get(0)); 
			dto.setGoodsAreaCodes(ld);// 获取库区
		}
		// 有效
		dto.setActive(FossConstants.ACTIVE);
		return signDao.queryStock(dto);//返回流水号信息
	}
	/**
	 * 查询子母件在库信息
	 * @author 231438-chenjunying
	 */
	@Override
	public List<StockDto> queryCzmInStock(SignDto dto) {
		//如果传入的对象为空
		if (dto == null){
			return null;
		}
		// ibm-meiying 2013-2-27 上午10:11:27添加库存外场、库区默认查询条件
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(dto.getLastLoadOrgCode());
		if (CollectionUtils.isNotEmpty(list)) {//如果查询的结果不为空
			List<String> ld=new ArrayList<String>();
			ld.add(list.get(1));
			ld.add(list.get(2));
			dto.setEndStockOrgCode(list.get(0)); 
			dto.setGoodsAreaCodes(ld);// 获取库区
		}
		// 有效
		dto.setActive(FossConstants.ACTIVE);
		return signDao.queryCzmInStock(dto);//返回流水号信息
	}
	/**
	 * 提交录入的签收信息
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:09:38
	 * @param list
	 * 		签收明细列表
	 * @param entity 到达联信息
	 * 		 entity.waybill 
	 * 			运单号
	 * 		entity.arrivesheetNo
	 * 			到达联编号
	 * 		entity.deliverymanname
	 * 			提货人名称
	 * 		entity.identifyType
	 * 			证件类型
	 * 		entity.identifyCode
	 * 			证件号码
	 * 		entity.situation
	 * 			签收情况
	 * 		entity.arriveSheetGoodsQty
	 * 			到达联件数
	 * 		entity.signGoodsQty
	 * 			签收件数
	 * 		entity.signNote
	 * 			签收备注
	 * 		entity.signTime
	 * 			签收时间
	 * 		entity.status
	 * 			到达联状态
	 * 		entity.isPdaSign
	 * 			是否PDA签到
	 * 		entity.operateTime
	 * 			签收操作时间
	 * 		entity.operator
	 * 			操作人
	 * 		entity.operatorCode
	 * 			操作人编码
	 * 		entity.operateOrgName
	 * 			 操作部门名称
	 * 		entity.operateOrgCode
	 * 			操作部门编码
	 * 		entity.destroyed
	 * 			是否作废
	 * @param dto 签收确认收入服务使用DTO
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
	 * @param currentInfo 当前登录人信息
	 * 			currentInfo.operate 
	 * 				操作人
	 *          currentInfo.operatorCode 
	 *          	操作人编码
	 *          currentInfo.operateOrgName 
	 *          	操作部门名称
	 *          currentInfo.operateOrgCode 
	 *          	操作部门编码
	 * @param orderNo
	 * 			订单号
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignService
	 * 		#addSign(java.util.List,
	 *      com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity,
	 *      com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public String addSign(List<SignDetailEntity> list,ArriveSheetEntity entity, LineSignDto dto, CurrentInfo currentInfo,String orderNo){
		if(!DateCompareUtil.isToday(entity.getSignTime())){
			throw new SignException(SignException.SIGNTIME_ISNOT_SYSTEMDATE,new Object[]{new SimpleDateFormat("yyyy-MM-dd").format(new Date())});//当前电脑时间有误，请调整日期为{0}
		}
		String resultMsg = SignException.SIGN_SUCCESS;//返回信息
		LOGGER.info("提交录入的签收信息开始  到达联信息：" + ReflectionToStringBuilder.toString(entity));//记录日志
		signChangeService.checkWayBillRfcStatus(entity.getWaybillNo());//验证运单签收变更、反签收申请情况
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(entity.getWaybillNo());
		if(waybill== null){
			throw new SignException(SignException.WAYBILLNO_NULL);//该运单号不存在
		}
		//调接送货接口 判断是否有工单,有工单不允许签收
		ReturnGoodsRequestEntity  workOrderEntity = returnGoodsRequestEntityService.queryReturnGoodsEntityByWayBillNo( entity.getWaybillNo());
		if(null != workOrderEntity && FossConstants.NO.equals(workOrderEntity.getIsHandle())){
			throw new SignException("该运单存在未处理工单!"+workOrderEntity.getIsHandle());//存在工单且未处理完结
		}        
		//待排单运单dto 快递与菜鸟对接DN201507020017 yl 268377
		WaybillToArrangeDto waybillToArrangeDto = new WaybillToArrangeDto();
		waybillToArrangeDto.setWaybillNo(waybill.getWaybillNo());
		//根据运单号查询派送单明细
		DeliverbillDetailEntity deliverbillDetailEntity=this.deliverbillDetailDao.queryByCondition(waybillToArrangeDto);
		if(deliverbillDetailEntity == null){
			//派送轨迹推送
			addSynTrackInfo(entity, currentInfo, orderNo, waybill);
		}else{//存在派送单，签收流水号为0001，造派送轨迹
			//判断签收流水号是否为空
			if (CollectionUtils.isNotEmpty(list)){
				List<String> serialNos = new ArrayList<String>();
				for(SignDetailEntity signDetailEntity :list){
					serialNos.add(signDetailEntity.getSerialNo());
				}
				//判断流水号集合是否存在0001
				if(serialNos.contains(SignConstants.SERIAL_ZERO_ONE)){
					//派送轨迹推送
					addSynTrackInfo(entity, currentInfo, orderNo, waybill);
				}
			}
		}
		//张新  限制签收
		List<PrintAgentWaybillRecordEntity> sdExternalBillRecords = printAgentWaybillService.queryRecordByWaybillNo(entity.getWaybillNo(),SignConstants.LIMIT_SIGN);
		if(CollectionUtils.isNotEmpty(sdExternalBillRecords)) 
		{
			//有效的营业部外发
			LOGGER.error("--该单已外发，如需人工签收，请于“营业部外发单号绑定”中解除绑定");//记录日志
			throw new SignException(SignException.SIGN_LIMIT);//签收限制
		}
		ActualFreightEntity actual = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
		
		//DMANA-9716 add by chenjunying 2015-01-16  判断限制有投诉变更的运单 不让进行正常签收
		if(actual==null){
			//抛出异常，不再继续执行
			throw new SignException("实际承运表中无该运单信息");
		}
		//限制 有投诉变更签收结果的运单，签收时再做正常签收
		if(SignConstants.YES.equals(actual.getComplainStatus()) && 
			SignConstants.NORMAL_SIGN.equals(entity.getSituation())){ 
			//有投诉自动变更，签收不让正常签收
			throw new SignException("投诉自动变更异常签收的运单，反签收后不允许再做正常签收");
		}
		
		boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
		MutexElement mutexElement = new MutexElement(entity.getArrivesheetNo(), "到达联编号", MutexElementType.RFC_SIGN);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		if(!isLocked){//如果没有得到锁
			LOGGER.error("当前运单操作中，请稍后再试");//记录日志
			throw new SignException(SignException.WAYBILL_LOCKED);//返回错误信息
		}
		Date systemDate = new Date();
		if(WaybillConstants.INNER_PICKUP.equals(waybill.getReceiveMethod())){//提货方式为汽运内部自提
			LOGGER.info("--提货方式为汽运内部自提 不需要调用结算签收接口----");//记录日志
		}else {
			try {
				// 系统调用财务接口完成财务出库 首先判断是否第一次签收。
				//如果是就调用，如果不是，就不调用。 根据运单号判断到达联表里是否存在签收。
				ArriveSheetEntity arriveSheetEntity = new ArriveSheetEntity(entity.getWaybillNo(), ArriveSheetConstants.STATUS_SIGN,
						FossConstants.ACTIVE,FossConstants.NO);
				Long signCountLong = arriveSheetManngerService.queryArriveExistSign(arriveSheetEntity);
				// 系统调用财务接口完成财务出库
				if (signCountLong == null || SignConstants.ZERO >= signCountLong) {
					// 签收日期
					dto.setSignDate(systemDate);
					// 运单号
					dto.setWaybillNo(entity.getWaybillNo());
					// 操作人名称
					dto.setOperatorName(currentInfo.getEmpName());
					// 操作人编码
					dto.setOperatorCode(currentInfo.getEmpCode());
					// 签收部门名称
					dto.setSignOrgName(currentInfo.getCurrentDeptName());
					// 签收部门编码
					dto.setSignOrgCode(currentInfo.getCurrentDeptCode());
					// 签收类型为专线
					dto.setSignType(SettlementConstants.LINE_SIGN);
					// 不是PDA签收
					dto.setIsPdaSign(FossConstants.NO);
					//CUBC签收   灰度改造    353654 ---------------------------start
					String vestSystemCode = null;
					try {
		              	List<String> arrayList = new ArrayList<String>();
		              	arrayList.add(dto.getWaybillNo());
		              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".addSign",
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
//						//查询签收结果表
//				        WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(entity.getWaybillNo(),FossConstants.ACTIVE);
//				        WaybillSignResultEntity waybillSignResultEntity = waybillSignResultDao.queryWaybillSignResult(wayEntity);
//				        //获取已签收件数+此次签收件数与运单总件数比较判断全部和部分签收
//				        int signCount = (waybillSignResultEntity == null ? 0 : waybillSignResultEntity.getSignGoodsQty()) + entity.getSignGoodsQty();
//						String signStatus = signCount >= waybill.getGoodsQtyTotal() ? SignConstants.SIGN_STATUS_ALL
//								: SignConstants.SIGN_STATUS_PARTIAL;
//						if(SignConstants.SIGN_STATUS_ALL.equals(signStatus)){
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
//						}else{
//							LOGGER.info("ExpSignService.addSign,运单号："+entity.getWaybillNo()+",部分签收,不调CUBC签收接口");
//						}
					}
					//CUBC签收   灰度改造    353654 ---------------------------end
				}
			} catch (SettlementException se) {//捕获异常  
				LOGGER.error("用财务接口完成财务出库 异常",se);//记录日志
				businessLockService.unlock(mutexElement);//解锁
				// 异常处理
				throw new SignException(se.getErrorCode(),se);
			}
		}
		if(waybill.getReceiveOrgCode().equals(currentInfo.getCurrentDeptCode())&&FossConstants.YES.equals(waybill.getIsWholeVehicle())){
			if(SignConstants.SIGN_STATUS_PARTIAL.equals(entity.getSignStatus())){
				businessLockService.unlock(mutexElement);//解锁
				throw new SignException(SignException.WVH_NOT_PARTIAL_SIGN);//收货部门整车签收，只能整票签收，不能部分签收！
			}
			if(waybill.getGoodsQtyTotal()>entity.getSignGoodsQty()){
				businessLockService.unlock(mutexElement);//解锁
				throw new SignException(SignException.WVH_NOT_PARTIAL_SIGN);//收货部门整车签收，只能整票签收，请确认当前到达联件数是否为运单开单件数！
			}
			// 传入参数(运单号,当前运单号生效)
			WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(entity.getWaybillNo(), FossConstants.ACTIVE);
			// 根据运单号 查询运单签收结果
			WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(wayEntity);
			// 如果当前运单不是第一次添加
			if (waybillSign != null) {
				businessLockService.unlock(mutexElement);//解锁
				throw new SignException(SignException.WAYBILL_IS_SIGN);//整车签收只能签收一次
			}
			try {
				// 调用中转接口，整车签收时，未到达的车辆统一做到达
				departureService.carLoadArrive(entity.getWaybillNo());
			} catch (BusinessException e) {//捕获异常
				LOGGER.error(e.getMessage(), e);//记录日志
				businessLockService.unlock(mutexElement);//解锁
				throw new SignException(e.getErrorCode(),e);//抛出异常
			}
		}else{
			if(list.size()>SignConstants.DEFAULT_SERIALNOS_LIMIT_COUNT){
				for (SignDetailEntity signDetail : list){
					//添加签收明细信息
					signDetailService.addSignDetailInfo(signDetail);
					SignStockEntity signStock = new SignStockEntity();
					// 运单号
					signStock.setWaybillNo(entity.getWaybillNo());
					// 流水号
					signStock.setSerialNo(signDetail.getSerialNo());
					// 部门编码
					signStock.setStockOrgCode(currentInfo.getCurrentDeptCode());
					signStock.setStockOrgName(currentInfo.getCurrentDeptName());//部门名称 
					// 操作人姓名
					signStock.setOperator(currentInfo.getEmpName());
					// 操作人工号
					signStock.setOperatorCode(currentInfo.getEmpCode());
					// 出入库类型 签收出库
					signStock.setInoutType(StockConstants.SIGN_OUT_STOCK_TYPE);
					try {
						signStockJobService.addSelective(signStock);
					} catch (BusinessException e) {//捕获异常
						LOGGER.error(e.getMessage(), e);//记录日志
						businessLockService.unlock(mutexElement);//解锁
						throw new SignException(e.getErrorCode(),e);//抛出异常
					}
				}
				LOGGER.info("--保存签收明细完成 添加sign_stock完成");//记录日志
			}else {
				// 系统调用中转接口（SUC-238）出库货物
				for (SignDetailEntity signDetail : list){
					//添加签收明细信息
					signDetailService.addSignDetailInfo(signDetail);
					InOutStockEntity inOutStock = new InOutStockEntity();
					// 运单号
					inOutStock.setWaybillNO(entity.getWaybillNo());
					// 流水号
					inOutStock.setSerialNO(signDetail.getSerialNo());
					// 部门编码
					inOutStock.setOrgCode(currentInfo.getCurrentDeptCode());
					// 操作人姓名
					inOutStock.setOperatorName(currentInfo.getEmpName());
					// 操作人工号
					inOutStock.setOperatorCode(currentInfo.getEmpCode());
					// 出入库类型 签收出库
					inOutStock.setInOutStockType(StockConstants.SIGN_OUT_STOCK_TYPE);
					// 进行出库操作
					try {
						stockService.outStockPC(inOutStock);
					} catch (StockException e) {//捕获异常
						LOGGER.error(e.getMessage(), e);//记录日志
						businessLockService.unlock(mutexElement);//解锁
						throw new SignException(e.getErrorCode(),e);//抛出异常
					}
				}
			}
		}
		ActualFreightDto actualFreightDto = new ActualFreightDto(entity.getWaybillNo(),entity.getSignGoodsQty());
		//无PDA签收 修改运单状态里的到达未出库件数
		actualFreightService.updateArriveNotoutGoodsQtyByWaybillNo(actualFreightDto);
		// 操作人
		entity.setOperator(currentInfo.getEmpName());
		//签收操作时间
		entity.setOperateTime(systemDate);
		// 操作人编码
		entity.setOperatorCode(currentInfo.getEmpCode());
		// 操作部门名称
		entity.setOperateOrgName(currentInfo.getCurrentDeptName());
		// 操作部门编码
		entity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
		//如果是合伙人快递，则提货方式为派送
		if(FossConstants.YES.equals(entity.getIsPtpExp())){
			entity.setReceiveMethod(ArriveSheetConstants.SIGN_RECEIVE_METHOD_DELIVER);//提货方式--派送
		}else{
			entity.setReceiveMethod(ArriveSheetConstants.SIGN_RECEIVE_METHOD_PICKUP);//提货方式--自提
		}
		WaybillSignResultEntity wayEntity=null;
		try {
			//DN201511090005 删除签收人字段 243921
			entity.setDeliverymanName(null);
			wayEntity = waybillSignResultService.checkWaybillSignResultSign(entity,waybill,systemDate,currentInfo);
		} catch (SignException e) {//捕获异常
			businessLockService.unlock(mutexElement);//解锁
			throw new SignException(e.getErrorCode(),e);//抛出异常
		}
		// 不是PDA签收
		wayEntity.setIsPdaSign(FossConstants.NO);
		// 添加运单签收结果信息
		waybillSignResultService.addWaybillSignResult(wayEntity);
		
		//DN201511090005 添加“签收人类型”字段  243921
		wayEntity.setDeliverymanType(entity.getDeliverymanType());
		
		//菜鸟轨迹 add by 231438
		sendWaybillTrackService.rookieTrackingsForSign(wayEntity);
		
		if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){//如果签收状态为全部签收
			waybillSignResultService.updateCrmOrder(orderNo, currentInfo, wayEntity);
		}
		
		//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，推送轨迹--add by 231438 快递100轨迹推动送 
		sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
		
		// 修改到达联签收信息。到达联状态为 签收SIGN
		entity.setStatus(ArriveSheetConstants.STATUS_SIGN);
		//签收前的到达联件数  ---并发控制
		entity.setOldArriveSheetGoodsQty(entity.getArriveSheetGoodsQty());
		//签收前的到达联状态为生成 --并发控制
		entity.setOldStatus(ArriveSheetConstants.STATUS_GENERATE);
		if(arriveSheetManngerService.updateArriveSheetByArrivesheetNo(entity)<=0){ //如果修改到达联失败
			LOGGER.error("签收失败，请重新查询一下!"+ReflectionToStringBuilder.toString(entity));//记录日志
			businessLockService.unlock(mutexElement);//解锁
			throw new SignException(SignException.SIGN_FAILED);//抛出异常
		}
		//如果签收件数小于运单开单件数
		if(entity.getSignGoodsQty() != null &&entity.getSignGoodsQty()<waybill.getGoodsQtyTotal()){
			List<String> serials = new ArrayList<String>();
			for (SignDetailEntity signDetail : list){
				serials.add(signDetail.getSerialNo());
			}
			todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),serials);
		}else {
			todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),null);
		}
		if(FossConstants.YES.equals(waybill.getIsWholeVehicle()) && FossConstants.NO.equals(waybill.getIsPassOwnDepartment())){
			//不经过到达部门的整车运单，无论是收货部门还是到达部门做自提签收，在自提签收后系统均不发送短信给发货客户及收货客户；
		}else {
			WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
			BeanUtils.copyProperties(entity,waybillSigndto);
			//   如果代收人证件号码为空，则不用给收货客户发送短信
			if(actual!= null  &&StringUtil.isBlank(actual.getCodIdentifyCode() )  && StringUtil.isBlank(actual.getCodIdentifyType())){
				waybillSigndto.setIsSendSMSToReceiveCustomer(FossConstants.NO);//不用给收货人发送短信
			}
			//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
			resultMsg =waybillSignResultService.sendMessNotice(waybill, currentInfo,waybillSigndto);
			//调取FOSS中转补码查询的接口--中转-自动补码-补码后将待人工补码记录删除
			autoAddCodeByHandService.deleteAddCodeByHand(entity.getWaybillNo());
		}
		//应用监控签收调用
		waybillSignResultService.signCounterByWaybillNo(currentInfo, waybill);
			//如果签收情况为内物短少或同票多类异常（在recordQmsErrorWaybill方法里判断流水号），则上报QMS内物短少差错
			if(ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT.equals(entity.getSituation())
					|| ReportConstants.SIGN_SITUATION_UNNORMAL_SAMEVOTEODD.equals(entity.getSituation())){
			this.saveRecordShortErrorInfo(entity,currentInfo,list);
		}
		LOGGER.info("提交录入的签收信息结束");//记录日志
		LOGGER.info("-------------------签收绑定单号-------------------");//记录日志
		try {
			signBindWaybillNo(entity,currentInfo);
		} catch (SignException e) {//捕获异常
			businessLockService.unlock(mutexElement);//解锁
			throw new SignException(e.getErrorCode(),e);//抛出异常
		}
		if(isSendInvoiceInfo){
			//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
			waybillSignResultService.sendInvoiceInfo(waybill,actual);
		}
		businessLockService.unlock(mutexElement);//解锁
		LOGGER.info("-------------------签收绑定单号-------------------");//记录日志
		return resultMsg;
	}

	/**
	 * 派送轨迹信息推送
	 * @param entity
	 * @param currentInfo
	 * @param orderNo
	 * @param waybill
	 */
	private void addSynTrackInfo(ArriveSheetEntity entity, CurrentInfo currentInfo, String orderNo, WaybillEntity waybill) {
		//组织信息实体
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.
                queryOrgAdministrativeInfoByCodeNoCache(waybill.getLastLoadOrgCode());
		//通过员工编号查询员工信息
		EmployeeEntity employ = employeeService.queryEmployeeByEmpCode(currentInfo.getEmpCode());
		if(orgAdministrativeInfoEntity == null || employ == null){
            LOGGER.info("快递自提签收，添加派送轨迹，组织信息或员工信息不能为空！");
            throw new SignException("快递自提签收，添加派送轨迹，组织信息或员工信息不能为空！");
        }
		//待同步轨迹实体
		SynTrackingEntity synTrackingEntity=new SynTrackingEntity();
		synTrackingEntity.setId(UUIDUtils.getUUID());
		//运单号
		synTrackingEntity.setWayBillNo(entity.getWaybillNo());
		//物流订单号(渠道订单号)
		synTrackingEntity.setOrderNo(orderNo);
		//发生时间 当前签收时间前一分钟
		synTrackingEntity.setOperateTime(entity.getSignTime()==null?new Date(new Date().getTime()-SettlementReportNumber.SIXTY*SettlementReportNumber.ONE_THOUSAND):new Date(entity.getSignTime().getTime()-SettlementReportNumber.SIXTY*SettlementReportNumber.ONE_THOUSAND));
		//创建时间
		synTrackingEntity.setCreateDate(new Date());
		//跟踪信息描述	"派送中"
		synTrackingEntity.setTrackInfo("派送中");
		//发生城市
		synTrackingEntity.setOperateCity(orgAdministrativeInfoEntity.getCityName());
		//站点类型	"1"
		synTrackingEntity.setOrgType("1");
		//编号：当前操作部门的网点编号,到达部门编号
		synTrackingEntity.setOrgCode(currentInfo.getCurrentDeptCode());
		//当前操作部门的网点名称
		synTrackingEntity.setOrgName(currentInfo.getCurrentDeptName());
		//事件
		synTrackingEntity.setEventType("SENT_SCAN");
		//当前操作人工号
		synTrackingEntity.setOperatorCode(employ.getEmpCode());
		//当前操作人姓名
		synTrackingEntity.setOperatorName(employ.getEmpName());
		//当前操作人联系方式 	读取不到则用95353代替
		synTrackingEntity.setOperatorPhone(StringUtil.isNotBlank(employ.getPhone())?employ.getPhone():"95353");
		//添加轨迹表信息
		//签收轨迹新增同步轨迹表 DN201609280013   add by 321603
		//pushTrackForCaiNiaoService.addSynTrack(synTrackingEntity);
		pushTrackForCaiNiaoService.addSynLpsTrack(synTrackingEntity);
		LOGGER.info("快递自提签收，添加派送轨迹结束！");
	}

	/**
	 * 保存QMS内物短少差错信息
	 * @author foss-bieyexiong
	 * @date 2015-7-6 10:15:30
	 * */
	private void saveRecordShortErrorInfo(ArriveSheetEntity arriveEntity,CurrentInfo currentInfo,List<SignDetailEntity> list){
		//记录日志
		LOGGER.info("*************保存QMS内物短少差错信息***********start");
		if(arriveEntity != null && currentInfo != null && CollectionUtils.isNotEmpty(list)){			
			RecordErrorWaybillDto recordErrorWaybillDto=new RecordErrorWaybillDto();
			//主键
			recordErrorWaybillDto.setId(UUIDUtils.getUUID());
			//是否上报(Y:未上报)
			recordErrorWaybillDto.setActive(SignConstants.YES);
			//运单号
			recordErrorWaybillDto.setWaybillNo(arriveEntity.getWaybillNo());
			//短少量
			recordErrorWaybillDto.setAlittleShort(arriveEntity.getAlittleShort());
			//外包装是否完好
			recordErrorWaybillDto.setPackingResult(arriveEntity.getPackingResult());
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
			StringBuffer strbuff = new StringBuffer();
			//遍历签收流水号，取出内物短少流水号
			for(SignDetailEntity signDetail : list){
				//当流水号签收情况为 '内物短少'时，添加进strbuff字符串
				if(ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT.equals(signDetail.getSituation())){
					//以','分割
					strbuff.append(signDetail.getSerialNo()).append(SignConstants.SPLIT_CHAR);
				}
			}
			if(StringUtils.isNotEmpty(strbuff.toString())){
				//将最后一个','截断
				String rstSerialNo = strbuff.toString().substring(0,strbuff.toString().length()-1);
				if(StringUtils.isNotEmpty(rstSerialNo)){
			//流水号
					recordErrorWaybillDto.setSerialNo(rstSerialNo);
			recordErrorWaybillService.recordErrorWaybillReportOA(recordErrorWaybillDto);
		}
	}
		}
		//记录日志
		LOGGER.info("*************保存QMS内物短少差错信息***********end");
	}
	
	/**
	 * 菜鸟----------签收绑定的原单号
	 * @date 2015-2-5 上午10:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.ExpSignService#signBindWaybillNo(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	private void signBindWaybillNo(ArriveSheetEntity entity,CurrentInfo currentInfo) {
		LOGGER.info("-------------------签收绑定单号 begin-------------------");//记录日志
		//根据返单号查询返货记录
		List<WaybillExpressEntity> expressList =waybillExpressService.queryWaybillListByWaybillNo(entity.getWaybillNo());
		WaybillExpressEntity newWaybillNoEntity = CollectionUtils.isNotEmpty(expressList) ?expressList.get(0) :null;
		//判断新单号
		if(newWaybillNoEntity!=null){
			String oldWaybillNo = newWaybillNoEntity.getOriginalWaybillNo(); //原单号
			if(StringUtils.isNotEmpty(oldWaybillNo)){
				WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(oldWaybillNo);
				if(waybill!= null){ //返货原单号不为空
					//判断是否是子母件，如果是子母件，重新查询判断返货原单是否结清；如果不是子母件，走原来逻辑
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("waybillNo", oldWaybillNo);
					params.put("active", FossConstants.YES);
					//子母件信息
					TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
					//判断当前运单是否子母件
					if(oneDto != null && oneDto.getIsTwoInOne().equals(FossConstants.YES)){
						//判断母件单号是否为空
						if(StringUtils.isEmpty(oneDto.getMainWaybillNo())){
							throw new SignException("子母件获取母件单号失败！");
						}
						//结清子母件并签收，如果未结清，则先结清然后再签收
						rookieService.returnTwoInOneSign(entity, currentInfo, oneDto,null);
					}else{
						WaybillSignResultEntity wEntity = new WaybillSignResultEntity();
						wEntity.setWaybillNo(oldWaybillNo);
						wEntity.setActive(FossConstants.YES);
						WaybillSignResultEntity oldresultWEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(wEntity);
						if(oldresultWEntity!=null&&SignConstants.SIGN_STATUS_ALL.equals(oldresultWEntity.getSignStatus())){
						}else{
							WaybillSignResultEntity resultWEntity=new WaybillSignResultEntity();
							resultWEntity.setDeliverymanName(entity.getDeliverymanName());
							//DN201511090005  添加“签收人类型”字段
							resultWEntity.setDeliverymanType(entity.getDeliverymanType());
							resultWEntity.setIdentifyType(entity.getIdentifyType());
							resultWEntity.setIdentifyCode(entity.getIdentifyCode());
							resultWEntity.setSignSituation(entity.getSituation());
							resultWEntity.setSignTime(entity.getSignTime());
							resultWEntity.setSignNote(entity.getSignNote());
							resultWEntity.setIsPdaSign(entity.getIsPdaSign());
							resultWEntity.setWaybillNo(oldWaybillNo);
							resultWEntity.setSignGoodsQty(waybill.getGoodsQtyTotal());
							resultWEntity.setReceiveMethod(entity.getReceiveMethod());
							rookieService.exceuteSignStatusPart(oldresultWEntity, waybill, resultWEntity,null, currentInfo, null, false);
						}
					}
				}
			}
		}else{
			//运单号
			if(StringUtils.isNotEmpty(entity.getWaybillNo())){
				WaybillExpressEntity waybillExpressEntity = waybillExpressService.queryWaybillByOriginalWaybillNo(entity.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
				if(waybillExpressEntity!=null){
					throw new SignException("该单号已经返货新开单,请使用新单号进行操作!");//该运单号不存在
				}
			}
		}
		LOGGER.info("-------------------签收绑定单号 end-------------------");//记录日志
	}
	
	/**
	 * 根据运单号查询子母件信息
	 * @author 231438-chenjunying
	 * @date 2015-08-24 下午15:35:12
	 * @return
	 * @see
	 */
	@Override
	public CzmSignDto queryCzmInfo(SignDto dto){
		String waybillNo = dto.getWaybillNo();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("waybillNo", waybillNo); //运单号
		params.put("active", FossConstants.YES); //是否有效
		TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
		//拿到母单号，判断母单是否结清(未结清是因为子单结清关联结清母单，为现金结清要等30分钟触发JOB结清，不会立即结清)，没结清触发结清母单
		if(FossConstants.NO.equals(oneDto.getIsTwoInOne())){
			return null; //不是子母件直接返回空
		}else{
			if(StringUtils.isEmpty(oneDto.getMainWaybillNo())){
				throw new SignException("子母件母件单号为空！"); 
			}
			CzmSignDto czmDto  = new CzmSignDto();
			czmDto.setQueryWaybillNo(waybillNo); //子母件签收 查询单号
			czmDto.setCzmGoodsQtyTotal(oneDto.getWaybillNoList().size());//子母件总票数
			
			List<String> czmWaybilllist = new ArrayList<String>(); //子母件所有运单集合
			czmWaybilllist.addAll(oneDto.getWaybillNoList());		
			czmDto.setCzmWaybilllist(czmWaybilllist);

			dto.setCzmWaybillNoList(oneDto.getWaybillNoList()); //查询条件中给子母件集合，用于查询在库子母件
			
			//根据子母件运单信息查询出在库子母件，未在库子母件
			List<StockDto> inStockList = this.queryCzmInStock(dto); //查在库子母件的运单号及流水号
			if(CollectionUtils.isEmpty(inStockList)){
				throw new SignException("子母件在库票数为0！");
			}
			List<String> czmInStockList = new ArrayList<String>();  //子母件在库运单集合
			czmDto.setCzmGoodsQtyInStock(inStockList.size());//子母件在库票数
			for(StockDto sd : inStockList){
				czmInStockList.add(sd.getWaybillNo());
			}
			czmDto.setStockInWaybillNoList(czmInStockList); //在库子母件列表
			
			List<String> czmNotInList = new ArrayList<String>(); //子母件未在库运单集合
			czmNotInList.addAll(czmWaybilllist); //将子母件运单列表赋值给czmNotInList
			czmNotInList.removeAll(czmInStockList); //子母件列表移除在库子母件，留在list中的为未在库的
			czmDto.setStockNotInWaybillNoList(czmNotInList);//未在库子母件列表
			return czmDto;
		}
	}
	/**
	 * Sets the 签收明细service.
	 *
	 * @param signDetailService the new 签收明细service
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}

	/**
	 * Sets the 签收出库Dao.
	 *
	 * @param signDao the new 签收出库Dao
	 */
	public void setSignDao(ISignDao signDao) {
		this.signDao = signDao;
	}

	/**
	 * Sets the 到达联service.
	 *
	 * @param arriveSheetManngerService the new 到达联service
	 */
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	/**
	 * Sets the 运单签收结果service.
	 *
	 * @param waybillSignResultService the new 运单签收结果service
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * Sets the 结算签收Service.
	 *
	 * @param lineSignService the new 结算签收Service
	 */
	public void setLineSignService(ILineSignService lineSignService) {
		this.lineSignService = lineSignService;
	}	

	/**
	 * Sets the 结清货款Service.
	 *
	 * @param repaymentService the new 结清货款Service
	 */
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	/**
	 * Sets the 中转出库接口.
	 *
	 * @param stockService the new 中转出库接口
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * Sets the 运单状态服务接口.
	 *
	 * @param actualFreightService the new 运单状态服务接口
	 */
	public void setActualFreightService(
			IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	/**
	 * Sets the 外场相关共通接口.
	 *
	 * @param handleQueryOutfieldService the new 外场相关共通接口
	 */
	public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}
	
	/**
	 * Sets the 业务互斥锁服务.
	 *
	 * @param businessLockService the new 业务互斥锁服务
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	/**
	 * Sets the 签收反签收同步改异步接口.
	 *
	 * @param signStockJobService the new 签收反签收同步改异步接口
	 */
	public void setSignStockJobService(ISignStockJobService signStockJobService) {
		this.signStockJobService = signStockJobService;
	}
	
	/**
	 * Sets the 签收变更结果接口.
	 *
	 * @param signChangeService the new 签收变更结果接口
	 */
	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}
	
	/**
	 * Sets the 运单service.
	 *
	 * @param waybillManagerService the new 运单service
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	public void setTodoActionService(ITodoActionService todoActionService) {
		this.todoActionService = todoActionService;
	}
	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}
	public void setDepartureService(IDepartureService departureService) {
		this.departureService = departureService;
	}
	public void setWaybillExpressService(IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	public void setRookieService(IRookieService rookieService) {
		this.rookieService = rookieService;
	}
	public void setPrintAgentWaybillService(
			IPrintAgentWaybillService printAgentWaybillService) {
		this.printAgentWaybillService = printAgentWaybillService;
	}
	public void setRecordErrorWaybillService(
			IRecordErrorWaybillService recordErrorWaybillService) {
		this.recordErrorWaybillService = recordErrorWaybillService;
	}
	public IDeliverbillDetailDao getDeliverbillDetailDao() {
		return deliverbillDetailDao;
	}
	/**
	 * 派送单明细DAO接口
	 */
	public void setDeliverbillDetailDao(IDeliverbillDetailDao deliverbillDetailDao) {
		this.deliverbillDetailDao = deliverbillDetailDao;
	}
	public IPushTrackForCaiNiaoService getPushTrackForCaiNiaoService() {
		return pushTrackForCaiNiaoService;
	}
	/**
	 * 货物轨迹推送Service注入
	 */
	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}
	public IEmployeeService getEmployeeService() {
		return employeeService;
	}
	/**
	 * 员工信息服务接口注入
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}
	/**
	 * 综合服务接口 组织信息 Service注入
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	 * Sets the 子母件服务
	 * @param waybillRelateDetailEntityService
	 */
/*	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}*/
	/**
	 * Sets the 数据字典
	 * @param configurationParamsService
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	private void checkParamsValidte(List<CzmSignListDto> czmSignList,ArriveSheetEntity entity,LineSignDto dto,CurrentInfo currentInfo,String orderNo){
		if(czmSignList == null){
			throw new SignException("传入的子母件参数为空，无法签收！");
		}
		if(entity == null){
			throw new SignException("传入的到达联参数为空，无法签收！");
		}
		if(dto == null){
			throw new SignException("传入的确认收入参数为空，无法签收！");
		}
		if(currentInfo == null){
			throw new SignException("传入的登录信息为空，无法签收！");
		}
	}
	/**
	 * 批量签收(子母件)
	 * @author 231438-chenjunying
	 * 2015-08-27 下午17:35:12
	 */
	@Override
	@Transactional
	public String addBatchCzmSign(List<CzmSignListDto> czmSignList,ArriveSheetEntity entity,LineSignDto dto,CurrentInfo currentInfo,String orderNo){
		LOGGER.info(" --快递自提开始签收出库操作,子母件批量签收传入参数---- ");
		//验证参数有效否
		checkParamsValidte(czmSignList,entity,dto,currentInfo,orderNo);
		Map<String ,Object> params = new HashMap<String ,Object>();
		params.put("waybillNo", entity.getWaybillNo());
		params.put("active", FossConstants.YES);
		TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
		if(null == oneDto){
			throw new SignException("子母件信息不存在！");
		}
		//拿到母单号，判断母单是否结清(未结清是因为子单结清关联结清母单，为现金结清要等30分钟触发JOB结清，不会立即结清),没结清触发结清母单
		if(StringUtils.isEmpty(oneDto.getMainWaybillNo())){
			throw new SignException("子母件无母件单号！");
		}
		String montherWaybillNo = oneDto.getMainWaybillNo();//子母件母单号
		WaybillEntity motherWaybill = waybillManagerService.queryWaybillBasicByNo(montherWaybillNo);
		if(motherWaybill== null){
			throw new SignException("母单号："+entity.getWaybillNo()+":"+SignException.WAYBILLNO_NULL);//该运单号不存在
		}		
		RepaymentEntity repayment = new RepaymentEntity();  //母件信息
		repayment.setWaybillNo(montherWaybillNo);
		//调用结清货款接口判断当前运单是否已结清，未结清做母件结清
		boolean result =repaymentService.paymentOperate(repayment);
		//运单(母单)未做货款结清，不能签收出库
		if(!result){
		LOGGER.error("--子母件母单未做货款结清，不能签收出库");//记录日志
		throw new SignException("母单"+montherWaybillNo+"未结清，不能签收出库");//该运单未做货款结清，不能签收出库
		}
		//循环结清子母件， 下一步查询出到达联信息调用签收方法签收
		for(CzmSignListDto czmDto:czmSignList){
			//已结清跳过改方法继续向下执行,未结清进入方法进行结清
			this.sonSettlePayment(repayment,czmDto,currentInfo,motherWaybill);
		}
		List<BtachExecPramsDto> execList = new ArrayList<BtachExecPramsDto>();
		//批量结清、生成到达联后，执行批量签收
		for(CzmSignListDto czmDto:czmSignList){			
			// 调接送货接口 判断是否有工单,有工单不允许签收
			ReturnGoodsRequestEntity  workOrderEntity = returnGoodsRequestEntityService.queryReturnGoodsEntityByWayBillNo( entity.getWaybillNo());
			if(null != workOrderEntity && FossConstants.NO.equals(workOrderEntity.getIsHandle())){
				throw new SignException("运单："+czmDto.getWaybillNo()+"该运单存在未处理工单!");//存在工单且未处理完结
			}
		
			//查询当前签收子母件的到达联信息（结清货款时都有生成到达联）
			List<ArriveSheetEntity> arrList = arriveSheetManngerService.queryArriveSheetListNoSign(czmDto.getWaybillNo());
			if(null ==arrList || arrList.size()<=0 || arrList.size()>1 ){//子母件运单都只有一件，结清后只能有一条有效的到达联信息
				throw new SignException("运单："+czmDto.getWaybillNo()+"到达联信息异常，无法签收！");
			}
			//子母件签收共用页面传入的到达联
			entity.setWaybillNo(arrList.get(0).getWaybillNo()); //运单号
			entity.setArrivesheetNo(arrList.get(0).getArrivesheetNo());//到达联编号
			entity.setSignGoodsQty(entity.getArriveSheetGoodsQty()); //设置签收件数(页面有赋值,再次赋值确保不为空)
			List<SignDetailEntity> list = new ArrayList<SignDetailEntity>();
			SignDetailEntity detailEntity = new SignDetailEntity();
			detailEntity.setArrivesheetNo(entity.getArrivesheetNo());  //到达联编号
			detailEntity.setSerialNo(czmDto.getSerialNo());  //流水号
			detailEntity.setSituation(entity.getSituation()); //流水号的签收情况 子母件一批签收共用签收情况
			list.add(detailEntity); 
		
			LOGGER.info(" --快递自提开始签收出库操作,子母件签收开始：---- ");
			if(!DateCompareUtil.isToday(entity.getSignTime())){
				throw new SignException(SignException.SIGNTIME_ISNOT_SYSTEMDATE,new Object[]{new SimpleDateFormat("yyyy-MM-dd").format(new Date())});//当前电脑时间有误，请调整日期为{0}
			}
			Date systemDate = new Date();
			WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(entity.getWaybillNo());
			if(waybill== null){
				throw new SignException(entity.getWaybillNo()+":"+SignException.WAYBILLNO_NULL);//该运单号不存在
			}
			
			//菜鸟造派送轨迹(有则给，无则向下执行)
			this.addSynTrack(waybill,entity,currentInfo, orderNo);
			//张新  限制签收
			List<PrintAgentWaybillRecordEntity> sdExternalBillRecords = printAgentWaybillService.queryRecordByWaybillNo(entity.getWaybillNo(),SignConstants.LIMIT_SIGN);
			if(CollectionUtils.isNotEmpty(sdExternalBillRecords)) 
			{
				//有效的营业部外发
				LOGGER.error("--该单已外发，如需人工签收，请于“营业部外发单号绑定”中解除绑定");//记录日志
				throw new SignException(SignException.SIGN_LIMIT);//签收限制
			}
			ActualFreightEntity actual = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
			//DMANA-9716 add by chenjunying 2015-01-16  判断限制有投诉变更的运单 不让进行正常签收
			if(actual==null){
				//抛出异常，不再继续执行
				throw new SignException("实际承运表中无该运单信息");
			}
			//限制 有投诉变更签收结果的运单，签收时不允许再做正常签收
			if(SignConstants.YES.equals(actual.getComplainStatus()) && 
				SignConstants.NORMAL_SIGN.equals(entity.getSituation())){ 
				//有投诉自动变更，签收不让正常签收
				throw new SignException("投诉自动变更异常签收的运单，反签收后不允许再做正常签收");
			}
			boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
			MutexElement mutexElement = new MutexElement(entity.getArrivesheetNo(), "到达联编号", MutexElementType.RFC_SIGN);
			//互斥锁定
			boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
			if(!isLocked){//如果没有得到锁
				LOGGER.error("当前运单操作中，请稍后再试");//记录日志
				throw new SignException(SignException.WAYBILL_LOCKED);//返回错误信息
			}
			if(WaybillConstants.INNER_PICKUP.equals(waybill.getReceiveMethod())){//提货方式为汽运内部自提
				LOGGER.info("--提货方式为汽运内部自提 不需要调用结算签收接口----");//记录日志
			}else {
				try {//确认收入
					// 系统调用财务接口完成财务出库 首先判断是否第一次签收。/如果是就调用，如果不是，就不调用。 根据运单号判断到达联表里是否存在签收。
					ArriveSheetEntity arriveSheetEntity = new ArriveSheetEntity(entity.getWaybillNo(), ArriveSheetConstants.STATUS_SIGN,
							FossConstants.ACTIVE,FossConstants.NO);
					Long signCountLong = arriveSheetManngerService.queryArriveExistSign(arriveSheetEntity);
					// 系统调用财务接口完成财务出库
					if (signCountLong == null || SignConstants.ZERO >= signCountLong) {
						this.confirmIncome(entity.getWaybillNo(), dto, currentInfo, systemDate,oneDto);
					}
				}catch (SettlementException se) {//捕获异常  
					LOGGER.error("用财务接口完成财务出库 异常",se);//记录日志
					businessLockService.unlock(mutexElement);//解锁
					// 异常处理
					throw new SignException(se.getErrorCode(),se);
				}
				LOGGER.info("用财务接口完成财务出库完成");//记录日志
			}
			
			if(list.size()>SignConstants.DEFAULT_SERIALNOS_LIMIT_COUNT){
				for (SignDetailEntity signDetail : list){
					//添加签收明细信息
					signDetailService.addSignDetailInfo(signDetail);
					SignStockEntity signStock = new SignStockEntity();
					// 运单号
					signStock.setWaybillNo(entity.getWaybillNo());
					// 流水号
					signStock.setSerialNo(signDetail.getSerialNo());
					// 部门编码
					signStock.setStockOrgCode(currentInfo.getCurrentDeptCode());
					signStock.setStockOrgName(currentInfo.getCurrentDeptName());//部门名称 
					// 操作人姓名
					signStock.setOperator(currentInfo.getEmpName());
					// 操作人工号
					signStock.setOperatorCode(currentInfo.getEmpCode());
					// 出入库类型 签收出库
					signStock.setInoutType(StockConstants.SIGN_OUT_STOCK_TYPE);
					try {
						signStockJobService.addSelective(signStock);
					} catch (BusinessException e) {//捕获异常
						LOGGER.error(e.getMessage(), e);//记录日志
						businessLockService.unlock(mutexElement);//解锁
						throw new SignException(e.getErrorCode(),e);//抛出异常
					}
				}
				LOGGER.info("--保存签收明细完成 添加sign_stock完成");//记录日志
			}else {
				// 系统调用中转接口（SUC-238）出库货物
				for (SignDetailEntity signDetail : list){
					//添加签收明细信息
					signDetailService.addSignDetailInfo(signDetail);
					InOutStockEntity inOutStock = new InOutStockEntity();
					// 运单号
					inOutStock.setWaybillNO(entity.getWaybillNo());
					// 流水号
					inOutStock.setSerialNO(signDetail.getSerialNo());
					// 部门编码
					inOutStock.setOrgCode(currentInfo.getCurrentDeptCode());
					// 操作人姓名
					inOutStock.setOperatorName(currentInfo.getEmpName());
					// 操作人工号
					inOutStock.setOperatorCode(currentInfo.getEmpCode());
					// 出入库类型 签收出库
					inOutStock.setInOutStockType(StockConstants.SIGN_OUT_STOCK_TYPE);
					// 进行出库操作
					try {
						stockService.outStockPC(inOutStock);
					} catch (StockException e) {//捕获异常
						LOGGER.error(e.getMessage(), e);//记录日志
						businessLockService.unlock(mutexElement);//解锁
						throw new SignException(e.getErrorCode(),e);//抛出异常
					}
				}
			}
			ActualFreightDto actualFreightDto = new ActualFreightDto(entity.getWaybillNo(),entity.getSignGoodsQty());
			//无PDA签收 修改运单状态里的到达未出库件数
			actualFreightService.updateArriveNotoutGoodsQtyByWaybillNo(actualFreightDto);
			// 操作人
			entity.setOperator(currentInfo.getEmpName());
			//签收操作时间
			entity.setOperateTime(systemDate);
			// 操作人编码
			entity.setOperatorCode(currentInfo.getEmpCode());
			// 操作部门名称
			entity.setOperateOrgName(currentInfo.getCurrentDeptName());
			// 操作部门编码
			entity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			entity.setReceiveMethod(ArriveSheetConstants.SIGN_RECEIVE_METHOD_PICKUP);//提货方式--自提
			WaybillSignResultEntity wayEntity=null;
			try {
				//DN201511090005 删除签收人字段 243921
				entity.setDeliverymanName(null);
				wayEntity = waybillSignResultService.checkWaybillSignResultSign(entity,waybill,systemDate,currentInfo);
				if(wayEntity.getSignGoodsQty() > waybill.getGoodsQtyTotal()){//如果签收件数>开单件数（防止并发）
					throw new SignException("运单:"+waybill.getWaybillNo()+"签收件数>开单件数");//抛出异常
				}
			} catch (SignException e) {//捕获异常
				businessLockService.unlock(mutexElement);//解锁
				throw new SignException(e.getErrorCode(),e);//抛出异常
			}
			// 不是PDA签收
			wayEntity.setIsPdaSign(FossConstants.NO);
			// 添加运单签收结果信息
			waybillSignResultService.addWaybillSignResult(wayEntity);
			
			//DN201511090005 添加“签收人类型”字段  243921
			wayEntity.setDeliverymanType(entity.getDeliverymanType());
			//菜鸟轨迹
			sendWaybillTrackService.rookieTrackingsForSign(wayEntity);
			//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，推送轨迹--add by 231438 快递100轨迹推动送 
			sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
			//调用CRM修改订单接口更新订单状态.
			if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){//如果签收状态为全部签收
				waybillSignResultService.updateCrmOrder(orderNo, currentInfo, wayEntity);
			}
			String arrStatus = entity.getStatus();//变量用来存未设置签收状态之前的原到达联状态
			// 修改到达联签收信息。到达联状态为 签收SIGN
			entity.setStatus(ArriveSheetConstants.STATUS_SIGN);
			//签收前的到达联件数  ---并发控制
			entity.setOldArriveSheetGoodsQty(entity.getArriveSheetGoodsQty());
			//签收前的到达联状态为生成 --并发控制
			entity.setOldStatus(ArriveSheetConstants.STATUS_GENERATE);
			if(arriveSheetManngerService.updateArriveSheetByArrivesheetNo(entity)<=0){ //如果修改到达联失败
				if(ArriveSheetConstants.STATUS_DELIVER.equals(arrStatus)){
					LOGGER.error(entity.getWaybillNo()+"签收失败，运单到达联状态为派送!"+ReflectionToStringBuilder.toString(entity));//记录日志
					businessLockService.unlock(mutexElement);//解锁
					throw new SignException("运单"+entity.getWaybillNo()+"到达联为派送状态，不能做自提签收！");//抛出异常
				}else{
					LOGGER.error("签收失败，请重新查询一下!"+ReflectionToStringBuilder.toString(entity));//记录日志
					businessLockService.unlock(mutexElement);//解锁
					throw new SignException(SignException.SIGN_FAILED);//抛出异常
				}
			}
			//如果签收件数小于运单开单件数
			if(entity.getSignGoodsQty() != null &&entity.getSignGoodsQty()<waybill.getGoodsQtyTotal()){
				List<String> serials = new ArrayList<String>();
				for (SignDetailEntity signDetail : list){
					serials.add(signDetail.getSerialNo());
				}
				todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),serials);
			}else {
				todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),null);
			}
			
			WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
			BeanUtils.copyProperties(entity,waybillSigndto);
			//   如果代收人证件号码为空，则不用给收货客户发送短信
			if(actual!= null  &&StringUtil.isBlank(actual.getCodIdentifyCode() )  && StringUtil.isBlank(actual.getCodIdentifyType())){
				waybillSigndto.setIsSendSMSToReceiveCustomer(FossConstants.NO);//不用给收货人发送短信
			}
			businessLockService.unlock(mutexElement);//解锁
			BtachExecPramsDto paramsDto = new BtachExecPramsDto();
			paramsDto.setWaybillSignResultDto(waybillSigndto);
			paramsDto.setWaybill(waybill);
			paramsDto.setActual(actual);
			paramsDto.setWayEntity(wayEntity);
			paramsDto.setSendInvoiceInfo(isSendInvoiceInfo);
			execList.add(paramsDto);
 		}
		// 循环调用签收要执行的异步方法
		if(CollectionUtils.isNotEmpty(execList) && execList.size()>0){
			//签收出库完成后，批量调用执行异步接口
			for(BtachExecPramsDto paramDto:execList){
				//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
				waybillSignResultService.sendMessNotice(paramDto.getWaybill(), currentInfo,paramDto.getWaybillSignResultDto());
				if(paramDto.isSendInvoiceInfo()){
					//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
					waybillSignResultService.sendInvoiceInfo(paramDto.getWaybill(),paramDto.getActual());
				}
			}
		}
		return "子母件批量签收完成！";
	} 
	/**
	 * 子件结清货款，先判断是否结清，未结清生成0.0的付款记录，付款信息copy母件的
	 * @param repayment
	 * @param entity
	 * @param currentInfo
	 * @param motherWaybill 
	 */
	private void sonSettlePayment(RepaymentEntity repayment,CzmSignListDto czmDto,CurrentInfo currentInfo, WaybillEntity motherWaybill){
		//判断当前签收的运单是否结清(母单前面有判断并关联结清，未结清会抛出异常阻断)，未结清手动生成结清 0.0的结清记录
		if (!repaymentService.isPayment(czmDto.getWaybillNo())) {
			RepaymentEntity mother = repaymentService.queryOneRepaymentInfo(repayment);//获取母件的付款信息
			if(null==mother){
				throw new SignException("子母件,母件:"+repayment.getWaybillNo()+"未结清,无法签收！");
			}
			RepaymentEntity son = new RepaymentEntity();
			BeanUtils.copyProperties(mother, son);
			// 运单号
			son.setWaybillNo(czmDto.getWaybillNo());
			// 实付运费
			son.setActualFreight(BigDecimal.ZERO);
			son.setCodAmount(BigDecimal.ZERO);// 代收货款
			// 财务单据无需生成
			son.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
			
			// 更新ActualFreight表中的结清状态为已结清
			ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
			//运单号
			actualFreightEntity.setWaybillNo(son.getWaybillNo());
			//结清状态-已结清
			actualFreightEntity.setSettleStatus(FossConstants.YES);
			//结款日期
			actualFreightEntity.setSettleTime(new Date());
			//收货人
			actualFreightEntity.setDeliverymanName(son.getDeliverymanName());
			//证件类型
			actualFreightEntity.setIdentifyType(son.getIdentifyType());
			//证件号
			actualFreightEntity.setIdentifyCode(son.getIdentifyCode());
			if (StringUtil.isNotBlank(son.getCodIdentifyCode())) {
				//证件类型(代收人)
				actualFreightEntity.setCodIdentifyType(son.getCodIdentifyType());
				//证件号码（代收）
				actualFreightEntity.setCodIdentifyCode(son.getCodIdentifyCode());
			} else {
				//证件类型(代收人)
				actualFreightEntity.setCodIdentifyType("");
				//证件号码（代收）
				actualFreightEntity.setCodIdentifyCode("");
			}
			//更新actualFreight表
			actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
			
			try {
				// 添加0.0的结清记录,同时生成到达联--不掉结算的接口
				repaymentDao.addPaymentInfo(son);
				// 生成到达联信息
				ArriveSheetEntity entity = new ArriveSheetEntity();
				//运单号
				entity.setWaybillNo(czmDto.getWaybillNo());
				//收货人
				entity.setDeliverymanName(son.getDeliverymanName());
				//证件类型
				entity.setIdentifyType(son.getIdentifyType());
				//证件号
				entity.setIdentifyCode(son.getIdentifyCode());
				//给到达联source赋值PKP_NOTIFY为的是：保证结清后一定会生成到达联(到达件数为0时，走通知生成到达联逻辑)
				entity.setSource(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
				// 调用到达联接口
				arriveSheetManngerService.checkGenerateArriveSheet(entity);
				// 更新到达联,通过运单号
				arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(son.getWaybillNo(), son.getDeliverymanName(), son.getIdentifyType(), son.getIdentifyCode());
			} catch (RepaymentException e) {//捕获异常
				LOGGER.error("--调用结清货款抛出异常:"+e.getErrorCode());//记录异常信息
				throw new SignException(e.getErrorCode(),e);//抛出捕获的异常
			}
			LOGGER.info("调用结清货款完成");//记录日志
		}
	}
	
	private void addSynTrack(WaybillEntity waybill,ArriveSheetEntity entity,CurrentInfo currentInfo,String orderNo){
		LOGGER.info("快递自提签收，添加派送轨迹开始。。。。");
		WaybillToArrangeDto waybillToArrangeDto = new WaybillToArrangeDto();
		waybillToArrangeDto.setWaybillNo(waybill.getWaybillNo());
		//根据运单号查询派送单明细
		DeliverbillDetailEntity deliverbillDetailEntity=this.deliverbillDetailDao.queryByCondition(waybillToArrangeDto);
		//是否存在派送单
		if(deliverbillDetailEntity == null) {
			//派送轨迹信息推送
			addSynTrackInfo(entity, currentInfo, orderNo, waybill);

		}
	}
	/**
	 * 确认收入：1.子件正常调用确认，如果子件是签收的最后一件同时确认母件的收入；
	 * 2.母件只在最后一件签收确认收入时确认(最后一件事母件确认母件，最后一件是子件确认子件和母件[母件之前签收不确认收入])
	 * @author 231438-chenjunying
	 * @param entity
	 * @param dto
	 * @param currentInfo
	 */
	public void confirmIncome(String waybillNo,LineSignDto dto,CurrentInfo currentInfo,Date systemDate,TwoInOneWaybillDto oneDto){
		//1.拿到子母件列表、母件单号
		//2.12.1判断当前运单是否母单
		//2.2查询签收结果，计算当前签收的是否为子母件的最后一件
		if(FossConstants.YES.equals(oneDto.getIsTwoInOne())){
		//判断当前运单是否子母件
			List<WaybillSignResultEntity> list = new ArrayList<WaybillSignResultEntity>();
			for(String signWaybillNo:oneDto.getWaybillNoList()){
				// 传入参数(运单号,当前运单号生效)
				WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(signWaybillNo, FossConstants.ACTIVE);
				// 根据运单号 查询运单签收结果
				WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(wayEntity);
				if( null != waybillSign){
					list.add(waybillSign);
				}
			}
			//判断当前件是子母件的母件
			if(oneDto.getMainWaybillNo().equals(waybillNo)){
				if(oneDto.getWaybillNoList().size() - list.size()  > 1){
					//母件不是最后一件签收，不做确认收入
				}else{ //母件签收是最后一件
					//母件确认收入
					this.comfirm(dto,currentInfo, systemDate,waybillNo);
				}
			}else{//子件
				//当前子件不是子母件签收的最后一件
				if(oneDto.getWaybillNoList().size() - list.size()  > 1){
					//子件确认收入
					this.comfirm(dto,currentInfo, systemDate,waybillNo);
				}else{//当前子件是子母件签收的最后一件
					//子件确认收入
					this.comfirm(dto,currentInfo, systemDate,waybillNo);
					
					//母件确认收入
					this.comfirm(dto,currentInfo, systemDate,oneDto.getMainWaybillNo());
				}
			}
			LOGGER.info("子母件调用结算签收接口结束");//记录日志
		}
	}
	//子母件签收
	private void comfirm(LineSignDto dto,CurrentInfo currentInfo,Date systemDate,String waybillNo){
		// 签收日期
		dto.setSignDate(systemDate);
		// 运单号
		dto.setWaybillNo(waybillNo);
		// 操作人名称
		dto.setOperatorName(currentInfo.getEmpName());
		// 操作人编码
		dto.setOperatorCode(currentInfo.getEmpCode());
		// 签收部门名称
		dto.setSignOrgName(currentInfo.getCurrentDeptName());
		// 签收部门编码
		dto.setSignOrgCode(currentInfo.getCurrentDeptCode());
		// 签收类型为专线
		dto.setSignType(SettlementConstants.LINE_SIGN);
		// 不是PDA签收
		dto.setIsPdaSign(FossConstants.NO);
		//CUBC签收   灰度改造    353654 ---------------------------start
		String vestSystemCode = null;
		try {
          	List<String> arrayList = new ArrayList<String>();
          	arrayList.add(dto.getWaybillNo());
          	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
          			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".comfirm",
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
	/**
	 * 查询子母件签收限制配置参数的值
	 * @author 231438-chenjunying
	 * @date 2015-09-09 上午10:55:12
	 * @return
	 * @see
	 */
	@Override
	public String queryCzmSignLimit(String valueCode){
		if(null == valueCode){
			return null;
		}else{
			return configurationParamsService.queryConfValueByCode(valueCode);
			}
	}
	
	/**
     * 初始化合伙人快递签收查询条件
     * @author gpz
     * @date 2016年1月21日
     * @param SignDto
     */
    private void initPtpExpressSignQueryCondition(SignDto dto) {
    	// 不包含到达联状态为派送中和已签收的 
    	dto.setArriveSheetstatus(new String[] {ArriveSheetConstants.STATUS_DELIVER,ArriveSheetConstants.STATUS_SIGN,ArriveSheetConstants.STATUS_REFUSE});  
        // 运输性质 （偏线和空运）
        List<String> productCodeList = new ArrayList<String>();
        //只能查询出‘标准快递’和‘3.60特惠件’运单号,商务专递
        productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
        productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE);
        productCodeList.add(PricingConstants.ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);
        productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT);

        dto.setProductCodeList(productCodeList);
        // 有效
        dto.setActive(FossConstants.ACTIVE);
        //未作废
        dto.setDestroyed(FossConstants.NO);
        // 运单已结清货款 状态为Y时已结清货款
        //dto.setSettleStatus(FossConstants.YES);
        // ibm-meiying 2013-2-27 上午10:11:27添加库存外场、库区默认查询条件
        List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(dto.getLastLoadOrgCode());
        if (CollectionUtils.isNotEmpty(list)) {
            dto.setEndStockOrgCode(list.get(0)); //最终库存部门编码
            dto.setGoodsAreaCode(list.get(1));// 获取库区
        }
        // 如果运单号不为空:其他条件都设为空
        if (StringUtil.isNotBlank(dto.getWaybillNo())) {
            dto.setArrivesheetNo(null); //到达联编号为空
            dto.setReceiveCustomerName(null);//收货人为空
            dto.setReceiveCustomerMobilephone(null);//收货人手机为空
            dto.setReceiveCustomerPhone(null);//收货人电话为空
            dto.setInStockTimeStart(null);//入库时间起为空
            dto.setInStockTimeEnd(null);// 入库时间止为空
        } else if (StringUtil.isNotBlank(dto.getArrivesheetNo())) {
            // 如果到达联不为空:收货人,收货人电话,收货人手机,结清时间起止为空
            dto.setReceiveCustomerName(null); //
            dto.setReceiveCustomerMobilephone(null);
            dto.setReceiveCustomerPhone(null);
            dto.setInStockTimeStart(null);//入库时间起为空
            dto.setInStockTimeEnd(null);// 入库时间止为空
        } else if (StringUtils.isNotBlank(dto.getReceiveCustomerName())) {
            // 如果收货人不为空:收货人电话,收货人手机,结清时间起止为空
            dto.setReceiveCustomerMobilephone(null);//收货人手机为空
            dto.setReceiveCustomerPhone(null);//收货人电话为空
            dto.setInStockTimeStart(null);//入库时间起为空
            dto.setInStockTimeEnd(null);// 入库时间止为空
        } else if (StringUtils.isNotBlank(dto.getReceiveCustomerMobilephone())) {
            // 如果收货人手机不为空:收货人电话为空,结清时间起止为空
            dto.setReceiveCustomerPhone(null);
            dto.setInStockTimeStart(null);//入库时间起为空
            dto.setInStockTimeEnd(null);// 入库时间止为空
        }else if (StringUtils.isNotBlank(dto.getReceiveCustomerPhone())) {
            // 如果收货人电话不为空:结清时间起止为空
            dto.setInStockTimeStart(null);//入库时间起为空
            dto.setInStockTimeEnd(null);// 入库时间止为空
        }else {
            //这里不作处理
        }
        
        //合伙人签收410初始化数据-----------start----------239284
  		//配置参数-合伙人签收4.10上线前运单走以前逻辑;  读取配置参数日期开关;  如果开关日期不为空，则日期之前走原签收流程；日期之后走合伙人签收流程
  		String configString = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
  		if (StringUtils.isNotBlank(configString)) {
  			dto.setConBillTime(DateUtils.convert(configString.trim(),DateUtils.DATE_TIME_FORMAT));
  		} else {
  			dto.setConBillTime(null);
  		}
  		//合伙人签收410初始化数据-----------end----------239284
        
    }

    /**
     * 根据条件分页查询和合伙人快递自提签收 到达联信息
     * @param SignDto
     * 		dto.waybillNo
     * 			运单号
     * 		dto.receiveCustomerName
     * 			收货人(收货客户名称)
     * 		dto.receiveCustomerPhone
     * 			货客户电话
     * 		dto.receiveCustomerMobilephone
     * 			收货人手机
     * 		dto.active
     * 			运单状态
     * 		dto.settleTimeStart
     * 			结清时间起
     * 		dto.settleTimeEnd
     * 			 结清时间止
     * 		dto.settleStatus
     * 			结清状态
     * 		dto.lastLoadOrgCode
     * 			最终配载部门（判断是否为本部门）
     * 		dto.isWholeVehicle
     * 			是否整车运单
     * 		dto.identifyType
     * 			证件类型
     * 		dto.productCode
     * 			运输性质
     * 		dto.deliverymanName
     * 			提货人名称
     * 		dto.identifyCode
     * 			 证件号码
     * 		dto.destroyed
     * 			是否作废
     * 		dto.arriveSheetGoodsQty
     * 			到达联件数
     * 		dto.endStockOrgCode
     * 			最后库存code
     * 		dto.goodsAreaCode
     * 			库区
     * 		dto.orderNo
     * 			订单号
     *      dto.isExpress
     *          是否快递
     * @param start
     * 		第几页
     * @param limit
     * 		每页显示多少条
     *
     * (non-Javadoc)
     * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignService#queryPtpExpressArriveSheetByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto, int, int)
     */
    @Override
    public ArriveSheetVo queryPtpExpressArriveSheetByParams(SignDto signDto,
                                                            int start, int limit) {
        ArriveSheetVo arriveSheetVo = new ArriveSheetVo();
        arriveSheetVo.setTotalCount(Long.valueOf(SignConstants.ZERO));
        //初始化查询条件
        this.initPtpExpressSignQueryCondition(signDto);
        // 如果运单号不为空
        if (StringUtil.isNotBlank(signDto.getWaybillNo())) {
        	//通过运单号获取运单信息
            WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(signDto.getWaybillNo());
            if(waybillEntity != null && FossConstants.YES.equals(waybillEntity.getIsWholeVehicle()) && signDto.getLastLoadOrgCode().equals(waybillEntity.getReceiveOrgCode())
                    && FossConstants.NO.equals(waybillEntity.getIsPassOwnDepartment()))//判断如果当前部门是整车，当前登录部门是收货部门，且不经过到达部门
            {
                //update by foss 231434 2015-7-22
                //对整车(三级)产品，若开单时间在2013-08-01之前(不包含2013-08-01)，不做配载校验
                Date date = DateUtils.convert("2013-08-01", DateUtils.DATE_FORMAT);
                //中转接口校验通过
                if(!date.before(waybillEntity.getBillTime()) ||
                        vehicleAssembleBillService.queryVehicleAssemblyBillByWaybillNo(signDto.getWaybillNo()))
                {
                    List<SignArriveSheetDto> dtos= new ArrayList<SignArriveSheetDto>();
                    //根据运单号查询到达联信息（整车）
                    dtos = signDao.queryArrivesheetListByWaybillWVH(signDto);
                    if(CollectionUtils.isNotEmpty(dtos)){
                        for (SignArriveSheetDto signArriveSheetDto : dtos) {
                            signArriveSheetDto.setIsCurrentOrgCodeReceiveOrgCodeWVH(FossConstants.YES);
                        }
                        arriveSheetVo.setSignArriveSheetDtoList(dtos);
                        arriveSheetVo.setTotalCount(Long.valueOf(SignConstants.ONE));

                    }else {
                        arriveSheetVo.setSignArriveSheetDtoList(null);
                    }
                    // 查询待处理列表
                    return arriveSheetVo;
                }else{
                    // 抛出业务异常
                    throw new SignException(SignException.VEHICLEASSEMBLEBILL_INVALID);
                }
            }
        }
        arriveSheetVo.setTotalCount(this.queryPtpExpArriveSheetInfoCountByParams(signDto));
        // 如果存在信息
        if (arriveSheetVo.getTotalCount() != null && arriveSheetVo.getTotalCount() > SignConstants.ZERO ) {
            arriveSheetVo.setSignArriveSheetDtoList(this.queryPtpExpArriveSheetInfoByParams(signDto,start, limit));
        }else {
            arriveSheetVo.setSignArriveSheetDtoList(null);
        }
        return arriveSheetVo;
    }
    
    /**
	 * 签收出库---根据条件分页查询到达联总数(合伙人快递自提签收)
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:08:58
	 * @param dto 签收出库Dto
	 * @return Long
	 */ 
	public Long queryPtpExpArriveSheetInfoCountByParams(SignDto dto){
		Long count = Long.valueOf(SignConstants.ZERO);//默认值 为0
		try {
			count = arriveSheetManngerService.queryPtpExpArriveSheetInfoCountByParams(dto);
			return count; //返回查询总数量
		} catch (RepaymentException e) {//捕获异常
			//记录日志
			LOGGER.error(e.getErrorCode(),e);
			// 异常处理
			throw new SignException(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 根据条件分页查询到达联信息(合伙人快递自提签收)
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:07:27
	 * @param dto 签收出库Dto
	 * @param start
	 * 		第几页
	 * @param limit
	 * 		每页显示多少条
	 * @return List<SignArriveSheetDto> 到达联结果集合
	 */
	public List<SignArriveSheetDto> queryPtpExpArriveSheetInfoByParams(SignDto dto,
			int start, int limit) {
		return arriveSheetManngerService.queryPtpExpArriveSheetInfoByParams(dto,start, limit);
	}
	
	
	
	/***********************************合伙人快递签收出库end*****************************************/
}