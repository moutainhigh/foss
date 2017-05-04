/**
 * 
 * 
 * 
 * 
 * 
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/AdjustTransportationPathService.java
 * 
 *  FILE NAME     :AdjustTransportationPathService.java
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
 *  
 *  1.1	相关业务用例
 *  
 *  BUC_FOSS_5.10.20_020  制定长途发车计划
 *  
 *  
 * 	BUC_FOSS_5.10.20_060  制定班车发车计划
 * 	
 * 
 * 
 * 
 * 	1.2	用例描述
 * 
 * 	在制定发车计划的过程中,将会产生调整货物的走货路径的需要.
 * 
 * 进行合车或者单一线路调整.
 * 
 * 
 * 
 * 
 * 前置条件	
 * 
 * 
 * 	调度员需要对现有某几条货物线路进行合并,修改部分货物的走货路径.
 * 
 * 或
 * 
 * 	调度员需要对现有某一货物线路进行改变.修改部分货物的走货路径
 * 
 * 
 * 
 * 
 * 后置条件	
 * 
 * 
 * 生成新的走货路径
 * 
 * 给货量预测提供合车的结果参考
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 1.5.3	界面描述
 * 
 * 走货路径查询界面:
 * 
 * 进入查询界面后,
 * 
 * 根据登陆人员选则的中转场及相应库区编号,
 * 
 * 查询相应库区内的货物走货路径信息.
 * 
 * 
 * 选择相应的条目,
 * 
 * 进行走货路径的调整.
 * 
 * 可以根据路径(库区号),
 * 
 * 路径中的运单及运单中的流水号进行不同力度的选择.
 * 
 * 
 * 选择项都为同一路径时,
 * 
 * 进入调整走货路径界面.
 * 
 * 
 * 
 * 选择项包含多路径时,进入合车调整界面.
 * 
 * 
 * 
 * 调整走货路径界面:
 * 
 * 进入调整走货路径界面,
 * 
 * 输入人工指定的下一到达站,
 * 
 * 系统出现线路班车列表,根据列表进行路线选择.
 * 
 * 选择的班次的出发/到达时间将会被带到人工调整的出发/到达时间输入框之中.
 * 
 * 操作员可以对该出发时间进行修改.到达时间为系统自动计算的.
 * 
 * 如果还要继续指定下一到达站,
 * 
 * 点击添加按钮添加下一段线路的设置.
 * 
 * 直到指定的最后一个中转地.
 * 
 * 点击保存,存储调整后的走货路径.
 * 
 * 调整截止时间,一旦填写该时间,
 * 
 * 则代表从当前时间起到截止时间内,
 * 
 * 该调整持续生效,之后到达的该部分货物都会自动进行调整.
 * 
 * 默认该选项为空.代表只调整当前选中货物.
 * 
 * 
 * 
 * 
 * 
 * 合车调整界面:
 * 
 * 进入合车调整界面,
 * 
 * 被选择的线路会被列举在界面中,
 * 
 * 勾选合车后的目标线路.进行保存.
 * 
 * 调整截止时间,一旦填写该时间,
 * 
 * 则代表从当前时间起到截止时间内,
 * 
 * 该调整持续生效,之后到达的该部分货物都会自动进行调整.
 * 
 * 默认该选项为空.代表只调整当前选中货物.
 * 
 * 
 * 
 * 
 * 
 * 1.5.4	按钮描述
 * 
 * 
 * 查询: 
 * 
 * 根据输入的库区编号信息,查询相应条目.
 * 
 * 
 * 清空: 
 * 
 * 清空当前输入的库区编号信息.
 * 
 * 
 * 路径调整: 
 * 
 * 选择项都为同一路径时,
 * 
 * 进入调整走货路径界面.
 * 
 * 选择项包含多路径时,
 * 
 * 进入合车调整界面.
 * 
 * 
 * 添加:
 *  
 * 添加一条指定线路的选择模版
 * 
 * 
 * 保存: 
 * 
 * 将调整后的走货路径信息提交保存
 * 
 * 
 * 取消: 
 * 
 * 放弃调整该部分货物走货路径.
 * 
 * 
 * 
 * 
 * 1.6	操作步骤
 * 
 * 
 * 1.6.1  走货路径查询界面:
 * 
 * 
 * 1	操作员需要对待合车货物进行走货路径的调整.		
 * 
 * 
 * 2	操作员选择中转场,根据中转场的库区编号,
 * 
 * 搜索相应货物走货路径信息.
 * 
 * (同一中转场内库区可多选)	
 * 	
 * 一票货物中,
 * 
 * 只有实际存放在该货区的货物才会被罗列至搜索结果列表.
 * 
 * 被搜索出的货物有同样的存放地点(当前搜索库区)及同样的到达地点.
 * 
 * 
 * 3	操作员批量选择需要修改的货物信息.
 * 
 * 点击调整路径按钮进行走货路径修改.		
 * 
 * 已经生成超过一条走货路径的条目,
 * 
 * 允许再次操作.必须在货物最初所属库区进行勾选调整.
 * 
 * 出现在新线路的条目前不提供勾选框.
 * 
 * 
 * 4	系统弹出修改页面.		
 * 
 * 
 * 选择项都为同一路径时,
 * 
 * 进入调整走货路径界面.
 * 
 * 选择项包含多路径时,
 * 
 * 进入合车调整界面.
 * 
 * 
 * 
 * 
 * 调整走货路径界面:
 * 
 * 
 *1	进入调整走货路径界面,操作员输入人工指定的下一到达站.		
 *
 *出发地到指定到达地必须有线路存在.
 *
 *调整截止时间填写逻辑见SR-2
 *
 *
 *
 *2	系统根据当前地及下一到达站,
 *
 *出现线路班车列表		
 *
 *
 *
 *3	操作员根据列表进行路线选择.		
 *
 *
 *
 *4	系统将选择线路的出发到达时间带入人工调整出发/到达时间输入框.
 *
 *操作员可以调整出发时间.系统根据线路自动计算改动后的到达时间.		
 *
 *
 *5	还要继续指定下一到达站,
 *
 *操作员点击添加按钮,
 *
 *重复1-3步骤.		
 *
 *
 *
 *6	操作员保存修改后的走货路径.
 *
 *保存的路径存为新的条目,不删除已有路径.	
 *	
 *系统调用<<计算走货路径>>,
 *
 *根据运单,
 *
 *补全后序未设置的部分走货路径.
 *
 *出发地为最后定制化的外场,
 *
 *到达地为最终目的地.
 *
 *具体见SR-1
 *
 *
 *7	保存之后刷新新路径的文字描述，
 *
 *页面需手工关闭		
 * 
 * 
 * 1.6.3  合车调整界面:
 * 
 * 1	进入合车调整界面,
 * 
 * 系统将被选择的路径会列举在界面中.		
 * 
 * 调整截止时间填写逻辑见SR-2
 * 
 * 
 * 
 * 2	页面左侧为全部待合车的路径,
 * 
 * 操作员直接勾选某一合车路径.
 * 
 * 系统将勾选路径带至右侧区域作为指定的合车后路径.	
 * 	
 * 待合车路径的全部货物将会被合并至指定的合车路径中.
 * 
 * 
 * 4	操作员保存修改后的走货路径.
 * 
 * 保存的路径存为新的条目,不删除已有路径.		
 * 
 * 
 * 
 * 
 * 1.7	业务规则
 * 
 * SR-1	
 * 
 * 调整走货路径是由一个走货路径分为N个线路+一个子走货路径,
 * 
 * 通过查询后,再按顺序合成为一条整体的走货路径.
 * 
 * 假设A-C是原路径,那么调整后可能由A-B-C.
 * 
 * 则首先需要保证A-B是一条确定的线路,
 * 
 * 再由B-C生成一条子走货路径,从而确定一条A-C的整体新走货路经.
 * 
 * 
 * 
 * SR-2	
 * 
 * 调整截止时间,
 * 
 * 一旦填写该时间,
 * 
 * 则代表从当前时间起到截止时间内,
 * 
 * 该调整持续生效,
 * 
 * 之后到达的该部分货物都会自动进行调整.
 * 
 * 默认该选项为空.
 * 
 * 代表只调整当前选中货物.
 * 
 * 
 * 
 * SR-3	
 * 
 * 外场的查询为公共选择框,
 * 
 * 可以通过输入编号或名称进行查询,
 * 
 * 外场操作员登陆默认显示该人员所在外场相关信息.
 * 
 * 是否可以调整其他外场,需要根据不同人员角色进行权限的控制.
 * 
 * 
 * 1.8	数据元素
 * 
 * 库区编号	货物所在库区的库区号					库存信息
 * 
 * 货物运单号	该批货物的运单号					订单信息
 * 
 * 货物流水号	该货物的流水号					订单信息
 * 
 * 货物出发地	当前货物所在的中转场					
 * 
 * 原到达地	原走货路径的目的地					订单信息
 * 
 * 调整到达地	新走货路径的第一个中转节点					
 *
 * 
 * 
 * 1.9	非功能性需求
 * 
 * 
 * 单位时间使用量	每天处理次小于300次
 * 
 * 
 * 2012年全网估计用户数	操作员数量约100名
 * 
 * 
 * 
 * 响应要求（如果与全系统要求 不一致的话）	响应速度为3
 * 
 * 
 * 使用时间段	中转外场上班时间，分为：白班和晚班
 * 
 * 
 * 高峰使用时间段	22:00-8:00
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 计算并调整走货路径
 * 
 * 1.1	相关业务用例
 * 
 * BUC_FOSS_5.30.10_110上报异常库存
 * 
 * 
 * 
 * 1.2	用例描述
 * 
 * 理货员卸车结束之后，
 * 
 * 提交卸车任务，
 * 
 * 系统比对上一装车环节生成的交接单，
 * 
 * 然后在比对上一装车环节扫描纪录，最终生成差异性报告并上报。
 * 
 * 差异信息包含多货、少货两种情况，
 * 
 * 供相关人员核对和后续处理。
 * 
 * 一旦出现多货,
 * 
 * 多出货物将需要重新安排走货路径.
 * 
 * 本用例会针对货物信息,
 * 
 * 计算新的走货路径并顶替之前的路径.
 * 
 * 接送货开单时,
 * 
 * 会针对该批货物的开单信息,
 * 
 * 生成走货路径.
 * 
 * 保存到开单信息中.
 * 
 * 走货路径查询&修改时,
 * 
 * 会针对一批货物进行人工的调整其走货路径,
 * 
 * 新建保存.
 * 
 * 合车调整修改货物走货路径后,
 * 
 * 在货物出库最终确定选择路径时,
 * 
 * 需要生成新的走货路径并对原路径进行修改替换.
 * 
 * PDA扫描货物后,
 * 
 * 将会把货物的走货路径中没有使用的条目删除.
 * 
 * 确定一批货的唯一走货路径.
 * 
 * 入库时,如本货物路径处于被调整路段,
 * 
 * 切调整处于生效阶段,
 * 
 * 则需要按照调整后的路径进行该货物的走货路径修改.
 * 
 * 修改单生成并且生效后(处理待办事项,打印新标签),
 * 
 * 按照货物的修改目的地,
 * 
 * 重新计算,
 * 
 * 修改货物的走货路径.
 * 
 * 
 * 
 * 1.3	用例条件
 * 
 * 
 * 前置条件	1.	开单时,需要针对货物确定走货路径. 
 * 
 * 或
 * 
 * 
 * 2.	出现异常库存时,需要针对异常货重新修订走货路径.
 * 
 * 
 * 
 * 或
 * 
 * 
 * 3.	调整合车走货路径后,根据出库时的实际情况生成新的走货路径,替换货物原路径.
 * 
 * 
 * 或
 * 
 * 
 * 4.	入库时,如果当前货物的原路径处于路径调整的生效状态,则对该货物的走货路径进行调整.
 * 
 * 
 * 或
 * 
 * 
 * 
 * 5.	修改单生成且生效后,按照货物的新目的地,重新计算并生成走货路径.	接送货-开单
 * 
 * 
 * 
 * 
 *上报异常库存
 *
 *
 *查询&修改走货路
 *
 *
 *后置条件	
 *
 *1.	生成新的走货路径.	
 *
 *查询&修改走货路径
 *
 *
 *1.6	操作步骤
 *
 *
 *1.6.1	开单生成走货路径
 *
 *1	开单模块请求系统计算走货路经，
 *
 *系统根据货物的开单信息,获取货物运单号,
 *
 *流水号,出发营业部,到达营业部，
 *
 *开单时间.	开单信息	
 *
 *
 *
 *2	系统通过开单信息查询基础信息的走货路经模板,
 *
 *传递参数为：出发,到达营业部/虚拟营业部、开单时间	走货路径模板配置.	
 *
 *配置以营业部为单元,
 *
 *根据出发到达营业部,
 *
 *返回走货路径模板，
 *
 *内容包括：整条路径中的全部线路节点、中转场间的线路、以及每段线路的出发到达时间.
 *
 *
 *
 *3	系统根据出发营业部,
 *
 *走货路径模板上的第一个中转外场,
 *
 *查询线路信息，
 *
 *查询走货路径中第一条线路.
 *
 *系统根据走货路径模板上的最后一个中转外场,
 *
 *到达营业部,
 *
 *查询线路信息, 
 *
 *查询走货路径中最后一条线路.	
 *
 *线路	基础资料提供的是营业组到营业组的走货路径模版,
 *
 * 是按照出发组、
 * 
 * 到达组制定走货路径模板的.
 * 
 * 所以始发营业部到第一个中转部门,
 * 
 * 最后一个中转部门到到达营业部的线路未确定.
 * 
 * 根据走货路径模版中提供的通用出发到达时间,
 * 
 * 匹配该段最终选择的线路。
 *
 *
 *4	系统保存完整的走货路径信息，
 *
 *并反馈给开单模块		
 *
 *系统返回走货路径结果给开单信息模块
 *
 *
 *
 *1.6.2	多货修改走货路径
 *
 *
 *1	系统根据多货的货物信息,
 *
 *获取货物运单号,
 *
 *流水号,
 *
 *到达营业部/虚拟营业部.	
 *
 *开单信息	
 *
 *
 *2	系统根据该货物现在所在的中转外场,
 *
 *获取中转场的驻地营业部信息.		
 *
 *多货则该货物本不该出现在该中转场,
 *
 *但是现在已经到达,
 *
 *则需要改变走货路径,
 *
 *以目前位置为起点,到达营业部为终点,
 *
 *重新确定走货路径.
 *
 *
 *
 *3	执行1.6.1的步骤		
 *
 *
 *4	保存新的走货路径,
 *
 *作废原货物走货路径		
 *
 *
 *
 *1.6.3	调整合车货物走货路径A(同一外场合车)
 *
 *
 *1	外场操作员根据需要,
 *
 *提出合车,
 *
 *人工调整中途经过的外场以及线路.		
 *
 *根据该外场,
 *
 *搜索出相应货物的原路径.
 *
 *参考用例”调整合车货物走货路经”
 *
 *
 *
 *2	系统接受多条线路合并的修改走货路径信息.		
 *
 *调整页面可能会将线路A,B 
 *
 *合并至线路C进行统一调配.
 *
 *
 *
 *3	系统将全部被调整线路进行记录		
 *
 *此时不修改货物的实际走货路径.
 *
 *只记录合车调整的动作.
 *
 *
 *
 *4	当有合车操作的货物实际出库装车时,
 *
 *根据实际所选择的路径,
 *
 *记录货物真实走货路径,
 *
 *和系统中的原路径匹配,
 *
 *进行更新和再计算.		
 *
 *此时更新原路径为实际所选择路径,更新后序走货路径.
 *
 *
 *
 *
 *
 *1.6.4	
 *
 *调整合车货物走货路径B(针对某一段路径进行调整不同外场之间合车)
 *
 *序号	基本步骤	相关数据	补充步骤
 *
 *1	外场操作员根据需要,
 *
 *提出合车调整,
 *
 *人工调整中途经过的外场以及线路.	
 *
 *	参考用例”调整合车货物走货路经”
 *
 *人工调整的路径可以是走货路径模板之外的走货方式
 *
 *
 *2	可以选择多个外场和外场之间的线路，重复1步骤		
 *
 *
 *3	系统根据指定的最后一个到达地点及货物的最终到达地点.
 *
 *生成新的走货路径		
 *
 *原货由A-B-C-D,
 *
 *现在人工改为A-E-F,
 *
 *人工指定的最后中转站为F.
 *
 *则系统需要生成F-D的子走货路径.
 *
 *
 *4			以目前位置为起点,到达营业部为终点,重新确定走货路
 *
 *
 *5	执行1.6.1的步骤		
 *
 *
 *6	系统保存新增的走货路径.		
 *
 *目前对于该货物有两条走货路径.替换该货物的原走货路径
 *
 *
 *7	装车后，
 *
 *按照实际装车所走的路径保留最终的走货路径,
 *
 *并且系统作废两条路径中无效的条目.
 *
 *确定唯一走货路径.		
 *
 *操作员对该批货物进行装车扫描时,会调用接口回传最终选择路径,系统根据实际的选择, 作废无效条目.确定该批货物的唯一走货路径.
 *
 *1.6.5	更改单修改走货路径
 *
 *序号	基本步骤	相关数据	补充步骤
 *
 *
 *1	更改单提出后,通过待办事项打印新的货物标签.		
 *
 *此时可能会修改货物的目的地信息
 *
 *
 *2	获取更改信息中的货物目的地,及货物现所在地信息.		
 *
 *货物在途中,则新标签会在下个货物停靠外场或营业部生效.货物所在地信息也使用该外场或营业部.
 *
 *
 *3	系统根据指定新目的地及所在地信息.
 *
 *生成后续的走货路径		
 *
 *
 *4	系统保存新增的走货路径.		
 *
 *替换该货物的原走货路径中未走的部分线路.
 *
 *
 *
 *
 *
 *1.7	业务规则
 *
 *SR-1	
 *
 *所有外场都有其驻地营业部,所以出发或到达地为外场的,将自动转化为该驻地营业部.
 *
 *
 *
 *
 *
 *
 *
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ChangePathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.DepartureDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAdjustTransportationPathService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustTransportationPathDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IChangePathDao;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.util.UUIDUtils;

/**
 * 调整走货路径service实现
 * 
 * @author huyue
 * @date 2012-10-12 下午6:44:01
 */
