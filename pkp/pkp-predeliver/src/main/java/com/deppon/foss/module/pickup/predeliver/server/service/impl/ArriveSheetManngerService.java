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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/service/impl/ArriveSheetManngerService.java
 * 
 * FILE NAME        	: ArriveSheetManngerService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 *****************************************************************************
 *修订记录 
 日期 	修订内容 	修订人员 	版本号 
 2012-05-22 	初版	邓亭亭	V0.1
 2012-05-31 	添加整车打印规则SR6和数据元素“到达类型”	林文升	V0.1
 2012-06-07	内部评审修订	邓亭亭	V0.9

 2012-09-26	到达联编号确定	  邓亭亭	V0.9

 2012-10-12
 参照RC-509修订 到达联广告语。	邓亭亭	
 2013-2-27	根据ISSUE-1679修订	王辉	
 2013-3-13	根据ISSUE-1878修订	王辉	




 1.	
 SUC-530-打印到达联
 1.1	
 相关业务用例
 BUC_FOSS_5.50.10_523 打印达到联
 1.2	
 用例描述
 营业员通过客户提供的运单号或其他信息查询到运单，打印到达联为客户办理提货手续。
 1.3	
 用例条件
 条件类型	描述	引用系统用例
 前置条件
 1、
 出发营业部已经开单成功，且到达部门录入到达	
 后置条件	验货出库	
 1.4	
 操作用户角色
 操作用户	描述
 营业员	
 1.
 根据运单号或其他信息查询运单信息 
 2．
 选择一条或多条运单记录根据板式打印到达联
 1.5
 界面要求
 1.5.1
 表现方式
 WEB界面

 1.5.2	
 界面原型
 查询打印界面：

 打印模板（激光版）

 注：激光版到达部门客户联德邦logo下面黄色的提货单 单号删除，
 在此处添加打印到达联条码，将原到达部门客户联中的始发站、
 目的站、发货人、收货人、货物品名、包装、件数、代收货款、
 保价声明价值这些信息下移

 打印模板（普通版）


 香港版



 1.5.3
 状态图

 （到达联状态图）


 1.5.4	
 界面描述
 查询打印界面：
 主要由四部分组成：查询条件区域、查询结果显示区域、运单详细信息区域，操作区域。
 界面初始化时默认显示：查询结果区域显示到达部门为本部门且未签收的货物。运单详细信息区域隐藏。
 1.
 查询条件区域：运单号、交接单号、提货方式、收货人的姓名、手机/固定电话号码、预派送单号。
 a)	
 运单号：运单编号。
 b)
 交接单号：交接单号。
 c)
 提货方式：提货方式包括 （全部、自提、送货）三种，默认为全部。
 d)
 收货人姓名：运单开单中的收货人姓名。
 e)
 手机号码：收货人的手机号码。
 f)
 固定电话：收货人的固定电话号码。
 g)
 预派送单号：预排单所产生的派送单号
 h)
 查询：点击查询 将结果显示在左边结果显示区域中。

 2.	
 查询结果区域：
 显示查询结果：
 显示运单号、是否受理、操作列是否打印。
 3.	
 运单详细信息区域：
 如界面所示显示运单的详细信息。	
 4.	
 操作区域
 选中所有：点击“选中所有”勾选所有是否打印列。
 取消选中：点击“取消选中”取消勾选所有打印列。
 预览：点击“预览”预览所有选中运单。
 打印：点击“打印”打印所有选中运单
 1.6	
 操作步骤
 基本步骤写正常操作过程
 如果是后台批处理用例，此处写逻辑
 序号	基本步骤	相关数据	补充步骤
 1	
 点击“查询”		系统显示到达部门为“本部门”且签收状态为“未签收”的货物。业务规则参照SR1、SR2、SR3
 2	
 选中某条运单记录，单击	运单记录列表	系统将该运单详细信息显示在运单详细信息区域。业务规则参照SR5
 3	
 点击“预览”	运单详细信息	预览所有选中“是否打印”列的运单。
 4	
 点击“打印”	运单详细信息	系统弹出模式窗口，选择打印模板后点击“确定”后打印并生成一条到达联，生成到达联数据元素参照“到达联统一管理”。业务规则参照SR4、SR6、SR7、SR8、SR9、SR10、SR11、SR12

 扩展事件写非典型或异常操作过程
 序号	扩展事件	相关数据	备注
 1a	输入条件点击查询	查询条件	系统查询并显示符合条件的运单列表。业务规则参照SR1、SR2、SR3
 1b	点击查询	查询条件	若未查询到结果则 在结果显示区域提示“无此单，或此单已签收！”
 3a	点击“预览”若无勾选打印列		提示：“请选择预览的运单信息！”
 4a	点击“选中所有”	运单记录列表	系统勾选所有 显示结果区域“是否打印”列。
 4b	点击“取消所有”	运单记录列表	系统取消勾选所有 显示结果区域“是否打印”列。
 4c	点击“打印”若无勾选打印列		则提示“请选择打印的运单信息！”


 1.7	
 业务规则
 序号	描述
 SR1	1、
 只有按单号查询 才能查到非本部门未签收的货物。否则只能查到本部门未签收的货物。
 2、
 查询到的货物按照提货方式（自提在前、上门派送在后）、运单分组显示。
 SR2
 查询时：1、若只输入了收货人姓名、手机号、电话号码查询中其中一项（对于此情况，只能查询到达部门为本部门且未签收的货物）。支持按姓名模糊查询。
 2.
 若输入了“单号”或“交接单号”此情况营业员可以查询到非本部门且未签收的运单并打印。查询时不能单独按照“提货方式”进行查询，
 必须与运单号或交接单号组合查询。其中条件 优先级：单号>交接单号>收货人信息。
 SR3	
 查询结果区域结果显示：
 a)	
 查询出来的运单库存件数与开单不一致，用紫色标识。
 b)	
 查询出来的运单非本部门库存运单，用 标识。
 c)	
 查询出来的运单有未受理更改，用 标识, ，打印时提示:“此单需受理出发更改后，方可打印！”
 d)	
 查询出来的运单含签收单用 标识。
 e)	
 该单已打印到达联的用 标识。
 其中只有操作列是否打印可以修改。其他项都为只读。
 SR4	1、
 在提交到达联打印时，若此运单有出发更改未受理，则提醒:“此单未受理，受理后方可打印。”。
 在打印完到达联时 系统后台自动记录日志，日志内容包括运单号、
 是否打印、目的地、操作人编号、操作人、操作时间、预派送单号等。
 2、	
 打印到达联后生成一条到达联记录，数据元素参照“SUC-794 修改_作废_激活到达联”。
 到达联编号生成规则：（大写字母D +运单号+3位数字实别）后三位数字从1开始自增。
 到达联生成时校验到达联编号的唯一性。
 SR5
 运单详细信息区域所有信息都不可修改。
 SR6	
 打印整车运单时,如果是出发部门打印到达联且到达类型是“到达营业部”那么不打印收货人信息。
 SR7	
 到达联能够打印出通知的内容包括计划送货时间、备注内容。

 SR8	
 1、
 如是出发更改且已受理的运单如果付款方式为到付则：到达联打印出更改受理后的运单相关信息，
 并且在普通版左侧中打印出出发更改单的内容，打印规则参照SR9第6项。附：目前激光版内无打印更改信息这一栏。
 SR9	3、
 打印规则：
 4、	
 所有带单位的数字信息出模板中已带单位否则在打印时必须带上单位，金额：￥、体积：m2、重量：kg。
 5、	
 运单中所有需要填充的信息字体都为宋体小四加粗。
 6、	
 “其他服务费用”一栏打印顺序依 次为保价费、综合服务费、燃油附加费、代收手续费，其他不限定顺序。
 7、	
 “其他服务费用”打印运单信息中以下框架内的所有内容，目前为7个空格。当其他费用在7项以内时，一个费用项对应一个格，
 当其他费用超过7项时，费用项目的间距缩小，不必按格打印。（注：仓储费作为小票处理。）
 8、
 为打印后美观内容过长的信息可根据模板宽度预留N个汉字后换行。
 9、	
 普通版更改内容打印规则：“更改内容：”+更改内容+时间，多项用分号隔开。
 10、	
 费用合计显示：上行显示：现付金额（大写） 下行显示：付款方式+金额+已付金额（大写）如模板所示。
 SR10
 11、
 在提交到达联打印后，派送处理前发起出发更改，更改单受理时，从右下角弹出提示重新打印此到达联。
 SR11	
 12、
 最终打印的到达联上的件数要与客户实际提货件数一致。如：
 13、	
 1、 到达联上件数不等于实际提货件数。则需线上标记更改到达联件数
 14、	
 更改后系统提示“到达联更改，是否重新打印？”如重新打印则作废以前到达联，如不重新打印，则线下手动修改纸质版到达联的件数。
 SR12	
 15、
 分批配送如第一次已结清，第二批费用合计显示为0 ，到达联模板上需打印已送多少件，还剩多少件。
 SR13
 16、
 到达联模版上的广告标语设置内容来源请参照：“SUC-178-新增_修改_作废_查询单据广告语”用例
 SR14	
 17、	
 增加支付状态类型，可以筛选出“网上支付”未付款订单；
 18、
 对“网上支付”未付款的运单，在查询处理之后进行颜色标识。
 19、	
 到达联打印时增加制单人、制单人时间。
 20、	
 对于网上支付的运单，调用财务接口对运单识别此运单是否已经付款；对于未付款的运单，标注颜色。

 1.8	
 数据元素
 1.8.1
 查询条件
 字段名称	说明	输入限制	输入项提示文本	长度	是否必填	备注
 运单号	运单编号	文本		40	否	
 交接单号	交接单编号	文本		40	否	
 提货方式	包括 （全部、自提、送货）三种	选择		20	否	
 收货人姓名	收货人姓名	文本		20	否	
 电话：	收货人电话	文本		20	否	
 固定电话	收货人固定电话	文本		20	否	

 1.8.2
 输出信息
 字段名称	说明	输入限制	长度	是否必填	可编辑
 角色	备注
 始发站	出发城市	输出文本	20	是	无	
 运输类型	精准卡航、精准城运、精准汽运等	输出文本	4	是	无	
 收货人	收货人名称	输出文本	20	是	无	
 目的站	目的城市	输出文本	20	是	无	
 电话/手机	收货人电话	输出文本	7-15	是	无	
 地址	收货人地址	输出文本	100	是	无	
 件数	货物件数	输出文本	9	是	无	
 包装	货物的包装类型，例如：纸质包装	输出文本	30	是	无	
 体积	货物的体积	输出文本	9	是	无	
 重量	货物的重量	输出文本	9	是	无	
 运费	货物根据计费方式，按体积或重量乘以价格得到的运费	输出文本	9	是	无	
 费率	即承运价格，例如每公斤1.25元	输出文本	4	是	无	
 货物品名	货物品名	输出文本	100	是	无	
 保险声明价值	货物保险声明价值	输出文本	10	是	无	
 现付总计	开单时现金付款金额	输出文本	9	是	无	
 交货方式	客户为自提或送货等	输出文本	10	是	无	
 到付总计	由到达部门收取的费用	输出文本	9	是	无	
 付款方式	付款方式为现金、到付、银行卡等	输出文本	10	是	无	
 代收货款	客户委托公司收取的货物钱款	输出文本	10	是	无	
 航班/日期	货物运输的飞机航班及日期	输出文本	20	是	无	
 提货网点/电话	即到达部门名称、地址及电话	输出文本	100	是	无	
 发货网点/电话	即出发部门名称、地址及电话	输出文本	100	是	无	
 制单人/时间	打印到达联的营业员名称及打印时间	输出文本	20	是	无	
 其他储运事项	客户需要特别注意的事项等	输出文本	1000	是	无	
 广告栏	公司的一些宣传活动广告	输出文本	1000	是	无	
 到货类型	到达营业部、到达客户	输出文本	1000	否	无	
 其他服务费用	其他服务费用包括：燃油附加费，保价费，代收费用，综合服务费等	输出文本	100	是	无	

 1.9	
 非功能性需求
 使用量	平均70000次/天
 2012年全网估计用户数	4000-7000用户
 响应要求（如果与全系统要求 不一致的话）	一般系统要求
 使用时间段	Am 8:00-PM 20:00
 高峰使用时间段	AM 8:00-AM 12:00  PM 14:00- PM 18:00


 1.10	
 接口描述：
 接口名称 	对方系统（外部系统或内部其他模块）	接口描述




 *
 **/
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrangeArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetActualFreightDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillAddPropertyDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivesheetDeliverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AutoTaskDTO;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.StorageJobDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ArriveSheetMannerException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IPickupService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignStockJobService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.define.PickupConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.dto.WaybillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICUBCReverseSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCSignOrRevSignResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FOSSSignOrRevSignRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCReverseSignException;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 到达联管理service
 * 
 * @author dp-dengtingting
 * 
 */
public class ArriveSheetManngerService implements IArriveSheetManngerService {
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.predeliver.server.service.impl.ArriveSheetManngerService";
	
	/**
	 * add by 353654 注入CUBC反签收申请。反签收操作
	 */
	private ICUBCReverseSignService cUBCReverseSignService;
	

