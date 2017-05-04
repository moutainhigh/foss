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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/TruckDepartPlanAction.java
 * 
 *  FILE NAME     :TruckDepartPlanAction.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.action
 * FILE    NAME: TruckDepartPlanAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.scheduling.api.define.TruckDepartPlanConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanUpdateService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckDepartPlanUpdateEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.CarInfoPageDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ForecastForPlanDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.MergeLogDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlanVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.PlatformDispatchException;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.TruckDepartPlanException;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.TruckDepartPlanVo;
import com.deppon.foss.util.DateUtils;

/**
 * 发车计划Action
 * 
 * 
 * 长途制定发车计划需求：
 * 
 * 
 * 首次录入某预测周期的发车计划时，根据出发部门、到达部门、
 * 
 * 
 * 发车日期带出所有相关线路的固定班次（综合管理的发车时效表），
 * 
 * 
 * 在可用线路班次列表表格里按照班次顺序显示，且线路经过本计划
 * 
 * 
 * 的出发部门的线路车辆信息需要排前面，用户可修改和完善带出的
 * 
 * 
 * 固定班次的各项值。
 * 
 * 
 * 图（1）界面是制定长途发车计划主界面。主要分为三部分：实时货
 * 
 * 
 * 量信息、长途发车计划列表(包含实时运力统计)。
 * 
 * 
 * 首次录入出发部门和到达部门某预测周期的发车计划时，根据线
 * 
 * 
 * 路带出该出发部门和到达部门的固定班次，在表格里按照班次顺序显示，
 * 
 * 
 * 且在上方显示最新货量预测信息、合发记录、以及表体右下方显示运力
 * 
 * 
 * 实时统计信息，提供新增公司车、新增外请车、下发、删除、编辑、
 * 
 * 
 * 复制短信模板：
 * 
 * 
 * 1. 新增公司车：弹出图（3）的窗口，用于新增公司车作为发车计划。
 * 
 * 
 * 2. 新增外请车：弹出图（2）的窗口，用于新增外请车作为发车计划。
 * 
 * 
 * 3. 删除：在长途发车计划列表中勾选好需要删除的发车计划后，点击
 * 
 * 
 * 删除进行删除操作。 4. 下发：计划列好后，将选择新建状态的计划，点击下发按钮，使长
 * 
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
 * 
 * 
 * 图（3）界面是新增/编辑公司车发车计划界面，主要是针对在车辆发车不能满
 * 
 * 足货量的情况且有公司车的情况下，需要新增/修改公司车作为发车计划，主要是通过
 * 
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
 * 
 * SR-2 新制定的班车发车计划与规定发车班次进行比对，相符的，记录为正常发车；新制定的多余规定发车次数的，
 * 
 * 记录为加发；新制定的少于规定发车次数的记录为停发；
 * 
 * 
 * SR-3 班车发车计划的修改记录需要记录进入班车发车计划日志，且发车计划需要保存修改状态及修改时间。
 * 
 * 
 * SR-4 线路字段不可编辑，车辆载重、车辆净空对于自有车而言是不可编辑的，对于外请车来说则是可编辑的。
 * 
 * 
 * SR-5 第一次制作发车计划时，系统自动将短途排班表的排班信息初始化为短途发车计划，且为非有效状态， *
 * 
 * 待用户填写好相应数据，且按照相关逻辑验证通过，保存后为有效状态。停发情况保存时不必校验，如果由停发状态改
 * 
 * 为其他状态，则必须执行验证。
 * 
 * 
 * SR-6 在做发车计划时，根据线路、班次、车牌、日期信息生成车辆任务信息，如果排班计划有有变化则需要提醒用户。
 * 
 * 如果在做停发或者删除发车计划时，需要同时修改车辆任务信息（特别是对班次的修改时）；
 * 
 * 
 * SR-7 出发部门（默认为制定计划人员所在地）、到达部门（默认为本计划的到达部门），车辆信息可由车牌号码带出， *
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
 * 
 * SR-10 车辆状态取值列表：在用（已计划、已装车、已出发、、已到达、）；空闲；不可用（年审、季审、维修、 保养、事故、其他）ISSUE-958
 * 
 * SR-11 公司车辆选择：当用户在加发公司发车计划界面，进行查询车辆的时候，根据查询结果，点击相应的选择框后，
 * 
 * 自动将车辆的车辆载重、车辆容积、车牌号码、预计装载载重、预计装载体积自动带入填充，默认情况下预计装载载重取车辆载重，
 * 
 * 预计装载体积取车辆容积（用户可根据实际计划情况修改）。
 * 
 * 
 * SR-12 班车列表中的统计字段来源：体积合计是指本班车列表中所有已经安排车辆的预计装载体积之和；载重合计是指本
 * 
 * 车辆是指本班车列表中所有已经安排车辆的预计装载载重之和；体积缺口=货量预测的总货物体积-体积合计；载重缺口=货量预测
 * 
 * 的预测货物总重量-载重合计；
 * 
 * 
 * SR-13 点击车牌号码，可提查看车辆使用情况
 * 
 * 
 * SR-15 可根据司机姓名、电话号码、车队、车牌号等带出相应的人员信息、车辆信息等，抽出作为公共选择器，供各个
 * 
 * 模块需要时使用。
 * 
 * 
 * SR-16 填写月台号时，填写预计月台使用和预计结束时间;预计月台结束时间系统默认为发车时效表规定的出发时间，
 * 
 * 但是允许修改; 预计月台开始时间默认为发车时效表规定的出发时间的前推一个（车型）装车时间（例如，车型9.6，装货时间3个小时，
 * 
 * 规定出发时间为6:00，那么预计月台开始时间为3:00），但是允许修改；假如无时效表发车规定的，默认为空，由调度自己填写.
 * 
 * 
 * SR-17 1：短途发车计划中【新增公司车】，【新增外请车】修改为：【加发公司车】，【加发外请车】
 * 
 * 2：短途发车计划中，增加转换功能（公司车与外请车的转换，初始化时归属类型默认为公司车，班次正常），在已经选择
 * 
 * 公司车的情况下，可以一键转换为外请车，反之也一样。下发前，才能转换，下发之后，不可转换，转换是清除车牌信息，司机信息
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-22 下午3:01:36
 */
