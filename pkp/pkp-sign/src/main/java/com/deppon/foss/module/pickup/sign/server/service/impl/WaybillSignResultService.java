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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/WaybillSignResultService.java
 * 
 * FILE NAME        	: WaybillSignResultService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 *****************************************************************************
 *修订记录 
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
 1.5.2界面原型

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
 *
 **/
package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.esb.inteface.domain.foss2ptp.PartnerUpdateTakeEffectTimeRequest;
import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.base.common.api.shared.exception.SMSSendLogException;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaReturnDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaOrderDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaPullbackgoodsDto;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IContrabandSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISerialSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SerialSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillCondition;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ContrabandInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.OrderStatusToCrmDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.PullbackRenewalDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordUnnormalSignWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillExpressDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWeixinSendService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WeixinConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
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
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICUBCSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IElectronicInvoiceSendService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOutWarehouseExceptionService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCSignOrRevSignResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.ElectronicInvoiceDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FOSSSignOrRevSignRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCSignException;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 运单签收结果
 * 
 * @author foss-meiying
 * @date 2012-10-23 上午11:52:17
 * @since
 * @version
 */
public class WaybillSignResultService implements IWaybillSignResultService {
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.WaybillSignResultService";
	
	/**
	 * add by 353654  注入CUBC签收服务
	 */
	private ICUBCSignService cUBCSignService;
	
	public void setcUBCSignService(ICUBCSignService cUBCSignService) {
		this.cUBCSignService = cUBCSignService;
	}

    /**
     * 数据字典Dao add by 353654 DN201608260005
     */
    private IDataDictionaryValueDao dataDictionaryValueDao;

    public void setDataDictionaryValueDao(IDataDictionaryValueDao dataDictionaryValueDao) {
        this.dataDictionaryValueDao = dataDictionaryValueDao;
    }

    /**
     * 记录 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WaybillSignResultService.class);

    /**
     * 运单签收结果Dao
     */
    private IWaybillSignResultDao waybillSignResultDao;

    /**
     * 运单状态服务接口
     */
    private IActualFreightService actualFreightService;

    /**
     * 消息服务
     */
    private IMessageService messageService;

    /**
     * 客户通知
     */
    private INotifyCustomerService notifyCustomerService;

    /**
     * 运单完结状态操作Service
     */
    private IWaybillTransactionService waybillTransactionService;

    /**
     * 计算&调整走货路径类
     */
    private ICalculateTransportPathService calculateTransportPathService;

    /**
     * 业务监控服务
     */
    private IBusinessMonitorService businessMonitorService;

    /**
     * 结清货款Service
     */
    private IRepaymentService repaymentService;

    /**
     * 短信模板service接口
     */
    private ISMSTempleteService sMSTempleteService;

    /**
     * CRM订单更新服务
     */
    private ICrmOrderJMSService crmOrderJMSService;

    /**
     * 到达联service
     */
    private IArriveSheetManngerService arriveSheetManngerService;

    /**
     * 运单service
     */
    private IWaybillManagerService waybillManagerService;

    /** 查询快递运单接口 **/
    private IWaybillExpressDao waybillExpressDao;

    /**
     * 张新 打印代理面单service
     */
    private IPrintAgentWaybillService printAgentWaybillService;

    /**
     * 组织信息 service接口
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    private ISignChangeService signChangeService;

    /**
     * 财务出库红冲操作service
     */
    protected IOutWarehouseExceptionService outWarehouseExceptionService;

    /***
     * 客户信息服务类
     */
    private ICustomerDao customerDao;

    /**
     * 小票红冲服务类
     */
    private IOtherRevenueService otherRevenueService;

    /**
     * 推送微信服务类
     */
    private IWeixinSendService weixinSendService;

    /**
     * 将电子发票数据发送给发票系统
     */
    private IElectronicInvoiceSendService electronicInvoiceSendService;

    /**
     * 员工service
     */
    private IEmployeeService employeeService;

    /**
     * 发送短信接口
     */
    private ISMSSendLogService smsSendLogService;

    /**
     * 快递车辆信息
     */
    private IExpressVehiclesService expressVehiclesService;

    /**
     * 快递服务类接口
     */
    private IWaybillExpressService waybillExpressService;

    /**
     * 子母件服务接口
     */
    private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;

    /**
     * 外发按流水签收服务类
     */
    private ISerialSignResultService serialSignResultService;

    /***
     * 记录异常运单 上报QMS的Service
     */
    private IRecordErrorWaybillDao recordErrorWaybillDao;

    /**
     * 快递服务类接口 注入
     */
    public void setWaybillExpressService(IWaybillExpressService waybillExpressService) {
        this.waybillExpressService = waybillExpressService;
    }

    /**
     * 子母件接口 注入
     */
    public void setWaybillRelateDetailEntityService(
            IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
        this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
    }

    /**
     * 推送微信服务类
     */
    public void setWeixinSendService(IWeixinSendService weixinSendService) {
        this.weixinSendService = weixinSendService;
    }

    public void setOtherRevenueService(IOtherRevenueService otherRevenueService) {
        this.otherRevenueService = otherRevenueService;
    }

