/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013  PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME : pkp-order
 * 
 * FILE PATH         : src/main/java/com/deppon/foss/module/pickup/order/server/service/impl/VehicleManageService.java
 * 
 * FILE NAME         : VehicleManageService.java
 * 
 * AUTHOR : FOSS接送货系统开发组
 * 
 * HOME PAGE : http://www.deppon.com
 * 
 * COPYRIGHT : Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 * 订单在调度系统中的状态  
 * 正常状态流向如下图所示
 * 若发生异常状态，则在 查询/处理订单任务（SUC-326）中对该任务进行状态修改（已开单、揽货失败两种状态不可进行修改）；
 * 订单状态与其它模块间的状态映射关系，由其它各模块各自维护，调度系统通过接口的形式传给调度订单状态给其它模块。
 * 
 * 
 * 序号	状态（订单调度）	对应营业部约车状态	状态描述	CRM对应订单状态	对应PDA系统状态
 * 1	未处理	未审核	接收到约车信息但还未受理；	已约车	/
 * 2	已派车	已受理	同意受理约车信息；	接货中	未读订单
 * 3	接货中	已受理	司机查看订单信息；	接货中	已读订单
 * 4	待改接	已受理	1、	司机无法接货或超出范围取消的订单，由调度再次受理；
 * 2、	调度主动要求改派的；	接货中	已取消订单
 * 5	已退回	驳回	拒绝受理约车信息；	已退回	/
 * 6	揽货失败	退回	由于客户原因，司机将订单退回到订单受理部门；	已退回	已取消订单
 * 7	已开单	确认车辆到达	完成接货且已开单；	已开单	已接订单
 * 
 * 1.5	
 * 操作步骤
 * 处理订单
 * 序号	基本步骤	相关数据	补充步骤
 * 1	点击订单调度页签，进入订单调度主界面		初始化页面，加载一部分数据，参见业务规则SR13
 * 2	录入查询条件	参看数据元素【订单查询条件】	查询条件参见业务规则SR5
 * 3	点击查询		参见业务规则SR1-SR4；处理营业部订单查询参见业务规则SR29；
 * 4	点击订单任务列表中的某一条记录		调用gis接口根据订单接货地址自动匹配出预处理建议，并填入处理订单区域的车牌号，为订单受理做准备。生成预处理建议参见业务规则SR6-SR10；
 * 5	点击受理按钮		信息确认之后，进行订单受理操作，司机收到接货任务的订单信息，并生成一条派车记录；
 * 同时，发送一条订单处理结果给客户。参见业务规则SR14-SR23、SR26-SR27。处理营业部订单参见业务规则SR30-SR31.
 * 
 * 扩展事件写非典型或异常操作过程
 * 序号	扩展事件	相关数据	备注
 * 2a	查询条件为空时，点击查询	参看数据元素【订单查询条件】	参见业务规则SR2
 * 3b	点击查询后，结果列表中没有数据显示		提示：“未查询到符合条件的订单！“
 * 4a	点击 退回		当操作员判断订单信息不全或有误时，进入 图8订单退回操作流程，必需选择或填入相应的原因才可以进行退回操作。
 * 参见业务规则SR14-18、SR27；
 * 4b	订单任务列表中没有预处理建议-车牌号		提示：“系统未匹配到预处理建议，请在可用车辆中选择！”
 * 参见业务规则SR9、SR10；
 * 4c	未勾选复选框就点击右下角批量受理按钮		提示：“请选择对应的订单任务后再进行受理！”
 * 4d	当前激活的订单任务没有预处理建议，就点击受理按钮		提示：“请对订单任务XXXXXXXX分配车辆后再进行受理！”
 * 4e	勾选多条订单任务后，点击主界面 图1右下方的受理按钮进行批量受理；若其中一条没有预处理建议		
 * 提示：“约车编号XXXX，订单任务号XXXX 没有进行分配，请分配后再次受理！”
 * 4f	若车辆装载信息有误，可点击 		弹出模式窗口 图10，修改当前车辆的剩余体积和剩余重量。
 * 4g	查看某条车辆信息的司机位置		调用GPS、电子地图接口，弹出模式窗口 图9。
 * 4h	勾选多条订单任务后在可用车辆信息中进行选择		只将最后一条勾选的（当前激活的）订单任务分配给所选车辆
 * 4i	点击  		调用gis系统接口，查看订单详细位置，参考 图3
 * 4j	若短信平台暂时不可用，鼠标悬停在短信发送复选框		打开图4，并可复制其中的短信内容
 * 5a	中转调度在受理约车时，若自有车辆不足时		参见业务规则SR28
 * 5b	图4 勾选订单任务后，扩展区域 若PDA发送和手机短信发送都没有勾选，点击受理		提示：“必需选择一种发送方式发送订单任务！”
 * 5c	若司机还未与车辆绑定，就点击受理		提示：“司机与车辆还未绑定，请建立绑定或排班之后再进行受理！”
 * 参见业务规则SR32
 * 
 * 1.6	
 * 业务规则
 * 序号	描述
 * SR1	
 * 查询到的待处理订单只能为车队服务区域营业部的订单；
 * SR2	
 * 查询订单时，若无选择筛选条件，则显示车队服务区域营业部的所有订单；
 * 查询完成之后，点击查询条件上 倒三角符号，收起查询条件，订单任务区域和可用车辆区域拉升至整个页面长度；
 * SR3	
 * 订单任务状态标记
 * a)	将待改接订单任务放在最上面，颜色标记不随时间变化而变化；
 * b)	处理时间超时用颜色标记；
 * c)	其余订单任务按照用车时间来进行升序排序，颜色的变化（处理时间超时根据约车时间来计算，其余两种状态根据用车时间来进行计算）；
 * d)	离用车还差15分钟用“!”标记，结束用车时间15分钟用“！！”标记；
 * e)	其计算放在客户端进行计算；
 * SR4	
 * 只显示订单状态为“未处理（包括超时未处理的订单）”、“待改接”的订单；
 * SR5	
 * a)	订单任务查询条件可以进行组合查询，若订单号不为空则以订单号为最高查询条件；
 * b)	行政区区域查询条件关联省市区域基础资料；
 * c)	定人定区查询条件关联定人定区基础资料；
 * SR6	
 * a)	预处理建议先在派车历史记录中获取历史派车建议，若没有历史派车建议，则进入b）进行匹配；
 * b)	预处理建议由接货任务地址匹配定人定区基础资料，得出区域，然后区域匹配定人定区，得出车牌号，
 * 然后车牌号匹配司机PDA签到信息（签到信息包括：工号、车牌号）；
 * c)	若未匹配得出预处理建议，则系统提示未得到预处理建议；
 * 用派车记录来匹配定人定区由GIS(地址库）来承接，FOSS不做本地的计算。
 * 如果接送货订单的营业部“所属集中接送货开单组”为空，则认为该订单为非集中处理订单，则取消这些订单的预处理建议功能。
 * SR7	
 * 当生成的预处理建议中的车辆已载体积与订单中货物体积之和大于车辆净空时,系统给予警示;警示后若确定，还是可以派车。
 * SR8	
 * 预处理建议和接送货调度人为判断的订单处理结果，所选择的公司自有司机和车辆只能为接送货调度负责的接送货车队的司机和车辆或通过“借车流程”获得的司机和车辆；
 * 所选择的外请车司机和车辆只能为接送货调度负责的接送货车队外请车基础资料中的司机和车辆；
 * SR9	
 * a)	若没有生成预处理建议，操作员在可用车辆信息列表中勾选，同时将此条车辆信息填充到 2>订单任务列表 预处理建议 中去；
 * b)	若当前勾选的可用车辆信息还未与司机进行绑定，则在受理之前需要先进行车辆、司机的绑定，然后才可以进行受理（还未登陆PDA可以临时在本界面中进行手动修改绑定，但是不影响司机排班仅本次有效）；司机排班表参考 中转SUC-220 制作短途排班表，非PDA不需要车辆、司机绑定后就能受理订单
 * c)	可用车辆信息列表按照剩余体积从大到小来进行排序；
 * SR10	
 * 若没有生成预处理建议，同时可用车辆信息中的车辆也不符合条件，
 * 则操作员在外请车辆信息中查询相应的车辆，选择外请车信息后将外请车车牌号带入 图2 预处理建议中去。外请车的绑定关系不会更新在 可用车辆信息 列表当中去。
 * SR11	
 * 当接货司机完成订单收货后，剩余体积=净空-（已完成的接货订单货物总体积+未送出的货物总体积）；体积单位为立方米（F）剩余体积：①PDA输入体积的按输入的计算，②没有体积的按订单体积计算。
 * SR12	
 * 剩余重量=净重-（已完成的接货订单货物总重量+未完成的派送货物总重量）；重量单位为吨（T）；剩余重量、剩余体积根据PDA返回的结果自动更新
 * SR13	
 * 页面初始加载后：
 * a)	若当前部门为车队，订单查询条件为空；
 * b)	若当前部门为营业部，订单查询条件中营业部默认为当前部门且不可修改；
 * c)	可用车辆信息默认加载出已绑定车辆、司机对应关系及本车队所属自有车信息；
 * SR14	
 * 可以在订单任务列表中勾选多条订单进行批量受理，但是退回操作只能进行单条退回；
 * SR15	
 * a)	订单发起人取消待受理（仅能够取消未受理和待改接）的订单，调度不能对该订单进行受理或退回，该订单在刷新时自动清除；
 * b)	调度在受理派车时调用CRM接口实时查询当前订单任务的状态是否已受理(CRM受理)，只有在CRM已受理时才可进行受理派车。
 * SR16	
 * 不能多人同时受理、退回、处理同一订单；冲突时，订单的受理、退回以最先提交为准；且要提示调度“此条订单已经受理（退回）！“
 * SR17	
 * 订单受理（订单受理即调度已派车）或退回后，其状态由“未处理”或“待改接”更改对应的“已派车”或“已退回”；
 * SR18	
 * 只有“未处理”和“待改接”的订单，可以执行“退回”；
 * SR19	
 * 状态为 “待改接”、“已派车”的订单，订单发起人不可修改订单信息； 
 * SR20	
 * 当订单为“待改接”时，若接送货调度处理的结果与上一次处理结果一样时，则系统提给出提醒，接送货调度确认后才可提交；
 * SR21	
 * 无论是系统给出的预处理建议或是接送货调度人为判断的处理结果：
 * a)	若对应司机建立PDA签到绑定，则自动勾选订单发送的方式为PDA发送，
 * 接送货调度也可以同时勾选手机短信发送，PDA发送、短信发送、发送给客户三个复选框均可编辑；
 * b)	若对应司机未建立PDA签到绑定，则PDA发送订单任务不可勾选，
 * 接送货调度只能勾选手机短信发送；外请车同此规则；
 * c)	发送给客户短信复选框默认勾选；
 * d)	若PDA发送和手机短信发送都没有勾选，
 * 受理时则给出提示“必需选择一种发送方式发送订单任务！”
 * e)	调度派车发送任务时，若司机有PDA且登陆，
 * 则直接发送任务至PDA中；若发送任务时，司机没登陆PDA，
 * 则直接生成一条短信发至司机手机，并发送任务至PDA服务器中，若以后司机登陆PDA，
 * 则可以根据司机工号直接从PDA服务器上下拉所有对应工号未完成的任务。
 * f)	订单受理之后向发起订单的营业部发送在线通知。
 * 通知模板 ：营业部名称（徐泾营业部）：订单编号为XXXXXX的订单  由司机张三（电话13917090922）车牌号XXXXXX  承接    受理时间 2012年12月6日 15:30:00  受理人:王辉 受理部门:上海车队  
 * SR22	
 * 系统自动标识以下四种订单状态：
 * a)	处理时间超时；
 * b)	快到约车用车时间；
 * c)	已过约车用车时间；
 * d)	“待改接”订单；
 * 且该三种状态随时间推移，更改状态标识；
 * （三个时间已确定：
 * a) 15分钟未受理   
 * b) 开始用车时间前15分钟   
 * c) 结束用车时间15分钟） 所有时间计算全部放在客户端来计算。
 * SR23	
 * 当接货车类型为外请车时，接送货调度只能通过公司短信平台的方式发送接货任务；
 * SR24	
 * 可用车辆信息
 * a)	当前车辆位置系统30分钟自动刷新一次，也可以点击刷新按钮刷新；
 * b)	每天的0：00自动触发，将车辆的载重、载空清空；
 * c)	已绑定司机的车辆放在前面，按照剩余体积从大到小进行排序；
 * d)	可以根据车辆组别进行可用车辆信息筛选；
 * e)	可用车辆信息先读取PDA与司机绑定关系，然后再读取司机排班表，
 * 若有冲突，以绑定关系优先，将读自司机排班表中对应的司机信息清空（空出对应车辆）； 
 *   例如：司机排班表中 A车对应司机 甲  但是当天司机甲 与B车进行了PDA绑定，则在可用车辆信息中 显示 B-甲  A-空；
 * SR25	
 * 受理订单任务完成之后
 * a)	反写营业部约车状态为已受理；
 * b)	反写CRM对应订单状态为接货中；
 * SR26	
 * 车牌和司机的选择只能是本调度车队服务区域所属车辆和司机；
 * 
 * SR27	
 * 受理或者退回完成之后，系统自动从调度订单任务列表中删除该条订单；
 * SR28	
 * 中转调度在受理时，若自有车辆不足时：
 * a）	先走“借车流程”借到车后，受理营业部约车；
 * b）	借车未成功，点击手动查询车辆，查询外请车信息，
 * 若没有查询到外请车，则走外请车流程并将外请车信息添加到外请车基础资料中进行约车；
 * c）	自有车、外请车查询时，若没有输入查询条件，
 * 则默认查出本调度所属车队下所有的自有车、外请车信息；
 * SR29	
 * 营业员获取CRM系统中本营业部的接货订单后先进行约车然后再在本用例中进行处理订单；
 * a）	可查询到CRM已受理的渠道接货订单，包括400、官网、阿里、友商等；
 * b）	客户直接联系营业部，营业员需要在CRM系统录入的接货订单然后再受理，调度处理订单；
 * c）	营业员在CRM中的订单受理系统中取消订单，处理订单系统中在刷新时自动清除该订单；
 * SR30	
 * 营业部物流班车：
 * a)	物流班车与营业部的绑定在中转物流班车发车计划确认并在FOSS中提交后，
 * 绑定，其绑定建立绑定时间，为发车计划发车时间；详细内容参看 SUC-216 （查询/制定发车计划（短途））
 * b)	营业部约车解除绑定：按照营业部做交接确认后触发车辆与营业部的绑定解除
 * c)	只有该营业部和物流班车绑定之后，营业员才可以在可用车辆信息中选择该车辆进行受理派送；
 * SR31	
 * 营业部约车：
 * a)	约车车辆与营业部的绑定：在中转调度受理约车提交后，建立车辆和司机与营业部的绑定；
 * 只有此绑定关系生成之后 才可以将此车辆、司机信息添入可用车辆信息列表当中，进行后续受理。参见系统用例SUC-100 （审核&受理约车申请）
 * b)	约车车辆与营业部的绑定解除：在受理的约车用车时间结束时间时，车辆与营业部的绑定关系解除；
 * SR32	a)	
 * 
 * SR33	
 * 1、接货短信模板：
 * “[”网单号+“] “接货：”+“，营业部+“，”+客户名称+“，”+电话+手机+“，地址：”+接货地址+“，
 * 货物信息：”+件数+“件/”+重量+“公斤/”+体积+“方, ”+包装+“，接货时间段”+最早接货时间（去掉年月日）
 * +“-”+最晚接货时间（去掉年月日）+“，约车编号：”+****+“，”+备注信息+“。”
 * 其中：备注信息：若有，则发送备注信息；若无，则不发送备注信息；其他为必须发送的内容。例如：
 * 接货：徐泾营业部，王先生，13916078507，地址：嘉定区伊宁路上海国际赛车场,
 * 货物信息：10件/1000公斤/2方，接货时间段12:30-13:45，约车编号：*9876543*+备注信息
 * 2、转货短信模板： 
 * “转货：”+营业部+“，货物信息：” +重量+“公斤/”+体积+“方,
 * 接货时间段”+最早接货时间（去掉年月日）+“-”+最晚接货时间（去掉年月日）+“，
 * 约车编号：”****+“，”+备注信息+“。”例如：
 * 转货：徐泾营业部，货物信息： 1000公斤/2方，
 * 接货时间段12:30-13:45，约车编号：*9876543*+备注信息
 * SR-34	
 * 驳回不可再次修改该条信息
 * SR-35	
 * 退回可以再次修改基本信息后再次提交
 * SR-36	
 * 通过必须要先选取响应匹配车辆.
 * SR-37	
 * 一旦有放行需求,需要在提交的时候进行接口调用,生成放行单,并且通知调度.
 * SR-38	
 * 生成放行需求,除了要根据选择结果外,还需要针对车辆部门及申请使用部门进行匹配,如果是同一外场,则不需要生成放行需求.
 * SR-39	
 * 当再次修改受理信息,匹配新车时,如果原车没有出场,则需要推送信息到生成放行需求的调度,对其进行取消生成需求步骤.
 * 如果原车已经出场(放行需求已经使用),则不需要再推送信息.
 * SR-40	
 * 送货生成放行需求选项默认为是,转货偏线生成放行需求选项默认为否.
 * SR-41	
 * 当车辆有多任务时,根据约车的预计使用时间,找到此时间点车辆的任务信息显示.
 * SR-42	
 * 
 * SR-43	
 * 
 * SR-44	
 * 
 * SR-45	
 * 请车价格线下确认,在受理时录入.
 * SR-46	
 * 
 * SR-47	
 * 外请车受理时，需用当前时间和综合维护的车辆超龄时间做比较，如果当前时间超过了车辆的超龄时间，系统给出提示，提醒用户是否继续操作
 * 1.8	非功能性需求
 * 使用量	
 * 目前一天约8W运单，集中接送货率18.56% 8W*18.56% = 1.5W                                100W运单，100W*18.56% = 18.56W
 * 约车审核使用量约为8000条/天
 * 外请车审核使用量约为200条/天
 * 2012年全网估计用户数	
 * 调度数量为500名
 * 响应要求（如果与全系统要求 不一致的话）	3S
 * 使用时间段	
 * 正常接送货时间
 * 高峰使用时间段	
 * 14：00-19：00
 * 4月,9月
 * 1.9 * 接口描述：
 * 接口名称 	
 * 对方系统（外部系统或内部其他模块）	接口描述
 * 查询外请车	
 * 综合管理子系统	
 * 查询外请车信息
 * 查询自有车	
 * 综合管理子系统	
 * 查询自有车信息
 * 约车接口	
 * 中转子系统	
 * 查询、修改约车信息
 * 车辆实时位置	
 * FOSS-GPS	
 * “车辆实时位置”调用该接口
 * GIS接口	Foss-GIS	
 * 根据传入的接货地址匹配出当前地址所属于的接货区域，从而匹配得出预处理建议。
 * 短信发送接口	
 * 短信平台	
 * 发送短信给司机、客户
 * PDA接口	
 * PDA	
 * 将派车任务发送至PDA服务器供PDA下拉订单
 * CRM订单状态修改	
 * CRM	
 * 订单信息反馈
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.BindingLeasedTruckDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LeasedVehicleWithDriverDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.ITruckResourceDao;
import com.deppon.foss.module.pickup.order.api.server.dao.ITruckScheduleDao;
import com.deppon.foss.module.pickup.order.api.server.service.IVehicleManageService;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.TruckConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.LeasedTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.LeasedTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDtoWithCount;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckSignOrSchedulingDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleActualSituationDto;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IVehicleActualSituationManageService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleActualSituationEntity;
import com.deppon.foss.module.transfer.scheduling.api.define.InviteVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.define.OrderVehicleConstants;
import com.deppon.foss.util.define.FossConstants;