public class TruckDepartPlanAction extends AbstractAction {

	private static final long serialVersionUID = -302159438416579731L;
	/**
	 * 
	 * 日志管理器
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TruckDepartPlanAction.class);

	/**
	 * 
	 * 发车计划Service
	 * 
	 */
	private ITruckDepartPlanService truckDepartPlanService;
	/**
	 * 
	 * 
	 * 发车计划明细Service
	 * 
	 */
	private ITruckDepartPlanDetailService truckDepartPlanDetailService;
	/**
	 * 
	 * 日志Service
	 * 
	 */
	private ITruckDepartPlanUpdateService truckDepartPlanUpdateService;
	/**
	 * 业务锁Service
	 */
	private IBusinessLockService businessLockService;

	/**
	 * 
	 * 页面VO
	 * 
	 */
	private TruckDepartPlanVo vo = new TruckDepartPlanVo();
	/**
	 * 导出Excel 文件流
	 */
	transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;

	/**
	 * 
	 * 设置 发车计划Service.
	 * 
	 * 
	 * @param truckDepartPlanService
	 * 
	 *            the new 发车计划Service
	 */
	public void setTruckDepartPlanService(ITruckDepartPlanService truckDepartPlanService) {
		this.truckDepartPlanService = truckDepartPlanService;
	}

	/**
	 * 
	 * 设置 发车计划明细Service.
	 * 
	 * @param truckDepartPlanDetailService
	 * 
	 *            the new 发车计划明细Service
	 */
	public void setTruckDepartPlanDetailService(ITruckDepartPlanDetailService truckDepartPlanDetailService) {
		this.truckDepartPlanDetailService = truckDepartPlanDetailService;
	}

	/**
	 * 
	 * 设置 日志Service.
	 * 
	 * @param truckDepartPlanUpdateService
	 * 
	 *            the new 日志Service
	 */
	public void setTruckDepartPlanUpdateService(ITruckDepartPlanUpdateService truckDepartPlanUpdateService) {
		this.truckDepartPlanUpdateService = truckDepartPlanUpdateService;
	}

