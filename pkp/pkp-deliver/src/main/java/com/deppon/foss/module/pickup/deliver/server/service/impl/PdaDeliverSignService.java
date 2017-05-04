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
 * PROJECT NAME	: pkp-deliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/deliver/server/service/impl/PdaDeliverSignService.java
 * 
 * FILE NAME        	: PdaDeliverSignService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 *****************************************************************************
 * suc用例
 * 修订记录 
	日期 	修订内容 
	修订人员 	版本号 
	2012-06-11 
	新增	王辉	V0.1
	2012-07-05	
	根据ITA建议修改	王辉	V0.2
	2012-07-25	
	Jira审批通过，升至0.9版本	王辉	V0.9
	  	  	  	 
	
	1.
	SUC-446–签收接口
	1.1	
	相关业务用例
	BUC_FOSS_5.50.10_530 PDA客户货物签收
	1.2
	用例描述
	司机使用PDA签收货物，调用签收接口。
	1.3
	用例条件
	条件类型	描述	引用系统用例
	前置条件
	1.	送货任务下发至PDA	1.
		获取派送任务（PDA）-SUC-358
	查询送货任务接口-SUC-464
	后置条件	无	
	1.4	
		操作用户角色
		操作用户	描述
		无	
	1.5	
		界面要求
	1.5.1	
		表现方式
	接口
	1.5.2	
		界面原型
	无
	1.5.3	
		界面描述
	无
	1.6	
		操作步骤
	序号	基本步骤	相关数据	补充步骤
	1	
		传入参数	签收信息	
	2	
		校验运单号	签收信息	运单号存在，继续步骤3
	3	
		校验到达联编号	签收信息	到达联状态为“派送中”，继续步骤4
	4	
		返回结果信息	结果信息	
	1、	
		系统记录PDA签收信息
	2、	
		系统更新到达联信息，包括状态、操作人、操作时间、签收部门
	3、	
		系统更新运单签收状态，参考SR3
	4、	
		调用结清货款接口，参考SR2
	5、	
		调用结算签收接口，参考SR1
	6、	
		调用短信接口发送短信至发货人、收货人
	7、	
		调用在线通知接口发送在线通知至发货部门
	
	
	扩展事件写非典型或异常操作过程
	序号	扩展事件	相关数据	备注
	2a	运单号不存在，返回结果信息“运单号不存在，请检查货物单号！” ，终止操作	结果信息	
	3a	到达联状态非“派送中”，系统返回结果信息“到达联状态非可签收状态” ，终止操作	结果信息	
	4a	返回结果PDA未收到，PDA再次发送请求，直接返回结果信息“签收成功”，终止操作	结果信息	
	4b	若签收情况为“部分签收”,系统记录签收拉回的货物信息		
	4c	调用结算子系统“结清货款接口”异常时，将结算子系统接口中异常信息返回，终止操作	结果信息	
	4d	调用结算子系统“签收接口”异常时，将结算子系统接口中异常信息返回，终止操作	结果信息	
	4e	调用短信接口异常时，将短信接口中的异常信息返回，不终止操作		
	4f	调用在线通知接口异常时，将在线通知接口中的异常信息返回，不终止操作		
	
	1.7	
		业务规则
	序号	描述
	SR1	
		1.	异常丢货、弃货、违禁品签收调用结算接口：SUC-159
		2.	专线正常签收、其他异常签收调用结算接口：SUC-597
	SR2	
		1.	
		付款方式为临欠或月结时：
		调用结算接口-到付运费结转临欠月结（SUC-780）
		2.
		付款方式为其他方式时：
		调用结算接口-实收货款（SUC-779）
	SR3
		若签收最后一个到达联，系统更新运单签收状态
		1.	
		到达联签收状态全部为“正常签收”，运单签收状态为“正常签收”
		2.	
		到达联签收状态中存在非“正常签收”，运单签收状态为“异常签收”
	
	1.8
		数据元素
	1.8.1
		签收信息 
	字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
	运单号		文本		20		
	件数		文本		10		
	到达联编号		文本		20		
	签收情况		文本		5		
	操作人		文本		10		
	操作时间		文本		6		格式为“yyyy-MM-dd HH:mm:ss”
	部门	司机所属部门	文本		10		
	货物件信息集合		文本			是	参考数据元素“货物件信息”
	
	1.8.2	
		货物件信息
	字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
	流水号		文本		10		
	签收情况		文本		6		包括正常签收、异常签收
	签收备注		文本		20		
	
	1.8.3	
		结果信息
	字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
	结果码		文本		5		
	结果详情		文本		200		
	
	1.9
		非功能性需求
	使用量	每天签收8W运单，按集中送货60%，4.8W次/天
	2012年全网估计用户数	1000-2000用户
	响应要求（如果与全系统要求 不一致的话）	3s内响应
	使用时间段	08:00-12:00
	高峰使用时间段	08:00-12:00
	
	1.10	
		接口描述：
	接口名称 	对方系统（外部系统或内部其他模块）	接口描述
	同步接口	官网	调用官网接口反馈签收信息，10分钟内签收信息同步完成
	结清货款接口	Foss-结算子系统	结算的结清货款接口
	签收接口	FOSS-结算子系统	结算的签收接口
	短信接口	短信平台	发送短信给发货人、收货人
	在线通知接口	Foss-综合管理子系统	发送通知给发货部门


 **/
package com.deppon.foss.module.pickup.deliver.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.deliver.server.utils.HttpClientUtils;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverSignService;
import com.deppon.foss.module.pickup.pda.api.shared.define.PdaConstants;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaBatchDeliverSignDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignShellDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSignDetailDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverHandoverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IFimsPdaService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IGpsPdaService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillReturnDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IPickupService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRecordErrorWaybillService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRookieService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.DeliverHandlerConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.PickupConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.util.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
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
import com.deppon.foss.module.settlement.consumer.api.server.service.ICUBCSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCSignOrRevSignResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FOSSSignOrRevSignRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCSignException;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeByHandService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 派送签收接口service实现
 * (司机使用PDA签收货物，
 * 调用签收接口)
 * SUC-446–签收接口
 * @author 
 * 			foss-meiying
 * @date 
 * 			2012-11-28 上午11:51:55 
 * @since
 * @version
 */
public class PdaDeliverSignService implements IPdaDeliverSignService {
	
	private IWaybillSignResultDao waybillSignResultDao;
	public void setWaybillSignResultDao(IWaybillSignResultDao waybillSignResultDao) {
		this.waybillSignResultDao = waybillSignResultDao;
	}
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.deliver.server.service.impl.PdaDeliverSignService";
	
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
	private static final Logger LOGGER = LoggerFactory.getLogger(PdaDeliverSignService.class);
	/**
	 * 运单签收结果
	 * service 
	 */
	private IWaybillSignResultService waybillSignResultService;
	
	/**
	 * 运单快递相关服务类接口
	 */
	private IWaybillExpressService waybillExpressService;
	
	/***
	 * 记录异常运单 上报OA的Service
	 */
	private IRecordErrorWaybillService recordErrorWaybillService; 
	
	/**
	 * 到达联管理
	 * 	接口
	 */
	private IArriveSheetManngerService arriveSheetManngerService;
	/**
	 * 运单
	 * service
	 * 	接口
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 结清货款
	 * Service接口
	 */
	private IRepaymentService repaymentService;
	/**
	 * 结算签收
	 * Service接口
	 */
	private ILineSignService lineSignService;
	/**张新
	 * 打印代理面单service
	 */
	private IPrintAgentWaybillService printAgentWaybillService;
	/**
	 * 员工
	 * service接口
	 */
	private IEmployeeService employeeService;
	/**
	 * 组织信息
	 * service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 签收明细
	 * service接口
	 */
	private ISignDetailService signDetailService;
	/**
	 * actualFreightService接口
	 */
	private IActualFreightService actualFreightService;
	/**
	 *  结算应收单服务
	 *  接口
	 */
	private IBillReceivableService billReceivableService;
	/**
	 * 处理异常
	 * 主数据Service层
	 */
	private IExceptionProcessService exceptionProcessService;
	/**
	 * 派送单
	 * service接口
	 */
	private IDeliverbillService deliverbillService;
	/**
	 * 交接 流水号
	 * service接口
	 */
	private IStayHandoverserialService stayHandoverserialService;
	/**
	 * 交接明细
	 * Service接口
	 * 
	 */
	private IStayHandoverDetailService stayHandoverDetailService;
	private IActualFreightDao actualFreightDao;
	/**
	 * 代办事项服务类
	 */
	private ITodoActionService todoActionService;
	private IGpsPdaService gpsPdaService;
	/**
	 *  中转出库接口
	 */
	private IStockService stockService;
	
	/**
	 * 快递点部营业部映射关系Service
	 */
	IExpressPartSalesDeptService expressPartSalesDeptService;
	
	/**
	 * 异步发送消息至发票系统服务类
	 */
	private IFimsPdaService fimsPdaService;
	
	/**
	 * service层
	 */
	private  IPickupService pickupService;
	
	/**
	 * 运单Dao
	 */
	private IWaybillDao waybillDao;

	/**
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	//待人工补码记录service
	private IAutoAddCodeByHandService autoAddCodeByHandService;
 
	public void setAutoAddCodeByHandService(
			IAutoAddCodeByHandService autoAddCodeByHandService) {
		this.autoAddCodeByHandService = autoAddCodeByHandService;
	}
	
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
	/**
	 * 交接单服务接口 
	 */
	private IDeliverHandoverbillService deliverHandoverbillService;
	public void setDeliverHandoverbillService(
			IDeliverHandoverbillService deliverHandoverbillService) {
		this.deliverHandoverbillService = deliverHandoverbillService;
	}
	
	/**
	 * 菜鸟相关服务类接口
	 */
	/**
	 ** 轨迹推送接口（快递100,菜鸟） 
	 */
	private ISendWaybillTrackService sendWaybillTrackService; 
	/**
	 * 轨迹推送接口 注入
	 */
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	/**
	 * 菜鸟相关服务类接口
	 */
	private IRookieService rookieService;

    /**
     * T+0相关注入
     */
    private IPdaPosManageService pdaPosManageService;
    private IRepaymentDao repaymentDao;

    public void setRepaymentDao(IRepaymentDao repaymentDao) {
        this.repaymentDao = repaymentDao;
    }

