/**
 * 
 * 
 * 
 *  initial comments.
 *  
 *  
 *  
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  
 *  you may not use this file except in compliance with the License.
 *  
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/CalculateOptimalPlatformService.java
 * 
 *  FILE NAME     :CalculateOptimalPlatformService.java
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
/**
 * 
 * 
 * PROJECT NAME: tfr-scheduling
 * 
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.service.impl
 * 
 * 
 * FILE    NAME: CalculateOptimalPlatformService.java
 * 
 * 
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 * 
 * 
 * 1.	SUC-371 计算车辆最优停靠月台
 * 
 * 1.1	相关业务用例
 * 
 * BUC_FOSS_5.10.30_020安排停靠月台
 * 
 * 1.2	用例描述
 * 
 * 根据月台当前使用信息,车辆到达信息等,合理安排到达车辆停靠的最优月台,返回最优列表.
 * 
 * 1.3	用例条件
 * 
 * 条件类型	描述	引用用例
 * 
 * 前置条件	
 * 
 * 1.	车辆到达,待选择停靠月台	查询月台计划停靠信息
 * 
 * 
 * 后置条件	
 * 
 * 
 * 1.	显示该月台当前和即将使用的信息
 * 
 * 2.	生成最新的预安排任务	查询月台计划停靠信息
 * 
 * 
 * 
 * 1.6	操作步骤
 * 
 * 
 * 序号	基本步骤	相关数据	补充步骤
 * 
 * 1	系统计算车辆携带货量情况,
 * 
 * 以备到达使用.	交接单/配载单	
 * 
 * 在车辆出发后根据车内装载货物清单，
 * 
 * 按照货区分组累计体积.计算前X的本车货物即可.
 * 
 * X推荐为80%,可以根据不同外场情况进行配置.
 * 
 * 
 * 2	车辆到达，需要分配月台时		
 * 
 * 有车辆到达外场,
 * 
 * 准备停靠月台卸车，
 * 
 * 系统启动计算最优停靠外场.
 * 
 * 3	根据车辆信息,
 * 
 * 匹配全部可用月台列表.	
 * 
 * 到达车辆信息
 * 
 * 月台信息	
 * 
 * 提取车辆到达信息,
 * 
 * 包括装货类别及货量,车辆型号.
 * 
 * 搜索月台状态,
 * 
 * 包括是否可以接收该车型,是否有其他安排.确定该车所有可用月台.
 * 
 * 
 * 4	计算车辆中货物到月台的最短距重值.		
 * 
 * 根据所有可用月台,依此计算距重值.
 * 
 * 使用步骤1中计算的体积排列.
 * 
 * 某货区货物的体积方数 * 月台到达目标停放仓库距离,
 * 
 * 得出每类货物到达指定地点的距方值
 * .将全部货物中比重最大的X货物进行计算后累加.
 * 
 * 得出该车辆停靠该月台的总距方值.
 * 
 * 依次计算后,按总距方值从小到大排列结果，
 * 
 * 计算过程参考SR-1,选取前N位为最优月台结果。
 * 
 * X推荐为80%,
 * 
 * N推荐为至少有5个可用月台.
 * 
 * 可以根据不同外场情况进行配置.
 * 
 * 
 * 
 * 1.7	业务规则
 * 
 * 序号	描述
 * 
 * SR-1	
 * 
 * 当累加货物的距重值时,
 * 
 * 每计算货区货物,
 * 
 * 要把当前总距方值与目前第N位的月台的总距方值进行比较,
 * 
 * 如果已经超出.则不再继续计算该月台总距方值.
 * 
 * 
 * 
 * 
 * 1.8	数据元素
 * 
 * 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 * 
 * 货物类型	该车辆所包含的货物的类型					由运单信息提供
 * 
 * 货物方数	每个类型货物的方数					由运单信息提供
 * 
 * 走货路径	该车辆的走货路径					由运单信息提供
 * 
 * 车型	根据车型选择可用月台					由运单信息提供
 * 
 * 可用月台号	针对该辆车所有可用的月台的号码					由基础信息提供月台信息,根据条件过滤后得出
 * 
 * 距离	特定月台到特定仓库的距离					由基础信息提供
 * 
 * 
 * 
 * 
 * 
 * 
 * 1.	SUC-50查询月台信息
 * 
 * 
 * 1.1	相关业务用例
 * 
 * 
 * BUC_FOSS_5.10.30_020安排停靠月台
 * 
 * 
 * 1.2	用例描述
 * 
 * 外场调度查看月台当前使用信息和预安排信息，
 * 
 * 同时可以为未来或当前时间分配月台以及修改月台信息。
 * 
 * 
 * 1.3	用例条件
 * 
 * 条件类型	描述	引用用例
 * 
 * 前置条件
 * 
 * 	1.	月台基础资料	SUC-184新增_修改_作废_查询月台号
 * 
 * 后置条件		
 * 
 * 1.5.3	界面描述
 * 
 * 本用例界面为查询月台信息界面，分为查询界面、装卸货进度以及清空操作界面和使用月台界面
 * 
 * 说明：
 * 
 * 一、	查询条件
 * 
 * 1、	月台号：月台唯一标示码；
 * 
 * 2、	车型：月台基础资料中的可停靠车型信息；
 * 
 * 3、	线路：月台基础资料中的线路信息；
 * 
 * 4、	时间段：时间范围；
 * 
 * 5、	车号：车牌号；
 * 
 * 6、	只显示可用月台：复选框；
 * 
 * 
 * 二、	查询显示列表
 * 
 * 1、	货区：线路名称；
 * 
 * 2、1、	月台号：月台号码；
 * 
 * 3、2、	横轴时间点：最小时间刻度为半小时，只显示整点时间点；
 * 
 * 三、	月台详情
 * 
 * 1、	界面来源月台基础资料，参见SUC-184新增_修改_作废_查询月台号；
 * 
 * 四、	清空月台
 * 
 * 1、时间段：清空哪个时间段的月台；
 * 
 * 2、确认清空按钮：执行清空操作；
 * 
 * 3、下方为装卸货进度表；
 * 
 * 五、	修改月台
 * 
 * 1、	界面下部为装卸车进度表；
 * 
 * 2、	月台号：当前行的月台号，可以修改；
 * 
 * 3、	时间：时间段；
 * 
 * 4、	确认修改按钮：修改车辆停靠月台或时间；
 * 
 * 六、	使用月台界面
 * 
 * 1、	月台号：选中行的月台号；
 * 
 * 2、	车牌号：来源车辆基础信息的车牌号；
 * 
 * 3、	时间：使用该月台的时间段；
 * 
 * 
 * 1.6	操作步骤
 * 
 * 1、查询月台信息步骤
 * 
 * 
 * 序号	基本步骤	相关数据	补充步骤
 * 
 * 1	打开月台查询界面，输入查询条件；	
 * 
 * 月台基础信息	参见SR-1
 * 
 * 2	点击查询； 	
 * 
 * 月台实时信息，月台计划停靠信息，月台基础信息	
 * 		1、	列表按货区显示符合条件的月台信息；
 * 
 * 		2、	每个月台分两行显示，第一行为计划停靠信息，第二行为实际停靠信息；
 * 
 * 		3、	月台显示按预计安排的车牌和实际使用的车牌显示，计划停靠车牌号用蓝底显示，实际停靠车牌号用灰底显示，车牌号所占长度为所用时间长度，计划停靠所用时间均为计划时间；
 * 
 * 		4、	只有小于等于当前时间的时间段才有实际停靠车牌号，大于当前时间段的只有计划停靠车牌号；
 * 
 *		5、	离当前时间点最近的半点纵轴高亮显示；
 *
 *
 *
 *2、查看月台详情步骤
 *
 *序号	基本步骤	相关数据	补充步骤
 *
 *1	外场调度点击月台号；		1、	外场调度已查询出月台信息
 *
 *2、	弹出月台详情，参见图2 月台
 *
 *详情界面；
 *
 *3、界面信息均来自月台基础信息，参见SUC-184新增_修改_作废_查询月台号；
 *
 *
 *
 *
 *
 *3、清空月台步骤
 *
 *序号	基本步骤	相关数据	补充步骤
 *
 *1	外场调度点击某个时间段内的车牌号；		1、	外场调度已查询出月台信息；
 *
 *2、	如果点击的车牌号所占时间段大于等于当前时间，则弹出清空月台和修改月台界面，界面包含两个页签，否则不响应；
 *
 *2	外场调度选择清空月台页签；	外场装卸货数据	1、	只有当前时间占用该月台的车辆才有装卸货进度；
 *
 *2、	根据车牌号，查询本外场目前的装卸货任务，根据装卸车任务类型显示该车辆的装货进度或卸货进度；
 *
 *3、	卸货进度可参考SUC-94查询卸车进度用例，装货进度可参考SUC-232查询装车进度用例；
 *
 *4、	时间段默认为当前该车辆所占用的时间段，不可修改；
 *
 *3	点击清空月台按钮；		
 *
 *	1、	如果为计划停靠的车辆，则只清空该车辆的计划停靠信息；
 *	2、	如果为实际使用的车辆，则生成历史记录，记录使用车牌号，开始使用时间和结束使用时间，开始使用时间为实际开始使用时间，结束使用时间为点击清空月台按钮时间；
 *
 *	4	关闭清空月台和修改月台界面；
 *
 *
 *
 *4、修改月台步骤
 *
 *序号	基本步骤	相关数据	补充步骤
 *
 *1	外场调度点击某个时间段内的车牌号；		1、	外场调度已查询出月台信息；
 *
 *2、	如果点击的车牌号所占时间段大于或包含当前时间，则弹出清空月台和修改月台界面（即不能修改历史记录），界面包含两个页签；
 *
 *2	外场调度选择修改月台页签；	
 *
 *外场装卸货数据	
 *
 *	1、	只有当前时间使用该月台的车辆才有装卸货进度；
 *
 *	2、	根据车牌号，查询本外场目前的装卸货任务，根据装卸车任务类型显示该车辆的装货进度或卸货进度；
 *
 *	3、	卸货进度可参考SUC-94查询卸车进度用例，装货进度可参考SUC-232查询装车进度用例；
 *
 *	4、	时间段默认为当前该车辆所占用的时间段；
 *
 *	5、	月台号默认为当前该车辆所使用或计划停靠的月台号；
 *
 *	6、	如果步骤1选择的车牌号为实际使用的车牌号，
 *
 *	则月台号不能修改，
 *
 *	起始时间不能修改，只能修改结束时间，
 *
 *	且结束时间不能小于当前时间；
 *
 *
 *
 *	7、	如果步骤1选择的车牌号为计划停靠信息的车牌号，
 *		则时间和月台号均可以修改，起始时间不能小于当前时间；
 *
 *3	点击确认修改月台；	
 *
 *	
 *	1、	如果修改的是从从发车计划、卸货月台分配生成过来的计划停靠信息，则提示“该车辆计划停靠的是发车计划或卸货月台分配的月台号，是否确认修改”，选择是，则进行修改，否则不修改，如果修改了月台号，则需要关联更新发车计划月台号或卸货分配月台号；
 *
 *	2、	不保留原计划停靠信息；
 *
 *
 *
 *4			关闭清空月台和修改月台界面；
 *
 *5、使用月台步骤
 *
 *序号	基本步骤	相关数据	补充步骤
 *	
 *	
 *	1	外场调度点击某个月台的空白时间段；	
 *	
 *		1、	弹出使用月台界面；
 *
 *		2、	月台号为点击行所在的月台号；
 *
 *		3、	车牌号来源车辆基础信息；
 *
 *		4、	时间段默认为当前时间点所在的最小时间间隔，如当前时间点为2012-06-11 17:06，则最小时间间隔为2012-06-11 17:00至2012-06-11 17:30；
 *
 *		5、	时间段选择范围为当前时间点前后12小时，以半点刻度为一项；
 *
 *2	选择车牌号和时间段；	车辆信息
 *
 *		1、	起始时间不能大于等于截止时间；
 *
 *		2、	截止时间不能小于当前时间；
 *
 *		3、	车牌号不能为空；
 *
 *3	点击确认使用；		
 *
 *		1、	如果所选时间段最小时间大于当前时间，
 *
 *		则该车辆为计划停靠信息，
 *		
 *		记录计划停靠车牌号，
 *		 
 *		预计开始使用时间和预计结束时间用时间，
 *
 *		预计开始使用时间为所选时间段最小时间，
 *
 *		预计结束时间为所选时间段最大时间；
 *
 *
 *		2、	如果所选时间段的最小时间小于当前时间，
 *
 *		则月台状态改为使用中，开始时间为步骤2选择的起始时间，
 *		
 *		预计结束时间为步骤2选择的结束时间；
 *
 *		3、	如果当前月台有正在使用的车辆，
 *
 *		则最所选时间段的最小时间不能小于当前正在使用的车辆的预计结束时间，
 *
 *		否则，给出提示“所选时间中已有车辆正在使用，请修改开始时间”；
 *
 *4			关闭使用月台界面，同时如果有对月台占用的操作，则刷新该月台的信息；
 * 
 * 
 * 
 * 1.7	业务规则
 * 
 * 序号	描述
 * 
 * SR-1	
 * 
 * 查询条件：
 * 
 * 1、	月台号默认为空，
 * 
 * 输入限制为整数，起始月台号和终止月台号可以只输入一个，
 * 
 * 如果只有一个月台号，
 * 
 * 则表示只显示该月台，
 * 
 * 如果两者都不为空，则前者必须小于后者
 * 
 * ，表示在此闭区间内所有月台号；
 * 
 * 2、	车型默认为空，
 * 
 * 来源于月台基础信息中的可停靠车辆信息，用下拉框显示；
 * 
 * 3、时间段默认为：
 * 
 * 起始时间为当前时间
 * 
 * ，截止时间为次日此时时间；时间跨度不超过24小时，起始时间不能大于截止时间，不可为空；
 * 
 * 4、	车牌号默认为空，信息来源于车辆基础信息；
 * 
 * 5、	只显示可用月台默认不选中，
 * 
 * 如果选择此条件则表明当前时间点所处的最小间隔内无实际使用月台；
 * 
 * 6、	外场默认为当前操作人所在部门，
 * 
 * 根据用户数据权限可做修改，但不能为空；
 * 
 * SR-2	查询显示规则；
 * 
 * 1、	按月台号码升序排列，
 * 
 * 相邻月台号如果具有相同货区，
 * 
 * 则货区合并；
 * 
 * 2、	当前时间点纵轴高亮显示；
 * 
 * 
 * 3、	如果月台计划停靠信息的车牌号所安排时间段小于当前时间点或当前时间点在此时间段内，
 * 
 * 则同时显示计划停靠车牌号和实际停靠车牌号，以不同颜色显示，若无实际使用车牌号，则不显示时间使用车牌号；如果月台计划停靠信息的车牌号所安排时间段大于当前时间点，则只显示该月台计划停靠车牌号；
 * 
 * 
 * 
 * 
 * 
 * 
 */
