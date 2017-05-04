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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/DeliverHandlerService.java
 * 
 * FILE NAME        	: DeliverHandlerService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 *****************************************************************************
 *
 *修订记录 
日期 	
修订内容 
	修订人员 
		版本号 
2012-06-16 	
	新增	赵斌	V0.1
2012-06-29	
加入验货签收部分内容，
包含了派送拉回后的签收录入流程	赵斌	V0.2
 2012-07-18
 根据ITA审核进行修改	赵斌	V0.3
  2012-10-12
  	  补充银行卡付款方式	  赵斌	 V1.0
2012-12-5	
按照ISSUE-1014进行用例修改	赵斌
	V1.01
1.	
	SUC-468-派送处理
1.1	
	相关业务用例
BUC_FOSS_5.50.10_550
  	客户货物签收
1.2	
	用例描述
司机送完货后，
回到派送部，
把签收联交予营业员，
营业员把签收联上的签收信息录入系统，
并告知相关人和部门。
1.3	
用例条件
条件类型	
描述	
引用系统用例
前置条件	
派送准备	
SUC-561 打印到达联（派送准备）
后置条件
	处理派送异常	SUC-488  处理异常
SUC-54分配、
修改交接单、配载单
1.4
	操作用户角色
操作用户
	描述
营业员	录入签收或拉回货物信息，
进行派送签收或卸车
1.5	
界面要求
1.5.1
	表现方式
Web页面 
1.5.2
	界面原型
1.5.3	
	界面描述
录入拉回货物
1.	
	录入拉回货物界面分为4部分：
	待处理运单列表、
	查询条件、
	财务信息、
	签收信息
1.1
	待处理包括运单、
	到达联编号字段列表以及送货确认按钮
1.2
	查询条件包括查询条件、
	查询、
	重置按钮：
a)	
	派送单号：建立派送任务时产生的单号	
1.3	
	财务信息：
	实付运费、
	收款总额、
	代收货款、
	付款方式、
	应收代收款、
	已收代收款、
	应收到付款、
	已收到付款
1.4	
	签收信息：
	签收情况、
	拉回原因、
	签收时间、
	到达联编号、
	总件数、
	签收件数、
	签收人、
	是否PDA

1.6
	操作步骤
序号	基本步骤
	相关数据
		补充步骤
1	
	营业员点击派送处理功能模块，进入此界面		

2	
	输入派送单号后点击“查询”按钮	【查询条件】	

3	
	点击左边待处理列表选择某一条单号	
		参见业务规则SR3-SR6；
4	
	录入签收信息后点击“确认”按钮	
		参见业务规则SR7；
5	
	点击“送货确认”按钮		
	参见业务规则SR8；

扩展事件写非典型或异常操作过程

序号	扩展事件	相关数据	备注
2a	
	查询条件为空		
	提示:“查询条件不能为空！”
2b	
	查询结果为空	
		提示:“查询结果为空！”


	

4a	

	没有选择签收情况就点击签收确认	
		提示：“签收信息不完整，请填写完整！”
4b
	签收人为空	
		提示：“签收人不能为空，请填写后再行签收！”
5a	
	还有到达联没有进行签收确认，就点击“送货确认”按钮	
		提示：“单号为XXXXXXXX的运单还没有进行签收确认，不能生成送货交接列表！”

1.7	
	业务规则
序号	描述
SR1	a)	

SR2	
	查询条件：
a)	
b)	
	查询条件根据派送单查询时需要加当前登录部门等于运单的最终配载部门条件限制。
	待处理里有记录时，
	送货确认按钮可编辑，
	否则不可编辑。
SR3	
a)
	有PDA签收，则默认界面带入PDA签收时所选的签收情况、
	签收时间、
	总件数和签收件数，
	然后营业员在系统中录入到达联上面的签收人；
b)	
	无PDA签收，营业员需要录入签收情况、签收时间、签收件数以及签收人；
	当签收情况不为货物拉回时,拉回原因不可见，签收备注可见且可编辑。
	签收信息里如果有PDA签收并已经录入签收人时、无PDA签收并且已经签收时，
	加载签收信息且签收信息所有控件不可编辑。
SR4	
a)
	签收情况选择为正常签收、
	异常-弃货、
	异常-丢货、
	异常违禁品或异常-一般异常时，
	签收件数显示为到达联件数且不可编辑
b)	
	签收情况选择为部分签收时，
	签收件数显示为到达联件数且可编辑
c)	
	签收情况选择为货物拉回时，拉回原因可编辑
SR5	
	部分签收录入签收件数校验规则：
	签收件数不能大于等于到达联件数
SR6	
	签收时间默认为当前系统时间，
	营业员可修改时分秒，
	不可修改日期
SR7	
	确认签收后，若付款方式为现金或银行卡付款，
	则调用财务 结清账款现金接口、
	运单签收接口进行财务结算；
SR8
	当该派送单中所有的运单到达联都完成了签收确认之后，
	点击待处理列表中送货确认
	，生成送货交接列表。
	送货确认成功后，
	需要调用派送单接口修改派送单状态为'已签收确认'。

1.8	
	数据元素
1.8.1	
输入：查询条件
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
派送单号	建立派送任务时产生的单号	文本		20	否	

1.8.2
输入：签收情况
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
签收情况	实际货物派送签收情况	下拉框		10	是	
包括正常签收、
异常-破损、
异常-潮湿、
异常-污染、
异常-其他、
部分签收，
货物拉回，
默认是正常签收
签收备注	签收备注	文本			是	
拉回原因	货物拉回的原因	下拉框		50	

是	
1.司机晚送；
2.付款问题；
3.无法卸货；
4.区域车辆禁行；
5.发票问题拒收；
6.车辆故障/事故；
7.客户要求更改地址；
8.客户不在无人收货；
9.排单有误，线路安排不对；
10.开单地址有误/电话录入错误；
11.因货物破损/件数不符等原因客户拒收；
12.客户要求开箱验货/送货上楼协商未果；
13.其他
签收时间	客户签收时间	日期选择		20	是	
PDA	是否PDA签收	复选框		5	是	
到达联编号	到达联生成编号	文本		20	是	
总件数	到达联总件数	文本（数字）		5	是	
签收件数	客户实际签收件数	文本（数字）		5	是	
签收人	签收人	文本		20	是	

1.8.3	输出：财务信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
应收代收款		文本（数字）			否	默认0
已收代收款		文本（数字）			否	默认0
已收到付款		文本（数字）			否	默认0
应收到付款		文本（数字）			否	默认0
代收货款		文本（数字）			是	默认为0
付款方式		下拉框			是	
								数据字典，
								包括现金、
								临欠、
								月结、
								银行卡、
								支票、
								电汇，
								默认是现金
实付运费		文本（数字）			是	
收款总额		文本（数字）			否	

1.9	
非功能性需求
使用量	100W*50% = 50W票派送货物
2012年全网估计用户数	
10000用户
响应要求（如果与全系统要求 不一致的话）	
3s内响应
使用时间段	
营业部上班时间
高峰使用时间段
	9:00-12:00 
	 14:00-20:00


1.10	
接口描述：
接口名称 	
对方系统（外部系统或内部其他模块）
接口描述
付款结清	
Foss结算子系统	
货款结清操作将调用财务数据生成接口生成财务数据
运单签收	
Foss结算子系统	结算收入统计报表
 **/
package com.deppon.foss.module.pickup.sign.server.service.impl;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverHandoverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillNewService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsDeliverService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillReturnDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.dao.IDeliverHandlerDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPdaRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRecordErrorWaybillService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.DeliverHandlerConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorImportantWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.DeliverHandlerException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.util.DateCompareUtil;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWeixinSendService;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WeixinConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WeixinSendDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCQueryReceivableAmountService;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICUBCSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCSignOrRevSignResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FOSSSignOrRevSignRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCSignException;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 派送处理
 * 		service实现
 * 司机送完货后，回到派送部，把签收联交予营业员，
 * 营业员把签收联上的签收信息录入系统，
 * 并告知相关人和部门
 * @author 
 * 			foss-meiying
 * @date 
 * 			2012-11-28 上午11:54:33 
 * @since
 * @version
 */
public class DeliverHandlerService implements IDeliverHandlerService {
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.DeliverHandlerService";
	
	private ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService;
	public void setCubcQueryReceivableAmountService(
			ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService) {
		this.cubcQueryReceivableAmountService = cubcQueryReceivableAmountService;
	}	
	/**
	 * add by 353654  注入CUBC签收服务
	 */
	private ICUBCSignService cUBCSignService;
	
	public void setcUBCSignService(ICUBCSignService cUBCSignService) {
		this.cUBCSignService = cUBCSignService;
	}
	
	/**
	 * 记录 
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeliverHandlerService.class);
	
	/**
	 * 保价声明价值
	 */
	private static final BigDecimal INSURANCEAMOUNT_NUM = new BigDecimal("10000");

	/** 
	 * 部门查询起始. 
	 */
	private static final int BEGIN_NUM = 0;
	/** 
	 * 部门查询起始. 
	 */
	private static final int PAGE_NUM = 100;
	/**
	 *  派送处理Dao
	 *  接口
	 */
	private IDeliverHandlerDao deliverHandlerDao;
	/**
	 * 到达联
	 * Service接口
	 */
	private IArriveSheetManngerService arriveSheetManngerService;
	/**
	 * 运单签收结果
	 * Service接口 
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 *  交接
	 *  Dao接口
	 */
	private IStayHandoverDao stayHandoverDao;
	/**
	 *  结清货款Service
	 */
	private IRepaymentService repaymentService;
	
	/**
	 * 交单Service
	 */
	private IDeliverHandoverbillService deliverHandoverbillService;
	/**
	 * 结算签收
	 * Service接口
	 */
	private ILineSignService lineSignService;
	/**
	 *  结算
	 *  应收单服务接口
	 */
	private IBillReceivableService billReceivableService;
	/**
	 *  运单状态
	 *  服务接口
	 */
	private IActualFreightService actualFreightService;
	/**
	 * IDeliverLoadTaskService
	 */
	private IDeliverLoadTaskService deliverLoadTaskService;
	/**
	 *  签收明细
	 *  service接口
	 */
	private ISignDetailService signDetailService;
	/**
	 * 交接 流水号service
	 */
	private IStayHandoverserialService stayHandoverserialService;
	/**
	 * 交接明细Service
	 */
	private IStayHandoverDetailService stayHandoverDetailService;
	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 处理异常
	 * 主数据Service层
	 */
	private IExceptionProcessService exceptionProcessService;
	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 查询外场服务 
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	/**
	 * 代办事项服务类
	 */
	private ITodoActionService todoActionService;	
	/**
	 * IDeliverbillNewService
	 */
	private IDeliverbillNewService deliverbillNewService;
	/**
	 * 营业部车队对应
	 */
	private ISalesMotorcadeService salesMotorcadeService;
	private IActualFreightDao actualFreightDao;
	/**
	 *  中转出库接口
	 */
	private IStockService stockService;
	/** 
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private static final String  PRIORITY= "FAST";
	
	private IWeixinSendService weixinSendService;
	
	/**
	 * 
	 */
	private IGpsDeliverService gpsDeliverService;
	
	/***
	 * 记录异常运单 上报OA的Service
	 */
	private IRecordErrorWaybillService recordErrorWaybillService; 
	
	public void setGpsDeliverService(IGpsDeliverService gpsDeliverService) {
		this.gpsDeliverService = gpsDeliverService;
	}
	
	/** 
	 * “公司司机”的数据库对应数据访问Service接口
	 */
	private IOwnDriverService ownDriverService;
	