/**
 * 调度订单车辆相关的管理Service
 * @author 038590-foss-wanghui
 * @date 2012-10-29 下午5:31:27
 */
public class VehicleManageService implements IVehicleManageService {
	
	// 注册BigDecimal转换器，否则Bigdecimal转换报错
	static {
		BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
		ConvertUtils.register(bigDecimalConverter, BigDecimal.class);
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VehicleManageService.class);
	// PDA签到DAO
	private IPdaSignEntityDao pdaSignEntityDao;
	// 车辆排班DAO
	private ITruckScheduleDao truckScheduleDao;
	// 车辆实况服务
	private IVehicleActualSituationManageService vehicleActualSituationManageService;
	// 外请车服务
	private ILeasedVehicleService leasedVehicleService;
	// 车辆相关资源服务：约车、借车
	private ITruckResourceDao truckResourceDao;
	// 综合组织服务
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	// 页数
	private static final int PAGE_SIZE = 100;
	
	
	//车辆查询
	private IOwnVehicleService ownVehicleService;
							   
	public void setOwnVehicleService(IOwnVehicleService ownVehicleService) {
		this.ownVehicleService = ownVehicleService;
	}
	
	/**
	 * 查询已用车辆.
	 * @param ownTruckConditionDto
     * 			vehicleNo
     * 				车牌号
     * 			vehicleType
     * 				车型
     * 			serviceFleetCode
     * 				接送货车队
     * 			driverCode
     * 				司机编码
     * 			regionType
     * 				区域类型（接货or送货）
     * 			regionNature
     * 				区域大小
     * 			active
     * 				是否激活
     * 			bundleStatus
     * 				绑定状态
     * 			schedulingType
     * 				排班类型
     * 			schedulingStatus
     * 				排班状态
     * 			schedulingPlanType
     * 				计划状态
     * 			departPlanType
     * 				物流班车类型
     * 			orgCode
     * 				组织code（营业部或车队）
     * 			departPlanStatus
     * 				发车计划状态
	 * @return the list
	 * @author 038590-foss-wanghui
	 * @date 2012-10-30 下午7:41:33
	 */
	@Override
	public List<OwnTruckDto> queryUsedVehicle(OwnTruckConditionDto ownTruckConditionDto) {
		initOwnTruckConditionDto(ownTruckConditionDto);
		/**
		 * 可用车辆信息先读取PDA与司机绑定关系，
		 * 然后再读取司机排班表，若有冲突，以绑定关系优先，
		 * 将读自司机排班表中对应的司机信息清空（空出对应车辆）
		 * 例如：司机排班表中 A车对应司机 甲  但是当天司机甲 与B车进行了PDA绑定，
		 * 则在可用车辆信息中 显示 B-甲  A-空
		 */
		// pda签到结果集
		List<OwnTruckDto> pdaSignResult = pdaSignEntityDao.querySign(ownTruckConditionDto);
		// 排班结果集
		List<OwnTruckDto> schedulingResult = truckScheduleDao.queryTruckScheduling(ownTruckConditionDto);
		List<OwnTruckDto> copyList = new ArrayList<OwnTruckDto>(Arrays.asList(new OwnTruckDto[schedulingResult.size()]));
		Collections.copy(copyList, schedulingResult);
		// 获得交集
		schedulingResult.retainAll(pdaSignResult);
		// 交集中对应的司机赋为空值
		for(OwnTruckDto ownTruckDto : schedulingResult){
			ownTruckDto.setDriverCode(null);
			ownTruckDto.setDriverName(null);
		}
		// 获得差集
		copyList.removeAll(pdaSignResult);
		// 获得总可用车辆
		pdaSignResult.addAll(schedulingResult);
		pdaSignResult.addAll(copyList);
		if(FossConstants.YES.equals(FossUserContext.getCurrentDept().getSalesDepartment())){
			// 营业部物流班车
			List<OwnTruckDto> truckDepartPlan = truckResourceDao.queryTruckDepartPlan(ownTruckConditionDto);
			// 营业部约车
			List<OwnTruckDto> orderVehicle = truckResourceDao.queryOrderVehicle(ownTruckConditionDto);
			pdaSignResult.addAll(truckDepartPlan);
			pdaSignResult.addAll(orderVehicle);
		} else {
			// 调度借车
			List<OwnTruckDto> borrowVehicle = truckResourceDao.queryBorrowVehicle(ownTruckConditionDto);
			pdaSignResult.addAll(borrowVehicle);
		}
		// pda签到结果集
		List<OwnTruckDto> pdaSignResult1 = new ArrayList(new HashSet(pdaSignResult));
		return pdaSignResult1;
	}

