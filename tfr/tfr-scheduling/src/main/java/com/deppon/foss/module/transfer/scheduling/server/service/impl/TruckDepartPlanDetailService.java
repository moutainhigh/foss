/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/TruckDepartPlanDetailService.java
 * 
 *  FILE NAME     :TruckDepartPlanDetailService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.service.impl
 * FILE    NAME: TruckDepartPlanDetailService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResStdEntity;
import com.deppon.foss.module.transfer.load.api.server.dao.ISealInformationDao;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.scheduling.api.define.PlatformDispatchConstants;
import com.deppon.foss.module.transfer.scheduling.api.define.TruckDepartPlanConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ILoadTruckTaskDetailService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPlatformDispatchService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanUpdateService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckDepartPlanUpdateEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.CarInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.CarInfoPageDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlanVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TfrLoadTruckTaskDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.TruckDepartPlanException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.CompareUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 计划明细service实现
 * 
 * * 长途制定发车计划需求：
 * 
 * 首次录入某预测周期的发车计划时，根据出发部门、到达部门、
 * 
 * 发车日期带出所有相关线路的固定班次（综合管理的发车时效表），
 * 
 * 在可用线路班次列表表格里按照班次顺序显示，且线路经过本计划
 * 
 * 的出发部门的线路车辆信息需要排前面，用户可修改和完善带出的
 * 
 * 固定班次的各项值。
 * 
 * 图（1）界面是制定长途发车计划主界面。主要分为三部分：实时货
 * 
 * 量信息、长途发车计划列表(包含实时运力统计)。
 * 
 * 首次录入出发部门和到达部门某预测周期的发车计划时，根据线
 * 
 * 路带出该出发部门和到达部门的固定班次，在表格里按照班次顺序显示，
 * 
 * 且在上方显示最新货量预测信息、合发记录、以及表体右下方显示运力
 * 
 * 实时统计信息，提供新增公司车、新增外请车、下发、删除、编辑、
 * 
 * 复制短信模板：
 * 
 * 1. 新增公司车：弹出图（3）的窗口，用于新增公司车作为发车计划。
 * 
 * 2. 新增外请车：弹出图（2）的窗口，用于新增外请车作为发车计划。
 * 
 * 3. 删除：在长途发车计划列表中勾选好需要删除的发车计划后，点击
 * 
 * 删除进行删除操作。
 * 
 * 4. 下发：计划列好后，将选择新建状态的计划，点击下发按钮，使长
 * 
 * 途车队调度、班车调度可以看见相应的发车计划。
 * 
 * 5. 编辑：点击编辑按钮，根据发车计划的车辆归属进行判断，如果是
 * 
 * 公司车，则弹出图（3）界面进行修改；如果是外请车，则弹出图（2）界面进行修改。
 * 
 * 6. 复制短信模板：主要是用于将发车计划中的车辆信息复制，系统后台
 * 
 * 根据信息模板组织成车辆发车计划信息，弹出一个发车计划的短信息框便于复制使用。
 * 
 * 图（2）界面是新增/编辑外请车发车计划界面，主要是针对在车辆发车不能满
 * 
 * 足货量的情况且无公司车的情况下，
 * 
 * 需要新增/修改外请车作为发车计划，主要是通过新增外请车按钮或点击外请车发车计
 * 
 * 划的编辑按钮触发而弹出的界面。
 * 
 * 如果是修改情况，则加载相应的发车计划的信息，待用户修改；如果是新增，则根据
 * 
 * 默认情况，提供新增的默认值，待用户完善其他线路、车辆、司机信息，然后保存。
 * 
 * 提供保存、取消、重置按钮。
 * 
 * 1. 保存：用于保存已经录入的外请车的发车计划。
 * 
 * 2. 取消：取消新增或修改，退出本界面，返回主界面。
 * 
 * 3．重置：将本界面的表单恢复到默认状态。
 * 
 * 图（3）界面是新增/编辑公司车发车计划界面，主要是针对在车辆发车不能满
 * 
 * 足货量的情况且有公司车的情况下，需要新增/修改公司车作为发车计划，主要是通过
 * 
 * 新增公司车按钮或点击公司车发车计划的编辑按钮触发而弹出的界面。
 * 
 * 如果是修改情况，则加载相应的发车计划的信息，待用户修改；如果是新增，则根据默
 * 
 * 认情况，提供新增的默认值，待用户完善其他线路、车辆、司机信息，然后保存。提供
 * 
 * 保存、取消、重置按钮。
 * 
 * 1. 保存：用于保存已经录入的公司车的发车计划。
 * 
 * 2. 取消：取消新增或修改，退出本界面，返回主界面。
 * 
 * 3．重置：将本界面的表单恢复到默认状态。
 * 
 * 1.5.3.1 实时货量信息
 * 
 * 主要是根据出发部门、到达部门，发车日期条件，查询货量预测得到的实时货量
 * 
 * 信息（可以调用货量预测模块的接口，传入到达部门、预测时间两个参数），主要包括到
 * 
 * 达部门、预测时间、总重量/总体积/票数、卡货重量/体积/票数、
 * 
 * 城货重量/体积/票数、开单未交接重量/体积/票数、余货重量/体积/票数、预计到达重量/
 * 
 * 体积/票数、（小计）合发重量/体积/票数、（汇总）重量/总体积/票数、合发记录信息
 * 
 * （线路、合发重量/体积/票数）；这些数据需要两个参数作为查询条件：出发部门、到达部门，
 * 
 * 发车日期，具体数据获取请参见SR-5。
 * 
 * 1.5.3.2 合发记录信息
 * 
 * 主要是对出发部门和到达部门当天的合发记录的信息的反应，包括本到达部门涉及所有
 * 
 * 线路合发到本线以及本线合发到其他线路的信息。
 * 
 * 主要是体现实际货量的线路调整日志。其中主要字段为 线路、时间、重量、体积。
 * 
 * 1.5.3.3 发车计划表单（外请车及公司车）中相应字段的默认设置
 * 
 * 长途发车计划新增表单见图（2）图（3），表单中各项信息参见【长途发车计划表单】
 * 
 * 线路：显示为线路的出发站目的站，根据出发部门到达部门查询出可用线路。
 * 
 * 车型：详见车型标准：“箱式、平板、高栏”+“4.2、6.5、7.6、13、17.5”米，默认全部；
 * 
 * 归属类型：公司车、外请车，必须选择一项，默认为公司车。
 * 
 * 状态：新增时系统后台默认为新建。
 * 
 * 是否正班车：有是和否，必须选择一项，默认为是。
 * 
 * 班次类型：加发、停发、正常，必须选择一项，默认为正常。
 * 
 * 1.5.3.4 长途发车计划列表
 * 
 * 长途发车计划列表显示信息：列表参见【发车计划列表】。
 * 
 * 
 * 其中还包括实时运力统计，实时运力统计主要包括对所选线路、发车日期的所有车辆载重
 * 
 * 及车辆容积的统计，便于和实时的货量预测信息起到比对作用，便于操作人员查看发车计划是否
 * 
 * 匹配实时的货量预测信息。其中字段主要为统计信息：车辆合计、载重合计、总载缺口、容积合计
 * 
 * 、容积缺口、合发总载重、合发总容积，
 * 
 * 差额请参考SR-7。
 * 
 * 短途制定发车计划需求：
 * 
 * 图（1）界面主要分为四部分：货量预测信息、发车计划信息、班车列表，发车计划修改记录。
 * 
 * 1．货量预测信息：主要根据出发部门到达部门和发车日期查询当前时间最新的货量预测信息，
 * 
 * 主要包括：出发部门、到达部门、预测时间、总货物体积、预测货物总重量。
 * 
 * 2. 发车计划信息：主要提供发车日期、是否异常、备注信息的记录功能，
 * 
 * 以及提供保存计划、返回、反馈外场操作员等操作功能按钮。
 * 
 * 3.班车列表：主要提供发车计划列表，以及班车的统计信息包括班车的体积合计、
 * 
 * 载重合计、体积缺口、载重缺口等信息，以及增加、编辑、删除等功能按钮，
 * 
 * 且同实时的货量统计信息。
 * 
 * 4. 保存计划：用于保存发车计划，主要是保存是否异常及备注的信息。
 * 
 * 5. 返回：用于返回班车发车计划查询界面。
 * 
 * （要生成送短信文本给司机和请车员-本描述根据ISSUE-1233删除）。根据ISSUE-1233，
 * 
 * 【生成短信文本】按钮去掉，在列表中的短信按钮，鼠标移动到按钮时，提示“拷贝短信文本”，
 * 
 * 发车计划针对短途排班相关的修改给车队部门发送在线通知。
 * 
 * 7．增加公司车：在排班表中不足的情况下，弹出新增加发公司车窗口，用于查询操作用户权限内
 * 
 * 的公司车辆所有状态的信息，操作人员可按照实际需要，选择查询的车辆信息、填写相应的司机信息，
 * 
 * 然后点击保存为发车计划。用户查询出车辆后，点击选择的的按钮，自动将车辆信息，如车型、车牌号、
 * 
 * 车辆载重、车辆净空等信息自动带入下面的车辆信息，并完善预计装载载重、预计装载体积等信息，
 * 
 * 可点击保存，系统验证其他必填项合法后，方可保存。
 * 
 * 8.增加外请车：在排班表中不足且需要增加外请车情况下，弹出增加新增加发外请车窗口，
 * 
 * 填写相应的线路信息、车辆信息、司机信息后，点击保存，系统执行验证，如都合法后，
 * 
 * 则执行保存到数据库作为外请车发车计划。
 * 
 * 8. 发车计划修改记录：用于查看发车计划中计划的修改情况和历史记录。
 * 
 * 当在排班表中不足且需要增加外请车情况下，点击了新增加发外请车按钮或者点击了修改按钮
 * 
 * （外请车的发车计划），用于新增加发和编辑外请车情况的发车计划信息。本界面提供以下功能按钮。
 * 
 * 1.保存：录入/修改相应的的外请车发车计划信息后，进行保存操作。
 * 
 * 2.取消：用于取消修改或者保存操作，关闭本新增加发/编辑框，返回主界面。
 * 
 * 3.重置：将本窗口表单数据恢复为默认状态。
 * 
 * 当在排班表中不足且需要增加公司车情况下，弹出增加新增加发公司车窗口，主要用于公司车的
 * 
 * 发车计划的新增加发和修改操作。如果是修改情况，则自动加载原有发车计划信息，以待用户修改；如果是新增
 * 
 * 加发情况，则用户需要查询车辆使用情况，选择车辆信息后自动带出相关信息并填充车辆信息的相关字段，完善
 * 
 * 线路信息、车辆信息、司机信息等，本界面提供以下功能按钮。
 * 
 * 1.保存：录入/修改相应的的公司车发车计划信息后，进行保存操作。
 * 
 * 2.取消：用于取消修改或者保存操作，关闭本新增加发/编辑框，返回主界面。
 * 
 * 3.重置：将本窗口表单数据恢复为默认状态。
 * 
 * SR-1 假如时间点的预测货量值低于X时，标记为货量异常；
 * 
 * 假如时间点的预测货量值高于X时，标记为货量异常；
 * 
 * 其中X的值是可配置的。
 * 
 * SR-2 新制定的班车发车计划与规定发车班次进行比对，相符的，记录为正常发车；新制定的多余规定发车次数的，
 * 
 * 记录为加发；新制定的少于规定发车次数的记录为停发；
 * 
 * SR-3 班车发车计划的修改记录需要记录进入班车发车计划日志，且发车计划需要保存修改状态及修改时间。
 * 
 * SR-4 线路字段不可编辑，车辆载重、车辆净空对于自有车而言是不可编辑的，对于外请车来说则是可编辑的。
 * 
 * SR-5 第一次制作发车计划时，系统自动将短途排班表的排班信息初始化为短途发车计划，且为非有效状态，
 * 
 * 待用户填写好相应数据，且按照相关逻辑验证通过，保存后为有效状态。停发情况保存时不必校验，如果由停发状态改
 * 
 * 为其他状态，则必须执行验证。
 * 
 * SR-6 在做发车计划时，根据线路、班次、车牌、日期信息生成车辆任务信息，如果排班计划有有变化则需要提醒用户。
 * 
 * 如果在做停发或者删除发车计划时，需要同时修改车辆任务信息（特别是对班次的修改时）；
 * 
 * SR-7 出发部门（默认为制定计划人员所在地）、到达部门（默认为本计划的到达部门），车辆信息可由车牌号码带出，
 * 
 * 比如：车型、车辆所属车队、车辆净空、车辆载重、以及填写本到达部门需要预计装载载重（默认为车辆载重）、预计装载体积
 * 
 * （默认为车辆净空）、班次（默认为排班表中的班次）、发车日期（默认为发车计划的日期）、发车时间（排班表中的发车时间），
 * 
 * 所有字段录入完毕后，保存作为发车计划，同时生成比对日志，发送在线通知提醒相关的排班人员。发车类别（如果是编辑，
 * 
 * 则取原值，如果为加发，则默认加发，用户根据具体情况可修改），月台号（可根据出发部门、到达部门到综合管理的月台
 * 
 * 信息中查询中自动带出，如不合理则可修改，如没有则用户自己填写），在制定计划的时候车辆信息中车牌、载重、司机信
 * 
 * 息等可先不填写（车型必须填写），待后期确定后再编辑补充相应的数据,发车时间可根据班次自动带出。
 * 
 * SR-8 针对同一车辆可能被安排为多个发车计划使用，系统提供车辆相关任务信息列表查看功能，主要是根据车牌，
 * 
 * 查询相关线路的运输任务（包括车牌号、出发部门、到达部门、装载信息、可用载重体积信息、预计到达时间等）。
 * 
 * SR-9 存在区域总调的情况，由区域总调制定发车计划（包括增删改差所有操作），外场操作员只能查看发车计划
 * 
 * （只能查询），班车调度可修改所有字段（除了加发和删除不能做）；不存在区域总调的情况，由班车调度制定发车计划
 * 
 * （包括增删改差所有操作），外场操作员只能查看发车计划（只能查询）。现有情况是没有区域总调。
 * 
 * SR-10 车辆状态取值列表：在用（已计划、已装车、已出发、、已到达、）；空闲；不可用（年审、季审、维修、
 * 
 * 保养、事故、其他）ISSUE-958
 * 
 * SR-11 公司车辆选择：当用户在加发公司发车计划界面，进行查询车辆的时候，根据查询结果，点击相应的选择框后，
 * 
 * 自动将车辆的车辆载重、车辆容积、车牌号码、预计装载载重、预计装载体积自动带入填充，默认情况下预计装载载重取车辆载重，
 * 
 * 预计装载体积取车辆容积（用户可根据实际计划情况修改）。
 * 
 * SR-12 班车列表中的统计字段来源：体积合计是指本班车列表中所有已经安排车辆的预计装载体积之和；载重合计是指本
 * 
 * 车辆是指本班车列表中所有已经安排车辆的预计装载载重之和；体积缺口=货量预测的总货物体积-体积合计；载重缺口=货量预测
 * 
 * 的预测货物总重量-载重合计；
 * 
 * SR-13 点击车牌号码，可提查看车辆使用情况
 * 
 * SR-15 可根据司机姓名、电话号码、车队、车牌号等带出相应的人员信息、车辆信息等，抽出作为公共选择器，供各个
 * 
 * 模块需要时使用。 SR-16 填写月台号时，填写预计月台使用和预计结束时间;预计月台结束时间系统默认为发车时效表规定的出发时间，
 * 
 * 但是允许修改; 预计月台开始时间默认为发车时效表规定的出发时间的前推一个（车型）装车时间（例如，车型9.6，装货时间3个小时，
 * 
 * 规定出发时间为6:00，那么预计月台开始时间为3:00），但是允许修改；假如无时效表发车规定的，默认为空，由调度自己填写.
 * 
 * SR-17 1：短途发车计划中【新增公司车】，【新增外请车】修改为：【加发公司车】，【加发外请车】
 * 
 * 2：短途发车计划中，增加转换功能（公司车与外请车的转换，初始化时归属类型默认为公司车，班次正常），在已经选择
 * 
 * 公司车的情况下，可以一键转换为外请车，反之也一样。下发前，才能转换，下发之后，不可转换，转换是清除车牌信息，司机信息
 * 
 * @author 096598-foss-zhongyubing
 * 
 * @date 2012-11-21 下午7:03:48
 * 
 * 
 */
