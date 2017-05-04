/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  
 *  
 *  you may not use this file except in compliance with the License.
 *  
 *  
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  
 *  
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  
 *  
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 *  
 *  See the License for the specific language governing permissions and
 *  
 *  
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/ForecastAction.java
 * 
 *  FILE NAME     :ForecastAction.java
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
 *  
 *  1.1	相关业务用例
 *  
 *  BUC_FOSS_5.10.20_010  预测货量
 *  
 *  1.2	用例描述
 *  
 *  系统通过后台job，
 *  
 *  每隔X小时（可配置）定时更新一次预测的货量信息，
 *  
 *  预测各调度区域内分线路未来Y小时（可配置）
 *  当天区域内营业部或开单组已开单未入库、
 *  已出发预计到达以及库存中的货量信息。
 *  
 *  
 *  
 *  1.3	用例条件
 *  
 *  条件类型	描述	引用用例
 *  
 *  
 *  
 *  前置条件	
 *  
 *  1.	营业部或开单组已开单；
 *  
 *  2.	车辆已做出发；
 *  
 *  3.	当天余货有库存信息；
 *  
 *  4.	Job启动参数已经设置	
 *  
 *  
 *  
 *  后置条件
 *  
 *  1.	生成到达货量预测数据；
 *  
 *  2.	生成出发货量预测数据；
 *  
 *  
 *  
 *  1.6	操作步骤
 *  
 *  序号	基本步骤	补充步骤
 *  
 *  1	系统每X小时启动任务,
 *  
 *  进行预测货量计算.	
 *  
 *  每小时做一次货量预测结果的快照，精确到线路，分出发和到达货量
 *  
 * 每天做一次实际货量的统计，
 *  
 *  精确到线路，
 *  
 *  分出发和到达货量
 *  
 *  
 *  
 *  2	按线路分组,
 *  
 *  根据开单后生成的走货路径统计预计到达时间的各段线路开单未交接的货量信息
 *  
 *  查询时,
 *  
 *  针对某一段时间进行信息截取.获得预计到达时间在预测范围内的信息.
 *  
 *  
 *  3	按线路分组,
 *  
 *  统计触发时间点为止的库存货量	
 *  
 *  
 *  4
 * 
 *  按线路分组,
 *  
 *  统计预计到达时间在预测范围内的在途货量.	
 *  
 *  
 *  5	统计2,3,4步骤中被重复统计的货量,
 *  
 *  加以剔除.	
 *  
 *  3类数据有分别小计,
 *  
 *  出现重叠后,
 *  
 *  剔除的优先级为,
 *  
 *  库存>在途>开单. 
 *  
 *  在库又在途的,算至在库中.依此类推
 *  
 *  6	按线路分组统计出发货量	
 *  
 *  在预测时间内,
 *  
 *  考虑货物是否能及时卸完,
 *  
 *  考虑库存量.见SR-1
 *  
 *  
 *  7	
 *  
 *  按线路分组统计到达货量	
 *  
 *  
 *  在预测时间内,计算预计到达的货量.
 *  
 *  
 *  
 *  
 *  1.7	业务规则
 *  
 *  
 *  序号	描述
 *  
 *  
 *  SR-1	
 *  
 *  A:触发时间点为止当天服务区域内所有的开单未交接的货量
 *  
 *  B:触发时间点为止的库存货量
 *  
 *  
 *  C:预计到达时间在预测范围内的在途货量.
 *  
 *  
 *  D:将要在本中转场的驻地派送部进行派送的货物
 *  
 *  出发货量统计=A+B+C1-D
 *  
 *  到达货量统计=A+C2
 *  
 *  说明：
 *  
 *  1、	Y=（预测范围终止时间-预计到达时间）*平均吞吐能力
 *  
 *  X= 车辆载重
 *  
 *  如果Y> X，C1 = X,否则 C1 = Y ,
 *  
 *  表示有可能车辆在预定时间内到达,但无法全部卸完,则该部分货物不能被算在当前时段内的货量中.
 *  
 *  2、	C2 = X, 到达货量不考虑吞吐能力.全部到达货物都计算在到达货量中.
 *  
 *  3、	B的计算方法:
 *  
 *  本周期为当前库存量.
 *  
 *  计算时需要考虑当天清仓的操作,在清仓操作完成后,使用清仓后的数据进行预测计算.
 *  
 *  之后第1周期的出发货量 减 第1周期的发车计划总量, 为负则当作0.
 *  
 *  依此类推, 如果之后的第N周期无发车计划, 则当作库存为0, 出发货量等于到达货量.
 *  
 *  
 *  
 *  SR-2	
 *  
 *  线路上运行的车辆预计到达时间由GPS提供；如果无GPS，计算时取车辆跟踪的预计到达时间
 *  
 *  SR-3	
 *  
 *  考虑外场平均吞吐能力 : Z吨/小时,不单独考虑人数,装卸速率.
 *  
 *  整车的货物按照吞吐能力考虑后，需要确定能否赶上计划的离开时间，赶不上则刷新计划的离开时间到下个时间节点.
 *  
 *  
 *  SR-4	
 *  
 *  统计总货量预测中卡货货量信息, 
 *  
 *  卡货为开单时运输类型开单为精准卡航的货物.
 *  
 *  统计总货量预测中城运货量信息, 
 *  
 *  城运为开单时运输类型开单为精准城运的货物.
 *  
 *  
 *  
 *  
 *  
 *  1.8.1	货量报表信息
 *  
 *  字段名称 	说明 	输入限制	提示输入本文	长度	是否必填	备注
 *  
 *  调度名称	中转调度所在的部门	文本		20	是	
 *  
 *  线路	包含走货流程中的出发地和目的地的组合	文本		20	是	
 *  
 *  总重量	货物的总重量，单位：公斤	数字		20	是	
 *  
 *  总体积	货物的总体积，单位：方	数字		20	是	
 *  
 *  总票数	货物的总票数，单位：票	数字		20	是	
 *  
 *  卡货总重量/总体积/总票数	运输类型开单为精准卡航的货物重量/体积/票数	数字			是	
 *  
 *  城货总重量/总体积/总票数	运输类型开单为精准城运的货物重量/体积/票数	数字			是	
 *  
 *  开单未交接总重量/总体积/总票数	预测时间点服务区域内已开单未交接的货物重量/体积/票数	数字			是	
 *  
 *  预计到达总重量/总体积/总票数	预测时间范围内预计到达的货物重量/体积/票数	数字			是	
 *  
 *  卸货中总重量/总体积/总票数	预测时间范围内卸货中的货物重量/体积/票数	数字			是	
 *  
 *  余货总重量/总体积/总票数	预测时间点库存余货的货物重量/体积/票数	数字			是	
 *  
 *  
 *  
 *  
 * 1.8	数据元素
 * 
 * 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 * 
 * 货物运单号	该批货物的运单号					由运单信息提供
 * 
 * 
 * 货物流水号	该货物的流水号					由运单信息提供
 * 
 * 
 * 货物出发地	该货物的出发营业部					由运单信息提供/多货和手工修改时为当前操作外场驻地营业部
 * 
 * 
 * 货物到达地	货物的到达营业部/虚拟营业部					由运单信息提供
 * 
 * 运输方式	该段线路所选择的运输方式					
 * 
 * 线路段号	同一货物可能有多段线路,该字段代表了它们的排列顺序.		
 * 
 * 
 * 
 * 
 * 
 * 
 * 功能性需求
 * 
 * 单位时间使用量	每1小时执行一次预测,每1天执行一次实际统计,可配置.
 * 
 * 线路数量,按目前70+外场,2000+营业部门计算,有6000-8000条支干线线路.
 * 
 * 2012年全网估计用户数	1
 * 
 * 响应要求（如果与全系统要求 不一致的话）	预测统计执行时长不超过10分钟
 * 
 * 使用时间段	全天
 * 
 * 
 * 
 * 高峰使用时间段	晚上8-12点
 *
 * 
 * 
 * 			
 *1.	SUC-63查询预测货量
 *
 *1.1	相关业务用例
 *
 *
 *BUC_FOSS_5.10.20_010 预测货量
 *
 *
 *
 *1.2	用例描述
 *
 *
 *本用例根据系统后台计算统计出的预测货量信息，
 *
 *用于各外场和车队查询本外场的出发或到达预测货量信息，
 *
 *及营业部或派送部查询到达预测货量信息。
 *
 *
 *1.3	用例条件
 *
 *条件类型	描述	引用用例
 *
 *
 *前置条件	
 *
 *已有货量预测结果	货量预测
 *
 *
 *后置条件	
 *
 *根据查询出的预测货量信息做长途发车计划、短途发车计划
 *
 *或
 *
 *根据查询出的预测货量信息对比历史货量
 *
 *或
 *
 *根据查询出的货量进行合车调整	录入发车计划（长途）、录入发车计划（短途）
 *
 *
 *对比历史货量
 *
 *1.4	操作用户角色
 *
 *操作用户	描述
 *
 *外场调度	查询本外场的预测出发货量或到达货量信息
 *
 *外场管理人员	（经理级以上人员）查询本外场的预测出发货量或到达货量信息
 *
 *车队调度	查询本车队对应外场的预测出发货量或到达货量信息
 *
 *
 *车队管理人员	（经理级以上人员）查询本车队对应外场的预测出发货量或到达货量信息
 *
 *
 *
 *
 *1.5.3	界面描述
 *
 *
 *一、	查询预测货量界面：
 *
 *
 *本界面主要包括2部分：
 *
 *预测货量查询条件、
 *
 *预测货量结果列表
 *
 *
 *1、查询条件包含：
 *
 *预测类型、预测线路、时间范围；
 *
 *预测类型：分为出发和到达两种类型
 *
 *预测线路：选择某一线路进行显示，过滤出与本部门有关的线路，如出发时，线路出发部门是本部门，到达时，线路到达部门是本部门的线路信息，且预测线路可以多选。
 *
 *时间范围：根据各外场的灵活配置，选择从某一时间点开始未来24小时内的时间段；
 *
 *2、列表信息包含：
 *
 *区域、
 *
 *目的地、
 *
 *总重量/总体积/总票数、
 *
 *卡货总重量/总体积/总票数、
 *
 *城货总重量/总体积/总票数、
 *
 *开单未交接总重量/体积/票数、
 *
 *在途总重量/总体积/总票数、
 *
 *库存总重量/总体积/总票数。
 *
 *具体说明见数据元素【预测货量列表信息】
 *
 *
 *3、列表上面，
 *
 *应显示查询结果对应的时间范围标签
 *
 *
 *4、线路前面有复选框可勾选，
 *
 *进入合发调整界面，
 *
 *合发之后调货的货量显示在”重量差”列.
 *
 *
 *5、按钮：
 *
 *查询、路径调整、查看总量统计图表、查看图表、发车计划、导出货量预测、导出货量预测明细；
 *
 *查询：
 *
 *普通按钮，根据查询条件显示出满足条件的结果信息；
 *
 * 
 * 路径调整：
 * 
 * 图片按钮，根据选择条目，弹出路径调整页面，若为多选，则进入合车调整页，若为单选。则进入路径调整页。
 * 
 * 
 * 总量统计图表：
 * 
 * 图片按钮，显示本部门总的货量出发或到达货量信息图形表示形
 * 
 * 
 * 查看图表：
 * 
 * 图片按钮，显示本部门分线路总的货量出发活到达货量信息图形表示形式
 * 
 * 
 * 发车计划：
 * 
 * 点击后可进入发车计划界面
 * 
 * 
 * 导出货量预测：
 * 
 * 图片按钮，根据查询项导出货量预测信息
 * 
 * 
 * 导出货量预测明细：
 * 
 * 图片按钮，根据某一特定条目，导出到达该目的地的开单及在途明细货量信息
 * 
 * 
 * 6、列表排列方式见业务规则SR-4
 * 
 * 
 * 
 * 二、	查询分线路预测货量明细界面：
 * 
 * 以预测来源分组显示结果，
 * 
 * 如果为如果预测类型为出发，
 * 
 * 分为开单未交接、在途、库存；
 * 
 * 如果预测类型为到达，
 * 
 * 分为开单未交接、在途，
 * 
 * 明细列表显示信息参见【预测货量线路明细】
 * 
 * 
 * 三、	合车调整页面
 * 
 * 进入后，
 * 
 * 按照走货路径调整的实际情况，
 * 
 * 将某条路径的货物调整至另一路径，
 * 
 * 保存以进行记录.
 * 
 * 
 * 
 * 四、	查询预测货量界面
 * 
 * 合成修改后，合车重量项会显示调整的货物重量值，以正负来表示增加或减少.
 * 
 * 
 * 
 * 五、	合车明细页面
 * 
 * 合车修改后,
 * 
 * 点击合车重量项中的数字,
 * 
 * 会弹出小窗明细该线路合车货物来源及去向明细信息.
 *
 *
 *
 *
 *1.6	操作步骤
 *
 *
 *
 *1、查询预测货量：
 *
 *
 *序号	基本步骤	相关数据	补充步骤
 *
 *
 *1	调度或外场点击菜单进入查询预测货量界面	
 *
 *
 *	默认预测类型为出发，时间范围为各外场配置的时间段
 *
 *2	选择预测类型		当选择到达类型时，
 *
 *预测来源：有开单未交接、在途、库存三种来源.
 *
 *
 *出发货量预测三者都选取,并且需要排除最后到达站为本外场的部分.
 *
 *到达货量预测只取开单未交接及在途, 库存被抛除。
 *
 *
 *4	选择预测的目的地信息		
 *
 *默认展示本部门对应的所有目的地的条目信息，
 *
 *如果需要选择目的地，
 *
 *弹出公共选择器，
 *
 *根据选择的预测类型进行过滤线路，参见【业务规则3】
 *
 *
 *5	选择需要预测的时间范围		
 *
 *下拉框格式为20120612-X～20120613-X, X为时间点,各外场可以设置这个周期起止时间点.查询最多支持7个预测周期.
 *
 *
 *6	点击查询按钮，
 *
 *显示满足条件的出发或到达货量信息	
 *
 *【预测货量列表信息】	
 *
 *结果信息根据各目的地对应区域的关系，
 *
 *聚合显示在一起
 *
 *2、查看预测货量各线路明细
 *
 *序号	基本步骤	相关数据	补充步骤
 *
 *1	根据查询出的结果，点击某一条线路信息		弹出线路货量明细查看窗口，以预测来源分组显示结果：
 *
 *1、	如果预测类型为出发，分为三类：
 *
 *开单未交接、在途、库存，显示明细参见界面描述部分；
 *
 *2、	如果预测类型为到达，分为两类：
 *
 *开单未交接、在途，显示明细参见界面描述部分。
 *
 *
 *序号	扩展事件	相关数据	备注
 *
 *
 *
 *3、合车调整:
 *
 *序号	基本步骤	相关数据	补充步骤
 *
 *1	根据查询出的结果，
 *
 *勾选某两条线路信息,
 *
 *点击合车调整按钮.		
 *
 *弹出合车调整子页面.
 *
 *2	填写需要调整的货物重量,
 *
 *选择调整方向.
 *
 *A至B或B至A.
 *
 *点击保存进行调整.
 *
 *调整后的结果会显示在主查询页面中的重量差一列中.		
 *
 *点击确定提交调整,取消则回到原状态.
 *
 *
 *
 *
 *1.7	业务规则
 *
 *序号	描述
 *
 *
 *SR-1	
 *
 *
 *当预测到达类型货量时，列
 *
 *表中的库存余货货量为空
 *
 *
 *SR-2	
 *
 *货量分线路，
 *
 *线路可按照区域(如华南、华东)聚合显示在一起，
 *
 *区域名称以及区域和目的地的关系可维护
 *
 *
 *SR-3	
 *
 *当预测类型为出发时，
 *应只有对应线路为本部门出发的可供选择；
 *当预测类型为到达时，
 *应只有对应线路信息为到达本部门的可选择
 *
 *
 *SR-4	
 *
 *列表的排列方式:
 *
 *由提供线路信息的基础数据组提供每条线路的权重值,
 *
 *按照权重进行展示排列.
 *
 *该权重值只针对货量预测部分进行配置.
 *
 *SR-5	货量预测结果可以导出，
 *
 *列表不展开到明细时，按照列表结果导出
 *
 *列表展开到明细，明细提供一个单独的导出，
 *
 *只导出当前条目的明细到营业区的货量，
 *
 *导出结果要求细分到具体营业部的货量,营业区货量取所有管辖营业部之和.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.server.action;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.ForecastConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.BillingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ChangeQuantityEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InTransitEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RealWeightAndVolumeDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ShowChartDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.TimeUtils;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.ForecastVO;