	/**
	 * 初始化查询条件.
	 * @param ownTruckConditionDto
     * 			vehicleNo
     * 				车牌号
     * 			vehicleType
     * 				车型
     * 			serviceFleetCode
     * 				接送货车队
     * 			driverCode
     * 				司机编码
     * 			regionType
     * 				区域类型（接货or送货）
     * 			regionNature
     * 				区域大小
     * 			active
     * 				是否激活
     * 			bundleStatus
     * 				绑定状态
     * 			schedulingType
     * 				排班类型
     * 			schedulingStatus
     * 				排班状态
     * 			schedulingPlanType
     * 				计划状态
     * 			departPlanType
     * 				物流班车类型
     * 			orgCode
     * 				组织code（营业部或车队）
     * 			departPlanStatus
     * 				发车计划状态
	 * @author 038590-foss-wanghui
	 * @date 2012-11-16 下午4:36:44
	 */
	private void initOwnTruckConditionDto(
			OwnTruckConditionDto ownTruckConditionDto) {
		ownTruckConditionDto.setActive(FossConstants.ACTIVE);
		// 集中接货大小区性质--小区
		ownTruckConditionDto.setRegionNature(ComnConst.REGION_NATURE_SQ);
		// 集中接货货区域类型--接货区域
		ownTruckConditionDto.setRegionType(DictionaryValueConstants.REGION_TYPE_PK);
		// PDA绑定状态
		ownTruckConditionDto.setBundleStatus(PdaSignStatusConstants.BUNDLE);
		// 排班类型--接送货
		ownTruckConditionDto.setSchedulingType(TruckConstants.SCHEDULE_TYPE_DELIVERY);
		// 排班状态--可用
		ownTruckConditionDto.setSchedulingStatus(FossConstants.ACTIVE);
		// 司机状态--工作
		ownTruckConditionDto.setSchedulingPlanType(TruckConstants.PLAN_TYPE_WORK);
		// 物流班车类型--短途
		ownTruckConditionDto.setDepartPlanType(TruckConstants.DEPART_PLAN_TYPE);
		List<String> orderVehicleStatus = new ArrayList<String>();
		// 已受理
		orderVehicleStatus.add(OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED);
		// 已到达
		orderVehicleStatus.add(OrderVehicleConstants.ORDERVEHICLE_STATUS_CONFIRMTO);
		// 已报道
		orderVehicleStatus.add(InviteVehicleConstants.INVITEVEHICLE_STATUS_VERIFY_ARRIVE);
		// 约车状态--已受理
		ownTruckConditionDto.setOrderVehicleStatus(orderVehicleStatus);
		// 调用综合的接口获取当前组织所在的车队
		OrgAdministrativeInfoEntity fleet = orgAdministrativeInfoComplexService.getTopFleetByCode(FossUserContextHelper.getOrgCode());
		if(StringUtils.isEmpty(ownTruckConditionDto.getServiceFleetCode()) && fleet != null){
			// 调用综合组服务获取车队下的组织
			List<OrgAdministrativeInfoEntity> serviceFleets = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByBizType(fleet.getCode(),BizTypeConstants.ORG_TRANS_TEAM);
			List<String> fleetCodeList = new ArrayList<String>();
			// 封装DTO
			for (OrgAdministrativeInfoEntity serviceFleet : serviceFleets) {
				fleetCodeList.add(serviceFleet.getCode());
			}
			ownTruckConditionDto.setFleetCodeList(fleetCodeList);
		} else {
			// 无操作
//			ownTruckConditionDto.setServiceFleetCode(fleet != null ? fleet.getCode() : FossUserContextHelper.getOrgCode());
		}
		ownTruckConditionDto.setOrgCode(fleet != null ? fleet.getCode() : FossUserContextHelper.getOrgCode());
	}
	