    /**
	 * 验证传入的参数是否为空
	 * 如果为空抛出异常信息
	 * @author
	 * 		 foss-meiying
	 * @date 
	 * 		2013-3-9 下午4:36:19
	 * @param dto
	 * 		dto.waybillNo 
	 * 				运单号
	 * 			dto.arrivesheetNo
	 * 				 到达联编号
	 * 			dto.signDeptCode
	 * 				 签收部门
	 * 			dto.signTime
	 * 				 签收时间
	 * 			dto.signGoodsQty
	 * 				 签收件数
	 * 			dto.pdaSignDetailDtos
	 * 				 流水号集合
	 * 				dto.pdaSignDetailDtos.situation
	 * 					 签收情况 
	 * 					REFUSE为拒收
	 * 					SIGN为签收
	 * 				dto.pdaSignDetailDtos.situation.serialNo
	 * 					流水号
	 * 			dto.situation
	 * 				签收情况
	 * 				（正常签收、
	 * 				异常-破损、
	 * 				异常-潮湿、
	 * 				异常-污染、
	 * 				异常-其他、
	 * 				部分签收）
	 * 			dto.vehicleNo
	 * 				车牌号
	 * 			dto.driverCode
	 * 				司机工号
	 * 			dto.paymentType
	 * 				付款方式
	 * 			（现金、临欠、月结、银行卡、支票、电汇）
	 * 			dto.signNote
	 * 				签收备注
	 * @see
	 */
	private void validateParams(PdaDeliverSignDto dto) {
		/**
		 * 接口传入的数据为空
		 */
		if (dto == null) {
			LOGGER.error("接口传入的数据为空");//记录日志
			throw new PdaProcessException("接口传入的数据为空");
		}else if (StringUtils.isBlank(dto.getWaybillNo())) {//如果传入的运单号为空
			LOGGER.error("运单号为空，不能签收出库");//记录日志
			throw new PdaProcessException("运单号为空，不能签收出库");//运单号为空，不能签收出库
		} else if (StringUtils.isBlank(dto.getArrivesheetNo())){//如果到达联编号为空
			LOGGER.error("到达联编号为空！,不能签收出库");//记录日志
			throw new PdaProcessException("到达联编号为空！,不能签收出库");//到达联编号为空！,不能签收出库
		} else if(StringUtils.isBlank(dto.getSignDeptCode())) {//如果签收部门为空
			LOGGER.error("签收部门为空，不能签收出库");//记录日志
			throw new PdaProcessException("签收部门为空，不能签收出库");//签收部门为空，不能签收出库
		}else if(StringUtils.isBlank(dto.getPaymentType())) {//如果付款方式为空
			LOGGER.error("付款方式为空，不能签收出库");//记录日志
			throw new PdaProcessException("付款方式为空，不能签收出库");//签收部门为空，不能签收出库
		}else if(dto.getSignTime() == null) {//如果签收时间为空
			LOGGER.error("签收时间为空，不能签收出库");//记录日志
			throw new PdaProcessException("签收时间为空，不能签收出库");//签收时间为空，不能签收出库
		}else if(dto.getSignGoodsQty() == null || dto.getSignGoodsQty().intValue() == 0) {
			LOGGER.error("签收件数为0，不能签收出库");//记录日志
			throw new PdaProcessException("签收件数为0，不能签收出库");//签收件数为0，不能签收出库
		}else if(CollectionUtils.isEmpty(dto.getPdaSignDetailDtos())){
			LOGGER.error("传入的流水号为空，不能签收出库");//记录日志
			throw new PdaProcessException("传入的流水号为空，不能签收出库");//传入的流水号为空，不能签收出库
		}else {//其他情况
			int count = 0;//默认值
			for (PdaSignDetailDto pdaSignDetailDto : dto.getPdaSignDetailDtos()) {//循环得到传入的流水号集合
				if(PdaConstants.SITUATION_SIGN.equals(pdaSignDetailDto.getSituation())){//如果流水号的情况签收
					count++;//统计流水号的情况为签收的数量
				}
			}
			if(dto.getSignGoodsQty() != count){//传入的流水号集合为签收的有效件数与签收件数不一致，不能签收出库
				LOGGER.error("传入的流水号集合为签收的有效件数与签收件数不一致，不能签收出库");//记录日志
				throw new PdaProcessException("传入的流水号集合为签收的有效件数与签收件数不一致，不能签收出库");//传入的流水号集合为签收的有效件数与签收件数不一致，不能签收出库
			}
		}
	}
	/**
	 * 
	 * pda签收出库操作
	 * @author 
	 * 			foss-meiying
	 * @date 
	 * 			2012-12-19 上午11:35:34
	 */
	@Override
	public String pdaSign(PdaDeliverSignDto dto) {
		String resultMsg = ""; //信息
		LOGGER.info("-- 开始签收出库操作---- "+ReflectionToStringBuilder.toString(dto));//记录日志
		this.validateParams(dto);//验证传的值
		WaybillEntity waybill = new WaybillEntity();
		//查询运单号在运单表里面是否存在
		//如果不存在
		if(!waybillManagerService.isWayBillExsits(dto.getWaybillNo())){
			StringBuffer errorMsg = new StringBuffer();//拼接错误信息
			errorMsg.append("运单号 :");
			errorMsg.append(dto.getWaybillNo());//运单号
			errorMsg.append(" 不存在，请检查货物单号！");
			LOGGER.error(errorMsg.toString());//记录运单号不存在信息
			throw new PdaProcessException("该运单号在系统中不存在，不能签收出库");
		} else {//运单号存在
			// 根据到达联编号,到达联状态为‘派送中’查运单运输性质  ，
			//是否整车运单,到达联id,证件号码,证件类型,提货人,到达联件数,订单号,提货方式
			SignDto signDto = arriveSheetManngerService.queryPartWaybillpartArrivesheet(dto.getArrivesheetNo(),ArriveSheetConstants.STATUS_DELIVER);
			if(signDto == null){//查询结果为空
				LOGGER.error("--找不到有效状态的到达联,不能签收出库");//记录日志
				throw new PdaProcessException("找不到有效状态的到达联,不能签收出库");
			}else {
				if(!dto.getWaybillNo().equals(signDto.getWaybillNo())){
					throw new PdaProcessException("传入的运单号跟到达联对应的运单号不一致，不能签收");//传入的运单号跟到达联对应的运单号不一致，不能签收
				}
				signDto.setSystemDate(new Date());//系统时间
				BeanUtils.copyProperties(signDto,waybill);//把signDto里的内容复制到waybill里
				if(!signDto.getArriveSheetGoodsQty().equals(dto.getPdaSignDetailDtos().size())){
					LOGGER.error("--传入的流水号集合与到达联件数不一致,不能签收出库");//记录日志
					throw new PdaProcessException("传入的流水号集合与到达联件数不一致,不能签收出库");
				}
			}
			dto.setSignDeptCode(signDto.getLastLoadOrgCode());//签收部门为最终配载部门
			CurrentInfo currentInfo = this.getCurrentInfo(dto);
			// 判断当前运单是否结清，若未结清，则调用货款结清接口进行结清操作。
			// 未结清
			if (!repaymentService.isPayment(dto.getWaybillNo())) {
				RepaymentEntity repayment = new RepaymentEntity();
				// 运单号
				repayment.setWaybillNo(dto.getWaybillNo());
				//PDA结清     243921
				repayment.setSettleApproach(SettlementDictionaryConstants.SETTLE_APPROACH_MOBILE);
				// 付款方式
				repayment.setPaymentType(dto.getPaymentType());
				BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(dto.getWaybillNo());
				//财务单据查询，灰度改造   353654 ---------------------------start 
				List<BillReceivableEntity> billReceivableEntities = null;
				String vestSystemCode = null;
	            try {
	            	List<String> arrayList = new ArrayList<String>();
	            	arrayList.add(dto.getWaybillNo());
	            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
	            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".pdaSign",
	            			SettlementConstants.TYPE_FOSS);
	            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	            	List<VestBatchResult> list = response.getVestBatchResult();
	            	vestSystemCode = list.get(0).getVestSystemCode();	
				} catch (Exception e) {
					LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				}
				if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
					LOGGER.info("FOSS查询财务单据开始!运单号："+ dto.getWaybillNo());
					billReceivableEntities = billReceivableService.queryReceivableAmountByCondition(billReceiveable);
					LOGGER.info("FOSS查询财务单据完成!运单号："+ dto.getWaybillNo());
				}
				if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
					try {
						billReceivableEntities = cubcQueryReceivableAmountService.queryReceivableAmount(dto.getWaybillNo());			
					} catch (Exception e) {
						LOGGER.error("调用CUBC结清查询财务单据异常信息为："+e.getMessage());
						throw new PdaProcessException("呀,亲!系统繁忙,CUBC查询财务单据失败,请稍后重试!");
					}
				}
				//财务单据查询，灰度改造   353654 ---------------------------end
				if(!CollectionUtils.isEmpty(billReceivableEntities)){
					for (BillReceivableEntity billReceivableEntity : billReceivableEntities) {
						// 到达应收单
						if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
							// 实付运费
							repayment.setActualFreight(billReceivableEntity.getUnverifyAmount());
							//银行交易流水号--到付流水号
							repayment.setBankTradeSerail(dto.getBankTradeSerail());
						}
						// 代收货款应收单
						else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
							repayment.setCodAmount(billReceivableEntity.getUnverifyAmount());// 代收货款
							//银行交易流水号--代收货款流水号
							repayment.setBankTradeSerail(dto.getCodBankTradeSerail());
						}
						// 合伙人到付运费应收单
						else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(billReceivableEntity.getBillType())){
							// 实付运费
							repayment.setActualFreight(billReceivableEntity.getUnverifyAmount());
							//银行交易流水号--到付流水号
							repayment.setBankTradeSerail(dto.getBankTradeSerail());
						}
					}
				}
				repayment.setActualFreight(repayment.getActualFreight() == null ? BigDecimal.ZERO:repayment.getActualFreight());
				repayment.setCodAmount(repayment.getCodAmount() == null? BigDecimal.ZERO :repayment.getCodAmount());
				//付款时间
				repayment.setPaymentTime(signDto.getSystemDate());
				try {
					// 调结清货款进行结清
					repaymentService.deliverOperate(repayment, currentInfo);
				} catch (RepaymentException e) {//捕获异常
					LOGGER.error("--调用结清货款抛出异常:"+e.getErrorCode());//记录异常信息
					throw new PdaProcessException(e.getErrorCode(),e);//抛出捕获的异常
				}
				LOGGER.info("调用结清货款完成");//记录日志
			}else {
				LOGGER.info("当前运单已经结清，不需要调用货款结清接口进行结清操作");//记录日志
			}
			if(WaybillConstants.INNER_PICKUP.equals(signDto.getReceiveMethod())){//提货方式为汽运内部自提
				LOGGER.info("--提货方式为汽运内部自提 不需要调用结算签收接口----");//记录日志
			}else {
				this.confirmTaking(dto,currentInfo,signDto);//调用结算子系统“签收接口”
			}
			ActualFreightEntity actual = actualFreightService.queryByWaybillNo(dto.getWaybillNo());
			boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
			//如果到达联件数大于签收件数 录入交接流水号
			if(signDto.getArriveSheetGoodsQty() > dto.getSignGoodsQty()){
				this.operatPullbackGoods(dto, signDto, currentInfo);//对拉回货物信息进行操作
			}
			//如果签收件数小于运单开单件数
			if(dto.getSignGoodsQty() != null &&dto.getSignGoodsQty()<waybill.getGoodsQtyTotal()){
				List<String> serials = new ArrayList<String>();
				for (PdaSignDetailDto pdaSignDetailDto : dto.getPdaSignDetailDtos()) {//循环得到流水号集合
					if(PdaConstants.SITUATION_SIGN.equals(pdaSignDetailDto.getSituation())){//如果流水号的情况为签收
						//流水号
						serials.add(pdaSignDetailDto.getSerialNo());
						InOutStockEntity inOutStock = new InOutStockEntity();
						// 运单号
						inOutStock.setWaybillNO(dto.getWaybillNo());
						// 流水号
						inOutStock.setSerialNO(pdaSignDetailDto.getSerialNo());
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
							throw new PdaProcessException(e.getErrorCode(),e);//抛出异常
						}
					}
				}
				todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),serials);
			}else {
				todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),null);
			}
			ActualFreightDto actualFreightDto = new ActualFreightDto(dto.getWaybillNo(),dto.getSignGoodsQty());
			actualFreightDto.setPaymentType(dto.getPaymentType());//付款方式
			//PDA签收 修改运单状态里的到达未出库件数
			actualFreightService.updateArriveNotoutGoodsQtyByWaybillNo(actualFreightDto);
			//更新到达联信息
			ArriveSheetEntity entity = this.updateArriveSheet(dto,currentInfo,signDto);
			try {
				//update by--FOSS bieyexiong 
				//对签收明细进行处理,返回值用于判断 流水号是否有'内物短少'
				boolean isGoodshort = this.operatSignDetailDtos(dto);
				WaybillSignResultEntity wayEntity = waybillSignResultService.checkWaybillSignResultSign(entity,waybill,signDto.getSystemDate(),currentInfo);
				// 是PDA签收
				wayEntity.setIsPdaSign(FossConstants.YES);
				// 添加运单签收结果信息
				waybillSignResultService.addWaybillSignResult(wayEntity);
				//菜鸟轨迹 add by 231438；封装的轨迹方法 赋值，判断是否符合条件 调用中转的轨迹接口
				sendWaybillTrackService.rookieTrackingsForSign(wayEntity);
				//pda签收，推送轨迹 --add by 231438，快递100，轨迹推送需求RFOSS2015031706
				sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
				//记录异常-内物短少差错 上报OA
				if(isGoodshort){
					this.saveRecordShortErrorInfo(dto,entity,currentInfo);
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
				//update
				if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){//如果签收状态为全部签收
					waybillSignResultService.updateCrmOrder(signDto.getOrderNo(), currentInfo, wayEntity);
				}
			} catch (BusinessException e) {//捕获异常
				throw new PdaProcessException(e.getErrorCode(), e);//抛出异常
			}
			WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
			BeanUtils.copyProperties(entity,waybillSigndto);
			//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
			resultMsg =waybillSignResultService.sendMessNotice(waybill, currentInfo,waybillSigndto);
			//应用监控签收调用
			waybillSignResultService.signCounterByWaybillNo(currentInfo,waybill);
			gpsPdaService.pDaDeliverSignToGps(dto.getArrivesheetNo(), dto.getWaybillNo(), dto.getSignTime());
			
			if(StringUtils.isNotEmpty(dto.getIsofferInvoice()) && FossConstants.YES.equals(dto.getIsofferInvoice())){
				//异步发送
				fimsPdaService.asynSendOtherRenueInfoToFims(dto);
			}
			if(isSendInvoiceInfo){
				//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
				waybillSignResultService.sendInvoiceInfo(waybill,actual);
			}
		}
		//签收出库成功,记录日志
		return StringUtils.isNotBlank(resultMsg) ? resultMsg : "PDA签收出库完成!";
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
				if(!CollectionUtils.isEmpty(endStockOrgCodes) && StringUtils.isNotEmpty(endStockOrgCodes.get(0))){
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
	 * @clasaName:com.deppon.foss.module.pickup.sign.server.service.impl.PdaDeliverSignService.recordErrorWaybill
	 * @author: foss-yuting
	 * @description: foss记录内物短少差错 上报OA PDA
	 * @date:2014年12月27日 下午15:59:21
	 * update by 231434 2015-7-3
	 */
	private void saveRecordShortErrorInfo(PdaDeliverSignDto dto,ArriveSheetEntity entity,CurrentInfo currentInfo ) {
		//记录日志
		LOGGER.info("*************保存QMS内物短少差错信息***********start");
		RecordErrorWaybillDto recordErrorWaybillDto=new RecordErrorWaybillDto();
		if(dto != null && entity!=null && currentInfo != null){
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
			//如果快递员为空
			if(StringUtils.isBlank(dto.getExpressEmpCode())){
				//上报人名字
				recordErrorWaybillDto.setOperateName(currentInfo.getEmpName());
				//上报人工号
				recordErrorWaybillDto.setOperateCode(currentInfo.getEmpCode());
			}else{
				//上报人名字
				recordErrorWaybillDto.setOperateName(dto.getExpressEmpName());
				//上报人工号
				recordErrorWaybillDto.setOperateCode(dto.getExpressEmpCode());
			}
			//上报人所在部门编码
			recordErrorWaybillDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			//上报人所在部门名称
			recordErrorWaybillDto.setOperateOrgName(currentInfo.getCurrentDeptName());
			if(org.apache.commons.collections.CollectionUtils.isNotEmpty(dto.getPdaSignDetailDtos())){
				StringBuffer sbuffer=new StringBuffer();
				for (PdaSignDetailDto pdaSignDetailDto : dto.getPdaSignDetailDtos()){
					String signSituation = pdaSignDetailDto.getSignSituation();
					if(StringUtils.isNotEmpty(signSituation)&&signSituation.equals(ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT)){
						sbuffer.append(pdaSignDetailDto.getSerialNo()).append(SignConstants.SPLIT_CHAR);
					}
				}
				if(StringUtils.isNotEmpty(sbuffer.toString())){
					String serialNos=sbuffer.toString().substring(0, sbuffer.toString().length()-1);
					if(StringUtils.isNotEmpty(serialNos)){
						//流水号
						recordErrorWaybillDto.setSerialNo(serialNos);
						recordErrorWaybillService.recordErrorWaybillReportOA(recordErrorWaybillDto);
					}
				}
			}
		}
		//记录日志
		LOGGER.info("*************保存QMS内物短少差错信息***********end");
	}
	
	/**
	 * 
	 * 快递PDA签收
	 * 快递派送
	 * @author 025000-foss-helong
	 * @date 2013-7-24
	 */
	@Override
	public String pdaExpressSign(PdaDeliverSignDto dto) {
		String resultMsg = ""; //信息
		LOGGER.info("-- 开始签收出库操作---- "+ReflectionToStringBuilder.toString(dto));//记录日志
		this.validateParams(dto);//验证传的值
		WaybillEntity waybill = new WaybillEntity();
		//查询运单号在运单表里面是否存在
		//如果不存在
		if(!waybillManagerService.isWayBillExsits(dto.getWaybillNo())){
			StringBuffer errorMsg = new StringBuffer();//拼接错误信息
			errorMsg.append("运单号 :");
			errorMsg.append(dto.getWaybillNo());//运单号
			errorMsg.append(" 不存在，请检查货物单号！");
			LOGGER.error(errorMsg.toString());//记录运单号不存在信息
			throw new PdaProcessException("该运单号在系统中不存在，不能签收出库");
		} else {//运单号存在
			// 根据到达联编号,到达联状态为‘派送中’查运单运输性质  ，
			//是否整车运单,到达联id,证件号码,证件类型,提货人,到达联件数,订单号,提货方式
			SignDto signDto = arriveSheetManngerService.queryPartWaybillpartArrivesheet(dto.getArrivesheetNo(),ArriveSheetConstants.STATUS_DELIVER);
			if(signDto == null){//查询结果为空
				LOGGER.error("--找不到有效状态的到达联,不能签收出库");//记录日志
				throw new PdaProcessException("找不到有效状态的到达联,不能签收出库");
			}else {
				if(!dto.getWaybillNo().equals(signDto.getWaybillNo())){
					throw new PdaProcessException("传入的运单号跟到达联对应的运单号不一致，不能签收");//传入的运单号跟到达联对应的运单号不一致，不能签收
				}
				signDto.setSystemDate(new Date());//系统时间
				BeanUtils.copyProperties(signDto,waybill);//把signDto里的内容复制到waybill里
				if(!signDto.getArriveSheetGoodsQty().equals(dto.getPdaSignDetailDtos().size())){
					LOGGER.error("--传入的流水号集合与到达联件数不一致,不能签收出库");//记录日志
					throw new PdaProcessException("传入的流水号集合与到达联件数不一致,不能签收出库");
				}
			}

			// 调接送货接口 判断是否有工单,有工单不允许签收
			ReturnGoodsRequestEntity  workOrderEntity = returnGoodsRequestEntityService.queryReturnGoodsEntityByWayBillNo( dto.getWaybillNo());
			if(null != workOrderEntity && FossConstants.NO.equals(workOrderEntity.getIsHandle())){
				throw new PdaProcessException("该运单存在未处理工单!");//存在工单且未处理完结
			}
			 
			//张新  限制签收
			List<PrintAgentWaybillRecordEntity> sdExternalBillRecords = printAgentWaybillService.queryRecordByWaybillNo(dto.getWaybillNo(),SignConstants.LIMIT_SIGN);
			if(!CollectionUtils.isEmpty(sdExternalBillRecords)) 
			{
				//有效的营业部外发
				LOGGER.error("--该单已外发，如需人工签收，请于“营业部外发单号绑定”中解除绑定");//记录日志
				throw new PdaProcessException("该单已外发，如需人工签收，请于“营业部外发单号绑定”中解除绑定");//签收限制
			}
			dto.setSignDeptCode(signDto.getLastLoadOrgCode());//签收部门为最终配载部门
			CurrentInfo currentInfo = this.getCurrentInfo(dto);

			//结清逻辑
			//判断是否子母件
			//1.如果是子母件：如果是母件 ；结清母件(即当前运单)，如果是子件，结清母件并结清子件(即当前运单)
			//2.如果不是子母件：结清当前运单
            //add by 309603 yangqiang 结清前先对交易流水号校验
            this.checkPos(dto, currentInfo);
            //end   add by 309603 yangqiang
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("waybillNo", dto.getWaybillNo());
			params.put("active", FossConstants.YES);
			TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
			//判断当前运单是否子母件
			if(oneDto != null){
				if(FossConstants.YES.equals(oneDto.getIsTwoInOne())){
					//判断母件单号是否为空
					if(StringUtils.isEmpty(oneDto.getMainWaybillNo())){
						throw new PdaProcessException("子母件获取母件单号失败！");
					}
					WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(oneDto.getMainWaybillNo());
					if(waybillEntity==null){
						throw new PdaProcessException("母件单号"+oneDto.getMainWaybillNo()+"不存在");
					}
					
					// 判断母件是否结清  如果结清不做处理  如果未结清手动结清
					expPayment(dto,signDto.getSystemDate(),currentInfo,oneDto.getMainWaybillNo());
				}
			}
			//结清当前运单，如果结清不做处理  如果未结清手动结清
			expPayment(dto,signDto.getSystemDate(),currentInfo,dto.getWaybillNo());
			
			if(WaybillConstants.INNER_PICKUP.equals(signDto.getReceiveMethod())){//提货方式为汽运内部自提
				LOGGER.info("--提货方式为汽运内部自提 不需要调用结算签收接口----");//记录日志
			}else {
				confirmTakingCheck(dto,currentInfo,signDto,oneDto);//调用结算子系统“签收接口”，判断是否是子母件
			}
			ActualFreightEntity actual = actualFreightService.queryByWaybillNo(dto.getWaybillNo());
			boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
			//如果到达联件数大于签收件数 录入交接流水号
			if(signDto.getArriveSheetGoodsQty() > dto.getSignGoodsQty()){
				this.operatPullbackGoods(dto, signDto, currentInfo);//对拉回货物信息进行操作
			}
			//如果签收件数小于运单开单件数
			if(dto.getSignGoodsQty() != null &&dto.getSignGoodsQty()<waybill.getGoodsQtyTotal()){
				List<String> serials = new ArrayList<String>();
				for (PdaSignDetailDto pdaSignDetailDto : dto.getPdaSignDetailDtos()) {//循环得到流水号集合
					if(PdaConstants.SITUATION_SIGN.equals(pdaSignDetailDto.getSituation())){//如果流水号的情况为签收
						//流水号
						serials.add(pdaSignDetailDto.getSerialNo());
						InOutStockEntity inOutStock = new InOutStockEntity();
						// 运单号
						inOutStock.setWaybillNO(dto.getWaybillNo());
						// 流水号
						inOutStock.setSerialNO(pdaSignDetailDto.getSerialNo());
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
							throw new PdaProcessException(e.getErrorCode(),e);//抛出异常
						}
					}
				}
				todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),serials);
			}else {
				todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),null);
			}
			ActualFreightDto actualFreightDto = new ActualFreightDto(dto.getWaybillNo(),dto.getSignGoodsQty());
			actualFreightDto.setPaymentType(dto.getPaymentType());//付款方式
			//PDA签收 修改运单状态里的到达未出库件数
			actualFreightService.updateArriveNotoutGoodsQtyByWaybillNo(actualFreightDto);
			//更新到达联信息
			ArriveSheetEntity entity = this.updateArriveSheet(dto,currentInfo,signDto);
			//快递员姓名
			String expressEmpName = "";
			String expressEmpTel = "";
			try {
				//update by--FOSS bieyexiong 
				//对签收明细进行处理,返回值用于判断 流水号是否有'内物短少'
				boolean isGoodshort = this.operatSignDetailDtos(dto);
				WaybillSignResultEntity wayEntity = waybillSignResultService.checkWaybillSignResultSign(entity,waybill,signDto.getSystemDate(),currentInfo);
				// 是PDA签收
				wayEntity.setIsPdaSign(FossConstants.YES);
				
				//到付金额
				wayEntity.setToPayAmount(dto.getToPayAmount());
				//代收货款
				wayEntity.setCodAmount(dto.getCodAmount());
				//代收货款--付款方式
				wayEntity.setCodPaymentType(dto.getCodPaymentType());
				//银行交易流水号--代收货款流水号
				wayEntity.setCodBankTradeSerail(dto.getCodBankTradeSerail());
				//合并金额
				wayEntity.setTotalAmount(dto.getTotalAmount());
				//合并--付款方式 
				wayEntity.setTotalPaymentType(dto.getTotalPaymentType());
										
				// 快递员code
				String empCode = StringUtil.defaultIfNull(dto.getExpressEmpCode());
				if(StringUtils.isEmpty(empCode)){
					empCode = dto.getDriverCode();
				}
				wayEntity.setExpressEmpCode(empCode);
				//判断快递员是否为空，若为空则无法根据快递员所属部门查询快递点部
				if(StringUtils.isNotEmpty(empCode)){
					LOGGER.info(wayEntity.getWaybillNo()+"---------- PDA派送签收调用接口：设置快递员及快递点信息----------" + dto.getExpressEmpCode());//记录日志
					//快递员编码查询所属部门
					EmployeeEntity employ = employeeService.queryEmployeeByEmpCode(StringUtil.defaultIfNull(empCode));
					if(null != employ){
						// 快递员名称
						wayEntity.setExpressEmpName(employ.getEmpName());
						OrgAdministrativeInfoEntity department = employ.getDepartment();
						if(null != department){
							//判断是否是快递点部
							if(FossConstants.YES.equals(department.getExpressPart())){
								// 快递点部CODE
								wayEntity.setExpressOrgCode(department.getCode());
								// 快递点部名称
								wayEntity.setExpressOrgName(department.getName());
							}

						}
						//为快递员姓名赋值
						expressEmpName = employ.getEmpName();
						//为快递员电话赋值
						expressEmpTel = employ.getMobilePhone();
					}
				}
				
				// 添加运单签收结果信息
				waybillSignResultService.addWaybillSignResult(wayEntity);
				
				//DN201511090005 添加“签收人类型”字段  243921
				wayEntity.setDeliverymanType(dto.getDeliverymanType());
				
				//菜鸟轨迹 add by 231438
				//封装的轨迹方法 赋值，判断是否符合条件 调用中转的轨迹接口
				sendWaybillTrackService.rookieTrackingsForSign(wayEntity);

				//快递pda签收，推送轨迹 --add by 231438，快递100，轨迹推送需求RFOSS2015031706
				sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);


				if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){//如果签收状态为全部签收
					waybillSignResultService.updateCrmOrder(signDto.getOrderNo(), currentInfo, wayEntity);
				}
				//update by--FOSS bieyexiong
				//流水号有内物短少时，上报QMS内物短少差错
				if(isGoodshort){
					this.saveRecordShortErrorInfo(dto,entity,currentInfo);
				}
			} catch (BusinessException e) {//捕获异常
				throw new PdaProcessException(e.getErrorCode(), e);//抛出异常
			}
			WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
			BeanUtils.copyProperties(entity,waybillSigndto);
			
			waybillSigndto.setExpressEmpName(expressEmpName);
			waybillSigndto.setExpressEmpTel(expressEmpTel);
			
			//应用监控签收调用
			waybillSignResultService.signCounterByWaybillNo(currentInfo,waybill);
			LOGGER.info("-------------------签收绑定单号-------------------");//记录日志
			try {
				signBindWaybillNo(dto,currentInfo);
			} catch (BusinessException  e) {
				LOGGER.error(e.getMessage(), e);//记录日志
				throw new PdaProcessException(e.getErrorCode(),e);//抛出捕获的异常
			}
			LOGGER.info("-------------------签收绑定单号-------------------");//记录日志
			//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
			resultMsg =waybillSignResultService.sendMessNotice(waybill, currentInfo,waybillSigndto);
			//调取FOSS中转补码查询的接口--中转-自动补码-补码后将待人工补码记录删除
			autoAddCodeByHandService.deleteAddCodeByHand(dto.getWaybillNo());
			//给发票系统提供数据的接口
			if(StringUtils.isNotEmpty(dto.getIsofferInvoice()) && FossConstants.YES.equals(dto.getIsofferInvoice())){
				//异步发送
				fimsPdaService.asynSendOtherRenueInfoToFims(dto);
			}
			if(isSendInvoiceInfo){
				//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
				waybillSignResultService.sendInvoiceInfo(waybill,actual);
			}
		}
		//签收出库成功,记录日志
		return StringUtils.isNotBlank(resultMsg) ? resultMsg : "PDA签收出库完成!";
	}

    private void checkPos(PdaDeliverSignDto dto, CurrentInfo currentInfo) {
        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(dto.getPaymentType())||
            SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(dto.getCodPaymentType())) {
            BigDecimal payAmount = BigDecimal.ZERO;									//使用总金额
            // 实收代收货款费用
            BigDecimal codAmount = dto.getCodAmount() == null?BigDecimal.ZERO:dto.getCodAmount();
            // 实收到实付运费
            BigDecimal actualFreight = dto.getToPayAmount() == null?BigDecimal.ZERO:dto.getToPayAmount();
            if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(dto.getPaymentType())){
                payAmount = payAmount.add(actualFreight);
            }
            if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(dto.getCodPaymentType())){
                payAmount = payAmount.add(codAmount);
            }
            //查询T+0报表获取未使用金额
            //准备查询数据
            PosCardEntity posCardEntity;
            PosCardManageDto posCardManageDto = new PosCardManageDto();
            posCardManageDto.setTradeSerialNo(dto.getRadeSerialNo());
            //posCardManageDto.setOrgCode(currentInfo.getCurrentDeptCode());
            //posCardManageDto.setBelongMoudleCode(SettlementDictionaryConstants.NCI_SETTLE);
            //查询T+0报表数据
            List<PosCardEntity> posCardEntitys = null;
            try {
                for (int i = 0; i < SettlementReportNumber.TWENTY; i++) {
                    Thread.sleep(SettlementReportNumber.TWO_THOUSAND);//异步接口，循环休息等待异步数据
                    //根据交流水号和部门编码查询POS刷卡数据   灰度  353654---start
                    String vestSystemCode = null;
                    try {
                    	List<String> arrayList = new ArrayList<String>();
                    	arrayList.add(dto.getWaybillNo());
                    	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                    			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".checkPos",
                    			SettlementConstants.TYPE_FOSS);
                    	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                    	List<VestBatchResult> list = response.getVestBatchResult();
                    	vestSystemCode = list.get(0).getVestSystemCode();	
        			} catch (Exception e) {
        				LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
    					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
        			}
        			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
        				posCardEntitys = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys();
        				if (posCardEntitys != null && posCardEntitys.size() == 1) {
        					break;
        				}
        				//是否存在
        	            if (posCardEntitys==null||posCardEntitys.isEmpty()) {
        	                throw new PdaProcessException("输入流水号不存在。");
        	            }else{
        	                posCardEntity = posCardEntitys.get(0);
        	            }
        	            //判断所属模块，结清货款，
        	            if(SettlementDictionaryConstants.NCI_DEPOSIT.equals(posCardEntity.getBelongModule())){
        	                throw new PdaProcessException("该交易流水号仅能做预收或做小票！");
        	            }
        	            //获取未使用金额
        	            BigDecimal amount = posCardEntity.getUnUsedAmount();
        	            //比较
        	            if (amount.compareTo(payAmount)<0) {//可用金额小于实收代收货款+实收到付运费
        	                throw new PdaProcessException("可用余额不足。");
        	            }
        	            posCardEntity.setModifyUser(currentInfo.getEmpName());										//更新人名称
        	            posCardEntity.setModifyUserCode(currentInfo.getEmpCode());									//更新人编码
        	            posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(payAmount));					//已使用金额
        	            posCardEntity.setUnUsedAmount(amount.subtract(payAmount));  								//未使用金额
        	            posCardEntity.setUnUsedAmount(posCardEntity.getUnUsedAmount().subtract(payAmount));   								//未使用金额
        				pdaPosManageService.updatePosCardMessageAmount(posCardEntity);
        	            //插入新的POS刷卡明细
        	            //准备数据
        	            PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
        	            posCardDetailEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo()); 					//交易流水号
        	            posCardDetailEntity.setInvoiceType("W2");													//运单
        	            posCardDetailEntity.setInvoiceNo(dto.getWaybillNo());							            //运单号
        	            //posCardDetailEntity.setAmount(waybilldto.getToPayAmount());								//发生金额
        	            BigDecimal totalAmount = repaymentDao.getTotalAmount(dto.getWaybillNo());
        	            posCardDetailEntity.setAmount(totalAmount);									                //发生金额
        	            posCardDetailEntity.setOccupateAmount(payAmount);											//已占用金额
        	            //BigDecimal unVerifyAmount = (receiveableAmount.add(receiveablePayAmoout)).subtract(payAmount);
        	            posCardDetailEntity.setUnVerifyAmount(BigDecimal.ZERO);										//未核销金额
        	            posCardDetailEntity.setCreateUser(currentInfo.getEmpName());								//创建人名称
        	            posCardDetailEntity.setCreateUserCode(currentInfo.getEmpCode());							//创建人编码
        				pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);//调用接口插入数据
        			}
        			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
        				//TODO  CUBC自己处理...
        			}
        			//根据交流水号和部门编码查询POS刷卡数据   灰度  353654---end
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
       
        }
    }

    /**
	 * 检验PDA批量签收快递母子件传入传数
	 * @param dtos
	 */
	private void validateBatchExpSignParams(PdaDeliverSignShellDto shellDto) {
		if (null == shellDto) {
			LOGGER.error("接口传入的数据为空");//记录日志
			throw new PdaProcessException("接口传入的数据为空");
		}else if(StringUtils.isBlank(shellDto.getPaymentType())) {//如果到付金额--付款方式为空
			LOGGER.error("批量签收存在到付金额--付款方式为空，不能签收出库");//记录日志
			throw new PdaProcessException("批量签收存在到付金额--付款方式为空，不能签收出库");//到付金额--付款方式为空，不能签收出库
		}//else if(StringUtils.isBlank(shellDto.getCodPaymentType())) {//如果代收货款--付款方式为空
			//LOGGER.error("批量签收存在代收货款--付款方式为空，不能签收出库");//记录日志
			//throw new PdaProcessException("批量签收存在代收货款--付款方式为空，不能签收出库");//代收货款--付款方式为空，不能签收出库
		//}else if(StringUtils.isBlank(shellDto.getTotalPaymentType())) {//如果合并--付款方式 为空
		//	LOGGER.error("批量签收存在合并--付款方式 为空，不能签收出库");//记录日志
		//	throw new PdaProcessException("批量签收存在合并--付款方式 为空，不能签收出库");//合并--付款方式 为空，不能签收出库
		//}
		/**
		 * 接口传入的数据为空
		 */
		if(null == shellDto.getPdaDeliverSignDtos() || shellDto.getPdaDeliverSignDtos().size() < 1){
			throw new PdaProcessException("批量签收运单列表为空!");
		}
		for (PdaDeliverSignDto dto : shellDto.getPdaDeliverSignDtos()) {
			LOGGER.info("-- 开始批量签收快递出库操作--签收运单列表---- "+ReflectionToStringBuilder.toString(dto));//记录日志
			if (StringUtils.isBlank(dto.getWaybillNo())) {//如果传入的运单号为空
				LOGGER.error("批量签收存在运单号为空，不能签收出库");//记录日志
				throw new PdaProcessException("批量签收存在运单号为空，不能签收出库");//运单号为空，不能签收出库
			} else if (StringUtils.isBlank(dto.getArrivesheetNo())){//如果到达联编号为空
				LOGGER.error("批量签收存在到达联编号为空！,不能签收出库");//记录日志
				throw new PdaProcessException("批量签收存在到达联编号为空！,不能签收出库");//到达联编号为空！,不能签收出库
			} else if(StringUtils.isBlank(dto.getSignDeptCode())) {//如果签收部门为空
				LOGGER.error("批量签收存在签收部门为空，不能签收出库");//记录日志
				throw new PdaProcessException("批量签收存在签收部门为空，不能签收出库");//签收部门为空，不能签收出库
			}else if(dto.getSignTime() == null) {//如果签收时间为空
				LOGGER.error("批量签收存在签收时间为空，不能签收出库");//记录日志
				throw new PdaProcessException("批量签收存在签收时间为空，不能签收出库");//签收时间为空，不能签收出库
			}else if(dto.getSignGoodsQty() == null || dto.getSignGoodsQty().intValue() == 0) {
				LOGGER.error("批量签收存在签收件数为0，不能签收出库");//记录日志
				throw new PdaProcessException("批量签收存在签收件数为0，不能签收出库");//签收件数为0，不能签收出库
			}else if(CollectionUtils.isEmpty(dto.getPdaSignDetailDtos())){
				LOGGER.error("批量签收存在传入的流水号为空，不能签收出库");//记录日志
				throw new PdaProcessException("批量签收存在传入的流水号为空，不能签收出库");//传入的流水号为空，不能签收出库
			}else {//其他情况
				int count = 0;//默认值
				for (PdaSignDetailDto pdaSignDetailDto : dto.getPdaSignDetailDtos()) {//循环得到传入的流水号集合
					if(PdaConstants.SITUATION_SIGN.equals(pdaSignDetailDto.getSituation())){//如果流水号的情况签收
						count++;//统计流水号的情况为签收的数量
					}
				}
				if(dto.getSignGoodsQty() != count){//传入的流水号集合为签收的有效件数与签收件数不一致，不能签收出库
					LOGGER.error("批量签收存在传入的流水号集合为签收的有效件数与签收件数不一致，不能签收出库");//记录日志
					throw new PdaProcessException("批量签收存在传入的流水号集合为签收的有效件数与签收件数不一致，不能签收出库");//传入的流水号集合为签收的有效件数与签收件数不一致，不能签收出库
				}
			}
			//子母件如果为返货单号不能签收  如果为返货开单原单号 也不能签收
			List<WaybillExpressEntity> expList = waybillExpressService.queryWaybillListByWaybillNo(dto.getWaybillNo());
			if( CollectionUtils.isNotEmpty(expList) && expList.size()>0){
				throw new PdaProcessException("单号"+dto.getWaybillNo()+"为返货开单，子母件不允许为返货单号 不能签收！");//抛出异常
			}
			//运单号
			if(StringUtils.isNotEmpty(dto.getWaybillNo())){
				WaybillExpressEntity waybillExpressEntity = waybillExpressService.queryWaybillByOriginalWaybillNo(dto.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
				if(waybillExpressEntity!=null){
					throw new SignException("该单号已经返货新开单,请使用新单号进行操作!");//该运单号不存在
				}
			}
			//张新  限制签收
			List<PrintAgentWaybillRecordEntity> sdExternalBillRecords = printAgentWaybillService.queryRecordByWaybillNo(dto.getWaybillNo(),SignConstants.LIMIT_SIGN);
			if(!CollectionUtils.isEmpty(sdExternalBillRecords)) 
			{
				//有效的营业部外发
				LOGGER.error("--该单已外发，如需人工签收，请于“营业部外发单号绑定”中解除绑定");//记录日志
				throw new PdaProcessException("运单"+dto.getWaybillNo()+"已外发，如需人工签收，请于“营业部外发单号绑定”中解除绑定");//签收限制
			}
		}
		
	}
	/**
	 * 
	 * 快递PDA批量签收（子母件）
	 * 快递派送
	 * @author 159231
	 * @date 2015-8-28
	 */
	@Override
	public String pdaExpressBatchSign(PdaDeliverSignShellDto shellDto) {
		LOGGER.info("-- 开始批量签收快递出库操作---- "+ReflectionToStringBuilder.toString(shellDto));//记录日志
		this.validateBatchExpSignParams(shellDto);//验证传的值
		List<PdaDeliverSignDto> dtos = shellDto.getPdaDeliverSignDtos();
		//得到母件单号
		//判断当前运单是否子母件
		String waybillNo = dtos.get(0).getWaybillNo();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("waybillNo", waybillNo);
		params.put("active", FossConstants.YES);
		TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
		if(oneDto != null){
			if(FossConstants.NO.equals(oneDto.getIsTwoInOne())){
				throw new PdaProcessException("该运单不是子母件!");
			}
			if(CollectionUtils.isEmpty(oneDto.getWaybillNoList())){
				throw new PdaProcessException("运单号"+waybillNo+"子件集合为空！");
			}
		}else{
			throw new PdaProcessException("该运单不是子母件!");
		}
		String femaleWaybill= oneDto.getMainWaybillNo();
		if(StringUtils.isBlank(femaleWaybill)){
			throw new PdaProcessException("母件单号不存在");
		}
		Date nowDate=new Date();
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(femaleWaybill);
		if(waybillEntity==null){
			throw new PdaProcessException("母件单号"+femaleWaybill+"不存在");
		}
		//得到母件 判断 母件是否已经结清 如果未结清，进行结清操作
		PdaDeliverSignDto femaleDto=new PdaDeliverSignDto();
		BeanUtils.copyProperties(dtos.get(0),femaleDto);//把dtos.get(0)里的内容复制到femaleDto里
		femaleDto.setWaybillNo(femaleWaybill);
		femaleDto.setSignDeptCode(waybillEntity.getLastLoadOrgCode());		//签收部门为最终配载部门
		femaleDto.setToPayAmount(shellDto.getToPayAmount());				//到付金额
		femaleDto.setPaymentType(shellDto.getPaymentType());				//到付金额--付款方式 
		femaleDto.setBankTradeSerail(shellDto.getBankTradeSerail());		//银行交易流水号--到付流水号
		femaleDto.setCodAmount(shellDto.getCodAmount());					//代收货款
		femaleDto.setCodPaymentType(shellDto.getCodPaymentType());			//代收货款--付款方式 
		femaleDto.setCodBankTradeSerail(shellDto.getCodBankTradeSerail());	//银行交易流水号--代收货款流水号
		femaleDto.setTotalAmount(shellDto.getTotalAmount());				//合并金额
		femaleDto.setTotalPaymentType(shellDto.getTotalPaymentType());		//合并--付款方式 
		femaleDto.setPdaSerial(dtos.get(0).getPdaSerial());					//串号
		CurrentInfo currentInfo = this.getCurrentInfo(femaleDto);
		//先结清母件
		expPayment(femaleDto,nowDate,currentInfo,femaleWaybill);//结清母件- 运单号传母件运单号，该运单号要改
		for (PdaDeliverSignDto pdaDeliverSignDto : dtos) {
			pdaDeliverSignDto.setSignDeptCode(waybillEntity.getLastLoadOrgCode());
			pdaDeliverSignDto.setPaymentType(shellDto.getPaymentType());				//到付金额--付款方式 
			pdaDeliverSignDto.setBankTradeSerail(shellDto.getBankTradeSerail());		//银行交易流水号--到付流水号
			pdaDeliverSignDto.setCodPaymentType(shellDto.getCodPaymentType());			//代收货款--付款方式 
			pdaDeliverSignDto.setCodBankTradeSerail(shellDto.getCodBankTradeSerail());	//银行交易流水号--代收货款流水号
			pdaDeliverSignDto.setTotalPaymentType(shellDto.getTotalPaymentType());		//合并--付款方式 
			if(!pdaDeliverSignDto.getWaybillNo().equals(femaleWaybill)){
				boolean isTwoinOne =false;
				for (int i = 0; i < oneDto.getWaybillNoList().size(); i++) {
					if(pdaDeliverSignDto.getWaybillNo().equals(oneDto.getWaybillNoList().get(i))){
						isTwoinOne=true;
						break;
					}
				}
				if(!isTwoinOne){
					throw new PdaProcessException("单号   "+pdaDeliverSignDto.getWaybillNo()+"不是子母件 不能进行当前操作");
				}
				
				expPayment(pdaDeliverSignDto,nowDate,currentInfo,pdaDeliverSignDto.getWaybillNo());//结清子件
			}else{
				pdaDeliverSignDto.setToPayAmount(shellDto.getToPayAmount());				//到付金额
				pdaDeliverSignDto.setCodAmount(shellDto.getCodAmount());					//代收货款
				pdaDeliverSignDto.setTotalAmount(shellDto.getTotalAmount());				//合并金额
			}
		}
		List<PdaBatchDeliverSignDto> batchSigndtos=new ArrayList<PdaBatchDeliverSignDto>();
		List<WaybillSignResultDto> waybillSignResultDtos = new ArrayList<WaybillSignResultDto>();
		for (PdaDeliverSignDto dto : dtos) {
			
			// 调接送货接口 判断是否有工单,有工单不允许签收
			ReturnGoodsRequestEntity  workOrderEntity = returnGoodsRequestEntityService.queryReturnGoodsEntityByWayBillNo( dto.getWaybillNo());
			if(null != workOrderEntity && FossConstants.NO.equals(workOrderEntity.getIsHandle())){
				throw new PdaProcessException("该运单存在未处理工单!");//存在工单且未处理完结
			}
			
			//结清完毕后进行批量签收
			WaybillEntity waybill = new WaybillEntity();
			// 根据到达联编号,到达联状态为‘派送中’查运单运输性质  ，
			//是否整车运单,到达联id,证件号码,证件类型,提货人,到达联件数,订单号,提货方式
			SignDto signDto = arriveSheetManngerService.queryPartWaybillpartArrivesheet(dto.getArrivesheetNo(),ArriveSheetConstants.STATUS_DELIVER);
			if(signDto == null){//查询结果为空
				LOGGER.error("--找不到有效状态的到达联,不能签收出库");//记录日志
				throw new PdaProcessException("找不到有效状态的到达联,不能签收出库");
			}else {
				if(!dto.getWaybillNo().equals(signDto.getWaybillNo())){
					throw new PdaProcessException("传入的运单号"+dto.getWaybillNo()+"跟到达联对应的运单号不一致，不能签收");//传入的运单号跟到达联对应的运单号不一致，不能签收
				}
				signDto.setSystemDate(nowDate);//系统时间
				BeanUtils.copyProperties(signDto,waybill);//把signDto里的内容复制到waybill里
				if(!signDto.getArriveSheetGoodsQty().equals(dto.getPdaSignDetailDtos().size())){
					LOGGER.error("--传入的流水号集合与到达联件数不一致,不能签收出库");//记录日志
					throw new PdaProcessException("传入的流水号集合与到达联件数不一致,不能签收出库");
				}
			}
			if(WaybillConstants.INNER_PICKUP.equals(signDto.getReceiveMethod())){//提货方式为汽运内部自提
				LOGGER.info("--提货方式为汽运内部自提 不需要调用结算签收接口----");//记录日志
			}else {
				this.confirmTakingCheck(dto,currentInfo,signDto,oneDto);//调用结算子系统“签收接口”，判断是否是子母件
			}
			ActualFreightEntity actual = actualFreightService.queryByWaybillNo(dto.getWaybillNo());
			boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
			//如果到达联件数大于签收件数 录入交接流水号
			if(signDto.getArriveSheetGoodsQty() > dto.getSignGoodsQty()){
				this.operatPullbackGoods(dto, signDto, currentInfo);//对拉回货物信息进行操作
			}
			//如果签收件数小于运单开单件数
			if(dto.getSignGoodsQty() != null &&dto.getSignGoodsQty()<waybill.getGoodsQtyTotal()){
				List<String> serials = new ArrayList<String>();
				for (PdaSignDetailDto pdaSignDetailDto : dto.getPdaSignDetailDtos()) {//循环得到流水号集合
					if(PdaConstants.SITUATION_SIGN.equals(pdaSignDetailDto.getSituation())){//如果流水号的情况为签收
						//流水号
						serials.add(pdaSignDetailDto.getSerialNo());
						InOutStockEntity inOutStock = new InOutStockEntity();
						// 运单号
						inOutStock.setWaybillNO(dto.getWaybillNo());
						// 流水号
						inOutStock.setSerialNO(pdaSignDetailDto.getSerialNo());
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
							throw new PdaProcessException(e.getErrorCode(),e);//抛出异常
						}
					}
				}
				todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),serials);
			}else {
				todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),null);
			}
			ActualFreightDto actualFreightDto = new ActualFreightDto(dto.getWaybillNo(),dto.getSignGoodsQty());
			actualFreightDto.setPaymentType(dto.getPaymentType());//付款方式
			//PDA签收 修改运单状态里的到达未出库件数
			actualFreightService.updateArriveNotoutGoodsQtyByWaybillNo(actualFreightDto);
			//更新到达联信息
			ArriveSheetEntity entity = this.updateArriveSheet(dto,currentInfo,signDto);
			//快递员姓名
			String expressEmpName = "";
			String expressEmpTel = "";
			try {
				//update by--FOSS bieyexiong 
				//对签收明细进行处理,返回值用于判断 流水号是否有'内物短少'
				boolean isGoodshort = this.operatSignDetailDtos(dto);
				WaybillSignResultEntity wayEntity = waybillSignResultService.checkWaybillSignResultSign(entity,waybill,signDto.getSystemDate(),currentInfo);
				// 是PDA签收
				wayEntity.setIsPdaSign(FossConstants.YES);
				
				//到付金额
				wayEntity.setToPayAmount(dto.getToPayAmount());
				//代收货款
				wayEntity.setCodAmount(dto.getCodAmount());
				//代收货款--付款方式
				wayEntity.setCodPaymentType(dto.getCodPaymentType());
				//银行交易流水号--代收货款流水号
				wayEntity.setCodBankTradeSerail(dto.getCodBankTradeSerail());
				//合并金额
				wayEntity.setTotalAmount(dto.getTotalAmount());
				//合并--付款方式 
				wayEntity.setTotalPaymentType(dto.getTotalPaymentType());
				
				// 快递员code
				String empCode = StringUtil.defaultIfNull(dto.getExpressEmpCode());
				if(StringUtils.isEmpty(empCode)){
					empCode = dto.getDriverCode();
				}
				wayEntity.setExpressEmpCode(empCode);
				//判断快递员是否为空，若为空则无法根据快递员所属部门查询快递点部
				if(StringUtils.isNotEmpty(empCode)){
					LOGGER.info(wayEntity.getWaybillNo()+"---------- PDA派送签收调用接口：设置快递员及快递点信息----------" + dto.getExpressEmpCode());//记录日志
					//快递员编码查询所属部门
					EmployeeEntity employ = employeeService.queryEmployeeByEmpCode(StringUtil.defaultIfNull(empCode));
					if(null != employ){
						// 快递员名称
						wayEntity.setExpressEmpName(employ.getEmpName());
						OrgAdministrativeInfoEntity department = employ.getDepartment();
						if(null != department){
							//判断是否是快递点部
							if(FossConstants.YES.equals(department.getExpressPart())){
								// 快递点部CODE
								wayEntity.setExpressOrgCode(department.getCode());
								// 快递点部名称
								wayEntity.setExpressOrgName(department.getName());
							}
						}
						//为快递员姓名赋值
						expressEmpName = employ.getEmpName();
						//为快递员电话赋值
						expressEmpTel = employ.getMobilePhone();
					}
				}
				// 添加运单签收结果信息
				waybillSignResultService.addWaybillSignResult(wayEntity);
				
				//DN201511090005 添加“签收人类型”字段  243921
				wayEntity.setDeliverymanType(dto.getDeliverymanType());
				
				//菜鸟轨迹 add by 231438
				//封装的轨迹方法 赋值，判断是否符合条件 调用中转的轨迹接口
				sendWaybillTrackService.rookieTrackingsForSign(wayEntity);
				//快递pda签收，推送轨迹 --add by 231438，快递100，轨迹推送需求RFOSS2015031706
				sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
				if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){//如果签收状态为全部签收
					waybillSignResultService.updateCrmOrder(signDto.getOrderNo(), currentInfo, wayEntity);
				}
				
				//QMS差错项目延期，先注释
				//update by--FOSS bieyexiong
				//流水号有内物短少时，上报QMS内物短少差错
				if(isGoodshort){				
					this.saveRecordShortErrorInfo(dto,entity,currentInfo);
				}
			} catch (BusinessException e) {//捕获异常
				throw new PdaProcessException(e.getErrorCode(), e);//抛出异常
			}
			WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
			BeanUtils.copyProperties(entity,waybillSigndto);
			
			waybillSigndto.setExpressEmpName(expressEmpName);
			waybillSigndto.setExpressEmpTel(expressEmpTel);
			waybillSignResultDtos.add(waybillSigndto);
			PdaBatchDeliverSignDto batchDto = new PdaBatchDeliverSignDto();
			batchDto.setActual(actual);
			batchDto.setWaybill(waybill);
			batchDto.setIsSendInvoiceInfo(isSendInvoiceInfo);
			batchDto.setPdaDeliverSignDto(dto);
			batchSigndtos.add(batchDto);
			//应用监控签收调用
			waybillSignResultService.signCounterByWaybillNo(currentInfo,waybill);
		}
		for (PdaBatchDeliverSignDto batchdto : batchSigndtos) {
			for (WaybillSignResultDto waybillSignResultDto : waybillSignResultDtos) {
				if(batchdto.getPdaDeliverSignDto().getWaybillNo().equals(waybillSignResultDto.getWaybillNo())){
					//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
					waybillSignResultService.sendMessNotice(batchdto.getWaybill(), currentInfo,waybillSignResultDto);
					waybillSignResultDtos.remove(waybillSignResultDto);
					break;
				}
			}
			//给发票系统提供数据的接口
			if(StringUtils.isNotEmpty(batchdto.getPdaDeliverSignDto().getIsofferInvoice()) && FossConstants.YES.equals(batchdto.getPdaDeliverSignDto().getIsofferInvoice())){
				//异步发送
				fimsPdaService.asynSendOtherRenueInfoToFims(batchdto.getPdaDeliverSignDto());
			}
			if(batchdto.getIsSendInvoiceInfo()){
				//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
				waybillSignResultService.sendInvoiceInfo(batchdto.getWaybill(),batchdto.getActual());
			}
		}
		//签收出库成功,记录日志
		return "PDA快递批量签收出库完成!";
	}
	/**
	 * 手动进行结清货款
	 * @param dto
	 * @param signDto
	 * @param currentInfo
	 */
	private void expPayment(PdaDeliverSignDto dto,Date nowDate,CurrentInfo currentInfo,String waybillNo){
		if (!repaymentService.isPayment(waybillNo)) {
			RepaymentEntity repayment = new RepaymentEntity();
			// 运单号
			repayment.setWaybillNo(waybillNo);
			//PDA结清     243921
			repayment.setSettleApproach(SettlementDictionaryConstants.SETTLE_APPROACH_MOBILE);
			// 付款方式
			repayment.setPaymentType(dto.getPaymentType());
			BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(waybillNo);
			//财务单据查询，灰度改造   353654 ---------------------------start 
			List<BillReceivableEntity> billReceivableEntities = null;
			String vestSystemCode = null;
            try {
            	List<String> arrayList = new ArrayList<String>();
            	arrayList.add(waybillNo);
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".expPayment",
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
					throw new PdaProcessException("呀,亲!系统繁忙,CUBC查询财务单据失败,请稍后重试!");
				}
			}
			//财务单据查询，灰度改造   353654 ---------------------------end
			if(CollectionUtils.isEmpty(billReceivableEntities)){
				// 实付运费
				repayment.setActualFreight(BigDecimal.ZERO);
				repayment.setCodAmount(BigDecimal.ZERO);// 代收货款
				repayment.setActualFreight(repayment.getActualFreight() == null ? BigDecimal.ZERO:repayment.getActualFreight());
				repayment.setCodAmount(repayment.getCodAmount() == null? BigDecimal.ZERO :repayment.getCodAmount());
				//付款时间
				repayment.setPaymentTime(nowDate);
				//串号
				repayment.setPdaSerial(dto.getPdaSerial());
				try {
					// 调结清货款进行结清
					repaymentService.deliverOperate(repayment, currentInfo);
				} catch (RepaymentException e) {//捕获异常
					LOGGER.error("--调用结清货款抛出异常:"+e.getErrorCode());//记录异常信息
					throw new PdaProcessException(e.getErrorCode(),e);//抛出捕获的异常
				}
				LOGGER.info("调用结清货款完成");//记录日志
			}else{
				for (BillReceivableEntity billReceivableEntity : billReceivableEntities) {
					//合并金额
					BigDecimal totalAmount = (dto.getTotalAmount() == null ? BigDecimal.ZERO : dto.getTotalAmount());
					// 到达应收单
					if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
						// 实付运费
						repayment.setActualFreight(billReceivableEntity.getUnverifyAmount());
						// 代收货款
						repayment.setCodAmount(BigDecimal.ZERO);
						//银行交易流水号--到付流水号
						repayment.setBankTradeSerail(dto.getBankTradeSerail());
						//判断是否合并，若为合并，则全部使用合并付款方式
						if(totalAmount.compareTo(BigDecimal.ZERO) > 0){
							//合并金额--付款方式
							repayment.setPaymentType(dto.getTotalPaymentType());
						}else{
							//到付金额--付款方式
							repayment.setPaymentType(dto.getPaymentType());
						}
					} 
					// 代收货款应收单
					else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(billReceivableEntity.getBillType())) {
						// 实付运费
						repayment.setActualFreight(BigDecimal.ZERO);
						// 代收货款
						repayment.setCodAmount(billReceivableEntity.getUnverifyAmount());
						//银行交易流水号--代收货款流水号
						repayment.setBankTradeSerail(dto.getCodBankTradeSerail());
						if(totalAmount.compareTo(BigDecimal.ZERO) > 0){
							//合并金额--付款方式
							repayment.setPaymentType(dto.getTotalPaymentType());
						}else{
							//代收货款--付款方式 
							repayment.setPaymentType(dto.getCodPaymentType());
						}
					}
					// 合伙人到付运费应收单
					else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(billReceivableEntity.getBillType())){
						// 实付运费
						repayment.setActualFreight(billReceivableEntity.getUnverifyAmount());
						// 代收货款
						repayment.setCodAmount(BigDecimal.ZERO);
						//银行交易流水号--到付流水号
						repayment.setBankTradeSerail(dto.getBankTradeSerail());
						//判断是否合并，若为合并，则全部使用合并付款方式
						if(totalAmount.compareTo(BigDecimal.ZERO) > 0){
							//合并金额--付款方式
							repayment.setPaymentType(dto.getTotalPaymentType());
						}else{
							//到付金额--付款方式
							repayment.setPaymentType(dto.getPaymentType());
						}
					}else{
						continue;
					}
					
					//若付款方式为现金，则流水号必须为空
					if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(repayment.getPaymentType())){
						//银行交易流水号--代收货款流水号
						repayment.setBankTradeSerail("");
						//串号
						repayment.setPdaSerial("");
					}else{
						//串号
						repayment.setPdaSerial(dto.getPdaSerial());
					}
					
					repayment.setActualFreight(repayment.getActualFreight() == null ? BigDecimal.ZERO:repayment.getActualFreight());
					repayment.setCodAmount(repayment.getCodAmount() == null? BigDecimal.ZERO :repayment.getCodAmount());
					//付款时间
					repayment.setPaymentTime(nowDate);
					
					try {
						// 调结清货款进行结清
						repaymentService.deliverOperate(repayment, currentInfo);
					} catch (RepaymentException e) {//捕获异常
						LOGGER.error("--调用结清货款抛出异常:"+e.getErrorCode());//记录异常信息
						throw new PdaProcessException(e.getErrorCode(),e);//抛出捕获的异常
					}
					LOGGER.info("调用结清货款完成");//记录日志
				}
			}
		}else {
			LOGGER.info("当前运单已经结清，不需要调用货款结清接口进行结清操作");//记录日志
		}
	}
	/**
	 * 调用结算子系统“签收接口”,判断是否是子母件
	 * @author foss-sunyanfei
	 * @date  2015-9-11
	 * @param dto
	 * @param currentInfo
	 * @param signDto
	 */
	private void confirmTakingCheck(PdaDeliverSignDto dto,CurrentInfo currentInfo,SignDto signDto,TwoInOneWaybillDto oneDto){
		try {
			//判断当前运单是否子母件
			if(oneDto != null && oneDto.getIsTwoInOne().equals(FossConstants.YES)){
				List<WaybillSignResultEntity> list = new ArrayList<WaybillSignResultEntity>();
				//List<String> oneDtoList = new ArrayList<String>();  //用来存储子母件运单号集合
				//if(oneDto.getWaybillNoList() != null){//当子母件不止一件时，将子件集合添加进子母件运单号集合
				//	oneDtoList.addAll(oneDto.getWaybillNoList());
				//}
				//oneDtoList.add(oneDto.getMainWaybillNo());//将母单号添加进子母件运单号集合
				for(String waybillNo:oneDto.getWaybillNoList()){
					// 传入参数(运单号,当前运单号生效)
					WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(waybillNo, FossConstants.ACTIVE);
					// 根据运单号 查询运单签收结果
					WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(wayEntity);
					if( null != waybillSign){
						list.add(waybillSign);
					}
				}
				
				//判断子母件是母件
				if(oneDto.getMainWaybillNo().equals(dto.getWaybillNo())){
					if(oneDto.getWaybillNoList().size() - list.size()  > 1){
						//母件不是最后一件签收，不做确认收入
					}else{ 
						//母件签收是最后一件，确认收入
						this.expConfirmTaking(dto, currentInfo, signDto);
					}
				}else{//子件
					//当前子件不是子母件签收的最后一件
					if(oneDto.getWaybillNoList().size() - list.size()  > 1){
						//子件确认收入
						this.confirmTaking(dto, currentInfo, signDto);
					}else{//当前子件是子母件签收的最后一件
						//子件确认收入
						this.confirmTaking(dto, currentInfo, signDto);
						PdaDeliverSignDto dtoTwo = new PdaDeliverSignDto();
						BeanUtils.copyProperties(dto,dtoTwo);
						//母件确认收入
						dtoTwo.setWaybillNo(oneDto.getMainWaybillNo());
						this.expConfirmTaking(dtoTwo, currentInfo, signDto);
					}
				}
			}else{
				//不是子母件，正常调用
				this.confirmTaking(dto, currentInfo, signDto);
			}
		} catch (SettlementException se) {//捕获异常
			LOGGER.error("子母件调用财务接口抛出异常",se);//记录日志
			throw new PdaProcessException(se.getErrorCode(),se);//抛出异常
		}
		LOGGER.info("子母件调用结算签收接口结束");//记录日志
	}
	/**
	 * 菜鸟----------签收绑定的原单号
	 * @date 2015-2-5 上午10:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.ExpSignService#signBindWaybillNo(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	private void signBindWaybillNo(PdaDeliverSignDto entity,CurrentInfo currentInfo) {
		LOGGER.info("-------------------签收绑定单号 begin-------------------");//记录日志
		List<WaybillExpressEntity> expressList =waybillExpressService.queryWaybillListByWaybillNo(entity.getWaybillNo());
		WaybillExpressEntity newWaybillNoEntity = CollectionUtils.isNotEmpty(expressList) ?expressList.get(0) :null;
		//判断新单号
		if(newWaybillNoEntity!=null){
			String oldWaybillNo = newWaybillNoEntity.getOriginalWaybillNo();
			if(StringUtils.isNotEmpty(oldWaybillNo)){
				// 判断是否是子母件，如果是子母件，重新查询判断返货原单是否结清；如果不是子母件，走原来逻辑
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("waybillNo", oldWaybillNo);
				params.put("active", FossConstants.YES);
				TwoInOneWaybillDto oneDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
				//判断当前运单是否子母件
				if(oneDto != null && FossConstants.YES.equals(oneDto.getIsTwoInOne())){
					//判断母件单号是否为空
					if(StringUtils.isEmpty(oneDto.getMainWaybillNo())){
						throw new SignException("子母件获取母件单号失败！");
					}
					//结清子母件并签收：如果未结清，先结清然后再签收
					rookieService.returnTwoInOneSign(entity, currentInfo, oneDto);
					
				}else{//不是子母件
					WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(oldWaybillNo);
					if(waybill!= null){
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
							resultWEntity.setSignSituation(entity.getSituation());
							resultWEntity.setSignTime(entity.getSignTime());
							resultWEntity.setSignNote(entity.getSignNote());
							resultWEntity.setWaybillNo(oldWaybillNo);
							resultWEntity.setSignGoodsQty(waybill.getGoodsQtyTotal());
							resultWEntity.setIsPdaSign(FossConstants.YES);
							resultWEntity.setReceiveMethod(waybill.getReceiveMethod());
							rookieService.exceuteSignStatusPart(oldresultWEntity, waybill, resultWEntity,entity,currentInfo, null, true);
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
	 * 
	 * 对部分签收的拉回货物进行操作
	 * @author
	 * 			 foss-meiying
	 * @date 
	 * 			2013-3-8 下午5:18:51
	 * @param dto
	 * 			dto.waybillNo 
	 * 				运单号
	 * 			dto.arrivesheetNo
	 * 				 到达联编号
	 * 			dto.signDeptCode
	 * 				 签收部门
	 * 			dto.signTime
	 * 				 签收时间
	 * 			dto.signGoodsQty
	 * 				 签收件数
	 * 			dto.pdaSignDetailDtos
	 * 				 流水号集合
	 * 				dto.pdaSignDetailDtos.situation
	 * 					 签收情况 
	 * 					REFUSE为拒收
	 * 					SIGN为签收
	 * 				dto.pdaSignDetailDtos.situation.serialNo
	 * 					流水号
	 * 			dto.situation
	 * 				签收情况
	 * 				（正常签收、
	 * 				异常-破损、
	 * 				异常-潮湿、
	 * 				异常-污染、
	 * 				异常-其他、
	 * 				部分签收）
	 * 			dto.vehicleNo
	 * 				车牌号
	 * 			dto.driverCode
	 * 				司机工号
	 * 			dto.paymentType
	 * 				付款方式
	 * 			（现金、临欠、月结、银行卡、支票、电汇）
	 * 			dto.signNote
	 * 				签收备注
	 * @param signDto
	 * 			signDto.arriveSheetGoodsQty 
	 * 				到达联件数
	 * 			signDto.identifyType 
	 * 				证件类型
	 *  		signDto.identifyCode 
	 * 				证件号码
	 * 	 		signDto.deliverymanName 
	 * 				提货人名称
	 * 	 		signDto.productCode 
	 * 				运输性质
	 *    		signDto.isWholeVehicle 
	 * 				是否整车运单
	 * 	  		signDto.lastLoadOrgCode 
	 * 				最终配载部门
	 * 			signDto.waybillNo 
	 * 				运单号
	 * 			signDto.receiveCustomerName
	 * 				收货人(收货客户名称)
	 * 			signDto.receiveCustomerPhone
	 * 				货客户电话
	 * 			signDto.receiveCustomerMobilephone
	 * 				收货人手机
	 * 			signDto.active
	 * 				运单状态
	 * 			signDto.settleTimeStart
	 * 				结清时间起
	 * 			signDto.settleTimeEnd
	 * 				 结清时间止
	 * 			signDto.settleStatus
	 * 				结清状态
	 * 			signDto.isWholeVehicle
	 * 				是否整车运单
	 * 			signDto.endStockOrgCode
	 * 				最后库存code
	 * 			signDto.goodsAreaCode
	 * 				库区
	 * 			signDto.orderNo
	 * 				订单号
	 * @param currentInfo
	 * 		  currentInfo.empName 
	 * 				操作人名称
	 * 		  currentInfo.empCode
	 * 				 操作人编码
	 *        currentInfo.currentDeptName 
	 * 				签收部门名称
	 *        currentInfo.currentDeptCode 
	 * 				签收部门编码
	 * @see
	 */
	private void operatPullbackGoods(PdaDeliverSignDto dto,SignDto signDto,CurrentInfo currentInfo){
		LOGGER.info("--处理部分签收的拉回货物开始");//记录日志
		DeliverbillDetailDto deliverbillDetailDto   = new DeliverbillDetailDto();
		//到达联编号
		deliverbillDetailDto.setArrivesheetNo(dto.getArrivesheetNo());
		//派送单状态  已取消
		deliverbillDetailDto.setStatus(DeliverbillConstants.STATUS_CANCELED);
		String deliverbillNo = deliverbillService.queryDeliverNoByArriveSheetNo(deliverbillDetailDto);
		//如果查询的派送单不为空
		if(StringUtils.isNotBlank(deliverbillNo)){
			StayHandoverDetailEntity detailEntity = new StayHandoverDetailEntity();
			//件数 = 到达联件数-签收件数
			detailEntity.setGoodsQty(signDto.getArriveSheetGoodsQty() - dto.getSignGoodsQty());
			detailEntity.setDeliverbillNo(deliverbillNo);//派送单号
			detailEntity.setWaybillNo(dto.getWaybillNo());//运单号
			stayHandoverDetailService.addStayHandoverDetail(detailEntity);
			for (PdaSignDetailDto pdaSignDetailDto : dto.getPdaSignDetailDtos()) {
				if(PdaConstants.SITUATION_REFUSE.equals(pdaSignDetailDto.getSituation())){//如果流水号的情况为拒收
					StayHandoverserialEntity stayHandoverserialEntity = new StayHandoverserialEntity();
					stayHandoverserialEntity.setSerailno(pdaSignDetailDto.getSerialNo());//流水号
					stayHandoverserialEntity.settSrvStayHandoverdetailId(detailEntity.getId());//交接明细 id
					stayHandoverserialService.addSelective(stayHandoverserialEntity);//添加交接流水号
					//异常签收时操作
					ExceptionEntity exceptionEntity = new ExceptionEntity();
					//运单号
					exceptionEntity.setWaybillNo(dto.getWaybillNo());
					//货物异常
					exceptionEntity.setExceptionType(ExceptionProcessConstants.LABELEDGOOD_EXCEPTION);
					//异常环节
					exceptionEntity.setExceptionLink(ExceptionProcessConstants.DELIVER);
					//异常状态 ---已处理
					exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);
					//流水号
					exceptionEntity.setSerialNo(pdaSignDetailDto.getSerialNo());
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
					try {
						exceptionProcessService.addExceptionProcessInfo(exceptionEntity);//添加异常信息
					} catch (BusinessException e) {//捕获异常
						LOGGER.error("--新增异常信息报错");//记录日志
						throw new PdaProcessException(e.getErrorCode(), e);//抛出异常
					}
				}
			}
			//快递  add by 231438 自动排单项目需求添加
			if(waybillExpressService.onlineDetermineIsExpressByProductCode(signDto.getProductCode(),new Date())){
				//快递运单不做处理，走原来逻辑
			}else{//零担
				//派送交单退回DTO
				DeliverHandoverbillReturnDto returnDto = new DeliverHandoverbillReturnDto();
				//运单号
				returnDto.setWaybillNo(dto.getWaybillNo());
				//拉回件数==到达件数-签收件数
				returnDto.setBillQty(signDto.getArriveSheetGoodsQty() - dto.getSignGoodsQty());
				//调用接口 将运单进入待交单表
				deliverHandoverbillService.goodsBackReturnPreDeliverbill(returnDto);
			}
			if(detailEntity.getGoodsQty()>0){
				//实际货物实体
				ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
				//运单号
				actualFreightEntity.setWaybillNo(dto.getWaybillNo());
				//到达联件数
				actualFreightEntity.setGenerateGoodsQty(detailEntity.getGoodsQty());
				//排单件数
				actualFreightEntity.setArrangeGoodsQty(detailEntity.getGoodsQty());
				//更新到达件数
				actualFreightDao.updateActualFreightPartByWaybillNo(actualFreightEntity);

			}
			LOGGER.info("录入交接流水号，异常处理表信息完成");//记录日志
		}else {
			throw new PdaProcessException("查询派送单编号为空！");//查询派送单编号为空！！
		}
	}
	/**
	 * 对签收明细进行处理
	 * @author 
	 * 			foss-meiying
	 * @date 
	 * 			2013-1-17 上午11:02:06
	 * @param dto
	 * 			dto.waybillNo 
	 * 				运单号
	 * 			dto.arrivesheetNo
	 * 				 到达联编号
	 * 			dto.signDeptCode
	 * 				 签收部门
	 * 			dto.signTime
	 * 				 签收时间
	 * 			dto.signGoodsQty
	 * 				 签收件数
	 * 			dto.pdaSignDetailDtos
	 * 				 流水号集合
	 * 				dto.pdaSignDetailDtos.situation
	 * 					 签收情况 
	 * 					REFUSE为拒收
	 * 					SIGN为签收
	 * 				dto.pdaSignDetailDtos.situation.serialNo
	 * 					流水号
	 * 			dto.situation
	 * 				签收情况
	 * 				（正常签收、
	 * 				异常-破损、
	 * 				异常-潮湿、
	 * 				异常-污染、
	 * 				异常-其他、
	 * 				部分签收）
	 * 			dto.vehicleNo
	 * 				车牌号
	 * 			dto.driverCode
	 * 				司机工号
	 * 			dto.paymentType
	 * 				付款方式
	 * 			（现金、临欠、月结、
	 * 			银行卡、支票、电汇）
	 * 			dto.signNote
	 * 				签收备注
	 * @see
	 */
	private boolean operatSignDetailDtos(PdaDeliverSignDto dto){
		// 判断是否内物短少的开关,为true 时，上报QMS内物短少差错
		boolean isGoodshort = false;
		for (PdaSignDetailDto pdaSignDetailDto : dto.getPdaSignDetailDtos()) {//循环得到流水号集合
			if(PdaConstants.SITUATION_SIGN.equals(pdaSignDetailDto.getSituation())){//如果流水号的情况为签收
				SignDetailEntity signDetail = new SignDetailEntity();
				//流水号
				signDetail.setSerialNo(pdaSignDetailDto.getSerialNo());
				// 到达联编号
				signDetail.setArrivesheetNo(dto.getArrivesheetNo());
				if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(dto.getSituation())){
					// 签收情况
					signDetail.setSituation(dto.getSituation());
				}else {
					if(StringUtils.isNotEmpty(pdaSignDetailDto.getGoodShorts())){
						if(pdaSignDetailDto.getGoodShorts().equals(ArriveSheetConstants.Y)){
							signDetail.setGoodShorts(pdaSignDetailDto.getGoodShorts());
							//update start by--FOSS bieyexiong
							//内物短少，设置isGoodshort为true
							isGoodshort = true;
						}
					}
					// 签收情况
					signDetail.setSituation(pdaSignDetailDto.getSignSituation());
				}
				//添加签收明细信息
				signDetailService.addSignDetailInfo(signDetail);
			}
		}
		return isGoodshort;
	}
	
	/**
	 * 通过司机工号查询姓名 ,通过签收部门编码查询部门名称 
	 * @author foss-meiying
	 * @date 2012-12-18 下午5:21:08
	 * @param dto
	 * 			dto.waybillNo 
	 * 				运单号
	 * 			dto.arrivesheetNo
	 * 				 到达联编号
	 * 			dto.signDeptCode
	 * 				 签收部门
	 * 			dto.signTime
	 * 				 签收时间
	 * 			dto.signGoodsQty
	 * 				 签收件数
	 * 			dto.pdaSignDetailDtos
	 * 				 流水号集合
	 * 				dto.pdaSignDetailDtos.situation
	 * 					 签收情况 
	 * 					REFUSE为拒收
	 * 					SIGN为签收
	 * 				dto.pdaSignDetailDtos.situation.serialNo
	 * 					流水号
	 * 			dto.situation
	 * 				签收情况
	 * 				（正常签收、
	 * 				异常-破损、
	 * 				异常-潮湿、
	 * 				异常-污染、
	 * 				异常-其他、
	 * 				部分签收）
	 * 			dto.vehicleNo
	 * 				车牌号
	 * 			dto.driverCode
	 * 				司机工号
	 * 			dto.paymentType
	 * 				付款方式
	 * 			（现金、临欠、月结、银行卡、支票、电汇）
	 * 			dto.signNote
	 * 				签收备注
	 * @return
	 * @see
	 */
	private CurrentInfo getCurrentInfo(PdaDeliverSignDto dto){
		//通过司机工号查询姓名
		EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(dto.getDriverCode());
		UserEntity user = new UserEntity();
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		// 获取操作部门
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(dto.getSignDeptCode());
		if(emp != null){
			user.setEmployee(emp);//得到员工信息
		}else {
			emp = new EmployeeEntity();
			emp.setEmpName(PdaConstants.DEFAULT_EMP_NAME_PDA);//员工名称
			emp.setEmpCode(dto.getDriverCode());//员工编码
			user.setEmployee(emp);//得到员工信息
		}
		if(org != null){
			dept.setName(org.getName());// 得到部门名称
			dept.setUnifiedCode(org.getUnifiedCode());//标杆编码
		}else {//其他
			dept.setName(PdaConstants.DEFAULT_EMP_NAME_PDA);//默认的部门名称
		}
		//部门编码
		dept.setCode(dto.getSignDeptCode());
		return new CurrentInfo(user, dept);//返回信息
	}
	/**
	 *  调用结算子系统“签收接口”
	 *  系统调用财务接口完成财务出库 首先判断是否第一次签收。
	 *	如果是就调用，如果不是，就不调用。 
	 *	根据运单号判断到达联表里是否存在签收。
	 * @author foss-meiying
	 * @date 2012-12-18 下午5:20:47
	 * @param dto
	 * 			dto.waybillNo 
	 * 				运单号
	 * 			dto.arrivesheetNo
	 * 				 到达联编号
	 * 			dto.signDeptCode
	 * 				 签收部门
	 * 			dto.signTime
	 * 				 签收时间
	 * 			dto.signGoodsQty
	 * 				 签收件数
	 * 			dto.pdaSignDetailDtos
	 * 				 流水号集合
	 * 				dto.pdaSignDetailDtos.situation
	 * 					 签收情况 
	 * 					REFUSE为拒收
	 * 					SIGN为签收
	 * 				dto.pdaSignDetailDtos.situation.serialNo
	 * 					流水号
	 * 			dto.situation
	 * 				签收情况
	 * 				（正常签收、
	 * 				异常-破损、
	 * 				异常-潮湿、
	 * 				异常-污染、
	 * 				异常-其他、
	 * 				部分签收）
	 * 			dto.vehicleNo
	 * 				车牌号
	 * 			dto.driverCode
	 * 				司机工号
	 * 			dto.paymentType
	 * 				付款方式
	 * 			（现金、临欠、月结、银行卡、支票、电汇）
	 * 			dto.signNote
	 * 				签收备注
	 * @param currentInfo 当前登录人信息
	 * 		  currentInfo.empName 
	 * 				操作人名称
	 * 		  currentInfo.empCode
	 * 				 操作人编码
	 *        currentInfo.currentDeptName 
	 * 				签收部门名称
	 *        currentInfo.currentDeptCode 
	 * 				签收部门编码
	 * @param signDto
	 * 			signDto.arriveSheetGoodsQty 
	 * 				到达联件数
	 * 			signDto.identifyType 
	 * 				证件类型
	 *  		signDto.identifyCode 
	 * 				证件号码
	 * 	 		signDto.deliverymanName 
	 * 				提货人名称
	 * 	 		signDto.productCode 
	 * 				运输性质
	 *    		signDto.isWholeVehicle 
	 * 				是否整车运单
	 * 	  		signDto.lastLoadOrgCode 
	 * 				最终配载部门
	 * 			signDto.waybillNo 
	 * 				运单号
	 * 			signDto.receiveCustomerName
	 * 				收货人(收货客户名称)
	 * 			signDto.receiveCustomerPhone
	 * 				货客户电话
	 * 			signDto.receiveCustomerMobilephone
	 * 				收货人手机
	 * 			signDto.active
	 * 				运单状态
	 * 			signDto.settleTimeStart
	 * 				结清时间起
	 * 			signDto.settleTimeEnd
	 * 				 结清时间止
	 * 			signDto.settleStatus
	 * 				结清状态
	 * 			signDto.isWholeVehicle
	 * 				是否整车运单
	 * 			signDto.endStockOrgCode
	 * 				最后库存code
	 * 			signDto.goodsAreaCode
	 * 				库区
	 * 			signDto.orderNo
	 * 				订单号
	 * 			
	 * @see
	 */
	private void confirmTaking(PdaDeliverSignDto dto,CurrentInfo currentInfo,SignDto signDto){
		try {
			LineSignDto lineSigndto = new LineSignDto();
			// 系统调用财务接口完成财务出库 首先判断是否第一次签收。
			//如果是就调用，如果不是，就不调用。 根据运单号判断到达联表里是否存在签收。
			 ArriveSheetEntity arriveSheetEntity=new ArriveSheetEntity(dto.getWaybillNo(),ArriveSheetConstants.STATUS_SIGN,FossConstants.ACTIVE,FossConstants.NO);
			 Long signCountLong = arriveSheetManngerService.queryArriveExistSign(arriveSheetEntity);
			//系统调用财务接口完成财务出库
			 if(signCountLong == null || PdaConstants.ZERO >= signCountLong ){ 
				//签收日期
				 lineSigndto.setSignDate(signDto.getSystemDate());
				//运单号
				 lineSigndto.setWaybillNo(dto.getWaybillNo());
				//操作人名称
				 lineSigndto.setOperatorName(currentInfo.getEmpName());
				//操作人编码
				 lineSigndto.setOperatorCode(currentInfo.getEmpCode());
				//签收部门名称
				 lineSigndto.setSignOrgName(currentInfo.getCurrentDeptName());
				//签收部门编码 
				 lineSigndto.setSignOrgCode(currentInfo.getCurrentDeptCode());
				 //签收类型 专线正常签收
				 lineSigndto.setSignType(SettlementConstants.LINE_SIGN);
				//是PDA签收
				 lineSigndto.setIsPdaSign(FossConstants.YES);
				//运输性质
				 lineSigndto.setProductType(signDto.getProductCode());
				//是否整车运单
				 lineSigndto.setIsWholeVehicle(signDto.getIsWholeVehicle());
				 //签收情况   by 243921 
				 lineSigndto.setSignSituation(dto.getSituation());
				//CUBC签收   灰度改造    353654 ---------------------------start
				 String vestSystemCode = null;
                 try {
                 	List<String> arrayList = new ArrayList<String>();
                 	arrayList.add(lineSigndto.getWaybillNo());
                 	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                 			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".confirmTaking",
                 			SettlementConstants.TYPE_FOSS);
                 	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                 	List<VestBatchResult> list = response.getVestBatchResult();
                 	vestSystemCode = list.get(0).getVestSystemCode();	
     			} catch (Exception e) {
     				LOGGER.info("灰度分流失败,"+"运单号："+lineSigndto.getWaybillNo());
    				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
     			}
				if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
					lineSignService.confirmTaking(lineSigndto, currentInfo);
				}
				if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
//					//查询运单总件数
//					WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
//					//查询签收结果表
//			        WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(dto.getWaybillNo(),FossConstants.ACTIVE);
//			        WaybillSignResultEntity waybillSignResultEntity = waybillSignResultDao.queryWaybillSignResult(wayEntity);
//			        //获取已签收件数+此次签收件数与运单总件数比较判断全部和部分签收
//			        int signCount = (waybillSignResultEntity == null ? 0 : waybillSignResultEntity.getSignGoodsQty()) + dto.getSignGoodsQty();
//			        LOGGER.info("运单号:"+dto.getWaybillNo()+",PDA传递签收件数为:"+ dto.getSignGoodsQty());
//					String signStatus = signCount >= waybill.getGoodsQtyTotal() ? SignConstants.SIGN_STATUS_ALL
//							: SignConstants.SIGN_STATUS_PARTIAL;
//					if(SignConstants.SIGN_STATUS_ALL.equals(signStatus)){
						FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
						reqDto.setCurrentInfo(currentInfo);
						reqDto.setLineSignDto(lineSigndto);
						CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMeg())){
								LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
								throw new CUBCSignException(resultDto1.getMeg());		
							}else{
								throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
							}
						}			
