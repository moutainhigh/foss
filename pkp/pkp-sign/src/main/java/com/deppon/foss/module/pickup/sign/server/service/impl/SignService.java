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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.dao.IDopSignDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IDopSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPickupService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRecordErrorWaybillService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignStockJobService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.DeliverHandlerConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.PickupConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.DopSignEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorImportantWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.util.DateCompareUtil;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
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
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
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
public class SignService implements ISignService {
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.SignService";
	
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
	private static final Logger LOGGER = LoggerFactory.getLogger(SignService.class);
	/** 2013-08-01 */
	private static final String dateStr = "2013-08-01";
	
	/** 时间格式 */
	private static final String format = "yyyy-MM-dd";
	
	
	/**
	 * 保价声明价值
	 */
	private static final BigDecimal INSURANCEAMOUNT_NUM = new BigDecimal("10000");

	/**
	 *  签收出库Dao
	 */
	private ISignDao signDao;
	public void setSignDao(ISignDao signDao) {
		this.signDao = signDao;
	}

	/**
	 * dop签收出库Dao
	 */
	private IDopSignDao dopSignDao;
	public void setDopSignDao(IDopSignDao dopSignDao){
		this.dopSignDao=dopSignDao;
	}
	
	/**
	 * 家装service
	 */
	private IDopSignService dopSignService;

	/**
	 *  到达联service
	 */
	private IArriveSheetManngerService arriveSheetManngerService;
	
	/***
	 * 记录异常运单 上报OA的Service
	 */
	private IRecordErrorWaybillService recordErrorWaybillService; 

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
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	/**
	 * service层
	 */
	private  IPickupService pickupService;
	