	/**
	 * 
	 * 获取 页面VO.
	 * 
	 * @return the 页面VO
	 * 
	 */
	public TruckDepartPlanVo getVo() {
		return vo;
	}

	/**
	 * 
	 * 设置 页面VO
	 * 
	 * @param vo
	 * 
	 *            the new 页面VO
	 */
	public void setVo(TruckDepartPlanVo vo) {
		this.vo = vo;
	}

	/**
	 * 导出文件流
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-26 下午2:38:09
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**
	 * 导出文件名
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-26 下午2:38:22
	 */
	public String getFileName() {
		return fileName;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * 查询发车计划；
	 * 
	 * 
	 * 
	 * 1. 查看：点击此按钮，进入录入发车计划页面，
	 * 
	 * 
	 * 
	 * 根据线路货量信息和实际的发车计划进行调整和修改，
	 * 
	 * 
	 * 
	 * 具体的修改参见用例SUC-313录入发车计划（长途）中的编辑调整功能。
	 * 
	 * 
	 * 
	 * 此编辑功能可针对本发车计划对应的线路的所有班次进行调整。
	 * 
	 * 
	 * 
	 * 2. 查询：按条件查询出所有满足要求的发车计划。
	 * 
	 * 
	 * 
	 * 3. 新增发车计划：用于跳转到录入界面进行新增发车计划。
	 * 
	 * 
	 * 
	 * 
	 * 4.录入查询条件，发车日期为必填项，
	 * 
	 * 
	 * 
	 * 默认为当天 外场操作员、长途车队调度、班车调度、
	 * 
	 * 
	 * 
	 * 请车员根据实际情况，点击查询按钮执行查询。
	 * 
	 * 
	 * 
	 * 系统根据查询条件返回相应的查询结果以表格形式显示，
	 * 
	 * 
	 * 
	 * 查询规则参见SR-3。
	 * 
	 * 
	 * 
	 * 点击编辑按钮，系统根据本发车计划信息，跳转到录入发车计划信息界面，
	 * 
	 * 
	 * 
	 * 用户可根据最新的货量预测信息调整编辑对应的发车计划。
	 * 
	 * 
	 * 
	 * 具体的修改参见用例SUC-313录入发车计划（长途）中的编辑功能。
	 * 
	 * 
	 * 
	 * 查询结果需要按照操作用户权限的匹配查询结果，发车日期为必填，
	 * 
	 * 
	 * 
	 * 进入本页面时，默认查询结果为空，待用户录入相应的查询条件并点击查询按钮时，
	 * 
	 * 
	 * 
	 * 
	 * 才执行查询操作，查询出相应的结果呈现在查询结果中，
	 * 
	 * 
	 * 
	 * 查询出的结果默认以创建时间降序排序。
	 * 
	 * 
	 * 用户录入了发车日期、出发部门、到达部门后，执行查询未查询到相应的发车计划，
	 * 
	 * 
	 * 
	 * 则显示新增计划按钮，用以新增对应发车日期、出发部门、
	 * 
	 * 
	 * 
	 * 到达部门的发车计划，跳转到录入发车计划页面。
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-22 下午3:37:11
	 */
	public String queryTruckDepartPlan() {
		// 捕捉异常
		try {
			// 判空
			if (vo != null && vo.getPlanDto() != null) {
				// 执行查询
				List<TruckDepartPlanDto> tempList = truckDepartPlanService.queryTruckDepartPlan(vo.getPlanDto(), limit,
						start);
				// 判空
				if (CollectionUtils.isNotEmpty(tempList)) {
					// 返回列表给Vo
					vo.setPlanList(tempList);
					// if结束
				}
				// 总条数查询
				Long totalCount = truckDepartPlanService.queryTruckDepartPlanTotal(vo.getPlanDto());
				// 总条数
				if (totalCount != null) {
					// 设置值
					this.setTotalCount(totalCount);
					// if结束
				}
				// 判空结束
			}
			// 成功
			return super.returnSuccess();
			// try结束
		}
		// 捕捉发车计划异常
		catch (TruckDepartPlanException e) {
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 捕捉业务异常
		} catch (BusinessException e) {
			// 记录异常错误
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
		} catch (Exception e) {
			// 记录异常错误
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 新增发车计划，
	 * 
	 * 后续还需要根据发车时效初始化，发车计划明细;
	 * 
	 * 
	 * 
	 * 在只填写了正确的线路及发车日期的情况下，未查询到任何的发车计划的情况下;
	 * 
	 * 系统显示新增计划按钮;
	 * 
	 * 
	 * 用户点击新增按钮，跳转到录入发车计划界面;
	 * 
	 * 跳转到录入发车计划界面,具体参见用例SUC-313，
	 * 
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
	 * @date 2012-11-22 下午3:37:11
	 */
	public String addTruckDepartPlan() {
		MutexElement mutex = null;
		try {
			// 判空
			if (vo != null && vo.getPlanDto() != null) {
				CurrentInfo user = FossUserContext.getCurrentInfo();
				// 出发部门，到达部门，发车计划类型，发车日期
				String lockStr = vo.getPlanDto().getOrigOrgCode() + vo.getPlanDto().getDestOrgCode()
						+ vo.getPlanDto().getPlanType()
						+ DateUtils.convert(vo.getPlanDto().getPlanDate(), DateUtils.DATE_SHORT_FORMAT);
				mutex = new MutexElement(lockStr, "新增发车计划", MutexElementType.TRUCK_SCHEDULING);
				// 锁定
				boolean flag = businessLockService.lock(mutex, 0);
				if (flag) {
					// 新增发车计划
					int flg = truckDepartPlanService.addTruckDepartPlan(vo.getPlanDto(), user);
					// 如果返回已经存在,则提示已经存在
					if (TruckDepartPlanConstants.SERVICE_TRANSACTION_HAS_ADDED == flg) {
						// 已经存在
						vo.setPlanExsit(TruckDepartPlanConstants.HAS_ADDED);
					}
					// 解锁
					businessLockService.unlock(mutex);
				} else {
					throw new TruckDepartPlanException("当前发车计划正在操作中，请稍后再试", "");
				}

			}
			// 成功
			return super.returnSuccess();
		} catch (TruckDepartPlanException e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 捕捉业务异常
		} catch (BusinessException e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 记录异常错误
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
		} // 捕捉异常
		catch (Exception e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 记录异常错误
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 分页查询发车计划明细;
	 * 
	 * 
	 * 当第一次进入本页面，发车计划列表为空，发车日期默认为当天，
	 * 
	 * 
	 * 出发部门默认为用户当前所在地，用户根据实际需要进行查询。
	 * 
	 * 
	 * 2.提供出发部门、到达部门、创建人、创建时间、发车日期、车牌号等条件的查询，
	 * 
	 * 
	 * 用户根据需要自动组合查询条件，发车日期为必填，创建人做成模糊查询自动完成文本框
	 * 
	 * 
	 * 发车日期默认为当天，BUG-2638创建时间
	 * 
	 * 默认为当天00:00:00点到23:59:59点BUG-2638要求不默认创建时间。
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-22 下午3:37:11
	 */
	public String queryTruckDepartPlanDetail() {
		try {
			// 判空
			if (vo != null && vo.getPlanDto() != null) {
				// 查询发车计划明细
				List<TruckDepartPlanDetailDto> detailList = truckDepartPlanDetailService.queryTruckDepartPlanDetail(
						vo.getPlanDto(), limit, start);
				// 查询总条数
				Long totalCount = truckDepartPlanDetailService.queryTruckDepartPlanDetailTotal(vo.getPlanDto());

				// 返回发车计划明细
				if (CollectionUtils.isNotEmpty(detailList)) {
					// 设置值
					vo.setDetailList(detailList);
				}
				// 判空
				if (totalCount != null) {
					// 设置值
					this.setTotalCount(totalCount);
				}
			}
			// 成功
			// 成功
			return super.returnSuccess();
			// 发车计划异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 错误
			return super.returnError(e);
			// 业务异常
		} catch (BusinessException e) {
			// 记录错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 异常记录错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 1.6.71.6.6
	 * 
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
	 * @date 2012-11-22 下午3:37:11
	 * 
	 */
	public String queryCarList() {
		try {
			// 判空
			if (vo != null && vo.getCarDto() != null) {
				// 查询
				CarInfoPageDto pageDto = truckDepartPlanDetailService.queryCarList(vo.getCarDto(), limit, start);
				// 返回发车计划明细
				if (pageDto != null && CollectionUtils.isNotEmpty(pageDto.getVehicleList())) {
					// 车辆列表
					vo.setCarList(pageDto.getVehicleList());
					// 总条数
					if (pageDto.getTotalCount() != null) {
						// 设置值
						this.setTotalCount(Long.valueOf(pageDto.getTotalCount()));
					}
				}
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 错误信息
			return super.returnError(e);
			// 业务异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
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
	 * @date 2012-11-22 下午3:37:11
	 */
	public String saveOrUpdateInnerPlanDetail() {
		MutexElement mutex = null;
		try {
			// 判空
			if (vo != null && vo.getDetailDto() != null) {
				// 当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				mutex = fetchMutexElement(vo.getDetailDto());
				// 锁定
				boolean flg = businessLockService.lock(mutex, 0);
				// 成功获取锁
				if (flg) {
					//根据车牌号查询正在使用该车牌的部门名称
					List<String> sName = truckDepartPlanDetailService.queryDepartPlanDetailOrig(vo.getDetailDto());
					//设置部门名称
					if(sName != null && sName.size()>0){
						vo.setOrigName(sName);
					}
					// 保存更新
					truckDepartPlanDetailService.saveOrUpdateInnerPlanDetail(vo.getDetailDto(), user);
					// 解锁
					businessLockService.unlock(mutex);
				} else {
					throw new TruckDepartPlanException("当前发车计划正在操作中，请稍后再试", "");
				}

			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 错误信息
			return super.returnError(e);
		} catch (PlatformDispatchException e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 错误信息
			return super.returnError(e.getErrorCode(), "");
			// 消息异常
		} catch (MessageException e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 错误信息
			return super.returnError(e.getErrorCode(), "");
			// 业务异常
		} catch (BusinessException e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 获取锁条件
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-22 上午10:14:03
	 */
	private MutexElement fetchMutexElement(TruckDepartPlanDetailDto detailDto) {
		// 出发部门，到达部门，发车计划类型，线路、班次、发车日期
		String lockStr = detailDto.getOrigOrgCode() + detailDto.getDestOrgCode() + detailDto.getPlanType()
				+ detailDto.getLineNo() + detailDto.getFrequencyNo()
				+ DateUtils.convert(detailDto.getDepartDate(), DateUtils.DATE_SHORT_FORMAT);
		// 锁定条件
		MutexElement mutex = new MutexElement(lockStr, "加发发车计划", MutexElementType.TRUCK_SCHEDULING);
		return mutex;
	}

	/**
	 * 
	 * 下发修改后保存备注信息
	 * 
	 * 
	 * 用于保存发车计划，主要是保存是否异常及备注的信息。
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * @date 2012-11-22 下午3:37:11
	 * 
	 */
	public String saveRemarkAfterSaveSuccess() {
		try {
			// 判空
			if (vo != null && vo.getDetailDto() != null) {
				// 当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				// 保存更新
				truckDepartPlanDetailService.saveRemarkAfterSaveSuccess(vo.getDetailDto(), user);
			}
			// 成功
			return super.returnSuccess();
			// 发车计划异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 错误信息
			return super.returnError(e);
			// 月台异常
		} catch (PlatformDispatchException e) {
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 错误信息
			return super.returnError(e.getErrorCode(), "");
		} catch (MessageException e) {
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 错误信息
			return super.returnError(e.getErrorCode(), "");
			// 业务异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 根据出发部门到达部门查询线路列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-22 下午3:37:11
	 * 
	 */
	public String queryLineListByOrigDestCode() {
		try {
			// 判空
			if (vo != null && vo.getDetailDto() != null) {
				// 查询
				List<TruckDepartPlanDetailDto> departureList = truckDepartPlanDetailService
						.queryLineListByOrigDestCode(vo.getDetailDto());
				// 判空
				if (CollectionUtils.isNotEmpty(departureList)) {
					// 设置值
					vo.setDetailList(departureList);
				}
			}
			// 成功
			return super.returnSuccess();
			// 发车计划异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 错误信息
			return super.returnError(e);
			// 业务异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 查询当前用户车队
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-22 下午3:37:11
	 * 
	 */
	public String queryCarGroup() {
		try {
			List<OrgAdministrativeInfoEntity> orgList = FossUserContext.getCurrentUserManagerDepts();
			// 判空
			if (CollectionUtils.isNotEmpty(orgList)) {
				// 设置值
				vo.setOrgList(orgList);
			}
			// 成功
			return super.returnSuccess();
			// 发车计划异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 
	 * 通过司机Code查询司机信息
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * @date 2012-11-28 下午4:45:50
	 * 
	 */
	public String queryDriverInfoByDriverCode() {
		try {
			// 判空
			if (vo != null && vo.getDetailDto() != null) {
				// 司机
				DriverAssociationDto driver = null;
				// 将driverCode1作为司机编码
				driver = truckDepartPlanService.queryDriverInfoByDriverCode(vo.getDetailDto().getDriverCode1(), vo
						.getDetailDto().getTruckType());
				// 判空
				if (driver != null) {
					// 设置值
					vo.setDriver(driver);
				}

			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 
	 * 通过车牌Code查询司机信息
	 * 
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * 
	 * 
	 * @date 2012-11-28 下午4:45:50
	 * 
	 */
	public String queryVehicleByVechileNo() {
		try {
			// 车牌号或车型不为空
			if (vo != null
					&& vo.getDetailDto() != null
					&& (StringUtils.isNotBlank(vo.getDetailDto().getVehicleNo())
							|| StringUtils.isNotBlank(vo.getDetailDto().getTruckModel()) || StringUtils.isNotBlank(vo
							.getDetailDto().getContainerNo()))) {
				// 车辆信息
				PlanVehicleDto vehicle = truckDepartPlanDetailService.queryVehicleByVechileNo(vo.getDetailDto());
				// 判空
				if (vehicle != null) {
					// 设置值
					vo.setVehicle(vehicle);
				}
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 外请车通过车牌查询司机
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-2 下午2:11:32
	 */
	public String queryDriverInfoByVechileNo() {
		try {
			// 车牌号或车型不为空
			if (vo != null && vo.getDetailDto() != null && StringUtils.isNotBlank(vo.getDetailDto().getVehicleNo())) {
				// 司机
				DriverAssociationDto driver = null;
				// 将driverCode1作为司机编码
				driver = truckDepartPlanService.queryDriverInfoByVechileNo(vo);
				// 判空
				if (driver != null) {
					// 设置值
					vo.setDriver(driver);
				}
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 查询合发记录
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-28 下午4:45:50
	 * 
	 */
	public String queryMergeLogs() {
		try {
			// 判空
			if (vo != null && vo.getDetailDto() != null) {
				// 查询合发记录,造假数据
				List<MergeLogDto> mergeLogs = truckDepartPlanService.queryMergeLogs(vo.getDetailDto());
				// 判空
				if (CollectionUtils.isNotEmpty(mergeLogs)) {
					// 设置值
					vo.setMergeLogs(mergeLogs);
				}

			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 查询货量预测信息
	 * 
	 * 长途：
	 * 
	 * 要根据出发部门到达部门和发车日期查询
	 * 
	 * 当前时间最新的货量预测信息:
	 * 
	 * 主要包括：出发部门、到达部门、预测时间、总货物体积、预测货物总重量。
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-28 下午4:45:50
	 * 
	 */
	public String queryForecastInfo() {
		try {
			// 判空
			if (vo != null && vo.getPlanDto() != null) {
				// 查询货量预测
				ForecastForPlanDto forecastDto = truckDepartPlanService.queryForecastInfo(vo.getPlanDto());
				// 判空
				if (forecastDto != null) {
					// 设置值
					vo.setForecastDto(forecastDto);
				}
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 查询发车计划修改记录
	 * 
	 * 短途：
	 * 
	 * 用于查看发车计划中计划的修改情况和历史记录。
	 * 
	 * 班车发车计划的修改记录需要记录进入班车发车计划日志，且发车计划需要保存修改状态及修改时间。
	 * 
	 * 长途：
	 * 
	 * 用于查看发车计划中计划的修改情况和历史记录。
	 * 
	 * 班车发车计划的修改记录需要记录进入发车计划日志，且发车计划需要保存修改状态及修改时间。
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-28 下午4:45:50
	 * 
	 */
	public String queryUpdateLogs() {
		try {
			// 判空
			if (vo != null && vo.getDetailDto() != null
					&& StringUtils.isNotBlank(vo.getDetailDto().getTruckDepartPlanId())) {
				// 分页查询修改记录,默认按照时间降序排序
				List<TruckDepartPlanUpdateEntity> logList = truckDepartPlanUpdateService.queryTruckDepartPlanUpdates(
						vo.getDetailDto(), limit, start);
				// 判空
				if (CollectionUtils.isNotEmpty(logList)) {
					// 设置值
					vo.setLogList(logList);
				}
				// 查询总条数
				Long totoalCount = truckDepartPlanUpdateService.queryTotalCount(vo.getDetailDto());
				// 判空
				if (totoalCount != null) {
					// 设置值
					this.setTotalCount(totoalCount);
				}
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 下发发车计划
	 * 
	 * 长途：
	 * 
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
	 * @date 2012-11-28 下午4:45:50
	 * 
	 */
	public String releaseTruckDepartPlanDetail() {
		try {
			// 判空
			if (vo != null && vo.getDetailDto() != null && CollectionUtils.isNotEmpty(vo.getDetailDto().getIds())) {
				// 用户信息
				CurrentInfo user = FossUserContext.getCurrentInfo();
				// 下发
				truckDepartPlanDetailService.releaseTruckDepartPlanDetail(vo.getDetailDto(), user);
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 删除发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-28 下午4:45:50
	 * 
	 */
	public String deleteTruckDepartPlanDetail() {
		try {
			// 判空
			if (vo != null && vo.getDetailDto() != null && CollectionUtils.isNotEmpty(vo.getDetailDto().getIds())) {
				// 执行删除
				truckDepartPlanDetailService.deleteTruckDepartPlanDetail(vo.getDetailDto());
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 更新保存备注及异常标记
	 * 
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-28 下午4:45:50
	 * 
	 */
	public String updatePlanRemark() {
		try {
			// 判空
			if (vo != null && vo.getPlanDto() != null && StringUtils.isNotBlank(vo.getPlanDto().getId())) {
				// 当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				// 更新备注及异常
				truckDepartPlanService.updatePlanRemark(vo.getPlanDto(), user);
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 获取当前部门对应的外场
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-28 下午4:45:50
	 * 
	 */
	public String queryTransferCenter() {
		try {
			// 判空
			if (vo != null && vo.getPlanDto() != null && StringUtils.isNotBlank(vo.getPlanDto().getOrigOrgCode())) {
				// 查询外场信息
				OrgAdministrativeInfoEntity transferCenter = truckDepartPlanService.queryTransferCenter(vo.getPlanDto()
						.getOrigOrgCode());
				// 设置外场信息
				vo.setOrgInfo(transferCenter);
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 获取车队
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-28 下午4:45:50
	 * 
	 * 
	 */
	public String queryTransDepartment() {
		try {
			// 判空
			if (vo != null && vo.getPlanDto() != null && StringUtils.isNotBlank(vo.getPlanDto().getOrigOrgCode())) {
				// 查询车队
				OrgAdministrativeInfoEntity transferCenter = truckDepartPlanService.queryTransDepartment(vo
						.getPlanDto().getOrigOrgCode());
				// 设置车队信息
				vo.setOrgInfo(transferCenter);
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 改变车辆归属类型
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-28 下午4:45:50
	 * 
	 */
	public String changeTruckType() {
		try {
			// 判空
			if (vo != null && vo.getDetailDto() != null) {
				// 改变车辆归属类型
				truckDepartPlanService.changeTruckType(vo.getDetailDto());
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 外请司机查询外请车
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-11-28 下午4:45:50
	 * 
	 */
	public String queryLeasedDriverVechile() {
		try {
			// 判空
			if (vo != null && vo.getDetailDto() != null) {
				// 外请司机查询外请车
				truckDepartPlanService.queryLeasedDriverVechile(vo.getDetailDto());
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		} catch (Exception e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
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
	 * @date 2013-3-20 下午2:27:29
	 */
	public String queryMaxfrequencyNo() {
		try {
			// 判空
			if (vo != null && vo.getDetailDto() != null) {
				// 设置当前状态
				vo.getDetailDto().setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
				// 外请司机查询外请车
				Integer maxfrequencyNo = truckDepartPlanService.queryMaxfrequencyNo(vo.getDetailDto());
				// 判空
				if (maxfrequencyNo != null) {
					// 设置最大班次号
					vo.getDetailDto().setFrequencyNo(String.valueOf(maxfrequencyNo));
				}
			}
			// 成功
			return super.returnSuccess();
			// 异常
		} catch (TruckDepartPlanException e) {
			// 记录异常错误
			// 错误信息
			LOGGER.error(e.getErrorCode(), e);
			// 返回错误信息
			return super.returnError(e);
			// 异常
		} catch (BusinessException e) {
			// 错误信息
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 返回错误信息
			return super.returnError(e.getMessage());
			// 异常
		}
	}

	/**
	 * 
	 * 导出排班表信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-10-26 上午10:42:37
	 * 
	 */
	public String exportExcel() {
		try {
			// 文件名默认短途
			fileName = encodeFileName(TruckDepartPlanConstants.SHORT_PLAN_EXCEL_FILE_NAME);
			String tmpName = TruckDepartPlanConstants.SHORT_PLAN_EXCEL_FILE_NAME;
			// 长途发车计划
			if (TruckDepartPlanConstants.PLAN_TYPE_LONG.equals(vo.getPlanDto().getPlanType())) {
				// 长途发车计划
				fileName = encodeFileName(TruckDepartPlanConstants.LONG_PLAN_EXCEL_FILE_NAME);
				tmpName = TruckDepartPlanConstants.LONG_PLAN_EXCEL_FILE_NAME;
			}
			// 导出文件流
			excelStream = truckDepartPlanService.exportExcel(vo, tmpName);
			// 成功
			return returnSuccess();
		} catch (TruckDepartPlanException e) {
			// 日志
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 异常信息
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			// 日志
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 异常信息
			return returnError("UnsupportedEncodingException", "");
		} catch (Exception e) {
			// 日志
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}
	public String encodeFileName(String fileName)
			throws UnsupportedEncodingException {
		String returnStr;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		} else {
			returnStr = URLEncoder.encode(fileName, "UTF-8");
		}
		return returnStr;
	}

	/**
	 * 
	 * 出发部门、到达部门查询月台号
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-10-26 上午10:42:37
	 * 
	 */
	@JSON
	public String queryPlatformNo() {
		try {
			if (vo != null) {
				TruckDepartPlanDetailDto detailDto = truckDepartPlanService.queryPlatformNo(vo);
				vo.setDetailDto(detailDto);
			}
			return returnSuccess();
		} catch (TruckDepartPlanException e) {
			// 日志
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 异常信息
			return returnError(e);
		} catch (Exception e) {
			// 日志
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}

	/**
	 * 
	 * 批量生成发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2012-10-26 上午10:42:37
	 * 
	 */
	@JSON
	public String batchCreateDepartPlan() {
		// 锁
		MutexElement mutex = null;
		try {
			// 不为空
			if (vo != null) {
				CurrentInfo user = FossUserContext.getCurrentInfo();
				mutex = new MutexElement(vo.getPlanDto().getOrigOrgCode() + vo.getPlanDto().getPlanType()
						+ DateUtils.convert(vo.getPlanDto().getPlanDate(), DateUtils.DATE_SHORT_FORMAT), "批量生成发车计划",
						MutexElementType.TRUCK_SCHEDULING);
				// 锁定
				boolean flg = businessLockService.lock(mutex, 0);
				// 成功获取锁
				if (flg) {
					// 批量生成发车计划
					truckDepartPlanService.batchCreateDepartPlan(vo, user);
					// 解锁
					businessLockService.unlock(mutex);
				} else {
					throw new TruckDepartPlanException("本发车计划正在生成中，请稍后再试", "");
				}
			}
			return returnSuccess();
		} catch (TruckDepartPlanException e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 日志
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 异常信息
			return returnError(e);
		} catch (Exception e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 日志
			LOGGER.error(TruckDepartPlanConstants.EXCEPTION_CODE, e);
			// 异常信息
			return super.returnError(e.toString(), "");
		}
	}
}