public class AdjustTransportationPathService implements IAdjustTransportationPathService {
	
	//日志
	private static final Logger logger = LogManager.getLogger(AdjustTransportationPathService.class);
	
	/**
	 * 调整走货路径dao
	 * 
	 */
	private IAdjustTransportationPathDao adjustTransportationPathDao;
	/**
	 * 合车&非合车调整dao
	 * 
	 */
	private IChangePathDao changePathDao;
	/**
	 * 线路service
	 * 
	 */
	private ILineService lineService;
	
	private IExpressLineService expresslineService ;
	
	/**
	 * 货区service
	 */
	private IGoodsAreaService goodsAreaService;
	/**
	 * 库存service
	 */
	private IStockService stockService;
	/**
	 * 计算走货路径service
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	/**
	 * 组织相关service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 数据字典service
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	//常量 保留3位小数字
	private static final int three = 3;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	
	//外场service
	//未使用-352203
//	private ICommonTransferCenterService commonTransferCenterService;

	/**
	 * orgAdministrativeInfoComplexService
	 * 
	 * @return the orgAdministrativeInfoComplexService
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set Date:2013-5-1上午9:54:29
	 */

	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	/**
	 * 获取 合车&非合车调整dao
	 */
	public IChangePathDao getChangePathDao() {
		return changePathDao;
	}
	/**
	 * 设置 合车&非合车调整dao
	 */
	public void setChangePathDao(IChangePathDao changePathDao) {
		this.changePathDao = changePathDao;
	}
	/**
	 * 获取 调整走货路径dao
	 */
	public IAdjustTransportationPathDao getAdjustTransportationPathDao() {
		return adjustTransportationPathDao;
	}
	/**
	 * 设置 调整走货路径dao
	 */
	public void setAdjustTransportationPathDao(IAdjustTransportationPathDao adjustTransportationPathDao) {
		this.adjustTransportationPathDao = adjustTransportationPathDao;
	}
	/**
	 * 获取 计算走货路径service
	 */
	public ICalculateTransportPathService getCalculateTransportPathService() {
		return calculateTransportPathService;
	}
	/**
	 * 设置 计算走货路径service
	 */
	public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	/**
	 * 获取 线路service
	 */
	public ILineService getLineService() {
		return lineService;
	}