	/**
	 * 运单Dao
	 */
	private IWaybillDao waybillDao;
	/**
	 * 配置参数service
	 */
	private IConfigurationParamsService configurationParamsService;
	
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
	public List<SignArriveSheetDto> queryArriveSheetInfoByParams(SignDto dto,
			int start, int limit) {
		return arriveSheetManngerService.queryArriveSheetInfoByParams(dto,start, limit);
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
			count = arriveSheetManngerService.queryArriveSheetInfoCountByParams(dto);
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
//			// 调用结清货款接口判断当前运单是否已结清
//			boolean result = repaymentService.paymentOperate(repayment);
//			if (!result) {// 如果未结清
//				LOGGER.error("运单号:"+dto.getWaybillNo()+ "未结清");//记录日志
//				arriveSheetVo.setSignArriveSheetDtoList(null);
//				return arriveSheetVo;
//			}
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
			if(waybillEntity != null && FossConstants.YES.equals(waybillEntity.getIsWholeVehicle()) && dto.getLastLoadOrgCode().equals(waybillEntity.getReceiveOrgCode())
					&& FossConstants.NO.equals(waybillEntity.getIsPassOwnDepartment()))//判断如果当前部门是整车，当前登录部门是收货部门，且不经过到达部门
			{
				//update by foss 231434 2015-7-22
				//对整车(三级)产品，若开单时间在2013-08-01之前(不包含2013-08-01)，不做配载校验
				Date date = DateUtils.convert(dateStr, format);
				//中转接口校验通过
				if(!date.before(waybillEntity.getBillTime()) ||
						vehicleAssembleBillService.queryVehicleAssemblyBillByWaybillNo(dto.getWaybillNo()))
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
			arriveSheetVo.setSignArriveSheetDtoList(this.queryArriveSheetInfoByParams(dto,start, limet));
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
		productCodeList.add(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE);
		productCodeList.add(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT);
		//新增過濾條件  ‘标准快递’和‘3.60特惠件’运单号，商务专递
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
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(dto.getLastLoadOrgCode());
		if (CollectionUtils.isNotEmpty(list)) {//如果查询的结果不为空
			dto.setEndStockOrgCode(list.get(0)); 
			dto.setGoodsAreaCode(list.get(1));// 获取库区
		}
		// 有效
		dto.setActive(FossConstants.ACTIVE);
		return signDao.queryStock(dto);//返回流水号信息
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
			throw new SignException(SignException.WAYBILLNO_NULL);//运单号无效，请在综合查询查询运单状态是否已作废或已终止
		}
		ActualFreightEntity actual = actualFreightService.queryByWaybillNo(entity.getWaybillNo());

		//DMANA-9716 add by chenjunying 2015-03-25  判断限制有投诉变更的运单 不让进行正常签收
		if(actual==null){
			throw new SignException("实际承运表中无该运单信息，请在综合查询确认运单状态");
		}
		if(SignConstants.YES.equals(actual.getComplainStatus()) && 
			SignConstants.NORMAL_SIGN.equals(entity.getSituation())){ 
			//有投诉自动变更，签收不让正常签收
			throw new SignException("投诉自动变更异常签收的运单，只允许做异常签收");
		}
		
		boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
		MutexElement mutexElement = new MutexElement(entity.getArrivesheetNo(), "到达联编号", MutexElementType.RFC_SIGN);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		if(!isLocked){//如果没有得到锁
			LOGGER.error("当前运单操作中，请稍后再试");//记录日志
			throw new SignException(SignException.WAYBILL_LOCKED);//返回错误信息
		}
		boolean isWholeVehicle = false;  //是否(是整车，出发部门是当前登陆部门)
		/**解决整车签收之后轨迹出现到达的问题（毫秒级误差 签收时间比到达时间先new出来。解决：先做到达再new签收时间爱你）*/
		if(waybill.getReceiveOrgCode().equals(currentInfo.getCurrentDeptCode())&&FossConstants.YES.equals(waybill.getIsWholeVehicle())){
			isWholeVehicle = true; //
			try {//zlz---2接送货签收(中转签收？？）
				// 调用中转接口，整车签收时，未到达的车辆统一做到达
				departureService.carLoadArrive(entity.getWaybillNo());
			} catch (BusinessException e) {//捕获异常
				LOGGER.error(e.getMessage(), e);//记录日志
				businessLockService.unlock(mutexElement);//解锁
				throw new SignException(e.getErrorCode(),e);//抛出异常
			}
		}
		Date systemDate = new Date();
		if(WaybillConstants.INNER_PICKUP.equals(waybill.getReceiveMethod())){//提货方式为汽运内部自提
			LOGGER.info("--提货方式为汽运内部自提 不需要调用结算签收接口----");//记录日志
		}else {//zlz---1结算签收start 
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
					//签收前判断该单有没有结清   add by 378375
					if(StringUtils.equals(actual.getSettleStatus(), FossConstants.NO) ){
						throw new SignException("该运单没有结清,不能签收，请先在结清货款内操作");
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
							CUBCSignOrRevSignResultDto resultDto = cUBCSignService.sendSignReqToCUBC(reqDto);
							if(StringUtils.equals(resultDto.getResultMark(), FossConstants.NO)){
								if(StringUtils.isNotBlank(resultDto.getMeg())){
									LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto.getMeg());
									throw new CUBCSignException(resultDto.getMeg());	
								}else{
									throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
								}
							}	
//						}else{
//							LOGGER.info("自提签收,运单号："+entity.getWaybillNo()+",部分签收,不调CUBC签收接口");
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
		}//zlz---1结算签收end
		if(isWholeVehicle){//是整车
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
					try {//zlz--3中转签收出库
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
		entity.setReceiveMethod(ArriveSheetConstants.SIGN_RECEIVE_METHOD_PICKUP);//提货方式--自提
		WaybillSignResultEntity wayEntity=null;
		try {//zlz---4判断运单是否之前录入过
			wayEntity = waybillSignResultService.checkWaybillSignResultSign(entity,waybill,systemDate,currentInfo);
		} catch (SignException e) {//捕获异常
			businessLockService.unlock(mutexElement);//解锁
			throw new SignException(e.getErrorCode(),e);//抛出异常
		}
		// 不是PDA签收
		wayEntity.setIsPdaSign(FossConstants.NO);
		// 添加运单签收结果信息
		waybillSignResultService.addWaybillSignResult(wayEntity);
		//菜鸟轨迹 add by 231438 zlz---5推送菜鸟
		//封装的轨迹方法 赋值，判断是否符合条件 调用中转的轨迹接口
		sendWaybillTrackService.rookieTrackingsForSign(wayEntity);
		//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，推送轨迹--add by 231438 快递100轨迹推送 
		sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
		
		//上报QMS内物短少差错(签收情况为异常内物短少 或 同票多类异常) 
		if(StringUtils.isNotEmpty(entity.getSituation())
				&&(entity.getSituation().equals(ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT)
						||entity.getSituation().equals(ReportConstants.SIGN_SITUATION_UNNORMAL_SAMEVOTEODD))){
			this.saveRecordShortErrorInfo(list,entity);
			}else{
				//保存QMS重大货物异常差错信息(签收情况为异常，保价金额>=10000 )
				if(StringUtils.isNotEmpty(entity.getSituation())&& (waybill.getInsuranceAmount().compareTo(INSURANCEAMOUNT_NUM)!=-1)){
					this.saveRecordImportantErrorInfo(list,entity,waybill);
				}
			}
		
		//update
		PickupResultDto pickupResultDto = pickupService.isExistByWaybill(entity.getWaybillNo());
		if(pickupResultDto!=null&&!pickupResultDto.getState().equals(PickupConstants.GOOD_STATE_REVOCATION)){
			if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){//如果签收状态为全部签收
				//已全部签收
				pickupResultDto.setState(PickupConstants.GOOD_STATE_ALLSIGN);
			}else if(SignConstants.SIGN_STATUS_PARTIAL.equals(wayEntity.getSignStatus())){//如果签收状态为部分签收
				//已部分签收
				pickupResultDto.setState(PickupConstants.GOOD_STATE_PARTSIGN);
			}
			WaybillEntity rstWaybill = waybillDao.queryWaybillByNo(entity.getWaybillNo());
			updateDataInit(pickupResultDto, rstWaybill, PickupConstants.IS_SIGN_STATE);
			pickupService.updatePickupStateByPickupResultDto(pickupResultDto);
		}
		//update   zlz---6调用CRM修改订单接口更新订单状态
		if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){//如果签收状态为全部签收
			waybillSignResultService.updateCrmOrder(orderNo, currentInfo, wayEntity);
		}
		// 修改到达联签收信息。到达联状态为 签收SIGN zlz---7修改到达联签收信息
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
		//如果签收件数小于运单开单件数  zlz---8
		if(entity.getSignGoodsQty() != null &&entity.getSignGoodsQty()<waybill.getGoodsQtyTotal()){
			List<String> serials = new ArrayList<String>();
			for (SignDetailEntity signDetail : list){
				serials.add(signDetail.getSerialNo());
			}
			todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),serials);
		}else {
			todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),null);
		}
		//zlz---9发短信
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
		}
		//应用监控签收调用//zlz---10
		waybillSignResultService.signCounterByWaybillNo(currentInfo, waybill);
		if(isSendInvoiceInfo){
			//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
			waybillSignResultService.sendInvoiceInfo(waybill,actual);
		}
		businessLockService.unlock(mutexElement);//解锁
		LOGGER.info("提交录入的签收信息结束");//记录日志
		
		if(StringUtils.isNotEmpty(actual.getSpecialValueAddedServiceType())&&
				actual.getSpecialValueAddedServiceType().contains("_EQUIP")){//判断家装运单 add 269871 zhuliangzhi 2015-12-23
			//处理DOP签收后的运单（先将暂存表里的数据存储到dop签收日志记录表里
			dealSignWaybill(entity.getWaybillNo(),entity.getSituation());
		}else {
			LOGGER.info("运单："+entity.getWaybillNo()+"  不是家装运单");
		}
		
		return resultMsg;
	}
	
	/**
	 * DOP传来的客户签收运单信息在FOSS系统进行签收后的操作
	 * {先将暂存表里的数据存储到dop签收日志记录表里，然后删除暂存表里对应的记录}
	 * @param waybillNo
	 */
	@Transactional
	public void dealSignWaybill(String waybillNo,String situation){
		if(!StringUtils.isEmpty(waybillNo)){
			//查询是不是DOP传来的家装信息
			SignDto signDto=new SignDto();
			signDto.setWaybillNo(waybillNo);
			
			DopSignEntity dopSignEntity =queryDopByParams(signDto);
			if(dopSignEntity!=null){//在DOP传来的暂存表里有记录
				try{
					//运单签收的时候若是DOP传来的家装运单，则保存到签收日志记录表里
					int i=dopSignDao.insertDopSign(dopSignEntity);
					if(i==0){
						LOGGER.info("保存dop传来的签收信息到签收日志表失败！运单号为： "+waybillNo);//记录日志
					}else{
						LOGGER.info("保存dop传来的签收信息到签收日志表成功！运单号为： "+waybillNo);//记录日志
						//删除家装cache表里的数据
						int num=dopSignDao.deleteDopCache(waybillNo);
						if(num>0){
							LOGGER.info("删除暂存dop传来的签收信息成功，运单为： "+waybillNo);//记录日志
						}
						//FOSS中的实际签收信息返回给DOP add zhuliangzhi 2015-12-15
						dopSignService.sendDopSignMessage(waybillNo,situation);
					}
				}catch(Exception e ){
					LOGGER.error(e.getMessage(), e);//记录日志
					throw new RuntimeException("家装运单签收时，执行SQL错误。运单号："+waybillNo);
				}
			}else{
				//反签收后的运单信息后返回给DOP签收信息 add zhuliangzhi 2015-12-15
				dopSignService.sendDopSignMessage(waybillNo,situation);
			}
		}else {
			throw new SignException(SignException.WAYBILLNO_NULL);//运单号无效，请在综合查询查询运单状态是否已作废或已终止
		}
	}
	
	/**
	 * 根据信息查询暂存dop传来数据的表（t_SrV_Dopcache）
	 * @author 269871 foss-zhuliangzhi
	 * @date 2015-09-10 上午10:14:34
	 */
	public DopSignEntity queryDopByParams(SignDto dto){
			if(dto!=null){
				String waybillNo = dto.getWaybillNo();
				if(StringUtils.isNotEmpty(waybillNo)){
					List<DopSignEntity> dtos=dopSignDao.queryDopListByWaybillNo(dto);
					if(dtos.isEmpty()){
						return null;
					}
					return dtos.get(0);
				}

			}else{
			throw new SignException("签收参数为空！");//抛出异常
			}
		return null;
	}
	/**
	 * 更新提前找货的状态
	 * @date 2014-11-24 下午6:45:19
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.SignService#updateInadvanceGoodsBySign(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto)
	 */
	@Override
	@Transactional
	public String updateInadvanceGoodsBySign(SignDto signDto) {
	  if(signDto!=null){
		  String waybillNo = signDto.getWaybillNo();
		  if(StringUtils.isNotEmpty(waybillNo)){
			  WaybillEntity waybill = waybillManagerService.queryPartWaybillByNo(waybillNo);
			  if(waybill!=null){
				  if(FossConstants.YES.equals(waybill.getIsWholeVehicle())){//是整车运单
					  //是整车环境
					  return PickupConstants.CURRENT_WAYBILL_NO_GOODS_INADVANCE;
				  }
			  }
			//校验未受理的更改单
			SignRfcEntity newEntity=new SignRfcEntity();
			newEntity.setWaybillNo(waybillNo);
			newEntity.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
			if(signChangeService.isRfc(newEntity)){
				return PickupConstants.CURRENT_WAYBILL_NO_GOODS_INADVANCE;
			}
			//查询该条运单是否存在
			PickupResultDto rstPickupResultDto = pickupService.isExistByWaybill(waybillNo);
			WaybillEntity rstWaybill = waybillDao.queryWaybillByNo(waybillNo);
			if(rstPickupResultDto==null){
				rstPickupResultDto=new PickupResultDto();
				rstPickupResultDto=updateDataInit(rstPickupResultDto,rstWaybill,PickupConstants.PICKUP_INSERT);
				pickupService.insertEntity(rstPickupResultDto);
			}else{
				//数据实时更新
				if(StringUtils.isNotEmpty(rstPickupResultDto.getState())&&rstPickupResultDto.getState().equals(PickupConstants.GOOD_STATE_ALLSIGN)){
					return PickupConstants.CURRENT_WAYBILL_NO_GOODS_INADVANCE;
				}else if(StringUtils.isNotEmpty(rstPickupResultDto.getState())&&
						rstPickupResultDto.getState().equals(PickupConstants.GOOD_STATE_HASINFORM)){
					  return PickupConstants.CURRENT_WAYBILL_ALREADY_GOODS_INADVANCE;
				}else{
					rstPickupResultDto=updateDataInit(rstPickupResultDto,rstWaybill,null);
					pickupService.updatePickupStateByPickupResultDto(rstPickupResultDto);
				}
			}
		  }
	  }
		return PickupConstants.OPERATE_SUCCESS;
	}
	
	
	
	/**
	 * 初始化符合条件的插入数据
	 * @date 2014-11-24 下午6:45:19
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.SignService#updateDataInit(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto)
	 */
	private PickupResultDto updateDataInit(PickupResultDto rstPickupResultDto,WaybillEntity rstWaybill,String state) {
		if(rstWaybill!=null){
			rstPickupResultDto.setBillingGoodsQty(rstWaybill.getGoodsQtyTotal());
			rstPickupResultDto.setGoodPackage(rstWaybill.getGoodsPackage());
			rstPickupResultDto.setGoodSize(rstWaybill.getGoodsSize());
			rstPickupResultDto.setGoodVolume(rstWaybill.getGoodsVolumeTotal());
			rstPickupResultDto.setGoodWeight(rstWaybill.getGoodsWeightTotal());
			rstPickupResultDto.setOperTime(new Date());
			String pickUpState = rstPickupResultDto.getState();
			if(StringUtils.isNotEmpty(state)&&state.equals(PickupConstants.IS_SIGN_STATE)){
				if(StringUtils.isNotEmpty(pickUpState)){
					if(pickUpState.equals(PickupConstants.GOOD_STATE_ALLSIGN)||pickUpState.equals(PickupConstants.GOOD_STATE_PARTSIGN)){
						//不操作
					}
				}
			}else{
				rstPickupResultDto.setState(PickupConstants.GOOD_STATE_HASINFORM);
			}
			if(StringUtil.isNotEmpty(state)&&StringUtils.equals(state, PickupConstants.PICKUP_INSERT)){//插入操作
				rstPickupResultDto.setWaybillNo(rstWaybill.getWaybillNo());
				rstPickupResultDto.setId(UUIDUtils.getUUID());
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				rstPickupResultDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
				rstPickupResultDto.setOperateOrgName(currentInfo.getCurrentDeptName());
				rstPickupResultDto.setOperateUserName(currentInfo.getEmpName());
				rstPickupResultDto.setOperateUserCode(currentInfo.getEmpCode());
				rstPickupResultDto.setState(PickupConstants.GOOD_STATE_HASINFORM);
				List<String> endStockOrgCodes = handleQueryOutfieldService.getEndStockCodeAndAreaCode(currentInfo.getCurrentDeptCode());
				// 获取对应的营业部或驻地外场
				if(CollectionUtils.isNotEmpty(endStockOrgCodes) && StringUtils.isNotEmpty(endStockOrgCodes.get(0))){
					rstPickupResultDto.setEndStockOrgCode(endStockOrgCodes.get(0));
				}else {
					rstPickupResultDto.setEndStockOrgCode(DeliverHandlerConstants.DEFAULT_TRANSFER_CENTER);
				}
				return rstPickupResultDto;
			}
		}
		return rstPickupResultDto;
	}

	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.server.service.impl.SignService.recordErrorWaybill
	 * @author: foss-yuting
	 * @description: foss记录内物短少差错 上报QMS
	 * @date:2014年12月5日 下午15:59:21
	 *   update by foss-bieyexiong 2015-06-23
	 */
	private void saveRecordShortErrorInfo(List<SignDetailEntity> list,ArriveSheetEntity entity){
		LOGGER.info("*************保存上报QMS内物短少差错信息***********start");// 记录日志
		RecordErrorWaybillDto recordErrorWaybillDto=new RecordErrorWaybillDto();
		if(entity!=null && CollectionUtils.isNotEmpty(list)){
			//主键
			recordErrorWaybillDto.setId(UUIDUtils.getUUID());
			//是否上报(Y:未上报)
			recordErrorWaybillDto.setActive(SignConstants.YES);
			//运单号
			recordErrorWaybillDto.setWaybillNo(entity.getWaybillNo());
			//短少量
			recordErrorWaybillDto.setAlittleShort(entity.getAlittleShort());
			//外包装是否完好
			recordErrorWaybillDto.setPackingResult(entity.getPackingResult());
			//创建时间
			recordErrorWaybillDto.setCreateTime(new Date());
			//修改时间
			recordErrorWaybillDto.setModifyTime(new Date());
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//上报人名字
			recordErrorWaybillDto.setOperateName(currentInfo.getEmpName());
			//上报人工号
			recordErrorWaybillDto.setOperateCode(currentInfo.getEmpCode());
			//上报人所在部门编码
			recordErrorWaybillDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			//上报人所在部门名称
			recordErrorWaybillDto.setOperateOrgName(currentInfo.getCurrentDeptName());
				StringBuffer sbuffer=new StringBuffer();
			//遍历签收流水号，取出内物短少流水号
				for (SignDetailEntity signDetail : list){
				//当流水号签收情况为 '内物短少'时，添加进strbuff字符串
				if(ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT.equals(signDetail.getSituation())){
					//以','分割
						sbuffer.append(signDetail.getSerialNo()).append(SignConstants.SPLIT_CHAR);
					}
				}
				if(StringUtils.isNotEmpty(sbuffer.toString())){
				//将最后一个','截断
					String serialNos=sbuffer.toString().substring(0, sbuffer.toString().length()-1);
					if(StringUtils.isNotEmpty(serialNos)){
					//流水号
						recordErrorWaybillDto.setSerialNo(serialNos);
						recordErrorWaybillService.recordErrorWaybillReportOA(recordErrorWaybillDto);
					}
				}
			}
		LOGGER.info("*************保存上报QMS内物短少差错信息***********end");// 记录日志
		}
	/**
	 * @author 306548-foss-honglujun
	 * foss记录重大货物异常自动上报信息 OA
	 * PdaDeliverSignDto dto,ArriveSheetEntity entity,CurrentInfo currentInfo
	 */
	private void saveRecordImportantErrorInfo(List<SignDetailEntity> list,ArriveSheetEntity entity,WaybillEntity waybill){
		//记录日志
		LOGGER.info("*************保存QMS重大货物异常差错信息***********start");
		if(entity!=null && CollectionUtils.isNotEmpty(list)&&waybill!=null){
			RecordErrorImportantWaybillDto recordErrorImportantWaybillDto = new RecordErrorImportantWaybillDto();
			//主键
			recordErrorImportantWaybillDto.setId(UUIDUtils.getUUID());
			//是否上报(Y:未上报)
			recordErrorImportantWaybillDto.setActive(SignConstants.YES);
			//运单号
			recordErrorImportantWaybillDto.setWaybillNo(entity.getWaybillNo());
			//保价声明价值
			recordErrorImportantWaybillDto.setInsuranceAmount(waybill.getInsuranceAmount());
			//创建时间
			recordErrorImportantWaybillDto.setCreateTime(new Date());
			//修改时间
			recordErrorImportantWaybillDto.setModifyTime(new Date());
			//开单时间
			recordErrorImportantWaybillDto.setBillTime(waybill.getBillTime());
			//获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
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
	 * Sets the 签收明细service.
	 *
	 * @param signDetailService the new 签收明细service
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
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
	
	public void setPickupService(IPickupService pickupService) {
		this.pickupService = pickupService;
	}
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
	
	/**
	 * 根据到达联号和流水号查询SignDetailEntity
	 * @date 2014-12-27 下午3:58:10
	 * @param dto
	 * @return 
	 */
	@JSON
	public List<SignDetailEntity> signSituationByArrivesheetNoSerialNo(SignDetailEntity signDetailEntity){
		return signDetailService.querySignDetailByParams(signDetailEntity);
	}
	 
	public void setRecordErrorWaybillService(
			IRecordErrorWaybillService recordErrorWaybillService) {
		this.recordErrorWaybillService = recordErrorWaybillService;
	}
	/**
	 * 处理异常查询库区
	 * @author 231438-foss-chenjunying
	 * @param SignDto
	 * @return List<StockDto>
	 * 2015-08-08
	 */
	@Override
	public List<StockDto> dealExceptionQueryStock(SignDto dto) {
		//如果传入的对象为空
		if (dto == null){
			return null;
		}
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(dto.getLastLoadOrgCode());
		if (CollectionUtils.isNotEmpty(list)) {
			List<String> ld=new ArrayList<String>();
			ld.add(list.get(1));
			ld.add(list.get(2));
			dto.setEndStockOrgCode(list.get(0)); //最终库存部门编码
			dto.setGoodsAreaCodes(ld);// 获取库区
		}
		// 有效
		dto.setActive(FossConstants.ACTIVE);
		return signDao.queryStock(dto);//返回流水号信息
	}
	/**
	 * 家装service注入
	 * @param dopSignService
	 */
	@Resource
	public void setDopSignService(IDopSignService dopSignService) {
		this.dopSignService = dopSignService;
	}
	
	 //////////////////////////////////////////////////////////////合伙人签收相关-start//////////////////////////////////////////////////////////////////
	
	
	/**
	 * 设置签收出库--
	 * 		查询条件 前置条件(到达联状态为生成, 到达联active有效,不按运单号查时(运单必须已结清货款))
	 *      查询条件中单号、到达联编号、收货人、收货人手机、收货人电话具有排他性 
	 *      优先级按单号、到达联编号、收货人、收货人手机、收货人电话
	 * @author foss-239284
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
	private void initPtpSignQuery(SignDto dto) {
		// 到达联状态为生成
		dto.setArriveSheetstatus(new String[] {ArriveSheetConstants.STATUS_GENERATE});  
		// 运输性质 （偏线和空运）
		List<String> productCodeList = new ArrayList<String>();
		productCodeList.add(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE);
		productCodeList.add(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT);
		//新增過濾條件  ‘标准快递’和‘3.60特惠件’运单号，商务专递
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
	    	try {
	    		SimpleDateFormat sdf = new SimpleDateFormat(SignConstants.PTP_INIT_DATE_410);
				long intTime = sdf.parse(configString.trim()).getTime();
				dto.setConBillTime(new Date(intTime));
			} catch (ParseException e) {
				dto.setConBillTime(null);
			}
		} else {
			dto.setConBillTime(null);
		}
		//合伙人过滤操作整车运单(SQL条件中)
		dto.setIsWholeVehicle(FossConstants.NO);
		//合伙人签收410初始化数据-----------end----------239284
		
	}
	
	
	/**
	 * 根据条件查询到达联信息（包含入库时间）-239284
	 * @param dto
	 * @param start
	 * @param limet
	 * @return
	 */
	public ArriveSheetVo queryPtpArriveSheetByParams(SignDto dto,int start,int limet){
		ArriveSheetVo arriveSheetVo = new ArriveSheetVo();
		arriveSheetVo.setTotalCount(Long.valueOf(SignConstants.ZERO));//默认值 为0
		this.initPtpSignQuery(dto);//加载传入的信息
		// 如果运单号不为空
		if (StringUtil.isNotBlank(dto.getWaybillNo())) {
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
			if(waybillEntity != null && FossConstants.YES.equals(waybillEntity.getIsWholeVehicle()) && dto.getLastLoadOrgCode().equals(waybillEntity.getReceiveOrgCode())
					&& FossConstants.NO.equals(waybillEntity.getIsPassOwnDepartment()))//判断如果当前部门是整车，当前登录部门是收货部门，且不经过到达部门
			{
				//update by foss 231434 2015-7-22
				//对整车(三级)产品，若开单时间在2013-08-01之前(不包含2013-08-01)，不做配载校验
				Date date = DateUtils.convert(dateStr, format);
				//中转接口校验通过
				if(!date.before(waybillEntity.getBillTime()) ||
						vehicleAssembleBillService.queryVehicleAssemblyBillByWaybillNo(dto.getWaybillNo()))
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
		arriveSheetVo.setTotalCount(this.queryPtpArriveSheetInfoCountByParams(dto));
		// 如果存在信息
		if (arriveSheetVo.getTotalCount() != null && arriveSheetVo.getTotalCount() >SignConstants.ZERO ) {
			arriveSheetVo.setSignArriveSheetDtoList(this.queryPtpArriveSheetInfoByParams(dto,start, limet));
		}else {
			arriveSheetVo.setSignArriveSheetDtoList(null);
		}
		return arriveSheetVo;
	}
	
	/**
	 * 合伙人签收出库---根据条件分页查询到达联总数
	 * @author foss-239284
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
	public Long queryPtpArriveSheetInfoCountByParams(SignDto dto){
		Long count = Long.valueOf(SignConstants.ZERO);//默认值 为0
		try {
			count = arriveSheetManngerService.queryPtpArriveSheetInfoCountByParams(dto);
			return count; //返回查询总数量
		} catch (RepaymentException e) {//捕获异常
			//记录日志
			LOGGER.error(e.getErrorCode(),e);
			// 异常处理
			throw new SignException(e.getErrorCode(),e);
		}
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
	public List<SignArriveSheetDto> queryPtpArriveSheetInfoByParams(SignDto dto,
			int start, int limit) {
		return arriveSheetManngerService.queryPtpArriveSheetInfoByParams(dto,start, limit);
	}
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	 //////////////////////////////////////////////////////////////合伙人签收相关-end//////////////////////////////////////////////////////////////////
	
}