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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/TruckDepartPlanUpdateService.java
 * 
 *  FILE NAME     :TruckDepartPlanUpdateService.java
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
 * FILE    NAME: TruckDepartPlanUpdateService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanUpdateDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanUpdateService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckDepartPlanUpdateEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.TruckDepartPlanException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.CompareUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 日志service实现
 * 
 * 
 * *************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 *  * 长途制定发车计划需求：
 *  
 * 
 * 
 * 首次录入某预测周期的发车计划时，根据出发部门、到达部门、
 * 
 * 
 * 
 * 发车日期带出所有相关线路的固定班次（综合管理的发车时效表），
 * 
 * 
 * 
 * 在可用线路班次列表表格里按照班次顺序显示，且线路经过本计划
 * 
 * 
 * 
 * 的出发部门的线路车辆信息需要排前面，用户可修改和完善带出的
 * 
 * 
 * 
 * 固定班次的各项值。
 * 
 * 
 * 
 *	图（1）界面是制定长途发车计划主界面。主要分为三部分：实时货
 *
 *
 *
 *量信息、长途发车计划列表(包含实时运力统计)。
 *
 *
 *
 *		首次录入出发部门和到达部门某预测周期的发车计划时，根据线
 *
 *
 *
 *路带出该出发部门和到达部门的固定班次，在表格里按照班次顺序显示，
 *
 *
 *
 *且在上方显示最新货量预测信息、合发记录、以及表体右下方显示运力
 *
 *
 *
 *实时统计信息，提供新增公司车、新增外请车、下发、删除、编辑、
  ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 *复制短信模板：
 *
 *
 *
 *		1.	新增公司车：弹出图（3）的窗口，用于新增公司车作为发车计划。
 *
 *
 *
 *		2.	新增外请车：弹出图（2）的窗口，用于新增外请车作为发车计划。
 *
 *
 *
 *		3.	删除：在长途发车计划列表中勾选好需要删除的发车计划后，点击
 *
 *
 *
 *删除进行删除操作。
 *
 *
 *
 *		4.	下发：计划列好后，将选择新建状态的计划，点击下发按钮，使长
 *
 *
 *
 *途车队调度、班车调度可以看见相应的发车计划。
 *
 *
 *
 *		5.	编辑：点击编辑按钮，根据发车计划的车辆归属进行判断，如果是
 *
 *
 *
 *公司车，则弹出图（3）界面进行修改；如果是外请车，则弹出图（2）界面进行修改。
 *
 *
 *
 *		6.	复制短信模板：主要是用于将发车计划中的车辆信息复制，系统后台
 *
 *
 *
 *根据信息模板组织成车辆发车计划信息，弹出一个发车计划的短信息框便于复制使用。
 *
 *
 *
 *		
 *		图（2）界面是新增/编辑外请车发车计划界面，主要是针对在车辆发车不能满
 *
 *
 *
 *足货量的情况且无公司车的情况下，
 *
 *
 *
 *需要新增/修改外请车作为发车计划，主要是通过新增外请车按钮或点击外请车发车计
 *
 *
 *
 *划的编辑按钮触发而弹出的界面。
 *
 *
 *
 *如果是修改情况，则加载相应的发车计划的信息，待用户修改；如果是新增，则根据
 *
 *
 *
 *默认情况，提供新增的默认值，待用户完善其他线路、车辆、司机信息，然后保存。
 *
 *
 *
 *
 *
 *提供保存、取消、重置按钮。
  ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 *		1. 保存：用于保存已经录入的外请车的发车计划。
 *
 *
 *
 *
 *		2. 取消：取消新增或修改，退出本界面，返回主界面。
 *
 *
 *
 *		3．重置：将本界面的表单恢复到默认状态。
 *
 *
 *
 *		
 *
 *		图（3）界面是新增/编辑公司车发车计划界面，主要是针对在车辆发车不能满
 *
 *
 *
 *足货量的情况且有公司车的情况下，需要新增/修改公司车作为发车计划，主要是通过
 *
 *
 *
 *
 *新增公司车按钮或点击公司车发车计划的编辑按钮触发而弹出的界面。
 *
 *
 *
 *如果是修改情况，则加载相应的发车计划的信息，待用户修改；如果是新增，则根据默
 *
 *
 *
 *认情况，提供新增的默认值，待用户完善其他线路、车辆、司机信息，然后保存。提供
 *
 *
 *
 *保存、取消、重置按钮。
  ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 *		1. 保存：用于保存已经录入的公司车的发车计划。
 *
 *
 *
 *		2. 取消：取消新增或修改，退出本界面，返回主界面。
 *
 *
 *
 *		3．重置：将本界面的表单恢复到默认状态。
 *
 *
 *
 *		1.5.3.1	实时货量信息
 *
 *
 *
 *		主要是根据出发部门、到达部门，发车日期条件，查询货量预测得到的实时货量
 *
 *
 *
 *信息（可以调用货量预测模块的接口，传入到达部门、预测时间两个参数），主要包括到
 *
 *
 *
 *达部门、预测时间、总重量/总体积/票数、卡货重量/体积/票数、
 *
 *
 *
 *城货重量/体积/票数、开单未交接重量/体积/票数、余货重量/体积/票数、预计到达重量/
 *
 *
 *
 *体积/票数、（小计）合发重量/体积/票数、（汇总）重量/总体积/票数、合发记录信息
 *
 *
 *
 *（线路、合发重量/体积/票数）；这些数据需要两个参数作为查询条件：出发部门、到达部门，
 *
 *
 *
 *
 *发车日期，具体数据获取请参见SR-5。
  ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 *		1.5.3.2	合发记录信息
 *
 *
 *
 *
 *		主要是对出发部门和到达部门当天的合发记录的信息的反应，包括本到达部门涉及所有
 *
 *
 *
 *
 *线路合发到本线以及本线合发到其他线路的信息。
 *
 *
 *
 *
 *主要是体现实际货量的线路调整日志。其中主要字段为 线路、时间、重量、体积。
 *
 *
 *
 *
 *
 *		1.5.3.3	发车计划表单（外请车及公司车）中相应字段的默认设置
 *
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 *		长途发车计划新增表单见图（2）图（3），表单中各项信息参见【长途发车计划表单】
 *
 *
 *		线路：显示为线路的出发站目的站，根据出发部门到达部门查询出可用线路。
 *
 *
 *
 *		车型：详见车型标准：“箱式、平板、高栏”+“4.2、6.5、7.6、13、17.5”米，默认全部； 
 *
 *
 *
 *
 *
 *		归属类型：公司车、外请车，必须选择一项，默认为公司车。
 *
 *
 *
 *
 *		状态：新增时系统后台默认为新建。
 *
 *
 *
 *
 *		是否正班车：有是和否，必须选择一项，默认为是。
 *
 *
 *
 *
 *		班次类型：加发、停发、正常，必须选择一项，默认为正常。
 *
 *
 *
 *
 *		1.5.3.4	长途发车计划列表
 *
 *
 *
 *
 *		长途发车计划列表显示信息：列表参见【发车计划列表】。
 *
 *
 *
 *
 *		其中还包括实时运力统计，实时运力统计主要包括对所选线路、发车日期的所有车辆载重
 *
 *
 *
 *
 *		及车辆容积的统计，便于和实时的货量预测信息起到比对作用，便于操作人员查看发车计划是否
 *
 *
 *
 *
 *		匹配实时的货量预测信息。其中字段主要为统计信息：车辆合计、载重合计、总载缺口、容积合计
 *
 *
 *
 *
 *		、容积缺口、合发总载重、合发总容积，
 *
 *
 *
 *
 *		差额请参考SR-7。
 *
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 ************************************************************************************
 *
 * 
 * 
 * 
 * 
 *短途制定发车计划需求：
 *
 *
 *
 *
 *		图（1）界面主要分为四部分：货量预测信息、发车计划信息、班车列表，发车计划修改记录。
 *
 *
 *
 *
 *		1．货量预测信息：主要根据出发部门到达部门和发车日期查询当前时间最新的货量预测信息，
 *
 *
 *
 *
 *		主要包括：出发部门、到达部门、预测时间、总货物体积、预测货物总重量。
 *
 *
 *
 *
 *		2. 发车计划信息：主要提供发车日期、是否异常、备注信息的记录功能，
 *
 *
 *
 *
 *		以及提供保存计划、返回、反馈外场操作员等操作功能按钮。
 *
 *
 *
 *
 *		3.班车列表：主要提供发车计划列表，以及班车的统计信息包括班车的体积合计、
 *
 *
 *
 *
 *		载重合计、体积缺口、载重缺口等信息，以及增加、编辑、删除等功能按钮，
 *
 *
 *
 *
 *
 *		且同实时的货量统计信息。
 *
 *
 *
 *		4. 保存计划：用于保存发车计划，主要是保存是否异常及备注的信息。
 *
 *
 *
 *		5. 返回：用于返回班车发车计划查询界面。
 *
 *
 *
 *		（要生成送短信文本给司机和请车员-本描述根据ISSUE-1233删除）。根据ISSUE-1233，
 *
 *
 *
 *		【生成短信文本】按钮去掉，在列表中的短信按钮，鼠标移动到按钮时，提示“拷贝短信文本”，
 *
 *
 *
 *		发车计划针对短途排班相关的修改给车队部门发送在线通知。
 *
 *
 *
 *
 *		7．增加公司车：在排班表中不足的情况下，弹出新增加发公司车窗口，用于查询操作用户权限内
 *
 *
 *		的公司车辆所有状态的信息，操作人员可按照实际需要，选择查询的车辆信息、填写相应的司机信息，
 *
 *
 *
 *		然后点击保存为发车计划。用户查询出车辆后，点击选择的的按钮，自动将车辆信息，如车型、车牌号、
 *
 *
 *
 *		车辆载重、车辆净空等信息自动带入下面的车辆信息，并完善预计装载载重、预计装载体积等信息，
 *
 *
 *
 *		可点击保存，系统验证其他必填项合法后，方可保存。
 *
 *
 *
 *		8.增加外请车：在排班表中不足且需要增加外请车情况下，弹出增加新增加发外请车窗口，
 *
 *
 *
 *		填写相应的线路信息、车辆信息、司机信息后，点击保存，系统执行验证，如都合法后，
 *
 *
 *
 *		则执行保存到数据库作为外请车发车计划。
 *
 *
 *
 *
 *		8. 发车计划修改记录：用于查看发车计划中计划的修改情况和历史记录。
 *
 *

 *		当在排班表中不足且需要增加外请车情况下，点击了新增加发外请车按钮或者点击了修改按钮
 *
 *
 *
 *		（外请车的发车计划），用于新增加发和编辑外请车情况的发车计划信息。本界面提供以下功能按钮。
 *
 *
 *
 *		1.保存：录入/修改相应的的外请车发车计划信息后，进行保存操作。
 *
 *
 *
 *
 *		2.取消：用于取消修改或者保存操作，关闭本新增加发/编辑框，返回主界面。
 *
 *
 *
 *		3.重置：将本窗口表单数据恢复为默认状态。
 *
 *
 *

 *		当在排班表中不足且需要增加公司车情况下，弹出增加新增加发公司车窗口，主要用于公司车的
 *
 *
 *
 *		发车计划的新增加发和修改操作。如果是修改情况，则自动加载原有发车计划信息，以待用户修改；如果是新增
 *
 *
 *
 *		加发情况，则用户需要查询车辆使用情况，选择车辆信息后自动带出相关信息并填充车辆信息的相关字段，完善
 *
 *
 *
 *		线路信息、车辆信息、司机信息等，本界面提供以下功能按钮。
 *
 *
 *
 *		1.保存：录入/修改相应的的公司车发车计划信息后，进行保存操作。
 *
 *
 *
 *		2.取消：用于取消修改或者保存操作，关闭本新增加发/编辑框，返回主界面。
 *
 *
 *
 *
 *		3.重置：将本窗口表单数据恢复为默认状态。
 *
 *
 *
 *		SR-1	假如时间点的预测货量值低于X时，标记为货量异常；
 *
 *
 *
 *		假如时间点的预测货量值高于X时，标记为货量异常；
 *
 *
 *
 *		其中X的值是可配置的。
 *
 *
 *
 *
 *		SR-2	新制定的班车发车计划与规定发车班次进行比对，相符的，记录为正常发车；新制定的多余规定发车次数的，
 *
 *
 *
 *		记录为加发；新制定的少于规定发车次数的记录为停发；
 *
 *
 *
 *
 *		SR-3	班车发车计划的修改记录需要记录进入班车发车计划日志，且发车计划需要保存修改状态及修改时间。
 *
 *
 *
 *
 *		SR-4	线路字段不可编辑，车辆载重、车辆净空对于自有车而言是不可编辑的，对于外请车来说则是可编辑的。
 *
 *
 *
 *
 *		SR-5	第一次制作发车计划时，系统自动将短途排班表的排班信息初始化为短途发车计划，且为非有效状态， 
 *
 *
 *
 *		待用户填写好相应数据，且按照相关逻辑验证通过，保存后为有效状态。停发情况保存时不必校验，如果由停发状态改
 *
 *
 *
 *		为其他状态，则必须执行验证。
 *
 *
 *
 *
 *		SR-6	在做发车计划时，根据线路、班次、车牌、日期信息生成车辆任务信息，如果排班计划有有变化则需要提醒用户。
 *
 *
 *
 *		如果在做停发或者删除发车计划时，需要同时修改车辆任务信息（特别是对班次的修改时）；
 *
 *
 *
 *		SR-7	出发部门（默认为制定计划人员所在地）、到达部门（默认为本计划的到达部门），车辆信息可由车牌号码带出， 
 *
 *
 *		比如：车型、车辆所属车队、车辆净空、车辆载重、以及填写本到达部门需要预计装载载重（默认为车辆载重）、预计装载体积
 *
 *
 *
 *		（默认为车辆净空）、班次（默认为排班表中的班次）、发车日期（默认为发车计划的日期）、发车时间（排班表中的发车时间），
 *
 *
 *
 *		所有字段录入完毕后，保存作为发车计划，同时生成比对日志，发送在线通知提醒相关的排班人员。发车类别（如果是编辑，
 *
 *
 *
 *
 *		则取原值，如果为加发，则默认加发，用户根据具体情况可修改），月台号（可根据出发部门、到达部门到综合管理的月台
 *
 *
 *
 *
 *		信息中查询中自动带出，如不合理则可修改，如没有则用户自己填写），在制定计划的时候车辆信息中车牌、载重、司机信
 *
 *
 *
 *		息等可先不填写（车型必须填写），待后期确定后再编辑补充相应的数据,发车时间可根据班次自动带出。
 *
 *
 *
 *		SR-8	针对同一车辆可能被安排为多个发车计划使用，系统提供车辆相关任务信息列表查看功能，主要是根据车牌，
 *
 *
 *
 *
 *		查询相关线路的运输任务（包括车牌号、出发部门、到达部门、装载信息、可用载重体积信息、预计到达时间等）。
 *
 *
 *
 *
 *
 *		SR-9	存在区域总调的情况，由区域总调制定发车计划（包括增删改差所有操作），外场操作员只能查看发车计划
 *
 *
 *
 *
 *
 *		（只能查询），班车调度可修改所有字段（除了加发和删除不能做）；不存在区域总调的情况，由班车调度制定发车计划
 *
 *
 *
 *
 *		（包括增删改差所有操作），外场操作员只能查看发车计划（只能查询）。现有情况是没有区域总调。
 *
 *
 *
 *
 *		SR-10	车辆状态取值列表：在用（已计划、已装车、已出发、、已到达、）；空闲；不可用（年审、季审、维修、
 *
 *
 *
 *
 *		保养、事故、其他）ISSUE-958
 *
 *
 *
 *
 *		SR-11	公司车辆选择：当用户在加发公司发车计划界面，进行查询车辆的时候，根据查询结果，点击相应的选择框后，
 *
 *
 *
 *
 *
 *		自动将车辆的车辆载重、车辆容积、车牌号码、预计装载载重、预计装载体积自动带入填充，默认情况下预计装载载重取车辆载重，
 *
 *
 *
 *		预计装载体积取车辆容积（用户可根据实际计划情况修改）。
 *
 *
 *
 *
 *		SR-12	班车列表中的统计字段来源：体积合计是指本班车列表中所有已经安排车辆的预计装载体积之和；载重合计是指本
 *
 *
 *
 *		车辆是指本班车列表中所有已经安排车辆的预计装载载重之和；体积缺口=货量预测的总货物体积-体积合计；载重缺口=货量预测
 *
 *
 *
 *		的预测货物总重量-载重合计；
 *
 *
 *
 *
 *		SR-13	点击车牌号码，可提查看车辆使用情况
 *
 *
 *
 *
 *		SR-15	可根据司机姓名、电话号码、车队、车牌号等带出相应的人员信息、车辆信息等，抽出作为公共选择器，供各个
 *
 *
 *
 *		模块需要时使用。
 *
 *
 *
 *
 *		SR-16	填写月台号时，填写预计月台使用和预计结束时间;预计月台结束时间系统默认为发车时效表规定的出发时间，
 *
 *
 *
 *
 *		但是允许修改; 预计月台开始时间默认为发车时效表规定的出发时间的前推一个（车型）装车时间（例如，车型9.6，装货时间3个小时，
 *
 *
 *
 *
 *		规定出发时间为6:00，那么预计月台开始时间为3:00），但是允许修改；假如无时效表发车规定的，默认为空，由调度自己填写.
 *
 *
 *
 *
 *
 *		SR-17	1：短途发车计划中【新增公司车】，【新增外请车】修改为：【加发公司车】，【加发外请车】 
 *
 *
 *
 *
 *		2：短途发车计划中，增加转换功能（公司车与外请车的转换，初始化时归属类型默认为公司车，班次正常），在已经选择
 *
 *
 *
 *
 *		公司车的情况下，可以一键转换为外请车，反之也一样。下发前，才能转换，下发之后，不可转换，转换是清除车牌信息，司机信息
 *
 *
 *
 *
 *
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午7:05:53
 */