import com.deppon.foss.util.DateUtils;

/**
 * 货量预测action类
 * 
 * @author huyue
 * @date 2012-10-31 下午5:23:12
 */
public class ForecastAction extends AbstractAction {

	private static final long serialVersionUID = -5182857592065055743L;

	/**
	 * 导出Excel 文件流
	 */
	private transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;
	/**
	 * 货量预测VO
	 */
	private ForecastVO forecastVO = new ForecastVO();
	/**
	 * 货量预测Service
	 */
	private transient IForecastService forecastService;
	/**
	 * 综合配置参数相关Service
	 */
	private transient IConfigurationParamsService configurationParamsService;
	
	private transient IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IMotorcadeService motorcadeService;
	
	private static final int three = 3;

	private String starts = "0300";

	private int day = 1;

	private static final int hourForDay = 24;

	private static final int threeMillionSixHundredThousand = 3600000;

	/**
	 * 获取 货量预测VO.
	 *
	 * @return the 货量预测VO
	 */
	public ForecastVO getForecastVO() {
		return forecastVO;
	}

	/**
	 * 设置 货量预测VO.
	 *
	 * @param forecastVO the new 货量预测VO
	 */
	public void setForecastVO(ForecastVO forecastVO) {
		this.forecastVO = forecastVO;
	}

