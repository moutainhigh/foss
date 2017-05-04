/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/MotorcadeDao.java
 * 
 * FILE NAME        	: MotorcadeDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
/**
DP-FOSS-综合管理系统用例-修改_查询行政组织业务属性-V1.2

修订记录
日期	修订内容	修订人员	版本号
2012-04-20	新增	李俊	V0.1
2012-06-28	去掉“可代收货款，可货到付款，可返回签单”属性（原因：所有的派送部都开展这三个业务，不必分开记录）。	李俊  	V0.2
2012-06-28	“到达营业部”的属性增加“单件重量上限(KG)”，“单件体积上限(方)”，“单票重量上限(KG)”，“单票体积上限(方)”，“自提区域”和“派送区域”。	李俊	V0.3
2012-07-02	修改了“接送货车队”的添加方式。	李俊	V0.4
2012-07-03	修改了“调度组信息”的“所属车队”的交互方式，供评审会议评审。	李俊	V0.5
2012-07-05	按照评审会议的评审进行修改：
部门面积 分为两个字段：总面积，外场面积
修改字段“接送货车队”为“车队”
添加标识：“集中开单组”（是否集中开单部门）
调度组所属车队由“公共选择框”修改为“列表框”	李俊	V0.6
2012-07-10	添加“适用产品”，“所属集中开单组”属性。	李俊	V0.61
2012-07-12	添加“是否收空运货”，“是否收汽运货”属性。	李俊	V0.62
2012-07-20	添加“车队信息”，“车队组信息”，“事业部信息”。	李俊	V0.63
2012-09-18	修改了行政组织业务属性的公共业务信息的显示方式，增加了“取消到达日期”，“转货部门”，去掉了“自提范围”的查看电子地图的功能。
增加了车队对应的营业区，行政区，车队类型。
增加了车队所属外场。	李俊	V1.1
2012-12-05	增加了 :

国家地区 部门英文简称,部门服务区坐标编号,部门电话,部门传真,部门简称,所属实体财务部, 部门备注信息,是否营业大区,是否实体财务部,是否派送排单,是否理货,派送排单服务外场,理货部门服务外场,理货业务类型

是否可开装卸费,是否可返回签单,是否可货到付款,是否可代收货款,

对公银行信息:
银行账号,银行开户名,部门标杆编码,银行编码,银行名称,支行编码,支行名称,省份编码,省份名称,城市编码,城市名称

修改了：是否营业区 为“是否营业小区”。	李俊	V1.2
2013-01-15	添加了组织的“组织级别”，车队中的“是否顶级车队”，“是否直接管车”，“所服务集中开单组”，车队组：“所服务集中开单组”。	李俊	V1.21

1.	SUC-85-修改_查询行政组织业务属性
1.1	相关业务用例
BUC_FOSS_5.20.30_510确认承运信息。
BUC_FOSS_5.20.20_520接货运单开单。
BUC_FOSS_5.20.10_560交接接货。
BUC_FOSS_5.20.30_530开单收货。
BUC_FOSS_5.60.05_540整车开单收货。
BUC_FOSS_5.60.10_730变更运单。

1.2	用例描述
该用例可对行政组织业务属性进行修改、查询。

1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1.	行政组织信息完备。	SUC-647同步行政组织接口

后置条件	1．为系统用例“SUC-496-录入运输信息”提供查询。	SUC-496录入运输信息

1.4	操作用户角色
操作用户	描述
系统维护人员	系统维护人员对行政组织业务属性进行修改、查询操作。
1.5	界面要求
无

1.5.1	表现方式
Web页面

1.5.2	界面原型-主界面
 
图一：行政组织业务属性主界面
1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。
1.5.4	界面原型-行政组织业务属性修改界面
   
 
图二：行政组织业务属性修改界面
1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。
1.5.6	界面原型-行政组织业务属性详情界面
   
图三：行政组织业务属性详情界面
1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。
1.5.8	界面原型-编辑或查看电子地图界面
 
图四：编辑或查看电子地图界面
1.6	操作步骤
1.6.1	行政组织业务属性查询操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入行政组织业务属性主界面。		
2	输入查询条件，点击查询按钮。	【行政组织业务属性查询条件】	系统返回查询结果，在组织树中突出显示查询结果。
1.6.2	行政组织业务属性修改操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入行政组织业务属性主界面。		
2	输入查询条件，点击查询按钮。	【行政组织业务属性查询条件】	系统返回查询结果，在组织树中突出显示查询结果。
3	点击树型结构中的名称。	【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【部门基本信息】，【车队信息】，【车队组信息】，【事业部信息】，【排单部门信息】，【理货部门信息】，【对公银行账号信息】	在组织树的右边展示行政组织业务属性详情界面。
4	点击修改按钮。		进入行政组织业务属性修改界面。
5	修改行政组织业务属性，然后点击保存按钮。		成功保存界面信息至数据库，返回主界面。
行政组织业务属性修改操作步骤-异常操作
序号	扩展事件	相关数据	备注
5a	点击取消按钮，退出当前界面，返回主界面。		
5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		

1.7	业务规则
序号	描述
SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。
SR-19	如果当前部门是外场，“部门英文简称”必填。
SR-20	是否可返回签单, 是否可货到付款, 是否可代收货款 营业部默认都有这些业务。
SR-21	只有派送排单才显示或者编辑“排单部门信息”。
SR-22	只有理货才显示或者编辑“理货部门信息”。
SR-23	“是否可开装卸费”默认不选 。
SR-24	部门基本信息 均从UUMS同步。
SR-25	是否事业部, 是否营业小区, 是否营业大区从UUMS同步，不能修改。
SR-26	只有选择了“可空运配载”才能修改或者显示“是否空运总调”
SR-27	关于最大临欠额度：
1. 结算每月初统计客户上月收入，存入"客户月收入表" 
2. 综合取出客户月收入表中最近3个月收入 
3. 综合根据最大月收入查找收入区间表，获得最新额度后，更新到部门最大临欠额度中 
4. 综合提供接口，用于查询部门最大临欠额度 
5. 综合现有"已用额度"取消 
6. 综合与结算实现方式保持一致，都用Java定时任务，或者都用Stored Procedure

1.8	数据元素
1.8.1	行政组织业务属性部门公共业务信息
字段名称 	说明 	输入限制	长度	是否必填	备注
部门编号	部门的编号	N/A	N/A	N/A	
标杆编码	同基准编码，OA，CRM这些系统对同一部门的唯一标识	N/A	N/A	N/A	
部门名称	部门的名称	N/A	N/A	N/A	
部门面积	部门面积（单位：平方米）	文本	20	否	
国家地区	部门所有的国家地区	下拉框	50	是	
省份	部门所在的省份	下拉框	50	是	
城市	部门所在的城市	下拉框	50	是	
区县	部门所在的区县	下拉框	50	是	
部门英文简称	部门英文简称	文本	200	否	
部门备注信息	部门备注信息	文本	1000	否	
部门服务区坐标编号	部门服务区坐标编号	文本	50	否	
部门电话	部门电话	文本	50	否	
部门传真	部门传真	文本	50	否	
部门简称	部门简称	文本	50	否	
所属实体财务部	所属实体财务部	文本	50	否	
是否营业部	是否是营业部	数字	10	是	
是否外场	是否是外场	数字	10	是	
可空运配载	是否可空运配载	数字	10	是	
是否车队	是否是车队	数字	10	是	
是否车队调度组	是否是车队调度组	数字	10	是	
是否集中开单组	这个部门是否是集中开单组	数字	10	是	
是否事业部	是否事业部	数字	10	是	
是否车队组	是否车队组	数字	10	是	
是否营业小区	是否营业区	数字	10	是	
是否营业大区	是否营业大区	数字	10	是	
是否实体财务部	是否实体财务部	数字	10	是	
是否派送排单	是否派送排单	数字	10	是	
是否理货	是否理货	数字	10	是	
派送排单服务外场	派送排单服务外场	文本	50	否	
理货部门服务外场	理货部门服务外场	文本	50	否	
理货业务类型	理货业务类型	文本	50	否	
是否空运总调	是否空运总调	数字	10	是	
					
					
可出发	是否可做出发业务	数字	10	是	
可到达	是否可做到达业务	数字	10	是	
是否驻地部门	是否是驻地部门	数字	10	是	
广告语	广告语	文本	200	否	
开业日期	部门的开业日期	日期	20	是	
最大临欠额度	最大临欠额度	文本	200	否	
已用临欠额度	已用临欠额度,由结算来维护。	N/A	N/A	N/A	
出发适用产品	出发部门能提供的产品	列表框	N/A	否	数据来源于产品价格模型
到达适用产品	到达部门能提供的产品	列表框	N/A	否	数据来源于产品价格模型
所属集中开单组	所属集中开单组	选择框	N/A	否	
驻地营业部所属外场					
车队	接送货车队	列表框	N/A	否	
1.8.2	营业部信息
字段名称 	说明 	输入限制	长度	是否必填	备注
可出发	是否可做出发业务	数字	10	是	
可到达	是否可做到达业务	数字	10	是	
是否驻地部门	是否是驻地部门	数字	10	是	
是否集中接送货					
是否可开装卸费	是否可开装卸费	文本	1	是	默认为是（Y）
是否可返回签单	是否可返回签单	文本	1	是	默认为是（Y）
是否可货到付款	是否可货到付款	文本	1	是	默认为是（Y）
是否可代收货款	是否可代收货款	文本	1	是	默认为是（Y）
广告语	广告语	文本	200	否	
开业日期	部门的开业日期	日期	20	是	
最大临欠额度	最大临欠额度	文本	200	N/A	
已用临欠额度	已用临欠额度,由结算来维护。	N/A	N/A	N/A	
出发适用产品	出发部门能提供的产品	列表框	N/A	否	数据来源于产品价格模型
到达适用产品	到达部门能提供的产品	列表框	N/A	否	数据来源于产品价格模型
所属集中开单组	所属集中开单组	选择框	N/A	否	
驻地营业部所属外场	驻地营业部所属外场	选择框	N/A	否	
车队	接送货车队	列表框	N/A	否	
取消到达日期	取消到达日期	日期	20	否	
转货部门	转货部门	选择框	N/A	否	
1.8.21.8.3	提货信息
字段名称 	说明 	输入限制	长度	是否必填	备注
可自提	是否可以让客户自提	数字	10	是	
可派送	是否能送货上门	数字	10	是	
可空运到达	可空运到达	数字	10	是	
可汽运到达	可汽运到达	数字	10	是	
单件重量上限（KG）	单件重量上限是多少（KG）	数字	10	否	
单件体积上限(方)	单件体积上限是多少(方)	数字	10	否	
单票重量上限（KG）	单票重量上限是多少（KG）	数字	10	否	
单票体积上限(方)	单票体积上限是多少(方)	数字	10	否	
自提区域描述	自提区域描述	文本	255	否	
派送区域描述	派送区域描述	文本	255	否	
1.8.31.8.4	外场信息
字段名称 	说明 	输入限制	长度	是否必填	备注
可汽运配载	是否汽运配载	数字	10	是	
可外发配载	是否外发配载	数字	10	是	
可打木架	是否可打木架	数字	10	是	
可中转	是否中转	数字	10	是	
是否有自动分拣机	是否有自动分拣机	数字	10	是	
外场编码	配载部门的外场编码	文本	88	是	
外场简码	外场的简码，不能重复。	文本	88	是	
货区面积(平方米)	货区的面积	文本	20	否	
货台面积(平方米)	货台的面积	文本	20	否	
库型	外场有几个入口（单边/双边/三边/四边），默认为“请选择”	下拉框	N/A	否	
1.8.41.8.5	调度组信息
字段名称 	说明 	输入限制	长度	是否必填	备注
所属车队	调度组所属的车队	列表框	N/A	是	
1.8.51.8.6	车队信息
字段名称 	说明 	输入限制	长度	是否必填	备注
是否集中接送货	是否集中接送货	数字	10	是	
是否顶级车队	是否顶级车队	数字	10	是	
是否直接管车	是否直接管车	数字	10	是	
所服务集中开单组	所服务集中开单组	文本	200	是	
集中接送货车队编码	集中接送货车队编码	文本	255	是	
车队所属外场	车队所属外场	选择框	N/A	否	
车队类型	车队类型（包含长途车队，物流班车车队，接送货车队）	下拉框	N/A	否	
车队负责行政区	车队负责行政区	选择框	N/A	否	
车队负责营业区	车队负责营业区	选择框	N/A	否	
1.8.61.8.7	车队组信息
字段名称 	说明 	输入限制	长度	是否必填	备注
是否集中接送货	是否集中接送货	数字	10	是	
所属车队	车队组所属的车队	文本	255	是	
车队所属外场	车队所属外场	选择框	N/A	否	
车队组类型	车队组类型（包含长途车队，物流班车车队，接送货车队）	下拉框	N/A	否	
车队组负责行政区	车队组负责行政区	选择框	N/A	否	
车队组负责营业区	车队组负责营业区	选择框	N/A	否	
所服务集中开单组	所服务集中开单组	文本	200	是	
1.8.71.8.8	事业部信息
字段名称 	说明 	输入限制	长度	是否必填	备注
事业部编码(用于集中接送货区编码)	事业部编码(用于集中接送货区编码)	文本	50	是	
1.8.81.8.9	部门基本信息
字段名称 	说明 	输入限制	长度	是否必填	备注
组织负责人	组织负责人工号	N/A	N/A	N/A	
组织负责人姓名	组织负责人姓名	N/A	N/A	N/A	
负责人	组织负责人工号	N/A	N/A	N/A	
联系电话	联系电话	N/A	N/A	N/A	
部门传真	部门传真	N/A	N/A	N/A	
上级组织名称	上级行政组织名称	N/A	N/A	N/A	
上级组织标杆编码	上级行政组织的标杆编码，DP+6位数字编码	N/A	N/A	N/A	从OA同步
上级组织编码	上级行政组织的标杆编码，DP+6位数字编码	N/A	N/A	N/A	
上级组织名称	上级行政组织名称	N/A	N/A	N/A	
邮编号码	邮编号码				
所属子公司	组织所属子公司的名称	N/A	N/A	N/A	
财务成本中心	部门对应的财务成本中心	N/A	N/A	N/A	
部门地址	部门的详细地址	N/A	N/A	N/A	
部门备注信息	部门备注信息	N/A	N/A	N/A	
UUMS主键ID	UUMS主键ID	N/A	N/A	N/A	
UUMS上级主键ID	UUMS上级主键ID	N/A	N/A	N/A	
UUMS主键ID序列	UUMS主键ID序列	N/A	N/A	N/A	
组织状态	显示顺序启用、作废、异动	N/AN/A	N/AN/A	N/AN/A	
显示顺序	部门层级	N/A	N/A	N/A	
部门层级	部门备注信息	N/A	N/A	N/A	
启用日期	组织启用日期	N/A	N/A	N/A	
作废日期	组织作废日期	N/A	N/A	N/A	
组织状态	启用、作废、异动	N/A	N/A	N/A	
是否为叶子节点	是否为叶子节点	N/A	N/A	N/A	
部门简称	部门简称	N/A	N/A	N/A	
组织性质	组织性质	N/A	N/A	N/A	
地区编码默认拼音	地区编码默认拼音	N/A	N/A	N/A	
组织邮箱	组织邮箱	N/A	N/A	N/A	
已封存系统	已封存系统	N/A	N/A	N/A	
EHR部门编码	EHR部门编码	N/A	N/A	N/A	
创建人编码	创建人编码	N/A	N/A	N/A	初始数据从UUMS同步，后期由系统维护
创建时间	创建时间	N/A	N/A	N/A	初始数据从UUMS同步，后期由系统维护
修改人编码	修改人编码	N/A	N/A	N/A	初始数据从UUMS同步，后期由系统维护
修改时间	修改时间	N/A	N/A	N/A	初始数据从UUMS同步，后期由系统维护
1.8.10	对公银行账号信息
字段名称 	说明 	输入限制	长度	是否必填	备注
银行账号	对公银行账号	N/A	N/A	N/A	
银行开户名	银行开户名	N/A	N/A	N/A	
部门标杆编码	对公银行省市支行	N/A	N/A	N/A	
银行编码	银行编码	N/A	N/A	N/A	
银行名称	银行名称	N/A	N/A	N/A	
支行编码	支行编码	N/A	N/A	N/A	
支行名称	支行名称	N/A	N/A	N/A	
省份编码	省份编码	N/A	N/A	N/A	
省份名称	省份名称	N/A	N/A	N/A	
城市编码	城市编码	N/A	N/A	N/A	
城市名称	城市名称	N/A	N/A	N/A	
	
	
	
	
1.8.91.8.11	行政组织业务属性查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
部门名称	部门名称	文本	80	否	

1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
组织架构信息接口	FOSS系统	由FOSS系统提供部门信息接口，UUMS调用传递部门的更新信息到FOSS系统。
部门地理位置信息接口	GIS系统	通过集成GIS电子地图，确定部门的地理位置（坐标）以及营业部的自提和派送区域。




dp-foss-综合管理系统用例-查询财务组织-v1.2

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-04-26 	新增 	李俊	V0.1
2012-06-12 	1. 按照UED要求，单个查询输入框无需“重置”按钮； 
2. 在跟UUMS沟通的新方案中，FOSS不提供“新增”财务组织的功能；    	李俊	V0.2
2012-07-13 	1.	添加了相关业务用例。
2.	修改了后置条件。	李俊	V0.3
2012-08-02	1. 通过业务部门审核签字版本升级到V0.9	李俊	V0.9
2013-02-19	与UUMS同步财务组织时需要在同步的时候添加以下属性：1、财务组织全路径 fullPath，2、 是否是叶子结点 isLeaf。	李俊	V1.2

1.	SUC-84-查询财务组织
1.1	相关业务用例
BUC_FOSS_4.7.30.30_010现金收银
BUC_FOSS_4.7.10.30_010现金小票

1.2	用例描述
该用例可对财务组织进行查询。

1.3	用例条件
条件类型	描述	引用系统用例
前置条件	UUMS已将财务组织信息同步至FOSS。	
后置条件		

1.4	操作用户角色
操作用户	描述
系统管理员	系统管理员对财务组织进行查询操作。

1.5	界面要求
1.5.1	表现方式
Web页面
1.5.2	界面原型-主界面
 
图一：财务组织主界面
1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
2.	树形结构区域
1)	点击树形结构中的一个财务组织，在树型结构的右边显示这个财务组织的详情。参见【图二：财务组织查看界面】。
2)	点击树形结构的“+”，在组织树中展开该财务组织下一级的所有财务组织。点击树形结构的“-”，则收起该财务组织下一级的所有财务组织。
3.	字段输入区域
1)	参见数据元素【财务组织查询条件】。

1.5.4	界面原型-查看界面
 
图二：财务组织查看界面
1.5.5	界面描述-查看界面
1.	字段显示区域
1)	参见数据元素【财务组织信息】。

1.6	操作步骤
1.6.1	财务组织查询操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入财务组织主界面		
2	输入查询条件，点击查询按钮。	【财务组织查询条件】	系统返回查询结果，在财务组织树型结构中被查询的财务组织突出显示。
3	点击树型结构中的目标财务组织。		在财务组织树的右边展示财务组织详情。

1.7	业务规则
序号	描述

1.8	数据元素
1.8.1	财务组织信息
字段名称 	说明 	输入限制	长度	是否必填	备注
名称	财务组织的名称	N/A	N/A	N/A	
组织编码	财务组织的编码	N/A	N/A	N/A	
上级组织名称	上级组织的名称	N/A	N/A	N/A	
是否是成本中心	是否是成本中心	N/A	N/A	N/A	
是否是子公司	是否是子公司	N/A	N/A	N/A	
全称	财务组织的全称	N/A	N/A	N/A	
描述	描述信息	N/A	N/A	N/A	
财务组织全路径	财务组织全路径	N/A	N/A	N/A	
是否是叶子结点	是否是叶子结点	N/A	N/A	N/A	
1.8.2	财务组织查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
名称	财务组织的名称	文本	50	否	

1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
同步财务组织信息接口	UUMS系统	由FOSS系统提供财务组织信息接口，由UUMS调用，通过此接口传递财务组织更新信息到FOSS系统



dp-foss-综合管理系统用例-撤销组织提醒-v1.1

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-5-31 	新增 	朱俊勇	V0.1
2012-08-02	1. 通过业务部门审核签字版本升级到V0.9	李俊	V0.9
2012-12-6	1．更新主界面，更新业务规则	赵鹏	V1.01

1.	SUC-195-撤销组织提醒
1.1	相关业务用例
无
1.2	用例描述
该系统用例主要为预撤销组织相关人员提供消息提醒功能。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1、	从UUMS同步到预撤销状态的组织信息
2、	FOSS中该组织存在相关用户（所属该组织的用户）	
后置条件	1、 待撤销组织的所有人员可以查看到消息提醒	
1.4	操作用户角色
操作用户	描述
无	被动提醒页面,无主动操作.
1.5	界面要求
1.5.1	表现方式
Web页面
1.5.2	界面原型-主界面
 
图一：撤销组织提醒界面
1.5.3	界面描述-主界面
参考FOSS系统消息提示界面。
 
1.6	操作步骤

序号	基本步骤	相关数据	补充步骤
1	显示撤销组织提醒界面		
2	用户浏览提醒信息后点击关闭按钮，返回首页		

1.7	业务规则
序号	描述
SR-1	如果系统中存在预撤销组织，则所有归属该组织的用户（包括非默认组织）在登录时需要得到提醒信息
SR-2	页面展示时调用各子系统提供的接口，取得未完成事宜列表，如果返回的事宜已经处理完成则不需要显示在提醒信息列表中。如果所有事宜都已完成 ，则不显示提醒信息列表（组织将于某时间撤销的提醒信息仍然需要显示）。
SR-3	如果相关用户所属的多个组织有预撤销状态时，按组织编码顺序显示提醒信息。

1.8	数据元素
1.8.1	组织架构数据（显示信息）
字段名称 	说明 	输入限制	长度	是否必填	备注
组织名称	行政组织名称	文本	50		
撤销日期	XXXX年XX月XX日	文本	11		
1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
*/		

