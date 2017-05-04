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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/TruckDepartPlanService.java
 * 
 *  FILE NAME     :TruckDepartPlanService.java
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
 * FILE    NAME: TruckDepartPlanService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.NetGroupSiteDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResStdEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKDepartCodeEntity;

import com.deppon.foss.module.transfer.scheduling.api.define.ForecastConstants;
import com.deppon.foss.module.transfer.scheduling.api.define.ScheduleConstants;
import com.deppon.foss.module.transfer.scheduling.api.define.TruckDepartPlanConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IForecastQuantityDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanUpdateService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ChangeQuantityEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckDepartPlanUpdateEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ForecastForPlanDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.MergeLogDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.TruckDepartPlanException;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.TruckDepartPlanVo;
import com.deppon.foss.module.transfer.scheduling.server.action.TruckDepartPlanAction;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 计划service实现 * 长途制定发车计划需求：
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
 * 
 * 班次类型：加发、停发、正常，必须选择一项，默认为正常。
 * 
 * 1.5.3.4 长途发车计划列表
 * 
 * 长途发车计划列表显示信息：列表参见【发车计划列表】。
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
 * 模块需要时使用。
 * 
 * SR-16 填写月台号时，填写预计月台使用和预计结束时间;预计月台结束时间系统默认为发车时效表规定的出发时间，
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
 * @date 2012-11-21 下午7:04:08
 */