	/**
	 * 设置 货量预测Service.
	 *
	 * @param forecastService the new 货量预测Service
	 */
	public void setForecastService(IForecastService forecastService) {
		this.forecastService = forecastService;
	}

	/**
	 * 设置 综合配置参数相关Service.
	 *
	 * @param configurationParamsService the new 综合配置参数相关Service
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param motorcadeService the motorcadeService to set
	 */
	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	/**
	 * 获取 导出Excel 文件流.
	 *
	 * @return the 导出Excel 文件流
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**
	 * 设置 导出Excel 文件流.
	 *
	 * @param excelStream the new 导出Excel 文件流
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	/**
	 * 获取 导出Excel 文件名.
	 *
	 * @return the 导出Excel 文件名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置 导出Excel 文件名.
	 *
	 * @param fileName the new 导出Excel 文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 查询某外场最新一批货量预测的预测天数LIST
	 * 
	 * @author huyue
	 * @date 2012-11-30 下午2:44:32
	 */
	@JSON
	public String queryTimeList() {
		try {
			
			/**
			 * 某外场最新一批货量预测的预测天数LIST
			 */
			String org = forecastVO.getForecastQuantityEntity().getBelongOrgCode();
			forecastVO.getForecastQuantityEntity().setBelongOrgCode(findTransforCenter(org));
			List<Date> forecastDateList = forecastService.queryForecastTimeList(forecastVO.getForecastQuantityEntity());
			forecastVO.setForecastDateList(forecastDateList);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 货量预测总表LIST查询
	 * 
	 * @author huyue
	 * @date 2012-11-21 下午2:30:58
	 */
	@JSON
	public String queryLevel1() {
		try {
			/**
			 * 分页查询以目的地为单位的信息
			 */
			String org = forecastVO.getForecastQuantityEntity().getBelongOrgCode();
			forecastVO.getForecastQuantityEntity().setBelongOrgCode(findTransforCenter(org));
			List<ForecastQuantityEntity> forecastQuantityList = forecastService.queryByPage(forecastVO.getForecastQuantityEntity(), start, limit);
			forecastVO.setForecastQuantityList(forecastQuantityList);
			/**
			 * 根据预测时间点 ,组织code 查询预测时间段
			 */
			/**
			 * 获取开始时间点
			 */
			ConfigurationParamsEntity entityStart = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_START, forecastVO.getForecastQuantityEntity().getBelongOrgCode());
			/**
			 * 如果不为空,则赋值
			 */
			if (entityStart != null && StringUtils.isNotEmpty(entityStart.getConfValue())) {
				starts = entityStart.getConfValue();
			}
			/**
			 * 获取持续天数
			 */
			ConfigurationParamsEntity entityDuration = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_DURATION, forecastVO.getForecastQuantityEntity().getBelongOrgCode());
			/**
			 * 如果不为空,则赋值
			 */
			if (entityDuration != null && StringUtils.isNotEmpty(entityDuration.getConfValue())) {
				day = Integer.valueOf(entityDuration.getConfValue());
			}
			/**
			 * 时间计算和转换
			 */
			Date forecastStartTime = TimeUtils.createStartTime(forecastVO.getForecastQuantityEntity().getForecastTime(), starts);
			Date forecastEndTime = TimeUtils.convertStringToDate(forecastVO.getForecastQuantityEntity().getForecastTime(), starts, day);
			/**
			 * 为空则转换失败,报异常
			 */
			if (null == forecastStartTime || null == forecastEndTime) {
				throw new TfrBusinessException(ForecastConstants.FORECAST_TRANSFORTIME_ERROR, "");
			}
			/**
			 * 查询全部区域数量(前台以此进行分页)
			 */
			int count = forecastService.queryByPageCount(forecastVO.getForecastQuantityEntity());
			this.setTotalCount((long) count);
			forecastVO.setForecastEndTime(forecastEndTime);
			forecastVO.setForecastStartTime(forecastStartTime);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 快递体积转向前台传一个 转换常数过去
	 * @author 200978-foss-xiaobingcheng
	 * 2014-8-9
	 * @return
	 */
	public String forecastQuantityIndex(){
	//xiaobc update start.......................
		try {
			//zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
			//获取当前部门
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			BigDecimal volumeRatio = this.forecastService.queryForecastParameter(currentDeptCode);
			//BigDecimal volumeRatio = this.forecastService.queryForecastParameter();
			forecastVO.setVolumeRatio(volumeRatio);
			return returnSuccess();
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	//xiaobc update end.......................
	}
	
	/**
	 * 货量预测开单表LIST查询
	 * 
	 * @author huyue
	 * @date 2012-11-21 下午2:30:49
	 */
	public String queryBilling() {
		try {
			/**
			 * 查询开单部分的货量预测信息
			 */
			List<BillingEntity> billingList = forecastService.queryBillingList(forecastVO.getBillingEntity());
			forecastVO.setBillingList(billingList);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 货量预测在途表LIST查询
	 * 
	 * @author huyue
	 * @date 2012-11-21 下午2:30:41
	 */
	public String queryInTransit() {
		try {
			/**
			 * 查询在途部分的货量预测信息
			 */
			List<InTransitEntity> inTransitList = forecastService.queryInTransitList(forecastVO.getInTransitEntity());
			forecastVO.setInTransitList(inTransitList);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 查询当天合车记录明细
	 * 
	 * @author huyue
	 * @date 2012-12-7 上午11:03:22
	 */
	public String queryChangeQuantity() {
		try {
			List<ChangeQuantityEntity> changeQuantityList = new ArrayList<ChangeQuantityEntity>();
			/**
			 * 查询合入的条目
			 */
			changeQuantityList.addAll(forecastService.queryChangeInByDate(forecastVO.getForecastQuantityEntity().getForecastTime(), forecastVO.getForecastQuantityEntity().getBelongOrgCode(), forecastVO.getForecastQuantityEntity().getRelevantOrgCode()));
			/**
			 * 查询合出的条目
			 */
			changeQuantityList.addAll(forecastService.queryChangeOutByDate(forecastVO.getForecastQuantityEntity().getForecastTime(), forecastVO.getForecastQuantityEntity().getBelongOrgCode(), forecastVO.getForecastQuantityEntity().getRelevantOrgCode()));
			forecastVO.setChangeQuantityList(changeQuantityList);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 货量预测合车调整
	 * 
	 * @author huyue
	 * @date 2012-11-21 下午2:46:06
	 */
	public String changeQuantity() {
		try {
			/**
			 * 提交合车调整相关信息 调用货量预测合车调整Service
			 */
			forecastService.changeQuantity(forecastVO.getForecastQuantityList(), forecastVO.getChangeQuantityEntity());
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 根据当前时间获取时间获取所属预测日期区间
	 * 
	 * @author huyue
	 * @date 2013-1-15 上午10:07:19
	 */
	public String nowForecastDate() {
		try {
			Date nowDate = new Date();
			/**
			 * 根据预测时间点 ,组织code 查询预测时间段
			 */
			/**
			 * 获取开始时间点
			 */
			String org = forecastVO.getForecastQuantityEntity().getBelongOrgCode();
			forecastVO.getForecastQuantityEntity().setBelongOrgCode(findTransforCenter(org));
			ConfigurationParamsEntity entityStart = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_START, forecastVO.getForecastQuantityEntity().getBelongOrgCode());
			/**
			 * 如果不为空,则赋值
			 */
			if (entityStart != null && StringUtils.isNotEmpty(entityStart.getConfValue())) {
				starts = entityStart.getConfValue();
			}
			/**
			 * 获取当前日期,小时分钟
			 */
			Date date = DateUtils.convert(DateUtils.convert(nowDate, DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
			SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
			String hhmm = sdf2.format(nowDate);
			/**
			 * 如果为第二天0点到3点.则实际的预测的时间为newDate减一天
			 */
			if (Integer.parseInt(hhmm) < Integer.parseInt(starts) && Integer.parseInt(hhmm) > Integer.parseInt("0000")) {
				forecastVO.setMaxStatisticsTime(DateUtils.addDayToDate(date, -1));
			} else {
				forecastVO.setMaxStatisticsTime(date);
			}
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 某一线路某天图表显示
	 * 
	 * @author huyue
	 * @date 2012-11-23 下午4:04:38
	 */
	public String showChartSpecPath() {
		try {
			String org = forecastVO.getForecastQuantityEntity().getBelongOrgCode();
			forecastVO.getForecastQuantityEntity().setBelongOrgCode(findTransforCenter(org));
			Date statistics = new Date();
			/**
			 * 获取货量预测时间
			 */
			Date forecast = forecastVO.getForecastQuantityEntity().getForecastTime();
			int ifSameDay = 0;
			/**
			 * 根据预测时间点 ,组织code 查询预测时间段
			 */
			/**
			 * 获取开始时间点
			 */
			ConfigurationParamsEntity entityStart = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_START, forecastVO.getForecastQuantityEntity().getBelongOrgCode());
			/**
			 * 如果不为空,则赋值
			 */
			if (entityStart != null && StringUtils.isNotEmpty(entityStart.getConfValue())) {
				starts = entityStart.getConfValue();
			}
			/**
			 * 比较预测时间和预测执行时间 如果小于等于1天则表示为当天,不需要计算实际值
			 */
			forecast = TimeUtils.createStartTime(forecast, starts);
			/**
			 * 返回差值
			 */
			Long diffDay = DateUtils.getTimeDiff(forecast, statistics);
			if (diffDay == 0) {
				/**
				 * 是当天
				 */
				ifSameDay = 1;
			}
			/**
			 * 根据预测DATE获取TIME 再根据周期进行拼接
			 */
			Date statisticsTime = TimeUtils.createStartTime(forecastVO.getForecastQuantityEntity().getStatisticsDate(), starts);
			forecastVO.getForecastQuantityEntity().setStatisticsDate(null);
			forecastVO.getForecastQuantityEntity().setStatisticsTime(statisticsTime);
			/**
			 * 查询某一线路信息
			 */
			List<ForecastQuantityEntity> forecastQuantityList = forecastService.queryForecastQuantityList(forecastVO.getForecastQuantityEntity());
			if (forecastQuantityList.size() > 0) {
				List<ShowChartDto> showChartDtoList = transferList(forecastQuantityList);
				/**
				 * 获取当天货量预测的周期开始时间,持续时间,间隔时间
				 */
				forecastVO.setStartTime(starts);
				/**
				 * 获取持续天数
				 */
				ConfigurationParamsEntity entityDuration = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_DURATION, forecastVO.getForecastQuantityEntity().getBelongOrgCode());
				/**
				 * 如果不为空,则赋值
				 */
				if (entityDuration != null && StringUtils.isNotEmpty(entityDuration.getConfValue())) {
					day = Integer.valueOf(entityDuration.getConfValue());
				}
				/**
				 * 将天转为小时数
				 */
				int durationHour = day * hourForDay;
				forecastVO.setDurationHour(durationHour);
				/**
				 * 获取执行间隔时间
				 */
				int executeGapHour = 1;
				ConfigurationParamsEntity entityGap = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_GAP, forecastVO.getForecastQuantityEntity().getBelongOrgCode());
				/**
				 * 如果不为空,则赋值
				 */
				if (entityGap != null && StringUtils.isNotEmpty(entityGap.getConfValue())) {
					executeGapHour = Integer.valueOf(entityGap.getConfValue());
				}
				forecastVO.setExecuteGapHour(executeGapHour);
				/**
				 * 如果为当天,需要补全今天剩余部分的
				 */
				if (ifSameDay == 1) {
					/**
					 * 是当天
					 */
					String lastHHMM = showChartDtoList.get(showChartDtoList.size() - 1).getStatisticsHHMM();
					Date lastDate = showChartDtoList.get(showChartDtoList.size() - 1).getStatisticsDate();
					Date last = TimeUtils.createStartTime(lastDate, lastHHMM);
					/**
					 * 得到本周期的最后时间
					 */
					Date forecastEndTime = TimeUtils.convertStringToDate(forecast, starts, day);
					/**
					 * 把当前得到数据的最后时间和本周期的最后时间相减
					 */
					Long lastLong = last.getTime();
					Long timeNeed = forecastEndTime.getTime() - lastLong;
					int hourNeed = (int) (timeNeed / threeMillionSixHundredThousand);
					int copyCount = hourNeed / executeGapHour - 1;
					/**
					 * 获取一共还需要补全多少个时间周期的记录
					 */
					for (int c = 0; c < copyCount; c++) {
						ShowChartDto lastDto = new ShowChartDto();
						BeanUtils.copyProperties(showChartDtoList.get(showChartDtoList.size() - 1), lastDto);
						/**
						 * 得到新时间
						 */
						lastLong = lastLong + threeMillionSixHundredThousand;
						Date newDate = new Date(lastLong);
						SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
						String newHHMM = sdf2.format(newDate);
						lastDto.setStatisticsHHMM(newHHMM);
						showChartDtoList.add(lastDto);
					}
				}
				/**
				 * 实际值获取
				 */
				/**
				 * 如果为当天.不需要实际值 否则查询实际值
				 */
				if (ifSameDay != 1) {
					/**
					 * 调用计算外场上班人数service中的方法获取本周期内的实际货量
					 */
					RealWeightAndVolumeDto realWeightAndVolumeDto = forecastService.countRealWeightAndVolume(forecastVO.getForecastQuantityEntity());
					for (int i = 0; i < showChartDtoList.size(); i++) {
						/**
						 * 如果为空,则赋0
						 */
						if (null == realWeightAndVolumeDto || null == realWeightAndVolumeDto.getTotalVolume()) {
							showChartDtoList.get(i).setRealVolume(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
						} else {
							showChartDtoList.get(i).setRealVolume(realWeightAndVolumeDto.getTotalVolume());
						}
						/**
						 * 如果为空,则赋0
						 */
						if (null == realWeightAndVolumeDto || null == realWeightAndVolumeDto.getTotalWeight()) {
							showChartDtoList.get(i).setRealWeight(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
						} else {
							showChartDtoList.get(i).setRealWeight(realWeightAndVolumeDto.getTotalWeight());
						}
					}
				}
				forecastVO.setShowChartDto(showChartDtoList);
			}
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 总货量某天图表显示
	 * 
	 * @author huyue
	 * @date 2012-11-30 下午3:59:28
	 */
	public String showChartAllPath() {
		try {
			String org = forecastVO.getForecastQuantityEntity().getBelongOrgCode();
			forecastVO.getForecastQuantityEntity().setBelongOrgCode(findTransforCenter(org));
			Date statistics = new Date();
			/**
			 * 获取货量预测时间
			 */
			Date forecast = forecastVO.getForecastQuantityEntity().getForecastTime();
			int ifSameDay = 0;
			/**
			 * 根据预测时间点 ,组织code 查询预测时间段
			 */
			/**
			 * 获取开始时间点
			 */
			ConfigurationParamsEntity entityStart = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_START, forecastVO.getForecastQuantityEntity().getBelongOrgCode());
			/**
			 * 如果不为空则赋值
			 */
			if (entityStart != null && StringUtils.isNotEmpty(entityStart.getConfValue())) {
				starts = entityStart.getConfValue();
			}
			/**
			 * 比较预测时间和预测执行时间 如果大于零则表示为当天,不需要计算实际值
			 */
			forecast = TimeUtils.createStartTime(forecast, starts);
			Long diffDay = DateUtils.getTimeDiff(forecast, statistics);
			if (diffDay == 0) {
				/**
				 * 是当天
				 */
				ifSameDay = 1;
			}
			/**
			 * 根据预测DATE获取TIME 再根据周期进行拼接
			 */
			Date statisticsTime = TimeUtils.createStartTime(forecastVO.getForecastQuantityEntity().getStatisticsDate(), starts);
			forecastVO.getForecastQuantityEntity().setStatisticsDate(null);
			forecastVO.getForecastQuantityEntity().setStatisticsTime(statisticsTime);
			/**
			 * 查询所有线路信息.按HHMM汇总后得到汇总LIST
			 */
			List<ForecastQuantityEntity> forecastQuantityList = forecastService.queryTotalList(forecastVO.getForecastQuantityEntity());
			if (forecastQuantityList.size() > 0) {
				List<ShowChartDto> showChartDtoList = transferList(forecastQuantityList);
				/**
				 * 获取当天货量预测的周期开始时间,持续时间,间隔时间
				 */
				forecastVO.setStartTime(starts);
				/**
				 * 获取持续天数
				 */
				ConfigurationParamsEntity entityDuration = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_DURATION, forecastVO.getForecastQuantityEntity().getBelongOrgCode());
				/**
				 * 如果不为空,则赋值
				 */
				if (entityDuration != null && StringUtils.isNotEmpty(entityDuration.getConfValue())) {
					day = Integer.valueOf(entityDuration.getConfValue());
				}
				/**
				 * 将天转为小时数
				 */
				int durationHour = day * hourForDay;
				forecastVO.setDurationHour(durationHour);
				/**
				 * 获取执行间隔时间
				 */
				int executeGapHour = 1;
				ConfigurationParamsEntity entityGap = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_GAP, forecastVO.getForecastQuantityEntity().getBelongOrgCode());
				/**
				 * 如果不为空,则赋值
				 */
				if (entityGap != null && StringUtils.isNotEmpty(entityGap.getConfValue())) {
					executeGapHour = Integer.valueOf(entityGap.getConfValue());
				}
				forecastVO.setExecuteGapHour(executeGapHour);
				/**
				 * 如果为当天,需要补全今天剩余部分的
				 */
				/**
				 * 是当天
				 */
				if (ifSameDay == 1) {
					ShowChartDto lastDto = new ShowChartDto();
					String lastHHMM = showChartDtoList.get(showChartDtoList.size() - 1).getStatisticsHHMM();
					Date lastDate = showChartDtoList.get(showChartDtoList.size() - 1).getStatisticsDate();
					Date last = TimeUtils.createStartTime(lastDate, lastHHMM);
					/**
					 * 得到本周期的最后时间
					 */
					Date forecastEndTime = TimeUtils.convertStringToDate(forecast, starts, day);
					/**
					 * 把当前得到数据的最后时间和本周期的最后时间相减
					 */
					Long lastLong = last.getTime();
					Long timeNeed = forecastEndTime.getTime() - lastLong;
					int hourNeed = (int) (timeNeed / threeMillionSixHundredThousand);
					int copyCount = hourNeed / executeGapHour - 1;
					/**
					 * 循环需要补全的时间周期
					 */
					for (int c = 0; c < copyCount; c++) {
						lastDto = new ShowChartDto();
						BeanUtils.copyProperties(showChartDtoList.get(showChartDtoList.size() - 1), lastDto);
						/**
						 * 得到新时间
						 */
						lastLong = lastLong + threeMillionSixHundredThousand;
						Date newDate = new Date(lastLong);
						SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
						String newHHMM = sdf2.format(newDate);
						lastDto.setStatisticsHHMM(newHHMM);
						showChartDtoList.add(lastDto);
					}
				}
				/**
				 * 获取实际值
				 */
				/**
				 * 如果为当天.不需要实际值 否则查询实际值
				 */
				if (ifSameDay != 1) {
					RealWeightAndVolumeDto realWeightAndVolumeDto = forecastService.countRealWeightAndVolume(forecastVO.getForecastQuantityEntity());
					for (int i = 0; i < showChartDtoList.size(); i++) {
						/**
						 * 如果为空,则赋0
						 */
						if (null == realWeightAndVolumeDto || null == realWeightAndVolumeDto.getTotalVolume()) {
							showChartDtoList.get(i).setRealVolume(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
						} else {
							showChartDtoList.get(i).setRealVolume(realWeightAndVolumeDto.getTotalVolume());
						}
						/**
						 * 如果为空,则赋0
						 */
						if (null == realWeightAndVolumeDto || null == realWeightAndVolumeDto.getTotalWeight()) {
							showChartDtoList.get(i).setRealWeight(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
						} else {
							showChartDtoList.get(i).setRealWeight(realWeightAndVolumeDto.getTotalWeight());
						}
					}
				}
				/**
				 * 预警值
				 */
				String orgCode = forecastVO.getForecastQuantityEntity().getBelongOrgCode();
				/**
				 * 获取警戒重量
				 */
				/**
				 * 先赋0
				 */
				BigDecimal warnWeight = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
				ConfigurationParamsEntity entityWeight = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__WARN_WEIGHT, orgCode);
				/**
				 * 如果不为空,则赋值
				 */
				if (entityWeight != null && StringUtils.isNotEmpty(entityWeight.getConfValue())) {
					warnWeight = new BigDecimal(entityWeight.getConfValue());
				}
				/**
				 * 获取警戒体积
				 */
				/**
				 * 先赋0
				 */
				BigDecimal warnVolume = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
				ConfigurationParamsEntity entityVolume = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__WARN_VOLUME, orgCode);
				/**
				 * 如果不为空,则赋值
				 */
				if (entityVolume != null && StringUtils.isNotEmpty(entityVolume.getConfValue())) {
					warnVolume = new BigDecimal(entityVolume.getConfValue());
				}
				/**
				 * 设置预警值
				 */
				for (int i = 0; i < showChartDtoList.size(); i++) {
					showChartDtoList.get(i).setWarnWeight(warnWeight);
					showChartDtoList.get(i).setWarnVolume(warnVolume);
				}
				forecastVO.setShowChartDto(showChartDtoList);
			}
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 特定时间点的货量显示
	 * 
	 * @author huyue
	 * @date 2012-11-30 下午4:02:14
	 */
	public String showChartSpecHour() {
		try {
			String org = forecastVO.getForecastQuantityEntity().getBelongOrgCode();
			forecastVO.getForecastQuantityEntity().setBelongOrgCode(findTransforCenter(org));
			List<ShowChartDto> showChartDtoList = new ArrayList<ShowChartDto>();
			
			/**
			 * 获取预测时间
			 */
			Date forecastTime = forecastVO.getForecastQuantityEntity().getForecastTime();
			Date statisticsDate = forecastVO.getForecastQuantityEntity().getStatisticsDate();
			/**
			 * 根据日期和需要的天数循环调用
			 */
			for (int i = 0; i <= forecastVO.getDay(); i++) {
				forecastVO.getForecastQuantityEntity().setStatisticsDate(DateUtils.convert((DateUtils.addDay(statisticsDate, -i)), "yyyy-MM-dd"));
				forecastVO.getForecastQuantityEntity().setForecastTime(DateUtils.convert((DateUtils.addDay(forecastTime, -i)), "yyyy-MM-dd"));
				ForecastQuantityEntity forecastQuantityEntity = forecastService.querySpecHourList(forecastVO.getForecastQuantityEntity());
				if (null != forecastQuantityEntity) {
					ShowChartDto showChartDto = transfer(forecastQuantityEntity);
					/**
					 * 实际值
					 */
					/**
					 * 如果为当天.不需要实际值 否则查询实际值
					 */
					if (i != 0) {
						RealWeightAndVolumeDto realWeightAndVolumeDto = forecastService.countRealWeightAndVolume(forecastVO.getForecastQuantityEntity());
						/**
						 * 如果为空,则赋0
						 */
						if (null == realWeightAndVolumeDto || null == realWeightAndVolumeDto.getTotalVolume()) {
							showChartDto.setRealVolume(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
						} else {
							showChartDto.setRealVolume(realWeightAndVolumeDto.getTotalVolume());
						}
						/**
						 * 如果为空,则赋0
						 */
						if (null == realWeightAndVolumeDto || null == realWeightAndVolumeDto.getTotalWeight()) {
							showChartDto.setRealWeight(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
						} else {
							showChartDto.setRealWeight(realWeightAndVolumeDto.getTotalWeight());
						}
					}
					showChartDtoList.add(showChartDto);
				}
			}
			forecastVO.setShowChartDto(showChartDtoList);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 根据出发,到达部门判断发车计划类型
	 * 
	 * @author huyue
	 * @date 2013-1-5 下午6:51:24
	 */
	public String queryDepartureType() {
		try {
			/**
			 * 获取部门,日期
			 */
			String origOrgCode = forecastVO.getOrigOrgCode();
			String destOrgCode = forecastVO.getDestOrgCode();
			/**
			 * 根据出发,到达部门获取发车计划类型
			 */
			String departureType = forecastService.queryDepartureType(origOrgCode, destOrgCode);
			forecastVO.setDepartureType(departureType);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 将预测查询结果导出到Excel文件
	 * 
	 * @author huyue
	 * @date 2013-1-7 下午2:34:39
	 */
	public String forecastExportExcel() {
		try {
			/**
			 * 设置文件名
			 */
			fileName = forecastService.encodeFileName("货量预测");
			/**
			 * 获取文件内容流
			 */

			String org = forecastVO.getForecastQuantityEntity().getBelongOrgCode();
			forecastVO.getForecastQuantityEntity().setBelongOrgCode(findTransforCenter(org));
			excelStream = forecastService.exportExcelStream(forecastVO.getForecastQuantityEntity());
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 将预测明细查询结果导出到Excel文件
	 * 
	 * @author huyue
	 * @date 2013-1-8 下午2:01:35
	 */
	public String detailExportExcel() {
		try {
			/**
			 * 设置文件名
			 */
			fileName = forecastService.encodeFileName("货量预测明细");
			/**
			 * 获取文件内容流
			 */
			excelStream = forecastService.detailExportExcelStream(forecastVO.getForecastQuantityEntity().getForecastQuantityId());
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 将entity 转为dto
	 * 
	 * @author huyue
	 * @date 2012-12-10 下午3:57:24
	 */
	public ShowChartDto transfer(ForecastQuantityEntity forecastQuantityEntity) {
		ShowChartDto showChartDto = new ShowChartDto();
		showChartDto.setStatisticsDate(forecastQuantityEntity.getStatisticsDate());
		showChartDto.setStatisticsHHMM(forecastQuantityEntity.getStatisticsHHMM());
		showChartDto.setTotalVolume(forecastQuantityEntity.getVolumeTotal());
		showChartDto.setTotalWeight(forecastQuantityEntity.getWeightTotal());
		return showChartDto;
	}

	/**
	 * 将entity 转为dto LIST
	 * 
	 * @author huyue
	 * @date 2012-12-10 下午3:57:39
	 */
	public List<ShowChartDto> transferList(List<ForecastQuantityEntity> forecastQuantityList) {
		List<ShowChartDto> showChartDtoList = new ArrayList<ShowChartDto>();
		for (int i = 0; i < forecastQuantityList.size(); i++) {
			ShowChartDto showChartDto = new ShowChartDto();
			showChartDto.setStatisticsDate(forecastQuantityList.get(i).getStatisticsDate());
			showChartDto.setStatisticsHHMM(forecastQuantityList.get(i).getStatisticsHHMM());
			showChartDto.setTotalVolume(forecastQuantityList.get(i).getVolumeTotal());
			showChartDto.setTotalWeight(forecastQuantityList.get(i).getWeightTotal());
			showChartDtoList.add(showChartDto);
		}
		return showChartDtoList;
	}
	
	/**
	 * 根据当前的员工的CODE查找所对应的外场或者营业部
	 * @param org
	 * @return
	 */
	public String findTransforCenter(String org){
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		bizTypes.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		OrgAdministrativeInfoEntity parentOrg=null;
		
		//查找当前员工的部门实体信息
		OrgAdministrativeInfoEntity orgs = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(org);
		//如果当前员工属于调度组
		if(null != orgs && StringUtils.equals("Y", orgs.getDispatchTeam())){
			//根据当前员工所在部门CODE查找顶级车队
			parentOrg = orgAdministrativeInfoComplexService.getTopFleetByCode(orgs.getCode());
			if(null != parentOrg && StringUtils.isNotEmpty(parentOrg.getCode())){
				//根据顶级车队CODE查找所对应的外场
				MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(parentOrg.getCode());
				if(null != motorcade && StringUtils.isNotEmpty(motorcade.getTransferCenter()))
				//返回外场CODE
				return motorcade.getTransferCenter();
			}
				throw new TfrBusinessException("未找到此部门：" + org + " 所对应的的上级 外场");
		}
		//当前员工不属于调度组的的时候
		parentOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(org, bizTypes);
		if(null == parentOrg || StringUtils.isEmpty(parentOrg.getCode())){
			throw new TfrBusinessException("未找到此部门：" + org + " 所对应的的上级 外场 或 营业部");
		}else{
			return parentOrg.getCode();
		}
	}
	/**
	 * 统计货量查询
	 * @return
	 */
	@JSON
	public String queryStatisticalInquiriesList(){
		try {
			//check
			if(null == forecastVO.getStatisticalInquiriesDto()){
				throw new TfrBusinessException("请输入正确的参数!");
			}
			String length="";
			try{
				//配载方案配置条数限制
			ConfigurationParamsEntity entityStart = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__STOWAGE_PLANS_DEFAULT_LENGTH, FossUserContext.getCurrentDeptCode());
			//默认值
			if(null == entityStart){
				length = "3";
			}else{
				length = entityStart.getConfValue();
			}
			}catch (TfrBusinessException e) {
				//设置默认值
				length = "3";
				//Log.error("未取出配置参数直接设置默认值->部门: " +FossUserContext.getCurrentDeptName() +" : "+FossUserContext.getCurrentDeptCode());
			}
			
			if (CollectionUtils.isNotEmpty(forecastVO.getStatisticalInquiriesDto().getArriveDeptList())) {
				if(forecastVO.getStatisticalInquiriesDto().getArriveDeptList().size()>Integer.valueOf(length)){
					throw new TfrBusinessException("添加的部门数不能大于"+length+"个！", "");
				}
			}
			
			//获取当前登陆人员所对应的营业部或外场或为登陆人的当前部门
			try {
				//返回当前员工所在的营业部或者外场
				forecastVO.getStatisticalInquiriesDto().setTransforCenterCode(this.findTransforCenter(FossUserContext.getCurrentDept().getCode()));
			} catch (TfrBusinessException ex) {
				//赋值为当前的登陆人的当前部门
				forecastVO.getStatisticalInquiriesDto().setTransforCenterCode(FossUserContext.getCurrentDept().getCode());
			}
			forecastVO.setStart(start);
			forecastVO.setLimit(limit);
			//由于service 里面要分辨的状态为5中不同的结果所以这边把展示层放到service里面处理不在action处理
			forecastVO = forecastService.queryStatisticalInquiries(forecastVO);

			return returnSuccess();
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 统计货量查询Execl 导出
	 * @return
	 */
	public String queryStatisticalInquiriesExcelStream(){
		try {
			/**
			 * 设置文件名
			 */
			fileName = forecastService.encodeFileName("统计货量查询");
			
			//check
			if(null == forecastVO.getStatisticalInquiriesDto()){
				throw new TfrBusinessException("请输入正确的参数!");
			}
			
			//获取当前登陆人员所对应的营业部或外场或为登陆人的当前部门
			try {
				//返回当前员工所在的营业部或者外场
				forecastVO.getStatisticalInquiriesDto().setTransforCenterCode(this.findTransforCenter(FossUserContext.getCurrentDept().getCode()));
			} catch (TfrBusinessException ex) {
				//赋值为当前的登陆人的当前部门
				forecastVO.getStatisticalInquiriesDto().setTransforCenterCode(FossUserContext.getCurrentDept().getCode());
			}
			forecastVO.setStart(start);
			forecastVO.setLimit(limit);
			
			
			/**
			 * 获取文件内容流
			 */
			excelStream = forecastService.queryStatisticalInquiriesExcelStream(forecastVO);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

}