public class TruckDepartPlanDetailService implements ITruckDepartPlanDetailService {

	/**
	 * 
	 * 日志
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TruckDepartPlanDetailService.class);
	/**
	 * 计划明细DAO
	 * 
	 */
	private ITruckDepartPlanDetailDao truckDepartPlanDetailDao;
	/**
	 * 
	 * 修改计划日志Service
	 * 
	 */
	private ITruckDepartPlanUpdateService truckDepartPlanUpdateService;
	/**
	 * 
	 * 车辆查询Service
	 * 
	 */
	private IVehicleService vehicleService;
	/**
	 * 自由车查询
	 * 
	 */
	private IOwnVehicleService ownVehicleService;
	/**
	 * 
	 * 线路Service
	 * 
	 */
	
	private ILineService lineService;
	
	private IExpressLineService expresslineService ;
	
	private IFOSSToWkService fossToWkService;
	
	// 屏蔽ECS系统接口服务类
	private IConfigurationParamsService configurationParamsService;
	
	private ISealInformationDao sealInformationDao;
	
	public void setSealInformationDao(ISealInformationDao sealInformationDao) {
		this.sealInformationDao = sealInformationDao;
	}

	/**
	 * 屏蔽ECS系统接口服务类
	 * @param configurationParamsService
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}
	
	/**
	 * 
	 * 操作月台Service
	 * 
	 */
	private IPlatformDispatchService platformDispatchService;

	/**
	 * 人员信息Service
	 * 
	 */
	private IOwnDriverService ownDriverService;

	/**
	 * 部门Service
	 * 
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 
	 * 计划Service
	 * 
	 */
	private ITruckDepartPlanService truckDepartPlanService;
	/**
	 * 
	 * 短途排班Service
	 * 
	 */
	private ITruckSchedulingTaskService truckSchedulingTaskService;
	/**
	 * 
	 * 消息Service
	 * 
	 */
	private IMessageService messageService;
	/**
	 * 查询车辆任务Service
	 * 
	 */
	private ILoadTruckTaskDetailService loadTruckTaskDetailService;
	/**
	 * 
	 * 查询装卸进度
	 * 
	 */
	private ILoadAndUnloadEfficiencyVehicleService loadAndUnloadEfficiencyVehicleService;
	/**
	 * 
	 * 月台查询Service
	 * 
	 */
	private IPlatformService platformService;
	/**
	 * 
	 * 车队查询服务
	 * 
	 */
	private IMotorcadeService motorcadeService;
	/**
	 * 
	 * 计数Service
	 * 
	 */
	private IBusinessMonitorService businessMonitorService;
	/**
	 * 
	 * 车型Service
	 * 
	 */
	private ILeasedVehicleTypeService leasedVehicleTypeService;
	/**
	 * 短信模板Service
	 */
	private ISMSTempleteService sMSTempleteService;
	
	//自有时效
	private int ownLimitation;
	
	//外请时效
	private int outSourceLimitation;

	public int getOwnLimitation() {
		return ownLimitation;
	}

	public void setOwnLimitation(int ownLimitation) {
		this.ownLimitation = ownLimitation;
	}

	public int getOutSourceLimitation() {
		return outSourceLimitation;
	}

	public void setOutSourceLimitation(int outSourceLimitation) {
		this.outSourceLimitation = outSourceLimitation;
	}

	/**
	 * 设置 计划明细DAO.
	 * 
	 * @param truckDepartPlanDetailDao
	 *            the new 计划明细DAO
	 * 
	 */
	public void setTruckDepartPlanDetailDao(ITruckDepartPlanDetailDao truckDepartPlanDetailDao) {
		this.truckDepartPlanDetailDao = truckDepartPlanDetailDao;
	}

	/**
	 * 
	 * 
	 * 设置 车辆查询Service.
	 * 
	 * @param vehicleService
	 *            the new 车辆查询Service
	 * 
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	/**
	 * 
	 * 设置 线路Service.
	 * 
	 * @param lineService
	 *            the new 线路Service
	 * 
	 */
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	/**
	 * 
	 * 设置 操作月台Service.
	 * 
	 * @param platformDispatchService
	 *            the new 操作月台Service
	 * 
	 */
	public void setPlatformDispatchService(IPlatformDispatchService platformDispatchService) {
		this.platformDispatchService = platformDispatchService;
	}