//					}else{
//						LOGGER.info("PdaDeliverSignService.confirmTaking,运单号："+dto.getWaybillNo()+",部分签收,不调CUBC签收接口");
//					}
				}
				//CUBC签收   灰度改造    353654 ---------------------------end			
			 }else{
				 //这里不做处理
			 }
		} catch (SettlementException se) {//捕获异常
			LOGGER.error("调用财务接口抛出异常",se);//记录日志
			throw new PdaProcessException(se.getErrorCode(),se);//抛出异常
		}
		LOGGER.info("调用结算签收接口结束");//记录日志
	}
	/**
	 * 子母件签收   针对母件确认收入
	 * @param dto
	 * @param currentInfo
	 * @param signDto
	 */
	private void expConfirmTaking(PdaDeliverSignDto dto,CurrentInfo currentInfo,SignDto signDto){
		try {
			LineSignDto lineSigndto = new LineSignDto();
			//签收日期
			 lineSigndto.setSignDate(signDto.getSystemDate());
			//运单号
			 lineSigndto.setWaybillNo(dto.getWaybillNo());
			//操作人名称
			 lineSigndto.setOperatorName(currentInfo.getEmpName());
			//操作人编码
			 lineSigndto.setOperatorCode(currentInfo.getEmpCode());
			//签收部门名称
			 lineSigndto.setSignOrgName(currentInfo.getCurrentDeptName());
			//签收部门编码 
			 lineSigndto.setSignOrgCode(currentInfo.getCurrentDeptCode());
			 //签收类型 专线正常签收
			 lineSigndto.setSignType(SettlementConstants.LINE_SIGN);
			//是PDA签收
			 lineSigndto.setIsPdaSign(FossConstants.YES);
			//运输性质
			 lineSigndto.setProductType(signDto.getProductCode());
			//是否整车运单
			 lineSigndto.setIsWholeVehicle(signDto.getIsWholeVehicle());
			//签收情况 by 243921
			 lineSigndto.setSignSituation(dto.getSituation());
			//CUBC签收   灰度改造    353654 ---------------------------start
			 String vestSystemCode = null;
			 try {
              	List<String> arrayList = new ArrayList<String>();
              	arrayList.add(lineSigndto.getWaybillNo());
              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".expConfirmTaking",
              			SettlementConstants.TYPE_FOSS);
              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
              	List<VestBatchResult> list = response.getVestBatchResult();
              	vestSystemCode = list.get(0).getVestSystemCode();	
  			} catch (Exception e) {
  				LOGGER.info("灰度分流失败,"+"运单号："+lineSigndto.getWaybillNo());
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
  			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
				lineSignService.confirmTaking(lineSigndto, currentInfo);
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
//				//查询运单总件数
//				WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
//				//查询签收结果表
//		        WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(dto.getWaybillNo(),FossConstants.ACTIVE);
//		        WaybillSignResultEntity waybillSignResultEntity = waybillSignResultDao.queryWaybillSignResult(wayEntity);
//		        //获取已签收件数+此次签收件数与运单总件数比较判断全部和部分签收
//		        int signCount = (waybillSignResultEntity == null ? 0 : waybillSignResultEntity.getSignGoodsQty()) + dto.getSignGoodsQty();
//		        LOGGER.info("运单号:"+dto.getWaybillNo()+",PDA传递签收件数为:"+ dto.getSignGoodsQty());
//				String signStatus = signCount >= waybill.getGoodsQtyTotal() ? SignConstants.SIGN_STATUS_ALL
//						: SignConstants.SIGN_STATUS_PARTIAL;
//				if(SignConstants.SIGN_STATUS_ALL.equals(signStatus)){
					FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
					reqDto.setCurrentInfo(currentInfo);
					reqDto.setLineSignDto(lineSigndto);
					LOGGER.info("--调用CUBC“签收接口”开始传入参数：----"+ReflectionToStringBuilder.toString(reqDto));//记录日志
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
//					LOGGER.info("PdaDeliverSignService.expConfirmTaking,运单号："+dto.getWaybillNo()+",部分签收,不调CUBC签收接口");
//				}
			}
			//CUBC签收   灰度改造    353654 ---------------------------end			 
		} catch (SettlementException se) {//捕获异常
			LOGGER.error("调用财务接口抛出异常",se);//记录日志
			throw new PdaProcessException(se.getErrorCode(),se);//抛出异常
		}
		LOGGER.info("调用结算签收接口结束");//记录日志
	}
	/**
	 * 更新到达联信息
	 * @author 
	 * 			foss-meiying
	 * @date 
	 * 			2012-12-18 上午11:18:05
	 * @param  dto
	 * 			dto.waybillNo 
	 * 				运单号
	 * 			dto.arrivesheetNo
	 * 				 到达联编号
	 * 			dto.signDeptCode
	 * 				 签收部门
	 * 			dto.signTime
	 * 				 签收时间
	 * 			dto.signGoodsQty
	 * 				 签收件数
	 * 			dto.pdaSignDetailDtos
	 * 				 流水号集合
	 * 				dto.pdaSignDetailDtos.situation
	 * 					 签收情况 
	 * 					REFUSE为拒收
	 * 					SIGN为签收
	 * 				dto.pdaSignDetailDtos.situation.serialNo
	 * 					流水号
	 * 			dto.situation
	 * 				签收情况
	 * 				（正常签收、
	 * 				异常-破损、
	 * 				异常-潮湿、
	 * 				异常-污染、
	 * 				异常-其他、
	 * 				部分签收）
	 * 			dto.vehicleNo
	 * 				车牌号
	 * 			dto.driverCode
	 * 				司机工号
	 * 			dto.paymentType
	 * 				付款方式
	 * 			（现金、临欠、月结、银行卡、支票、电汇）
	 * 			dto.signNote
	 * 				签收备注
	 * @param 	currentInfo
	 * 			currentInfo.operate 
	 * 				操作人
	 *          currentInfo.operatorCode 
	 *          	操作人编码
	 *          currentInfo.operateOrgName 
	 *          	操作部门名称
	 *          currentInfo.operateOrgCode 
	 *          	操作部门编码
	 * @return 
	 * @see
	 */
	private ArriveSheetEntity updateArriveSheet(PdaDeliverSignDto dto,CurrentInfo currentInfo,SignDto signDto){
		ArriveSheetEntity entity = new ArriveSheetEntity();
		//到达联编号
		entity.setArrivesheetNo(dto.getArrivesheetNo());
		//签收时间
		entity.setSignTime(dto.getSignTime());
		//到达联状态为签收
		entity.setStatus(ArriveSheetConstants.STATUS_SIGN);
		//签收件数
		entity.setSignGoodsQty(dto.getSignGoodsQty());
		//备注
		entity.setSignNote(dto.getSignNote());
		//签收情况
		entity.setSituation(dto.getSituation());
		//是PDA签收
		entity.setIsPdaSign(FossConstants.YES);
		//签收操作时间
		entity.setOperateTime(signDto.getSystemDate());
		entity.setReceiveMethod(ArriveSheetConstants.SIGN_RECEIVE_METHOD_DELIVER);//提货方式--派送
		// 操作人
		entity.setOperator(currentInfo.getEmpName());
		// 操作人编码
		entity.setOperatorCode(currentInfo.getEmpCode());
		// 操作部门名称
		entity.setOperateOrgName(currentInfo.getCurrentDeptName());
		// 操作部门编码
		entity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
		// 快递需求MANA-581
		entity.setDeliverymanName(dto.getDeliverymanName()!=null?dto.getDeliverymanName():null);
		// 签收人类型
		entity.setDeliverymanType(dto.getDeliverymanType()!=null?dto.getDeliverymanType():null);
		//签收前的到达联状态为派送中 
		//--并发控制
		entity.setOldStatus(ArriveSheetConstants.STATUS_DELIVER);
		if(signDto.getArriveSheetGoodsQty()!= null && signDto.getArriveSheetGoodsQty()>dto.getSignGoodsQty()){
			entity.setSignStatus(SignConstants.SIGN_STATUS_PARTIAL);//到达联签收状态为部分签收
		}else {
			entity.setSignStatus(SignConstants.SIGN_STATUS_ALL);//到达联签收状态为全部签收
		}
		if(arriveSheetManngerService.updateArriveSheetByArrivesheetNo(entity)<=0){//如果修改到达联失败
			//记录错误信息
			LOGGER.error("签收失败，请重新查询一下!"+ ReflectionToStringBuilder.toString(entity));//记录日志
			throw new PdaProcessException("并发控制中，签收失败，请重新查询一下!");//抛出异常
		}
		//运单号
		entity.setWaybillNo(dto.getWaybillNo());
		return entity;//返回到达联信息
	}
	/**
	 * Sets 
	 * 		the 
	 * 		运单签收结果service.
	 *
	 * @param 
	 * 		waybillSignResultService 
	 * 		the new 运单签收结果service
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	/**
	 * Sets
	 * 		 the 
	 * 		到达联service.
	 *
	 * @param 
	 * 		arriveSheetManngerService 
	 * 		the new 到达联service
	 */
	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}
	/**
	 * Sets 
	 * 		the
	 * 		 运单service.
	 *
	 * @param 
	 * 		waybillManagerService 
	 * 		the new 运单service
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 * Sets 
	 * 		the 
	 * 		结清货款Service.
	 *
	 * @param 
	 * 		repaymentService 
	 * 		the new 结清货款Service
	 */
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}
	/**
	 * Sets 
	 * 		the
	 * 		 结算签收Service.
	 *
	 * @param
	 *		lineSignService 
	 * 		the new 结算签收Service
	 */
	public void setLineSignService(ILineSignService lineSignService) {
		this.lineSignService = lineSignService;
	}
	/**
	 * Sets 
	 * 		the 
	 * 		员工service.
	 *
	 * @param 
	 * 		employeeService 
	 * 		the new 
	 * 		员工service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * Sets 
	 * 		the
	 * 		 组织信息service.
	 *
	 * @param 
	 * 		orgAdministrativeInfoService
	 * 		 the new 
	 * 		组织信息service
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * Sets 
	 * 		the 
	 * 		签收明细service.
	 *
	 * @param 
	 * 		signDetailService
	 * 		 the new 
	 * 		签收明细service
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}
	/**
	 * Sets 
	 * 		the 
	 * 		actualFreightService.
	 *
	 *
	 * @param 
	 * 		actualFreightService
	 * 		 the new actualFreightService
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	/**
	 * Sets 
	 * 		the
	 * 		 结算应收单服务.
	 *
	 * @param
	 * 		 billReceivableService
	 * 			 the 
	 * 		new 结算应收单服务
	 * 		
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}
	/**
	 * Sets 
	 * 		the
	 * 		 处理异常主数据Service层.
	 *
	 * @param 
	 * 		exceptionProcessService 
	 * 		the new 
	 * 		处理异常主数据Service层
	 */
	public void setExceptionProcessService(IExceptionProcessService exceptionProcessService) {
		this.exceptionProcessService = exceptionProcessService;
	}
	/**
	 * Sets 
	 * 		the
	 * 		 派送单service.
	 *
	 * @param 
	 * 		deliverbillService 
	 * 		the new 派送单service
	 */
	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}
	/**
	 * Sets 
	 * 		the 
	 * 		交接 流水号service.
	 * @param 
	 * 		stayHandoverserialService 
	 * 		the new 交接 流水号service
	 */
	public void setStayHandoverserialService(IStayHandoverserialService stayHandoverserialService) {
		this.stayHandoverserialService = stayHandoverserialService;
	}
	/**
	 * Sets 
	 * 		the
	 * 		 交接明细Service.
	 * @param 
	 * 		stayHandoverDetailService 
	 * 		the new 交接明细Service
	 */
	public void setStayHandoverDetailService(IStayHandoverDetailService stayHandoverDetailService) {
		this.stayHandoverDetailService = stayHandoverDetailService;
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
	
	public void setExpressPartSalesDeptService(IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}
	public void setGpsPdaService(IGpsPdaService gpsPdaService) {
		this.gpsPdaService = gpsPdaService;
	}
	public void setFimsPdaService(IFimsPdaService fimsPdaService) {
		this.fimsPdaService = fimsPdaService;
	}
	public void setPickupService(IPickupService pickupService) {
		this.pickupService = pickupService;
	}
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
	/**
	 * Sets the 外场相关共通接口.
	 *
	 * @param handleQueryOutfieldService the new 外场相关共通接口
	 */
	public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}
		
	public void setRecordErrorWaybillService(
			IRecordErrorWaybillService recordErrorWaybillService) {
		this.recordErrorWaybillService = recordErrorWaybillService;
	}
	public void setRookieService(IRookieService rookieService) {
		this.rookieService = rookieService;
	}
	public void setWaybillExpressService(IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	public void setPrintAgentWaybillService(
			IPrintAgentWaybillService printAgentWaybillService) {
		this.printAgentWaybillService = printAgentWaybillService;
	}

    public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
        this.pdaPosManageService = pdaPosManageService;
    }
}