package com.deppon.foss.module.transfer.scheduling.server.service.impl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.define.PlatformDispatchConstants;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IOptimalPlatformDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateOptimalPlatformService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPlatformDispatchService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OptimalPlatformEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.FeedbackDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OptimalPlatformDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDistributeDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.CalculateOptimalPlatformException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
// TODO: Auto-generated Javadoc
/**
 * 计算最优月台.
 * 
 * @author 104306-foss-wangLong
 * @date 2012-11-15 上午6:32:27
 */
public class CalculateOptimalPlatformService implements
		ICalculateOptimalPlatformService {
	/**
	 * 
	 * The Constant LOGGER.
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CalculateOptimalPlatformService.class);
	/**
	 * 
	 * 月台信息service.
	 * 
	 */
	private IPlatformService platformService;
	/**
	 * 月台分配service.
	 * 
	 */
	private IPlatformDispatchService platformDispatchService;
	/**
	 * 交接单service.
	 */
	private IHandOverBillService handOverBillService;
	/**
	 * 走货路径service.
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	/**
	 * 库区service.
	 */
	private IGoodsAreaService goodsAreaService;
	/**
	 * 车辆信息service.
	 * 
	 */
	private IVehicleService vehicleService;
	/**
	 * 
	 * 部门信息service.
	 * 
	 */
	//未使用-352203
//	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 配置参数service.
	 * 
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 外场 Service.
	 */
	private IOutfieldService outfieldService;
	/**
	 * The org administrative info
	 * service.
	 * 
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * The vehicle agency dept service.
	 * 
	 */
	private IVehicleAgencyDeptService vehicleAgencyDeptService;
	/**
	 * The sale department service.
	 * 
	 */
	private ISaleDepartmentService saleDepartmentService;
	private IOptimalPlatformDao optimalPlatformDao;
	/**
	 * 
	 * 根据部门编码来查询部门名称
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-25 下午3:21:51
	 */
	private String getOrgNameByCode(String code) {
		if (code == null)
			return "";
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(code);
		if (orgEntity != null) {
			return orgEntity.getName();
		}
		return "";
	}
	/**
	 * 计算最优月台</br> 正在使用, 未使用的月台都参与计算.
	 * 
	 * 
	 * @param handOverBillNoList
	 *            交接单编号List
	 * @return 返回月台基础信息
	 * @throws CalculateOptimalPlatformException
	 *             the calculate optimal
	 *             platform exception
	 * @author 104306-foss-wangLong
	 * @date 2012-11-15 上午6:33:11
	 * @see #calcOptimalPlatform(List,
	 *      Map)
	 */
	@Transactional(readOnly = true) public List<OptimalPlatformDto> calcOptimalPlatform(
			List<String> handOverBillNoList)
			throws CalculateOptimalPlatformException {
		List<OptimalPlatformDto> platformList = calcOptimalPlatform(
				handOverBillNoList,
				new HashMap<String, PlatformDistributeEntity>());
		return platformList;
	}
	/**
	 * 计算最优月台</br> 仅计算未使用(空闲)的月台.
	 * 
	 * 
	 * @param handOverBillNoList
	 *            交接单编号List
	 * @return 返回月台基础信息
	 * @throws CalculateOptimalPlatformException
	 *             the calculate optimal
	 *             platform exception
	 * @author 104306-foss-wangLong
	 * @date 2012-11-15 上午6:33:11
	 * @see #calcOptimalPlatform(List,
	 *      Map)
	 */
	public List<OptimalPlatformDto> calcUnusedOptimalPlatform(
			List<String> handOverBillNoList)
			throws CalculateOptimalPlatformException {
		// 正在使用的月台
		Map<String, PlatformDistributeEntity> usingPlatformMap = platformDispatchService
				.queryUseingPlatform();
		List<OptimalPlatformDto> platformList = calcOptimalPlatform(
				handOverBillNoList, usingPlatformMap);
		return platformList;
	}
	/**
	 * 计算最优月台.
	 * 
	 * 
	 * @param handOverBillNoList
	 *            交接单编号List
	 * @param usingPlatformMap
	 *            正在使用中的月台
	 * @return the list
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午8:42:32
	 */
	private List<OptimalPlatformDto> calcOptimalPlatform(
			List<String> handOverBillNoList,
			Map<String, PlatformDistributeEntity> usingPlatformMap) {
		long begin = System.currentTimeMillis();
		LOGGER.info("开始计算最优月台:{} ms,", begin);
		String deptCode = FossUserContext.getCurrentDeptCode();
		// 获取当前部门对应的外场编码
		String organizationCode = getTransferCenterCode(deptCode);
		// 传入交接单集合为空,.
		if (handOverBillNoList == null || handOverBillNoList.isEmpty()) {
			LOGGER.info("交接单编号集合为" + handOverBillNoList);
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "参数错误.交接单编号List为空." });
		}
		// 取一个交接单号
		String handOverBillNo = handOverBillNoList.get(0);
		HandOverBillEntity handOverBillEntity = handOverBillService
				.queryHandOverBillByNo(handOverBillNo);
		if (handOverBillEntity == null) {
			// 交接单不存在
			LOGGER.info("交接单不存在. 交接单号  = " + handOverBillNo);
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "交接单不存在. 交接单号  " + handOverBillNo });
		}
		// 车牌号
		String vehicleNo = handOverBillEntity.getVehicleNo();
		if (StringUtil.isBlank(vehicleNo)) {
			LOGGER.info("交接单中车牌号为空..");
			// 异常 车牌号不存在..
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "交接单中车牌号为空." });
		}
		// 查询车辆信息
		VehicleAssociationDto vehicleAssociationDto = vehicleService
				.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
		if (vehicleAssociationDto == null) {
			// 异常 车辆不存在
			LOGGER.info("综合车辆信息不存在,车牌号  = " + vehicleNo);
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "车辆信息不存在,车牌号  :" + vehicleNo });
		}
		// 车型编码
		String vehicleLengthCode = vehicleAssociationDto.getVehicleLengthCode();
		if (vehicleLengthCode == null) {
			// 异常 车辆车型编码为null
			LOGGER.info("车辆车型编码为空,车牌号:" + vehicleNo);
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "车辆车型编码为空,车牌号:" + vehicleNo });
		}
		LOGGER.info("验证参数完成,共耗时:{}ms", System.currentTimeMillis() - begin);
		// 根据库区虚拟编码(去向)分组. key : 库区虚拟编码
		// value： 库区总重量
		Map<String, GoodsWeight> groupGoodAreaWeightMap = getHandOverBillDetailMapGroupGoodArea(handOverBillNoList);
		LOGGER.info("根据交接单去向分组，共耗时:{}ms", System.currentTimeMillis() - begin);
		if (!CollectionUtils.isEmpty(groupGoodAreaWeightMap)) {
			GoodsWeight noPath = groupGoodAreaWeightMap.get(null);
			if (noPath != null) {
				LOGGER.info("不在走货路径的货物信息:{}, ", noPath.toString());
				// 删除掉无走货路径的 不参与统计，计算.
				groupGoodAreaWeightMap.remove(null);
			}
		}
		List<GoodsWeight> groupGoodAreaWeightList = new ArrayList<GoodsWeight>(
				groupGoodAreaWeightMap.values());
		// 货物总重量排序
		Collections.sort(groupGoodAreaWeightList,
				new Comparator<GoodsWeight>() {
					public int compare(GoodsWeight o1, GoodsWeight o2) {
						return o2.getWeight().compareTo(o1.getWeight());
					}
				});
		// 本车货物total重量 不包含无走货路径的
		double sumWeight = getSumWeight(groupGoodAreaWeightList);
		LOGGER.info("货物总量[不包含无走货路径的] . = " + sumWeight);
		if (sumWeight <= 0) {
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "货物(不包含无走货路径的)总重量为0" });
		}
		// 80%货物的重量 不包含无走货路径的
		// 如果没有配置 默认为0.8
		double eightypercentSumWeight = sumWeight
				* getGoodWeightPercent(organizationCode);
		LOGGER.info("获取货物总量的80%:{},  共耗时:{} ", eightypercentSumWeight,
				System.currentTimeMillis() - begin);
		// 获取80%的本车货物去向
		List<GoodsWeight> eightyPercentGoodsList = getEightyPercentGoods(
				groupGoodAreaWeightList, eightypercentSumWeight);
		// 根据车长度 月台可用状态 查询月台
		List<PlatformEntity> platformList = queryPlatformListByCondition(
				organizationCode, vehicleLengthCode);
		if (platformList == null || platformList.isEmpty()) {
			// 没有合适月台
			LOGGER.info("综合月台接口返回 , 没有合适的停靠月台, 车型= " + vehicleLengthCode);
			return new ArrayList<OptimalPlatformDto>();
		}
		Map<String, PlatformEntity> platformMap = convertPlatformMapByVirtualCode(platformList);
		List<PlatformDistance> resutListPlatform = Collections
				.synchronizedList(new ArrayList<PlatformDistance>());
		long calcBegin = System.currentTimeMillis();
		LOGGER.info("开始计算: {} ms", calcBegin);
		for (PlatformEntity platformEntity : platformList) {
			// 月台虚拟编码
			String platformVirtualCode = platformEntity.getVirtualCode();
			// 过滤掉正在使用的月台 不参与计算
			if (usingPlatformMap != null
					&& usingPlatformMap.containsKey(platformVirtualCode)) {
				continue;
			}
			double distanceWeight = 0;
			for (GoodsWeight goodsWeight : eightyPercentGoodsList) {
				if (!resutListPlatform.isEmpty()) {
					int lastItemIndex = resutListPlatform.size() - 1;
					PlatformDistance lastItem = resutListPlatform
							.get(lastItemIndex);
					if (distanceWeight > lastItem.getDistance()) {
						break;
					}
				}
				// 库区虚拟编码
				String goodsAreaVirtualCode = goodsWeight
						.getGoodsAreaVirtualCode();
				// 重量
				Double weight = goodsWeight.getWeight();
				// 库区到 某个月台的距离 调用综合接口
				Double distance = getPlatformGoodsAreaDistance(
						organizationCode, platformVirtualCode,
						goodsAreaVirtualCode);
				if (distance == null) {
					continue;
				} else {
					// 重量 * 距离
					distanceWeight += weight * distance;
				}
			}
			PlatformDistance platformDistance = new PlatformDistance(
					platformEntity.getVirtualCode(), distanceWeight);
			resutListPlatform.add(platformDistance);
			LOGGER.info(platformDistance.toString());
			sort(resutListPlatform);
			LOGGER.info("排序后的月台距重： " + resutListPlatform);
		}
		LOGGER.info("计算完成， 共耗时: {} ms", System.currentTimeMillis() - calcBegin);
		// 月台虚拟编号List
		LOGGER.info("月台虚拟编号集合:" + resutListPlatform);
		// 包装月台dto
		List<OptimalPlatformDto> result = warpOptimalPlatformDto(platformMap,
				resutListPlatform, organizationCode);
		LOGGER.info("返回结果:" + result);
		LOGGER.info("计算最优月台共耗时:{} ms,", System.currentTimeMillis() - begin);
		return result;
	}
	/**
	 * 计算最优月台</br> 正在使用, 未使用的月台都参与计算
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-15 上午6:33:11
	 * @param truckTaskDetailId
	 *            任务车辆明细ID
	 * @see #calcOptimalPlatform(List,
	 *      Map)
	 * @return 返回月台基础信息List
	 */
	@Override public List<OptimalPlatformDto> calcOptimalPlatform(
			String truckTaskDetailId) throws CalculateOptimalPlatformException {
		// 取得该任务下的所有的月台
		List<OptimalPlatformEntity> optimalPlatformList = optimalPlatformDao
				.queryListByTruckTaskDetailId(truckTaskDetailId);
		// list类型转换
		List<PlatformEntity> platformlist = new ArrayList<PlatformEntity>();
		for (OptimalPlatformEntity opt : optimalPlatformList) {
			PlatformEntity entity = new PlatformEntity();
			entity.setPlatformCode(opt.getPlatformNo());
			entity.setVirtualCode(opt.getPlatformVirtualCode());
			entity.setHasLift(opt.getIfHasLift());
			entity.setVehicleName(opt.getVehicleTypeName());
			platformlist.add(entity);
		}
		// 月台信息转换为map key对象虚拟编号 value
		// 实体对象
		Map<String, PlatformEntity> platformMap = convertPlatformMapByVirtualCode(platformlist);
		Map<String, Date> endTimeMap = queryPlatformDistributeEndTimeMap();
		// 根据月台虚拟编号关联月台实体
		List<OptimalPlatformDto> result = new CopyOnWriteArrayList<OptimalPlatformDto>();
		Date nowDate = new Date();
		for (OptimalPlatformEntity optimalPlatformEntity : optimalPlatformList) {
			String virtualCode = optimalPlatformEntity.getPlatformVirtualCode();
			PlatformEntity platformEntity = platformMap.get(virtualCode);
			OptimalPlatformDto optimalPlatformDto = new OptimalPlatformDto();
			optimalPlatformDto.setPlatformEntity(platformEntity);
			optimalPlatformDto.setIsUsable(FossConstants.YES);
			// 最近可用开始时间
			String usableTime = DateUtils.convert(nowDate);
			if (endTimeMap.containsKey(virtualCode)) {
				// 不可用
				optimalPlatformDto.setIsUsable(FossConstants.NO);
				Date date = endTimeMap.get(virtualCode);
				if (date == null) {
					usableTime = "未知";
				} else {
					usableTime = DateUtils.convert(date);
				}
			}
			optimalPlatformDto.setUsableTime(usableTime);
			result.add(optimalPlatformDto);
		}
		return result;
	}
	/**
	 * 计算最优月台</br> 仅计算未使用(空闲)的月台
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-15 上午6:33:11
	 * @param truckTaskDetailId
	 *            任务车辆明细ID
	 * @see #calcOptimalPlatform(List,
	 *      Map)
	 * @return 返回月台基础信息List
	 */
	@Override public List<OptimalPlatformDto> calcUnusedOptimalPlatform(
			String truckTaskDetailId) throws CalculateOptimalPlatformException {
		// 取得该任务下的所有的月台
		List<OptimalPlatformEntity> optimalPlatformList = optimalPlatformDao
				.queryListByTruckTaskDetailId(truckTaskDetailId);
		// list类型转换
		List<PlatformEntity> platformlist = new ArrayList<PlatformEntity>();
		for (OptimalPlatformEntity opt : optimalPlatformList) {
			PlatformEntity entity = new PlatformEntity();
			entity.setPlatformCode(opt.getPlatformNo());
			entity.setVirtualCode(opt.getPlatformVirtualCode());
			entity.setHasLift(opt.getIfHasLift());
			entity.setVehicleName(opt.getVehicleTypeName());
			platformlist.add(entity);
		}
		// 月台信息转换为map key对象虚拟编号 value
		// 实体对象
		Map<String, PlatformEntity> platformMap = convertPlatformMapByVirtualCode(platformlist);
		Map<String, Date> endTimeMap = queryPlatformDistributeEndTimeMap();
		// 根据月台虚拟编号关联月台实体
		List<OptimalPlatformDto> result = new CopyOnWriteArrayList<OptimalPlatformDto>();
		Date nowDate = new Date();
		for (OptimalPlatformEntity optimalPlatformEntity : optimalPlatformList) {
			String virtualCode = optimalPlatformEntity.getPlatformVirtualCode();
			PlatformEntity platformEntity = platformMap.get(virtualCode);
			if (platformEntity != null) {
				continue;
			}
			OptimalPlatformDto optimalPlatformDto = new OptimalPlatformDto();
			optimalPlatformDto.setPlatformEntity(platformEntity);
			optimalPlatformDto.setIsUsable(FossConstants.YES);
			// 最近可用开始时间
			String usableTime = DateUtils.convert(nowDate);
			if (endTimeMap.containsKey(virtualCode)) {
				// 不可用
				optimalPlatformDto.setIsUsable(FossConstants.NO);
				Date date = endTimeMap.get(virtualCode);
				if (date == null) {
					usableTime = "未知";
				} else {
					usableTime = DateUtils.convert(date);
				}
			}
			optimalPlatformDto.setUsableTime(usableTime);
			result.add(optimalPlatformDto);
		}
		return result;
	}
	/**
	 * 删除最优月台JOB.
	 * 
	 * @author huyue
	 * @date 2013-4-10 下午7:13:28
	 */
	public void deleteOptimalPlatformJob(String truckTaskDetailId) {
		optimalPlatformDao.deleteByTruckTaskDetailId(truckTaskDetailId);
	}
	/**
	 * 计算最优月台JOB.
	 * 
	 * 
	 * @param platformMap
	 *            the platform map
	 * @param resutListPlatform
	 *            the resut list
	 *            platform
	 * @param organizationCode
	 *            the organization code
	 * @return the list
	 * @author huyue
	 * @date 2013-3-13 下午5:12:31
	 */
	public void calcOptimalPlatformJob(String truckTaskDetailId,
			List<String> handOverBillNoList, String deptOrgCode,
			String vehicleNo) {
		// 传入交接单集合为空,.
		if (handOverBillNoList == null || handOverBillNoList.isEmpty()) {
			LOGGER.info("交接单编号集合为" + handOverBillNoList);
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "参数错误.交接单编号List为空." });
		}
		// 取一个交接单号
		String handOverBillNo = handOverBillNoList.get(0);
		HandOverBillEntity handOverBillEntity = handOverBillService
				.queryHandOverBillByNo(handOverBillNo);
		if (handOverBillEntity == null) {
			// 交接单不存在
			LOGGER.info("交接单不存在. 交接单号  = " + handOverBillNo);
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "交接单不存在. 交接单号  " + handOverBillNo });
		}
		// 获取当前部门对应的外场编码
		String organizationCode = getTransferCenterCode(deptOrgCode);
		// 查询车辆信息
		VehicleAssociationDto vehicleAssociationDto = vehicleService
				.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
		if (vehicleAssociationDto == null) {
			// 异常 车辆不存在
			LOGGER.info("综合车辆信息不存在,车牌号  = " + vehicleNo);
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "车辆信息不存在,车牌号  :" + vehicleNo });
		}
		// 车型编码
		String vehicleLengthCode = vehicleAssociationDto.getVehicleLengthCode();
		if (vehicleLengthCode == null) {
			// 异常 车辆车型编码为null
			LOGGER.info("车辆车型编码为空,车牌号:" + vehicleNo);
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "车辆车型编码为空,车牌号:" + vehicleNo });
		}
		// 根据库区虚拟编码(去向)分组. key : 库区虚拟编码
		// value： 库区总重量
		Map<String, GoodsWeight> groupGoodAreaWeightMap = getHandOverBillDetailMapGroupGoodArea(handOverBillNoList);
		if (!CollectionUtils.isEmpty(groupGoodAreaWeightMap)) {
			GoodsWeight noPath = groupGoodAreaWeightMap.get(null);
			if (noPath != null) {
				LOGGER.info("不在走货路径的货物信息:{}, ", noPath.toString());
				// 删除掉无走货路径的 不参与统计，计算.
				groupGoodAreaWeightMap.remove(null);
			}
		}
		List<GoodsWeight> groupGoodAreaWeightList = new ArrayList<GoodsWeight>(
				groupGoodAreaWeightMap.values());
		// 货物总重量排序
		Collections.sort(groupGoodAreaWeightList,
				new Comparator<GoodsWeight>() {
					public int compare(GoodsWeight o1, GoodsWeight o2) {
						return o2.getWeight().compareTo(o1.getWeight());
					}
				});
		// 本车货物total重量 不包含无走货路径的
		double sumWeight = getSumWeight(groupGoodAreaWeightList);
		if (sumWeight <= 0) {
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "货物(不包含无走货路径的)总重量为0" });
		}
		// 80%货物的重量 不包含无走货路径的
		// 如果没有配置 默认为0.8
		double eightypercentSumWeight = sumWeight
				* getGoodWeightPercent(organizationCode);
		// 获取80%的本车货物去向
		List<GoodsWeight> eightyPercentGoodsList = getEightyPercentGoods(
				groupGoodAreaWeightList, eightypercentSumWeight);
		// 根据车长度 月台可用状态 查询月台
		// TODO
		List<PlatformEntity> platformList = queryPlatformListByCondition(
				organizationCode, vehicleLengthCode);
		if (platformList == null || platformList.isEmpty()) {
			// 没有合适月台
			LOGGER.info("综合月台接口返回 , 没有合适的停靠月台, 车型= " + vehicleLengthCode);
		}
		Map<String, PlatformEntity> platformMap = convertPlatformMapByVirtualCode(platformList);
		List<PlatformDistance> resutListPlatform = Collections
				.synchronizedList(new ArrayList<PlatformDistance>());
		for (PlatformEntity platformEntity : platformList) {
			// 月台虚拟编码
			String platformVirtualCode = platformEntity.getVirtualCode();
			double distanceWeight = 0;
			for (GoodsWeight goodsWeight : eightyPercentGoodsList) {
				if (!resutListPlatform.isEmpty()) {
					int lastItemIndex = resutListPlatform.size() - 1;
					PlatformDistance lastItem = resutListPlatform
							.get(lastItemIndex);
					if (distanceWeight > lastItem.getDistance()) {
						break;
					}
				}
				// 库区虚拟编码
				String goodsAreaVirtualCode = goodsWeight
						.getGoodsAreaVirtualCode();
				// 重量
				Double weight = goodsWeight.getWeight();
				// 循环查询所有可用月台,得到distance
				// TODO
				// 库区到 某个月台的距离 调用综合接口
				Double distance = getPlatformGoodsAreaDistance(
						organizationCode, platformVirtualCode,
						goodsAreaVirtualCode);
				if (distance == null) {
					continue;
				} else {
					// 重量 * 距离
					distanceWeight += weight * distance;
				}
			}
			PlatformDistance platformDistance = new PlatformDistance(
					platformEntity.getVirtualCode(), distanceWeight);
			resutListPlatform.add(platformDistance);
			LOGGER.info(platformDistance.toString());
			sort(resutListPlatform);
		}
		// 月台虚拟编号List
		// TODO
		// 整理数据并存表
		if (resutListPlatform == null) {
			LOGGER.info("没有筛选出任何可用月台");
		} else {
			// 包装月台entity
			for (PlatformDistance platformDistance : resutListPlatform) {
				String virtualCode = platformDistance.getPlatformCode();
				PlatformEntity platformEntity = platformMap.get(virtualCode);
				if (platformEntity == null) {
					continue;
				}
				OptimalPlatformEntity optimalPlatformEntity = new OptimalPlatformEntity();
				optimalPlatformEntity.setOptimalPlatformId(UUIDUtils.getUUID());
				optimalPlatformEntity.setTruckTaskDetailId(truckTaskDetailId);
				optimalPlatformEntity.setPlatformNo(platformEntity
						.getPlatformCode());
				optimalPlatformEntity.setPlatformVirtualCode(platformEntity
						.getVirtualCode());
				optimalPlatformEntity.setIfHasLift(platformEntity.getHasLift());
				optimalPlatformEntity.setVehicleTypeName(platformEntity
						.getVehicleName());
				optimalPlatformDao.addOptimalPlatform(optimalPlatformEntity);
			}
		}
	}
	/**
	 * 封装最优月台Dto</br> 1.最近可用时间 2.可用状态
	 * 
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-30 下午4:32:42
	 * @param platformMap
	 * @param resutListPlatform
	 */
	private List<OptimalPlatformDto> warpOptimalPlatformDto(
			Map<String, PlatformEntity> platformMap,
			List<PlatformDistance> resutListPlatform, String organizationCode) {
		if (resutListPlatform == null) {
			return null;
		}
		Map<String, Date> endTimeMap = queryPlatformDistributeEndTimeMap();
		// 根据月台虚拟编号关联月台实体
		List<OptimalPlatformDto> result = new CopyOnWriteArrayList<OptimalPlatformDto>();
		Date nowDate = new Date();
		for (PlatformDistance platformDistance : resutListPlatform) {
			String virtualCode = platformDistance.getPlatformCode();
			PlatformEntity platformEntity = platformMap.get(virtualCode);
			if (platformEntity == null) {
				continue;
			}
			OptimalPlatformDto optimalPlatformDto = new OptimalPlatformDto();
			optimalPlatformDto.setPlatformEntity(platformEntity);
			optimalPlatformDto.setIsUsable(FossConstants.YES);
			// 最近可用开始时间
			String usableTime = DateUtils.convert(nowDate);
			if (endTimeMap.containsKey(virtualCode)) {
				// 不可用
				optimalPlatformDto.setIsUsable(FossConstants.NO);
				Date date = endTimeMap.get(virtualCode);
				if (date == null) {
					usableTime = "未知";
				} else {
					usableTime = DateUtils.convert(date);
				}
			}
			optimalPlatformDto.setUsableTime(usableTime);
			result.add(optimalPlatformDto);
		}
		// 配置项 根据部门编码查询 最优月台个数
		int platFormCount = getOptimalPlatformResultCount(organizationCode);
		int resultSize = result.size();
		if (resultSize > platFormCount) {
			return result.subList(NumberConstants.ZERO, platFormCount);
		}
		return result;
	}
	/**
	 * 获取使用中的月台结束时间 即可用开始使用</br> map的key
	 * 为月台的虚拟编号 value为结束时间.
	 * 
	 * @return {@link java.util.Map}
	 * @author 104306-foss-wangLong
	 * @date 2012-11-30 下午3:13:20
	 */
	private Map<String, Date> queryPlatformDistributeEndTimeMap() {
		PlatformDistributeDto platformDistributeDto = new PlatformDistributeDto();
		PlatformDistributeEntity platformDistributeEntity = new PlatformDistributeEntity();
		// 状态 为正在使用
		platformDistributeEntity
				.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_USING);
		// 类型位 当前停靠
		platformDistributeEntity
				.setType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE);
		platformDistributeDto
				.setPlatformDistributeEntity(platformDistributeEntity);
		return platformDispatchService
				.queryPlatformDistributeEndTimeMap(platformDistributeDto);
	}
	/**
	 * 获取当前部门对应的外场.
	 * 
	 * @param deptCode
	 *            the dept code
	 * @return 外场编码
	 * @author 104306-foss-wangLong
	 * @date 2012-11-28 上午12:57:17
	 */
	private String getTransferCenterCode(String deptCode) {
		// 判断是不是本部门为空运总调.如果是则转换成对应外场后再返回
		String transferCode = outfieldService
				.queryTransferCenterByAirDispatchCode(deptCode);
		if (StringUtils.isEmpty(transferCode)) {
			return deptCode;
		} else {
			return transferCode;
		}
	}
	/**
	 * 月台信息转换为map key对象虚拟编号 value 实体对象
	 * convertPlatformMapByVirtualCode.
	 * 
	 * 
	 * @param platformList
	 *            the platform list
	 * 
	 * @return java.util.Map
	 * 
	 * @author 104306-foss-wangLong
	 * 
	 * @date 2012-11-22 下午8:57:10
	 */
	private Map<String, PlatformEntity> convertPlatformMapByVirtualCode(
			List<PlatformEntity> platformList) {
		Map<String, PlatformEntity> map = new HashMap<String, PlatformEntity>();
		for (PlatformEntity platformEntity : platformList) {
			map.put(platformEntity.getVirtualCode(), platformEntity);
		}
		return map;
	}
	/**
	 * 获取本车货物中80%的货物.
	 * 
	 * 
	 * @param groupGoodAreaWeightList
	 *            the group good area
	 *            weight list
	 * @param eightypercentSumWeight
	 *            the eightypercent sum
	 *            weight
	 * @return the eighty percent goods
	 * @author 104306-foss-wangLong
	 * @date 2012-11-20 下午1:35:42
	 */
	private List<GoodsWeight> getEightyPercentGoods(
			List<GoodsWeight> groupGoodAreaWeightList,
			double eightypercentSumWeight) {
		List<GoodsWeight> eightypercentSumList = new ArrayList<CalculateOptimalPlatformService.GoodsWeight>();
		double total = 0;
		for (GoodsWeight goodsWeight : groupGoodAreaWeightList) {
			total = total + goodsWeight.getWeight();
			eightypercentSumList.add(goodsWeight);
			LOGGER.info("total = " + total);
			if (total > eightypercentSumWeight) {
				break;
			}
		}
		return eightypercentSumList;
	}
	/**
	 * 本车货物总重量.
	 * 
	 * 
	 * @param groupGoodAreaWeightList
	 *            the group good area
	 *            weight list
	 * @return the sum weight
	 * @author 104306-foss-wangLong
	 * @date 2012-11-20 下午12:57:28
	 */
	private double getSumWeight(List<GoodsWeight> groupGoodAreaWeightList) {
		double sumWeight = 0;
		for (GoodsWeight goodsWeight : groupGoodAreaWeightList) {
			sumWeight = sumWeight + goodsWeight.getWeight();
		}
		return sumWeight;
	}
	/**
	 * 获取指定月台到库区的距离.
	 * 
	 * 
	 * @param organizationCode
	 *            外场编码
	 * @param platformVirtualCode
	 *            月台虚拟编码
	 * @param goodsAreaVirtualCode
	 *            库区虚拟编码
	 * @return the platform goods area
	 *         distance
	 * @author 104306-foss-wangLong
	 * @date 2012-11-17 上午11:53:24
	 */
	private Double getPlatformGoodsAreaDistance(String organizationCode,
			String platformVirtualCode, String goodsAreaVirtualCode) {
		LOGGER.info("获取月台到库区的距离,月台虚拟编号:{},", platformVirtualCode);
		long begin = System.currentTimeMillis();
		// 查询某一外场下月台到库区的距离
		Double distance = platformService.queryDistanceByPlatformGoodsAreaVir(
				organizationCode, platformVirtualCode, goodsAreaVirtualCode);
		if (distance == null) {
			LOGGER.info("没有获取月台到库区的距离, 月台编号:{}, 库区编号:{}, 共耗时:{} ms",
					new Object[] { platformVirtualCode, goodsAreaVirtualCode,
							System.currentTimeMillis() - begin });
			return null;
		}
		LOGGER.info("获取月台到库区的距离,月台虚拟编号:{}, 共耗时:{} ms", platformVirtualCode,
				System.currentTimeMillis() - begin);
		return distance.doubleValue();
	}
	/**
	 * 结果排序 按 距离 * 重量 total.
	 * 
	 * @param resutList
	 *            the resut list
	 * @author 104306-foss-wangLong
	 * @date 2012-11-17 下午12:14:39
	 */
	private void sort(List<PlatformDistance> resutList) {
		Collections.sort(resutList, new Comparator<PlatformDistance>() {
			public int compare(PlatformDistance o1, PlatformDistance o2) {
				return o1.getDistance().compareTo(o2.getDistance());
			}
		});
	}
	/**
	 * 根据车型查询 可用月台.
	 * 
	 * @param organizationCode
	 *            外场编码
	 * @param vehicleLengthName
	 *            车型
	 * @return {@link java.util.List}
	 * @author 104306-foss-wangLong
	 * @date 2012-11-16 下午12:12:57
	 */
	private List<PlatformEntity> queryPlatformListByCondition(
			String organizationCode, String vehicleLengthName) {
		List<PlatformEntity> list = platformService
				.queryPlatformListByVehicleType(organizationCode,
						vehicleLengthName);
		return list;
	}
	/**
	 * 根据交接单明细去向分组.
	 * 
	 * @param handOverBillNoList
	 *            the hand over bill no
	 *            list
	 * @return the hand over bill detail
	 *         map group good area
	 * @author 104306-foss-wangLong
	 * @date 2012-11-15 下午2:25:23
	 */
	protected Map<String, GoodsWeight> getHandOverBillDetailMapGroupGoodArea(
			List<String> handOverBillNoList) {
		Map<String, GoodsWeight> groupGoodAreaWeightMap = new HashMap<String, CalculateOptimalPlatformService.GoodsWeight>();
		// 交接单
		for (String handOverBillNo : handOverBillNoList) {
			HandOverBillEntity handOverBillEntity = handOverBillService
					.queryHandOverBillByNo(handOverBillNo);
			// 交接单明细List
			List<HandOverBillDetailEntity> handOverBillDetailList = handOverBillService
					.queryHandOverBillDetailByNo(handOverBillNo);
			if (handOverBillDetailList == null
					|| handOverBillDetailList.isEmpty()) {
				LOGGER.info("根据交接单号查询  交接单明细  返回 明细集合为空  . 交接单号 : "
						+ handOverBillNo);
				throw new CalculateOptimalPlatformException(
						PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
						new Object[] { "交接单明细 为空  " });
			}
			for (HandOverBillDetailEntity handOverBillDetailEntity : handOverBillDetailList) {
				String wayBillNoDetail = handOverBillDetailEntity
						.getWaybillNo();
				String handOverBillNoDetail = handOverBillDetailEntity
						.getHandOverBillNo();
				// 交接单明细流水
				List<HandOverBillSerialNoDetailEntity> handOverBillSerialNoDetailList = handOverBillService
						.getHandOverBillSerialNoDetailsByWayBillNo(
								wayBillNoDetail, handOverBillNoDetail);
				if (handOverBillSerialNoDetailList == null
						|| handOverBillSerialNoDetailList.isEmpty()) {
					// 交接单明下面不存在 流水
					continue;
				}
				HandOverBillSerialNoDetailEntity handOverBillSerialNoDetail = handOverBillSerialNoDetailList
						.get(0);
				// 运单号
				String wayBillNoHandOverBillSerialNoDetail = handOverBillSerialNoDetail
						.getWaybillNo();
				// 流水号
				String serialNOHandOverBillSerialNoDetail = handOverBillSerialNoDetail
						.getSerialNo();
				// 因为是交接单刚出发就计算,所以要用出发部门
				String orgCode = handOverBillEntity.getArriveDeptCode();
				// 库区虚拟code
				String goodsAreaVirtualCode = getGoodsAreaVirtualCode(
						wayBillNoHandOverBillSerialNoDetail,
						serialNOHandOverBillSerialNoDetail, orgCode);
				LOGGER.info("库区虚拟编码 : {}", goodsAreaVirtualCode);
				GoodsWeight goodsWeight = groupGoodAreaWeightMap
						.get(goodsAreaVirtualCode);
				Double handOverBillWeight = handOverBillDetailEntity
						.getWeight().doubleValue();
				if (goodsWeight == null) {
					goodsWeight = new GoodsWeight(handOverBillWeight,
							goodsAreaVirtualCode);
				} else {
					double weight = goodsWeight.getWeight()
							+ handOverBillWeight;
					goodsWeight.setWeight(weight);
				}
				groupGoodAreaWeightMap.put(goodsAreaVirtualCode, goodsWeight);
			}
		}
		return groupGoodAreaWeightMap;
	}
	/**
	 * 根据下一部门得到该外场相应库区虚拟编号 综合管理接口.
	 * 
	 * @param wayBillNo
	 *            运单号
	 * @param serialNO
	 *            明细流水号
	 * @param orgCode
	 *            部门
	 * @return 库区虚拟编码
	 * @author 104306-foss-wangLong
	 * @date 2012-11-15 下午12:57:41
	 * @see #getNextOrgCode(String,
	 *      String, String)
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService#queryGoodsAreaByCondition(GoodsAreaEntity,
	 *      int, int)
	 */
	private String getGoodsAreaVirtualCode(String wayBillNo, String serialNO,
			String orgCode) {
		String nextOrgCode = getNextOrgCode(wayBillNo, serialNO, orgCode);
		// 不再走货路径.
		if (StringUtil.isBlank(nextOrgCode)) {
			LOGGER.info("不在走货路径, 运单号:{}, 到达部门{}, 流水号:{},", new Object[] {
					wayBillNo, getOrgNameByCode(orgCode), serialNO });
			return null;
		}
		// 根据下一部门得到该外场相应货区编号 综合管理接口
		List<GoodsAreaEntity> list = new ArrayList<GoodsAreaEntity>();
		if (CollectionUtils.isEmpty(list)) {
			// 根据本组织和下一组织获取库区实体
			GoodsAreaEntity c = goodsAreaService
					.queryGoodsAreaByArriveRegionCode(orgCode, nextOrgCode,
							null);
			if (c != null) {
				list.add(c);
			}
			if (CollectionUtils.isEmpty(list)) {
				// 首先判断是否空运总调
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(orgCode);
				// 部门是空运总调，入库到相应外场空运货区
				if (StringUtils.equals(FossConstants.YES,
						orgAdministrativeInfoEntity.getAirDispatch())) {
					// 判断是不是本部门为空运总调.如果是则转换成对应外场后再返回
					String transferCode = outfieldService
							.queryTransferCenterByAirDispatchCode(orgCode);
					list = goodsAreaService
							.queryGoodsAreaListByType(
									transferCode,
									DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT);
				}
				if (CollectionUtils.isEmpty(list)) {
					// 判断下一部门是否是外发网点
					OuterBranchEntity outerBranchEntity = vehicleAgencyDeptService
							.queryOuterBranchByBranchCode(
									nextOrgCode,
									DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
					if (outerBranchEntity != null) {
						// 偏线货区
						list = goodsAreaService
								.queryGoodsAreaListByType(
										orgCode,
										DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER);
					}
					if (CollectionUtils.isEmpty(list)) {
						// 判断下一部门是否是当前外场的驻地派送部
						SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
								.querySaleDepartmentByCode(nextOrgCode);
						if (saleDepartmentEntity != null
								&& StringUtils.equals(FossConstants.YES,
										saleDepartmentEntity.getStation())
								&& StringUtils.equals(saleDepartmentEntity
										.getTransferCenter(), orgCode)) {
							// 派送货区
							list = goodsAreaService
									.queryGoodsAreaListByType(
											orgCode,
											DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
						}
						if (CollectionUtils.isEmpty(list)) {
							throw new CalculateOptimalPlatformException(
									PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
									new Object[] { "根据本部门:"
											+ getOrgNameByCode(orgCode)
											+ "和走货路径返回的下一站部门:"
											+ getOrgNameByCode(nextOrgCode)
											+ ", 获取下一站对应库区失败" });
						}
					}
				}
			}
		}
		// 库区虚拟编码
		String goodsAreaVirtualCode = list.get(0).getVirtualCode();
		if (goodsAreaVirtualCode == null) {
			LOGGER.info("根据走货路径返回的下一站部门:" + getOrgNameByCode(nextOrgCode)
					+ ", 查询外场编号失败, 综合接口返回NULL");
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "根据走货路径返回的下一站部门:"
							+ getOrgNameByCode(nextOrgCode)
							+ ", 获取下一站对应库区失败, 综合接口返回空!" });
		}
		return goodsAreaVirtualCode;
	}
	/**
	 * 查询走货路径 获取下一部门的code.
	 * 
	 * 
	 * @param wayBillNo
	 *            运单号
	 * @param serialNO
	 *            明细流水号
	 * @param orgCode
	 *            部门
	 * @return the next org code
	 * @author 104306-foss-wangLong
	 * @date 2012-11-15 下午1:08:40
	 * @see ICalculateTransportPathService#getNextOrgAndTime(String,
	 *      String, String)
	 * @see PathDetailEntity#getObjectiveOrgCode()
	 */
	private String getNextOrgCode(String wayBillNo, String serialNO,
			String orgCode) {
		FeedbackDto feedbackDto = null;
		try {
			feedbackDto = calculateTransportPathService.getNextOrgAndTime(
					wayBillNo, serialNO, orgCode);
		} catch (TfrBusinessException e) {
			throw new CalculateOptimalPlatformException(
					PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE,
					new Object[] { "查询走货路径失败, 运单编号:" + wayBillNo + ", 流水号:"
							+ serialNO + ", 当前部门:" + getOrgNameByCode(orgCode) });
		}
		if (feedbackDto == null) {
			LOGGER.info("查询走货路径失败. ");
			return null;
		}
		int result = feedbackDto.getResult();
		if (result == TransportPathConstants.STATUS_WRONG) {
			LOGGER.info("查询走货路径失败，不在走货路径,运单号:{}", wayBillNo);
			return null;
		}
		if (result == TransportPathConstants.STATUS_NODATA) {
			LOGGER.info("查询走货路径失败，在路径但没有下一部门,运单号:{}", wayBillNo);
			return null;
		}
		PathDetailEntity pathDetailEntity = feedbackDto.getPathDetailEntity();
		if (pathDetailEntity.getObjectiveOrgCode() == null) {
			LOGGER.info("走货路径服务接口返回错误. 下一到达部门为:"
					+ getOrgNameByCode(pathDetailEntity.getObjectiveOrgCode()));
			return null;
		}
		return pathDetailEntity.getObjectiveOrgCode();
	}
	/**
	 * 获取 计算最优月台 货物总体积取值百分比.
	 * 
	 * @param orgCode
	 *            the org code
	 * @return the good weight percent
	 * @author 104306-foss-wangLong
	 * @date 2013-1-17 上午11:48:45
	 */
	private double getGoodWeightPercent(String orgCode) {
		Number valueNumber = getConfigurationParams(
				orgCode,
				ConfigurationParamsConstants.TFR_PARM__OPTIMAL_PLATFORM_GOODS_PERCENT,
				PlatformDispatchConstants.DEFAULT_OPTIMAL_PLATFORM_GOODS_PERCENT);
		LOGGER.info("获取 计算最优月台 货物总体积取值百分比：{}", valueNumber);
		return valueNumber.doubleValue();
	}
	/**
	 * 获取 最优月台个数 配置项.
	 * 
	 * @param orgCode
	 *            the org code
	 * @return the optimal platform
	 *         result count
	 * @author 104306-foss-wangLong
	 * @date 2013-1-17 上午11:48:45
	 */
	private int getOptimalPlatformResultCount(String orgCode) {
		Number valueNumber = getConfigurationParams(
				orgCode,
				ConfigurationParamsConstants.TFR_PARM__OPTIMAL_PLATFORM_RESULT_COUNT,
				PlatformDispatchConstants.DEFAULT_OPTIMAL_PLATFORM_RESULT_COUNT);
		int count = valueNumber.intValue();
		LOGGER.info(" 获取  最优月台个数 配置项：{}", count);
		return count <= NumberConstants.ZERO ? PlatformDispatchConstants.DEFAULT_OPTIMAL_PLATFORM_RESULT_COUNT
				: count;
	}
	/**
	 * 查询配置参数.
	 * 
	 * @param orgCode
	 *            the org code
	 * @param parmCode
	 *            the parm code
	 * @param defaultValue
	 *            the default value
	 * @return the configuration params
	 * @author 104306-foss-wangLong
	 * @date 2013-1-17 下午12:02:30
	 */
	private Number getConfigurationParams(String orgCode, String parmCode,
			Number defaultValue) {
		ConfigurationParamsEntity configurationParamsEntity = null;
		try {
			configurationParamsEntity = configurationParamsService
					.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
							parmCode, orgCode);
		} catch (BusinessException e) {
			LOGGER.info("读取配置项出错.错误信息:{}", e);
			return defaultValue;
		}
		if (configurationParamsEntity == null
				|| StringUtil.isBlank(configurationParamsEntity.getConfValue())) {
			return defaultValue;
		}
		return Double.valueOf(configurationParamsEntity.getConfValue());
	}
	/**
	 * The Class PlatformDistance.
	 * 
	 * 
	 */
	private class PlatformDistance {
		/** 月台 对应月台虚拟编号. */
		private String platformCode;
		/** 距离 * 重量 total. */
		private Double distance;
		/**
		 * Instantiates a new platform
		 * distance.
		 * 
		 * @param platformCode
		 *            the platform code
		 * @param distance
		 *            the distance
		 * @author 104306-foss-wangLong
		 * @date 2012-11-16 下午8:27:45
		 */
		public PlatformDistance(String platformCode, Double distance) {
			super();
			this.platformCode = platformCode;
			this.distance = distance;
		}
		/**
		 * Gets the platform code.
		 * 
		 * @return the platform code
		 */
		public String getPlatformCode() {
			return platformCode;
		}
		/**
		 * Gets the distance.
		 * 
		 * @return the distance
		 */
		public Double getDistance() {
			return distance;
		}
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.lang.Object#toString()
		 */
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("[platformCode,").append(platformCode)
					.append("]");
			stringBuilder.append("[distance,").append(distance).append("]");
			return stringBuilder.toString();
		}
	}
	/**
	 * The Class GoodsWeight.
	 */
	private class GoodsWeight {
		/** 重量. */
		private Double weight;
		/** 去向. */
		private String goodsAreaVirtualCode;
		/**
		 * Instantiates a new goods
		 * weight.
		 * 
		 * @param weight
		 *            the weight
		 * @param goodsAreaVirtualCode
		 *            the goods area
		 *            virtual code
		 */
		public GoodsWeight(Double weight, String goodsAreaVirtualCode) {
			this.weight = weight;
			this.goodsAreaVirtualCode = goodsAreaVirtualCode;
		}
		/**
		 * Gets the weight.
		 * 
		 * @return the weight
		 */
		public Double getWeight() {
			return weight;
		}
		/**
		 * Sets the weight.
		 * 
		 * @param weight
		 *            the new weight
		 */
		public void setWeight(Double weight) {
			this.weight = weight;
		}
		/**
		 * Gets the goods area virtual
		 * code.
		 * 
		 * @return the goods area
		 *         virtual code
		 */
		public String getGoodsAreaVirtualCode() {
			return goodsAreaVirtualCode;
		}
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.lang.Object#toString()
		 */
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("[weight,").append(weight).append("]");
			stringBuilder.append("[goodsAreaVirtualCode,")
					.append(goodsAreaVirtualCode).append("]");
			return stringBuilder.toString();
		}
	}
	/**
	 * 设置platformService.
	 * 
	 * @param platformService
	 *            the platformService to
	 *            set
	 */
	public void setPlatformService(IPlatformService platformService) {
		this.platformService = platformService;
	}
	/**
	 * 设置platformDispatchService.
	 * 
	 * @param platformDispatchService
	 *            the
	 *            platformDispatchService
	 *            to set
	 */
	public void setPlatformDispatchService(
			IPlatformDispatchService platformDispatchService) {
		this.platformDispatchService = platformDispatchService;
	}
	/**
	 * 设置handOverBillService.
	 * 
	 * @param handOverBillService
	 *            the
	 *            handOverBillService to
	 *            set
	 */
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	/**
	 * 设置calculateTransportPathService.
	 * 
	 * @param calculateTransportPathService
	 *            the
	 *            calculateTransportPathService
	 *            to set
	 */
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	/**
	 * 设置goodsAreaService.
	 * 
	 * @param goodsAreaService
	 *            the goodsAreaService
	 *            to set
	 */
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	/**
	 * 设置vehicleService.
	 * 
	 * @param vehicleService
	 *            the vehicleService to
	 *            set
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
	/**
	 * 设置orgAdministrativeInfoComplexService
	 * .
	 * 
	 * @param orgAdministrativeInfoComplexService
	 *            the
	 *            orgAdministrativeInfoComplexService
	 *            to set
	 */
/*	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}*/
	/**
	 * 设置configurationParamsService.
	 * 
	 * @param configurationParamsService
	 *            the
	 *            configurationParamsService
	 *            to set
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	/**
	 * 设置outfieldService.
	 * 
	 * @param outfieldService
	 *            the new outfield
	 *            service
	 * @author huyue
	 * @date 2013-3-5 下午2:02:15
	 */
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	/**
	 * Sets the org administrative info
	 * service.
	 * 
	 * @param orgAdministrativeInfoService
	 *            the new org
	 *            administrative info
	 *            service
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * Sets the vehicle agency dept
	 * service.
	 * 
	 * @param vehicleAgencyDeptService
	 *            the new vehicle agency
	 *            dept service
	 */
	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}
	/**
	 * Sets the sale department service.
	 * 
	 * @param saleDepartmentService
	 *            the new sale
	 *            department service
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	public IOptimalPlatformDao getOptimalPlatformDao() {
		return optimalPlatformDao;
	}
	public void setOptimalPlatformDao(IOptimalPlatformDao optimalPlatformDao) {
		this.optimalPlatformDao = optimalPlatformDao;
	}
}