public class TruckDepartPlanService implements ITruckDepartPlanService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TruckDepartPlanAction.class);
	/**
	 * 计划Dao
	 */
	private ITruckDepartPlanDao truckDepartPlanDao;
	/**
	 * 计划明细Dao
	 */
	private ITruckDepartPlanDetailDao truckDepartPlanDetailDao;
	/**
	 * 计划明细Service
	 */
	private ITruckDepartPlanUpdateService truckDepartPlanUpdateService;
	/**
	 * 线路Service
	 */
	private ILineService lineService;
	
	private IExpressLineService expresslineService ;
	
	private IFOSSToWkService fossToWkService;
	
	// 屏蔽ECS系统接口服务类
	private IConfigurationParamsService configurationParamsService;
	
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
	 * 组织机构Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService; 
	/**
	 * 自有司机Service
	 */
	private IOwnDriverService ownDriverService;
	/**
	 * 外请司机Service
	 */
	private ILeasedDriverService leasedDriverService;
	/**
	 * 外请车Service
	 */
	private ILeasedVehicleService leasedVehicleService;
	/**
	 * 货量预测Dao
	 */
	private IForecastQuantityDao forecastQuantityDao;
	/**
	 * 合车记录Service
	 */
	private IForecastService forecastService;
	/**
	 * 排班计划Service
	 */
	private ITruckSchedulingTaskService truckSchedulingTaskService;
	/**
	 * 车队Service
	 */
	private IMotorcadeService motorcadeService;
	/**
	 * 月台Service
	 */
	private IPlatformService platformService;

	/**
	 * 
	 * 设置 计划Dao.
	 * 
	 * @param truckDepartPlanDao
	 *            the new 计划Dao
	 */
	public void setTruckDepartPlanDao(ITruckDepartPlanDao truckDepartPlanDao) {
		this.truckDepartPlanDao = truckDepartPlanDao;
	}

	/**
	 * 
	 * 设置 计划明细Dao.
	 * 
	 * @param truckDepartPlanDetailDao
	 *            the new 计划明细Dao
	 */
	public void setTruckDepartPlanDetailDao(ITruckDepartPlanDetailDao truckDepartPlanDetailDao) {
		this.truckDepartPlanDetailDao = truckDepartPlanDetailDao;
	}

	/**
	 * 
	 * 设置 线路Service.
	 * 
	 * @param lineService
	 * 
	 *            the new 线路Service
	 */
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	/**
	 * 
	 * 设置 组织机构Service.
	 * 
	 * @param orgAdministrativeInfoService
	 * 
	 *            the new 组织机构Service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 
	 * 设置 自有司机Service.
	 * 
	 * @param ownDriverService
	 * 
	 *            the new 自有司机Service
	 */
	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}

	/**
	 * 
	 * 设置 外请司机Service.
	 * 
	 * @param leasedDriverService
	 * 
	 *            the new 外请司机Service
	 */
	public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
		this.leasedDriverService = leasedDriverService;
	}

	/**
	 * 
	 * 
	 * 设置 计划明细Service.
	 * 
	 * @param truckDepartPlanUpdateService
	 * 
	 *            the new 计划明细Service
	 */
	public void setTruckDepartPlanUpdateService(ITruckDepartPlanUpdateService truckDepartPlanUpdateService) {
		this.truckDepartPlanUpdateService = truckDepartPlanUpdateService;
	}

	/**
	 * 
	 * 
	 * 设置 货量预测Dao.
	 * 
	 * 
	 * @param forecastQuantityDao
	 * 
	 *            the new 货量预测Dao
	 */
	public void setForecastQuantityDao(IForecastQuantityDao forecastQuantityDao) {
		this.forecastQuantityDao = forecastQuantityDao;
	}

	/**
	 * 
	 * 
	 * 设置 合车记录Service.
	 * 
	 * @param forecastService
	 * 
	 * 
	 *            the new 合车记录Service
	 */
	public void setForecastService(IForecastService forecastService) {
		this.forecastService = forecastService;
	}

	/**
	 * 
	 * 
	 * 设置 排班计划Service.
	 * 
	 * @param truckSchedulingTaskService
	 * 
	 * 
	 *            the new 排班计划Service
	 */
	public void setTruckSchedulingTaskService(ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}

	/**
	 * 
	 * 
	 * 车队Service
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * @date 2013-1-12 下午5:00:11
	 */
	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		this.leasedVehicleService = leasedVehicleService;
	}

	public void setPlatformService(IPlatformService platformService) {
		this.platformService = platformService;
	}

	/**
	 * 新增计划
	 * 
	 * <dl>
	 * 
	 * <dd>1、根据Dto的构建发车计划</dd>
	 * 
	 * <dd>2、根据发车计划的出发部门、到达部门线路信息</dd>
	 * 
	 * <dd>3、根据线路信息 查询线路发车时效（班次）列表</dd>
	 * 
	 * <dd>4、根据发车时效（班次）列表，初始化本发车计划的发车计划明细</dd>
	 * 
	 * <dd>5、持久化发车计划及发车计划明细</dd>
	 * 
	 * </dl>
	 * 后续还需要根据发车时效初始化，发车计划明细;
	 * 
	 * 在只填写了正确的线路及发车日期的情况下，未查询到任何的发车计划的情况下;
	 * 
	 * 系统显示新增计划按钮;
	 * 
	 * 用户点击新增按钮，跳转到录入发车计划界面;
	 * 
	 * 跳转到录入发车计划界面,具体参见用例SUC-313，
	 * 
	 * 可针对本发车计划对应的线路的所有班次进行调整或新增。
	 * 
	 * 点击编辑按钮，系统根据本发车计划信息，跳转到录入发车计划信息界面，
	 * 
	 * 用户可根据最新的货量预测信息调整编辑对应的发车计划。
	 * 
	 * 具体的修改参见用例SUC-313录入发车计划（长途）中的编辑功能。
	 * 
	 * 查询结果需要按照操作用户权限的匹配查询结果，发车日期为必填，
	 * 
	 * 进入本页面时，默认查询结果为空，待用户录入相应的查询条件并点击查询按钮时，
	 * 
	 * 才执行查询操作，查询出相应的结果呈现在查询结果中，
	 * 
	 * 查询出的结果默认以创建时间降序排序。
	 * 
	 * 用户录入了发车日期、出发部门、到达部门后，执行查询未查询到相应的发车计划，
	 * 
	 * 则显示新增计划按钮，用以新增对应发车日期、出发部门、
	 * 
	 * 到达部门的发车计划，跳转到录入发车计划页面。
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-21 下午7:04:08
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#addTruckDepartPlan(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@Override
	@Transactional
	public int addTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto, CurrentInfo user)
			throws TruckDepartPlanException {
		// 校验出发部门到达部门
		validateOrigAndDestDepart(truckDepartPlanDto);
		// 新增
		if (truckDepartPlanDto != null) {
			// 验证出发部门，到达部门，计划日期
			if (StringUtils.isNotBlank(truckDepartPlanDto.getOrigOrgCode())
					&& StringUtils.isNotBlank(truckDepartPlanDto.getDestOrgCode())
					&& truckDepartPlanDto.getPlanDate() != null) {
				// 如果不是出发部门或者达到部门
				if (!validateOrg(truckDepartPlanDto.getOrigOrgCode())
						&& !validateOrg(truckDepartPlanDto.getDestOrgCode())) {
					throw new TruckDepartPlanException("非营业部或者外场", "");
				}
			} else {
				throw new TruckDepartPlanException("出发部门、到达部门、计划日期必填", "");
			}
			// 验证计划日期、出发部门，到达部门是否已经制定过
			boolean flag = hasExsitTruckDepartPlan(truckDepartPlanDto);
			// 如果还没有，则执行新增，并初始化
			if (!flag) {
				// 初始化发车计划的班次列表
				initTruckDepartPlan(truckDepartPlanDto, user);
				// 批量持久化发车计划明细
				truckDepartPlanDetailDao.batchAddTruckDepartPlanDetail(truckDepartPlanDto);
				// 持久化发车计划
				truckDepartPlanDao.addTruckDepartPlan(truckDepartPlanDto);
				// 构建新增计划明细日志
				makeTruckDepartPlanUpdateLog(truckDepartPlanDto, user);
			} else {
				// 已经存在
				return TruckDepartPlanConstants.SERVICE_TRANSACTION_HAS_ADDED;
			}
			// 返回
			return TruckDepartPlanConstants.SERVICE_TRANSACTION_SUCCESS;
		} else {
			// 返回
			return TruckDepartPlanConstants.SERVICE_TRANSACTION_FAILURE;
		}
	}

	/**
	 * 
	 * 生成日志
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-4 上午10:01:44
	 */
	private void makeTruckDepartPlanUpdateLog(TruckDepartPlanDto truckDepartPlanDto, CurrentInfo user) {
		// 日志列表
		List<TruckDepartPlanUpdateEntity> detailLogs = null;
		// 判空
		if (truckDepartPlanDto != null && CollectionUtils.isNotEmpty(truckDepartPlanDto.getList())) {
			// 日志对象
			TruckDepartPlanUpdateEntity detailLog = null;
			// 日志列表
			detailLogs = new ArrayList<TruckDepartPlanUpdateEntity>();
			// 循环比对，生成日志对象
			for (TruckDepartPlanDetailDto detailDto : truckDepartPlanDto.getList()) {
				// 判空
				if (detailDto != null) {
					// 获取日志比对信息
					detailLog = truckDepartPlanUpdateService.compareDepartPlanDetail(null, detailDto, user);
					// 判空
					if (detailLog != null) {
						// 日志类型-自动记录的日志
						detailLog.setLogType(TruckDepartPlanConstants.LOG_TYPE_AUTO);
						// 创建时间信息
						detailLog.setCreateTime(Calendar.getInstance().getTime());
						if (user != null) {
							// 创建人信息
							detailLog.setCreateUserCode(user.getEmpCode());
							// 当前用户名
							detailLog.setCreateUserName(user.getEmpName());
							// 编码
							detailLog.setCreateOrgCode(user.getCurrentDeptCode());
						}
						// 列表
						detailLogs.add(detailLog);
					}
				}
			}
			// 不为空则批量新增
			if (CollectionUtils.isNotEmpty(detailLogs)) {
				// 新增
				truckDepartPlanUpdateService.addTruckDepartPlanUpdates(detailLogs);
			}
		}
	}

	/**
	 * 
	 * 查询本发车计划是否已经存在
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-29 下午1:07:58
	 * 
	 */
	@Override
	public boolean hasExsitTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto) {
		// 初始化
		boolean flag = false;
		// 查询是否已经存在计划日期、出发部门、到达部门的发车计划
		List<TruckDepartPlanDto> planList = truckDepartPlanDao.queryTruckDepartPlan(truckDepartPlanDto);
		// 不空
		if (CollectionUtils.isNotEmpty(planList)) {
			// 将查询的ID 返回
			truckDepartPlanDto.setId(planList.get(0).getId());
			// 真
			flag = true;
		}
		// 返回
		return flag;
	}

	/**
	 * 
	 * 初始化发车计划
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * @date 2012-11-29 下午12:59:39
	 * 
	 */
	private void initTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto, CurrentInfo user) {
		// UUID
		truckDepartPlanDto.setId(UUIDUtils.getUUID());
		// 是否异常
		truckDepartPlanDto.setIsIssue(TruckDepartPlanConstants.IS_ISSUE_NO);
		// 状态
		truckDepartPlanDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		// 创建信息
		truckDepartPlanDto.setCreateTime(Calendar.getInstance().getTime());
		if (user != null) {
			// 编码
			truckDepartPlanDto.setCreateUserCode(user.getEmpCode());
			// 部门编码
			truckDepartPlanDto.setCreateOrgCode(user.getCurrentDeptCode());
			// 部门名称
			truckDepartPlanDto.setCreateUserName(user.getEmpName());
		}
		// 出发部门到达部门不为空
		if (StringUtils.isNotBlank(truckDepartPlanDto.getOrigOrgCode())
				&& StringUtils.isNotBlank(truckDepartPlanDto.getDestOrgCode())) {
			// 根据出发部门、到达部门查询班次列表
			List<DepartureStandardDto> departureList = lineService.queryDepartureStandardListBySourceTarget(
					truckDepartPlanDto.getOrigOrgCode(), truckDepartPlanDto.getDestOrgCode());
			// 不空
			if (CollectionUtils.isNotEmpty(departureList)) {
				// 根据班次列表生成的发车计划明细列表
				List<TruckDepartPlanDetailDto> detailList = new ArrayList<TruckDepartPlanDetailDto>();
				// 如果是长途发车计划
				if (TruckDepartPlanConstants.PLAN_TYPE_LONG.equals(truckDepartPlanDto.getPlanType())) {
					// 初始化长途发车计划明细
					initLongTruckDepartPlanDetail(truckDepartPlanDto, detailList, departureList, user);
				}
				// 短途发车计划
				else {
					// 初始化短途发车计划明细
					initShortTruckDepartPlanDetail(truckDepartPlanDto, detailList, departureList, user);
				}
				// 发车计划明细保存入发车计划
				truckDepartPlanDto.setList(detailList);
				// 验证计划非空字段信息
				validateDepartPlanNotNullProperty(truckDepartPlanDto);
			}else{
				//增加ECS系统调用开关
				if (configurationParamsService.queryTfrSwitch4Ecs()) {
					//ECS系统接口
					Log.error("调用ECS系统接口 ： getExpressLine");
					departureList = getExpressLine(truckDepartPlanDto.getOrigOrgCode(), truckDepartPlanDto.getDestOrgCode());
				} else {
					//调用快递接口
					Log.error("调用FOSS系统接口：expresslineService");
					departureList = expresslineService.queryDepartureStandardListBySourceTarget(truckDepartPlanDto.getOrigOrgCode(), truckDepartPlanDto.getDestOrgCode());
				}

				if (CollectionUtils.isNotEmpty(departureList)) {
						// 根据班次列表生成的发车计划明细列表
						List<TruckDepartPlanDetailDto> detailList = new ArrayList<TruckDepartPlanDetailDto>();
						// 如果是长途发车计划
						if (TruckDepartPlanConstants.PLAN_TYPE_LONG.equals(truckDepartPlanDto.getPlanType())) {
							// 初始化长途发车计划明细
							initLongTruckDepartPlanDetail(truckDepartPlanDto, detailList, departureList, user);
						}
						// 短途发车计划
						else {
							// 初始化短途发车计划明细
							initShortTruckDepartPlanDetail(truckDepartPlanDto, detailList, departureList, user);
						}
						// 发车计划明细保存入发车计划
						truckDepartPlanDto.setList(detailList);
						// 验证计划非空字段信息
						validateDepartPlanNotNullProperty(truckDepartPlanDto);
					}
			}
		}
	}

	/**
	 * 
	 * 初始化短途发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-18 上午11:37:43
	 * 
	 */
	private void initShortTruckDepartPlanDetail(TruckDepartPlanDto truckDepartPlanDto,
			List<TruckDepartPlanDetailDto> detailList, List<DepartureStandardDto> departureList, CurrentInfo user) {
		// 计划明细
		TruckDepartPlanDetailDto detailEntity = null;
		// 预计准备到达时间
		Date planArriveTime = null;
		// 当前时间
		Calendar c = Calendar.getInstance();
		// 根据班次列信息循获取线路列表
		Map<String, String> lineMap = new HashMap<String, String>();
		// 线路班次预计到达时间
		Map<String, Date> lineArriveDayMap = new HashMap<String, Date>();
		// 将线路列表和线路班次对应的预计达到时间收集起来
		if (CollectionUtils.isNotEmpty(departureList)) {
			// 玄幻
			for (DepartureStandardDto departureDto : departureList) {
				// 加入线路
				lineMap.put(departureDto.getLineVirtualCode(), departureDto.getLineName());
				// 发车日期
				truckDepartPlanDto.getPlanDate();
				// 预计到达时间
				if (StringUtils.isNotBlank(departureDto.getArriveTime()) && departureDto.getArriveDay() != null) {
					// 设置为计划发车时间
					c.setTime(truckDepartPlanDto.getPlanDate());
					// 根据到达天数，新增至到达当天
					c.add(Calendar.DATE, departureDto.getArriveDay());
					// 根据预计时间，设置当前预计时间
					planArriveTime = DateUtils.concat(DateUtils.convert(c.getTime(), DateUtils.DATE_FORMAT),
							departureDto.getLeaveTime());
					// 使用线路班次作为key,预计到达时间作为value
					lineArriveDayMap.put(departureDto.getLineVirtualCode() + departureDto.getOrder(), planArriveTime);
				} else {
					// 预计到达时间
					planArriveTime = null;
				}
			}
		}
		// 通过线路列表、配合发车日期、查询短途排班列表
		if (!lineMap.isEmpty()) {
			// 线路时效
			ArrayList<String> lineVirtualCode = new ArrayList<String>(lineMap.keySet());
			// 根据线路列表和日期 ，查询短途排班对应的排班列表
			List<SimpleTruckScheduleDto> taskDtos = truckSchedulingTaskService.queryFrequencyNosBylineVirtualCode(
					lineVirtualCode, truckDepartPlanDto.getPlanDate());
			// 如果查询的短途排班不为空，则使用此数据初始化
			if (CollectionUtils.isNotEmpty(taskDtos)) {
				Map<String, SimpleTruckScheduleDto> tmpMap = new HashMap<String, SimpleTruckScheduleDto>();
				// 循环
				for (SimpleTruckScheduleDto scheduleDto : taskDtos) {
					if (scheduleDto != null) {
						// 如果已经存在，则跳过继续循环
						if (tmpMap.get(scheduleDto.getLineNo() + scheduleDto.getFrequencyNo()) != null) {
							continue;
						}
						// 计划明细
						detailEntity = new TruckDepartPlanDetailDto();
						// 发车计划初始化为未出发
						detailEntity.setHasLeft(TruckDepartPlanConstants.LEFT_FLAG_N);
						// 导入状态
						detailEntity.setInitFlag(TruckDepartPlanConstants.INIT_FLAG_Y);
						// 状态(可用)
						detailEntity.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
						// 计划类型
						detailEntity.setPlanType(truckDepartPlanDto.getPlanType());
						// 出发部门
						detailEntity.setOrigOrgCode(truckDepartPlanDto.getOrigOrgCode());
						// 到达部门
						detailEntity.setDestOrgCode(truckDepartPlanDto.getDestOrgCode());
						// 判空
						if (truckDepartPlanDto.getPlanDate() != null) {
							// UUID
							detailEntity.setId(UUIDUtils.getUUID());
							// 发车计划日期
							detailEntity.setDepartDate(truckDepartPlanDto.getPlanDate());
						} else {
							// 发车日期为空
							throw new TruckDepartPlanException("发车日期为空", "");
						}
						// 班次时间
						if (scheduleDto.getDepartTime() != null) {
							// 计划发车时间
							detailEntity.setPlanDepartTime(scheduleDto.getDepartTime());
						} else {
							// 短途排班未安排计划发车时间
							throw new TruckDepartPlanException("短途排班未安排计划发车时间", "");
						}
						// 线路虚拟code
						if (StringUtils.isNotBlank(scheduleDto.getLineNo())) {
							// 线路虚拟code
							detailEntity.setLineNo(scheduleDto.getLineNo());
							// 线路名
							detailEntity.setLineName(lineMap.get(scheduleDto.getLineNo()));
						} else {
							throw new TruckDepartPlanException("线路虚拟编码为空", "");
						}
						// 班次号
						if (StringUtils.isNotBlank(scheduleDto.getFrequencyNo())) {
							// 班次号
							detailEntity.setFrequencyNo(scheduleDto.getFrequencyNo());
						}
						// 预计到达时间
						String key = scheduleDto.getLineNo() + scheduleDto.getFrequencyNo();
						// 判空
						if (lineArriveDayMap.get(key) != null) {
							// 预计到达时间
							detailEntity.setPlanArriveTime(lineArriveDayMap.get(key));
						}
						// 计划ID
						detailEntity.setTruckDepartPlanId(truckDepartPlanDto.getId());
						// 创建信息
						detailEntity.setCreateTime(Calendar.getInstance().getTime());
						// 判空
						if (user != null) {
							// 用户编码
							detailEntity.setCreateUserCode(user.getEmpCode());
							// 当前部门
							detailEntity.setCreateOrgCode(user.getCurrentDeptCode());
							// 部门名称
							detailEntity.setCreateUserName(user.getEmpName());
						}
						// 状态
						detailEntity.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
						// 计划类型
						detailEntity.setPlanType(truckDepartPlanDto.getPlanType());
						// 是否异常
						detailEntity.setIsOnScheduling(TruckDepartPlanConstants.IS_ON_SCHEDULING_YES);
						// 班次类型
						detailEntity.setFrequencyType(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
						// 车型
						if (scheduleDto.getTruckModel() != null) {
							// 车型
							detailEntity.setTruckModel(scheduleDto.getTruckModel());
						}
						// 司机信息
						if (StringUtils.isNotBlank(scheduleDto.getDriverCode())) {
							// 司机编码
							detailEntity.setDriverCode1(scheduleDto.getDriverCode());
							// 司机姓名
							detailEntity.setDriverName1(scheduleDto.getDriverName());
							// 司机电话
							detailEntity.setDriverPhone1(scheduleDto.getDriverPhone());
						}
						// 车牌号
						if (StringUtils.isNotBlank(scheduleDto.getVehicleNo())) {
							// 设置车牌号
							detailEntity.setVehicleNo(scheduleDto.getVehicleNo());
						}
						// 车辆归属类型
						detailEntity.setTruckType(TruckDepartPlanConstants.TRUCK_TYPE_MYSELF_OWN);
						// 计划状态
						detailEntity.setPlanStatus(TruckDepartPlanConstants.PLAN_STATUS_NEW);
						// 时效导入
						detailEntity.setInitFlag(TruckDepartPlanConstants.INIT_FLAG_Y);
						// 短途排版班任务ID
						detailEntity.setScheduleTaskId(scheduleDto.getScheduleTaskId());
						// 验证计划明细非空字段
						validateDepartPlanDetailNotNullProperty(detailEntity);
						// 加入列表
						detailList.add(detailEntity);
						tmpMap.put(scheduleDto.getLineNo() + scheduleDto.getFrequencyNo(), scheduleDto);
					}
				}
			}
		}
	}

	/**
	 * 
	 * 
	 * 初始化发车计划明细
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-29 下午1:04:14
	 * 
	 */
	private void initLongTruckDepartPlanDetail(TruckDepartPlanDto truckDepartPlanDto,
			List<TruckDepartPlanDetailDto> detailList, List<DepartureStandardDto> departureList, CurrentInfo user) {
		// 计划明细
		TruckDepartPlanDetailDto detailEntity = null;
		// 计划发车班次时间
		Date planDepartTime = null;
		// 预计准备到达时间
		Date planArriveTime = null;
		// 当前时间
		Calendar c = Calendar.getInstance();
		// 根据班次列信息循环创建发车明细
		if (CollectionUtils.isNotEmpty(departureList)) {
			Map<String, TruckDepartPlanDetailDto> tmpMap = new HashMap<String, TruckDepartPlanDetailDto>();
			for (DepartureStandardDto departureDto : departureList) {
				// null或已经存在，则跳过
				if (null == departureDto
						|| tmpMap.get(departureDto.getLineVirtualCode() + departureDto.getOrder()) != null) {
					continue;
				}
				detailEntity = new TruckDepartPlanDetailDto();
				// 发车计划初始化为未出发
				detailEntity.setHasLeft(TruckDepartPlanConstants.LEFT_FLAG_N);
				// 导入状态
				detailEntity.setInitFlag(TruckDepartPlanConstants.INIT_FLAG_Y);
				// 状态(可用)
				detailEntity.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
				// 计划类型
				detailEntity.setPlanType(truckDepartPlanDto.getPlanType());
				// 出发部门
				detailEntity.setOrigOrgCode(truckDepartPlanDto.getOrigOrgCode());
				// 到达部门
				detailEntity.setDestOrgCode(truckDepartPlanDto.getDestOrgCode());
				if (truckDepartPlanDto.getPlanDate() != null) {
					// UUID
					detailEntity.setId(UUIDUtils.getUUID());
					// 发车计划日期
					detailEntity.setDepartDate(truckDepartPlanDto.getPlanDate());
					// 班次时间
					if (StringUtils.isNotBlank(departureDto.getLeaveTime()) && truckDepartPlanDto.getPlanDate() != null) {
						// 转换为日期时间类型
						planDepartTime = DateUtils.concat(
								DateUtils.convert(truckDepartPlanDto.getPlanDate(), DateUtils.DATE_FORMAT),
								departureDto.getLeaveTime());
						if (planDepartTime != null) {
							// 计划发车时间
							detailEntity.setPlanDepartTime(planDepartTime);
						} else {
							// 异常
							throw new TruckDepartPlanException("时效标准计划发车时间为空,请先配置再操作", "");
						}
					} else {
						// 异常
						throw new TruckDepartPlanException("时效标准计划发车时间为空,请先配置再操作", "");
					}
					// 预计到达时间
					if (StringUtils.isNotBlank(departureDto.getArriveTime()) && departureDto.getArriveDay() != null) {
						// 设置为计划发车时间
						c.setTime(truckDepartPlanDto.getPlanDate());
						// 根据到达天数，新增至到达当天
						c.add(Calendar.DATE, departureDto.getArriveDay());
						// 根据预计时间，设置当前预计时间
						planArriveTime = DateUtils.concat(DateUtils.convert(c.getTime(), DateUtils.DATE_FORMAT),
								departureDto.getLeaveTime());
						// 设置预计到达时间
						detailEntity.setPlanArriveTime(planArriveTime);
					} else {
						// 异常
						throw new TruckDepartPlanException("时效标准预计到达时间或准点到达时间的天数为空,请先配置再操作", "");
					}
				} else {
					// 异常
					throw new TruckDepartPlanException("发车时间为空", "");
				}
				// 班次号
				if (departureDto.getOrder() != null) {
					// 班次号
					detailEntity.setFrequencyNo(departureDto.getOrder().toString());
				}
				// 线路虚拟code
				detailEntity.setLineNo(departureDto.getLineVirtualCode());
				// 线路名
				detailEntity.setLineName(departureDto.getLineName());
				// 计划ID
				detailEntity.setTruckDepartPlanId(truckDepartPlanDto.getId());
				// 创建信息
				detailEntity.setCreateTime(Calendar.getInstance().getTime());
				// 判空
				if (user != null) {
					// 用户编码
					detailEntity.setCreateUserCode(user.getEmpCode());
					// 当前部门编码
					detailEntity.setCreateOrgCode(user.getCurrentDeptCode());
					// 用户名
					detailEntity.setCreateUserName(user.getEmpName());
				}
				// 状态
				detailEntity.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
				// 计划类型
				detailEntity.setPlanType(truckDepartPlanDto.getPlanType());
				// 是否异常
				detailEntity.setIsOnScheduling(TruckDepartPlanConstants.IS_ON_SCHEDULING_YES);
				// 班次类型
				detailEntity.setFrequencyType(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
				// 导入时车型可为空
				// 车辆归属类型
				detailEntity.setTruckType(TruckDepartPlanConstants.TRUCK_TYPE_MYSELF_OWN);
				// 计划状态
				detailEntity.setPlanStatus(TruckDepartPlanConstants.PLAN_STATUS_NEW);
				// 时效导入
				detailEntity.setInitFlag(TruckDepartPlanConstants.INIT_FLAG_Y);
				// 验证计划明细非空字段
				validateDepartPlanDetailNotNullProperty(detailEntity);
				// 加入列表
				detailList.add(detailEntity);
				tmpMap.put(departureDto.getLineVirtualCode() + departureDto.getOrder(), detailEntity);
			}
		}
	}

	/**
	 * 
	 * 
	 * 验证计划非空字段信息
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-3 上午11:01:21
	 * 
	 */
	private void validateDepartPlanNotNullProperty(TruckDepartPlanDto truckDepartPlanDto) {
		// 计划对象
		if (truckDepartPlanDto != null) {
			// 判空
			if (StringUtils.isBlank(truckDepartPlanDto.getId())) {
				// 异常
				throw new TruckDepartPlanException("司机编码为空", "");
			}
			// 出发部门编码
			if (StringUtils.isBlank(truckDepartPlanDto.getOrigOrgCode())) {
				// 异常
				throw new TruckDepartPlanException("出发部门编码为空", "");
			}// 到达部门编码
			if (StringUtils.isBlank(truckDepartPlanDto.getDestOrgCode())) {
				// 异常
				throw new TruckDepartPlanException("到达部门编码为空", "");
			}
			// 计划日期
			if (truckDepartPlanDto.getPlanDate() == null) {
				// 异常
				throw new TruckDepartPlanException("计划日期为空", "");
			}
			// 计划类型
			if (StringUtils.isBlank(truckDepartPlanDto.getPlanType())) {
				// 异常
				throw new TruckDepartPlanException("计划类型为空", "");
			}
			// 是否异常
			if (StringUtils.isBlank(truckDepartPlanDto.getIsIssue())) {
				// 异常
				throw new TruckDepartPlanException("是否异常为空", "");
			}
			// 计划状态
			if (StringUtils.isBlank(truckDepartPlanDto.getStatus())) {
				// 异常
				throw new TruckDepartPlanException("计划状态为空", "");
			}
			// 创建时间
			if (truckDepartPlanDto.getCreateTime() == null) {
				// 异常
				throw new TruckDepartPlanException("创建时间为空", "");
			}
			// 创建人编码
			if (StringUtils.isBlank(truckDepartPlanDto.getCreateUserCode())) {
				// 异常
				throw new TruckDepartPlanException("创建人编码为空", "");
			}
			// 创建人姓名
			if (StringUtils.isBlank(truckDepartPlanDto.getCreateUserName())) {
				// 异常
				throw new TruckDepartPlanException("创建人姓名为空", "");
			}
			// 创建所属机构
			if (StringUtils.isBlank(truckDepartPlanDto.getCreateOrgCode())) {
				// 异常
				throw new TruckDepartPlanException("创建所属机构为空", "");
			}
		}
	}

	/**
	 * 
	 * 验证计划明细非空字段
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-3 上午11:10:58
	 * 
	 */
	private void validateDepartPlanDetailNotNullProperty(TruckDepartPlanDetailDto detailEntity) {
		// 判空
		if (detailEntity != null) {
			// ID
			if (StringUtils.isBlank(detailEntity.getId())) {
				// 异常
				throw new TruckDepartPlanException("计划明细ID为空", "");
			}
			// 发车计划ID
			if (StringUtils.isBlank(detailEntity.getTruckDepartPlanId())) {
				// 异常
				throw new TruckDepartPlanException("发车计划ID为空", "");
			}
			// 计划类型PLAN_TYPE
			if (StringUtils.isBlank(detailEntity.getPlanType())) {
				// 异常
				throw new TruckDepartPlanException("计划类型为空", "");
			}
			// 线路LINE_NO
			if (StringUtils.isBlank(detailEntity.getLineNo())) {
				// 异常
				throw new TruckDepartPlanException("线路编号虚拟编码为空", "");
			}
			// 发车日期DEPART_DATE
			if (detailEntity.getDepartDate() == null) {
				// 异常
				throw new TruckDepartPlanException("发车日期为空", "");
			}
			// 计划发车时间PLAN_DEPART_TIME
			if (detailEntity.getPlanDepartTime() == null) {
				// 异常
				throw new TruckDepartPlanException("计划发车时间为空", "");
			}
			// 班次FREQUENCY_NO
			if (StringUtils.isBlank(detailEntity.getFrequencyNo())) {
				// 异常
				throw new TruckDepartPlanException("班次为空", "");
			}
			// 出发部门ORIG_ORG_CODE
			if (StringUtils.isBlank(detailEntity.getOrigOrgCode())) {
				// 异常
				throw new TruckDepartPlanException("出发部门为空", "");
			}
			// 到达部门DEST_ORG_CODE
			if (StringUtils.isBlank(detailEntity.getDestOrgCode())) {
				// 异常
				throw new TruckDepartPlanException("到达部门为空", "");
			}
			// 是否正班车IS_ON_SCHEDULING
			if (StringUtils.isBlank(detailEntity.getIsOnScheduling())) {
				// 异常
				throw new TruckDepartPlanException("是否正班车为空", "");
			}
			// 班次类型 FREQUENCY_TYPE
			if (StringUtils.isBlank(detailEntity.getId())) {
				// 异常
				throw new TruckDepartPlanException("班次类型为空", "");
			}
			// 车型TRUCK_MODEL,暂时不需要验证

			// 车辆归属类型TRUCK_TYPE
			if (StringUtils.isBlank(detailEntity.getId())) {
				// 异常
				throw new TruckDepartPlanException("车辆归属类型为空", "");
			}
			// STATUS
			if (StringUtils.isBlank(detailEntity.getStatus())) {
				// 异常
				throw new TruckDepartPlanException("计划明细状态为空", "");
			}
			// 明细创建时间为空
			if (detailEntity.getCreateTime() == null) {
				// 异常
				throw new TruckDepartPlanException("明细创建时间为空", "");
			}
			// 创建人编码
			if (StringUtils.isBlank(detailEntity.getCreateUserCode())) {
				// 异常
				throw new TruckDepartPlanException("明细创建人编码为空", "");
			}
			// 创建人姓名
			if (StringUtils.isBlank(detailEntity.getCreateUserName())) {
				// 异常
				throw new TruckDepartPlanException("明细创建人姓名为空", "");
			}
			// 创建人机构
			if (StringUtils.isBlank(detailEntity.getCreateOrgCode())) {
				// 异常
				throw new TruckDepartPlanException("明细创建人机构为空", "");
			}
		}
	}

	/**
	 * 
	 * 删除发车计划
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-21 下午7:04:08
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#deleteTruckDepartPlan(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 * 
	 */
	@Override
	@Transactional
	public int deleteTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto, CurrentInfo user)
			throws TruckDepartPlanException {
		// 删除发车计划
		truckDepartPlanDao.deleteTruckDepartPlan(truckDepartPlanDto);
		// 返回状态
		return TruckDepartPlanConstants.SERVICE_TRANSACTION_SUCCESS;
	}

	/**
	 * 查询统计发车计划
	 * 
	 * 
	 * 1. 查看：点击此按钮，进入录入发车计划页面，
	 * 
	 * 
	 * 根据线路货量信息和实际的发车计划进行调整和修改，
	 * 
	 * 
	 * 具体的修改参见用例SUC-313录入发车计划（长途）中的编辑调整功能。
	 * 
	 * 
	 * 此编辑功能可针对本发车计划对应的线路的所有班次进行调整。
	 * 
	 * 
	 * 2. 查询：按条件查询出所有满足要求的发车计划。
	 * 
	 * 
	 * 3. 新增发车计划：用于跳转到录入界面进行新增发车计划。
	 * 
	 * 
	 * 4.录入查询条件，发车日期为必填项，
	 * 
	 * 默认为当天 外场操作员、长途车队调度、班车调度、
	 * 
	 * 
	 * 请车员根据实际情况，点击查询按钮执行查询。
	 * 
	 * 系统根据查询条件返回相应的查询结果以表格形式显示，
	 * 
	 * 查询规则参见SR-3。
	 * 
	 * 点击编辑按钮，系统根据本发车计划信息，跳转到录入发车计划信息界面，
	 * 
	 * 用户可根据最新的货量预测信息调整编辑对应的发车计划。
	 * 
	 * 具体的修改参见用例SUC-313录入发车计划（长途）中的编辑功能。
	 * 
	 * 查询结果需要按照操作用户权限的匹配查询结果，发车日期为必填，
	 * 
	 * 进入本页面时，默认查询结果为空，待用户录入相应的查询条件并点击查询按钮时，
	 * 
	 * 才执行查询操作，查询出相应的结果呈现在查询结果中，
	 * 
	 * 查询出的结果默认以创建时间降序排序。
	 * 
	 * 用户录入了发车日期、出发部门、到达部门后，执行查询未查询到相应的发车计划，
	 * 
	 * 则显示新增计划按钮，用以新增对应发车日期、出发部门、
	 * 
	 * 到达部门的发车计划，跳转到录入发车计划页面。
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-21 下午7:04:08
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#queryTruckDepartPlan(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@Override
	public List<TruckDepartPlanDto> queryTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto, int limit, int start)
			throws TruckDepartPlanException {
		// 校验出发部门到达部门
		validateOrigAndDestDepart(truckDepartPlanDto);
		// 查询发车计划
		List<TruckDepartPlanDto> tempList = truckDepartPlanDao.queryTruckDepartPlan(truckDepartPlanDto, limit, start);
		// 发车计划不为空
		if (CollectionUtils.isNotEmpty(tempList)) {
			// 货量预测的重量、体积
			ForecastForPlanDto forecastIfno = null;
			// 循环查询货量预测信息
			// 载重合计(本线路发车当日发车计划所有车辆载重之和)
			BigDecimal preVolumeTotal = null;
			// 体积合计(本线路发车当日发车计划所有车辆净空之和)
			BigDecimal preWeightTotal = null;
			// 预测体积(最新发车当日货量预测的体积)
			BigDecimal weightTotal = null;
			// 预测重量(最新发车当日货量预测的重量)
			BigDecimal volumeTotal = null;
			// 循环
			for (TruckDepartPlanDto dto : tempList) {
				// 出发部门名称
				dto.setOrigOrgName(queryOrgName(dto.getOrigOrgCode()));
				// 到达部门名称
				dto.setDestOrgName(queryOrgName(dto.getDestOrgCode()));
				try {
					// 查询货量预测
					forecastIfno = this.queryForecastInfo(dto);
					// 异常
				} catch (Exception e) {
					LOGGER.error(
							dto.getPlanDate() + "：" + dto.getOrigOrgName() + "到" + dto.getDestOrgName() + "货量预测为空", e);
				}
				// 长途发车计划 体积、重量、缺口计算
				if (truckDepartPlanDto != null
						&& TruckDepartPlanConstants.PLAN_TYPE_LONG.equals(truckDepartPlanDto.getPlanType())) {
					// 查询货量预测的重量、体积
					if (forecastIfno != null) {
						// 总重量
						dto.setWeightTotal(forecastIfno.getWeightTotal());
						// 总体积
						dto.setVolumeTotal(forecastIfno.getVolumeTotal());
					}
					// 是否异常
					if (dto.getWeightGapTotal() != null && dto.getVolumeGapTotal() != null) {
						// 判空
						if (dto.getWeightGapTotal().compareTo(BigDecimal.ZERO) == 0
								&& dto.getVolumeGapTotal().compareTo(BigDecimal.ZERO) == 0) {
							// 设置异常标记
							dto.setIsIssue(TruckDepartPlanConstants.IS_ISSUE_NO);
						} else {
							// 设置异常标记
							dto.setIsIssue(TruckDepartPlanConstants.IS_ISSUE_YES);
						}
					} else {
						// 设置异常标记
						dto.setIsIssue(TruckDepartPlanConstants.IS_ISSUE_YES);
					}
				}
				// 短途发车计划 体积、重量、缺口计算
				else {
					// 查询货量预测的重量、体积
					if (forecastIfno != null) {
						// 转换对应的统计值
						// 预测体积(最新发车当日货量预测的体积)
						volumeTotal = forecastIfno.getVolumeTotal();
						// 预测重量(最新发车当日货量预测的重量)
						weightTotal = forecastIfno.getWeightTotal();
						// 载重合计(本线路发车当日发车计划所有车辆载重之和)
						preWeightTotal = BigDecimal.ZERO;
						// 判空
						if (dto.getPreWeightTotal() != null) {
							// 预计总重量
							preWeightTotal = dto.getPreWeightTotal();
						}
						// 体积合计(本线路发车当日发车计划所有车辆净空之和)
						preVolumeTotal = BigDecimal.ZERO;
						// 判空
						if (dto.getPreVolumeTotal() != null) {
							// 预计体积
							preVolumeTotal = dto.getPreVolumeTotal();
						}
						// 设置正确的值给前台
						// 预测重量(最新发车当日货量预测的重量)
						dto.setWeightTotal(weightTotal);
						// 预测体积(最新发车当日货量预测的体积)
						dto.setVolumeTotal(volumeTotal);
						// 体积合计(本线路发车当日发车计划所有车辆净空之和)
						dto.setPreVolumeTotal(preVolumeTotal);
						// 载重合计(本线路发车当日发车计划所有车辆载重之和)
						dto.setPreWeightTotal(preWeightTotal);
					}
					// 是否异常
					if (StringUtils.isNotBlank(dto.getIsIssue())) {
						// 按照用户调整的情况显示
					} else {
						// 重量差
						if (dto.getWeightGapTotal() != null && dto.getVolumeGapTotal() != null) {
							// 判空
							if (dto.getWeightGapTotal().compareTo(BigDecimal.ZERO) == 0
									&& dto.getVolumeGapTotal().compareTo(BigDecimal.ZERO) == 0) {
								// 异常标记
								dto.setIsIssue(TruckDepartPlanConstants.IS_ISSUE_NO);
							} else {
								// 异常标记
								dto.setIsIssue(TruckDepartPlanConstants.IS_ISSUE_YES);
							}
						} else {
							// 异常标记
							dto.setIsIssue(TruckDepartPlanConstants.IS_ISSUE_YES);
						}
					}
				}
			}
		}
		// 判断出发部门和到达部门是否已经被作废，如果已经被作废，则设为空。
		for (TruckDepartPlanDto dto : tempList) {
			OrgAdministrativeInfoEntity org1 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getOrigOrgCode());
			if(org1==null){
				dto.setOrigOrgCode("");
			}
			OrgAdministrativeInfoEntity org2 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getDestOrgCode());
			if(org2==null){
				dto.setDestOrgCode("");
			}
		}
		// 返回
		return tempList;
	}

	/**
	 * 
	 * 校验出发部门到达部门
	 * 
	 * @author 096598-foss-zhongyubing
	 
	 * @date 2012-12-28 下午1:59:23
	 * 
	 */
	private void validateOrigAndDestDepart(TruckDepartPlanDto truckDepartPlanDto) {
		// 判空
		if (truckDepartPlanDto != null) {
			// 出发部门到达部门不为空，检验是否存在此部门
			if (StringUtils.isNotBlank(truckDepartPlanDto.getOrigOrgCode())
					&& StringUtils.isNotBlank(truckDepartPlanDto.getDestOrgCode())) {
				// 出发部门不存在
				if (null == queryDepartment(truckDepartPlanDto.getOrigOrgCode())) {
					// 异常
					throw new TruckDepartPlanException(truckDepartPlanDto.getOrigOrgCode() + "出发部门不存在", "");
				}
				// 到达部门不存在
				if (null == queryDepartment(truckDepartPlanDto.getDestOrgCode())) {
					// 异常
					throw new TruckDepartPlanException(truckDepartPlanDto.getDestOrgCode() + "到达部门不存在", "");
				}
			}
		}
	}

	/**
	 * 
	 * 查询组织机构部门名称
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-11 上午9:47:24
	 * 
	 */
	private String queryOrgName(String orgCode) {
		// 查询部门
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		// 判空
		if (org != null) {
			// 返回
			return org.getName();
		} else {
			// 异常
			return "";
			//throw new TruckDepartPlanException("没有查询到本部门:" + orgCode, "");
		}
	}

	/**
	 * 
	 * 更新发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-21 下午7:04:08
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#updateTruckDepartPlan(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 * 
	 */
	@Override
	@Transactional
	public int updateTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto, CurrentInfo user)
			throws TruckDepartPlanException {
		// 更新
		truckDepartPlanDao.updateTruckDepartPlan(truckDepartPlanDto);
		// 返回
		return TruckDepartPlanConstants.SERVICE_TRANSACTION_SUCCESS;
	}

	/**
	 * 
	 * 
	 * 验证是否出发部门或者到达部门
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * @date 2012-11-27 上午8:47:44
	 * 
	 * 
	 */
	private boolean validateOrg(String orgCode) {
		// 初始化
		boolean flag = false;
		// 查询
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		// 不为空
		if (org != null) {
			// 不为空
			if (StringUtils.isNotBlank(org.getSalesDepartment())
					&& TruckDepartPlanConstants.IS_SALES_DEPARTMENT.equals(org.getSalesDepartment())) {
				// 真
				flag = true;
				// 不为空
			} else if (StringUtils.isNotBlank(org.getTransferCenter())
					&& TruckDepartPlanConstants.IS_TRANSFER_CENTER.equals(org.getTransferCenter())) {
				// 真
				flag = true;
			} else {
				// 假
				flag = false;
			}
		} else {
			// 假
			flag = false;
		}
		// 返回
		return flag;
	}

	/**
	 * 
	 * 通过司机Code查询司机信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-1 下午4:40:29
	 * 
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#queryDriverInfoByDriverCode()
	 * 
	 */
	@Override
	public DriverAssociationDto queryDriverInfoByDriverCode(String driverCode, String truckType)
			throws TruckDepartPlanException {
		// 司机对象
		DriverAssociationDto driver = null;
		// 不为空
		if (StringUtils.isNotBlank(driverCode)) {
			// 归属类型不为空
			if (StringUtils.isNotBlank(truckType)) {
				// 公司司机
				if (TruckDepartPlanConstants.TRUCK_TYPE_MYSELF_OWN.equals(truckType)) {
					// 执行公司司机查询
					if (StringUtils.isNotBlank(driverCode)) {
						// 司机信息
						driver = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(driverCode);
					}
				}
				// 外请司机
				else {
					// 司机
					LeasedDriverEntity leasedDriver = new LeasedDriverEntity();
					// 身份证
					leasedDriver.setIdCard(driverCode);
					// 查询结果
					LeasedDriverEntity outerDriver = leasedDriverService.queryLeasedDriverBySelective(leasedDriver);
					// 不空
					if (outerDriver != null) {
						// 创建司机
						driver = new DriverAssociationDto();
						// 电话
						driver.setDriverPhone(outerDriver.getDriverPhone());
						// 编码
						driver.setDriverCode(driverCode);
						// 姓名
						driver.setDriverName(outerDriver.getDriverName());
						// 身份证
						driver.setDriverIdCard(driverCode);
					}
				}
			} else {
				// 无 司机类型
				throw new TruckDepartPlanException("无司机归属类型", "");
			}
		} else {
			// 异常
			throw new TruckDepartPlanException("司机编码为空", "");
		}
		// 为空
		if (driver == null) {
			// 异常
			throw new TruckDepartPlanException(driverCode + "司机未查询到", "");
		}
		// 返回
		return driver;
	}

	/**
	 * 
	 * 查询发车计划总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-3 下午2:04:35
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#queryTruckDepartPlanTotal(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto,
	 *      int, int)
	 */
	@Override
	public Long queryTruckDepartPlanTotal(TruckDepartPlanDto truckDepartPlanDto) throws TruckDepartPlanException {
		// 查询计划总条数
		return truckDepartPlanDao.queryTruckDepartPlanTotal(truckDepartPlanDto);
	}

	/**
	 * 
	 * 查询合发记录
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-3 下午3:25:48
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#queryMergeLogs(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 * 
	 */
	@Override
	public List<MergeLogDto> queryMergeLogs(TruckDepartPlanDetailDto detailDto) {
		// 查询合发记录,造假数据
		List<MergeLogDto> mergeLogs = new ArrayList<MergeLogDto>();
		// 发车日期
		Date departDate = detailDto.getDepartDate();
		// 出发部门
		String origOrgCode = detailDto.getOrigOrgCode();
		// 到达部门
		String destorgCode = detailDto.getDestOrgCode();
		// 如果 发车日期 出发部门 到达部门 其中之一为空，则不继续转换数据
		if (StringUtils.isBlank(origOrgCode) || StringUtils.isBlank(destorgCode) || departDate == null) {
			// 日志
			LOGGER.info("查询合车记录时，出发部门 <或>到达部门<或>发车日期为空");
		} else {
			// 查询合入列表
			List<ChangeQuantityEntity> mergeInList = forecastService.queryChangeInByDate(departDate, origOrgCode,
					destorgCode);
			// 查询合出列表
			List<ChangeQuantityEntity> mergeOutList = forecastService.queryChangeOutByDate(departDate, origOrgCode,
					destorgCode);
			// 合并合入列表
			if (CollectionUtils.isNotEmpty(mergeInList)) {
				// 合并
				makeMergeList(mergeInList, TruckDepartPlanConstants.MERGE_TYPE_IN_DESC, mergeLogs);
			}
			// 合并合出列表
			if (CollectionUtils.isNotEmpty(mergeOutList)) {
				// 合并
				makeMergeList(mergeOutList, TruckDepartPlanConstants.MERGE_TYPE_OUT_DESC, mergeLogs);
			}
		}
		// 返回
		return mergeLogs;
	}

	/**
	 * 
	 * 查询部门信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-24 上午11:51:17
	 * 
	 * 
	 */
	private OrgAdministrativeInfoEntity queryDepartment(String code) {
		// 根据编码查询部门信息
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
		// 不空
		if (org != null) {
			// 返回
			return org;
			// 为空
		} else {
			// 返回
			return null;
		}
	}

	/**
	 * 
	 * 构建合发列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-12 上午11:52:43
	 * 
	 */
	private void makeMergeList(List<ChangeQuantityEntity> mergeList, String mergeType, List<MergeLogDto> mergeLogs) {
		// 调整描述
		StringBuffer operationDesc = null;
		// 合车记录对象
		MergeLogDto mergeLogDto = null;
		// 部门信息
		OrgAdministrativeInfoEntity depart = null;
		// 部门名
		String departName = "";
		// 循环
		for (ChangeQuantityEntity mergeEntity : mergeList) {
			// 容器
			operationDesc = new StringBuffer();
			// 日志
			mergeLogDto = new MergeLogDto();
			// 操作时间
			mergeLogDto.setOperationDate(mergeEntity.getChangeTime());
			// 操作人编码
			mergeLogDto.setOperatorCode(mergeEntity.getCreateUser());
			// 操作人
			mergeLogDto.setOperatorName(mergeEntity.getCreateUser());
			// 操作描述
			if (TruckDepartPlanConstants.MERGE_TYPE_IN_DESC.equals(mergeType)) {
				// 合入重量
				mergeLogDto.setMeringInWeightTotal(mergeEntity.getModifyWeight());
				// 由原目的地跳入本目的地
				depart = queryDepartment(mergeEntity.getOrigDestOrg());
				// 不空
				if (depart != null) {
					// 部门
					departName = depart.getName();
				}
				// 部门
				operationDesc.append(departName);
				// 类型
				operationDesc.append(mergeType);
				// 描述
				operationDesc.append(mergeEntity.getModifyWeight());
			} else {
				// 查询部门
				depart = queryDepartment(mergeEntity.getNewDestOrg());
				// 不空
				if (depart != null) {
					// 部门
					departName = depart.getName();
				}
				// 合出重量
				mergeLogDto.setMeringOutWeightTotal(mergeEntity.getModifyWeight());
				// 由原目的地跳出到新目的地
				operationDesc.append(departName);
				// 类型
				operationDesc.append(mergeType);
				// 描述
				operationDesc.append(mergeEntity.getModifyWeight());
			}
			// 描述
			operationDesc.append(TruckDepartPlanConstants.VOLUMN_UNIT_DESC);
			// 操作描述
			mergeLogDto.setOperationDesc(operationDesc.toString());
			// 加入列表
			mergeLogs.add(mergeLogDto);
		}
	}

	/**
	 * 
	 * 
	 * 查询货量预测信息
	 * 
	 * 要根据出发部门到达部门和发车日期查询
	 * 
	 * 当前时间最新的货量预测信息:
	 * 
	 * 
	 * 主要包括：出发部门、到达部门、预测时间、总货物体积、预测货物总重量。
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-10 上午11:30:20
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#queryForecastInfo(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@Override
	public ForecastForPlanDto queryForecastInfo(TruckDepartPlanDto truckDepartPlanDto) {
		// 货量预测信息
		ForecastForPlanDto forecastDto = null;
		// 不空
		if (truckDepartPlanDto != null) {
			// 查询条件
			ForecastQuantityEntity forecast = new ForecastQuantityEntity();
			// 配置查询条件
			makeForecastQuantityEntity(forecast, truckDepartPlanDto);
			// 执行查询货量信息
			List<ForecastQuantityEntity> forecastList = forecastQuantityDao.queryByStatistics(forecast);
			// 长途货量预测
			if (TruckDepartPlanConstants.PLAN_TYPE_LONG.equals(truckDepartPlanDto.getPlanType())) {
				// 不空
				if (CollectionUtils.isNotEmpty(forecastList)) {
					// 转换为货量预测dto
					forecastDto = convertLongForecastQuantity(forecastList.get(0));
				} else {
					// 预测对象
					forecastDto = new ForecastForPlanDto();
					// 创建没有货量预测时候的数据
					makeNoneForecastDto(forecastDto, truckDepartPlanDto);
				}
			}
			// 短途货量预测
			else {
				if (CollectionUtils.isNotEmpty(forecastList)) {
					// 转换为货量预测dto
					forecastDto = convertShortForecastQuantity(forecastList.get(0));
				} else {
					// 预测对象
					forecastDto = new ForecastForPlanDto();
					// 创建没有货量预测时候的数据
					makeNoneForecastDto(forecastDto, truckDepartPlanDto);
				}
				// 短途发车计划且ID如果不为空，则需要查询 异常标记及备注
				if (StringUtils.isNotBlank(truckDepartPlanDto.getId())) {
					// 计划对象
					TruckDepartPlanDto tempPlanDto = truckDepartPlanDao.queryTruckDepartPlanById(truckDepartPlanDto);
					// 不空
					if (tempPlanDto != null) {
						// 发车计划备注信息
						forecastDto.setNotes(tempPlanDto.getNotes());
						// 异常标记
						forecastDto.setIsIssue(tempPlanDto.getIsIssue());
					}
				}
			}
		} else {
			// 异常
			throw new TruckDepartPlanException("查询条件为空", "");
		}
		// 补充出发部门名和到达部门名
		if (forecastDto != null) {
			// 补充出发部门名和到达部门名
			makeOrgNames(truckDepartPlanDto, forecastDto);
		}
		// 返回
		return forecastDto;
	}

	/**
	 * 
	 * 补充出发部门名和到达部门名
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-25 上午11:56:05
	 * 
	 */
	private void makeOrgNames(TruckDepartPlanDto truckDepartPlanDto, ForecastForPlanDto forecastDto) {
		if (forecastDto != null) {
			// 部门名称不为空
			if (truckDepartPlanDto != null) {
				// 不空
				if (StringUtils.isNotBlank(truckDepartPlanDto.getOrigOrgName())
						&& StringUtils.isNotBlank(truckDepartPlanDto.getDestOrgName())) {
					// 出发部门名称
					forecastDto.setOrigOrgName(truckDepartPlanDto.getOrigOrgName());
					// 到达部门名称
					forecastDto.setDestOrgName(truckDepartPlanDto.getDestOrgName());
				}
				// 根据编码查询部门名称
				else {
					// 出发部门名称
					forecastDto.setOrigOrgName(this.queryOrgName(truckDepartPlanDto.getOrigOrgCode()));
					// 到达部门名称
					forecastDto.setDestOrgName(this.queryOrgName(truckDepartPlanDto.getDestOrgCode()));
				}
			}
		}
	}

	/**
	 * 
	 * 创建没有货量预测时候的数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-18 下午7:31:48
	 * 
	 */
	private void makeNoneForecastDto(ForecastForPlanDto forecastDto, TruckDepartPlanDto truckDepartPlanDto) {
		// 出发部门编码
		forecastDto.setOrigOrgCode(truckDepartPlanDto.getOrigOrgCode());
		// 到达部门编码
		forecastDto.setDestOrgCode(truckDepartPlanDto.getDestOrgCode());
		// 计划日期
		forecastDto.setDepartDate(truckDepartPlanDto.getPlanDate());
		// 用于短途
		// 预测货物体积
		forecastDto.setForecastVolumeTotal(BigDecimal.ZERO);
		// 预测货物重量
		forecastDto.setForecastWeightTotal(BigDecimal.ZERO);
		// 用于长途
		// 合发体积
		forecastDto.setDeviationVolume(BigDecimal.ZERO);
		// 总重量
		forecastDto.setWeightTotal(BigDecimal.ZERO);
		// 总体积
		forecastDto.setVolumeTotal(BigDecimal.ZERO);
	}

	/**
	 * 
	 * 短途货量预测转换
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-18 下午7:23:15
	 * 
	 */
	private ForecastForPlanDto convertShortForecastQuantity(ForecastQuantityEntity forecastEntity) {
		// 预测对象
		ForecastForPlanDto forecastDto = new ForecastForPlanDto();
		// / 出发部门编码
		forecastDto.setOrigOrgCode(forecastEntity.getBelongOrgCode());
		// 到达部门编码
		forecastDto.setDestOrgCode(forecastEntity.getRelevantOrgCode());
		// 计划日期
		forecastDto.setDepartDate(forecastEntity.getForecastTime());
		// 预测货物体积
		forecastDto.setForecastVolumeTotal(forecastEntity.getVolumeTotal());
		// 总体积
		forecastDto.setVolumeTotal(forecastEntity.getVolumeTotal());
		// 预测货物重量
		forecastDto.setForecastWeightTotal(forecastEntity.getWeightTotal());
		// 总重量
		forecastDto.setWeightTotal(forecastEntity.getWeightTotal());
		// 预测时间
		forecastDto.setForecastTime(forecastEntity.getForecastTime());
		// 返回
		return forecastDto;
	}

	/**
	 * 
	 * 配置查询条件并验证必要的查询条件
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-12-10 下午3:39:00
	 * 
	 */
	private void makeForecastQuantityEntity(ForecastQuantityEntity forecast, TruckDepartPlanDto truckDepartPlanDto) {
		if (truckDepartPlanDto != null && forecast != null) {
			// 类型为-出发
			forecast.setType(ForecastConstants.FORECAST_DEPART);
			// 出发部门
			if (StringUtils.isNotBlank(truckDepartPlanDto.getOrigOrgCode())) {
				// 出发部门
				forecast.setBelongOrgCode(truckDepartPlanDto.getOrigOrgCode());
			} else {
				// 异常
				throw new TruckDepartPlanException("出发部门为空", "");
			}
			// 到达部门
			if (StringUtils.isNotBlank(truckDepartPlanDto.getDestOrgCode())) {
				// 异常
				forecast.setRelevantOrgCode(truckDepartPlanDto.getDestOrgCode());
			} else {
				// 异常
				throw new TruckDepartPlanException("到达部门为空", "");
			}
			// 日期
			if (truckDepartPlanDto.getPlanDate() != null) {
				// 日期
				forecast.setForecastTime(truckDepartPlanDto.getPlanDate());
			} else {
				// 异常
				throw new TruckDepartPlanException("计划日期为空", "");
			}
		}

	}

	/**
	 * 将货量预测实体信息转换为Dto供显示
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-10 上午11:33:44
	 */
	private ForecastForPlanDto convertLongForecastQuantity(ForecastQuantityEntity forecastEntity) {

		// 货量预测信息
		ForecastForPlanDto forecastDto = new ForecastForPlanDto();

		// 转换货量预测信息
		if (forecastEntity != null) {

			// / 出发部门编码
			forecastDto.setOrigOrgCode(forecastEntity.getBelongOrgCode());

			// 到达部门编码
			forecastDto.setDestOrgCode(forecastEntity.getRelevantOrgCode());

			// 计划日期
			forecastDto.setDepartDate(forecastEntity.getForecastTime());

			// 所有的体积、重量保持两位有效数字精度
			// 卡货重量/体积/票数
			StringBuffer descTotal = new StringBuffer();
			descTotal
					.append(forecastEntity.getGpsEnabledResWeightTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getGpsEnabledResWeightTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
									RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getGpsEnabledResVolumeTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getGpsEnabledResVolumeTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
									RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getGpsEnabledResQtyTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getGpsEnabledResQtyTotal());
			forecastDto.setTruckInfo(descTotal.toString());

			// 城货重量/体积/票数
			descTotal = new StringBuffer();
			descTotal
					.append(forecastEntity.getPrecisionIfsWeightTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getPrecisionIfsWeightTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
									RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getPrecisionIfsVolumeTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getPrecisionIfsVolumeTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
									RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getPrecisionIfsQtyTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getPrecisionIfsVolumeTotal());
			forecastDto.setIntercityInfo(descTotal.toString());

			// 开单未交接重量/体积/票数
			descTotal = new StringBuffer();
			descTotal
					.append(forecastEntity.getBillingWeightTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getBillingWeightTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
									RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getBillingVolumeTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getBillingVolumeTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
									RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getBillingQtyTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getBillingQtyTotal());
			forecastDto.setNoneTransferOfBilling(descTotal.toString());

			// 余货重量/体积/票数
			descTotal = new StringBuffer();
			descTotal
					.append(forecastEntity.getInventoryWeightTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getInventoryWeightTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
									RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getInventoryVolumeTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getInventoryVolumeTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
									RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getInventoryQtyTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getInventoryQtyTotal());
			forecastDto.setLastGoodsInfo(descTotal.toString());

			// 预计到达重量/体积/票数
			descTotal = new StringBuffer();
			descTotal
					.append(forecastEntity.getIntransitWeightTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getIntransitWeightTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
									RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getIntransitVolumeTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getIntransitVolumeTotal().setScale(TruckDepartPlanConstants.SCALE_NUM,
									RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getIntransitQtyTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getIntransitQtyTotal());
			forecastDto.setExceptArrivalInfo(descTotal.toString());

			// 总重量/体积/票数
			descTotal = new StringBuffer();
			descTotal
					.append(forecastEntity.getWeightTotal() == null ? TruckDepartPlanConstants.EMPTY : forecastEntity
							.getWeightTotal().setScale(TruckDepartPlanConstants.SCALE_NUM, RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getVolumeTotal() == null ? TruckDepartPlanConstants.EMPTY : forecastEntity
							.getVolumeTotal().setScale(TruckDepartPlanConstants.SCALE_NUM, RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getWaybillQtyTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getWaybillQtyTotal());
			forecastDto.setTotalInfo(descTotal.toString());

			// (小计)合发重量/体积/票数
			descTotal = new StringBuffer();
			descTotal
					.append(TruckDepartPlanConstants.EMPTY)
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getDeviationVolume() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getDeviationVolume().setScale(TruckDepartPlanConstants.SCALE_NUM,
									RoundingMode.HALF_UP)).append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(TruckDepartPlanConstants.EMPTY);
			forecastDto.setMergerInfo(descTotal.toString());

			// (汇总)重量/体积/票数
			descTotal = new StringBuffer();
			// 默认为0
			BigDecimal volumeTotal = BigDecimal.ZERO;
			if (forecastEntity.getVolumeTotal() != null) {
				volumeTotal = forecastEntity.getVolumeTotal();
				// 总体积+合发体积
				if (forecastEntity.getDeviationVolume() != null) {
					volumeTotal = volumeTotal.add(forecastEntity.getDeviationVolume());
				}
			} else {
				if (forecastEntity.getDeviationVolume() != null) {
					volumeTotal = forecastEntity.getDeviationVolume();
				}
			}
			descTotal
					.append(forecastEntity.getWeightTotal() == null ? TruckDepartPlanConstants.EMPTY : forecastEntity
							.getWeightTotal().setScale(TruckDepartPlanConstants.SCALE_NUM, RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(volumeTotal.setScale(TruckDepartPlanConstants.SCALE_NUM, RoundingMode.HALF_UP))
					.append(TruckDepartPlanConstants.QUANTITY_DESC_SEPERATOR)
					.append(forecastEntity.getWaybillQtyTotal() == null ? TruckDepartPlanConstants.EMPTY
							: forecastEntity.getWaybillQtyTotal());
			forecastDto.setAllTotalGoodsInfo(descTotal.toString());

			// 货量预测时间
			forecastDto.setForecastTime(forecastEntity.getStatisticsTime());

			// 总重量
			if (forecastEntity.getWeightTotal() != null) {
				forecastDto.setWeightTotal(forecastEntity.getWeightTotal());
			} else {
				forecastDto.setWeightTotal(BigDecimal.ZERO);
			}

			// 总体积
			forecastDto.setVolumeTotal(volumeTotal);

			// 合发体积
			forecastDto.setDeviationVolume(forecastEntity.getDeviationVolume());

		}
		return forecastDto;

	}

	/**
	 * 更新保存备注及异常标记
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-19 下午1:53:58
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#updatePlanRemark(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	@Transactional
	public void updatePlanRemark(TruckDepartPlanDto planDto, CurrentInfo user) {
		if (planDto != null && user != null) {
			// 更新时间及操作人信息
			planDto.setUpdateOrgCode(user.getCurrentDeptCode());
			// 更新人
			planDto.setUpdateTime(Calendar.getInstance().getTime());
			// 更新人编码
			planDto.setUpdateUserCode(user.getEmpCode());
			// 更新人姓名
			planDto.setUpdateUserName(user.getEmpName());
			// 执行保存更新
			truckDepartPlanDao.updatePlanRemark(planDto);
		}

	}

	/**
	 * 获取当前部门对应的外场
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-12 下午4:58:59
	 */
	@Override
	public OrgAdministrativeInfoEntity queryTransferCenter(String deptCode) {
		// 外场信息
		OrgAdministrativeInfoEntity org = null;
		// 首先查看是否已经是外场
		OrgAdministrativeInfoEntity transferCenterOrg = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(deptCode);
		// 如果是外场
		if (transferCenterOrg != null
				&& TruckDepartPlanConstants.TRANSFER_CENTER_Y.equals(transferCenterOrg.getTransferCenter())) {
			return transferCenterOrg;
		}
		// 如果是车队

		try {
			// 查询车队
			MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(deptCode);
			// 查询外场
			if (motorcade != null && StringUtils.isNotBlank(motorcade.getTransferCenter())) {
				org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(motorcade.getTransferCenter());
			}
		} catch (Exception e) {
			LOGGER.info("获取部门对应的外场出错，错误信息:" + e.getMessage());
			throw new TruckDepartPlanException("获取部门对应的外场出错，错误信息:", e);
		}

		return org;
	}

	/**
	 * 获取车队
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-15 上午10:31:22
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#queryTransDepartment(java.lang.String)
	 */
	@Override
	public OrgAdministrativeInfoEntity queryTransDepartment(String deptCode) {
		// 车队
		OrgAdministrativeInfoEntity org = null;
		// 首先查看是否已经是外场
		org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
		if (org != null && TruckDepartPlanConstants.TRANS_DEPARTMENT_Y.equals(org.getTransDepartment())) {
			return org;
		} else {
			return null;
		}

	}

	/**
	 * 改变车辆归属类型
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-15 下午7:28:06
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#changeTruckType(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	@Transactional
	public void changeTruckType(TruckDepartPlanDetailDto detailDto) {
		if (detailDto != null && StringUtils.isNotBlank(detailDto.getId())) {
			// 计划明细
			TruckDepartPlanDetailDto planDetail = truckDepartPlanDetailDao.queryTruckDepartPlanDetailById(detailDto);
			if (planDetail != null) {
				// 归属类型
				planDetail.setTruckType(detailDto.getTruckType());
				// 司机1
				planDetail.setDriverCode1(null);
				planDetail.setDriverName1(null);
				planDetail.setDriverPhone1(null);
				// 司机2
				planDetail.setDriverCode2(null);
				planDetail.setDriverName2(null);
				planDetail.setDriverPhone2(null);
				// 车型
				planDetail.setTruckModel(null);
				// 车牌
				planDetail.setVehicleNo(null);
				// 车辆净空
				planDetail.setTruckVolume(null);
				// 预计装载体积
				planDetail.setPlanLoadVolume(null);
				// 预计装载载重
				planDetail.setPlanLoadWeight(null);
				// 实际最大载重
				planDetail.setActualMaxLoadWeight(null);
				// 车辆载重
				planDetail.setMaxLoadWeight(null);
				// 货柜号
				planDetail.setContainerNo(null);
				// 执行改变车辆归属类型
				truckDepartPlanDetailDao.changePlanDetailTruckType(planDetail);
			}

		}
	}

	/**
	 * 外请司机查询外请车
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-16 下午3:07:30
	 */
	@Override
	public void queryLeasedDriverVechile(TruckDepartPlanDetailDto detailDto) {
		// 外请司机
		LeasedDriverEntity leasedDriverParams = new LeasedDriverEntity();
		// 将司机ID座位查询条件
		if (detailDto != null) {
			// 司机身份证
			leasedDriverParams.setIdCard(detailDto.getDriverCode1());
			// 可用
			leasedDriverParams.setActive(FossConstants.ACTIVE);
			LOGGER.info("通过外请司机查询车牌,身份证:" + detailDto.getDriverCode1() + "," + FossConstants.ACTIVE);
			// 查询外请司机
			LeasedDriverEntity leasedDriver = leasedDriverService.queryLeasedDriverBySelective(leasedDriverParams);
			// 查询结果不为空
			if (leasedDriver != null) {
				// 日志
				LOGGER.info("通过外请司机查询车牌,返回车牌:" + leasedDriver.getVehicleNo());
				// 将车牌号码返回
				detailDto.setVehicleNo(leasedDriver.getVehicleNo());
			}
		}

	}

	/**
	 * 
	 * 根据
	 * 
	 * 状态status为Y
	 * 
	 * 线路编号lineNo
	 * 
	 * 发车日期departDate
	 * 
	 * 出发地 origOrgCode
	 * 
	 * 目的地destOrgCode
	 * 
	 * 计划类型planType
	 * 
	 * 查询当前最大的下一个班次号
	 * 
	 * 包括停发班次的
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2013-3-20 上午11:03:35
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#queryMaxfrequencyNo(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public Integer queryMaxfrequencyNo(TruckDepartPlanDetailDto detailDto) {
		// 判空
		if (detailDto != null) {
			// 日期
			if (null == detailDto.getDepartDate()) {
				// 日志
				LOGGER.info("日期为空");
				// 异常信息
				throw new TruckDepartPlanException("日期为空", "");
			}
			// 线路编码
			if (StringUtils.isEmpty(detailDto.getLineNo())) {
				// 日志
				LOGGER.info("线路编码为空");
				// 异常信息
				throw new TruckDepartPlanException("线路编码为空", "");
			}
			// 出发部门
			if (StringUtils.isEmpty(detailDto.getLineNo())) {
				// 日志
				LOGGER.info("出发部门为空");
				// 异常信息
				throw new TruckDepartPlanException("出发部门为空", "");
			}
			// 到达部门
			if (StringUtils.isEmpty(detailDto.getLineNo())) {
				// 日志
				LOGGER.info("到达部门为空");
				// 异常信息
				throw new TruckDepartPlanException("到达部门为空", "");
			}
		}
		// 查询最大的班次号
		// 返回
		return truckDepartPlanDao.queryMaxfrequencyNo(detailDto);
	}

	/**
	 * 导出发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-26 下午2:41:47
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#exportExcel(com.deppon.foss.module.transfer.scheduling.api.shared.vo.TruckDepartPlanVo)
	 */
	@Override
	public InputStream exportExcel(TruckDepartPlanVo vo, String name) {
		InputStream excelStream = null;
		// 定义表头
		String[] rowHeads = TruckDepartPlanConstants.EXCEL_HEADER_ROWS;
		if (TruckDepartPlanConstants.PLAN_TYPE_LONG.equals(vo.getPlanDto().getPlanType())) {
			rowHeads=TruckDepartPlanConstants.EXCEL_LONG_HEADER_ROWS;
		}
		// 标题数据
		SheetData sheetData = new SheetData();
		// 头数据
		sheetData.setRowHeads(rowHeads);
		// 查询出的所有数据
		List<List<String>> rowList = new ArrayList<List<String>>();
		// 查询出需要导出的排班计划和任务
		List<TruckDepartPlanDto> plans = truckDepartPlanDao.queryTruckDepartPlanForExport(vo.getPlanDto());
		// 循环转换
		List<String> columnList = null;
		// 判空
		if (CollectionUtils.isNotEmpty(plans)) {
			// 明细列表
			List<TruckDepartPlanDetailDto> tempList = null;
			// 查询条件
			TruckDepartPlanDetailDto detailDto = null;
			// 循环
			for (TruckDepartPlanDto planDto : plans) {
				// 明细对象
				detailDto = new TruckDepartPlanDetailDto();
				// 计划类型
				detailDto.setPlanType(planDto.getPlanType());
				// 计划ID
				detailDto.setTruckDepartPlanId(planDto.getId());
				// 查询明细
				tempList = truckDepartPlanDetailDao.queryTruckDepartPlanDetailBylimitForExport(detailDto);
				if (CollectionUtils.isNotEmpty(tempList)) {
					for (TruckDepartPlanDetailDto tmpDetail : tempList) {
						columnList = new ArrayList<String>();
						// 出发部门
						columnList.add(tmpDetail.getOrigOrgCode());
						// 到达部门
						columnList.add(tmpDetail.getDestOrgCode());
						// 发车日期
						columnList.add(DateUtils.convert(tmpDetail.getDepartDate(), DateUtils.DATE_FORMAT));
						// 线路
						columnList.add(tmpDetail.getLineNo());
						// 发车时间
						columnList.add(DateUtils.convert(tmpDetail.getPlanDepartTime(), DateUtils.DATE_TIME_FORMAT));
						// 班次
						columnList.add(tmpDetail.getFrequencyNo());
						// 正班车
						columnList.add(tmpDetail.getIsOnScheduling());
						// 班次类型
						columnList.add(tmpDetail.getFrequencyType());
						// 司机1
						columnList.add(tmpDetail.getDriverName1());
						// 联系方式1
						columnList.add(tmpDetail.getDriverPhone1());
						// 司机2
						columnList.add(tmpDetail.getDriverName2());
						// 联系方式2
						columnList.add(tmpDetail.getDriverPhone2());
						// 车牌号
						columnList.add(tmpDetail.getVehicleNo());
						// 车型
						columnList.add(tmpDetail.getTruckModel());
						// 归属类型
						columnList.add(tmpDetail.getTruckType());
						// 月台号
						columnList.add(tmpDetail.getPlatformNo());
						// 货柜号
						columnList.add(tmpDetail.getContainerNo());
						//挂牌号
						if (TruckDepartPlanConstants.PLAN_TYPE_LONG.equals(vo.getPlanDto().getPlanType())) {
							columnList.add(tmpDetail.getTrailerVehicleNo());
						}
						// 车队
						columnList.add(tmpDetail.getLongCarGroup());
						// 创建人
						columnList.add(tmpDetail.getCreateUserName());
						// 创建时间
						columnList.add(DateUtils.convert(tmpDetail.getCreateTime(), DateUtils.DATE_TIME_FORMAT));
						// 计划状态
						if (TruckDepartPlanConstants.LEFT_FLAG_Y.equals(tmpDetail.getHasLeft())) {
							columnList.add(TruckDepartPlanConstants.LEFT_FLAG_Y_DESC);
						} else {
							columnList.add(tmpDetail.getPlanStatus());
						}
						// 加入一行发车明细
						rowList.add(columnList);
					}
				}
			}
		}
		sheetData.setRowList(rowList);
		// 导出工具
		ExcelExport excelExportUtil = new ExcelExport();
		// 导出成文件
		excelStream = excelExportUtil.inputToClient(excelExportUtil.exportExcel(sheetData, name,
				ScheduleConstants.EXCEL_DEFAULT_SHEET_SIZE));
		// 返回
		return excelStream;
	}

	/**
	 * 外请车通过车牌查询司机
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-2 下午2:15:12
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#queryDriverInfoByVechileNo(com.deppon.foss.module.transfer.scheduling.api.shared.vo.TruckDepartPlanVo)
	 */
	@Override
	public DriverAssociationDto queryDriverInfoByVechileNo(TruckDepartPlanVo vo) {
		DriverAssociationDto driver = null;
		if (vo != null && vo.getDetailDto() != null) {
			// 外请车牌查询司机
			LeasedTruckEntity leasedVehicle = leasedVehicleService.queryLeasedVehicleByVehicleNo(vo.getDetailDto()
					.getVehicleNo());
			if (leasedVehicle != null && StringUtils.isNotBlank(leasedVehicle.getLeasedDriverIdCard())) {
				// 查询司机姓名
				// 司机
				LeasedDriverEntity leasedDriver = new LeasedDriverEntity();
				// 身份证
				leasedDriver.setIdCard(leasedVehicle.getLeasedDriverIdCard());
				// 查询结果
				LeasedDriverEntity outerDriver = leasedDriverService.queryLeasedDriverBySelective(leasedDriver);
				// 不空
				if (outerDriver != null) {
					driver = new DriverAssociationDto();
					// 创建司机
					driver = new DriverAssociationDto();
					// 电话
					driver.setDriverPhone(outerDriver.getDriverPhone());
					// 编码
					driver.setDriverCode(leasedVehicle.getLeasedDriverIdCard());
					// 姓名
					driver.setDriverName(outerDriver.getDriverName());
					// 身份证
					driver.setDriverIdCard(leasedVehicle.getLeasedDriverIdCard());
				}
			}
		}
		return driver;
	}

	/**
	 * 出发部门、到达部门查询月台号
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-2 下午5:29:34
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#queryPlatformNo(com.deppon.foss.module.transfer.scheduling.api.shared.vo.TruckDepartPlanVo)
	 */
	@Override
	public TruckDepartPlanDetailDto queryPlatformNo(TruckDepartPlanVo vo) {
		TruckDepartPlanDetailDto platform = null;
		if (vo != null && vo.getDetailDto() != null && StringUtils.isNotBlank(vo.getDetailDto().getOrigOrgCode())
				&& StringUtils.isNotBlank(vo.getDetailDto().getDestOrgCode())) {
			PlatformEntity tmpPlatform = platformService.queryMinDistancePlatform(vo.getDetailDto().getOrigOrgCode(),
					vo.getDetailDto().getDestOrgCode(), null);
			if (tmpPlatform != null) {
				// 月台信息
				platform = new TruckDepartPlanDetailDto();
				// 虚拟编码
				platform.setPlatformNo(tmpPlatform.getVirtualCode());
				// 月台号
				platform.setPlatformNoCode(tmpPlatform.getPlatformCode());
			}
		}
		return platform;
	}

	/**
	 * 批量生成发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-7 上午9:52:03
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService#batchCreateDepartPlan(com.deppon.foss.module.transfer.scheduling.api.shared.vo.TruckDepartPlanVo)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void batchCreateDepartPlan(TruckDepartPlanVo vo, CurrentInfo user) {
		// 判空
		if (vo != null && vo.getPlanDto() != null) {
			// 出发部门为空
			if (StringUtils.isBlank(vo.getPlanDto().getOrigOrgCode())) {
				throw new TruckDepartPlanException("出发部门为空", "");
			}
			// 发车计划类型为空
			if (StringUtils.isBlank(vo.getPlanDto().getPlanType())) {
				throw new TruckDepartPlanException("发车计划类型为空", "");
			}
			// 计划日期为空
			if (null == vo.getPlanDto().getPlanDate()) {
				throw new TruckDepartPlanException("计划日期为空", "");
			}
			// 发车计划列表
			List<TruckDepartPlanDto> planList = queryPlanList(vo.getPlanDto());
			// 根据出发到达列表批量生成发车计划
			if (CollectionUtils.isNotEmpty(planList)) {
				LOGGER.info("批量生成发车计划");
				for (TruckDepartPlanDto tempDto : planList) {
					// 校验到达部门是否已经被作废，如果作废的话，则不添加发车计划。
					OrgAdministrativeInfoEntity org1 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(tempDto.getDestOrgCode());
					if(org1!=null){
						LOGGER.info("生成出发部门:" + tempDto.getOrigOrgCode() + "到达部门:" + tempDto.getDestOrgCode() + "日期:"
								+ DateUtils.convert(tempDto.getPlanDate(), DateUtils.DATE_FORMAT));
						// 新增出发部门到达部门发车计划
						addTruckDepartPlan(tempDto, user);
					}

				}
			}
		}
	}

	/**
	 * 查询到达部门
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-18 上午9:42:46
	 */
	private List<TruckDepartPlanDto> queryPlanList(TruckDepartPlanDto planDto) {
		// 发车计划列表
		List<TruckDepartPlanDto> planList = new ArrayList<TruckDepartPlanDto>();
		if (planDto != null) {
			// 出发部门查询到达部门
			// 到达部门列表
			List<String> destList = queryDestList(planDto);// lineService.queryArriveOrgListBySource(vo.getPlanDto().getOrigOrgCode());
			// 验证出发到达部门是否已经存在发车计划
			TruckDepartPlanDto tmpPlanDto = null;
			// 判空
			if (CollectionUtils.isNotEmpty(destList)) {
				for (String destCode : destList) {
					if (StringUtils.isNotBlank(destCode)) {
						tmpPlanDto = new TruckDepartPlanDto();
						// 可用
						tmpPlanDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
						// 发车日期
						tmpPlanDto.setPlanDate(planDto.getPlanDate());
						// 出发部门
						tmpPlanDto.setOrigOrgCode(planDto.getOrigOrgCode());
						// 到达部门
						tmpPlanDto.setDestOrgCode(destCode);
						// 发车计划类型
						tmpPlanDto.setPlanType(planDto.getPlanType());
						planList.add(tmpPlanDto);
					}
				}
			}
		}
		return planList;
	}

	/**
	 * 查询到达部门
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-18 上午9:44:59
	 */
	private List<String> queryDestList(TruckDepartPlanDto planDto) {
		List<String> destList = null;
		// 判空
		if (planDto != null) {
			// 长途
			if (TruckDepartPlanConstants.PLAN_TYPE_LONG.equals(planDto.getPlanType())) {
				// 查询到达部门列表
				destList = lineService.queryArriveOrgListBySource(planDto.getOrigOrgCode());
				//ECS系统接口开关
				if (configurationParamsService.queryTfrSwitch4Ecs()) {
					Log.error("调用CES系统接口：getExpressArriveDepartList");
					// 调用综合接口
					List<String> expressDestList = getExpressArriveDepartList(planDto.getOrigOrgCode(), TruckDepartPlanConstants.PLAN_TYPE_LONG);
					
					if(CollectionUtils.isEmpty(destList) && CollectionUtils.isNotEmpty(expressDestList)){
						return expressDestList;
					}
					//如果快递的到达部门列表是空，返回零担的
					if (CollectionUtils.isEmpty(expressDestList)) {
						return destList;
					}
					//合并快递和零担的到达部门列表，去除重复的。
					destList.removeAll(expressDestList);
					destList.addAll(expressDestList);
				} else {
					if(destList.isEmpty()){
						// 调用综合接口
						Log.error("调用FOSS系统接口：expresslineService");
						destList = expresslineService.queryArriveOrgListBySource(planDto.getOrigOrgCode());
					}
				}
				
			} else if (TruckDepartPlanConstants.PLAN_TYPE_SHORT.equals(planDto.getPlanType())) {
				// 查询短途到达部门
				List<NetGroupSiteDto> tempList = lineService
						.queryTargetLineListByTransferCode(planDto.getOrigOrgCode());
				
				// 调用ECS系统接口开关
				if (configurationParamsService.queryTfrSwitch4Ecs()) {
					// 转换抽取到达部门编码
					destList = convertDestList(tempList);
					// 调用综合接口
					List<String> expressDestList = getExpressArriveDepartList(planDto.getOrigOrgCode(), TruckDepartPlanConstants.PLAN_TYPE_SHORT);

					if(CollectionUtils.isEmpty(destList) && CollectionUtils.isNotEmpty(expressDestList)){
						return expressDestList;
					}
					//如果快递的到达部门列表是空，返回零担的
					if (CollectionUtils.isEmpty(expressDestList)) {
						return destList;
					}
					
					//合并快递和零担的到达部门列表，去除重复的。
					destList.removeAll(expressDestList);
					destList.addAll(expressDestList);
				} else {
					if(tempList.isEmpty()){
						tempList = expresslineService
								.queryTargetLineListByTransferCode(planDto.getOrigOrgCode());
					}
					// 转换抽取到达部门编码
					destList = convertDestList(tempList);
				}
			}
		}
		return destList;
	}

	/**
	 * 转换抽取到达部门编码
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-18 上午9:51:00
	 */
	private List<String> convertDestList(List<NetGroupSiteDto> tempList) {
		List<String> destList = null;
		if (CollectionUtils.isNotEmpty(tempList)) {
			// 构建返回容器
			destList = new ArrayList<String>();
			// 循环
			for (NetGroupSiteDto tmpDto : tempList) {
				destList.add(tmpDto.getCode());
			}
		}
		return destList;
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
			}
		
		} catch (Exception e) {
			LOGGER.error("获取悟空系统信息异常", e.getMessage());
			return resultList;
		}
		return resultList;
	}
	
	/**
	 * 
	* @description FOSS调用悟空接口的中间方法
	* @param origOrgCode 出发部门编码
	* @param planType 计划类型（长途， 短途）
	* @return 到达部门列表
	* @version 1.0
	* @author 332209-foss-ruilibao
	* @update 2016-5-12 下午5:33:22
	 */
	private List<String> getExpressArriveDepartList(String origOrgCode, String planType) {

		// 返回快递到达部门列表
		List<String> resultList = null;
		try {
			// 调用获取快递线路Service
			FossToWKDepartCodeEntity  entity = fossToWkService.getExpressArriveDepartListFromWk(origOrgCode, planType);
			// 返回的实体转换为的线路(有可能是半截)的发车标准DTO LIST
			if (entity != null && "100".equals(entity.getStatusCode())) {
				resultList = entity.getDestinationList();
				return resultList;
			} else {
				LOGGER.error("获取悟空系统信息异常" + entity == null ? "" : entity.getErrMsg());
			}
		} catch (Exception e) {
			LOGGER.error("获取悟空系统信息异常", e.getMessage());
			return resultList;
		}
		return resultList;
	}
}