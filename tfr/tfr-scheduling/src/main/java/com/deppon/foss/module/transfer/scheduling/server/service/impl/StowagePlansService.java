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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IStowagePlansDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IStowagePlansService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.StowagePlansDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;

/**
 * 调整走货路径service实现
 * 
 * @author huyue
 * @date 2012-10-12 下午6:44:01
 */
public class StowagePlansService implements IStowagePlansService {

	// 日志
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager
			.getLogger(StowagePlansService.class);

	/**
	 * 配载方案配置 DAO
	 */
	private IStowagePlansDao stowagePlansDao;

	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-7-30 下午3:37:43 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IStowagePlansService#selectStowagePlansList(com.deppon.foss.module.transfer.scheduling.api.shared.dto.StowagePlansDto)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<StowagePlansEntity> selectStowagePlansList(
			StowagePlansDto stowagePlansDto, int start, int limit) {
		
		return stowagePlansDao.selectStowagePlansList(stowagePlansDto, start,
				limit);
	}
	/**
	 * @author 134019-foss-yuyongxiang
	 */
	@Override
	@Transactional(readOnly = true)
	public Long selectStowagePlansListCount(
			StowagePlansDto stowagePlansDto) {
		return stowagePlansDao.selectStowagePlansListCount(stowagePlansDto);
	}

	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-7-30 下午3:37:43 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IStowagePlansService#selectStowagePlansDetailList(com.deppon.foss.module.transfer.scheduling.api.shared.dto.StowagePlansDto)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<StowagePlansDetailEntity> selectStowagePlansDetailList(
			StowagePlansDto stowagePlansDto) {
		return stowagePlansDao.selectStowagePlansDetailList(stowagePlansDto);
	}

	/**
	 * 
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-8-2 上午11:17:16 
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IStowagePlansService#queryStowageWithid(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> queryStowageWithid(
			String origOrgCode, String destOrgCode) {
		
		
		//new
		StowagePlansDto stowagePlansDto =new StowagePlansDto();
		// 设置但前外场
		stowagePlansDto.setOrigOrgCode(origOrgCode);
		// 设置到达外场
		stowagePlansDto.setDestOrgCode(destOrgCode);
		//查出来的数据应该是唯一的一条数据
		List<StowagePlansEntity> list = this.selectStowagePlansList(stowagePlansDto, 0,ConstantsNumberSonar.SONAR_NUMBER_10);
		//获取第一条数据
		if(CollectionUtils.isNotEmpty(list)){
			StowagePlansEntity stowagePlans = list.get(0);
			stowagePlansDto.setStowagePlansId(stowagePlans.getId());
			List<StowagePlansDetailEntity> stowagePlansDetailList= this.selectStowagePlansDetailList(stowagePlansDto);
			if(CollectionUtils.isNotEmpty(stowagePlansDetailList)){
				List<String>  stowagePlansList=new ArrayList<String>();
				for(StowagePlansDetailEntity s :stowagePlansDetailList){
					stowagePlansList.add(s.getOrgCode());
				}
				return stowagePlansList;
			}
			
		}
		return null;
	}

	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-7-30 下午3:37:43 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IStowagePlansService#insertStowagePlans(com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansEntity)
	 */
	@Override
	@Transactional
	public void insertStowagePlans(StowagePlansDto stowagePlansDto,List<StowagePlansDetailEntity> stowagePlansDetailList) {
		/****************************check start***********************************/
		if(null == stowagePlansDto || null == stowagePlansDetailList){
			throw new TfrBusinessException("到达部门或配载部门不能为空！");
		}
		//到达部门不能为空
		if(StringUtils.isBlank(stowagePlansDto.getDestOrgCode())){
			throw new TfrBusinessException("到达部门不能为空！");
		}
		if(CollectionUtils.isEmpty(stowagePlansDetailList)){
			throw new TfrBusinessException("配载部门不能为空！");
		}
		for(StowagePlansDetailEntity  stowagePlansDetail: stowagePlansDetailList){
			if(StringUtils.isNotBlank(stowagePlansDetail.getOrgCode()) && stowagePlansDetail.getOrgCode().equals(stowagePlansDto.getOrigOrgCode())){
				throw new TfrBusinessException("配载部门不能配置为当前外场！");
			}
		}
		
		/****************************check end***********************************/
		
		StowagePlansEntity stowagePlansEntity=new StowagePlansEntity();
		
		/**
		 * 设置ID
		 */
		stowagePlansEntity.setId(UUIDUtils.getUUID());
		//创建时间这个是为了消除时间误差所以单独NEW一次
		Date date=new Date();
		/**
		 * 设置创建时间
		 */
		stowagePlansEntity.setCreateTime(date);
		/**
		 * 设置更新时间
		 */
		stowagePlansEntity.setModifyTime(date);
		/**
		 * 当前外场名称
		 */
		stowagePlansEntity.setOrigOrgName(stowagePlansDto.getOrigOrgName());
		/**
		 * 当前外场编码
		 */
		stowagePlansEntity.setOrigOrgCode(stowagePlansDto.getOrigOrgCode());
		/**
		 * 到达外场名称
		 */
		stowagePlansEntity.setDestOrgName(stowagePlansDto.getDestOrgName());
		/**
		 * 到达外场编码
		 */
		stowagePlansEntity.setDestOrgCode(stowagePlansDto.getDestOrgCode());
		/**
		 * 创建人名称
		 */
		stowagePlansEntity.setCreateUserName(FossUserContext.getCurrentUser().getEmpName());
		/**
		 * 创建人编码
		 */
		stowagePlansEntity.setCreateUserCode(FossUserContext.getCurrentUser().getEmpCode());
		/**
		 * 修改人名称
		 */
		stowagePlansEntity.setModifyUserName(FossUserContext.getCurrentUser().getEmpName());
		/**
		 * 修改人编码
		 */
		stowagePlansEntity.setModifyUserCode(FossUserContext.getCurrentUser().getEmpCode());
		
		//插入之前做重复性check
		//这个在Entity 赋值之后.插入之前是为了劲量减少误差,但是这个还是不能应对高并发
		//PS : 这个地方用的比较少基本不会出现并发情况所以这边没有添加业务锁,如果需求改变导致会出现高并发请修改逻辑使用业务锁
		Long count = this.selectStowagePlansListCount(stowagePlansDto);
		if(count>0){
			throw new TfrBusinessException("该配载方案已存在！");
		}

		stowagePlansDao.insertStowagePlans(stowagePlansEntity);
		
		//插入明细表
		this.insertStowagePlansDetail(stowagePlansEntity,stowagePlansDetailList);
	}


	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-7-30 下午3:37:43 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IStowagePlansService#updateStowagePlans(com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansEntity)
	 */
	@Override
	@Transactional
	public void updateStowagePlans(StowagePlansDto stowagePlansDto,List<StowagePlansDetailEntity> stowagePlansDetailList) {
		/****************************check start***********************************/
		if(null == stowagePlansDto || null == stowagePlansDetailList){
			throw new TfrBusinessException("到达部门或配载部门不能为空！");
		}
		//到达部门不能为空
		if(StringUtils.isBlank(stowagePlansDto.getDestOrgCode())){
			throw new TfrBusinessException("到达部门不能为空！");
		}
		if(CollectionUtils.isEmpty(stowagePlansDetailList)){
			throw new TfrBusinessException("配载部门不能为空！");
		}
		if(StringUtils.isBlank(stowagePlansDto.getId())){
			throw new TfrBusinessException("参数错误!");
		}

		Long count = this.selectStowagePlansListCount(stowagePlansDto);
		if(count == 0){
			throw new TfrBusinessException("该配载方案不存在！");
		}
		/****************************check end***********************************/
		StowagePlansEntity stowagePlansEntity=new StowagePlansEntity();

		/**
		 * 设置ID
		 */
		stowagePlansEntity.setId(stowagePlansDto.getId());
		/**
		 * 设置更新时间
		 */
		stowagePlansEntity.setModifyTime(new Date());
		/**
		 * 修改人名称
		 */
		stowagePlansEntity.setModifyUserName(FossUserContext.getCurrentUser().getEmpName());
		/**
		 * 修改人编码
		 */
		stowagePlansEntity.setModifyUserCode(FossUserContext.getCurrentUser().getEmpCode());
		//查询被修改的数据
		List<StowagePlansEntity> stowagePlansList = this.selectStowagePlansList(stowagePlansDto,0,ConstantsNumberSonar.SONAR_NUMBER_100);
		StowagePlansEntity stowagePlansTemp=stowagePlansList.get(0);
		//判定在修改的时候是否已经被别人修改了
		if(stowagePlansTemp.getModifyTime().before(stowagePlansDto.getModifyTime())){
			throw new TfrBusinessException("该配载方案已被修改！");
		}
		stowagePlansDao.updateStowagePlans(stowagePlansEntity);
		//修改明细
		this.updateStowagePlansDetail(stowagePlansEntity,stowagePlansDetailList,stowagePlansDto);
	}
	
	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-8-1 下午7:04:32 
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IStowagePlansService#deleteStowagePlansWithId(com.deppon.foss.module.transfer.scheduling.api.shared.dto.StowagePlansDto)
	 */
	@Override
	@Transactional
	public void deleteStowagePlansWithId(StowagePlansDto stowagePlansDto) {
		//删除主表
		stowagePlansDao.deleteStowagePlansWithId(stowagePlansDto);
		//设置明细表主键
		stowagePlansDto.setStowagePlansId(stowagePlansDto.getId());
		//删除明细表
		stowagePlansDao.deleteStowagePlansDetailWithStowagePlansId(stowagePlansDto);
	}
	
	

	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-7-30 下午3:37:43 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IStowagePlansService#insertStowagePlansDetail(com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansDetailEntity)
	 */
	private void insertStowagePlansDetail(StowagePlansEntity stowagePlans,
			List<StowagePlansDetailEntity> stowagePlansDetailList) {
		//这个地方前台限制最多三条数据
		for(StowagePlansDetailEntity stowagePlansDetail : stowagePlansDetailList){
			//设置ID
			stowagePlansDetail.setId(UUIDUtils.getUUID());
			//设置主表ID
			stowagePlansDetail.setStowagePlansId(stowagePlans.getId());
			//插入动作
			stowagePlansDao.insertStowagePlansDetail(stowagePlansDetail);
		}
	}
	
	/**
	 * 伪更新
	 * 实际业务逻辑是 :
	 * 现根据抓表ID删除明细表里面的数据
	 * 再根据前台传过来的数据添加到表里面
	 * 
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-8-1 下午6:57:04
	 * @param stowagePlans
	 * @param stowagePlansDetailList
	 * @param stowagePlansDto
	 */
	private void updateStowagePlansDetail(
			StowagePlansEntity stowagePlans,List<StowagePlansDetailEntity> stowagePlansDetailList,StowagePlansDto stowagePlansDto) {
		
		//设置明细表外键
		stowagePlansDto.setStowagePlansId(stowagePlansDto.getId());
		//先根据主表删除之前的数据
		stowagePlansDao
		.deleteStowagePlansDetailWithStowagePlansId(stowagePlansDto);
		
		//再插入新的数据
		this.insertStowagePlansDetail(stowagePlans, stowagePlansDetailList);
		
		
	}

	/**
	 * @return the stowagePlansDao
	 */
	public IStowagePlansDao getStowagePlansDao() {
		return stowagePlansDao;
	}

	/**
	 * @param stowagePlansDao
	 *            the stowagePlansDao to set
	 */
	public void setStowagePlansDao(IStowagePlansDao stowagePlansDao) {
		this.stowagePlansDao = stowagePlansDao;
	}

}