	/**
	 * 
	 * 设置 人员信息Service.
	 * 
	 * @param ownDriverService
	 *            the new 人员信息Service
	 * 
	 */
	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}

	/**
	 * 
	 * 设置 部门Service.
	 * 
	 * @param orgAdministrativeInfoService
	 *            the new 部门Service
	 * 
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 
	 * 设置 计划Service.
	 * 
	 * @param truckDepartPlanService
	 *            the new 计划Service
	 * 
	 */
	public void setTruckDepartPlanService(ITruckDepartPlanService truckDepartPlanService) {
		this.truckDepartPlanService = truckDepartPlanService;
	}

	/**
	 * 
	 * 设置 修改计划日志Service.
	 * 
	 * @param truckDepartPlanUpdateService
	 *            the new 修改计划日志Service
	 * 
	 */
	public void setTruckDepartPlanUpdateService(ITruckDepartPlanUpdateService truckDepartPlanUpdateService) {
		this.truckDepartPlanUpdateService = truckDepartPlanUpdateService;
	}

	/**
	 * 
	 * 设置 自由车查询.
	 * 
	 * 
	 * @param ownVehicleService
	 *            the new 自由车查询
	 * 
	 */
	public void setOwnVehicleService(IOwnVehicleService ownVehicleService) {
		this.ownVehicleService = ownVehicleService;
	}

	/**
	 * 
	 * 设置 短途排班Service.
	 * 
	 * @param truckSchedulingTaskService
	 *            the new 短途排班Service
	 * 
	 */
	public void setTruckSchedulingTaskService(ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}

	/**
	 * 
	 * 设置 消息Service.
	 * 
	 * @param messageService
	 *            the new 消息Service
	 * 
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * 
	 * 设置 查询车辆任务Service.
	 * 
	 * @param loadTruckTaskDetailService
	 *            the new 查询车辆任务Service
	 * 
	 */
	public void setLoadTruckTaskDetailService(ILoadTruckTaskDetailService loadTruckTaskDetailService) {
		this.loadTruckTaskDetailService = loadTruckTaskDetailService;
	}

	/**
	 * 
	 * 设置 查询装卸进度.
	 * 
	 * 
	 * @param loadAndUnloadEfficiencyVehicleService
	 *            the new 查询装卸进度
	 * 
	 */
	public void setLoadAndUnloadEfficiencyVehicleService(
			ILoadAndUnloadEfficiencyVehicleService loadAndUnloadEfficiencyVehicleService) {
		this.loadAndUnloadEfficiencyVehicleService = loadAndUnloadEfficiencyVehicleService;
	}

	/**
	 * 
	 * 设置 月台查询Service.
	 * 
	 * @param platformService
	 *            the new 月台查询Service
	 * 
	 * 
	 */
	public void setPlatformService(IPlatformService platformService) {
		this.platformService = platformService;
	}

	/**
	 * 
	 * 
	 * 设置 车型Service.
	 * 
	 * 
	 * @param leasedVehicleTypeService
	 *            the new 车型Service
	 * 
	 */
	public void setLeasedVehicleTypeService(ILeasedVehicleTypeService leasedVehicleTypeService) {
		this.leasedVehicleTypeService = leasedVehicleTypeService;
	}

	/**
	 * 
	 * 设置 车队查询服务.
	 * 
	 * @param motorcadeService
	 *            the new 车队查询服务
	 * 
	 */
	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	/**
	 * 
	 * 设置 计数Service.
	 * 
	 * @param businessMonitorService
	 *            the new 计数Service
	 * 
	 */
	public void setBusinessMonitorService(IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}

	/**
	 * 
	 * 新增计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-21 下午7:03:48
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#addTruckDepartPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	@Transactional
	public void addTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto)
			throws TruckDepartPlanException {
		// 新增计划明细
		truckDepartPlanDetailDao.addTruckDepartPlanDetail(truckDepartPlanDetailDto);

	}

	/**
	 * 
	 * 删除计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-21 下午7:03:48
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#deleteTruckDepartPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 * 
	 */
	@Override
	@Transactional
	public void deleteTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto)
			throws TruckDepartPlanException {
		// 删除计划明细
		truckDepartPlanDetailDao.deleteTruckDepartPlanDetail(truckDepartPlanDetailDto);
	}

	/**
	 * 
	 * 查询计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-21 下午7:03:48
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryTruckDepartPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 * 
	 */
	@Override
	public List<TruckDepartPlanDetailDto> queryTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto)
			throws TruckDepartPlanException {
		// 查询计划明细
		return truckDepartPlanDetailDao.queryTruckDepartPlanDetail(truckDepartPlanDetailDto);
	}

	/**
	 * 
	 * 更新计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-21 下午7:03:48
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#updateTruckDepartPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 * 
	 */
	@Override
	@Transactional
	public void updateTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto)
			throws TruckDepartPlanException {
		truckDepartPlanDetailDao.updateTruckDepartPlanDetail(truckDepartPlanDetailDto);

	}

	/**
	 * 分页查询发车计划明细
	 * 
	 * 
	 * 分页查询发车计划明细;
	 * 
	 * 当第一次进入本页面，发车计划列表为空，发车日期默认为当天，
	 * 
	 * 出发部门默认为用户当前所在地，用户根据实际需要进行查询。
	 * 
	 * 2.提供出发部门、到达部门、创建人、创建时间、发车日期、车牌号等条件的查询，
	 * 
	 * 用户根据需要自动组合查询条件，发车日期为必填，创建人做成模糊查询自动完成文本框
	 * 
	 * 发车日期默认为当天，BUG-2638创建时间
	 * 
	 * 默认为当天00:00:00点到23:59:59点BUG-2638要求不默认创建时间。
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-26 上午10:10:06
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryTruckDepartPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto,
	 *      int, int)
	 */
	@Override
	public List<TruckDepartPlanDetailDto> queryTruckDepartPlanDetail(TruckDepartPlanDto planDto, int limit, int start)
			throws TruckDepartPlanException {
		// 明细对象
		TruckDepartPlanDetailDto detailDto = new TruckDepartPlanDetailDto();
		// 计划类型
		detailDto.setPlanType(planDto.getPlanType());
		// 计划ID
		detailDto.setTruckDepartPlanId(planDto.getId());
		// 执行查询
		List<TruckDepartPlanDetailDto> tempList = truckDepartPlanDetailDao.queryTruckDepartPlanDetailBylimit(detailDto,
				limit, start);
		// 转换部分数据显示
		if (CollectionUtils.isNotEmpty(tempList)) {
			// 循环补充车辆所属车队、车队名、出发部门、到达部门等信息
			for (TruckDepartPlanDetailDto dto : tempList) {
				// 车辆所属车队
				if (StringUtils.isNotBlank(dto.getVehicleNo())) {
					// 车辆信息
					VehicleAssociationDto vehicle = null;
					try {
						LOGGER.info("车牌号查询车辆:" + dto.getVehicleNo());
						// 查询车辆信息
						vehicle = vehicleService.queryVehicleAssociationDtoByVehicleNo(dto.getVehicleNo());
						// 日志信息
						LOGGER.info("车牌号查询车辆结果:" + vehicle);
						// 异常
					} catch (Exception e) {
						// 日志信息
						LOGGER.error("综合查询车辆接口出错", e);
						// 异常
						throw new TruckDepartPlanException("综合查询车辆接口出错", e);

					}
					// 判空
					if (vehicle != null) {
						// 设置值
						dto.setCarOwnerName(vehicle.getVehicleOrganizationName());
					}
				}
				// 从缓存补充其他数据
				fetchValueByKey(dto);

			}
		}
		return tempList;
	}

	/**
	 * 
	 * 查询车队
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-24 下午3:43:22
	 */
	private String queryCarGroupByCode(String carGroupCode) {
		// 日志
		LOGGER.info("查询车队编码:" + carGroupCode);
		// 查询车队
		MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(carGroupCode);
		// 返回车队名
		if (motorcade != null) {
			// 日志
			LOGGER.info("查询车队结果:" + motorcade.getName());
			// 返回
			return motorcade.getName();
		} else {
			// 日志
			LOGGER.info("查询车队结果:");
			// 返回
			return StringUtils.EMPTY;
		}

	}

	/**
	 * 
	 * 查询部门信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-24 上午11:51:17
	 * 
	 */
	private String queryDepartment(String code) {
		// 日志
		LOGGER.info("查询部门信息编码:" + code);
		// 查询部门信息
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
		// 不为空，则取回部门信息
		if (org != null) {
			// 日志
			LOGGER.info("查询部门信息结果:" + org.getName());
			// 返回
			return org.getName();
		} else {
			LOGGER.info("查询部门信息结果:");
			// 返回
			return null;
		}
	}

	/**
	 * 查询车辆信息列表
	 * 
	 * 1.6.71.6.6
	 * 
	 * 查询可用车辆信息
	 * 
	 * 图（3）中，录入查询条件，点击查询按钮
	 * 
	 * 根据实际情况填写查询字段，点击查询按钮
	 * 
	 * 系统查询出对应的车辆信息，提供用户选择。
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-26 下午5:01:57
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryCarList(com.deppon.foss.module.transfer.scheduling.api.shared.dto.CarInfoDto)
	 * 
	 */
	@Override
	public CarInfoPageDto queryCarList(CarInfoDto carDto, int limit, int start) throws TruckDepartPlanException {
		// 车辆对象
		CarInfoPageDto carInfoPageDto = new CarInfoPageDto();
		// 车辆结果列表
		List<CarInfoDto> carList = new ArrayList<CarInfoDto>();
		// 如果车队不为空,
		// 查询并转换为车辆列表
		if (carDto != null) {
			// 车牌号列表
			List<String> vehicleNos = new ArrayList<String>();
			// 判空
			if (StringUtils.isNotBlank(carDto.getVehicleNo())) {
				// 放入
				vehicleNos.add(carDto.getVehicleNo());
			}
			// 调用综合接口,查询车辆信息列表
			LOGGER.info("调用综合接口,查询车辆信息列表:"
					+ new StringBuffer().append(carDto.getCarOwnerCode()).append(vehicleNos)
							.append(TruckDepartPlanConstants.NOTES_SEPERATOR).append(carDto.getTruckModel())
							.append(TruckDepartPlanConstants.NOTES_SEPERATOR).append(carDto.getCarStatus()).toString());
			// 车辆状态
			String carStatus = carDto.getCarStatus();
			// 判空
			if (StringUtils.isNotBlank(carStatus) && TruckDepartPlanConstants.CAR_STATUS_ALL.equals(carStatus)) {
				// 设空
				carStatus = null;
			}
			// 查询车辆列表
			PaginationDto paginationdto = ownVehicleService.queryVehicleAssociationDtoListPaginationByCondition(
					carDto.getCarOwnerCode(), vehicleNos, carDto.getTruckModel(), carStatus, start, limit,
					DictionaryValueConstants.VEHICLE_TYPE_TRAILER);
			// 转换列表
			// 判空
			if (paginationdto != null && CollectionUtils.isNotEmpty(paginationdto.getPaginationDtos())) {
				// 车辆信息
				CarInfoDto dto = null;
				// 查询
				@SuppressWarnings("unchecked")
				List<VehicleAssociationDto> vehicleList = paginationdto.getPaginationDtos();
				// 不为空
				if (CollectionUtils.isNotEmpty(vehicleList)) {
					// 循环
					for (VehicleAssociationDto vehicleInfo : vehicleList) {
						// 车辆对象
						dto = new CarInfoDto();
						// 车辆所属车队
						dto.setCarOwnerName(vehicleInfo.getVehicleMotorcadeName());
						// 车辆车队小组
						dto.setCarOwnerGroupName(vehicleInfo.getVehicleOrganizationName());
						// 车辆所属车队CODE
						dto.setCarOwnerCode(vehicleInfo.getVehicleOrganizationCode());
						// 车辆状态
						dto.setCarStatus(vehicleInfo.getStatus());
						// 车牌号
						dto.setVehicleNo(vehicleInfo.getVehicleNo());
						// 车型
						if (vehicleInfo.getVehicleLength() != null) {
							// 车型编码
							dto.setTruckModel(vehicleInfo.getVehicleLengthCode());
							// 车型名
							dto.setTruckModelValue(vehicleInfo.getVehicleLengthName());
						}
						// 车辆载重
						if (vehicleInfo.getVehicleDeadLoad() != null) {
							// 车辆载重
							dto.setMaxLoadWeight(vehicleInfo.getVehicleDeadLoad());
						}
						// 车辆净空
						if (vehicleInfo.getVehicleSelfVolume() != null) {
							// 车辆净空
							dto.setTruckVolume(vehicleInfo.getVehicleSelfVolume());
						}
						// 查询车辆状态信息
						// 如果车辆状态为可用,在T_OPT_TRUCK_TASK
						// 通过车牌号查询
						if (TruckDepartPlanConstants.CAR_STATUS_ENABLE.equals(vehicleInfo.getStatus())) {
							// 查询条件
							TfrLoadTruckTaskDetailDto truckTaskDetail = new TfrLoadTruckTaskDetailDto();
							// 车牌号
							truckTaskDetail.setVehicleNo(vehicleInfo.getVehicleNo());
							// 日志
							LOGGER.info("车牌号查询车辆任务:" + vehicleInfo.getVehicleNo());
							// 车牌号查询车辆任务
							List<TfrLoadTruckTaskDetailDto> tempList = loadTruckTaskDetailService
									.queryTruckTaskDetailByVehicleNo(truckTaskDetail);
							// 如果找到，则为【在用】，同时也能获取司机信息
							if (CollectionUtils.isNotEmpty(tempList)) {
								// 【在用】
								dto.setCarStatusDesc(TruckDepartPlanConstants.CAR_STATUS_DESC_USING);
								// 同时获取司机信息
								String driverCode = tempList.get(0).getDriverCode1();
								LOGGER.info("车辆状态:在用,查询到相应的司机编码:" + driverCode);
								// 获取司机信息
								makeDriverInfo(dto, driverCode);
							}
							// 如果以上未找到,则在发车计划表中查找当天的该车牌号是否有发车计划
							else {
								// 通过车牌在发车计划表中查找当天的该车牌号是否有发车计划
								TruckDepartPlanDetailDto detailDto = truckDepartPlanDetailDao
										.queryPlanDetailByVehicleNoAndDate(carDto);
								if (detailDto != null) {
									// 【在用（已计划）】
									dto.setCarStatusDesc(TruckDepartPlanConstants.CAR_STATUS_DESC_PLAN);
									// 同时获取司机信息
									String driverCode = detailDto.getDriverCode1();
									LOGGER.info("车辆状态:在用（已计划）,查询到相应的司机编码:" + driverCode);
									makeDriverInfo(dto, driverCode);
								} else {
									// 【空闲】
									dto.setCarStatusDesc(TruckDepartPlanConstants.CAR_STATUS_DESC_FREE);
								}
							}
						} else {
							LOGGER.info("车辆不可用原因:" + vehicleInfo.getUnavilableReason());
							// 获取不可用原因如果不可用,则从数据字段查询出原因
							dto.setCarStatusDesc(TruckDepartPlanConstants.CAR_STATUS_DESC_N
									+ vehicleInfo.getUnavilableReason());
						}

						carList.add(dto);
					}
				}
				// 总条数
				carInfoPageDto.setTotalCount(paginationdto.getTotalCount());
				// 设置值
				carInfoPageDto.setVehicleList(carList);
			}
		}
		// 返回
		return carInfoPageDto;
	}

	/**
	 * 
	 * 根据司机编号查询司机相关信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * 
	 * @date 2012-12-20 下午4:46:40
	 * 
	 */
	private void makeDriverInfo(CarInfoDto dto, String driverCode) {
		if (dto != null && StringUtils.isNotBlank(driverCode)) {
			LOGGER.info("根据司机编号查询司机相关信息:" + driverCode);
			// 同时获取司机信息
			DriverAssociationDto ownDriver = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(driverCode);
			// 司机CODE
			dto.setDriverCode(driverCode);
			if (ownDriver != null) {
				// 司机名
				dto.setDriverName(ownDriver.getDriverName());
				// 司机车队小组CODE
				dto.setDriverOrgCode(ownDriver.getDriverOrganizationCode());
				// 司机车队小组名称
				dto.setDriverOrgName(ownDriver.getDriverOrganizationName());
			}
		}

	}

	/**
	 * 
	 * 新增或更新计划明细前预处理
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-29 上午9:12:44
	 * 
	 */
	private void preSaveOrUpdatePlanDetail(TruckDepartPlanDetailDto detailDto, CurrentInfo user) {

		// 计划状态
		detailDto.setPlanStatus(TruckDepartPlanConstants.PLAN_STATUS_NEW);
		// 状态
		detailDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		// 司机信息
		validateAndSetDriverInfo(detailDto);
		// 线路及线路名称、预计到达时间
		lineAndPlanArriveTime(detailDto);
		// 验证车牌
		validateVehicle(detailDto);
		// 验证预计发车时间是否与发车计划日期一致
		validatePlanDepartTime(detailDto);
		// 明细操作用户信息
		planDetailUserInfo(detailDto, user);

	}

	/**
	 * 
	 * 发车计划明细操作用户信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-24 上午10:41:24
	 * 
	 */
	private void planDetailUserInfo(TruckDepartPlanDetailDto detailDto, CurrentInfo user) {
		if (detailDto != null && user != null) {
			// 更新发车明细操作
			if (StringUtils.isNotBlank(detailDto.getId())) {
				// 更新信息
				detailDto.setUpdateTime(Calendar.getInstance().getTime());
				// 更新人信息
				detailDto.setUpdateUserCode(user.getEmpCode());
				detailDto.setUpdateOrgCode(user.getCurrentDeptCode());
				detailDto.setUpdateUserName(user.getEmpName());

			}
			// 新增发车明细操作
			else {
				// 创建时间
				detailDto.setCreateTime(Calendar.getInstance().getTime());
				// 创建人信息
				detailDto.setCreateUserCode(user.getEmpCode());
				detailDto.setCreateOrgCode(user.getCurrentDeptCode());
				detailDto.setCreateUserName(user.getEmpName());

			}
		}
	}

	/**
	 * 
	 * 验证预计发车时间是否与发车计划日期一致
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-13 下午4:19:35
	 * 
	 */
	private void validatePlanDepartTime(TruckDepartPlanDetailDto detailDto) {
		if (detailDto.getDepartDate() != null && detailDto.getPlanDepartTime() != null) {
			// 计划日期
			String departDate = DateUtils.convert(detailDto.getDepartDate(), DateUtils.DATE_FORMAT);
			// 预计发车时间
			String planDepartTime = DateUtils.convert(detailDto.getPlanDepartTime(), DateUtils.DATE_FORMAT);
			// 如果发车计划日期与预计出发日期不为同一天，则跑出异常
			if (StringUtils.isNotBlank(departDate)) {
				if (!departDate.equals(planDepartTime)) {
					LOGGER.info("计划发车时间与计划发车日期不在同一天");
					throw new TruckDepartPlanException("计划发车时间与计划发车日期不在同一天", "");
				}
			} else {
				LOGGER.info("计划发车日期为空");
				throw new TruckDepartPlanException("计划发车日期为空", "");
			}
		}
	}

	/**
	 * 
	 * 验证车牌是否存在(所有车辆中查找)
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-1 下午4:01:41
	 * 
	 */
	private void validateVehicle(TruckDepartPlanDetailDto detailDto) {
		if (detailDto != null && StringUtils.isNotBlank(detailDto.getVehicleNo())) {
			// 根据车牌查询车辆信息
			PlanVehicleDto vehicle = this.queryVehicleByVechileNo(detailDto);
			// 没有相关车辆信息
			if (vehicle == null) {
				throw new TruckDepartPlanException(detailDto.getVehicleNo() + "车牌不存在", "");
			} else {
				// 非拖头情况,验证车型是否与车牌对应车辆匹配
				if (!DictionaryValueConstants.VEHICLE_TYPE_TRACTORS.equals(vehicle.getVehicleType())
						&& StringUtils.isNotBlank(vehicle.getTruckModel())
						&& !vehicle.getTruckModel().equals(detailDto.getTruckModel())) {
					throw new TruckDepartPlanException("车牌号与车型不匹配", "");
				}
			}
		}

	}

	/**
	 * 
	 * 线路及线路名称、预计到达时间
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-1 下午3:47:26
	 * 
	 */
	private void lineAndPlanArriveTime(TruckDepartPlanDetailDto detailDto) {
		// 线路名称、预计到达时间
		if (detailDto != null && StringUtils.isNotBlank(detailDto.getLineNo())) {
			LOGGER.info("查询线路:" + detailDto.getLineNo());
			// 查询线路
			LineEntity lineInfo = null;
			
			//判断线路的虚拟code是来自零担还是快递-352203
			if(FossConstants.NO.equals(detailDto.getLineNoExpress())){
				//零担
				lineInfo = lineService.queryLineByVirtualCode(detailDto.getLineNo());
			}
			
			// 在途普车时效
			int ontheWayTimeTotal = TruckDepartPlanConstants.COMMON_AGING_DEFAULT;
			if (lineInfo != null) {
				detailDto.setLineName(lineInfo.getLineName());
				// 在途普车时效
				if (lineInfo.getCommonAging() != null) {
					ontheWayTimeTotal = BigDecimal
							.valueOf((lineInfo.getCommonAging() / TruckDepartPlanConstants.COMMON_AGING_DIVISOR))
							.multiply(TruckDepartPlanConstants.MIN_60).multiply(TruckDepartPlanConstants.SEC_60)
							.intValue();
					LOGGER.info("在途普车时效:" + ontheWayTimeTotal + "秒");
				}
			}else{
				//增加ECS屏蔽开关
				if (configurationParamsService.queryTfrSwitch4Ecs()) {
					Log.error("调用ECS系统接口 ：getExpressLine");
					//获取快递线路名称
					List<DepartureStandardDto> list = getExpressLine(detailDto.getOrigOrgCode(), detailDto.getDestOrgCode());
					DepartureStandardDto tempDto = null;
					if (CollectionUtils.isNotEmpty(list)) {
						for (DepartureStandardDto dto : list) {
							if (dto.getLineVirtualCode().equals(detailDto.getLineNo())) {
								tempDto = dto;
								break;
							}
						}
					}
					ExpressLineEntity explineInfo = new ExpressLineEntity();
					explineInfo.setLineName(tempDto.getLineName());

					if (TruckDepartPlanConstants.TRUCK_TYPE_MYSELF_OWN.equals(detailDto.getTruckType())) {
						explineInfo.setCommonAging(Long.valueOf(this.getOwnLimitation()));
					} else {
						explineInfo.setCommonAging(Long.valueOf(this.getOutSourceLimitation()));
					}
					if (explineInfo != null) {
						detailDto.setLineName(explineInfo.getLineName());
						// 在途普车时效
						if (explineInfo.getCommonAging() != null) {
							ontheWayTimeTotal = explineInfo.getCommonAging().intValue();
							LOGGER.info("在途普车时效:" + ontheWayTimeTotal + "秒");
						}
					}
				} else {
					Log.error("调用FOSS系统接口：expresslineService");
					ExpressLineEntity explineInfo = expresslineService.queryLineByVirtualCode(detailDto.getLineNo()); 
					if (explineInfo != null) {
						detailDto.setLineName(explineInfo.getLineName());
						// 在途普车时效
						if (explineInfo.getCommonAging() != null) {
							ontheWayTimeTotal = BigDecimal
									.valueOf((explineInfo.getCommonAging() / TruckDepartPlanConstants.COMMON_AGING_DIVISOR))
									.multiply(TruckDepartPlanConstants.MIN_60).multiply(TruckDepartPlanConstants.SEC_60)
									.intValue();
							LOGGER.info("在途普车时效:" + ontheWayTimeTotal + "秒");
						}
					}
				}
			}
			// 查询到达时间
			if (StringUtils.isNotBlank(detailDto.getFrequencyNo()) && StringUtils.isNumeric(detailDto.getFrequencyNo())) {
				int frequencyNo = Integer.parseInt(detailDto.getFrequencyNo());
				// 计划到达时间
				DepartureStandardDto departureDto = null;
				try {
					LOGGER.info("查询线路班次:" + detailDto.getLineNo() + "," + frequencyNo);
					
					//判断线路虚拟编码是来自零担还是快递-352203
					if("N".equals(detailDto.getLineNoExpress())){
						//零担
						departureDto = lineService.queryDepartureStandardByLineSequence(detailDto.getLineNo(), frequencyNo);
					}
					
				} catch (Exception e) {
					LOGGER.error("查询线路班次异常", e);
				}
				// 存在此线路班次
				if (departureDto != null) {
					// 计算预计到达时间
					if (StringUtils.isNotBlank(departureDto.getArriveTime()) && departureDto.getArriveDay() != null) {
						// 计算到达时间
						Date planArriveTime = calcPlanArriveTime(detailDto.getPlanDepartTime(), ontheWayTimeTotal,
								departureDto.getLeaveTime());
						// 设置预计到达时间
						detailDto.setPlanArriveTime(planArriveTime);
					}
				}
				else {
					// ECS系统接口屏蔽类
					if (configurationParamsService.queryTfrSwitch4Ecs()) {
						Log.error("调用ECS系统接口getExpressLine");
						// 调用快递接口通过线路虚拟编码和班次，查出发车时间和到达时间
						List<DepartureStandardDto> departureDtoList = getExpressLine(detailDto.getOrigOrgCode(), detailDto.getDestOrgCode());
						for (DepartureStandardDto temp : departureDtoList) {
							if (temp.getOrder().toString().equals(String.valueOf(frequencyNo)) 
									&& detailDto.getLineNo().equals(temp.getLineVirtualCode())) {
								departureDto = temp;
							}
						}
					} else {
						Log.error("调用FOSS系统接口expresslineService");
						departureDto = expresslineService.queryDepartureStandardByLineSequence(detailDto.getLineNo(), frequencyNo);
					}

					if (departureDto != null) {
						// 计算预计到达时间
						if (StringUtils.isNotBlank(departureDto.getArriveTime()) && departureDto.getArriveDay() != null) {
							// 计算到达时间
							Date planArriveTime = calcPlanArriveTime(detailDto.getPlanDepartTime(), ontheWayTimeTotal,
									departureDto.getLeaveTime());
							// 设置预计到达时间
							detailDto.setPlanArriveTime(planArriveTime);
						}
					}
					// 不存在此线路、班次,则提示用户，无此班次需要调整为加发
					// 正常班次，则提醒用户需要，改为加发
					else if (TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL.equals(detailDto.getFrequencyType())) {
						throw new TruckDepartPlanException("发车时效无此班次，如需继续，请将班次类型调整为[加发]", "");
					}
				}
			}
			// 如果无班次的情况，预计到达时间如何取,如果以上都没有算出预计到达时间,则使用普车时效进行计算
			if (detailDto.getPlanArriveTime() == null) {
				// 计算到达时间
				Date planArriveTime = calcPlanArriveTime(detailDto.getPlanDepartTime(), ontheWayTimeTotal, null);
				// 设置预计到达时间
				detailDto.setPlanArriveTime(planArriveTime);
			}

		}

	}

	/**
	 * 
	 * 获取到达时间
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2013-2-27 下午4:44:50
	 * 
	 */
	private Date calcPlanArriveTime(Date sourceDepartDate, int addDateNum, String leaveTime) {
		// 预计到达时间
		Date planArriveTime = null;
		// 获取当前时间
		Calendar c = Calendar.getInstance();
		// 设置为计划发车时间
		if (sourceDepartDate != null) {
			// 原始时间
			c.setTime(sourceDepartDate);
		}
		// 根据到达天数，新增至到达当天
		c.add(Calendar.SECOND, addDateNum);
		// 根据预计时间，设置当前预计时间
		if (StringUtils.isNotBlank(leaveTime)) {
			// 预计到达时间
			planArriveTime = DateUtils.concat(DateUtils.convert(c.getTime(), DateUtils.DATE_FORMAT), leaveTime);
		} else {
			// 预计到达时间
			planArriveTime = c.getTime();
		}
		// 返回预计到达时间
		return planArriveTime;
	}

	/**
	 * 验证和设置司机信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-1 下午3:43:40
	 * 
	 */
	private void validateAndSetDriverInfo(TruckDepartPlanDetailDto detailDto) {
		// 不空
		if (detailDto != null) {
			// 司机名称1
			DriverAssociationDto dirver = null;
			// 不空
			if (StringUtils.isNotBlank(detailDto.getDriverCode1())) {
				// 公司车
				if (TruckDepartPlanConstants.TRUCK_TYPE_MYSELF_OWN.equals(detailDto.getTruckType())) {
					// 司机
					dirver = queryOwnDriverInfo(detailDto.getDriverCode1());
					// 不空
					if (dirver != null) {
						// 设置司机1信息
						writeDriver1Info(detailDto, dirver);
					}

				}// 外请车
				else {
					dirver = truckDepartPlanService.queryDriverInfoByDriverCode(detailDto.getDriverCode1(),
							detailDto.getTruckType());
					if (dirver != null) {
						// 设置司机1信息
						writeDriver1Info(detailDto, dirver);
					}
				}
			}

			// 司机名称2
			if (StringUtils.isNotBlank(detailDto.getDriverCode2())) {
				// 公司车
				if (TruckDepartPlanConstants.TRUCK_TYPE_MYSELF_OWN.equals(detailDto.getTruckType())) {
					// 查询司机
					dirver = queryOwnDriverInfo(detailDto.getDriverCode2());
					// 不空
					if (dirver != null) {
						// 设置司机2信息
						writeDriver2Info(detailDto, dirver);
					}
				}
				// 外请车
				else {
					// 司机
					dirver = truckDepartPlanService.queryDriverInfoByDriverCode(detailDto.getDriverCode2(),
							detailDto.getTruckType());
					// 设置司机2信息
					if (dirver != null) {
						// 设置司机2信息
						writeDriver2Info(detailDto, dirver);
					}
				}
			}
		}
	}

	/**
	 * 
	 * 设置司机2的信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-3 下午1:43:30
	 * 
	 */
	private void writeDriver2Info(TruckDepartPlanDetailDto detailDto, DriverAssociationDto dirver) {
		// 不空
		if (dirver != null) {
			// 司机姓名
			detailDto.setDriverName2(dirver.getDriverName());
			// 如果司机电话没有填写
			if (StringUtils.isBlank(detailDto.getDriverPhone2())) {
				// 如果司机电话没有填写
				detailDto.setDriverPhone2(dirver.getDriverPhone());
			}
		}
	}

	/**
	 * 
	 * 设置司机1的信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-3 下午1:39:28
	 * 
	 */
	private void writeDriver1Info(TruckDepartPlanDetailDto detailDto, DriverAssociationDto dirver) {
		// 不空
		if (dirver != null && detailDto != null) {
			// 姓名//不空
			detailDto.setDriverName1(dirver.getDriverName());
			// 如果司机电话没有填写
			if (StringUtils.isBlank(detailDto.getDriverPhone1())) {
				// 电话
				detailDto.setDriverPhone1(dirver.getDriverPhone());
			}
		}
	}

	/**
	 * 
	 * 新增或更新计划明细
	 * 
	 * 长途：
	 * 
	 * 在制定长途发车计划界面点击新增外请车
	 * 
	 * 弹出图（2）的外请车发车计划界面，根据出发部门、到达部门、发车日期，
	 * 
	 * 自动带出相应的一些默认值，完善相应的线路、车辆、司机信息等 长途发车计划表单数据
	 * 完善必填字段，需要遵循SR-13、SR-14、SR-15、SR-16
	 * 
	 * 点击保存
	 * 
	 * 验证保存的发车计划信息
	 * 
	 * 根据业务规则验证相应的字段信息
	 * 
	 * 如果验证通过
	 * 
	 * 保存发车计划，返回主界面，刷新
	 * 
	 * 发车计划列表，重新计算运力实时统计信息 ，如果已经保存作为发车计划的线路班次，则在可用线路班次自动取消显示，以免重复添加
	 * 
	 * 如果验证未通过 返回错误提示信息，用户完善相应的错误信息，保存知道完全验证通过，并保存执行验证通过的相应后续步骤。
	 * 
	 * 录入的线路与班次重复时，弹出提示框，提示已经录入（其他的错误信息也需要弹出提示）
	 * 
	 * 点击确认或关闭窗口后回到主界面，
	 * 
	 * 同时焦点在该编辑项上；
	 * 
	 * 在制定长途发车计划界面点击新增公司车
	 * 
	 * 弹出图（3）的公司车发车计划界面，用户根据需要查询车辆列表，选择相应的车辆的同时，
	 * 
	 * 将已选择车辆的信息自动带到下面的车辆信息表单的相应字段中填充，
	 * 
	 * 继续完善相应的线路、车辆、司机信息等 长途发车计划表单数据
	 * 
	 * 选择时自动带到下面表单的字段有：车牌号、最大载重、实际最大载重、
	 * 
	 * 车容积、预计装载重量（默认取实际最大载重）、预计装载体积（默认取车容积），
	 * 
	 * 用户可根据实际情况需要修改预计装载重量和预计装载体积,完善必填字段，
	 * 
	 * 需要遵循SR-13、SR-14、SR-15、SR-16
	 * 
	 * 点击保存
	 * 
	 * 验证保存的发车计划信息
	 * 
	 * 根据业务规则验证相应的字段信息
	 * 
	 * 如果验证通过
	 * 
	 * 保存发车计划，返回主界面，刷新
	 * 
	 * 发车计划列表，重新计算运力实时统计信息 ，
	 * 
	 * 如果已经保存作为发车计划的线路班次，
	 * 
	 * 则在可用线路班次自动取消显示，以免重复添加
	 * 
	 * 如果验证未通过
	 * 
	 * 返回错误提示信息，用户完善相应的错误信息，保存知道完全验证通过，并保存执行验证通过的相应后续步骤。
	 * 
	 * 短途： 已做发车计划列表，点击编辑按钮，调整车型等信息
	 * 
	 * 参照下面的运力统计信息，发现运力少于货量预测，则修改大的车型；
	 * 
	 * 运力大于货量预测，则修改小的车型，其他字段可根据实际情况进行修改
	 * 
	 * （线路字段不可编辑，车辆载重、车辆净空对于自有车而言是不可编辑的，
	 * 
	 * 对于外请车来说则是可编辑的）。如果修改的是外请车，则弹出图（2）所示，
	 * 
	 * 待用户修改 完善相应的线路、车辆、司机信息;
	 * 
	 * 如果修改的是公司车和班车，则弹出图（3），待用户修改完善相应的线路、车辆、司机信息
	 * 
	 * 修改完后，点击保存按钮;
	 * 
	 * 验证字段信息及必填信息，验证通过后，系统保存修改的发车计划信息
	 * 
	 * （比对排班表，记录修改日志，发送在线通知给相应的排班车队部门，
	 * 
	 * 在线通知的内容需要根据模板进行生成）;
	 * 
	 * 执行相关验证，具体按照下面的规则执行;
	 * 
	 * 如果通过，则提示保存成功，如果未通过，则返回提示信息，直到合法并保存成功为止
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-27 下午8:09:07
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#saveOrUpdateInnerPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 * 
	 */
	@Override
	@Transactional
	public void saveOrUpdateInnerPlanDetail(TruckDepartPlanDetailDto detailDto, CurrentInfo user)
			throws TruckDepartPlanException {
		// 月台
		PlatformDistributeEntity platformentity = null;
		// 判空
		if (detailDto != null) {
			// 保存前预处理计划明细对象
			preSaveOrUpdatePlanDetail(detailDto, user);
			// 如果车牌号和月台号都有才允许保存和更新月台号
			if (StringUtils.isNotBlank(detailDto.getVehicleNo()) && StringUtils.isNotBlank(detailDto.getPlatformNo())) {
				// 如果车牌号和月台号都有才允许保存和更新月台号
				platformentity = saveOrUpdatePlatform(detailDto, user);
			}
			// 如果是新增月台，则将月台ID 返回
			if (platformentity != null) {
				// 如果是新增月台，则将月台ID 返回
				detailDto.setPlatformDistributeId(platformentity.getId());
			}
			// 更新发车明细操作
			if (StringUtils.isNotBlank(detailDto.getId())) {
				// 验证发车计划明细
				validateTruckDepartPlanDetail(detailDto, TruckDepartPlanConstants.OPERATION_TYPE_UPDATE);
				// 记录更新日志
				makePlanUpdateLog(detailDto, TruckDepartPlanConstants.OPERATION_TYPE_UPDATE, user);
				// 更新发车计划的情况，如果是对线路，班次，车辆，司机进行调整(存在针对排班表不同的情况),通知模板为：发车计划中：线路Xx，班次信XX，车辆Xx，司机XX；调整为：线路XX、班次XX，车辆yy，司机yy
				if (TruckDepartPlanConstants.PLAN_TYPE_SHORT.equals(detailDto.getPlanType())) {
					// 比对
					compareToShortchedule(detailDto, user, TruckDepartPlanConstants.OPERATION_TYPE_UPDATE);
				}
				// 根据 出发部门、到达部门、日期、线路、班次查询，校验排除当天线路班次唯一
				queryCurrentDayLineFrequencyOnly(detailDto);
				// 如果是已经下发的发车计划，则需要执行下发校验条件
				if (TruckDepartPlanConstants.PLAN_STATUS_RELEASE.equals(detailDto.getPlanStatus())
						&& doValidateReleasePlanDetail(detailDto)) {
					// 跑出异常
					throw new TruckDepartPlanException("本发车计划已下发，请确认(司机信息，车辆信息，线路、发车时间、班次、车型、车牌等)已经完善", "");
				}

				// 更新
				truckDepartPlanDetailDao.updateTruckDepartPlanDetail(detailDto);
				// 新增计数器(发车计划次数)
				BusinessMonitorIndicator indicator = BusinessMonitorIndicator.TRUCK_DEPART_PLAN_MODIFY_COUNT;
				// 计数
				businessMonitorService.counter(indicator, user);

			}
			// 新增发车明细操作
			else {
				// ID
				detailDto.setId(UUIDUtils.getUUID());
				// 设置导入标记(非初始化导入)
				detailDto.setInitFlag(TruckDepartPlanConstants.INIT_FLAG_N);
				// 出发状态(未出发)
				detailDto.setHasLeft(TruckDepartPlanConstants.LEFT_FLAG_N);
				// 验证发车计划明细
				validateTruckDepartPlanDetail(detailDto, TruckDepartPlanConstants.OPERATION_TYPE_INSERT);
				// 记录新增日志
				makePlanUpdateLog(detailDto, TruckDepartPlanConstants.OPERATION_TYPE_INSERT, user);
				// 根据 出发部门、到达部门、日期、线路、班次查询，校验排除当天线路班次唯一
				queryCurrentDayLineFrequencyOnly(detailDto);
				// 新增
				truckDepartPlanDetailDao.addTruckDepartPlanDetail(detailDto);
				// 新增计数器(发车计划次数)
				BusinessMonitorIndicator indicator = BusinessMonitorIndicator.TRUCK_DEPART_PLAN_COUNT;
				// 计数
				businessMonitorService.counter(indicator, user);
				// 短途新增发车计划的情况，通知模板为：新增发车计划：线路XX、班次XX，车辆yy，司机yy
				if (TruckDepartPlanConstants.PLAN_TYPE_SHORT.equals(detailDto.getPlanType())) {
					// 通知信息
					compareToShortchedule(detailDto, user, TruckDepartPlanConstants.OPERATION_TYPE_INSERT);
				}

			}
		}
	}

	/**
	 * 
	 * 查询短途排班，并与短途排班进行比较，比对内容（线路，班次，车辆，司机），如果有，则生成通知待办
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-19 下午4:13:25
	 * 
	 */
	private void compareToShortchedule(TruckDepartPlanDetailDto detailDto, CurrentInfo user, String operationType) {
		// 新增发车计划
		if (TruckDepartPlanConstants.OPERATION_TYPE_INSERT.equals(operationType)) {
			// 如果没查询到本排班任务（表示新增的发车计划）,计划信息、模板构造通知信息
			createMessageWithTemplate(detailDto, null, user, CompareUtils.PLAN_ADD_NEW);
		} else
		// 更新发车计划
		if (TruckDepartPlanConstants.OPERATION_TYPE_UPDATE.equals(operationType)) {
			// 判空
			if (detailDto != null && StringUtils.isNotBlank(detailDto.getId())) {
				// 根据ID进行查询排班
				TruckDepartPlanDetailDto tempDetailDto = truckDepartPlanDetailDao
						.queryTruckDepartPlanDetailById(detailDto);
				// 如果导入排班ID 不为空
				if (tempDetailDto != null && StringUtils.isNotBlank(tempDetailDto.getScheduleTaskId())) {
					// 排班任务查询条件
					TruckSchedulingTaskEntity schedulingTask = new TruckSchedulingTaskEntity();
					// 任务ID
					schedulingTask.setId(tempDetailDto.getScheduleTaskId());
					// 根据ID 查询排班计划
					LOGGER.info("排班任务查询条件:" + schedulingTask.toString());
					// 查询排班
					SimpleTruckScheduleDto tempSchedulingTask = truckSchedulingTaskService
							.queryTruckSchedulingTask(schedulingTask);
					// 根据具体的排班信息、计划信息、模板构造通知信息
					createMessageWithTemplate(detailDto, tempSchedulingTask, user, CompareUtils.SCHEDULING_MODIDY);
				}
			}
		}

	}

	/**
	 * 根据具体的排班信息、计划信息、模板构造通知信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-8 下午5:27:03
	 */
	private void createMessageWithTemplate(TruckDepartPlanDetailDto detailDto,
			SimpleTruckScheduleDto tempSchedulingTask, CurrentInfo user, String acctionType) {
		// 如果查询到本排班任务,则进行比较比较
		Map<String, String> compMap = appendChangeMessage(detailDto, tempSchedulingTask, acctionType);
		SmsParamDto tempDto = new SmsParamDto();
		// 模板值
		tempDto.setMap(compMap);
		// 通知头
		String header = StringUtils.EMPTY;
		// 通知尾
		String tail = StringUtils.EMPTY;
		// 如果为修改才查询模板，否则只需要模板尾发送通知
		if (CompareUtils.SCHEDULING_MODIDY.equals(acctionType)) {
			// 短信头
			tempDto.setSmsCode(CompareUtils.TFR_PLAN_NOTICE_TMP_HEADER);
			// 调用综合接口查询模板
			header = sMSTempleteService.querySmsByParam(tempDto);
		}
		// 短信头
		tempDto.setSmsCode(CompareUtils.TFR_PLAN_NOTICE_TMP_TAIL);
		// 调用综合接口查询模板
		tail = sMSTempleteService.querySmsByParam(tempDto);
		// 根据模板取出结果
		String compareResult = CompareUtils.compareDetaiAndTask(compMap, header, tail);
		// 如果比对结果不为空
		if (StringUtils.isNotBlank(compareResult)) {
			LOGGER.info("通知信息内容:" + compareResult);
			// 创建消息对象
			InstationJobMsgEntity msg = createMsg(detailDto, compareResult, user);
			// 系统在线通知该车队部门，小组经理根据变动项调整排班表内容;
			messageService.createBatchInstationMsg(msg);
		}

	}

	/**
	 * 创建消息对象 通知模板为：发车计划中：线路Xx，班次信XX，车辆Xx，司机XX；调整为：线路XX、班次XX，车辆yy，司机yy
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-19 下午6:05:06
	 * 
	 */
	private InstationJobMsgEntity createMsg(TruckDepartPlanDetailDto detailDto, String compareResult, CurrentInfo user) {
		// 消息对象
		InstationJobMsgEntity jobMsg = new InstationJobMsgEntity();
		// id
		jobMsg.setId(UUIDUtils.getUUID());
		// 发送方编码
		jobMsg.setSenderCode(user.getEmpCode());
		// 姓名
		jobMsg.setSenderName(user.getEmpName());
		// 发送组织信息
		jobMsg.setSenderOrgCode(user.getCurrentDeptCode());
		// 部门名
		jobMsg.setSenderOrgName(user.getCurrentDeptName());
		// 接收方组织编码(计划的车队)
		jobMsg.setReceiveOrgCode(detailDto.getLongCarGroup());
		// 组织车队
		jobMsg.setReceiveOrgName(queryCarGroupByCode(detailDto.getLongCarGroup()));
		// 接收方类型
		jobMsg.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
		// 消息内容
		jobMsg.setContext(compareResult.toString());
		// 站内消息类型
		jobMsg.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
		// 发送时间
		jobMsg.setPostDate(Calendar.getInstance().getTime());
		// 消息状态
		jobMsg.setStatus(MessageConstants.MSG__STATUS__PROCESSING);
		// 返回
		return jobMsg;
	}

	/**
	 * 创建比对信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-19 下午5:00:59
	 */
	private Map<String, String> appendChangeMessage(TruckDepartPlanDetailDto detailDto,
			SimpleTruckScheduleDto tempSchedulingTask, String acctionType) {
		Map<String, String> compMap = new HashMap<String, String>();
		// 如果原值不等于旧值
		if (detailDto != null) {
			// 线路名
			String lineName = "";
			// 修改排班对应的发车计划
			if (CompareUtils.SCHEDULING_MODIDY.equals(acctionType) && tempSchedulingTask != null) {
				// 不空
				if (StringUtils.isNotBlank(tempSchedulingTask.getLineNo())) {
					// 根据虚拟code查询线路名称
					LineEntity line = null;
					//判断线路来源-352203
					if("N".equals(detailDto.getLineNoExpress())){
						//零担
						line = lineService.queryLineByVirtualCode(tempSchedulingTask.getLineNo());
					}
					// 不空
					if (line != null) {
						// 线路名
						lineName = line.getLineName();
					}else{
						ExpressLineEntity explineEntity = expresslineService.queryLineByVirtualCode(tempSchedulingTask.getLineNo());
						if (explineEntity != null) {
							// 线路名
							lineName = explineEntity.getLineName();
						}
					}
				}
				// 短途排班与计划的比对
				if (!StringUtils.equals(lineName, detailDto.getLineName())
						|| !StringUtils.equals(tempSchedulingTask.getFrequencyNo(), detailDto.getFrequencyNo())
						|| !StringUtils.equals(tempSchedulingTask.getVehicleNo(), detailDto.getVehicleNo())
						|| !StringUtils.equals(tempSchedulingTask.getDriverCode(), detailDto.getDriverCode1())) {
					// 调整关键字
					compMap.put(CompareUtils.CHANGE_DESC_KEY, CompareUtils.CHANGE_DESC_KEY);
					// 日期
					compMap.put(CompareUtils.FIELD_NAME_SCHEDULE_DATE_OLD,
							DateUtils.convert(tempSchedulingTask.getSchedulingDate(), DateUtils.DATE_FORMAT));
					// 线路
					compMap.put(CompareUtils.FIELD_NAME_LINE_NO_OLD, lineName);
					// 班次
					compMap.put(CompareUtils.FIELD_NAME_FREQUENCY_NO_OLD, tempSchedulingTask.getFrequencyNo());
					// 车辆
					compMap.put(CompareUtils.FIELD_NAME_VEHICLE_NO_OLD, tempSchedulingTask.getVehicleNo());
					// 司机
					compMap.put(CompareUtils.FIELD_NAME_DRIVER_NAME_OLD, tempSchedulingTask.getDriverName());

					// 调整后的值
					putTailInfo(compMap, detailDto);
				}

			}// 针对排班新增的发车计划
			else if (CompareUtils.PLAN_ADD_NEW.equals(acctionType)) {
				// 加发计划关键字
				compMap.put(CompareUtils.CHANGE_ADD_VECHILE_DESC_KEY, CompareUtils.CHANGE_ADD_VECHILE_DESC_KEY);
				// 加发的发车计划信息
				putTailInfo(compMap, detailDto);

			}
		}
		return compMap;
	}

	/**
	 * 放入调整后的通知信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-8 下午5:21:01
	 */
	private void putTailInfo(Map<String, String> compMap, TruckDepartPlanDetailDto detailDto) {
		if (compMap != null && detailDto != null) {
			// 日期
			compMap.put(CompareUtils.FIELD_NAME_SCHEDULE_DATE_NEW,
					DateUtils.convert(detailDto.getDepartDate(), DateUtils.DATE_FORMAT));
			// 线路
			compMap.put(CompareUtils.FIELD_NAME_LINE_NO_NEW, detailDto.getLineName());
			// 班次
			compMap.put(CompareUtils.FIELD_NAME_FREQUENCY_NO_NEW, detailDto.getFrequencyNo());
			// 车辆
			compMap.put(CompareUtils.FIELD_NAME_VEHICLE_NO_NEW, detailDto.getVehicleNo());
			// 司机
			compMap.put(CompareUtils.FIELD_NAME_DRIVER_NAME_NEW, detailDto.getDriverName1());
		}
	}

	/**
	 * 
	 * 验证发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-17 上午11:08:48
	 * 
	 */
	private void validateTruckDepartPlanDetail(TruckDepartPlanDetailDto detailDto, String actionType) {
		// 校验所有非空的字段
		if (StringUtils.isBlank(detailDto.getId())) {
			// 计划明细ID不能为空
			throw new TruckDepartPlanException("计划明细ID不能为空", "");
		}
		// 发车计划ID
		if (StringUtils.isBlank(detailDto.getTruckDepartPlanId())) {
			// 发车计划ID不能为空
			throw new TruckDepartPlanException("发车计划ID不能为空", "");
		}
		// 计划类型
		if (StringUtils.isBlank(detailDto.getPlanType())) {
			// 计划类型不能为空
			throw new TruckDepartPlanException("计划类型不能为空", "");
		}
		// 线路虚拟编号
		if (StringUtils.isBlank(detailDto.getLineNo())) {
			// 线路虚拟编号不能为空
			throw new TruckDepartPlanException("线路虚拟编号不能为空", "");
		}
		// 发车日期
		if (detailDto.getDepartDate() == null) {
			// 发车日期不能为空
			throw new TruckDepartPlanException("发车日期不能为空", "");
		}
		// 计划发车时间
		if (detailDto.getPlanDepartTime() == null) {
			// 发车日期不能为空
			throw new TruckDepartPlanException("计划发车时间不能为空", "");
		}
		// 出发部门
		if (StringUtils.isBlank(detailDto.getOrigOrgCode())) {
			// 出发部门不能为空
			throw new TruckDepartPlanException("出发部门不能为空", "");
		}
		// 到达部门
		if (StringUtils.isBlank(detailDto.getDestOrgCode())) {
			// 到达部门不能为空
			throw new TruckDepartPlanException("到达部门不能为空", "");
		}

		// 是否正班车
		if (StringUtils.isBlank(detailDto.getIsOnScheduling())) {
			// 是否正班车不能为空
			throw new TruckDepartPlanException("是否正班车不能为空", "");
		}
		// 班次类型
		if (StringUtils.isBlank(detailDto.getFrequencyType())) {
			// 班次类型不能为空
			throw new TruckDepartPlanException("班次类型不能为空", "");
		} else {
			// 长短途情况、车队字段：当班次为正常、加发时，必填
			if ((TruckDepartPlanConstants.FREQUENCY_TYPE_ADD.equals(detailDto.getFrequencyType()) || TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL
					.equals(detailDto.getFrequencyType())) && StringUtils.isBlank(detailDto.getLongCarGroup())) {
				// 当班次为正常、加发时，车队必填
				throw new TruckDepartPlanException("当班次为正常、加发时，车队必填", "");
			}
			// 车型
			if ((TruckDepartPlanConstants.FREQUENCY_TYPE_ADD.equals(detailDto.getFrequencyType()) || TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL
					.equals(detailDto.getFrequencyType())) && StringUtils.isBlank(detailDto.getTruckModel())) {
				// 车型不能为空
				throw new TruckDepartPlanException("车型不能为空", "");
			}
		}
		// 车辆归属类型
		if (StringUtils.isBlank(detailDto.getTruckType())) {
			// 车辆归属类型不能为空
			throw new TruckDepartPlanException("车辆归属类型不能为空", "");
		}
		// 如果是新建，则需要创建人相关人信息
		if (TruckDepartPlanConstants.OPERATION_TYPE_INSERT.equals(actionType)) {
			// 状态
			if (StringUtils.isBlank(detailDto.getStatus())) {
				// 状态不能为空
				throw new TruckDepartPlanException("状态不能为空", "");
			}
			// 创建时间
			if (detailDto.getCreateTime() == null) {
				// 创建时间不能为空
				throw new TruckDepartPlanException("创建时间不能为空", "");
			}
			// 创建人编码
			if (StringUtils.isBlank(detailDto.getCreateUserCode())) {
				// 创建人编码不能为空
				throw new TruckDepartPlanException("创建人编码不能为空", "");
			}
			// 创建人姓名
			if (StringUtils.isBlank(detailDto.getCreateUserName())) {
				// 创建人姓名不能为空
				throw new TruckDepartPlanException("创建人姓名不能为空", "");
			}
			// 创建人所属部门
			if (StringUtils.isBlank(detailDto.getCreateOrgCode())) {
				// 创建人所属部门不能为空
				throw new TruckDepartPlanException("创建人所属部门不能为空", "");
			}
			// 导入标记
			if (StringUtils.isBlank(detailDto.getInitFlag())) {
				throw new TruckDepartPlanException("导入标记不能为空", "");
			}
			// 是否已发车
			if (StringUtils.isBlank(detailDto.getHasLeft())) {
				// 是否已发车不能为空
				throw new TruckDepartPlanException("是否已发车不能为空", "");
			}
			// 计划状态
			if (StringUtils.isBlank(detailDto.getPlanStatus())) {
				// 计划状态不能为空
				throw new TruckDepartPlanException("计划状态不能为空", "");
			}
		}
		// 线路名称
		if (StringUtils.isBlank(detailDto.getLineName())) {
			// 线路名称不能为空
			throw new TruckDepartPlanException("线路名称不能为空", "");
		}
	}

	/**
	 * 
	 * 构造发车计划修改日志，并返回
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-3 下午5:22:03
	 * 
	 */
	private void makePlanUpdateLog(TruckDepartPlanDetailDto detailDto, String operationType, CurrentInfo user) {
		// 日志对象
		TruckDepartPlanUpdateEntity detailLog = null;
		// 不空
		if (detailDto != null) {
			// 如果是更新操作
			if (TruckDepartPlanConstants.OPERATION_TYPE_UPDATE.equals(operationType)) {
				// 首先根据ID查询发车明细
				TruckDepartPlanDetailDto oldDetail = truckDepartPlanDetailDao.queryTruckDepartPlanDetailById(detailDto);
				// 月台号设置
				if (oldDetail != null) {
					// 原始对象
					oldDetail.setPlatformNo(detailDto.getOrigPlatformNo());
				}
				// 补充现有发车计划中外键对应的值
				fetchValueByKey(detailDto);
				// 补充原有发车计划中外键对应的值
				fetchValueByKey(oldDetail);
				// 比对修改记录
				detailLog = truckDepartPlanUpdateService.compareDepartPlanDetail(oldDetail, detailDto, user);
			}
			// 新增操作,原始比对计划明细对象为空
			else {
				// 补充现有发车计划中外键对应的值
				fetchValueByKey(detailDto);
				// 比对修改记录
				detailLog = truckDepartPlanUpdateService.compareDepartPlanDetail(null, detailDto, user);
			}

			// 如果修改内容不为空,则保存修改比对记录作为日志
			if (detailLog != null) {
				// 操作用户信息
				logUserInfo(detailLog, user);
				// 日志类型-自动记录变动
				detailLog.setLogType(TruckDepartPlanConstants.LOG_TYPE_AUTO);
				// ID
				detailLog.setId(UUIDUtils.getUUID());
				// 发车计划明细ID
				detailLog.setTruckDepartPlanDetailId(detailDto.getId());
				// 新增操作日志
				truckDepartPlanUpdateService.addTruckDepartPlanUpdate(detailLog);
			}
		}
	}

	/**
	 * 
	 * 补充原有发车计划的月台号信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-26 上午8:28:28
	 * 
	 */
	private void addPlatformNoForOldetail(TruckDepartPlanDetailDto oldDetail) {
		// 判空
		if (oldDetail != null && StringUtils.isNotBlank(oldDetail.getPlatformDistributeId())) {
			// 查询月台使用信息
			LOGGER.info("查询月台使用信息:" + oldDetail.getPlatformDistributeId());
			// 查询月台信息
			PlatformDistributeEntity platform = platformDispatchService.queryPlatformDistribute(oldDetail
					.getPlatformDistributeId());
			// 月台虚拟编码不为空
			if (platform != null && StringUtils.isNotBlank(platform.getPlatformNo())) {
				// 查询月台虚拟编码
				LOGGER.info("查询月台虚拟编码:" + platform.getPlatformNo());
				// 月台虚拟编码
				oldDetail.setOrigPlatformNo(platform.getPlatformNo());
			} else {
				// 查询月台号为空
				LOGGER.info("查询月台号为空");
			}
		}

	}

	/**
	 * 
	 * 补充外键对应的值，主要针对发车明细中的引用其他基础表的编码和值得转换
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-24 下午6:45:36
	 * 
	 */
	private void fetchValueByKey(TruckDepartPlanDetailDto detailDto) {
		// 判空
		if (detailDto != null) {
			// 出发部门
			if (StringUtils.isNotBlank(detailDto.getOrigOrgCode())) {
				// 出发部门
				detailDto.setOrigOrgName(queryDepartment(detailDto.getOrigOrgCode()));
			}
			// 到达部门
			if (StringUtils.isNotBlank(detailDto.getDestOrgCode())) {
				// 到达部门
				detailDto.setDestOrgName(queryDepartment(detailDto.getDestOrgCode()));
			}
			// 月台号
			if (StringUtils.isNotBlank(detailDto.getPlatformNo())) {
				// 月台号
				detailDto.setPlatformNoCode(queryPlatformNo(detailDto.getPlatformNo()));
			}
			// 车型
			if (StringUtils.isNotBlank(detailDto.getTruckModel())) {
				// 车型
				detailDto.setTruckModelValue(queryTruckModel(detailDto.getTruckModel()));
			}
			// 车队
			if (StringUtils.isNotBlank(detailDto.getLongCarGroup())) {
				// 车队
				detailDto.setCarGroupName(this.queryCarGroupByCode(detailDto.getLongCarGroup()));
			}
		}
	}

	/**
	 * 
	 * 通过车型编号查询车型
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-24 下午7:09:49
	 * 
	 */
	private String queryTruckModel(String truckModel) {
		// 综合查询接口,查询车型
		LOGGER.info("综合查询接口,查询车型:" + truckModel);
		// 查询车型
		VehicleTypeEntity vehicleType = leasedVehicleTypeService.queryLeasedVehicleTypeByVehicleLengthCode(truckModel);
		// 不为空则返回，月台号码
		if (vehicleType != null) {
			// 车型编码查询车型
			LOGGER.info("车型编码查询车型:" + vehicleType.getVehicleLengthName());
			// 返回
			return vehicleType.getVehicleLengthName();
		} else {
			// 车型编码查询车型为空
			LOGGER.info("车型编码查询车型为空");
			// 返回
			return StringUtils.EMPTY;
		}
	}

	/**
	 * 
	 * 根据月台虚拟编码查询月台号
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-24 下午6:54:28
	 * 
	 */
	private String queryPlatformNo(String platformNo) {
		// 月台虚拟编码查询月台号
		LOGGER.info("月台虚拟编码查询月台号:" + platformNo);
		// 查询月台号信息
		PlatformEntity platfDto = platformService.queryPlatformByVirtualCode(platformNo);
		// 不为空则返回，月台号码
		if (platfDto != null) {
			// 日志
			LOGGER.info(platfDto.getPlatformCode());
			// 月台号码
			return platfDto.getPlatformCode();
		} else {
			// 月台号码为空
			LOGGER.info("月台号码为空");
			// 空
			return StringUtils.EMPTY;
		}

	}

	/**
	 * 
	 * 新增保存用户备注日志
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-24 下午6:26:28
	 * 
	 */
	private void makeUserRemarkLog(TruckDepartPlanDetailDto detailDto, CurrentInfo user) {
		// 用户备注日志
		TruckDepartPlanUpdateEntity userRemark = new TruckDepartPlanUpdateEntity();
		// 如果变更日志不为空，则拷变更日志信息
		// 设置用户信息
		logUserInfo(userRemark, user);
		// 用户备注
		userRemark.setLogType(TruckDepartPlanConstants.LOG_TYPE_REMARK);
		// 用户备注
		userRemark.setModifyContent(detailDto.getRemark());
		// ID
		userRemark.setId(UUIDUtils.getUUID());
		// 发车计划明细ID
		userRemark.setTruckDepartPlanDetailId(detailDto.getId());
		// 保存
		truckDepartPlanUpdateService.addTruckDepartPlanUpdate(userRemark);

	}

	/**
	 * 
	 * 日志操作用户信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-24 上午10:32:01
	 * 
	 */
	private void logUserInfo(TruckDepartPlanUpdateEntity detailLog, CurrentInfo user) {
		// 判空
		if (detailLog != null) {
			// 创建信息
			detailLog.setCreateTime(Calendar.getInstance().getTime());
			if (user != null) {
				// 创建人编码
				detailLog.setCreateUserCode(user.getEmpCode());
				// 创建人姓名
				detailLog.setCreateUserName(user.getEmpName());
				// 创建人部门
				detailLog.setCreateOrgCode(user.getCurrentDeptCode());
			}
		}
	}

	/**
	 * 
	 * 新增或更新月台信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-28 下午12:51:30
	 * 
	 */
	private PlatformDistributeEntity saveOrUpdatePlatform(TruckDepartPlanDetailDto detailDto, CurrentInfo user)
			throws TruckDepartPlanException {
		// 月台
		PlatformDistributeEntity platformntity = null;
		// 首先判断是否存在月台ID
		if (StringUtils.isNotBlank(detailDto.getPlatformDistributeId())) {
			// 更新月台信息
			platformntity = new PlatformDistributeEntity();
			// 修改人
			if (user != null) {
				// 修改人
				platformntity.setModifyUser(user.getEmpCode());
			}
			// 修改时间
			platformntity.setModifyDate(Calendar.getInstance().getTime());
			// 月台ID
			platformntity.setId(detailDto.getPlatformDistributeId());
			// 拷贝月台信息
			copyPlatform(detailDto, platformntity);
			// 如果月台号不为空,补充原有发车计划的月台号信息
			addPlatformNoForOldetail(detailDto);
			// 执行更新
			platformDispatchService.updatePlatformDistribute(platformntity);
		} else {
			// 不空
			if (StringUtils.isNotBlank(detailDto.getPlatformNo())) {
				// 新增月台信息
				platformntity = new PlatformDistributeEntity();
				// 不空
				if (user != null) {// 创建人
					platformntity.setCreateUser(user.getEmpCode());
				}
				// 创建时间
				platformntity.setCreateDate(Calendar.getInstance().getTime());
				// 月台ID
				platformntity.setId(UUIDUtils.getUUID());
				// 拷贝月台信息
				copyPlatform(detailDto, platformntity);
				// 执行新增
				platformDispatchService.addPlatformDistribute(platformntity);
			}
		}
		return platformntity;
	}

	/**
	 * 
	 * 拷贝月台信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-28 下午12:56:41
	 * 
	 */
	private void copyPlatform(TruckDepartPlanDetailDto detailDto, PlatformDistributeEntity platformntity) {
		// 不空
		if (platformntity != null) {
			// 外场信息
			if (StringUtils.isNotBlank(detailDto.getOrigOrgCode())) {
				// 外场编号
				platformntity.setTransferCenterNo(detailDto.getOrigOrgCode());
				// 查询外场信息,出发部门
				LOGGER.info("查询外场信息,出发部门:" + detailDto.getOrigOrgCode());
				// 查询部门
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(detailDto.getOrigOrgCode());
				// 外场名称
				if (org != null) {
					// 外场名称
					platformntity.setTransferCenterName(org.getName());
				} else {
					// 外场信息为空
					LOGGER.error("外场信息为空");
				}
			}
			// 月台号
			platformntity.setPlatformNo(detailDto.getPlatformNo());
			// 使用启始时间
			if (detailDto.getPlatformTimeStart() != null) {
				// 使用启始时间
				platformntity.setUseStartTime(detailDto.getPlatformTimeStart());
			}
			// 使用结束时间
			if (detailDto.getPlatformTimeEnd() != null) {
				// 使用结束时间
				platformntity.setUseEndTime(detailDto.getPlatformTimeEnd());
			}
			// 车辆类型
			platformntity.setVehicleModel(detailDto.getTruckModel());
			// 车牌号
			platformntity.setVehicleNo(detailDto.getVehicleNo());
			// 状态
			platformntity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_AVAILABLE);
			// 分配类型
			platformntity.setType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_PLAN);
			// 来源
			platformntity.setScheduleSource(PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_STARTSCHEDULE);
		}
	}

	/**
	 * 
	 * 根据出发部门、到达部门查询线路列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-28 上午8:44:53
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryLineListByOrigDestCode(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 * 
	 */
	@Override
	public List<TruckDepartPlanDetailDto> queryLineListByOrigDestCode(TruckDepartPlanDetailDto detailDto) {
		// 明细列表
		List<TruckDepartPlanDetailDto> tempList = null;
		// 查询线路列表
		if (StringUtils.isNotBlank(detailDto.getOrigOrgCode()) && StringUtils.isNotBlank(detailDto.getDestOrgCode())) {
			// 日志
			LOGGER.info("查询线路列表,出发部门:" + detailDto.getOrigOrgCode() + ",到达部门:" + detailDto.getDestOrgCode());
			// 查询发车时效
			List<DepartureStandardDto> departureList = lineService.queryDepartureStandardListBySourceTarget(detailDto
					.getOrigOrgCode().trim(), detailDto.getDestOrgCode().trim());
			
			// 不为空，循环封装线路列表
			if (CollectionUtils.isNotEmpty(departureList)) {
				// 不为空，循环封装线路列表
				tempList = new ArrayList<TruckDepartPlanDetailDto>();
				// 用于去除重复的线路
				Map<String, String> tempMap = new HashMap<String, String>();
				// 线路信息
				TruckDepartPlanDetailDto dto = null;
				// 循环
				for (DepartureStandardDto lineDto : departureList) {
					// 判断是否已存在,不存在，则加入
					if (tempMap.get(lineDto.getLineVirtualCode()) == null) {
						// 明细
						dto = new TruckDepartPlanDetailDto();
						// 线路名称
						dto.setLineName(lineDto.getLineName());
						// 线路虚拟编码
						dto.setLineNo(lineDto.getLineVirtualCode());
						//线路类型-零担线路
						dto.setLineNoExpress(FossConstants.NO);
						// 判断是否已存在,不存在，则加入
						tempList.add(dto);
						// 判断是否已存在,不存在，则加入
						tempMap.put(lineDto.getLineVirtualCode(), lineDto.getLineName());
					}
				}
			}else{
				//增加ECS屏蔽开关
				 if (configurationParamsService.queryTfrSwitch4Ecs()) {
					 //通过调用ECS接口快递线路
					 Log.error("调用的是ECS接口获取快递线路 ： getExpressLine");
					 departureList = getExpressLine(detailDto
								.getOrigOrgCode().trim(), detailDto.getDestOrgCode().trim());
				 } else {
					 //业务老逻辑的快递线路获取方式
					 Log.error("调用FOSS方法获取快递线路 ： expresslineService");
					 departureList = expresslineService.queryDepartureStandardListBySourceTarget(detailDto
								.getOrigOrgCode().trim(), detailDto.getDestOrgCode().trim());
				 }
				// 不为空，循环封装线路列表
					if (CollectionUtils.isNotEmpty(departureList)) {
						// 不为空，循环封装线路列表
						tempList = new ArrayList<TruckDepartPlanDetailDto>();
						// 用于去除重复的线路
						Map<String, String> tempMap = new HashMap<String, String>();
						// 线路信息
						TruckDepartPlanDetailDto dto = null;
						// 循环
						for (DepartureStandardDto lineDto : departureList) {
							// 判断是否已存在,不存在，则加入
							if (tempMap.get(lineDto.getLineVirtualCode()) == null) {
								// 明细
								dto = new TruckDepartPlanDetailDto();
								// 线路名称
								dto.setLineName(lineDto.getLineName());
								// 线路虚拟编码
								dto.setLineNo(lineDto.getLineVirtualCode());
								//快递线路
								dto.setLineNoExpress(FossConstants.YES);
								// 判断是否已存在,不存在，则加入
								tempList.add(dto);
								// 判断是否已存在,不存在，则加入
								tempMap.put(lineDto.getLineVirtualCode(), lineDto.getLineName());
							}
						}
					}
			}
		}
		return tempList;
	}

	/**
	 * 查询公司司机信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @return
	 * 
	 * @date 2012-11-28 下午8:39:22
	 * 
	 */
	private DriverAssociationDto queryOwnDriverInfo(String driverCode) {
		// 日志
		LOGGER.info("查询公司司机信息:" + driverCode);
		// 日志
		DriverAssociationDto driver = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(driverCode);
		// 不空
		if (driver == null) {
			// 日志
			LOGGER.error("司机未查询到:" + driverCode);
			// 司机未查询到
			throw new TruckDepartPlanException(driverCode + "司机未查询到", "");
		}
		// 返回
		return driver;
	}

	/**
	 * 
	 * 通过车牌号查询车辆信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-29 下午6:42:48
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryVehicleByVechileNo(java.lang.String)
	 * 
	 */
	@Override
	public PlanVehicleDto queryVehicleByVechileNo(TruckDepartPlanDetailDto detailDto) {
		// 车辆信息
		PlanVehicleDto vehicleDto = null;
		// 车辆信息不为空
		if (detailDto != null) {
			// 车牌查询车辆信息
			VehicleAssociationDto vehicle = null;
			// 查询车辆信息
			if (StringUtils.isNotBlank(detailDto.getVehicleNo())) {
				// 日志
				LOGGER.info("查询车辆信息:" + detailDto.getVehicleNo());
				// 查询车辆
				vehicle = vehicleService.queryVehicleAssociationDtoByVehicleNo(detailDto.getVehicleNo());
			}
			// 按照车牌计算
			if (vehicle != null) {
				// 车型
				String vehicleLengthCode = vehicle.getVehicleLengthCode();
				// 车辆对象
				vehicleDto = new PlanVehicleDto();
				// 车辆类型
				vehicleDto.setVehicleType(vehicle.getVehicleType());
				// 判断归属类型
				// 公司车
				if (ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY.equals(vehicle.getVehicleOwnershipType())
						&& TruckDepartPlanConstants.TRUCK_TYPE_MYSELF_OWN.equals(detailDto.getTruckType())) {
					// 计划发车时间不为空
					// 计算公司车月台使用时间
					calcInnerCarPlatformTime(detailDto, vehicle, vehicleDto, vehicleLengthCode);

				} else // 外请
				if (ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED.equals(vehicle.getVehicleOwnershipType())
						&& TruckDepartPlanConstants.TRUCK_TYPE_OUTER.equals(detailDto.getTruckType())) {
					// 计算外请车月台使用时间
					calcOuterCarPlatformTime(detailDto, vehicle, vehicleDto, vehicleLengthCode);
				} else {
					// 不是正确的车辆
					throw new TruckDepartPlanException(detailDto.getVehicleNo() + "未查询到正确车辆信息", "");
				}
				// 装车结束时间
				if (detailDto.getPlanDepartTime() != null) {
					// 装车结束时间
					vehicleDto.setPlatformTimeEnd(detailDto.getPlanDepartTime());
				}
				// 车牌号码
				vehicleDto.setVehicleNo(vehicle.getVehicleNo());
				// 车辆净空
				vehicleDto.setTruckVolume(vehicle.getVehicleSelfVolume());
				// 额定载重
				vehicleDto.setMaxLoadWeight(vehicle.getVehicleDeadLoad());
				// 实际最大载重 默认=额定载重
				vehicleDto.setActualMaxLoadWeight(vehicle.getVehicleDeadLoad());
				// 计算实际最大载重,默认为载重,如果存在货柜号，则另行计算
				// 如果货柜号不为空，则以货柜号带出车型；其他则取车牌的车型
				if (StringUtils.isNotBlank(detailDto.getContainerNo())) {
					// 通过货柜号计算重量和体积
					calcWeightVolByContainer(detailDto, vehicle, vehicleDto);
				} else {
					// 车型编码对应值
					vehicleDto.setTruckModelValue(vehicle.getVehicleLengthName());
					// 车型CODE
					vehicleDto.setTruckModel(vehicle.getVehicleLengthCode());
				}
				// 预计体积 默认= 车辆净空
				vehicleDto.setPlanLoadVolume(vehicleDto.getTruckVolume());
				// 预计载重 默认=实际最大载重
				vehicleDto.setPlanLoadWeight(vehicleDto.getActualMaxLoadWeight());
			}// 按照车型计算发车时效
			else {
				// 车型不为空
				if (StringUtils.isNotBlank(detailDto.getTruckModel())) {
					// 车型
					String vehicleLengthCode = detailDto.getTruckModel();
					// 车辆信息
					vehicleDto = new PlanVehicleDto();
					// 判断归属类型
					// 公司车
					if (TruckDepartPlanConstants.TRUCK_TYPE_MYSELF_OWN.equals(detailDto.getTruckType())) {
						// 计划发车时间不为空
						// 计算公司车月台使用时间
						calcInnerCarPlatformTime(detailDto, null, vehicleDto, vehicleLengthCode);
					} else // 外请
					if (TruckDepartPlanConstants.TRUCK_TYPE_OUTER.equals(detailDto.getTruckType())) {
						// 计算外请车月台使用时间
						calcOuterCarPlatformTime(detailDto, null, vehicleDto, vehicleLengthCode);
					} else {
						// 不是正确的车辆
						throw new TruckDepartPlanException(detailDto.getVehicleNo() + "未查询到正确车型时效信息", "");
					}
					// 装车结束时间
					if (detailDto.getPlanDepartTime() != null) {
						// 装车结束时间
						vehicleDto.setPlatformTimeEnd(detailDto.getPlanDepartTime());
					}
				}
			}
			// 如果货柜号不为空，则以货柜号带出车型；其他则取车牌的车型
			if (StringUtils.isNotBlank(detailDto.getContainerNo())) {
				if (vehicleDto == null) {
					// 车辆信息
					vehicleDto = new PlanVehicleDto();
				}
				// 通过货柜号查询车型
				VehicleTypeEntity tempVechileType = queryVechileTypeByContainer(detailDto);
				// 结果不为空
				if (tempVechileType != null) {
					// 车型编码对应值
					vehicleDto.setTruckModelValue(tempVechileType.getVehicleLengthName());
					// 车型CODE
					vehicleDto.setTruckModel(tempVechileType.getVehicleLengthCode());
				} else {
					// 日志
					LOGGER.info("通过货柜号查询车型,未查询到车型信息:" + detailDto.getContainerNo());
				}
			}
		}
		// 返回
		return vehicleDto;
	}

	/**
	 * 
	 * 通过货柜号计算重量和体积
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-21 上午10:32:15
	 * 
	 */
	private void calcWeightVolByContainer(TruckDepartPlanDetailDto detailDto, VehicleAssociationDto vehicle,
			PlanVehicleDto vehicleDto) {
		// 货柜号查询条件
		OwnTruckEntity ownTruck = new OwnTruckEntity();
		// 货柜号
		ownTruck.setContainerCode(detailDto.getContainerNo());
		// 查询货柜信息
		LOGGER.info("查询货柜信息:" + detailDto.getContainerNo());
		// 货柜
		OwnTruckEntity containerVehicle = ownVehicleService.queryOwnVehicleBySelective(ownTruck, null);
		// 通过货柜号和车牌计算
		if (containerVehicle != null) {
			// 货柜净空
			vehicleDto.setTruckVolume(containerVehicle.getSelfVolume());
			// 单双桥
			String bridge = vehicle.getBridge();
			// 根据车辆类型判断 单双桥,车牌号为车头,如果是双桥
			if (DictionaryValueConstants.VEHICLE_BRIGE_TYPE_TWINBRIDGE.equals(bridge)) {
				// 双桥默认最大载重=33
				vehicleDto.setMaxLoadWeight(TruckDepartPlanConstants.BRIDGE_DOUBLE_DEFAULT_MAX_WEIGHT);
				// 双桥实际最大载重计算因子
				BigDecimal actualMaxLoadWeight = TruckDepartPlanConstants.BRIDGE_DOUBLE_ACT_MAX_WEIGHT;
				// 车头重量
				BigDecimal vehicleHeader = vehicle.getSelfWeight();
				// 双桥实际最大载重=55-车头自重-货柜自重
				if (vehicleHeader != null && containerVehicle.getSelfWeight() != null) {
					// 自重
					actualMaxLoadWeight = actualMaxLoadWeight.subtract(vehicleHeader).subtract(
							containerVehicle.getSelfWeight());
				}
				// 实际最大载重
				vehicleDto.setActualMaxLoadWeight(actualMaxLoadWeight);
				// 判空
			} else if (DictionaryValueConstants.VEHICLE_BRIGE_TYPE_SINGLE.equals(bridge)) {
				// 双桥默认最大载重=30
				vehicleDto.setMaxLoadWeight(TruckDepartPlanConstants.BRIDGE_SINGLE_DEFAULT_MAX_WEIGHT);
				// 实际最大载重
				BigDecimal actualMaxLoadWeight = TruckDepartPlanConstants.BRIDGE_SINGLE_ACT_DEFAULT_MAX_WEIGHT;
				// 车头重量
				BigDecimal vehicleHeader = vehicle.getSelfWeight();
				// 双桥实际最大载重=55-车头自重-货柜自重
				if (vehicleHeader != null && containerVehicle.getSelfWeight() != null) {
					// 实际最大载重
					actualMaxLoadWeight = actualMaxLoadWeight.subtract(vehicleHeader).subtract(
							containerVehicle.getSelfWeight());
				}
				// 实际最大载重
				vehicleDto.setActualMaxLoadWeight(actualMaxLoadWeight);
			}
		}
	}

	/**
	 * 
	 * 货柜号查询车辆车型信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2013-1-17 上午9:48:16
	 * 
	 */
	private VehicleTypeEntity queryVechileTypeByContainer(TruckDepartPlanDetailDto detailDto) {
		// 判空
		if (detailDto != null && StringUtils.isNotBlank(detailDto.getContainerNo())) {
			// 货柜号查询条件
			OwnTruckEntity ownTruck = new OwnTruckEntity();
			// 货柜号
			ownTruck.setContainerCode(detailDto.getContainerNo());
			// 查询货柜信息
			LOGGER.info("查询货柜信息:" + detailDto.getContainerNo());
			// 货柜信息
			OwnTruckEntity containerVehicle = ownVehicleService.queryOwnVehicleBySelective(ownTruck, null);
			// 不为空
			if (containerVehicle != null) {
				// 车型
				VehicleTypeEntity vehicleTypeParams = new VehicleTypeEntity();
				// 可用
				vehicleTypeParams.setActive(FossConstants.ACTIVE);
				// 车型
				vehicleTypeParams.setVehicleLengthCode(containerVehicle.getVehcleLengthCode());
				// 公司车
				vehicleTypeParams.setVehicleSort(DictionaryValueConstants.DATA_OWNERSHIP_TYPE_COMPANY);
				// 执行查询车型
				// 返回
				return leasedVehicleTypeService.queryLeasedVehicleType(vehicleTypeParams);
			} else {
				// 返回
				return null;
			}
		} else {
			// 返回
			return null;
		}
	}

	/**
	 * 计算外请车月台使用时间
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-21 上午10:25:27
	 * 
	 */
	private void calcOuterCarPlatformTime(TruckDepartPlanDetailDto detailDto, VehicleAssociationDto vehicle,
			PlanVehicleDto vehicleDto, String vehicleLengthCode) {
		// 计划发车时间
		Date planDepartTime = detailDto.getPlanDepartTime();
		// 计划发车时间不为空
		if (planDepartTime != null) {
			// 根据外场编号、车型查询装车时效
			LOGGER.info("根据外场编号、车型查询装车时效,车型:" + vehicleLengthCode + ",外场:" + detailDto.getOrigOrgCode());
			// 卸车时效
			LoadAndUnloadEfficiencyVehicleEntity loadEfficiency = queryVehicleTime(vehicleLengthCode,
					detailDto.getOrigOrgCode());
			// 发车时效不为空
			if (loadEfficiency != null) {
				// 外请车车有高栏属性
				// 高栏
				if (vehicle != null && TruckDepartPlanConstants.RAIL_VEHICLE_Y.equals(vehicle.getRailVehicle())) {
					// 高栏开蓬
					if (vehicle.isOpenVehicle()) {
						// 带高栏敞篷车装车标准用时
						String glCpLoadHours = loadEfficiency.getGlCpLoadHours();
						// 带高栏敞篷车装车标准用分
						String glCpLoadMins = loadEfficiency.getGlCpLoadMins();
						// 计算开始结束时间
						calcStartAndEndTime(planDepartTime, glCpLoadHours, glCpLoadMins, vehicleDto);
					}
					// 高栏非开蓬
					else {
						// 非敞篷车装车标准用时
						String ncpLoadHours = loadEfficiency.getNcpLoadHours();
						// 非敞篷车装车标准用分
						String ncpLoadMins = loadEfficiency.getNcpLoadMins();
						// 计算开始结束时间
						calcStartAndEndTime(planDepartTime, ncpLoadHours, ncpLoadMins, vehicleDto);
					}
				} else {
					// 非高栏开蓬
					if (vehicle != null && vehicle.isOpenVehicle()) {
						// 不带高栏敞篷车装车标准用时
						String nglCpLoadHours = loadEfficiency.getNglCpLoadHours();
						// 不带高栏敞篷车装车标准用分
						String nglCpLoadMins = loadEfficiency.getNglCpLoadMins();
						// 计算开始结束时间
						calcStartAndEndTime(planDepartTime, nglCpLoadHours, nglCpLoadMins, vehicleDto);
					}
					// 非高栏非开蓬
					else {
						// 非敞篷车装车标准用时
						String ncpLoadHours = loadEfficiency.getNcpLoadHours();
						// 非敞篷车装车标准用分
						String ncpLoadMins = loadEfficiency.getNcpLoadMins();
						// 计算开始结束时间
						calcStartAndEndTime(planDepartTime, ncpLoadHours, ncpLoadMins, vehicleDto);
					}
				}
			} else {
				// 日志
				LOGGER.info("未查询到外场编号、车型查询装车时效,车型:" + vehicleLengthCode + ",外场:" + detailDto.getOrigOrgCode());
			}
		}
	}

	/**
	 * 
	 * 计算公司车月台使用时间
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-21 上午10:19:45
	 * 
	 */
	private void calcInnerCarPlatformTime(TruckDepartPlanDetailDto detailDto, VehicleAssociationDto vehicle,
			PlanVehicleDto vehicleDto, String vehicleLengthCode) {
		// 判空
		if (detailDto != null) {
			// 计划发车时间
			Date planDepartTime = detailDto.getPlanDepartTime();
			// 计划发车时间不为空，则计算使用时间
			if (planDepartTime != null) {
				// 根据外场编号、车型查询装车时效
				LoadAndUnloadEfficiencyVehicleEntity loadEfficiency = queryVehicleTime(vehicleLengthCode,
						detailDto.getOrigOrgCode());
				if (loadEfficiency != null) {
					// 公司车无高栏属性
					// 开蓬
					if (vehicle != null && vehicle.isOpenVehicle()) {
						// 不带高栏敞篷车装车标准用时
						String nglCpLoadHours = loadEfficiency.getNglCpLoadHours();
						// 不带高栏敞篷车装车标准用分
						String nglCpLoadMins = loadEfficiency.getNglCpLoadMins();
						// 计算开始结束时间
						calcStartAndEndTime(planDepartTime, nglCpLoadHours, nglCpLoadMins, vehicleDto);
					}
					// 非开蓬
					else {
						// 非敞篷车装车标准用时
						String ncpLoadHours = loadEfficiency.getNcpLoadHours();
						// 非敞篷车装车标准用分
						String ncpLoadMins = loadEfficiency.getNcpLoadMins();
						// 计算开始结束时间
						calcStartAndEndTime(planDepartTime, ncpLoadHours, ncpLoadMins, vehicleDto);
					}
				}
			}
		}
	}

	/**
	 * 计算开始结束时间
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-21 上午10:22:15
	 */
	private void calcStartAndEndTime(Date planDepartTime, String hours, String mins, PlanVehicleDto vehicleDto) {
		// 不空且为数字
		if (StringUtils.isNotBlank(hours) && StringUtils.isNotBlank(mins) && StringUtils.isNumeric(hours)
				&& StringUtils.isNumeric(mins)) {
			// 发车时间计算发车月台使用时间
			Calendar c = Calendar.getInstance();
			// 预计发车时间
			c.setTime(planDepartTime);
			// 减去小时
			c.add(Calendar.HOUR_OF_DAY, TruckDepartPlanConstants.TIME_ZERO - Integer.valueOf(hours));
			// 减去分钟
			c.add(Calendar.MINUTE, TruckDepartPlanConstants.TIME_ZERO - Integer.valueOf(mins));
			// 判空
			if (vehicleDto != null) {
				// 日志
				LOGGER.info("装车开始时间" + DateUtils.convert(c.getTime(), DateUtils.TIME_FORMAT));
				// 装车开始时间
				vehicleDto.setPlatformTimeStart(c.getTime());
			} else {
				// 日志
				LOGGER.error("车辆信息为空,无法计算月台开始时间");
				// 车辆信息为空
				throw new TruckDepartPlanException("车辆信息为空", "");
			}
		}
	}

	/**
	 * 
	 * 查询发车计划明细总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-3 下午2:18:54
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryTruckDepartPlanDetailTotal(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@Override
	public Long queryTruckDepartPlanDetailTotal(TruckDepartPlanDto planDto) {
		// 转换为计划明细作为查询条件
		TruckDepartPlanDetailDto truckDepartPlanDetailDto = new TruckDepartPlanDetailDto();
		// 发车计划明细ID
		truckDepartPlanDetailDto.setTruckDepartPlanId(planDto.getId());
		// 查询条数
		return truckDepartPlanDetailDao.queryTotalCount(truckDepartPlanDetailDto);
	}

	/**
	 * 
	 * 根据 车牌号、出发部门、到达部门查询最近未出发的发车计划
	 * 
	 * @param vehicleNo
	 * 
	 *            车牌号
	 * 
	 * @param origOrgCode
	 *            出发部门
	 * 
	 * 
	 * @param destOrgCode
	 *            到达部门
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-4 下午1:47:03
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryLatestTruckDepartPlanDetail(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 * 
	 */
	@Override
	public TruckDepartPlanDetailDto queryLatestTruckDepartPlanDetail(String origOrgCode, String destOrgCode,
			String vehicleNo, Date departDate) throws TruckDepartPlanException {
		// 出发部门不能为空
		if (StringUtils.isBlank(origOrgCode)) {
			throw new TruckDepartPlanException("出发部门不能为空", "");
		}
		// 到达部门不能为空
		if (StringUtils.isBlank(destOrgCode)) {
			throw new TruckDepartPlanException("到达部门不能为空", "");
		}
		// 车牌号不能为空
		if (StringUtils.isBlank(vehicleNo)) {
			throw new TruckDepartPlanException("车牌号不能为空", "");
		}
		// 创建查询条件
		TruckDepartPlanDetailDto detailDto = new TruckDepartPlanDetailDto();
		// 发车日期
		if (departDate != null) {
			// 发车日期
			detailDto.setDepartDate(departDate);
		}
		// 车牌
		detailDto.setVehicleNo(vehicleNo);
		// 出发部门
		detailDto.setOrigOrgCode(origOrgCode);
		// 到达部门
		detailDto.setDestOrgCode(destOrgCode);
		// 可用
		detailDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		// 下发
		// 短途新建
		detailDto.setPlanStatusNew(TruckDepartPlanConstants.PLAN_STATUS_NEW);
		// 短途新建
		detailDto.setPlanTypeShort(TruckDepartPlanConstants.PLAN_TYPE_SHORT);
		// 长途下发
		detailDto.setPlanStatusRelease(TruckDepartPlanConstants.PLAN_STATUS_RELEASE);
		// 长途下发
		detailDto.setPlanTypeLong(TruckDepartPlanConstants.PLAN_TYPE_LONG);
		// 未出发
		detailDto.setHasLeft(TruckDepartPlanConstants.LEFT_FLAG_N);
		// 加发和正常的
		List<String> list = new ArrayList<String>();
		// 加发
		list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_ADD);
		// 正常
		list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
		// 列表
		detailDto.setList(list);
		// 执行查询
		return truckDepartPlanDetailDao.queryLatestTruckDepartPlanDetail(detailDto);
	}

	/**
	 * 
	 * 下发发车计划
	 * 
	 * 长途：
	 * 
	 * 图（1）中外场操作员选择新建状态的发车计划，
	 * 
	 * 并点击下发按钮 长途发车计划列表 将发车计划的状态改为下发状态，
	 * 
	 * 下发时，修改/录入表单中所有的带*号的字段必须全部已经填写完。如果
	 * 
	 * 存在没有填写的值，不可以下发。
	 * 
	 * 如果选择的存在非新建状态的发车计划，则提示用户进行确认。
	 * 
	 * 长途发车计划列表 弹出提示框，并提示相应的原因，
	 * 
	 * 让用户再确认下发的列表。
	 * 
	 * 只有做下发后操作后，长途车队调度、班车调度可以看见相应的发车计划，
	 * 
	 * 外场操作员可看见相应的所有状态的发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-15 下午5:00:43
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#releaseTruckDepartPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	@Transactional
	public void releaseTruckDepartPlanDetail(TruckDepartPlanDetailDto planDetailDto, CurrentInfo user) {
		// 判空
		if (planDetailDto != null && CollectionUtils.isNotEmpty(planDetailDto.getIds())) {
			// 验证下发列表
			validateReleaseTruckDepartPlanDetail(planDetailDto);
			// 下发日志
			TruckDepartPlanUpdateEntity releaseLog = null;
			// 下发明细列表不为空
			for (String detailId : planDetailDto.getIds()) {
				// 新建一个下发日志
				releaseLog = new TruckDepartPlanUpdateEntity();
				// 用户备注
				releaseLog.setLogType(TruckDepartPlanConstants.LOG_TYPE_AUTO);
				// 修改内容
				releaseLog.setModifyContent("下发发车计划");
				// UUID
				releaseLog.setId(UUIDUtils.getUUID());
				// 明细ID
				releaseLog.setTruckDepartPlanDetailId(detailId);
				// 用户信息
				logUserInfo(releaseLog, user);
				// 计划明细操作用户信息
				// 更新用户信息
				planDetailDto.setUpdateTime(Calendar.getInstance().getTime());
				if (user != null) {
					// 用户编码
					planDetailDto.setUpdateUserCode(user.getEmpCode());
					// 用户部门编码
					planDetailDto.setUpdateOrgCode(user.getCurrentDeptCode());
					// 用户姓名
					planDetailDto.setUpdateUserName(user.getEmpName());
				}
				// 保存
				truckDepartPlanUpdateService.addTruckDepartPlanUpdate(releaseLog);
			}
		}
		// 下发
		truckDepartPlanDetailDao.releaseTruckDepartPlanDetail(planDetailDto);
	}

	/**
	 * 
	 * 验证下发列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-26 下午5:09:57
	 * 
	 */
	private boolean validateReleaseTruckDepartPlanDetail(TruckDepartPlanDetailDto planDetailDto) {
		// 初始化
		boolean flag = true;
		// 列表不为空，进行校验
		if (planDetailDto != null && CollectionUtils.isNotEmpty(planDetailDto.getIds())) {
			// 发车计划明细
			TruckDepartPlanDetailDto detailDtoCriteria = null;
			// 循环验证
			for (String detailId : planDetailDto.getIds()) {
				// 查询条件
				detailDtoCriteria = new TruckDepartPlanDetailDto();
				// 明细ID
				detailDtoCriteria.setId(detailId);
				// 查询明细
				TruckDepartPlanDetailDto tempDetailDto = truckDepartPlanDetailDao
						.queryTruckDepartPlanDetailById(detailDtoCriteria);
				// 存在，则开始进行校验// 验证下发条件
				if (tempDetailDto != null && doValidateReleasePlanDetail(tempDetailDto)) {
					// 异常
					throw new TruckDepartPlanException("请确认您下发的发车计划，都已经完善！(司机信息，车辆信息，线路、发车时间、班次、车型、车牌等)", "");
				}
			}
		}
		// 返回
		return flag;
	}

	/**
	 * 
	 * 验证下发条件
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-26 下午5:21:47
	 * 
	 */
	private boolean doValidateReleasePlanDetail(TruckDepartPlanDetailDto tempDetailDto) {
		// 返回变量初始化为fasle
		boolean flag = false;
		// 判空
		if (tempDetailDto != null) {
			// 判空
			if ( // 出发部门
			StringUtils.isBlank(tempDetailDto.getOrigOrgCode()) || // 到达部门
					StringUtils.isBlank(tempDetailDto.getDestOrgCode()) || // 线路名称
					StringUtils.isBlank(tempDetailDto.getLineNo()) || // 车队
					StringUtils.isBlank(tempDetailDto.getLongCarGroup()) || // 是否正班车
					StringUtils.isBlank(tempDetailDto.getIsOnScheduling()) || // 班次类型
					StringUtils.isBlank(tempDetailDto.getFrequencyType()) || // 计划发车时间
					tempDetailDto.getPlanDepartTime() == null || // 车型
					StringUtils.isBlank(tempDetailDto.getTruckModel())
					// 班次
					|| StringUtils.isBlank(tempDetailDto.getFrequencyNo())
					// 车牌
					|| StringUtils.isBlank(tempDetailDto.getVehicleNo())) {
				// 真
				flag = true;
			}
			// 长途还需要
			if (TruckDepartPlanConstants.PLAN_TYPE_LONG.equals(tempDetailDto.getPlanType())) {
				// 司机姓名1 ，// 无需校验 司机姓名2
				if (StringUtils.isBlank(tempDetailDto.getDriverCode1())) {
					// 真
					flag = true;
				}
			}
		}
		// 返回
		return flag;

	}

	/**
	 * 
	 * 根据外场编码 、车型查询装卸时效
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-20 下午8:33:35
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryVehicleTime(java.lang.String,
	 *      java.lang.String)
	 * 
	 */
	@Override
	public LoadAndUnloadEfficiencyVehicleEntity queryVehicleTime(String truckModel, String orgCode)
			throws TruckDepartPlanException {
		// 查询条件
		LoadAndUnloadEfficiencyVehicleEntity entity = new LoadAndUnloadEfficiencyVehicleEntity();
		// 车型编码
		entity.setVehicleTypeLength(truckModel);
		// 外场编码
		entity.setOrgCode(orgCode);
		// 查询卸车效率
		LOGGER.info("查询卸车效率,车型:" + truckModel + ",外场:" + orgCode);
		// 查询
		List<LoadAndUnloadEfficiencyVehicleEntity> tempList = loadAndUnloadEfficiencyVehicleService
				.queryLoadAndUnloadEfficiencyVehicleExactByEntity(entity, TruckDepartPlanConstants.PAGE_START,
						TruckDepartPlanConstants.PAGE_LIMIT);
		// 不空
		if (CollectionUtils.isNotEmpty(tempList)) {
			// 返回
			return tempList.get(0);
			// 其他
		} else {
			// 返回空
			return null;
		}
	}

	/**
	 * 根据 出发部门、到达部门、日期、线路、班次查询，用于排除当天线路班次唯一
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-23 下午4:29:48
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryCurrentDayLineFrequencyOnly(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public void queryCurrentDayLineFrequencyOnly(TruckDepartPlanDetailDto planDetailDto) {
		// 查询条件
		TruckDepartPlanDetailDto queryDto = new TruckDepartPlanDetailDto();
		// 复制查询条件
		try {
			// 克隆
			queryDto = (TruckDepartPlanDetailDto) planDetailDto.clone();
			// 异常
		} catch (CloneNotSupportedException e) {
			// 异常
			LOGGER.error("克隆查询条件异常", e);
		}
		// 根据 出发部门、到达部门、日期、线路、班次查询，用于排除当天线路班次唯一
		TruckDepartPlanDetailDto dto = truckDepartPlanDetailDao.queryCurrentDayLineFrequencyOnly(queryDto);
		// 如果结果大于0，则表示不唯一, 查询的发车计划明细不为空
		if (planDetailDto != null && StringUtils.isNotBlank(planDetailDto.getId()) && dto != null) {
			// 设置计划状态
			planDetailDto.setPlanStatus(dto.getPlanStatus());
			// 新增的情况
			if (StringUtils.isNotBlank(dto.getId()) && !dto.getId().equals(planDetailDto.getId())) {
				// 异常
				throw new TruckDepartPlanException(DateUtils.convert(planDetailDto.getDepartDate(),
						DateUtils.DATE_FORMAT) + "计划日期的出发部门，到达部门、线路、班次存在重复，请核实再操作", "");
			}
		}
	}
	/** 
	 * 通过发车计划明细id和状态查询发车计划
	 * 
	 * @author 105869-foss-heyongdong
	 * 
	 * @date 2013-7-26 下午1:45:51
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryTruckDepartPlanDetailById(java.lang.String, java.lang.String)
	 */
	@Override
	public TruckDepartPlanDetailDto queryTruckDepartPlanDetailById(
			String truckDepartPlanDetailId, String status) throws TruckDepartPlanException {
		// 判空
		if (StringUtils.isBlank(truckDepartPlanDetailId)) {
			// 日志
			LOGGER.error("发车明细不能为空");
			// 异常
			throw new TruckDepartPlanException("发车明细不能为空", "");
		}
		// 创建查询条件
		TruckDepartPlanDetailDto detailDto = new TruckDepartPlanDetailDto();
		//发车明细id
		detailDto.setId(truckDepartPlanDetailId);
		//发车状态
		detailDto.setStatus(status);
		//调用dao返回发车明细
		return truckDepartPlanDetailDao.queryTruckDepartPlanDetailById(detailDto);
	}
	/**
	 * 
	 * 根据 出发部门、到达部门、日期查询发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2013-1-7 上午9:12:33
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryPlanDetailCount(java.lang.String,
	 *      java.lang.String, java.util.Date)
	 * 
	 */
	@Override
	public Long queryPlanDetailCount(String origOrgCode, String destOrgCode, Date departDate)
			throws TruckDepartPlanException {
		// 判空
		if (StringUtils.isBlank(origOrgCode)) {
			// 日志
			LOGGER.error("出发部门不能为空");
			// 异常
			throw new TruckDepartPlanException("出发部门不能为空", "");
		}
		// 判空
		if (StringUtils.isBlank(destOrgCode)) {
			// 日志
			LOGGER.error("到达部门不能为空");
			// 异常
			throw new TruckDepartPlanException("到达部门不能为空", "");
		}
		// 判空
		if (departDate == null) {
			// 日志
			LOGGER.error("发车日期不能为空");
			// 异常
			throw new TruckDepartPlanException("发车日期不能为空", "");
		}
		// 创建查询条件
		TruckDepartPlanDetailDto detailDto = new TruckDepartPlanDetailDto();
		// 发车日期
		detailDto.setDepartDate(departDate);
		// 出发部门
		detailDto.setOrigOrgCode(origOrgCode);
		// 到达部门
		detailDto.setDestOrgCode(destOrgCode);
		// 可用
		detailDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		// 短途新建
		detailDto.setPlanStatusNew(TruckDepartPlanConstants.PLAN_STATUS_NEW);
		// 短途新建
		detailDto.setPlanTypeShort(TruckDepartPlanConstants.PLAN_TYPE_SHORT);
		// 长途下发
		detailDto.setPlanStatusRelease(TruckDepartPlanConstants.PLAN_STATUS_RELEASE);
		// 长途下发
		detailDto.setPlanTypeLong(TruckDepartPlanConstants.PLAN_TYPE_LONG);
		// 未出发
		detailDto.setHasLeft(TruckDepartPlanConstants.LEFT_FLAG_N);
		// 长途
		detailDto.setPlanType(TruckDepartPlanConstants.PLAN_TYPE_LONG);
		// 加发和正常的
		List<String> list = new ArrayList<String>();
		// 加发
		list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_ADD);
		// 正常
		list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
		// 列表
		detailDto.setList(list);
		// 执行查询
		return truckDepartPlanDetailDao.queryPlanDetailCount(detailDto);
	}

	/**
	 * 
	 * 下发修改后保存备注信息
	 * 
	 * 下发修改后保存备注信息
	 * 
	 * 用于保存发车计划，主要是保存是否异常及备注的信息。
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2013-1-17 下午3:11:47
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#saveRemarkAfterSaveSuccess(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public void saveRemarkAfterSaveSuccess(TruckDepartPlanDetailDto truckDepartPlanDetailDto, CurrentInfo user) {
		// 用户备注不为空,则保存用户备注日志
		if (StringUtils.isNotBlank(truckDepartPlanDetailDto.getRemark())
				&& StringUtils.isNotBlank(truckDepartPlanDetailDto.getId())) {
			// 新增保存用户备注日志
			makeUserRemarkLog(truckDepartPlanDetailDto, user);
		}
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-6-25 下午3:34:59
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryLatestTruckDepartPlanDetailByContainerNo(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public TruckDepartPlanDetailDto queryLatestTruckDepartPlanDetailByContainerNo(
			String origOrgCode, String destOrgCode, String containerNo,
			Date departDate) {
		        // 创建查询条件
				TruckDepartPlanDetailDto detailDto = new TruckDepartPlanDetailDto();
				// 发车日期
				if (departDate != null) {
					// 发车日期
					detailDto.setDepartDate(departDate);
				}
				// 货柜编号
				detailDto.setContainerNo(containerNo);
				// 出发部门
				detailDto.setOrigOrgCode(origOrgCode);
				// 到达部门
				detailDto.setDestOrgCode(destOrgCode);
				// 可用
				detailDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
				// 下发
				// 长途下发
				detailDto.setPlanStatusRelease(TruckDepartPlanConstants.PLAN_STATUS_RELEASE);
				// 长途下发
				detailDto.setPlanTypeLong(TruckDepartPlanConstants.PLAN_TYPE_LONG);
				// 未出发
				detailDto.setHasLeft(TruckDepartPlanConstants.LEFT_FLAG_N);
				// 加发和正常的
				List<String> list = new ArrayList<String>();
				// 加发
				list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_ADD);
				// 正常
				list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
				// 列表
				detailDto.setList(list);
				// 执行查询
				return truckDepartPlanDetailDao.queryLatestTruckDepartPlanDetailByContainerNo(detailDto);
	}
	/**
	 **根据挂牌号查询最早的发车计划
	 * @author heyongdong
	 * @date 2014年9月1日 15:37:37
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService#queryDepartPlanDetailByTrailerVehicleNo(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public TruckDepartPlanDetailDto queryDepartPlanDetailByTrailerVehicleNo(
			String orgCode, String arriveDeptCode, String trailerVehicleNo,Date departDate) {
		// 出发部门不能为空
				if (StringUtils.isBlank(orgCode)) {
					throw new TruckDepartPlanException("出发部门不能为空", "");
				}
				// 到达部门不能为空
				if (StringUtils.isBlank(arriveDeptCode)) {
					throw new TruckDepartPlanException("到达部门不能为空", "");
				}
				// 挂牌号不能为空
				if (StringUtils.isBlank(trailerVehicleNo)) {
					throw new TruckDepartPlanException("车牌号不能为空", "");
				}
				// 创建查询条件
				TruckDepartPlanDetailDto detailDto = new TruckDepartPlanDetailDto();
				// 发车日期
				if (departDate != null) {
					// 发车日期
					detailDto.setDepartDate(departDate);
				}
				// 车牌
				detailDto.setVehicleNo(trailerVehicleNo);
				// 出发部门
				detailDto.setOrigOrgCode(orgCode);
				// 到达部门
				detailDto.setDestOrgCode(arriveDeptCode);
				// 可用
				detailDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
				// 下发
				// 短途新建
				detailDto.setPlanStatusNew(TruckDepartPlanConstants.PLAN_STATUS_NEW);
				// 短途新建
				detailDto.setPlanTypeShort(TruckDepartPlanConstants.PLAN_TYPE_SHORT);
				// 长途下发
				detailDto.setPlanStatusRelease(TruckDepartPlanConstants.PLAN_STATUS_RELEASE);
				// 长途下发
				detailDto.setPlanTypeLong(TruckDepartPlanConstants.PLAN_TYPE_LONG);
				// 未出发
				detailDto.setHasLeft(TruckDepartPlanConstants.LEFT_FLAG_N);
				// 加发和正常的
				List<String> list = new ArrayList<String>();
				// 加发
				list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_ADD);
				// 正常
				list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
				// 列表
				detailDto.setList(list);
				// 执行查询
				return truckDepartPlanDetailDao.queryDepartPlanDetailByTrailerVehicleNo(detailDto);
		
	}
	
	/**
	 * 
	* @description FOSS调用悟空接口的中间方法
	* @param origOrgCode 出发部门编码
	* @param destOrgCode 到达部门编码
	* @return 线路(有可能是半截)的发车标准Dto
	* @version 1.0
	* @author 332209-foss-ruilibao
	* @update 2016-5-12 下午5:33:22
	 */
	private List<DepartureStandardDto> getExpressLine(String origOrgCode, String destOrgCode) {

		// 调用悟空方法后返回的线路(有可能是半截)的发车标准DTO LIST
		List<DepartureStandardDto> resultList = new ArrayList<DepartureStandardDto>();
		
		try {
			
			// 调用获取快递线路Service
			FossToWKResStdEntity  entity = fossToWkService.getExpresslineInfoFromWk(origOrgCode, destOrgCode);
			// 返回的实体转换为的线路(有可能是半截)的发车标准DTO LIST
			if (entity != null && entity.getData() != null) {
				resultList = JSONObject.parseObject(JSONObject.toJSONString(entity.getData()), new TypeReference<List<DepartureStandardDto>>(){});
				int ownLimitation = 0;
				int outSourceLimitation = 0;
				if (StringUtils.isNotEmpty(entity.getOwnLimitation())) {
					ownLimitation = BigDecimal.valueOf(Double.valueOf(entity.getOwnLimitation())).multiply(TruckDepartPlanConstants.MIN_60).multiply(TruckDepartPlanConstants.SEC_60).intValue();
				}
				if (StringUtils.isNotEmpty(entity.getOutSourceLimitation())) {
					outSourceLimitation = BigDecimal.valueOf(Double.valueOf(entity.getOutSourceLimitation())).multiply(TruckDepartPlanConstants.MIN_60).multiply(TruckDepartPlanConstants.SEC_60).intValue();
				}
				this.setOwnLimitation(ownLimitation);
				this.setOutSourceLimitation(outSourceLimitation);
			}
		} catch (Exception e) {
			LOGGER.error("获取悟空系统信息异常", e.getMessage());
			return resultList;
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryDepartPlanDetailOrig(
			TruckDepartPlanDetailDto truckDepartPlanDetailDto) {
		// TODO Auto-generated method stub
				if(!TruckDepartPlanConstants.PLAN_TYPE_LONG.equals(truckDepartPlanDetailDto.getPlanType())){
					return null;
				}
				Map<String,String> paramMap = new HashMap<String, String>();
				paramMap.put("vehicleNo", truckDepartPlanDetailDto.getVehicleNo());
				paramMap.put("deptCode", truckDepartPlanDetailDto.getOrigOrgCode());
				//未出发状态
				paramMap.put("status", TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
				List<String> listNameUndepart = (List<String>) sealInformationDao.checkExpressEIRCarGenerateDao(paramMap);
				if(listNameUndepart != null && !listNameUndepart.isEmpty()){
					return listNameUndepart;
				}
				//在途状态
				paramMap.put("status", TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);
				List<String> listNameOntheway = (List<String>) sealInformationDao.checkExpressEIRCarGenerateDao(paramMap);
				if(listNameOntheway !=null && !listNameOntheway.isEmpty()){
					return listNameOntheway;
				}
				return null;
	}
	
}