public class TruckDepartPlanUpdateService implements ITruckDepartPlanUpdateService {

	/**
	 * 日志DAO
	 */
	private ITruckDepartPlanUpdateDao truckDepartPlanUpdateDao;

	/**
	 * 设置 日志DAO.
	 *
	 * @param truckDepartPlanUpdateDao the new 日志DAO
	 */
	public void setTruckDepartPlanUpdateDao(ITruckDepartPlanUpdateDao truckDepartPlanUpdateDao) {
		this.truckDepartPlanUpdateDao = truckDepartPlanUpdateDao;
	}

	/**
	 * 新增日志
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-23 上午9:05:58
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanUpdateService#addTruckDepartPlanUpdate(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	@Transactional
	public void addTruckDepartPlanUpdate(TruckDepartPlanUpdateEntity truckDepartPlanUpdateEntity)
			throws TruckDepartPlanException {
		truckDepartPlanUpdateDao.addTruckDepartPlanUpdate(truckDepartPlanUpdateEntity);
	}

	/**
	 * 查询日志
	 * 
	 * 
	 * 
	 * 短途：
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 用于查看发车计划中计划的修改情况和历史记录。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 班车发车计划的修改记录需要记录进入班车发车计划日志，且发车计划需要保存修改状态及修改时间。
	 * 
	 * 
	 * 
	 * 
	 * 长途：
	 * 
	 * 
	 * 
	 * 用于查看发车计划中计划的修改情况和历史记录。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 班车发车计划的修改记录需要记录进入发车计划日志，且发车计划需要保存修改状态及修改时间。
	 * 
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * 
	 * @date 2012-11-23 上午9:05:58
	 * 
	 * 
	 * 
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanUpdateService#queryTruckDepartPlanUpdates(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto,
	 *      int, int)
	 */
	@Override
	public List<TruckDepartPlanUpdateEntity> queryTruckDepartPlanUpdates(TruckDepartPlanDetailDto detailDto, int limit,
			int start) throws TruckDepartPlanException {
		return truckDepartPlanUpdateDao.queryTruckDepartPlanUpdates(detailDto, limit, start);
	}