	public void setcUBCReverseSignService(
			ICUBCReverseSignService cUBCReverseSignService) {
		this.cUBCReverseSignService = cUBCReverseSignService;
	}
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ArriveSheetManngerService.class);
	/**
	 * 常量值3
	 */
	public static final int THREE = 3;
	/**
	 * 常量值0
	 */
	public static final String ZERO = "0";
	
	/**
	 * 到达联开始计数
	 */
	public static final String BENGIN_NUM = "001";
	/**
	 * 到达联DAO
	 */
	private IArrivesheetDao arrivesheetDao;
	/**
	 * 
	 * actualFreightDao
	 */
	private IActualFreightDao actualFreightDao;
	/**
	 * 运单基本信息
	 */
	private IWaybillDao waybillDao;
	/**
	 * 运单管理服务
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * addAdministrativeRegions
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	/**
	 * 运单状态服务接口
	 */
	private IActualFreightService actualFreightService;
	/**
	 * 签收明细接口
	 */
	private ISignDetailService signDetailService;
	/**
	 * 营业部 Service实现
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 运单签收结果service
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * 结算签收Service
	 */
	private ILineSignService lineSignService;
	/**
	 * 根据部门Code获得名称及电话接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 货物的入库、出库及查询库存service
	 */
	private IStockService stockService;
	/**
	 * 运单完结状态操作Service
	 */
	private IWaybillTransactionService waybillTransactionService;
	/**
	 * 计算&调整走货路径类
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	/**
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;
	/**
	 * 运单通知Service
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	/**
	 * 员工服务
	 */
	private IEmployeeService employeeService;
	/**
	 * 签收反签收同步改异步接口
	 */
	private ISignStockJobService signStockJobService;
	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;

	private IArriveSheetManngerService arriveSheetManngerService;

	private INotifyCustomerService notifyCustomerService;
	/**
	 * 部门复杂service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * service层
	 */
	private  IPickupService pickupService;
	
	/**
	 * 子母件服务接口层
	 */
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
	/**
	 * 运单签收结果接口
	 */
	private IWaybillSignResultDao waybillSignResultDao;
	/**
	 * 
	 * 到达联管理模块 根据条件查询到达联
	 * 
	 * @author dp-dengtingting
	 * @date 2012-10-13 上午10:57:15
	 */
	@Transactional
	@Override
	public List<ArriveSheetDto> queryArriveSheet(ArriveSheetDto dto, int start, int limit) {
		return arrivesheetDao.queryArriveSheetData(dto, start, limit);
	}

	private void cleanArriveSheetDto(ArriveSheetDto dto) {
		dto.setArriveSheetStatus(null);
		dto.setCreateBeginTime(null);
		dto.setCreateEndTime(null);
		dto.setCreateUserName(null);
		// 是否有效不清除 wnagfei 2013-4-11 14:20:35
		//		dto.setDestroyed(null);
		dto.setGoodsName(null);
		dto.setIsPrinted(null);
		dto.setReceiveCustomerMobilephone(null);
		dto.setReceiveCustomerName(null);
	}

	/**
	 * 修改到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-17 下午5:49:03
	 * @param arriveSheetEntity waybillNo 运单号 arrivesheetNo 到达联编号
	 *            deliverymanname 提货人名称 identifyType 证件类型 identifyCode 证件号码
	 *            situation 签收情况 arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数
	 *            signNote 签收备注 signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到
	 *            operateTime 签收操作时间 operator 操作人 operatorCode 操作人编码
	 *            operateOrgName 操作部门名称 operateOrgCode 操作部门编码 destroyed 是否作废
	 *            isPrinted 是否打印 printtimes 打印次数 createUserName 创建人
	 *            createUserCode 创建人编码 createOrgName 创建部门 createOrgCode 创建部门编码
	 *            createTime 创建时间 isSentRequired 是否必送货 isNeedInvoice 是否需要发票
	 *            preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #updateArriveSheetData(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Transactional
	@Override
	public int updateArriveSheetData(ArriveSheetEntity arriveSheetEntity) {
		ArriveSheetEntity queryArrivSheetEntity = arrivesheetDao.queryArriveSheetByEntity(arriveSheetEntity);
		arrivesheetDao.updateArriveSheetData(arriveSheetEntity);
		//更新 actualFreight 表的 "生成到达联件数"字段
		ActualFreightEntity actualFreight = new ActualFreightEntity();
		actualFreight.setWaybillNo(arriveSheetEntity.getWaybillNo());
		//已生成件数 = actualFreight原有件数 +（queryArrivSheetEntity修改后件数 -原有件数）
		actualFreight.setGenerateGoodsQty(arriveSheetEntity.getArriveSheetGoodsQty() - queryArrivSheetEntity.getArriveSheetGoodsQty());
		actualFreightDao.updateAddGenerateGoodsQtyByWaybillNo(actualFreight);
		return 0;
	}

	/**
	 * 签收出库---根据条件查询到达联
	 * 
	 * @author foss-meiying
	 * @date 2013-1-30 下午4:59:27
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #queryArriveSheetInfoByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto,
	 *      int, int)
	 */
	@Override
	public List<SignArriveSheetDto> queryArriveSheetInfoByParams(SignDto dto, int start, int limit) {
		return arrivesheetDao.queryArriveSheetInfoByParams(dto, start, limit);
	}

	/**
	 * 签收出库---根据条件分页查询到达联总数
	 * 
	 * @author foss-meiying
	 * @date 2013-1-30 下午4:59:20
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #queryArriveSheetInfoCountByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto)
	 */
	public Long queryArriveSheetInfoCountByParams(SignDto dto) {
		return arrivesheetDao.queryArriveSheetInfoCountByParams(dto);
	}

	/**
	 * 根据条件查询到达联总件数
	 * 
	 * @author dp-dengtingting
	 * @date 2012-10-19 下午2:52:59
	 */
	@Override
	public Long getArriveSheetCount(ArriveSheetDto dto) {
		dto.setWaybillActive(FossConstants.YES);
		dto.setActive(FossConstants.YES);
		//如果运单号或者到达联编号为空的时候只能查询得到本部门的到达联 且优先级 到达联编号>运单号>其他
		if (StringUtils.isNotEmpty(dto.getArrivesheetNo())) {
			dto.setWaybillNo(null);
			cleanArriveSheetDto(dto);
		} else if (StringUtils.isNotEmpty(dto.getWaybillNo())) {
			cleanArriveSheetDto(dto);
		} else {
			dto.setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());
		}
		return arrivesheetDao.getArriveSheetCount(dto);
	}

	/**
	 * 打印到达联 根据运单信息查询可打印到达联的运单信息
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-10-25 上午11:04:05
	 */
	@Override
	public List<ArriveSheetWaybillDto> queryWaybillDetailByWaybill(ArriveSheetWaybillDto dto, CurrentInfo currentInfo) {
		//條件更改當狀態
		String[] waybillRfcStatus = new String[] { WaybillRfcConstants.PRE_AUDIT, WaybillRfcConstants.PRE_ACCECPT };
		// 更改单状态
		List<String> waybillRfcStatusList = new ArrayList<String>(Arrays.asList(waybillRfcStatus));
		//追加條件
		dto.setWaybillRfcStatus(waybillRfcStatusList);
		dto.setActive(FossConstants.YES);
		dto.setDestroyed(FossConstants.NO);
		// 关联已生成、派送中的到达联
		List<String> arriveSheetStatus = new ArrayList<String>();
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_GENERATE);
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_DELIVER);
		dto.setArriveSheetStatus(arriveSheetStatus);
		List<ArriveSheetWaybillDto> lists = new ArrayList<ArriveSheetWaybillDto>();
		//按照运单号查询
		if (StringUtil.isNotEmpty(dto.getWaybillNo())) {
			// 解析运单号为列表
			dto.setArrayWaybillNos(dto.getWaybillNo().split("\\n"));
			if (dto.getArrayWaybillNos().length > 0) {
				// 添加纠正到达件数、到达时间的方法
				this.updateWaybillArriveInfo(dto.getArrayWaybillNos());
				// 查询列表
				lists = arrivesheetDao.queryWaybillDetailByWaybill(dto);
			}
		} else if (StringUtil.isNotEmpty(dto.getHandoverNo())) { //按交接单号查询
			lists = arrivesheetDao.queryWaybillDetailByHandoverNo(dto);
		} else if (StringUtils.isNotEmpty(dto.getDeliverbillId())) { //按预派送单号查询
			lists = arrivesheetDao.queryWaybillDetailByDeliverbillId(dto);
		} else if (StringUtils.isNotEmpty(dto.getReceiveCustomerName()) || StringUtils.isNotEmpty(dto.getReceiveCustomerPhone())) {
			// 收货人姓名、手机验证
			// TODO 这里只判断了CustomerPhone，没有判断CustomerMobilePhone，应该是有问题的
			String currOrgCode = FossUserContextHelper.getOrgCode();
			// 登录人员所在部门
			dto.setLastLoadOrgCode(currOrgCode);
			List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(currOrgCode);
			if (CollectionUtils.isNotEmpty(list)) {
				List<String> ld=new ArrayList<String>();
				ld.add(list.get(1));
				ld.add(list.get(2));
				dto.setEndStockOrgCode(list.get(0)); //最终库存部门编码
				dto.setGoodsAreaCodes(ld);// 获取库区
				}
			lists = arrivesheetDao.queryWaybillDetailByCustomer(dto);
		} else if (dto.getInStockTimeFrom() != null && dto.getInStockTimeTo() != null) {
			// 入库时间
			String currOrgCode = FossUserContextHelper.getOrgCode();
			// 登录人员所在部门
			dto.setLastLoadOrgCode(currOrgCode);
			List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(currOrgCode);
			if (CollectionUtils.isNotEmpty(list)) {
				List<String> ld=new ArrayList<String>();
				ld.add(list.get(1));
				ld.add(list.get(2));
				dto.setEndStockOrgCode(list.get(0)); //最终库存部门编码
				dto.setGoodsAreaCodes(ld);// 获取库区
			}
			if (StringUtil.equals(dto.getIsPrinted(), FossConstants.YES)) {
				dto.setIsPrintedStatus(NumberConstants.ONE);
			} else if (StringUtil.equals(dto.getIsPrinted(), FossConstants.NO)) {
				dto.setIsPrintedStatus(NumberConstants.ZERO);
			} 
			dto.setIsPrinted(null);
			lists = arrivesheetDao.queryWaybillDetailByInStockTime(dto);
		} else if (dto.getArriveStartTime() != null && dto.getArriveEndTime() != null) {
			// 根据到达时间查
			String currOrgCode = FossUserContextHelper.getOrgCode();
			dto.setLastLoadOrgCode(currOrgCode);
			if (StringUtil.equals(dto.getIsPrinted(), FossConstants.YES)) {
				dto.setIsPrintedStatus(NumberConstants.ONE);
			} else if (StringUtil.equals(dto.getIsPrinted(), FossConstants.NO)) {
				dto.setIsPrintedStatus(NumberConstants.ZERO);
			} 
			dto.setIsPrinted(null);
			lists = arrivesheetDao.queryWaybillDetailByArriveTime(dto);
		}else {
			// 未输入任何条件，不做查询，直接返回
			return lists;
		}
		//判断该运单对应的到达联是否已打印
		for (ArriveSheetWaybillDto arriveSheetWaybillDto : lists) {
//			ArriveSheetEntity entity = new ArriveSheetEntity();
//			entity.setWaybillNo(arriveSheetWaybillDto.getWaybillNo());
//			entity.setIsPrinted(FossConstants.YES);
//			entity.setActive(FossConstants.YES);
//			int num = arrivesheetDao.getCountArriveSheetByWaybillNo(entity);
//			if (num > 0) {
//				arriveSheetWaybillDto.setIsPrinted(FossConstants.YES);
//			} else {
//				arriveSheetWaybillDto.setIsPrinted(FossConstants.NO);
//			}
			//是否为网上支付
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(arriveSheetWaybillDto.getPaidMethod())) {
				//根据运单号，查询开单付款方式为网上支付的应收单的未核销金额
				WaybillReceivableDto receivableDto = billReceivableService.queryReceivableAmountByWaybillNO(arriveSheetWaybillDto.getWaybillNo());
				//没有数据的话，返回为空,没有未结清欠款,yes为结清货款，no为未结清货款
				if (receivableDto == null) {
					arriveSheetWaybillDto.setIsPay(FossConstants.YES);
				} else {
					arriveSheetWaybillDto.setIsPay(FossConstants.NO);
				}
			}
			//非本部门库存
			//获得ActualFreightEntity
			ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(arriveSheetWaybillDto.getWaybillNo());
			//最终库存部门编码
			String endStockOrgCode = actualFreightEntity.getEndStockOrgCode();
			//获得登入信息
			List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(currentInfo.getCurrentDeptCode());
			if (CollectionUtils.isNotEmpty(list) && StringUtils.isNotEmpty(list.get(0))) {
				if (list.get(0).equals(endStockOrgCode)) {
					arriveSheetWaybillDto.setIsSelfFlg(FossConstants.YES);
				} else {
					arriveSheetWaybillDto.setIsSelfFlg(FossConstants.NO);
				}

			}
		}
		
		if (StringUtil.isNotBlank(dto.getWaybillNo()))  {
			HashMap<String , ArriveSheetWaybillDto> map = new HashMap<String, ArriveSheetWaybillDto>();
			for (int i = 0; i < lists.size(); i++) {
				map.put(lists.get(i).getWaybillNo(), lists.get(i));
			}
			List<ArriveSheetWaybillDto> arriveSheetWaybillDtos = new ArrayList<ArriveSheetWaybillDto>();
			for (String waybill : dto.getArrayWaybillNos()) {
				if (map.containsKey(waybill)) {
					arriveSheetWaybillDtos.add(map.get(waybill));
				}
			}
			return arriveSheetWaybillDtos;
		}
		return lists;

	}

	/**
	 * 通过ID将到达联状态改为“派送中”
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-10 下午2:10:31
	 */
	private int upArriveSheetByArrivesheetNo(String id) {
		ArriveSheetEntity entity = new ArriveSheetEntity();
		entity.setId(id);
		entity.setStatus(ArriveSheetConstants.STATUS_DELIVER);
		return arrivesheetDao.updateByPrimaryKeySelective(entity);
	}

	/**
	 * 根据运单号、已排单数校验生成到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-12 下午8:44:06
	 */
	@Override
	public ArrangeArriveSheetDto checkGenerateArriveSheet(String waybillNo, Integer arrangeGoodsQty) {
		MutexElement mutexElement = new MutexElement(waybillNo, "运单号", MutexElementType.ARRIVE_SHEET_CREATE_WAYBILLNO);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		//若未上锁
		if (!isLocked) {
			//抛出派送单异常
			throw new ArriveSheetMannerException(ArriveSheetMannerException.ARRIVE_SHEET_CREATING);
		}
		ArrangeArriveSheetDto arrangeArriveSheetDto = new ArrangeArriveSheetDto();
		arrangeArriveSheetDto.setArrangeGoodsQty(arrangeGoodsQty);
		arrangeArriveSheetDto.setWaybillNo(waybillNo);
		Integer printNum = 0; //可打印件数
		String arrivesheetNo = null;
		ActualFreightEntity actualFreightEntity = actualFreightDao.queryByWaybillNo(waybillNo);
		//计算可打印件数
		if (actualFreightEntity != null && actualFreightEntity.getGoodsQty() != null && actualFreightEntity.getGenerateGoodsQty() != null) {
			// 为了最大程度在确认派送单时，将到达联与派送单进行绑定，可打印件数修改为运单开单件数 - 已生成到达联件数
			printNum = actualFreightEntity.getGoodsQty() - actualFreightEntity.getGenerateGoodsQty();
		}
		//根据运单号 校验是否有效且为“生成”状态的到达联
		List<ArriveSheetEntity> list = arrivesheetDao.queryArriveSheetByWaybillNo(new ArriveSheetEntity(waybillNo, ArriveSheetConstants.STATUS_GENERATE, FossConstants.YES, FossConstants.NO));
		//是否有生成状态的到达联
		ArriveSheetEntity existedArriveSheetEntity = null;
		//生成状态下的到达联件数
		int existedArriveSheetGoodsQty = 0;
		// 根据到达联编号 判断已有“生成”状态的到达联
		if (CollectionUtils.isNotEmpty(list)) {
			existedArriveSheetEntity = list.get(0);
			existedArriveSheetGoodsQty = existedArriveSheetEntity.getArriveSheetGoodsQty();
		}
		if (printNum > 0) {
			// 如果已有“生成”状态的到达联
			if (existedArriveSheetEntity != null) {
				//如果排单件数等于原到达联件数
				if (existedArriveSheetGoodsQty == arrangeGoodsQty) {
					arrangeArriveSheetDto.setArriveSheetNo(existedArriveSheetEntity.getArrivesheetNo());
					arrangeArriveSheetDto.setOldArriveSheetGoodsQty(existedArriveSheetGoodsQty);
					arrangeArriveSheetDto.setNewArriveSheetGoodsQty(existedArriveSheetGoodsQty);
					//通过到达联编号将到达联状态改为“派送中”
					this.upArriveSheetByArrivesheetNo(existedArriveSheetEntity.getId());
					businessLockService.unlock(mutexElement);
					return arrangeArriveSheetDto;
				}
				//如果存在“生成”状态的到达联 则 可排单数 = printNum + 该到达联件数 
				Integer nums = printNum + existedArriveSheetGoodsQty;

				//若可排单件数大于排单件数 此时生成到达联的件数 = 排单件数，否则生成到达联的件数 = 可排单件数
				existedArriveSheetEntity.setArriveSheetGoodsQty(nums >= arrangeGoodsQty ? arrangeGoodsQty : nums);
				existedArriveSheetEntity.setWaybillNo(waybillNo);
				existedArriveSheetEntity.setStatus(ArriveSheetConstants.STATUS_DELIVER);
				//到达联编号不变作废之前到达联  并重新生成到达联。
				arrivesheetNo = this.copyAndUpdateArriveSheet(existedArriveSheetEntity);

				//更新 actualFreight 表的 "生成到达联件数"字段
				ActualFreightEntity actualFreight = new ActualFreightEntity();
				actualFreight.setWaybillNo(waybillNo);
				//已生成件数 = actualFreight原有件数 +（queryArrivSheetEntity修改后件数 -原有件数）
				actualFreight.setGenerateGoodsQty(existedArriveSheetEntity.getArriveSheetGoodsQty() - existedArriveSheetGoodsQty);
				// 与偕旭、赵斌讨论，派送时，不更新已派单件数 2013-6-19 21:34:15
				// actualFreightEntity.setArrangeGoodsQty(arrangeGoodsQty);
				actualFreightDao.updateAddGenerateGoodsQtyByWaybillNo(actualFreight);
				//返回设置参数
				arrangeArriveSheetDto.setArriveSheetNo(arrivesheetNo);
				arrangeArriveSheetDto.setOldArriveSheetGoodsQty(existedArriveSheetGoodsQty);
				arrangeArriveSheetDto.setNewArriveSheetGoodsQty(existedArriveSheetEntity.getArriveSheetGoodsQty());
			} else {
				// 无“生成”状态的到达联
				//直接 生成到达联
				//此时生成到达联的件数 = 排单件数
				ArriveSheetEntity generateArriveSheet = new ArriveSheetEntity();
				generateArriveSheet.setArriveSheetGoodsQty(printNum >= arrangeGoodsQty ? arrangeGoodsQty : printNum);
				generateArriveSheet.setWaybillNo(waybillNo);
				generateArriveSheet.setStatus(ArriveSheetConstants.STATUS_DELIVER);
				arrivesheetNo = this.generateArriveSheet(generateArriveSheet);
				if (StringUtil.isEmpty(arrivesheetNo)) {
					LOGGER.info(waybillNo + "无法生成0件的到达联。");
					//设置返回参数
					arrangeArriveSheetDto.setOldArriveSheetGoodsQty(0);
					arrangeArriveSheetDto.setNewArriveSheetGoodsQty(0);
					businessLockService.unlock(mutexElement);
					return arrangeArriveSheetDto;
				}
				//更新 actualFreight 表的 "生成到达联件数"字段
				ActualFreightEntity actualFreight = new ActualFreightEntity();
				actualFreight.setWaybillNo(waybillNo);
				actualFreight.setGenerateGoodsQty(generateArriveSheet.getArriveSheetGoodsQty());
				// 赵斌要求添加 2013-5-3 10:33:13 “排单件数” = “排单件数” - “作废到达联的件数”
				// 与偕旭、赵斌讨论，派送时，不更新已派单件数 2013-6-19 21:34:15
				// actualFreightEntity.setArrangeGoodsQty(arrangeGoodsQty);
				actualFreightDao.updateAddGenerateGoodsQtyByWaybillNo(actualFreight);
				//设置返回参数
				arrangeArriveSheetDto.setArriveSheetNo(arrivesheetNo);
				arrangeArriveSheetDto.setOldArriveSheetGoodsQty(0);
				arrangeArriveSheetDto.setNewArriveSheetGoodsQty(generateArriveSheet.getArriveSheetGoodsQty());
			}
		} else {
			//可打印件数为0 
			// 已有“生成”状态的到达联
			if (existedArriveSheetEntity != null && existedArriveSheetEntity.getArriveSheetGoodsQty().equals(arrangeGoodsQty)) {
				//更新到达联为已排单状态
				this.upArriveSheetByArrivesheetNo(existedArriveSheetEntity.getId());
				//设置返回参数
				arrangeArriveSheetDto.setArriveSheetNo(existedArriveSheetEntity.getArrivesheetNo());
				arrangeArriveSheetDto.setOldArriveSheetGoodsQty(existedArriveSheetGoodsQty);
				arrangeArriveSheetDto.setNewArriveSheetGoodsQty(existedArriveSheetGoodsQty);
			} else if(existedArriveSheetEntity != null && !existedArriveSheetEntity.getArriveSheetGoodsQty().equals(arrangeGoodsQty)){
				// 若存在生成到达联，则判断件数，正常以排单件数为准生成新的到达联并作废之前的到达联，保留记录
				existedArriveSheetEntity.setArriveSheetGoodsQty(existedArriveSheetGoodsQty >= arrangeGoodsQty ? arrangeGoodsQty : existedArriveSheetGoodsQty);
				existedArriveSheetEntity.setWaybillNo(waybillNo);
				existedArriveSheetEntity.setStatus(ArriveSheetConstants.STATUS_DELIVER);
				//到达联编号不变作废之前到达联  并重新生成到达联。
				arrivesheetNo = this.copyAndUpdateArriveSheet(existedArriveSheetEntity);
				//更新 actualFreight 表的 "生成到达联件数"字段
				ActualFreightEntity actualFreight = new ActualFreightEntity();
				actualFreight.setWaybillNo(waybillNo);
				//已生成件数 = actualFreight原有件数 +（queryArrivSheetEntity修改后件数 -原有件数）
				actualFreight.setGenerateGoodsQty(existedArriveSheetEntity.getArriveSheetGoodsQty() - existedArriveSheetGoodsQty);
				actualFreightDao.updateAddGenerateGoodsQtyByWaybillNo(actualFreight);
				//设置返回参数
				arrangeArriveSheetDto.setArriveSheetNo(existedArriveSheetEntity.getArrivesheetNo());
				arrangeArriveSheetDto.setOldArriveSheetGoodsQty(existedArriveSheetGoodsQty);
				arrangeArriveSheetDto.setNewArriveSheetGoodsQty(existedArriveSheetEntity.getArriveSheetGoodsQty());
			} else {
				//设置返回参数
				arrangeArriveSheetDto.setOldArriveSheetGoodsQty(0);
				arrangeArriveSheetDto.setNewArriveSheetGoodsQty(0);
			}
		}
		businessLockService.unlock(mutexElement);
		return arrangeArriveSheetDto;
	}

	/**
	 * 
	 * 根据运单号校验生成到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-12 下午8:44:06
	 * @param entity waybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname 提货人名称
	 *            identifyType 证件类型 identifyCode 证件号码 situation 签收情况
	 *            arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数 signNote 签收备注
	 *            signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到 operateTime
	 *            签收操作时间 operator 操作人 operatorCode 操作人编码 operateOrgName 操作部门名称
	 *            operateOrgCode 操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes
	 *            打印次数 createUserName 创建人 createUserCode 创建人编码 createOrgName
	 *            创建部门 createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *            isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #checkGenerateArriveSheet(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Override
	@Transactional
	public int checkGenerateArriveSheet(ArriveSheetEntity entity) {
		MutexElement mutexElement = new MutexElement(entity.getWaybillNo(), "运单号", MutexElementType.ARRIVE_SHEET_CREATE_WAYBILLNO);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		//若未上锁
		if (!isLocked) {
			//抛出派送单异常
			throw new ArriveSheetMannerException(ArriveSheetMannerException.ARRIVE_SHEET_CREATING);
		}
		//计算可打印件数
		Integer printNum = 0; //可打印件数
		ActualFreightEntity actualFreightEntity = actualFreightDao.queryByWaybillNo(entity.getWaybillNo());
		if (actualFreightEntity != null && actualFreightEntity.getArriveGoodsQty() != null && actualFreightEntity.getGenerateGoodsQty() != null) {
			printNum = actualFreightEntity.getArriveGoodsQty() - actualFreightEntity.getGenerateGoodsQty();
		}
		//校验是否有效且为“生成”状态的到达联
		List<ArriveSheetEntity> list = arrivesheetDao.queryArriveSheetByWaybillNo(new ArriveSheetEntity(entity.getWaybillNo(), ArriveSheetConstants.STATUS_GENERATE, FossConstants.YES, FossConstants.NO));
		//是否有生成状态的到达联
		ArriveSheetEntity existedArriveSheetEntity = null;
		//生成状态下的到达联件数
		// 根据到达联编号 判断已有“生成”状态的到达联
		if (CollectionUtils.isNotEmpty(list)) {
			existedArriveSheetEntity = list.get(0);
		}
		//可打印件数 大于0
		if (printNum > 0) {
			//有 有效且为“生成”状态的到达联
			// 计算已生成件数
			int generateGoodsQty = actualFreightEntity.getGenerateGoodsQty() + printNum;
			if (generateGoodsQty > actualFreightEntity.getGoodsQty()) {
				generateGoodsQty = actualFreightEntity.getGoodsQty();
			}
			if (existedArriveSheetEntity != null) {
				//存在“生成”状态的到达联 则作废之前到达联并生成新的到达联
				//如果通知客户值不为空 则重新设置
				if (entity.getCreateDate() != null) {
					existedArriveSheetEntity.setCreateDate(entity.getCreateDate());
				}
				if (StringUtils.isNotEmpty(entity.getCreateUserName())) {
					existedArriveSheetEntity.setCreateUserName(entity.getCreateUserName());
				}
				if (StringUtils.isNotEmpty(entity.getCreateUserCode())) {
					existedArriveSheetEntity.setCreateUserCode(entity.getCreateUserCode());
				}
				if (StringUtils.isNotEmpty(entity.getCreateOrgName())) {
					existedArriveSheetEntity.setCreateOrgName(entity.getCreateOrgName());
				}
				if (StringUtils.isNotEmpty(entity.getCreateOrgCode())) {
					existedArriveSheetEntity.setCreateOrgCode(entity.getCreateOrgCode());
				}
				// 是否必送货
				if (StringUtils.isNotEmpty(entity.getIsSentRequired())) {
					existedArriveSheetEntity.setIsSentRequired(entity.getIsSentRequired());
				}
				// 是否需要发票
				if (StringUtils.isNotEmpty(entity.getIsNeedInvoice())) {
					existedArriveSheetEntity.setIsNeedInvoice(entity.getIsNeedInvoice());
				}
				// 提前通知内容
				if (StringUtils.isNotEmpty(entity.getPreNoticeContent())) {
					existedArriveSheetEntity.setPreNoticeContent(entity.getPreNoticeContent());
				}
				// 送货要求
				if (StringUtils.isNotEmpty(entity.getDeliverRequire())) {
					existedArriveSheetEntity.setDeliverRequire(entity.getDeliverRequire());
				}
				//此时生成到达联的件数 = 可打印件数 + 作废的件数
				if ((printNum + list.get(0).getArriveSheetGoodsQty()) > actualFreightEntity.getGoodsQty()) {
					existedArriveSheetEntity.setArriveSheetGoodsQty(actualFreightEntity.getGoodsQty());
				} else {
					existedArriveSheetEntity.setArriveSheetGoodsQty(printNum + list.get(0).getArriveSheetGoodsQty());
				}
				this.copyAndUpdateArriveSheet(existedArriveSheetEntity);

				//更新 actualFreight 表的 "生成到达联件数"字段
				ActualFreightEntity actualFreight = new ActualFreightEntity();
				actualFreight.setWaybillNo(entity.getWaybillNo());
				actualFreight.setGenerateGoodsQty(generateGoodsQty);
				actualFreightDao.updateGenerateGoodsQtyByWaybillNo(actualFreight);
			} else {
				//否则直接 生成到达联
				addNewArriveSheet(printNum, actualFreightEntity, entity, generateGoodsQty);
			}
		} else if(StringUtil.equals(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY, entity.getSource()) && existedArriveSheetEntity == null){
			// 是通知客户打印 并且没有生成状态的到达联 
			// 可打印件数= 开单件数 - 已生成件数
			addNewArriveSheet(actualFreightEntity.getGoodsQty() - actualFreightEntity.getGenerateGoodsQty(), actualFreightEntity, entity, actualFreightEntity.getGoodsQty());
		}
		businessLockService.unlock(mutexElement);
		return 0;
	}
	
	/**
	 * 
	 * 生成新的到达联
	 * 
	 * @param printNum
	 * @param actualFreightEntity
	 * @param entity
	 * @param generateGoodsQty
	 * @author ibm-wangfei
	 * @date Jun 26, 2013 5:08:31 PM
	 */
	private void addNewArriveSheet(Integer printNum, ActualFreightEntity actualFreightEntity, ArriveSheetEntity entity, int generateGoodsQty) {
		// 对重复到达联，再次添加验证
		//校验是否有效且为“生成”状态的到达联
		List<ArriveSheetEntity> list = arrivesheetDao.queryArriveSheetByWaybillNo(new ArriveSheetEntity(entity.getWaybillNo(), ArriveSheetConstants.STATUS_GENERATE, FossConstants.YES, FossConstants.NO));
		if(CollectionUtils.isNotEmpty(list)) {
			return;
		}
		
		entity.setArriveSheetGoodsQty(printNum);
		//到达提货人
		entity.setDeliverymanName(actualFreightEntity.getDeliverymanName());
		//证件类型
		entity.setIdentifyType(actualFreightEntity.getIdentifyType());
		//证件号码
		entity.setIdentifyCode(actualFreightEntity.getIdentifyCode());
		//生成到达联
		String arrivesheetNo = this.generateArriveSheet(entity);
		if (StringUtil.isEmpty(arrivesheetNo)) {
			LOGGER.info(entity.getWaybillNo() + "无法生成0件的到达联。");
			return;
		}
		//更新 actualFreight 表的 "生成到达联件数"字段 
		ActualFreightEntity actualFreight = new ActualFreightEntity();
		actualFreight.setWaybillNo(entity.getWaybillNo());
		actualFreight.setGenerateGoodsQty(generateGoodsQty);
		actualFreightDao.updateGenerateGoodsQtyByWaybillNo(actualFreight);
	}

	/**
	 * 
	 * <p>
	 * 更新到达联,通过运单号<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-8
	 * @param waybillNo
	 * @param deliverymanName
	 * @param identifyType
	 * @param identifyCode void
	 */
	@Override
	public void updateArriveSheetEntityByWaybillNo(String waybillNo, String deliverymanName, String identifyType, String identifyCode) {

		ArriveSheetEntity entity = new ArriveSheetEntity();
		//到达提货人
		entity.setDeliverymanName(deliverymanName);
		//证件类型
		entity.setIdentifyType(identifyType);
		//证件号码
		entity.setIdentifyCode(identifyCode);
		//生成状态
		entity.setStatus(ArriveSheetConstants.STATUS_GENERATE);
		//运单号
		entity.setWaybillNo(waybillNo);
		//更新到达联
		arrivesheetDao.updateArriveSheetByWaybillNo(entity);
	}

	/**
	 * 
	 * <p>
	 * 更新到达联的信息更具运单号，更新 DELIVER_REQUIRE送货要求， IS_SENT_REQUIRED是否必须送货，
	 * IS_NEED_INVOICE是否需要发票， PRE_NOTICE_CONTENT提前通知内容 运单号<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-12
	 * @param arriveSheetEntity waybillNo 运单号 arrivesheetNo 到达联编号
	 *            deliverymanname 提货人名称 identifyType 证件类型 identifyCode 证件号码
	 *            situation 签收情况 arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数
	 *            signNote 签收备注 signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到
	 *            operateTime 签收操作时间 operator 操作人 operatorCode 操作人编码
	 *            operateOrgName 操作部门名称 operateOrgCode 操作部门编码 destroyed 是否作废
	 *            isPrinted 是否打印 printtimes 打印次数 createUserName 创建人
	 *            createUserCode 创建人编码 createOrgName 创建部门 createOrgCode 创建部门编码
	 *            createTime 创建时间 isSentRequired 是否必送货 isNeedInvoice 是否需要发票
	 *            preNoticeContent 提前通知内容 isRfcing 是否审批中 void
	 */
	@Override
	public void updateArriveSheetEntityByWaybillNo(ArriveSheetEntity arriveSheetEntity) {
		//更新到达联
		//生成状态
		arriveSheetEntity.setStatus(ArriveSheetConstants.STATUS_GENERATE);
		arrivesheetDao.updateArriveSheetByWaybillNo(arriveSheetEntity);
	}

	/**
	 * 
	 * 确认派送 根据运单打印到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-14 下午2:40:38
	 * @param entity waybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname 提货人名称
	 *            identifyType 证件类型 identifyCode 证件号码 situation 签收情况
	 *            arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数 signNote 签收备注
	 *            signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到 operateTime
	 *            签收操作时间 operator 操作人 operatorCode 操作人编码 operateOrgName 操作部门名称
	 *            operateOrgCode 操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes
	 *            打印次数 createUserName 创建人 createUserCode 创建人编码 createOrgName
	 *            创建部门 createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *            isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #printArriveSheet(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Override
	@Transactional
	public String printArriveSheet(ArriveSheetEntity entity) {
		this.checkGenerateArriveSheet(entity);
		StringBuffer arriveSheetNos = new StringBuffer();
		//根据运单查询  到达联状态为 “签收”和派送中 的到达联集合
		ArriveSheetDto dto = new ArriveSheetDto();
		dto.setWaybillNo(entity.getWaybillNo());
		List<String> statusList = new ArrayList<String>();
		statusList.add(ArriveSheetConstants.STATUS_GENERATE);
		statusList.add(ArriveSheetConstants.STATUS_DELIVER);
		dto.setArriveSheetStatus(statusList);
		dto.setActive(FossConstants.YES);
		dto.setDestroyed(FossConstants.NO);
		List<ArriveSheetEntity> returnArriveSheet = arrivesheetDao.queryArriveSheetByLifeCycle(dto);
		//返回 该运单需打印的到达联编号
		if (returnArriveSheet.size() > 0) {
			for (int i = 0; i < returnArriveSheet.size(); i++) {
				if (arriveSheetNos.length() == 0) {
					arriveSheetNos.append(returnArriveSheet.get(i).getArrivesheetNo());
				} else {
					arriveSheetNos.append(ArriveSheetConstants.COMMA).append(returnArriveSheet.get(i).getArrivesheetNo());
				}
			}
			//更改"打印次数"，以及"是否打印"的值
			this.updatePrintTimes(arriveSheetNos.toString());
		} else {
			throw new ArriveSheetMannerException("您选择的运单:" + entity.getWaybillNo() + "可打印到达联件数为0，无法打印！");
		}
		return arriveSheetNos.toString();
	}

	/**
	 * 更改打印次数
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-4 上午10:44:35
	 */
	@Override
	public void updatePrintTimes(String arrivesheetNos) {
		if (StringUtil.isEmpty(arrivesheetNos)) {
			throw new ArriveSheetMannerException("您选中的运单没有到达联或者已经作废，无法打印。");
		}
		String[] nos = arrivesheetNos.split(ArriveSheetConstants.COMMA);
		//更改"打印次数"，以及"是否打印"的值

		ArriveSheetEntity updateEntity;
		ArriveSheetLogEntity arriveSheetLogEntity = null;
		for (String arriveSheetNo : nos) {
			if (StringUtil.isNotBlank(arriveSheetNo) && arriveSheetNo.length() > NumberConstants.NUMBER_10) {
				updateEntity = new ArriveSheetEntity();
				updateEntity.setArrivesheetNo(arriveSheetNo);
				updateEntity.setIsPrinted(FossConstants.YES);
				// 打印时间
				updateEntity.setPrintTime(new Date());
				// 打印人
				updateEntity.setPrintUserName(FossUserContextHelper.getUserName());
				// 打印人code
				updateEntity.setPrintUserCode(FossUserContextHelper.getUserCode());
				// 打印部门
				updateEntity.setPrintOrgName(FossUserContextHelper.getOrgName());
				// 打印部门code
				updateEntity.setPrintOrgCode(FossUserContextHelper.getOrgCode());
				arrivesheetDao.updatePrintTimesByArrivesheetNo(updateEntity);
				// 添加打印历史记录
				arriveSheetLogEntity = new ArriveSheetLogEntity();
				arriveSheetLogEntity.setArriveSheetNo(arriveSheetNo);
				arriveSheetLogEntity.setWaybillNo(arriveSheetNo.substring(0, arriveSheetNo.length() - THREE));
				arriveSheetLogEntity.setOperateContent("PRINT");
				// 打印人
				arriveSheetLogEntity.setOperator(FossUserContextHelper.getUserName());
				// 打印人code
				arriveSheetLogEntity.setOperatorCode(FossUserContextHelper.getUserCode());
				// 打印部门
				arriveSheetLogEntity.setOperateOrgName(FossUserContextHelper.getOrgName());
				// 打印部门code
				arriveSheetLogEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
				arriveSheetLogEntity.setOperateTime(new Date());
				arrivesheetDao.insertArriveSheetLogEntity(arriveSheetLogEntity);
			}
		}
	}

	@Override
	public String printArriveSheet(String waybillNo) {
		return null;
	}

	/**
	 * 根据运单号判断到达联表里是否存在签收。
	 * 
	 * @author foss-meiying
	 * @date 2013-3-19 上午9:28:08
	 * @param entity waybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname 提货人名称
	 *            identifyType 证件类型 identifyCode 证件号码 situation 签收情况
	 *            arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数 signNote 签收备注
	 *            signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到 operateTime
	 *            签收操作时间 operator 操作人 operatorCode 操作人编码 operateOrgName 操作部门名称
	 *            operateOrgCode 操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes
	 *            打印次数 createUserName 创建人 createUserCode 创建人编码 createOrgName
	 *            创建部门 createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *            isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #queryArriveExistSign(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Override
	public Long queryArriveExistSign(ArriveSheetEntity entity) {
		return arrivesheetDao.queryArriveExistSign(entity);
	}

	/**
	 * 修改到达联。 根据到达联编号 active = 'Y' AND DESTROYED是否作废为N 未作废
	 * 
	 * @author foss-meiying
	 * @date 2013-3-19 上午9:25:43
	 * @param entity waybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname 提货人名称
	 *            identifyType 证件类型 identifyCode 证件号码 situation 签收情况
	 *            arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数 signNote 签收备注
	 *            signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到 operateTime
	 *            签收操作时间 operator 操作人 operatorCode 操作人编码 operateOrgName 操作部门名称
	 *            operateOrgCode 操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes
	 *            打印次数 createUserName 创建人 createUserCode 创建人编码 createOrgName
	 *            创建部门 createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *            isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #updateArriveSheetByArrivesheetNo
	 *      (com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Override
	public int updateArriveSheetByArrivesheetNo(ArriveSheetEntity entity) {
		//到达联是否有效---有效
		entity.setActive(FossConstants.YES);
		//到达联是否作废  ---未作废
		entity.setDestroyed(FossConstants.NO);
		return arrivesheetDao.updateArriveSheetByArrivesheetNo(entity);
	}

	/**
	 * 
	 * 根据运单号校验生成到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-12 下午8:44:06
	 * @param entity waybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname 提货人名称
	 *            identifyType 证件类型 identifyCode 证件号码 situation 签收情况
	 *            arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数 signNote 签收备注
	 *            signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到 operateTime
	 *            签收操作时间 operator 操作人 operatorCode 操作人编码 operateOrgName 操作部门名称
	 *            operateOrgCode 操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes
	 *            打印次数 createUserName 创建人 createUserCode 创建人编码 createOrgName
	 *            创建部门 createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *            isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #queryArriveSheetByEntity(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Override
	public ArriveSheetEntity queryArriveSheetByEntity(ArriveSheetEntity entity) {
		//有效
		entity.setActive(FossConstants.ACTIVE);
		//未作废
		entity.setDestroyed(FossConstants.NO);
		return arrivesheetDao.queryArriveSheetByEntity(entity);
	}

	/**
	 * 
	 * 生成到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-13 下午6:34:07
	 * @param entity waybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname 提货人名称
	 *            identifyType 证件类型 identifyCode 证件号码 situation 签收情况
	 *            arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数 signNote 签收备注
	 *            signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到 operateTime
	 *            签收操作时间 operator 操作人 operatorCode 操作人编码 operateOrgName 操作部门名称
	 *            operateOrgCode 操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes
	 *            打印次数 createUserName 创建人 createUserCode 创建人编码 createOrgName
	 *            创建部门 createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *            isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @return
	 * @see
	 */
	private String generateArriveSheet(ArriveSheetEntity entity) {
		if (entity.getArriveSheetGoodsQty() == 0) {
			return "";
		}
		UserEntity user = FossUserContext.getCurrentUser();
		OrgAdministrativeInfoEntity orgEntity = FossUserContext.getCurrentDept();
		//产生到达联编号
		entity.setArrivesheetNo(this.generateArriveSheetId(entity.getWaybillNo()));
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.YES);
		if (!ArriveSheetConstants.STATUS_DELIVER.equals(entity.getStatus())) {
			entity.setStatus(ArriveSheetConstants.STATUS_GENERATE);
		}
		//将状态作为有效
		entity.setDestroyed(FossConstants.NO);
		entity.setCreateTime(new Date());
		entity.setCreateOrgCode(orgEntity.getCode());
		entity.setCreateOrgName(orgEntity.getName());
		entity.setCreateUserCode(user.getEmployee().getEmpCode());
		entity.setCreateUserName(user.getEmployee().getEmpName());
		entity.setIsPrinted(FossConstants.NO);
		entity.setPrinttimes(0);
		// 添加通知信息
		notifyCustomerService.setArriveSheetNotifyInfo(entity);
		arrivesheetDao.addArriveSheetData(entity);
		return entity.getArrivesheetNo();
	}

	/**
	 * 
	 * 根据时间建模Copy 原有到达联生成有效到达联 并置废原有到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-24 下午6:05:20
	 * @param entity waybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname 提货人名称
	 *            identifyType 证件类型 identifyCode 证件号码 situation 签收情况
	 *            arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数 signNote 签收备注
	 *            signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到 operateTime
	 *            签收操作时间 operator 操作人 operatorCode 操作人编码 operateOrgName 操作部门名称
	 *            operateOrgCode 操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes
	 *            打印次数 createUserName 创建人 createUserCode 创建人编码 createOrgName
	 *            创建部门 createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *            isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @return
	 * @see
	 */
	private String copyAndUpdateArriveSheet(ArriveSheetEntity entity) {
		if (entity != null) {
			//将原有到达联 置废  将有效日期改为 此时
			ArriveSheetEntity updateArriveSheet = new ArriveSheetEntity();
			Date now = new Date();
			updateArriveSheet.setArrivesheetNo(entity.getArrivesheetNo());
			updateArriveSheet.setActive(FossConstants.NO);
			updateArriveSheet.setModifyTime(now);
			updateArriveSheet.setId(entity.getId());
			arrivesheetDao.updateByPrimaryKeySelective(updateArriveSheet);
			//copy原有到达联 并生成新的到达联。
			entity.setCreateTime(now);
			entity.setActive(FossConstants.YES);
			entity.setDestroyed(FossConstants.NO);
			entity.setModifyDate(null);
			if (!ArriveSheetConstants.STATUS_DELIVER.equals(entity.getStatus())) {
				entity.setStatus(ArriveSheetConstants.STATUS_GENERATE);
			}
			// 添加通知信息
			notifyCustomerService.setArriveSheetNotifyInfo(entity);
			arrivesheetDao.addArriveSheetData(entity);
			return entity.getArrivesheetNo();
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 生成到达联编号
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-13 下午4:31:10
	 * @param waybillNo 运单号
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #generateArriveSheetId(java.lang.String)
	 */
	@Transactional
	public String generateArriveSheetId(String waybillNo) {
		Long maxNum = arrivesheetDao.queryMaxArriveSheetNoByWayBillNo(waybillNo);
		StringBuffer arriveSheetNo = new StringBuffer(waybillNo);
		if (maxNum != null) {
			String maxArriveSheetNo = maxNum.toString();
			//截取最后三位到达联编号
			String idString = maxArriveSheetNo.substring(maxArriveSheetNo.length() - THREE);
			Integer lastNum = Integer.valueOf(idString);
			//累加
			lastNum = lastNum + 1;
			//少三位 补0
			String id = lastNum.toString();
			if (id.length() < THREE) {
				for (int i = 0; i < THREE - id.length(); i++) {
					arriveSheetNo.append(ZERO);
				}
				arriveSheetNo.append(id);
			}
			return arriveSheetNo.toString();
		} else {
			return arriveSheetNo.append(BENGIN_NUM).toString();
		}
	}

	/**
	 * 
	 * 作废到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-17 下午4:45:22
	 * @param entity waybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname 提货人名称
	 *            identifyType 证件类型 identifyCode 证件号码 situation 签收情况
	 *            arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数 signNote 签收备注
	 *            signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到 operateTime
	 *            签收操作时间 operator 操作人 operatorCode 操作人编码 operateOrgName 操作部门名称
	 *            operateOrgCode 操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes
	 *            打印次数 createUserName 创建人 createUserCode 创建人编码 createOrgName
	 *            创建部门 createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *            isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #invalidArriveSheet(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Override
	@Transactional
	public int invalidArriveSheet(ArriveSheetEntity entity) {
		//作废到达联
		arrivesheetDao.updateArriveSheetData(entity);
		//更新 actualFreight 表的 "生成到达联件数"字段
		ActualFreightEntity actualFreight = new ActualFreightEntity();
		actualFreight.setWaybillNo(entity.getWaybillNo());
		//已生成件数 = 原有件数 - 作废件数
		actualFreight.setGenerateGoodsQty(entity.getArriveSheetGoodsQty());
		actualFreightDao.updateSubGenerateGoodsQtyByWaybillNo(actualFreight);
		return 0;
	}

	/**
	 * 
	 * 激活到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-17 下午4:47:08
	 * @param arriveSheetEntity waybillNo 运单号 arrivesheetNo 到达联编号
	 *            deliverymanname 提货人名称 identifyType 证件类型 identifyCode 证件号码
	 *            situation 签收情况 arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数
	 *            signNote 签收备注 signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到
	 *            operateTime 签收操作时间 operator 操作人 operatorCode 操作人编码
	 *            operateOrgName 操作部门名称 operateOrgCode 操作部门编码 destroyed 是否作废
	 *            isPrinted 是否打印 printtimes 打印次数 createUserName 创建人
	 *            createUserCode 创建人编码 createOrgName 创建部门 createOrgCode 创建部门编码
	 *            createTime 创建时间 isSentRequired 是否必送货 isNeedInvoice 是否需要发票
	 *            preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #activateArriveSheet(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Override
	@Transactional
	public int activateArriveSheet(ArriveSheetEntity arriveSheetEntity) {
		ArriveSheetEntity queryArriveSheetEntity = arriveSheetEntity;
		queryArriveSheetEntity.setStatus(ArriveSheetConstants.STATUS_GENERATE);
		queryArriveSheetEntity.setActive(FossConstants.YES);
		queryArriveSheetEntity.setDestroyed(FossConstants.NO);
		List<ArriveSheetEntity> list = arrivesheetDao.queryArriveSheetByWaybillNo(queryArriveSheetEntity);
		if (list.size() > 0) {
			throw new ArriveSheetMannerException("不能激活该到达联，已存在激活状态的到达联");
		} else {
			arriveSheetEntity.setDestroyed(FossConstants.NO);
			arrivesheetDao.updateArriveSheetData(arriveSheetEntity);
			//更新 actualFreight 表的 "生成到达联件数"字段
			ActualFreightEntity actualFreight = new ActualFreightEntity();
			actualFreight.setWaybillNo(arriveSheetEntity.getWaybillNo());
			//已生成件数 = 原有件数 +激活件数
			actualFreight.setGenerateGoodsQty(arriveSheetEntity.getArriveSheetGoodsQty());
			actualFreightDao.updateAddGenerateGoodsQtyByWaybillNo(actualFreight);
		}
		return 0;
	}

	/**
	 * 
	 * <p>
	 * 通过CODE，获得NAME<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-15
	 * @param code
	 * @return String
	 */
	private String getCityNameValue(String code) {
		LOGGER.info("code==" + code);
		//获得部门
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
		if (org != null) {
			//城市
			String cityCode = org.getCityCode();
			//精确查询 通过 行政区域编码CODE 查询行政区域信息
			AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(cityCode);
			if (administrativeRegionsEntity != null) {
				LOGGER.info("administrativeRegionsEntity.getName()==" + administrativeRegionsEntity.getName());
				return administrativeRegionsEntity.getName();
			}
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * 把运单信息中的始发站code转换成名称<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-13
	 * @param waybillEntity void
	 */
	private String getUserNameByUserCode(String code) {
		//员工信息
		EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(code);
		//返回员工名称
		return employeeEntity.getEmpName();
	}

	/**
	 * 
	 * <p>
	 * 根据组织信息CODE获得组织信息<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-15
	 * @param waybillEntity void
	 */
	private OrgAdministrativeInfoEntity getOrgInfo(String code) {
		//组织信息
		return orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
	}

	/**
	 * 根据运单号查询运单详细信息
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-10 下午3:57:47
	 */
	@Override
	public ArriveSheetVo queryWaybillDetailByWaybillNo(String waybillNo) {
		//返回页面VO
		ArriveSheetVo vo = new ArriveSheetVo();
		//运单增值服务DTO信息
		//		WaybillDto waybillDto = new WaybillDto();
		//获得运费明细LIST
		//		List<WaybillChargeDtlEntity> chargeList = waybillChargeDtlService.queryChargeDtlEntityByNo(waybillNo);
		//获得运单信息
		//		WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(waybillNo);
		//运单信息 与查询到达联详细信息Dto
		//		ArriveSheetWaybillAddPropertyDto dto = new ArriveSheetWaybillAddPropertyDto();
		WaybillEntity condition = new WaybillEntity();
		condition.setWaybillNo(waybillNo);
		condition.setActive(FossConstants.ACTIVE);
		ArriveSheetWaybillAddPropertyDto dto = arrivesheetDao.queryPrintInfoByWaybillNo(condition);
		//把查询的运单信息复制到DTO中
		//		BeanUtils.copyProperties(waybillEntity, dto, true);
		//获得页面显示的件数
		//		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
		//把件数放到运单信息中显示
		//		dto.setArriveNotoutGoodsQty(actualFreightEntity.getArriveNotoutGoodsQty());

		//		//获取增值服务
		//		for (WaybillChargeDtlEntity waybillChargeDtlEntity : dto.getWaybillChargeDtlEntity()) {
		//			//通过服务CODE获得服务名称
		//			waybillChargeDtlEntity.setModifyUser(priceEntryService.queryPriceEntryNameByCode(
		//					waybillChargeDtlEntity.getPricingEntryCode()));
		//			LOGGER.info("waybillChargeDtlEntity.getModifyUser=="+waybillChargeDtlEntity.getModifyUser());
		//		}
		//把增值服务添加到运单DTO中
		//		waybillDto.setWaybillChargeDtlEntity(chargeList);
		//对DTO的数据进行转换
		//		changeDtoValue(dto);
		//运单增值服务DTO信息
		//		vo.setWaybillDto(waybillDto);
		
		/**
		 * 处理保管费字段 
		 * author yangkang
		 */
	    if (dto.getStorageCharge() != null && dto.getStorageCharge().compareTo(BigDecimal.ZERO) > 0){
				
			   // 设置保管费计算的共通参数
				String[] codes = { NotifyCustomerConstants.STORAGE_CHARGE_MIN_DESC };// 最小仓储费
				// 系统配置参数获取
				StorageJobDto storageJobDto = notifyCustomerService.getConfigurationParams(codes);
				// 设置仓储费最小金额
				if (storageJobDto.getStorageChangeMin().compareTo(dto.getStorageCharge()) > 0) {
					dto.setStorageCharge(storageJobDto.getStorageChangeMin());
				}else{
					dto.setStorageCharge(dto.getStorageCharge());
				}
		}else{
			dto.setStorageCharge(BigDecimal.ZERO);
		}
	    //是否有发货地址备注
	    if(StringUtils.isNotEmpty(dto.getDeliveryCustomerAddressNote())){
	    	dto.setDeliveryCustomerAddress(dto.getDeliveryCustomerAddress()+"("+dto.getDeliveryCustomerAddressNote()+")");
	    }
	    //是否有收货地址备注
	    if(StringUtils.isNotEmpty(dto.getReceiveCustomerAddressNote())){
	    	dto.setReceiveCustomerAddress(dto.getReceiveCustomerAddress()+"("+dto.getReceiveCustomerAddressNote()+")");
	    }
	    
		dto.setArriveSheetCreateDate(new Date(System.currentTimeMillis()));
		
		// 判断是否PDA运单
		if(dto!=null && WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(dto.getPendingType())){
			//获取运单补录时信息
			WaybillEntity firstWaybill=waybillDao.queryWaybillForFirst(waybillNo);
			if(firstWaybill!=null){
				dto.setBillCreateUserName(firstWaybill.getCreateUserName());
				dto.setBillCreateTime(firstWaybill.getCreateTime());
			}
		}else{
			dto.setBillCreateUserName(dto.getCreateUserCode());
			dto.setBillCreateTime(dto.getBillTime());
		}
		
		//到达联  运单信息
		vo.setArriveSheetWaybillAddPropertyDto(dto);
		return vo;
	}

	/**
	 * 
	 * <p>
	 * 把CODE转换成Name<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-16
	 * @param dto void
	 */
	@SuppressWarnings("unused")
	private void changeDtoValue(ArriveSheetWaybillAddPropertyDto dto) {
		//显示的地址
		StringBuffer address = new StringBuffer();
		//地址
		address.append(administrativeRegionsService.queryAdministrativeRegionsNameByCode(dto.getReceiveCustomerProvCode())).append(administrativeRegionsService.queryAdministrativeRegionsNameByCode(dto.getReceiveCustomerCityCode()))
				.append(administrativeRegionsService.queryAdministrativeRegionsNameByCode(dto.getReceiveCustomerDistCode())).append(dto.getReceiveCustomerAddress());
		dto.setReceiveCustomerAddress(address.toString());
		//收货部门(出发部门)
		OrgAdministrativeInfoEntity orgReceived = getOrgInfo(dto.getReceiveOrgCode());
		//提货网点
		OrgAdministrativeInfoEntity orgDelivery = getOrgInfo(dto.getCustomerPickupOrgCode());

		//目的站--(收货市)dto.setReceiveCustomerCityCode(dto.getTargetOrgCode());
		if (orgReceived != null) {
			//始发站--(发货市)
			dto.setDeliveryCustomerCityCode(orgReceived.getCityName());
			//收货部门(出发部门)--始发站
			dto.setReceiveOrgCode(orgReceived.getName());
			//收货部门(出发部门) 发货网点电话
			dto.setReceiveDepTelephone(orgReceived.getDepTelephone());
		}
		//目的站--把城市CODE转换成城市Name--dto.setTargetOrgCode();
		if (orgDelivery != null) {
			//提货网点
			dto.setCustomerPickupOrgCode(orgDelivery.getName());
			//提货网点电话
			dto.setCustomerPickupTelephone(orgDelivery.getDepTelephone());
			dto.setCustomerPickupProvCode(orgDelivery.getProvCode());
		}
		//目的站
		//收货市-把城市CODE转换成城市Name
		dto.setReceiveCustomerCityCode(getCityNameValue(dto.getReceiveCustomerCityCode()));

		//CODE转换成NAME
		//转换用户CODE到用户名称(把运单制单人CODE转化成名称)
		dto.setCreateUserCode(getUserNameByUserCode(dto.getCreateUserCode()));
		//创建部门编码转换到名字
		dto.setCreateOrgCode(getNameByCode(dto.getCreateOrgCode()));
		//转换用户CODE到用户名称(把到达联制单人CODE转化成名称)
		//获取当前用户
		//获取当前时间
		dto.setArriveSheetCreateDate(new Date(System.currentTimeMillis()));
		//获取当前部门
	}

	/**
	 * 
	 * <p>
	 * 根据CODE获得Name<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-2-22
	 * @param code
	 * @return String
	 */
	private String getNameByCode(String code) {
		//获得部门--发货部门
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
		if (org != null) {
			return org.getName();
		}
		return null;
	}

	/**
	 * 根据运单、最终库存部门编码 检查是否存在有效的到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-14 上午10:46:49
	 */
	@Override
	public Boolean checkArriveSheetByWaybillNo(String waybillNo, String endStockOrgCode) {
		Boolean falg = false;
		ArriveSheetActualFreightDto dto = new ArriveSheetActualFreightDto();
		dto.setActive(FossConstants.YES);
		dto.setEndStockOrgCode(endStockOrgCode);
		dto.setWaybillNo(waybillNo);
		Integer num = arrivesheetDao.queryArriveSheetByWaybillNo(dto);
		if (num > 0) {
			falg = true;
		}
		return falg;
	}

	/**
	 * 结算接口： 根据条件查询到达联的派送记录 返回 运单、派送单、到达联上相关信息
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-18 下午8:33:05
	 */
	@Override
	public List<ArrivesheetDeliverDto> queryArriveSheetByDriverCode(ArrivesheetDeliverDto dto) {
		if (dto == null) {
			throw new ArriveSheetMannerException("传入条件不能为空！");
		} else if (dto.getOperateBeginTime() == null) {
			throw new ArriveSheetMannerException("起始时间不能为空！");
		} else if (dto.getOperateEndTime() == null) {
			throw new ArriveSheetMannerException("结束时间不能为空！");
		} else if (StringUtils.isEmpty(dto.getDriverCode())) {
			throw new ArriveSheetMannerException("司机编号不能为空！");
		} else {
			dto.setActive(FossConstants.YES);
			dto.setDestroyed(FossConstants.NO);
			dto.setStatus(ArriveSheetConstants.STATUS_SIGN);
			dto.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
			dto.setDeliverbillStatus(DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED);//派送单状态为已签收确认
			return arrivesheetDao.queryArriveSheetByDriverCode(dto);
		}
	}
	
	/**
	 * 结算接口： 根据条件查询到达联的派送记录 返回 运单、派送单、到达联上相关信息（快递）
	 * 
	 * @author WangQianJin
	 * @date 2014-06-06
	 */
	@Override
	public List<ArrivesheetDeliverDto> queryExpressArriveSheetByDriverCode(ArrivesheetDeliverDto dto) {
		if (dto == null) {
			throw new ArriveSheetMannerException("传入条件不能为空！");
		} else if (dto.getOperateBeginTime() == null) {
			throw new ArriveSheetMannerException("起始时间不能为空！");
		} else if (dto.getOperateEndTime() == null) {
			throw new ArriveSheetMannerException("结束时间不能为空！");
		} else if (StringUtils.isEmpty(dto.getDriverCode())) {
			throw new ArriveSheetMannerException("司机编号不能为空！");
		} else {
			dto.setActive(FossConstants.YES);
			dto.setDestroyed(FossConstants.NO);
			dto.setStatus(ArriveSheetConstants.STATUS_SIGN);
			dto.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
			dto.setDeliverbillStatus(DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED);//派送单状态为已签收确认
			return arrivesheetDao.queryExpressArriveSheetByDriverCode(dto);
		}
	}

	/**
	 * Sets the 到达联DAO.
	 * 
	 * @param arrivesheetDao the new 到达联DAO
	 */
	public void setArrivesheetDao(IArrivesheetDao arrivesheetDao) {
		this.arrivesheetDao = arrivesheetDao;
	}

	/**
	 * Sets the actualFreightDao.
	 * 
	 * @param actualFreightDao the new actualFreightDao
	 */
	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}

	/**
	 * Sets the 运单基本信息.
	 * 
	 * @param waybillDao the new 运单基本信息
	 */
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	/**
	 * Sets the addAdministrativeRegions.
	 * 
	 * @param administrativeRegionsService the new addAdministrativeRegions
	 */
	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/** 20121116lizhiguo **/
	@Override
	public List<ArriveSheetEntity> queryArriveSheetByWaybillNo(ArriveSheetEntity entity) {
		return arrivesheetDao.queryArriveSheetByWaybillNo(entity);
	}

	/**
	 * 根据到达联编号查运单运输性质 是否整车运单, 到达联id,提货人证件号码,证件类型,到达联件数, 最终配载部门,订单号,提货方式等运单的部分信息
	 * 
	 * @author foss-meiying
	 * @date 2013-1-6 下午7:21:28
	 * @param arriveSheetNo 到达联编号
	 * @param status 状态
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #queryPartWaybillpartArrivesheet(java.lang.String, java.lang.String)
	 */
	@Override
	public SignDto queryPartWaybillpartArrivesheet(String arriveSheetNo, String status) {
		ArriveSheetEntity arrive = new ArriveSheetEntity();
		//到达联编号
		arrive.setArrivesheetNo(arriveSheetNo);
		//到达联是否有效---有效
		arrive.setActive(FossConstants.YES);
		//到达联是否作废  ---未作废
		arrive.setDestroyed(FossConstants.NO);
		arrive.setStatus(status);//到达联状态
		return arrivesheetDao.queryPartWaybillpartArrivesheet(arrive);
	}

	/**
	 * 
	 * 签收变更接口 专线 更新到达联（t_srv_arrivesheet） , 更新运单签收结果(t_srv_waybill_sign_result)
	 * "更新运单签收结果(t_srv_waybill_sign_result)
	 * 
	 * @author foss-meiying
	 * @date 2012-12-19 下午4:43:32
	 * @param oldArriveSheet waybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname
	 *            提货人名称 identifyType 证件类型 identifyCode 证件号码 situation 签收情况
	 *            arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数 signNote 签收备注
	 *            signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到 operateTime
	 *            签收操作时间 operator 操作人 operatorCode 操作人编码 operateOrgName 操作部门名称
	 *            operateOrgCode 操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes
	 *            打印次数 createUserName 创建人 createUserCode 创建人编码 createOrgName
	 *            创建部门 createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *            isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @param newArriveSheet waybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname
	 *            提货人名称 identifyType 证件类型 identifyCode 证件号码 situation 签收情况
	 *            arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数 signNote 签收备注
	 *            signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到 operateTime
	 *            签收操作时间 operator 操作人 operatorCode 操作人编码 operateOrgName 操作部门名称
	 *            operateOrgCode 操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes
	 *            打印次数 createUserName 创建人 createUserCode 创建人编码 createOrgName
	 *            创建部门 createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *            isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @see
	 */
	@Override
	public void changeArriveSheet(ArriveSheetEntity oldArriveSheet, ArriveSheetEntity newArriveSheet) {
		//如果传入的对象不为空 
		if (oldArriveSheet == null || newArriveSheet == null) {
			throw new ArriveSheetMannerException(ArriveSheetMannerException.OBJECT_NULL_NOT_SIGN_CHANGE_OPERATE);//传入的对象为空，不能进行签收变更操作
		}
		//得到当前时间
		Date date = new Date();
		ArriveSheetEntity oldEntity = new ArriveSheetEntity();
		//时效时间为当前时间
		oldEntity.setModifyTime(date);
		//旧的到达联id
		oldEntity.setId(oldArriveSheet.getId());
		//旧的到达联为无效
		oldEntity.setActive(FossConstants.INACTIVE);
		// 更新到达联
		arrivesheetDao.updateByPrimaryKeySelective(oldEntity);
		//生效时间为当前时间
		newArriveSheet.setCreateTime(date);
		//新的到达联有效
		newArriveSheet.setActive(FossConstants.ACTIVE);
		// 添加通知信息
		notifyCustomerService.setArriveSheetNotifyInfo(newArriveSheet);
		//添加新的到达联
		arrivesheetDao.addArriveSheetData(newArriveSheet);
		//对运单签收结果进行处理
		WaybillSignResultEntity signResult = this.checkWaybillSignResult(newArriveSheet.getWaybillNo(), date);
		if(!StringUtils.equals(oldArriveSheet.getDeliverymanName(), newArriveSheet.getDeliverymanName())){
			// 签收人
			signResult.setDeliverymanName(newArriveSheet.getDeliverymanName());
		}
		if(!StringUtils.equals(oldArriveSheet.getIdentifyCode(),newArriveSheet.getIdentifyCode())){
			// 证件号码
			signResult.setIdentifyCode(newArriveSheet.getIdentifyCode());
		}
		if(!StringUtils.equals(oldArriveSheet.getIdentifyType(),newArriveSheet.getIdentifyType())){
			// 证件类型
			signResult.setIdentifyType(newArriveSheet.getIdentifyType());
		}
		if(!StringUtils.equals(oldArriveSheet.getSignNote(),newArriveSheet.getSignNote())){
			// 得到签收备注
			signResult.setSignNote(newArriveSheet.getSignNote());
		}
		if(DateUtils.getSecondDiff(oldArriveSheet.getSignTime(),newArriveSheet.getSignTime())!=0){
			// 签收时间
			signResult.setSignTime(newArriveSheet.getSignTime());
		}
		// 添加最新运单签收结果信息
		waybillSignResultService.addWaybillSignResult(signResult);

	}

	/**
	 * 
	 * 反签收接口 专线 作废到达联（t_srv_arrivesheet）， 设置ACTIVE为“N”。
	 * 
	 * 根据作废到达联中的签收明细（t_srv_sign_detail）进行入库, 入库部门为作废到达联中的OPERATE_ORG_CODE
	 * "更新运单签收结果(t_srv_waybill_sign_result)
	 * 
	 * @author foss-meiying
	 * @date 2012-12-19 下午5:05:34
	 * @param list
	 * @param currentInfo currentInfo.operate 操作人 currentInfo.operatorCode 操作人编码
	 *            currentInfo.operateOrgName 操作部门名称 currentInfo.operateOrgCode
	 *            操作部门编码
	 * @see
	 */
	@Override
	public void reverseArriveSheet(List<ArriveSheetEntity> list, CurrentInfo currentInfo) {
		if (CollectionUtils.isEmpty(list) || currentInfo == null) {
			LOGGER.error("传入的集合对象为空，不能进行反签收操作");//记录日志
			throw new ArriveSheetMannerException(ArriveSheetMannerException.OBJECT_NULL_NOT_REVERSE_SIGN_OPERATE);//传入的集合对象为空，不能进行反签收操作
		}
		LOGGER.info("专线--- 反签收开始");//记录日志
		// 作废到达联的签收件数
		Integer arriveNotoutGoodsQty = ArriveSheetConstants.ZERO;
		// 作废到达联的件数
		Integer generateGoodsQty = ArriveSheetConstants.ZERO;
		List<String> serialNOs = new ArrayList<String>();
		List<InOutStockEntity> inOutStockList = new ArrayList<InOutStockEntity>();
		Date systemDate = new Date();//当前系统时间
		
		SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(list.get(0).getOperateOrgCode());
		String orgCode = "";//部门编码为空
		if (saleDepartment == null) {
			//非营业部找到它上级所属外场的编码
			List<String> bizTypes = new ArrayList<String>();
			bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(list.get(0).getOperateOrgCode(), bizTypes);
			if(orgAdministrativeInfoEntity != null){
				orgCode= orgAdministrativeInfoEntity.getCode();
				
			}else {
				// 给中转传到达联的操作部门编码
				orgCode = list.get(0).getOperateOrgCode();
			}
		} else {
			if(saleDepartment.checkStation()){// 给中转传驻地营业部所属外场
				orgCode=saleDepartment.getTransferCenter();
			}else {
				// 给中转传到达联的操作部门编码
				orgCode = list.get(0).getOperateOrgCode();
			}
		}
		// 作废到达联
		for (ArriveSheetEntity arriveSheetEntity : list) {
			ArriveSheetEntity arr = new ArriveSheetEntity();
			arr.setId(arriveSheetEntity.getId());
			// 作废
			arr.setActive(FossConstants.INACTIVE);
			//时效时间为当前时间
			arr.setModifyTime(systemDate);
			arrivesheetDao.updateByPrimaryKeySelective(arr);
			// 根据作废到达联中的签收明细（t_srv_sign_detail）进行入库,入库部门为作废到达联中的OPERATE_ORG_CODE
			List<SignDetailEntity> signDetailLit = signDetailService.queryByArrivesheetNo(arriveSheetEntity.getArrivesheetNo());
			//如果签收明细集合不为空
			if (CollectionUtils.isNotEmpty(signDetailLit)) {
				for (SignDetailEntity signDetailEntity : signDetailLit) {
					InOutStockEntity inOutStockEntity = new InOutStockEntity();
					// 给中转传到达联的操作部门编码
					inOutStockEntity.setOrgCode(orgCode);
					//运单号
					inOutStockEntity.setWaybillNO(arriveSheetEntity.getWaybillNo());
					//出入库类型 --反签收入库
					inOutStockEntity.setInOutStockType(StockConstants.REVERSE_SIGN_IN_STOCK_TYPE);
					//操作人工号  
					inOutStockEntity.setOperatorCode(currentInfo.getEmpCode());
					//操作人姓名
					inOutStockEntity.setOperatorName(currentInfo.getEmpName());
					//流水号
					inOutStockEntity.setSerialNO(signDetailEntity.getSerialNo());
					serialNOs.add(signDetailEntity.getSerialNo());
					inOutStockList.add(inOutStockEntity);
				}
			}
			// 累加作废到达联的签收件数
			arriveNotoutGoodsQty += arriveSheetEntity.getSignGoodsQty();
			// 累加作废到达联的签收件数
			generateGoodsQty += arriveSheetEntity.getSignGoodsQty();
		}
		//对运单签收结果进行处理
		String waybillNo = list.get(ArriveSheetConstants.ZERO).getWaybillNo();//运单号
		WaybillSignResultEntity signResult = this.checkWaybillSignResult(waybillNo, systemDate);
		WaybillEntity waybillEntity = waybillManagerService.queryPartWaybillByNo(waybillNo);
		if (null == waybillEntity) {
			LOGGER.error("该运单对应实体不存在");//记录日志
			throw new ArriveSheetMannerException(ArriveSheetMannerException.ENTITY_IS_NOT_EXIST);//该运单对应实体不存在 
		}
		if (inOutStockList.size() > SignConstants.DEFAULT_SERIALNOS_LIMIT_COUNT) {
			for (InOutStockEntity inOutStockEntity : inOutStockList) {
				SignStockEntity signStock = new SignStockEntity();
				// 运单号
				signStock.setWaybillNo(inOutStockEntity.getWaybillNO());
				// 流水号
				signStock.setSerialNo(inOutStockEntity.getSerialNO());
				// 操作人姓名
				signStock.setOperator(inOutStockEntity.getOperatorName());
				signStock.setStockOrgCode(inOutStockEntity.getOrgCode());// 部门编码
				// 操作人工号
				signStock.setOperatorCode(inOutStockEntity.getOperatorCode());
				signStock.setIsWholeVehicle(waybillEntity.getIsWholeVehicle());//是否整 车运单
				signStock.setSignStatus(signResult.getSignStatus());//签收状态
				// 出入库类型 
				signStock.setInoutType(inOutStockEntity.getInOutStockType());
				// 进行出库操作
				try {
					signStockJobService.addSelective(signStock);
				} catch (BusinessException e) {//捕获异常
					LOGGER.error(e.getMessage(), e);//记录日志
					throw new ArriveSheetMannerException(e.getErrorCode(), e);//抛出异常
				}
			}
			LOGGER.info("--添加sign_stock完成");//记录日志
		} else {
			for (InOutStockEntity inOutStockEntity : inOutStockList) {
				try {
					stockService.inStockPC(inOutStockEntity);
				} catch (StockException e) {
					LOGGER.error(e.getMessage(), e);//记录日志
					throw new ArriveSheetMannerException(e.getErrorCode(), e);//抛出异常
				}
			}
			LOGGER.info("反签收中转入库完成");//记录日志
			//如果签收状态为全部签收  调用中转的走货路径接口
			if (SignConstants.SIGN_STATUS_ALL.equals(signResult.getSignStatus()) && FossConstants.NO.equals(waybillEntity.getIsWholeVehicle())) {
				try {
					calculateTransportPathService.rollBackList(waybillNo, serialNOs, null, TransportPathConstants.TRANSPORTPATH_STATUS_SIGNIN);
					LOGGER.info("调用中转的走货路径接口完成");//记录日志
				} catch (TfrBusinessException e) {//捕获异常
					LOGGER.error(e.getMessage(), e);//记录日志
					throw new ArriveSheetMannerException(e.getErrorCode(), e);
				}
			}
		}
		// “签收件数”=“签收件数”-“作废到达联的签收件数”
		signResult.setSignGoodsQty(signResult.getSignGoodsQty() - arriveNotoutGoodsQty);
		//update   
		PickupResultDto pickupResultDto = pickupService.isExistByWaybill(waybillNo);
		if(pickupResultDto!=null&&!pickupResultDto.getState().equals(PickupConstants.GOOD_STATE_REVOCATION)){
			if (ArriveSheetConstants.ZERO < signResult.getSignGoodsQty()) {
				//部分签收
				pickupResultDto.setState(PickupConstants.GOOD_STATE_PARTSIGN);
			}else{
				//反签收
				pickupResultDto.setState(PickupConstants.GOOD_STATE_ANTISIGN);
			}
			WaybillEntity rstWaybill = waybillDao.queryWaybillByNo(waybillNo);
			updateDataInit(pickupResultDto, rstWaybill);
			pickupService.updatePickupStateByPickupResultDto(pickupResultDto);
		}
		//update
		if (ArriveSheetConstants.ZERO < signResult.getSignGoodsQty()) {//如果签收件数大于0
			// 签收状态--部分签收
			signResult.setSignStatus(SignConstants.SIGN_STATUS_PARTIAL);
			signResult.setCreateOrgCode(currentInfo.getCurrentDeptCode());//签收部门编码
			signResult.setCreateOrgName(currentInfo.getCurrentDeptName());//签收部门名称
			signResult.setCreator(currentInfo.getEmpName());//操作人
			signResult.setCreatorCode(currentInfo.getEmpCode());//操作人编码
			// 添加最新运单签收结果信息
			waybillSignResultService.addWaybillSignResult(signResult);
			LOGGER.info("添加运单签收结果完成");//记录日志
		} else {
			if (WaybillConstants.INNER_PICKUP.equals(waybillEntity.getReceiveMethod())) {//提货方式为汽运内部自提
				LOGGER.info("--提货方式为汽运内部自提 不需要调用结算反签收接口----");//记录日志
			} else {
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
						try{
							//调用结算的反签收接口将母件反签收
							this.reverseConfirmTaking(twoInOneDto.getMainWaybillNo(),currentInfo,waybillEntity.getProductCode(),systemDate);
						}catch(ArriveSheetMannerException e){
							LOGGER.error("母单反确认异常", e);//记录日志
							throw new ArriveSheetMannerException("母单号" + twoInOneDto.getMainWaybillNo() + "反签收异常：" + e.getErrorCode(), e);
						}
					}
					//是母件并且不是第一个,由于子件反签收时，已将母件的反结算，所以母件不用再反结算
					if(waybillNo.equals(twoInOneDto.getMainWaybillNo()) && !isFirst){
						isReverseConfirm = false;
					}
				}
				if(isReverseConfirm){
					//调用结算的反签收接口
					this.reverseConfirmTaking(waybillNo, currentInfo, waybillEntity.getProductCode(), systemDate);
				}
			}
		}
		// 更新ActualFreight中的 到达未出库件数,已生成到达联件数,排单件数
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		// "到达未出库件数”=“到达未出库件数”+“作废到达联的签收件数”，
		actualFreightEntity.setArriveNotoutGoodsQty(arriveNotoutGoodsQty);
		// “已生成到达联件数”=“已生成到达联件数”-“作废到达联的签收件数”
		actualFreightEntity.setGenerateGoodsQty(generateGoodsQty);
		// “排单件数” = “排单件数” - “作废到达联的签收件数”
		actualFreightEntity.setArrangeGoodsQty(generateGoodsQty);
		actualFreightEntity.setWaybillNo(waybillNo);
		actualFreightService.updateActualFreightPartByWaybillNo(actualFreightEntity);
		//反标识业务完结
		WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
		//运单号
		waybillTransactionEntity.setWaybillNo(signResult.getWaybillNo());
		waybillTransactionService.updateReverseBusinessOver(waybillTransactionEntity);
		LOGGER.info("专线--- 反签收完成");//记录日志
	}

	/**
	 * 初始化符合条件的插入数据
	 * @date 2014-11-24 下午6:45:19
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.SignService#updateDataInit(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto)
	 */
	private PickupResultDto updateDataInit(PickupResultDto rstPickupResultDto,WaybillEntity rstWaybill) {
		if(rstWaybill!=null){
			rstPickupResultDto.setBillingGoodsQty(rstWaybill.getGoodsQtyTotal());
			rstPickupResultDto.setGoodPackage(rstWaybill.getGoodsPackage());
			rstPickupResultDto.setGoodSize(rstWaybill.getGoodsSize());
			rstPickupResultDto.setGoodVolume(rstWaybill.getGoodsVolumeTotal());
			rstPickupResultDto.setGoodWeight(rstWaybill.getGoodsWeightTotal());
			rstPickupResultDto.setOperTime(new Date());
			rstPickupResultDto.setState(PickupConstants.GOOD_STATE_ANTISIGN);
		}
		return rstPickupResultDto;
	}
	
	/**
	 * 调用结算的反签收接口
	 * 
	 * @author foss-meiying
	 * @date 2013-1-18 下午4:05:51
	 * @param waybillNo 运单号
	 * @param currentInfo 当前登录人信息
	 * @param productCode 运输性质
	 * @see
	 */
	private void reverseConfirmTaking(String waybillNo, CurrentInfo currentInfo, String productCode, Date systemDate) {
		try {
			LineSignDto dto = new LineSignDto();
			// 运单号
			dto.setWaybillNo(waybillNo);
			// 运输性质
			dto.setProductType(productCode);
			// 反签收部门编码
			dto.setSignOrgCode(currentInfo.getCurrentDeptCode());
			// 反签收部门名称
			dto.setSignOrgName(currentInfo.getCurrentDeptName());
			dto.setSignType(SettlementConstants.LINE_SIGN);//签收类型
			// 业务日期                           
			dto.setSignDate(systemDate);
			//CUBC反签收   灰度改造    353654 ---------------------------start
			String vestSystemCode = null;
            try {
            	List<String> arrayList = new ArrayList<String>();
            	arrayList.add(dto.getWaybillNo());
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".reverseConfirmTaking",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
            	List<VestBatchResult> list = response.getVestBatchResult();
            	vestSystemCode = list.get(0).getVestSystemCode();		
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"错误方法位置："+SERVICE_CODE+".reverseConfirmTaking");
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
				lineSignService.reverseConfirmTaking(dto, currentInfo);
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
				reqDto.setCurrentInfo(currentInfo);
				reqDto.setLineSignDto(dto);
				CUBCSignOrRevSignResultDto resultDto1 = cUBCReverseSignService.sendReverseSignReqToCUBC(reqDto);
				if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
					if(StringUtils.isNotBlank(resultDto1.getMeg())){
						LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
						throw new CUBCReverseSignException(resultDto1.getMeg());
					}else{
						throw new CUBCReverseSignException("调用CUBC签收接口失败但未获取到异常信息");
					}
				}		
			}
			//CUBC反签收   灰度改造    353654 ---------------------------end
		} catch (SettlementException e) {
			LOGGER.error("调用财务接口抛出异常", e);//记录日志
			throw new ArriveSheetMannerException(e.getErrorCode(), e);
		}
		LOGGER.info("调用财务接口完成");//记录日志
	}

	/**
	 * 时间建模处理 根据运单号查询运单签收结果， 如果存在， 修改当前运单签收结果为无效，得到最新的运单签收结果记录返回
	 * 如果运单是一票多件，且外发流水签收表不为空，将该运单的外发流水签收信息都作废
	 * @author foss-meiying
	 * @date 2012-12-20 下午3:10:12
	 * @param waybillNo 运单号,
	 *        reverse 是否反签收调用
	 * @return
	 * @see
	 */
	private WaybillSignResultEntity checkWaybillSignResult(String waybillNo, Date modifyTime) {
		WaybillSignResultEntity signResult = new WaybillSignResultEntity(waybillNo, FossConstants.ACTIVE);
		// 根据运单号查询运单签收结果
		signResult = waybillSignResultService.queryWaybillSignResultByWaybillNo(signResult);
		if (signResult != null) {
			// 作废运单签收结果里当前运单号
			waybillSignResultService.invalidWaybillSignResult(signResult.getId(), modifyTime);
		} else {
			LOGGER.error("运单号" + waybillNo + "在运单签收结果里不存在");//记录日志
			throw new ArriveSheetMannerException(ArriveSheetMannerException.WAYBILL_SIGN_RESULT_IS_NOT_EXIST);//该运单号在运单签收结果里不存在
		}

		// 运单号，到达联状态为签收，到达联有效,未作废
		ArriveSheetEntity arriveSheetEntity = new ArriveSheetEntity(waybillNo, ArriveSheetConstants.STATUS_SIGN, FossConstants.ACTIVE, FossConstants.NO);
		// 正常签收
		arriveSheetEntity.setSituation(SignConstants.NORMAL_SIGN);
		// 根据运单号，到达联状态为签收，到达联有效，签收情况不等于正常签收 来 查询是否存在
		Long count = arrivesheetDao.queryArriveExistSign(arriveSheetEntity);
		// 存在异常签收
		if (count != null && count > 0) {
			// 签收情况为异常签收
			signResult.setSignSituation(SignConstants.UNNORMAL_SIGN);
		} else {
			// 签收情况为正常签收
			signResult.setSignSituation(SignConstants.NORMAL_SIGN);
		}
		// 生效时间为当前时间
		signResult.setCreateTime(modifyTime);
		// 时效时间为空，添加时采用默认值
		signResult.setModifyTime(null);
		return signResult;
	}

	/**
	 * 
	 * 根据ID更新arrivesheet
	 * 
	 * @author foss-meiying
	 * @date 2012-12-22 下午7:56:25
	 * @param entity waybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname 提货人名称
	 *            identifyType 证件类型 identifyCode 证件号码 situation 签收情况
	 *            arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数 signNote 签收备注
	 *            signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到 operateTime
	 *            签收操作时间 operator 操作人 operatorCode 操作人编码 operateOrgName 操作部门名称
	 *            operateOrgCode 操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes
	 *            打印次数 createUserName 创建人 createUserCode 创建人编码 createOrgName
	 *            创建部门 createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *            isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	@Override
	public Integer updateByPrimaryKeySelective(ArriveSheetEntity entity) {
		return arrivesheetDao.updateByPrimaryKeySelective(entity);
	}

	/**
	 * 根据运单号查询到达联集合
	 * 
	 * @author foss-meiying
	 * @date 2012-12-24 下午5:15:58
	 * @param waybillNo 运单号
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #queryArriveSheetListByWaybillNo(java.lang.String)
	 */
	@Override
	public List<ArriveSheetEntity> queryArriveSheetListByCondition(String waybillNo) {
		ArriveSheetDto dto = new ArriveSheetDto();
		//运单号
		dto.setWaybillNo(waybillNo);
		//有效
		dto.setActive(FossConstants.ACTIVE);
		//未作废
		dto.setDestroyed(FossConstants.NO);
		//到达联状态集合
		List<String> arriveSheetStatus = new ArrayList<String>();
		//到达联状态 --派送中
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_DELIVER);
		//到达联状态 --签收
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_SIGN);
		dto.setArriveSheetStatus(arriveSheetStatus);//到达联状态集合
		return arrivesheetDao.queryArriveSheetByLifeCycle(dto);//查询满足条件的信息
	}

	/**
	 * 
	 * 根据运单号查询签收的到达联集合
	 * 
	 * @param waybillNo 运单号
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 8, 2013 8:57:43 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #queryArriveSheetListByWaybillNoWithSign(java.lang.String)
	 */
	@Override
	public List<ArriveSheetEntity> queryArriveSheetListByWaybillNoWithSign(String waybillNo) {
		ArriveSheetDto dto = new ArriveSheetDto();
		//运单号
		dto.setWaybillNo(waybillNo);
		//有效
		dto.setActive(FossConstants.ACTIVE);
		//未作废
		dto.setDestroyed(FossConstants.NO);
		//到达联状态集合
		List<String> arriveSheetStatus = new ArrayList<String>();
		//到达联状态 --签收
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_SIGN);
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_REFUSE);
		dto.setArriveSheetStatus(arriveSheetStatus);//到达联状态集合
		return arrivesheetDao.queryArriveSheetByLifeCycle(dto);
	}

	/**
	 * 
	 * 根据运单号查询未签收的到达联集合
	 * 返单签收关联签收原单优化
	 * @param waybillNo 运单号
	 * @return
	 * @author 231438-FOSS-chenjunying
	 */
	@Override
	public List<ArriveSheetEntity> queryArriveSheetListNoSign(String waybillNo) {
		ArriveSheetDto dto = new ArriveSheetDto();
		//运单号
		dto.setWaybillNo(waybillNo);
		//有效
		dto.setActive(FossConstants.ACTIVE);
		//未作废
		dto.setDestroyed(FossConstants.NO);
		//到达联状态集合
		List<String> arriveSheetStatus = new ArrayList<String>();
		//到达联状态 --未签收
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_GENERATE);
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_DELIVER);
		dto.setArriveSheetStatus(arriveSheetStatus);//到达联状态集合
		return arrivesheetDao.queryArriveSheetByLifeCycle(dto);
	}
	/**
	 * 
	 * 根据运单号查询派送中的到达联集合
	 * @param waybillNo 运单号
	 * @return
	 * @author 231438-FOSS-chenjunying
	 */
	@Override
	public List<ArriveSheetEntity> queryArriveSheetListDeliver(String waybillNo) {
		ArriveSheetDto dto = new ArriveSheetDto();
		//运单号
		dto.setWaybillNo(waybillNo);
		//有效
		dto.setActive(FossConstants.ACTIVE);
		//未作废
		dto.setDestroyed(FossConstants.NO);
		//到达联状态集合
		List<String> arriveSheetStatus = new ArrayList<String>();
		//到达联状态 --派送中
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_DELIVER);
		dto.setArriveSheetStatus(arriveSheetStatus);//到达联状态集合
		return arrivesheetDao.queryArriveSheetByLifeCycle(dto);
	}
	/**
	 * 生成到达联
	 * 
	 * @author foss-meiying
	 * @date 2013-1-29 上午10:02:43
	 * @parawaybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname 提货人名称 identifyType
	 *                证件类型 identifyCode 证件号码 situation 签收情况 arriveSheetGoodsQty
	 *                到达联件数 signGoodsQty 签收件数 signNote 签收备注 signTime 签收时间 status
	 *                到达联状态 isPdaSign 是否PDA签到 operateTime 签收操作时间 operator 操作人
	 *                operatorCode 操作人编码 operateOrgName 操作部门名称 operateOrgCode
	 *                操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes 打印次数
	 *                createUserName 创建人 createUserCode 创建人编码 createOrgName 创建部门
	 *                createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *                isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing
	 *                是否审批中
	 * @return
	 * @see
	 */
	@Override
	@Transactional
	public int addArriveSheetData(ArriveSheetEntity entity) {
		return arrivesheetDao.addArriveSheetData(entity);
	}

	/**
	 * 更改运单到达部门插入pkp临时表记录.
	 * 
	 * @param dto 更改到达部门用--原走货路径 dto.taskDetailId 运单号 dto.taskDetailType M
	 *            更改到达部门用--新走货路径 dto.taskDetailId 运单号 dto.taskDetailType
	 *            N（changeNew）
	 * @throws TfrBusinessException the tfr business exception
	 * @author ibm-wangfei
	 * @date 2013-3-5 10:40:09
	 */
	@Override
	public void insertONTempForPKP(AutoTaskDTO dto) {
		if (dto == null || StringUtil.isEmpty(dto.getTaskDetailId()) || StringUtil.isEmpty(dto.getTaskDetailType())) {
			throw new TfrBusinessException("必输字段必须录入");
		}
		Calendar cal = Calendar.getInstance();
		long startTime = cal.getTimeInMillis();
		LOGGER.info("insert PKP的temp开始时间：" + startTime);
		arrivesheetDao.insertONTempForPKP(dto);
		cal = Calendar.getInstance();
		LOGGER.info("insert PKP的temp截至时间：" + cal.getTimeInMillis());
		LOGGER.info("insert PKP的temp所用时间：" + (cal.getTimeInMillis() - startTime));
	}

	/**
	 * 单件出入库插入pkp临时表记录.
	 * 
	 * @param dto dto.taskDetailId 运单号 dto.taskDetailType 单票入库-I ；单票出库-O
	 *            dto.ioQty 单票出入库数量 dto.stockOrgCode 单票出入库部门CODE
	 * @throws TfrBusinessException the tfr business exception
	 * @author ibm-wangfei
	 * @date Feb 25, 2013 1:39:01 PM
	 */
	@Override
	public void insertIOTempForPKP(AutoTaskDTO dto) throws TfrBusinessException {
		if (dto == null || StringUtil.isEmpty(dto.getTaskDetailId()) || StringUtil.isEmpty(dto.getTaskDetailType()) || dto.getIoQty() == null || StringUtil.isEmpty(dto.getStockOrgCode())) {
			throw new TfrBusinessException("必输字段必须录入");
		}
		Calendar cal = Calendar.getInstance();
		long startTime = cal.getTimeInMillis();
		LOGGER.info("更新PKP的状态开始时间：" + startTime);
		arrivesheetDao.insertIOTempForPKP(dto);
		cal = Calendar.getInstance();
		LOGGER.info("更新PKP的状态截至时间：" + cal.getTimeInMillis());
		LOGGER.info("更新PKP的状态所用时间：" + (cal.getTimeInMillis() - startTime));
	}

	/**
	 * 
	 * 根据运单号，更新到达件数、到达时间
	 * 
	 * @param waybillNos
	 * @author ibm-wangfei
	 * @date Jun 14, 2013 3:26:44 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService#updateWaybillArriveInfo(java.lang.String[])
	 */
	@Override
	public void updateWaybillArriveInfo(String[] waybillNos) {
		if (waybillNos == null || waybillNos.length == 0) {
			return;
		}
		ArriveSheetWaybillDto dto = new ArriveSheetWaybillDto();
		dto.setArrayWaybillNos(waybillNos);
		List<ActualFreightDto> dtos = arrivesheetDao.queryWaybillAllQty(dto);
		if (CollectionUtils.isEmpty(dtos)) {
			return;
		}
		for (ActualFreightDto actualFreightDto : dtos) {
			arriveSheetManngerService.updateWaybillArriveDetialInfo(actualFreightDto);
		}
	}

	@Override
	@Transactional
	public void updateWaybillArriveDetialInfo(ActualFreightDto actualFreightDto) {
		if (actualFreightDto == null) {
			return;
		}
		// 开单件数
		Integer goodsQty = actualFreightDto.getGoodsQty() == null ? 0 : actualFreightDto.getGoodsQty();
		// 签收件数
		Integer signQty = actualFreightDto.getSignQty() == null ? 0 : actualFreightDto.getSignQty();
		// 库存件数
		Integer stockQty = actualFreightDto.getStockQty() == null ? 0 : actualFreightDto.getStockQty();
		// 到达件数
		Integer arriveGoodsQty = actualFreightDto.getArriveGoodsQty() == null ? 0 : actualFreightDto.getArriveGoodsQty();
		// 到达未出库件数
		Integer arriveNotoutGoodsQty = actualFreightDto.getArriveNotoutGoodsQty() == null ? 0 : actualFreightDto.getArriveNotoutGoodsQty();
		if ((signQty + stockQty) > arriveGoodsQty && arriveGoodsQty < goodsQty) {
			// 如果签收件数 + 库存件数 》 到达件数 并且 到达件数《 开单件数
			// 到达件数 = 签收件数 + 库存件数（与开单件数比较）
			arriveGoodsQty = (signQty + stockQty) > goodsQty ? goodsQty : (signQty + stockQty);
			// 到达未库存件数 = 到达件数 - 签收件数
			arriveNotoutGoodsQty = (arriveGoodsQty - signQty) < 0 ? 0 : (arriveGoodsQty - signQty);
			ActualFreightEntity actualFreight = new ActualFreightEntity();
			actualFreight.setWaybillNo(actualFreightDto.getWaybillNo());
			// 到达件数
			actualFreight.setArriveGoodsQty(arriveGoodsQty);
			// 到达件数
			actualFreight.setArriveNotoutGoodsQty(arriveNotoutGoodsQty);
			// 开单件数
			actualFreight.setArriveTime(actualFreightDto.getInStockDate());
			actualFreightDao.updateArriveByWaybillNo(actualFreight);
		}
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
				waybillSign = waybillSignResultDao.queryWaybillSignResult(wayEntity);
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
	 * 根据运单号集合修改合送编码 跟送货要求
	 * @param entity
	 * @return
	 */
	@Override
	public Integer updateTogetherSendCodeByWaybillNos(ArriveSheetDto dto){
		return arrivesheetDao.updateTogetherSendCodeByWaybillNos(dto);
	}
	
	/**
	 * 签收出库---根据条件分页查询到达联总数 快递相关
	 * 
	 * @author foss-yuting
	 * @date 2014-10-09 下午4:59:20
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #queryArriveSheetInfoCountByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto)
	 */
	public Long queryArriveSheetInfoCountByParamsExp(SignDto dto) {
		return arrivesheetDao.queryArriveSheetInfoCountByParamsExp(dto);
	}
	
	/**
	 * 签收出库---根据条件查询到达联 快递相关
	 * 
	 * @author foss-yuting
	 * @date 2014-10-09 下午4:59:27
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #queryArriveSheetInfoByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto,
	 *      int, int)
	 */
	
	@Override
	public List<SignArriveSheetDto> queryArriveSheetInfoByParamsExp(
			SignDto dto, int start, int limit) {
		return arrivesheetDao.queryArriveSheetInfoByParamsExp(dto, start, limit);
	}
	
	/** 根据条件查询到最新一次 达联信息 (分批签收用)。
	 * DMANA-9716
	 * @author 231438-foss-chenjunying
	 * @date 2015-03-25 09:13:45
	 * @return ArrivesheetEntity
	 * @see
	 */
	public ArriveSheetEntity queryArriveSheetBySignTimeDesc(ArriveSheetDto dto){
		return arrivesheetDao.queryArriveSheetBySignTimeDesc(dto);
	}
	
	/**
	 * 合伙人零担签收出库---根据条件分页查询到达联总数
	 * 
	 * @author foss-meiying
	 * @date 2013-1-30 下午4:59:20
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #queryArriveSheetInfoCountByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto)
	 */
	public Long queryPtpArriveSheetInfoCountByParams(SignDto dto) {
		return arrivesheetDao.queryPtpArriveSheetInfoCountByParams(dto);
	}
	
	/**
	 * 合伙人零担签收出库---根据条件查询到达联
	 * 
	 * @author foss-239284
	 * @date 2013-1-30 下午4:59:27
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #queryArriveSheetInfoByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto,
	 *      int, int)
	 */
	@Override
	public List<SignArriveSheetDto> queryPtpArriveSheetInfoByParams(SignDto dto, int start, int limit) {
		return arrivesheetDao.queryPtpArriveSheetInfoByParams(dto, start, limit);
	}
	
	/* 
	 * 合伙人快递自提签收出库---根据条件分页查询到达联总数
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService#queryPtpExpArriveSheetInfoCountByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto)
	 */
	@Override
	public Long queryPtpExpArriveSheetInfoCountByParams(SignDto dto) {
		return arrivesheetDao.queryPtpExpArriveSheetInfoCountByParams(dto);
	}
	
	/*
	 * 合伙人快递自提签收出库---根据条件分页查询到达联信息集合
	 *  (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService#queryPtpExpArriveSheetInfoByParams(com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto, int, int)
	 */
	@Override
	public List<SignArriveSheetDto> queryPtpExpArriveSheetInfoByParams(
			SignDto dto, int start, int limit) {
		return arrivesheetDao.queryPtpExpArriveSheetInfoByParams(dto,start,limit);
	}
	
	/**
	 * Sets the 运单签收结果service.
	 * 
	 * @param waybillSignResultService the new 运单签收结果service
	 */
	public void setWaybillSignResultService(IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * Gets the 运单签收结果service.
	 * 
	 * @return the 运单签收结果service
	 */
	public IWaybillSignResultService getWaybillSignResultService() {
		return waybillSignResultService;
	}

	/**
	 * Gets the actualFreightDao.
	 * 
	 * @return the actualFreightDao
	 */
	public IActualFreightDao getActualFreightDao() {
		return actualFreightDao;
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
	 * Sets the 营业部 Service实现.
	 * 
	 * @param saleDepartmentService the new 营业部 Service实现
	 */
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/**
	 * Sets the 根据部门Code获得名称及电话接口.
	 * 
	 * @param orgAdministrativeInfoService the new 根据部门Code获得名称及电话接口
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * Sets the 签收明细接口.
	 * 
	 * @param signDetailService the new 签收明细接口
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}

	/**
	 * Sets the 货物的入库、出库及查询库存service.
	 * 
	 * @param stockService the new 货物的入库、出库及查询库存service
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
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
	 * Sets the 运单管理服务.
	 * 
	 * @param waybillManagerService the new 运单管理服务
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * Gets the 结算签收Service.
	 * 
	 * @return the 结算签收Service
	 */
	public ILineSignService getLineSignService() {
		return lineSignService;
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

	/**
	 * Sets the 应收单服务.
	 * 
	 * @param billReceivableService the new 应收单服务
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * Sets the 运单通知Service.
	 * 
	 * @param handleQueryOutfieldService the new 运单通知Service
	 */
	public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	/**
	 * 
	 * <p>
	 * 员工服务<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-15
	 * @param employeeService void
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setSignStockJobService(ISignStockJobService signStockJobService) {
		this.signStockJobService = signStockJobService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	@Override
	public List<ArriveSheetEntity> queryArriveSheetByLifeCycle(
			ArriveSheetDto dto) {
		return arrivesheetDao.queryArriveSheetByLifeCycle(dto);
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setPickupService(IPickupService pickupService) {
		this.pickupService = pickupService;
	}

	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}

	public void setWaybillSignResultDao(IWaybillSignResultDao waybillSignResultDao) {
		this.waybillSignResultDao = waybillSignResultDao;
	}

}