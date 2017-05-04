/**
 * 总体说明	1、新增菜单“派送交单”
 *2、界面布局分【待交单运单】和【已交单运单】2个页签显示
 *3、需要对【待交单运单】列表和【已交单运单】列表做的功能做按钮操作权限控制（见下面的需求描述）
 *4、此需求只针对于零担
 *识别待交单运单	一、系统根据规则识别是系统自动交单还是人工交单（见下面规则描述），识别满足以下条件之一的运单
 *1、	有预计出发未到达的运单（已交接并发车但未到达）
 *2、	货物已到达未入库
 *3、	货物已入库
 *二、到达部门为本部门
三、运输方式为零担运单，不包含快递
自动交单进入排单池	一、系统识别同时满足以下4个条件的运单，可以自动交单给调度排单：
1、	货物在库存中
2、	货物有送货日期
3、	到达部门为当前部门
4、	在自动交单的设置范围之内
【自动交单管理：参见ASP-用户需求说明书-交单管理】：
（1）	若交单管理中，操作部门的“自动交当日运单”时间已设置，则在此时间内自动读取满足货物在库存中且库存件数等于开单件数、送货日期为交单之日的、无异常未处理的运单；
（2）	若交单管理中，操作部门的“自动交第二日运单”时间已设置，则在此时间范围内，系统自动读取货物在库存中，送货日期为【交单日+1日】的、无异常未处理的运单。
（3）	若交单管理的自动交单管理“只自动交入库位的运单”字段进行了勾选设置，表示在设置的自动交单之间内【第（1）、（2）规则的时间设置】，只能交已进行库位确认的运单。
（4）	若不满足上述自动交单的条件的，不自动交单，但可人工交单。
（5）	自动交单时发现有更改单单未受理和有异常未处理，自动过滤。
二、自动交单不能交单的情形（满足其中之一即可）：
1、	若自动交单的运单有更改单未处理；
2、	有异常未处理完毕
3、	运单未补录
4、	开单为网上支付未付款的。
三、自动交单运单流向规则说明：
1、	若自动交单的部门为非驻地到达部门，交单之后，运单流向此非驻地到达部门的排单池【创建派送单（新）功能】
2、	若自动交单为驻地派送部，则交单之后运单流入对应顶级车队的排单池【创建派送单(新)功能】，供车队进行排单。
3、	若自动交单为驻地部门，但无对应的车队，交单之后，运单自动流入此驻地部门的排单池【创建派送单（新）功能】
4、	以上货物的流向与现在创建派送单功能的查询规则的数据获取方向是一致的，可以对此规则进行参考。
四、若运单的件数已经全部排单，则不自动交单（如：交单的运单在以前的创建派送单已经创建，则更新过滤）
五、自动交单后，待排单列表的数据更新最新待交单状态，运单状态变为已交单。
查询待交单运单	一、默认查询：
1、满足以下条件的提货方式为“派送”属性的运单可以在交单界面查询出来：
（1）	已交接并发车但未到达（有预计到达时间）
（2）	货物已到达未入库
（3）	货物在库存中
2、同时满足运单目的站部门为本部门的货物。
二、相关部门查询：
1、针对以下功能做操作权限控制：
（1）【待交单运单】如下功能操做操作权限：
① 
② 
③ 
（2）【已交单运单】如下功能做操作权限：
① 
2、数据查询说明：
(1)	满足【规则1】的运单到达部门（包括驻地、非驻地、非驻地没有对应的顶级车队部门）可以查询出来；
(2)	若运单的到达部门为驻地有对应的顶级车队，顶级车队及顶级车队的下属组织也可以查询出来。
3、给配置权限的角色才能有相应的操作功能。
二、特定查询：
1、	通过单号查询时，具有唯一排他性，输入单号满足【规则一】的在列表中累加显示；
2、	通过单号查询时，满足【规则一】的提货方式为“自提“属性的货物也可以查询出来
3、	运单号查询设置可以批量查询，最多可以输入50个单号。
三、【待交单运单】列表说明：
1、根据查询条件显示查询的结果，查询条件有：
（1）	运单号
（2）	交接单/车次号
（3）	运输性质
（4）	提货方式
（5）	通知情况
（6）	货物状态
（7）	时间范围
（8）	预计送货日期
（9）	送货时间段
（10）	送货时间点
（11）	是否已库位确认
2、查询条件说明：
（1）运单号：运单编号
（2）交接单/车次号：通过交接单和车次号进行查询，具有优先级特定查询。
（3）运输性质：运单运输性质，默认全部运输性质
（4）提货方式：运单信息提货方式，默认显示“全部”
（5）通知情况：全部、通知成功、通知失败、未通知，默认显示全部
（6）货物状态“全部、预计到达、已到达、库存中、默认显示“库存中”，表示查询全部结果。
①	预计到达：表示货物已交接出发，而未进行货物到达确认件数，直接读取车辆任务的状态
②	已到达：已进行车辆到达确认但未卸车入库的货物，直接，直接读取车辆任务的状态。
③	库存中：货物已在库存中，未交单的运单
（7）时间范围：
①预计到达：当前时间向后推7天；
②已到达：当前时间向后推7天
③库存中：当前时间向前推7天
（8）预计送货日期：
①通知客户时记录的预计送货日期，直接获取；
②查询条件显示为日期的格式，有起止时间 
③默认查询区间为30天，可以调整日期查询
④在查询条件不勾选“预计送货日期“时，表示只查询没有预计送货日期的运单，勾选时，有预计送货日期的和没有预计送货日期的都可以查询出来，默认为勾选。 
（9）送货时间段：全部、全天、上午、下午；通知客户标记获取，默认显示全部
（10）送货时间点：通知客户标记获取，有则显示，无则不显示，显示最新记录，默认为空，表示显示全部。
（11）是否已库位确认：进入库存的货物是否进行库位确认操作，库存管理读取标记，默认不勾选。
5、界面信息显示：
（1）	默认显示20条固定宽度；
（2）	实行分页显示，每页默认显示50条；
（3）	界面增加可调整显示条数： 50、100、200、300；
三、【已交单运单】列表说明：
1、查询条件：
（1）	运单号
（2）	运输性质
（3）	提货方式
（4）	通知情况
（5）	预计送货日期
（6）	送货时间段
（7）	送货时间点
（8）	交单时间
（9）	排单状态
2、查询条件说明：
（1）	通知情况：包括“全部、通知成功、通知失败、未通知”，取值通知结果，默认显示为全部，表示查询全部通知结果。
（2）	运输性质：运单运输性质（不包含快递）
（3）	提货方式：运单信息提货方式，默认显示“全部”（不含快递）
（4）	预计送货日期：
①通知客户时记录的预计送货日期，直接获取；
②查询条件显示为日期的格式，若查询的送货日期选择当天，则可以查询到当天以前的送货日期的货物，
③默认查询区间为30天，可以调整日期查询
④默认日期显示为当天
（5）	送货时间段：全部、全天、上午、下午；通知客户标记获取，默认显示全部
（6）	送货时间点：通知客户标记获取，有则显示，无则不显示，显示最新记录，默认显示全部
（7）	交单时间：交单操作时间，默认为当前3天操作时间，可以调整查询
（8）	排单状态：派送单的状态，直接读取
3、数据查询说明：
(1)	满足【规则1】的运单到达部门（包括驻地、非驻地、非驻地没有对应的顶级车队部门）可以查询出来；
(2)	若运单的到达部门为驻地有对应的顶级车队，顶级车队及顶级车队的下属组织也可以查询出来。
4、给配置权限的角色才能有相应的操作功能。
5、查询无预计送货日期的运单，不采用之前的实现方式，直接将有预计送货日期和无送货日期单独作为字段进行组合和单独查询；
6、若交单之前运单已经排单，则不能再查询出来；
7、派送交单送货时间点查询，选择起止时间后，只能查询大于等于起始时间，小于等于截止时间的运单。
显示查询结果	一、通用操作功能：
1.	单选和多选
2.	界面操作功能：
（1）	【待交单运单】列表功能：
①	 
②	 
③	 
④	 只能单个修改通知信息。
⑤	 
⑥	 
⑦	 
⑧	 调度退回的运单用蓝色标识。
⑧ ：查询的结果票数统计显示。
（2）	【已交单运单】列表功能：
①	 
②	 
③	 
④ ：查询的结果票数统计显示。
2、导出：导出当前页面信息
三、查询结果显示：
1、【待交单运单】显示字段有：
（1）	运单号
（2）	运输性质
（3）	提货方式
（4）	待交件数
①默认为满足交单条件的未交单的件数；
②若是分配配载的运单，则显示每次交单的件数，若交单之前满足多个交单条件的即满足有预计到达时间、已到达、库存中，则整合显示。
（5）	开单件数
（6）	到达件数
（7）	库存件数
（8）	重量
（9）	体积
（10）	货物状态：多个状态取值最新状态，即优先级：库存中>已到达>预计到达
（11）	通知情况
（12）	预计送货日期
（13）	送货时间段
（14）	送货时间点
（15）	发票类型
（16）	预计到达时间
（17）	入库时间
（18）	调度退回原因
（19）	调度退回操作人
（20）	退回时间
2、待交单查询结果显示规则描述：
(1)	对开单件数与库存件数与到达件数不一致的进行颜色标识；
(2)	待交件数（可交件数）默认等于（开单件数-已交件数），可以进行修改交单，但不能大于（开单件数-已交件数）；多次交单会在已交单列表对此单号交单进行累加；
(3)	派送拉回的货物，做了拉回操作后，如果是部分拉回，已交件数会更新减掉，重新再待交单列表可以查询；
(4)	查询无预计送货日期的运单，不采用之前的实现方式，直接将有预计送货日期和无送货日期单独作为字段进行组合和单独查询：
①、	预计送货日期默认为空，限制范围为一次查询30天，
②、	默认查询有无预计送货日期都能查询出来，若选择“无预计送货日期”，不填写预计送货日期，则只查询无预计送货日期的货物；
③、	若有预计送货日期和无预计送货日期都选择，则查询无预计送货日期和在选定预计送货日期内的运单；
④、	若只选择预计送货日期，不选则“无预计送货日期的货物”，只查询在选择预计送货日期内的运单。
(5)	若交单之前运单已经排单，则不能再查询出来；
(6)	如果已交单列表的运单目的站发生了更改（非当前部门目的站），更改单会调用已交单的接口，删除已交单的运单；
(7)	部分交单的运单未排单，则采用规则6的方式剔除已交单运单；
(8)	若在查询之前运单已经排单（如再以前创建派送单功能已经排单，则无法查询出来运单交单）
(9)	派送拉回的货物可以交单的规则：
①	标记“派送拉回”时即剔除已交单件数和已排单件数；
②	若有选择约定送货日期原因的货物，直接进入待交单界面不生成异常；
③	若有选择其他拉回原因的货物，满足查询条件可以查询出来，同时生成一条“未处理”异常，待异常处理完之后即可交单；
④	派送拉回的货物在标记派送拉回后，即校验排单情况减掉已排信息。
3、【已交单运单】显示内容有：
（1）	运单号
（2）	运输性质
（3）	提货方式
（4）	交单件数
①默认为满足交单条件未交单的件数；
②若是分配配载的运单，则显示每次交单的件数，若交单之前满足多个交单条件的：即满足有预计到达时间、已到达、库存中，则整合显示。
（5）	开单件数
（6）	排单件数
（7）	重量
（8）	体积
（9）	通知情况
（10）	预计送货日期
（11）	送货时间段
（12）	送货时间点
（13）	发票类型
（14）	交单人
①系统交单的标记为“系统”
②人工交单标记为具体的人
③若存在多次交单，取最新的交单人
（15）	交单时间：取最新交单时间
（16）	排单状态：取值派送单状态，若一个运单存在在多个派送单中，显示多个记录和状态。
4、已交单运单查询和显示规则：
(1)	若果运单部分交单，剩余部分的运单的相关信息修改（如预计送货日期、发票信息等）不修改已交单运单里面相应的信息。
(2)	2、已交单列表的交单时间默认为当前时间往前推3天的时间
(3)	排单件数=已排单件数，排单状态取最新的状态
(4)	如果已交单列表的运单目的站发生了更改（非当前部门目的站），更改单会调用已交单的接口，删除已交单的运单；
(5)	5、派送拉回的货物，做了拉回操作后，如果是部分拉回，已交件数会更新减掉，重新再待交单列表可以查询；
修改通知信息	一、修改通知相关信息，并同步更新到通知信息中，修改的字段如下：
（1）	通知内容
（2）	发票信息
（3）	实际收货地址
参照需求：【ASP-用户需求说明书-派送通知收货地址优化需求】的修改方式。
（4）	预计送货日期
（5）	送货时间段
（6）	送货时间点
2、	修改通知信息只能是针对运单未排单的运单（即未保存为派送单之前的运单）
3、	点击“信息修改时“默认带出最新通知客户通知的相关信息
同步更改通知信息	1、	通知信息进行更改并记录最新信息；
2、	交单界面同步更新交单结果
人工交单入排单池	1、	人工通过查询条件筛选结果，人工确认那些运单可以进入排单池；
2、	只要能够查询出来的运单都可以交单（除限制条件外，见如下规则4描述）；
3、	人工交单可以单个选择和批量选择交单
4、	以下情况人工不能交单：
a)	若人工交单运单有更改单未受理、
b)	有异常未处理完毕
c)	运单未补录
d)	网上支付未支付的运单
5、	人工批量交单提交时，若识别选择的运单有更改单未受理和有异常未处理，运单未补录，直接进行过滤。
6、	若交单的操作部门在【交单管理】功能的“人工能否未入库交单”处设置了“否”，则不允许人工交未入库的运单，若设置单操作了非库存的运单则提示“不能交未入库的运单”详情参见：ASP-用户需求说明书-交单管理。
7、	人工交单只能交送货日期为当前日期及后3天之内的运单。
8、	人工交单运单流向规则说明：
（1）	若自动交单的部门为非驻地到达部门，交单之后，运单流向此非驻地到达部门的排单池【创建派送单（新）功能】
（2）	若自动交单为驻地派送部，则交单之后运单流入对应顶级车队的排单池【创建派送单(新)功能】，供车队进行排单。
（3）	若自动交单为驻地部门，但无对应的车队，交单之后，运单自动流入此驻地部门的排单池【创建派送单（新）功能】
（4）	以上货物的流向与现在创建派送单功能的查询规则的数据获取方向是一致的，可以对此规则进行参考。

撤销交单	1、	若未创建派送单（保存派送单之前），则可以进行撤销交单操作
排单请参考需求：ASP-用户需求说明书-创建派送单（新）
提示无法撤销	若已交单运单已分车；则提示“此运单已排单，无法进行撤销操作”
晚交单规则	若当日交单时间不在设定的“自动交单（当日及之前）和“自动交单（第二日及之前）”的时间内，则交单运单标记为晚交单，后台标识，分单时进行调用。 

 */
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IHandoverBillInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HandoverBillInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAutoDeliverHandoverDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverHandoverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverHandoverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverHandoverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AutoDeliverHandoverEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillOtherDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillReturnDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PermissionControlDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PreDeliverHandoverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PreDeliverHandoverbillQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverHandoverbillException;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillChargeDtlService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 派送交单service
 * @author 159231 meiying
 * 2015-4-20  上午11:24:28
 */