	/**
	 * 查询总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-23 上午9:05:58
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanUpdateService#queryTotalCount(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@Override
	public Long queryTotalCount(TruckDepartPlanDetailDto detailDto) throws TruckDepartPlanException {
		return truckDepartPlanUpdateDao.queryTotalCount(detailDto);
	}

	/**
	 * 比对计划明细修改情况
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-3 下午5:39:18
	 */
	@Override
	public TruckDepartPlanUpdateEntity compareDepartPlanDetail(TruckDepartPlanDetailDto oldDetail,
			TruckDepartPlanDetailDto detailDto, CurrentInfo user) throws TruckDepartPlanException {
		TruckDepartPlanUpdateEntity detailLog = new TruckDepartPlanUpdateEntity();
		// 操作内容描述
		StringBuffer modifyContent = null;
		// 如果存在原始比对对象，则为更新
		detailLog.setId(UUIDUtils.getUUID());
		// 发车计划明细ID
		detailLog.setTruckDepartPlanDetailId(detailDto.getId());
		// 执行比对(修改)
		modifyContent = CompareUtils.compareModifyContent(oldDetail, detailDto);
		// 表示更新
		if (oldDetail != null) {
			// 更新内容为空,则不返回任何的修改日志
			if (StringUtils.isBlank(modifyContent.toString())) {
				return null;
			} else {
				// 返回正常的修改内容
			}
		}
		detailLog.setModifyContent(modifyContent.toString());

		return detailLog;
	}

	/**
	 * 批量新增日志
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 上午10:06:10
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanUpdateService#addTruckDepartPlanUpdates(java.util.List)
	 */
	@Override
	public void addTruckDepartPlanUpdates(List<TruckDepartPlanUpdateEntity> detailLogs) {
		if (CollectionUtils.isNotEmpty(detailLogs)) {
			truckDepartPlanUpdateDao.addTruckDepartPlanUpdates(detailLogs);
		}

	}
}