	/**
	 * 查询自有车.
	 * @param ownTruckConditionDto
     * 			vehicleNo
     * 				车牌号
     * 			vehicleType
     * 				车型
     * 			serviceFleetCode
     * 				接送货车队
     * 			driverCode
     * 				司机编码
     * 			regionType
     * 				区域类型（接货or送货）
     * 			regionNature
     * 				区域大小
     * 			active
     * 				是否激活
     * 			bundleStatus
     * 				绑定状态
     * 			schedulingType
     * 				排班类型
     * 			schedulingStatus
     * 				排班状态
     * 			schedulingPlanType
     * 				计划状态
     * 			departPlanType
     * 				物流班车类型
     * 			orgCode
     * 				组织code（营业部或车队）
     * 			departPlanStatus
     * 				发车计划状态
	 * @return the list
	 * @author 038590-foss-wanghui
	 * @date 2012-10-30 下午7:47:13
	 */
	@Override
	public OwnTruckDtoWithCount queryOwnTruck(
			OwnTruckConditionDto ownTruckConditionDto, int start, int limit) {
		// 初始化查询条件
		initOwnTruckConditionDto(ownTruckConditionDto);
		List<OwnTruckDto> ownTruckDtos = new ArrayList<OwnTruckDto>();
		OwnTruckDtoWithCount truckDtoWithCount = new OwnTruckDtoWithCount();
		/*
		 * 判断司机code是否为空
		 * 1. 不为空，则单独查询PDA签到表，若PDA签到表查询结果空，再查询排班表 
		 * 2. 为空，则查询车辆表，关联PDA签到表和排班表，查看对应的车辆是否排班或签到
		 */
		if(ownTruckConditionDto.getDriverCode() != null && !"".equals(ownTruckConditionDto.getDriverCode())){
			OwnTruckDto ownTruckDto = null;
			ownTruckDto = pdaSignEntityDao.querySignByDriverCode(ownTruckConditionDto);
			if(ownTruckDto == null){
				ownTruckDto = truckScheduleDao.queryTruckSchedulingByDriverCode(ownTruckConditionDto);
			}
			ownTruckDtos.add(ownTruckDto);
			truckDtoWithCount.setCount(1L);
			truckDtoWithCount.setOwnTruckList(ownTruckDtos);
		} else {
			// 查询自有车的数量
			Long count = pdaSignEntityDao.queryOwnTruckCount(ownTruckConditionDto);
			if(count > 0){
				List<OwnTruckSignOrSchedulingDto> ownTruckSignOrSchedulingDtos = pdaSignEntityDao.queryOwnTruck(ownTruckConditionDto, start, limit);
				// 循环处理查询结果
				for(OwnTruckSignOrSchedulingDto ownTruckSignOrSchedulingDto : ownTruckSignOrSchedulingDtos){
					OwnTruckDto ownTruckDto = new OwnTruckDto();
					// 车牌号
					ownTruckDto.setVehicleNo(ownTruckSignOrSchedulingDto.getVehicleNo());
					// 车型
					ownTruckDto.setVehicleType(ownTruckSignOrSchedulingDto.getVehicleType());
					// 净重
					ownTruckDto.setNetWeight(ownTruckSignOrSchedulingDto.getNetWeight());
					// 净空
					ownTruckDto.setNetVolume(ownTruckSignOrSchedulingDto.getNetVolume());
					// 剩余重量
					ownTruckDto.setRemainingWeight(ownTruckSignOrSchedulingDto.getRemainingWeight());
					// 剩余体积
					ownTruckDto.setRemainingVolume(ownTruckSignOrSchedulingDto.getRemainingVolume());
					// 所属区域名称
					ownTruckDto.setOwnedZoneName(ownTruckSignOrSchedulingDto.getOwnedZoneName());
					// 所属区域编码
					ownTruckDto.setOwnedZoneCode(ownTruckSignOrSchedulingDto.getOwnedZoneCode());
					/*
					 *  判断PDA设备号是否为空
					 *  1. 不为空，设置对应的签到司机信息和设备号
					 *  2. 为空，则查看排班司机是否为空，若不为空，则设置排班司机信息
					 */
					if(ownTruckSignOrSchedulingDto.getDeviceNo() != null){
						// 设备号
						ownTruckDto.setDeviceNo(ownTruckSignOrSchedulingDto.getDeviceNo());
						// 司机姓名
						ownTruckDto.setDriverName(ownTruckSignOrSchedulingDto.getSignDriverName());
						// 司机编码
						ownTruckDto.setDriverCode(ownTruckSignOrSchedulingDto.getSignDriverCode());
						// 司机手机
						ownTruckDto.setDriverMobile(ownTruckSignOrSchedulingDto.getSignDriverMobile());
						// 是否PDA绑定
						ownTruckDto.setIsPdaBundle(FossConstants.YES);
					} else if(ownTruckSignOrSchedulingDto.getSchedulingDriverCode() != null) {
						// 司机姓名
						ownTruckDto.setDriverName(ownTruckSignOrSchedulingDto.getSchedulingDriverName());
						// 司机编码
						ownTruckDto.setDriverCode(ownTruckSignOrSchedulingDto.getSchedulingDriverCode());
						// 司机手机
						ownTruckDto.setDriverMobile(ownTruckSignOrSchedulingDto.getSchedulingDriverMobile());
						// 是否PDA绑定
						ownTruckDto.setIsPdaBundle(FossConstants.NO);
					} else {
						// 是否PDA绑定
						ownTruckDto.setIsPdaBundle(FossConstants.NO);
					}
					ownTruckDtos.add(ownTruckDto);
				}
			}
			truckDtoWithCount.setCount(count);
			truckDtoWithCount.setOwnTruckList(ownTruckDtos);
		}
		return truckDtoWithCount;
	}