public class DeliverHandoverbillService implements IDeliverHandoverbillService
{ 
	
	/** 
	 * The Constant LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeliverHandoverbillService.class);
	private IDeliverHandoverbillDao deliverHandoverbillDao;
	private IProductService productService;
	private INotifyCustomerService notifyCustomerService;
	private IAdministrativeRegionsService administrativeRegionsService;
	private IActualFreightService actualFreightService;
	public static final String DEFAULT_NULL = "N/A";
    /**
     * 部门查询起始.
     */
    private static final int BEGIN_NUM = 0;

    /**
     * 派送部查询页面大小.
     */
    private static final int PAGE_SIZE = 1;


    public void setWaybillChargeDtlService(IWaybillChargeDtlService waybillChargeDtlService) {
        this.waybillChargeDtlService = waybillChargeDtlService;
    }

    private  IWaybillChargeDtlService waybillChargeDtlService;
	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
    private IHandleQueryOutfieldService handleQueryOutfieldService;
    private IDeliverbillDao deliverbillDao;

    public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
        this.handleQueryOutfieldService = handleQueryOutfieldService;
    }

    public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }

    public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
        this.saleDepartmentService = saleDepartmentService;
    }

    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    /**
     * 营业部 Service接口
     */
    private ISaleDepartmentService saleDepartmentService;
    /**
     * 交单管理
     */
    private IHandoverBillInfoService HandoverBillInfoService;
    
	/**
	 * 到达联Service
	 */
	private IArriveSheetManngerService arriveSheetManngerService;
   /**
	 * 运单管理服务
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 组织信息
	 * service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IAutoDeliverHandoverDao autoDeliverHandoverDao;
	/**
	 * 查询待交单运单总数
	 * @author 159231 meiying
	 * 2015-4-21  上午8:47:36
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */

	@Override
	public Long queryPreDeliverHandoverCount(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto) {
		LOGGER.info("派送交单待交单查询COUNT参数：" + ReflectionToStringBuilder.toString(preDeliverHandoverbillQueryDto));
		if(preDeliverHandoverbillQueryDto==null){
			return null;
		}
		this.initPreDeliverHandoverbillQuery(preDeliverHandoverbillQueryDto);
		// 打印页面传入参数
        // 当前部门编码
        PermissionControlDto pre=initOrgRole();
        if(pre!= null && pre.isNext()){
            preDeliverHandoverbillQueryDto.setLastLoadOrgCode(pre.getLastLoadOrgCode());
            preDeliverHandoverbillQueryDto.setEndStockOrgCode(pre.getEndStockOrgCode());
            preDeliverHandoverbillQueryDto.setGoodsAreaCode(pre.getGoodsAreaCode());
            preDeliverHandoverbillQueryDto.setOrgRoleType(pre.getOrgRoleType());
        }else{
            return null;
        }
        //如果交接单号不为空
        if(StringUtils.isNotBlank(preDeliverHandoverbillQueryDto.getHandoverNo())){
        	return deliverHandoverbillDao.queryPreDeliverHandoverByhandoverNoCountCount(preDeliverHandoverbillQueryDto);
        }//如果车次号不为空
        else if(StringUtils.isNotBlank(preDeliverHandoverbillQueryDto.getVehicleAssembleNo())){
        	return deliverHandoverbillDao.queryPreDeliverHandoverByVehicleNoCount(preDeliverHandoverbillQueryDto);
        }//如果货物状态为已到达
        else if(StringUtils.isNotBlank(preDeliverHandoverbillQueryDto.getStockStatus()) 
        		&& TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED.equals(preDeliverHandoverbillQueryDto.getStockStatus())){
        	return deliverHandoverbillDao.queryPreDeliverHandoverArriveCount(preDeliverHandoverbillQueryDto);
        }//如果货物状态为预计到达
        else if(StringUtils.isNotBlank(preDeliverHandoverbillQueryDto.getStockStatus()) 
        		&& TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY.equals(preDeliverHandoverbillQueryDto.getStockStatus())){
        	return deliverHandoverbillDao.queryPreDeliverHandoverPreArriveCount(preDeliverHandoverbillQueryDto);
        }//如果货物状态为库存中
        else if(StringUtils.isNotBlank(preDeliverHandoverbillQueryDto.getStockStatus()) 
        		&& DeliverHandoverbillConstants.IN_STOCK.equals(preDeliverHandoverbillQueryDto.getStockStatus())){
        	return deliverHandoverbillDao.queryPreDeliverHandoverByStockCount(preDeliverHandoverbillQueryDto);
        }else{
        	return null;
        }
	}
    private PermissionControlDto initOrgRole(){
        String orgCode = FossUserContextHelper.getOrgCode();
        PermissionControlDto permissionControlDto = new PermissionControlDto();
        permissionControlDto.setNext(true);
        permissionControlDto.setOrgRoleType(0);
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
                    permissionControlDto.setOrgRoleType(1);
                    permissionControlDto.setLastLoadOrgCode(orgCode);
                    // 添加库存外场、库区默认查询条件
                    List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(orgCode);
                    if (CollectionUtils.isNotEmpty(list)) {
                        permissionControlDto.setEndStockOrgCode(list.get(0));
                        permissionControlDto.setGoodsAreaCode(list.get(1));
                    }
                } else
                {
                    // 查询排单服务外场
                    OrgAdministrativeInfoEntity transferCenter = this.orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoOfConnOutfieldByCode(orgCode);
                    String orgCode1 = null;
                    permissionControlDto.setOrgRoleType(0);
                    //
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
                            orgCode1 = salesList.get(0).getCode();
                            // 添加库存外场、库区默认查询条件
                            List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(orgCode1);
                            if (CollectionUtils.isNotEmpty(list)) {
                                permissionControlDto.setEndStockOrgCode(list.get(0));
                                permissionControlDto.setGoodsAreaCode(list.get(1));
                            }
                            permissionControlDto.setLastLoadOrgCode(orgCode1);
                        }else{
                            permissionControlDto.setNext(false);
                            return permissionControlDto;
                        }
                    }else{
                        permissionControlDto.setNext(false);
                        return permissionControlDto;
                    }

                }
                return permissionControlDto;
            } else
            {
                permissionControlDto.setNext(false);
                return permissionControlDto;
            }

        } else
        {
            permissionControlDto.setNext(false);
            return permissionControlDto;
        }
    }
    
	/**
	 * 初始化查询待交单运单查询条件
	 * @author 159231 meiying
	 * 2015-4-21  上午9:13:32
	 * @param preDeliverHandoverbillQueryDto
	 */
	private void initPreDeliverHandoverbillQuery(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto){
		preDeliverHandoverbillQueryDto.setActive(FossConstants.YES);
		// 默认运单不等于空运、偏线 快递的，整车
		String productCodes[] = { ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE 
				,ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE,ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE,
				ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE,ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE};
		preDeliverHandoverbillQueryDto.setProductCodes(productCodes);
		// 默认查询中派送方式--派送
		preDeliverHandoverbillQueryDto.setReceiveMethodTmp(WaybillConstants.DELIVER_FREE);	
	}
    /**
     * 初始化查询交单运单查询条件
     * @author 159231 meiying
     * 2015-4-21  上午9:13:32
     * @param
     */
    private void initDeliverHandoverbillQuery(DeliverHandoverbillQueryDto deliverHandoverbillQueryDto){

        if (StringUtil.isNotBlank(deliverHandoverbillQueryDto.getWaybillNo())) {
            // 解析运单号为列表
            deliverHandoverbillQueryDto.setWaybillNoList(deliverHandoverbillQueryDto.getWaybillNo().split("\\n"));
        }
        if (null != deliverHandoverbillQueryDto.getWaybillNoList() && deliverHandoverbillQueryDto.getWaybillNoList().length > 0) {
            deliverHandoverbillQueryDto.setHandoverNo(null);
            deliverHandoverbillQueryDto.setVehicleAssembleNo(null);
            deliverHandoverbillQueryDto.setNoArrageBill(null);
            deliverHandoverbillQueryDto.setDeliverbillStatus(null);
        } else if (StringUtil.isNotBlank(deliverHandoverbillQueryDto.getHandoverNo())) {
            //如果车次号、交接单号不为空，则有运单号，以运单查询为主；
            //没有运单号，有车次号、交接单，以交接单号查询为主，忽略其他条件
            //运单号，交接单号，车次号都为空，走其他查询条件;
            deliverHandoverbillQueryDto.setVehicleAssembleNo(null);
            deliverHandoverbillQueryDto.setDeliverbillStatus(null);
        } else if (StringUtil.isNotBlank(deliverHandoverbillQueryDto.getVehicleAssembleNo())) {
            //如果车次号、交接单号不为空，则有运单号，以运单查询为主；
            //没有运单号，有车次号、交接单，以交接单号查询为主，忽略其他条件
            //运单号，交接单号，车次号都为空，走其他查询条件;
            deliverHandoverbillQueryDto.setDeliverbillStatus(null);
        }
        deliverHandoverbillQueryDto.setActive(FossConstants.YES);
    }
	/**
	 * 查询待交单运单List
	 * @author 159231 meiying
	 * 2015-4-21  上午9:08:38
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	@Override
	public List<PreDeliverHandoverbillDto> queryPreDeliverHandoverList(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto, int start, int limit) {
		List<PreDeliverHandoverbillDto> lists= new ArrayList<PreDeliverHandoverbillDto>();
		String selectRoleType=null;
		 //如果交接单号不为空
        if(StringUtils.isNotBlank(preDeliverHandoverbillQueryDto.getHandoverNo())){
        	lists= deliverHandoverbillDao.queryPreDeliverHandoverByhandoverNoList(preDeliverHandoverbillQueryDto, start, limit);
        }//如果车次号不为空
        else if(StringUtils.isNotBlank(preDeliverHandoverbillQueryDto.getVehicleAssembleNo())){
        	lists= deliverHandoverbillDao.queryPreDeliverHandoverByVehicleNoList(preDeliverHandoverbillQueryDto, start, limit);
        }//如果货物状态为已到达
        else if(StringUtils.isNotBlank(preDeliverHandoverbillQueryDto.getStockStatus()) 
        		&& TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED.equals(preDeliverHandoverbillQueryDto.getStockStatus())){
        	selectRoleType="ARRIVED";
            preDeliverHandoverbillQueryDto.setStockStatus(selectRoleType);
        	lists= deliverHandoverbillDao.queryPreDeliverHandoverArrive(preDeliverHandoverbillQueryDto, start, limit);
        }//如果货物状态为预计到达
        else if(StringUtils.isNotBlank(preDeliverHandoverbillQueryDto.getStockStatus()) 
        		&& TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY.equals(preDeliverHandoverbillQueryDto.getStockStatus())){
        	selectRoleType="ONTHEWAY";
            preDeliverHandoverbillQueryDto.setStockStatus(selectRoleType);
        	lists= deliverHandoverbillDao.queryPreDeliverHandoverPreArriveList(preDeliverHandoverbillQueryDto, start, limit);
        }//如果货物状态为库存中
        else if(StringUtils.isNotBlank(preDeliverHandoverbillQueryDto.getStockStatus()) 
        		&& DeliverHandoverbillConstants.IN_STOCK.equals(preDeliverHandoverbillQueryDto.getStockStatus())){
        	selectRoleType=DeliverHandoverbillConstants.IN_STOCK;
            preDeliverHandoverbillQueryDto.setStockStatus(DeliverHandoverbillConstants.IN_STOCK);
        	lists= deliverHandoverbillDao.queryPreDeliverHandoverByStockList(preDeliverHandoverbillQueryDto, start, limit);
        }else{
        	return null;
        }
        
        if(lists!=null && lists.size()>0){
        	HandoverBillInfoEntity hs=HandoverBillInfoService.queryHandoverBillInfoByDepartment(FossUserContextHelper.getOrgCode());
            for (PreDeliverHandoverbillDto p : lists) {
            	if(hs!=null && StringUtils.isNotBlank(hs.getArtificialMark())){
                	p.setArtificialMark(hs.getArtificialMark());
                }
            	if(StringUtils.isNotBlank(selectRoleType)){
            		p.setSelectRoleType(selectRoleType);
            	}
                p.setOrgRoleType(preDeliverHandoverbillQueryDto.getOrgRoleType());
                p.setNowDate(new Date());
            }
        }
        return lists;
	}
	/**
	 * 
	 * @author 159231 meiying
	 * 2015-6-4  下午2:21:13
	 * @param waybillNo
	 * @return
	 */
	private String[] distinctWaybillNos(String waybillNo){
		if(StringUtils.isNotBlank(waybillNo)){
			String[] waybillnos =waybillNo.replace("\r", "").split("\\n");
			List<String> list = new ArrayList<String>();  
	        for (int i=0; i<waybillnos.length; i++) {  
	            if(!list.contains(waybillnos[i])) {  
	                list.add(waybillnos[i]);  
	            }  
	        }  
	        if(list!= null && list.size()>0){
	        	String[] newStr =  list.toArray(new String[1]);
	        	return newStr;
	        }
		}
		return null;
	}
	/**
	 * 根据运单号集合查询待交单信息
	 * @author 159231 meiying
	 * 2015-5-19  下午6:17:25
	 * @param preDeliverHandoverbillQueryDto
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<PreDeliverHandoverbillDto> queryPreDeliverHandoverByWaybillNosList(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto, int start, int limit){
		List<PreDeliverHandoverbillDto> lists= new ArrayList<PreDeliverHandoverbillDto>();
		this.initPreDeliverHandoverbillQuery(preDeliverHandoverbillQueryDto); 
		preDeliverHandoverbillQueryDto.setReceiveMethodTmp(null);
		//家装项目的提货方式不希望交单、创建派送单 - start - 239284
		List<String> notReceiveMethodTmp = new ArrayList<String>();
		DataDictionaryEntity datas = DictUtil.getDataByTermsCode("PICKUPGOODSSPECIALDELIVERYTYPE");
		if (null != datas) {
			for (DataDictionaryValueEntity value : datas.getDataDictionaryValueEntityList()) {
				notReceiveMethodTmp.add(value.getValueCode());
			}
		}
		preDeliverHandoverbillQueryDto.setNotReceiveMethodTmp(notReceiveMethodTmp);
		//家装项目的提货方式不希望交单、创建派送单 - end - 239284
		preDeliverHandoverbillQueryDto.setStockTimeFrom(null);
		preDeliverHandoverbillQueryDto.setStockTimeTo(null);
		
		PermissionControlDto pre=initOrgRole();
        if(pre!= null && pre.isNext()){
            preDeliverHandoverbillQueryDto.setLastLoadOrgCode(pre.getLastLoadOrgCode());
            preDeliverHandoverbillQueryDto.setEndStockOrgCode(pre.getEndStockOrgCode());
            preDeliverHandoverbillQueryDto.setGoodsAreaCode(pre.getGoodsAreaCode());
            preDeliverHandoverbillQueryDto.setOrgRoleType(pre.getOrgRoleType());
        }else{
            return null;
        }
		if (StringUtil.isNotBlank(preDeliverHandoverbillQueryDto.getWaybillNo())) {
	            // 解析运单号为列表
			 preDeliverHandoverbillQueryDto.setWaybillNoList(distinctWaybillNos(preDeliverHandoverbillQueryDto.getWaybillNo()));
			 if(preDeliverHandoverbillQueryDto.getWaybillNoList()!=null &&preDeliverHandoverbillQueryDto.getWaybillNoList().length>0 ){
				 preDeliverHandoverbillQueryDto.setStockStatus(DeliverHandoverbillConstants.IN_STOCK);
				 //查在库的运单号
				 List<PreDeliverHandoverbillDto> listsStock= deliverHandoverbillDao.queryPreDeliverHandoverByStockList(preDeliverHandoverbillQueryDto, start, limit);

                 preDeliverHandoverbillQueryDto.setStockStatus(TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED);
				 //查已到达(已卸车未入库.UNLOADED, 建立卸车任务直接提交空任务未做入库操作。)的运单号, 直接拼装在SQL中
				 List<PreDeliverHandoverbillDto> listsArrive=deliverHandoverbillDao.queryPreDeliverHandoverArrive(preDeliverHandoverbillQueryDto, start, limit);

				  preDeliverHandoverbillQueryDto.setStockStatus(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);
				 //查预计到达的运单号
				 List<PreDeliverHandoverbillDto> listsPreArrive=deliverHandoverbillDao.queryPreDeliverHandoverPreArriveList(preDeliverHandoverbillQueryDto, start, limit);
				if (CollectionUtils.isEmpty(listsStock)
						 &&CollectionUtils.isEmpty(listsArrive)
						 &&CollectionUtils.isEmpty(listsPreArrive)){
					 return null;
				 }
				 HandoverBillInfoEntity hs=HandoverBillInfoService.queryHandoverBillInfoByDepartment(FossUserContextHelper.getOrgCode());
				 String[]waybillNos =preDeliverHandoverbillQueryDto.getWaybillNoList();
				 for (int i = 0; i < waybillNos.length; i++) {
					 PreDeliverHandoverbillDto dto = new PreDeliverHandoverbillDto();
					 boolean hasUsed=false;
					if(CollectionUtils.isNotEmpty(listsStock)){
						for (PreDeliverHandoverbillDto preDeliverHandoverbillDto : listsStock) {
							if(waybillNos[i].equals(preDeliverHandoverbillDto.getWaybillNo())){
								hasUsed=true;
								dto=preDeliverHandoverbillDto;
								break;
							}
						}
					}
					if(CollectionUtils.isNotEmpty(listsArrive)){
						for (PreDeliverHandoverbillDto preDeliverHandoverbillDto : listsArrive) {
							if(waybillNos[i].equals(preDeliverHandoverbillDto.getWaybillNo())){
								if(hasUsed){
									dto.setArriveGoodsQty(preDeliverHandoverbillDto.getArriveGoodsQty());
								}else{
									dto=preDeliverHandoverbillDto;
									hasUsed=true;
								}
								break;
							}
						}
					}
					if(CollectionUtils.isNotEmpty(listsPreArrive)){
						for (PreDeliverHandoverbillDto preDeliverHandoverbillDto : listsPreArrive) {
							if(waybillNos[i].equals(preDeliverHandoverbillDto.getWaybillNo())){
								if(hasUsed){
									dto.setPlanArriveTime(preDeliverHandoverbillDto.getPlanArriveTime());
								}else{
									hasUsed=true;
									dto=preDeliverHandoverbillDto;
								}
								break;
							}
						}
					}
					if(dto!=null && StringUtils.isNotBlank(dto.getWaybillNo())){
						if(hs!=null && StringUtils.isNotBlank(hs.getArtificialMark())){
							dto.setArtificialMark(hs.getArtificialMark());
		                }
						dto.setOrgRoleType(preDeliverHandoverbillQueryDto.getOrgRoleType());
		                dto.setNowDate(new Date());
						lists.add(dto);
					}
				}
			 }else{
				 return null;
			 }
		}else{
	    	 return null;
	     }
		return lists;
	}
	/**
	 * 查询已交单运单总数
	 * @author 159231 meiying
	 * 2015-4-21  上午9:08:47
	 * @param deliverHandoverbillQueryDto
	 * @return
	 */
	@Override
	public Long queryDeliverHandoverCount(
			DeliverHandoverbillQueryDto deliverHandoverbillQueryDto) {
        this.initDeliverHandoverbillQuery(deliverHandoverbillQueryDto);

        PermissionControlDto pre=initOrgRole();
        if(pre!= null && pre.isNext()){
            deliverHandoverbillQueryDto.setLastLoadOrgCode(pre.getLastLoadOrgCode());
            deliverHandoverbillQueryDto.setEndStockOrgCode(pre.getEndStockOrgCode());
            deliverHandoverbillQueryDto.setGoodsAreaCode(pre.getGoodsAreaCode());
            deliverHandoverbillQueryDto.setOrgRoleType(pre.getOrgRoleType());
        }else{
            return null;
        }

		return deliverHandoverbillDao.queryDeliverHandoverCount(deliverHandoverbillQueryDto);
	}
	/**
	 * 查询已交单运单List
	 * @author 159231 meiying
	 * 2015-4-21  上午9:08:50
	 * @param deliverHandoverbillQueryDto
	 * @return
	 */
	@Override
	public List<DeliverHandoverbillDto> queryDeliverHandoverList(
			DeliverHandoverbillQueryDto deliverHandoverbillQueryDto, int start, int limit) {

        List<DeliverHandoverbillDto> list =deliverHandoverbillDao.queryDeliverHandoverList(deliverHandoverbillQueryDto, start, limit);
        if(list!=null && list.size()>0){
            //如果界面上查询条件有排单状态，则取消查询派送单状态信息
            if(StringUtils.isNotBlank(deliverHandoverbillQueryDto.getDeliverbillStatus())){

            }else{//循环查询得到最新的派送单状态
                for (DeliverHandoverbillDto deliverHandoverbillDto : list) {
                        DeliverbillDto deliverbillDto = new DeliverbillDto();
                        deliverbillDto.setWaybillNo(deliverHandoverbillDto.getWaybillNo());
                        deliverbillDto=deliverbillDao.queryLastDeliverbillbyWaybill(deliverbillDto);
                        //得到最新一次的派送单状态
                        if(deliverbillDto!= null && StringUtils.isNotBlank(deliverbillDto.getStatus())){
                            deliverHandoverbillDto.setDeliverBillStatus(deliverbillDto.getStatus());
                        }

                    deliverHandoverbillDto.setOrgRoleType(deliverHandoverbillQueryDto.getOrgRoleType());
                }
            }
        	return list;
        }else{
            return null;
        }
	}
	
	
	/**
	 * 导出派送交单-待交单信息
	 * @author 159231 meiying
	 * 2015-4-28  下午6:10:24
	 * @param
	 * @return
	 */
	@Override
	public InputStream exportPreDeliverHandover(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto, int start, int limit) {
		List<PreDeliverHandoverbillDto> preList=new ArrayList<PreDeliverHandoverbillDto>(); 
    	if(StringUtil.isNotBlank(preDeliverHandoverbillQueryDto.getWaybillNo())){
    		preList =this.queryPreDeliverHandoverByWaybillNosList(preDeliverHandoverbillQueryDto, start, limit);
    	}else{
    		this.initPreDeliverHandoverbillQuery(preDeliverHandoverbillQueryDto);
   		 	PermissionControlDto pre=initOrgRole();
           if(pre!= null&& pre.isNext()){
               preDeliverHandoverbillQueryDto.setLastLoadOrgCode(pre.getLastLoadOrgCode());
               preDeliverHandoverbillQueryDto.setEndStockOrgCode(pre.getEndStockOrgCode());
               preDeliverHandoverbillQueryDto.setGoodsAreaCode(pre.getGoodsAreaCode());
               preDeliverHandoverbillQueryDto.setOrgRoleType(pre.getOrgRoleType());
           }else{
               return null;
           }
           preList =this.queryPreDeliverHandoverList(preDeliverHandoverbillQueryDto, start, limit);
    	}
		List<List<String>> rowList = new ArrayList<List<String>>();
		if(CollectionUtils.isNotEmpty(preList) && preList.size()>0){
			for (PreDeliverHandoverbillDto prebill : preList) {
				List<String> columnList = new ArrayList<String>();
				//运单号
				columnList.add(prebill.getWaybillNo());
				
				//运输性质
				ProductEntity  pro =productService.getProductByCache(prebill.getProductCode(),new Date());
				if(null != pro){
					columnList.add(pro.getName());//运输性质
				}else {
					columnList.add(prebill.getProductCode());//运输性质
				}
				//提货方式
				if(StringUtils.isNotBlank(prebill.getReceiveMethod())){
					//提货方式
					columnList.add(DictUtil.rendererSubmitToDisplay(prebill.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS));
				
				}else {
					columnList.add(prebill.getReceiveMethod());
				}
				//待交件数
				if(null != prebill.getBillQty()){
					columnList.add( prebill.getBillQty().toString());//待交件数
				}else {
					columnList.add(BigDecimal.ZERO.toString());//待交件数
				}
				//开单件数
				if(null != prebill.getGoodsQtyTotal()){
					columnList.add( prebill.getGoodsQtyTotal().toString());
				}else {
					columnList.add(BigDecimal.ZERO.toString());
				}
				//到达件数
				if(null != prebill.getArriveGoodsQty()){
					columnList.add( prebill.getArriveGoodsQty().toString());
				}else {
					columnList.add(BigDecimal.ZERO.toString());
				}
				//库存件数
				if(null != prebill.getStockGoodsQty()){
					columnList.add( prebill.getStockGoodsQty().toString());
				}else {
					columnList.add(BigDecimal.ZERO.toString());
				}
				//重量
				if(null != prebill.getGoodsWeight()){
					columnList.add(prebill.getGoodsWeight().toString());
				}else {
					columnList.add(BigDecimal.ZERO.toString());
				}
				//体积
				if(null != prebill.getGoodsVolume()){
					columnList.add(prebill.getGoodsVolume().toString());
				}else {
					columnList.add(BigDecimal.ZERO.toString());
				}
				//货物状态
				columnList.add(prebill.getStockStatus());
				//通知情况
				if(StringUtils.isNotBlank(prebill.getNoticeResult())){
					columnList.add(DictUtil.rendererSubmitToDisplay(prebill.getNoticeResult(), DictionaryConstants.PKP_NOTIFY_CUSTOMER_STATUS));
				}else{
					columnList.add("");
				}
				//预计送货日期
				columnList.add(DateUtils.convert(prebill.getPreDeliverDate(), DateUtils.DATE_TIME_FORMAT));
				//送货时间段
				columnList.add(prebill.getDeliveryTimeInterval());
				//送货时间点(起)
				columnList.add(prebill.getDeliveryTimeStart());
				//送货时间点(止)
				columnList.add(prebill.getDeliveryTimeOver());
				//发票类型
				columnList.add(DictUtil.rendererSubmitToDisplay(prebill.getInvoiceType(), DictionaryConstants.PKP_RECEIPT_INVOICE_TYPE));
				//预计到达时间
				columnList.add(DateUtils.convert(prebill.getPlanArriveTime(), DateUtils.DATE_TIME_FORMAT));
				//入库时间
				columnList.add(DateUtils.convert(prebill.getInStockTime(), DateUtils.DATE_TIME_FORMAT));
				//调度退回原因
				if(StringUtils.isNotBlank(prebill.getDeliverReturnReason())){
					columnList.add(DictUtil.rendererSubmitToDisplay(prebill.getDeliverReturnReason(), DictionaryConstants.PKP_VISIBLE_WAYBILL_RETURN));
				
				}else {
					columnList.add(prebill.getDeliverReturnReason());
				}
				//调度退回操作人
				columnList.add(prebill.getDeliverReturnOperate());
				//退回时间
				columnList.add(DateUtils.convert(prebill.getDeliverReturnTime(), DateUtils.DATE_TIME_FORMAT));
				rowList.add(columnList);
			}
			String[] rowHeads = {"运单号","运输性质","提货方式","待交件数","开单件数","到达件数","库存件数",
					"重量","体积","货物状态","通知情况","预计送货日期","送货时间段","送货时间点(起)",
					"送货时间点(止)","发票类型","预计到达时间","入库时间","调度退回原因","调度退回操作人","退回时间"};
			
			ExportResource exportResource = new ExportResource();
			exportResource.setHeads(rowHeads);
			exportResource.setRowList(rowList);
			ExportSetting exportSetting = new ExportSetting();
			exportSetting.setSheetName("派送待交单信息");
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			return objExporterExecutor.exportSync(exportResource, exportSetting);
		}else {
			return null;
		}
		
	}
	
	/**
	 * 导出派送交单-交单信息
	 * @author 159231 meiying
	 * 2015-4-28  下午6:10:24
	 * @param
	 * @return
	 */
	@Override
	public InputStream exportDeliverHandover(DeliverHandoverbillQueryDto dto, int start, int limit) {
		this.initDeliverHandoverbillQuery(dto);
		PermissionControlDto pre=initOrgRole();
        if(pre!= null && pre.isNext()){
        	dto.setLastLoadOrgCode(pre.getLastLoadOrgCode());
        	dto.setEndStockOrgCode(pre.getEndStockOrgCode());
        	dto.setGoodsAreaCode(pre.getGoodsAreaCode());
        	dto.setOrgRoleType(pre.getOrgRoleType());
        }else{
            return null;
        }
		List<DeliverHandoverbillDto> list =this.queryDeliverHandoverList(dto, start, limit);
		List<List<String>> rowList = new ArrayList<List<String>>();
		if(CollectionUtils.isNotEmpty(list)){
			for (DeliverHandoverbillDto bill : list) {
				List<String> columnList = new ArrayList<String>();
				//运单号
				columnList.add(bill.getWaybillNo());
				
				//运输性质
				ProductEntity  pro =productService.getProductByCache(bill.getProductCode(),new Date());
				if(null != pro){
					columnList.add(pro.getName());//运输性质
				}else {
					columnList.add(bill.getProductCode());//运输性质
				}
				//提货方式
				if(StringUtils.isNotBlank(bill.getReceiveMethod())){
					//提货方式
					columnList.add(DictUtil.rendererSubmitToDisplay(bill.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS));
				
				}else {
					columnList.add(bill.getReceiveMethod());
				}
				//交单件数
				if(null != bill.getBillQty()){
					columnList.add( bill.getBillQty().toString());
				}else {
					columnList.add(BigDecimal.ZERO.toString());
				}
				//开单件数
				if(null != bill.getGoodsQtyTotal()){
					columnList.add( bill.getGoodsQtyTotal().toString());
				}else {
					columnList.add(BigDecimal.ZERO.toString());
				}
				//排单件数
				if(null != bill.getArrangeGoodsQty()){
					columnList.add( bill.getArrangeGoodsQty().toString());
				}else {
					columnList.add(BigDecimal.ZERO.toString());
				}
				//重量
				if(null != bill.getGoodsWeight()){
					columnList.add(bill.getGoodsWeight().toString());
				}else {
					columnList.add(BigDecimal.ZERO.toString());
				}
				//体积
				if(null != bill.getGoodsVolume()){
					columnList.add(bill.getGoodsVolume().toString());
				}else {
					columnList.add(BigDecimal.ZERO.toString());
				}
				
				//通知情况
				if(StringUtils.isNotBlank(bill.getNoticeContent())){
					columnList.add(DictUtil.rendererSubmitToDisplay(bill.getNoticeContent(), DictionaryConstants.PKP_NOTIFY_CUSTOMER_STATUS));
				}else{
					columnList.add("");
				}
				//预计送货日期
				columnList.add(DateUtils.convert(bill.getPreDeliverDate(), DateUtils.DATE_TIME_FORMAT));
				//送货时间段
				columnList.add(bill.getDeliveryTimeInterval());
				//送货时间点(起)
				columnList.add(bill.getDeliveryTimeStart());
				//送货时间点(止)
				columnList.add(bill.getDeliveryTimeOver());
				//发票类型
				columnList.add(DictUtil.rendererSubmitToDisplay(bill.getInvoiceType(), DictionaryConstants.PKP_RECEIPT_INVOICE_TYPE));
				columnList.add(bill.getInvoiceDetail());
				//交单人
				columnList.add(bill.getBillOperateName());
				//交单时间
				columnList.add(DateUtils.convert(bill.getBillTime(), DateUtils.DATE_TIME_FORMAT));
				//排单状态
				if(StringUtils.isNotBlank(bill.getDeliverBillStatus())){
					columnList.add(DictUtil.rendererSubmitToDisplay(bill.getDeliverBillStatus(), DictionaryConstants.PKP_DELIVERBILL_STATUS));
				
				}else {
					columnList.add(bill.getDeliverBillStatus());
				}
				rowList.add(columnList);
			}
			String[] rowHeads = {"运单号","运输性质","提货方式","交单件数","开单件数","排单件数",
					"重量","体积","通知情况","预计送货日期","送货时间段","送货时间点(起)",
					"送货时间点(止)","发票类型","发票备注","交单人","交单时间","排单状态"};
			
			ExportResource exportResource = new ExportResource();
			exportResource.setHeads(rowHeads);
			exportResource.setRowList(rowList);
			ExportSetting exportSetting = new ExportSetting();
			exportSetting.setSheetName("交单信息");
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			return objExporterExecutor.exportSync(exportResource, exportSetting);
		}else {
			return null;
		}
		
	}
	/**
	 * 设置deliverHandoverbillDao  
	 * @param deliverHandoverbillDao deliverHandoverbillDao 
	 */
	public void setDeliverHandoverbillDao(
			IDeliverHandoverbillDao deliverHandoverbillDao) {
		this.deliverHandoverbillDao = deliverHandoverbillDao;
	}
	/**
	 * 改最后一次通知信息
	 * @author 159231 meiying
	 * 2015-4-30  下午3:15:50
	 * @param notify
	 * @return
	 */
	@Override
	@Transactional
	public NotificationEntity updateLastNotifyByWaybillNo(
			NotificationEntity notify) {
		if(notify!= null && StringUtils.isNotBlank(notify.getWaybillNo())){
			if(StringUtils.isBlank(notify.getDeliveryTimeStart())){
				notify.setDeliveryTimeStart(DEFAULT_NULL);
			}
			if(StringUtils.isBlank(notify.getDeliveryTimeOver())){
				notify.setDeliveryTimeOver(DEFAULT_NULL);
			}
			if(StringUtils.isBlank(notify.getDeliveryTimeInterval())){
				notify.setDeliveryTimeInterval(DEFAULT_NULL);
			}
			if(StringUtils.isBlank(notify.getInvoiceType())){
				notify.setInvoiceType(DEFAULT_NULL);
			}
			if(notifyCustomerService.updatePartByPrimaryKeySelective(notify)>0){
				ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
				actualFreightEntity.setWaybillNo(notify.getWaybillNo());
				actualFreightEntity.setDeliveryTimeInterval(notify.getDeliveryTimeInterval());
				actualFreightEntity.setDeliveryTimeOver(notify.getDeliveryTimeOver());
				actualFreightEntity.setDeliveryTimeStart(notify.getDeliveryTimeStart());
				actualFreightEntity.setInvoiceType(notify.getInvoiceType());
				actualFreightEntity.setModifyTime(new Date());
				actualFreightEntity.setDeliverDate(DateUtils.convert(notify.getDeliverDate(), DateUtils.DATE_FORMAT));
				actualFreightService.updatePartNotifyByWaybillNo(actualFreightEntity);
				 //将送货要求信息同步到到达联 
                ArriveSheetDto dto =new ArriveSheetDto();//NotificationEntity
                String [] waybillNos={notify.getWaybillNo()};
                dto.setWaybillNos(waybillNos);
    			if(StringUtils.isBlank(notify.getDeliverRequire())){
    				throw new DeliverHandoverbillException("送货要求不能为空!");
    			}else{
    				dto.setDeliverRequire(notify.getDeliverRequire());
    			}
                arriveSheetManngerService.updateTogetherSendCodeByWaybillNos(dto);
			}else{
				throw new DeliverHandoverbillException("修改通知信息失败!");
			}
		}else{
			throw new DeliverHandoverbillException("传入信息为空,修改失败");
		}
		return null;
	}
	/**
	 * 修改预计送货日期
	 * 
	 * 一、修改通知相关信息，并同步更新到通知信息中，修改的字段如下：
     *     	（1）	通知内容
	 *	         （2）	发票信息
		（3）	实际收货地址
		参照需求：【ASP-用户需求说明书-派送通知收货地址优化需求】的修改方式。
		（4）	预计送货日期
		（5）	送货时间段
		（6）	送货时间点
		2、	修改通知信息只能是针对运单未排单的运单（即未保存为派送单之前的运单）
		3、	点击“信息修改时“默认带出最新通知客户通知的相关信息
		1、	通知信息进行更改并记录最新信息；
		2、	交单界面同步更新交单结果

	 * @author 159231 meiying
	 * 2015-4-21  上午9:08:53
	 * @param
	 * @return
	 */
	@Override
	@Transactional
	public int updatePreDeliverDateByWaybillNo(
			PreDeliverHandoverbillQueryDto dto) {
		if(dto!= null && dto.getWaybillNoList().length>0  && dto.getDeliverDate()!=null){
			for (int i = 0; i < dto.getWaybillNoList().length; i++) {
				NotificationEntity notificationEntity = new NotificationEntity();
				notificationEntity.setWaybillNo(dto.getWaybillNoList()[i]);
				notificationEntity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
                notificationEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
				NotificationEntity notify1 =notifyCustomerService.queryLastNotifyByWaybillNo(notificationEntity);
				if(notify1!=null){
					notificationEntity.setId(notify1.getId());
					notificationEntity.setDeliverDate(DateUtils.convert(dto.getDeliverDate()));
					notificationEntity.setModifyDate(new Date());
					notifyCustomerService.updatePartByPrimaryKeySelective(notificationEntity);
				}
			}
			//修改actual_freight送货日期
			ActualFreightDto acdto = new ActualFreightDto();
			acdto.setWaybillNoList(dto.getWaybillNoList());
			acdto.setDeliverDate(dto.getDeliverDate());
            acdto.setUpDeliverDateOrgCode(FossUserContextHelper.getOrgCode());
			if(actualFreightService.updateDeliverDateByWaybillNos(acdto)>0){
				return 1;
			}else{
				throw new DeliverHandoverbillException("修改失败,请重试!");
			}
		}else{
			throw new DeliverHandoverbillException("修改失败,请重试!");
		}
	} 
	/**
	 * 派送交单
	 * @author 159231 meiying
	 * 2015-4-30  下午3:16:02
	 * @param dtos
	 */
	@Override
	@Transactional
	public void executePreDeliverbill(List<PreDeliverHandoverbillDto> dtos) {
		if(dtos!= null ){
			String lateNo=FossConstants.NO;
			HandoverBillInfoEntity hs=HandoverBillInfoService.queryHandoverBillInfoByDepartment(FossUserContextHelper.getOrgCode());
			if(hs!=null){
				if(hs.getEnddateOneday()!=null && hs.getEnddateTwoday()!=null && hs.getEnddateOneday().compareTo(hs.getEnddateTwoday())<0){
		            SimpleDateFormat myFmt2=new SimpleDateFormat("HH:mm:ss");
		            String nowHMS= myFmt2.format(new Date());
					if(nowHMS.compareTo(hs.getEnddateOneday())>0 && nowHMS.compareTo(hs.getEnddateTwoday())<0){
						lateNo=FossConstants.YES;
					}
				}
			}
			for (PreDeliverHandoverbillDto preDeliverHandoverbillDto : dtos) {
				MutexElement mutexElement = new MutexElement(preDeliverHandoverbillDto.getWaybillNo(), "运单号", MutexElementType.DELIVER_HANDOVERBILL);
				//互斥锁定
				boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
				//若未上锁
				if (!isLocked) {
					//抛出派送单异常
					throw new NotifyCustomerException(NotifyCustomerException.NOTIFY_UNDERWAY);
				}	
				DeliverHandoverbillEntity bill= new DeliverHandoverbillEntity();
				BeanUtils.copyProperties(preDeliverHandoverbillDto,bill);//把preDeliverHandoverbillDto里的内容复制到bill里
				NotificationEntity notificationEntity = new NotificationEntity();
				notificationEntity.setWaybillNo(preDeliverHandoverbillDto.getWaybillNo());
				notificationEntity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
                notificationEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
				NotificationEntity notify1 =notifyCustomerService.queryLastNotifyByWaybillNo(notificationEntity);
				if(notify1!=null){
					if(StringUtils.isNotBlank(notify1.getActualProvCode())||
							StringUtils.isNotBlank(notify1.getActualCityCode())||
							StringUtils.isNotBlank(notify1.getActualDistrictCode())){
						bill.setReceiveCustomerProvCode(notify1.getActualProvCode()==null?null:notify1.getActualProvCode());
						bill.setReceiveCustomerCityCode(notify1.getActualCityCode()==null?null:notify1.getActualCityCode());
						bill.setReceiveCustomerDistCode(notify1.getActualDistrictCode()==null?null:notify1.getActualDistrictCode());
						bill.setReceiveCustomerAddress(notify1.getActualAddressDetail()==null?null:notify1.getActualAddressDetail());
						bill.setReceiveCustomerAddressNote(notify1.getActualStreetn()==null?null:notify1.getActualStreetn());
					}
                    //是否会展货
                    if(StringUtils.isNotBlank(notify1.getIsException())){
                        bill.setIsExhibition(notify1.getIsExhibition());
                    }
                    
                    //是否空车出
                    if(StringUtils.isNotBlank(notify1.getIsEmptyCar())) {
                    	bill.setIsEmptyCar(notify1.getIsEmptyCar());
                    }
                    
                    bill.setDeliverRequire(notify1.getDeliverRequire());//送货要求
                    bill.setInvoiceDetail(notify1.getInvoiceDetail());
				}
                bill.setActive(FossConstants.ACTIVE);
                //人工交单
                bill.setBillType(DeliverHandoverbillConstants.BILL_TYPE_ARTIFICIAL);
                //交单操作人
                bill.setBillOperateCode(FossUserContextHelper.getUserCode());
                //交单操作人名称
                bill.setBillOperateName(FossUserContextHelper.getUserName());
                //交单操作部门CODE
                bill.setBillOperateOrgCode(FossUserContextHelper.getOrgCode());
                //交单操作部门名称
                bill.setBillOperateOrgName(FossUserContextHelper.getOrgName());
                bill.setLastLoadOrgCode(FossUserContextHelper.getOrgCode());
                bill.setBillTime(new Date());
                bill.setLateNo(lateNo);
                //通知结果
                bill.setNoticeContent(preDeliverHandoverbillDto.getNoticeResult()==null?null:preDeliverHandoverbillDto.getNoticeResult());
                DeliverHandoverbillEntity billEntity= new DeliverHandoverbillEntity();
                billEntity.setWaybillNo(preDeliverHandoverbillDto.getWaybillNo());
                billEntity.setActive(FossConstants.ACTIVE);
                billEntity=deliverHandoverbillDao.queryByWaybillNo(billEntity);
                if(billEntity!=null){
                    if(billEntity.getBillQty()>0 ){
                       if(preDeliverHandoverbillDto.getGoodsQtyTotal()>0 &&
                               preDeliverHandoverbillDto.getGoodsQtyTotal()>=bill.getBillQty()+preDeliverHandoverbillDto.getDeliverBillQty()){
                           bill.setBillQty(bill.getBillQty()+preDeliverHandoverbillDto.getDeliverBillQty());
                       }else{
                           businessLockService.unlock(mutexElement);
                           throw new DeliverHandoverbillException("单号"+preDeliverHandoverbillDto.getWaybillNo()+"可交单件数加已交单件数大于开单件数!");
                       }
                    }
                    String [] waybillNos ={preDeliverHandoverbillDto.getWaybillNo()};
                    PreDeliverHandoverbillQueryDto predto=new PreDeliverHandoverbillQueryDto();
                    predto.setActive(FossConstants.NO);
                    predto.setModifyTime(new Date());
                    predto.setWaybillNoList(waybillNos);
                    predto.setOldActive(FossConstants.ACTIVE);
                    deliverHandoverbillDao.updateBywaybillNOs(predto);
                }
                //是否超远派送
                List<WaybillChargeDtlEntity> waybillChargedtls=waybillChargeDtlService.queryChargeDtlEntityByNo(preDeliverHandoverbillDto.getWaybillNo());
                if(waybillChargedtls!= null){
                    for(WaybillChargeDtlEntity w: waybillChargedtls){
                        if(PricingConstants.PriceEntityConstants.PRICING_CODE_CCDDJS.equals(w.getPricingEntryCode())){
                            bill.setUitraLongDelivery(FossConstants.ACTIVE);
                            break;
                        }
                    }
                }
                if(deliverHandoverbillDao.insertSelective(bill)>0){
                    //修改actual_freight送货日期
                    ActualFreightDto acdto = new ActualFreightDto();
                    String[] waybillNoList={preDeliverHandoverbillDto.getWaybillNo()};
                    acdto.setWaybillNoList(waybillNoList);
                    acdto.setDeliverBillQtyAdd(preDeliverHandoverbillDto.getBillQty());//交单件数
                    acdto.setOldDeliverBillQty(preDeliverHandoverbillDto.getDeliverBillQty());//原交单件数
                    if(actualFreightService.updateDeliverDateByWaybillNos(acdto)>0){
                        businessLockService.unlock(mutexElement);
                    }else{
                        businessLockService.unlock(mutexElement);
                        throw new DeliverHandoverbillException("单号"+preDeliverHandoverbillDto.getWaybillNo()+"交单失败!");
                    }

                }else{
                    businessLockService.unlock(mutexElement);
                    throw new DeliverHandoverbillException("单号"+preDeliverHandoverbillDto.getWaybillNo()+"交单失败!");
                }
                businessLockService.unlock(mutexElement);
			}
			
		}
	}
	
	/**
	 * 根据运单号查询最后一次通知的信息
	 * @author 159231 meiying
	 * 2015-4-30  下午5:18:48
	 * @param dto
	 * @return
	 */
	@Override
	public NotificationEntity queryLastNotifyByWaybillNo(
			PreDeliverHandoverbillDto dto) {
		if(dto!=null &&StringUtils.isNotBlank(dto.getWaybillNo())){
			NotificationEntity notificationEntity = new NotificationEntity();
			notificationEntity.setWaybillNo(dto.getWaybillNo());
			notificationEntity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
            notificationEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
			NotificationEntity notify =notifyCustomerService.queryLastNotifyByWaybillNo(notificationEntity);
			if(notify!= null){
				AdministrativeRegionsEntity entity;
				if(StringUtils.isNotBlank(notify.getActualProvCode())||
						StringUtils.isNotBlank(notify.getActualCityCode())||StringUtils.isNotBlank(notify.getActualDistrictCode())	){
					// 根据编码查询 -- 省
					if (StringUtil.isNotBlank(dto.getReceiveCustomerProvCode())) {
						entity = administrativeRegionsService.queryAdministrativeRegionsByCode(notify.getActualProvCode());
						if (entity != null && StringUtil.isNotBlank(entity.getName())) {
							notify.setActualProvN(entity.getName());
						}
					}
					// 根据编码查询 -- 市
					if (StringUtil.isNotBlank(dto.getReceiveCustomerCityCode())) {
						entity = administrativeRegionsService.queryAdministrativeRegionsByCode(notify.getActualCityCode());
						if (entity != null && StringUtil.isNotBlank(entity.getName())) {
							notify.setActualCityN(entity.getName());
						}
					}
					// 根据编码查询 -- 区
					if (StringUtil.isNotBlank(dto.getReceiveCustomerDistCode())) {
						entity = administrativeRegionsService.queryAdministrativeRegionsByCode(notify.getActualDistrictCode());
						if (entity != null && StringUtil.isNotBlank(entity.getName())) {
							notify.setActualDistrictN(entity.getName());
						}
					}
					return notify;
				}else{
					notify.setActualAddressDetail(dto.getReceiveCustomerAddress());
					notify.setActualStreetn(dto.getReceiveCustomerAddressNote());
					
					// 根据编码查询 -- 省
					if (StringUtil.isNotBlank(dto.getReceiveCustomerProvCode())) {
						entity = administrativeRegionsService.queryAdministrativeRegionsByCode(dto.getReceiveCustomerProvCode());
						if (entity != null && StringUtil.isNotBlank(entity.getName())) {
							notify.setActualProvCode(dto.getReceiveCustomerProvCode());
							notify.setActualProvN(entity.getName());
						}
					}
					// 根据编码查询 -- 市
					if (StringUtil.isNotBlank(dto.getReceiveCustomerCityCode())) {
						entity = administrativeRegionsService.queryAdministrativeRegionsByCode(dto.getReceiveCustomerCityCode());
						if (entity != null && StringUtil.isNotBlank(entity.getName())) {
							notify.setActualCityCode(dto.getReceiveCustomerCityCode());
							notify.setActualCityN(entity.getName());
						}
					}
					// 根据编码查询 -- 区
					if (StringUtil.isNotBlank(dto.getReceiveCustomerDistCode())) {
						entity = administrativeRegionsService.queryAdministrativeRegionsByCode(dto.getReceiveCustomerDistCode());
						if (entity != null && StringUtil.isNotBlank(entity.getName())) {
							notify.setActualDistrictCode(dto.getReceiveCustomerDistCode());
							notify.setActualDistrictN(entity.getName());
						}
					}
					return notify;
				}
				
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	/**
	 * 设置productService  
	 * @param productService productService 
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	/**
	 * 设置notifyCustomerService  
	 * @param notifyCustomerService notifyCustomerService 
	 */
	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}
	/**
	 * 设置administrativeRegionsService  
	 * @param administrativeRegionsService administrativeRegionsService 
	 */
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	/**
	 * 撤销派送交单
	 * 撤销交单
	 * 1、	若未创建派送单（保存派送单之前），则可以进行撤销交单操作 排单请参考需求：ASP-用户需求说明书-创建派送单（新）
	 * 若已交单运单已分车；则提示“此运单已排单，无法进行撤销操作”
	 * @author 159231 meiying
	 * 2015-5-4  下午2:39:14
	 * @param
	 */
	@Override
    @Transactional
	public void revokedPreDeliverbill(
            PreDeliverHandoverbillQueryDto dto) {
        if(dto!=null && dto.getWaybillNoList()!=null && dto.getWaybillNoList().length>0){

            //修改actual_freight送货日期
            ActualFreightDto acdto = new ActualFreightDto();
            acdto.setWaybillNoList(dto.getWaybillNoList());
            acdto.setModifyTime(new Date());
            acdto.setDeliverBillQtyZero(0);
            acdto.setArrangeGoodsQty(1);
            int result =actualFreightService.updateDeliverDateByWaybillNos(acdto);
            if(result ==dto.getWaybillNoList().length) {
                dto.setActive(FossConstants.NO);
                dto.setModifyTime(new Date());
                dto.setOldActive(FossConstants.ACTIVE);
                deliverHandoverbillDao.updateBywaybillNOs(dto);
            }else{
            	throw new DeliverHandoverbillException("撤销失败,请刷新一下再试!已排运单不能撤销");
            }
        }else{
            throw new DeliverHandoverbillException("传入的信息为空,请刷新一下再试!");
        }

	}

	/**
	 * 派送拉回的货物，做了拉回操作后，如果是部分拉回，已交件数会更新减掉，重新再待交单列表可以查询
	 * @author 159231 meiying
	 * 2015-5-18  上午10:00:54
	 * @param dto
	 */
    @Override
    public void goodsBackReturnPreDeliverbill( DeliverHandoverbillReturnDto dto) {
        if(dto!=null && StringUtils.isNotBlank(dto.getWaybillNo()) && dto.getBillQty()>0){
            ActualFreightEntity en =actualFreightService.queryByWaybillNo(dto.getWaybillNo());
            if(en!= null && en.getDeliverBillQty()!=null &&
                    en.getDeliverBillQty() > 0 ){
                if(en.getDeliverBillQty()>dto.getBillQty()){
                    ActualFreightDto acdto = new ActualFreightDto();
                    acdto.setWaybillNo(dto.getWaybillNo());
                    acdto.setModifyTime(new Date());
                    acdto.setDeliverBillQtyReduce(dto.getBillQty());
                    acdto.setOldDeliverBillQty(en.getDeliverBillQty());
                    acdto.setDeliverDate(dto.getPreDeliverDate());
                    int result =actualFreightService.updateDeliverDateByWaybillNos(acdto);
                    if(result>0) {
                        DeliverHandoverbillEntity billEntity= new DeliverHandoverbillEntity();
                        billEntity.setWaybillNo(dto.getWaybillNo());
                        billEntity.setActive(FossConstants.ACTIVE);
                        billEntity.setModifyTime(new Date());
                        billEntity=deliverHandoverbillDao.queryByWaybillNo(billEntity);
                        if(billEntity!=null){
                            String [] waybillNos ={dto.getWaybillNo()};
                            PreDeliverHandoverbillQueryDto predto=new PreDeliverHandoverbillQueryDto();
                            predto.setActive(FossConstants.NO);
                            predto.setModifyTime(new Date());
                            predto.setWaybillNoList(waybillNos);
                            predto.setOldActive(FossConstants.ACTIVE);
                            deliverHandoverbillDao.updateBywaybillNOs(predto);
                            billEntity.setModifyTime(null);
                            billEntity.setBillQty(en.getDeliverBillQty()-dto.getBillQty());
                            deliverHandoverbillDao.insertSelective(billEntity);
                        }

                    }
                }else{
                    ActualFreightDto acdto = new ActualFreightDto();
                    acdto.setWaybillNo(dto.getWaybillNo());
                    acdto.setModifyTime(new Date());
                    acdto.setDeliverBillQtyZero(1);
                    acdto.setDeliverDate(dto.getPreDeliverDate());
                    acdto.setOldDeliverBillQty(en.getDeliverBillQty());
                    int result =actualFreightService.updateDeliverDateByWaybillNos(acdto);
                    if(result>0) {
                        String [] waybillNos ={dto.getWaybillNo()};
                        PreDeliverHandoverbillQueryDto predto=new PreDeliverHandoverbillQueryDto();
                        predto.setActive(FossConstants.NO);
                        predto.setModifyTime(new Date());
                        predto.setWaybillNoList(waybillNos);
                        predto.setOldActive(FossConstants.ACTIVE);
                        deliverHandoverbillDao.updateBywaybillNOs(predto);
                    }
                }

            }else{
            	if(dto.getPreDeliverDate()!=null){
            		ActualFreightDto acdto = new ActualFreightDto();
                    acdto.setWaybillNo(dto.getWaybillNo());
                    acdto.setModifyTime(new Date());
                    acdto.setDeliverDate(dto.getPreDeliverDate());
                    actualFreightService.updateDeliverDateByWaybillNos(acdto);
            	}
            }

        }

    }
    /**
     * 可视化排单，分区排单，创建派送单（新）里运单退回调用接口
     * @author 159231 meiying
     * 2015-5-18  上午9:57:56
     * @param dto
     */
    @Override
    public List<String> returnPreDeliverbill(
            DeliverHandoverbillReturnDto dto) {
        if(dto!=null && dto.getWaybillNos()!=null && dto.getWaybillNos().length>0){
        	List<String> failedWaybillNo=new ArrayList<String>();
        	for (int i = 0; i < dto.getWaybillNos().length; i++) {
        		 ActualFreightEntity en =actualFreightService.queryByWaybillNo(dto.getWaybillNos()[i]);
                 if(en!= null && en.getDeliverBillQty()!=null && en.getDeliverBillQty() > 0 ){
                	 if(en.getArrangeGoodsQty()!=null && en.getDeliverBillQty()-en.getArrangeGoodsQty()>0){
                		 if(en.getArrangeGoodsQty()<=0){
            			 	ActualFreightDto acdto = new ActualFreightDto();
            	            acdto.setWaybillNo(dto.getWaybillNos()[i]);
            	            acdto.setModifyTime(new Date());
            	            acdto.setDeliverBillQtyZero(0);
            	            acdto.setOldDeliverBillQty(en.getDeliverBillQty());
            	            int result =actualFreightService.updateDeliverDateByWaybillNos(acdto);
            	            if(result>0) {
            	            	DeliverHandoverbillEntity bill = new DeliverHandoverbillEntity();
            	            	bill.setActive(FossConstants.NO);
            	            	bill.setModifyTime(new Date());
            	            	bill.setWaybillNo(dto.getWaybillNos()[i]);
            	            	bill.setOldActive(FossConstants.ACTIVE);
            	                deliverHandoverbillDao.updatePartByParams(bill);
            	            }else{
            	            	failedWaybillNo.add(dto.getWaybillNos()[i]);
            	            }
                		 }else{
                			 ActualFreightDto acdto = new ActualFreightDto();
             	            acdto.setWaybillNo(dto.getWaybillNos()[i]);
             	            acdto.setModifyTime(new Date());
             	           acdto.setDeliverBillQtyReduce(en.getDeliverBillQty()-en.getArrangeGoodsQty());
             	            acdto.setOldDeliverBillQty(en.getDeliverBillQty());
             	            int result =actualFreightService.updateDeliverDateByWaybillNos(acdto);
             	            if(result>0) {
             	            	DeliverHandoverbillEntity bill = new DeliverHandoverbillEntity();
             	            	bill.setModifyTime(new Date());
             	            	bill.setWaybillNo(dto.getWaybillNos()[i]);
             	            	bill.setOldActive(FossConstants.ACTIVE);
             	            	bill.setBillQty(en.getArrangeGoodsQty());
             	                deliverHandoverbillDao.updateByWaybillNoSelective(bill);
             	            }else{
             	            	failedWaybillNo.add(dto.getWaybillNos()[i]);
             	            }
                		 }
                	 }else{
                		 failedWaybillNo.add(dto.getWaybillNos()[i]);
                	 }
                 }else{
                	 failedWaybillNo.add(dto.getWaybillNos()[i]);
                 }
			}
        	return failedWaybillNo;
        }else{
            throw new DeliverHandoverbillException("传入的信息为空,请刷新一下再试!");
        }

    }
    /**
     * 目的站发更改时提供接口
     * @author 159231 meiying
     * 2015-5-18  上午9:59:46
     * @param dto
     */
    @Override
    public void LastLoadOrgCodeChangeDeliverbill(
            DeliverHandoverbillReturnDto dto) {
        if(dto!=null && StringUtils.isNotBlank(dto.getWaybillNo())){
            ActualFreightEntity en =actualFreightService.queryByWaybillNo(dto.getWaybillNo());
            if(en!= null && en.getDeliverBillQty()!=null && en.getDeliverBillQty()>0){
                ActualFreightDto acdto = new ActualFreightDto();
                acdto.setWaybillNo(dto.getWaybillNo());
                acdto.setModifyTime(new Date());
                acdto.setDeliverBillQtyZero(0);
                int result =actualFreightService.updateDeliverDateByWaybillNos(acdto);
                if(result>0) {
                    String [] waybillNos ={dto.getWaybillNo()};
                    PreDeliverHandoverbillQueryDto predto=new PreDeliverHandoverbillQueryDto();
                    predto.setActive(FossConstants.NO);
                    predto.setModifyTime(new Date());
                    predto.setWaybillNoList(waybillNos);
                    predto.setOldActive(FossConstants.ACTIVE);
                    deliverHandoverbillDao.updateBywaybillNOs(predto);
                }
            }

        }

    }
    /**
	 * 初始化交单信息
	 * @author 159231 meiying
	 * 2015-4-21  上午9:13:32
	 * @param 
	 */
	private void initDeliverHandoverbill(DeliverHandoverbillEntity bill,WaybillEntity waybillEntity,ActualFreightEntity actual){
		bill.setProductCode(waybillEntity.getProductCode());//运输性质
		if(StringUtils.isNotBlank(actual.getNotificationOrgCode()) 
        		&& actual.getNotificationOrgCode().equals(waybillEntity.getLastLoadOrgCode())){
        	bill.setNoticeContent(actual.getNotificationResult()); //通知结果
        	bill.setDeliveryTimeInterval(actual.getDeliveryTimeInterval());//送货时间段
        	bill.setInvoiceType(actual.getInvoiceType());//发票类型
        	bill.setDeliveryTimeStart(actual.getDeliveryTimeStart());//送货时间点(起)
        	bill.setDeliveryTimeOver(actual.getDeliveryTimeOver());//送货时间点(止)
        	
        }else if(StringUtils.isNotBlank(actual.getUpDeliverDateOrgCode()) 
        		&& actual.getUpDeliverDateOrgCode().equals(waybillEntity.getLastLoadOrgCode())){
        	bill.setNoticeContent(actual.getNotificationResult()); //通知结果
        }else{
        	bill.setNoticeContent(NotifyCustomerConstants.NONE_NOTICE); //通知结果-未通知
        }
		bill.setReceiveMethod(waybillEntity.getReceiveMethod());// 提货方式
		bill.setPreDeliverDate(actual.getDeliverDate());//预计送货日期
		//收货省份
		bill.setReceiveCustomerProvCode(waybillEntity.getReceiveCustomerProvCode()==null?null:waybillEntity.getReceiveCustomerProvCode());
		//收货市
		bill.setReceiveCustomerCityCode(waybillEntity.getReceiveCustomerCityCode()==null?null:waybillEntity.getReceiveCustomerCityCode());
		//收货区
		bill.setReceiveCustomerDistCode(waybillEntity.getReceiveCustomerDistCode()==null?null:waybillEntity.getReceiveCustomerDistCode());
		//收货具体地址
		bill.setReceiveCustomerAddress(waybillEntity.getReceiveCustomerAddress()==null?null:waybillEntity.getReceiveCustomerAddress());
		//收货人地址备注
		bill.setReceiveCustomerAddressNote(actual.getReceiveCustomerAddressNote()==null?null:actual.getReceiveCustomerAddressNote());
	
		//开单件数
		bill.setGoodsQtyTotal(waybillEntity.getGoodsQtyTotal());
		//总重量
		bill.setGoodsWeight(waybillEntity.getGoodsWeightTotal());
		//总体积
		bill.setGoodsVolume(waybillEntity.getGoodsVolumeTotal());
		bill.setActive(FossConstants.ACTIVE);
        //人工交单
        bill.setBillType(DeliverHandoverbillConstants.BILL_TYPE_AUTO);
        //交单操作人
       // bill.setBillOperateCode("000000");
        //交单操作人名称
        bill.setBillOperateName("系统");
        //交单操作部门CODE
        bill.setBillOperateOrgCode(waybillEntity.getLastLoadOrgCode());
        // 获取操作部门
 		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillEntity.getLastLoadOrgCode());
     	if(org!=null){
     		//交单操作部门名称
            bill.setBillOperateOrgName(org.getName());
     	}
        bill.setLastLoadOrgCode(waybillEntity.getLastLoadOrgCode());//最终配载部门
        bill.setBillTime(new Date());//交单时间
        bill.setBillQty(waybillEntity.getGoodsQtyTotal());//交单件数
        //货物包装
        bill.setGoodsPackage(waybillEntity.getGoodsPackage());
        //货物尺寸
        bill.setGoodsSize(waybillEntity.getGoodsSize());
        //到付金额
        bill.setToPayAmount(waybillEntity.getToPayAmount());
        //是否超远派送
        List<WaybillChargeDtlEntity> waybillChargedtls=waybillChargeDtlService.queryChargeDtlEntityByNo(bill.getWaybillNo());
        if(waybillChargedtls!= null){
            for(WaybillChargeDtlEntity w: waybillChargedtls){
                if(PricingConstants.PriceEntityConstants.PRICING_CODE_CCDDJS.equals(w.getPricingEntryCode())){
                    bill.setUitraLongDelivery(FossConstants.ACTIVE);
                    break;
                }
            }
        }
        NotificationEntity notificationEntity = new NotificationEntity();
		notificationEntity.setWaybillNo(bill.getWaybillNo());
		notificationEntity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
        notificationEntity.setOperateOrgCode(waybillEntity.getLastLoadOrgCode());
		NotificationEntity notify1 =notifyCustomerService.queryLastNotifyByWaybillNo(notificationEntity);
		if(notify1!=null){
			if(StringUtils.isNotBlank(notify1.getActualProvCode())||
					StringUtils.isNotBlank(notify1.getActualCityCode())||
					StringUtils.isNotBlank(notify1.getActualDistrictCode())){
				bill.setReceiveCustomerProvCode(notify1.getActualProvCode()==null?null:notify1.getActualProvCode());
				bill.setReceiveCustomerCityCode(notify1.getActualCityCode()==null?null:notify1.getActualCityCode());
				bill.setReceiveCustomerDistCode(notify1.getActualDistrictCode()==null?null:notify1.getActualDistrictCode());
				bill.setReceiveCustomerAddress(notify1.getActualAddressDetail()==null?null:notify1.getActualAddressDetail());
				bill.setReceiveCustomerAddressNote(notify1.getActualStreetn()==null?null:notify1.getActualStreetn());
			}
            //是否会展货
            if(StringUtils.isNotBlank(notify1.getIsException())){
                bill.setIsExhibition(notify1.getIsExhibition());
            }
            
            //是否空车出
            if (StringUtils.isNotBlank(notify1.getIsEmptyCar())) {
            	bill.setIsEmptyCar(notify1.getIsEmptyCar());
            }
            
            bill.setDeliverRequire(notify1.getDeliverRequire());//送货要求
            bill.setInvoiceDetail(notify1.getInvoiceDetail());
		}
	}
	/**
	 * 派送交单
	 * @author 159231 meiying
	 * 2015-4-30  下午3:16:02
	 * @param
	 */
	 @Override
	 @Transactional
	public void executeAutoPreDeliverbill(AutoDeliverHandoverEntity pre) {
		if(pre!=null && StringUtils.isNotBlank(pre.getWaybillNo())){
			LOGGER.info("待自动交单运单执行自动交单开始："+pre.toString());
			
			MutexElement mutexElement = new MutexElement(pre.getWaybillNo(), "运单号", MutexElementType.DELIVER_HANDOVERBILL);
			//互斥锁定
			boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
			//若未上锁
			if (!isLocked) {
				//删除自动交单池中的运单
				autoDeliverHandoverDao.deleteByWaybillNo(pre.getWaybillNo());
				return;
			}	
			
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(pre.getWaybillNo());
			ActualFreightEntity actual = actualFreightService.queryByWaybillNo(pre.getWaybillNo());
			if(waybillEntity!=null && actual!=null){
				if(actual.getDeliverBillQty()!=null && actual.getDeliverBillQty()>0){
					autoDeliverHandoverDao.deleteByWaybillNo(pre.getWaybillNo());
					return;
				}
				DeliverHandoverbillEntity bill= new DeliverHandoverbillEntity();
				bill.setWaybillNo(pre.getWaybillNo());
				this.initDeliverHandoverbill(bill, waybillEntity,actual);
	            DeliverHandoverbillEntity billEntity= new DeliverHandoverbillEntity();
	            billEntity.setWaybillNo(pre.getWaybillNo());
	            billEntity.setActive(FossConstants.ACTIVE);
	            billEntity=deliverHandoverbillDao.queryByWaybillNo(billEntity);
	            if(billEntity!=null){
	                //删除要处理的运单
	            	autoDeliverHandoverDao.deleteByWaybillNo(pre.getWaybillNo());
	            	return;
	            }
	            if(deliverHandoverbillDao.insertSelective(bill)>0){
	                //修改actual_freight送货日期
	                ActualFreightDto acdto = new ActualFreightDto();
	                String[] waybillNoList={pre.getWaybillNo()};
	                acdto.setWaybillNoList(waybillNoList);
	                acdto.setDeliverBillQtyAdd(waybillEntity.getGoodsQtyTotal());//交单件数
	                acdto.setOldDeliverBillQty(0);//原交单件数
	                // 如果并发人工、系统同时交单，其中一步操作先将实际承运表的派送交单件数更新>0, 此处更新结果=0 
	                if(actualFreightService.updateDeliverDateByWaybillNos(acdto)>0){
	                	autoDeliverHandoverDao.deleteByWaybillNo(pre.getWaybillNo());
	                }else{
	                	/*String [] waybillNos ={pre.getWaybillNo()};
	                    PreDeliverHandoverbillQueryDto predto=new PreDeliverHandoverbillQueryDto();
	                    predto.setActive(FossConstants.NO);
	                    predto.setModifyTime(new Date());
	                    predto.setWaybillNoList(waybillNos);
	                    //下面只作废自动交单的记录 暂时未使用
	                    //predto.setBillType(DeliverHandoverbillConstants.BILL_TYPE_AUTO);
	                    predto.setOldActive(FossConstants.ACTIVE);
	                    deliverHandoverbillDao.updateBywaybillNOs(predto);*/
	                	//修改更新实际承运表失败时，只作废自动交单记录
	                	DeliverHandoverbillEntity entity = new DeliverHandoverbillEntity();
	                	entity.setId(bill.getId());
	                	entity.setActive(FossConstants.NO);
	                    deliverHandoverbillDao.updatePartByParams(entity);
	                	autoDeliverHandoverDao.deleteByWaybillNo(pre.getWaybillNo());
	                }
	            }else{
	            	autoDeliverHandoverDao.deleteByWaybillNo(pre.getWaybillNo());
	            }
	            LOGGER.info("待自动交单运单执行自动交单结束："+pre.toString());
			}else{
				return;
			}
			
			//解锁
			businessLockService.unlock(mutexElement);
		}
	}
	/**
	 * 设置actualFreightService  
	 * @param actualFreightService actualFreightService 
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}



    /**
	 * 设置businessLockService  
	 * @param businessLockService businessLockService 
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * 设置handoverBillInfoService  
	 * @param handoverBillInfoService handoverBillInfoService 
	 */
	public void setHandoverBillInfoService(
			IHandoverBillInfoService handoverBillInfoService) {
		HandoverBillInfoService = handoverBillInfoService;
	}

	/**
	 * 设置waybillManagerService  
	 * @param waybillManagerService waybillManagerService 
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * 设置orgAdministrativeInfoService  
	 * @param orgAdministrativeInfoService orgAdministrativeInfoService 
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置autoDeliverHandoverDao  
	 * @param autoDeliverHandoverDao autoDeliverHandoverDao 
	 */
	public void setAutoDeliverHandoverDao(
			IAutoDeliverHandoverDao autoDeliverHandoverDao) {
		this.autoDeliverHandoverDao = autoDeliverHandoverDao;
	}
	/**
	 *  根据传入的运单号集合＋active = 'Y'查询满足条件的运单
	 * @author 159231 meiying
	 * 2015-6-1  下午5:18:36
	 * @param pre
	 * @return
	 */
	@Override
	public List<DeliverHandoverbillEntity> queryDeliverHandoverbillByWaybillNos(
			DeliverHandoverbillOtherDto pre) {
		return deliverHandoverbillDao.queryDeliverHandoverbillByWaybillNos(pre);
	}
	/**
	 *  根据运单号集合＋active = 'Y'修改满足条件的信息
	 * @author 159231 meiying
	 * 2015-6-1  下午5:18:39
	 * @param pre
	 * @return
	 */
	@Override
	public int updatePartByWaybillNos(DeliverHandoverbillOtherDto pre) {
		return deliverHandoverbillDao.updatePartByWaybillNos(pre);
	}
	/**
	 * 修改派送交单信息
	 * @author 159231 meiying
	 * 2015-6-5  下午3:51:22
	 * @param entity
	 * @return
	 */
	@Override
	public int updateByWaybillNoSelective(DeliverHandoverbillEntity entity) {
		if(entity==null ||StringUtils.isBlank(entity.getWaybillNo())){
			throw new DeliverHandoverbillException("传入的运单号为空!不能进行当前操作");
		}else{
			return deliverHandoverbillDao.updateByWaybillNoSelective(entity);
		}
	}

    public void setDeliverbillDao(IDeliverbillDao deliverbillDao) {
        this.deliverbillDao = deliverbillDao;
    }

	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}
}