    public void setCustomerDao(ICustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * 违禁品service
     */
    private IContrabandSignService contrabandSignService;

    public void setContrabandSignService(IContrabandSignService contrabandSignService) {
        this.contrabandSignService = contrabandSignService;
    }

    /**
     * 修改运单签收结果部分信息
     * 
     * @author foss-meiying
     * @date 2012-12-18 下午6:58:56
     * @param waybillSignResultEntity
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#updateWaybillSignResult
     *      (com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
     */
    @Override
    @Transactional
    public int updateWaybillSignResult(WaybillSignResultEntity waybillSignResultEntity) {
        return waybillSignResultDao.updateWaybillSignResult(waybillSignResultEntity);
    }

    /**
     * 保存运单签收结果信息
     * 
     * @author foss-meiying
     * @date 2012-12-18 下午6:59:59
     * @param waybillSignResultEntity
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#addWaybillSignResult
     *      (com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
     */
    @Override
    @Transactional
    public Integer addWaybillSignResult(WaybillSignResultEntity waybillSignResultEntity) {
        return waybillSignResultDao.addWaybillSignResult(waybillSignResultEntity);
    }

    /**
     * 判断该运单是否已经录入运单签收结果信息， 如果录入，作废当前运单号. 完善最新运单签收结果数据返回。
     * 
     * @author foss-meiying
     * @date 2012-12-18 下午7:02:30
     * @param entity entity.waybill 运单号 entity.arrivesheetNo 到达联编号 entity.deliverymanname 提货人名称
     *        entity.identifyType 证件类型 entity.identifyCode 证件号码 entity.situation 签收情况
     *        entity.arriveSheetGoodsQty 到达联件数 entity.signGoodsQty 签收件数 entity.signNote 签收备注
     *        entity.signTime 签收时间 entity.status 到达联状态 entity.isPdaSign 是否PDA签到 entity.operateTime
     *        签收操作时间 entity.operator 操作人 entity.operatorCode 操作人编码 entity.operateOrgName 操作部门名称
     *        entity.operateOrgCode 操作部门编码 entity.destroyed 是否作废 entity.isPrinted 是否打印
     *        entity.printtimes 打印次数 entity.createUserName 创建人 entity.createUserCode 创建人编码
     *        entity.createOrgName 创建部门 entity.createOrgCode 创建部门编码 entity.createTime 创建时间
     *        entity.isSentRequired 是否必送货 entity.isNeedInvoice 是否需要发票 entity.preNoticeContent 提前通知内容
     *        entity.isRfcing 是否审批中
     * @param waybill 运单信息
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#checkWaybillSignResultSign
     *      (com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
     */
    @Override
    public WaybillSignResultEntity checkWaybillSignResultSign(ArriveSheetEntity entity,
            WaybillEntity waybill, Date modifyTime, CurrentInfo currentInfo) {
        SignRfcEntity checkEntity = new SignRfcEntity();
        WeixinSendDto dto = new WeixinSendDto();
        // 运单号
        checkEntity.setWaybillNo(entity.getWaybillNo());
        // 更改单状态--审批中(1:审批中，2:通过,3：拒绝)
        checkEntity.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
        if (signChangeService.isRfc(checkEntity)) {
            throw new SignException(SignException.SIGN_RFC_SIGN_APPROVALIN); // 该运单有到达更改还没有审批，请审批后再签收
        }
        // 传入参数(运单号,当前运单号生效)
        WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(entity.getWaybillNo(),
                FossConstants.ACTIVE);
        // 根据运单号 查询运单签收结果
        WaybillSignResultEntity waybillSign = waybillSignResultDao.queryWaybillSignResult(wayEntity);
        // 如果当前运单不是第一次添加
        if (waybillSign != null) {
            // 运单签收结果里作废当前运单号
            this.invalidWaybillSignResult(waybillSign.getId(), modifyTime);
            // 运单签收结果的签收件数=原先运单签收的件数+到达联签收件数
            wayEntity.setSignGoodsQty(entity.getSignGoodsQty() + waybillSign.getSignGoodsQty());
            // 签收情况
            wayEntity.setSignSituation(SignConstants.NORMAL_SIGN.equals(entity.getSituation()) ? waybillSign
                    .getSignSituation() : SignConstants.UNNORMAL_SIGN);
            dto.setWaybillNo(entity.getWaybillNo());
            // 本次签收件数
            dto.setCurrentPieces(entity.getSignGoodsQty());
            // 已处理件数
            dto.setProcessedPieces(wayEntity.getSignGoodsQty());
            // 状态类型
            dto.setStateType(entity.getSituation());
        } else {// 如果当前运单是第一次添加
            // 运单签收结果的签收件数 = 到达联签收件数
            wayEntity.setSignGoodsQty(entity.getSignGoodsQty());
            // 签收情况如果存在一次异常签收则为异常签收,全部为正常签收,则为正常签收
            wayEntity
                    .setSignSituation(SignConstants.NORMAL_SIGN.equals(entity.getSituation()) ? SignConstants.NORMAL_SIGN
                            : SignConstants.UNNORMAL_SIGN);
            dto.setWaybillNo(entity.getWaybillNo());
            // 本次签收件数
            dto.setCurrentPieces(entity.getSignGoodsQty());
            // 已处理件数
            dto.setProcessedPieces(entity.getSignGoodsQty());
            // 状态类型
            dto.setStateType(entity.getSituation());
        }
        // 提货人
        wayEntity.setDeliverymanName(entity.getDeliverymanName() == null ? "" : entity.getDeliverymanName());
        if (WaybillConstants.directDetermineIsExpressByProductCode(waybill.getProductCode())) {
            // 快递 DN201511090005 添加“签收人类型”字段 243921
            if (StringUtils.isNotEmpty(entity.getDeliverymanType())) {
                dto.setSignName(DictUtil.rendererSubmitToDisplay(entity.getDeliverymanType(),
                        DictionaryConstants.PKP_SIGN_PERSON_TYPE));
            } else {
                dto.setSignName("代理");
            }
        } else { // 零担 签收人姓名
            dto.setSignName(StringUtil.replace(wayEntity.getDeliverymanName(),
                    NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE, ""));
        }
        // 签收时间
        dto.setCreateTime(entity.getSignTime() == null ? new Date() : entity.getSignTime());
        wayEntity.setCreateOrgCode(entity.getOperateOrgCode());// 签收部门编码
        wayEntity.setCreateOrgName(entity.getOperateOrgName());// 签收部门名称
        wayEntity.setCreator(entity.getOperator());// 操作人
        wayEntity.setCreatorCode(entity.getOperatorCode());// 操作人编码
        wayEntity.setReceiveMethod(entity.getReceiveMethod());// 提货方式（记录最新的一次）
        // 生效
        wayEntity.setActive(FossConstants.ACTIVE);
        // 生效时间为当前时间
        wayEntity.setCreateTime(modifyTime);
        // 时效时间为空，添加时采用默认值
        wayEntity.setModifyTime(null);
        // 签收时间
        wayEntity.setSignTime(entity.getSignTime());
        // 证件号码
        wayEntity.setIdentifyCode(entity.getIdentifyCode() == null ? "" : entity.getIdentifyCode());
        // 证件类型
        wayEntity.setIdentifyType(entity.getIdentifyType() == null ? "" : entity.getIdentifyType());
        // 得到签收备注
        wayEntity.setSignNote(entity.getSignNote());
        // 签收状态--全部签收/部分签收
        wayEntity
                .setSignStatus((wayEntity.getSignGoodsQty() >= waybill.getGoodsQtyTotal()) ? SignConstants.SIGN_STATUS_ALL
                        : SignConstants.SIGN_STATUS_PARTIAL);
        // 给快递员工号赋值，取自页面传入到达联的快递员工号 add by 231438
        wayEntity.setExpressEmpCode(entity.getSendExpressEmpCode() == null ? "" : entity
                .getSendExpressEmpCode());
        // 是否伙伴派送
        wayEntity.setIsPartner(entity.getIsPartner());
        // 伙伴姓名
        wayEntity.setPartnerName(entity.getPartnerName());
        // 伙伴手机
        wayEntity.setPartnerPhone(entity.getPartnerPhone());
        // 如果签收状态为全部签收
        if (SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())) {
            // 标识业务完结
            WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
            // 运单号
            waybillTransactionEntity.setWaybillNo(wayEntity.getWaybillNo());
            waybillTransactionService.updateBusinessOver(waybillTransactionEntity);
            if (FossConstants.NO.equals(waybill.getIsWholeVehicle())) {// 如果该运单不是整车运单
                // 调中转的走货路径接口
                try {
                    calculateTransportPathService.signIn(entity.getWaybillNo(),
                            TransportPathConstants.TRANSPORTPATH_STATUS_SIGNIN);
                } catch (TfrBusinessException e) {// 捕获异常
                    LOGGER.error("调中转的走货路径接口有异常", e);// 异常记录
                    throw new SignException(e.getMessage(), e);
                }
            } else {
                // 这里不作处理
            }

            // 异常签收字段上报（必须全部签收，只包含零担，不包括快递），此差错不包含丢货、弃货、违禁品以及内物短少，
            // 不是快递、不是正常签收、不是内物短少(同票多类中的内物短少、以及分批签收的内物短少由job去判断)时
            if (!WaybillConstants.directDetermineIsExpressByProductCode(waybill.getProductCode())
                    && !SignConstants.NORMAL_SIGN.equals(entity.getSituation())
                    && !ReportConstants.SIGN_SITUATION_UNNORMAL_GOODSHORT.equals(entity.getSituation())) {
                this.saveRecordUnnormalErrorInfo(entity);
            }
        }

        /**
         * 新增MANA-771需求，添加微信消息推送的方法
         * 
         * @author Foss-105888-Zhangxingwang
         * @date 2014年3月10日 14:04:04
         */
        if (!wayEntity.getSignStatus().equals(ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS)) {
            LOGGER.info("签收完毕，微信消息开发推送。参数类型:" + ReflectionToStringBuilder.toString(dto));
            ResultDto resultDto = weixinSendService.sysnWeixinInfoForWebSiteUnDirectly(dto,
                    WeixinConstants.WEIXIN_SIGN_TYPE);
            if (ResultDto.FAIL.equals(resultDto.getCode())) {
                LOGGER.info("推送微信失败，错误详情：" + resultDto.getMsg());
            }
            LOGGER.info("微信消息处理完毕,此处略去一万字");
        } else {
            // 如果签收情况为异常弃货，则不给微信推送签收结果
        }
        /**
         * 新增DMANA-464需求，签收时，是否对已经产生的保管费小票记录进行红冲
         * 
         * @author 130376-yangkang
         * @date 2014年5月26日 10:04:04 业务要求开单付款方式为月结，提货方式为非自提货物；整车、偏线、空运及快递货物，不会产生保管费小票记录 不需要进行红冲
         */
        if (!WaybillConstants.MONTH_PAYMENT.equals(waybill.getPaidMethod())
                && !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())
                && !ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybill.getProductCode())
                && !ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(waybill.getProductCode())
                && !waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(),
                        waybill.getBillTime())
                && (WaybillConstants.SELF_PICKUP.equals(waybill.getReceiveMethod()) || WaybillConstants.INNER_PICKUP
                        .equals(waybill.getReceiveMethod()))) {
	
        	//红冲小票记录---------灰度----------start---------------356354
        	List<OtherRevenueEntity> otherRevenueEntityList = null;
        	String vestSystemCode = null;
        	try {
        		ArrayList<String> arrayList = new ArrayList<String>();
            	arrayList.add(entity.getWaybillNo());
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".checkWaybillSignResultSign",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
        		List<VestBatchResult> list = response.getVestBatchResult();
        		vestSystemCode = list.get(0).getVestSystemCode();		
        	} catch (Exception e) {
        		LOGGER.info("灰度分流失败,"+"运单号："+entity.getWaybillNo());
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
        	}
        	if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
        		otherRevenueEntityList = otherRevenueService.queryOtherRevenueByWayBillNOAndInvoiceCategory(waybill.getWaybillNo(), currentInfo);
        		if (otherRevenueEntityList != null && otherRevenueEntityList.size() > 0
        				&& entity.getSituation().contains("UNNORMAL")) {
        			
        			// 调用结算接口 对小票记录进行红冲操作
        			for (OtherRevenueEntity otherRevenueEntity : otherRevenueEntityList) {
        				OtherRevenueDto otherRevenueDto = new OtherRevenueDto();
        				otherRevenueDto.setActive(FossConstants.ACTIVE);
        				otherRevenueDto.setOtherRevenueNo(otherRevenueEntity.getOtherRevenueNo());
        				//红冲小票记录---------灰度----------end---------------356354
        				otherRevenueService.cancelOtherRevenue(otherRevenueDto,currentInfo);
        			}
        		}
        	}
        	if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
        		//TODO  查询  红冲小票  是否在CUBC异常签收时就完成
        	}
        }
        return wayEntity;
    }

    /**
     * @author: foss-231434-bieyexiong
     * @description: foss记录异常 上报QMS
     * @date:2016年02月18日 下午15:37:21
     */
    public void saveRecordUnnormalErrorInfo(ArriveSheetEntity entity) {
        if (entity != null) {
            LOGGER.info("*************保存QMS异常签收线上划责信息***********start");
            // 上报数据
            RecordUnnormalSignWaybillDto unnormalDto = new RecordUnnormalSignWaybillDto();
            // 主键id
            unnormalDto.setId(UUIDUtils.getUUID());
            // 运单号
            unnormalDto.setWaybillNo(entity.getWaybillNo());
            // 签收时间
            unnormalDto.setSignTime(entity.getSignTime() == null ? new Date() : entity.getSignTime());
            // 签收备注
            unnormalDto.setSignNote(entity.getSignNote());
            // 是否已上报(默认为Y，未上报)
            unnormalDto.setActive("Y");
            // 创建时间
            unnormalDto.setCreateTime(new Date());
            // 备注
            unnormalDto.setNote("未上报");

            // 保存异常划责信息
            recordErrorWaybillDao.insertUnnormalEntity(unnormalDto);

            LOGGER.info("*************保存QMS异常签收线上划责信息***********end");
        }
    }

    /**
     * 查询运单表里的货物总件数
     * 
     * @author foss-meiying
     * @date 2012-12-18 下午7:01:08
     * @param entity entity.waybillNo 运单号 entity.signSituation 签收情况 entity.signNote 签收备注
     *        entity.identifyType 证件类型 entity.identifyCode 证件号码 entity.settleStatus 结清状态
     *        entity.signGoodsQty 签收件数 entity.signTime 签收时间 entity.active 是否有效 entity.modifyTime
     *        时效时间 entity.createTime 生效时间 entity.isPdaSign 是否PDA签到 entity.agentCode 代理编码
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#queryWaybillQty
     *      (com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
     */
    @Override
    public Integer queryWaybillQty(WaybillSignResultEntity entity) {
        return waybillSignResultDao.queryWaybillQty(entity);
    }

    /**
     * 据运单号,active = 'Y'查询运单签结果里的运单信息
     * 
     * @author foss-meiying
     * @date 2012-12-18 下午7:01:42
     * @param entity.waybillNo 运单号 entity.signSituation 签收情况 entity.signNote 签收备注
     *        entity.identifyType 证件类型 entity.identifyCode 证件号码 entity.settleStatus 结清状态
     *        entity.signGoodsQty 签收件数 entity.signTime 签收时间 entity.active 是否有效 entity.modifyTime
     *        时效时间 entity.createTime 生效时间 entity.isPdaSign 是否PDA签到 entity.agentCode 代理编码
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#queryWaybillSignResultByWaybillNo
     *      (com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
     */
    @Override
    public WaybillSignResultEntity queryWaybillSignResultByWaybillNo(WaybillSignResultEntity entity) {
        return waybillSignResultDao.queryWaybillSignResult(entity);
    }
   
    /**
     * 根据签收时间范围查询运单签结果里的运单信息
     * 
     * @author foss-caodajun 
     * FOSS设置定时任务将签收结果表同步到PTP，每天 05:00,23:00 点进行签收时间同步,
	 * 同步的为前一天签收的数据， PTP保存并更新最新的签收时间.
     */
    @Override
    public List<PartnerUpdateTakeEffectTimeRequest> queryWaybillSignResultBySignTime(Date signTimeStart,Date signTimeEnd){
    	return waybillSignResultDao.queryWaybillSignTimeResult(signTimeStart,signTimeEnd);
    }
    
    /**
     *  * 提供给中转：外发时校验是否已签收
     * 
     * @author 231438
     */
    @Override
    public WaybillSignResultEntity checkWaybillSignResult(WaybillSignResultEntity entity) {
        if (null == entity) {
            throw new SignException("传入参数为空！");
        }
        WaybillSignResultEntity wayEntity = waybillSignResultDao.queryWaybillSignResult(entity);
        if (null != wayEntity && SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())) {
            // 运单存在有效的全部签收记录
            return wayEntity; // 已签收
        }
        // 如果存在部分签收，查流水号签收情况表
        if (StringUtils.isNotEmpty(entity.getSerialNo())) {
            SerialSignResultEntity serEntity = new SerialSignResultEntity();
            serEntity.setWaybillNo(entity.getWaybillNo());
            serEntity.setSerialNo(entity.getSerialNo());
            serEntity.setActive(FossConstants.YES);// 有效
            SerialSignResultEntity result = serialSignResultService.queryByConditions(serEntity);
            if (null != result) {
                // 外发流水签收不为空,将之查到的签收结果（部分签收）返回
                return wayEntity; // 运单流水号已签收
            }
        }
        // 以上情况都不满足，表示为未签收返回false
        return null;
    }

    /**
     * 根据运单号，active = 'Y'，和签收状态，查询第一次全部签收的签收结果信息
     * 
     * @author foss-bieyexiong
     * @date 2015-04-21 下午16:30:01
     * @param entity
     * @return
     */
    @Override
    public WaybillSignResultEntity queryFirstSignAllByEntity(WaybillSignResultEntity entity) {
        return waybillSignResultDao.queryFirstSignAllByEntity(entity);
    }

    /**
     * 根据运单号查询运单部分信息
     * 
     * @author foss-meiying
     * @date 2012-12-18 下午7:02:52
     * @param waybillNo
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#queryWaybillActualFreightPartByWaybillNo
     */
    @Override
    public WaybillDto queryWaybillActualFreightPartByWaybillNo(String waybillNo) {
        return waybillSignResultDao.queryWaybillActualFreightPartByWaybillNo(waybillNo);
    }

    /**
     * 根据主键查询运单签收结果
     * 
     * @author foss-meiying
     * @date 2012-12-18 下午7:03:10
     * @param id 主键id
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService
     *      #queryWaybillSignResultById(java.lang.String)
     */
    @Override
    public WaybillSignResultEntity queryWaybillSignResultById(String id) {
        return waybillSignResultDao.queryWaybillSignResultById(id);
    }

    /**
     * 根据运单号查询运单签收结果里第一次添加的签收时间
     * 
     * @author foss-meiying
     * @date 2012-12-18 下午7:03:42
     * @param waybillNo 运单号
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService
     *      #queryFirstSignTimeByWaybillNo(java.lang.String)
     */
    @Override
    public Date queryFirstSignTimeByWaybillNo(String waybillNo) {
        return waybillSignResultDao.queryFirstSignTimeByWaybillNo(waybillNo);
    }

    /**
     * 更新运单签收结果
     * 
     * @author foss-meiying
     * @date 2012-12-18 下午7:04:12
     * @param waybillSignResultEntity entity.waybillNo 运单号 entity.signSituation 签收情况 entity.signNote
     *        签收备注 entity.identifyType 证件类型 entity.identifyCode 证件号码 entity.settleStatus 结清状态
     *        entity.signGoodsQty 签收件数 entity.signTime 签收时间 entity.active 是否有效 entity.modifyTime
     *        时效时间 entity.createTime 生效时间 entity.isPdaSign 是否PDA签到 entity.agentCode 代理编码
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#updateWaybillSignResultById
     *      (com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
     */
    @Override
    @Transactional
    public void updateWaybillSignResultById(WaybillSignResultEntity waybillSignResultEntity) {
        waybillSignResultDao.updateWaybillSignResultById(waybillSignResultEntity);
    }

    /**
     * 根据传入的待撤销组织CODE,返回当前组织的未签收票数
     * 
     * @author foss-meiying
     * @date 2012-12-17 下午3:00:08
     * @param lastLoadOrgCode 最终配载部门
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService
     *      #queryNotSignGoodsQtyByOrgCode(java.lang.String)
     */
    @Override
    public int queryNotSignGoodsQtyByOrgCode(String lastLoadOrgCode) {
        return waybillSignResultDao.queryNotSignGoodsQtyByOrgCode(lastLoadOrgCode);
    }

    /**
     * 
     * 为签收变更提供接口 偏线/空运 更新运单签收结果(t_srv_waybill_sign_result) 更新AcutalFreight
     * 
     * @author foss-meiying
     * @date 2012-12-19 下午9:06:06
     * @param oldWaybillSignResult entity.waybillNo 运单号 entity.signSituation 签收情况 entity.signNote
     *        签收备注 entity.identifyType 证件类型 entity.identifyCode 证件号码 entity.settleStatus 结清状态
     *        entity.signGoodsQty 签收件数 entity.signTime 签收时间 entity.active 是否有效 entity.modifyTime
     *        时效时间 entity.createTime 生效时间 entity.isPdaSign 是否PDA签到 entity.agentCode 代理编码
     * @param newWaybillSignResult entity.waybillNo 运单号 entity.signSituation 签收情况 entity.signNote
     *        签收备注 entity.identifyType 证件类型 entity.identifyCode 证件号码 entity.settleStatus 结清状态
     *        entity.signGoodsQty 签收件数 entity.signTime 签收时间 entity.active 是否有效 entity.modifyTime
     *        时效时间 entity.createTime 生效时间 entity.isPdaSign 是否PDA签到 entity.agentCode 代理编码
     * @see
     */
    @Override
    public void changeWaybillSignResult(WaybillSignResultEntity oldWaybillSignResult,
            WaybillSignResultEntity newWaybillSignResult) {
        LOGGER.info("签收变更提供接口   偏线/空运  开始" + ReflectionToStringBuilder.toString(oldWaybillSignResult)
                + ReflectionToStringBuilder.toString(newWaybillSignResult));
        if (oldWaybillSignResult == null || newWaybillSignResult == null) {
            LOGGER.error("传入的对象为空，不能进行签收变更操作");// 记录日志
            throw new SignException(SignException.OBJECT_IS_NOT_NULL);
        }
        // 得到当前时间
        Date modifyTime = new Date();
        // 作废运单签收结果里当前运单号
        this.invalidWaybillSignResult(oldWaybillSignResult.getId(), modifyTime);
        // 添加新的运单签收结果
        newWaybillSignResult.setActive(FossConstants.ACTIVE);// 有效
        newWaybillSignResult.setCreateTime(modifyTime);
        // 时效时间为空，添加时采用默认值
        newWaybillSignResult.setModifyTime(null);
        // 不是PDA签收
        newWaybillSignResult.setIsPdaSign(FossConstants.NO);
        if (StringUtils.isBlank(newWaybillSignResult.getSignSituation())) {
            throw new SignException(SignException.SIGN_SITUATION_IS_NOT_NULL);// 变更后的签收情况为空！不能执行变更操作
        }
        // 签收状态--部分签收
        if (SignConstants.PARTIAL_SIGN.equals(newWaybillSignResult.getSignSituation())) {
            newWaybillSignResult.setSignStatus(SignConstants.SIGN_STATUS_PARTIAL);
            // 如果变更前的运单签收状态为全部签收
            if (SignConstants.SIGN_STATUS_ALL.equals(oldWaybillSignResult.getSignStatus())) {
                // 反标识业务完结
                WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
                // 运单号
                waybillTransactionEntity.setWaybillNo(newWaybillSignResult.getWaybillNo());
                waybillTransactionService.updateReverseBusinessOver(waybillTransactionEntity);
            }
        } else {
            // 如果变更前的运单签收状态为部分部签收
            if (SignConstants.SIGN_STATUS_PARTIAL.equals(oldWaybillSignResult.getSignStatus())) {
                // 签收状态--全部签收
                newWaybillSignResult.setSignStatus(SignConstants.SIGN_STATUS_ALL);
                // 标识业务完结
                WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
                // 运单号
                waybillTransactionEntity.setWaybillNo(newWaybillSignResult.getWaybillNo());
                waybillTransactionService.updateBusinessOver(waybillTransactionEntity);
            }
        }
        waybillSignResultDao.addWaybillSignResult(newWaybillSignResult);
        // 更新AcutalFreight中的到达未出库件数”=“到达未出库件数”-（newWaybillSignResult的件数-oldNewWaybillSignResult的件数）
        if (!newWaybillSignResult.getSignGoodsQty().equals(oldWaybillSignResult.getSignGoodsQty())) {
            ActualFreightDto actualFreightDto = new ActualFreightDto(oldWaybillSignResult.getWaybillNo(),
                    newWaybillSignResult.getSignGoodsQty() - oldWaybillSignResult.getSignGoodsQty());
            // 修改运单状态里的到达未出库件数
            actualFreightService.updateArriveNotoutGoodsQtyByWaybillNo(actualFreightDto);
            LOGGER.info("修改运单状态里的到达未出库件数完成");// 记录日志
        }
        LOGGER.info("签收变更提供接口   偏线/空运  结束");// 记录日志
    }

    /**
     * 
     * 系统发送短信至发货人、 收货人，通知内容包括：货物单号、 此货签收时间、签收件数、签收人、是否有异常
     * 
     * @author foss-meiying
     * @date 2012-12-22 下午3:08:27
     * @param currentInfo currentInfo.operate 操作人 currentInfo.operatorCode 操作人编码
     *        currentInfo.operateOrgName 操作部门名称 currentInfo.operateOrgCode 操作部门编码
     * @param customerSms 内容
     * @param isToReceiveCustomer 是否给收货人发送短信
     * @param waybillDto waybillNo 运单号 receiveCustomerName 收货人(收货客户名称) receiveCustomerProvCode 收货省份
     *        receiveCustomerCityCode 收货市 receiveCustomerDistCode 收货区 receiveCustomerAddress
     *        收货客户具体地址 receiveCustomerPhone 收货客户电话 arriveTime 到达时间 notificationResult 是否通知成功
     *        notificationTime 上次通知时间 toPayAmount 到付金额 codAmount 代收货款（开单时） transportFee 运费
     *        deliveryGoodsFee 送货费 insuranceFee 保价费 paidMethod 付款方式（出发部门） otherFee 其他费用
     *        insuranceAmount 货物价值 stockStatus 库存状态 receiveMethod 派送方式 settleStatus 结清状态 createTime
     *        出发时间 deliveryCustomerCityCode 始发站 deliveryCustomerCityName 始发站名称 receiveOrgCode
     *        始发部门(收货部门(出发部门)) receiveOrgName 始发部门(收货部门(出发部门)) 名称 goodsName 货物名称 isWholeVehicle
     *        是否整车运单 goodsQtyTotal 件数 goodsWeightTotal 重量 goodsVolumeTotal 体积 goodsPackage 包装
     *        deliveryCustomerName 发货人(发货客户名称) transportType 运输类型 productCode 运输性质 storageCharge 仓储费
     *        receiveCustomerMobilephone 收货人手机 deliveryCustomerMobilephone 发货人手机
     *        oldArriveNotoutGoodsQty 并发控制 --到达未出库件数
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#sendMess(com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo,
     *      java.lang.String, java.lang.String,
     *      com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto)
     */
    public boolean sendMess(CurrentInfo currentInfo, String customerSms, boolean isToReceiveCustomer,
            String moduleCode, WaybillEntity waybill) {
        boolean sendMessResult = false;
        LOGGER.info("短信发送开始!");// 记录日志
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
            if (isToReceiveCustomer) {
                // 收货人
                notificationEntity.setConsignee(waybill.getReceiveCustomerContact());
                // 手机号
                notificationEntity.setMobile(waybill.getReceiveCustomerMobilephone());
            } else {
                // 发货人
                notificationEntity.setConsignee(waybill.getDeliveryCustomerContact());
                // 手机号
                notificationEntity.setMobile(waybill.getDeliveryCustomerMobilephone());
            }
            // 操作时间
            notificationEntity.setOperateTime(new Date());
            // 模块名称
            notificationEntity.setModuleName(moduleCode);
            // 发送短信
            notifyCustomerService.sendMessage(notificationEntity);
            sendMessResult = true;
        } catch (NotifyCustomerException e) {// 捕获异常
            // 异常处理
            LOGGER.error("--短信发送失败!" + e.getErrorCode(), e);// 记录日志
            sendMessResult = false;
        }
        return sendMessResult;

    }

    /**
     * 调用发送短信,在线通知
     * 
     * @author foss-meiying
     * @date 2013-3-1 上午9:51:19
     * @param waybill 运单
     * @param currentinfo 当前登录人信息 currentInfo.operate 操作人 currentInfo.operatorCode 操作人编码
     *        currentInfo.operateOrgName 操作部门名称 currentInfo.operateOrgCode 操作部门编码
     * @param dto * waybillNo 运单号 signSituation 签收情况 deliverymanName 提货人名称 identifytype 证件类型
     *        identifyCode 证件号码 signGoodsQty 签收件数 signNote 签收备注 signTime 签收时间 createTime 生效时间
     *        modifyTime 时效时间 active 是否有效 goodsQtyTotal 运单表里货物总件数 signStatus 签收状态 signTimeStart 起始时间
     *        signTimeEnd 结束时间 waybillNos 运单号集合 isException 是否有异常 situation 签收情况
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService
     *      #sendMessNotice (java.lang.String,
     *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo,
     *      com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto)
     */
    public String sendMessNotice(WaybillEntity waybill, CurrentInfo currentinfo, WaybillSignResultDto dto) {
        String resultMsg = "";
        // 若运输产品类型-快递
        if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(),
                waybill.getBillTime())) {
            // 快递签收短信
            resultMsg = sendExpressMSG(waybill, currentinfo, dto);
        } else {
            // 零担签收短信
            resultMsg = sendLTLMSG(waybill, currentinfo, dto);
        }
        return resultMsg;
    }

    /**
     * 
     * 系统发送在线通知给发货部门， 通知内容包括：货物单号、此货签收时间、签收件数、签收人、是否有异常
     * 
     * @author foss-meiying
     * @date 2012-12-21 上午10:00:25
     * @param currentinfo 当前登录人信息 currentInfo.operate 操作人 currentInfo.operatorCode 操作人编码
     *        currentInfo.operateOrgName 操作部门名称 currentInfo.operateOrgCode 操作部门编码
     * @param receiveOrgCode 接收方组织编码
     * @param noticeContext 通知内容
     * @see
     */
    public boolean sendNotice(CurrentInfo currentinfo, String receiveOrgCode, String noticeContext) {
        LOGGER.info("--发送在线通知开始,消息内容:" + noticeContext);// 记录日志
        boolean sendNoticeResult = false;
        try {
            // 如果接受组织code、发送方编码不为空
            if (StringUtils.isNotBlank(receiveOrgCode) && currentinfo != null) {
                InstationJobMsgEntity entity = new InstationJobMsgEntity();
                // UUID
                entity.setId(UUIDUtils.getUUID());
                // 发送方编码
                entity.setSenderCode(currentinfo.getEmpCode());
                // 发送方名称
                entity.setSenderName(currentinfo.getEmpName());
                // 发送方组织编码
                entity.setSenderOrgCode(currentinfo.getCurrentDeptCode());
                // 发送方组织名称
                entity.setSenderOrgName(currentinfo.getCurrentDeptName());
                // 接收方组织编码
                entity.setReceiveOrgCode(receiveOrgCode);
                // 接收方类型
                entity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
                // 消息内容
                entity.setContext(noticeContext);
                // 站内消息类型
                entity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
                // 发送时间
                entity.setPostDate(new Date());
                // 消息状态
                entity.setStatus(MessageConstants.MSG__STATUS__PROCESSING);
                messageService.createBatchInstationMsg(entity);
                sendNoticeResult = true;
            }
        } catch (MessageException e) {// 捕获异常
            LOGGER.error("--发送在线通知异常" + e.getErrorCode(), e);// 记录日志
            sendNoticeResult = false;
        }
        return sendNoticeResult;
    }

    /**
     * 获取短信信息
     * 
     * @author foss-meiying
     * @date 2013-1-8 上午11:34:39
     * @param entity * waybillNo 运单号 signSituation 签收情况 deliverymanName 提货人名称 identifytype 证件类型
     *        identifyCode 证件号码 signGoodsQty 签收件数 signNote 签收备注 signTime 签收时间 createTime 生效时间
     *        modifyTime 时效时间 active 是否有效 goodsQtyTotal 运单表里货物总件数 signStatus 签收状态 signTimeStart 起始时间
     *        signTimeEnd 结束时间 waybillNos 运单号集合 isException 是否有异常 situation 签收情况
     * @param currentInfo currentInfo.operate 操作人 currentInfo.operatorCode 操作人编码
     *        currentInfo.operateOrgName 操作部门名称 currentInfo.operateOrgCode 操作部门编码
     * @param smsCode 短信编码
     * @return
     * @see
     */
    public String getSmsContent(WaybillSignResultDto entity, CurrentInfo currentInfo, String smsCode) {
        String sms = ""; // 返回短信
        // 模版参数
        SmsParamDto smsParamDto = new SmsParamDto();
        // 短信编码
        smsParamDto.setSmsCode(smsCode);
        // 部门编码
        smsParamDto.setOrgCode(currentInfo.getCurrentDeptCode());
        smsParamDto.setMap(getSmsParam(entity));
        try {
            sms = sMSTempleteService.querySmsByParam(smsParamDto);
        } catch (SMSTempleteException e) {// 捕获异常
            LOGGER.error("短信内容为空", e);// 记录日志
            throw new SignException(SignException.MESS_CONTENT_ISNULL, e);// 短信内容为空
        }
        if (StringUtil.isBlank(sms)) {
            LOGGER.error("没有对应的短信模版");// 记录日志
            throw new SignException(SignException.NO_SMS_TEMPLATES);// 没有对应的短信模版
        }
        return sms;
    }

    /**
     * 设置短信模版内容的参数
     * 
     * @author foss-meiying
     * @date 2013-2-28 下午7:24:23
     * @param entity * waybillNo 运单号 signSituation 签收情况 deliverymanName 提货人名称 identifytype 证件类型
     *        identifyCode 证件号码 signGoodsQty 签收件数 signNote 签收备注 signTime 签收时间 createTime 生效时间
     *        modifyTime 时效时间 active 是否有效 goodsQtyTotal 运单表里货物总件数 signStatus 签收状态 signTimeStart 起始时间
     *        signTimeEnd 结束时间 waybillNos 运单号集合 isException 是否有异常 situation 签收情况
     * @return
     * @see
     */
    private Map<String, String> getSmsParam(WaybillSignResultDto entity) {
        Map<String, String> map = new HashMap<String, String>();
        // 客户名称
        map.put("customerName", entity.getCustomerName());
        // 货物单号
        map.put("waybillNo", entity.getWaybillNo());
        // 签收时间
        map.put("signTime", DateUtils.convert(entity.getSignTime(), null));
        // 签收件数
        map.put("signGoodsQty", entity.getSignGoodsQty().toString());
        // 签收人
        map.put("delName", entity.getDeliverymanName());
        // 签收人类型 update DN201511090005 签收人类型为空的时候，短信内容自动填充“代理”字样 --快递
        map.put("delType",
                StringUtils.isNotEmpty(entity.getDeliverymanType()) ? "["
                        + DictUtil.rendererSubmitToDisplay(entity.getDeliverymanType(),
                                DictionaryConstants.PKP_SIGN_PERSON_TYPE) + "]" : "代理");
        // 是否有异常
        map.put("isException", entity.getIsException());
        // 收货部门手机号
        map.put("receiveOrgTel",
                StringUtil.isEmpty(entity.getReceiveOrgTel()) ? "" : entity.getReceiveOrgTel());
        // 到达部门手机号
        map.put("customerPickupOrgTel",
                StringUtil.isEmpty(entity.getCustomerPickupOrgTel()) ? "" : entity.getCustomerPickupOrgTel());
        // 快递新增到达城市
        map.put("arriveCity", entity.getArriveCity());
        // 快递新增快递员姓名
        map.put("courierName", entity.getExpressEmpName());
        // 快递新增快递员电话
        map.put("courierMobile", entity.getExpressEmpTel());
        // 原运单单号
        map.put("rawWaybillNo", entity.getRawWaybillNo());
        // 到达部门电话
        map.put("arriveDepTelephone",
                StringUtil.isEmpty(entity.getArriveDepTelephone()) ? "" : entity.getArriveDepTelephone());
        return map;
    }

    /**
     * 
     * 根据 派送出库时间开始日期 派送出库时间结束日期查询运单签收结果里的运单号
     * 
     * @author foss-meiying
     * @date 2012-12-24 下午7:02:54
     * @param signTimeStart 起始时间
     * @param signTimeEnd 结束时间
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService
     *      #queryWaybillNoByCondition(java.util.Date, java.util.Date)
     */
    @Override
    public List<String> queryWaybillNoByCondition(Date signTimeStart, Date signTimeEnd) {
        // 如果起始时间和结束时间同时为空 返回为空
        if (signTimeStart == null && signTimeEnd == null) {
            return null;
        }
        WaybillSignResultDto dto = new WaybillSignResultDto();
        // 起始时间
        dto.setSignTimeStart(signTimeStart);
        // 结束时间
        dto.setSignTimeEnd(signTimeEnd);
        // 生效
        dto.setActive(FossConstants.ACTIVE);
        // 签收状态--全部签收
        dto.setSignStatus(SignConstants.SIGN_STATUS_ALL);
        return waybillSignResultDao.queryWaybillNoByCondition(dto);
    }

    /**
     * 根据运单号查询运单信息
     * 
     * @author 038590-foss-wanghui
     * @date 2012-12-25 下午9:05:50
     * @param condition
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService
     *      #queryWaybillInfoForEdi
     *      (com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillCondition)
     */
    @Override
    public AirWaybillDto queryWaybillInfoForEdi(AirWaybillCondition condition) {
        return waybillSignResultDao.queryWaybillInfoForEdi(condition);
    }

    /**
     * 通过id 作废运单签收结果信息
     * 
     * @author foss-meiying
     * @date 2013-1-23 下午4:35:11
     * @param entity
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#invalidWaybillSignResult(com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity)
     */
    @Override
    public int invalidWaybillSignResult(String id, Date modifyTime) {
        // 作废当前运单签收结果为无效
        WaybillSignResultEntity waySign = new WaybillSignResultEntity();
        // 时效时间为当前时间
        waySign.setModifyTime(modifyTime);
        // 无效
        waySign.setActive(FossConstants.INACTIVE);
        // 得到运单签收结果的id
        waySign.setId(id);
        return waybillSignResultDao.updateWaybillSignResult(waySign);
    }

    /**
     * 查询中转库存流水号 通过运单编号,部门编码
     * 
     * @author foss-meiying
     * @date 2013-1-28 下午4:37:47
     * @param dto
     * @return
     * @see
     */
    @Override
    public List<SignDetailEntity> queryOptStockSerinalNo(ContrabandInfoDto dto) {
        return waybillSignResultDao.queryOptStockSerinalNo(dto);
    }

    /**
     * 据运单号集合 查询运单签结果里的运单编号
     * 
     * @author foss-meiying
     * @date 2013-2-2 下午5:03:32
     * @param waybillNos 运单号集合
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#queryWaybillSignResultWaybillNos(java.util.List)
     */
    @Override
    public List<String> queryWaybillSignResultWaybillNos(List<String> waybillNos) {
        WaybillSignResultDto dto = new WaybillSignResultDto();
        // 运单号集合
        dto.setWaybillNos(waybillNos);
        // 有效
        dto.setActive(FossConstants.ACTIVE);
        return waybillSignResultDao.queryWaybillSignResultWaybillNos(dto);
    }

    /**
     * 根据传入的运单号,入库时间起止查询满足条件的运单信息
     * 
     * @author foss-meiying
     * @date 2013-2-3 下午3:45:19
     * @param dto
     * @param start
     * @param limit
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#queryLostCargoInfoByCondition(com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto,
     *      int, int)
     */
    @Override
    public List<LostCargoInfoDto> queryLostCargoInfoByCondition(LostCargoInfoDto dto, int start, int limit) {
        return waybillSignResultDao.queryLostCargoInfoByCondition(dto, start, limit);
    }

    /**
     * 根据传入的运单号,入库时间起止查询满足条件的运单信息总数
     * 
     * @author foss-meiying
     * @date 2013-2-3 下午3:45:44
     * @param dto
     * @return
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#queryLostCargoCountByCondition(com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto,
     *      int, int)
     */
    @Override
    public Long queryLostCargoCountByCondition(LostCargoInfoDto dto) {
        return waybillSignResultDao.queryLostCargoCountByCondition(dto);
    }

    /**
     * 签收时执行应用监控
     * 
     * @author foss-meiying
     * @date 2013-2-21 下午2:55:02
     * @param currentInfo currentInfo.operate 操作人 currentInfo.operatorCode 操作人编码
     *        currentInfo.operateOrgName 操作部门名称 currentInfo.operateOrgCode 操作部门编码
     * @param waybillNo 运单号
     * @see
     */
    public void signCounterByWaybillNo(CurrentInfo currentInfo, WaybillEntity waybill) {
        LOGGER.info("应用监控调用开始 ，运单号——" + ReflectionToStringBuilder.toString(waybill));// 记录日志
        @SuppressWarnings("unchecked")
        Map<BusinessMonitorIndicator, Number> indicators = new HashedMap();
        // 提货方式
        if (StringUtil.isNotBlank(waybill.getReceiveMethod())) {
            // 如果提货方式以“DELIVER”开头
            if (waybill.getReceiveMethod().startsWith(SignConstants.RECEIVE_METHOD_DELIVER)) {
                // 送货签收票数
                indicators.put(BusinessMonitorIndicator.POD_DELIVERY_COUNT, 1);
            } else {
                // 自提签收票数
                indicators.put(BusinessMonitorIndicator.POD_PICKUP_COUNT, 1);
            }
        }
        // 运输性质 productCode
        // 如果运输性质为精准卡航或精准城运
        if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT
                .equals(waybill.getProductCode())
                || ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(waybill
                        .getProductCode())) {
            // 精准签收票数
            indicators.put(BusinessMonitorIndicator.POD_RECISION_COUNT, 1);
        }// 如果运输性质为 精准汽运（长途）或 精准汽运（短途)
        else if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(waybill
                .getProductCode())
                || ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(waybill
                        .getProductCode())) {
            // 普货签收票数
            indicators.put(BusinessMonitorIndicator.POD_COMMON_COUNT, 1);
        }// 如果运输性质为汽运偏线
        else if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybill.getProductCode())) {
            // 偏线签收票数
            indicators.put(BusinessMonitorIndicator.POD_PARTIAL_COUNT, 1);
        }// 如果运输性质为精准空运
        else if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())) {
            // 空运签收票数
            indicators.put(BusinessMonitorIndicator.POD_AIR_COUNT, 1);
        }// 如果运输性质为整车
        else if (ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(waybill.getProductCode())) {
            // 整车签收票数
            indicators.put(BusinessMonitorIndicator.POD_WHOLE_COUNT, 1);
        } else if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(),
                waybill.getBillTime())) {
            // 快递代理理签收票数
            indicators.put(BusinessMonitorIndicator.EXPRESS_LDP_COUNT, 1);
        }
        // 开单总金额 totalFee
        indicators.put(BusinessMonitorIndicator.BILLING_TOTAL_AMOUNT, waybill.getTotalFee());
        // 开单保价金额 insuranceAmount
        indicators.put(BusinessMonitorIndicator.BILLING_INSURANCE_AMOUNT, waybill.getInsuranceAmount());
        // 开单代收货款金额 codAmount
        indicators.put(BusinessMonitorIndicator.BILLING_COD_AMOUNT, waybill.getCodAmount());
        RepaymentEntity repayment = new RepaymentEntity();
        repayment.setWaybillNo(waybill.getWaybillNo());// 运单号
        repayment.setActive(FossConstants.ACTIVE);// 有效
        repayment = repaymentService.queryRepaymentTypebyNo(repayment);
        if (repayment != null && StringUtil.isNotBlank(repayment.getPaymentType())) {// 如果查询到的付款方式不为空
            // 如果支付方式为现金
            if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(repayment
                    .getPaymentType())) {
                if (repayment.getActualFreight().compareTo(BigDecimal.ZERO) == 0) {
                    // 现付签收票数
                    indicators.put(BusinessMonitorIndicator.POD_PREPAY_COUNT, 1);
                } else {
                    // 到付签收票数
                    indicators.put(BusinessMonitorIndicator.POD_TOPAY_COUNT, 1);
                }
            } else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repayment
                    .getPaymentType())) {
                // 银行卡签收票数
                indicators.put(BusinessMonitorIndicator.POD_CARD_COUNT, 1);
            } else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(repayment
                    .getPaymentType())) {
                // 月结签收票数
                indicators.put(BusinessMonitorIndicator.POD_CREDIT_COUNT, 1);
            } else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT.equals(repayment
                    .getPaymentType())) {
                // 临时欠款签收票数
                indicators.put(BusinessMonitorIndicator.POD_DEBT_COUNT, 1);
            } else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(repayment
                    .getPaymentType())) {
                // 网上支付签收票数
                indicators.put(BusinessMonitorIndicator.POD_ONLINE_COUNT, 1);
            } else {
                // 这里不作处理
            }
        }
        businessMonitorService.counter(indicators, currentInfo);
        LOGGER.info("--应用监控调用完成 ，运单号——" + waybill.getWaybillNo());// 记录日志

    }

    /**
     * 调用CRM修改订单接口更新订单状态.
     * 
     * @author foss-meiying
     * @date 2013-3-13 下午4:08:26
     * @param orderNo 订单号
     * @param currentinfo 当前登录人的信息 currentInfo.operate 操作人 currentInfo.operatorCode 操作人编码
     *        currentInfo.operateOrgName 操作部门名称 currentInfo.operateOrgCode 操作部门编码
     * @param entity 运单签收结果 entity.waybillNo 运单号 entity.signSituation 签收情况 entity.signNote 签收备注
     *        entity.identifyType 证件类型 entity.identifyCode 证件号码 entity.settleStatus 结清状态
     *        entity.signGoodsQty 签收件数 entity.signTime 签收时间 entity.active 是否有效 entity.modifyTime
     *        时效时间 entity.createTime 生效时间 entity.isPdaSign 是否PDA签到 entity.agentCode 代理编码
     * @see
     */
    public void updateCrmOrder(String orderNo, CurrentInfo currentinfo, WaybillSignResultEntity entity) {
        LOGGER.info("更新CRM订单开始" + "订单号：" + orderNo + "运单签收结果信息：" + ReflectionToStringBuilder.toString(entity));// 记录日志
        if (StringUtil.isBlank(orderNo)) {
            LOGGER.error("--订单号为空");// 记录日志
            return;
        }
        // 更新CRM订单
        CrmModifyInfoDto crmModifyInfoDto = new CrmModifyInfoDto();
        // 订单号
        crmModifyInfoDto.setOrderNumber(orderNo);
        // 操作人编码
        crmModifyInfoDto.setOprateUserNum(currentinfo.getEmpCode());
        // 操作人组织code
        crmModifyInfoDto.setOprateDeptCode(currentinfo.getDept().getUnifiedCode());
        if (ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(entity.getSignSituation())) {// 如果签收情况为正常签收
            // 订单状态
            crmModifyInfoDto.setGoodsStatus(SignConstants.CRM_ORDER_STATUS_SIGNSUCCESS);// 正常签收
        } else {
            // 订单状态
            crmModifyInfoDto.setGoodsStatus(SignConstants.CRM_ORDER_STATUS_SIGNFAIL);// 异常签收
        }
        // 操作备注
        crmModifyInfoDto.setBackInfo(entity.getSignNote());
        crmModifyInfoDto.setWaybillNumber(entity.getWaybillNo());// 运单号
        // 根据运单号，获取运单信息 DN201602220004 --by 243921
        WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(entity.getWaybillNo());
        if (null != waybillEntity) {
            // 只有是东方购物的客户才推送客户编码至CRM
            if (SignConstants.CRM_ORDER_CUSTOMER_CODE.equals(waybillEntity.getDeliveryCustomerCode())) {
                crmModifyInfoDto.setDeliveryCustomerCode(waybillEntity.getDeliveryCustomerCode());
            }
        }
        // 调用CRM订单修改接口
        crmOrderJMSService.sendModifyOrder(crmModifyInfoDto);
        LOGGER.info("更新CRM订单完成");// 记录日志
    }

    /**
     * 提供给接送货的接口，获取订单的拒收状态，推送给crm DN201602220004
     * 
     * @author 243921
     * @date 2016-03-03上午9:11:35
     * @param dto
     */
    @Override
    public void updateCrmOrderForRefuse(OrderStatusToCrmDto dto) {
        if (dto == null) {
            LOGGER.error("--传入的参数为空");// 记录日志
            return;
        }
        if (StringUtil.isBlank(dto.getOrderNo())) {
            LOGGER.error("--订单号为空");// 记录日志
            return;
        }
        if (StringUtil.isBlank(dto.getWaybillNo())) {
            LOGGER.error("--运单号为空");// 记录日志
            return;
        }
        if (StringUtil.isBlank(dto.getBackInfo())) {
            LOGGER.error("--操作备注为空");// 记录日志
            return;
        }
        LOGGER.info("更新CRM订单开始" + "订单号：" + dto.getOrderNo());// 记录日志

        // 更新CRM订单
        CrmModifyInfoDto crmModifyInfoDto = new CrmModifyInfoDto();
        // 订单号
        crmModifyInfoDto.setOrderNumber(dto.getOrderNo());
        // 操作人编码
        crmModifyInfoDto.setOprateUserNum(dto.getOprateUserCode());
        // 操作人组织code
        crmModifyInfoDto.setOprateDeptCode(dto.getOprateDeptCode());
        // 订单状态
        crmModifyInfoDto.setGoodsStatus(SignConstants.CRM_ORDER_STATUS_SIGNREFUSE);// 拒收
        // 操作备注
        crmModifyInfoDto.setBackInfo(dto.getBackInfo());
        crmModifyInfoDto.setWaybillNumber(dto.getWaybillNo());// 运单号
        // 客户编码
        crmModifyInfoDto.setDeliveryCustomerCode(dto.getCustomerCode());
        // 调用CRM订单修改接口
        crmOrderJMSService.sendModifyOrder(crmModifyInfoDto);
        LOGGER.info("更新CRM订单完成");// 记录日志
    }

    /**
     * 弃货签收接口
     * 
     * @author foss-meiying
     * @date 2013-4-11 上午9:56:55
     * @param waybillNo
     * @see com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService#addAbandonGoodsSign(java.lang.String)
     */
    @Override
    public void addAbandonGoodsSign(String waybillNo, CurrentInfo current) {
        // 传入参数(运单号,当前运单号生效)
        WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(waybillNo, FossConstants.ACTIVE);
        WaybillEntity waybill = waybillManagerService.queryPartWaybillByNo(waybillNo);
        if (waybill == null) {
            throw new SignException(SignException.WAYBILLNO_NULL);// 该运单号不存在
        }
        if (current.getCurrentDeptCode() != null) {
            // 操作部门编码
            wayEntity.setCreateOrgCode(current.getCurrentDeptCode());
            wayEntity.setCreateOrgName(current.getCurrentDeptName());
        } else {
            // 张新 限制签收
            // 张新 2015-2-3 判断当前运单是否为快递
            if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(),
                    waybill.getBillTime())) {// 是
                List<PrintAgentWaybillRecordEntity> sdExternalBillRecords = printAgentWaybillService
                        .queryRecordByWaybillNo(waybillNo, SignConstants.LIMIT_SIGN);
                if (CollectionUtils.isNotEmpty(sdExternalBillRecords)) {
                    // 有效的营业部外发
                    throw new SignException(SignException.SIGN_LIMIT);// 签收限制
                }
            }
            // 操作部门编码
            wayEntity.setCreateOrgCode(waybill.getLastLoadOrgCode());
            // 获取操作部门
            OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
                    .queryOrgAdministrativeInfoByCodeClean(waybill.getLastLoadOrgCode());
            if (org != null) {
                // 操作部门名称
                wayEntity.setCreateOrgName(org.getName());
            }
        }
        if (current.getEmpCode() != null) {
            wayEntity.setCreator(current.getEmpCode());
            wayEntity.setCreateUser(current.getEmpName());
        }
        // 根据运单号 查询运单签收结果
        WaybillSignResultEntity waybillSign = waybillSignResultDao.queryWaybillSignResult(wayEntity);
        // 得到当前时间
        Date modifyTime = new Date();
        // 如果当前运单不是第一次添加
        if (waybillSign != null) {
            // 运单签收结果里作废当前运单号
            this.invalidWaybillSignResult(waybillSign.getId(), modifyTime);
        } else {
            // 财务出库 结清账款 -- 异常出库类型 弃货(红冲到达应收、代收货款应收、代收货款应付)
            // this.outWarehouseException(waybillNo,current);
        }
        // 签收情况弃货签收
        wayEntity.setSignSituation(ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS);
        // 运单签收结果的签收件数 =运单开单件数
        wayEntity.setSignGoodsQty(waybill.getGoodsQtyTotal());
        // 生效
        wayEntity.setActive(FossConstants.ACTIVE);
        // 生效时间为当前时间
        wayEntity.setCreateTime(modifyTime);
        // 时效时间为空，添加时采用默认值
        wayEntity.setModifyTime(null);
        // 签收时间
        wayEntity.setSignTime(modifyTime);
        // 得到签收备注 异常-弃货
        wayEntity.setSignNote(SignConstants.ABANDONGOODS_SIGN_NOTE);
        // 签收状态--全部签收
        wayEntity.setSignStatus(SignConstants.SIGN_STATUS_ALL);
        wayEntity.setIsPdaSign(FossConstants.NO);// 不是pda签收
        this.addWaybillSignResult(wayEntity);

        // 如果当前运单是第一次添加
        if (waybillSign == null) {
            contrabandSignService
                    .handleAllExceptionGoods(waybill,
                            SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__GIVE_UP_GOODS,
                            current);
        }

        /*
         * 异常弃货快递100,菜鸟不推送轨迹
         */
        // 菜鸟轨迹 add by 231438
        // 封装的轨迹方法 赋值，判断是否符合条件 调用中转的轨迹接口
        // sendWaybillTrackService.rookieTrackingsForSign(wayEntity);
        // 调用轨迹中封装的方法赋值，赋值方法中调用轨迹接口，推送轨迹--add by 231438 快递100轨迹推动送
        // sendWaybillTrackService.packagingMethodForSign(wayEntity, current);
        ArriveSheetEntity entity = new ArriveSheetEntity();
        entity.setWaybillNo(waybillNo);// 运单号
        entity.setActive(FossConstants.YES);
        entity.setDestroyed(FossConstants.NO);
        List<ArriveSheetEntity> arriveList = arriveSheetManngerService.queryArriveSheetByWaybillNo(entity);
        if (CollectionUtils.isNotEmpty(arriveList)) {
            for (ArriveSheetEntity arriveSheetEntity : arriveList) {
                ArriveSheetEntity entity1 = new ArriveSheetEntity();
                entity1.setActive(FossConstants.NO);
                entity1.setId(arriveSheetEntity.getId());
                entity1.setModifyTime(modifyTime);// 时效日期为当前日期
                arriveSheetManngerService.updateByPrimaryKeySelective(entity1);
            }
        }

    }

    /**
     * 财务出库 结清账款 -- 异常出库类型 弃货
     * 
     * @author foss-bieyexiong
     * @date 2015-9-17 16:05:30
     */
    public void outWarehouseException(String waybillNo, CurrentInfo current) {
        // 判断子母件(判断子母件是否已全部做弃货)
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(SignConstants.WAYBILL_NO, waybillNo);
        param.put(SignConstants.ACTIVE, FossConstants.YES);
        TwoInOneWaybillDto twoInOneDto = waybillRelateDetailEntityService
                .queryWaybillRelateByWaybillOrOrderNo(param);
        // 子母件财务出库
        // 是子母件、子件集合不为空
        if (twoInOneDto != null && FossConstants.YES.equals(twoInOneDto.getIsTwoInOne())
                && CollectionUtils.isNotEmpty(twoInOneDto.getWaybillNoList())) {
            // 将母单号放进子单号集合，用于判断其余单号是否全部弃货签收
            twoInOneDto.getWaybillNoList().add(twoInOneDto.getMainWaybillNo());
            // 查询条件
            WaybillSignResultEntity wayEntityParam = new WaybillSignResultEntity();
            // 签收结果
            WaybillSignResultEntity signResult = null;
            // 是否全部弃货,默认为true
            boolean isAllAbandonGoods = true;
            for (String sonWaybillNo : twoInOneDto.getWaybillNoList()) {
                // 如果是本单号，则跳过，进入下一次循环
                if (waybillNo.equals(sonWaybillNo)) {
                    continue;
                }
                wayEntityParam.setWaybillNo(sonWaybillNo);
                wayEntityParam.setActive(FossConstants.ACTIVE);
                signResult = waybillSignResultDao.queryWaybillSignResult(wayEntityParam);
                // 签收结果为空 或者 签收情况不为弃货签收
                if (signResult == null
                        || !ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS.equals(signResult
                                .getSignSituation())) {
                    // 未全部弃货
                    isAllAbandonGoods = false;
                    break;
                }
            }
            // 已全部弃货
            if (isAllAbandonGoods) {
                // 将所有子母件一次全部财务出库(红冲到达应收、代收货款应收、代收货款应付)
                for (@SuppressWarnings("unused") String sonWaybillNo : twoInOneDto.getWaybillNoList()) {
                	//CUBC签收   灰度改造    353654 ---------------------------start
                	String vestSystemCode1 = null;
        			try {
                      	List<String> arrayList = new ArrayList<String>();
                      	arrayList.add(waybillNo);
                      	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                      			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".outWarehouseException",
                      			SettlementConstants.TYPE_FOSS);
                      	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                      	List<VestBatchResult> list1 = response.getVestBatchResult();
                      	vestSystemCode1 = list1.get(0).getVestSystemCode();	
        	  			} catch (Exception e) {
        	  				LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
    		  				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
        	  			}
    				if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
    					outWarehouseExceptionService.outWarehouseException(waybillNo, SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__GIVE_UP_GOODS, current);
    				}
    				if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
    					LineSignDto dto = new LineSignDto();
    					dto.setWaybillNo(waybillNo);
    	       			FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
    	       			reqDto.setCurrentInfo(current);
    	       			reqDto.setLineSignDto(dto);
    	       			reqDto.setExpSignType(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__GIVE_UP_GOODS);
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
            }
        } else {
            // 非子母件财务出库(红冲到达应收、代收货款应收、代收货款应付)
        	//CUBC签收   灰度改造    353654 ---------------------------start
        	String vestSystemCode1 = null;
			try {
              	List<String> arrayList = new ArrayList<String>();
              	arrayList.add(waybillNo);
              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".outWarehouseException",
              			SettlementConstants.TYPE_FOSS);
              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
              	List<VestBatchResult> list1 = response.getVestBatchResult();
              	vestSystemCode1 = list1.get(0).getVestSystemCode();	
	  			} catch (Exception e) {
	  				LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
	  				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
	  			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
				outWarehouseExceptionService.outWarehouseException(waybillNo, SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__GIVE_UP_GOODS, current);
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
				LineSignDto dto = new LineSignDto();
	  			dto.setWaybillNo(waybillNo);
	  			FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
	  			reqDto.setCurrentInfo(current);
	  			reqDto.setLineSignDto(dto);
	  			reqDto.setExpSignType(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__GIVE_UP_GOODS);
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
    }

    /**
     * Sets the 业务监控服务.
     *
     * @param businessMonitorService the new 业务监控服务
     */
    public void setBusinessMonitorService(IBusinessMonitorService businessMonitorService) {
        this.businessMonitorService = businessMonitorService;
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
     * Gets the 运单签收结果Dao.
     *
     * @return the 运单签收结果Dao
     */
    public IWaybillSignResultDao getWaybillSignResultDao() {
        return waybillSignResultDao;
    }

    /**
     * Sets the 运单签收结果Dao.
     *
     * @param waybillSignResultDao the new 运单签收结果Dao
     */
    public void setWaybillSignResultDao(IWaybillSignResultDao waybillSignResultDao) {
        this.waybillSignResultDao = waybillSignResultDao;
    }

    /**
     * Sets the 运单状态服务接口.
     *
     * @param actualFreightService the new 运单状态服务接口
     */
    public void setActualFreightService(IActualFreightService actualFreightService) {
        this.actualFreightService = actualFreightService;
    }

    /**
     * Sets the 消息服务.
     *
     * @param messageService the new 消息服务
     */
    public void setMessageService(IMessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Sets the 客户通知.
     *
     * @param notifyCustomerService the new 客户通知
     */
    public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
        this.notifyCustomerService = notifyCustomerService;
    }

    /**
     * Sets the 运单完结状态操作Service.
     *
     * @param waybillTransactionService the new 运单完结状态操作Service
     */
    public void setWaybillTransactionService(IWaybillTransactionService waybillTransactionService) {
        this.waybillTransactionService = waybillTransactionService;
    }

    /**
     * Sets the 计算&调整走货路径类.
     *
     * @param calculateTransportPathService the new 计算&调整走货路径类
     */
    public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
        this.calculateTransportPathService = calculateTransportPathService;
    }

    public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
        this.sMSTempleteService = sMSTempleteService;
    }

    /**
     * Sets the cRM订单更新服务.
     *
     * @param crmOrderJMSService the new cRM订单更新服务
     */
    public void setCrmOrderJMSService(ICrmOrderJMSService crmOrderJMSService) {
        this.crmOrderJMSService = crmOrderJMSService;
    }

    public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService) {
        this.arriveSheetManngerService = arriveSheetManngerService;
    }

    public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
        this.waybillManagerService = waybillManagerService;
    }

    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    @Override
    public List<WaybillSignResultEntity> queryWaybillSignResultLit(WaybillSignResultEntity entity) {
        return waybillSignResultDao.queryWaybillSignResultLit(entity);
    }

    public void setSignChangeService(ISignChangeService signChangeService) {
        this.signChangeService = signChangeService;
    }

    public void setOutWarehouseExceptionService(IOutWarehouseExceptionService outWarehouseExceptionService) {
        this.outWarehouseExceptionService = outWarehouseExceptionService;
    }

    /**
     * update:
     * 
     * @data 2014年7月9日 下午4:30:16 添加状态码的判断：业务需求：分成三种状态 1.发件人短信批量发送 2.发件人短信停发 3.收件人短信停发 三种状态码都是通过0,1
     *       进行标识 0表示的是未选中 1表示的是选中 发件人短信批量发送
     *       当CRM勾选发件人短信批量发送时，停发快递订单调度受理短信、快递签收收货人短信、签收单返单短信，次日向客户发送批量打包短信 发件人短信停发
     *       当CRM勾选发件人短信停发时，停发快递订单调度受理短信、快递签收发件人短信。 收件人短信停发
     *       当CRM勾选收件人短信停发时，停止发送快递派送收货人短信、快递开单收货人短信、快递签收收货人短信 ####################### 快递签收发件人短信
     *       快递签收收货人短信 根据传入快递产品的运单号，发送快递模板短信 根据传入快递产品的运单号，发送快递模板短信 MANA-581 根据传入快递产品的运单号，发送快递模板短信
     *       MANA-581
     * @author foss-yangbin
     * @date 2014-3-3 下午3:45:44
     * @param dto
     * @return
     */
    private String sendExpressMSG(WaybillEntity waybill, CurrentInfo currentinfo, WaybillSignResultDto dto) {
        // ISSUE-31182013年6月15日前开的单，做签收时不再发送短信给发货客户
        Date dd = DateUtils.convert("2013-06-15 00:00:00", "yyyy-MM-dd HH:mm:ss");
        if (dd != null && waybill.getBillTime() != null && dd.compareTo(waybill.getBillTime()) > 0) {
            return SignException.SIGN_SUCCESS;
        }
        LOGGER.info("--调用发送短信,在线通知开始  waybillSignResultDto:" + dto);// 记录日志
        String resultMsg = "";
        if (ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(dto.getSituation())) {
            // 无异常
            dto.setIsException(SignConstants.IS_EXCEPTION_NO);
        } else {// 有异常
            dto.setIsException(SignConstants.IS_EXCEPTION_YES);
        }
        // 实际承运信息
        ActualFreightEntity actual = actualFreightService.queryByWaybillNo(waybill.getWaybillNo());
        // 开单是否需要发送短信
        String deliveryCustIsMsg = actual.getIsSMS();
        // 取得开单类型
        String pendingType = waybill.getPendingType();
        // 取得提货网点
        OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByCode(waybill.getCustomerPickupOrgCode());
        // 重新设置是否读缓存
        SqlUtil.loadCache = true;
        String arriveCity = "";
        // 到达部门电话
        String arriveDepTelephone = "";
        // 判断提货网点是否为空对象
        if (null != orgDeliveryInfo) {
            if (StringUtils.isNotEmpty(orgDeliveryInfo.getCityName())) {
                arriveCity = orgDeliveryInfo.getCityName();
            }
            // 判断到达部门电话是否为空
            if (StringUtils.isNotEmpty(orgDeliveryInfo.getDepTelephone())) {
                arriveDepTelephone = orgDeliveryInfo.getDepTelephone();
            }
        }
        LOGGER.info("--到达城市：  waybillSignResultDto:" + arriveCity);// 记录日志
        // 设置到达城市名称
        dto.setArriveCity(arriveCity);
        // 设置到达部门电话
        dto.setArriveDepTelephone(arriveDepTelephone);
        boolean toDeliveryResult = false; // 给发货人发送短信返回结果
        boolean toReceiveResult = false; // 给收货人发送短信返回结果
        boolean noticeResult = false;
        boolean isSendSpecificContacts = true;// 是否给固定用户发短信
        // 短信业务类型-快递签收
        String moduleCode = NotifyCustomerConstants.SMS_PKP_SIGN_EXP;
        // 1、PC_ACTIVE:非PDA开单,默认给发货人发送短信
        // 2、PDA_ACTIVE:PDA开单，PDA开单勾选发送短信，需要给 发货人发送短信
        /**
         * 更改 通过crm端状态 判断是否发送 快递签收发件人短信 有一种情况 不需要发送 快递签收发件人短信 当crm勾选发件人短信停发时 不需要发送
         */
        // 是否记录 收货人日志 相关信息
        boolean isRecordReceiverMessageStop = false;
        // 是否记录发货人 日志相关信息
        boolean isRecordSenderMessaegStop = false;
        String deliveryCustomerCode = waybill.getDeliveryCustomerCode();

        // 是否是返货单
        boolean isRepeatOrder = false;
        /** 查询运单快递 */
        WaybillExpressEntity waybillExpressEntity = waybillExpressDao.queryWaybillExpressByNo(waybill
                .getWaybillNo());

        /**
         * 根据原运单单号查询快递运单
         */
        List<WaybillExpressEntity> returnWaybillExpressEntitys = waybillExpressDao
                .queryWaybillByOriginalWaybillNo(waybill.getWaybillNo());

        // 判断是否是返货单
        if (waybillExpressEntity != null && StringUtils.isNotBlank(waybillExpressEntity.getReturnType())
                && StringUtils.isNotBlank(waybillExpressEntity.getOriginalWaybillNo())) {
            isRepeatOrder = true;
        }

        // 是否发送快递信息的标记
        boolean isSendpackageFlag = true;
        if (StringUtils.isEmpty(deliveryCustomerCode)) {
            isSendpackageFlag = true;
        } else if (StringUtils.isNotEmpty(deliveryCustomerCode)) {
            CustomerEntity customerEntity = customerDao.queryCustStateByCode(deliveryCustomerCode);
            if (customerEntity != null) {
                String shipperSms = customerEntity.getShipperSms();
                if (StringUtils.isNotEmpty(shipperSms)
                        && (shipperSms.equals(SignConstants.BATCH_MESSAGE_FOR_DELIVER) || shipperSms
                                .equals(SignConstants.STOP_MESSAGE_FOR_DELIVER))) {
                    isSendpackageFlag = false;
                    isRecordSenderMessaegStop = false;
                } else {
                    isRecordSenderMessaegStop = true;
                    isSendpackageFlag = true;
                }
                if (StringUtils.isNotEmpty(customerEntity.getFixedReceiveMobile())
                        && customerEntity.getFixedReceiveMobile().equals(
                                waybill.getReceiveCustomerMobilephone())) {
                    // 根据是否是固定电话 判断 是否给收货人发送短息 快递派送处理,快递自提签收,pda快递派送签收,pda快递签收出库 update
                    isSendSpecificContacts = false;
                    isRecordReceiverMessageStop = false;
                }
            } else {
                isRecordSenderMessaegStop = true;
                isSendpackageFlag = true;
            }
        }

        // 判断此运单下是否有返货单
        if (returnWaybillExpressEntitys != null && returnWaybillExpressEntitys.size() > 0) {
            isSendpackageFlag = false;
        }
        // DN201511090005 FOSS签收界面签收人字段删除 by 243921
        String deliveryManType = dto.getDeliverymanType();

        if (isSendpackageFlag) {
            if ((StringUtils.isNotEmpty(pendingType) && pendingType
                    .equals(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE))
                    || (StringUtils.isNotEmpty(pendingType)
                            && pendingType.equals(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE) && (StringUtil
                            .isNotEmpty(deliveryCustIsMsg) && deliveryCustIsMsg.equals(SignConstants.YES)))) {
                try {
                    dto.setCustomerName(waybill.getDeliveryCustomerContact());
                    String deliverCustomerContent = "";

                    if (isRepeatOrder) { // 为返货单 发货人信息
                        deliverCustomerContent = this.returnExpressOrder(waybill, waybillExpressEntity,
                                currentinfo, dto, deliverCustomerContent, deliveryManType, "", true);
                        ;
                    } else {
                        deliverCustomerContent = validaCurrentInfo(currentinfo,
								dto, deliveryManType);
                    }
                    toDeliveryResult = sendMess(currentinfo, deliverCustomerContent, false, moduleCode,
                            waybill);
                } catch (SignException e) {// 捕获异常
                    // 异常处理
                    LOGGER.error("--短信发送失败!" + e.getErrorCode(), e);// 记录日志
                    toDeliveryResult = false;
                }
            }
        }
        String receiveCustomerCode = waybill.getReceiveCustomerCode();
        // 签收-收货人模板
        /**
         * 更改 通过crm端状态 判断是否发送 快递签收收货人短信 有两种情况是 不需要发送 快递签收收货人短信 1.crm端勾选 发件人短信批量发送 2.crm端勾选 收件人短信停发
         */
        if (true == dto.isRecordReceiverMessageStop()) {
            isSendpackageFlag = false;
        } else {
            if (StringUtils.isEmpty(receiveCustomerCode)) {
                isSendpackageFlag = true;
                isRecordReceiverMessageStop = true;
            } else if (StringUtils.isNotEmpty(receiveCustomerCode)) {
                CustomerEntity receiveCustomerEntity = customerDao.queryCustStateByCode(receiveCustomerCode);
                if (receiveCustomerEntity != null) {
                    String receiverSms = receiveCustomerEntity.getReceiverSms();
                    if (StringUtils.isNotEmpty(receiverSms)
                            && receiverSms.equals(SignConstants.STOP_MESSAGE_FOR_RECEIPT)) {
                        isRecordReceiverMessageStop = false;
                        isSendpackageFlag = false;
                    } else {
                        isRecordReceiverMessageStop = true;
                        isSendpackageFlag = true;
                    }
                } else {
                    isRecordReceiverMessageStop = true;
                    isSendpackageFlag = true;
                }
            }
        }
        // DN201506290022 收货人短信停发需求 219396 2015-08-24
        if (true == ifStopSmsToReciever(waybill)) {
            isSendSpecificContacts = false;
            isRecordReceiverMessageStop = false;
        }
        // 如果签收人类型为“本人”的话，就停止发送短信，如果是“非本人”的话，保持原有的发送规则
        if (StringUtils.isNotEmpty(deliveryManType) && "SIGN_PERSON_ME".equals(deliveryManType)) {
            isSendpackageFlag = false;
            isRecordReceiverMessageStop = false;
        }

        // 判断此运单下是否有返货单
        if (returnWaybillExpressEntitys != null && returnWaybillExpressEntitys.size() > 0) {
            isSendpackageFlag = false;
        }

        if (isSendpackageFlag == true && isSendSpecificContacts == true) {
            try {
                dto.setCustomerName(waybill.getReceiveCustomerContact());
                String receiveCustomercontent = "";

                if (isRepeatOrder) { // 为返货单 收货人信息
                    receiveCustomercontent = returnExpressOrder(waybill, waybillExpressEntity, currentinfo,
                            dto, "", deliveryManType, receiveCustomercontent, false);
                } else {
                    if (StringUtils.isNotEmpty(deliveryManType) && "SIGN_PERSON_ME".equals(deliveryManType)) {// 签收人类型不为空,且签收人类型为本人
                        // 获得短信模板内容
                        if (FossConstants.YES.equals(dto.getIsPdaSign())) {
                            receiveCustomercontent = getSmsContent(dto, currentinfo,
                                    SignConstants.SMS_CODE_EXPRESS_SIGN_RECEIVE_CUSTOMER_PDA);
                        } else {
                            receiveCustomercontent = getSmsContent(dto, currentinfo,
                                    SignConstants.SMS_CODE_EXPRESS_SIGN_RECEIVE_CUSTOMER);
                        }
                    } else {
                        // 获得短信模板内容
                        if (FossConstants.YES.equals(dto.getIsPdaSign())) {
                            receiveCustomercontent = getSmsContent(dto, currentinfo,
                                    SignConstants.SMS_CODE_EXPRESS_PROXY_SIGN_RECEIVE_CUSTOMER_PDA);
                        } else {
                            receiveCustomercontent = getSmsContent(dto, currentinfo,
                                    SignConstants.SMS_CODE_EXPRESS_PROXY_SIGN_RECEIVE_CUSTOMER);
                        }
                    }
                }
                // 是否发送成功
                toReceiveResult = sendMess(currentinfo, receiveCustomercontent, true, moduleCode, waybill);
            } catch (SignException e) {// 捕获异常
                // 异常处理
                LOGGER.error("--短信发送失败!" + e.getErrorCode(), e);// 记录日志
                toReceiveResult = false;
            }
        }
        // 发送在线通知
        try {
            noticeResult = sendNotice(currentinfo, waybill.getReceiveOrgCode(),
                    getSmsContent(dto, currentinfo, SignConstants.PKP_SIGN_NOTICE));
        } catch (BusinessException e) {
            // 异常处理
            LOGGER.error("--在线通知失败!" + e.getErrorCode(), e);// 记录日志
        }
        // 1、PC_ACTIVE:非PDA开单,默认给发货人发送短信
        // 2、PDA_ACTIVE:PDA开单，PDA开单勾选发送短信，需要给 发货人发送短信 判断是否发送成功
        // 更改通过外层判断 记录 日志

        if (isRecordReceiverMessageStop && isRecordSenderMessaegStop) {
            if ((StringUtils.isNotEmpty(pendingType) && pendingType
                    .equals(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE))
                    || (StringUtils.isNotEmpty(pendingType)
                            && pendingType.equals(WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE) && (StringUtil
                            .isNotBlank(deliveryCustIsMsg) && deliveryCustIsMsg.equals(SignConstants.YES)))) {
                if (toReceiveResult && noticeResult && toDeliveryResult) {// 如果短信和在线通知都发送成功
                    resultMsg = SignException.SMS_SIGN_NOTICE_SUCCESS;
                    LOGGER.info("--发货人短信，收货人短信，在线通知都发送成功");// 记录日志
                } else if (toReceiveResult && (!noticeResult) && toDeliveryResult) {
                    resultMsg = SignException.SIGN_SMS_OK_NOTICE_FAILED;
                    LOGGER.info("--收货人短信，发货人短信发送成功;在线通知发送失败");// 记录日志
                } else if (toReceiveResult && noticeResult && (!toDeliveryResult)) {
                    resultMsg = SignException.SIGN_RECEIVESMS_NOTICE_OK_DELIVERYSMS_FAILED;
                    LOGGER.info("--收货人短信，在线通知发送成功，发货人短信发送失败");// 记录日志
                } else if (toReceiveResult && (!noticeResult) && (!toDeliveryResult)) {
                    resultMsg = SignException.SIGN_RECEIVESMS_OK_DELIVERYSMS_NOTICE_FAILED;
                    LOGGER.info("--收货人短信发送成功;在线通知，发货人短信发送失败");// 记录日志
                } else if (!toReceiveResult && (!noticeResult) && toDeliveryResult) {
                    resultMsg = SignException.SIGN_DELIVERYSMS_OK_RECEIVESMS_NOTICE_FAILED;
                    LOGGER.info("--发货人短信发送成功;在线通知，收货人短信发送失败");// 记录日志
                } else if (!toReceiveResult && noticeResult && toDeliveryResult) {
                    resultMsg = SignException.SIGN_DELIVERYSMS_NOTICE_OK_RECEIVESMS_FAILED;
                    LOGGER.info("--发货人短信,在线通知发送成功;收货人短信发送失败");// 记录日志
                } else if (!toReceiveResult && noticeResult && (!toDeliveryResult)) {
                    resultMsg = SignException.SIGN_NOTICE_OK_RECEIVESMS_DELIVERYSMS_FAILED;
                    LOGGER.info("--在线通知发送成功;发货人短信，收货人短信发送失败");// 记录日志
                } else if (!toReceiveResult && (!noticeResult) && (!toDeliveryResult)) {
                    resultMsg = SignException.SIGN_OK_DELIVERYSMS_NOTICE_RECEIVESMS_FAILED;
                    LOGGER.info("--发货人短信，收货人短信，在线通知发送都失败！");// 记录日志
                }
            } else {
                if (noticeResult && toReceiveResult) {
                    resultMsg = "签收出库成功!在线通知,收货人短信发送成功！";
                    LOGGER.info("--签收出库成功!在线通知,收货人短信发送成功！");// 记录日志
                } else if ((!noticeResult) && toReceiveResult) {
                    resultMsg = "签收出库成功!收货人短信发送成功,在线通知发送失败！";
                    LOGGER.info("--签收出库成功!收货人短信发送成功,在线通知发送失败！");// 记录日志
                } else if (noticeResult && (!toReceiveResult)) {
                    resultMsg = "签收出库成功!在线通知发送成功,收货人短信发送失败！";
                    LOGGER.info("-签收出库成功!在线通知发送成功,收货人短信发送失败！");// 记录日志
                } else if ((!noticeResult) && (!toDeliveryResult)) {
                    resultMsg = "签收出库成功!收货人短信,在线通知发送失败！";
                    LOGGER.info("--签收出库成功!收货人短信,在线通知发送失败！");// 记录日志
                }
            }
        } else {
            if (isRecordReceiverMessageStop && !isRecordSenderMessaegStop) {
                if (toReceiveResult && noticeResult) {
                    resultMsg = "签收出库成功!在线通知,收货人短信发送成功！";
                    LOGGER.info("--签收出库成功!在线通知,收货人短信发送成功！");// 记录日志
                } else if (!toReceiveResult && !noticeResult) {
                    resultMsg = "签收出库成功!在线通知,收货人短信发送失败！";
                    LOGGER.info("--签收出库成功!在线通知,收货人短信发送失败！");// 记录日志
                } else if (!toReceiveResult && noticeResult) {
                    resultMsg = "签收出库成功!在线通知发送成功,收货人短信发送失败！";
                    LOGGER.info("--签收出库成功!在线通知发送成功,收货人短信发送失败！");// 记录日志
                } else if (toReceiveResult && !noticeResult) {
                    resultMsg = "签收出库成功!在线通知发送失败,收货人短信发送成功！";
                    LOGGER.info("--签收出库成功!在线通知发送失败,收货人短信发送成功！");// 记录日志
                }
            }
            if (!isRecordReceiverMessageStop && isRecordSenderMessaegStop) {
                if (toDeliveryResult && noticeResult) {
                    resultMsg = "签收出库成功!在线通知,发货人短信发送成功！";
                    LOGGER.info("--签收出库成功!在线通知,发货人短信发送成功！");// 记录日志
                } else if (!toDeliveryResult && !noticeResult) {
                    resultMsg = "签收出库成功!在线通知,发货人短信发送失败！";
                    LOGGER.info("--签收出库成功!在线通知,发货人短信发送失败！");// 记录日志
                } else if (!toDeliveryResult && noticeResult) {
                    resultMsg = "签收出库成功!在线通知发送成功,发货人短信发送失败！";
                    LOGGER.info("--签收出库成功!在线通知发送成功,发货人短信发送失败！");// 记录日志
                } else if (toDeliveryResult && !noticeResult) {
                    resultMsg = "签收出库成功!在线通知发送失败,发货人短信发送成功！";
                    LOGGER.info("--签收出库成功!在线通知发送失败,发货人短信发送成功！");// 记录日志
                }
            }
            if (!isRecordReceiverMessageStop && !isRecordSenderMessaegStop) {
                if (noticeResult) {
                    resultMsg = "签收出库成功!在线通知发送成功！";
                    LOGGER.info("--签收出库成功!在线通知发送成功！");// 记录日志
                } else if (!noticeResult) {
                    resultMsg = "签收出库成功!在线通知发送失败！";
                    LOGGER.info("--签收出库成功!在线通知发送失败！");// 记录日志
                }
            }
        }
        return resultMsg;
    }

	private String validaCurrentInfo(CurrentInfo currentinfo,
			WaybillSignResultDto dto, String deliveryManType) {
		String deliverCustomerContent;
		if (StringUtils.isNotEmpty(deliveryManType)
		        && "SIGN_PERSON_ME".equals(deliveryManType)) { // 签收人类型不为空,且签收人类型为本人
		    if (FossConstants.YES.equals(dto.getIsPdaSign())) {
		        deliverCustomerContent = getSmsContent(dto, currentinfo,
		                SignConstants.SMS_CODE_EXPRESS_SIGN_DELIVERY_CUSTOMER_PDA);
		    } else {
		        deliverCustomerContent = getSmsContent(dto, currentinfo,
		                SignConstants.SMS_CODE_EXPRESS_SIGN_DELIVERY_CUSTOMER);
		    }
		} else { // 签收人类型为空，且签收人类型不为本人
		    if (FossConstants.YES.equals(dto.getIsPdaSign())) {
		        deliverCustomerContent = getSmsContent(dto, currentinfo,
		                SignConstants.SMS_CODE_EXPRESS_PROXY_SIGN_DELIVERY_CUSTOMER_PDA);
		    } else {
		        deliverCustomerContent = getSmsContent(dto, currentinfo,
		                SignConstants.SMS_CODE_EXPRESS_SIGN_DELIVERY_CUSTOMER);
		    }
		}
		return deliverCustomerContent;
	}

    /**
     * 快递返货签收短信模板获取
     * 
     * @param waybill 运单 实体
     * @param waybillExpressEntity 运单快递实体
     * @param currentinfo 当前用户信息
     * @param dto 运单签收结果
     * @param deliverCustomerContent 发货人短信模板
     * @param deliveryManType 签收人类型
     * @param receiveCustomercontent 收货人短信模板
     * @param flag true:为发货人短信 false:为收货人短信
     * @return 短信内容
     */
    private String returnExpressOrder(WaybillEntity waybill, WaybillExpressEntity waybillExpressEntity,
            CurrentInfo currentinfo, WaybillSignResultDto dto, String deliverCustomerContent,
            String deliveryManType, String receiveCustomercontent, boolean flag) {
        // 设置原单号
        dto.setRawWaybillNo(waybillExpressEntity.getOriginalWaybillNo());
        /**
         * 原运单快递
         */
        WaybillExpressEntity rawWaybillExpressEntity = waybillExpressDao
                .queryWaybillExpressByNo(waybillExpressEntity.getOriginalWaybillNo());

        if (flag) {// 为返货单 发货人信息
            if (StringUtils.isNotBlank(waybill.getDeliveryCustomerMobilephone())
                    && waybill.getDeliveryCustomerMobilephone().equals(
                            waybillExpressEntity.getReceiveCustomerMobilephone())
                    || (StringUtils.isNotBlank(waybill.getDeliveryCustomerCode()) && waybill
                            .getDeliveryCustomerCode().equals(waybillExpressEntity.getReceiveCustomerCode()))) {
                if (null != rawWaybillExpressEntity) {
                    waybill.setDeliveryCustomerContact(rawWaybillExpressEntity.getReceiveCustomerContact());
                    waybill.setDeliveryCustomerMobilephone(rawWaybillExpressEntity
                            .getReceiveCustomerMobilephone());
                }

                if (StringUtils.isNotEmpty(deliveryManType) && "SIGN_PERSON_ME".equals(deliveryManType)) { // 签收人类型不为空,且签收人类型为本人
                    if (FossConstants.YES.equals(dto.getIsPdaSign())) {
                        deliverCustomerContent = getSmsContent(dto, currentinfo,
                                SignConstants.SMS_CODE_EXPRESS_RETURN_RECEIVE_CUSTOMER_PDA);
                        return deliverCustomerContent;
                    } else {
                        deliverCustomerContent = getSmsContent(dto, currentinfo,
                                SignConstants.SMS_CODE_EXPRESS_RETURN_RECEIVE_CUSTOMER);
                        return deliverCustomerContent;
                    }
                } else {
                    if (FossConstants.YES.equals(dto.getIsPdaSign())) {
                        deliverCustomerContent = getSmsContent(dto, currentinfo,
                                SignConstants.SMS_CODE_EXPRESS_RETURN_RECEIVE_CUSTOMER_PDA);
                        return deliverCustomerContent;
                    } else {
                        deliverCustomerContent = getSmsContent(dto, currentinfo,
                                SignConstants.SMS_CODE_EXPRESS_RETURN_RECEIVE_CUSTOMER);
                        return deliverCustomerContent;
                    }
                }
            } else {
                if (FossConstants.YES.equals(dto.getIsPdaSign())) {
                    deliverCustomerContent = getSmsContent(dto, currentinfo,
                            SignConstants.SMS_CODE_EXPRESS_RETURN_DELIVERY_CUSTOMER_PDA);
                    return deliverCustomerContent;
                } else {
                    deliverCustomerContent = getSmsContent(dto, currentinfo,
                            SignConstants.SMS_CODE_EXPRESS_RETURN_DELIVERY_CUSTOMER);
                    return deliverCustomerContent;
                }
            }
        } else { // 为返货单 收货人信息
            if (StringUtils.isNotEmpty(deliveryManType) && "SIGN_PERSON_ME".equals(deliveryManType)) { // 签收人类型不为空,且签收人类型为本人
                // 获得短信模板内容
                if (FossConstants.YES.equals(dto.getIsPdaSign())) {
                    receiveCustomercontent = getSmsContent(dto, currentinfo,
                            SignConstants.SMS_CODE_EXPRESS_RETURN_RECEIVE_CUSTOMER_PDA);
                    return receiveCustomercontent;
                } else {
                    receiveCustomercontent = getSmsContent(dto, currentinfo,
                            SignConstants.SMS_CODE_EXPRESS_RETURN_RECEIVE_CUSTOMER);
                    return receiveCustomercontent;
                }

            } else {
                // 获得短信模板内容
                if (FossConstants.YES.equals(dto.getIsPdaSign())) {
                    receiveCustomercontent = getSmsContent(dto, currentinfo,
                            SignConstants.SMS_CODE_EXPRESS_RETURN_RECEIVE_CUSTOMER_PDA);
                    return receiveCustomercontent;
                } else {
                    receiveCustomercontent = getSmsContent(dto, currentinfo,
                            SignConstants.SMS_CODE_EXPRESS_RETURN_RECEIVE_CUSTOMER);
                    return receiveCustomercontent;
                }
            }
        }
    }

    /**
     * 根据传入零担产品的运单号，发送零担模板短信
     * 
     * @author foss-yangbin
     * @date 2014-3-3 下午3:45:44
     * @param dto
     * @return
     */
    private String sendLTLMSG(WaybillEntity waybill, CurrentInfo currentinfo, WaybillSignResultDto dto) {
        // ISSUE-31182013年6月15日前开的单，做签收时不再发送短信给发货客户；DN201508110026--15年5月前开单货物取消签收短信通知
        // DN201606210011--2016年1月1日前开单货物取消签收短信
        Date dd = DateUtils.convert("2016-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
        if (dd != null && waybill.getBillTime() != null && dd.compareTo(waybill.getBillTime()) > 0) {
            return SignException.SIGN_SUCCESS;
        }
        LOGGER.info("--调用发送短信,在线通知开始  waybillSignResultDto:" + dto);// 记录日志
        String resultMsg = "";
        String deliverySmsCode = "";// 发货人短信模板
        String receiveSmsCode = "";// 收货人短信模板
        boolean onlySendtoReceive = false;// 只给收货人发短信
        if (ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(dto.getSituation())) {
            deliverySmsCode = SignConstants.SMS_CODE_DELIVERY_NORMAL_SIGN_LD;
            receiveSmsCode = SignConstants.SMS_CODE_RECEIVE_NORMAL_SIGN_LD;
            if (StringUtils.isNotEmpty(waybill.getProductCode())// 如果运输性质不为空运，偏线,并且收货人是签收人本人的时候不给收货人发送短信
                    && waybill.getProductCode() != "AF" && waybill.getProductCode() != "PF") {
                if (StringUtils.isNotEmpty(dto.getDeliverymanName())
                        && StringUtils.isNotEmpty(waybill.getReceiveCustomerContact())) {
                    if (dto.getDeliverymanName().trim().equals(waybill.getReceiveCustomerContact().trim())) {
                        dto.setIsSendSMSToReceiveCustomer(FossConstants.NO);// 不给收货人发送短信
                    }
                }
            }
            /**
             * DN201606250013 代签收 正常签收，当输入签收人字段信息与实际运单收货联系人不一致，只给收货人发送另一套短信模板
             */
            if (StringUtils.isNotEmpty(dto.getDeliverymanName())
                    && StringUtils.isNotEmpty(waybill.getReceiveCustomerContact())) {
                if (!dto.getDeliverymanName().trim().equals(waybill.getReceiveCustomerContact().trim())) {
                    onlySendtoReceive = true;// 只给收货人发送短信
                    receiveSmsCode = SignConstants.SMS_CODE_PKP_NOTIFY_DRIVER_ACCEPT_MODIFY;// 代签收，收货人与签收人不一致短信模板
                }
            }
            // 无异常
            dto.setIsException(SignConstants.IS_EXCEPTION_NO);
        } else {// 有异常
            dto.setIsException(SignConstants.IS_EXCEPTION_YES);
            deliverySmsCode = SignConstants.SMS_CODE_DELIVERY_UNNORMAL_SIGN_LD;
            receiveSmsCode = SignConstants.SMS_CODE_RECEIVE_UNNORMAL_SIGN_LD;
            // 根据收货部门code获取对应的部门电话
            OrgAdministrativeInfoEntity receiveOrg = orgAdministrativeInfoService
                    .queryOrgAdministrativeInfoByCodeClean(waybill.getReceiveOrgCode());
            if (receiveOrg != null) {
                dto.setReceiveOrgTel(StringUtil.isEmpty(receiveOrg.getDepTelephone()) ? "" : receiveOrg
                        .getDepTelephone());
            }
            // 签收出库收货人短信---如运输性质为空运，则在短信后缀中增加：详情可致电当地营业部，收货部门电话号码
            if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())) {
                if (receiveOrg != null) {
                    dto.setCustomerPickupOrgTel(StringUtil.isEmpty(receiveOrg.getDepTelephone()) ? ""
                            : receiveOrg.getDepTelephone());
                }
            } else {
                // 根据提货网点code获取对应的部门电话
                OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService
                        .queryOrgAdministrativeInfoByCodeClean(waybill.getCustomerPickupOrgCode());
                if (orgAdministrativeInfo != null) {
                    dto.setCustomerPickupOrgTel(StringUtil.isEmpty(orgAdministrativeInfo.getDepTelephone()) ? ""
                            : orgAdministrativeInfo.getDepTelephone());
                }
            }
        }
        boolean toDeliveryResult = false; // 给发货人发送短信返回结果
        boolean toReceiveResult = false; // 给收货人发送短信返回结果
        boolean noticeResult = false;

        // 短信业务类型-零担签收
        String moduleCode = NotifyCustomerConstants.BS_TYPE_PKP_SIGN;
        /**
         * DN201606250013 正常签收，当输入签收人字段信息与实际运单收货联系人不一致，只给收货人发送另一套短信模板
         */
        if (onlySendtoReceive != true) {
            try {
                dto.setCustomerName(waybill.getDeliveryCustomerContact());// 提货人名称
                String deliverCustomerContent = getSmsContent(dto, currentinfo, deliverySmsCode);
                toDeliveryResult = sendMess(currentinfo, deliverCustomerContent, false, moduleCode, waybill);
            } catch (SignException e) {// 捕获异常
                // 异常处理
                LOGGER.error("--短信发送失败!" + e.getErrorCode(), e);// 记录日志
                toDeliveryResult = false;
            }
        }
        // 如果“是否收货人发送短信”字段为空 默认发送
        if (StringUtil.isBlank(dto.getIsSendSMSToReceiveCustomer())) {
            try {
                dto.setCustomerName(waybill.getReceiveCustomerContact());// 收货人名称
                String receiveCustomercontent = getSmsContent(dto, currentinfo, receiveSmsCode);
                toReceiveResult = sendMess(currentinfo, receiveCustomercontent, true, moduleCode, waybill);
            } catch (SignException e) {// 捕获异常
                // 异常处理
                LOGGER.error("--短信发送失败!" + e.getErrorCode(), e);// 记录日志
                toReceiveResult = false;
            }
        }
        try {
            noticeResult = sendNotice(currentinfo, waybill.getReceiveOrgCode(),
                    getSmsContent(dto, currentinfo, SignConstants.PKP_SIGN_NOTICE));
        } catch (BusinessException e) {
            // 异常处理
            LOGGER.error("--在线通知短信发送失败!" + e.getErrorCode(), e);// 记录日志
            noticeResult = false;
        }
        if (StringUtil.isBlank(dto.getIsSendSMSToReceiveCustomer())) {
            if (toReceiveResult && noticeResult && toDeliveryResult) {// 如果短信和在线通知都发送成功
                resultMsg = SignException.SMS_SIGN_NOTICE_SUCCESS;
                LOGGER.info("--发货人短信，收货人短信，在线通知都发送成功");// 记录日志
            } else if (toReceiveResult && (!noticeResult) && toDeliveryResult) {
                resultMsg = SignException.SIGN_SMS_OK_NOTICE_FAILED;
                LOGGER.info("--收货人短信，发货人短信发送成功;在线通知发送失败");// 记录日志
            } else if (toReceiveResult && noticeResult && (!toDeliveryResult)) {
                resultMsg = SignException.SIGN_RECEIVESMS_NOTICE_OK_DELIVERYSMS_FAILED;
                LOGGER.info("--收货人短信，在线通知发送成功，发货人短信发送失败");// 记录日志
            } else if (toReceiveResult && (!noticeResult) && (!toDeliveryResult)) {
                resultMsg = SignException.SIGN_RECEIVESMS_OK_DELIVERYSMS_NOTICE_FAILED;
                LOGGER.info("--收货人短信发送成功;在线通知，发货人短信发送失败");// 记录日志
            } else if (!toReceiveResult && (!noticeResult) && toDeliveryResult) {
                resultMsg = SignException.SIGN_DELIVERYSMS_OK_RECEIVESMS_NOTICE_FAILED;
                LOGGER.info("--发货人短信发送成功;在线通知，收货人短信发送失败");// 记录日志
            } else if (!toReceiveResult && noticeResult && toDeliveryResult) {
                resultMsg = SignException.SIGN_DELIVERYSMS_NOTICE_OK_RECEIVESMS_FAILED;
                LOGGER.info("--发货人短信,在线通知发送成功;收货人短信发送失败");// 记录日志
            } else if (!toReceiveResult && noticeResult && (!toDeliveryResult)) {
                resultMsg = SignException.SIGN_NOTICE_OK_RECEIVESMS_DELIVERYSMS_FAILED;
                LOGGER.info("--在线通知发送成功;发货人短信，收货人短信发送失败");// 记录日志
            } else if (!toReceiveResult && (!noticeResult) && (!toDeliveryResult)) {
                resultMsg = SignException.SIGN_OK_DELIVERYSMS_NOTICE_RECEIVESMS_FAILED;
                LOGGER.info("--发货人短信，收货人短信，在线通知发送都失败！");// 记录日志
            }
        } else {
            if (noticeResult && toDeliveryResult) {// 如果短信和在线通知都发送成功
                resultMsg = SignException.SIGN_NOTICE_SUCCESS;
                LOGGER.info("--发货人短信，在线通知都发送成功");// 记录日志
            } else if ((!noticeResult) && toDeliveryResult) {
                resultMsg = SignException.SIGN_DELIVERYSMS_OK_NOTICE_FAILED;
                LOGGER.info("--发货人短信发送成功;在线通知发送失败");// 记录日志
            } else if (noticeResult && (!toDeliveryResult)) {
                resultMsg = SignException.SIGN_NOTICE_OK_DELIVERYSMS_FAILED;
                LOGGER.info("--在线通知发送成功，发货人短信发送失败");// 记录日志
            } else if ((!noticeResult) && (!toDeliveryResult)) {
                resultMsg = SignException.SIGN_DELIVERYSMS_NOTICE_FAILED;
                LOGGER.info("--在线通知，发货人短信发送失败");// 记录日志
            }
        }
        return resultMsg;
    }

    /**
     * 校验是否需要将发票信息传输至发票系统 需要满足的条件（是否第一次签收，预付金额是否大于0，运单类型是否为电子发票）
     */
    @Override
    public boolean isNeedSendInvoiceInfo(WaybillEntity entity, ActualFreightEntity actual) {
        boolean isSendInvoice = false;
        if (actual != null && entity.getPrePayAmount() != null && entity.getPrePayAmount().longValue() > 0
                && StringUtils.isNotBlank(actual.getIsElectronicInvoice())
                && FossConstants.YES.equals(actual.getIsElectronicInvoice())) {
            // 传入参数(运单号)
            WaybillSignResultEntity waySignEntity = new WaybillSignResultEntity();
            waySignEntity.setWaybillNo(entity.getWaybillNo());
            // 根据运单号 查询运单签收结果--如果存在 签收记录 则不用将发票信息传输至发票系统
            WaybillSignResultEntity waybillSign = waybillSignResultDao.queryWaybillSignResult(waySignEntity);
            if (waybillSign == null) {
                isSendInvoice = true;
            }
        }
        return isSendInvoice;
    }

    public void setElectronicInvoiceSendService(IElectronicInvoiceSendService electronicInvoiceSendService) {
        this.electronicInvoiceSendService = electronicInvoiceSendService;
    }

    /**
     * DMANA-9716 为投诉自动变更签收结果为异常签收提供接口 更新运单签收结果(t_srv_waybill_sign_result)
     * 
     * @author 231438-foss-chenjunying
     * @param
     * */
    public void changeExceptionSignResult(WaybillSignResultEntity oldWaybillSignResult,
            WaybillSignResultEntity newWaybillSignResult) {
        LOGGER.info("签收变更提供接口 运单正常签收(72小时内投诉)->异常签收  开始"
                + ReflectionToStringBuilder.toString(oldWaybillSignResult)
                + ReflectionToStringBuilder.toString(newWaybillSignResult));
        if (oldWaybillSignResult == null || newWaybillSignResult == null) {
            LOGGER.error("传入的对象为空，不能进行签收变更操作");// 记录日志
            throw new SignException(SignException.OBJECT_IS_NOT_NULL);
        }
        // 得到当前时间
        Date modifyTime = new Date();
        // 作废运单签收结果里当前运单号
        this.invalidWaybillSignResult(oldWaybillSignResult.getId(), modifyTime);
        // 添加新的运单签收结果
        newWaybillSignResult.setActive(FossConstants.ACTIVE);
        // 生效时间为当前时间
        newWaybillSignResult.setCreateTime(modifyTime);
        // 更改时间为当前时间
        newWaybillSignResult.setModifyTime(modifyTime);
        // 不是PDA签收
        newWaybillSignResult.setIsPdaSign(FossConstants.NO);
        if (StringUtils.isBlank(newWaybillSignResult.getSignSituation())) {
            throw new SignException(SignException.SIGN_SITUATION_IS_NOT_NULL);// 变更后的签收情况为空！不能执行变更操作
        }
        // 添加新的运单签收记录（异常签收）
        waybillSignResultDao.addWaybillSignResult(newWaybillSignResult);
        LOGGER.info("签收变更提供接口   正常签收->异常签收   结束");// 记录日志
    }

    /**
     * 第一次签收成功后FOSS将发票信息传输至发票系统
     * 
     * @throws Exception
     */
    @Override
    public void sendInvoiceInfo(WaybillEntity entity, ActualFreightEntity actual) {
        // 获取操作部门
        OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByCodeClean(entity.getReceiveOrgCode());
        if (org == null) {
            return;
        }
        ElectronicInvoiceDto dto = new ElectronicInvoiceDto();
        dto.setWayBillNo(entity.getWaybillNo());
        dto.setBillingTime(entity.getBillTime());
        dto.setCompanyCode(org.getSubsidiaryCode());// 开票子公司编码-必填
        dto.setCompanyStandCode(org.getUnifiedCode());// 开票部门标杆编码-必填
        if (StringUtils.isNotBlank(entity.getDeliveryCustomerName())) {
            dto.setBuyerName(entity.getDeliveryCustomerName());// 购货方名称-必填
        } else {
            dto.setBuyerName(entity.getDeliveryCustomerContact());// 购货方名称-必填
        }
        dto.setBuyerPhone(actual.getInvoiceMobilePhone());// 购货方电话-必填
        dto.setBuyerMail(actual.getEmail());// 非必填
        dto.setAmountTotal(entity.getPrePayAmount());// 开票金额合计-必填
        dto.setBusinessType(entity.getProductCode());// 业务类型 与发票开发确认传运输性质
        dto.setBuyerProvince(entity.getDeliveryCustomerProvCode());// 发货省份CODE
        /*
         * if(StringUtils.isNotBlank(entity.getProductCode()) &&
         * (ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(entity.getProductCode())
         * ||
         * ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE.equals(entity.getProductCode
         * ())) ){ dto.setBusinessType("01");//业务类型：快递01,零担02，通过产品类型区分 }else {
         * dto.setBusinessType("02");//业务类型：快递01,零担02，通过产品类型区分 }
         */
        try {
            electronicInvoiceSendService.electronicInvoiceSend(dto);
        } catch (Exception e) {
            LOGGER.error("将发票信息传输至发票系统有异常", e);// 异常记录
        }
    }

    public void setPrintAgentWaybillService(IPrintAgentWaybillService printAgentWaybillService) {
        this.printAgentWaybillService = printAgentWaybillService;
    }

    /**
     * PDA新增派送拉回原因为“联系不上客户”，发送短信通知客户（派送/取件）
     * 
     * @author foss-sunyanfei
     * @date 2015-7-13 上午 09:30:12
     * @param dto 派件拉回dto
     * @param pdaReturnDto 订单拉回dto
     * @param waybill 运单
     * @param orderEntity 订单
     * @param currentinfo 当前人
     * @param isDelivery 是否是派件
     */
    @Override
    public boolean sendMessCustomer(PdaPullbackgoodsDto dto, PdaReturnDto pdaReturnDto,
            WaybillEntity waybill, DispatchOrderEntity orderEntity, CurrentInfo currentinfo,
            boolean isDelivery) {

        LOGGER.info("--发送短信通知客户（派送/取件）!");// 记录日志

        PdaPullbackgoodsDto entity = new PdaPullbackgoodsDto();
        PdaOrderDto pullEntity = new PdaOrderDto();
        EmployeeEntity emp = new EmployeeEntity();

        // 短信发送返回结果
        boolean resultMsg = false;
        // 短信模板
        String sendBackSms = "";
        // 模块名称
        String moduleCode = "";
        // 快递员电话
        String expressEmpTel = "";

        if (isDelivery) {// 派件
            emp = getEmployeeEntity(dto.getDriverCode());
            LOGGER.info("获取快递员的相关信息:" + ReflectionToStringBuilder.toString(emp));
            expressEmpTel = emp.getMobilePhone();
            // 短信业务类型-派送拉回
            moduleCode = SignConstants.SMS_CODE_EXP_PKP_CONSIGNEE;

            entity.setWaybillNo(dto.getWaybillNo()); // 运单号
            entity.setArrivesheetNo(dto.getArrivesheetNo()); // 到达联编号
            entity.setPullbackReason(dto.getPullbackReason()); // 拉回原因
            entity.setDriverCode(dto.getDriverCode()); // 司机工号
            entity.setPullbackTime(dto.getPullbackTime()); // 拉回时间
            entity.setSignNote(dto.getSignNote()); // 备注
            entity.setPullbackQty(dto.getPullbackQty()); // 拉回件数
            entity.setVehicleNo(dto.getVehicleNo()); // 车牌号
            entity.setOperateOrgCode(dto.getOperateOrgCode()); // 操作部门
            entity.setNextDeliverTime(dto.getNextDeliverTime()); // 再次派送时间
            entity.setExpressEmpTel(expressEmpTel); // 快递员电话
            if (StringUtil.isBlank(dto.getWaybillNo()) || StringUtil.isBlank(expressEmpTel)) {
                LOGGER.info("派件时，运单号和快递员手机号为null");
                return false;
            }
            LOGGER.info("派件信息：" + ReflectionToStringBuilder.toString(entity));
        } else {// 收件
            emp = getEmployeeEntity(pdaReturnDto.getDriverCode());
            expressEmpTel = emp.getMobilePhone();
            // 短信业务类型-取件拉回
            moduleCode = SignConstants.SMS_CODE_EXP_PKP_CONSIGNOR;

            pullEntity.setOrderNo(pdaReturnDto.getOrderNo()); // 订单号
            pullEntity.setOrderType(pdaReturnDto.getOrderType()); // 订单类型
            pullEntity.setDriverCode(pdaReturnDto.getDriverCode()); // 司机编码
            pullEntity.setUpdateDateTime(pdaReturnDto.getUpdateDateTime()); // 更新时间
            pullEntity.setOrderStatus(pdaReturnDto.getOrderStatus()); // 订单状态
            pullEntity.setReturnReason(pdaReturnDto.getReturnReason()); // 退回原因
            pullEntity.setRemark(pdaReturnDto.getRemark()); // 备注
            pullEntity.setForwardDriverCode(pdaReturnDto.getForwardDriverCode()); // 转发人工号
            pullEntity.setForwardDriverName(pdaReturnDto.getForwardDriverName()); // 转发人姓名
            pullEntity.setOptState(pdaReturnDto.getOptState()); // 操作状态
            pullEntity.setLatestPickupTime(pdaReturnDto.getUpdateDateTime()); // 更新时间
            if (StringUtil.isBlank(expressEmpTel)) {
                LOGGER.info("取件时快递员号码为null");
                return false;
            }
            LOGGER.info("取件时快递员号码为：" + expressEmpTel);
            pullEntity.setExpressEmpTel(expressEmpTel); // 快递员电话
        }

        try {
            if (isDelivery) {// 获取短信模板内容、发送短信-----派件

                // 获取短信内容----派件
                sendBackSms = getSmsSendBackMessage(entity, null, currentinfo,
                        SignConstants.SMS_CODE_EXPRESS_PKP_CONSIGNEE, isDelivery, emp);
                LOGGER.info("派件联系不上客户-短信内容：" + sendBackSms);
                // 若运输产品类型-快递
                if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(),
                        waybill.getBillTime())) {
                    // 快递派送拉回短信
                    resultMsg = sendMessageCustomer(currentinfo, sendBackSms, isDelivery, moduleCode,
                            waybill, null);
                }
            } else {// 获取短信模板内容、发送短信-----收件

                // 获取短信内容----收件
                sendBackSms = getSmsSendBackMessage(null, pullEntity, currentinfo,
                        SignConstants.SMS_CODE_EXPRESS_PKP_CONSIGNOR, isDelivery, emp);
                LOGGER.info("收件联系不上客户-短信内容：" + sendBackSms);

                // 若运输产品类型-快递
                if (waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(),
                        waybill.getBillTime())) {
                    // 快递派送拉回短信
                    resultMsg = sendMessageCustomer(currentinfo, sendBackSms, isDelivery, moduleCode, null,
                            orderEntity);
                }
            }
        } catch (Exception e) {
            // 异常处理
            LOGGER.error("--短信发送失败!", e);// 记录日志

        }
        return resultMsg;
    }

    /**
     * 发送短信-（派送/收件）
     * 
     * @author foss-sunyanfei
     * @date 2015-7-13 上午 11:08:20
     * @param currentInfo currentInfo.operate 操作人 currentInfo.operatorCode 操作人编码
     *        currentInfo.operateOrgName 操作部门名称 currentInfo.operateOrgCode 操作部门编码
     * @param customerSms 短信内容
     * @param isToReceiveCustomer 是否给收货人发送短信
     * @param moduleCode 模块名称
     * @param waybill
     * @return
     */
    public boolean sendMessageCustomer(CurrentInfo currentinfo, String customerSms,
            boolean isToReceiveCustomer, String moduleCode, WaybillEntity waybill,
            DispatchOrderEntity orderEntity) {
        LOGGER.info("发送短信-（派送/收件）!");// 记录日志
        boolean sendMessResult = false;
        try {
            NotificationEntity notificationEntity = new NotificationEntity();
            if (isToReceiveCustomer) {
                // 运单号
                notificationEntity.setWaybillNo(waybill.getWaybillNo());
                // 收货人
                notificationEntity.setConsignee(waybill.getReceiveCustomerContact());
                // 手机号
                notificationEntity.setMobile(waybill.getReceiveCustomerMobilephone());
            } else {
                // 订单号
                notificationEntity.setWaybillNo(orderEntity.getOrderNo());
                // 发货人
                notificationEntity.setConsignee(orderEntity.getCustomerName());
                // 手机号
                notificationEntity.setMobile(orderEntity.getMobile());
            }
            // 通知类型为短信通知
            notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
            // 通知内容
            notificationEntity.setNoticeContent(customerSms);
            // 操作人
            notificationEntity.setOperator(currentinfo.getEmpName());
            // 操作人编码
            notificationEntity.setOperatorNo(currentinfo.getEmpCode());
            // 操作部门
            notificationEntity.setOperateOrgName(currentinfo.getCurrentDeptName());
            // 操作部门编码
            notificationEntity.setOperateOrgCode(currentinfo.getCurrentDeptCode());
            // 操作时间
            notificationEntity.setOperateTime(new Date());
            // 模块名称
            notificationEntity.setModuleName(moduleCode);
            LOGGER.info("派件、取件发送短信前参数：" + ReflectionToStringBuilder.toString(notificationEntity));
            // 发送短信
            sendSms(notificationEntity);
            // notifyCustomerService.sendMessage(notificationEntity);
            sendMessResult = true;
        } catch (NotifyCustomerException e) {// 捕获异常
            // 异常处理
            LOGGER.error("--短信发送失败!" + e.getErrorCode(), e);// 记录日志
            sendMessResult = false;
        }
        return sendMessResult;
    }

    /**
     * 获取短信信息内容
     * 
     * @author foss-sunyanfei
     * @date 2015-7-13 上午10:10:39
     * @param entity 派送拉回dto
     * @param currentInfo
     * @param smsCode
     * @return
     */
    public String getSmsSendBackMessage(PdaPullbackgoodsDto entity, PdaOrderDto pullEntity,
            CurrentInfo currentInfo, String smsCode, boolean isDelivery, EmployeeEntity emp) {
        LOGGER.info("获取短信内容（派件、订单）");// 记录日志
        String sms = ""; // 返回短信
        // 模版参数
        SmsParamDto smsParamDto = new SmsParamDto();
        // 短信编码
        smsParamDto.setSmsCode(smsCode);

        if (isDelivery) { // 派送
            if (null != emp.getOrgCode()) {
                // 部门编码
                smsParamDto.setOrgCode(emp.getOrgCode());
            }
            // 派送拉回联系
            smsParamDto.setMap(getSmsSendBackParam(entity));
        } else { // 取件
            if (null != emp.getOrgCode()) {
                // 部门编码
                smsParamDto.setOrgCode(emp.getOrgCode());
            }
            // 收件拉回
            smsParamDto.setMap(getSmsPullBackParam(pullEntity));
        }
        LOGGER.info("派件、取件查询短信dto：" + ReflectionToStringBuilder.toString(smsParamDto));
        try {
            sms = sMSTempleteService.querySmsByParam(smsParamDto);
            LOGGER.info("收件、派件查询返回短信内容：" + sms);
        } catch (SMSTempleteException e) {// 捕获异常
            LOGGER.error("短信内容为空", e);// 记录日志
            throw new SignException(SignException.MESS_CONTENT_ISNULL, e);// 短信内容为空
        }
        if (StringUtil.isBlank(sms)) {
            LOGGER.error("没有对应的短信模版");// 记录日志
            throw new SignException(SignException.NO_SMS_TEMPLATES);// 没有对应的短信模版
        }
        return sms;
    }

    /**
     * 设置短信模版内容的参数-派送拉回
     * 
     * @author foss-sunyanfei
     * @date 2015-7-13 上午10:24:23
     * @param entity * waybillNo 运单号 arrivesheetNo 到达联编号 pullbackReason 拉回原因 driverCode 司机工号
     *        pullbackTime 拉回时间 signNote 备注 pullbackQty 拉回件数 vehicleNo 车牌号 operateOrgCode 操作部门
     *        nextDeliverTime 再次派送时间 *
     * @return
     */
    private Map<String, String> getSmsSendBackParam(PdaPullbackgoodsDto entity) {
        LOGGER.info("获取短信内容（派件）--派送拉回 map");// 记录日志
        Map<String, String> map = new HashMap<String, String>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sdf.format(new Date());
        // 运单号
        map.put("waybillNo", entity.getWaybillNo());

        // 到达联编号
        map.put("arrivesheetNo", entity.getArrivesheetNo());

        // 拉回原因
        map.put("pullbackReason", entity.getPullbackReason());

        // 司机工号
        map.put("driverCode", entity.getDriverCode());

        // 拉回时间
        map.put("pullbackTime", date);
        // 备注
        map.put("signNote", entity.getSignNote());

        // 拉回件数
        map.put("pullbackQty", entity.getPullbackQty().toString());

        // 车牌号
        map.put("vehicleNo", entity.getVehicleNo());

        // 操作部门
        map.put("operateOrgCode", entity.getOperateOrgCode());

        // 再次派送时间
        map.put("nextDeliverTime", DateUtils.convert(entity.getNextDeliverTime(), null));

        // 快递员电话
        map.put("expressEmpTel", entity.getExpressEmpTel());

        LOGGER.info("派件短信封装map:" + ReflectionToStringBuilder.toString(map));

        return map;
    }

    /**
     * 设置短信模版内容的参数-收件拉回
     * 
     * @author foss-sunyanfei
     * @date 2015-7-13 下午15:20:53
     * @param entity * orderNo 订单号 orderType 订单类型 driverCode 司机编码 updateDateTime 更新时间 orderStatus
     *        订单状态 returnReason 退回原因 remark 备注 forwardDriverCode 转发人工号 forwardDriverName 转发人姓名
     *        optState 操作状态 earliestPickupTime 接货最早时间 latestPickupTime 接货最晚时间 *
     * @return
     */
    private Map<String, String> getSmsPullBackParam(PdaOrderDto entity) {
        LOGGER.info("获取短信内容（订单）---收件拉回map");// 记录日志
        Map<String, String> map = new HashMap<String, String>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        // 订单号
        map.put("orderNo", entity.getOrderNo());

        // 订单类型
        map.put("orderType", entity.getOrderType());

        // 司机编码
        map.put("driverCode", entity.getDriverCode());

        String date = sdf.format(new Date());

        // 更新时间
        map.put("updateDateTime", date);

        // 订单状态
        map.put("orderStatus", entity.getOrderStatus());

        // 退回原因
        map.put("returnReason", entity.getReturnReason());

        // 备注
        map.put("remark", entity.getRemark());

        // 转发人工号
        map.put("forwardDriverCode", entity.getForwardDriverCode());

        // 转发人姓名
        map.put("forwardDriverName", entity.getForwardDriverName());

        // 操作状态
        map.put("optState", entity.getOptState());

        // 接货最早时间
        map.put("earliestPickupTime", DateUtils.convert(entity.getEarliestPickupTime(), null));

        // 接货最晚时间
        map.put("latestPickupTime", DateUtils.convert(entity.getLatestPickupTime(), null));

        // 快递员电话
        map.put("expressEmpTel", entity.getExpressEmpTel());

        LOGGER.info("取件短信封装map:" + ReflectionToStringBuilder.toString(map));

        return map;
    }

    /**
     * 获取快递员所在组织架构部门
     * 
     * @author foss-sunyanfei
     * @date 2015-7-14 上午10:14:13
     * @param empCode
     * @return
     */
    // 根据快递员code，获取快递员所在组织架构部门(营业部和快递点部层级)。
    private EmployeeEntity getEmployeeEntity(String empCode) {
        LOGGER.info("获取短信内容（派件、订单）---获取快递员所在组织架构部门");// 记录日志
        EmployeeEntity employ = new EmployeeEntity();
        // 判断快递员是否为空，若为空则无法根据快递员所属部门查询快递点部
        if (StringUtils.isNotEmpty(empCode)) {
            LOGGER.info("---------- 派送拉回调用接口：设置快递员及快递点信息----------");// 记录日志
            // 快递员编码查询所属部门
            employ = employeeService.queryEmployeeByEmpCode(StringUtil.defaultIfNull(empCode));
            OrgAdministrativeInfoEntity department = employ.getDepartment();
            if (null != department) {
                // 判断是否是快递点部
                if (FossConstants.YES.equals(department.getExpressPart())) {
                    // 快递点部CODE
                    employ.setOrgCode(department.getCode());
                    // 快递点部名称
                    employ.setOrgName(department.getName());
                }

            }

            // 获取快递车辆快递员电话
            ExpressVehiclesEntity express = new ExpressVehiclesEntity();
            express.setEmpCode(empCode);
            express.setActive(FossConstants.ACTIVE);
            List<ExpressVehiclesEntity> expressList = expressVehiclesService
                    .queryExpressVehiclesByEntity(express);
            employ.setMobilePhone(expressList.get(0).getMobilePhone());

        }
        return employ;
    }

    /**
     * 发送短信
     * 
     */
    private void sendSms(NotificationEntity notificationEntity) {
        SMSSendLogEntity smsSendLog = new SMSSendLogEntity();
        try {
            // 发送部门编码
            smsSendLog.setSenddeptCode(notificationEntity.getOperateOrgCode());
            // 发送人员编码
            smsSendLog.setSenderCode(notificationEntity.getOperatorNo());
            // 电话
            smsSendLog.setMobile(notificationEntity.getMobile());
            // 短信内容
            smsSendLog.setContent(notificationEntity.getNoticeContent());
            // 发送部门
            smsSendLog.setSenddept(notificationEntity.getOperateOrgName());
            // 发送人
            smsSendLog.setSender(notificationEntity.getOperator());
            // 业务类型
            smsSendLog.setMsgtype(notificationEntity.getModuleName());
            // 短信来源
            smsSendLog.setMsgsource(NotifyCustomerConstants.SYS_SOURCE);
            // 唯一标识
            smsSendLog.setUnionId(UUIDUtils.getUUID());
            // 运单号
            smsSendLog.setWaybillNo(notificationEntity.getWaybillNo());
            // 发送时间
            smsSendLog.setSendTime(new Date());
            // 服务类型（1:短信）
            smsSendLog.setServiceType(NumberConstants.NUMERAL_S_ONE);
            LOGGER.info("收件、派件发送短信操作实体SMSSendLogEntity：" + ReflectionToStringBuilder.toString(smsSendLog));
            // 发送短信内容
            smsSendLogService.sendSMS(smsSendLog);
        } catch (SMSSendLogException se) {
            LOGGER.error(NotifyCustomerException.ERROR, se);
            throw new NotifyCustomerException(se.getMessage(), se);
        }
    }

    /**
     * 判断是否停发收货人短信
     */
    private boolean ifStopSmsToReciever(WaybillEntity waybill) {
        if (null != waybill) {
            // 零担不停发
            if (!waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(),
                    waybill.getBillTime())) {
                return false;
            }
            if (StringUtils.isNotBlank(waybill.getDeliveryCustomerCode())) {
                CustomerEntity senderCustomerEntity = customerDao.queryCustStateByCode(waybill
                        .getDeliveryCustomerCode());
                if (null != senderCustomerEntity) {
                    // 如果发货客户RECEIVER_SMS属性为"两者都停发"时,短信停发 219396-chengdaolin 20150812
                    if (SignConstants.SMS_STOP_RECIEVER_BOTH.equals(senderCustomerEntity.getReceiverSms())) {
                        return true;
                    } else if (SignConstants.SMS_STOP_RECIEVER.equals(senderCustomerEntity.getReceiverSms())) {
                        // 如果发货客户RECEIVER_SMS属性为"客户的收件人短信停发",短信停发 219396-chengdaolin 20150812
                        return true;
                    }
                }
            }
            if (StringUtils.isNotBlank(waybill.getReceiveCustomerCode())) {
                CustomerEntity recieverCustomerEntity = customerDao.queryCustStateByCode(waybill
                        .getReceiveCustomerCode());
                // 如果收货客户RECEIVER_SMS属性为"两者都停发"时,短信停发 219396-chengdaolin 20150812
                if (null != recieverCustomerEntity) {
                    if (SignConstants.SMS_STOP_RECIEVER_BOTH.equals(recieverCustomerEntity.getReceiverSms())) {
                        return true;
                    } else if (SignConstants.STOP_MESSAGE_FOR_RECEIPT.equals(recieverCustomerEntity
                            .getReceiverSms())) {
                        // 如果收货客户RECEIVER_SMS属性为"客户作为收件人短信停发"时,短信停发 219396-chengdaolin 20150812
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 封装拿到子母件集合 重新给子件集合赋值为子母件集合 foss-231438-chenjunying
     * 
     * @param params
     * @return
     */
    public TwoInOneWaybillDto queryWaybillRelateByWaybillOrOrderNo(Map<String, Object> params) {
        TwoInOneWaybillDto oneDto = waybillRelateDetailEntityService
                .queryWaybillRelateByWaybillOrOrderNo(params);
        if (FossConstants.YES.equals(oneDto.getIsTwoInOne())) { // 是子母件
            if (null == oneDto.getMainWaybillNo()) {
                return oneDto; // 返回原Dto到外层
            } else {
                List<String> list = new ArrayList<String>(); // 子母件集合
                if (null != oneDto && null != oneDto.getWaybillNoList()) { // 如果子件集合不为空
                    list.addAll(oneDto.getWaybillNoList());// 将子件集合添加新集合进去
                }
                list.add(oneDto.getMainWaybillNo());// 将母件添加到集合中去
                oneDto.setWaybillNoList(list); // 子母件集合--原方法中为子件集合
                return oneDto;
            }
        } else {// 不是子母件，返货原dto
            return oneDto;
        }
    }

    /**
     * @author 231438 查询运单是否有派送中状态，给接送货GUI端使用
     * @param ArriveSheetEntity entity
     */
    @Override
    public List<ArriveSheetEntity> queryArriveSheetByWaybillNo(ArriveSheetEntity entity) {
        // 是否有效
        entity.setActive(FossConstants.YES);
        // 是否作废
        entity.setDestroyed(FossConstants.NO);
        return arriveSheetManngerService.queryArriveSheetByWaybillNo(entity);
    }

    /**
     * /**
     * 
     * @author foss-sunyanfei
     * @date 2015-7-14 上午10:14:13
     * @param employeeService
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setSmsSendLogService(ISMSSendLogService smsSendLogService) {
        this.smsSendLogService = smsSendLogService;
    }

    public void setExpressVehiclesService(IExpressVehiclesService expressVehiclesService) {
        this.expressVehiclesService = expressVehiclesService;
    }

    @Resource
    public void setWaybillExpressDao(IWaybillExpressDao waybillExpressDao) {
        this.waybillExpressDao = waybillExpressDao;
    }

    public void setSerialSignResultService(ISerialSignResultService serialSignResultService) {
        this.serialSignResultService = serialSignResultService;
    }

    public void setRecordErrorWaybillDao(IRecordErrorWaybillDao recordErrorWaybillDao) {
        this.recordErrorWaybillDao = recordErrorWaybillDao;
    }

    /**
     * add by 353654 董杨炀 DN201608260005 FOSS回传派送拉回原因到OMS的接口实现
     */
    public void updatePullBackStatus(PullbackRenewalDto dto) {
        if (null != dto && null != dto.getEntity()) {
            LOGGER.info("派送拉回，FOSS回传OMS开始");
            // 更新CRM订单
            CrmModifyInfoDto crmModifyInfoDto = new CrmModifyInfoDto();
            // 订单号
            crmModifyInfoDto.setOrderNumber(dto.getOrderNo());
            // 操作人编码
            crmModifyInfoDto.setOprateUserNum(dto.getCurrentInfo().getEmpCode());
            // 操作人组织code
            crmModifyInfoDto.setOprateDeptCode(dto.getCurrentInfo().getDept().getUnifiedCode());
            // 设置货物拉回状态
            crmModifyInfoDto.setGoodsStatus(ArriveSheetConstants.SITUATION_GOODS_BACK);
            // 先将备注字段统一转成中文
            String valueName = DictUtil.rendererSubmitToDisplay(dto.getEntity().getSignNote(),
                    DictionaryConstants.PKP_PULLBACK_REASON);
            // 获取对应得code值
            String valueCode = DictUtil.rendererDisplayToSubmit(valueName,
                    DictionaryConstants.PKP_PULLBACK_REASON);
            // 获取表记录设置拓展字段异常编码
            DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueDao
                    .queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PKP_PULLBACK_REASON,
                            valueCode);
            if (dataDictionaryValueEntity != null) {
                crmModifyInfoDto.setBackInfo(dataDictionaryValueEntity.getExtAttribute1());
            } else {
                LOGGER.error("根据code获取数据字典实体失败");
            }
            // 运单号
            crmModifyInfoDto.setWaybillNumber(dto.getEntity().getWaybillNo());
            // 调用订单修改接口
            crmOrderJMSService.sendModifyOrder(crmModifyInfoDto);
            LOGGER.info("派送拉回，FOSS回传OMS完成");
        } else {
            LOGGER.error("FOSS回传原因到OMS,调用updatePullBackStatus方法传递参数不合法");
        }
    }
}