	/**
	 * 设置 线路service
	 */
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}
	
	public IExpressLineService getExpresslineService() {
		return expresslineService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}
	
	/**
	 * 获取 货区service
	 */
	public IGoodsAreaService getGoodsAreaService() {
		return goodsAreaService;
	}
	/**
	 * 设置 货区service
	 */
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	/**
	 * 获取 库存service
	 */
	public IStockService getStockService() {
		return stockService;
	}
	/**
	 * 设置 库存service
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	/**
	 * 获取 组织相关service
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}
	/**
	 * 设置 组织相关service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 获取 数据字典service
	 */
	public IDataDictionaryValueService getDataDictionaryValueService() {
		return dataDictionaryValueService;
	}
	/**
	 * 设置 数据字典service
	 */
	public void setDataDictionaryValueService(IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	/**
	 * 查询调整走货路径LIST,匹配运单表,库存表,只精确到库区, 有边界值
	 * 
	 * @author huyue
	 * @date 2012-10-12 下午6:49:17
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IAdjustTransportationPathService#queryLevel1(com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity,
	 *      int, int)
	 */
	@Override
	public List<AdjustEntity> queryLevel1(AdjustEntity adjustEntity, int limit, int start) throws TfrBusinessException {
		BigDecimal countW;
		BigDecimal countV;

		// modify by liangfuxiang 2013-6-3下午10:32:21 begin BUG-19817 烟台到济南的货物，先需要与青岛合车，但是青岛和济南货区排列不在一个页面，导致无法合车

		// // modify by liangfuxiang 2013-5-1上午9:56:31 begin BUG-8196
		reInitAdjustEntity(adjustEntity);
		// // modify by liangfuxiang 2013-5-1上午9:56:55 end;
		//
		// // 根据组织号获取库区list 并且分页
		// List<AdjustEntity> adjust = adjustTransportationPathDao.queryLevel1(adjustEntity, limit, start);
		//
		// // 根据组织号获取运单号list
		// List<AdjustEntity> waybillList = adjustTransportationPathDao.queryWaybillList(adjustEntity);
		// 获取获取集合
		List<String> goodsAreaCodesList = getGoodsAreaCodesByAdjustEntity(adjustEntity);
		//判断是否包含快递货区
		if(StringUtils.isNotBlank(adjustEntity.getPackageGoodsAreaCode()) && StringUtils.isNotBlank(adjustEntity.getPackageNextOrgCode())){
			for (String str : goodsAreaCodesList) {
				if(str.equals(adjustEntity.getPackageGoodsAreaCode())){
					goodsAreaCodesList.remove(adjustEntity.getPackageGoodsAreaCode());
					break;
				}
			}
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgCode", adjustEntity.getOrgCode());
		paramMap.put("goodsAreaCodesList", goodsAreaCodesList);
		paramMap.put("packageNextOrgCode",adjustEntity.getPackageNextOrgCode());
		paramMap.put("packageGoodsAreaCode", adjustEntity.getPackageGoodsAreaCode());
		// 根据组织号获取库区list 并且分页
		List<AdjustEntity> adjust = adjustTransportationPathDao.queryLevel1ByParamMap(paramMap, limit, start);
		// 根据组织号获取运单号list
		List<AdjustEntity> waybillList = adjustTransportationPathDao.queryWaybillListByParamMap(paramMap);
		// modify by liangfuxiang 2013-6-3下午10:32:42 end BUG-19817;
		// 将waybill合并为库区数据
		for (int i = 0; i < adjust.size(); i++) {
			// 如果组织code不为空
			if (null != adjustEntity && StringUtils.isNotEmpty(adjustEntity.getOrgCode())) {
				// 设置组织code和组织名称
				adjust.get(i).setOrgCode(adjustEntity.getOrgCode());
				adjust.get(i).setOrgName(getNameByCode(adjustEntity.getOrgCode()));
			}
			// 调用基础资料查询库区对应的库区线路信息
			GoodsAreaEntity line = goodsAreaService.queryGoodsAreaByCode(adjust.get(i).getOrgCode(), adjust.get(i).getGoodsAreaCode());

			// modify by liangfuxiang 2013-7-17下午3:05:15 begin BUG-45551 深圳枢纽中心 外场调整走货路由时，打开页面即报错，请解决。

			// //如果得不到库区线路信息
			// if (null == line) {
			// // modify by liangfuxiang 2013-5-23下午3:22:50 begin 增加日志
			// logger.error("AdjustTransportationPathService[queryLevel1()]:" + TransportPathConstants.QUERYGOODSAREABYCODE_NULL + "OrgCode : " + adjust.get(i).getOrgCode() + " GoodsAreaCode : "
			// + adjust.get(i).getGoodsAreaCode());
			// // 抛异常提示
			// // throw new TfrBusinessException("通过货区获取线路信息返回值为空! OrgCode : " + adjust.get(i).getOrgCode() + " GoodsAreaCode : " + adjust.get(i).getGoodsAreaCode(), "");
			// throw new TfrBusinessException(TransportPathConstants.QUERYGOODSAREABYCODE_NULL_GLOB, new Object[] { calculateTransportPathService.getOrgNameAndCode(adjust.get(i).getOrgCode()),
			// adjust.get(i).getGoodsAreaCode() });
			// // modify by liangfuxiang 2013-5-23下午3:23:14 end;
			// }

			// 给重量和体积赋0值
			countW = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			countV = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			// 循环查询到的运单list
			for (int j = 0; j < waybillList.size(); j++) {
				// 如果运单的货区为当前货区
				if (StringUtils.equals(waybillList.get(j).getGoodsAreaCode(), adjust.get(i).getGoodsAreaCode()) && null != waybillList.get(j).getGoodsQtyTotal()
						&& null != waybillList.get(j).getGoodsWeightTotal() && null != waybillList.get(j).getGoodsVolumeTotal()) {
					BigDecimal bd = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
					// 获取在库件数和总件数的比
					bd = BigDecimal.valueOf((double) waybillList.get(j).getStockGoodsQTY() / (double) waybillList.get(j).getGoodsQtyTotal());
					// 根据比值乘总票重量&体积,累加重量和体积
					countW = countW.add(waybillList.get(j).getGoodsWeightTotal().multiply(bd));
					countV = countV.add(waybillList.get(j).getGoodsVolumeTotal().multiply(bd));
				}
			}
			// 设置货区重量和体积
			adjust.get(i).setAreaWeightTotal(countW);
			adjust.get(i).setAreaVolumeTotal(countV);

			if (null == line) {

				logger.warn("AdjustTransportationPathService[queryLevel1()]:" + TransportPathConstants.QUERYGOODSAREABYCODE_NULL + "OrgCode : " + adjust.get(i).getOrgCode() + " GoodsAreaCode : "
						+ adjust.get(i).getGoodsAreaCode());
				// 设置库区编码
				adjust.get(i).setGoodsAreaName(adjust.get(i).getGoodsAreaCode());
				// 不可调整货区
				adjust.get(i).setIfDisable(0);
				// 设置线路
				adjust.get(i).setAreaLine(
						TransportPathConstants.QUERYGOODSAREABYCODE_NULL + calculateTransportPathService.getOrgNameAndCode(adjust.get(i).getOrgCode()) + TransportPathConstants.ORIGGOODSAREACODE
								+ adjust.get(i).getGoodsAreaCode());
			}
			else {

				// 如果库区线路信息中有库区名
				if (StringUtils.isNotEmpty(line.getGoodsAreaName())) {
					// // 设置库区名
					adjust.get(i).setGoodsAreaName(line.getGoodsAreaName());
				}
				// 判断是否不可调整路径货区
				if (StringUtils.isEmpty(line.getArriveRegionCode()) && !"BSE_GOODSAREA_TYPE_EXPRESS".equals(line.getGoodsAreaType())) {
					// 根据TYPE获取数据字典中的TYPE名称
					if (StringUtils.isNotEmpty(line.getGoodsAreaType())) {
						// 根据 TERMS_CODE,VALUE_CODE 查询值对象：
						DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_GOODSAREA_TYPE,
								line.getGoodsAreaType());
						if (null != dataDictionaryValueEntity && StringUtils.isNotEmpty(dataDictionaryValueEntity.getValueName())) {
							// 不为空则设置名称
							adjust.get(i).setAreaLine(dataDictionaryValueEntity.getValueName());
						}
						else {
							// 否则设置type
							adjust.get(i).setAreaLine(line.getGoodsAreaType());
						}
					}
					// 不可调整货区则设置为0
					adjust.get(i).setIfDisable(1);
				}
				else {
					//允许快递货区修改
					if("BSE_GOODSAREA_TYPE_EXPRESS".equals(line.getGoodsAreaType())){
						// 根据 TERMS_CODE,VALUE_CODE 查询值对象：
						DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_GOODSAREA_TYPE,
								line.getGoodsAreaType());
						// 不为空则设置名称
						adjust.get(i).setAreaLine(dataDictionaryValueEntity.getValueName());
					}else{
						// 设置线路 根据出发和到达部门进行拼接
						adjust.get(i).setAreaLine(getNameByCode(adjust.get(i).getOrgCode()) + "-" + line.getArriveRegionName());
					}
					
				}
			}
			// modify by liangfuxiang 2013-7-17下午3:05:15 end BUG-45551 深圳枢纽中心 外场调整走货路由时，打开页面即报错，请解决。
		}
		return adjust;
	}
	
	/**
	 * @Title: getGoodsAreaCodesByAdjustEntity
	 * @Description: 获取组织编码
	 * @param adjustEntity
	 * @return 设定文件
	 * @return List<String> 返回类型
	 * @see getOrgCodesByAdjustEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-6-3 下午10:33:59
	 * @throws
	 */
	private List<String> getGoodsAreaCodesByAdjustEntity(AdjustEntity adjustEntity) {

		// 库区编码集合
		List<String> initGoodsAreaCodesList = new ArrayList<String>();

		// 获取当前组织
		String initGoodsAreaCodes = adjustEntity.getGoodsAreaCode();
		// 当前组织为空
		if (!StringUtil.isEmpty(initGoodsAreaCodes) && StringUtils.length(StringUtils.trim(initGoodsAreaCodes)) > 0) {
			// 拆分多个orgCode
			String[] inputOrgCodes = initGoodsAreaCodes.split(",");
			// 保存在列表中
			for (int i = 0; i < inputOrgCodes.length; i++) {
				initGoodsAreaCodesList.add(StringUtils.trim(inputOrgCodes[i]));
			}
		}
		return initGoodsAreaCodesList;
	}

	/**
	 * 
	 * @Title: reInitAdjustEntity
	 * @Description: 重新初始化<修改货物走货路径>
	 * @param adjustEntity 设定文件
	 * @return void 返回类型
	 * @see reInitAdjustEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-5-1 上午10:23:40
	 * @throws
	 */
	private void reInitAdjustEntity(AdjustEntity adjustEntity) {
		if (null == adjustEntity) {
			logger.error(TransportPathConstants.QUERY_LEVEL1_ADJUSTENTITY_NULL);
			throw new TfrBusinessException(TransportPathConstants.QUERY_LEVEL1_ADJUSTENTITY_GLOB);
		}
		// 获取当前组织
		String initOrgCode = adjustEntity.getOrgCode();
		// 当前组织为空
		if (StringUtil.isEmpty(initOrgCode)) {
			logger.info(TransportPathConstants.QUERY_LEVEL1_ORGCODE_NULL);
			//重新获取当前部门编码
			initOrgCode=FossUserContext.getCurrentDeptCode();
		}
		// 根据当前组织编码获取组织信息
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = querySuperiorOrgByOrgCode(initOrgCode);
		// 重新配置当前组织编码
		adjustEntity.setOrgCode(orgAdministrativeInfoEntity.getCode());
	}
	
	/**
	 * 
	 * @Title: querySuperiorOrgByOrgCode
	 * @Description: 根据当前部门获取查找顶级组织部门
	 * @param orgCode
	 * @return 设定文件
	 * @return OrgAdministrativeInfoEntity 返回类型
	 * @see querySuperiorOrgByOrgCode
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-5-1 上午9:40:28
	 * @throws
	 */
	public OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode) throws TfrBusinessException {

		// 设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		// 外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		// 空运总调类型
		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
		// 营业部（派送部）
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		// 查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if (orgAdministrativeInfoEntity != null) {
			// 返回部门
			return orgAdministrativeInfoEntity;
		}
		else {
			// 获取上级部门失败
			logger.error("AdjustTransportationPathService[querySuperiorOrgByOrgCode()]:" + TransportPathConstants.QUERY_TOP_ORG_BY_CURRENT_ORG_FAIL + orgCode);
			throw new TfrBusinessException(TransportPathConstants.QUERY_TOP_ORG_BY_CURRENT_ORG_FAIL_GLOB, new Object[] { orgCode });
		}
	}
	
	/**
	 * 获取本库区中以运单为单位的条目
	 * 
	 * @author huyue
	 * @date 2012-11-13 上午9:40:23
	 */
	public List<AdjustEntity> queryByGoodsArea(AdjustEntity adjustEntity) throws TfrBusinessException {
		// 获取所有原部门,库区为当前的数据
		List<AdjustEntity> adjustList = adjustTransportationPathDao.queryWaybillList(adjustEntity);
		// 得到运单号的list 调用运单接口得到对应的重量,体积,件数
		for (int i = 0; i < adjustList.size(); i++) {
			//如果部门code不为空
			if (null != adjustList.get(i).getOrgCode()) {
				//则获取并设置部门名称
				adjustList.get(i).setOrgName(getNameByCode(adjustList.get(i).getOrgCode()));
			}
			// 如果不为空.计算实际库区内的重量体积件数
			if (null != adjustList.get(i).getGoodsQtyTotal() && null != adjustList.get(i).getGoodsWeightTotal() && null != adjustList.get(i).getGoodsVolumeTotal()) {
				BigDecimal bd = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
				//获取在库件数和总件数的比
				bd = BigDecimal.valueOf((double) adjustList.get(i).getStockGoodsQTY() / (double) adjustList.get(i).getGoodsQtyTotal());
				//根据比值乘总票重量&体积,累加重量和体积
				adjustList.get(i).setGoodsWeightTotal(adjustList.get(i).getGoodsWeightTotal().multiply(bd));
				adjustList.get(i).setGoodsVolumeTotal(adjustList.get(i).getGoodsVolumeTotal().multiply(bd));
			} else {
				// 为空则设置为0
				adjustList.get(i).setGoodsWeightTotal(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
				adjustList.get(i).setGoodsVolumeTotal(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
			}
		}
		return adjustList;
	}

	/**
	 * 查询调整走货路径2级list, 根据waybill
	 * 
	 * @author huyue
	 * @date 2012-10-12 下午6:49:17
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IAdjustTransportationPathService#queryLevel1(com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity,
	 *      int, int)
	 */
	@Override
	public List<AdjustEntity> queryLevel2(AdjustEntity adjustEntity) throws TfrBusinessException {
		//设置原路径为空
		String origPath = "";
		int ifDisable = 0;
		// 调用基础资料查询运单对应的修改前线路
		GoodsAreaEntity line = goodsAreaService.queryGoodsAreaByCode(adjustEntity.getOrgCode(), adjustEntity.getGoodsAreaCode());

		// modify by liangfuxiang 2013-7-17下午7:13:28 begin BUG-45551
		if (null == line) {
			logger.error("AdjustTransportationPathService[queryLevel2()]:" + TransportPathConstants.QUERYGOODSAREABYCODE_NULL + "OrgCode : " + adjustEntity.getOrgCode() + " GoodsAreaCode : "
					+ adjustEntity.getGoodsAreaCode());
			origPath = TransportPathConstants.QUERYGOODSAREABYCODE_NULL + calculateTransportPathService.getOrgNameAndCode(adjustEntity.getOrgCode()) + TransportPathConstants.ORIGGOODSAREACODE
					+ adjustEntity.getGoodsAreaCode();
			ifDisable = 0;
		}
		else {
			// 判断是否不可调整路径货区
			if (StringUtils.isEmpty(line.getArriveRegionCode()) && !"BSE_GOODSAREA_TYPE_EXPRESS".equals(line.getGoodsAreaType())) {
				// 根据TYPE获取数据字典中的TYPE名称
				if (StringUtils.isNotEmpty(line.getGoodsAreaType())) {
					//根据 TERMS_CODE,VALUE_CODE 查询值对象
					DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_GOODSAREA_TYPE, line.getGoodsAreaType());
					if (null != dataDictionaryValueEntity && StringUtils.isNotEmpty(dataDictionaryValueEntity.getValueName())) {
						//如果不为空则设置原路径
						origPath = dataDictionaryValueEntity.getValueName();
					} else {
						//否则设置为库区类型
						origPath = line.getGoodsAreaType();
					}
				}
				// 不可调整货区则设置为0
				ifDisable = 1;
			} else {
				//允许快递货区修改
				if("BSE_GOODSAREA_TYPE_EXPRESS".equals(line.getGoodsAreaType())){
					// 根据 TERMS_CODE,VALUE_CODE 查询值对象：
					DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_GOODSAREA_TYPE,
							line.getGoodsAreaType());
					// 不为空则设置名称
					origPath = dataDictionaryValueEntity.getValueName();
				}else{
					//非快递库区时，快递的目的站部门code置为空
					adjustEntity.setPackageNextOrgCode("");
				//设置线路 根据出发和到达部门进行拼接
				origPath = line.getOrganizationName() + "-" + line.getArriveRegionName();
				}
			}
		}
		

		// 获取所有原部门,库区为当前的数据
		List<AdjustEntity> adjustList = adjustTransportationPathDao.queryWaybillList(adjustEntity);
		if(CollectionUtils.isNotEmpty(adjustList)){
			// 得到运单号的list 调用运单接口得到对应的重量,体积,件数
			for (int i = 0; i < adjustList.size(); i++) {
				if (null != adjustList.get(i).getOrgCode()) {
					if(null!=line){
						adjustList.get(i).setOrgName(line.getOrganizationName());
					}
					else{
						adjustList.get(i).setOrgName(adjustEntity.getOrgCode());
					}
				}
				// 如果不为空.计算实际库区内的重量体积件数
				if (null != adjustList.get(i).getGoodsQtyTotal() && null != adjustList.get(i).getGoodsWeightTotal() && null != adjustList.get(i).getGoodsVolumeTotal()) {
					BigDecimal bd = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
					//获取在库件数和总件数的比
					bd = BigDecimal.valueOf((double) adjustList.get(i).getStockGoodsQTY() / (double) adjustList.get(i).getGoodsQtyTotal());
					//根据比值乘总票重量&体积,累加重量和体积
					adjustList.get(i).setGoodsWeightTotal(adjustList.get(i).getGoodsWeightTotal().multiply(bd));
					adjustList.get(i).setGoodsVolumeTotal(adjustList.get(i).getGoodsVolumeTotal().multiply(bd));
				} else {
					// 为空则设置为0
					adjustList.get(i).setGoodsWeightTotal(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
					adjustList.get(i).setGoodsVolumeTotal(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
				}
				//设置原路径
				adjustList.get(i).setBeforeLine(origPath);
				//设置是否不能修改
				adjustList.get(i).setIfDisable(ifDisable);
				// 根据运单号,本部门,修改前库区,查库存改变表,得到修改后库存编号list
				Map<String, String> changeMap = new HashMap<String, String>();
				changeMap.put("waybillNo", adjustList.get(i).getWaybillNo());
				changeMap.put("orgCode", adjustEntity.getOrgCode());
				changeMap.put("origGoodsAreaCode", adjustEntity.getGoodsAreaCode());
				//根据运单号,部门号,原库区编号查询改变后的库区list
				List<String> newGoodsAreaCodeList = adjustTransportationPathDao.queryNewGoodsAreaCodeList(changeMap);
				StringBuffer path = new StringBuffer();
				// 如果为0 则表示没有调整后路径
				if (newGoodsAreaCodeList.size() == 0) {
					//设置为空
					path.append("");
				} else {
					//快递除外 PRICING_PRODUCT_EXPRESS_PACKAGE
					//goodsAreaService.queryCodeByArriveRegionCode(organizationCode, arriveRegionCode, productCode)
					for (int j = 0; j < newGoodsAreaCodeList.size(); j++) {
						// 调用基础资料方法查询调整后目的地
						GoodsAreaEntity afterLine = goodsAreaService.queryGoodsAreaByCode(adjustList.get(i).getOrgCode(), newGoodsAreaCodeList.get(j));
						if(afterLine!=null)
						{
							if(StringUtils.isNotBlank(afterLine.getGoodsAreaName())){
								if(StringUtils.contains(afterLine.getGoodsAreaName(), "快递库区")){
									path.append("快递库区");
								}
							}else{
								String arriveOrgName = afterLine.getArriveRegionName();
								// 拼接线路
								if (j != 0) {
									path.append("/");
								}
								path.append(afterLine.getOrganizationName());
								path.append("-");
								path.append(arriveOrgName);
							}
							
						}
					}
				}
				//设置调整后的线路
				adjustList.get(i).setAfterLine(path.toString());
			}
		}
		
		// 获取所有修改后货区为当前的条目
		// 查询修改后为本线路的运单信息
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgCode", adjustEntity.getOrgCode());
		map.put("goodsAreaCode", adjustEntity.getGoodsAreaCode());
		// 获取所有修改后部门,库区为当前要求的数据
		List<AdjustEntity> changedAdjustList = adjustTransportationPathDao.queryChangedWaybillList(map);
		
		if(CollectionUtils.isNotEmpty(changedAdjustList)){
			// 更新其他字段
			for (int i = 0; i < changedAdjustList.size(); i++) {
				// 如果不为空,计算实际库区内的重量体积件数
				if (null != changedAdjustList.get(i).getGoodsQtyTotal() && changedAdjustList.get(i).getGoodsQtyTotal() != 0 && null != changedAdjustList.get(i).getGoodsWeightTotal() && !changedAdjustList.get(i).getGoodsWeightTotal().equals(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN)) && null != changedAdjustList.get(i).getGoodsVolumeTotal() && !changedAdjustList.get(i).getGoodsVolumeTotal().equals(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN))) {
					BigDecimal bd = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
					//获取在库件数和总件数的比
					bd = BigDecimal.valueOf((double) changedAdjustList.get(i).getStockGoodsQTY() / (double) changedAdjustList.get(i).getGoodsQtyTotal());
					//根据比值乘总票重量&体积,累加重量和体积
					changedAdjustList.get(i).setGoodsWeightTotal(changedAdjustList.get(i).getGoodsWeightTotal().multiply(bd));
					changedAdjustList.get(i).setGoodsVolumeTotal(changedAdjustList.get(i).getGoodsVolumeTotal().multiply(bd));
				} else {
					// 如果为空则设置为0
					changedAdjustList.get(i).setGoodsWeightTotal(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
					changedAdjustList.get(i).setGoodsVolumeTotal(BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN));
				}
				// 调用基础资料接口获取修改前的线路
				GoodsAreaEntity beforeLine = goodsAreaService.queryGoodsAreaByCode(changedAdjustList.get(i).getOrgCode(), changedAdjustList.get(i).getGoodsAreaCode());
				
				if(null!=beforeLine){
					//有快递货区 合车过来的运单
					if(WaybillConstants.PACKAGE.equals(changedAdjustList.get(i).getProductCode())
						||"RCP".equals(changedAdjustList.get(i).getProductCode())
						||"EPEP".equals(changedAdjustList.get(i).getProductCode())
						){
						changedAdjustList.get(i).setBeforeLine("快递库区");
					}else{
						changedAdjustList.get(i).setBeforeLine(beforeLine.getOrganizationName() + "-" + beforeLine.getArriveRegionName());
					}
					
					// 不可调整设置为0
					changedAdjustList.get(i).setIfDisable(1);
				}
				else{
					
					logger.error("AdjustTransportationPathService[queryLevel2()]:" + TransportPathConstants.QUERYGOODSAREABYCODE_NULL + "OrgCode : " + changedAdjustList.get(i).getOrgCode() + " GoodsAreaCode : "
							+ changedAdjustList.get(i).getGoodsAreaCode());
					changedAdjustList.get(i).setBeforeLine(TransportPathConstants.QUERYGOODSAREABYCODE_NULL + calculateTransportPathService.getOrgNameAndCode(changedAdjustList.get(i).getOrgCode()) + TransportPathConstants.ORIGGOODSAREACODE
							+ changedAdjustList.get(i).getGoodsAreaCode());
					// 不可调整设置为0
					changedAdjustList.get(i).setIfDisable(0);
				}
				// 设置修改后线路为本线路
				changedAdjustList.get(i).setAfterLine(origPath);
			}
		}
		
		// 添加修改后为本库区的进原List
		adjustList.addAll(changedAdjustList);
		// modify by liangfuxiang 2013-7-17下午7:13:28 end BUG-45551
		return adjustList;
	}

	/**
	 * 查询走货路径count
	 * 
	 * @author huyue
	 * @date 2012-10-12 下午6:49:21
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IAdjustTransportationPathService#getCount(com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity)
	 */
	@Override
	public Long getCount(AdjustEntity adjustEntity) throws TfrBusinessException {
		// modify by liangfuxiang 2013-5-1上午10:25:12 begin BUG-8196
		reInitAdjustEntity(adjustEntity);
		// modify by liangfuxiang 2013-5-1上午10:25:25 end;
		//查询条数,便于分页
		return adjustTransportationPathDao.getCount(adjustEntity);
	}

	/**
	 * 调整走货路径第三层查询,根据运单匹配在库货件号
	 * 
	 * @author huyue
	 * @date 2012-10-12 下午8:00:14
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IAdjustTransportationPathService#queryLevel3(com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity)
	 */
	@Override
	public List<AdjustEntity> queryLevel3(AdjustEntity adjustEntity) throws TfrBusinessException {
		//根据运单信息查询货件LIST
		return adjustTransportationPathDao.queryLevel3(adjustEntity);
	}

	/**
	 * 根据现部门code 找寻可能到达的部门code list
	 * 
	 * @author huyue
	 * @date 2012-11-6 下午7:17:07
	 */
	public List<String> findObjectiveOrgCode(String origOrgCode) throws TfrBusinessException {
		// 调用基础资料接口得到相应可以到达的外场
		List<String> objectiveOrgCodeList = lineService.queryTargetCodeListBySourceCode(origOrgCode);
		//如果为空
		if (null == objectiveOrgCodeList) {
			List<String> objectiveOrgCodeList2 = expresslineService.queryTargetCodeListBySourceCode(origOrgCode);
			if (null == objectiveOrgCodeList2) {
			// modify by liangfuxiang 2013-5-29下午5:26:12 begin 增加日志，细化异常
			logger.error("AdjustTransportationPathService[findObjectiveOrgCode()]:" + TransportPathConstants.TRANSPORTPATH_ADJUST_CANTFINDTRANSFERCENTER + origOrgCode);
			// 抛异常
			throw new TfrBusinessException(TransportPathConstants.TRANSPORTPATH_ADJUST_CANTFINDTRANSFERCENTER, new Object[] { origOrgCode });
			// modify by liangfuxiang 2013-5-29下午5:26:28 end;
			}else {
				return objectiveOrgCodeList2;
			}
		} else {
			return objectiveOrgCodeList;
		}
	}

	/**
	 * 根据现部门和下一部门找到所有的班次
	 * 
	 * @author huyue
	 * @date 2012-11-6 下午7:14:48
	 */
	public List<DepartureDto> findDepartMsg(String origOrgCode, String destOrgCode) throws TfrBusinessException {
		
		// modify by liangfuxiang 2013-5-23下午4:53:48 begin 验证入参有效性
		if (StringUtils.isEmpty(origOrgCode)) {
			logger.error("AdjustTransportationPathService[findDepartMsg()]:"+TransportPathConstants.CURRENT_ORG_CODE_IS_NULL);
			throw new TfrBusinessException(TransportPathConstants.CURRENT_ORG_CODE_IS_NULL_GLOB);
		}

		if (StringUtils.isEmpty(destOrgCode)) {
			logger.error("AdjustTransportationPathService[findDepartMsg()]:"+TransportPathConstants.DEST_ORG_CODE_IS_NULL);
			throw new TfrBusinessException(TransportPathConstants.DEST_ORG_CODE_IS_NULL_GLOB);
		}

		// modify by liangfuxiang 2013-5-23下午4:54:32 end;
		List<DepartureStandardDto> departureStandardDto = lineService.queryDepartureStandardListBySourceTarget(origOrgCode, destOrgCode);
		
		if (departureStandardDto == null) {
			List<DepartureStandardDto> departureStandardDto2 = expresslineService.queryDepartureStandardListBySourceTarget(origOrgCode, destOrgCode);
			if (departureStandardDto2 == null) {
				// modify by liangfuxiang 2013-5-23下午4:52:25 begin 添加日志
				// throw new TfrBusinessException(TransportPathConstants.TRANSPORTPATH_ADJUST_CANTFINDDEPARTURESTANDARD, "");
				logger.error("AdjustTransportationPathService[findDepartMsg()]:" + TransportPathConstants.TRANSPORTPATH_ADJUST_CANTFINDDEPARTURESTANDARD + TransportPathConstants.CURRENT_ORGCODE + origOrgCode
						+ TransportPathConstants.BLANK_SPACE_STRING + TransportPathConstants.DEST_ORGCODE + destOrgCode);
				throw new TfrBusinessException(TransportPathConstants.TRANSPORTPATH_ADJUST_CANTFINDDEPARTURESTANDARD,new Object[]{origOrgCode,destOrgCode});
				// modify by liangfuxiang 2013-5-23下午4:52:30 end;
			}else{
				//新建DTO
				List<DepartureDto> departureDto = new ArrayList<DepartureDto>();
				Date nowDate = new Date();
				// 赋值
				for (int i = 0; i < departureStandardDto2.size(); i++) {
					departureDto.add(new DepartureDto());
					//到达天数
					departureDto.get(i).setArriveDay(departureStandardDto2.get(i).getArriveDay());
					//到达时间
					departureDto.get(i).setArriveTime(departureStandardDto2.get(i).getArriveDate(nowDate));
					//班次虚拟编码
					departureDto.get(i).setDepartureStandardVirtualCode(departureStandardDto2.get(i).getDepartureStandardVirtualCode());
					//出发时间
					departureDto.get(i).setLeaveTime(departureStandardDto2.get(i).getLeaveDate(nowDate));
					//线路名称
					departureDto.get(i).setLineName(departureStandardDto2.get(i).getLineName());
					//线路虚拟编码
					departureDto.get(i).setLineVirtualCode(departureStandardDto2.get(i).getLineVirtualCode());
					//出发部门code
					departureDto.get(i).setSourceCode(departureStandardDto2.get(i).getSourceCode());
					//到达部门code
					departureDto.get(i).setTargetCode(departureStandardDto2.get(i).getTargetCode());
				}
				return departureDto;
			}
		}else{
			//新建DTO
			List<DepartureDto> departureDto = new ArrayList<DepartureDto>();
			Date nowDate = new Date();
			// 赋值
			for (int i = 0; i < departureStandardDto.size(); i++) {
				departureDto.add(new DepartureDto());
				//到达天数
				departureDto.get(i).setArriveDay(departureStandardDto.get(i).getArriveDay());
				//到达时间
				departureDto.get(i).setArriveTime(departureStandardDto.get(i).getArriveDate(nowDate));
				//班次虚拟编码
				departureDto.get(i).setDepartureStandardVirtualCode(departureStandardDto.get(i).getDepartureStandardVirtualCode());
				//出发时间
				departureDto.get(i).setLeaveTime(departureStandardDto.get(i).getLeaveDate(nowDate));
				//线路名称
				departureDto.get(i).setLineName(departureStandardDto.get(i).getLineName());
				//线路虚拟编码
				departureDto.get(i).setLineVirtualCode(departureStandardDto.get(i).getLineVirtualCode());
				//出发部门code
				departureDto.get(i).setSourceCode(departureStandardDto.get(i).getSourceCode());
				//到达部门code
				departureDto.get(i).setTargetCode(departureStandardDto.get(i).getTargetCode());
			}
			return departureDto;
		}
	}

	/**
	 * 非合车调整走货路径 保存至改变表 更新相应走货路径
	 * 
	 * @author huyue
	 * @throws Exception
	 * @date 2012-11-6 下午7:07:41
	 */
	// modify by liangfuxiang 2013-5-24下午1:57:52 begin 加入transactional注解，解决库存接口出现异常，而更新的走货路径数据无法回滚问题
	@Transactional
	public void modifyPath(List<String> waybillList, List<String> areaCodeList, ChangePathEntity changePathEntity, List<ChangePathEntity> changePathList, AdjustEntity adjustEntity, String userCode, String userName) throws TfrBusinessException, Exception {
		Date modifyTime = new Date();
		// 去掉毫秒
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String temp = sdf1.format(modifyTime);
		modifyTime = sdf1.parse(temp);
		// 保存到改变表
		List<ChangePathEntity> newList = new ArrayList<ChangePathEntity>();
		ChangePathEntity change = null;
		// 如果结束时间不为空,设置开始时间为当前时间
		if (null != changePathEntity && changePathEntity.getEffectEndTime() != null) {
			changePathEntity.setEffectStartTime(modifyTime);
			//BUG-53100 mod by songjie begin 
			Date sysTime = new Date();
			//调整的截止时间小于当前时间
			if(sysTime.after(changePathEntity.getEffectEndTime())){
				logger.warn("AdjustTransportationPathService[modifyPath()]:调整的截止时间小于当前时间");
				throw new TfrBusinessException("调整的截止时间不能小于当前时间");
			}
			//BUG-53100 mod by songjie end
		} else {
			// 否则设置开始和结束时间都为当前时间
			changePathEntity = new ChangePathEntity();
			changePathEntity.setEffectStartTime(modifyTime);
			changePathEntity.setEffectEndTime(modifyTime);
		}
		// 根据修改的条目进行拼装
		for (int j = 0; j < changePathList.size(); j++) {
			// clone
			change = (ChangePathEntity) changePathList.get(j).clone();
			// 设置时间
			change.setEffectStartTime(changePathEntity.getEffectStartTime());
			change.setEffectEndTime(changePathEntity.getEffectEndTime());
			// 设置ID
			change.setChangePathId(UUIDUtils.getUUID());
			// 设定调整类型为非合车
			change.setChangeType(TransportPathConstants.CHANGEPATH_STATUS_MODIFY);
			// 设定原库区
			change.setOrigGoodsAreaCode(adjustEntity.getGoodsAreaCode());
			// 设定原线路
			change.setOrigPath(adjustEntity.getAreaLine());
			// 设置route段号
			change.setRouteNo(String.valueOf(j + 1));
			// 根据出发到达部门设置新线路
			change.setChangePath(change.getOrigOrgCode() + "-" + change.getDestOrgCode());
			newList.add(change);
		}
		changePathDao.addListChangePath(newList);
		// 更新走货路径
		for (int i = 0; i < waybillList.size(); i++) {
			String waybillNo = waybillList.get(i);
			// 根据运单号查在库货件号
			AdjustEntity queryGoods = new AdjustEntity();
			queryGoods.setWaybillNo(waybillNo);
			queryGoods.setOrgCode(adjustEntity.getOrgCode());
			List<AdjustEntity> goodsList = adjustTransportationPathDao.queryLevel3(queryGoods);
			for (int j = 0; j < goodsList.size(); j++) {
				// 调用非合车调整走货路径接口
				calculateTransportPathService.notJoinCarModify(waybillNo, goodsList.get(j).getSerialNo(), adjustEntity.getGoodsAreaCode(), modifyTime, adjustEntity.getOrgCode());

				// modify by liangfuxiang 2013-5-23下午6:55:25 begin 去除try..catch..使库存的异常直接抛出!
				// 调库存接口
				// try{
				stockService.adjustGoodsAreaStock(waybillNo, goodsList.get(j).getSerialNo(), adjustEntity.getOrgCode(), userCode, userName);
				// }catch (BusinessException e) {
				// throw new TfrBusinessException(TransportPathConstants.TRANSPORTPATH_ADJUST_STOCKINTERFACEERROR, "");
				// }
				// modify by liangfuxiang 2013-5-23下午6:56:18 end;
			}
		}
	}

	/**
	 * 合车调整走货路径 保存调整至改变表
	 * 
	 * @author huyue
	 * @throws Exception
	 * @throws IllegalAccessException
	 * @throws Exception
	 * @date 2012-10-19 下午1:50:12
	 */
	// modify by liangfuxiang 2013-5-24下午1:57:52 begin 加入transactional注解，解决出现异常数据无法回滚问题
	@Transactional
	public void joinVehicle(List<String> waybillList, List<String> areaCodeList, ChangePathEntity changePathEntity, AdjustEntity adjustEntity, String userCode, String userName) throws TfrBusinessException, Exception {
		List<ChangePathEntity> changePathList = new ArrayList<ChangePathEntity>();
		// 如果结束时间不为空,设置开始时间为当前时间 否则新建changePathEntity
		if (null != changePathEntity && changePathEntity.getEffectEndTime() != null) {
			changePathEntity.setEffectStartTime(new Date());
		} else {
			changePathEntity = new ChangePathEntity();
		}
		
		// modify by liangfuxiang 2013-5-23下午7:00:17 begin 入参有效性判断
		if(null==adjustEntity){
			logger.error("AdjustTransportationPathService[joinVehicle()]:"+TransportPathConstants.ADJUSTENTITY_IS_NULL);
			throw new TfrBusinessException(TransportPathConstants.ADJUSTENTITY_IS_NULL_GLOB);
		}
		
		//部门编码为空
		if(StringUtils.isEmpty(adjustEntity.getOrgCode())){
			logger.error("AdjustTransportationPathService[joinVehicle()]:"+TransportPathConstants.ADJUSTENTITY_ORGCODE_IS_NULL);
			throw new TfrBusinessException(TransportPathConstants.ADJUSTENTITY_ORGCODE_IS_NULL_GLOB);
		}
		//库区编码为空
		if(StringUtils.isEmpty(adjustEntity.getGoodsAreaCode())){
			logger.error("AdjustTransportationPathService[joinVehicle()]:"+TransportPathConstants.ADJUSTENTITY_GETGOODSAREACODE_IS_NULL);
			throw new TfrBusinessException(TransportPathConstants.ADJUSTENTITY_GETGOODSAREACODE_IS_NULL_GLOB);
		}
		// modify by liangfuxiang 2013-5-23下午7:00:31 end;
		
		// 调用基础资料接口获取改变后目的地部门code
		GoodsAreaEntity changedLine = goodsAreaService.queryGoodsAreaByCode(adjustEntity.getOrgCode(), adjustEntity.getGoodsAreaCode());
		
		// modify by liangfuxiang 2013-5-23下午7:08:06 begin 查看了下goodsAreaService.queryGoodsAreaByCode，存在返回为空的情况，故加此异常判断
		if (null == changedLine) {
			logger.error("AdjustTransportationPathService[joinVehicle()]:" + TransportPathConstants.GOODSAREAENTITY_IS_NULL + TransportPathConstants.CURRENT_ORGCODE + adjustEntity.getOrgCode()
					+ TransportPathConstants.BLANK_SPACE_STRING + TransportPathConstants.GOODS_AREA_CODE + adjustEntity.getGoodsAreaCode());
			throw new TfrBusinessException(TransportPathConstants.GOODSAREAENTITY_IS_NULL_GLOB, new Object[] { adjustEntity.getOrgCode(), adjustEntity.getGoodsAreaCode() });
		}
		// modify by liangfuxiang 2013-5-23下午7:08:10 end;
		
		changePathEntity.setDestOrgCode(changedLine.getArriveRegionCode());
		// 获取目标线路
		changePathEntity.setChangePath(adjustEntity.getOrgCode() + "-" + changedLine.getArriveRegionCode());
		// 获取当前组织code
		changePathEntity.setOrigOrgCode(adjustEntity.getOrgCode());

		// 归并不同库区
		Set<String> set = new HashSet<String>();

		// 循环新增变更
		for (int i = 0; i < areaCodeList.size(); i++) {
			set.add(areaCodeList.get(i));
		}
		if (set.size() > 0) {
			Object goodsPath[] = set.toArray();
			for (int i = 0; i < set.size(); i++) {
				// 判断是否新路径和原路径不同,相同不做修改
				if (!StringUtils.equals((String) goodsPath[i], adjustEntity.getGoodsAreaCode())) {
					// clone
					ChangePathEntity change = (ChangePathEntity) changePathEntity.clone();
					// 设定调整类型为合车
					change.setChangeType(TransportPathConstants.CHANGEPATH_STATUS_JOINCAR);
					// 设定原货区
					change.setOrigGoodsAreaCode((String) goodsPath[i]);
					// 调用基础资料查询库区对应的线路
					GoodsAreaEntity origLine = goodsAreaService.queryGoodsAreaByCode(changePathEntity.getOrigOrgCode(), (String) goodsPath[i]);
					
					// modify by liangfuxiang 2013-5-23下午7:08:06 begin 查看了下goodsAreaService.queryGoodsAreaByCode，存在返回为空的情况，故加此异常判断
					if (null == changedLine) {
						logger.error("AdjustTransportationPathService[joinVehicle()]:" + TransportPathConstants.GOODSAREAENTITY_IS_NULL + TransportPathConstants.CURRENT_ORGCODE
								+ changePathEntity.getOrigOrgCode() + TransportPathConstants.BLANK_SPACE_STRING + TransportPathConstants.GOODS_AREA_CODE + (String) goodsPath[i]);
						throw new TfrBusinessException(TransportPathConstants.GOODSAREAENTITY_IS_NULL_GLOB, new Object[] { changePathEntity.getOrigOrgCode(), (String) goodsPath[i] });
					}
					// modify by liangfuxiang 2013-5-23下午7:08:10 end;
					
					// 设置原路线
					change.setOrigPath(changePathEntity.getOrigOrgCode() + "-" + origLine.getArriveRegionCode());
					// 获得UUID
					change.setChangePathId(UUIDUtils.getUUID());
					// 将完成的条目放入list
					changePathList.add(change);

				}
			}
		}
		changePathDao.addListChangePath(changePathList);
		// 获取需要修改的运单号LIST
		List<String> waybill = new ArrayList<String>();
		List<String> areaCode = new ArrayList<String>();
		for (int l = 0; l < areaCodeList.size(); l++) {
			if (!StringUtils.equals(areaCodeList.get(l), adjustEntity.getGoodsAreaCode())) {
				waybill.add(waybillList.get(l));
				areaCode.add(areaCodeList.get(l));
			}
		}
		
		// modify by liangfuxiang 2013-5-23下午7:17:31 begin 去掉try...catch..将最底层的异常抛出...方便排查异常
		// 调库存接口
		// try{
		stockService.adjustTogetherTruckStock(waybill, areaCode, adjustEntity.getGoodsAreaCode(), adjustEntity.getOrgCode());
		// }catch (BusinessException e){
		// throw new TfrBusinessException(TransportPathConstants.TRANSPORTPATH_ADJUST_STOCKINTERFACEERROR, "");
		// }
		// modify by liangfuxiang 2013-5-23下午7:18:09 end;
	}

	/**
	 * 根据组织code查询缓存获取name
	 * 
	 * @author huyue
	 * @date 2013-1-22 下午3:42:47
	 */
	public String getNameByCode(String orgCode) {
		//通过编码在缓存中查名称(包括德邦自有部门和代理网点)
		String orgName = orgAdministrativeInfoService.queryCommonNameByCommonCodeFromCache(orgCode);
		if (StringUtils.isEmpty(orgName)) {
			//如果为空,则返回code
			return orgCode;
		} else {
			//不为空则返回名字
			return orgName;
		}
	}

	/**
	 * 
	* @Title: findOutOrgCode 
	* @Description: T 获取当前登录部门对应的外场编码
	* @return
	* @throws TfrBusinessException  设定文件 
	* @return String    返回类型 
	* @see findOutOrgCode
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-1 下午3:12:03   
	* @throws
	 */
	@Override
	public String findOutOrgCode() throws TfrBusinessException {
		// 获取当前部门编码
		String currentOrgCode = FossUserContext.getCurrentDeptCode();
		// 获取当前登录部门的外场对象
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = querySuperiorOrgByOrgCode(currentOrgCode);
		// 获取外场编码
		String outOrgCode = orgAdministrativeInfoEntity.getCode();
		// 若外场编码为空
		if (StringUtils.isEmpty(outOrgCode)) {
			logger.error("AdjustTransportationPathService[findOutOrgCode()]:" + TransportPathConstants.OrgAdministrativeInfoEntity_OUT_CODE_NULL + currentOrgCode);
			throw new TfrBusinessException(TransportPathConstants.OrgAdministrativeInfoEntity_OUT_CODE_NULL_GLOB, new Object[] { currentOrgCode });
		}
		// 记录外场编码
		logger.info("AdjustTransportationPathService[findOutOrgCode()]:outOrgCode:" + outOrgCode);
		return outOrgCode;
	}

	/**
	* @description 根据当前部门code 以及库区编码 查询库存里下一部门的code以及下一部门的Name(去除重复部门) 用于下拉菜单使用
	* @param orgCode
	* @param goodArea
	* @param orgName 部门名称 用于模糊查询 非必填
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月18日 上午10:10:57
	*/
	@Override
	public List<BaseDataDictDto> queryNextOrgByStock(AdjustEntity adjustEntity,int start,int limit) {
		List<BaseDataDictDto> backList = new ArrayList<BaseDataDictDto>();
		try{
			backList = stockService.queryNextOrgByStock(adjustEntity.getOrgCode(), adjustEntity.getGoodsAreaCode(), adjustEntity.getOrgName(),start,limit);
		}catch(Exception e){
			//sonar-352203
			logger.info("AdjustTransportationPathService.queryNextOrgByStock 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
		}
		return backList;
	}

	/**
	* @description 根据当前部门code 以及库区编码 查询库存里下一部门的code以及下一部门的Name(去除重复部门) 用于下拉菜单使用 总记录数
	* @param orgCode
	* @param goodArea
	* @param orgName 部门名称 用于模糊查询 非必填
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月18日 上午10:10:57
	*/
	@Override
	public long queryNextOrgByStockCount(AdjustEntity adjustEntity) {
		Integer backCount = 0;
		try{
			backCount =	stockService.queryNextOrgByStockCount(adjustEntity.getOrgCode(), adjustEntity.getGoodsAreaCode(), adjustEntity.getOrgName());
		}catch(Exception e){
			//sonar-352203
			logger.info("AdjustTransportationPathService.queryNextOrgByStockCount 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
		}
		if(backCount==null){
			return 0L;
		}else{
			return Long.parseLong(backCount+"");
		}
		
	}
}