	/**
	 * 人员接口
	 */
	private IEmployeeService employeeService;
	/**
	 * 人员接口
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	/** 
	 * “外请车司机”的数据库对应数据访问
	 */
	private ILeasedDriverService leasedDriverService;
	/**
	 * 派送单状态更新记录表Service 
	 */
	private IDeliverBillVaryStatusService deliverBillVaryStatusService;
	/**
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	private ICommonExpressVehicleService commonExpressVehicleService;
	/**
	 * POS刷卡管理service
	 */
	private IPdaRepaymentService pdaRepaymentService;
	private IPdaPosManageService pdaPosManageService;
	private IRepaymentDao repaymentDao;
	public void setPdaRepaymentService(IPdaRepaymentService pdaRepaymentService) {
		this.pdaRepaymentService = pdaRepaymentService;
	}

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}

	public void setRepaymentDao(IRepaymentDao repaymentDao) {
		this.repaymentDao = repaymentDao;
	}

	public void setCommonExpressVehicleService(
			ICommonExpressVehicleService commonExpressVehicleService) {
		this.commonExpressVehicleService = commonExpressVehicleService;
	}
	
	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}
	
	public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
		this.leasedDriverService = leasedDriverService;
	}
	
	private IDeliverbillDao deliverbillDao;
	
	public void setDeliverbillDao(IDeliverbillDao deliverbillDao) {
		this.deliverbillDao = deliverbillDao;
	}
	
	public void setWeixinSendService(IWeixinSendService weixinSendService) {
		this.weixinSendService = weixinSendService;
	}
	public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}
	/**
	 * 派送处理
	 * 		 根据条件查询运单编号
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:13:21
	 * @param dto
	 * 		 dto.id
	 * 			派送单id
	 * 		dto.deliverbillNo
	 * 			派送单编号
	 * 		dto.waybillNo
	 * 			运单号
	 * 		dto.arrivesheetNo
	 * 			到达联编号
	 * 		dto.vehicleNo
	 * 			车牌号
	 * 		dto.driverCode
	 * 			司机工号
	 * 		dto.deliverymanName
	 * 			签收人
	 * 		dto.active
	 * 			到达联是否有效 
	 * 		dto.deliverStatus
	 * 			派送单状态
	 * 		dto.lastLoadOrgCode
	 * 			最终配载部门（判断是否为本部门）
	 * 		dto.createUserName
	 * 			创建人
	 * 		dto.createUserCode
	 * 			创建人编码
	 * 		dto.createTime
	 * 			创建时间
	 * 		dto.ids
	 * 			派送单id集合
	 * 		dto.deliverbillNos
	 * 			派送单编号集合
	 * 		dto.destroyed
	 * 			到达联是否作废
	 * 			派送单编号
	 * 		dto.tSrvStayHandoverId
	 * 			交接id
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService
	 * #queryDeliverbillWaybillNo(com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto)
	 */
	@Override
	public List<DeliverbillDetailDto> queryDeliverbillWaybillNo(DeliverbillDetailDto dto) {
		if (dto != null) {//如果传入的参数不为空
			OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
			if(org==null){
				return null;
			}
			String isSale = org.getSalesDepartment();
			List<String> orgIdList = new ArrayList<String>();
			if (FossConstants.YES.equals(isSale))
			{
				//如果是营业部派送部 则通过营业部-车队对应关系表获取车队code
				SalesMotorcadeEntity entity = new SalesMotorcadeEntity();
				entity.setSalesdeptCode(FossUserContextHelper.getOrgCode());
				List<SalesMotorcadeEntity> salesMotorcadeList = salesMotorcadeService.querySalesMotorcadeExactByEntity(entity, BEGIN_NUM, PAGE_NUM);
				if (!CollectionUtils.isEmpty(salesMotorcadeList))
				{
					for (SalesMotorcadeEntity salesMotorcadeEntity : salesMotorcadeList) 
					{
						orgIdList.add(salesMotorcadeEntity.getMotorcadeCode());
					}
					dto.setOrgIdList(orgIdList);
				}else
				{	
					orgIdList.add(FossUserContextHelper.getOrgCode());
					dto.setOrgIdList(orgIdList);
				}
			}else {
				return null;
			}/* else
			{
				// 获取当前组织对应车队
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.getTopFleetByCode(FossUserContextHelper.getOrgCode());
				if(orgAdministrativeInfoEntity != null)
				{
					orgIdList.add(orgAdministrativeInfoEntity.getCode());
					dto.setOrgIdList(orgIdList);
				}else
				{
					orgIdList.add(FossUserContextHelper.getOrgCode());
					dto.setOrgIdList(orgIdList);
				}
			}*/
			List<String> subOrgCodeList = new ArrayList<String>();
			for (String string : orgIdList) {
				//根据IBM-罗越决议 找顶级车队下所有子组织code
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(string);
				if (CollectionUtils.isNotEmpty(orgList)) {
					for (OrgAdministrativeInfoEntity orgEntity : orgList) {
						subOrgCodeList.add(orgEntity.getCode());
					}
				}
			}
			subOrgCodeList.add(FossUserContextHelper.getOrgCode());
			dto.setSubOrgCodeList(subOrgCodeList);
			// 有效
			dto.setActive(FossConstants.ACTIVE);
			//默认值 "N/A"
			dto.setArrivesheetNo(SignConstants.DEFAULT_VALUE);
			// 派送单状态为 已确认  已下拉   已签收确认
			List<String> deliverStatusList = new ArrayList<String>();
			//已确认
			deliverStatusList.add(DeliverbillConstants.STATUS_CONFIRMED);
			// 派送单状态为"已签收确认"
			deliverStatusList.add(DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED);
			//已下拉 
			deliverStatusList.add(DeliverbillConstants.STATUS_PDA_DOWNLOADED);
			dto.setDeliverStatusList(deliverStatusList);//派送单状态集合
			return deliverHandlerDao.queryDeliverbillWaybillNo(dto);
		} else {//传入的参数为空
			return null;//返回空
		}
	}
	public void setSalesMotorcadeService(
			ISalesMotorcadeService salesMotorcadeService) {
		this.salesMotorcadeService = salesMotorcadeService;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	/**
	 * 根据派送单号,运单号得到流水号集合
	 * @author foss-meiying
	 * @date 2013-1-16 下午7:50:13
	 * @param waybillNo
	 * 			运单号
	 * @param deliverBillNo
	 * 			派送单编号
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService
	 * 	#queryStock(java.lang.String, java.lang.String)
	 */
	public List<SignDetailEntity> queryStock(String waybillNo,String deliverBillNo) {
		List<SignDetailEntity> signDetailEntitys = new ArrayList<SignDetailEntity>();
		//如果传入的运单号和派送单号都不为空
		if (StringUtil.isNotBlank(waybillNo) && StringUtil.isNotBlank(deliverBillNo)){
			//调用中转查询流水号集合
			List<String> serialNos = deliverLoadTaskService.queryLastLoadSerialNos(deliverBillNo, waybillNo);
			if(serialNos.size()>0){ //如果有流水号集合信息
				for (String serialNo : serialNos) {//循环得到流水号
					SignDetailEntity signDetailEntity = new SignDetailEntity();//签收明细对象
					signDetailEntity.setSerialNo(serialNo);//添加流水号
					signDetailEntitys.add(signDetailEntity);//添加签收明细到集合中
				}
			}
		}
		return signDetailEntitys;//返回流水号集合
	}
	/**
	 * 根据运单号查询财务信息
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:13:50
	 * @param waybillNo
	 * 			运单号
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService
	 * 	#queryFinanceSign(java.lang.String)
	 */
	@Override
	public FinancialDto queryFinanceSign(String waybillNo) {
		FinancialDto financialDto = deliverHandlerDao.queryWaybillByWaybillNo(waybillNo);
		if (financialDto == null) {//如果查询部分财务信息为空
			 financialDto = new FinancialDto();
		}
		BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(waybillNo);
		//财务单据查询，灰度改造   353654 ---------------------------start 
		List<BillReceivableEntity> billReceivableEntities = null;
		String vestSystemCode = null;
        try {
        	ArrayList<String> arrayList = new ArrayList<String>();
        	arrayList.add(waybillNo);
        	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
        			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".queryFinanceSign",
        			SettlementConstants.TYPE_FOSS);
        	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
        	List<VestBatchResult> list = response.getVestBatchResult();
        	vestSystemCode = list.get(0).getVestSystemCode();	
		} catch (Exception e) {
			LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
			throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
		}
		if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
			LOGGER.info("FOSS查询财务单据开始!运单号："+ waybillNo);
			billReceivableEntities = billReceivableService.queryReceivableAmountByCondition(billReceiveable);
			LOGGER.info("FOSS查询财务单据完成!运单号："+ waybillNo);
		}
		if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
			try {
				billReceivableEntities = cubcQueryReceivableAmountService.queryReceivableAmount(waybillNo);			
			} catch (Exception e) {
				LOGGER.error("调用CUBC结清查询财务单据异常信息为："+e.getMessage());
				throw new CUBCGrayException("呀,亲!系统繁忙,CUBC查询财务单据失败,请稍后重试!");
			}
		}
		//财务单据查询，灰度改造   353654 ---------------------------end
		if(CollectionUtils.isEmpty(billReceivableEntities)){//查询客户的应收单到付金额和应收代收货款金额 为空时
			LOGGER.info("单号"+waybillNo+";"+vestSystemCode+"财务查询为空");
			// 应收代收款
			financialDto.setReceiveableAmount(BigDecimal.ZERO);
			// 已收代收款
			financialDto.setReceivedAmount(BigDecimal.ZERO);
			// 应收到付款
			financialDto.setReceiveablePayAmoout(BigDecimal.ZERO);
			//已收到付款
			financialDto.setReceivedPayAmount(BigDecimal.ZERO);
			return financialDto;
		}
		for (BillReceivableEntity billReceivableEntity : billReceivableEntities) {
			// 到达应收单
			if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
				// 应收到付款
				financialDto.setReceiveablePayAmoout(billReceivableEntity.getUnverifyAmount());
				// 已收到付款
				financialDto.setReceivedPayAmount(billReceivableEntity.getVerifyAmount());
			} // 代收货款应收单
			else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
				// 应收代收款
				financialDto.setReceiveableAmount(billReceivableEntity.getUnverifyAmount());
				// 已收代收款
				financialDto.setReceivedAmount(billReceivableEntity.getVerifyAmount());
			} else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(billReceivableEntity.getBillType())){
				// 应收到付款
				financialDto.setReceiveablePayAmoout(billReceivableEntity.getUnverifyAmount());
				// 已收到付款
				financialDto.setReceivedPayAmount(billReceivableEntity.getVerifyAmount());
			}
		}
		return financialDto;//返回财务信息
	}

	/**
	 * 根据条件查询到达联信息
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:14:10
	 * @param arrivesheetNo
	 * 		到达联编号
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService#queryArriveSheetByParams(java.lang.String)
	 */
	@Override
	public ArriveSheetEntity queryArriveSheetByParams(String arrivesheetNo) {
		ArriveSheetEntity arrive = new ArriveSheetEntity();//到达联实体
		//如果到达联编号为空
		if(StringUtil.isBlank(arrivesheetNo)){
			return null;//返回null
		}
		// 到达联编号
		arrive.setArrivesheetNo(arrivesheetNo);
		return arriveSheetManngerService.queryArriveSheetByEntity(arrive);//查询到达联并返回
	}
	private CurrentInfo getSignOrgCode()
	{
		
		// 当前部门编码
		String orgCode = FossUserContextHelper.getOrgCode();
		//若当前部门编码不为空时
		if (!StringUtils.isEmpty(orgCode))
		{	
			//获取当前用户设置的当前部门
			OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
			//若用户设置的当前部门不为空
			if (org != null)
			{
				String salesDepartment = org.getSalesDepartment();

				// 若当前部门为营业部，则取最终配载部门为当前部门的运单进行排单
				if (FossConstants.YES.equals(salesDepartment))
				{
					
					return FossUserContext.getCurrentInfo();
				}else{
					return null;
				}/* else
				{
					// 查询排单服务外场
					OrgAdministrativeInfoEntity transferCenter = this.orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoOfConnOutfieldByCode(orgCode);
					if (transferCenter != null)
					{
						// 取外场驻地派送部的运单进行排单
						SaleDepartmentEntity sale = new SaleDepartmentEntity();
						sale.setTransferCenter(transferCenter.getCode());
						sale.setDelivery(FossConstants.YES);
						sale.setActive(FossConstants.ACTIVE);
						sale.setStation(FossConstants.ACTIVE);
						List<SaleDepartmentEntity> salesList = saleDepartmentService.querySaleDepartmentExactByEntity(sale, BEGIN_NUM, PAGE_SIZE);
						//若salelist集合不为空
						if (!CollectionUtils.isEmpty(salesList))
						{
							UserEntity user = new UserEntity();
							EmployeeEntity emp=new EmployeeEntity();
							emp.setEmpName(FossUserContext.getCurrentUser().getEmpName());//员工名称
							emp.setEmpCode(FossUserContext.getCurrentUser().getEmpCode());//员工编码
							user.setEmployee(emp);//得到员工信息
							OrgAdministrativeInfoEntity dept = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(salesList.get(0).getCode());
							dept.setName(salesList.get(0).getName());// 得到部门名称
							dept.setUnifiedCode(dept.getUnifiedCode());//标杆编码
							dept.setCode(salesList.get(0).getCode());//的部门编码
							return new CurrentInfo(user, dept);
						}else {
							return null;
						}
					}else {
						return null;
					}
				}*/
				
			} else
			{
				return null;
			}
		} else
		{
			return null;
		}
	}
	/**
	 * 派送处理 ----
	 * 		有PDA签收时操作
	 * @author foss-meiying
	 * @date 2013-1-21 上午11:51:12
	 * @param arriveSheet
	 * 		arriveSheet.waybill 
	 * 			运单号
	 * 		arriveSheet.arrivesheetNo
	 * 			到达联编号
	 * 		arriveSheet.deliverymanname
	 * 			提货人名称
	 * 		arriveSheet.identifyType
	 * 			证件类型
	 * 		arriveSheet.identifyCode
	 * 			证件号码
	 * 		arriveSheet.situation
	 * 			签收情况
	 * 		arriveSheet.arriveSheetGoodsQty
	 * 			到达联件数
	 * 		arriveSheet.signGoodsQty
	 * 			签收件数
	 * 		arriveSheet.signNote
	 * 			签收备注
	 * 		arriveSheet.signTime
	 * 			签收时间
	 * 		arriveSheet.status
	 * 			到达联状态
	 * 		arriveSheet.isPdaSign
	 * 			是否PDA签到
	 * 		arriveSheet.operateTime
	 * 			签收操作时间
	 * 		arriveSheet.operator
	 * 			操作人
	 * 		arriveSheet.operatorCode
	 * 			操作人编码
	 * 		arriveSheet.operateOrgName
	 * 			 操作部门名称
	 * 		arriveSheet.operateOrgCode
	 * 			操作部门编码
	 * 		arriveSheet.destroyed
	 * 			是否作废
	 * 		arriveSheet.isPrinted
	 * 			是否打印
	 * 		arriveSheet.printtimes
	 * 			打印次数
	 * 		arriveSheet.createUserName
	 * 			创建人
	 * 		arriveSheet.createUserCode
	 * 			创建人编码
	 * 		arriveSheet.createOrgName
	 * 			创建部门
	 * 		arriveSheet.createOrgCode
	 * 			创建部门编码
	 * 		arriveSheet.createTime
	 * 			创建时间
	 * 		arriveSheet.isSentRequired
	 * 			是否必送货	
	 * 		arriveSheet.isNeedInvoice
	 * 			是否需要发票
	 * 		arriveSheet.preNoticeContent
	 * 			提前通知内容
	 * 		arriveSheet.isRfcing
	 * 			是否审批中
	 * @return
	 * @see
	 */
	@Transactional
	public boolean addPdaSignInfo(ArriveSheetEntity arriveSheet) {
		LOGGER.info("pda录入签收人开始" +ReflectionToStringBuilder.toString(arriveSheet));//记录日志
		MutexElement mutexElement = new MutexElement(arriveSheet.getArrivesheetNo(), "到达联编号", MutexElementType.RFC_SIGN);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement,NumberConstants.NUMBER_4);
		if(!isLocked){//如果没有得到锁
			LOGGER.error("当前运单存在其他用户操作中，请稍后在签收");//记录错误日志
			throw new DeliverHandlerException(DeliverHandlerException.WAYBILL_LOCKED);//当前运单存在其他用户操作中，请稍后在签收
		}
		// 如果到达联有PDA签收 添加运单签收结果信息
		this.addPdaWaybillSign(arriveSheet);
		ArriveSheetEntity arrive = new ArriveSheetEntity();
		// 得到到达联编号
		arrive.setArrivesheetNo(arriveSheet.getArrivesheetNo());
		// 得到签收人
		arrive.setDeliverymanName(arriveSheet.getDeliverymanName());
		// 修改到达联信息
		arriveSheetManngerService.updateArriveSheetByArrivesheetNo(arrive);
		businessLockService.unlock(mutexElement);//解锁
		LOGGER.info("pda签收确认结束"); //记录日志
		return true;//返回结果
	}
	/**
	 * 派送处理 --->
	 * 			签收确认
	 * @author 
	 * 		foss-meiying
	 * @date 
	 * 		2012-12-18 下午7:14:49
	 * @param arriveSheet
	 *      arriveSheet.waybill 
	 * 			运单号
	 * 		arriveSheet.arrivesheetNo
	 * 			到达联编号
	 * 		arriveSheet.deliverymanname
	 * 			提货人名称
	 * 		arriveSheet.identifyType
	 * 			证件类型
	 * 		arriveSheet.identifyCode
	 * 			证件号码
	 * 		arriveSheet.situation
	 * 			签收情况
	 * 		arriveSheet.arriveSheetGoodsQty
	 * 			到达联件数
	 * 		arriveSheet.signGoodsQty
	 * 			签收件数
	 * 		arriveSheet.signNote
	 * 			签收备注
	 * 		arriveSheet.signTime
	 * 			签收时间
	 * 		arriveSheet.status
	 * 			到达联状态
	 * 		arriveSheet.isPdaSign
	 * 			是否PDA签到
	 * 		arriveSheet.operateTime
	 * 			签收操作时间
	 * 		arriveSheet.operator
	 * 			操作人
	 * 		arriveSheet.operatorCode
	 * 			操作人编码
	 * 		arriveSheet.operateOrgName
	 * 			 操作部门名称
	 * 		arriveSheet.operateOrgCode
	 * 			操作部门编码
	 * 		arriveSheet.destroyed
	 * 			是否作废
	 * 		arriveSheet.isPrinted
	 * 			是否打印
	 * 		arriveSheet.printtimes
	 * 			打印次数
	 * 		arriveSheet.createUserName
	 * 			创建人
	 * 		arriveSheet.createUserCode
	 * 			创建人编码
	 * 		arriveSheet.createOrgName
	 * 			创建部门
	 * 		arriveSheet.createOrgCode
	 * 			创建部门编码
	 * 		arriveSheet.createTime
	 * 			创建时间
	 * 		arriveSheet.isSentRequired
	 * 			是否必送货	
	 * 		arriveSheet.isNeedInvoice
	 * 			是否需要发票
	 * 		arriveSheet.preNoticeContent
	 * 			提前通知内容
	 * 		arriveSheet.isRfcing
	 * 			是否审批中
	 * @param financialDto 财务信息
	 * 		financialDto.toPayAmount
	 * 			到付金额(实付运费)
	 * 		financialDto.codAmount
	 * 			代收货款
	 * 		financialDto.receivedAllAmount
	 * 			收款总额
	 * 		financialDto.paymentType
	 * 			开单付款方式
	 * 		financialDto.receiveableAmount
	 * 			应收代收款
	 * 		financialDto.receivedAmount
	 * 			已收代收款
	 * 		financialDto.receiveablePayAmoout
	 * 			应收到付款
	 * 		financialDto.receivedPayAmount
	 * 			 已收到付款
	 * 		financialDto.isWholeVehicle
	 * 			是否整车运单
	 * 		financialDto.productCode
	 * 			运输性质
	 * 		financialDto.consigneeCode
	 * 			客户编码
	 *  	financialDto.consigneeName
	 * 			客户名称
	 *  	financialDto.claimNo
	 * 			款项认领编号
	 *  	financialDto.deliverbillNo
	 * 			派送单编号
	 *  	financialDto.orderNo
	 * 			订单号
	 * @param currentInfo
	 *            当前登录人的信息
	 *           currentInfo.operate 
	 * 				操作人
	 *          currentInfo.operatorCode 
	 *          	操作人编码
	 *          currentInfo.operateOrgName 
	 *          	操作部门名称
	 *          currentInfo.operateOrgCode 
	 *          	操作部门编码
	 * @param signDetailEntitys 
	 * 			流水号集合
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService
	 * 		#addNoPdaSign(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity,
	 *      com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo,java.util.List)
	 */
	@Transactional
	public String addNoPdaSign(ArriveSheetEntity arriveSheet,FinancialDto financialDto, CurrentInfo currentInfo,List<SignDetailEntity> signDetailEntitys) {
		if(!DateCompareUtil.isToday(arriveSheet.getSignTime())){
			throw new DeliverHandlerException(SignException.SIGNTIME_ISNOT_SYSTEMDATE,new Object[]{new SimpleDateFormat("yyyy-MM-dd").format(new Date())});//当前电脑时间有误，请调整日期为{0}
		}
		LOGGER.info("--签收确认开始" +ReflectionToStringBuilder.toString(arriveSheet));//记录日志
		MutexElement mutexElement = new MutexElement(arriveSheet.getArrivesheetNo(), "到达联编号", MutexElementType.RFC_SIGN);
		String result= DeliverHandlerException.OPERATER_SUCCESS; //默认返回值为操作成功 
		currentInfo =this.getSignOrgCode();
		if(currentInfo==null){
			throw new DeliverHandlerException(DeliverHandlerException.CURRENTORG_CODE_FAILED);//登陆人部门有误，请重新登录操作
		}
		Date systemDate = new Date();
		//签收操作时间
		arriveSheet.setOperateTime(systemDate);
		// 操作人
		arriveSheet.setOperator(currentInfo.getEmpName());
		// 操作人编码
		arriveSheet.setOperatorCode(currentInfo.getEmpCode());
		// 操作部门名称
		arriveSheet.setOperateOrgName(currentInfo.getCurrentDeptName());
		// 操作部门编码
		arriveSheet.setOperateOrgCode(currentInfo.getCurrentDeptCode());
		//签收前的到达联件数  ---并发控制
		arriveSheet.setOldArriveSheetGoodsQty(arriveSheet.getArriveSheetGoodsQty());
		//签收前的到达联状态为派送中 --并发控制
		arriveSheet.setOldStatus(ArriveSheetConstants.STATUS_DELIVER);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement,NumberConstants.NUMBER_4);
		if(!isLocked){//如果没有得到锁
			LOGGER.error("当前运单存在其他用户操作中，请稍后在签收");//记录错误日志
			throw new DeliverHandlerException(DeliverHandlerException.WAYBILL_LOCKED);//当前运单存在其他用户操作中，请稍后在签收
		}
		if(StringUtils.isBlank(arriveSheet.getSituation())){//如果签收情况为空
			businessLockService.unlock(mutexElement);//解锁
			throw new DeliverHandlerException(DeliverHandlerException.WAYBILL_LOCKED);//当前运单存在其他用户操作中，请稍后在签收
		}
		if(StringUtils.isNotBlank(arriveSheet.getArrivesheetNo())&& StringUtils.isNotBlank(arriveSheet.getWaybillNo()) &&arriveSheet.getArrivesheetNo().contains(arriveSheet.getWaybillNo())){//如果到达联编号跟运单号相对应
			
		}else{
			businessLockService.unlock(mutexElement);//解锁
			throw new DeliverHandlerException("运单号与到达联编号不符，请重新点击待处理的信息！");
		}
		// 如果签收情况为'货物拉回' 到达联签收状态为拒收
		if (ArriveSheetConstants.SITUATION_GOODS_BACK.equals(arriveSheet.getSituation())) {
			// 修改到达联签收信息。到达联状态为 拒收
			arriveSheet.setStatus(ArriveSheetConstants.STATUS_REFUSE);
			arriveSheet.setSignGoodsQty(SignConstants.ZERO); //签收件数为0
			// 修改到达联信息
			if(arriveSheetManngerService.updateArriveSheetByArrivesheetNo(arriveSheet)<= 0){
				LOGGER.error("--拒收操作失败，请重新查询或处理异常"+ ReflectionToStringBuilder.toString(arriveSheet));//记录日志
				businessLockService.unlock(mutexElement);//解锁
				throw new DeliverHandlerException(DeliverHandlerException.REFUSE_FAILED);//拒收操作失败，请重新查询或处理异常
			}
			this.operaterPullbackgoods(arriveSheet, signDetailEntitys, financialDto.getDeliverbillNo(),currentInfo);
			createWeixinInfo(arriveSheet, null, financialDto.getDeliverbillNo());
			//FOSS派送拉回 轨迹推送，零担:派送拉回操作人及部门相关信息从CurrentInfo中取
			//派送拉回：调用轨迹接口，推送轨迹 --add by 231438，快递100，轨迹推送需求RFOSS2015031706 
			sendWaybillTrackService.packagingMethodForDeliveryBack(arriveSheet, currentInfo); //--add by 231438
			//派送拉回：调用轨迹接口，WQS推送轨迹 add by 243921
			sendWaybillTrackService.rookieTrackingsForDeliveryBack(arriveSheet);
		} else {
			WaybillEntity waybill = waybillManagerService.queryPartWaybillByNo(arriveSheet.getWaybillNo());
			if(waybill== null){
				businessLockService.unlock(mutexElement);//解锁
				throw new SignException(SignException.WAYBILLNO_NULL);//运单号无效，请在综合查询查询运单状态是否已作废或已终止
			}
			if(waybill.getLastLoadOrgCode()!= null && (!waybill.getLastLoadOrgCode().equals(FossUserContext.getCurrentDeptCode()))){
				businessLockService.unlock(mutexElement);//解锁
				throw new SignException(SignException.CURRENT_ORG_CODE_IS_FALSE);
			}
			ActualFreightEntity actual = actualFreightService.queryByWaybillNo(arriveSheet.getWaybillNo());
			boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
			// 判断当前运单是否结清，若未结清，则调用货款结清接口进行结清操作。
			// 未结清
			if (!repaymentService.isPayment(arriveSheet.getWaybillNo())) {
				
				  //-----------  begin  ------------------------
                //add by 309603 yangqiang
                LOGGER.info("--查询是否已经结清");//记录日志
                //选择银行卡
                /**
                 * 银行卡检验
                 * @author yangqiang 309603
                 * @date 2016-2-23
                 */
                PosCardEntity posCardEntity = null;            //POS实体
                BigDecimal amount = null;            //未使用金额
                BigDecimal paymentAmount = null;            //收款金额
                if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(financialDto.getPaymentType())) {
                    //查询T+0报表获取未使用金额
                    //准备查询数据
                    PosCardManageDto posCardManageDto = new PosCardManageDto();
                    posCardManageDto.setTradeSerialNo(financialDto.getClaimNo());
                    //查询应该收单，获取应收单的营收部门
                    BillReceivableEntity billReceivableEntity = pdaRepaymentService.queryBillReceivable(arriveSheet.getWaybillNo());
                    //赋值给查询条件
                    posCardManageDto.setOrgCode(billReceivableEntity.getReceivableOrgCode());
                    //查询T+0报表数据
                    //根据交流水号和部门编码查询POS刷卡数据  T+0报表数据  灰度  353654---start
                    String vestSystemCode = null;
                    List<PosCardEntity> posCardEntitys = null;
                    try {
                    	List<String> arrayList = new ArrayList<String>();
                    	arrayList.add(arriveSheet.getWaybillNo());
                    	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                    			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".addNoPdaSign",
                    			SettlementConstants.TYPE_FOSS);
                    	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                    	List<VestBatchResult> list = response.getVestBatchResult();
                    	vestSystemCode = list.get(0).getVestSystemCode();	
        			} catch (Exception e) {
        				LOGGER.info("灰度分流失败,"+"运单号："+arriveSheet.getWaybillNo());
        				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
        			}
        			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
        				posCardEntitys = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys();
        				//是否存在
        				if (posCardEntitys == null || posCardEntitys.isEmpty()) {
        					throw new DeliverHandlerException("请到OA-财务自助上报登记反馈，审核后就可签收出库");
        				} else {
        					posCardEntity = posCardEntitys.get(0);
        				}
        				//判断所属模块，结清货款，
        				/* if (SettlementDictionaryConstants.NCI_DEPOSIT.equals(posCardEntity.getBelongModule())) {
                        	throw new RepaymentException("该交易流水号仅能做预收或做小票！");
                    	}*/
        				//获取未使用金额
        				amount = posCardEntity.getUnUsedAmount();
        				BigDecimal receiveableAmount = financialDto.getReceiveableAmount()==null ? BigDecimal.ZERO : financialDto.getReceiveableAmount();
        				BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout()==null ? BigDecimal.ZERO : financialDto.getReceiveablePayAmoout();
        				//比较
        				//收款金额
        				paymentAmount = receiveableAmount.add(receiveablePayAmoout);
        				if (amount.compareTo(paymentAmount) < 0) {//可用金额小于还款金额
        					throw new DeliverHandlerException("刷卡金额不足，请客户重新刷卡，已刷卡费用联系收银员做退预收操作");
        				}
        			}
        			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
        				//TODO  银行卡校验，待定  CUBC自己做。
        			}
        			//根据交流水号和部门编码查询POS刷卡数据   灰度  353654---end
                }
				RepaymentEntity repayment = new RepaymentEntity();
				// 运单号
				repayment.setWaybillNo(arriveSheet.getWaybillNo());
				//付款时间
				repayment.setPaymentTime(systemDate);
				// 付款方式
				repayment.setPaymentType(financialDto.getPaymentType());
				// 提货人
				repayment.setDeliverymanName(arriveSheet.getDeliverymanName());
				// 实付运费
				repayment.setActualFreight(financialDto.getReceiveablePayAmoout()== null ?BigDecimal.ZERO : financialDto.getReceiveablePayAmoout());
				// 代收货款
				repayment.setCodAmount(financialDto.getReceiveableAmount()==null ? BigDecimal.ZERO :financialDto.getReceiveableAmount());
				repayment.setConsigneeName(financialDto.getConsigneeName());// 客户名称
				repayment.setConsigneeCode(financialDto.getConsigneeCode());//客户编码
				//付款方式是‘银行卡’时，款项确认编号必须输入数字
				if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(financialDto.getPaymentType())
						&& (StringUtil.isBlank(financialDto.getClaimNo())
						|| !financialDto.getClaimNo().matches("[0-9]+"))){
					throw new RepaymentException("付款方式为银行卡时，款项确认编号必须输入数字。");
				}
				repayment.setClaimNo(financialDto.getClaimNo());//款项认领编号
				// 调结清货款进行结清
				try {
					repaymentService.deliverOperate(repayment, currentInfo);
					
					  //----------- begin  使用T+0交易流水  add  by  yangqiang 309603  --------
	                /**
	                 *更新T+0数据 调用更新数据
	                 * @author yangqiang 309603
	                 * @date 2016-2-23
	                 */
	                if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(financialDto.getPaymentType())) {
	                        //更新POS刷卡信息
	                        //准备数据 paymentAmount
	                        posCardEntity.setModifyUser(currentInfo.getEmpName());										//更新人名称
	                        posCardEntity.setModifyUserCode(currentInfo.getEmpCode());									//更新人编码
	                        posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(paymentAmount));					//已使用金额
	                        posCardEntity.setUnUsedAmount(posCardEntity.getUnUsedAmount().subtract(paymentAmount));   								//未使用金额
	                        //根据交流水号去更新POS刷卡已使用流水号金额，未使用金额   灰度  353654---start
	                        String vestSystemCode = null;
	                        try {
	                        	List<String> arrayList = new ArrayList<String>();
	                        	arrayList.add(repayment.getWaybillNo());
	                        	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
	                        			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".addNoPdaSign",
	                        			SettlementConstants.TYPE_FOSS);
	                        	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	                        	List<VestBatchResult> list = response.getVestBatchResult();
	                        	vestSystemCode = list.get(0).getVestSystemCode();	
	            			} catch (Exception e) {
	            				LOGGER.info("灰度分流失败,"+"运单号："+repayment.getWaybillNo());
	            				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
	            			}
	            			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
	            				pdaPosManageService.updatePosCardMessageAmount(posCardEntity);
	            				//插入新的POS刷卡明细
		                        //准备数据
		                        PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
		                        posCardDetailEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo()); 					//交易流水号
		                        posCardDetailEntity.setInvoiceType("W2");													//运单
		                        posCardDetailEntity.setInvoiceNo(arriveSheet.getWaybillNo());							//运单号
		                        //posCardDetailEntity.setAmount(waybilldto.getToPayAmount());									//发生金额
		                        BigDecimal totalAmount = repaymentDao.getTotalAmount(arriveSheet.getWaybillNo());
		                        posCardDetailEntity.setAmount(totalAmount);									                //发生金额
		                        posCardDetailEntity.setOccupateAmount(paymentAmount);											//已占用金额
		                       // BigDecimal unVerifyAmount = (receiveableAmount.add(receiveablePayAmoout)).subtract(payAmount);
		                        posCardDetailEntity.setUnVerifyAmount(BigDecimal.ZERO);										    //未核销金额
		                        posCardDetailEntity.setCreateUser(currentInfo.getEmpName());								    //创建人名称
		                        posCardDetailEntity.setCreateUserCode(currentInfo.getEmpCode());							    //创建人编码
		            			pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);//调用接口插入数据
	            			}
	            			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
	            				//TODO  CUBC不做处理
	            			}
	            			//根据交流水号去更新POS刷卡已使用流水号金额，未使用金额   灰度  353654---end
	                        
	                }
					LOGGER.info("--调结清货款进行结清成功");//记录日志
				} catch (RepaymentException se) {//捕获异常
					LOGGER.error("--调用货款结清接口异常",se);//记录日志
					//解锁
					businessLockService.unlock(mutexElement);
					// 捕捉结清货款抛出的异常
					throw new DeliverHandlerException(se.getErrorCode(), se);
				}
			}
			if(WaybillConstants.INNER_PICKUP.equals(waybill.getReceiveMethod())){//提货方式为汽运内部自提
				LOGGER.info("--提货方式为汽运内部自提 不需要调用结算签收接口----");//记录日志
			}else {
				this.confirmTaking(arriveSheet, financialDto, currentInfo,mutexElement);
			}
			// 修改到达联签收信息。到达联状态为 签收SIGN
			arriveSheet.setStatus(ArriveSheetConstants.STATUS_SIGN);
			arriveSheet.setReceiveMethod(ArriveSheetConstants.SIGN_RECEIVE_METHOD_DELIVER);//提货方式--派送
			// 修改到达联信息(修改到达联一定要放到调用结算的接口后面。不然会有问题)
			if(arriveSheetManngerService.updateArriveSheetByArrivesheetNo(arriveSheet)<= 0){
				LOGGER.error("--签收操作失败，请在当前页面重新查询!"+ ReflectionToStringBuilder.toString(arriveSheet));//记录日志
				businessLockService.unlock(mutexElement);//解锁
				throw new DeliverHandlerException(DeliverHandlerException.SIGN_FAILED);//签收操作失败，请在当前页面重新查询!
			}
			List<String> serials = null;
			//如果流水号集合不为空
			if(!CollectionUtils.isEmpty(signDetailEntitys)){
				serials =new ArrayList<String>();
				for (SignDetailEntity signDetailEntity : signDetailEntitys) {
					serials.add(signDetailEntity.getSerialNo());
					signDetailService.addSignDetailInfo(signDetailEntity);//添加签收明细。
					InOutStockEntity inOutStock = new InOutStockEntity();
					// 运单号
					inOutStock.setWaybillNO(arriveSheet.getWaybillNo());
					// 流水号
					inOutStock.setSerialNO(signDetailEntity.getSerialNo());
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
						stockService.outStockDelivery(inOutStock);
					} catch (StockException e) {//捕获异常
						LOGGER.error(e.getMessage(), e);//记录日志
						businessLockService.unlock(mutexElement);//解锁
						throw new DeliverHandlerException(e.getErrorCode(),e);//抛出异常
					}
				}
				
			}
			//如果签收件数小于运单开单件数
			if(arriveSheet.getSignGoodsQty() != null &&arriveSheet.getSignGoodsQty()<waybill.getGoodsQtyTotal()){
				todoActionService.updateLabeledStatusByWaybillNum(arriveSheet.getWaybillNo(),serials);
			}else {
				todoActionService.updateLabeledStatusByWaybillNum(arriveSheet.getWaybillNo(),null);
			}
			ActualFreightDto actualFreightDto = new ActualFreightDto(arriveSheet.getWaybillNo(),arriveSheet.getSignGoodsQty(),financialDto.getPaymentType());
			//无PDA签收时修改运单状态里的
			actualFreightService.updateArriveNotoutGoodsQtyByWaybillNo(actualFreightDto);
			try {
				WaybillSignResultEntity wayEntity = waybillSignResultService.checkWaybillSignResultSign(arriveSheet,waybill,systemDate,currentInfo);
				wayEntity.setIsPdaSign(arriveSheet.getIsPdaSign());
				// 添加运单签收结果信息
				waybillSignResultService.addWaybillSignResult(wayEntity);
				if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){//如果签收状态为全部签收
					waybillSignResultService.updateCrmOrder(financialDto.getOrderNo(), currentInfo, wayEntity);
				}
				
				//菜鸟轨迹 add by 231438
				//封装的轨迹方法 赋值，判断是否符合条件 调用中转的轨迹接口
				sendWaybillTrackService.rookieTrackingsForSign(wayEntity);
				//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，推送轨迹--add by 231438 快递100轨迹推送
				sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
				
			} catch (SignException e) {//捕获异常
				businessLockService.unlock(mutexElement);//解锁
				throw new DeliverHandlerException(e.getErrorCode(), e);//抛出异常
			}

			//保存QMS内物短少差错信息(签收情况为异常内物短少 或 同票多类异常)
			if(StringUtils.isNotEmpty(arriveSheet.getSituation())
					&& (arriveSheet.getSituation().equals(ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT)
							||arriveSheet.getSituation().equals(ReportConstants.SIGN_SITUATION_UNNORMAL_SAMEVOTEODD))){
				this.saveRecordShortErrorInfo(arriveSheet,currentInfo,signDetailEntitys);
			}else{
				//306548保存QMS重大货物异常差错信息(签收情况为异常，保价金额>=10000)
				if(StringUtils.isNotEmpty(arriveSheet.getSituation())&& (waybill.getInsuranceAmount().compareTo(INSURANCEAMOUNT_NUM)!=-1)){
					this.saveRecordImportantErrorInfo(arriveSheet, currentInfo, signDetailEntitys,waybill);
				}
			}
			//应用监控签收调用
			waybillSignResultService.signCounterByWaybillNo(currentInfo, waybill);
			//如果到达联签收状态为部分签收
			if(SignConstants.SIGN_STATUS_PARTIAL.equals(arriveSheet.getSignStatus())){
				this.operaterPullbackgoods(arriveSheet, signDetailEntitys, financialDto.getDeliverbillNo(),currentInfo);
			}
			WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
			BeanUtils.copyProperties(arriveSheet,waybillSigndto);//复制代码
			//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
			result =waybillSignResultService.sendMessNotice(waybill, currentInfo,waybillSigndto);
			//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
			if(isSendInvoiceInfo){
				waybillSignResultService.sendInvoiceInfo(waybill,actual);
			}
		}
		businessLockService.unlock(mutexElement);//解锁
		LOGGER.info("--签收确认结束"); // 记录日志
		return result;//返回结果
	}
	
	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.server.service.impl.DeliverHandlerService.recordErrorWaybill
	 * @author: foss-yuting
	 * @description: foss记录内物短少差错 上报OA
	 * @date:2014年01月21日 下午15:59:21
	 * 	update by foss-bieyexiong 2015-06-24
	 */
	private void saveRecordShortErrorInfo(ArriveSheetEntity arriveSheet,CurrentInfo currentInfo,List<SignDetailEntity> list) {
		//记录日志
		LOGGER.info("*************保存QMS内物短少差错信息***********start");
		RecordErrorWaybillDto recordErrorWaybillDto=new RecordErrorWaybillDto();
		if(arriveSheet!=null && CollectionUtils.isNotEmpty(list)){
			//主键
			recordErrorWaybillDto.setId(UUIDUtils.getUUID());
			//是否上报(Y:未上报)
			recordErrorWaybillDto.setActive(SignConstants.YES);
			//运单号
			recordErrorWaybillDto.setWaybillNo(arriveSheet.getWaybillNo());
			//短少量
			recordErrorWaybillDto.setAlittleShort(arriveSheet.getAlittleShort());
			//外包装是否完好
			recordErrorWaybillDto.setPackingResult(arriveSheet.getPackingResult());
			//创建时间
			recordErrorWaybillDto.setCreateTime(new Date());
			//修改时间
			recordErrorWaybillDto.setModifyTime(new Date());
			//快递员为空时
			if(StringUtils.isBlank(arriveSheet.getSendExpressEmpCode())){
				//上报人名字(保存登录人信息)
			recordErrorWaybillDto.setOperateName(currentInfo.getEmpName());
				//上报人工号
			recordErrorWaybillDto.setOperateCode(currentInfo.getEmpCode());
			}else{
				//上报人工号
				recordErrorWaybillDto.setOperateCode(arriveSheet.getSendExpressEmpCode());
			}
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
		//记录日志
		LOGGER.info("*************保存QMS内物短少差错信息***********end");
		}
	
	
	/**
	 * @author 306548-foss-honglujun
	 * foss记录重大货物异常自动上报信息 OA
	 */
	private void saveRecordImportantErrorInfo(ArriveSheetEntity arriveSheet,CurrentInfo currentInfo,List<SignDetailEntity> list,WaybillEntity waybill){
		//记录日志
		LOGGER.info("*************保存QMS重大货物异常差错信息***********start");
		if(arriveSheet!=null && CollectionUtils.isNotEmpty(list)&& waybill!=null){
			RecordErrorImportantWaybillDto recordErrorImportantWaybillDto = new RecordErrorImportantWaybillDto();
			//主键
			recordErrorImportantWaybillDto.setId(UUIDUtils.getUUID());
			//是否上报(Y:未上报)
			recordErrorImportantWaybillDto.setActive(SignConstants.YES);
			//运单号
			recordErrorImportantWaybillDto.setWaybillNo(arriveSheet.getWaybillNo());
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
	 * <p>根据不同类型封装相对应的数据</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-2-13 18:37:11
	 * @param arriveSheet
	 * @param wayEntity
	 * @param deliverBillNo 
	 */
	private void createWeixinInfo(ArriveSheetEntity arriveSheet, WaybillSignResultEntity wayEntity, String deliverBillNo) {
		//派送拉回
		WeixinSendDto dto = new WeixinSendDto();
		dto.setWaybillNo(arriveSheet.getWaybillNo());
		if(ArriveSheetConstants.SITUATION_GOODS_BACK.equals(arriveSheet.getSituation())){
			//设置签收失败详情
			dto.setStateType(arriveSheet.getSignNote());
			//PDA或者他们的签收时间
			dto.setCreateTime(arriveSheet.getSignTime());
			DeliverbillDto deliverbillDto = new DeliverbillDto();
			deliverbillDto.setDeliverbillNo(deliverBillNo);
			DeliverbillEntity entity = deliverbillDao.queryByDeliverbillDto(deliverbillDto);
			if(entity == null){
				LOGGER.info("派送单号为"+deliverBillNo+"后台查询不到");
			}else{
				//创建组织
				dto.setDeliverOrgCode(entity.getCreateOrgCode());
				//封装派送人电话，所在部门的详细信息
				queryDriverTelephone(entity, dto);
			}
			//推送消息
			ResultDto resultDto = weixinSendService.sysnWeixinInfoForWebSiteUnDirectly(dto,WeixinConstants.WEIXIN_DELIVER_RETURN_TYPE);
			if(ResultDto.FAIL.equals(resultDto.getCode())){
				LOGGER.info("单号为："+dto.getWaybillNo()+"微信消息发送失败，错误详情："+resultDto.getMsg());
			}
			LOGGER.info("模块：PDA签收时货物拉回微信消息处理完毕");
			LOGGER.info("Foss取消送货任务至Gps开启");
			//推送消息
			com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto gpsResultDto = 
					gpsDeliverService.syscCancelDeliverTaskToGps(entity, arriveSheet.getWaybillNo());
			if(ResultDto.FAIL.equals(gpsResultDto.getCode())){
				LOGGER.info("单号为："+dto.getWaybillNo()+"Foss取消送货任务至Gps发送失败，错误详情："+gpsResultDto.getMsg());
			}
			LOGGER.info("模块：Foss取消送货任务至Gps处理完毕");
		}
	}
	
	/**
	 * 根据派送单中的数据工号和车牌号进行判断
	 * 优先级别，如果是快递单，则优先根据车牌号进行查询
	 * @param entity
	 * @param dto
	 */
	private void queryDriverTelephone(DeliverbillEntity entity, WeixinSendDto dto) {
		//若司机工号不为空
		if (StringUtil.isNotEmpty(entity.getDriverCode())){
			//如果是快递派送单
			if(FossConstants.YES.equals(entity.getIsExpress())){
				validaDeliverbillEntity(entity, dto);
			}else{
				// 内部司机根据工号查询相关信息
				validaDeliverbill(entity, dto);
			}
		//如果车牌号不为空
		} else if (StringUtil.isNotEmpty(entity.getVehicleNo())){
		    if(FossConstants.YES.equals(entity.getIsExpress())){
				validaEntity(entity, dto);
			}else{
		    	// 外请司机根据车牌号进行查询
				List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(entity.getVehicleNo());
				if (org.apache.commons.collections.CollectionUtils.isNotEmpty(driverAssociationDtos)){
					//司机姓名
					dto.setDeliverManName(driverAssociationDtos.get(0).getDriverName());
					// 司机电话
					dto.setDeliverManMobile(driverAssociationDtos.get(0).getDriverPhone());
				}
		    }
		}
	}

	private void validaEntity(DeliverbillEntity entity, WeixinSendDto dto) {
		if(StringUtils.isNotEmpty(entity.getVehicleNo())){
			ExpressVehicleDto expressVeh = new ExpressVehicleDto();
			expressVeh.setVehicleNo(entity.getVehicleNo());
			List<ExpressVehicleDto> expDtoList = commonExpressVehicleService.queryExpressVehicleNoListBySelectiveCondition(expressVeh,0 , SettlementReportNumber.FIFTY);
			if(expDtoList != null && expDtoList.size()>0){
				// 司机电话
				dto.setDeliverManMobile(expDtoList.get(0).getMobilePhone());
				//司机姓名
				dto.setDeliverManName(expDtoList.get(0).getEmpName());
			}else{
				EmployeeEntity employEntity = employeeService.queryEmployeeByEmpCode(entity.getDriverCode());
				if(employEntity != null){
					// 司机电话
					dto.setDeliverManMobile(employEntity.getMobilePhone());
					//司机姓名
					dto.setDeliverManName(employEntity.getEmpName());
				}
			}				
		}
	}

	private void validaDeliverbill(DeliverbillEntity entity, WeixinSendDto dto) {
		DriverAssociationDto driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(entity.getDriverCode());
		//用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
		if (driverAssociationDto != null){
			// 司机电话
			dto.setDeliverManMobile(driverAssociationDto.getDriverPhone());
			//司机姓名
			dto.setDeliverManName(driverAssociationDto.getDriverName());
		} else{
		  // 外请司机根据车牌号进行查询
		  List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(entity.getVehicleNo());
		  
		  if (org.apache.commons.collections.CollectionUtils.isNotEmpty(driverAssociationDtos)){
			  //司机姓名
			  dto.setDeliverManName(driverAssociationDtos.get(0).getDriverName());
			  // 司机电话
			  dto.setDeliverManMobile(driverAssociationDtos.get(0).getDriverPhone());
		  }
		}
	}

	private void validaDeliverbillEntity(DeliverbillEntity entity,
			WeixinSendDto dto) {
		validaWeixinSend(entity, dto);
	}

	private void validaWeixinSend(DeliverbillEntity entity, WeixinSendDto dto) {
		if(StringUtils.isNotEmpty(entity.getVehicleNo())){
			ExpressVehicleDto expressVeh = new ExpressVehicleDto();
			expressVeh.setVehicleNo(entity.getVehicleNo());
			List<ExpressVehicleDto> expDtoList = commonExpressVehicleService.queryExpressVehicleNoListBySelectiveCondition(expressVeh,SettlementReportNumber.FIFTY , 0);
			if(expDtoList != null && expDtoList.size()>0){
				// 司机电话
				dto.setDeliverManMobile(expDtoList.get(0).getMobilePhone());
				//司机姓名
				dto.setDeliverManName(expDtoList.get(0).getEmpName());
			}else{
				EmployeeEntity employEntity = employeeService.queryEmployeeByEmpCode(entity.getDriverCode());
				if(employEntity != null){
					// 司机电话
					dto.setDeliverManMobile(employEntity.getMobilePhone());
					//司机姓名
					dto.setDeliverManName(employEntity.getEmpName());
				}
			}				
		}
	}

	/**
	 * 如果签收情况为“拉回货物”，部分签收时，
	 * 将拉回货物，
	 * 部分签收未签收的流水号录入交接流水号表里
	 * @author
	 * 		 foss-meiying
	 * @date 
	 * 		2013-1-31 下午3:40:56
	 * @param arriveSheet
	 * 			到达联
	 * 		arriveSheet.waybill 
	 * 			运单号
	 * 		arriveSheet.arrivesheetNo
	 * 			到达联编号
	 * 		arriveSheet.deliverymanname
	 * 			提货人名称
	 * 		arriveSheet.identifyType
	 * 			证件类型
	 * 		arriveSheet.identifyCode
	 * 			证件号码
	 * 		arriveSheet.situation
	 * 			签收情况
	 * 		arriveSheet.arriveSheetGoodsQty
	 * 			到达联件数
	 * 		arriveSheet.signGoodsQty
	 * 			签收件数
	 * 		arriveSheet.signNote
	 * 			签收备注
	 * 		arriveSheet.signTime
	 * 			签收时间
	 * 		arriveSheet.status
	 * 			到达联状态
	 * 		arriveSheet.isPdaSign
	 * 			是否PDA签到
	 * 		arriveSheet.operateTime
	 * 			签收操作时间
	 * 		arriveSheet.operator
	 * 			操作人
	 * 		arriveSheet.operatorCode
	 * 			操作人编码
	 * 		arriveSheet.operateOrgName
	 * 			 操作部门名称
	 * 		arriveSheet.operateOrgCode
	 * 			操作部门编码
	 * 		arriveSheet.destroyed
	 * 			是否作废
	 * @param signDetailEntitys
	 * 			签收明细集合
	 * @param deliverbillNo
	 * 			派送单编号
	 * @see
	 */
	private void operaterPullbackgoods(ArriveSheetEntity arriveSheet,List<SignDetailEntity> signDetailEntitys,String deliverbillNo,CurrentInfo currentInfo){
		StayHandoverDetailEntity detailEntity = new StayHandoverDetailEntity();
		detailEntity.setWaybillNo(arriveSheet.getWaybillNo());//运单号
		detailEntity.setDeliverbillNo(deliverbillNo);//派送单号
		//拉回件数= 到达联件数-签收件数
		detailEntity.setGoodsQty(arriveSheet.getArriveSheetGoodsQty() - arriveSheet.getSignGoodsQty());
		if(null != stayHandoverDetailService.addStayHandoverDetail(detailEntity)){
			//调用中转接口查询满足条件的所有流水号集合
			List<String> serialNos = deliverLoadTaskService.queryLastLoadSerialNos(deliverbillNo, arriveSheet.getWaybillNo());
			//如果到达联签收状态为部分签收
			if(SignConstants.SIGN_STATUS_PARTIAL.equals(arriveSheet.getSignStatus())){
				if(CollectionUtils.isNotEmpty(serialNos)){//如果传入的流水号集合不为空
					for (int i = 0; i < serialNos.size(); i++) {//循环流水号集合
						for (SignDetailEntity signDetailEntity : signDetailEntitys) {
							if(serialNos.get(i).equals(signDetailEntity.getSerialNo())){//如果传入的流水号存在查询的流水号集合
								serialNos.remove(i);//移除该流水号
								i--;
								break;//跳出当前循环
							}
						}
					}
					for (String serialNo : serialNos) {//循环流水号集合
						StayHandoverserialEntity stayHandoverserialEntity = new StayHandoverserialEntity();
						stayHandoverserialEntity.settSrvStayHandoverdetailId(detailEntity.getId());//交接明细id
						stayHandoverserialEntity.setSerailno(serialNo);//流水号
						stayHandoverserialService.addSelective(stayHandoverserialEntity);//添加交接流水号
						//异常签收时操作
						ExceptionEntity exceptionEntity = new ExceptionEntity();
						//运单号
						exceptionEntity.setWaybillNo(arriveSheet.getWaybillNo());
						//货物异常
						exceptionEntity.setExceptionType(ExceptionProcessConstants.LABELEDGOOD_EXCEPTION);
						//异常环节
						exceptionEntity.setExceptionLink(ExceptionProcessConstants.DELIVER);
						//异常原因
						exceptionEntity.setExceptionReason(DictUtil.rendererSubmitToDisplay(arriveSheet.getSignNote(), DictionaryConstants.PKP_PULLBACK_REASON));
						//异常操作  ---派送拉回
						exceptionEntity.setExceptiOperate(ExceptionProcessConstants.PKP_EXCEPTION_SEND_RETURN);
						//异常状态 ---已处理
						exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);
						//流水号
						exceptionEntity.setSerialNo(serialNo);
						//异常生成时间
						exceptionEntity.setExceptionTime(new Date());
						//登记人
						exceptionEntity.setCreateUserName(currentInfo.getEmpName() == null ? "" :currentInfo.getEmpName());
						//登记人编码
						exceptionEntity.setCreateUserCode(currentInfo.getEmpCode()== null ? "" : currentInfo.getEmpCode());
						//登记部门编码
						exceptionEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode()== null ? "" : currentInfo.getCurrentDeptCode());
						 //登记部门
						exceptionEntity.setCreateOrgName(currentInfo.getCurrentDeptName()== null ? "" : currentInfo.getCurrentDeptName());
						exceptionProcessService.addExceptionProcessInfo(exceptionEntity);//添加异常信息
					}
					
					//创建派送交单退回DTO，调用交单接口
					DeliverHandoverbillReturnDto dto = new DeliverHandoverbillReturnDto();
					dto.setWaybillNo(arriveSheet.getWaybillNo());
					dto.setBillQty(detailEntity.getGoodsQty());
					deliverHandoverbillService.goodsBackReturnPreDeliverbill(dto);
					
					if(detailEntity.getGoodsQty()>0){
						//实际货物实体
						ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
						//运单号
						actualFreightEntity.setWaybillNo(arriveSheet.getWaybillNo());
						//到达联件数
						actualFreightEntity.setGenerateGoodsQty(detailEntity.getGoodsQty());
						//排单件数
						actualFreightEntity.setArrangeGoodsQty(detailEntity.getGoodsQty());
						//更新到达件数
						actualFreightDao.updateActualFreightPartByWaybillNo(actualFreightEntity);

					}
				}else {
					LOGGER.error("--根据派送单编号，到达联编号查询拉回货物的流水号为空！");//记录日志
				}
			}else {//货物拉回
				if(CollectionUtils.isNotEmpty(serialNos)){//如果流水号不为空
					for (String serialNo : serialNos) {//循环流水号集合
						StayHandoverserialEntity stayHandoverserialEntity = new StayHandoverserialEntity();
						stayHandoverserialEntity.settSrvStayHandoverdetailId(detailEntity.getId());//交接明细id
						stayHandoverserialEntity.setSerailno(serialNo);//流水号
						stayHandoverserialService.addSelective(stayHandoverserialEntity);//添加交接流水号
					}
				
				}
				//拉回原因为客户要求其他时间送货（客户），送货日期不为空并且是新派送单 
				//息306566  DN201601290015 拉回原因优化 
			//	arriveSheet.setNextDeliverTime(null);
				//异常签收时操作
				ExceptionEntity exceptionEntity = new ExceptionEntity();
				//运单号
				exceptionEntity.setWaybillNo(arriveSheet.getWaybillNo());
				//运单异常
				exceptionEntity.setExceptionType(ExceptionProcessConstants.WAYBILL_EXCEPTION);
				//异常环节
				exceptionEntity.setExceptionLink(ExceptionProcessConstants.DELIVER);
					
				exceptionEntity.setArrivesheetId(arriveSheet.getId());//到达联id
				//异常生成时间
				exceptionEntity.setExceptionTime(new Date());
				//登记人
				exceptionEntity.setCreateUserName(currentInfo.getEmpName() == null ? "" :currentInfo.getEmpName());
				//登记人编码
				exceptionEntity.setCreateUserCode(currentInfo.getEmpCode()== null ? "" : currentInfo.getEmpCode());
				//登记部门编码
				exceptionEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode()== null ? "" : currentInfo.getCurrentDeptCode());
				 //登记部门
				exceptionEntity.setCreateOrgName(currentInfo.getCurrentDeptName()== null ? "" : currentInfo.getCurrentDeptName());
				//异常原因
				    
				//拉回备注----当拉回原因为其他原因的时候----把备注字段保存息306566  DN201601290015   拉回原因优化 
				if( ArriveSheetConstants.PULLBACK_REASON_OTHER.equals(arriveSheet.getSignNote())){
					if(StringUtils.isEmpty(arriveSheet.getPullbackNote())){
						exceptionEntity.setExceptionReason(DictUtil.rendererSubmitToDisplay(arriveSheet.getSignNote(), DictionaryConstants.PKP_PULLBACK_REASON));	
					}else{
						exceptionEntity.setExceptionReason(DictUtil.rendererSubmitToDisplay(arriveSheet.getSignNote(), DictionaryConstants.PKP_PULLBACK_REASON)+"【"+arriveSheet.getPullbackNote()+"】");	
					}
				}else{
					exceptionEntity.setExceptionReason(DictUtil.rendererSubmitToDisplay(arriveSheet.getSignNote(), DictionaryConstants.PKP_PULLBACK_REASON));	
				}
				//异常操作  ---派送拉回
				exceptionEntity.setExceptiOperate(ExceptionProcessConstants.PKP_EXCEPTION_SEND_RETURN);
				//306566  DN201601290015    拉回原因 为客户要求其他时间送货（客户）的时候生成一条已处理的异常记录
				if( ArriveSheetConstants.PULLBACK_REASON_OTHER_TIME_DELIVERY.equals(arriveSheet.getSignNote())){//快递
					//异常状态 ---零担已处理
					exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);
				}else{
					//异常状态 ---处理中
					exceptionEntity.setStatus(ExceptionProcessConstants.HANDLING);
				}
				exceptionProcessService.addExceptionProcessInfo(exceptionEntity);//添加异常信息
				 
				
				//创建派送交单退回DTO，调用交单接口
				DeliverHandoverbillReturnDto dto = new DeliverHandoverbillReturnDto();
				dto.setWaybillNo(arriveSheet.getWaybillNo());
				dto.setBillQty(arriveSheet.getArriveSheetGoodsQty());
				dto.setPreDeliverDate(arriveSheet.getNextDeliverTime());
				deliverHandoverbillService.goodsBackReturnPreDeliverbill(dto);
				
				//实际货物实体
				ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
				//运单号
				actualFreightEntity.setWaybillNo(arriveSheet.getWaybillNo());
				//到达联件数
				actualFreightEntity.setGenerateGoodsQty(arriveSheet.getArriveSheetGoodsQty());
				//排单件数
				actualFreightEntity.setArrangeGoodsQty(arriveSheet.getArriveSheetGoodsQty());
				//更新到达件数
				actualFreightDao.updateActualFreightPartByWaybillNo(actualFreightEntity);
			}
		}
		
	}
	/**
	 * 
	 *系统调用财务接口完成财务出库 首先判断是否第一次签收。
	 * 根据运单号判断到达联表里是否存在签收,
	 * 如果未签收就调用，如果已经签收，就不调用。
	 * @author
	 * 		 foss-meiying
	 * @date
	 * 		 2012-12-7 下午2:32:59
	 * @param arriveSheet
	 * 		arriveSheet.waybill 
	 * 			运单号
	 * 		arriveSheet.arrivesheetNo
	 * 			到达联编号
	 * 		arriveSheet.deliverymanname
	 * 			提货人名称
	 * 		arriveSheet.identifyType
	 * 			证件类型
	 * 		arriveSheet.identifyCode
	 * 			证件号码
	 * 		arriveSheet.situation
	 * 			签收情况
	 * 		arriveSheet.arriveSheetGoodsQty
	 * 			到达联件数
	 * 		arriveSheet.signGoodsQty
	 * 			签收件数
	 * 		arriveSheet.signNote
	 * 			签收备注
	 * 		arriveSheet.signTime
	 * 			签收时间
	 * 		arriveSheet.status
	 * 			到达联状态
	 * 		arriveSheet.isPdaSign
	 * 			是否PDA签到
	 * 		arriveSheet.operateTime
	 * 			签收操作时间
	 * 		arriveSheet.operator
	 * 			操作人
	 * 		arriveSheet.operatorCode
	 * 			操作人编码
	 * 		arriveSheet.operateOrgName
	 * 			 操作部门名称
	 * 		arriveSheet.operateOrgCode
	 * 			操作部门编码
	 * 		arriveSheet.destroyed
	 * 			是否作废
	 * @param financialDto
	 * 		financialDto.toPayAmount
	 * 			到付金额(实付运费)
	 * 		financialDto.codAmount
	 * 			代收货款
	 * 		financialDto.receivedAllAmount
	 * 			收款总额
	 * 		financialDto.paymentType
	 * 			开单付款方式
	 * 		financialDto.receiveableAmount
	 * 			应收代收款
	 * 		financialDto.receivedAmount
	 * 			已收代收款
	 * 		financialDto.receiveablePayAmoout
	 * 			应收到付款
	 * 		financialDto.receivedPayAmount
	 * 			 已收到付款
	 * 		financialDto.isWholeVehicle
	 * 			是否整车运单
	 * 		financialDto.productCode
	 * 			运输性质
	 * 		financialDto.consigneeCode
	 * 			客户编码
	 *  	financialDto.consigneeName
	 * 			客户名称
	 *  	financialDto.claimNo
	 * 			款项认领编号
	 *  	financialDto.deliverbillNo
	 * 			派送单编号
	 *  	financialDto.orderNo
	 * 			订单号
	 * @param currentInfo
	 * 		currentInfo.operate 
	 * 			操作人
	 *      currentInfo.operatorCode 
	 *          操作人编码
	 *      currentInfo.operateOrgName 
	 *          操作部门名称
	 *      currentInfo.operateOrgCode 
	 *          操作部门编码
	 * @see
	 */
	private void confirmTaking(ArriveSheetEntity arriveSheet,FinancialDto financialDto, CurrentInfo currentInfo,MutexElement mutexElement) {
		try {
			//到达联查询条件（运单号，状态为签收，有效，未作废）
			ArriveSheetEntity arriveSheetEntity = new ArriveSheetEntity(arriveSheet.getWaybillNo(),ArriveSheetConstants.STATUS_SIGN, FossConstants.ACTIVE,FossConstants.NO);
			Long signCountLong = arriveSheetManngerService.queryArriveExistSign(arriveSheetEntity);
			//如果该运单在到达联里没有存在签收，调用结算出库接口
			if (signCountLong == null || SignConstants.ZERO >= signCountLong) {
				LineSignDto dto = new LineSignDto();
				// 操作人编码
				dto.setOperatorName(currentInfo.getEmpName());
				// 操作人名称
				dto.setOperatorCode(currentInfo.getEmpCode());
				// 签收部门名称
				dto.setSignOrgName(currentInfo.getCurrentDeptName());
				// 签收部门编码
				dto.setSignOrgCode(currentInfo.getCurrentDeptCode());
				// 运单号
				dto.setWaybillNo(arriveSheet.getWaybillNo());
				// 签收时间
				dto.setSignDate(arriveSheet.getOperateTime());
				dto.setSignType(SettlementConstants.LINE_SIGN);
				// 是否整车运单
				dto.setIsWholeVehicle(financialDto.getIsWholeVehicle());
				// 是否PDA签收
				dto.setIsPdaSign(arriveSheet.getIsPdaSign());
				// 运输性质
				dto.setProductType(financialDto.getProductCode());
				//签收情况
				dto.setSignSituation(arriveSheet.getSituation());
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
//					//查询运单总件数
//					WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
//					//查询签收结果表
//			        WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(dto.getWaybillNo(),FossConstants.ACTIVE);
//			        WaybillSignResultEntity waybillSignResultEntity = waybillSignResultDao.queryWaybillSignResult(wayEntity);
//			        //获取已签收件数+此次签收件数与运单总件数比较判断全部和部分签收
//			        int signCount = (waybillSignResultEntity == null ? 0 : waybillSignResultEntity.getSignGoodsQty()) + arriveSheet.getSignGoodsQty();
//					String signStatus = signCount >= waybill.getGoodsQtyTotal() ? SignConstants.SIGN_STATUS_ALL
//							: SignConstants.SIGN_STATUS_PARTIAL;
//					LOGGER.info("运单号:"+dto.getWaybillNo()+",DeliverHandlerService.confirmTaking传递签收件数为:"+ arriveSheet.getSignGoodsQty());
//					if(SignConstants.SIGN_STATUS_ALL.equals(signStatus)){
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
//					}else{
//						LOGGER.info("DeliverHandlerService.confirmTaking(2162行)："+dto.getWaybillNo()+",部分签收,不调CUBC签收接口");
//					}
				}
				//CUBC签收   灰度改造    353654 ---------------------------end
			}
		} catch (SettlementException se) {//捕获异常
			LOGGER.error("--调用财务接口出现异常");//记录日志
			businessLockService.unlock(mutexElement);
			// 捕捉结算抛出的异常
			throw new DeliverHandlerException(se.getErrorCode(), se);
		}
	}

	/**
	 * PDA签收时 根据运单号查询运单签收结果信息. 
	 * 	作废当前运单签收结果记录,得到签收人,
	 *  重新录入运单签收结果信息.
	 * 
	 * @author foss-meiying
	 * @date 2012-12-7 下午2:23:18
	 * @param arriveSheet
	 * 		arriveSheet.waybill 
	 * 			运单号
	 * 		arriveSheet.arrivesheetNo
	 * 			到达联编号
	 * 		arriveSheet.deliverymanname
	 * 			提货人名称
	 * 		arriveSheet.identifyType
	 * 			证件类型
	 * 		arriveSheet.identifyCode
	 * 			证件号码
	 * 		arriveSheet.situation
	 * 			签收情况
	 * 		arriveSheet.arriveSheetGoodsQty
	 * 			到达联件数
	 * 		arriveSheet.signGoodsQty
	 * 			签收件数
	 * 		arriveSheet.signNote
	 * 			签收备注
	 * 		arriveSheet.signTime
	 * 			签收时间
	 * 		arriveSheet.status
	 * 			到达联状态
	 * 		arriveSheet.isPdaSign
	 * 			是否PDA签到
	 * 		arriveSheet.operateTime
	 * 			签收操作时间
	 * 		arriveSheet.operator
	 * 			操作人
	 * 		arriveSheet.operatorCode
	 * 			操作人编码
	 * 		arriveSheet.operateOrgName
	 * 			 操作部门名称
	 * 		arriveSheet.operateOrgCode
	 * 			操作部门编码
	 * 		arriveSheet.destroyed
	 * 			是否作废
	 * @see
	 */
	private void addPdaWaybillSign(ArriveSheetEntity arriveSheet){
		LOGGER.info("--PDA签收 (录入运单签收结果) 开始" + ReflectionToStringBuilder.toString(arriveSheet));//记录日志
		// 传入参数(运单号,当前运单号生效)
		WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(
				arriveSheet.getWaybillNo(), FossConstants.ACTIVE);
		// 根据运单号 查询运单签收结果
		WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(wayEntity);
		// 得到当前时间
		Date modifyTime = new Date();
		// 如果当前运单不是第一次添加
		if (waybillSign != null) {
			WaybillSignResultEntity waySign = new WaybillSignResultEntity();
			// 时效时间为当前时间
			waySign.setModifyTime(modifyTime);
			// 无效
			waySign.setActive(FossConstants.INACTIVE);
			// 得到运单签收结果的id
			waySign.setId(waybillSign.getId());
			// 运单签收结果里作废当前运单号
			waybillSignResultService.updateWaybillSignResult(waySign);
			// 得到签收人
			waybillSign.setDeliverymanName(arriveSheet.getDeliverymanName());
			waybillSign.setCreator(arriveSheet.getOperator());//操作人
			waybillSign.setCreatorCode(arriveSheet.getOperatorCode());//操作人编号
			// 生效时间为当前时间
			waybillSign.setCreateTime(modifyTime);
			// 时效时间为空，添加时采用默认值
			waybillSign.setModifyTime(null);
			// 添加运单签收结果信息
			waybillSignResultService.addWaybillSignResult(waybillSign);
		}

		//获取当前用户登录信息 --add by 2313438 快递100轨迹推动送
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，推送轨迹--add by 2313438 快递100轨迹推动送
		sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
		
		LOGGER.info("--PDA签收 (录入运单签收结果)结束");//记录日志
	}

	/**
	 * 录入拉回货物信息
	 * @author foss-meiying
	 * @date 2012-12-11 下午7:22:40
	 * @param dto
	 * 		dto.id
	 * 			派送单id
	 * 		dto.deliverbillNo
	 * 			派送单编号
	 * 		dto.waybillNo
	 * 			运单号
	 * 		dto.arrivesheetNo
	 * 			到达联编号
	 * 		dto.vehicleNo
	 * 			车牌号
	 * 		dto.driverCode
	 * 			司机工号
	 * 		dto.deliverymanName
	 * 			签收人
	 * 		dto.active
	 * 			到达联是否有效 
	 * 		dto.deliverStatus
	 * 			派送单状态
	 * 		dto.lastLoadOrgCode
	 * 			最终配载部门（判断是否为本部门）
	 * 		dto.createUserName
	 * 			创建人
	 * 		dto.createUserCode
	 * 			创建人编码
	 * 		dto.createTime
	 * 			创建时间
	 * 		dto.ids
	 * 			派送单id集合
	 * 		dto.deliverbillNos
	 * 			派送单编号集合
	 * 		dto.destroyed
	 * 			到达联是否作废
	 * 			派送单编号
	 * 		dto.tSrvStayHandoverId
	 * 			交接id
	 * @return   1 没有拉回货物
	 * 			 2 录入拉回货物成功
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService
	 * #addPullbackInfo
	 * (com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto)
	 */
	@Override
	public int addPullbackInfo(DeliverbillDetailDto dto, String transferCenter){
		LOGGER.info("--录入拉回货物信息 开始");//记录日志
		int result = SignConstants.ZERO;
		// 到达联有效
		dto.setActive(FossConstants.ACTIVE);
		//到达联未作废
		dto.setDestroyed(FossConstants.NO);
		List<StayHandoverDto> list = deliverHandlerDao.queryStayHandoverBydeliverbillNo(dto);
		if (list == null || SignConstants.ZERO == list.size()) {
			// 没有拉回货物
			result = SignConstants.ONE;
			LOGGER.info("--没有拉回货物");//记录日志
		} else {
			// 添加交接表信息
			StayHandoverEntity stayHandler = new StayHandoverEntity();
			Map<String, Integer> fastWaybillQty = new HashMap<String, Integer>();
			Map<String, Integer> afWaybillQty = new HashMap<String, Integer>();
			Map<String, Integer> flfWaybillQty = new HashMap<String, Integer>();
			Map<String, Integer> fsfWaybillQty = new HashMap<String, Integer>();
			int goodsQtyTotal = SignConstants.ZERO;
			// 总体积
			BigDecimal volumeTotal = BigDecimal.valueOf(SignConstants.ZERO);
			// 总重量
			BigDecimal weightTotal = BigDecimal.valueOf(SignConstants.ZERO);
			// 添加交接明细表
			for (StayHandoverDto stayHandoverDto : list) {
				Integer goodsQty = stayHandoverDto.getArriveSheetGoodsQty() - stayHandoverDto.getSignGoodsQty();
				BigDecimal volume = BigDecimalOperationUtil.mul(BigDecimalOperationUtil.div(stayHandoverDto.getGoodsVolumeTotal(), (new BigDecimal(stayHandoverDto.getGoodsQtyTotal())), 2),BigDecimal.valueOf(goodsQty), 2);
				BigDecimal weight = BigDecimalOperationUtil.mul(BigDecimalOperationUtil.div(stayHandoverDto.getGoodsWeightTotal(), (new BigDecimal(stayHandoverDto.getGoodsQtyTotal())), 2),
						BigDecimal.valueOf(goodsQty), 2);
				goodsQtyTotal += goodsQty;
				// 总体积
				volumeTotal = volumeTotal.add(volume);
				// 总重量
				weightTotal = weightTotal.add(weight);
				if(PRIORITY.equals(stayHandoverDto.getPriority())){//卡货票数
					if(!fastWaybillQty.containsKey(stayHandoverDto.getWaybillNo())){
						fastWaybillQty.put(stayHandoverDto.getWaybillNo(), SignConstants.ONE);
					}
				}
				// 如果运输性质为精准卡航
				if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(stayHandoverDto.getProductCode())) {
					if(!flfWaybillQty.containsKey(stayHandoverDto.getWaybillNo())){
						flfWaybillQty.put(stayHandoverDto.getWaybillNo(), SignConstants.ONE);
						stayHandler.setFlfWaybillVolume((stayHandler.getFlfWaybillVolume()==null ? BigDecimal.ZERO :stayHandler.getFlfWaybillVolume()).add(volume));
						stayHandler.setFlfWaybillWeight((stayHandler.getFlfWaybillWeight()==null ? BigDecimal.ZERO :stayHandler.getFlfWaybillWeight()).add(weight));
					}
				}else if(ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(stayHandoverDto.getProductCode())){
					if(!fsfWaybillQty.containsKey(stayHandoverDto.getWaybillNo())){
						fsfWaybillQty.put(stayHandoverDto.getWaybillNo(), SignConstants.ONE);
						stayHandler.setFsfWaybillVolume((stayHandler.getFsfWaybillVolume()==null ? BigDecimal.ZERO :stayHandler.getFsfWaybillVolume()).add(volume));
						stayHandler.setFsfWaybillWeight((stayHandler.getFsfWaybillWeight()==null ? BigDecimal.ZERO :stayHandler.getFsfWaybillWeight()).add(weight));
					}
					//精准城运
				}else if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(stayHandoverDto.getProductCode())){
					if(!afWaybillQty.containsKey(stayHandoverDto.getWaybillNo())){
						afWaybillQty.put(stayHandoverDto.getWaybillNo(), SignConstants.ONE);
						stayHandler.setAfWaybillVolume((stayHandler.getAfWaybillVolume()==null ? BigDecimal.ZERO :stayHandler.getAfWaybillVolume()).add(volume));
						stayHandler.setAfWaybillWeight((stayHandler.getAfWaybillWeight()==null ? BigDecimal.ZERO :stayHandler.getAfWaybillWeight()).add(weight));
					}
					//精准空运
				}
			}
			stayHandler.setAfWaybillQty(afWaybillQty.size());//空运票数
			stayHandler.setFlfWaybillQty(flfWaybillQty.size());//卡货票数
			stayHandler.setFsfWaybillQty(fsfWaybillQty.size());//城运票数
			// 总体积
			stayHandler.setVolumeTotal(volumeTotal);
			// 总重量
			stayHandler.setWeightTotal(weightTotal);
			// 总件数
			stayHandler.setGoodsQtyTotal(goodsQtyTotal);
			// 卡货票数
			stayHandler.setFastWaybillQty(fastWaybillQty.size());
			// 交接表的id
			stayHandler.setId(UUIDUtils.getUUID());
			// 交接类型--货物拉回
			stayHandler.setHandoverType(DeliverHandlerConstants.HANDOVER_TYPE_BACK);
			// 创建时间
			stayHandler.setCreateTime(dto.getCreateTime());
			// 车牌号
			stayHandler.setVehicleNo(list.get(SignConstants.ZERO).getVehicleNo());
			// 创建人
			stayHandler.setCreateUserName(dto.getCreateUserName());
			// 创建人编码
			stayHandler.setCreateUserCode(dto.getCreateUserCode());
			// 驻地外场
			stayHandler.setTransferCenter(transferCenter);
			// 添加交接信息
			stayHandoverDao.addStayHandover(stayHandler);
			
			//交接id
			dto.settSrvStayHandoverId(stayHandler.getId());
			//根据派送单号修改交接id
			stayHandoverDetailService.updateByDeliverbillNo(dto);
			result = SignConstants.TWO;//返回结果
		}
		for (String id : dto.getIds()) {
			// 修改派送单状态为'已签收确认'
			DeliverbillDetailDto deliverbillDto = new DeliverbillDetailDto();
			deliverbillDto.setId(id);
			// 派送单状态为"已签收确认"
			deliverbillDto.setDeliverStatus(DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED);//修改派送单状态
			if(deliverHandlerDao.updateDeliverbill(deliverbillDto)<=0){
				return SignConstants.THREE;
			}else{
				DeliverbillEntity deliverbill = deliverbillNewService.queryDeliverbill(id);
				if (StringUtils.isNotBlank(deliverbill.getDeliverbillNo())) {
					DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
					deliverBillVary.setDeliverBillNo(deliverbill.getDeliverbillNo());//派送单号
					deliverBillVary.setDeliverBillStatus(deliverbillDto.getDeliverStatus());//派送单状态
					//添加纪录
					deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
				}
				
			}
		}
		LOGGER.info("--录入拉回货物信息 结束");//记录日志
		return result;
	}

	/**
	 * 派送处理---
	 * 		送货确认 录入拉回货物信息量
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:15:30
	 * @param dto 派送单明细dto
	 * 		dto.id
	 * 			派送单id
	 * 		dto.deliverbillNo
	 * 			派送单编号
	 * 		dto.waybillNo
	 * 			运单号
	 * 		dto.arrivesheetNo
	 * 			到达联编号
	 * 		dto.vehicleNo
	 * 			车牌号
	 * 		dto.driverCode
	 * 			司机工号
	 * 		dto.deliverymanName
	 * 			签收人
	 * 		dto.active
	 * 			到达联是否有效 
	 * 		dto.deliverStatus
	 * 			派送单状态
	 * 		dto.lastLoadOrgCode
	 * 			最终配载部门（判断是否为本部门）
	 * 		dto.createUserName
	 * 			创建人
	 * 		dto.createUserCode
	 * 			创建人编码
	 * 		dto.createTime
	 * 			创建时间
	 * 		dto.ids
	 * 			派送单id集合
	 * 		dto.deliverbillNos
	 * 			派送单编号集合
	 * 		dto.destroyed
	 * 			到达联是否作废
	 * 			派送单编号
	 * 		dto.tSrvStayHandoverId
	 * 			交接id
	 * @param currentInfo	
	 * 			当前登录人
	 * 			currentInfo.operate 
	 * 				操作人
	 *          currentInfo.operatorCode 
	 *          	操作人编码
	 *          currentInfo.operateOrgName 
	 *          	操作部门名称
	 *          currentInfo.operateOrgCode 
	 *          	操作部门编码
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService
	 * 		#addPullbackGoods(com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public int addPullbackGoods(DeliverbillDetailDto dto,CurrentInfo currentInfo) {
		int result = SignConstants.ZERO;//返回结果
		// 创建时间
		dto.setCreateTime(new Date());
		// 创建人编码
		dto.setCreateUserCode(currentInfo.getEmpCode());
		// 创建人
		dto.setCreateUserName(currentInfo.getEmpName());
		// 驻地外场
		String transferCenter = getTransferCenter(currentInfo.getCurrentDeptCode());
		result = this.addPullbackInfo(dto, transferCenter);
		return result;
	}
	
	/**
	 * 
	 * 获取对应的营业部或驻地外场
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-24 下午2:15:05
	 */
	private String getTransferCenter(String code){
		List<String> endStockOrgCodes = handleQueryOutfieldService.getEndStockCodeAndAreaCode(code);
		// 获取对应的营业部或驻地外场
		if(CollectionUtils.isNotEmpty(endStockOrgCodes) && StringUtils.isNotEmpty(endStockOrgCodes.get(0))){
			return endStockOrgCodes.get(0);
		}
		return DeliverHandlerConstants.DEFAULT_TRANSFER_CENTER;
	}

	/**
	 * 查询当前派送单中
	 * 所有的运单到达联是否都完成了签收确认
	 * @author 
	 * 		foss-meiying
	 * @date 
	 * 		2012-12-18 下午7:16:42
	 * @param dto派送单明细dto
	 * 		 dto.id
	 * 			派送单id
	 * 		dto.deliverbillNo
	 * 			派送单编号
	 * 		dto.waybillNo
	 * 			运单号
	 * 		dto.arrivesheetNo
	 * 			到达联编号
	 * 		dto.vehicleNo
	 * 			车牌号
	 * 		dto.driverCode
	 * 			司机工号
	 * 		dto.deliverymanName
	 * 			签收人
	 * 		dto.active
	 * 			到达联是否有效 
	 * 		dto.deliverStatus
	 * 			派送单状态
	 * 		dto.lastLoadOrgCode
	 * 			最终配载部门（判断是否为本部门）
	 * 		dto.createUserName
	 * 			创建人
	 * 		dto.createUserCode
	 * 			创建人编码
	 * 		dto.createTime
	 * 			创建时间
	 * 		dto.ids
	 * 			派送单id集合
	 * 		dto.deliverbillNos
	 * 			派送单编号集合
	 * 		dto.destroyed
	 * 			到达联是否作废
	 * 		dto.deliverbillNo
	 * 			派送单编号
	 * 		dto.tSrvStayHandoverId
	 * 			交接id
	 * @return
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService
	 * #queryArrivesheetIsSign
	 * (com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto)
	 */
	@Override
	public List<DeliverbillDetailDto> queryArrivesheetIsSign(DeliverbillDetailDto dto) {
		List<String> list = new ArrayList<String>();
		// 到达联状态 为签收
		list.add(ArriveSheetConstants.STATUS_SIGN);
		// 到达联状态 拒收
		list.add(ArriveSheetConstants.STATUS_REFUSE);
		dto.setStatus(list);
		// 有效
		dto.setActive(FossConstants.ACTIVE);
		//到达联未作废 
		dto.setDestroyed(FossConstants.NO);
		//签收
		dto.setStatusSign(ArriveSheetConstants.STATUS_SIGN);
		return deliverHandlerDao.queryArrivesheetIsSign(dto); //查询到达联是否签收
	}
	/**
	 * 
	 * 根据运单号查询部分派送单信息（综合查询提供到达联派送中接口）
	 * 
	 * @return 
	 * @author foss-meiying
	 * @date 2013-08-7 下午2:54:18
	 */
	public List<DeliverbillDetailDto> queryPartDeliverBillByWaybillNo(DeliverbillDetailDto dto){
		return deliverHandlerDao.queryPartDeliverBillByWaybillNo(dto);
	}
	/**
	 * Sets 
	 * 		the 派送处理Dao.
	 *
	 * @param deliverHandlerDao 
	 * 		the new 派送处理Dao
	 */
	public void setDeliverHandlerDao(IDeliverHandlerDao deliverHandlerDao) {
		this.deliverHandlerDao = deliverHandlerDao;
	}

	/**
	 * Sets 
	 * 		the 到达联Service.
	 *
	 * @param arriveSheetManngerService 
	 * 		the new 到达联Service
	 */
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	/**
	 * Sets 
	 * 		the 运单签收结果Service.
	 *
	 * @param waybillSignResultService 
	 * 		the new 运单签收结果Service
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * Sets 
	 * 		the 交接Dao.
	 *
	 * @param stayHandoverDao 
	 * 		the new 交接Dao
	 */
	public void setStayHandoverDao(IStayHandoverDao stayHandoverDao) {
		this.stayHandoverDao = stayHandoverDao;
	}
	/**
	 * Sets
	 * 		 the 结算签收Service.
	 *
	 * @param lineSignService 
	 * 		 the new 结算签收Service
	 */
	public void setLineSignService(ILineSignService lineSignService) {
		this.lineSignService = lineSignService;
	}

	/**
	 * Sets 
	 * 		the 结算应收单服务.
	 *
	 * @param billReceivableService
	 * 	 the new 结算应收单服务
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * Sets 
	 * 		the 结清货款Service.
	 *
	 * @param repaymentService 
	 * 		the new 结清货款Service
	 */
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}


	/**
	 * Sets 
	 * 		the 运单状态服务接口.
	 *
	 * @param actualFreightService 
	 * 		the new 运单状态服务接口
	 */
	public void setActualFreightService(
			IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	
	/**
	 * Sets 
	 * 		the iDeliverLoadTaskService.
	 *
	 * @param deliverLoadTaskService 
	 * 		the new iDeliverLoadTaskService
	 */
	public void setDeliverLoadTaskService(IDeliverLoadTaskService deliverLoadTaskService) {
		this.deliverLoadTaskService = deliverLoadTaskService;
	}
	/**
	 * Sets 
	 * 		the 
	 * 		签收明细service.
	 *
	 * @param signDetailService 
	 * 		the new 签收明细service
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}	
	/**
	 * Sets 
	 * 		the 
	 * 		交接 流水号service.
	 *
	 * @param stayHandoverserialService 
	 * 		the new 交接 流水号service
	 */
	public void setStayHandoverserialService(IStayHandoverserialService stayHandoverserialService) {
		this.stayHandoverserialService = stayHandoverserialService;
	}
	
	/**
	 * Sets 
	 * 		the 
	 * 		交接明细Service.
	 *
	 * @param stayHandoverDetailService 
	 * 		the 
	 * 		new 交接明细Service
	 */
	public void setStayHandoverDetailService(IStayHandoverDetailService stayHandoverDetailService) {
		this.stayHandoverDetailService = stayHandoverDetailService;
	}
	
	/**
	 * Sets 
	 * 		the 
	 * 		业务互斥锁服务.
	 *
	 * @param businessLockService
	 * 		 the 
	 * 		new 业务互斥锁服务
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	public void setExceptionProcessService(IExceptionProcessService exceptionProcessService) {
		this.exceptionProcessService = exceptionProcessService;
	}
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	public void setTodoActionService(ITodoActionService todoActionService) {
		this.todoActionService = todoActionService;
	}
	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	public void setRecordErrorWaybillService(
			IRecordErrorWaybillService recordErrorWaybillService) {
		this.recordErrorWaybillService = recordErrorWaybillService;
	}

	public void setDeliverHandoverbillService(
			IDeliverHandoverbillService deliverHandoverbillService) {
		this.deliverHandoverbillService = deliverHandoverbillService;
	}

	public void setDeliverBillVaryStatusService(
			IDeliverBillVaryStatusService deliverBillVaryStatusService) {
		this.deliverBillVaryStatusService = deliverBillVaryStatusService;
	}

	public void setDeliverbillNewService(
			IDeliverbillNewService deliverbillNewService) {
		this.deliverbillNewService = deliverbillNewService;
	}
	
}