	/**
	 * 
	 * 查询外请车的数量
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-13 上午8:48:01
	 */
	@Override
	public Long queryLeasedTruckCount(LeasedTruckConditionDto leasedTruckConditionDto) {
		return leasedVehicleService.countQueryLeasedVehicleWithDriverDtosByCondition(leasedTruckConditionDto.getVehicleNo(), leasedTruckConditionDto.getVehicleType(), leasedTruckConditionDto.getDriverName(), leasedTruckConditionDto.getDriverMobile(), leasedTruckConditionDto.isOpenVehicle(), FossConstants.YES);
	}

	/**
	 * 查询外请车.
	 * 
	 * @param leasedTruckConditionDto 外请车查询条件Dto
	 * 			vehicleNo
	 * 				车牌号
	 * 			vehicleType
	 * 				车型
	 * 			driverName
	 * 				司机姓名
	 * 			driverMobile
	 * 				司机手机
	 * 			openVehicle
	 * 				是否敞篷
	 * @return the list
	 * @author 038590-foss-wanghui
	 * @date 2012-10-31 上午8:23:19
	 */
	@Override
	public List<LeasedTruckDto> queryLeasedTruck(
			LeasedTruckConditionDto leasedTruckConditionDto, int start, int limit) {
		List<LeasedVehicleWithDriverDto> leasedVehicleWithDriverDtos = null;
		// 封装综合dto
		List<LeasedTruckDto> leasedTruckDtos = new ArrayList<LeasedTruckDto>();
		// 判断条件是否为空
		if(leasedTruckConditionDto != null) {
			// 调用综合查询外请车接口
			leasedVehicleWithDriverDtos = leasedVehicleService.queryLeasedVehicleWithDriverDtosByCondition(leasedTruckConditionDto.getVehicleNo(), leasedTruckConditionDto.getVehicleType(), leasedTruckConditionDto.getDriverName(), leasedTruckConditionDto.getDriverMobile(), leasedTruckConditionDto.isOpenVehicle(), FossConstants.YES, start, limit); 
			for(LeasedVehicleWithDriverDto leasedVehicleWithDriverDto : leasedVehicleWithDriverDtos){
				LeasedTruckDto leasedTruckDto = new LeasedTruckDto();
				if(leasedVehicleWithDriverDto.getLeasedVehicle() != null){
					// 车牌号
					leasedTruckDto.setVehicleNo(leasedVehicleWithDriverDto.getLeasedVehicle().getVehicleNo());
					// 车型
					leasedTruckDto.setVehicleType(leasedVehicleWithDriverDto.getLeasedVehicle().getVehicleLengthName());
					// 是否开蓬
					leasedTruckDto.setOpenVehicle(leasedVehicleWithDriverDto.getLeasedVehicle().isOpenVehicle());
					// 是否有尾板
					leasedTruckDto.setTailBoard(leasedVehicleWithDriverDto.getLeasedVehicle().isHasTailBoard());
				}
				if(leasedVehicleWithDriverDto.getLeasedDriver() != null){
					// 司机姓名
					leasedTruckDto.setDriverName(leasedVehicleWithDriverDto.getLeasedDriver().getDriverName());
					// 司机手机
					leasedTruckDto.setDriverMobile(leasedVehicleWithDriverDto.getLeasedDriver().getDriverPhone());
				}
				leasedTruckDtos.add(leasedTruckDto);
			}
		}
		return leasedTruckDtos;
	}