package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 车队部门 DAO
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午10:53:19
 */
public class MotorcadeDao extends SqlSessionDaoSupport implements IMotorcadeDao {

    /**
     * 日志 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleDepartmentDao.class);
    /**
     * 
     * mybatis 命名空间
     */
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".motorcade.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:31:59
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao#addMotorcade(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity)
     */
    @Override
    public MotorcadeEntity addMotorcade(MotorcadeEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setModifyUser(entity.getCreateUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addMotorcade", entity);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:31:59
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao#deleteMotorcade(java.lang.String)
     */
    @Override
    public MotorcadeEntity deleteMotorcade(MotorcadeEntity entity) {
	// 请求参数合法性验证
	if(null == entity || StringUtils.isBlank(entity.getCode())){
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(NAMESPACE + "deleteMotorcade", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:31:59
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao#deleteMotorcadeMore(java.lang.String[], java.lang.String)
     */
    @Override
    public MotorcadeEntity deleteMotorcadeMore(String[] codes , String deleteUser) {
	// 请求合法性判断：
	if(ArrayUtils.isEmpty(codes)) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	MotorcadeEntity entity = new MotorcadeEntity();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);
	entity.setModifyUser(deleteUser);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	
	int result = getSqlSession().update(
		NAMESPACE + "deleteMotorcadeMore", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过CODE标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:31:59
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao#updateMotorcade(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity)
     */
    @Override
    public MotorcadeEntity updateMotorcade(MotorcadeEntity entity) {
	// 请求合法性判断：
	if (null == entity || StringUtils.isBlank(entity.getCode())) {
	    return entity;
	}
	
	// 更新要先删除旧的数据：
	MotorcadeEntity result = this.deleteMotorcade(entity);
	if (result == null) {
	    String msg = "更新时，作废失败";
	    LOGGER.error(msg);
	}

	// 组装插入参数
	entity.setId(UUIDUtils.getUUID());
	// CreateUser为传入的用户编码，CreateDate为当前时间
	Date now = new Date();
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	
	entity.setActive(FossConstants.ACTIVE);
	int resultNum = getSqlSession().insert(NAMESPACE + "addMotorcade", entity);
	return resultNum > NumberConstants.ZERO ? entity : null;
    }



    /**
     * 以下全为查询：
     */
    
    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:31:59
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao#queryMotorcadeByCode(java.lang.String)
     */
    @Override
    public MotorcadeEntity queryMotorcadeByCode(String code) {
	// 检查参数
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	
	// 构造查询条件：
	MotorcadeEntity entity=new MotorcadeEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setCode(code);
	
	return (MotorcadeEntity) getSqlSession().selectOne(NAMESPACE + "queryMotorcadeByCode", entity);
    }

    
    /**
     * 精确查询
     * 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao#queryMotorcadeBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MotorcadeEntity> queryMotorcadeBatchByCode(String[] codes) {
	// 请求参数合法性判断
	if (ArrayUtils.isEmpty(codes)){
	    return null;
	}
	
	// 构造查询条件：
	Map<String,Object> map = new HashMap<String , Object>();
	map.put("codes", codes);
	map.put("active", FossConstants.ACTIVE);
	
	return getSqlSession().selectList(NAMESPACE + "queryMotorcadeBatchByCode", map);
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao#queryMotorcadeExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MotorcadeEntity> queryMotorcadeExactByEntity(MotorcadeEntity entity, int start, int limit) {
	MotorcadeEntity queryEntity = entity == null ? new MotorcadeEntity() : entity;
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(NAMESPACE + "queryMotorcadeExactByEntity", queryEntity, rowBounds);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao#queryMotorcadeExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity)
     */
    @Override
    public long queryMotorcadeExactByEntityCount(MotorcadeEntity entity) {
	MotorcadeEntity queryEntity = entity == null ? new MotorcadeEntity() : entity;
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(NAMESPACE + "queryMotorcadeExactByEntityCount", queryEntity);
    }

    /**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:31:59
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao#queryMotorcadeMore(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MotorcadeEntity> queryMotorcadeByEntity(MotorcadeEntity entity, int start, int limit) {
	MotorcadeEntity queryEntity = entity == null ? new MotorcadeEntity() : entity;
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(NAMESPACE + "queryMotorcadeByEntity", queryEntity, rowBounds);
    }

    /**
     * 模糊查询
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:31:59
     * @see com.deppon.foss.module.base.baseinfo.server.dao.IMotorcadeDao#queryMotorcadeByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity)
     */
    @Override
    public long queryMotorcadeByEntityCount(MotorcadeEntity entity) {
	MotorcadeEntity queryEntity = entity == null ? new MotorcadeEntity() : entity;
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(NAMESPACE + "queryMotorcadeByEntityCount", queryEntity);
    }

    /**
     * 根据外场查询顶级车队
     */
	@Override
	public MotorcadeEntity queryTopFleetByTransferCenterCode(String code) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String , Object>();
		map.put("code", code);
		map.put("active", "Y");
		map.put("isTopFleet", "Y");
		return (MotorcadeEntity) getSqlSession().selectOne(NAMESPACE + "queryTopFleetByTransferCenterCode", map);
	}
    
    

}