	/**
	 * 查询已绑定外请车数量
	 * @author liuxiangcheng 329757
	 * @data 2016年5月31日 下午1:06:22
	 * @param bindingLeasedTruckDto
	 * @return
	 */
	@Override
	public long queryBundLeasedTruckCount(
			BindingLeasedTruckDto bindingLeasedTruckDto) {
		return leasedVehicleService.queryBindingLeasedVehicleListTotal(bindingLeasedTruckDto);
	}

	/**
	 * 查询已绑定外请车信息
	 * @author liuxiangcheng 329757
	 * @data 2016年5月31日 下午1:07:51
	 * @param bindingLeasedTruckDtos
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	@Transactional
	public List<BindingLeasedTruckDto> queryBundLeasedTruck(
			BindingLeasedTruckDto bindingLeasedTruckDto, int start,
			int limit) {
		// 封装综合dto
		List<BindingLeasedTruckDto> bindingLeasedTrucs = new ArrayList<BindingLeasedTruckDto>();
		// 判断条件是否为空
		if(bindingLeasedTruckDto != null) {
			// 调用综合查询外请车接口
			bindingLeasedTrucs = leasedVehicleService.queryBindingLeasedVehicleList(bindingLeasedTruckDto, start, limit);
		}
		return bindingLeasedTrucs;
	}
	
	/**
	 * 根据车牌号修改车辆实际情况.
	 * 
	 * @param vehicleActualSituationDto
	 * 			id
	 * 				id
	 * 			vehicleNo
	 * 				车牌号
	 * 			remainingWeight
	 * 				剩余重量
	 * 			remainingVolume
	 * 				剩余体积
	 * 			alreadyPickupGoodsQty
	 * 				已接票数
	 * 			nonePickupGoodsQty
	 * 				未接票数
	 * 			alreadyDeliverGoodsQty
	 * 				已送票数
	 * 			noneDeliverGoodsQty
	 * 				未送票数
	 * @return true, if successful
	 * @author 038590-foss-wanghui
	 * @date 2012-11-3 下午2:25:27
	 */
	@Transactional
	@Override
	public boolean modifyVehicle(VehicleActualSituationDto vehicleActualSituationDto) {
		VehicleActualSituationEntity vehicleActualSituationEntity = new VehicleActualSituationEntity();
		try {
			BeanUtils.copyProperties(vehicleActualSituationEntity, vehicleActualSituationDto);
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage());
		} catch (InvocationTargetException e) {
			LOGGER.error(e.getMessage());
		}
		boolean isSuccess = vehicleActualSituationManageService
				.updateWVByVehicleNo(vehicleActualSituationEntity);
		return isSuccess;
	}
	
	/**
	 * 清空车辆的载重载空.
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-12 下午2:46:44
	 */
	@Override
	public void emptyVehicle() {
		int start = 0;
		int limit = PAGE_SIZE;
		while(true){
			// 分页查询车辆
			List<VehicleActualSituationEntity> actualSituationEntities = vehicleActualSituationManageService.queryByPage(start, limit);
			// 判断是否为空，为空则跳出
			if(CollectionUtils.isEmpty(actualSituationEntities)){
				break;
			}
			// 清空车载信息
			for(VehicleActualSituationEntity vehicleActualSituation : actualSituationEntities){
				if(vehicleActualSituation.getRemainingWeight() == null  || vehicleActualSituation.getRemainingVolume() == null){
					continue ;
				}
				// 更新车载信息空
				vehicleActualSituationManageService.updateWV2EmptyByVehicleNo(vehicleActualSituation);
			}
			// 分页加pageSize
			start += PAGE_SIZE;
			limit += PAGE_SIZE;
		}
	}
	
	/**
	 * 根据vehicleActualSituationEntity增加相应的车载信息.
	 * 
	 * @param vehicleActualSituationDto
	 * 			id
	 * 				id
	 * 			vehicleNo
	 * 				车牌号
	 * 			remainingWeight
	 * 				剩余重量
	 * 			remainingVolume
	 * 				剩余体积
	 * 			alreadyPickupGoodsQty
	 * 				已接票数
	 * 			nonePickupGoodsQty
	 * 				未接票数
	 * 			alreadyDeliverGoodsQty
	 * 				已送票数
	 * 			noneDeliverGoodsQty
	 * 				未送票数
	 * @return true, if successful
	 * @author 038590-foss-wanghui
	 * @date 2012-11-19 下午5:16:45
	 */
	@Override
	public boolean addVehicleWVByVehicleNo(
			VehicleActualSituationDto vehicleActualSituationDto) {
		VehicleActualSituationEntity vehicleActualSituationEntity = new VehicleActualSituationEntity();
		try {
			BeanUtils.copyProperties(vehicleActualSituationEntity, vehicleActualSituationDto);
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage());
		} catch (InvocationTargetException e) {
			LOGGER.error(e.getMessage());
		}
		return vehicleActualSituationManageService.addWVByVehicleNo(vehicleActualSituationEntity);
	}
	
	/**
	 * 根据车牌号查询对应的净空和剩余体积.
	 * @param ownTruckConditionDto
     * 			vehicleNo
     * 				车牌号
     * 			vehicleType
     * 				车型
     * 			serviceFleetCode
     * 				接送货车队
     * 			driverCode
     * 				司机编码
     * 			regionType
     * 				区域类型（接货or送货）
     * 			regionNature
     * 				区域大小
     * 			active
     * 				是否激活
     * 			bundleStatus
     * 				绑定状态
     * 			schedulingType
     * 				排班类型
     * 			schedulingStatus
     * 				排班状态
     * 			schedulingPlanType
     * 				计划状态
     * 			departPlanType
     * 				物流班车类型
     * 			orgCode
     * 				组织code（营业部或车队）
     * 			departPlanStatus
     * 				发车计划状态
	 * @return the own truck dto
	 * @author 038590-foss-wanghui
	 * @date 2012-12-7 上午11:51:08
	 */
	@Override
	public OwnTruckDto queryVolumeByVehicleNo(OwnTruckConditionDto ownTruckConditionDto) {
		ownTruckConditionDto.setActive(FossConstants.ACTIVE);
		return truckResourceDao.queryVolumeByVehicleNo(ownTruckConditionDto);
	}
	
	/**
	 * 根据车牌号查询该车辆的已接、未接、已送、未送票数.
	 * 
	 * @param vehicleNo the vehicleNo
	 * @return the vehicle actual situation dto
	 * @author 038590-foss-wanghui
	 * @date 2012-12-26 下午2:33:45
	 */
	@Override
	public VehicleActualSituationDto queryTaskByVehicleNo(String vehicleNo) {
		VehicleActualSituationEntity actualSituationEntity = vehicleActualSituationManageService.queryByVehicleNo(vehicleNo);
		VehicleActualSituationDto actualSituationDto = new VehicleActualSituationDto();
		if(actualSituationEntity != null){
			try {
				BeanUtils.copyProperties(actualSituationDto, actualSituationEntity);
			} catch (IllegalAccessException e) {
				LOGGER.error(e.getMessage(),e);
			} catch (InvocationTargetException e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
		return actualSituationDto;
	}
	
	/**
	 * 
	 * 根据车牌号查询司机姓名
	 * @author 038590-foss-wanghui
	 * @date 2013-5-20 下午4:08:04
	 * @see com.deppon.foss.module.pickup.order.api.server.service.IVehicleManageService#queryTruckSchedulingByVehicleNo(com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto)
	 */
	@Override
	public OwnTruckDto queryTruckSchedulingByVehicleNo(OwnTruckConditionDto ownTruckConditionDto) {
		List<OwnTruckDto> ownTruckDtos = truckScheduleDao.queryTruckSchedulingByVehicleNo(ownTruckConditionDto);
		return CollectionUtils.isNotEmpty(ownTruckDtos) ? ownTruckDtos.get(0) : null;
	}
	
	/**
	 * Sets the vehicleActualSituationManageService.
	 * 
	 * @param vehicleActualSituationManageService the
	 *            vehicleActualSituationManageService to see
	 */
	public void setVehicleActualSituationManageService(
			IVehicleActualSituationManageService vehicleActualSituationManageService) {
		this.vehicleActualSituationManageService = vehicleActualSituationManageService;
	}
	
	/**
	 * Sets the pdaSignEntityDao.
	 * 
	 * @param pdaSignEntityDao the pdaSignEntityDao to see
	 */
	public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
		this.pdaSignEntityDao = pdaSignEntityDao;
	}

	/**
	 * Sets the leasedVehicleService.
	 * 
	 * @param leasedVehicleService the leasedVehicleService to see
	 */
	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		this.leasedVehicleService = leasedVehicleService;
	}

	/**
	 * Sets the truckScheduleDao.
	 * 
	 * @param truckScheduleDao the truckScheduleDao to see
	 */
	public void setTruckScheduleDao(ITruckScheduleDao truckScheduleDao) {
		this.truckScheduleDao = truckScheduleDao;
	}

	/**
	 * Sets the truckResourceDao.
	 * 
	 * @param truckResourceDao the truckResourceDao to see
	 */
	public void setTruckResourceDao(ITruckResourceDao truckResourceDao) {
		this.truckResourceDao = truckResourceDao;
	}

	/**
	 * Sets the orgAdministrativeInfoComplexService.
	 * 
	 * @param orgAdministrativeInfoComplexService the
	 *            orgAdministrativeInfoComplexService to see
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	@Override
	public OwnTruckEntity queryOwnVehicleBySelective(OwnTruckEntity entity,
			Object object) {
		return ownVehicleService.queryOwnVehicleBySelective(entity, null);
	}

}
